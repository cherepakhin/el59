package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.PaySertDTO;

public class MessagePaySertDTO extends MessageEntity<PaySertDTO> {
   private static final long serialVersionUID = -4378502261675288845L;
   PaySertDTO entity;

   public PaySertDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PaySertDTO entity) {
      this.entity = entity;
   }
}
