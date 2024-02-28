package ru.perm.v.el59.office.critery;

import ru.perm.v.el59.dto.dao.CommonCritery;

import java.io.Serializable;

public class ClientCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 8628042499876922691L;
   public String address = "";
   public String phone = "";

   public ClientCritery() {
   }

   public ClientCritery(String name) {
      super(name);
   }
}
