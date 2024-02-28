package ru.el59.office.shopmodel;

import java.util.Date;
import ru.el59.dao.AEntity;
import ru.el59.office.db.UserShop;
import ru.el59.ui.UI;

public class Log extends AEntity {
   private static final long serialVersionUID = -950299058109959885L;
   @UI(
      readonly = false,
      title = "Дата",
      visible = true,
      width = 10
   )
   private Date ddate;
   @UI(
      readonly = false,
      title = "Автор",
      visible = true,
      width = 10
   )
   private UserShop usershop;
   @UI(
      readonly = false,
      title = "Таблица",
      visible = true,
      width = 10
   )
   private String entityname;
   @UI(
      readonly = false,
      title = "Номер",
      visible = true,
      width = 5
   )
   private Long nentity;
   @UI(
      readonly = false,
      title = "Описание",
      visible = true,
      width = 10
   )
   private String entitydescription;
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 20
   )
   private String comment;

   public static String getDescriptionClass() {
      return "Журнал исправлений";
   }
}
