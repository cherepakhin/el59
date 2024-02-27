package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.PayPodCardEldoradoDTO;

public class MessagePayPodCardEldoradoDTO extends MessageEntity<PayPodCardEldoradoDTO> {
   private static final long serialVersionUID = -3055035154990013719L;
   PayPodCardEldoradoDTO entity;

   public PayPodCardEldoradoDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayPodCardEldoradoDTO entity) {
      this.entity = entity;
   }
}
