package ru.perm.v.el59.office.iproviders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.iproviders.critery.TovarCritery;
import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.dto.TovarDTO;

public interface ITovarProvider extends IGenericDao<Tovar, Integer> {
   List<Tovar> getDublicate(Tovar var1);

   void setDublicate(Integer var1, Integer var2) throws Exception;

   ArrayList<TovarDTO> getDTObyCritery(TovarCritery var1) throws Exception;

   Long getCountByName(String var1);

   Tovar generateNewNnum(Tovar var1);

   List<Tovar> getByNnulTypetovar();

   List<String> getCommets();

   GroupTovar getGroupByTrade(String var1);

   Tovar setCenaSupplier(Tovar var1, BigDecimal var2, Date var3) throws Exception;

   void setDeteted(List<Tovar> var1, boolean var2) throws Exception;
}
