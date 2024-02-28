package ru.perm.v.el59.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UI {
   String title();

   int width() default 10;

   boolean readonly() default false;

   boolean visible();

   boolean complex() default false;

   Justify justify() default Justify.LEFT;
}
