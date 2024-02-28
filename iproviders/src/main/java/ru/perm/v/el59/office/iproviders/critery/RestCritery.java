package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import ru.el59.office.db.TypeStock;

public class RestCritery implements Serializable {
   private static final long serialVersionUID = -2516739392931586433L;
   public Date ddate;
   public ArrayList<TypeStock> arrtypestock = new ArrayList();
   public TovarCritery tovarCritery = new TovarCritery();
   public Boolean insertPrice = false;
}
