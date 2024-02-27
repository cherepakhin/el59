package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DocTitleDTO extends AEntityDTO implements Serializable {
   private static final long serialVersionUID = 165544375370198734L;
   private String numdoc;
   private Date ddate = new Date();
   private BigDecimal summain = new BigDecimal("0.00");
   private BigDecimal summainold = new BigDecimal("0.00");
   private BigDecimal summaout = new BigDecimal("0.00");
   private TypeDocDTO typedoc;
   private TypeOperationDTO typeOperation;
   private UserShopDTO usershop;
   private ContragentDTO contragent;
   private TypeStockDTO typeStock;
   protected static String descriptionClass = "Шапка документа";
   protected BigDecimal sumpayment = new BigDecimal("0.00");
   protected BigDecimal diff = new BigDecimal("0.00");
   protected BigDecimal sumprepay = new BigDecimal("0.00");
   private String comment = "";
   private Boolean deleted = false;
   protected BigDecimal sumOutOrder;
   private TypePriceDTO typePrice;
   private BigDecimal k1;
   private BigDecimal k2;
   private Long parentn;

   public DocTitleDTO() {
      this.summain = new BigDecimal("0.00");
      this.sumOutOrder = new BigDecimal("0.00");
      this.comment = "";
      this.deleted = false;
      this.k1 = new BigDecimal("0.00");
      this.k2 = new BigDecimal("0.00");
   }

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getSummainold() {
      return this.summainold;
   }

   public void setSummainold(BigDecimal summainold) {
      this.summainold = summainold;
   }

   public BigDecimal getSummaout() {
      return this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
   }

   public TypeDocDTO getTypedoc() {
      return this.typedoc;
   }

   public void setTypedoc(TypeDocDTO typedoc) {
      this.typedoc = typedoc;
   }

   public TypeOperationDTO getTypeOperation() {
      return this.typeOperation;
   }

   public void setTypeOperation(TypeOperationDTO typeOperation) {
      this.typeOperation = typeOperation;
   }

   public UserShopDTO getUsershop() {
      return this.usershop;
   }

   public void setUsershop(UserShopDTO usershop) {
      this.usershop = usershop;
   }

   public ContragentDTO getContragent() {
      return this.contragent;
   }

   public void setContragent(ContragentDTO contragent) {
      this.contragent = contragent;
   }

   public TypeStockDTO getTypeStock() {
      return this.typeStock;
   }

   public void setTypeStock(TypeStockDTO typeStock) {
      this.typeStock = typeStock;
   }

   public BigDecimal getSumpayment() {
      return this.sumpayment;
   }

   public void setSumpayment(BigDecimal sumpayment) {
      this.sumpayment = sumpayment;
   }

   public BigDecimal getDiff() {
      return this.diff;
   }

   public void setDiff(BigDecimal diff) {
      this.diff = diff;
   }

   public BigDecimal getSumprepay() {
      return this.sumprepay;
   }

   public void setSumprepay(BigDecimal sumprepay) {
      this.sumprepay = sumprepay;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public BigDecimal getSumOutOrder() {
      return this.sumOutOrder;
   }

   public void setSumOutOrder(BigDecimal sumOutOrder) {
      this.sumOutOrder = sumOutOrder;
   }

   public TypePriceDTO getTypePrice() {
      return this.typePrice;
   }

   public void setTypePrice(TypePriceDTO typePrice) {
      this.typePrice = typePrice;
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

   public Long getParentn() {
      return this.parentn;
   }

   public void setParentn(Long parentn) {
      this.parentn = parentn;
   }
}
