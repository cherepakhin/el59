package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DocItemDTO implements Serializable {
   private static final long serialVersionUID = -958974076962566801L;
   private String numdoc = "";
   private Date ddate = new Date();
   private Integer nnum = 0;
   private BigDecimal qtyzakaz = new BigDecimal("0.00");
   private BigDecimal summain = new BigDecimal("0.00");
   private BigDecimal summaout = new BigDecimal("0.00");
   private String shopcod = "";
   private String sclad = "";
   private BigDecimal qtyout = new BigDecimal("0.00");
   private String typedocname = "";

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

   public Integer getNnum() {
      return this.nnum;
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
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

   public BigDecimal getQtyzakaz() {
      return this.qtyzakaz;
   }

   public void setQtyzakaz(BigDecimal qtyzakaz) {
      this.qtyzakaz = qtyzakaz;
   }

   public String getSclad() {
      return this.sclad;
   }

   public void setSclad(String sclad) {
      this.sclad = sclad;
   }

   public BigDecimal getQtyout() {
      return this.qtyout;
   }

   public void setQtyout(BigDecimal qtyout) {
      this.qtyout = qtyout;
   }

   public String getShopcod() {
      return this.shopcod;
   }

   public void setShopcod(String shopcod) {
      this.shopcod = shopcod;
   }

   public String getTypedocname() {
      return this.typedocname;
   }

   public void setTypedocname(String typedocname) {
      this.typedocname = typedocname;
   }
}
