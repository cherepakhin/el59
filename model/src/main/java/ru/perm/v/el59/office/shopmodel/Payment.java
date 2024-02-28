package ru.perm.v.el59.office.shopmodel;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.UserShop;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.UI;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Payment extends AUIBean implements Serializable {
    private static final long serialVersionUID = 221611909878367487L;
    @UI(
            readonly = false,
            title = "ID в магазине",
            visible = true,
            width = 6
    )
    private Long nn;
    @UI(
            readonly = false,
            title = "Магазин",
            visible = true,
            width = 10
    )
    private Shop shop;
    @UI(
            readonly = false,
            title = "Тип платежа",
            visible = true,
            width = 10
    )
    private TypePayment typePayment;
    @UI(
            readonly = false,
            title = "Документ",
            visible = true,
            width = 10
    )
    private DocTitle docTitle;
    @UI(
            readonly = false,
            title = "Сумма",
            visible = true,
            width = 10
    )
    private BigDecimal summa = new BigDecimal("0.00");
    @UI(
            readonly = false,
            title = "Дата",
            visible = true,
            width = 10
    )
    private Date ddate = new Date();
    @UI(
            readonly = false,
            title = "Дата создания",
            visible = true,
            width = 10
    )
    private Date ddatecreate = new Date();
    @UI(
            readonly = false,
            title = "Контрагент",
            visible = true,
            width = 10
    )
    private Contragent contragent;
    @UI(
            readonly = false,
            title = "Статья",
            visible = true,
            width = 10
    )
    private Expense expense;
    @UI(
            readonly = false,
            title = "Основание",
            visible = true,
            width = 10
    )
    private Reason reason;
    @UI(
            readonly = false,
            title = "Наличие документов",
            visible = true,
            width = 10
    )
    private TypeCash typeCash;
    @UI(
            readonly = false,
            title = "Автор",
            visible = true,
            width = 10
    )
    private UserShop userShop;
    @UI(
            readonly = false,
            title = "Комментарий",
            visible = true,
            width = 20
    )
    private String comment = "";
    public BigDecimal nal = new BigDecimal("0.00");
    @UI(
            readonly = false,
            title = "К1",
            visible = true,
            width = 10
    )
    private BigDecimal k1 = new BigDecimal("0.00");
    @UI(
            readonly = false,
            title = "К2",
            visible = true,
            width = 10
    )
    private BigDecimal k2 = new BigDecimal("0.00");
    @UI(
            readonly = false,
            title = "Удален?",
            visible = true,
            width = 4
    )
    private Boolean deleted = false;
    @UI(
            readonly = false,
            title = "Сторнирующий документ",
            visible = true,
            width = 10
    )
    private Payment stornoPayment;

    public static String getDescriptionClass() {
        return "Платеж";
    }

    public Payment() {
        this.comment = "";
        this.summa = new BigDecimal("0.00");
        this.ddate = new Date();
    }

    public BigDecimal getSumma() {
        return this.summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public Date getDdate() {
        return this.ddate;
    }

    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Expense getExpense() {
        return this.expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Reason getReason() {
        return this.reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Long getNn() {
        return this.nn;
    }

    public void setNn(Long nn) {
        this.nn = nn;
    }

    public Date getDdatecreate() {
        return this.ddatecreate;
    }

    public void setDdatecreate(Date ddatecreate) {
        this.ddatecreate = ddatecreate;
    }

    public Contragent getContragent() {
        return this.contragent;
    }

    public void setContragent(Contragent contragent) {
        this.contragent = contragent;
    }

    public BigDecimal getNal() {
        return this.nal;
    }

    public void setNal(BigDecimal nal) {
        this.nal = nal;
    }

    public BigDecimal getK1() {
        return this.k1;
    }

    public void setK1(BigDecimal k1) {
        this.k1 = k1;
    }

    public BigDecimal getK2() {
        return this.k2;
    }

    public void setK2(BigDecimal k2) {
        this.k2 = k2;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Payment getStornoPayment() {
        return this.stornoPayment;
    }

    public void setStornoPayment(Payment stornoPayment) {
        this.stornoPayment = stornoPayment;
    }

    public TypePayment getTypePayment() {
        return this.typePayment;
    }

    public void setTypePayment(TypePayment typePayment) {
        this.typePayment = typePayment;
    }

    public DocTitle getDocTitle() {
        return this.docTitle;
    }

    public void setDocTitle(DocTitle docTitle) {
        this.docTitle = docTitle;
    }

    public TypeCash getTypeCash() {
        return this.typeCash;
    }

    public void setTypeCash(TypeCash typeCash) {
        this.typeCash = typeCash;
    }

    public UserShop getUserShop() {
        return this.userShop;
    }

    public void setUserShop(UserShop userShop) {
        this.userShop = userShop;
    }
}
