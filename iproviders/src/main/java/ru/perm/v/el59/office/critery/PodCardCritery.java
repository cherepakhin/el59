package ru.perm.v.el59.office.critery;

import java.io.Serializable;
import java.util.Date;

import ru.el59.office.db.Shop;
import ru.perm.v.el59.dto.dao.CommonCritery;

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
