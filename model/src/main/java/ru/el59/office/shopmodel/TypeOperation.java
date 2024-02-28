package ru.el59.office.shopmodel;

import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class TypeOperation extends AEntity {
   private static final long serialVersionUID = -7243177653987130864L;
   @UI(
      readonly = false,
      title = "Вид документа",
      visible = true,
      width = 10
   )
   private TypeDocShop typeDocShop;
   @UI(
      readonly = false,
      title = "Актуальный?",
      visible = true,
      width = 4
   )
   private Boolean worked = true;

   public static String getDescriptionClass() {
      return "Операция";
   }

   public TypeDocShop getTypeDocShop() {
      return this.typeDocShop;
   }

   public void setTypeDocShop(TypeDocShop typeDocShop) {
      this.typeDocShop = typeDocShop;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }
}
