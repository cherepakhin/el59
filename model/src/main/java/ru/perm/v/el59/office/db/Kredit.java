package ru.perm.v.el59.office.db;

import java.math.BigDecimal;

public class Kredit {
   private Shop shop;
   private String agentcode = "";
   private BigDecimal sum = new BigDecimal("0.00");
   private BigDecimal sumTO = new BigDecimal("0.00");

   public Kredit() {
      this.agentcode = "";
      this.sum = new BigDecimal("0.00");
      this.sumTO = new BigDecimal("0.00");
   }

   public BigDecimal getSumTO() {
      return this.sumTO;
   }

   public void setSumTO(BigDecimal sumTO) {
      this.sumTO = sumTO;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String getAgentcode() {
      return this.agentcode;
   }

   public void setAgentcode(String agentcode) {
      this.agentcode = agentcode;
   }

   public BigDecimal getSum() {
      return this.sum;
   }

   public void setSum(BigDecimal sum) {
      this.sum = sum;
   }
}
