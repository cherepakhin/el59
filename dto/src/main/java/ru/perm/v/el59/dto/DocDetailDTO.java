package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocDetailDTO extends AEntityDTO implements Serializable {
   private static final long serialVersionUID = -271722447534483296L;
   private Long docn;
   private Integer nnum;
   private BigDecimal qty = new BigDecimal("0.00");
   private BigDecimal price = new BigDecimal("0.00");
   private BigDecimal summain = new BigDecimal("0.00");
   private BigDecimal summaout = new BigDecimal("0.00");
   private TypeStockDTO typeStock;
   private String comment = "";
   private BigDecimal k1 = new BigDecimal("0.00");
   private BigDecimal k2 = new BigDecimal("0.00");
   private Boolean deleted = false;

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getPrice() {
      return this.price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getSummaout() {
      return this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
   }

   public TypeStockDTO getTypeStock() {
      return this.typeStock;
   }

   public void setTypeStock(TypeStockDTO typeStock) {
      this.typeStock = typeStock;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
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

   public Long getDocn() {
      return this.docn;
   }

   public void setDocn(Long docn) {
      this.docn = docn;
   }
}
