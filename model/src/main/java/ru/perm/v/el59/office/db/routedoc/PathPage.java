package ru.perm.v.el59.office.db.routedoc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.office.db.Manager;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class PathPage extends AEntity {
   private static final long serialVersionUID = 4908548474708722445L;
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
      title = "Дата",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private Date ddate = new Date();
   @UI(
      readonly = true,
      title = "Задания",
      visible = true,
      width = 10
   )
   private List<RouteJob> listRouteJob = new ArrayList();
   @UI(
      readonly = false,
      title = "Объем",
      visible = true,
      width = 10,
      justify = Justify.RIGHT
   )
   private BigDecimal volume = new BigDecimal("0.00");
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
      title = "Шаблон",
      visible = true,
      complex = true,
      width = 20
   )
   private TemplatePathPage templatePathPage;
   @UI(
      readonly = false,
      title = "Закрыт",
      visible = true,
      complex = true,
      width = 20
   )
   private Boolean closed = false;

   public static String getDescriptionClass() {
      return "Путевой лист";
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

   public List<RouteJob> getListRouteJob() {
      return this.listRouteJob;
   }

   public void setListRouteJob(List<RouteJob> listRouteJob) {
      this.listRouteJob = listRouteJob;
   }

   public BigDecimal getVolume() {
      return this.volume;
   }

   public void setVolume(BigDecimal volume) {
      this.volume = volume;
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

   public TemplatePathPage getTemplatePathPage() {
      return this.templatePathPage;
   }

   public void setTemplatePathPage(TemplatePathPage templatePathPage) {
      this.templatePathPage = templatePathPage;
   }

   public Boolean getClosed() {
      return this.closed;
   }

   public void setClosed(Boolean closed) {
      this.closed = closed;
   }
}
