package ru.perm.v.el59.office.db.dto;

import java.io.Serializable;
import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.Tovar;

public class ChangeGroup implements Serializable {
   private static final long serialVersionUID = -2146146892691520831L;
   public GroupTovar oldBestGroup;
   public GroupTovar newBestGroup;
   public Tovar tovar;
}
