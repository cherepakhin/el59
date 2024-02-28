package ru.el59.office.db.service;

import java.io.Serializable;
import ru.el59.ui.UI;

public class TDocSendToSC extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -2140144457841904509L;
   @UI(
      readonly = false,
      title = "Через кого",
      visible = true,
      width = 20
   )
   private String sender = "";

   public static String getDescriptionClass() {
      return "Отправка в СЦ";
   }

   public String getSender() {
      return this.sender;
   }

   public void setSender(String sender) {
      this.sender = sender;
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public String getContent() {
      return this.getSender();
   }
}
