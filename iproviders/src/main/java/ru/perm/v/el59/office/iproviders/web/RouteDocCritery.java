package ru.perm.v.el59.office.iproviders.web;

import java.io.Serializable;
import java.util.Date;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Shop;

public class RouteDocCritery implements Serializable {
   private static final long serialVersionUID = -6407392897193553309L;
   public Date fromDate;
   public Date toDate;
   public Contragent supplier;
   public Shop shop;
}
