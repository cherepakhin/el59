package ru.perm.v.el59.office.critery;

import java.io.Serializable;
import java.util.Date;
import ru.el59.office.db.service.TDoc;

public class TDocCritery implements Serializable {
   private static final long serialVersionUID = 7834980040424007620L;
   public Date fromdate;
   public Date todate;
   public String typedoc = "";
   public TDoc root;
   public TDoc parent;
}
