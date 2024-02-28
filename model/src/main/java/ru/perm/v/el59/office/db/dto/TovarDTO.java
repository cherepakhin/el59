package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.UI;

public class TovarDTO extends AUIBean implements Serializable {
   private static final long serialVersionUID = 3512735629287570241L;
   private String bestgroup;
   @UI(
      readonly = true,
      title = "Н.номер",
      visible = true,
      width = 10
   )
   protected Integer nnum;
   @UI(
      readonly = true,
      title = "Название",
      visible = true,
      width = 30
   )
   protected String name;
   private String ed;
   private Integer category;
   private String annotation;
   private ArrayList<String> s_annot = new ArrayList();
   private HashMap<String, Integer> hashPos = new HashMap();
   private HashMap<String, String> hashVal = new HashMap();
   private boolean splitted;
   protected Integer srcNnum;

   public String getAnnotation() {
      return this.annotation;
   }

   public void setAnnotation(String _annotation) {
      this.annotation = _annotation;
   }

   public Integer getCategory() {
      if (this.category == null) {
         this.category = 0;
      }

      return this.category;
   }

   public void setCategory(Integer category) {
      this.category = category;
   }

   public TovarDTO() {
      this.nnum = 0;
      this.name = new String("");
      this.category = 0;
      this.ed = "шт.";
      this.splitted = false;

      for(int i = 1; i < 36; ++i) {
         this.s_annot.add(this.getFormatAnnotString(i, ""));
      }

      this.hashVal.put("ZNAMEALL", "");
      this.hashPos.put("ZNAMEALL", 17);
      this.hashVal.put("ZBRAND", "");
      this.hashPos.put("ZBRAND", 18);
      this.hashVal.put("ZKODMOD", "");
      this.hashPos.put("ZKODMOD", 19);
      this.hashVal.put("ZNAMERUSSIA", "");
      this.hashPos.put("ZNAMERUSSIA", 20);
      this.hashVal.put("ZPROPERTIES", "");
      this.hashPos.put("ZPROPERTIES", 26);
      this.hashVal.put("ZANNOTACIA", "");
      this.hashPos.put("ZANNOTACIA", 21);
      this.hashVal.put("ZANNOTACIA1", "");
      this.hashPos.put("ZANNOTACIA1", 22);
      this.hashVal.put("ZANNOTACIA2", "");
      this.hashPos.put("ZANNOTACIA2", 23);
      this.hashVal.put("ZANNOTACIA3", "");
      this.hashPos.put("ZANNOTACIA3", 24);
      this.hashVal.put("ZCOUNTRY", "");
      this.hashPos.put("ZCOUNTRY", 25);
      this.hashVal.put("ZDNTFORGET1", "");
      this.hashPos.put("ZDNTFORGET1", 3);
      this.hashVal.put("ZDNTFORGET2", "");
      this.hashPos.put("ZDNTFORGET2", 4);
      this.hashVal.put("ZDNTFORGET3", "");
      this.hashPos.put("ZDNTFORGET3", 5);
      this.hashVal.put("ZWARRANTY", "");
      this.hashPos.put("ZWARRANTY", 6);
      this.hashVal.put("ZSGAB", "");
      this.hashPos.put("ZSGAB", 27);
      this.hashVal.put("ZSMOT", "");
      this.hashPos.put("ZSMOT", 28);
      this.hashVal.put("ZSMZAG", "");
      this.hashPos.put("ZSMZAG", 29);
      this.hashPos.put("ZANNOTACIA", 30);
      this.hashPos.put("ZANNOTACIA1", 31);
      this.hashPos.put("ZANNOTACIA2", 32);
      this.hashPos.put("ZANNOTACIA3", 33);
      this.hashVal.put("ZFRIDHC", "");
      this.hashPos.put("ZFRIDHC", 29);
      this.hashVal.put("ZENERFRID", "");
      this.hashPos.put("ZENERFRID", 34);
      this.hashVal.put("ZFRIDALLV", "");
      this.hashPos.put("ZFRIDALLV", 27);
      this.hashVal.put("ZFRIDMC", "");
      this.hashPos.put("ZFRIDMC", 28);
      this.hashVal.put("ZFRIDKK", "");
      this.hashPos.put("ZFRIDKK", 35);
      this.annotation = this.getAnnotation();
   }

   public TovarDTO(Tovar tovar) {
      this.nnum = tovar.getNnum();
      this.ed = tovar.getEd();
      this.category = tovar.getCategory();
      this.bestgroup = tovar.getGroup().getBestAsString();
      this.name = tovar.getName();
   }

   public Integer getNnum() {
      return this.nnum;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         TovarDTO other = (TovarDTO)obj;
         if (this.nnum == null) {
            if (other.nnum != null) {
               return false;
            }
         } else if (!this.nnum.equals(other.nnum)) {
            return false;
         }

         return true;
      }
   }

   public void setNnum(Integer nnum) {
      this.nnum = nnum;
   }

   public String getEd() {
      return this.ed;
   }

   public void setEd(String ed) {
      this.ed = ed;
   }

   public int hashCode() {
      int result = 31 + (this.nnum == null ? 0 : this.nnum.hashCode());
      return result;
   }

   public long hashSum() {
      long ret = (long)this.nnum;
      return ret;
   }

   private String getFormatAnnotString(Integer i, String s) {
      return !s.contains("\r\n") ? i.toString() + "." + s + "\r\n" : s;
   }

   public void setFeature(String name, String val) {
      if (this.hashVal.containsKey(name)) {
         this.hashVal.put(name, val);
         this.s_annot.set((Integer)this.hashPos.get(name) - 1, this.getFormatAnnotString((Integer)this.hashPos.get(name), val));
         this.annotation = "";

         for(int i = 0; i < 35; ++i) {
            this.annotation = this.annotation + (String)this.s_annot.get(i);
         }
      }

   }

   private void splitAnnotation() {
      if (this.annotation != null && !this.splitted) {
         this.splitted = true;
         String[] split = this.annotation.split("\r\n");

         for(int i = 0; i < split.length; ++i) {
            if (this.hashPos.containsValue(i + 1)) {
               Iterator iter = this.hashPos.keySet().iterator();

               while(iter.hasNext()) {
                  String feature = (String)iter.next();
                  if ((Integer)this.hashPos.get(feature) == i + 1) {
                     String s = split[i];
                     String snum = String.valueOf(i + 1) + ".";
                     s = s.replaceFirst(snum, "");
                     s = s.replaceFirst("\r\n", "");
                     this.hashVal.put(feature, s);
                  }
               }
            }
         }
      }

   }

   public String getFeature(String val) {
      this.splitAnnotation();
      return this.hashVal.containsKey(val) ? (String)this.hashVal.get(val) : "";
   }

   public String getBestgroup() {
      return this.bestgroup;
   }

   public void setBestgroup(String bestgroup) {
      this.bestgroup = bestgroup;
   }

   public Integer getSrcNnum() {
      return this.srcNnum;
   }

   public void setSrcNnum(Integer srcNnum) {
      this.srcNnum = srcNnum;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
