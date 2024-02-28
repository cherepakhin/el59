package ru.perm.v.el59.office.db;

import java.io.Serializable;

public class TovarPhoto implements Serializable, ITovar {
   private static final long serialVersionUID = 1155758415709756386L;
   protected Integer n;
   protected String filename;
   protected Tovar tovar;

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public Integer getN() {
      return this.n;
   }

   public void setN(Integer n) {
      this.n = n;
   }

   public String getFilename() {
      return this.filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }
}
