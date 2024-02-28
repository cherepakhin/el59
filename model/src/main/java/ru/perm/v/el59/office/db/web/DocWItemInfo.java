package ru.perm.v.el59.office.db.web;

import java.io.Serializable;
import java.math.BigDecimal;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class DocWItemInfo extends AUIBean implements Serializable {
   private static final long serialVersionUID = 7018949657606835838L;
   @UI(
      readonly = false,
      title = "W-выписка",
      visible = true,
      width = 10
   )
   private DocWItem docwItem;
   @UI(
      readonly = false,
      title = "Способ оплаты",
      visible = true,
      width = 20
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

   public static String getDescriptionClass() {
      return "Заказанный товар W-выписки";
   }

   public BigDecimal getSumPay() {
      return this.sumPay;
   }

   public void setSumPay(BigDecimal sumPay) {
      this.sumPay = sumPay;
   }

   public String getMethodPay() {
      return this.methodPay;
   }

   public void setMethodPay(String methodPay) {
      this.methodPay = methodPay;
   }

   public DocWItem getDocwItem() {
      return this.docwItem;
   }

   public void setDocwItem(DocWItem docwItem) {
      this.docwItem = docwItem;
   }
}
