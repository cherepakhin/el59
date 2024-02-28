package ru.perm.v.el59.office.db;

import java.util.Collection;
import java.util.HashSet;
import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class Thing extends AEntity {
   private static final long serialVersionUID = 5202443376633299409L;
   private Collection<ThingSinonim> sinonims = new HashSet();
   @UI(
      readonly = false,
      title = "Полное название",
      visible = true,
      width = 15
   )
   private String nameFull = "";

   public void addSinonim(ThingSinonim sinonim) {
      if (sinonim == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         sinonim.setThing(this);
         this.sinonims.add(sinonim);
      }
   }

   public Collection<ThingSinonim> getSinonims() {
      return this.sinonims;
   }

   public void setSinonims(Collection<ThingSinonim> sinonims) {
      this.sinonims = sinonims;
   }

   public static String getDescriptionClass() {
      return "Классификатор";
   }

   public String getNameFull() {
      return this.nameFull;
   }

   public void setNameFull(String nameFull) {
      this.nameFull = nameFull;
   }
}
