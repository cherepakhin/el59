package ru.perm.v.el59.office.critery;

import java.util.ArrayList;
import java.util.Date;
import ru.el59.office.db.Shop;

public class CriteryLOPT extends TovarCritery {
   private static final long serialVersionUID = -8174102065880645400L;
   public Date fromDate;
   public Date toDate;
   public String numdoc = "";
   public String clientName = "";
   public Integer riskDays;
   public ArrayList<Shop> arrshops = new ArrayList();
   public Boolean onrest = false;
}
