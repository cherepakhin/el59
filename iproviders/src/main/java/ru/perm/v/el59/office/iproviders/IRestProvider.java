package ru.perm.v.el59.office.iproviders;

import java.util.ArrayList;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.iproviders.critery.RestCritery;
import ru.perm.v.el59.office.db.Rest;

public interface IRestProvider extends IGenericDao<Rest, Long> {
   ArrayList<Rest> getOnDate(RestCritery var1);
}
