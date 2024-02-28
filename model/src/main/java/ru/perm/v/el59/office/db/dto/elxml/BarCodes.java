package ru.perm.v.el59.office.db.dto.elxml;

import java.io.Serializable;

public class BarCodes implements Serializable {
   private static final long serialVersionUID = 2255337302819459636L;
   private String GB_Code;
   private String BC_Format;
   private String Is_Base;

   public String getGB_Code() {
      return this.GB_Code;
   }

   public void setGB_Code(String gBCode) {
      this.GB_Code = gBCode;
   }

   public String getBC_Format() {
      return this.BC_Format;
   }

   public void setBC_Format(String bCFormat) {
      this.BC_Format = bCFormat;
   }

   public String getIs_Base() {
      return this.Is_Base;
   }

   public void setIs_Base(String isBase) {
      this.Is_Base = isBase;
   }
}
