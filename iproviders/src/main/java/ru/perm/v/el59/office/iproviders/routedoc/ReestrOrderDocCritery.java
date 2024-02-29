package ru.perm.v.el59.office.iproviders.routedoc;

import java.io.Serializable;
import java.util.Date;
import ru.perm.v.el59.office.db.Manager;

public class ReestrOrderDocCritery implements Serializable {
   private static final long serialVersionUID = -4362468281499900718L;
   public Date fromDate;
   public Date toDate;
   public Boolean agreed;
   private Boolean paid;
   public Manager manager;
}
