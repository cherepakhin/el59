package ru.perm.v.el59.office.dao.impl;

import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.office.critery.DocFileCritery;
import ru.perm.v.el59.dto.office.critery.DocSummaryCritery;
import ru.el59.office.db.*;
import ru.el59.office.db.dto.FileAttach;
import ru.el59.office.db.routedoc.DocSummary;
import ru.el59.office.db.routedoc.SumQty;
import ru.perm.v.el59.dto.office.emailer.IEmailer;
import ru.perm.v.el59.dto.office.iproviders.IDocFileProvider;
import ru.perm.v.el59.dto.office.iproviders.IManagerProvider;
import ru.perm.v.el59.dto.office.iproviders.ITovarProvider;
import ru.perm.v.el59.dto.office.iproviders.ITypeFileProvider;
import ru.perm.v.el59.dto.office.iproviders.routedoc.IReestrDocProvider;
import ru.perm.v.el59.office.util.Helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.logging.Logger;

public class DocFileProvider extends GenericDaoHibernateImpl<DocFile, Long>
        implements IDocFileProvider {

    private final static Logger LOGGER = Logger.getLogger(DocFileProvider.class.getName());
    private String dirForDoc;

    private String nameOrder;
    private String nameInvoice;
    private String nameReceive;

    private ITypeFileProvider typeFileProvider;
    private IReestrDocProvider reestrDocProvider;
    private IManagerProvider managerProvider;
    private ITovarProvider tovarProvider;
    private ArrayList<TypeFile> listTypeFile;
    private TypeFile tfOrder;
    private TypeFile tfInvoice;
    private TypeFile tfReceive;
    private IEmailer emailer;

    public DocFileProvider(Class<DocFile> type) {
        super(type);
    }

    @Override
    public List<DocFile> getByCritery(Object critery) {
        DocFileCritery c = (DocFileCritery) critery;
        Criteria criteria = getSession().createCriteria(DocFile.class);
        Criteria docCritery = criteria.createCriteria("doc");
        if ((c.fromDate != null) && (c.toDate != null)) {
            criteria.add(Restrictions.ge("ddate", c.fromDate));
            criteria.add(Restrictions.le("ddate", c.toDate));
        }
        if (c.listShop.size() > 0) {
            docCritery.add(Restrictions.in("shop", c.listShop));
        }
        if (c.listTypeFile.size() > 0) {
            criteria.add(Restrictions.in("typeFile", c.listTypeFile));
        }
        if (c.listContragent.size() > 0) {
            docCritery.add(Restrictions.in("contragent", c.listContragent));
        }
        if (c.listManager.size() > 0) {
            criteria.add(Restrictions.in("manager", c.listManager));
        }
        criteria.add(Restrictions.eq("bu", false));
        List<DocFile> list = criteria.list();
        Collections.sort(list);
        return list;
    }

    @Override
    public List<DocFile> getListDocFile(Doc doc) {
        Criteria criteria = getSession().createCriteria(DocFile.class);
        criteria.add(Restrictions.eq("doc", doc));
        criteria.addOrder(Order.asc("typeFile"));
        criteria.addOrder(Order.asc("bu"));
        criteria.addOrder(Order.asc("ddate"));
        List<DocFile> list = criteria.list();
        return list;
    }

    private static String getFileExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');
        if (i > 0 && i < f.length() - 1) {
            ext = f.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    @Override
    public Long create(DocFile docFile) throws Exception {
        String filename = docFile.getName();
        if (docFile.getBody() != null) {
            int pos = filename.lastIndexOf("\\");
            if (pos < 0) {
                pos = filename.lastIndexOf("/");
            }
            if (pos > 0) {
                filename = filename.substring(pos + 1);
            }
            Calendar cal = Calendar.getInstance();
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH) + 1;
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            String ext = getFileExtension(filename);
            int ii = filename.lastIndexOf(".");
            String name = "";
            if (ii > 0) {
                name = filename.substring(0, ii);
            }
            Long i = getMax();
            String newname = year.toString() + File.separator
                    + month.toString() + File.separator + day.toString()
                    + File.separator + name + "(" + i.toString() + ")" + "."
                    + ext;
            docFile.setName(newname);
            send(docFile);
            if (docFile.getDateSending() == null) {
                docFile.setDateSending(Helper.getNullDate());
            }
            if (docFile.getManagerAgreed() == null) {
                docFile.setManagerAgreed(getManagerProvider().getNullManager());
            }
            Long n = super.create(docFile);
            docFile.setN(n);
            // Если заказ, то добавить в путевой лист
            /*
             * if(docFile.getTypeFile().getName().equals(nameOrder)) {
             * getPathPageProvider().createInCurrentPathPage(docFile); }
             */
            return n;
        }
        return null;
    }

    @Override
    public String agree(DocFile selectedDocFile, Boolean agree,
                        Manager managerAgreed) throws Exception {
        if (selectedDocFile.getAgreed() && agree) {
            return "Документ уже согласован";
        }
        if (!selectedDocFile.getManagerAgreed().equals(
                getManagerProvider().getNullManager())) {
            if (!selectedDocFile.getManagerAgreed().equals(managerAgreed)) {
                return "Документ согласован "
                        + selectedDocFile.getManagerAgreed()
                        + ".\n"
                        + "Только он может согласовать или отменить согласование.";
            }
        }
        if (!agree) {
            if (!selectedDocFile.getManagerAgreed().equals(managerAgreed)) {
                return "Документ согласован "
                        + selectedDocFile.getManagerAgreed() + ".\n"
                        + "Только он может отменить согласование.";

            }
        }
        Date dateAgreed = new Date();
        if (!agree) {
            managerAgreed = getManagerProvider().getNullManager();
            dateAgreed = Helper.getNullDate();
        }
        selectedDocFile.setAgreed(agree);
        selectedDocFile.setManagerAgreed(managerAgreed);
        selectedDocFile.setDateAgreed(dateAgreed);
        update(selectedDocFile);
        return "";

    }


    public byte[] getBody(DocFile docFile) throws IOException {
        copyToFromFtp(docFile, false);
        return docFile.getBody();
    }

    public DocFile receive(DocFile docFile) {
        try {
            copyToFromFtp(docFile, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docFile;
    }

    public void send(DocFile docFile) {
        try {
            copyToFromFtp(docFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyToFromFtp(DocFile docFile, boolean store)
            throws IOException {
        if (store) {
            ByteArrayInputStream input = new ByteArrayInputStream(
                    docFile.getBody());
            try {
                FileUtils.copyInputStreamToFile(input, getFile(docFile));
                Logger.getLogger(this.getClass().getName()).info(
                        "Сохранение файла " + docFile.getName());
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).severe(
                        "Сохранение файла " + docFile.getName());
            }
        } else {
            byte[] data = FileUtils.readFileToByteArray(getFile(docFile));
            docFile.setBody(data);
        }
    }

    private File getFile(DocFile docFile) {
        String filename = getDirForDoc() + File.separator + docFile.getName();
        filename = filename.replace("\\", "/");
        return new File(filename);
    }

    @Override
    public void deleteByDoc(Doc doc) throws Exception {
        List<DocFile> listDocFile = getListDocFile(doc);
        for (DocFile docFile : listDocFile) {
            delete(docFile);
        }
    }

    @Override
    public List<DocItem> loadDbf(DocFile docFile) throws IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        ArrayList<DocItem> ret = new ArrayList<DocItem>();
        byte[] body = getBody(docFile);
        File tmpFile = File.createTempFile("out", "."
                + getFileExtension(docFile.getName()));
        tmpFile.deleteOnExit();
        FileUtils.writeByteArrayToFile(tmpFile, body);

        Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
        // Для windows
/*		Connection conn = DriverManager.getConnection(
				"jdbc:dbf:/" + tmpFile.getParent() + "?charSet=Cp866", "", "");*/

        Connection conn = DriverManager.getConnection(
                "jdbc:dbf:////" + tmpFile.getParent() + "?charSet=Cp866", "", "");
        String sqldbf = "select nnum,kol_i,cena_i from \"" + tmpFile.getName()
                + "\"";
        PreparedStatement stmtdbf = conn.prepareStatement(sqldbf);
        ResultSet rsdbf = stmtdbf.executeQuery();
        while (rsdbf.next()) {
            Integer nnum = Integer.parseInt(rsdbf.getString(1));
            BigDecimal qty = rsdbf.getBigDecimal(2);
            BigDecimal cena = rsdbf.getBigDecimal(3);
            DocItem docitem = new DocItem();
            docitem.setQty(qty);
            docitem.setCena(cena);
            docitem.setDoc(docFile.getDoc());
            Tovar t = getTovarProvider().read(nnum);
            if (t == null) {
                String err = "Не найден товар " + nnum;
                LOGGER.severe(err);
                throw new IOException(err);
            }
            // Проверка на двойники
            if (t.getParentnnum() != null) {
                t = getTovarProvider().read(t.getParentnnum());
            }
            docitem.setTovar(t);
            ret.add(docitem);

        }
        return ret;
    }

    private void refreshListTypeFile() {
        if (listTypeFile == null) {
            tfOrder = getTfOrder();
            tfInvoice = getTfInvoice();
            tfReceive = getTfReceive();
            listTypeFile = new ArrayList<TypeFile>();
            listTypeFile.add(tfOrder);
            listTypeFile.add(tfInvoice);
            listTypeFile.add(tfReceive);
        }
    }

    public TypeFile getTfOrder() {
        if (tfOrder == null) {
            tfOrder = getTypeFileProvider().getByEqName(getNameOrder());
        }
        return tfOrder;
    }

    public TypeFile getTfInvoice() {
        if (tfInvoice == null) {
            tfInvoice = getTypeFileProvider().getByEqName(getNameInvoice());
        }
        return tfInvoice;
    }

    public TypeFile getTfReceive() {
        if (tfReceive == null) {
            tfReceive = getTypeFileProvider().getByEqName(getNameReceive());
        }
        return tfReceive;
    }

    @Override
    public List<DocSummary> getListDocSummary(DocSummaryCritery critery) {
        if (critery.fromDate == null || critery.toDate == null) {
            return new ArrayList<DocSummary>();
        }
        refreshListTypeFile();

        Criteria criteria = getSession().createCriteria(DocFile.class);
        Criteria docCriteria = criteria.createCriteria("doc");
        criteria.add(Restrictions.ge("ddate", critery.fromDate));
        criteria.add(Restrictions.le("ddate", critery.toDate));
        criteria.add(Restrictions.in("typeFile", listTypeFile));
        if (critery.listShop.size() > 0) {
            docCriteria.add(Restrictions.in("shop", critery.listShop));
        }
        if (critery.listManager.size() > 0) {
            criteria.add(Restrictions.in("manager", critery.listManager));
        }
        criteria.add(Restrictions.eq("bu", false));
        List<DocFile> listDocFile = criteria.list();

        HashMap<Shop, HashMap<Contragent, DocSummary>> hash1 = new HashMap<Shop, HashMap<Contragent, DocSummary>>();
        for (DocFile docFile : listDocFile) {
            Doc doc = docFile.getDoc();
            if (doc.getShop().getCod().equals("00000")) {
                continue;
            }
            if (!hash1.containsKey(doc.getShop())) {
                HashMap<Contragent, DocSummary> hash2 = new HashMap<Contragent, DocSummary>();
                DocSummary docSummary = new DocSummary();
                docSummary = setTypeFileAndSumma(docSummary, docFile);
                docSummary.setShop(doc.getShop());
                docSummary.setContragent(doc.getContragent());
                hash2.put(doc.getContragent(), docSummary);
                hash1.put(doc.getShop(), hash2);
            } else {
                HashMap<Contragent, DocSummary> hash2 = hash1
                        .get(doc.getShop());
                if (!hash2.containsKey(doc.getContragent())) {
                    DocSummary docSummary = new DocSummary();
                    docSummary = setTypeFileAndSumma(docSummary, docFile);
                    docSummary.setShop(doc.getShop());
                    docSummary.setContragent(doc.getContragent());
                    hash2.put(doc.getContragent(), docSummary);
                } else {
                    DocSummary docSummary = hash2.get(doc.getContragent());
                    docSummary = setTypeFileAndSumma(docSummary, docFile);
                }
            }
        }
        ArrayList<DocSummary> ret = new ArrayList<DocSummary>();
        for (HashMap<Contragent, DocSummary> hash2 : hash1.values()) {
            ret.addAll(hash2.values());
        }
        return ret;
    }

    private DocSummary setTypeFileAndSumma(DocSummary docSummary,
                                           DocFile docFile) {
        if (docFile.getTypeFile().equals(tfOrder)) {
            increaseSumQty(docSummary.getOrder(), docFile.getSumma());
        }
        if (docFile.getTypeFile().equals(tfInvoice)) {
            increaseSumQty(docSummary.getInvoice(), docFile.getSumma());
        }
        if (docFile.getTypeFile().equals(tfReceive)) {
            increaseSumQty(docSummary.getReceive(), docFile.getSumma());
        }
        return docSummary;
    }

    private void increaseSumQty(SumQty sumQty, BigDecimal summa) {
        sumQty.setSumma(sumQty.getSumma().add(summa));
        sumQty.setQty(sumQty.getQty().add(new BigDecimal("1.00")));
    }

    @Override
    public List<Manager> getAutorsDocFile() {
        String sql = "select distinct manager from DocFile";
        Query q1 = getSession().createQuery(sql);
        List<Manager> list = q1.list();
        return list;
    }

    @Override
    public List<DocFile> refresh(List<DocFile> listDocFile) {
        ArrayList<DocFile> ret = new ArrayList<DocFile>();
        for (DocFile docFile : listDocFile) {
            ret.add(read(docFile.getN()));
        }
        return ret;
    }

    @Override
    public String sendByEmail(Manager manager, String recipients,
                              String message, String subject, List<DocFile> listDocFile) {
        List<FileAttach> listFileAttach = new ArrayList<FileAttach>();
        for (DocFile docFile : listDocFile) {
            byte[] body;
            try {
                body = getBody(docFile);
            } catch (IOException e) {
                Logger.getLogger(this.getClass().getName()).severe(
                        "Ошибка:" + e.getMessage());
                return e.getLocalizedMessage();
            }
            FileAttach fileAttach = new FileAttach();
            fileAttach.name = docFile.getName();
            fileAttach.body = body;
            listFileAttach.add(fileAttach);
        }
        String ans = getEmailer().send(manager, recipients, message, subject,
                listFileAttach);
        return ans;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    public String getNameInvoice() {
        return nameInvoice;
    }

    public void setNameInvoice(String nameInvoice) {
        this.nameInvoice = nameInvoice;
    }

    public String getNameReceive() {
        return nameReceive;
    }

    public void setNameReceive(String nameReceive) {
        this.nameReceive = nameReceive;
    }

    public ITypeFileProvider getTypeFileProvider() {
        return typeFileProvider;
    }

    public void setTypeFileProvider(ITypeFileProvider typeFileProvider) {
        this.typeFileProvider = typeFileProvider;
    }

    public String getDirForDoc() {
        return dirForDoc;
    }

    public void setDirForDoc(String dirForDoc) {
        this.dirForDoc = dirForDoc;
    }

    @Override
    public ArrayList<TypeFile> getListTypeFile() {
        refreshListTypeFile();
        return listTypeFile;
    }

    public IEmailer getEmailer() {
        return emailer;
    }

    public void setEmailer(IEmailer emailer) {
        this.emailer = emailer;
    }

    public IReestrDocProvider getReestrDocProvider() {
        return reestrDocProvider;
    }

    public void setReestrDocProvider(IReestrDocProvider reestrDocProvider) {
        this.reestrDocProvider = reestrDocProvider;
    }

    public IManagerProvider getManagerProvider() {
        return managerProvider;
    }

    public void setManagerProvider(IManagerProvider managerProvider) {
        this.managerProvider = managerProvider;
    }

    @Override
    public String delete(DocFile docFile, Manager manager) throws Exception {
        if (docFile.getAgreed()) {
            return "Вы не можете удалить этот документ. Он согласован.\n"
                    + "Перед удалением нужно отменить согласование";
        }
        if (docFile.getManager().equals(manager)) {
            delete(docFile);
            return "";
        } else {
            return "Вы не можете удалить этот документ. Он не ваш.";
        }
    }

    @Override
    public List<DocFile> getFreeForReestr(Date fromDate, Date toDate) {
        String sql = "select docfile from ReestrDoc r right outer join r.listDocFile docfile where r.n is null and docfile.ddate>=:fromDate and docfile.ddate<=:toDate and docfile.typeFile=:typeFile";
        Query q1 = getSession().createQuery(sql);
        q1.setParameter("fromDate", fromDate);
        q1.setParameter("toDate", toDate);
        q1.setParameter("typeFile", getTfInvoice());
        List<DocFile> list = q1.list();
        return list;
    }

    @Override
    public void delete(DocFile o) throws Exception {
        getReestrDocProvider().removeDocFile(o);
        // getPathPageProvider().delete(o.getd);
        getSession().flush();
        getSession().evict(o);
        getSession().clear();
        super.delete(o);
    }

    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }

}
