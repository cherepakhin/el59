package ru.el59.office.db;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import ru.el59.dao.AEntity;
import ru.el59.office.shopmodel.ShopRight;
import ru.el59.ui.UI;

public class Dolgnost extends AEntity {
   private static final long serialVersionUID = -560929258515874765L;
   @UI(
      readonly = false,
      title = "Тариф",
      visible = true,
      width = 10
   )
   private BigDecimal tarif = new BigDecimal("0.00");
   @UI(
      readonly = true,
      title = "Права",
      visible = true,
      width = 10
   )
   private Collection<ShopRight> listShopRight = new HashSet();

   public static String getDescriptionClass() {
      return "Должность";
   }

   public BigDecimal getTarif() {
      return this.tarif;
   }

   public void setTarif(BigDecimal tarif) {
      this.tarif = tarif;
   }

   public void addRight(ShopRight right) {
      if (this.listShopRight == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.listShopRight.add(right);
      }
   }

   public void removeShopRight(ShopRight right) {
      if (this.listShopRight == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.listShopRight.remove(right);
      }
   }

   public Collection<ShopRight> getListShopRight() {
      return this.listShopRight;
   }

   public void setListShopRight(Collection<ShopRight> listShopRight) {
      this.listShopRight = listShopRight;
   }
}
