package ru.perm.v.el59.office.critery;


import ru.perm.v.el59.office.dao.CommonCritery;

import java.io.Serializable;

public class AnalogCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 7098124963301378152L;
   public String tovarDescription = "";

   public AnalogCritery() {
   }

   public AnalogCritery(String name) {
      super(name);
   }

   public AnalogCritery(String analogName, String tovarDescription) {
      super(analogName);
      this.tovarDescription = tovarDescription;
   }
}
