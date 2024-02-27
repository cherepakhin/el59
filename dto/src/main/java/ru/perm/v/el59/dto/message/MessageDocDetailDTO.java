package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.DocDetailDTO;

public class MessageDocDetailDTO extends MessageEntity<DocDetailDTO> {
   private static final long serialVersionUID = -9220533512849699298L;
   DocDetailDTO entity;

   public DocDetailDTO getEntity() {
      return this.entity;
   }

   public void setEntity(DocDetailDTO entity) {
      this.entity = entity;
   }
}
