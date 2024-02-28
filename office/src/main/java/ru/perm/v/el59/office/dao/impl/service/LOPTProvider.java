package ru.perm.v.el59.office.dao.impl.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.dao.CommonCritery;
import ru.perm.v.el59.dto.office.critery.CriteryLOPT;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.TypeDoc;
import ru.el59.office.db.service.LOPT;
import ru.el59.office.db.service.TDoc;
import ru.perm.v.el59.dto.office.iproviders.IContragentProvider;
import ru.perm.v.el59.dto.office.iproviders.IManagerProvider;
import ru.perm.v.el59.dto.office.iproviders.ITypeDocProvider;
import ru.perm.v.el59.dto.office.iproviders.service.ILOPTDao;
import ru.perm.v.el59.dto.office.iproviders.service.ITDocProvider;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LOPTProvider extends GenericDaoHibernateImpl<LOPT, Long> implements
        ILOPTDao, Serializable {

    private static final long serialVersionUID = 7318442119440459978L;
    private ITypeDocProvider typeDocProvider;
    private ITDocProvider tdocProvider;
    private IContragentProvider contragentProvider;
    private IManagerProvider managerProvider;

    public LOPTProvider(Class<LOPT> type) {
        super(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getByCritery(Object _critery) {
        CriteryLOPT criteryLopt = (CriteryLOPT) _critery;
        Criteria critery = getSession().createCriteria(LOPT.class);
        Criteria client = critery.createCriteria("client");
        Criteria move = critery.createCriteria("move");
        Criteria tovar = move.createCriteria("tovar");
        Criteria tdoc = critery.createCriteria("tdoc");
        Criteria tdocTypedoc = tdoc.createCriteria("typeDoc");
        Criteria lastTDoc = tdoc.createCriteria("lastTDoc");
        Criteria lastTDocTypedoc = lastTDoc.createCriteria("typeDoc", "tc");
        // Criteria tdoc = move.createCriteria("lastTDoc");
        if (criteryLopt.numdoc.length() > 0) {
            System.out.println("length " + criteryLopt.numdoc.length());
            System.out.println("numdoc " + criteryLopt.numdoc);
            critery.add(Restrictions.eq("n", Long.parseLong(criteryLopt.numdoc)));
        }
        if (criteryLopt.clientName.length() > 0)
            client.add(Restrictions.like("name", criteryLopt.clientName,
                    MatchMode.ANYWHERE).ignoreCase());
        if (criteryLopt.nnum.size() > 0)
            tovar.add(Restrictions.in("nnum", criteryLopt.nnum));
        if (criteryLopt.name.length() > 0)
            tovar.add(Restrictions.like("name", criteryLopt.name,
                    MatchMode.ANYWHERE).ignoreCase());
        if ((criteryLopt.fromDate != null) && (criteryLopt.toDate != null)
                && (!criteryLopt.onrest))
            tdoc.add(Restrictions.between("ddate", criteryLopt.fromDate,
                    criteryLopt.toDate));
        if (criteryLopt.arrshops.size() > 0)
            move.add(Restrictions.in("shop", criteryLopt.arrshops));
        if (criteryLopt.onrest) {
            lastTDocTypedoc.add(Restrictions.eq("isrest", criteryLopt.onrest));
        }
        if (criteryLopt.riskDays != null) {
            /*
             * select CURRENT_DATE-cast(tdld.ddate as date), CURRENT_DATE,
             * cast(tdld.ddate as date), period from db.lopt l inner join
             * db.tdoc td on l.tdoc_n=td.n inner join db.tdoc tdld on
             * td.lastTDoc_n=tdld.n inner join db.typedoc typedoc on
             * tdld.typedoc_n=typedoc.n
             *
             * where (CURRENT_DATE - cast(tdld.ddate as date))+1>=typedoc.period
             */
            // Criteria tdoc = critery.createCriteria("tdoc");
            // tdoc.createAlias("lastTDoc", "lasttdoc");
//             lastTDoc.add(Restrictions.le(lopt.testexpr, lopt.riskDays));
//            lastTDoc.add(Restrictions.sqlRestriction("(cast({alias}.ddate as date)+(select t.period from db.TypeDoc t where t.n={alias}.typedoc_n))-?<=?",
//                    new Object[]{new Date(), criteryLopt.riskDays}, null",);
        }
        ArrayList<LOPT> list = new ArrayList<LOPT>();
        critery.addOrder(Order.asc("n"));
        try {
            list = (ArrayList<LOPT>) critery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(LOPT o) throws Exception {
        super.update(o);
    }

    @Override
    public LOPT create(LOPT o, String autor) throws Exception {
        LOPT lopt = (LOPT) o;
        TDoc tdoc = new TDoc();
        Manager manager = (Manager) getManagerProvider().getByCritery(
                new CommonCritery(autor)).get(0);
        tdoc.setAutor(manager);
        tdoc.setDdate(new Date());
        TypeDoc typedoc = (TypeDoc) getTypeDocProvider().getByEqName(lopt.getDescriptionClass());
        tdoc.setTypeDoc(typedoc);
        TDoc rootparent = (TDoc) getTdocProvider().read(0L);
        tdoc.setParent(rootparent);
        tdoc.setRootDoc(rootparent);
        getTdocProvider().create(tdoc);
        tdoc.setLastTDoc(tdoc);
        lopt.setTdoc(tdoc);
        lopt.setN(tdoc.getN());
        if (lopt.getContragent() == null) {
            Contragent supplier = getContragentProvider().getByCritery(
                    new CommonCritery("Нет")).get(0);
            lopt.setContragent(supplier);
        }
        if (lopt.getContragent().getN() == null) {
            Contragent supplier = getContragentProvider().getByCritery(
                    new CommonCritery("Нет")).get(0);
            lopt.setContragent(supplier);
        }
        getSession().save(lopt);
        return lopt;
    }

    @Override
    public LOPT getByN(Long n) {
        return (LOPT) read(n);
    }

    public IContragentProvider getContragentProvider() {
        return contragentProvider;
    }

    public void setContragentProvider(IContragentProvider contragentProvider) {
        this.contragentProvider = contragentProvider;
    }

    public IManagerProvider getManagerProvider() {
        return managerProvider;
    }

    public void setManagerProvider(IManagerProvider managerProvider) {
        this.managerProvider = managerProvider;
    }

    public ITypeDocProvider getTypeDocProvider() {
        return typeDocProvider;
    }

    public void setTypeDocProvider(ITypeDocProvider typeDocProvider) {
        this.typeDocProvider = typeDocProvider;
    }

    public void setTdocProvider(ITDocProvider tdocProvider) {
        this.tdocProvider = tdocProvider;
    }

    public ITDocProvider getTdocProvider() {
        return tdocProvider;
    }

}
