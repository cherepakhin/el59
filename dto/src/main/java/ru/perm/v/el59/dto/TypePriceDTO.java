package ru.perm.v.el59.dto;

import java.io.Serializable;

public class TypePriceDTO extends AEntityDTO implements Serializable {
   private static final long serialVersionUID = -2442715849239224408L;
   protected static String descriptionClass = "��� ������";
   private String best;

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public String getBest() {
      return this.best;
   }

   public void setBest(String best) {
      this.best = best;
   }
}
