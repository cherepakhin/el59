package ru.el59.office.db.service;

import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class Client extends AEntity {
   private static final long serialVersionUID = -2151008539477140663L;
   @UI(
      readonly = false,
      title = "Адрес",
      visible = true,
      width = 30
   )
   private String address;
   @UI(
      readonly = false,
      title = "Телефон",
      visible = true,
      width = 15
   )
   private String phone;
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment;
   protected static String descriptionClass = "Покупатель";

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Client() {
      this.name = "";
      this.address = "";
      this.phone = "";
      this.comment = "";
   }

   public String toString() {
      return this.name + ";" + this.address + ";" + this.phone;
   }
}
