package ru.perm.v.el59.office.db;

import java.io.Serializable;

public class StringSummaInOut implements Serializable {
   private static final long serialVersionUID = 2849338604844317362L;
   public String content;
   public SummaInOut summaInOut = new SummaInOut();

   public StringSummaInOut(String content, SummaInOut summaInOut) {
      this.content = content;
      this.summaInOut = summaInOut;
   }
}
