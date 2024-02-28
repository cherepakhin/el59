package ru.perm.v.el59.office.db.subs;

import ru.perm.v.el59.dao.AEntity;

import java.util.Objects;

public class CorrectionName extends AEntity {
   private static final long serialVersionUID = 1302409012467507703L;
   private MainFeature mainFeature;

   public MainFeature getMainFeature() {
      return this.mainFeature;
   }

   public void setMainFeature(MainFeature mainFeature) {
      this.mainFeature = mainFeature;
   }

   public static String getDescriptionClass() {
      return "Имя характеристики";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof CorrectionName)) return false;
      if (!super.equals(o)) return false;
      CorrectionName that = (CorrectionName) o;
      return Objects.equals(mainFeature, that.mainFeature);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), mainFeature);
   }
}
