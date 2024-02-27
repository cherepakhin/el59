package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.PayCreditDTO;

public class MessagePayCreditDTO extends MessageEntity<PayCreditDTO> {
   private static final long serialVersionUID = 8847500783588866737L;
   PayCreditDTO entity;

   public PayCreditDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayCreditDTO entity) {
      this.entity = entity;
   }
}
