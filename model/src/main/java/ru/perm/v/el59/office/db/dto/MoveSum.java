package ru.perm.v.el59.office.db.dto;

import java.math.BigDecimal;
import ru.perm.v.el59.office.db.OpGroup;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class MoveSum extends AUIBean {
   @UI(
      readonly = false,
      title = "Операция",
      visible = true,
      width = 10
   )
   private OpGroup opGroup = new OpGroup();
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

   public MoveSum() {
      this.summain = new BigDecimal("0.00");
      this.summaout = new BigDecimal("0.00");
   }

   public OpGroup getOpGroup() {
      return this.opGroup;
   }

   public void setOpGroup(OpGroup opGroup) {
      this.opGroup = opGroup;
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

   public static String getDescriptionClass() {
      return "Сумма по движению";
   }
}
