package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.util.Date;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class PeopleCount extends AUIBean implements Serializable {
   private static final long serialVersionUID = 4373986370457953886L;
   @UI(
      readonly = true,
      title = "Номер",
      visible = true,
      width = 0
   )
   private Long n;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate;
   @UI(
      readonly = false,
      title = "К-во",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Integer qty = 0;
   @UI(
      readonly = false,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop = new Shop();

   public static String getDescriptionClass() {
      return "Счетчик покупателей";
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Integer getQty() {
      return this.qty;
   }

   public void setQty(Integer qty) {
      this.qty = qty;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }
}
