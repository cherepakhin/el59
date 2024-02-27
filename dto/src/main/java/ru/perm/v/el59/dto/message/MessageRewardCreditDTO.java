package ru.perm.v.el59.dto.message;

import ru.perm.v.el59.dto.RewardCreditDTO;

public class MessageRewardCreditDTO extends MessageEntity<RewardCreditDTO> {
   private static final long serialVersionUID = 9186079722544880110L;
   RewardCreditDTO entity;

   public RewardCreditDTO getEntity() {
      return this.entity;
   }

   public void setEntity(RewardCreditDTO entity) {
      this.entity = entity;
   }
}
