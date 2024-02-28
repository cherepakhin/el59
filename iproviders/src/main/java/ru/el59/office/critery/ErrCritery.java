package ru.el59.office.critery;

import java.io.Serializable;
import java.util.Date;

public class ErrCritery implements Serializable {
   private static final long serialVersionUID = -1108458760822550458L;
   public Date fromdate;
   public Date todate;
   public String shop = "";
   public String typeerr = "";
   public String description = "";
   public String sortOrder = "ddate";
}
