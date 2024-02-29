package ru.perm.v.el59.office.dao.impl.web;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.web.CommentDocW;
import ru.perm.v.el59.office.db.web.DocW;
import ru.perm.v.el59.office.iproviders.web.ICommentDocWProvider;

import java.util.List;

public class CommentDocWProvider extends
		GenericDaoHibernateImpl<CommentDocW, Long> implements
		ICommentDocWProvider {
	public CommentDocWProvider(Class<CommentDocW> type) {
		super(type);
	}

	@Override
	public List<CommentDocW> getByDocW(DocW docw) {
		Criteria criteria = getSession().createCriteria(CommentDocW.class);
		criteria.add(Restrictions.eq("docw", docw));
		criteria.addOrder(Order.asc("ddate"));
		@SuppressWarnings("unchecked")
		List<CommentDocW> list = criteria.list();
		return list;
	}

	@Override
	public void delete(CommentDocW o) throws Exception {
		o.setDeleted(true);
		update(o);
	}

	@Override
	public void deleteByDocW(DocW docw) {
		String sql = "update CommentDocW set deleted=true where docw=:docw";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("docw", docw);
		q1.executeUpdate();
	}

}
