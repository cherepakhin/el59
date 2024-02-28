package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class RestSupplier extends AUIBean implements Serializable, ITovar {
   private static final long serialVersionUID = -4597225167702278755L;
   @UI(
      readonly = true,
      title = "id",
      visible = true,
      width = 0
   )
   private Long n;
   @UI(
      readonly = false,
      title = "Название",
      visible = true,
      width = 10
   )
   protected String name = "";
   @UI(
      readonly = false,
      title = "Поставщик",
      visible = true,
      width = 8
   )
   private Contragent contragent;
   @UI(
      readonly = false,
      title = "Цена поставщика",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cena = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Остаток",
      visible = true,
      width = 8,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      width = 20
   )
   private Tovar tovar;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Кол-во дней поставки",
      visible = true,
      width = 4
   )
   private Integer qtyDayDelivery = 0;
   @UI(
      readonly = false,
      title = "Название товара поставщика",
      visible = true,
      width = 30
   )
   private String tovarname = "";
   @UI(
      readonly = false,
      title = "Маржа",
      visible = true,
      width = 5
   )
   private BigDecimal margin = new BigDecimal("0.00");

   public BigDecimal getCena() {
      return this.cena;
   }

   public void setCena(BigDecimal cena) {
      this.cena = cena;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public String getTovarname() {
      return this.tovarname;
   }

   public void setTovarname(String tovarname) {
      this.tovarname = tovarname;
   }

   public static String getDescriptionClass() {
      return "Цены и остатки поставщика";
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public Integer getQtyDayDelivery() {
      return this.qtyDayDelivery;
   }

   public void setQtyDayDelivery(Integer qtyDayDelivery) {
      this.qtyDayDelivery = qtyDayDelivery;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public BigDecimal getMargin() {
      return this.margin;
   }

   public void setMargin(BigDecimal margin) {
      this.margin = margin;
   }
}
