package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.PayCashDTO;

public class MessagePayCashDTO extends MessageEntity<PayCashDTO> {
   private static final long serialVersionUID = -9220533512849699298L;
   PayCashDTO entity;

   public PayCashDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayCashDTO entity) {
      this.entity = entity;
   }
}
