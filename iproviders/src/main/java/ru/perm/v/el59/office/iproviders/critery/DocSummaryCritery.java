package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

public class DocSummaryCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 717300273004071105L;
   public List<Shop> listShop = new ArrayList();
   public Date fromDate = new Date();
   public Date toDate = new Date();
   public List<Manager> listManager = new ArrayList();
}
