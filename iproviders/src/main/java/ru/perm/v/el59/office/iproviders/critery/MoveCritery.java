package ru.perm.v.el59.office.iproviders.critery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.office.db.OpGroup;
import ru.el59.office.db.TypeStock;

public class MoveCritery extends TovarCritery {
   private static final long serialVersionUID = -6434279866207644627L;
   public Date fromDate = new Date();
   public Date toDate = new Date();
   public ArrayList<OpGroup> arrOpgroup = new ArrayList();
   public String numdoc = "";
   public BigDecimal qty;
   public Integer fromstock_n;
   public String vid;
   public List<TypeStock> arrtypestock = new ArrayList();
   public String seller;
   public String bonusk;
   public ArrayList<String> excludeSeller = new ArrayList();
}
