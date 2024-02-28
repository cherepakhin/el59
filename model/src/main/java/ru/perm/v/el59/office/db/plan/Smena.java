package ru.perm.v.el59.office.db.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.dao.AEntity;

public class Smena extends AEntity implements Serializable {
   private static final long serialVersionUID = 4292289514946604156L;
   private Tabel tabel;
   private Date ddate;
   private List<TDocTabel> tdoctabel = new ArrayList();

   public Tabel getTabel() {
      return this.tabel;
   }

   public void setTabel(Tabel tabel) {
      this.tabel = tabel;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public void addTDocTabel(TDocTabel _tdoc) {
      if (this.tdoctabel == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         _tdoc.setSmena(this);
         this.tdoctabel.add(_tdoc);
      }
   }

   public void delTDocTabel(TDocTabel _tdoc) {
      if (this.tdoctabel == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.tdoctabel.remove(_tdoc);
      }
   }

   public List<TDocTabel> getTdoctabel() {
      return this.tdoctabel;
   }

   public void setTdoctabel(List<TDocTabel> tdoctabel) {
      this.tdoctabel = tdoctabel;
   }

   public static String getDescriptionClass() {
      return "Смена";
   }
}
