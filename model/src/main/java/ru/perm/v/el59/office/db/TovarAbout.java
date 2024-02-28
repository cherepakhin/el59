package ru.perm.v.el59.office.db;

import java.io.Serializable;

public class TovarAbout implements Serializable, ITovar {
   private static final long serialVersionUID = 6746036353923507844L;
   protected Integer n;
   protected Tovar tovar;
   protected String about = "";

   public TovarAbout() {
      this.about = "";
   }

   public String getAbout() {
      return this.about;
   }

   public void setAbout(String about) {
      this.about = about;
   }

   public Integer getN() {
      return this.n;
   }

   public void setN(Integer n) {
      this.n = n;
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }
}
