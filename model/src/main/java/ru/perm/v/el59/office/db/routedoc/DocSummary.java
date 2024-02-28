package ru.perm.v.el59.office.db.routedoc;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.ui.UI;

public class DocSummary extends AEntity {
   private static final long serialVersionUID = -6807575881123023239L;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop;
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
      title = "Заказы",
      visible = true,
      complex = true,
      width = 10
   )
   private SumQty order = new SumQty();
   @UI(
      readonly = false,
      title = "Счета",
      visible = true,
      complex = true,
      width = 10
   )
   private SumQty invoice = new SumQty();
   @UI(
      readonly = false,
      title = "Приходы",
      visible = true,
      complex = true,
      width = 10
   )
   private SumQty receive = new SumQty();

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public SumQty getOrder() {
      return this.order;
   }

   public void setOrder(SumQty order) {
      this.order = order;
   }

   public SumQty getInvoice() {
      return this.invoice;
   }

   public void setInvoice(SumQty invoice) {
      this.invoice = invoice;
   }

   public SumQty getReceive() {
      return this.receive;
   }

   public void setReceive(SumQty receive) {
      this.receive = receive;
   }
}
