package ru.perm.v.el59.office.db.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Shop;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class RouteDoc extends AEntity {
   private static final long serialVersionUID = -458350143865824972L;
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
   private Shop shop;
   @UI(
      readonly = false,
      title = "Автор",
      visible = true,
      complex = true,
      width = 10
   )
   private Manager manager;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Date ddate = new Date();
   private List<DocWItem> listDocWItem = new ArrayList();

   public static String getDescriptionClass() {
      return "Маршрутный лист";
   }

   public Contragent getContragent() {
      return this.contragent;
   }

   public void setContragent(Contragent contragent) {
      this.contragent = contragent;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public List<DocWItem> getListDocWItem() {
      return this.listDocWItem;
   }

   public void setListDocWItem(List<DocWItem> listDocWItem) {
      this.listDocWItem = listDocWItem;
   }
}
