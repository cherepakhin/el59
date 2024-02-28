package ru.perm.v.el59.office.dao.impl.shopmodel;

import java.util.List;

import org.hibernate.Hibernate;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.db.Dolgnost;
import ru.perm.v.el59.office.iproviders.IDolgnostProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class DolgnostProvider extends GenericDaoMessageImpl<Dolgnost, Long>
		implements IDolgnostProvider {

	public DolgnostProvider(Class<Dolgnost> type) {
		super(type);
	}

	@Override
	public List<Dolgnost> getAll() {
		List<Dolgnost> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

	@Override
	public Dolgnost initialize(Long id) {
		Dolgnost o = (Dolgnost) getSession().get(Dolgnost.class, id);
		Hibernate.initialize(o.getListShopRight());
		return o;
	}

}
