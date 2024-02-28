package ru.el59.office.db.routedoc;

import java.util.Date;
import ru.el59.dao.AEntity;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class Procuratory extends AEntity {
   private static final long serialVersionUID = 3950854592945245878L;
   @UI(
      readonly = false,
      title = "Номер доверенности",
      visible = true,
      width = 5,
      justify = Justify.RIGHT
   )
   private Integer numdoc = 0;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 8
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Действительна до",
      visible = true,
      width = 8
   )
   private Date endDdate = new Date();
   @UI(
      readonly = false,
      title = "Поставщик",
      visible = true,
      width = 10
   )
   private Contragent supplier;
   @UI(
      readonly = false,
      title = "Кому выдана",
      visible = true,
      width = 10
   )
   private Contragent person;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Счет",
      visible = true,
      width = 10
   )
   private String numberOrder = "";
   @UI(
      readonly = false,
      title = "Автор",
      visible = true,
      width = 10
   )
   private Manager autor;

   public static String getDescriptionClass() {
      return "Доверенность";
   }

   public Integer getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(Integer numdoc) {
      this.numdoc = numdoc;
   }

   public Contragent getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Contragent supplier) {
      this.supplier = supplier;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String getNumberOrder() {
      return this.numberOrder;
   }

   public void setNumberOrder(String numberOrder) {
      this.numberOrder = numberOrder;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Contragent getPerson() {
      return this.person;
   }

   public void setPerson(Contragent person) {
      this.person = person;
   }

   public Date getEndDdate() {
      return this.endDdate;
   }

   public void setEndDdate(Date endDdate) {
      this.endDdate = endDdate;
   }

   public Manager getAutor() {
      return this.autor;
   }

   public void setAutor(Manager autor) {
      this.autor = autor;
   }
}
