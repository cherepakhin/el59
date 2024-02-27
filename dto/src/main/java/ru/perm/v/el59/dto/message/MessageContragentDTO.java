package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.ContragentDTO;

public class MessageContragentDTO extends MessageEntity<ContragentDTO> {
   private static final long serialVersionUID = 9186079722544880110L;
   protected ContragentDTO entity;

   public ContragentDTO getEntity() {
      return this.entity;
   }

   public void setEntity(ContragentDTO entity) {
      this.entity = entity;
   }
}
