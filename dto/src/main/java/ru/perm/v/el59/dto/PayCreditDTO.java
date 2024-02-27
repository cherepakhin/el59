package ru.perm.v.el59.dto;

import java.math.BigDecimal;

public class PayCreditDTO extends PaymentDTO {
   private static final long serialVersionUID = 2701323258449211261L;
   private BankActionDTO bankAction;
   private BigDecimal firstPay = new BigDecimal("0.00");
   private BigDecimal summaCredit = new BigDecimal("0.00");
   private String contract = "";

   public BankActionDTO getBankAction() {
      return this.bankAction;
   }

   public void setBankAction(BankActionDTO bankAction) {
      this.bankAction = bankAction;
   }

   public BigDecimal getFirstPay() {
      return this.firstPay;
   }

   public void setFirstPay(BigDecimal firstPay) {
      this.firstPay = firstPay;
   }

   public BigDecimal getSummaCredit() {
      return this.summaCredit;
   }

   public void setSummaCredit(BigDecimal summaCredit) {
      this.summaCredit = summaCredit;
   }

   public String getContract() {
      return this.contract;
   }

   public void setContract(String contract) {
      this.contract = contract;
   }
}
