package ru.perm.v.el59.office.shopmodel;

import java.io.Serializable;
import java.util.Date;
import ru.perm.v.el59.office.db.Shop;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class SMS extends AUIBean implements Serializable {
   private static final long serialVersionUID = -7996914983135883652L;
   @UI(
      readonly = false,
      title = "Номер",
      visible = true,
      width = 5
   )
   protected Long n;
   @UI(
      readonly = false,
      title = "ID в магазине",
      visible = true,
      width = 5
   )
   private Long nn;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Создан",
      visible = true,
      width = 10
   )
   private Date ddatecreate = new Date();
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Телефон",
      visible = true,
      width = 10
   )
   private String phone;
   @UI(
      readonly = false,
      title = "Сообщение",
      visible = true,
      width = 10
   )
   private String message;
   @UI(
      readonly = false,
      title = "Отослано",
      visible = true,
      width = 10
   )
   private Date dateSend;
   @UI(
      readonly = false,
      title = "Ошибка",
      visible = true,
      width = 10
   )
   private String err = "";

   public SMS() {
      this.ddatecreate = new Date();
   }

   public Date getDdatecreate() {
      return this.ddatecreate;
   }

   public void setDdatecreate(Date ddatecreate) {
      this.ddatecreate = ddatecreate;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Long getNn() {
      return this.nn;
   }

   public void setNn(Long nn) {
      this.nn = nn;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public Date getDateSend() {
      return this.dateSend;
   }

   public void setDateSend(Date dateSend) {
      this.dateSend = dateSend;
   }

   public String getErr() {
      return this.err;
   }

   public void setErr(String err) {
      this.err = err;
   }
}
