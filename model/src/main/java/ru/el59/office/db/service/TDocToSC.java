package ru.el59.office.db.service;

import java.io.Serializable;
import ru.el59.ui.UI;

public class TDocToSC extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -2140144457841904509L;
   @UI(
      readonly = false,
      title = "Сервисный центр",
      visible = true,
      width = 20
   )
   private String sc = "";
   @UI(
      readonly = false,
      title = "Квитанция",
      visible = true,
      width = 10
   )
   private String nacl = "";

   public static String getDescriptionClass() {
      return "Передача в ремонт СЦ";
   }

   public String getSc() {
      return this.sc;
   }

   public void setSc(String sc) {
      this.sc = sc;
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public String getNacl() {
      return this.nacl;
   }

   public void setNacl(String nacl) {
      this.nacl = nacl;
   }

   public String getContent() {
      return this.getSc() + " Накл №" + this.getNacl();
   }
}
