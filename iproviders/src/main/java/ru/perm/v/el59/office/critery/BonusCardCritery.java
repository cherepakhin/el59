package ru.perm.v.el59.office.critery;

import ru.perm.v.el59.dto.dao.CommonCritery;

import java.io.Serializable;
import java.util.Date;

public class BonusCardCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 1563907843878080891L;
   public String shopname;
   public Shop shop;
   public String name;
   public String stroke;
   public Date fromdate;
   public Date todate;
   public Long n;
   public String shopCod;
   public Long nn;
}
