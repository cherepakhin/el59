package ru.el59.office.exception;

public class MessageException extends Exception {
   private static final long serialVersionUID = -1106056040534293649L;
   private String messageString;

   public MessageException(String messageString) {
      this.messageString = messageString;
   }

   public String toString() {
      return this.messageString;
   }
}
