package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;

public class Token implements Serializable {
   private static final long serialVersionUID = 2316143710175780259L;
   public String word = "";
   public Long weight = 0L;

   public Token(String w, Long c) {
      this.word = w;
      this.weight = c;
   }
}
