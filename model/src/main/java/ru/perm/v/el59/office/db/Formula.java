package ru.perm.v.el59.office.db;

import ru.perm.v.el59.dao.AEntity;
import ru.el59.ui.UI;

public class Formula extends AEntity {
   private static final long serialVersionUID = 7341491902891498300L;
   @UI(
      readonly = false,
      title = "Формула",
      visible = true,
      width = 40
   )
   private String content = "";

   public static String getDescriptionClass() {
      return "Формула пересчета прайса";
   }

   public String getContent() {
      return this.content;
   }

   public void setContent(String content) {
      this.content = content;
   }
}
