package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.PayPBankDTO;

public class MessagePayPBankDTO extends MessageEntity<PayPBankDTO> {
   private static final long serialVersionUID = -8862330018196937095L;
   PayPBankDTO entity;

   public PayPBankDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayPBankDTO entity) {
      this.entity = entity;
   }
}
