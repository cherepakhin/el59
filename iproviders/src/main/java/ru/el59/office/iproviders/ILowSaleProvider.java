package ru.el59.office.iproviders;

import java.util.HashMap;
import ru.el59.office.critery.MoveCritery;
import ru.el59.office.report.LowSale;

public interface ILowSaleProvider {
   HashMap<String, LowSale> build(MoveCritery var1);
}
