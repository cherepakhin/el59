package ru.el59.office.db;

import ru.el59.dao.AEntity;

public class TypeDocStatus extends AEntity {
   private static final long serialVersionUID = 7717525872463965058L;

   public TypeDocStatus() {
      this.name = "-";
   }

   public static String getDescriptionClass() {
      return "Состояние документа";
   }
}
