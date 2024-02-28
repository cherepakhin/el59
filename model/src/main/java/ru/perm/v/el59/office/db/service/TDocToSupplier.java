package ru.perm.v.el59.office.db.service;

import java.io.Serializable;

public class TDocToSupplier extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = 547630959012640211L;

   public static String getDescriptionClass() {
      return "Отправка документов на компенсацию";
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public String getContent() {
      return getDescriptionClass();
   }
}
