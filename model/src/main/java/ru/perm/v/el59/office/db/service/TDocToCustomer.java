package ru.perm.v.el59.office.db.service;

import java.io.Serializable;

public class TDocToCustomer extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -2140144457841904509L;

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public static String getDescriptionClass() {
      return "Доставка покупателю";
   }

   public String getContent() {
      return getDescriptionClass();
   }
}
