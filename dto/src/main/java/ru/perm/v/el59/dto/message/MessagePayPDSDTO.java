package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.PayPDSDTO;

public class MessagePayPDSDTO extends MessageEntity<PayPDSDTO> {
   private static final long serialVersionUID = -4066643343207701303L;
   PayPDSDTO entity;

   public PayPDSDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayPDSDTO entity) {
      this.entity = entity;
   }
}
