package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.util.Date;
import ru.el59.ui.AUIBean;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class HistoryTovar extends AUIBean implements Serializable, ITovar {
   @UI(
      readonly = true,
      title = "id",
      visible = true,
      width = 0
   )
   private Long n;
   private static final long serialVersionUID = -3132018595608326300L;
   private Date ddatechange = new Date();
   @UI(
      readonly = true,
      title = "Изменил",
      visible = true,
      width = 10,
      justify = Justify.LEFT
   )
   private Manager manager;
   @UI(
      readonly = true,
      title = "Дата изменения",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Date ddate;
   @UI(
      readonly = true,
      title = "Товар",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Tovar tovar;
   protected GroupTovar group;
   @UI(
      readonly = true,
      title = "Название",
      visible = true,
      width = 30
   )
   protected String description = "";
   @UI(
      readonly = true,
      title = "Производитель",
      visible = true,
      width = 20
   )
   private String brand = "";
   @UI(
      readonly = true,
      title = "Классификатор",
      visible = true,
      width = 20
   )
   private String thing = "";
   @UI(
      readonly = true,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment = "";

   public HistoryTovar() {
      this.ddatechange = new Date();
   }

   public static String getDescriptionClass() {
      return "История товара";
   }

   public Date getDdatechange() {
      return this.ddatechange;
   }

   public void setDdatechange(Date ddatechange) {
      this.ddatechange = ddatechange;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
   }

   public GroupTovar getGroup() {
      return this.group;
   }

   public void setGroup(GroupTovar group) {
      this.group = group;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getBrand() {
      return this.brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public String getThing() {
      return this.thing;
   }

   public void setThing(String thing) {
      this.thing = thing;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }
}
