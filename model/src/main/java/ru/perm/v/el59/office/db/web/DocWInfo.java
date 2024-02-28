package ru.perm.v.el59.office.db.web;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class DocWInfo extends AUIBean implements Serializable {
   private static final long serialVersionUID = -8157323115007372428L;
   @UI(
      readonly = false,
      title = "W-выписка",
      visible = true,
      width = 10
   )
   private DocW docw;
   @UI(
      readonly = false,
      title = "К-во в выписке",
      visible = true,
      width = 4,
      justify = Justify.RIGHT
   )
   private Integer qtyOrder = 0;
   @UI(
      readonly = false,
      title = "Зарезервировано",
      visible = true,
      width = 4,
      justify = Justify.RIGHT
   )
   private Integer qtyOrderSupplier = 0;
   @UI(
      readonly = false,
      title = "Приход в магазин",
      visible = true,
      width = 4,
      justify = Justify.RIGHT
   )
   private Integer qtyInputShop = 0;
   @UI(
      readonly = false,
      title = "Доставлено покупателю",
      visible = true,
      width = 4,
      justify = Justify.RIGHT
   )
   private Integer qtyToCustomer = 0;
   @UI(
      readonly = false,
      title = "Способ оплаты",
      visible = true,
      width = 10
   )
   private String methodPay = "";
   @UI(
      readonly = false,
      title = "Сумма оплаты",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal sumPay = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Исполнен?",
      visible = true,
      width = 10,
      justify = Justify.LEFT
   )
   private Boolean closed = false;
   @UI(
      readonly = false,
      title = "Счета загружены?",
      visible = true,
      width = 10,
      justify = Justify.LEFT
   )
   private Boolean isLoadDocFile = false;

   public static String getDescriptionClass() {
      return "W-выписка с  суммами";
   }

   public BigDecimal getSumPay() {
      return this.sumPay;
   }

   public void setSumPay(BigDecimal sumPay) {
      this.sumPay = sumPay;
   }

   public DocW getDocw() {
      return this.docw;
   }

   public void setDocw(DocW docw) {
      this.docw = docw;
   }

   public Integer getQtyOrderSupplier() {
      return this.qtyOrderSupplier;
   }

   public void setQtyOrderSupplier(Integer qtyOrderSupplier) {
      this.qtyOrderSupplier = qtyOrderSupplier;
   }

   public Integer getQtyInputShop() {
      return this.qtyInputShop;
   }

   public void setQtyInputShop(Integer qtyInputShop) {
      this.qtyInputShop = qtyInputShop;
   }

   public Integer getQtyToCustomer() {
      return this.qtyToCustomer;
   }

   public void setQtyToCustomer(Integer qtyToCustomer) {
      this.qtyToCustomer = qtyToCustomer;
   }

   public Integer getQtyOrder() {
      return this.qtyOrder;
   }

   public void setQtyOrder(Integer qtyOrder) {
      this.qtyOrder = qtyOrder;
   }

   public String getMethodPay() {
      return this.methodPay;
   }

   public void setMethodPay(String methodPay) {
      this.methodPay = methodPay;
   }

   public Boolean getClosed() {
      return this.qtyToCustomer >= this.qtyOrder ? true : false;
   }

   public void setClosed(Boolean closed) {
      this.closed = closed;
   }

   public Boolean getIsLoadDocFile() {
      return this.isLoadDocFile;
   }

   public void setIsLoadDocFile(Boolean isLoadDocFile) {
      this.isLoadDocFile = isLoadDocFile;
   }
}
