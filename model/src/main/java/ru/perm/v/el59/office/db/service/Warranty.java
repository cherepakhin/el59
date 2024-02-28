package ru.perm.v.el59.office.db.service;

import ru.el59.dao.AEntity;

public class Warranty extends AEntity {
   private static final long serialVersionUID = -3522279063861998848L;

   public static String getDescriptionClass() {
      return "Вид гарантии";
   }

   public String toString() {
      return this.name;
   }
}
