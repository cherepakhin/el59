package ru.el59.office.db;

import java.io.Serializable;

public class Firm implements Serializable {
   private static final long serialVersionUID = -5263163088430465965L;
   Integer n = 0;
   String name;

   public Integer getN() {
      return this.n;
   }

   public void setN(Integer n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }
}
