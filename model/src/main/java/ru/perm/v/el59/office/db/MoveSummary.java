package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.el59.ui.AUIBean;
import ru.el59.ui.IUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class MoveSummary extends AUIBean implements Serializable, IUIBean {
   private static final long serialVersionUID = 1024291221063806666L;
   private Tovar tovar = new Tovar();
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private String shopname;
   @UI(
      readonly = true,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumQty = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Себ-ть",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumSummain = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Отпуск.цена",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumSummaout = new BigDecimal("0.00");
   private Date fromdate;
   private Date todate;

   public static String getDescriptionClass() {
      return "Сумма по движениям";
   }

   public Date getFromdate() {
      return this.fromdate;
   }

   public void setFromdate(Date fromdate) {
      this.fromdate = fromdate;
   }

   public Date getTodate() {
      return this.todate;
   }

   public void setTodate(Date todate) {
      this.todate = todate;
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public BigDecimal getSumQty() {
      return this.sumQty;
   }

   public void setSumQty(BigDecimal sumQty) {
      this.sumQty = sumQty;
   }

   public BigDecimal getSumSummain() {
      return this.sumSummain;
   }

   public void setSumSummain(BigDecimal sumSummain) {
      this.sumSummain = sumSummain;
   }

   public BigDecimal getSumSummaout() {
      return this.sumSummaout;
   }

   public void setSumSummaout(BigDecimal sumSummaout) {
      this.sumSummaout = sumSummaout;
   }

   public String getShopname() {
      return this.shopname;
   }

   public void setShopname(String shopname) {
      this.shopname = shopname;
   }
}
