package ru.perm.v.el59.office.critery;

import ru.perm.v.el59.office.dao.CommonCritery;

import java.io.Serializable;

public class UserShopCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 6818952918814836695L;
   private String shopname;
   private Boolean worked;

   public UserShopCritery() {
   }

   public UserShopCritery(String name) {
      super(name);
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public String getShopname() {
      return this.shopname;
   }

   public void setShopname(String shopname) {
      this.shopname = shopname;
   }
}
