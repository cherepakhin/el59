package ru.perm.v.el59.office.db.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.perm.v.el59.ui.UI;

public class TDocCallMaster extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = -3908276948791370917L;
   @UI(
      readonly = false,
      title = "Мастер",
      visible = true,
      width = 20
   )
   private String master = "";
   @UI(
      readonly = false,
      title = "Отремонтировал?",
      visible = true,
      width = 10
   )
   private String result = "";
   @UI(
      readonly = false,
      title = "Дата результата",
      visible = true,
      width = 10
   )
   private Date ddateresult = new Date();
   @UI(
      readonly = false,
      title = "Предполагаемая сумма ремонта",
      visible = true,
      width = 10
   )
   private BigDecimal summa = new BigDecimal("0.00");

   public static String getDescriptionClass() {
      return "Вызов мастера";
   }

   public String getResult() {
      return this.result;
   }

   public void setResult(String result) {
      this.result = result;
      this.setDdateresult(new Date());
   }

   public Date getDdateresult() {
      return this.ddateresult;
   }

   public void setDdateresult(Date ddateresult) {
      this.ddateresult = ddateresult;
   }

   public TDocCallMaster() {
      this.master = "";
      this.result = "";
      this.ddateresult = new Date();
      this.summa = new BigDecimal("0.00");
   }

   public String getMaster() {
      return this.master;
   }

   public void setMaster(String master) {
      this.master = master;
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString() + " ";
      }

      if (this.master != null) {
         ret = ret + "Мастер " + this.master;
      }

      return ret;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public String getContent() {
      return this.getDdateresult().toLocaleString() + " " + this.getMaster() + " " + this.getResult();
   }
}
