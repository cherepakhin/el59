package ru.perm.v.el59.office.db.subs;

import ru.el59.dao.AEntity;

public class CorrectionVal extends AEntity {
   private static final long serialVersionUID = 4631143418951787275L;
   private ValFeature valFeature;

   public static String getDescriptionClass() {
      return "Ошибочное значение для значения хар-ки";
   }

   public ValFeature getValFeature() {
      return this.valFeature;
   }

   public void setValFeature(ValFeature valFeature) {
      this.valFeature = valFeature;
   }

   public int hashCode() {
      int result = 31 + (this.valFeature == null ? 0 : this.valFeature.hashCode()) + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CorrectionVal other = (CorrectionVal)obj;
         if (this.valFeature == null) {
            if (other.valFeature != null) {
               return false;
            }
         } else if (!this.valFeature.equals(other.valFeature)) {
            return false;
         }

         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else if (!this.name.equals(other.name)) {
            return false;
         }

         return true;
      }
   }
}
