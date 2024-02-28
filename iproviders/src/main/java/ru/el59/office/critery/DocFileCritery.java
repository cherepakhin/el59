package ru.el59.office.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.dao.CommonCritery;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.el59.office.db.TypeFile;

public class DocFileCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 2928305409170385801L;
   public List<Shop> listShop = new ArrayList();
   public List<TypeFile> listTypeFile = new ArrayList();
   public Date fromDate = new Date();
   public Date toDate = new Date();
   public List<Contragent> listContragent = new ArrayList();
   public List<Manager> listManager = new ArrayList();
}
