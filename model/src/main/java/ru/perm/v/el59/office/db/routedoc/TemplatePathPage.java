package ru.perm.v.el59.office.db.routedoc;

import java.util.ArrayList;
import java.util.List;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.ui.UI;

public class TemplatePathPage extends AEntity {
   private static final long serialVersionUID = 4808468032935361968L;
   public static String[] DAYS_OF_WEEK = new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
   @UI(
      readonly = false,
      title = "Активен",
      visible = true,
      complex = true,
      width = 10
   )
   private Boolean worked = true;
   @UI(
      readonly = false,
      title = "День недели",
      visible = true,
      complex = true,
      width = 10
   )
   private Integer dayOfWeek = 0;
   @UI(
      readonly = false,
      title = "Магазины",
      visible = true,
      complex = true,
      width = 10
   )
   private List<Shop> listShop = new ArrayList();
   @UI(
      readonly = false,
      title = "Контрагенты",
      visible = true,
      complex = true,
      width = 10
   )
   private List<Contragent> listContragent = new ArrayList();
   @UI(
      readonly = false,
      title = "Машина",
      visible = true,
      complex = true,
      width = 20
   )
   private Machine machine;
   @UI(
      readonly = false,
      title = "Экспедитор",
      visible = true,
      complex = true,
      width = 20
   )
   private Dispatcher dispatcher;
   @UI(
      readonly = false,
      title = "Водитель",
      visible = true,
      complex = true,
      width = 20
   )
   private Driver driver;
   @UI(
      readonly = false,
      title = "День недели",
      visible = true,
      complex = true,
      width = 10
   )
   private String day = "";

   public static String getDescriptionClass() {
      return "Шаблон путевого листа";
   }

   public Machine getMachine() {
      return this.machine;
   }

   public void setMachine(Machine machine) {
      this.machine = machine;
   }

   public Dispatcher getDispatcher() {
      return this.dispatcher;
   }

   public void setDispatcher(Dispatcher dispatcher) {
      this.dispatcher = dispatcher;
   }

   public Driver getDriver() {
      return this.driver;
   }

   public void setDriver(Driver driver) {
      this.driver = driver;
   }

   public List<Contragent> getListContragent() {
      return this.listContragent;
   }

   public void setListContragent(List<Contragent> listContragent) {
      this.listContragent = listContragent;
   }

   public Boolean getWorked() {
      return this.worked;
   }

   public void setWorked(Boolean worked) {
      this.worked = worked;
   }

   public Integer getDayOfWeek() {
      return this.dayOfWeek;
   }

   public void setDayOfWeek(Integer dayOfWeek) {
      this.dayOfWeek = dayOfWeek;
   }

   public String getDay() {
      return DAYS_OF_WEEK[this.dayOfWeek];
   }

   public List<Shop> getListShop() {
      return this.listShop;
   }

   public void setListShop(List<Shop> listShop) {
      this.listShop = listShop;
   }
}
