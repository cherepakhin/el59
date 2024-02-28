package ru.perm.v.el59.office.shopmodel;

import ru.perm.v.el59.ui.UI;
import ru.perm.v.el59.dao.AEntity;

import java.util.Objects;

public class TypePrice extends AEntity {
   private static final long serialVersionUID = -2442715849239224408L;
   @UI(
      readonly = false,
      title = "Прайс БЭСТ",
      visible = true,
      width = 10
   )
   private String best = "00000";

   public static String getDescriptionClass() {
      return "Тип прайса";
   }

   public String getBest() {
      return this.best;
   }

   public void setBest(String best) {
      this.best = best;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof TypePrice)) return false;
      if (!super.equals(o)) return false;
      TypePrice typePrice = (TypePrice) o;
      return Objects.equals(best, typePrice.best);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), best);
   }
}
