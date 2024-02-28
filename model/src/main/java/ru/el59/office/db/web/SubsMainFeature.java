package ru.el59.office.db.web;

import java.io.Serializable;
import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class SubsMainFeature extends AEntity implements Serializable {
   private static final long serialVersionUID = 4276159506633040498L;
   public static String[] namesTypeProperty = new String[]{"В названии", "В значении"};
   @UI(
      readonly = false,
      title = "Тип",
      visible = true,
      width = 100
   )
   private TypeProperty typeProp;
   @UI(
      readonly = false,
      title = "Что искать",
      visible = true,
      width = 15
   )
   private String seekPhrase;
   @UI(
      readonly = false,
      title = "Заменить на",
      visible = true,
      width = 15
   )
   private String newValue;
   @UI(
      readonly = false,
      title = "Где искать",
      visible = true,
      width = 10
   )
   private String nameTypeProp;

   public SubsMainFeature() {
      this.typeProp = TypeProperty.NAME;
      this.seekPhrase = "";
      this.newValue = "";
      this.nameTypeProp = "";
   }

   public static String getDescriptionClass() {
      return "Замена характеристик";
   }

   public TypeProperty getTypeProp() {
      return this.typeProp;
   }

   public void setTypeProp(TypeProperty typeProp) {
      this.typeProp = typeProp;
   }

   public String getSeekPhrase() {
      return this.seekPhrase;
   }

   public void setSeekPhrase(String seekPhrase) {
      this.seekPhrase = seekPhrase;
   }

   public String getNewValue() {
      return this.newValue;
   }

   public void setNewValue(String newValue) {
      this.newValue = newValue;
   }

   public String getNameTypeProp() {
      this.nameTypeProp = namesTypeProperty[this.typeProp.ordinal()];
      return this.nameTypeProp;
   }

   public void setNameTypeProp(String nameTypeProp) {
      this.nameTypeProp = nameTypeProp;
   }
}
