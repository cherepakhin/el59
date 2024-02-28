package ru.perm.v.el59.office.critery;

import java.io.Serializable;

public class TypedocCritery implements Serializable {
   private static final long serialVersionUID = 3734119808222028537L;
   private Long n;
   private String name;

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
