package ru.perm.v.el59.office.db;

import java.io.Serializable;

import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.UI;

public class Operation extends AUIBean implements Serializable {
   private static final long serialVersionUID = 3071294513795158155L;
   private String chr;
   @UI(
      readonly = false,
      title = "Операция",
      visible = true,
      width = 10
   )
   private String name = "";
   private String best;
   private OpGroup opgroup;
   private Integer znak;

   public static String getDescriptionClass() {
      return "Операция";
   }

   public Integer getZnak() {
      return this.znak;
   }

   public void setZnak(Integer znak) {
      this.znak = znak;
   }

   public String getChr() {
      return this.chr;
   }

   public void setChr(String chr) {
      this.chr = chr;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBest() {
      return this.best;
   }

   public void setBest(String best) {
      this.best = best;
   }

   public OpGroup getOpgroup() {
      return this.opgroup;
   }

   public void setOpgroup(OpGroup opgroup) {
      this.opgroup = opgroup;
   }

   public int hashCode() {
      int result = 31 + (this.chr == null ? 0 : this.chr.hashCode());
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
         Operation other = (Operation)obj;
         if (this.chr == null) {
            if (other.chr != null) {
               return false;
            }
         } else if (!this.chr.equals(other.chr)) {
            return false;
         }

         return true;
      }
   }

   public String toString() {
      return this.name;
   }
}
