package ru.perm.v.el59.office.db.web;

import java.math.BigDecimal;
import java.util.Date;

import ru.el59.office.db.TypeDoc;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.PriceType;
import ru.perm.v.el59.office.db.Shop;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class DocW extends AEntity {
   private static final long serialVersionUID = -7385192877080726957L;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop = new Shop();
   @UI(
      readonly = false,
      title = "Покупатель",
      visible = true,
      complex = true,
      width = 10
   )
   private Contragent contragent;
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
      width = 5,
      justify = Justify.RIGHT
   )
   private String numdoc = "";
   @UI(
      readonly = false,
      title = "Сайт",
      visible = true,
      width = 5
   )
   private String source = "";
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 16
   )
   private String comment = "";
   @UI(
      readonly = false,
      title = "Тип документа",
      visible = true,
      complex = true,
      width = 10
   )
   private TypeDoc typeDoc = new TypeDoc();
   @UI(
      readonly = false,
      title = "Прайс",
      visible = true,
      complex = true,
      width = 10
   )
   private PriceType priceType;
   @UI(
      readonly = false,
      title = "Адрес доставки",
      visible = true,
      width = 5
   )
   private String shippingAddress = "";
   @UI(
      readonly = false,
      title = "Удален?",
      visible = true,
      width = 5
   )
   private Boolean deleted = false;

   public static String getDescriptionClass() {
      return "Выписка инет-магазина";
   }

   public DocW() {
      this.ddate = new Date();
      this.summa = new BigDecimal("0.00");
      this.numdoc = "";
      this.comment = "";
   }

   public String getNumdoc() {
      return this.numdoc;
   }

   public void setNumdoc(String numdoc) {
      this.numdoc = numdoc;
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

   public String getSource() {
      return this.source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public String getShippingAddress() {
      return this.shippingAddress;
   }

   public void setShippingAddress(String shippingAddress) {
      this.shippingAddress = shippingAddress;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public TypeDoc getTypeDoc() {
      return this.typeDoc;
   }

   public void setTypeDoc(TypeDoc typeDoc) {
      this.typeDoc = typeDoc;
   }

   public PriceType getPriceType() {
      return this.priceType;
   }

   public void setPriceType(PriceType priceType) {
      this.priceType = priceType;
   }
}
