package ru.perm.v.el59.office.critery;

import ru.perm.v.el59.office.dao.CommonCritery;

import java.io.Serializable;

public abstract class ADocItemCritery extends CommonCritery implements Serializable {
   public Integer nnum;
   public String nameTovar = "";

   public ADocItemCritery() {
   }

   public ADocItemCritery(String name) {
      super(name);
   }
}
