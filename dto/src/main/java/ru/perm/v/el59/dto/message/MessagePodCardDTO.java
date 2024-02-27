package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.PodCardDTO;

public class MessagePodCardDTO extends MessageEntity<PodCardDTO> {
   private static final long serialVersionUID = -6833769637922540236L;
   PodCardDTO entity;

   public PodCardDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PodCardDTO entity) {
      this.entity = entity;
   }
}
