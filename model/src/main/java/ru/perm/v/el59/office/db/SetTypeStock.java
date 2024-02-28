package ru.perm.v.el59.office.db;

import java.util.Collection;
import java.util.HashSet;
import ru.el59.dao.AEntity;

public class SetTypeStock extends AEntity {
   private static final long serialVersionUID = -8794945334153327044L;
   private Collection<TypeStock> typeStocks = new HashSet();

   public void addTypeStock(TypeStock _typestock) {
      if (this.typeStocks == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.typeStocks.add(_typestock);
      }
   }

   public static String getDescriptionClass() {
      return "Набор складов";
   }

   public Collection<TypeStock> getTypeStocks() {
      return this.typeStocks;
   }

   public void setTypeStocks(Collection<TypeStock> typeStocks) {
      this.typeStocks = typeStocks;
   }
}
