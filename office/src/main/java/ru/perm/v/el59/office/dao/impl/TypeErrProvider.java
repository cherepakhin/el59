package ru.perm.v.el59.office.dao.impl;


import ru.perm.v.el59.office.db.TypeErr;

public class TypeErrProvider extends GenericDaoHibernateImpl<TypeErr, Long> {

    public TypeErrProvider(Class<TypeErr> type) {
        super(type);
    }

}
