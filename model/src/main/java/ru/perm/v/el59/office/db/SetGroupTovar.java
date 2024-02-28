package ru.perm.v.el59.office.db;

import java.util.Collection;
import java.util.HashSet;
import ru.el59.dao.AEntity;

public class SetGroupTovar extends AEntity {
   private static final long serialVersionUID = -3022163233946564845L;
   private Collection<GroupTovar> groups = new HashSet();

   public void addGroupTovar(GroupTovar _group) {
      if (this.groups == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.groups.add(_group);
      }
   }

   public Collection<GroupTovar> getGroups() {
      return this.groups;
   }

   public void setGroups(Collection<GroupTovar> groups) {
      this.groups = groups;
   }

   public static String getDescriptionClass() {
      return "НаборЫ групп товаров";
   }
}
