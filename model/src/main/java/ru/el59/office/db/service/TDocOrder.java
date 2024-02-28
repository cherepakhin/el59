package ru.el59.office.db.service;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.ui.UI;

public class TDocOrder extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = 7032646113566945408L;
   @UI(
      readonly = false,
      title = "Сумма ремонта",
      visible = true,
      width = 50
   )
   private BigDecimal summa = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Счет за ремонт";
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public String getContent() {
      return this.getSumma().toString();
   }
}
