package ru.perm.v.el59.office.db.subs;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.UI;

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

   public String getNameForTag() {
      return this.nameForTag;
   }

   public void setNameForTag(String nameForTag) {
      this.nameForTag = nameForTag;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof MainFeature)) return false;
      if (!super.equals(o)) return false;
      MainFeature that = (MainFeature) o;
      return Objects.equals(groupTovarMainFeature, that.groupTovarMainFeature) && Objects.equals(listCorrectionName, that.listCorrectionName) && Objects.equals(listValFeature, that.listValFeature) && Objects.equals(nameForTag, that.nameForTag);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), groupTovarMainFeature, listCorrectionName, listValFeature, nameForTag);
   }
}
