package ru.perm.v.el59.dto;

import java.io.Serializable;

public class TypeDocDTO extends AEntityDTO implements Serializable {
   private static final long serialVersionUID = -8802857403192632636L;
   private Integer znak;
   protected static String descriptionClass = "��� ���������";

   public Integer getZnak() {
      return this.znak;
   }

   public void setZnak(Integer znak) {
      this.znak = znak;
   }

   public String getDescriptionClass() {
      return descriptionClass;
   }
}
