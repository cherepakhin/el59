package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.BankActionDTO;

public class MessageBankActionDTO extends MessageEntity<BankActionDTO> {
   private static final long serialVersionUID = 4749006572505982042L;
   protected BankActionDTO entity;

   public BankActionDTO getEntity() {
      return this.entity;
   }

   public void setEntity(BankActionDTO entity) {
      this.entity = entity;
   }
}
