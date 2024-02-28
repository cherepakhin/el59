package ru.perm.v.el59.office.shopmodel;

import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class TypePrice extends AEntity {
   private static final long serialVersionUID = -2442715849239224408L;
   @UI(
      readonly = false,
      title = "Прайс БЭСТ",
      visible = true,
      width = 10
   )
   private String best = "00000";

   public static String getDescriptionClass() {
      return "Тип прайса";
   }

   public String getBest() {
      return this.best;
   }

   public void setBest(String best) {
      this.best = best;
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (this.n == null ? 0 : this.n.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         TypePrice other = (TypePrice)obj;
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
