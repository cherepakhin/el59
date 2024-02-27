package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.PriceDTO;
import ru.perm.v.el59.dto.message.MessageEntity;

public class MessagePriceDTO extends MessageEntity<PriceDTO> {
   private static final long serialVersionUID = 4749006572505982042L;
   PriceDTO entity;

   public PriceDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PriceDTO entity) {
      this.entity = entity;
   }
}
