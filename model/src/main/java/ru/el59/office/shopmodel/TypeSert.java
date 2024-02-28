package ru.el59.office.shopmodel;

import java.math.BigDecimal;
import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class TypeSert extends AEntity {
   private static final long serialVersionUID = -2031244952477313434L;
   @UI(
      readonly = false,
      title = "Стоимость",
      visible = true,
      width = 10
   )
   private BigDecimal cost = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Проверить скидку",
      visible = true,
      width = 4
   )
   private Boolean checkDiscount = false;
   @UI(
      readonly = false,
      title = "Работает?",
      visible = true,
      width = 4
   )
   private Boolean worked = true;

   public static String getDescriptionClass() {
      return "Тип подарочной карты";
   }

   public TypeSert() {
      this.cost = new BigDecimal("0.00");
   }

   public BigDecimal getCost() {
      return this.cost;
   }

   public void setCost(BigDecimal cost) {
      this.cost = cost;
   }

   public Boolean getCheckDiscount() {
      return this.checkDiscount;
   }

   public void setCheckDiscount(Boolean checkDiscount) {
      this.checkDiscount = checkDiscount;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }
}
