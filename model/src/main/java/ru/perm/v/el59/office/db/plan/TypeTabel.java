package ru.perm.v.el59.office.db.plan;

import ru.perm.v.el59.dao.AEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeTabel extends AEntity implements Serializable {
   private static final long serialVersionUID = 5254165448118890847L;
   private List<TypeTabelVal> vals = new ArrayList();

   public List<TypeTabelVal> getVals() {
      return this.vals;
   }

   public void setVals(List<TypeTabelVal> vals) {
      this.vals = vals;
   }

   public void addVal(TypeTabelVal val) {
      val.setTypeTabel(this);
      this.vals.add(val);
   }

   public void delVal(TypeTabelVal val) {
      this.vals.remove(val);
   }

   public static String getDescriptionClass() {
      return "Тип табеля";
   }
}
