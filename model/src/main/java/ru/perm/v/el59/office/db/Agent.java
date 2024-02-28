package ru.perm.v.el59.office.db;

import java.io.Serializable;

public class Agent implements Serializable {
   private static final long serialVersionUID = -9106609084021696087L;
   private String n = "--";
   private String name = "";

   public String getN() {
      return this.n;
   }

   public void setN(String n) {
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
