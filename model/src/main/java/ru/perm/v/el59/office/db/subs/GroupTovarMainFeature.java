package ru.perm.v.el59.office.db.subs;

import java.util.ArrayList;
import java.util.List;
import ru.el59.dao.AEntity;
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

   public int hashCode() {
      int result = 31 + (this.groupTovar == null ? 0 : this.groupTovar.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         GroupTovarMainFeature other = (GroupTovarMainFeature)obj;
         if (this.groupTovar == null) {
            if (other.groupTovar != null) {
               return false;
            }
         } else if (!this.groupTovar.equals(other.groupTovar)) {
            return false;
         }

         return true;
      }
   }
}
