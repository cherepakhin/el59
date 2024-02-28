package ru.perm.v.el59.office.db;

import java.math.BigDecimal;

public class CalcParameter {
   private String abcd = "";
   private BigDecimal kobor = new BigDecimal("0.00");
   private BigDecimal qty = new BigDecimal("0.00");

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public String getAbcd() {
      return this.abcd;
   }

   public void setAbcd(String abcd) {
      this.abcd = abcd;
   }

   public BigDecimal getKobor() {
      return this.kobor;
   }

   public void setKobor(BigDecimal kobor) {
      this.kobor = kobor;
   }
}
