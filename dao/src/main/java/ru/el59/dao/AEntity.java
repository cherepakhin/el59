package ru.el59.dao;

import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

import java.io.Serializable;

public abstract class AEntity extends AUIBean implements Serializable, IEntitySimple {
   private static final long serialVersionUID = 6241496481473675071L;
   @UI(
      readonly = false,
      title = "Номер",
      visible = true,
      width = 6,
      justify = Justify.RIGHT
   )
   protected Long n = -1L;
   @UI(
      readonly = false,
      title = "Название",
      visible = true,
      width = 20
   )
   protected String name = "";

   public AEntity() {
      this.name = "";
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
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
         AEntity other = (AEntity)obj;
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

   public String getContent() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
