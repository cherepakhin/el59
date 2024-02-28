package ru.perm.v.el59.office.db.service;

import java.io.Serializable;
import ru.perm.v.el59.ui.AUIBean;

public abstract class ATDoc extends AUIBean implements ITDoc, Serializable {
   private static final long serialVersionUID = -4163829288390981457L;
   protected Long n;
   protected TDoc tdoc;

   public ATDoc(TDoc tdoc2) {
      this();
      this.tdoc = tdoc2;
      this.n = tdoc2.getN();
   }

   public ATDoc() {
      this.tdoc = new TDoc();
      this.tdoc = new TDoc();
   }

   public TDoc getTdoc() {
      return this.tdoc;
   }

   public void setTdoc(TDoc tdoc) {
      this.tdoc = tdoc;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String toString() {
      String ret = "";
      if (this.tdoc != null) {
         ret = ret + this.tdoc.toString();
      }

      return ret;
   }

   public int hashCode() {
      int result = 31 + (this.n == null ? 0 : this.n.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ATDoc other = (ATDoc)obj;
         if (this.n == null) {
            if (other.getN() != null) {
               return false;
            }
         } else if (!this.n.equals(other.getN())) {
            return false;
         }

         return true;
      }
   }

   public abstract String getContent();
}
