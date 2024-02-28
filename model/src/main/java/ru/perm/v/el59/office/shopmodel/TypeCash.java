package ru.perm.v.el59.office.shopmodel;

import java.io.Serializable;
import ru.el59.dao.AEntity;

public class TypeCash extends AEntity implements Serializable {
   private static final long serialVersionUID = -3416321614235983286L;

   public TypeCash() {
      this.name = "?";
   }

   public static String getDescriptionClass() {
      return "Тип кассы";
   }
}
