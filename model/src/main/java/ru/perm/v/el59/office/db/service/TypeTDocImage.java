package ru.perm.v.el59.office.db.service;

import ru.perm.v.el59.dao.AEntity;

public class TypeTDocImage extends AEntity {
   private static final long serialVersionUID = -7169728514381264348L;

   public String toString() {
      return this.name;
   }

   public static String getDescriptionClass() {
      return "Тип документа";
   }
}
