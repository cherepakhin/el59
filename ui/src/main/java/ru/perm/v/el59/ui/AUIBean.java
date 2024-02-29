package ru.perm.v.el59.ui;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.PropertyUtils;

public abstract class AUIBean implements IUIBean {
   public Long n = -1L;
   public  String name = "";
   private static Character delimeter = '.';

   public Long getN() {
      return n;
   }

   public void setN(Long n) {
      this.n = n;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public static Character getDelimeter() {
      return delimeter;
   }

   public static void setDelimeter(Character delimeter) {
      AUIBean.delimeter = delimeter;
   }

   public Object getValByField(String fieldname) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      return this.getValByField(this, fieldname);
   }

   public Object getValByField(Object obj, String fieldname) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      if (fieldname.contains(".")) {
         if (obj == null) {
            return "";
         } else {
            Object f = "";

            try {
               f = PropertyUtils.getNestedProperty(obj, fieldname);
            } catch (Exception var5) {
               var5.printStackTrace();
            }

            return f == null ? "" : f;
         }
      } else {
         return PropertyUtils.getSimpleProperty(obj, fieldname);
      }
   }

   public Field getFieldByName(String fieldname) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      return this.getFieldByName(this, fieldname);
   }

   public Field getFieldByName(Object obj, String fieldname) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      if (fieldname.contains(".")) {
         int index = fieldname.indexOf(delimeter);
         Object o = this.getValByField(obj, fieldname.substring(0, index));
         fieldname = fieldname.substring(index + 1);
         Field f = this.getFieldByName(o, fieldname);
         return f;
      } else {
         return obj == null ? null : this.getField(obj, fieldname);
      }
   }

   private Field getField(Object o, String fieldname) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
      Class clazz = o.getClass();

      Field field;
      try {
         field = clazz.getDeclaredField(fieldname);
      } catch (Exception var8) {
         try {
            field = clazz.getSuperclass().getDeclaredField(fieldname);
         } catch (Exception var7) {
            field = clazz.getSuperclass().getSuperclass().getDeclaredField(fieldname);
         }
      }

      field.setAccessible(true);
      return field;
   }
}
