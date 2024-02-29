package ru.perm.v.el59.office.iproviders.critery;

import java.io.Serializable;
import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.Shop;

public class LinesCritery implements Serializable {
   private static final long serialVersionUID = 5877893673709390507L;
   public GroupTovar group;
   public Shop shop;
}
