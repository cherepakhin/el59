package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.office.critery.RestCritery;
import ru.perm.v.el59.dto.office.critery.TovarCritery;
import ru.el59.office.db.*;
import ru.el59.office.db.dto.TovarDTO;
import ru.perm.v.el59..office.iproviders.IRestCurProvider;
import ru.perm.v.el59..office.iproviders.*;
import ru.perm.v.el59.office.util.Helper;
import ru.perm.v.el59.office.util.ILuceneSearcher;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class TovarProvider extends GenericDaoMessageImpl<Tovar, Integer>
        implements ITovarProvider {

    private final static Logger LOG = Logger.getLogger(TovarProvider.class.getCanonicalName());
    private IGroupTovarProvider groupTovarProvider;
    private ITovarInfoProvider tovarInfoProvider;
    private IRestCurProvider restCurProvider;
    private IManagerProvider managerProvider;
    private IHistoryTovarProvider historyTovarProvider;
    private ILuceneSearcher luceneSearcher;

    public TovarProvider(Class<Tovar> type) {
        super(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tovar> getByCritery(Object critery) {
        TovarCritery tovarQuery = (TovarCritery) critery;
        if (tovarQuery.isRest) {
            RestCritery rc = new RestCritery();
            rc.tovarCritery = tovarQuery;
            List<RestCur> list = getRestCurProvider().getByCritery(rc);
            List<Tovar> ret = new ArrayList<Tovar>();
            for (RestCur restCur : list) {
                if (!ret.contains(restCur.getTovar())) {
                    ret.add(restCur.getTovar());
                }
            }
            return ret;
        } else {
            Criteria tovarCritery = getSession().createCriteria(Tovar.class);
            if (tovarQuery.groups.size() > 0) {
                Criteria groupCritery = tovarCritery.createCriteria("group");
                Disjunction gc = Restrictions.disjunction();
                for (int i = 0; i < tovarQuery.groups.size(); i++) {
                    String s = tovarQuery.groups.get(i).getCod();
                    s = Helper.clear00(s);
                    gc.add(Restrictions.like("cod", s, MatchMode.START)
                            .ignoreCase());
                }
                groupCritery.add(gc);
            }
            if (tovarQuery.checked != null) {
                tovarCritery
                        .add(Restrictions.eq("checked", tovarQuery.checked));
            }
            if (tovarQuery.deleted != null) {
                tovarCritery.add(Restrictions.eq("deleted", tovarQuery.deleted));
            }
            if (tovarQuery.typetovar != null) {
                tovarCritery.add(Restrictions.eq("typetovar",
                        tovarQuery.typetovar));
            }
            if (tovarQuery.comment != null) {
                tovarCritery
                        .add(Restrictions.eq("comment", tovarQuery.comment));
            }
            if (!tovarQuery.name.equals("")) {
                tovarCritery.add(Restrictions.like("name", tovarQuery.name,
                        MatchMode.ANYWHERE).ignoreCase());
            }
            if (tovarQuery.nnum.size() > 0) {
                tovarCritery.add(Restrictions.in("nnum", tovarQuery.nnum));
            }
            if (tovarQuery.isRest) {
                tovarCritery
                        .add(Restrictions
                                .sqlRestriction("nnum in (select distinct tovar_nnum from db.restcur r where r.qty>0.00)"));
            }
            if (tovarQuery.fromDateInsert != null) {
                tovarQuery.fromDateInsert = Helper
                        .getNullHour(tovarQuery.fromDateInsert);
                tovarCritery.add(Restrictions.ge("dateinsert",
                        tovarQuery.fromDateInsert));
            }
            if (tovarQuery.toDateInsert != null) {
                tovarQuery.toDateInsert = Helper
                        .getNullHour(tovarQuery.toDateInsert);
                tovarCritery.add(Restrictions.le("dateinsert",
                        tovarQuery.toDateInsert));
            }
            if (tovarQuery.toDateChenged != null) {
                tovarQuery.toDateChenged = Helper
                        .getNullHour(tovarQuery.toDateChenged);
                tovarCritery.add(Restrictions.le("dateChanged",
                        tovarQuery.toDateChenged));
            }
            if (tovarQuery.fromDateChenged != null) {
                tovarQuery.fromDateChenged = Helper
                        .getNullHour(tovarQuery.fromDateChenged);
                tovarCritery.add(Restrictions.ge("dateChanged",
                        tovarQuery.fromDateChenged));
            }

            if (tovarQuery.withoutDuplicates) {
                tovarCritery.add(Restrictions.isNull("parentnnum"));
            }

            tovarCritery.addOrder(Order.asc("nnum"));
            ArrayList<Tovar> list = (ArrayList<Tovar>) tovarCritery.list();
            return list;
        }

        // return null;
    }

    @Override
    public List<Tovar> getDublicate(Tovar tovar) {
        /*
         * Старая схема поиска товара. Новая через lucene TovarCritery critery =
         * new TovarCritery(); Integer countChar = 3; List<Tovar> list = new
         * ArrayList<Tovar>(); if (tovar.getBrand().length() > 0) { do { int pos
         * = tovar.getName().indexOf(tovar.getBrand()) +
         * tovar.getBrand().length() + 1; String model =
         * tovar.getName().substring(pos, pos + countChar) .trim(); String name
         * = tovar.getBrand().trim() + "%" + model; name.replaceAll("\\W", "%");
         * critery.name = name; list = getByCritery(critery);
         * list.remove(tovar); countChar++; } while (list.size() > 4); }
         */
        List<Integer> nnums = null;
        try {
            nnums = getLuceneSearcher().getAnalog(tovar.getName());
        } catch (IOException e) {
            Logger.getLogger(TovarProvider.class.getName()).severe(e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Tovar> list = new ArrayList<Tovar>();
        if (nnums != null) {
            TovarCritery critery = new TovarCritery();
            critery.nnum = nnums;
            List<Tovar> _list = getByCritery(critery);
            _list.remove(tovar);
            list.addAll(_list);
        }
        return list;
    }

    @Override
    public void setDublicate(Integer nnumOrigin, Integer nnumDublicate)
            throws Exception {
        Tovar dublicateTovar = (Tovar) read(nnumDublicate);
        dublicateTovar.setParentnnum(nnumOrigin);
        update(dublicateTovar);
    }

    @Override
    public ArrayList<TovarDTO> getDTObyCritery(TovarCritery critery)
            throws Exception {
        ArrayList<Tovar> tovars = (ArrayList<Tovar>) getByCritery(critery);
        ArrayList<TovarDTO> ret = new ArrayList<TovarDTO>();
        for (Tovar tovar : tovars) {
            ret.add(getTovarDTO(tovar));
        }
        return ret;
    }

    private TovarDTO getTovarDTO(Tovar tovar) throws Exception {
        Integer nnumDoubler = tovar.getNnum();
        // Замена двойников
        if (tovar.getParentnnum() != null) {
            nnumDoubler = tovar.getNnum();
            tovar = read(tovar.getParentnnum());
        }
        TovarDTO dto = new TovarDTO(tovar);
        dto.setSrcNnum(nnumDoubler);
        dto.setAnnotation(getTovarInfoProvider().getAnnotation(tovar));
        return dto;
    }

    @Override
    public Long getCountByName(String s) {
        Long count = (Long) getSession().createQuery(
                "select count(*) from Tovar t where  lower(t.name) like '%"
                        + s.toLowerCase() + "%'").uniqueResult();
        return count;
    }

    @Override
    public Integer create(Tovar o) throws Exception {
        if (o.getManager() == null) {
            o.setManager(getManagerProvider().getNullManager());
        }
        Integer nnum = (Integer) super.create(o);
        TovarInfo tovarInfo = (TovarInfo) getTovarInfoProvider().initialize(
                nnum);

        if (tovarInfo == null) {
            tovarInfo = new TovarInfo();
            tovarInfo.setNnum(nnum);
            Tovar tovar = (Tovar) o;
            tovarInfo.setTovar(tovar);
            /*
             * annotdel List<Feature> listFeature = tovar.getListFeature(); for
             * (Feature feature : listFeature) { tovarInfo.addFeature(feature);
             * }
             */
            if (tovarInfo.getManager() == null) {
                tovarInfo.setManager(tovar.getManager());
            }
            getTovarInfoProvider().create(tovarInfo);
        }
        getLuceneSearcher().addTovar(o);
        return nnum;
    }

    @Override
    public Tovar generateNewNnum(Tovar tovar) {
        GroupTovar group = tovar.getGroup();
        Integer icodbest = group.getBest();
        String sql = "select min(t.nnum) "
                + "  from Tovar t where  t.nnum >:fromnnum and t.nnum<=:tonnum";
        Query q1 = getSession().createQuery(sql);

        q1.setInteger("fromnnum", icodbest * 1000 + 50000);
        q1.setInteger("tonnum", icodbest * 1000 + 100000 - 1);
        ArrayList q1list = (ArrayList) q1.list();
        Iterator iter = q1.list().iterator();
        Object row;
        Integer lnewnnum = 0;
        while (iter.hasNext()) {
            row = (Object) iter.next();
            try {
                lnewnnum = (Integer) row - 1;
            } catch (Exception e) {
                lnewnnum = icodbest * 1000 + 100000 - 1;
            }
        }
        if (lnewnnum == null)
            lnewnnum = icodbest * 1000 + 100000 - 1;
        tovar.setNnum(lnewnnum);
        return tovar;
    }

    public IGroupTovarProvider getGroupTovarProvider() {
        return groupTovarProvider;
    }

    public void setGroupTovarProvider(IGroupTovarProvider groupTovarProvider) {
        this.groupTovarProvider = groupTovarProvider;
    }

    /*
     * @Override public GroupTovar checkGroup(Tovar tovar) throws Exception {
     * return getGroupTovarProvider().checkGroup(tovar.getGroup()); }
     */

    public ITovarInfoProvider getTovarInfoProvider() {
        return tovarInfoProvider;
    }

    public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
        this.tovarInfoProvider = tovarInfoProvider;
    }

    @Override
    public List<Tovar> getByNnulTypetovar() {
        String sql = " "
                + "  from Tovar t where  t.typetovar is null and t.nnum not in (select nnum from TovarInfo)";
        Query q1 = getSession().createQuery(sql);
        List<Tovar> ret = q1.list();
        return ret;
    }

    @Override
    public void update(Tovar o) throws Exception {
        o.setDateChanged(new Date());
        if (o.getManager() == null) {
            o.setManager(getManagerProvider().getNullManager());
        }

        super.update(o);
        Tovar oldTovar = read(o.getNnum());
        if (oldTovar != null) {
            HistoryTovar historyTovar = new HistoryTovar();
            historyTovar.setTovar(oldTovar);
            historyTovar.setManager(oldTovar.getManager());
            historyTovar.setGroup(oldTovar.getGroup());
            historyTovar.setDescription(oldTovar.getName());
            historyTovar.setBrand(oldTovar.getBrand());
            historyTovar.setDdate(oldTovar.getDateChanged());
            historyTovar.setThing(oldTovar.getThing());
            historyTovar.setComment(oldTovar.getComment());
            getHistoryTovarProvider().create(historyTovar);
        }

        // getSession().merge(o);
    }

    @Override
    public List<String> getCommets() {
        String sql = "select distinct comment from Tovar where comment is not null and trim(comment)!=''";
        Query q1 = getSession().createQuery(sql);
        List l = q1.list();
        ArrayList<String> ret = new ArrayList<String>();
        for (Object r : l) {
            Object[] rr = (Object[]) r;
        }
        return ret;
    }

    public IManagerProvider getManagerProvider() {
        return managerProvider;
    }

    public void setManagerProvider(IManagerProvider managerProvider) {
        this.managerProvider = managerProvider;
    }

    public IHistoryTovarProvider getHistoryTovarProvider() {
        return historyTovarProvider;
    }

    public void setHistoryTovarProvider(
            IHistoryTovarProvider historyTovarProvider) {
        this.historyTovarProvider = historyTovarProvider;
    }

    @Override
    public GroupTovar getGroupByTrade(String mecat) {
        return getGroupTovarProvider().getGroupByTrade(mecat);
    }

    public IRestCurProvider getRestCurProvider() {
        return restCurProvider;
    }

    public void setRestCurProvider(IRestCurProvider restCurProvider) {
        this.restCurProvider = restCurProvider;
    }

    public ILuceneSearcher getLuceneSearcher() {
        return luceneSearcher;
    }

    public void setLuceneSearcher(ILuceneSearcher luceneSearcher) {
        this.luceneSearcher = luceneSearcher;
    }

    @Override
    public Tovar initialize(Integer id) {
        // Logger.getLogger(this.getClass()).info("Init tovar nnum="+id);
        Tovar t = super.initialize(id);
        if (t == null) {
            Logger.getLogger(this.getClass().getName()).info("Tovar null nnum=" + id);
            return null;
        }
        if (t.getGroup() == null) {
            Logger.getLogger(this.getClass().getName())
                    .info("GroupTovar null nnum=" + id);
            return null;
        }
        Hibernate.initialize(t.getGroup());
        Hibernate.initialize(t.getManager());
        return t;
    }

    @Override
    public Tovar setCenaSupplier(Tovar tovar, BigDecimal cena, Date ddate)
            throws Exception {
        boolean changed = false;
//		if (tovar.getCenaSupplier() == null) {
//			changed = true;
//		}
//		if (!changed && ddate.compareTo(tovar.getDateCenaSupplier()) > 0) {
//			changed = true;
//		}
//		if (!changed && cena.compareTo(tovar.getCenaSupplier()) < 0) {
//			changed = true;
//		}
//		if (changed) {
//			tovar.setCenaSupplier(cena);
//			tovar.setDateCenaSupplier(ddate);
//			// Вызываю getSession().saveOrUpdate, т.к не нужно менять дату
//			// изменения товара и не отслеживать историю
//			getSession().saveOrUpdate(tovar);
//		}
        return tovar;
    }

    @Override
    public void setDeteted(List<Tovar> listTovar, boolean flagDeleted)
            throws Exception {
        for (Tovar tovar : listTovar) {
            delete(tovar);
        }
    }

}
