package ru.perm.v.el59.office.db.routedoc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.GroupContragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.TypeFile;
import ru.perm.v.el59.ui.Justify;
import ru.perm.v.el59.ui.UI;

public class PlanDownload extends AEntity {
   private static final long serialVersionUID = -822856937137555888L;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true,
      complex = true,
      width = 10
   )
   private Shop shop;
   @UI(
      readonly = true,
      title = "Поставщик",
      visible = true,
      complex = true,
      width = 10
   )
   private GroupContragent groupContragent;
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
      title = "Запланировано из плана",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   public BigDecimal planUsed = new BigDecimal("0.00");
   private HashMap<TypeFile, BigDecimal> mapTypeFileSum = new HashMap();

   public static String getDescriptionClass() {
      return "Планы погрузок";
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public BigDecimal getSumma() {
      return this.summa;
   }

   public void setSumma(BigDecimal summa) {
      this.summa = summa;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public GroupContragent getGroupContragent() {
      return this.groupContragent;
   }

   public void setGroupContragent(GroupContragent groupContragent) {
      this.groupContragent = groupContragent;
   }

   public HashMap<TypeFile, BigDecimal> getMapTypeFileSum() {
      return this.mapTypeFileSum;
   }

   public void setMapTypeFileSum(HashMap<TypeFile, BigDecimal> mapTypeFileSum) {
      this.mapTypeFileSum = mapTypeFileSum;
   }

   public BigDecimal getPlanUsed() {
      return this.planUsed;
   }

   public void setPlanUsed(BigDecimal planUsed) {
      this.planUsed = planUsed;
   }
}
