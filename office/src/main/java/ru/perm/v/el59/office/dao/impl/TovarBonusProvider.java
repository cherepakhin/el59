package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.Tovar;
import ru.perm.v.el59.office.shopmodel.TovarBonus;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.iproviders.critery.TovarBonusCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.ITovarBonusProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.math.BigDecimal;
import java.util.List;

public class TovarBonusProvider extends GenericDaoMessageImpl<TovarBonus, Long>
		implements ITovarBonusProvider {

	private ITovarProvider tovarProvider;

	public TovarBonusProvider(Class<TovarBonus> type) {
		super(type);
	}

	@Override
	public void deleteList(List<TovarBonus> list) throws Exception {
		for (TovarBonus tovarBonus : list) {
			delete(tovarBonus);
		}

	}

	@Override
	public void create(List<Integer> listNnum, BigDecimal percent) throws Exception {
		for (Integer nnum : listNnum) {
			Tovar t = (Tovar) getTovarProvider().read(nnum);
			TovarBonus tb = new TovarBonus();
			tb.setTovar(t);
			tb.setPercent(percent);
			create(tb);
		}

	}

	@Override
	public List getByCritery(Object critery) {
		TovarBonusCritery c = (TovarBonusCritery) critery;
		Criteria criteria = getSession().createCriteria(TovarBonus.class);
		Criteria criteriaTovar = criteria.createCriteria("tovar");
		if (c.listNnum != null) {
			criteriaTovar.add(Restrictions.in("nnum", c.listNnum));
		}
		if (!c.description.isEmpty()) {
			criteriaTovar.add(Restrictions.like("name", c.description,
					MatchMode.ANYWHERE).ignoreCase());
		}
		List list = criteria.list();
		return list;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

}
