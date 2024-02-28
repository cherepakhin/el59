package ru.perm.v.el59.office.iproviders.critery;

import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TovarBonusCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 8528763323754577819L;
   public String description = "";
   public BigDecimal percent;
   public List<Integer> listNnum;
}
