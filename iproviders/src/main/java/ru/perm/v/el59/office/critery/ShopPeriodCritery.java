package ru.perm.v.el59.office.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.el59.office.db.Shop;
import ru.perm.v.el59.office.dao.CommonCritery;

public class ShopPeriodCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 3937714725308020180L;
   public Date fromdate;
   public Date todate;
   public List<Shop> shops = new ArrayList();
   public Boolean check;
}