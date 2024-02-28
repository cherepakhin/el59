package ru.perm.v.el59.office.db.service;

import java.io.Serializable;
import java.util.Date;
import ru.el59.ui.UI;

public class TDocRequestManager extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -2140144457841904509L;
   @UI(
      readonly = false,
      title = "Согласовано?",
      visible = true,
      width = 20
   )
   private String result = "";

   public static String getDescriptionClass() {
      return "Согласование";
   }

   public String getResult() {
      return this.result;
   }

   public void setResult(String result) {
      this.result = result;
      this.getTdoc().setDdate(new Date());
   }

   public TDocRequestManager() {
      this.result = "";
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString() + " " + this.getResult();
      }

      return ret;
   }

   public String getContent() {
      return this.getResult();
   }
}
