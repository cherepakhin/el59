package ru.el59.office.shopmodel;

import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class Expense extends AEntity {
   private static final long serialVersionUID = 6618435810416581353L;
   @UI(
      readonly = false,
      title = "Доход(+1)/Расход(-1)",
      visible = true,
      width = 3
   )
   private Integer znak = 1;

   public static String getDescriptionClass() {
      return "Статья (доход/расход)";
   }

   public Integer getZnak() {
      return this.znak;
   }

   public void setZnak(Integer znak) {
      this.znak = znak;
   }
}
