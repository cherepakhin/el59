package ru.perm.v.el59.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDTO extends AEntityDTO {
   private static final long serialVersionUID = -5949306431687988251L;
   private TypePaymentDTO typepayment;
   private DocTitleDTO doctitle;
   protected BigDecimal summa = new BigDecimal("0.00");
   private Date ddate;
   private Date ddatecreate = new Date();
   private UserShopDTO usershop;
   private ContragentDTO contragent;
   private ExpenseDTO expense;
   private ReasonDTO reason;
   private TypeCashDTO typecash;
   private String comment = "";
   public String description = "";
   private BigDecimal k1 = new BigDecimal("0.00");
   private BigDecimal k2 = new BigDecimal("0.00");
   private Boolean deleted = false;
   private Long stornoPayment;

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
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

   public Date getDdatecreate() {
      return this.ddatecreate;
   }

   public void setDdatecreate(Date ddatecreate) {
      this.ddatecreate = ddatecreate;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
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

   public ContragentDTO getContragent() {
      return this.contragent;
   }

   public void setContragent(ContragentDTO contragent) {
      this.contragent = contragent;
   }

   public DocTitleDTO getDoctitle() {
      return this.doctitle;
   }

   public void setDoctitle(DocTitleDTO doctitle) {
      this.doctitle = doctitle;
   }

   public TypePaymentDTO getTypepayment() {
      return this.typepayment;
   }

   public void setTypepayment(TypePaymentDTO typepayment) {
      this.typepayment = typepayment;
   }

   public UserShopDTO getUsershop() {
      return this.usershop;
   }

   public void setUsershop(UserShopDTO usershop) {
      this.usershop = usershop;
   }

   public ExpenseDTO getExpense() {
      return this.expense;
   }

   public void setExpense(ExpenseDTO expense) {
      this.expense = expense;
   }

   public TypeCashDTO getTypecash() {
      return this.typecash;
   }

   public void setTypecash(TypeCashDTO typecash) {
      this.typecash = typecash;
   }

   public Long getStornoPayment() {
      return this.stornoPayment;
   }

   public void setStornoPayment(Long stornoPayment) {
      this.stornoPayment = stornoPayment;
   }

   public ReasonDTO getReason() {
      return this.reason;
   }

   public void setReason(ReasonDTO reason) {
      this.reason = reason;
   }
}
