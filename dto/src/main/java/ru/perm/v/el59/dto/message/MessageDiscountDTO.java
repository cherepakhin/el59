package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.DiscountDTO;

public class MessageDiscountDTO extends MessageEntity<DiscountDTO> {
   private static final long serialVersionUID = -8840183967085884338L;
   DiscountDTO entity;

   public DiscountDTO getEntity() {
      return this.entity;
   }

   public void setEntity(DiscountDTO entity) {
      this.entity = entity;
   }
}
