package ru.el59.office.db;

import java.util.Date;
import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class Analog extends AEntity implements ITovar {
   private static final long serialVersionUID = -2605221743907918446L;
   @UI(
      readonly = true,
      title = "Товар",
      visible = true,
      width = 30
   )
   private Tovar tovar;
   @UI(
      readonly = true,
      title = "Изменил",
      visible = true,
      width = 15
   )
   private Manager manager;
   @UI(
      readonly = true,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate = new Date();

   public static String getDescriptionClass() {
      return "Аналог";
   }

   public Tovar getTovar() {
      return this.tovar;
   }

   public void setTovar(Tovar tovar) {
      this.tovar = tovar;
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
}
