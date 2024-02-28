package ru.perm.v.el59.office.db;

import java.util.Collection;
import java.util.HashSet;

public class SetShop {
   private Long n;
   private String name;
   private Collection<Shop> shops = new HashSet();

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public void addShop(Shop _shop) {
      if (this.shops == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.shops.add(_shop);
      }
   }

   public Collection<Shop> getShops() {
      return this.shops;
   }

   public void setShops(Collection<Shop> shops) {
      this.shops = shops;
   }
}
