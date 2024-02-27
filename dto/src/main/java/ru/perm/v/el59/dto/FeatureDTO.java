package ru.perm.v.el59.dto;

import java.io.Serializable;

public class FeatureDTO implements Serializable {
   private static final long serialVersionUID = -8617033474294309824L;
   private String name = "";
   private String val = "";
   private String grp = "";
   private Boolean prmry = false;

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

   public String getGrp() {
      return this.grp;
   }

   public void setGrp(String grp) {
      this.grp = grp;
   }

   public Boolean getPrmry() {
      return this.prmry;
   }

   public void setPrmry(Boolean prmry) {
      this.prmry = prmry;
   }
}
