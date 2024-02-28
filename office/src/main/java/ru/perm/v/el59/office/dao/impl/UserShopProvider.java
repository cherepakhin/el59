package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.office.critery.UserShopCritery;
import ru.el59.office.db.UserShop;
import ru.perm.v.el59..office.iproviders.IShopProvider;
import ru.perm.v.el59..office.iproviders.IUserShopProvider;
import ru.perm.v.el59.dto.office.wscommand.ICommander;

public class UserShopProvider extends GenericDaoHibernateImpl<UserShop, Long>
		implements IUserShopProvider {

	private ICommander commander;
	private IShopProvider shopProvider;
	private Long nNullUserShop = 0L;
	private UserShop nullUserShop;

	public UserShopProvider(Class<UserShop> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserShop> getByCritery(Object critery) {
		UserShopCritery c = (UserShopCritery) critery;
		Criteria userCriteria = getSession().createCriteria(UserShop.class);
		Criteria shopCriteria = userCriteria.createCriteria("shop");
		if (c.getWorked() != null)
			userCriteria.add(Restrictions.eq("worked", c.getWorked()));
		if ((c.getName() != null) && (!c.getName().equals("")))
			userCriteria.add(Restrictions.like("name", c.getName(),
					MatchMode.ANYWHERE).ignoreCase());
		if ((c.getShopname() != null) && (!c.getShopname().equals("")))
			shopCriteria.add(Restrictions.like("name", c.getShopname(),
					MatchMode.ANYWHERE).ignoreCase());
		ArrayList<UserShop> list = (ArrayList<UserShop>) userCriteria.list();
		return list;
	}

	public ICommander getCommander() {
		return commander;
	}

	public void setCommander(ICommander commander) {
		this.commander = commander;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	String getShopCod(Object o) throws Exception {
		UserShop userShop = (UserShop) o;
		String shopCod = userShop.getShop().getCod();
		if (userShop.getShop().equals(getShopProvider().getNullShop())) {
			shopCod = "*";
		}
		return shopCod;
	}

	@Override
	public Long create(UserShop o) throws Exception {
		Long n = super.create(o);
		getCommander().create(o, getShopCod(o));
		return n;
	}

	@Override
	public void delete(UserShop o) throws Exception {
		super.delete(o);
		getCommander().delete(o, getShopCod(o));
	}

	@Override
	public void update(UserShop o) throws Exception {
		super.update(o);
		getCommander().update(o, getShopCod(o));
	}

	public Long getnNullUserShop() {
		return nNullUserShop;
	}

	public void setnNullUserShop(Long nNullUserShop) {
		this.nNullUserShop = nNullUserShop;
	}

	@Override
	public UserShop getNullUserShop() throws Exception {
		if (nullUserShop == null) {
			nullUserShop = read(getnNullUserShop());
		}
		if (nullUserShop == null) {
			throw new Exception("Не установлен параметр сотрудник по умолчанию");
		}
		return nullUserShop;
	}
}
