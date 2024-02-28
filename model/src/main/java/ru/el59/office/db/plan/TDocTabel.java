package ru.el59.office.db.plan;

import java.io.Serializable;
import ru.el59.office.db.service.ATDoc;
import ru.el59.office.db.service.ITDoc;
import ru.el59.ui.UI;

public class TDocTabel extends ATDoc implements Serializable, ITDoc {
   private static final long serialVersionUID = 1928872940659947222L;
   @UI(
      readonly = false,
      title = "Комментарий",
      visible = true,
      width = 40
   )
   private String comment;
   protected Smena smena;

   public static String getDescriptionClass() {
      return "Приложение к табелю";
   }

   public String getContent() {
      return this.getComment();
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Smena getSmena() {
      return this.smena;
   }

   public void setSmena(Smena smena) {
      this.smena = smena;
   }
}
