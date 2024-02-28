package ru.el59.office.shopmodel;

import java.math.BigDecimal;
import ru.el59.dao.AEntity;
import ru.el59.office.db.ITovar;
import ru.el59.office.db.Shop;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.TypeStock;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class DocDetail extends AEntity implements ITovar {
   private static final long serialVersionUID = 7388794386157897084L;
   @UI(
      readonly = false,
      title = "Документ",
      visible = true,
      width = 10
   )
   private DocTitle docTitle;
   @UI(
      readonly = false,
      title = "ID в магазине",
      visible = true,
      width = 6
   )
   private Long nn = 0L;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = false,
      title = "Товар",
      visible = true,
      width = 15
   )
   private Tovar tovar;
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Вх.сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summain = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Вых.сумма",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal summaout = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Склад",
      visible = true,
      width = 10
   )
   private TypeStock typeStock;
   @UI(
      readonly = false,
      title = "Скидка",
      visible = true,
      width = 10
   )
   private Discount discount;
   @UI(
      readonly = false,
      title = "К1",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal k1 = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "К2",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal k2 = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Цена из прайса",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal price = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Удален?",
      visible = true,
      width = 4
   )
   private Boolean deleted = false;
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment = "";

   public static String getDescriptionClass() {
      return "Содержание документа";
   }

   public DocDetail() {
      this.summain = new BigDecimal("0.00");
      this.summaout = new BigDecimal("0.00");
      this.qty = new BigDecimal("0.00");
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getSummain() {
      return this.summain;
   }

   public void setSummain(BigDecimal summain) {
      this.summain = summain;
   }

   public BigDecimal getSummaout() {
      return this.summaout;
   }

   public void setSummaout(BigDecimal summaout) {
      this.summaout = summaout;
   }

   public Discount getDiscount() {
      return this.discount;
   }

   public void setDiscount(Discount discount) {
      this.discount = discount;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public DocTitle getDocTitle() {
      return this.docTitle;
   }

   public void setDocTitle(DocTitle docTitle) {
      this.docTitle = docTitle;
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

   public BigDecimal getK1() {
      return this.k1;
   }

   public void setK1(BigDecimal k1) {
      this.k1 = k1;
   }

   public BigDecimal getK2() {
      return this.k2;
   }

   public void setK2(BigDecimal k2) {
      this.k2 = k2;
   }

   public BigDecimal getPrice() {
      return this.price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public TypeStock getTypeStock() {
      return this.typeStock;
   }

   public void setTypeStock(TypeStock typeStock) {
      this.typeStock = typeStock;
   }
}
