package ru.perm.v.el59.office.shopmodel;

import java.math.BigDecimal;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.ui.UI;

public class TovarBonus extends AEntity {
   private static final long serialVersionUID = 6848587900270680395L;
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      width = 10
   )
   private Tovar tovar;
   @UI(
      readonly = false,
      title = "Процент",
      visible = true,
      width = 10
   )
   private BigDecimal percent = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Для зачисления на карту(Да), для списания(Нет)",
      visible = true,
      width = 4
   )
   private Boolean increase = true;

   public static String getDescriptionClass() {
      return "Процент отчисления на боусную карту";
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public BigDecimal getPercent() {
      return this.percent;
   }

   public void setPercent(BigDecimal percent) {
      this.percent = percent;
   }

   public Boolean getIncrease() {
      return this.increase;
   }

   public void setIncrease(Boolean increase) {
      this.increase = increase;
   }
}
