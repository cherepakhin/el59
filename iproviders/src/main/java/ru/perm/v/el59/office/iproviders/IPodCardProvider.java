package ru.perm.v.el59.office.iproviders;

import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.shopmodel.PodCard;

import java.util.Date;
import java.util.List;

public interface IPodCardProvider {
    List<PodCard> getByCritery(Object critery);
    void create(Shop shop, Date ddate, Integer qty, String prefixPodCard) throws Exception;
    PodCard getByStroke(String stroke);
}
