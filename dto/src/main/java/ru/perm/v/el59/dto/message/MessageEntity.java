package ru.perm.v.el59.dto.message;

import java.io.Serializable;
import ru.perm.v.el59.dto.message.TypeCommand;
public class MessageEntity<T> implements Serializable {
   private static final long serialVersionUID = 4749006572505982042L;
   protected TypeCommand typeCommand;
   protected String className;
   protected String shopCod;
   protected T entity;

   public MessageEntity() {
      this.typeCommand = TypeCommand.CREATE;
      this.className = "";
      this.shopCod = "";
   }

   public TypeCommand getTypeCommand() {
      return this.typeCommand;
   }

   public void setTypeCommand(ru.perm.v.el59.dto.message.TypeCommand typeCommand) {
      this.typeCommand = typeCommand;
   }

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String className) {
      this.className = className;
   }

   public String getShopCod() {
      return this.shopCod;
   }

   public void setShopCod(String shopCod) {
      this.shopCod = shopCod;
   }

   public T getEntity() {
      return this.entity;
   }

   public void setEntity(T entity) {
      this.entity = entity;
   }
}
