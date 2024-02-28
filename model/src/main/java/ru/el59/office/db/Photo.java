package ru.el59.office.db;

import java.io.Serializable;
import java.util.Date;
import ru.el59.ui.AUIBean;
import ru.el59.ui.UI;

public class Photo extends AUIBean implements Serializable {
   private static final long serialVersionUID = -949376883123549105L;
   private TovarInfo tovarInfo;
   @UI(
      readonly = false,
      title = "Вид",
      visible = true,
      width = 10
   )
   private String name;
   @UI(
      readonly = false,
      title = "Путь",
      visible = true,
      width = 10
   )
   private String path = "";
   @UI(
      readonly = false,
      title = "Очередность",
      visible = true,
      width = 4
   )
   private Integer ord = 0;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true
   )
   private Date ddate = new Date();
   @UI(
      readonly = false,
      title = "Дата",
      visible = true
   )
   private Manager manager;

   public static String getDescriptionClass() {
      return "Фотография";
   }

   public String getPath() {
      return this.path;
   }

   public void setPath(String path) {
      this.path = path;
   }

   public Integer getOrd() {
      return this.ord;
   }

   public void setOrd(Integer ord) {
      this.ord = ord;
   }

   public TovarInfo getTovarInfo() {
      return this.tovarInfo;
   }

   public void setTovarInfo(TovarInfo tovarInfo) {
      this.tovarInfo = tovarInfo;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Date getDdate() {
      return this.ddate;
   }

   public void setDdate(Date ddate) {
      this.ddate = ddate;
   }

   public Manager getManager() {
      return this.manager;
   }

   public void setManager(Manager manager) {
      this.manager = manager;
   }
}
