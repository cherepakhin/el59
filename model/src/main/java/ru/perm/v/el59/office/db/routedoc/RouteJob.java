package ru.perm.v.el59.office.db.routedoc;

import java.util.Date;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Doc;
import ru.el59.ui.UI;

public class RouteJob extends AEntity {
   private static final long serialVersionUID = -7217444065628510066L;
   @UI(
      readonly = false,
      title = "Путевой лист",
      visible = true,
      complex = true,
      width = 10
   )
   private PathPage pathPage;
   @UI(
      readonly = false,
      title = "Документ",
      visible = true,
      complex = true,
      width = 10
   )
   private Doc doc;
   @UI(
      readonly = false,
      title = "Прибытие",
      visible = true,
      width = 20
   )
   private Date timeArrival;
   @UI(
      readonly = false,
      title = "Отъезд",
      visible = true,
      width = 20
   )
   private Date timeDeparture;
   @UI(
      readonly = false,
      title = "Куда",
      visible = true,
      width = 20
   )
   private Contragent targetContragent;
   @UI(
      readonly = false,
      title = "От кого",
      visible = true,
      width = 20
   )
   private Contragent otherContragent;
   @UI(
      readonly = false,
      title = "Погрузка?",
      visible = true,
      width = 4
   )
   private Boolean loading = true;
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment;

   public static String getDescriptionClass() {
      return "Маршрутное задание";
   }

   public Date getTimeArrival() {
      return this.timeArrival;
   }

   public void setTimeArrival(Date timeArrival) {
      this.timeArrival = timeArrival;
   }

   public Date getTimeDeparture() {
      return this.timeDeparture;
   }

   public void setTimeDeparture(Date timeDeparture) {
      this.timeDeparture = timeDeparture;
   }

   public Contragent getTargetContragent() {
      return this.targetContragent;
   }

   public void setTargetContragent(Contragent targetContragent) {
      this.targetContragent = targetContragent;
   }

   public Contragent getOtherContragent() {
      return this.otherContragent;
   }

   public void setOtherContragent(Contragent otherContragent) {
      this.otherContragent = otherContragent;
   }

   public Boolean getLoading() {
      return this.loading;
   }

   public void setLoading(Boolean loading) {
      this.loading = loading;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public PathPage getPathPage() {
      return this.pathPage;
   }

   public void setPathPage(PathPage pathPage) {
      this.pathPage = pathPage;
   }

   public Doc getDoc() {
      return this.doc;
   }

   public void setDoc(Doc doc) {
      this.doc = doc;
   }
}
