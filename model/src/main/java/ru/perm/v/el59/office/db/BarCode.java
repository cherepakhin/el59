package ru.perm.v.el59.office.db;

import java.io.Serializable;

public class BarCode implements Serializable {
   private static final long serialVersionUID = 6096849592904176092L;
   private TovarInfo tovarInfo;
   private String code = "";
   private String format = "";
   private Boolean isBase = false;

   public TovarInfo getTovarInfo() {
      return this.tovarInfo;
   }

   public void setTovarInfo(TovarInfo tovarInfo) {
      this.tovarInfo = tovarInfo;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getFormat() {
      return this.format;
   }

   public void setFormat(String format) {
      this.format = format;
   }

   public Boolean getIsBase() {
      return this.isBase;
   }

   public void setIsBase(Boolean isBase) {
      this.isBase = isBase;
   }
}
