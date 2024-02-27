package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.TovarDTO;

public class MessageTovarDTO extends MessageEntity<TovarDTO> {
   private static final long serialVersionUID = 4749006572505982042L;
   TovarDTO entity;

   public TovarDTO getEntity() {
      return this.entity;
   }

   public void setEntity(TovarDTO entity) {
      this.entity = entity;
   }
}
