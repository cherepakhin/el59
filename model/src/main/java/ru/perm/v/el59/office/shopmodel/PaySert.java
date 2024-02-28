package ru.perm.v.el59.office.shopmodel;

import ru.perm.v.el59.ui.UI;

public class PaySert extends Payment {
   private static final long serialVersionUID = -7978458045066350339L;
   @UI(
      readonly = false,
      title = "Тип",
      visible = true,
      width = 10
   )
   private TypeSert typeSert;
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 5
   )
   private Integer qty;

   public static String getDescriptionClass() {
      return "Сертификат";
   }

   public Integer getQty() {
      return this.qty;
   }

   public void setQty(Integer qty) {
      this.qty = qty;
   }

   public TypeSert getTypeSert() {
      return this.typeSert;
   }

   public void setTypeSert(TypeSert typeSert) {
      this.typeSert = typeSert;
   }
}
