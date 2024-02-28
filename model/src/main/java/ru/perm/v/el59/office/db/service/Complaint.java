package ru.perm.v.el59.office.db.service;

import ru.perm.v.el59.dao.AEntity;

public class Complaint extends AEntity {
   private static final long serialVersionUID = 719033737064612983L;
   protected static String descriptionClass = "Причина обращения";

   public String getDescriptionClass() {
      return descriptionClass;
   }
}
