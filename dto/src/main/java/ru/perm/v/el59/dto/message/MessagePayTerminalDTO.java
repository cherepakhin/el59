package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.PayTerminalDTO;

public class MessagePayTerminalDTO extends MessageEntity<PayTerminalDTO> {
   private static final long serialVersionUID = -2411663493095685632L;
   PayTerminalDTO entity;

   public PayTerminalDTO getEntity() {
      return this.entity;
   }

   public void setEntity(PayTerminalDTO entity) {
      this.entity = entity;
   }
}
