package ru.perm.v.el59.office.iproviders.routedoc;

import java.io.Serializable;
import java.util.Date;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;

public class OrderDocCritery implements Serializable {
   private static final long serialVersionUID = -7150149844944631722L;
   public Date fromDate;
   public Date toDate;
   public Contragent supplier;
   public Shop shop;
   public Manager manager;
   public Long numberReestDoc;
   public Boolean noReestr;
   public Boolean notAgree;
}
