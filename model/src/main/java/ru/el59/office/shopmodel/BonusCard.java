package ru.el59.office.shopmodel;

import java.util.Date;
import ru.el59.dao.AEntity;
import ru.el59.office.db.Shop;
import ru.el59.ui.UI;

public class BonusCard extends AEntity {
   private static final long serialVersionUID = 1529008727113598192L;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Штрих-код",
      visible = true,
      width = 20
   )
   private String stroke;
   @UI(
      readonly = false,
      title = "Дата генерации",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();

   public static String getDescriptionClass() {
      return "Бонусная карта";
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String getStroke() {
      return this.stroke;
   }

   public void setStroke(String stroke) {
      this.stroke = stroke;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }
}
