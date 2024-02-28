package ru.perm.v.el59.office.db.subs;

import ru.perm.v.el59.dao.AEntity;

public class CorrectionName extends AEntity {
   private static final long serialVersionUID = 1302409012467507703L;
   private MainFeature mainFeature;

   public MainFeature getMainFeature() {
      return this.mainFeature;
   }

   public void setMainFeature(MainFeature mainFeature) {
      this.mainFeature = mainFeature;
   }

   public static String getDescriptionClass() {
      return "Имя характеристики";
   }

   public int hashCode() {
      int result = 31 + (this.mainFeature == null ? 0 : this.mainFeature.hashCode()) + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CorrectionName other = (CorrectionName)obj;
         if (this.mainFeature == null) {
            if (other.mainFeature != null) {
               return false;
            }
         } else if (!this.mainFeature.equals(other.mainFeature)) {
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
