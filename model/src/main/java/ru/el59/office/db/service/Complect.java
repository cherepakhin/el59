package ru.el59.office.db.service;

import ru.el59.dao.AEntity;

public class Complect extends AEntity {
   private static final long serialVersionUID = 719033737064612983L;
   protected static String descriptionClass = "Комплектность";

   public String getDescriptionClass() {
      return descriptionClass;
   }
}
