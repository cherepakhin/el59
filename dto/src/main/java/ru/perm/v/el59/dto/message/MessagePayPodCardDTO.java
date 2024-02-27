package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.PayPodCardDTO;

public class MessagePayPodCardDTO extends MessageEntity<PayPodCardDTO> {
   private static final long serialVersionUID = -6542752716735711214L;
   PayPodCardDTO entity;

   public PayPodCardDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayPodCardDTO entity) {
      this.entity = entity;
   }
}
