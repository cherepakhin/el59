package ru.perm.v.el59.office.db.service;

import java.io.Serializable;
import ru.perm.v.el59.ui.AUIBean;
import ru.perm.v.el59.ui.UI;

public class TDocImage extends AUIBean implements Serializable {
   private static final long serialVersionUID = -4568232219843618776L;
   private Long n;
   private TDoc tdoc;
   @UI(
      readonly = false,
      title = "Название",
      visible = true,
      width = 10
   )
   private TypeTDocImage name;
   @UI(
      readonly = false,
      title = "Файл",
      visible = true,
      width = 20
   )
   private String filename;
   private byte[] body;

   public static String getDescriptionClass() {
      return "Скан док-та";
   }

   public TDocImage() {
   }

   public TDocImage(TDoc tdoc) {
      this();
      this.tdoc = tdoc;
      this.n = tdoc.getN();
   }

   public String getFilename() {
      return this.filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

      public TDoc getTdoc() {
      return this.tdoc;
   }

   public void setTdoc(TDoc tdoc) {
      this.tdoc = tdoc;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String toString() {
      String ret = "";
      if (this.name != null) {
         ret = this.name.toString();
      }

      return ret;
   }

   public int hashCode() {
      int result = 31 + (this.n == null ? 0 : this.n.hashCode());
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
         TDocImage other = (TDocImage)obj;
         if (this.n == null) {
            if (other.n != null) {
               return false;
            }
         } else if (!this.n.equals(other.n)) {
            return false;
         }

         return true;
      }
   }

   public byte[] getBody() {
      return this.body;
   }

   public void setBody(byte[] body) {
      this.body = body;
   }
}
