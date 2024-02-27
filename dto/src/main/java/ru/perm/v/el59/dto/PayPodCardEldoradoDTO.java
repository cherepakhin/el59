package ru.perm.v.el59.dto;

public class PayPodCardEldoradoDTO extends PaymentDTO {
   private static final long serialVersionUID = -6524726692476890963L;
   private String number = "-";
   private String stroke = "-";
   private Integer pin = 0;

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getStroke() {
      return this.stroke;
   }

   public void setStroke(String stroke) {
      this.stroke = stroke;
   }

   public Integer getPin() {
      return this.pin;
   }

   public void setPin(Integer pin) {
      this.pin = pin;
   }
}
