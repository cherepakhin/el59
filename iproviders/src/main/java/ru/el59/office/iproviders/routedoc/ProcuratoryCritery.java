package ru.el59.office.iproviders.routedoc;

import java.io.Serializable;
import java.util.Date;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Shop;

public class ProcuratoryCritery implements Serializable {
   private static final long serialVersionUID = -8934247956225585805L;
   public Date fromDate;
   public Date toDate;
   public Contragent supplier;
   public Shop shop;
   public Contragent person;
   public Integer numdoc;
}
