package ru.perm.v.el59.dto;

public class PriceTypeDTO extends AEntityDTO {
   private static final long serialVersionUID = -5523780798611852523L;
   private Boolean isBase = true;
   private String shopCod = "00000";
   private Long mainPriceType;
   private Integer priority = 0;

   public Boolean getIsBase() {
      return this.isBase;
   }

   public void setIsBase(Boolean isBase) {
      this.isBase = isBase;
   }

   public String getShopCod() {
      return this.shopCod;
   }

   public void setShopCod(String shopCod) {
      this.shopCod = shopCod;
   }

   public Long getMainPriceType() {
      return this.mainPriceType;
   }

   public void setMainPriceType(Long mainPriceType) {
      this.mainPriceType = mainPriceType;
   }

   public Integer getPriority() {
      return this.priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }
}
