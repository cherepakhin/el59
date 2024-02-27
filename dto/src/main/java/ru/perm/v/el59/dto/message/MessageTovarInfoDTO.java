package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.TovarInfoDTO;

public class MessageTovarInfoDTO extends MessageEntity<TovarInfoDTO> {
   private static final long serialVersionUID = -7221805040731294953L;
   TovarInfoDTO entity;

   public TovarInfoDTO getEntity() {
      return this.entity;
   }

   public void setEntity(TovarInfoDTO entity) {
      this.entity = entity;
   }
}
