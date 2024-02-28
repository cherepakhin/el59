package ru.el59.office.db.service;

import java.io.Serializable;

public class TDocStatementCustomer extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = 5820863881000416763L;

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public static String getDescriptionClass() {
      return "Заявление покупателя";
   }

   public String getContent() {
      return getDescriptionClass();
   }
}
