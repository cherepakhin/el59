package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.el59.office.db.Shop;
import ru.el59.office.db.TypeStock;
import ru.el59.office.db.UserShop;
import ru.el59.office.shopmodel.DocDetail;
import ru.el59.office.shopmodel.DocTitle;
import ru.el59.office.shopmodel.TypeDocShop;
import ru.el59.office.shopmodel.TypePrice;
import ru.perm.v.el59.dto.DocTitleDTO;
import ru.perm.v.el59.dto.office.critery.DocTitleCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.iproviders.IContragentProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.ITypeStockProvider;
import ru.perm.v.el59.office.iproviders.IUserShopProvider;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypeOperationProvider;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class DocTitleProvider extends GenericDaoHibernateImpl<DocTitle, Long>
        implements IDocTitleProvider {

    private IContragentProvider contragentProvider;
    private ITypeDocShopProvider typeDocShopProvider;
    private ITypeDocStatusShopProvider typeDocStatusShopProvider;
    private ITypeOperationProvider typeOperationProvider;
    private ITypePriceProvider typePriceProvider;
    private ITypeStockProvider typeStockProvider;
    private IUserShopProvider usershopProvider;
    private IShopProvider shopProvider;
    private IDocDetailProvider docDetailProvider;

    private String nameReal;
    private String nameOrder;
    private String nameOrderW;
    private String nameInvoiceIn;
    private String nameInvoiceOut;
    private Long numberNullDocTitle;
    private DocTitle nullDocTitle;
    private String nameInInvoice;
    private String nameOutInvoice;

    public DocTitleProvider(Class<DocTitle> type) {
        super(type);
    }

    @Override
    public Long create(DocTitle o) throws Exception {
        if (o.getTypeStock() == null) {
            o.setTypeStock(getTypeStockProvider().read(0L));
        }
        Long n = null;
        try {
            n = (Long) getSession().save(o);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger("").info(e.getMessage());
        }
        return n;
    }

    @Override
    public DocTitle getNullDocTitle() {
        if (nullDocTitle == null) {
            nullDocTitle = read(numberNullDocTitle);
        }
        return nullDocTitle;
    }

    @Override
    public List<DocTitle> getByCritery(Object critery) {
//		DocTitleCritery c = (DocTitleCritery) critery;
//		Criteria criteria = getSession().createCriteria(DocTitle.class);
//		if ((c.fromdate != null) && (c.todate != null)) {
//			criteria.add(Restrictions.ge("ddate", c.fromdate));
//			criteria.add(Restrictions.le("ddate", c.todate));
//		}
//		if (c.nn != null) {
//			criteria.add(Restrictions.eq("nn", c.nn));
//		}
//		if (c.shops.size() > 0) {
//			criteria.add(Restrictions.in("shop", c.shops));
//		}
//		if (c.typedocs.size() > 0) {
//			criteria.add(Restrictions.in("typeDocShop", c.typedocs));
//		}
//		if (c.numdoc != null && !c.numdoc.isEmpty()) {
//			criteria.add(Restrictions.eq("numdoc", c.numdoc.trim()));
//		}
//		if (c.parent != null) {
//			criteria.add(Restrictions.eq("parent", c.parent));
//		}
//		if (c.deleted == null || c.deleted == false) {
//			criteria.add(Restrictions.eq("deleted", false));
//		}
//		if (c.typedocname != null) {
//			Criteria typedocCriteria = criteria.createCriteria("typeDocShop");
//			typedocCriteria.add(Restrictions.eq("name", c.typedocname));
//
//		}
//		List<DocTitle> list = criteria.list();
//		return list;
        return Collections.emptyList();
    }

    @Override
    public DocTitle getByDTO(DocTitleDTO dto, Shop shop) {
        DocTitleCritery docTitleCritery = new DocTitleCritery();
        docTitleCritery.nn = dto.getN();
        docTitleCritery.shops.add(shop);
        List<DocTitle> list = getByCritery(docTitleCritery);
        DocTitle dt = new DocTitle();
        if (list.size() > 0) {
            dt = list.get(0);
        } else {
            Logger.getLogger(String.valueOf(this.getClass().getName())).info(
                    String.format("Будет создан документ %d shop=%s",
                            dto.getN(), shop.getCod()));
        }
        dt.setContragent(getContragentProvider().getByEqName(
                dto.getContragent().getName()));
        dt.setDdate(dto.getDdate());
        dt.setDdatecreate(new Date());
        dt.setDeleted(dto.getDeleted());
        dt.setK1(dto.getK1());
        dt.setK2(dto.getK2());
        dt.setNn(dto.getN());
        dt.setNumdoc(dto.getNumdoc());
        dt.setShop(shop);
        dt.setSummain(dto.getSummain());
        dt.setSummainold(dt.getSummainold());
        dt.setSummaout(dto.getSummaout());
        dt.setSumOutOrder(dt.getSumOutOrder());
        dt.setSumprepay(dt.getSumprepay());
        if (dto.getTypedoc() != null) {
            dt.setTypeDocShop((TypeDocShop) getTypeDocShopProvider().read(
                    dto.getTypedoc().getN()));
        }
/*		if (dto.getTypeOperation() != null
				&& dto.getTypeOperation().getN() != null) {
			dt.setTypeOperation((TypeOperation) getTypeOperationProvider()
					.read(dto.getTypeOperation().getN()));
		}*/
        if (dto.getTypePrice() != null) {
            dt.setTypePrice((TypePrice) getTypePriceProvider().read(
                    dto.getTypePrice().getN()));
        }
        if (dto.getTypeStock() != null) {
            dt.setTypeStock((TypeStock) getTypeStockProvider().read(
                    dto.getTypeStock().getN()));
        } else {
            dt.setTypeStock(getTypeStockProvider().read(0L));
        }
        if (dto.getUsershop() != null) {
            dt.setUserShop((UserShop) getUsershopProvider().read(
                    dto.getUsershop().getN()));
        }
        if (dto.getParentn() == null || dto.getParentn().equals(0L)) {
            dt.setParent(read(0L));
        }
        if (dto.getParentn() != null && !dto.getParentn().equals(0L)) {
            DocTitleCritery c = new DocTitleCritery();
            c.nn = dto.getParentn();
            c.shops.add(shop);
            List<DocTitle> _list = getByCritery(c);
            if (_list.size() > 0) {
                dt.setParent(_list.get(0));
            } else {
                dt.setParent(read(0L));
            }
        }
        return dt;
    }

    @Override
    public DocTitle getByDTO(DocTitleDTO dto, String shopCod) {
        if (dto == null || shopCod == null)
            return null;
        Shop shop = getShopProvider().initialize(shopCod);
        DocTitle docTitle = getByDTO(dto, shop);
        return docTitle;
    }

    public IContragentProvider getContragentProvider() {
        return contragentProvider;
    }

    public void setContragentProvider(IContragentProvider contragentProvider) {
        this.contragentProvider = contragentProvider;
    }

    public ITypeDocShopProvider getTypeDocShopProvider() {
        return typeDocShopProvider;
    }

    public void setTypeDocShopProvider(ITypeDocShopProvider typeDocShopProvider) {
        this.typeDocShopProvider = typeDocShopProvider;
    }

    public ITypeDocStatusShopProvider getTypeDocStatusShopProvider() {
        return typeDocStatusShopProvider;
    }

    public void setTypeDocStatusShopProvider(
            ITypeDocStatusShopProvider typeDocStatusShopProvider) {
        this.typeDocStatusShopProvider = typeDocStatusShopProvider;
    }

    public ITypeOperationProvider getTypeOperationProvider() {
        return typeOperationProvider;
    }

    public void setTypeOperationProvider(
            ITypeOperationProvider typeOperationProvider) {
        this.typeOperationProvider = typeOperationProvider;
    }

    public ITypePriceProvider getTypePriceProvider() {
        return typePriceProvider;
    }

    public void setTypePriceProvider(ITypePriceProvider typePriceProvider) {
        this.typePriceProvider = typePriceProvider;
    }

    public ITypeStockProvider getTypeStockProvider() {
        return typeStockProvider;
    }

    public void setTypeStockProvider(ITypeStockProvider typeStockProvider) {
        this.typeStockProvider = typeStockProvider;
    }

    public IUserShopProvider getUsershopProvider() {
        return usershopProvider;
    }

    public void setUsershopProvider(IUserShopProvider usershopProvider) {
        this.usershopProvider = usershopProvider;
    }

    public IShopProvider getShopProvider() {
        return shopProvider;
    }

    public void setShopProvider(IShopProvider shopProvider) {
        this.shopProvider = shopProvider;
    }

    @Override
    public String getNameReal() {
        return nameReal;
    }

    public void setNameReal(String nameReal) {
        this.nameReal = nameReal;
    }

    @Override
    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    @Override
    public String getNameOrderW() {
        return nameOrderW;
    }

    public void setNameOrderW(String nameOrderW) {
        this.nameOrderW = nameOrderW;
    }

    public String getNameInvoiceIn() {
        return nameInvoiceIn;
    }

    public void setNameInvoiceIn(String nameInvoiceIn) {
        this.nameInvoiceIn = nameInvoiceIn;
    }

    public String getNameInvoiceOut() {
        return nameInvoiceOut;
    }

    public void setNameInvoiceOut(String nameInvoiceOut) {
        this.nameInvoiceOut = nameInvoiceOut;
    }

    public Long getNumberNullDocTitle() {
        return numberNullDocTitle;
    }

    public void setNumberNullDocTitle(Long numberNullDocTitle) {
        this.numberNullDocTitle = numberNullDocTitle;
    }

    @Override
    public String getNameInInvoice() {
        return nameInInvoice;
    }

    public void setNameInInvoice(String nameInInvoice) {
        this.nameInInvoice = nameInInvoice;
    }

    @Override
    public String getNameOutInvoice() {
        return nameOutInvoice;
    }

    public void setNameOutInvoice(String nameOutInvoice) {
        this.nameOutInvoice = nameOutInvoice;
    }

    @Override
    public void update(DocTitle docTitle) throws Exception {
        super.update(docTitle);
        if (docTitle.getDeleted()) {
            List<DocDetail> docDetails = getDocDetailProvider().getByDocTitle(docTitle);
            for (DocDetail docDetail : docDetails) {
                docDetail.setDeleted(true);
                getDocDetailProvider().update(docDetail);
            }
        }
    }

    public IDocDetailProvider getDocDetailProvider() {
        return docDetailProvider;
    }

    public void setDocDetailProvider(IDocDetailProvider docDetailProvider) {
        this.docDetailProvider = docDetailProvider;
    }

}
