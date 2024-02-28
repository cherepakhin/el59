package ru.perm.v.el59.office.db.subs;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.UI;

public class ValFeature extends AEntity {
   private static final long serialVersionUID = 2705901007703964502L;
   private MainFeature mainFeature;
   private Set<CorrectionVal> listCorrectionVal = new HashSet();
   @UI(
      readonly = false,
      title = "Для ценника",
      visible = true,
      width = 20
   )
   private String forTag = "";

   public static String getDescriptionClass() {
      return "Правильное ЗНАЧЕНИЕ для конкретной хара-ки";
   }

   public MainFeature getMainFeature() {
      return this.mainFeature;
   }

   public void setMainFeature(MainFeature mainFeature) {
      this.mainFeature = mainFeature;
   }

   public Set<CorrectionVal> getListCorrectionVal() {
      return this.listCorrectionVal;
   }

   public void setListCorrectionVal(Set<CorrectionVal> listCorrectionVal) {
      this.listCorrectionVal = listCorrectionVal;
   }

   public String getForTag() {
      return this.forTag;
   }

   public void setForTag(String forTag) {
      this.forTag = forTag;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ValFeature)) return false;
      if (!super.equals(o)) return false;
      ValFeature that = (ValFeature) o;
      return Objects.equals(mainFeature, that.mainFeature) && Objects.equals(listCorrectionVal, that.listCorrectionVal) && Objects.equals(forTag, that.forTag);
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), mainFeature, listCorrectionVal, forTag);
   }
}
