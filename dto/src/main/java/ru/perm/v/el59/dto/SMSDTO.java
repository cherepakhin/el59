package ru.perm.v.el59.dto;

import java.io.Serializable;

public class SMSDTO implements Serializable {
   private static final long serialVersionUID = 281114776161474189L;
   private Long n;
   private String name;
   private String phone;
   private String message = "";

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
