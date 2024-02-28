package ru.perm.v.el59.office.db;

import java.math.BigDecimal;

import ru.perm.v.el59.dao.AEntity;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class BonusK extends AEntity {
   private static final long serialVersionUID = -5384978789014822513L;
   @UI(
      readonly = true,
      title = "Коэф-т для 50 коп",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal minK = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Коэф-т для 0 коп",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal maxK = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Бонус";
   }

   public BigDecimal getMaxK() {
      return this.maxK;
   }

   public void setMaxK(BigDecimal maxK) {
      this.maxK = maxK;
   }

   public BigDecimal getMinK() {
      return this.minK;
   }

   public void setMinK(BigDecimal minK) {
      this.minK = minK;
   }
}
