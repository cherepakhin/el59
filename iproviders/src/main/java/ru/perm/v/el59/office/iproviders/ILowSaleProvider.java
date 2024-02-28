package ru.perm.v.el59.office.iproviders;

import java.util.HashMap;
import ru.perm.v.el59.office.critery.MoveCritery;
import ru.perm.v.el59.office.report.LowSale;

public interface ILowSaleProvider {
   HashMap<String, LowSale> build(MoveCritery var1);
}
