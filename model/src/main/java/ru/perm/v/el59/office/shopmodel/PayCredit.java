package ru.perm.v.el59.office.shopmodel;

import java.math.BigDecimal;
import ru.el59.ui.UI;

public class PayCredit extends Payment {
   private static final long serialVersionUID = 7817518445013601680L;
   @UI(
      readonly = true,
      title = "Банк",
      visible = true,
      width = 20
   )
   private BankAction bankAction;
   @UI(
      readonly = false,
      title = "Первоначальный платеж",
      visible = true,
      width = 10
   )
   private BigDecimal firstPay = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Сумма кредита",
      visible = true,
      width = 10
   )
   private BigDecimal summaCredit = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Номер договора",
      visible = true,
      width = 15
   )
   private String contract = "";

   public static String getDescriptionClass() {
      return "Кредит";
   }

   public BankAction getBankAction() {
      return this.bankAction;
   }

   public void setBankAction(BankAction bankAction) {
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
