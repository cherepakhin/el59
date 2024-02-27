package ru.perm.v.el59.dto;

public class TypeOperationDTO extends AEntityDTO {
   private static final long serialVersionUID = -147807333392527667L;
   private Boolean worked;
   private Long typeDocShop_n;

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public Long getTypeDocShop_n() {
      return this.typeDocShop_n;
   }

   public void setTypeDocShop_n(Long typeDocShopN) {
      this.typeDocShop_n = typeDocShopN;
   }
}
