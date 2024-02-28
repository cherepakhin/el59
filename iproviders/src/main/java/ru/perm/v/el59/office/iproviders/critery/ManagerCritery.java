package ru.perm.v.el59.office.iproviders.critery;

import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.io.Serializable;

public class ManagerCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = -4863029579498662318L;
   private Boolean worked;

   public ManagerCritery() {
   }

   public ManagerCritery(String name) {
      super(name);
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }
}
