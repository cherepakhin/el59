package ru.perm.v.el59.office.db.plan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.perm.v.el59.office.db.Shop;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class UptRptAsp extends AUIBean implements Serializable {
   private static final long serialVersionUID = 8903678251659177923L;
   @UI(
      readonly = true,
      title = "№",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   private Integer order = 0;
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = true,
      title = "К-во чеков",
      visible = true,
      width = 5,
      justify = Justify.RIGHT
   )
   private Long qtyCheck = 0L;
   @UI(
      readonly = true,
      title = "К-во товаров",
      visible = true,
      width = 5,
      justify = Justify.RIGHT
   )
   private BigDecimal qtyTovar = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "ТО",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumTovar = new BigDecimal("0.00");

   public UptRptAsp() {
      this.qtyCheck = 0L;
      this.qtyTovar = new BigDecimal("0.00");
      this.sumTovar = new BigDecimal("0.00");
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Long getQtyCheck() {
      return this.qtyCheck;
   }

   public void setQtyCheck(Long qtyCheck) {
      this.qtyCheck = qtyCheck;
   }

   public BigDecimal getQtyTovar() {
      return this.qtyTovar;
   }

   public void setQtyTovar(BigDecimal qtyTovar) {
      this.qtyTovar = qtyTovar;
   }

   public BigDecimal getSumTovar() {
      return this.sumTovar;
   }

   public void setSumTovar(BigDecimal sumTovar) {
      this.sumTovar = sumTovar;
   }

   public BigDecimal getUPT() {
      return this.getQtyCheck() > 0L ? this.getQtyTovar().divide(new BigDecimal(this.getQtyCheck()), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP) : new BigDecimal("0.00");
   }

   public BigDecimal getRPT() {
      return this.getQtyCheck() > 0L ? this.getSumTovar().divide(new BigDecimal(this.getQtyCheck()), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP) : new BigDecimal("0.00");
   }

   public BigDecimal getASP() {
      return this.getQtyCheck() > 0L ? this.getSumTovar().divide(this.getQtyTovar(), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP) : new BigDecimal("0.00");
   }

   public Integer getOrder() {
      return this.order;
   }

   public void setOrder(Integer order) {
      this.order = order;
   }
}
