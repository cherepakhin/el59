package ru.perm.v.el59.office.db;

import java.util.Date;
import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class Err extends AEntity {
   private static final long serialVersionUID = 4915454275092274564L;
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = true,
      title = "Дата ошибки",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = true,
      title = "Описание ошибки",
      visible = true,
      width = 20
   )
   private String description;

   public static String getDescriptionClass() {
      return "Ошибка";
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }
}
