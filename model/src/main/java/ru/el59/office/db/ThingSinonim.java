package ru.el59.office.db;

import java.io.Serializable;

public class ThingSinonim implements Serializable {
   private static final long serialVersionUID = 6952276073558413418L;
   private Long n;
   private String name = "";
   private Thing thing;

   public Thing getThing() {
      return this.thing;
   }

   public void setThing(Thing thing) {
      this.thing = thing;
   }

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

   public String toString() {
      return this.name;
   }
}
