package ru.el59.office.db.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Shop;
import ru.el59.office.db.Tovar;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class RestWeb extends AUIBean implements Serializable {
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
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal qty = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Цена поставщика",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cenaSupplier = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Цена",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal cenaOut = new BigDecimal("0.00");
   @UI(
      readonly = false,
      title = "Поставщик",
      visible = true,
      width = 10
   )
   private Contragent contragent;
   @UI(
      readonly = false,
      title = "Кол-во дней поставки",
      visible = true,
      width = 3
   )
   private Integer qtyDayDelivery = 0;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();

   public static String getDescriptionClass() {
      return "Остатки для сайта";
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

   public BigDecimal getQty() {
      return this.qty;
   }

   public void setQty(BigDecimal qty) {
      this.qty = qty;
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (this.shop == null ? 0 : this.shop.hashCode());
      result = 31 * result + (this.tovar == null ? 0 : this.tovar.hashCode());
      result = 31 * result + (this.contragent == null ? 0 : this.contragent.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         RestWeb other = (RestWeb)obj;
         if (this.shop == null) {
            if (other.shop != null) {
               return false;
            }
         } else if (!this.shop.equals(other.shop)) {
            return false;
         }

         if (this.tovar == null) {
            if (other.tovar != null) {
               return false;
            }
         } else if (!this.tovar.equals(other.tovar)) {
            return false;
         }

         if (this.contragent == null) {
            if (other.contragent != null) {
               return false;
            }
         } else if (!this.contragent.equals(other.contragent)) {
            return false;
         }

         return true;
      }
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

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getCenaOut() {
      return this.cenaOut;
   }

   public void setCenaOut(BigDecimal cenaOut) {
      this.cenaOut = cenaOut;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public BigDecimal getCenaSupplier() {
      return this.cenaSupplier;
   }

   public void setCenaSupplier(BigDecimal cenaSupplier) {
      this.cenaSupplier = cenaSupplier;
   }
}
