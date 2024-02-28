package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.db.SetTovar;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.iproviders.ISetTovarProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class SetTovarProvider extends GenericDaoMessageImpl<SetTovar, Long>
		implements ISetTovarProvider {
	private ITovarProvider tovarProvider;

	public SetTovarProvider(Class<SetTovar> type) {
		super(type);
	}

	public SetTovar load(Long id) {
		SetTovar settovar = (SetTovar) read(id);
		Hibernate.initialize(settovar.getTovars());
		return settovar;
	}

	public List<Tovar> loadByName(String name) {
		Criteria criteria = getSession().createCriteria(SetTovar.class);
		criteria.add(Restrictions.eq("name", name));
		ArrayList<SetTovar> list = (ArrayList<SetTovar>) criteria.list();
		ArrayList<Tovar> ret = new ArrayList<Tovar>();
		if (list.size() > 0) {
			for (SetTovar setTovar : list) {
				SetTovar sgt = load(list.get(0).getN());
				ret.addAll(sgt.getTovars());
			}
		}
		return ret;
	}

	@Override
	public List<SetTovar> getAll() {
		Criteria criteria = getSession().createCriteria(SetTovar.class);
		ArrayList<SetTovar> list = (ArrayList<SetTovar>) criteria.list();
		return list;
	}

	@Override
	public SetTovar initialize(Long id) {
		SetTovar o = (SetTovar) getSession().get(SetTovar.class, id);
		Hibernate.initialize(o.getTovars());
		return o;
	}

	@Override
	public void addTovarNnum(SetTovar selectedSetTovar, Integer nnum)
			throws Exception {
		Tovar tovar = getTovarProvider().read(nnum);
		selectedSetTovar = (SetTovar) initialize(selectedSetTovar.getN());
		addTovar(selectedSetTovar, tovar);
	}

	@Override
	public void removeByNnum(SetTovar setTovar, Integer nnum) throws Exception {
		Tovar tovar = getTovarProvider().read(nnum);
		if (tovar != null) {
			removeTovar(setTovar, tovar);
		}
	}

	@Override
	public void addTovar(SetTovar setTovar, Tovar tovar) throws Exception {
		if (tovar != null) {
			setTovar = (SetTovar) initialize(setTovar.getN());
			setTovar.addTovar(tovar);
			update(setTovar);
		}
	}

	@Override
	public void removeTovar(SetTovar setTovar, Tovar tovar) throws Exception {
		setTovar = (SetTovar) initialize(setTovar.getN());
		setTovar.removeTovar(tovar);
		update(setTovar);
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

}
