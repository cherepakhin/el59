package ru.el59.office.iproviders;

import java.util.ArrayList;
import ru.el59.dao.IGenericDao;
import ru.el59.office.critery.RestCritery;
import ru.el59.office.db.Rest;

public interface IRestProvider extends IGenericDao<Rest, Long> {
   ArrayList<Rest> getOnDate(RestCritery var1);
}
