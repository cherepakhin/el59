package ru.el59.office.critery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.office.db.Contragent;

public class RestSupplierCritery extends TovarCritery {
   private static final long serialVersionUID = 8158774486103806715L;
   public Date ddate;
   public List<Contragent> listContragent = new ArrayList();
}
