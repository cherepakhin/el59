package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

public class HistoryTagCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = -5716521287390019113L;
   public TovarCritery tovarCritery;
   public List<Shop> shops = new ArrayList();
   public Date fromDate;
   public Date toDate;
}
