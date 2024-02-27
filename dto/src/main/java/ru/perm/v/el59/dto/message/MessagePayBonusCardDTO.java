package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.PayBonusCardDTO;

public class MessagePayBonusCardDTO extends MessageEntity<PayBonusCardDTO> {
   private static final long serialVersionUID = -642893609688453800L;
   PayBonusCardDTO entity;

   public PayBonusCardDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayBonusCardDTO entity) {
      this.entity = entity;
   }
}
