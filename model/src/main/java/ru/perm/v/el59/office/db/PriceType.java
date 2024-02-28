package ru.perm.v.el59.office.db;

import java.util.ArrayList;
import java.util.List;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.UI;

public class PriceType extends AEntity {
   private static final long serialVersionUID = 7835614868504404786L;
   @UI(
      readonly = true,
      title = "Основной(Да),Вспомогательный(Нет)",
      visible = true
   )
   private Boolean isBase = true;
   @UI(
      readonly = true,
      title = "Магазин",
      visible = true
   )
   private Shop shop;
   @UI(
      readonly = true,
      title = "Срок действия",
      visible = true
   )
   private Integer qtyDays = 0;
   @UI(
      readonly = true,
      title = "Владельцы",
      visible = true
   )
   private List<Manager> owners = new ArrayList();
   @UI(
      readonly = true,
      title = "Основной прайс",
      visible = true
   )
   private PriceType mainPriceType;
   @UI(
      readonly = true,
      title = "Приоритет",
      visible = true
   )
   private Integer priority = 0;

   public static String getDescriptionClass() {
      return "Тип прайса";
   }

   public Boolean getIsBase() {
      return this.isBase;
   }

   public void setIsBase(Boolean isBase) {
      this.isBase = isBase;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public Integer getQtyDays() {
      return this.qtyDays;
   }

   public void setQtyDays(Integer qtyDays) {
      this.qtyDays = qtyDays;
   }

   public List<Manager> getOwners() {
      return this.owners;
   }

   public void setOwners(List<Manager> owners) {
      this.owners = owners;
   }

   public PriceType getMainPriceType() {
      return this.mainPriceType;
   }

   public void setMainPriceType(PriceType mainPriceType) {
      this.mainPriceType = mainPriceType;
   }

   public Integer getPriority() {
      return this.priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }
}
