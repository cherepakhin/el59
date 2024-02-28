package ru.perm.v.el59.office.iproviders.critery;

import ru.perm.v.el59.office.iproviders.dao.CommonCritery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ADocCritery extends CommonCritery implements Serializable {
   public List<Long> listN = new ArrayList();
   public List<Shop> shops = new ArrayList();
   public Date fromdate;
   public Date todate;
   public String numdoc = "";
   public String typedocname = "";
   public TypeDoc typedoc;
   public Manager manager;
   public Long numberReestDoc;
   public Boolean noReestr;
   public Boolean notAgree;
   public Boolean deleted = false;

   public ADocCritery() {
   }

   public ADocCritery(String name) {
      super(name);
   }
}
