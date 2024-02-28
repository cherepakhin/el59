package ru.perm.v.el59.office.dao.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.commerceml.IExporterToCommerceML;
import ru.perm.v.el59.office.critery.FeatureCritery;
import ru.perm.v.el59.office.critery.TovarCritery;
import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.office.db.dto.Annotation;
import ru.perm.v.el59.office.db.dto.elxml.Good;
import ru.perm.v.el59.office.db.subs.GroupTovarMainFeature;
import ru.perm.v.el59.office.db.subs.MainFeature;
import ru.perm.v.el59.office.db.subs.ValFeature;
import ru.perm.v.el59.office.iproviders.IManagerProvider;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.iproviders.IVarProvider;
import ru.perm.v.el59.office.iproviders.subs.IGroupTovarMainFeatureProvider;
import ru.perm.v.el59.office.iproviders.subs.IMainFeatureProvider;
import ru.perm.v.el59.office.iproviders.web.IRestWebProvider;
import ru.perm.v.el59.office.util.Helper;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.io.File;
import java.util.*;

public class TovarInfoProvider extends
        GenericDaoMessageImpl<TovarInfo, Integer> implements ITovarInfoProvider {
    private static final String DELIMETER_NAME_VAL = ":";
    private static final String DELIMETER_VAL = ";";
    private static Logger LOGGER = Logger.getLogger(TovarInfoProvider.class);
    private ITovarProvider tovarProvider;
    private IVarProvider varProvider;
    private IRestWebProvider restWebProvider;
    private IManagerProvider managerProvider;
    private IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider;
    private IMainFeatureProvider mainFeatureProvider;
    private String nameVarDirForPhoto;
    private String defaultDirForPhoto;
    private IExporterToCommerceML exporterToCommerceML;

    private Mapper mapper;

    public TovarInfoProvider(Class<TovarInfo> type) {
        super(type);
    }

    /**
     * В Эльдорадо н.номер может быть не числом. Проверка что н.номер число
     *
     * @param string
     * @return
     */
    public boolean checkString(String string) {
        if (string == null || string.length() == 0)
            return false;

        int i = 0;
        if (string.charAt(0) == '-')
            i = 1;

        char c;
        for (; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Создание товаров из xml Эльдорадо
     *
     * @return сообщение о результате
     * @throws Exception
     */
    @Override
    public String createByListGood(List<Good> listGood, String filename)
            throws Exception {
        LOGGER.info(filename);
        for (Good good : listGood) {
            LOGGER.info(good.getGoodCode());
            if (checkString(good.getGoodCode())) {
                Tovar tovarGood = getMapper().map(good, Tovar.class);
                Tovar tovar = (Tovar) getTovarProvider().initialize(
                        tovarGood.getNnum());
                // Если двойник , то пропустить
                if (tovar != null && tovar.getParentnnum() != null) {
                    continue;
                }
                // Если он уже есть, то тоже пропустить.
                if (tovar != null) {
                    continue;
                } else {
/*                    tovar.setFile(filename);
                    tovar.setName(tovarGood.getName());
                    tovar.setGroup(tovarGood.getGroup());
                    tovar.setTypetovar(tovarGood.getTypetovar());
                    tovar.setBrand(tovarGood.getBrand());
                    tovar.setDateinsert(new Date());
*/                    
                	
                	GroupTovar g = getTovarProvider().getGroupByTrade(
                            good.getMecat());
                	
                	// ВОТ оно создание!
                    tovar = tovarGood;
                    tovar.setFile(filename);
                    tovar.setGroup(g);
                }

                TovarInfo tovarInfoGood = getMapper()
                        .map(good, TovarInfo.class);
                TovarInfo tovarInfo = (TovarInfo) initialize(tovar.getNnum());
                if (tovarInfo != null) {
                    if (tovarInfoGood.getListFeaturePrice() != null) {
                        for (FeaturePrice f : tovarInfoGood
                                .getListFeaturePrice()) {
                            tovarInfo.addFeaturePrice(f);
                        }
                    }
                } else {
                    tovarInfo = tovarInfoGood;
                }
                tovarInfo.setTovar(tovar);
                try {
                    getSession().flush();
                    getSession().clear();
                    getTovarProvider().update(tovar);
                } catch (Exception e) {
                    LOGGER.error(e);
                }
                update(tovarInfo);
            }

        }
        return null;
    }

    @Override
    public List<String> getNotLoadedFiles(List<String> listFiles) {
        // Очистка от каталога
        List<String> listInputFileName = new ArrayList<String>();
        for (String filename : listFiles) {
            listInputFileName.add(getOnlyFileName(filename));
        }
        String sql = "select distinct file from Tovar";
        Query q1 = getSession().createQuery(sql);
        List<String> listLoadedFiles = (ArrayList<String>) q1.list();
        List<String> listDbFileName = new ArrayList<String>();
        for (String filename : listLoadedFiles) {
            if (filename != null) {
                listDbFileName.add(getOnlyFileName(filename));
            }
        }
        listInputFileName.removeAll(listDbFileName);
        return listInputFileName;
    }

    private String getOnlyFileName(String filename) {
        int pos = filename.lastIndexOf("/");
        if (pos == -1) {
            pos = filename.lastIndexOf("\\");
        }
        if (pos == -1)
            return filename;

        filename = filename.substring(pos + 1);
        return filename;
    }

    @Override
    public TovarInfo clearFeaturesWeb(TovarInfo tovarInfo) throws Exception {
        Hibernate.initialize(tovarInfo.getListFeature());
        tovarInfo.getListFeature().clear();
        update(tovarInfo);
        return tovarInfo;
    }

    @Override
    public TovarInfo deletePhoto(TovarInfo tovarInfo) {
        tovarInfo.getListPhoto().clear();
        return tovarInfo;
    }

    @Override
    public List<TovarInfo> getByCritery(Object critery) {
        TovarCritery tovar = (TovarCritery) critery;
        Criteria tovarInfoCritery = getSession()
                .createCriteria(TovarInfo.class);
        Criteria tovarCritery = tovarInfoCritery.createCriteria("tovar");
        Criteria groupCritery = tovarCritery.createCriteria("group");
        if (tovar.groups.size() > 0) {
            Disjunction gc = Restrictions.disjunction();
            for (int i = 0; i < tovar.groups.size(); i++) {
                String s = tovar.groups.get(i).getCod();
                s = Helper.clear00(s);
                gc.add(Restrictions.like("cod", s, MatchMode.START)
                        .ignoreCase());
            }
            groupCritery.add(gc);
        }
        if (!tovar.name.equals("")) {
            tovarCritery.add(Restrictions.like("name", tovar.name,
                    MatchMode.ANYWHERE).ignoreCase());
        }
        if (tovar.typetovar != null) {
            tovarCritery.add(Restrictions.eq("typetovar", tovar.typetovar));
        }
        if (tovar.nnum != null && tovar.nnum.size() > 0) {
            tovarCritery.add(Restrictions.in("nnum", tovar.nnum));
        }
        if (tovar.matrix != null) {
            tovarCritery.add(Restrictions.le("matrix.n", tovar.matrix));
        }
        if (tovar.isRest) {
            tovarInfoCritery
                    .add(Restrictions
                            .sqlRestriction("tovar_nnum in (select distinct tovar_nnum from db.restweb r )"));
        }
        if (tovar.deleted != null) {
            tovarCritery.add(Restrictions.eq("deleted", tovar.deleted));
        }
        if (tovar.qtyPhoto != null) {
            tovarInfoCritery.add(Restrictions.eq("qtyPhoto", tovar.qtyPhoto));
        }

        if (tovar.fromDateChenged != null) {
            tovar.fromDateChenged = Helper.getNullHour(tovar.fromDateChenged);
            LOGGER.info(String.format("С даты: %s", Helper.getDateFornmatter().format(tovar.fromDateChenged)));
            tovarCritery.add(Restrictions.ge("dateChanged",
                    tovar.fromDateChenged));
        }
        if (tovar.toDateChenged != null) {
            tovar.toDateChenged = Helper.getNullHour(tovar.toDateChenged);
            LOGGER.info(String.format("По дату: %s", Helper.getDateFornmatter().format(tovar.toDateChenged)));
            tovarCritery.add(Restrictions.le("dateChanged",
                    tovar.toDateChenged));
        }
        if (tovar.checked != null) {
            tovarCritery
                    .add(Restrictions.eq("checked", tovar.checked));
        }

        if (tovar.withoutDuplicates) {
            tovarCritery.add(Restrictions.isNull("parentnnum"));
        }

        tovarInfoCritery.addOrder(Order.asc("nnum"));
        List<TovarInfo> list = (ArrayList<TovarInfo>) tovarInfoCritery.list();
        return list;
    }

    @Override
    public TovarInfo initialize(Integer id) {
        TovarInfo tovarInfo = (TovarInfo) read(id);
        if (tovarInfo != null) {
//			LOGGER.info(tovarInfo.getNnum());
//			LOGGER.info("Hibernate.initialize(tovarInfo.getListFeature())");
            Hibernate.initialize(tovarInfo.getListFeature());
//			LOGGER.info("Hibernate.initialize(tovarInfo.getListFeaturePrice())");
            Hibernate.initialize(tovarInfo.getListFeaturePrice());
//			LOGGER.info("Hibernate.initialize(tovarInfo.getListPhoto())");
            Hibernate.initialize(tovarInfo.getListPhoto());
            if (tovarInfo.getListFeature() == null) {
                tovarInfo.setListFeature(new ArrayList<Feature>());
            }
            if (tovarInfo.getListFeaturePrice() == null) {
                tovarInfo.setListFeaturePrice(new ArrayList<FeaturePrice>());
            }
            if (tovarInfo.getListPhoto() == null) {
                tovarInfo.setListPhoto(new ArrayList<Photo>());
            }
            getTovarProvider().initialize(tovarInfo.getNnum());
        }
//		LOGGER.info("End init " + id);
        return tovarInfo;
    }

    @Override
    public byte[] getDataPhoto(Photo photo) throws Exception {
        byte[] data = Helper.getImageData(photo.getPath(), getDirForPhoto());
        return data;
    }

    @Override
    public void addPhoto(TovarInfo tovarInfo, Photo photo, byte[] data,
                         Manager manager) throws Exception {
        // String filename = FilenameUtils.getName(photo.getPath());
        Integer nnum = tovarInfo.getNnum();
        String filename = getNewName(nnum, photo.getPath(), tovarInfo);
        FileUtils.writeByteArrayToFile(new File(filename), data);
        String basepath = getDirForPhoto();
        filename = filename.replace(basepath, "");
        // filename=FilenameUtils.concat(dir, filename);
        // сохранение в базе
        photo.setPath(filename);
        tovarInfo.removePhoto(photo);
        photo.setManager(manager);
        photo.setDdate(new Date());
        tovarInfo.addPhoto(photo);
        update(tovarInfo);

        // загрузка файла
        // filename=getDirForPhoto()+filename;
        // FileUtils.writeByteArrayToFile(new File(filename), data);
    }

    private String getNewName(Integer nnum, String path, TovarInfo tovarInfo)
            throws Exception {
        int pos = path.lastIndexOf(".");
        String ext = path.substring(pos);
        String fullname = FilenameUtils.concat(getDirForPhoto(nnum), nnum + "_"
                + tovarInfo.getListPhoto().size() + ext);
        return fullname;
    }

    @Override
    public String getDirForPhoto() throws Exception {
        Var var = (Var) getVarProvider().getByEqName(getNameVarDirForPhoto());
        if (var == null) {
            getVarProvider().setValue(getNameVarDirForPhoto(),
                    getDefaultDirForPhoto());
            return getDefaultDirForPhoto();
        } else {
            return var.getVal();
        }
    }

    @Override
    public String getDirForPhoto(Integer nnum) throws Exception {
        String basedir = getDirForPhoto();
        String dir = getPathForNnum(basedir, nnum);
        return dir;
    }

    public String getNameVarDirForPhoto() {
        return nameVarDirForPhoto;
    }

    public void setNameVarDirForPhoto(String nameVarDirForPhoto) {
        this.nameVarDirForPhoto = nameVarDirForPhoto;
    }

    private String getPathForNnum(String basedir, Integer nnum) {
        String s = nnum.toString();
        if (s.length() < 8) {
            s = "0" + s;
        }
        // Название каталога хранения картинки первые 4 символа ном.номера
        s = s.substring(0, 4);
        String dir = FilenameUtils.concat(basedir, s);
        File d = new File(dir);
        if (!d.exists()) {
            d.mkdir();
        }
        return dir;
    }

    /*
     * @Override public byte[] getZipFileByListTovar(List<Tovar> listTovar)
     * throws IOException { List<TovarInfo> listTovarInfo = new
     * ArrayList<TovarInfo>(); for (Tovar tovar : listTovar) { TovarInfo
     * ti=(TovarInfo) initialize(tovar.getNnum()); if(ti!=null) {
     * listTovarInfo.add(ti); } } if(listTovarInfo.size()>0) { return
     * getZipFileByListTovarInfo(listTovarInfo); } return null; }
     *
     * @Override public byte[] getZipFileByListRestCur(List<RestCur>
     * listRestCur) throws IOException { List<TovarInfo> listTovarInfo = new
     * ArrayList<TovarInfo>(); for (RestCur restCur : listRestCur) { TovarInfo
     * ti=(TovarInfo) initialize(restCur.getTovar().getNnum()); if(ti!=null) {
     * listTovarInfo.add(ti); } } if(listTovarInfo.size()>0) { return
     * getZipFileByListTovarInfo(listTovarInfo); } return null; }
     *
     * @Override public byte[] getZipFileByListNnum(List<Integer> listNnum)
     * throws IOException { List<TovarInfo> listTovarInfo = new
     * ArrayList<TovarInfo>(); for (Integer nnum : listNnum) { TovarInfo
     * ti=(TovarInfo) initialize(nnum); if(ti!=null) { listTovarInfo.add(ti); }
     * } if(listTovarInfo.size()>0) { return
     * getZipFileByListTovarInfo(listTovarInfo); } return null; }
     */
    @Override
    public String getXmlCommerceMLByListTovarInfo(List<TovarInfo> listTovarInfo)
            throws Exception {
        return getExporterToCommerceML().getXmlCommerceML(listTovarInfo,
                getRestWebProvider().getListLiderSaleForSite());
    }

    public List<TovarInfo> getFromRestWeb() {
        Query query = getSession()
                .createQuery(
                        "select distinct t from TovarInfo t,RestWeb r where r.tovar.nnum=t.nnum order by t.nnum");
        List<TovarInfo> list = query.list();
        return list;
    }

    @Override
    public String getXmlCommerceByTovarCritery() throws Exception {
        List<TovarInfo> listTovarInfo = getFromRestWeb();
        if (listTovarInfo.size() > 0) {
            return getXmlCommerceMLByListTovarInfo(listTovarInfo);
        }
        return "";
    }

    @Override
    public List<Integer> getEmptyNnumTovarInfo() {
        String sql = "select distinct nnum from Tovar";
        Query q1 = getSession().createQuery(sql);
        List<Integer> listNnumTovar = (ArrayList<Integer>) q1.list();

        sql = "select distinct nnum from TovarInfo";
        q1 = getSession().createQuery(sql);
        List<Integer> listNnumTovarInfo = (ArrayList<Integer>) q1.list();
        listNnumTovar.removeAll(listNnumTovarInfo);
        return listNnumTovar;
    }

    @Override
    public void fillEmpty() throws Exception {
        List<Integer> listNnumTovar = getEmptyNnumTovarInfo();
        // int i=0;
        for (Integer nnum : listNnumTovar) {
            createByNnum(nnum);
            // System.out.println(i);
            // i++;
        }
    }

    @Override
    public void createByNnum(Integer nnum) throws Exception {
        // System.out.println(nnum);
        TovarInfo tovarInfo = new TovarInfo();
        tovarInfo.setNnum(nnum);
        Tovar tovar = (Tovar) getTovarProvider().initialize(nnum);
        tovarInfo.setTovar(tovar);
        /*
         * annotdel List<Feature> listFeature = tovar.getListFeature(); for
		 * (Feature feature : listFeature) { tovarInfo.addFeature(feature); }
		 */
        create(tovarInfo);
    }

    @Override
    public String getAnnotation(Tovar tovar) {
        return getAnnotation(tovar.getNnum());
    }

    @Override
    public String getAnnotation(Integer nnum) {
        TovarInfo tovarInfo = (TovarInfo) initialize(nnum);
        Annotation a = new Annotation();

        boolean found = false;
        for (FeaturePrice f : tovarInfo.getListFeaturePrice()) {
            if (f.getCode().equals(ConstAnnotFeature.ZBRAND)) {
                found = true;
            }
        }
        if (!found) {
            FeaturePrice fBrand = new FeaturePrice();
            fBrand.setCode(ConstAnnotFeature.ZBRAND);
            fBrand.setVal(tovarInfo.getTovar().getBrand());
            tovarInfo.getListFeaturePrice().add(fBrand);
        }
		/*
		 * fBrand=new FeaturePrice();
		 * fBrand.setCode(ConstAnnotFeature.ZNAMERUSSIA);
		 * fBrand.setVal(tovar.getBrand());
		 * tovarInfo.getListFeaturePrice().add(fBrand);
		 */
        String s = a.getAnnotation(tovarInfo.getListFeaturePrice());
        return s;
    }

    /**
     * Подсчет количества хар-к
     *
     * @param ti
     * @return
     */
    private TovarInfo calcQtyFeatures(TovarInfo ti) {
        ti.setQtyFeatures(ti.getListFeature().size());
        // ti.setQtyFeaturesAnnot(ti.getListFeaturePrice().size());
        return ti;
    }

    private TovarInfo calcQtyPhoto(TovarInfo ti) {
        ti.setQtyPhoto(ti.getListPhoto().size());
        return ti;
    }

    @Override
    public void update(TovarInfo o) throws Exception {
        TovarInfo ti = (TovarInfo) o;
//        super.update(ti);
        // ti = (TovarInfo) getSession().merge(o);
        ti.setDateChanged(new Date());
        ti = calcQtyFeatures(ti);
        ti = calcQtyPhoto(ti);
        if (o.getManager() == null) {
            o.setManager(getManagerProvider().getNullManager());
        }
        Tovar tovar = ti.getTovar();
        tovar.setDateChanged(new Date());
        tovar.setManager(ti.getManager());
        getTovarProvider().update(tovar);
        super.update(ti);
        // Заполнение кол-ва основных хар-к
        fillQtyMainFeature(ti);
//        super.update(ti);
    }

    @Override
    public Integer create(TovarInfo o) throws Exception {
        LOGGER.info(String.format("Create tovarInfo nnum=%d", o.getNnum()));
        TovarInfo ti = (TovarInfo) o;
        ti = calcQtyFeatures(ti);
        ti = calcQtyPhoto(ti);
        for (Feature feature : ti.getListFeature()) {
            feature.setOldname(feature.getName());
            feature.setOldval(feature.getVal());
            if(feature.getPrmry()==null) {
                feature.setPrmry(false);
            }
        }

        if (o.getManager() == null) {
            o.setManager(getManagerProvider().getNullManager());
        }
        return super.create(ti);
    }

    @Override
    public List<Feature> getFeatureByCritery(FeatureCritery featureCritery) {
        String sql = "select tovarinfo_nnum,name,val from db.tovarinfo_listfeature {f} where ";
        String param = "";
        if (featureCritery.getName() != null
                && !featureCritery.getName().equals("")) {
            sql = sql + "name=:param";
            param = featureCritery.getName();
        }
        if (featureCritery.val != null) {
            sql = sql + "val=:param";
            param = featureCritery.val;
        }
        Query q1 = getSession().createSQLQuery(sql);
        ;
        q1.setString("param", param);
        Iterator i = q1.list().iterator();
        ArrayList<Feature> ret = new ArrayList<Feature>();
        while (i.hasNext()) {
            Object[] o = (Object[]) i.next();
            Feature f = new Feature();
            TovarInfo ti = new TovarInfo();
            f.setTovarInfo(ti);
            ti.setNnum((Integer) o[0]);
            f.setName((String) o[1]);
            f.setVal((String) o[2]);
            ret.add(f);
        }
        return ret;
    }

    /**
     * Заполнение кол-ва осн-х характеристик. Полей qtyMainFeature и
     * qtyErrMainFeature
     *
     * @param tovarInfo
     * @return
     */
    private void fillQtyMainFeature(TovarInfo tovarInfo) {
        GroupTovarMainFeature groupTovarMainFeature = getGroupTovarMainFeatureProvider()
                .getGroupTovarMainFeatureByGroupTovar(
                        tovarInfo.getTovar().getGroup());
        if (groupTovarMainFeature != null) {
            Hibernate.initialize(tovarInfo.getListFeature());
            int qtyMainFeatureGroupTovar = groupTovarMainFeature
                    .getMainFeatures().size();
            List<Feature> listFeature = tovarInfo.getListFeature();
            int qtyMainFeatureTovarInfo = 0;
            for (Feature feature : listFeature) {
                if (feature != null && feature.getPrmry() != null
                        && feature.getPrmry()) {
                    qtyMainFeatureTovarInfo++;
                }
            }
//            tovarInfo.setQtyErrMainFeature(qtyMainFeatureGroupTovar
//                    - qtyMainFeatureTovarInfo);
            String sql = "update db.tovarinfo set " +
                    "qtyErrMainFeature = :qtyErrMainFeature," +
                    "qtyMainFeature = :qtyMainFeature " +
                    "where tovar_nnum=:tovar_nnum";
            Query q1 = getSession().createSQLQuery(sql);
            q1.setParameter("qtyErrMainFeature", qtyMainFeatureGroupTovar
                    - qtyMainFeatureTovarInfo);
            q1.setParameter("qtyMainFeature", qtyMainFeatureGroupTovar);
            q1.setParameter("tovar_nnum", tovarInfo.getTovar().getNnum());
            q1.executeUpdate();
        }

    }

    @Override
    public void calcMainFeature(TovarInfo tovarInfo) throws Exception {
        if (tovarInfo != null && tovarInfo.getNnum() != 0) {
            tovarInfo = read(tovarInfo.getNnum());
            fillQtyMainFeature(tovarInfo);
        }
//        super.update(tovarInfo);
    }

    @Override
    public TovarInfo generateTagPrice(TovarInfo tovarInfo) throws Exception {
        tovarInfo = initialize(tovarInfo.getNnum());
        // Вычисление установленных основных хар-к
        ArrayList<Feature> listPrimaryFeature = new ArrayList<Feature>();
        HashMap<Feature, ValFeature> hashPrimaryFeature = new HashMap<Feature, ValFeature>();
        List<MainFeature> mainFeatures = getMainFeatureProvider()
                .getMainFeatureForGroupTovar(tovarInfo.getTovar().getGroup());
        for (Feature feature : tovarInfo.getListFeature()) {
            if (feature.getPrmry()) {
                for (MainFeature mainFeature : mainFeatures) {
                    if (feature.getName().equals(mainFeature.getName())) {
                        MainFeature m = getMainFeatureProvider().init(
                                mainFeature);
                        for (ValFeature v : m.getListValFeature()) {
                            if (feature.getVal().equals(v.getName())) {
                                hashPrimaryFeature.put(feature, v);
                                listPrimaryFeature.add(feature);
                            }
                        }
                    }
                }
            }
        }
        if (listPrimaryFeature.size() > 0) {
            tovarInfo = setPriceTagForCode(tovarInfo, 0, hashPrimaryFeature,
                    listPrimaryFeature, ConstAnnotFeature.ZPROPERTIES);
        }
        if (listPrimaryFeature.size() > 1) {
            tovarInfo = setPriceTagForCode(tovarInfo, 1, hashPrimaryFeature,
                    listPrimaryFeature, ConstAnnotFeature.ZANNOTACIA);
        }
        if (listPrimaryFeature.size() > 2) {
            tovarInfo = setPriceTagForCode(tovarInfo, 2, hashPrimaryFeature,
                    listPrimaryFeature, ConstAnnotFeature.ZANNOTACIA2);
        }
        if (listPrimaryFeature.size() > 3) {
            String val = "";
            for (int i = 3; i < listPrimaryFeature.size(); i++) {
                ValFeature valFeature = hashPrimaryFeature
                        .get(listPrimaryFeature.get(i));
                if (!valFeature.getMainFeature().getNameForTag().isEmpty()) {
                    val = valFeature.getMainFeature().getNameForTag()
                            + DELIMETER_NAME_VAL;
                }
                val = val + valFeature.getForTag() + DELIMETER_VAL;
            }
            tovarInfo = setForPriceTag(tovarInfo,
                    ConstAnnotFeature.ZANNOTACIA3, val);
        }
        update(tovarInfo);
        return tovarInfo;
    }

    private TovarInfo setPriceTagForCode(TovarInfo tovarInfo, int index,
                                         HashMap<Feature, ValFeature> hashPrimaryFeature,
                                         ArrayList<Feature> listPrimaryFeature, String code) {
        ValFeature valFeature = hashPrimaryFeature.get(listPrimaryFeature
                .get(index));
        String val = "";
        if (!valFeature.getMainFeature().getNameForTag().isEmpty()) {
            val = valFeature.getMainFeature().getNameForTag()
                    + DELIMETER_NAME_VAL;
        }
        val = val + valFeature.getForTag();
        tovarInfo = setForPriceTag(tovarInfo, code, val);
        return tovarInfo;
    }

    private TovarInfo setForPriceTag(TovarInfo tovarInfo, String code,
                                     String val) {
        FeaturePrice f = null;
        for (FeaturePrice featurePrice : tovarInfo.getListFeaturePrice()) {
            if (featurePrice.getCode().equals(code)) {
                featurePrice.setVal(val);
                return tovarInfo;
            }
        }
        f = new FeaturePrice();
        f.setCode(code);
        f.setName(ConstAnnotFeature.getName(code));
        f.setVal(val);
        tovarInfo.addFeaturePrice(f);
        return tovarInfo;
    }

    public IMainFeatureProvider getMainFeatureProvider() {
        return mainFeatureProvider;
    }

    public void setMainFeatureProvider(IMainFeatureProvider mainFeatureProvider) {
        this.mainFeatureProvider = mainFeatureProvider;
    }

    public IGroupTovarMainFeatureProvider getGroupTovarMainFeatureProvider() {
        return groupTovarMainFeatureProvider;
    }

    public void setGroupTovarMainFeatureProvider(
            IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider) {
        this.groupTovarMainFeatureProvider = groupTovarMainFeatureProvider;
    }

    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public IVarProvider getVarProvider() {
        return varProvider;
    }

    public void setVarProvider(IVarProvider varProvider) {
        this.varProvider = varProvider;
    }

    public String getDefaultDirForPhoto() {
        return defaultDirForPhoto;
    }

    public void setDefaultDirForPhoto(String defaultDirForPhoto) {
        this.defaultDirForPhoto = defaultDirForPhoto;
    }

    public IRestWebProvider getRestWebProvider() {
        return restWebProvider;
    }

    public void setRestWebProvider(IRestWebProvider restWebProvider) {
        this.restWebProvider = restWebProvider;
    }

    public IManagerProvider getManagerProvider() {
        return managerProvider;
    }

    public void setManagerProvider(IManagerProvider managerProvider) {
        this.managerProvider = managerProvider;
    }

    public IExporterToCommerceML getExporterToCommerceML() {
        return exporterToCommerceML;
    }

    public void setExporterToCommerceML(
            IExporterToCommerceML exporterToCommerceML) {
        this.exporterToCommerceML = exporterToCommerceML;
    }

}
