package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.Date;

import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

public class PodCardCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = -6661087615352012200L;
   public String shopname;
   public Shop shop;
   public String name;
   public String stroke;
   public Date fromdate;
   public Date todate;
   public Long n;
}
