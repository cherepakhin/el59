package ru.perm.v.el59.office.db;

import ru.perm.v.el59.dao.AEntity;

public class TypeStock extends AEntity {
   private static final long serialVersionUID = 2501211945029436377L;
   private Boolean worked = true;

   public static String getDescriptionClass() {
      return "Тип склада";
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public boolean isBrak() {
      return !this.worked;
   }

   public TypeStock getDTO() {
      return null;
   }
}
