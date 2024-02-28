package ru.perm.v.el59.office.dao.impl.routedoc;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Doc;
import ru.perm.v.el59.office.db.routedoc.RouteJob;
import ru.perm.v.el59.office.iproviders.routedoc.IRouteJobProvider;

public class RouteJobProvider extends GenericDaoHibernateImpl<RouteJob, Long>
		implements IRouteJobProvider {

	public RouteJobProvider(Class<RouteJob> type) {
		super(type);
	}

	@Override
	public void deleteByDoc(Doc doc) throws Exception {

		Criteria criteria = getSession().createCriteria(RouteJob.class);
		Criteria docFileCritery = criteria.createCriteria("doc");
		docFileCritery.add(Restrictions.eq("n", doc.getN()));
		List<RouteJob> list = criteria.list();
		for (RouteJob routeJob : list) {
			routeJob.getPathPage().getListRouteJob().remove(routeJob);
			delete(routeJob);
		}

		String sql = "delete from RouteJob where doc=:doc";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("doc", doc);
		q1.executeUpdate();

		getSession().flush();
		getSession().clear();
	}

}
