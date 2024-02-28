package ru.perm.v.el59.office.shopmodel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import ru.el59.dao.AEntity;
import ru.perm.v.el59.office.db.CreditBank;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.UserShop;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class RewardCredit extends AEntity {
   private static final long serialVersionUID = 1073602138641527368L;
   private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "№ Договора",
      visible = true,
      width = 15
   )
   private String numberContract = "";
   @UI(
      readonly = false,
      title = "Сумма кредита",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumCredit = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "%",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal percent = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Вознаграждение",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumReward = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Банк",
      visible = true,
      width = 15
   )
   private CreditBank bank;
   @UI(
      readonly = false,
      title = "Сотрудник",
      visible = true,
      width = 15
   )
   private UserShop userShop;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 15
   )
   private Shop shop;

   public static String getDescriptionClass() {
      return "Вознаграждение банка за кредит";
   }

   public RewardCredit() {
   }

   public RewardCredit(String ddate, BigDecimal _numberContract, BigDecimal percent, BigDecimal sumCredit, BigDecimal sumReward) throws ParseException {
      this.ddate = sdf.parse(ddate);
      this.numberContract = _numberContract.toPlainString();
      this.percent = percent;
      this.sumCredit = sumCredit;
      this.sumReward = sumReward;
   }

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

   public UserShop getUserShop() {
      return this.userShop;
   }

   public void setUserShop(UserShop userShop) {
      this.userShop = userShop;
   }

   public CreditBank getBank() {
      return this.bank;
   }

   public void setBank(CreditBank bank) {
      this.bank = bank;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String toString() {
      return "RewardCredit [ddate=" + this.ddate + ", numberContract=" + this.numberContract + ", sumCredit=" + this.sumCredit + ", percent=" + this.percent + ", sumReward=" + this.sumReward + ", bank=" + this.bank + ", userShop=" + this.userShop + ", shop=" + this.shop + "]";
   }
}
