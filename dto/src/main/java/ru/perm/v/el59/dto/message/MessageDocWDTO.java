package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.DocWDTO;

public class MessageDocWDTO extends MessageEntity<DocWDTO> {
   private static final long serialVersionUID = -9220533512849699298L;
   DocWDTO entity;

   public DocWDTO getEntity() {
      return this.entity;
   }

   public void setEntity(DocWDTO entity) {
      this.entity = entity;
   }
}
