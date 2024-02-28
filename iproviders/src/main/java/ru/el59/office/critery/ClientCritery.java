package ru.el59.office.critery;

import java.io.Serializable;
import ru.el59.dao.CommonCritery;

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
