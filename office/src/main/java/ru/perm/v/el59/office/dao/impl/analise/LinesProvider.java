package ru.perm.v.el59.office.dao.impl.analise;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.iproviders.analise.Lines;

import java.util.List;

public class LinesProvider extends GenericDaoHibernateImpl<Lines, Long> {

    public LinesProvider(Class<Lines> type) {
        super(type);
    }

    @Override
    public List<Lines> getByCritery(Object critery) {
            return super.getByCritery(critery);
    }
}