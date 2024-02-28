package ru.perm.v.el59.office.shopmodel;

import java.math.BigDecimal;
import java.util.Date;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class BonusCardMove extends AEntity {
   private static final long serialVersionUID = 4476834272284143334L;
   @UI(
      readonly = false,
      title = "ID в магазине",
      visible = true,
      width = 6
   )
   private Long nn;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Дата выдачи",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "+Зачислено/-Списано",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Выписка",
      visible = true,
      width = 10
   )
   private DocTitle docTitle;
   @UI(
      readonly = false,
      title = "Бонусная карта",
      visible = true,
      width = 10
   )
   private BonusCard bonusCard;
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment = "";
   @UI(
      readonly = false,
      title = "Активация",
      visible = true,
      width = 4
   )
   private Boolean active = true;
   @UI(
      readonly = false,
      title = "Удален?",
      visible = true,
      width = 4
   )
   private Boolean deleted = false;

   public static String getDescriptionClass() {
      return "Движения по картам";
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public DocTitle getDocTitle() {
      return this.docTitle;
   }

   public void setDocTitle(DocTitle docTitle) {
      this.docTitle = docTitle;
   }

   public BonusCard getBonusCard() {
      return this.bonusCard;
   }

   public void setBonusCard(BonusCard bonusCard) {
      this.bonusCard = bonusCard;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Boolean getActive() {
      return this.active;
   }

   public void setActive(Boolean active) {
      this.active = active;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public Long getNn() {
      return this.nn;
   }

   public void setNn(Long nn) {
      this.nn = nn;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }
}
