package ru.el59.office.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.office.db.GroupT;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.SetTovar;
import ru.el59.office.db.Shop;

public class TovarCritery implements Serializable {
   private static final long serialVersionUID = -8588536471720217323L;
   public List<GroupTovar> groups = new ArrayList();
   public List<GroupT> listGroupt = new ArrayList();
   public List<Integer> nnum = new ArrayList();
   public List<Integer> excludennum = new ArrayList();
   public List<SetTovar> listIncludeSetTovar = new ArrayList();
   public List<SetTovar> listExcludeSetTovar = new ArrayList();
   public String name = "";
   public Integer category;
   public String abcd = "";
   public Integer matrix;
   public Boolean isRest = false;
   public Date fromDateInsert;
   public Date toDateInsert;
   public Date fromDateChenged;
   public Date toDateChenged;
   public Boolean checked;
   public Boolean deleted;
   public Integer oldnnum;
   public ArrayList<String> sort = new ArrayList();
   public Integer typetovar;
   public List<Shop> arrshops = new ArrayList();
   public String comment;
   public boolean withoutDuplicates = true;
   public Integer qtyPhoto;

   public void clone(TovarCritery c) {
      this.nnum = c.nnum;
      this.excludennum = c.excludennum;
      this.listIncludeSetTovar = c.listIncludeSetTovar;
      this.listExcludeSetTovar = c.listExcludeSetTovar;
      this.name = c.name;
      this.category = c.category;
      this.abcd = c.abcd;
      this.matrix = 5;
      this.fromDateInsert = c.fromDateInsert;
      this.toDateInsert = c.toDateInsert;
      this.checked = c.checked;
      this.oldnnum = c.oldnnum;
      this.groups = c.groups;
      this.isRest = c.isRest;
      this.sort = c.sort;
      this.comment = c.comment;
      this.withoutDuplicates = c.withoutDuplicates;
      this.fromDateChenged = c.fromDateChenged;
      this.toDateChenged = c.toDateChenged;
      this.qtyPhoto = c.qtyPhoto;
   }
}
