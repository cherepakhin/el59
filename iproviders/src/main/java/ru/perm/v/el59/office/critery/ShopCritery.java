package ru.perm.v.el59.office.critery;

import ru.perm.v.el59.office.dao.CommonCritery;

import java.io.Serializable;

public class ShopCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 3734119808222028537L;
   private String cod;
   private Boolean worked;

   public ShopCritery() {
   }

   public ShopCritery(String name) {
      super(name);
   }

   public String getCod() {
      return this.cod;
   }

   public void setCod(String cod) {
      this.cod = cod;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }
}
