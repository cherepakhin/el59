package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.BonusCardDTO;

public class MessageBonusCardDTO extends MessageEntity<BonusCardDTO> {
   private static final long serialVersionUID = 9186079722544880110L;
   protected BonusCardDTO entity;

   public BonusCardDTO getEntity() {
      return this.entity;
   }

   public void setEntity(BonusCardDTO entity) {
      this.entity = entity;
   }
}
