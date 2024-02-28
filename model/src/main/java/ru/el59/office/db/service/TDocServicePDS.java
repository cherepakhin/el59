package ru.el59.office.db.service;

import java.io.Serializable;

public class TDocServicePDS extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -9044163680443012887L;

   public static String getDescriptionClass() {
      return "Обслуживание по ПДС";
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
