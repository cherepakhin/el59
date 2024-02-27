package ru.perm.v.el59.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class RewardCreditDTO {
   private Long n = 0L;
   private Date ddate;
   private String numberContract = "";
   private BigDecimal sumCredit = new BigDecimal("0.00");
   private BigDecimal percent = new BigDecimal("0.00");
   private BigDecimal sumReward = new BigDecimal("0.00");
   private Long bank_n;
   private Long userShop_n;
   private String shop_cod;

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public String getNumberContract() {
      return this.numberContract;
   }

   public void setNumberContract(String numberContract) {
      this.numberContract = numberContract;
   }

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

   public Long getBank_n() {
      return this.bank_n;
   }

   public void setBank_n(Long bank_n) {
      this.bank_n = bank_n;
   }

   public Long getUserShop_n() {
      return this.userShop_n;
   }

   public void setUserShop_n(Long userShop_n) {
      this.userShop_n = userShop_n;
   }

   public String getShop_cod() {
      return this.shop_cod;
   }

   public void setShop_cod(String shop_cod) {
      this.shop_cod = shop_cod;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }
}
