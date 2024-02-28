package ru.el59.office.shopmodel;

import java.math.BigDecimal;

public class RewardCreditXLS {
   private String ddate;
   private BigDecimal numberContract;
   private BigDecimal sumCredit = new BigDecimal("0.00");
   private BigDecimal percent = new BigDecimal("0.00");
   private BigDecimal sumReward = new BigDecimal("0.00");

   public BigDecimal getSumCredit() {
      return this.sumCredit;
   }

   public void setSumCredit(BigDecimal sumCredit) {
      this.sumCredit = sumCredit;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal percent) {
      this.percent = percent;
   }

   public BigDecimal getSumReward() {
      return this.sumReward;
   }

   public void setSumReward(BigDecimal sumReward) {
      this.sumReward = sumReward;
   }

   public String getDdate() {
      return this.ddate;
   }

   public void setDdate(String ddate) {
      this.ddate = ddate;
   }

   public String toString() {
      return "RewardCreditXLS [ddate=" + this.ddate + ", numberContract=" + this.numberContract + ", sumCredit=" + this.sumCredit + ", percent=" + this.percent + ", sumReward=" + this.sumReward + "]";
   }

   public BigDecimal getNumberContract() {
      return this.numberContract;
   }

   public void setNumberContract(BigDecimal numberContract) {
      this.numberContract = numberContract;
   }
}
