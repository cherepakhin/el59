package ru.el59.office.critery;

import java.io.Serializable;
import ru.el59.dao.CommonCritery;

public abstract class ADocItemCritery extends CommonCritery implements Serializable {
   public Integer nnum;
   public String nameTovar = "";

   public ADocItemCritery() {
   }

   public ADocItemCritery(String name) {
      super(name);
   }
}
