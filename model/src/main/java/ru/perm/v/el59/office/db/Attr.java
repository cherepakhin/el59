package ru.perm.v.el59.office.db;

import java.io.Serializable;

public class Attr implements Serializable {
   private static final long serialVersionUID = 3521040514069749972L;
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
