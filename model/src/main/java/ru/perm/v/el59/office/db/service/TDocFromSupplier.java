package ru.perm.v.el59.office.db.service;

import java.io.Serializable;

public class TDocFromSupplier extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -6885051606699151685L;

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public static String getDescriptionClass() {
      return "Получение компенсации";
   }

   public String getContent() {
      return getDescriptionClass();
   }
}
