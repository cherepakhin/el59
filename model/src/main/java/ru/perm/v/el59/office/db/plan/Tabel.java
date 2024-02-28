package ru.perm.v.el59.office.db.plan;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import ru.perm.v.el59.dao.AEntity;

public class Tabel extends AEntity implements Serializable {
   private static final long serialVersionUID = 789870848211025478L;
   private TypeTabel typeTabel;
   private UserZP userzp;
   private Map<Date, Smena> smena = new HashMap();
   private HashMap<String, Integer> itogo = new HashMap();

   public Tabel() {
      this.smena = new HashMap();
   }

   public TypeTabel getTypeTabel() {
      return this.typeTabel;
   }

   public void setTypeTabel(TypeTabel typeTabel) {
      this.typeTabel = typeTabel;
   }

   public UserZP getUserzp() {
      return this.userzp;
   }

   public void setUserzp(UserZP userzp) {
      this.userzp = userzp;
   }

   public void addSmena(Smena _smena) {
      if (this.smena == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         _smena.setTabel(this);
         this.smena.put(_smena.getDdate(), _smena);
      }
   }

   public void delTabel(Smena _smena) {
      if (this.smena == null) {
         throw new IllegalArgumentException("Null category");
      } else {
         this.smena.remove(_smena.getDdate());
      }
   }

   public Map<Date, Smena> getSmena() {
      return this.smena;
   }

   public void setSmena(Map<Date, Smena> smena) {
      this.smena = smena;
   }

   public HashMap<String, Integer> getItogo() {
      this.itogo = new HashMap();
      Iterator i$ = this.getSmena().keySet().iterator();

      while(i$.hasNext()) {
         Date date = (Date)i$.next();
         Smena smena = (Smena)this.getSmena().get(date);
         if (!smena.getName().isEmpty()) {
            if (this.itogo.containsKey(smena.getName())) {
               this.itogo.put(smena.getName(), (Integer)this.itogo.get(smena.getName()) + 1);
            } else {
               this.itogo.put(smena.getName(), 1);
            }
         }
      }

      return this.itogo;
   }

   public static String getDescriptionClass() {
      return "Табель";
   }
}
