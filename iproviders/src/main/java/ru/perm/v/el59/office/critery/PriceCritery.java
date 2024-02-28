package ru.perm.v.el59.office.critery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PriceCritery implements Serializable {
   private static final long serialVersionUID = 8397993427963227678L;
   public TovarCritery tovarCritery;
   public String namePriceType;
   public Date fromDdateChange;
   public Date toDdateChange;
   public BigDecimal cena;
   public String supplier = "";
}
