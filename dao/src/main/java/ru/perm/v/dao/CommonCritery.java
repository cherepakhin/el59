package ru.perm.v.dao;

import java.io.Serializable;

public class CommonCritery implements Serializable {
   private static final long serialVersionUID = 3734119808222028537L;
   private String name = "";

   public CommonCritery() {
   }

   public CommonCritery(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
