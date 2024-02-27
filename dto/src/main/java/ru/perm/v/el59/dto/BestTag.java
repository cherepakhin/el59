package ru.perm.v.el59.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.logging.Logger;

public class BestTag implements Serializable {
   private static final long serialVersionUID = -5888593261390514479L;
   public String group = "";
   public Integer nnum = 0;
   public String name = "";
   public String annot = "";
   public BigDecimal cena = new BigDecimal("0.00");
   public BigDecimal label = new BigDecimal("0.00");
   public String priceName = "";

   public BestTag() {
   }

   public BestTag(String group, Integer nnum, String name, String annot, BigDecimal cena, BigDecimal label, String priceName) {
      this.group = group;
      this.nnum = nnum;
      this.name = name;
      this.annot = annot;
      this.cena = cena;
      this.label = label;
      this.priceName = priceName;
   }

   public String toString() {
      return "BestTag [group=" + this.group + ", nnum=" + this.nnum + ", name=" + this.name + ", annot=" + this.annot + ", cena=" + this.cena + ", label=" + this.label + "]";
   }

   public int hashCode() {
      int result = 31 + (this.annot == null ? 0 : this.annot.hashCode());
      result = 31 * result + (this.cena == null ? 0 : this.cena.hashCode());
      result = 31 * result + (this.label == null ? 0 : this.label.hashCode());
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      result = 31 * result + (this.nnum == null ? 0 : this.nnum.hashCode());
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
         BestTag other = (BestTag)obj;
         if (this.annot == null) {
            if (other.annot != null) {
               return false;
            }
         } else {
            try {
               byte[] b1 = this.annot.getBytes("cp866");
               byte[] b2 = other.annot.getBytes("cp866");
               if (!Arrays.equals(b1, b2)) {
                  return false;
               }
            } catch (UnsupportedEncodingException var8) {
               var8.printStackTrace();
               return false;
            }
         }

         if (this.cena == null || other.cena == null) {
            Logger.getLogger(String.format("other.cena==null nnum=%d", this.nnum));
         }

         if (this.cena == null) {
            if (other.cena != null) {
               return false;
            }
         } else if (this.cena.compareTo(other.cena) != 0) {
            return false;
         }

         if (this.label == null) {
            if (other.label != null) {
               return false;
            }
         } else if (this.label.compareTo(other.label) != 0) {
            return false;
         }

         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else {
            String s1 = this.name.substring(0, this.name.length() > 33 ? 33 : this.name.length()).trim();
            String s2 = other.name.substring(0, other.name.length() > 33 ? 33 : other.name.length()).trim();

            try {
               byte[] b1 = s1.getBytes("cp866");
               byte[] b2 = s2.getBytes("cp866");
               if (!Arrays.equals(b1, b2)) {
                  return false;
               }
            } catch (UnsupportedEncodingException var7) {
               var7.printStackTrace();
               return false;
            }
         }

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

   public String getPriceName() {
      return this.priceName;
   }

   public void setPriceName(String priceName) {
      this.priceName = priceName;
   }
}
