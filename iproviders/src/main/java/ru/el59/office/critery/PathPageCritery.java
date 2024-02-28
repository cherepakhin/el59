package ru.el59.office.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.dao.CommonCritery;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Shop;
import ru.el59.office.db.routedoc.Dispatcher;
import ru.el59.office.db.routedoc.Driver;
import ru.el59.office.db.routedoc.Machine;

public class PathPageCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 911519895769664483L;
   public Date fromDate;
   public Date toDate;
   public Boolean closed;
   public List<Contragent> listContragentFromTemplate = new ArrayList();
   public List<Shop> listShopFromTemplate = new ArrayList();
   public List<Machine> listMachine = new ArrayList();
   public List<Driver> listDriver = new ArrayList();
   public List<Dispatcher> listDispatcher = new ArrayList();
}
