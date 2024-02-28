package ru.el59.office.critery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.office.db.Shop;
import ru.el59.office.shopmodel.DocTitle;

public class PaymentCritery implements Serializable {
   private static final long serialVersionUID = 6091672362386191786L;
   public DocTitle docTitle;
   public Date fromdate;
   public Date todate;
   public List<String> listTypePayment = new ArrayList();
   public List<String> listExpense = new ArrayList();
   public List<String> listReason = new ArrayList();
   public List<String> listTypeCash = new ArrayList();
   public List<Shop> listShop = new ArrayList();
   public Long nn;
   public boolean deleted = false;
}
