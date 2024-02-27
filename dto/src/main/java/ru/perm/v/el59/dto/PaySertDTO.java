package ru.perm.v.el59.dto;

public class PaySertDTO extends PaymentDTO {
   private static final long serialVersionUID = -4797662320808981896L;
   private TypeSertDTO typeSert;
   private Integer qty;
   public static String descriptionClass = "����������";

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public Integer getQty() {
      return this.qty;
   }

   public void setQty(Integer qty) {
      this.qty = qty;
   }

   public TypeSertDTO getTypeSert() {
      return this.typeSert;
   }

   public void setTypeSert(TypeSertDTO typeSert) {
      this.typeSert = typeSert;
   }
}
