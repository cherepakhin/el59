package ru.perm.v.el59.office.shopmodel;

import java.math.BigDecimal;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class PodCardRange extends AEntity {
   private static final long serialVersionUID = -2994108807221824003L;
   @UI(
      readonly = false,
      title = "От",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal min = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "До",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal max = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Начислить",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Таблица зачислений по подарочным картам";
   }

   public BigDecimal getMin() {
      return this.min;
   }

   public void setMin(BigDecimal min) {
      this.min = min;
   }

   public BigDecimal getMax() {
      return this.max;
   }

   public void setMax(BigDecimal max) {
      this.max = max;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }
}
