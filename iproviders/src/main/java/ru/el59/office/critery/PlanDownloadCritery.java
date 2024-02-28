package ru.el59.office.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.dao.CommonCritery;
import ru.el59.office.db.Shop;

public class PlanDownloadCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 717300273004071105L;
   public List<Shop> listShop = new ArrayList();
   public Date ddate = new Date();
}
