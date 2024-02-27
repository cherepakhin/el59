package ru.perm.v.el59.dto;

import java.math.BigDecimal;

public class TypeSertDTO extends AEntityDTO {
   private static final long serialVersionUID = 5289543667395047799L;
   protected static String descriptionClass = "��� ���������� �����";
   private BigDecimal cost = new BigDecimal("0.00");
   private Boolean worked = true;

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public BigDecimal getCost() {
      return this.cost;
   }

   public void setCost(BigDecimal cost) {
      this.cost = cost;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }
}
