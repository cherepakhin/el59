package ru.perm.v.el59.dto;

import java.io.Serializable;

public class TypeStockDTO extends AEntityDTO implements Serializable {
   private static final long serialVersionUID = 970643425770206937L;
   private Boolean worked;
   protected static String descriptionClass = "��� ������";

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public int hashCode() {
      int result = 31 + (this.n == null ? 0 : this.n.hashCode());
      return result;
   }
}
