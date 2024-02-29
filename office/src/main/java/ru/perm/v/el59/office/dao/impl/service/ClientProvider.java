package ru.perm.v.el59.office.dao.impl.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.critery.ClientCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.Client;
import ru.perm.v.el59.office.iproviders.service.IClientProvider;

import java.util.ArrayList;
import java.util.List;

public class ClientProvider extends GenericDaoHibernateImpl<Client, Long> implements IClientProvider{

	public ClientProvider(Class<Client> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getByCritery(Object critery) {
		ClientCritery client = (ClientCritery) critery;
		Criteria criteria = getSession().createCriteria(Client.class);
		if (!client.getName().equals(""))
			criteria.add(Restrictions.like("name", client.getName(),
					MatchMode.ANYWHERE));
		if (!client.address.equals(""))
			criteria.add(Restrictions.like("address", client.address,
					MatchMode.ANYWHERE));
		if (!client.phone.equals(""))
			criteria.add(Restrictions.like("phone", client.phone,
					MatchMode.ANYWHERE));
		ArrayList<Client> list = (ArrayList<Client>) criteria.list();
		return list;
	}
}
