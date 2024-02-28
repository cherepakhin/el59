package ru.el59.office.db;

import java.math.BigDecimal;

public class DocItemXls {
   BigDecimal nnum = new BigDecimal("0.00");
   String name = "";
   BigDecimal qty = new BigDecimal("0.00");
   BigDecimal cena = new BigDecimal("0.00");

   public DocItemXls() {
      this.name = "";
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public BigDecimal getNnum() {
      return this.nnum;
   }

   public void setNnum(BigDecimal nnum) {
      this.nnum = nnum;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
   }
}
