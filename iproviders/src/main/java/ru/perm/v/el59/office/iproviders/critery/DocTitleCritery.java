package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.el59.office.db.Shop;
import ru.el59.office.shopmodel.DocTitle;
import ru.el59.office.shopmodel.TypeDocShop;
import ru.el59.office.shopmodel.TypeDocStatusShop;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

public class DocTitleCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 1699673651169954021L;
   public List<Shop> shops = new ArrayList();
   public Date fromdate;
   public Date todate;
   public String numdoc;
   public String typedocname;
   public List<TypeDocShop> typedocs = new ArrayList();
   public TypeDocStatusShop typedocstatus;
   public String typedocstatusname = "";
   public Boolean deleted = false;
   public Long nn;
   public DocTitle parent;

   public DocTitleCritery() {
   }

   public DocTitleCritery(String name) {
      super(name);
   }
}
