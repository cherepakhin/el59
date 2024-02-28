package ru.perm.v.el59.office.db.subs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.GroupTovar;

public class GroupTovarMainFeature extends AEntity {
   private static final long serialVersionUID = -7326629496661078134L;
   private GroupTovar groupTovar;
   private List<MainFeature> mainFeatures = new ArrayList();

   public List<MainFeature> getMainFeatures() {
      return this.mainFeatures;
   }

   public void setMainFeatures(List<MainFeature> mainFeatures) {
      this.mainFeatures = mainFeatures;
   }

   public void addMainFeature(MainFeature mainFeature) {
      if (!this.mainFeatures.contains(mainFeature)) {
         mainFeature.setGroupTovarMainFeature(this);
         this.mainFeatures.add(mainFeature);
      }

   }

   public void removeMainFeature(MainFeature mainFeature) {
      if (this.mainFeatures.contains(mainFeature)) {
         this.mainFeatures.remove(mainFeature);
      }

   }

   public GroupTovar getGroupTovar() {
      return this.groupTovar;
   }

   public void setGroupTovar(GroupTovar groupTovar) {
      this.groupTovar = groupTovar;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof GroupTovarMainFeature)) return false;
      if (!super.equals(o)) return false;
      GroupTovarMainFeature that = (GroupTovarMainFeature) o;
      return Objects.equals(groupTovar, that.groupTovar) && Objects.equals(mainFeatures, that.mainFeatures);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), groupTovar, mainFeatures);
   }
}
