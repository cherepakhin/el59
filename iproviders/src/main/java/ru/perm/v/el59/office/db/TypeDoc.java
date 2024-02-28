package ru.perm.v.el59.office.db;

import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class TypeDoc extends AEntity implements Comparable<TypeDoc> {
   private static final long serialVersionUID = -7169728514381264348L;
   private Integer period;
   @UI(
      readonly = false,
      title = "Почта",
      visible = true,
      width = 20
   )
   private String email;
   @UI(
      readonly = false,
      title = "На остатках?",
      visible = true,
      width = 4
   )
   private Boolean isrest;

   public TypeDoc() {
      this.name = "-";
      this.period = 0;
   }

   public Boolean getIsrest() {
      return this.isrest;
   }

   public void setIsrest(Boolean isrest) {
      this.isrest = isrest;
   }

   public Integer getPeriod() {
      return this.period;
   }

   public void setPeriod(Integer period) {
      this.period = period;
   }

   public static String getDescriptionClass() {
      return "Тип документа";
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public int compareTo(TypeDoc o) {
      return o != null ? this.getName().compareTo(o.getName()) : -1;
   }

   public int hashCode() {
      int result = 31  + (this.n == null ? 0 : this.n.hashCode());
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
         TypeDoc other = (TypeDoc)obj;
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
