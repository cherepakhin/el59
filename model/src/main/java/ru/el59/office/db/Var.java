package ru.el59.office.db;

import ru.el59.dao.AEntity;
import ru.el59.ui.UI;

public class Var extends AEntity {
   private static final long serialVersionUID = 8258005915750592417L;
   @UI(
      readonly = false,
      title = "Значение",
      visible = true,
      width = 10
   )
   private String val;
   @UI(
      readonly = false,
      title = "Описание",
      visible = true,
      width = 200
   )
   private String info = "";
   @UI(
      readonly = false,
      title = "Тип",
      visible = true,
      width = 10
   )
   private TypeVar typevar;

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getVal() {
      return this.val;
   }

   public void setVal(String val) {
      this.val = val;
   }

   public Var() {
      this.typevar = TypeVar.T_STRING;
      this.name = "";
      this.val = "";
   }

   public Long getN() {
      return this.n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public static String getDescriptionClass() {
      return "Переменные";
   }

   public String getInfo() {
      return this.info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public TypeVar getTypevar() {
      return this.typevar;
   }

   public void setTypevar(TypeVar typevar) {
      this.typevar = typevar;
   }
}
