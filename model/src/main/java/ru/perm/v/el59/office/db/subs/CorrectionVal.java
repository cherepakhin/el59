package ru.perm.v.el59.office.db.subs;

import ru.perm.v.el59.dao.AEntity;

import java.util.Objects;

public class CorrectionVal extends AEntity {
   private static final long serialVersionUID = 4631143418951787275L;
   private ValFeature valFeature;

   public static String getDescriptionClass() {
      return "Ошибочное значение для значения хар-ки";
   }

   public ValFeature getValFeature() {
      return this.valFeature;
   }

   public void setValFeature(ValFeature valFeature) {
      this.valFeature = valFeature;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof CorrectionVal)) return false;
      if (!super.equals(o)) return false;
      CorrectionVal that = (CorrectionVal) o;
      return Objects.equals(valFeature, that.valFeature);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), valFeature);
   }
}
