package ru.perm.v.el59.office.dao.impl.shopmodel;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.DocDetailDTO;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TypeStock;
import ru.perm.v.el59.office.iproviders.*;
import ru.perm.v.el59.office.iproviders.critery.DocDetailCritery;
import ru.perm.v.el59.office.iproviders.critery.DocTitleCritery;
import ru.perm.v.el59.office.iproviders.critery.MoveCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.IDocDetailProvider;
import ru.perm.v.el59.office.iproviders.shopmodel.IDocTitleProvider;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypePDSProvider;
import ru.perm.v.el59.office.shopmodel.DocDetail;
import ru.perm.v.el59.office.shopmodel.DocTitle;
import ru.perm.v.el59.office.util.Helper;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class DocDetailProvider extends GenericDaoHibernateImpl<DocDetail, Long>
        implements IDocDetailProvider {

    private ITypeStockProvider typeStockProvider;
    private ITovarProvider tovarProvider;
    private IDocTitleProvider docTitleProvider;
    private IShopProvider shopProvider;
    private ITypePDSProvider typePDSProvider;
    private IMoveProvider moveProvider;
    private IOperationProvider operationProvider;
    private Integer nnumBonusCard = 71049743;
    private Logger logger = Logger.getLogger(DocDetailProvider.class.getName());

    public DocDetailProvider(Class<DocDetail> type) {
        super(type);
    }

    @Override
    public List<DocDetail> getByCritery(Object critery) {
        DocDetailCritery c = (DocDetailCritery) critery;
        Criteria criteria = getSession().createCriteria(DocDetail.class);
        if (c.nn != null) {
            criteria.add(Restrictions.eq("nn", c.nn));
        }
        if (c.doctitle_n != null) {
            criteria.add(Restrictions.eq("docTitle.n", c.doctitle_n));
        }
        if (c.shops.size() > 0) {
            criteria.add(Restrictions.in("shop", c.shops));
        }
        if (c.getTovars().size() > 0) {
            // Criteria tovarCriteria = criteria.createCriteria("tovar");
            criteria.add(Restrictions.in("tovar", c.getTovars()));

        }
        Criteria docTitleCriteria = criteria.createCriteria("docTitle");
        if (c.getFromdate() != null && c.getTodate() != null) {
            docTitleCriteria.add(Restrictions.ge("ddate", c.getFromdate()));
            docTitleCriteria.add(Restrictions.le("ddate", c.getTodate()));
        }
        if (c.getNameTypeDoc() != null) {
            Criteria typedocCriteria = docTitleCriteria
                    .createCriteria("typeDocShop");
            typedocCriteria.add(Restrictions.eq("name", c.getNameTypeDoc()));
        }
        List<DocDetail> list = criteria.list();
        // FIXME -DocDetailProvider доделать по DocDetailCritery
        return list;
    }

    @Override
    public DocDetail getByDTO(DocDetailDTO dto, Shop shop) {
        DocTitleCritery docTitleCritery = new DocTitleCritery();
        docTitleCritery.nn = dto.getDocn();
        docTitleCritery.shops.add(shop);
        List<DocTitle> listDoc = getDocTitleProvider().getByCritery(
                docTitleCritery);
        DocTitle docTitle;
        if (listDoc.size() > 0) {
            docTitle = listDoc.get(0);
        } else {
            Logger.getLogger(this.getClass().getName()).severe(
                    String.format("Не найден документ n=%d shop=%s",
                            dto.getDocn(), shop.getCod()));
            return null;
        }

        DocDetailCritery critery = new DocDetailCritery();
        critery.nn = dto.getN();
        critery.shops.add(shop);
        DocDetail d;
        List<DocDetail> list = getByCritery(critery);
        if (list.size() > 0) {
            d = list.get(0);
        } else {
            d = new DocDetail();
        }

        d.setDocTitle(docTitle);
        d.setNn(dto.getN());
        d.setQty(dto.getQty());
        d.setShop(shop);
        d.setSummain(dto.getSummain());
        d.setSummaout(dto.getSummaout());
        Tovar tovar = getTovarProvider().read(dto.getNnum());
        d.setTovar(tovar);
        if (dto.getTypeStock() != null) {
            TypeStock typestock = getTypeStockProvider().read(
                    dto.getTypeStock().getN());
            d.setTypeStock(typestock);
        }

        d.setK1(dto.getK1());
        d.setK2(dto.getK2());
        d.setPrice(dto.getPrice());
        d.setDeleted(dto.getDeleted());
        d.setComment(dto.getComment());
        /*
         * Long n = (Long) create(d); d.setN(n);
         */
        return d;
    }

    @Override
    public DocDetail getByDTO(DocDetailDTO dto, String shopCod) {
        if (dto == null || shopCod == null) {
            if (dto == null) {
                Logger.getLogger(this.getClass().getName()).severe(
                        String.format("dto is null"));

            }
            if (shopCod == null) {
                Logger.getLogger(this.getClass().getName()).severe(
                        String.format("shoCod is null"));
            }
            return null;
        }
        Shop shop = getShopProvider().initialize(shopCod);
        DocDetail d = getByDTO(dto, shop);
        return d;
    }

    public ITypeStockProvider getTypeStockProvider() {
        return typeStockProvider;
    }

    public void setTypeStockProvider(ITypeStockProvider typeStockProvider) {
        this.typeStockProvider = typeStockProvider;
    }

    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }

    public IDocTitleProvider getDocTitleProvider() {
        return docTitleProvider;
    }

    public void setDocTitleProvider(IDocTitleProvider docTitleProvider) {
        this.docTitleProvider = docTitleProvider;
    }

    public IShopProvider getShopProvider() {
        return shopProvider;
    }

    public void setShopProvider(IShopProvider shopProvider) {
        this.shopProvider = shopProvider;
    }

    @Override
    public List<DocDetail> getByDocTitle(DocTitle docTitle) {
        DocDetailCritery critery = new DocDetailCritery();
        critery.doctitle_n = docTitle.getN();
        return getByCritery(critery);
    }

    @Override
    public List<DocDetail> getControlPrice(DocDetailCritery critery) {
        String sql = "from DocDetail d "
                + "where  "
                + "d.docTitle.ddate>=:fromDate and d.docTitle.ddate<=:toDate "
                + "and d.qty>0 and abs(d.summaout/d.qty-d.price)>0.01 "
                + "and d.docTitle.shop in (:shops) and d.docTitle.typeOperation.name =:operation";
        Query q1 = getSession().createQuery(sql);
        q1.setParameter("fromDate", critery.getFromdate());
        q1.setParameter("toDate", critery.getTodate());
        q1.setParameter("operation", getDocTitleProvider().getNameReal());
        q1.setParameterList("shops", critery.shops);
        List list = q1.list();
        List<DocDetail> ret = List.of();
        for (Object object : list) {
            // Object[] o = (Object[]) object;
            DocDetail d = (DocDetail) object;
            ret.add(d);
        }
        return ret;
    }

    @Override
    public Long create(DocDetail docDetail) throws Exception {
        // Создать Move если ПДС и не W-выписка
        if ((getTypePDSProvider().isPDS(docDetail.getTovar().getNnum()) || (docDetail.getTovar().getNnum().compareTo(71049743) == 0))
                && !docDetail.getDocTitle().getTypeDocShop().getName()
                .equals(getDocTitleProvider().getNameOrderW())) {
            Move move = new Move();
            move = setByDocDetail(move, docDetail);
            move.setSummain(BigDecimal.ZERO);
            try {
                getMoveProvider().create(move);
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());

            }
        }
        return super.create(docDetail);
    }

    private Move setByDocDetail(Move m, DocDetail o) {
        // Сделал тупо
        if (o.getDocTitle().getTypeDocShop().getName().contains("Возврат")) {
            m.setOperation(getOperationProvider().getHashChrOperation()
                    .get("h"));
            m.setCodeoper("22");
            m.setTypeoper("5");
            m.setVid("1");
        } else {
            // Тогда реализация
            m.setOperation(getOperationProvider().getHashChrOperation()
                    .get("m"));
            m.setCodeoper("43");
            m.setTypeoper("1");
            m.setVid("2");
        }

        m.setAgent(o.getDocTitle().getUserShop().getName());
        m.setDdate(Helper.getNullHour(o.getDocTitle().getDdate()));
        m.setNumdoc(o.getDocTitle().getNumdoc());
        m.setQty(o.getQty());
        m.setSeller(o.getDocTitle().getUserShop().getName());
        m.setSubtype("");
        m.setSummain(o.getSummain());
        m.setSummaout(o.getSummaout());
        m.setFromstock(getTypeStockProvider().read(100L));
        m.setShop(o.getDocTitle().getShop());
        m.setTovar(o.getTovar());
        return m;
    }

    @Override
    public void update(DocDetail docDetail) throws Exception {
        // Обновить Move если ПДС
        if (getTypePDSProvider().isPDS(docDetail.getTovar().getNnum())) {
            Move move = getMoveByDocDetail(docDetail);
            if (move != null) {
                move = setByDocDetail(move, docDetail);
                move.setSummain(BigDecimal.ZERO);
                if (docDetail.getDeleted()) {
                    getMoveProvider().delete(move);
                } else {
                    getMoveProvider().update(move);
                }
            } else {
                if (!docDetail.getDeleted()) {
                    Move newMove = setByDocDetail(new Move(), docDetail);
                    newMove.setSummain(BigDecimal.ZERO);
                    getMoveProvider().create(newMove);
                }
            }
        }
        super.update(docDetail);
    }

    private Move getMoveByDocDetail(DocDetail docDetail) {
        MoveCritery moveCritery = new MoveCritery();
        moveCritery.numdoc = docDetail.getDocTitle().getNumdoc();
        moveCritery.arrshops.add(docDetail.getShop());
        moveCritery.nnum.add(docDetail.getTovar().getNnum());
        List<Move> listMove = getMoveProvider().getByCritery(moveCritery);
        Move ret = null;
        for (Move move : listMove) {
            if (move.getSummaout().compareTo(docDetail.getSummaout()) == 0) {
                ret = move;
            }
        }
        return ret;
    }

    @Override
    public void delete(DocDetail o) throws Exception {
        // Удалить из Move если ПДС
        if (getTypePDSProvider().isPDS(o.getTovar().getNnum())) {
            Move move = getMoveByDocDetail(o);
            if (move != null) {
                getMoveProvider().delete(move);
            }
        }
        super.delete(o);
    }

    public ITypePDSProvider getTypePDSProvider() {
        return typePDSProvider;
    }

    public void setTypePDSProvider(ITypePDSProvider typePDSProvider) {
        this.typePDSProvider = typePDSProvider;
    }

    public IMoveProvider getMoveProvider() {
        return moveProvider;
    }

    public void setMoveProvider(IMoveProvider moveProvider) {
        this.moveProvider = moveProvider;
    }

    public IOperationProvider getOperationProvider() {
        return operationProvider;
    }

    public void setOperationProvider(IOperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    public Integer getNnumBonusCard() {
        return nnumBonusCard;
    }

    public void setNnumBonusCard(Integer nnumBonusCard) {
        this.nnumBonusCard = nnumBonusCard;
    }

}
