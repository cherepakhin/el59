package ru.perm.v.el59.dto;

import java.io.Serializable;

public class AEntityDTO implements Serializable {
   protected Long n;
   protected String name;

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
