package ru.perm.v.el59.dto;

import java.io.Serializable;

public class ContragentDTO implements Serializable {
   private static final long serialVersionUID = 2844610258089818060L;
   private Long n;
   private Long nn;
   private String name = "";
   private String address = "";
   private String info = "";
   private String phone = "";
   private String email = "";
   private String groupContragent;

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

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getInfo() {
      return this.info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public String getGroupContragent() {
      return this.groupContragent;
   }

   public void setGroupContragent(String groupContragent) {
      this.groupContragent = groupContragent;
   }

   public Long getNn() {
      return this.nn;
   }

   public void setNn(Long nn) {
      this.nn = nn;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
}
