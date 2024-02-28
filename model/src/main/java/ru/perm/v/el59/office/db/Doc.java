package ru.perm.v.el59.office.db;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class Doc extends AEntity implements Comparable<Doc> {
   private static final long serialVersionUID = 4734702208826830838L;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Поставщик",
      visible = true,
      complex = true,
      width = 10
   )
   private Contragent contragent;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop = new Shop();
   private Date datereceive = new Date();
   @UI(
      readonly = false,
      title = "Сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summa = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "№ док-та",
      visible = true,
      width = 7
   )
   private String numdoc = "";
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 16
   )
   private String comment = "";
   @UI(
      readonly = false,
      title = "Доп-но",
      visible = true,
      width = 16
   )
   private String source = "";
   @UI(
      readonly = false,
      title = "Тип документа",
      visible = true,
      complex = true,
      width = 10
   )
   private TypeDoc typedoc = new TypeDoc();
   @UI(
      readonly = false,
      title = "Оплачен",
      visible = true,
      width = 4
   )
   private Boolean paid = false;
   @UI(
      readonly = false,
      title = "Создал",
      visible = true
   )
   private Manager manager;
   @UI(
      readonly = false,
      title = "Объем",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal volume = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Отправлен",
      visible = true,
      complex = true,
      width = 10
   )
   private Date dateSending;

   public static String getDescriptionClass() {
      return "Документ";
   }

   public Doc() {
      this.summa = new BigDecimal("0.00");
      this.numdoc = "";
      this.comment = "";
      Calendar c = Calendar.getInstance();
      c.set(1, 2001);
      c.set(2, 0);
      c.set(5, 1);
      c.set(10, 0);
      c.set(12, 0);
      c.set(13, 0);
      c.set(14, 0);
      this.dateSending = c.getTime();
   }

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
   }

   public TypeDoc getTypedoc() {
      return this.typedoc;
   }

   public void setTypedoc(TypeDoc typedoc) {
      this.typedoc = typedoc;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Date getDatereceive() {
      return this.datereceive;
   }

   public void setDatereceive(Date datereceive) {
      this.datereceive = datereceive;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public Boolean getPaid() {
      return this.paid;
   }

   public void setPaid(Boolean paid) {
      this.paid = paid;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public BigDecimal getVolume() {
      return this.volume;
   }

   public void setVolume(BigDecimal volume) {
      this.volume = volume;
   }

   public int compareTo(Doc o) {
      int ret = this.shop.compareTo(o.getShop());
      return ret == 0 ? this.contragent.compareTo(o.getContragent()) : ret;
   }

   public Date getDateSending() {
      return this.dateSending;
   }

   public void setDateSending(Date dateSending) {
      this.dateSending = dateSending;
   }

   public String getSource() {
      return this.source;
   }

   public void setSource(String source) {
      this.source = source;
   }
}
