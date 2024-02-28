package ru.perm.v.el59.office.db;

import ru.el59.dao.AEntity;

public class TypeFile extends AEntity implements Comparable<TypeFile> {
   private static final long serialVersionUID = -4726704981833103486L;

   public static String getDescriptionClass() {
      return "Тип файла";
   }

   public int compareTo(TypeFile o) {
      return this.name.compareToIgnoreCase(o.getName());
   }
}
