package ru.perm.v.el59.office.db;

import ru.perm.v.el59.dao.AEntity;

public class CreditBank extends AEntity {
   private static final long serialVersionUID = 8578514187076512085L;

   public static String getDescriptionClass() {
      return "Банк";
   }

   public CreditBank() {
      this.name = "";
   }
}
