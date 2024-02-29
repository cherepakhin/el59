package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Hibernate;
import ru.perm.v.el59.office.db.SetTypeStock;
import ru.perm.v.el59.office.iproviders.ISetTypeStockProvider;

public class SetTypeStockProvider extends
        GenericDaoHibernateImpl<SetTypeStock, Long> implements
        ISetTypeStockProvider {

    public SetTypeStockProvider(Class<SetTypeStock> type) {
        super(type);
    }

    @Override
    public SetTypeStock initialize(Long id) {
        SetTypeStock o = (SetTypeStock) getSession()
                .get(SetTypeStock.class, id);
        Hibernate.initialize(o.getTypeStocks());
        return o;
    }

}
