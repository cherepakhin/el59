package ru.perm.v.el59.office.shopmodel;

import java.math.BigDecimal;
import java.util.Date;

import ru.perm.v.el59.ui.UI;
import ru.perm.v.el59.dao.AEntity;

public class Discount extends AEntity {
   private static final long serialVersionUID = 7333862563046565841L;
   @UI(
      readonly = false,
      title = "Скидка",
      visible = true,
      width = 10
   )
   private String val;
   @UI(
      readonly = false,
      title = "Действует на товар(не на выписку)?",
      visible = true,
      width = 4
   )
   private Boolean ondetail;
   @UI(
      readonly = false,
      title = "С даты",
      visible = true,
      width = 10
   )
   private Date fromdate;
   @UI(
      readonly = false,
      title = "По дату",
      visible = true,
      width = 10
   )
   private Date todate;

   public Discount() {
      this.name = "?";
      this.ondetail = true;
      this.val = "-";
      this.fromdate = new Date();
      this.todate = new Date();
   }

   public BigDecimal calc(BigDecimal price) {
      return new BigDecimal("0.00");
   }

   public String getVal() {
      return this.val;
   }

   public void setVal(String val) {
      this.val = val;
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

   public Boolean getOndetail() {
      return this.ondetail;
   }

   public void setOndetail(Boolean ondetail) {
      this.ondetail = ondetail;
   }

   public static String getDescriptionClass() {
      return "Скидка";
   }
}
