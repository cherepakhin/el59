package ru.el59.office.db.service;

import java.io.Serializable;

public class TDocToShop extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -2140144457841904509L;

   public static String getDescriptionClass() {
      return "Доставка в магазин";
   }

   public TDocToShop() {
   }

   public TDocToShop(TDoc tdoc) {
      this();
      this.tdoc = tdoc;
      this.n = tdoc.getN();
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
