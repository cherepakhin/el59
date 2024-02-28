package ru.perm.v.el59.office.dao.impl.routedoc;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.routedoc.TemplatePathPage;
import ru.perm.v.el59.office.iproviders.IContragentProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IDispatcherProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IDriverProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IMachineProvider;
import ru.perm.v.el59.office.iproviders.routedoc.ITemplatePathPageProvider;

public class TemplatePathPageProvider extends
		GenericDaoHibernateImpl<TemplatePathPage, Long> implements
		ITemplatePathPageProvider {

	private IDispatcherProvider dispatcherProvider;
	private IDriverProvider driverProvider;
	private IMachineProvider machineProvider;
	private IContragentProvider contragentProvider;

	public TemplatePathPageProvider(Class<TemplatePathPage> type) {
		super(type);
	}

	public IDispatcherProvider getDispatcherProvider() {
		return dispatcherProvider;
	}

	public void setDispatcherProvider(IDispatcherProvider dispatcherProvider) {
		this.dispatcherProvider = dispatcherProvider;
	}

	public IDriverProvider getDriverProvider() {
		return driverProvider;
	}

	public void setDriverProvider(IDriverProvider driverProvider) {
		this.driverProvider = driverProvider;
	}

	public IMachineProvider getMachineProvider() {
		return machineProvider;
	}

	public void setMachineProvider(IMachineProvider machineProvider) {
		this.machineProvider = machineProvider;
	}

	public IContragentProvider getContragentProvider() {
		return contragentProvider;
	}

	public void setContragentProvider(IContragentProvider contragentProvider) {
		this.contragentProvider = contragentProvider;
	}

	@Override
	public Long create(TemplatePathPage o) throws Exception {
		if (o.getDispatcher() == null) {
			o.setDispatcher(getDispatcherProvider().getDefaultDispatcher());
		}
		if (o.getDriver() == null) {
			o.setDriver(getDriverProvider().getDefaultDriver());
		}
		if (o.getMachine() == null) {
			o.setMachine(getMachineProvider().getDefaultMachine());
		}
		return super.create(o);
	}

	@Override
	public List<TemplatePathPage> getAll() {
		return getByLikeName("");
	}

	@Override
	public TemplatePathPage initialize(TemplatePathPage _templatePathPage) {
		TemplatePathPage templatePathPage = (TemplatePathPage) super
				.initialize(_templatePathPage.getN());
		if (templatePathPage.getListContragent() != null) {
			Hibernate.initialize(templatePathPage.getListContragent());
		}
		if (templatePathPage.getListShop() != null) {
			Hibernate.initialize(templatePathPage.getListShop());
		}
		return templatePathPage;
	}

	@Override
	public TemplatePathPage addShop(TemplatePathPage templatePathPage, Shop shop)
			throws Exception {
		templatePathPage = initialize(templatePathPage);
		if (!templatePathPage.getListShop().contains(shop)) {
			templatePathPage.getListShop().add(shop);
			update(templatePathPage);
		}
		return templatePathPage;
	}

	@Override
	public TemplatePathPage removeShop(TemplatePathPage templatePathPage,
			Shop shop) throws Exception {
		templatePathPage = initialize(templatePathPage);
		if (templatePathPage.getListShop().contains(shop)) {
			templatePathPage.getListShop().remove(shop);
			update(templatePathPage);
		}
		return templatePathPage;
	}

	@Override
	public TemplatePathPage addContragent(TemplatePathPage templatePathPage,
			Contragent contragent) throws Exception {
		templatePathPage = initialize(templatePathPage);
		if (!templatePathPage.getListContragent().contains(contragent)) {
			templatePathPage.getListContragent().add(contragent);
			update(templatePathPage);
		}
		return templatePathPage;
	}

	@Override
	public TemplatePathPage removeContragent(TemplatePathPage templatePathPage,
			Contragent contragent) throws Exception {
		templatePathPage = initialize(templatePathPage);
		if (templatePathPage.getListContragent().contains(contragent)) {
			templatePathPage.getListContragent().remove(contragent);
			update(templatePathPage);
		}
		return templatePathPage;
	}

	@Override
	public List<TemplatePathPage> getWorked() {
		Criteria criteria = getSession().createCriteria(TemplatePathPage.class);
		criteria.add(Restrictions.eq("worked", true));
		List<TemplatePathPage> list = criteria.list();
		return list;
	}
}
