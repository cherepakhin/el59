package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.UserShopDTO;

public class MessageUserShopDTO extends MessageEntity<UserShopDTO> {
   private static final long serialVersionUID = 4749006572505982042L;
   UserShopDTO entity;

   public UserShopDTO getEntity() {
      return this.entity;
   }

   public void setEntity(UserShopDTO entity) {
      this.entity = entity;
   }
}
