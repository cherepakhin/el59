package ru.perm.v.el59.office.dao.impl.routedoc;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.critery.DocCritery;
import ru.perm.v.el59.office.critery.PathPageCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Doc;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.routedoc.PathPage;
import ru.perm.v.el59.office.db.routedoc.RouteJob;
import ru.perm.v.el59.office.db.routedoc.TemplatePathPage;
import ru.perm.v.el59.office.iproviders.IContragentProvider;
import ru.perm.v.el59.office.iproviders.IDocProvider;
import ru.perm.v.el59.office.iproviders.routedoc.*;
import ru.perm.v.el59.office.util.Helper;

import java.util.*;

public class PathPageProvider extends GenericDaoHibernateImpl<PathPage, Long>
		implements IPathPageProvider {

	private IDocProvider docProvider;
	private IDispatcherProvider dispatcherProvider;
	private IDriverProvider driverProvider;
	private IMachineProvider machineProvider;
	private IContragentProvider contragentProvider;
	private IRouteJobProvider routeJobProvider;
	private ITemplatePathPageProvider templatePathPageProvider;
	private RouteJobComparator routeJobComparator;

	public PathPageProvider(Class<PathPage> type) {
		super(type);
	}

	@Override
	public List<PathPage> getByCritery(Object critery) {
		PathPageCritery c = (PathPageCritery) critery;
		Criteria criteria = getSession().createCriteria(PathPage.class, "p");
		if (c.fromDate != null && c.toDate != null) {
			criteria.add(Restrictions.ge("p.ddate", c.fromDate));
			criteria.add(Restrictions.le("p.ddate", c.toDate));
		}
		if (c.closed != null) {
			criteria.add(Restrictions.le("p.closed", c.closed));
		}
		if (c.listDriver.size() > 0) {
			Criteria driverCritery = criteria.createCriteria("driver");
			driverCritery.add(Restrictions.in("p.driver", c.listDriver));
		}
		if (c.listMachine.size() > 0) {
			Criteria machineCritery = criteria.createCriteria("machine");
			machineCritery.add(Restrictions.in("p.machine", c.listMachine));
		}
		if (c.listDispatcher.size() > 0) {
			Criteria dispatcherCritery = criteria.createCriteria("dispatcher");
			dispatcherCritery.add(Restrictions.in("p.dispatcher",
					c.listDispatcher));
		}
		if (c.listContragentFromTemplate.size() > 0
				|| c.listShopFromTemplate.size() > 0) {
			Criteria templateCritery = criteria.createCriteria(
					"templatePathPage", "t");
			if (c.listContragentFromTemplate.size() > 0) {
				Criteria listContragentCritery = templateCritery
						.createCriteria("listContragent");
				ArrayList<Long> list_n = new ArrayList<Long>();
				for (Contragent contragent : c.listContragentFromTemplate) {
					list_n.add(contragent.getN());
				}
				listContragentCritery.add(Restrictions.in("n", list_n));
			}
			if (c.listShopFromTemplate.size() > 0) {
				Criteria listShopCritery = templateCritery
						.createCriteria("listShop");
				ArrayList<String> list_n = new ArrayList<String>();
				for (Shop shop : c.listShopFromTemplate) {
					list_n.add(shop.getCod());
				}
				listShopCritery.add(Restrictions.in("cod", list_n));
			}
		}
		criteria.addOrder(Order.asc("p.ddate"));
		ArrayList<PathPage> list = (ArrayList<PathPage>) criteria.list();
		return list;
	}

	@Override
	public List<PathPage> getInPeriod(Date fromDate, Date toDate) {
		Criteria criteria = getSession().createCriteria(PathPage.class);
		if (fromDate != null && toDate != null) {
			criteria.add(Restrictions.ge("ddate", fromDate));
			criteria.add(Restrictions.le("ddate", toDate));
		}
		criteria.addOrder(Order.asc("n"));
		List<PathPage> ret = criteria.list();
		return ret;
	}

	@Override
	public PathPage deleteRouteJobFromPathPage(RouteJob routeJob,
			PathPage pathPage) throws Exception {
		pathPage = initialize(pathPage.getN());
		ArrayList<RouteJob> listForDelete = new ArrayList<RouteJob>();
		/*
		 * if(pathPage.getListRouteJob().contains(routeJob)) {
		 * listForDelete.add(routeJob); }
		 */
		for (RouteJob _routeJob : pathPage.getListRouteJob()) {
			if (_routeJob.getDoc().equals(routeJob.getDoc())) {
				listForDelete.add(_routeJob);
			}
		}
		for (RouteJob routeJob2 : listForDelete) {
			pathPage.getListRouteJob().remove(routeJob2);
		}
		update(pathPage);
		for (RouteJob routeJob2 : listForDelete) {
			getRouteJobProvider().delete(routeJob2);
		}
		return pathPage;
	}

	@Override
	public PathPage addRouteJobToPathPage(RouteJob routeJob, PathPage pathPage)
			throws Exception {
		pathPage = initialize(pathPage.getN());
		routeJob.setPathPage(pathPage);
		getRouteJobProvider().update(routeJob);
		pathPage.getListRouteJob().add(routeJob);
		update(pathPage);
		return pathPage;
	}

	public PathPage generate(List<Doc> listDoc, Manager manager)
			throws Exception {
		PathPage pathPage = new PathPage();
		pathPage.setManager(manager);
		pathPage.setDispatcher(getDispatcherProvider().getDefaultDispatcher());
		pathPage.setMachine(getMachineProvider().getDefaultMachine());
		pathPage.setDriver(getDriverProvider().getDefaultDriver());
		Long n = create(pathPage);
		pathPage.setN(n);
		for (Doc doc : listDoc) {
			List<RouteJob> listRouteJob = createRouteJob(pathPage, doc);
			pathPage.getListRouteJob().addAll(listRouteJob);
		}
		update(pathPage);
		return pathPage;
	}

	private List<RouteJob> createRouteJob(PathPage pathPage, Doc doc)
			throws Exception {
		// Погрузка товара
		ArrayList<RouteJob> ret = new ArrayList<RouteJob>();
		RouteJob routeJob = new RouteJob();
		routeJob.setPathPage(pathPage);
		routeJob.setDoc(doc);
		routeJob.setTimeArrival(Helper.getNullDate());
		routeJob.setTimeDeparture(Helper.getNullDate());
		routeJob.setTargetContragent(doc.getContragent());
		Contragent c = getContragentProvider().getByEqName(
				doc.getShop().getFullname());
		routeJob.setOtherContragent(c);
		routeJob.setLoading(true);
		getRouteJobProvider().create(routeJob);
		ret.add(routeJob);
		getSession().flush();
		// Выгрузка товара
		routeJob = new RouteJob();
		routeJob.setPathPage(pathPage);
		routeJob.setDoc(doc);
		routeJob.setTimeArrival(Helper.getNullDate());
		routeJob.setTimeDeparture(Helper.getNullDate());
		routeJob.setTargetContragent(c);
		routeJob.setOtherContragent(doc.getContragent());
		routeJob.setLoading(false);
		getRouteJobProvider().create(routeJob);
		ret.add(routeJob);
		getSession().flush();
		return ret;
	}

	@Override
	public List<RouteJob> getRouteJob(PathPage pathPage) {
		pathPage = initialize(pathPage.getN());
		return pathPage.getListRouteJob();
	}

	@Override
	public PathPage createFromListDoc(List<Doc> listDoc, Manager manager)
			throws Exception {
		PathPage pathPage = generate(listDoc, manager);
		return pathPage;
	}

	@Override
	public PathPage addDoc(List<Doc> listDoc, PathPage pathPage)
			throws Exception {
		pathPage = initialize(pathPage.getN());
		for (Doc doc : listDoc) {
			List<RouteJob> listRouteJob = createRouteJob(pathPage, doc);
			pathPage.getListRouteJob().addAll(listRouteJob);
		}
		update(pathPage);
		return pathPage;
	}

	@Override
	public PathPage initialize(Long id) {
		PathPage pathPage = (PathPage) super.initialize(id);
		if (pathPage.getListRouteJob() != null) {
			Hibernate.initialize(pathPage.getListRouteJob());
		}
		return pathPage;
	}

	@Override
	public void up(RouteJob routeJob) throws Exception {
		// Убрать constraint в pathpage_routejob
		PathPage pathPage = routeJob.getPathPage();
		int i = pathPage.getListRouteJob().indexOf(routeJob);
		pathPage.getListRouteJob().remove(routeJob);
		pathPage.getListRouteJob().add(i - 1, routeJob);
		update(pathPage);
	}

	@Override
	public void down(RouteJob routeJob) throws Exception {
		// Убрать constraint в pathpage_routejob
		PathPage pathPage = initialize(routeJob.getPathPage().getN());
		int i = pathPage.getListRouteJob().indexOf(routeJob);
		pathPage.getListRouteJob().remove(routeJob);
		pathPage.getListRouteJob().add(i + 1, routeJob);
		update(pathPage);
	}

	@Override
	public void createByTemplateWorked(Manager manager) throws Exception {
		List<TemplatePathPage> listTemplate = getTemplatePathPageProvider()
				.getWorked();
		for (TemplatePathPage templatePathPage : listTemplate) {
			Calendar cal = Calendar.getInstance();

			int dayOfWeek = 6 - (8 - new GregorianCalendar()
					.get(Calendar.DAY_OF_WEEK)) % 7;
			if (templatePathPage.getDayOfWeek() > dayOfWeek) {
				cal.add(Calendar.DAY_OF_YEAR, templatePathPage.getDayOfWeek()
						- dayOfWeek);
			} else {
				int nextDayOfWeek = 7 + templatePathPage.getDayOfWeek();
				cal.add(Calendar.DAY_OF_YEAR, nextDayOfWeek - dayOfWeek);
			}
			PathPage p = createByTemplate(templatePathPage, manager);
			p.setDdate(cal.getTime());
			update(p);
		}
		// Добавление документов, ктр. еще нет в путевых листах, во вновь
		// созданные путевые листы
		// 12.08.2013 начало работы с путевыми листами
		DocCritery critery = new DocCritery();
		Calendar calFromDate = Calendar.getInstance();
		calFromDate.set(Calendar.YEAR, 2013);
		calFromDate.set(Calendar.MONTH, 7);
		calFromDate.set(Calendar.DAY_OF_MONTH, 19);
		critery.fromdate = calFromDate.getTime();

		Calendar calToDate = Calendar.getInstance();
		calToDate.set(Calendar.YEAR, 2113);
		calToDate.set(Calendar.MONTH, 7);
		calToDate.set(Calendar.DAY_OF_MONTH, 19);
		critery.todate = calToDate.getTime();
		List<Doc> listDoc = getDocProvider().getFreeForPathPage(critery);
		for (Doc doc : listDoc) {
			createInCurrentPathPage(doc);
		}
	}

	@Override
	public PathPage createByTemplate(TemplatePathPage templatePathPage,
			Manager manager) throws Exception {
		PathPage p = new PathPage();
		p.setDriver(templatePathPage.getDriver());
		p.setDispatcher(templatePathPage.getDispatcher());
		p.setMachine(templatePathPage.getMachine());
		p.setManager(manager);
		p.setTemplatePathPage(templatePathPage);
		p.setName(templatePathPage.getName());
		create(p);
		return p;
	}

	@Override
	public void createInCurrentPathPage(Doc doc) throws Exception {
		PathPageCritery c = new PathPageCritery();
		/*
		 * c.listContragentFromTemplate.add(docFile.getDoc().getContragent());
		 * c.listShopFromTemplate.add(docFile.getDoc().getShop());
		 */
		c.fromDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 200);
		c.toDate = cal.getTime();
		c.closed = false;
		List<PathPage> list = getByCritery(c);
		if (list.size() > 0) {
			ArrayList<PathPage> list1 = new ArrayList<PathPage>();
			for (PathPage pathPage : list) {
				if (pathPage.getTemplatePathPage().getListContragent().size() == 0
						|| pathPage.getTemplatePathPage().getListContragent()
								.contains(doc.getContragent())) {
					list1.add(pathPage);
				}
			}

			ArrayList<PathPage> list2 = new ArrayList<PathPage>();
			for (PathPage pathPage : list1) {
				if (pathPage.getTemplatePathPage().getListShop().size() == 0
						|| pathPage.getTemplatePathPage().getListShop()
								.contains(doc.getShop())) {
					list2.add(pathPage);
				}
			}

			if (list2.size() > 0) {
				PathPage pathPage = list2.get(0);
				List<RouteJob> listRouteJob = createRouteJob(pathPage, doc);
				pathPage.getListRouteJob().addAll(listRouteJob);
				update(pathPage);
			}
		}
	}

	public IDocProvider getDocProvider() {
		return docProvider;
	}

	public void setDocProvider(IDocProvider docProvider) {
		this.docProvider = docProvider;
	}

	public IDispatcherProvider getDispatcherProvider() {
		return dispatcherProvider;
	}

	public void setDispatcherProvider(IDispatcherProvider dispatcherProvider) {
		this.dispatcherProvider = dispatcherProvider;
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

	public IDriverProvider getDriverProvider() {
		return driverProvider;
	}

	public void setDriverProvider(IDriverProvider driverProvider) {
		this.driverProvider = driverProvider;
	}

	public IRouteJobProvider getRouteJobProvider() {
		return routeJobProvider;
	}

	public void setRouteJobProvider(IRouteJobProvider routeJobProvider) {
		this.routeJobProvider = routeJobProvider;
	}

	public ITemplatePathPageProvider getTemplatePathPageProvider() {
		return templatePathPageProvider;
	}

	public void setTemplatePathPageProvider(
			ITemplatePathPageProvider templatePathPageProvider) {
		this.templatePathPageProvider = templatePathPageProvider;
	}

	@Override
	public void delete(Doc doc) throws Exception {
		getRouteJobProvider().deleteByDoc(doc);
	}

	public RouteJobComparator getRouteJobComparator() {
		return routeJobComparator;
	}

	public void setRouteJobComparator(RouteJobComparator routeJobComparator) {
		this.routeJobComparator = routeJobComparator;
	}

	@Override
	public void update(PathPage o) throws Exception {
		// Hibernate.initialize(o.getListRouteJob());
		if (o.getListRouteJob() != null && o.getListRouteJob().size() > 0) {
			// Hibernate.initialize(o.getListRouteJob());
			Collections.sort(o.getListRouteJob(), getRouteJobComparator());
		}
		super.update(o);
		getSession().flush();
		getSession().clear();
		/*
		 * getSession().saveOrUpdate(o); getSession().flush(); //
		 * getSession().clear(); getSession().getTransaction().commit();
		 * getSession().getTransaction().begin(); // Сортировка
		 * o=initialize(o.getN()); Collections.sort(o.getListRouteJob(),
		 * getRouteJobComparator()); String sql =
		 * "update pathpage_routejob set position=:n where pathpage_n=:p and listroutejob_n=:r"
		 * ; SQLQuery q = getSession().createSQLQuery(sql); for (int
		 * i=0;i<o.getListRouteJob().size();i++) { RouteJob r
		 * =o.getListRouteJob().get(i); q.setParameter("n", i+1);
		 * q.setParameter("p", o.getN()); q.setParameter("r", r.getN());
		 * q.executeUpdate(); }
		 */
		// getSession().getTransaction().commit();
	}

	@Override
	public boolean checkExist(PathPage pathPage) {
		PathPageCritery c = new PathPageCritery();
		c.listDispatcher.add(pathPage.getDispatcher());
		c.listMachine.add(pathPage.getMachine());
		c.listDriver.add(pathPage.getDriver());
		c.fromDate = pathPage.getDdate();
		c.toDate = pathPage.getDdate();
		List<PathPage> list = getByCritery(c);
		return list.size() > 0;
	}
}
