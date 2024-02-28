package ru.el59.office.db;

import java.math.BigDecimal;

public class ShopSqRest {
   Shop shop = new Shop();
   BigDecimal restfact = new BigDecimal("0.00");
   BigDecimal restplan = new BigDecimal("0.00");

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public BigDecimal getRestfact() {
      return this.restfact;
   }

   public void setRestfact(BigDecimal restfact) {
      this.restfact = restfact;
   }

   public BigDecimal getRestplan() {
      return this.restplan;
   }

   public void setRestplan(BigDecimal restplan) {
      this.restplan = restplan;
   }
}
