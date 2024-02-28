package ru.perm.v.el59.office.iproviders.shopmodel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import ru.el59.dao.IGenericDao;
import ru.perm.v.el59.office.critery.PaymentCritery;
import ru.el59.office.db.Shop;
import ru.el59.office.db.web.DocW;
import ru.el59.office.shopmodel.Payment;

public interface IPaymentProvider extends IGenericDao {
   Serializable getMax();

   BigDecimal getSumPay(DocW var1);

   List<Payment> getListPayDocW(DocW var1);

   Map<Shop, BigDecimal> getSumPaymentReal(PaymentCritery var1);
}
