package ru.el59.office.critery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import ru.el59.dao.CommonCritery;

public class TovarBonusCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 8528763323754577819L;
   public String description = "";
   public BigDecimal percent;
   public List<Integer> listNnum;
}
