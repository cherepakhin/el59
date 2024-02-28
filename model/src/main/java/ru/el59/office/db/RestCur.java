package ru.el59.office.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class RestCur extends AUIBean implements Serializable, ITovar {
   private static final long serialVersionUID = 9063643879106537306L;
   private Long n;
   @UI(
      readonly = true,
      title = "Товар",
      visible = true,
      width = 10
   )
   private Tovar tovar = new Tovar();
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      width = 10
   )
   private Shop shop = new Shop();
   @UI(
      readonly = true,
      title = "Склад",
      visible = true,
      width = 10
   )
   private TypeStock typeStock = new TypeStock();
   @UI(
      readonly = true,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Себ-ть",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cenain = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Категория",
      visible = true,
      width = 5
   )
   private Integer category = 0;
   @UI(
      readonly = true,
      title = "Свободно",
      visible = true,
      width = 10
   )
   private BigDecimal freeqty = new BigDecimal("0.00");

   public BigDecimal getFreeqty() {
      return this.freeqty;
   }

   public void setFreeqty(BigDecimal freeqty) {
      this.freeqty = freeqty;
   }

   public Integer getCategory() {
      if (this.category == null) {
         this.category = 0;
      }

      return this.category;
   }

   public void setCategory(Integer category) {
      this.category = category;
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public TypeStock getTypeStock() {
      return this.typeStock;
   }

   public void setTypeStock(TypeStock typeStock) {
      this.typeStock = typeStock;
   }

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public BigDecimal getCenain() {
      return this.cenain;
   }

   public void setCenain(BigDecimal cenain) {
      this.cenain = cenain;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getKey() {
      return this.typeStock.getN().toString() + "_" + this.tovar.getNnum().toString();
   }

   public Rest asRest() {
      Rest rest = new Rest();
      rest.setCenain(this.cenain);
      rest.setDdate(new Date());
      rest.setDdatecena(new Date());
      rest.setQty(this.qty);
      rest.setShop(this.shop);
      rest.setTovar(this.tovar);
      rest.setTypeStock(this.typeStock);
      rest.setQty(this.freeqty);
      rest.setN(this.n);
      return rest;
   }

   public static String getDescriptionClass() {
      return "Текущие остатки";
   }
}
