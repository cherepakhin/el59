package ru.perm.v.el59.dto;

public class PayPBankDTO extends PaymentDTO {
   private static final long serialVersionUID = -8355708618324909395L;
   private String platdoc;

   public String getPlatdoc() {
      return this.platdoc;
   }

   public void setPlatdoc(String platdoc) {
      this.platdoc = platdoc;
   }
}
