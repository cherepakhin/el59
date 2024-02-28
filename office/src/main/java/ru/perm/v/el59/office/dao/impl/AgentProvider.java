package ru.perm.v.el59.office.dao.impl;


import ru.el59.dao.IGenericDao;
import ru.el59.office.db.Agent;

public class AgentProvider extends GenericDaoHibernateImpl<Agent, String> implements IGenericDao<Agent, String> {

    public AgentProvider(Class<Agent> type) {
        super(type);
    }

}
