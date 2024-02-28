package ru.perm.v.el59.office.db.web;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class DocWFull extends AUIBean implements Serializable {
   private static final long serialVersionUID = -8157323115007372428L;
   @UI(
      readonly = false,
      title = "W-выписка",
      visible = true,
      width = 100
   )
   private DocW docw;
   @UI(
      readonly = false,
      title = "Сумма выписок БЕСТ",
      visible = true,
      width = 100,
      justify = Justify.RIGHT
   )
   private BigDecimal sumOrderBest = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Сумма оплаты",
      visible = true,
      width = 100,
      justify = Justify.RIGHT
   )
   private BigDecimal sumPay = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Сумма заказа поставщику",
      visible = true,
      width = 100,
      justify = Justify.RIGHT
   )
   private BigDecimal sumOrderSupplier = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Сумма отгрузок поставщика",
      visible = true,
      width = 100,
      justify = Justify.RIGHT
   )
   private BigDecimal sumInvoiceSupplier = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Сумма отгрузок покупателю",
      visible = true,
      width = 100,
      justify = Justify.RIGHT
   )
   private BigDecimal sumInvoiceBest = new BigDecimal("0.00");

   public BigDecimal getSumOrderBest() {
      return this.sumOrderBest;
   }

   public void setSumOrderBest(BigDecimal sumOrderBest) {
      this.sumOrderBest = sumOrderBest;
   }

   public BigDecimal getSumPay() {
      return this.sumPay;
   }

   public void setSumPay(BigDecimal sumPay) {
      this.sumPay = sumPay;
   }

   public BigDecimal getSumOrderSupplier() {
      return this.sumOrderSupplier;
   }

   public void setSumOrderSupplier(BigDecimal sumOrderSupplier) {
      this.sumOrderSupplier = sumOrderSupplier;
   }

   public BigDecimal getSumInvoiceSupplier() {
      return this.sumInvoiceSupplier;
   }

   public void setSumInvoiceSupplier(BigDecimal sumInvoiceSupplier) {
      this.sumInvoiceSupplier = sumInvoiceSupplier;
   }

   public BigDecimal getSumInvoiceBest() {
      return this.sumInvoiceBest;
   }

   public void setSumInvoiceBest(BigDecimal sumInvoiceBest) {
      this.sumInvoiceBest = sumInvoiceBest;
   }

   public static String getDescriptionClass() {
      return "W-выписка с  суммами";
   }

   public DocW getDocw() {
      return this.docw;
   }

   public void setDocw(DocW docw) {
      this.docw = docw;
   }
}
