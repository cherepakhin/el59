package ru.el59.office.critery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.dao.CommonCritery;
import ru.el59.office.db.Shop;

public class SMSCritery extends CommonCritery {
   private static final long serialVersionUID = 7459679388709179184L;
   public Date fromDate;
   public Date toDate;
   public String message = "";
   public String phone = "";
   public List<Shop> listShop = new ArrayList();
}
