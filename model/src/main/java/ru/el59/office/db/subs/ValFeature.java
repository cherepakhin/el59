package ru.el59.office.db.subs;

import java.util.HashSet;
import java.util.Set;
import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class ValFeature extends AEntity {
   private static final long serialVersionUID = 2705901007703964502L;
   private MainFeature mainFeature;
   private Set<CorrectionVal> listCorrectionVal = new HashSet();
   @UI(
      readonly = false,
      title = "Для ценника",
      visible = true,
      width = 20
   )
   private String forTag = "";

   public static String getDescriptionClass() {
      return "Правильное ЗНАЧЕНИЕ для конкретной хара-ки";
   }

   public MainFeature getMainFeature() {
      return this.mainFeature;
   }

   public void setMainFeature(MainFeature mainFeature) {
      this.mainFeature = mainFeature;
   }

   public Set<CorrectionVal> getListCorrectionVal() {
      return this.listCorrectionVal;
   }

   public void setListCorrectionVal(Set<CorrectionVal> listCorrectionVal) {
      this.listCorrectionVal = listCorrectionVal;
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
         ValFeature other = (ValFeature)obj;
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

   public String getForTag() {
      return this.forTag;
   }

   public void setForTag(String forTag) {
      this.forTag = forTag;
   }
}
