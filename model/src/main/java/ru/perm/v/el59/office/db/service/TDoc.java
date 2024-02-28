package ru.perm.v.el59.office.db.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import ru.el59.office.db.TypeDoc;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Vars;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class TDoc extends AUIBean implements Serializable {
   private static final long serialVersionUID = -9059726917983863014L;
   private Long n;
   @UI(
      readonly = true,
      title = "Автор",
      visible = true,
      width = 10
   )
   private Manager autor;
   @UI(
      readonly = false,
      title = "Создан",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Документ",
      visible = true,
      width = 10
   )
   private TypeDoc typeDoc = new TypeDoc();
   @UI(
      readonly = true,
      title = "Основание",
      visible = true,
      width = 10
   )
   private TDoc parent;
   @UI(
      readonly = true,
      title = "Начало",
      visible = true,
      width = 10
   )
   private TDoc rootDoc;
   @UI(
      readonly = true,
      title = "Содержание",
      visible = true,
      width = 22
   )
   private Object content;
   @UI(
      readonly = true,
      title = "Последний документ",
      visible = true
   )
   private TDoc lastTDoc;
   @UI(
      readonly = true,
      title = "Срок истекает через",
      visible = true,
      width = 5
   )
   private Integer restday = 0;
   protected static String descriptionClass = "Документ";
   @UI(
      readonly = true,
      title = "Изображения",
      visible = true
   )
   private Collection<TDocImage> tdocimage = new HashSet();

   public Collection<TDocImage> getTdocimage() {
      return this.tdocimage;
   }

   public void setTdocimage(Collection<TDocImage> tdocimage) {
      this.tdocimage = tdocimage;
   }

   public String getDescriptionClass() {
      return descriptionClass;
   }

   public Object getContent() {
      return this.content;
   }

   public void setContent(Object content) {
      this.content = content;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public Manager getAutor() {
      return this.autor;
   }

   public void setAutor(Manager autor) {
      this.autor = autor;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public TypeDoc getTypeDoc() {
      return this.typeDoc;
   }

   public void setTypeDoc(TypeDoc typeDoc) {
      this.typeDoc = typeDoc;
      this.restday = this.getRestday();
   }

   public TDoc getParent() {
      return this.parent;
   }

   public void setParent(TDoc parent) {
      this.parent = parent;
   }

   public TDoc getRootDoc() {
      return this.rootDoc;
   }

   public void setRootDoc(TDoc rootDoc) {
      this.rootDoc = rootDoc;
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
         TDoc other = (TDoc)obj;
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

   public String toString() {
      return " №" + this.n.toString() + " от " + Vars.format(this.ddate);
   }

   public TDoc getLastTDoc() {
      return this.lastTDoc;
   }

   public void setLastTDoc(TDoc lastTDoc) {
      this.lastTDoc = lastTDoc;
   }

   public static Long getDiffDate(Date fromdate, Date todate) {
      return (todate.getTime() - fromdate.getTime()) / 86400000L;
   }

   public Long getRestDay() {
      return getDiffDate(this.getDdate(), new Date());
   }

   public void setRestday(Integer restday) {
      this.restday = restday;
   }

   public Integer getRestday() {
      return this.restday;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }
}
