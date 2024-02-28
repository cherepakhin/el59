package ru.perm.v.el59.office.dao.impl.analise;

import ru.el59.office.critery.LinesCritery;
import ru.el59.office.iproviders.analise.Lines;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;

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