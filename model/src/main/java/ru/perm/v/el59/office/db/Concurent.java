package ru.perm.v.el59.office.db;

import java.io.Serializable;

public class Concurent implements Serializable {
   private static final long serialVersionUID = -6134673474469969804L;
   private Long n;
   private String name = "-";

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
