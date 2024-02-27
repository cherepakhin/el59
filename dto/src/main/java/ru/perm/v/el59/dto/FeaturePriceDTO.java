package ru.perm.v.el59.dto;

import java.io.Serializable;

public class FeaturePriceDTO implements Serializable {
   private static final long serialVersionUID = 2930154079247880202L;
   private String name = "";
   private String val = "";
   private String code = "";

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getVal() {
      return this.val;
   }

   public void setVal(String val) {
      this.val = val;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String code) {
      this.code = code;
   }
}
