package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.DocWItemDTO;

public class MessageDocWItemDTO extends MessageEntity<DocWItemDTO> {
   private static final long serialVersionUID = -1396558236361773764L;
   DocWItemDTO entity;

   public DocWItemDTO getEntity() {
      return this.entity;
   }

   public void setEntity(DocWItemDTO entity) {
      this.entity = entity;
   }
}
