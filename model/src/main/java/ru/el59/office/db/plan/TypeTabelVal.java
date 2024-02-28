package ru.el59.office.db.plan;

import java.io.Serializable;
import ru.el59.dao.AEntity;
import ru.el59.ui.Justify;
import ru.el59.ui.UI;

public class TypeTabelVal extends AEntity implements Serializable {
   private static final long serialVersionUID = -4811956108575450017L;
   private TypeTabel typeTabel;
   @UI(
      readonly = false,
      title = "Порядок",
      visible = true,
      width = 25,
      justify = Justify.RIGHT
   )
   private Integer index = 0;
   @UI(
      readonly = false,
      title = "Описание",
      visible = true,
      width = 15
   )
   private String description = "";

   public TypeTabelVal() {
      this.index = 0;
   }

   public TypeTabel getTypeTabel() {
      return this.typeTabel;
   }

   public void setTypeTabel(TypeTabel typeTabel) {
      this.typeTabel = typeTabel;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Integer getIndex() {
      return this.index;
   }

   public void setIndex(Integer index) {
      this.index = index;
   }

   public static String getDescriptionClass() {
      return "Значение табеля";
   }
}
