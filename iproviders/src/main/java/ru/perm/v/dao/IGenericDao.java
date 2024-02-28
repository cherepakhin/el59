package ru.perm.v.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T, PK extends Serializable> {
   PK create(T var1) throws Exception;

   T read(PK var1);

   void update(T var1) throws Exception;

   void delete(T var1) throws Exception;

   List<T> getByCritery(Object var1);

   T initialize(PK var1);

   T getByEqName(String var1);

   List<T> getByLikeName(String var1);

   Class<T> getType();

   PK getMax();
}
