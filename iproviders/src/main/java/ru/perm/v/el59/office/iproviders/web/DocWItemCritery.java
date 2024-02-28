package ru.perm.v.el59.office.iproviders.web;

import java.util.ArrayList;
import java.util.List;
import ru.perm.v.el59.office.critery.ADocItemCritery;
import ru.el59.office.db.DocItem;
import ru.el59.office.db.web.DocW;

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
