package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.util.Date;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.UI;

public class FeatureOld extends AUIBean implements Serializable {
   private static final long serialVersionUID = -257835430201974349L;
   private Long n;
   @UI(
      readonly = false,
      title = "Название",
      visible = true,
      width = 10
   )
   protected String name = "";
   private TovarInfo tovarInfo;
   @UI(
      readonly = false,
      title = "Дата замены",
      visible = true,
      width = 15
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Характеристика(до замены)",
      visible = true,
      width = 15
   )
   private String oldname = "";
   @UI(
      readonly = false,
      title = "Значение",
      visible = true,
      width = 15
   )
   private String val = "";
   @UI(
      readonly = false,
      title = "Значение",
      visible = true,
      width = 15
   )
   private String oldval = "";
   @UI(
      readonly = false,
      title = "Группа",
      visible = true,
      width = 10
   )
   private String grp = "";
   @UI(
      readonly = false,
      title = "Основная",
      visible = true,
      width = 10
   )
   private Boolean prmry = false;

   public static String getDescriptionClass() {
      return "Характеристика товара";
   }

   public FeatureOld(Feature feature, TovarInfo tovarInfo2) {
      this.tovarInfo = tovarInfo2;
      this.name = feature.getName();
      this.oldname = feature.getOldname();
      this.val = feature.getVal();
      this.oldval = feature.getOldval();
      this.prmry = feature.getPrmry();
      this.grp = feature.getGrp();
   }

   public FeatureOld() {
   }

   public String getVal() {
      return this.val;
   }

   public void setVal(String val) {
      this.val = val;
   }

   public String getGrp() {
      return this.grp;
   }

   public void setGrp(String grp) {
      this.grp = grp;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int hashCode() {
      int result = 31 + (this.name == null ? 0 : this.name.hashCode());
      result = 31 * result + (this.tovarInfo == null ? 0 : this.tovarInfo.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         FeatureOld other = (FeatureOld)obj;
         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else if (!this.name.equals(other.name)) {
            return false;
         }

         if (this.tovarInfo == null) {
            if (other.tovarInfo != null) {
               return false;
            }
         } else if (!this.tovarInfo.equals(other.tovarInfo)) {
            return false;
         }

         return true;
      }
   }

   public TovarInfo getTovarInfo() {
      return this.tovarInfo;
   }

   public void setTovarInfo(TovarInfo tovarInfo) {
      this.tovarInfo = tovarInfo;
   }

   public Boolean getPrmry() {
      return this.prmry;
   }

   public void setPrmry(Boolean prmry) {
      this.prmry = prmry;
   }

   public String getOldname() {
      return this.oldname;
   }

   public void setOldname(String oldname) {
      this.oldname = oldname;
   }

   public String getOldval() {
      return this.oldval;
   }

   public void setOldval(String oldval) {
      this.oldval = oldval;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }
}
