package ru.el59.office.db.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.office.db.Operation;
import ru.el59.office.db.Shop;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class MoveOperationSum extends AUIBean implements Serializable {
   private static final long serialVersionUID = 2090042038332598589L;
   @UI(
      readonly = false,
      title = "Операция",
      visible = true,
      width = 10
   )
   private Operation operation = new Operation();
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop = new Shop();
   @UI(
      readonly = false,
      title = "Себ-ть",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summain = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaout = new BigDecimal("0.00");

   public MoveOperationSum() {
      this.summain = new BigDecimal("0.00");
      this.summaout = new BigDecimal("0.00");
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getSummaout() {
      return this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
   }

   public Operation getOperation() {
      return this.operation;
   }

   public void setOperation(Operation operation) {
      this.operation = operation;
   }

   public static String getDescriptionClass() {
      return "Сумма по операции";
   }
}
