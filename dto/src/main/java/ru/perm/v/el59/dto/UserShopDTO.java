package ru.perm.v.el59.dto;

import java.io.Serializable;

public class UserShopDTO extends AEntityDTO implements Serializable {
   private static final long serialVersionUID = 1455906778859238605L;
   private String dolgnost;
   private Boolean worked;
   private String namebest;
   private String password;

   public Long getN() {
      return this.n;
   }

   public String getDolgnost() {
      return this.dolgnost;
   }

   public void setDolgnost(String dolgnost) {
      this.dolgnost = dolgnost;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public String getNamebest() {
      return this.namebest;
   }

   public void setNamebest(String namebest) {
      this.namebest = namebest;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
