package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.DocTitleDTO;

public class MessageDocTitleDTO extends MessageEntity<DocTitleDTO> {
   private static final long serialVersionUID = -9220533512849699298L;
   DocTitleDTO entity;

   public DocTitleDTO getEntity() {
      return this.entity;
   }

   public void setEntity(DocTitleDTO entity) {
      this.entity = entity;
   }
}
