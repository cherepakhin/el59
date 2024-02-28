package ru.perm.v.el59.office.shopmodel;

import java.util.Collection;
import java.util.HashSet;
import ru.el59.dao.AEntity;
import ru.perm.v.el59.office.db.CreditBank;
import ru.el59.ui.UI;

public class BankAction extends AEntity {
   private static final long serialVersionUID = -5613064403045102973L;
   @UI(
      readonly = false,
      title = "Банк",
      visible = true,
      width = 10
   )
   private CreditBank bank;
   private Collection<TypePrice> typeprices = new HashSet();
   private Collection<Discount> discounts = new HashSet();
   @UI(
      readonly = false,
      title = "Работает?",
      visible = true,
      width = 4
   )
   private Boolean worked = true;

   public static String getDescriptionClass() {
      return "Продукт банка";
   }

   public CreditBank getBank() {
      return this.bank;
   }

   public void setBank(CreditBank bank) {
      this.bank = bank;
   }

   public void addTypePrice(TypePrice typeprice) {
      if (this.typeprices == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.typeprices.add(typeprice);
      }
   }

   public void removeTypePrice(TypePrice typeprice) {
      if (this.typeprices == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.typeprices.remove(typeprice);
      }
   }

   public Collection<TypePrice> getTypeprices() {
      return this.typeprices;
   }

   public void setTypeprices(Collection<TypePrice> typeprices) {
      this.typeprices = typeprices;
   }

   public Collection<Discount> getDiscounts() {
      return this.discounts;
   }

   public void setDiscounts(Collection<Discount> discounts) {
      this.discounts = discounts;
   }

   public void addDiscount(Discount discount) {
      if (this.discounts == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.discounts.add(discount);
      }
   }

   public void removeDiscount(Discount discount) {
      if (this.discounts == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.discounts.remove(discount);
      }
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }
}
