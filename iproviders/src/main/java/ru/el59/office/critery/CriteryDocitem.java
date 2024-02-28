package ru.el59.office.critery;

import ru.el59.office.db.Tovar;

public class CriteryDocitem extends TDocCritery {
   private static final long serialVersionUID = -9019860692511768639L;
   public Tovar tovar;
   public TovarCritery criteryTovar = new TovarCritery();
}
