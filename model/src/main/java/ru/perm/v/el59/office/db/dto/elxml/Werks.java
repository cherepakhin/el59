package ru.perm.v.el59.office.db.dto.elxml;

import java.io.Serializable;

public class Werks implements Serializable {
   private static final long serialVersionUID = -8501432436055016031L;
   private String werk_code;

   public String getWerk_code() {
      return this.werk_code;
   }

   public void setWerk_code(String werkCode) {
      this.werk_code = werkCode;
   }
}
