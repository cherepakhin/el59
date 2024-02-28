package ru.perm.v.el59.office.db.service;

import java.io.Serializable;
import ru.perm.v.el59.ui.UI;

public class TDocObmen extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = 5820863881000416763L;
   @UI(
      readonly = false,
      title = "Обмен?",
      visible = true,
      width = 20
   )
   private String result = "";

   public static String getDescriptionClass() {
      return "Обмен/Возврат денег";
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public String getResult() {
      return this.result;
   }

   public void setResult(String result) {
      this.result = result;
   }

   public String getContent() {
      return this.getResult();
   }
}
