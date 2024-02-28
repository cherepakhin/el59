package ru.el59.office.db.routedoc;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class SumQty extends AUIBean implements Serializable {
   private static final long serialVersionUID = 2113683516567233910L;
   @UI(
      readonly = false,
      title = "Сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }
}
