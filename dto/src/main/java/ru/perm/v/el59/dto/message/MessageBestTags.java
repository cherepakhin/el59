package ru.perm.v.el59.dto.message;


import ru.perm.v.el59.dto.BestTags;
import ru.perm.v.el59.dto.message.MessageEntity;

public class MessageBestTags extends MessageEntity<BestTags> {
   private static final long serialVersionUID = -2486565338656140068L;
   protected BestTags entity;

   public MessageBestTags() {
      this.className = BestTags.class.getSimpleName();
   }

   public BestTags getEntity() {
      return this.entity;
   }

   public void setEntity(BestTags entity) {
      this.entity = entity;
   }
}
