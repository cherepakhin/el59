package ru.perm.v.el59.office.iproviders.web;

import java.util.ArrayList;
import java.util.List;
import ru.perm.v.el59.office.db.DocItem;
import ru.perm.v.el59.office.db.web.DocW;
import ru.perm.v.el59.office.iproviders.critery.ADocItemCritery;

public class DocWItemCritery extends ADocItemCritery {
   private static final long serialVersionUID = -5867727857722705384L;
   public List<DocW> listDocW = new ArrayList();
   public List<DocItem> listDocItem = new ArrayList();
   public Boolean deleted = false;

   public DocWItemCritery() {
   }

   public DocWItemCritery(String name) {
      super(name);
   }
}
