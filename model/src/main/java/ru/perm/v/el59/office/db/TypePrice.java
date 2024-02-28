package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.ui.IUIBean;
import ru.perm.v.el59.ui.UI;

public class TypePrice extends AEntity implements Serializable, IUIBean {
   private static final long serialVersionUID = 7835614868504404786L;
   private Long n;
   @UI(
      readonly = true,
      title = "Основной(Да),Вспомогательный(Нет)",
      visible = true
   )
   private Boolean isBase;

   public TypePrice() {
      this.name = "?????";
      this.isBase = false;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Boolean getIsBase() {
      return this.isBase;
   }

   public void setIsBase(Boolean isBase) {
      this.isBase = isBase;
   }

   public static String getDescriptionClass() {
      return "Тип прайса";
   }

   @Override
   public Object getValByField(String s) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      return null;
   }

   @Override
   public Field getFieldByName(String s) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      return null;
   }
}
