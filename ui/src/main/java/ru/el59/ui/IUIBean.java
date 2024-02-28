package ru.el59.ui;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public interface IUIBean {
   Object getValByField(String var1) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

   Field getFieldByName(String var1) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
