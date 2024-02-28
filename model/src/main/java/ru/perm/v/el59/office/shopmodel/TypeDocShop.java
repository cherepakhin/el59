package ru.perm.v.el59.office.shopmodel;

import ru.el59.ui.UI;
import ru.perm.v.el59.dao.AEntity;

public class TypeDocShop extends AEntity {
   private static final long serialVersionUID = 4293203383520152218L;
   @UI(
      readonly = false,
      title = "Знак",
      visible = true,
      width = 3
   )
   private Integer znak;

   public TypeDocShop() {
      this.name = "?";
      this.znak = 1;
   }

   public Integer getZnak() {
      return this.znak;
   }

   public void setZnak(Integer znak) {
      this.znak = znak;
   }

   public static String getDescriptionClass() {
      return "Тип документа магазина";
   }
}
