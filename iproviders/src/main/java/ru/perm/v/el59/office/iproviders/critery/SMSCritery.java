package ru.perm.v.el59.office.iproviders.critery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

public class SMSCritery extends CommonCritery {
   private static final long serialVersionUID = 7459679388709179184L;
   public Date fromDate;
   public Date toDate;
   public String message = "";
   public String phone = "";
   public List<Shop> listShop = new ArrayList();
}
