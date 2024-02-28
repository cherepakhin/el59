package ru.perm.v.el59.office.db.subs;

import java.util.HashSet;
import java.util.Set;
import ru.perm.v.el59.dao.AEntity;
import ru.el59.ui.UI;

public class MainFeature extends AEntity {
   private static final long serialVersionUID = 6385417357474840730L;
   private GroupTovarMainFeature groupTovarMainFeature;
   private Set<CorrectionName> listCorrectionName = new HashSet();
   private Set<ValFeature> listValFeature = new HashSet();
   @UI(
      readonly = false,
      title = "Для ценника",
      visible = true,
      width = 20
   )
   private String nameForTag = "";

   public static String getDescriptionClass() {
      return "Название хар-киs";
   }

   public GroupTovarMainFeature getGroupTovarMainFeature() {
      return this.groupTovarMainFeature;
   }

   public void setGroupTovarMainFeature(GroupTovarMainFeature groupTovarMainFeature) {
      this.groupTovarMainFeature = groupTovarMainFeature;
   }

   public Set<CorrectionName> getListCorrectionName() {
      return this.listCorrectionName;
   }

   public void setListCorrectionName(Set<CorrectionName> listCorrectionName) {
      this.listCorrectionName = listCorrectionName;
   }

   public Set<ValFeature> getListValFeature() {
      return this.listValFeature;
   }

   public void setListValFeature(Set<ValFeature> listValFeature) {
      this.listValFeature = listValFeature;
   }

   public int hashCode() {
      int result = 31 + (this.groupTovarMainFeature == null ? 0 : this.groupTovarMainFeature.hashCode()) + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         MainFeature other = (MainFeature)obj;
         if (this.groupTovarMainFeature == null) {
            if (other.groupTovarMainFeature != null) {
               return false;
            }
         } else if (!this.groupTovarMainFeature.equals(other.groupTovarMainFeature)) {
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

   public String getNameForTag() {
      return this.nameForTag;
   }

   public void setNameForTag(String nameForTag) {
      this.nameForTag = nameForTag;
   }
}
