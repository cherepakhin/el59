package ru.perm.v.el59.office.iproviders.critery;

import ru.perm.v.el59.office.db.Tovar;

public class CriteryDocitem extends TDocCritery {
   private static final long serialVersionUID = -9019860692511768639L;
   public Tovar tovar;
   public TovarCritery criteryTovar = new TovarCritery();
}
