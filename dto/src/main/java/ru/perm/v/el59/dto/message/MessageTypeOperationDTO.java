package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.TypeOperationDTO;

public class MessageTypeOperationDTO extends MessageEntity<TypeOperationDTO> {
   private static final long serialVersionUID = 4749006572505982042L;
   TypeOperationDTO entity;

   public TypeOperationDTO getEntity() {
      return this.entity;
   }

   public void setEntity(TypeOperationDTO entity) {
      this.entity = entity;
   }
}
