package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.DolgnostDTO;

public class MessageDolgnostDTO extends MessageEntity<DolgnostDTO> {
   private static final long serialVersionUID = 4749006572505982042L;
   DolgnostDTO entity;

   public DolgnostDTO getEntity() {
      return this.entity;
   }

   public void setEntity(DolgnostDTO entity) {
      this.entity = entity;
   }
}
