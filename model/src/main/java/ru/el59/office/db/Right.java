package ru.el59.office.db;

import java.io.Serializable;

public class Right implements Serializable {
   private static final long serialVersionUID = -6164644779579115142L;
   private Long n;
   private String name = "";
   private Boolean read = false;
   private Boolean write = false;

   public Right() {
      this.read = false;
      this.write = false;
   }

   public Boolean getRead() {
      return this.read;
   }

   public void setRead(Boolean read) {
      this.read = read;
   }

   public Boolean getWrite() {
      return this.write;
   }

   public void setWrite(Boolean write) {
      this.write = write;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
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
         Right other = (Right)obj;
         if (this.n == null) {
            if (other.n != null) {
               return false;
            }
         } else if (!this.n.equals(other.n)) {
            return false;
         }

         return true;
      }
   }
}
