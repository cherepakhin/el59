package ru.perm.v.el59.office.shopmodel;

import java.io.Serializable;
import ru.perm.v.el59.dao.AEntity;

public class ShopRight extends AEntity implements Serializable {
   private static final long serialVersionUID = 2072281800206268258L;

   public ShopRight() {
      this.name = "?";
   }

   public static String getDescriptionClass() {
      return "Права";
   }
}
