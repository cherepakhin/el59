package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.ShopDTO;

public class MessageShopDTO extends MessageEntity<ShopDTO> {
   private static final long serialVersionUID = -3297116104840179182L;
   ShopDTO entity;

   public ShopDTO getEntity() {
      return this.entity;
   }

   public void setEntity(ShopDTO entity) {
      this.entity = entity;
   }
}
