package ru.perm.v.el59.office.iproviders.web;

import java.util.Date;
import ru.perm.v.el59.office.critery.ADocCritery;
import ru.el59.office.db.Contragent;

public class DocWCritery extends ADocCritery {
   private static final long serialVersionUID = 3986928936573894055L;
   public Contragent contragent;
   public Boolean noRouteDoc;
   public Boolean noOrder;
   public Date fromDateOut;
   public Date toDateOut;
}
