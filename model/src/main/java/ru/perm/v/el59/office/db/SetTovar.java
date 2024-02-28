package ru.perm.v.el59.office.db;

import java.util.Collection;
import java.util.HashSet;
import ru.el59.dao.AEntity;

public class SetTovar extends AEntity {
   private static final long serialVersionUID = -4154310581974264158L;
   private Collection<Tovar> tovars = new HashSet();

   public SetTovar() {
      this.tovars = new HashSet();
   }

   public void addTovar(Tovar _tovar) {
      if (this.tovars == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         if (!this.tovars.contains(_tovar)) {
            this.tovars.add(_tovar);
         }

      }
   }

   public void removeTovar(Tovar _tovar) {
      if (this.tovars == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         if (this.tovars.contains(_tovar)) {
            this.tovars.remove(_tovar);
         }

      }
   }

   public Collection<Tovar> getTovars() {
      return this.tovars;
   }

   public void setTovars(Collection<Tovar> tovars) {
      this.tovars = tovars;
   }

   public static String getDescriptionClass() {
      return "Набор товаров";
   }
}
