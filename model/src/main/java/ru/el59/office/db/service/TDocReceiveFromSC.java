package ru.el59.office.db.service;

import java.io.Serializable;
import ru.el59.ui.UI;

public class TDocReceiveFromSC extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -2140144457841904509L;
   @UI(
      readonly = false,
      title = "Отремонтирован?",
      visible = true,
      width = 20
   )
   private String result = "";

   public static String getDescriptionClass() {
      return "Прием из ремонта";
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public String getContent() {
      return this.getResult();
   }

   public String getResult() {
      return this.result;
   }

   public void setResult(String result) {
      this.result = result;
   }
}
