package ru.perm.v.el59.office.db.web;

import java.util.Date;
import ru.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Manager;
import ru.el59.ui.UI;

public class CommentDocW extends AEntity {
   private static final long serialVersionUID = 191004861243123660L;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 30
   )
   private String comment = "";
   @UI(
      readonly = false,
      title = "Выписка",
      visible = true,
      complex = true,
      width = 10
   )
   private DocW docw;
   @UI(
      readonly = false,
      title = "Автор",
      visible = true,
      complex = true,
      width = 10
   )
   private Manager manager;
   @UI(
      readonly = false,
      title = "Удален?",
      visible = true,
      complex = true,
      width = 4
   )
   private Boolean deleted = false;

   public static String getDescriptionClass() {
      return "Заметки к выписке инет-магазина";
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public DocW getDocw() {
      return this.docw;
   }

   public void setDocw(DocW docw) {
      this.docw = docw;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }
}
