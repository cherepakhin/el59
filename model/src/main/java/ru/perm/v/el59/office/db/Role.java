package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class Role implements Serializable {
   private static final long serialVersionUID = -6164644779579115142L;
   private Long n;
   private String name = "";
   private Collection<Right> rights = new HashSet();

   public Collection<Right> getRights() {
      return this.rights;
   }

   public void setRights(Collection<Right> rights) {
      this.rights = rights;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }

   public Right getRightByName(String rightName) {
      Iterator i$ = this.rights.iterator();

      Right r;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         r = (Right)i$.next();
      } while(!r.getName().equals(rightName));

      return r;
   }
}
