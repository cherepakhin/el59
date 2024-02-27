package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.SMSDTO;

public class MessageSMSDTO extends MessageEntity<SMSDTO> {
   private static final long serialVersionUID = 4749006572505982042L;
   SMSDTO entity;

   public SMSDTO getEntity() {
      return this.entity;
   }

   public void setEntity(SMSDTO entity) {
      this.entity = entity;
   }
}
