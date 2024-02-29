package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.perm.v.el59.office.db.CreditBank;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.UserShop;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

public class RewardCreditCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = -7250979760723529503L;
   public List<Shop> shops = new ArrayList();
   public String numberContract = "";
   public List<UserShop> users = new ArrayList();
   public Date fromDate;
   public Date toDate;
   public List<CreditBank> banks = new ArrayList();
}
