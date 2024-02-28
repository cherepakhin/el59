package ru.perm.v.el59.office.dao.impl.subs;

import org.hibernate.Query;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.subs.CorrectionName;
import ru.perm.v.el59.office.db.subs.MainFeature;
import ru.perm.v.el59.office.iproviders.subs.ICorrectionNameProvider;

import java.util.List;

public class CorrectionNameProvider extends
		GenericDaoHibernateImpl<CorrectionName, Long> implements
		ICorrectionNameProvider {

	public CorrectionNameProvider(Class<CorrectionName> type) {
		super(type);
	}

	@Override
	public void delete(CorrectionName o) throws Exception {
		o = read(o.getN());
		super.delete(o);
	}

	@Override
	public Long create(CorrectionName o) throws Exception {
		o.setName(o.getName().trim());
		return super.create(o);
	}

	@Override
	public void update(CorrectionName o) throws Exception {
		o.setName(o.getName().trim());
		super.update(o);
	}

	@Override
	public List<String> getPossible(MainFeature mainFeature) {
		String sql = "select distinct f.oldname from TovarInfo ti,RestSupplier r " +
				"inner join ti.listFeature f " +
				"where ti.nnum=r.tovar.nnum and ti.tovar.group=:groupTovar and " +
				"f.oldname not in (" +
				"select name from CorrectionName where mainFeature.groupTovarMainFeature=:groupTovarMainFeature and " +
				"mainFeature!=:mainFeature" +
				") " +
				"order by f.oldname";
		Query q1 = getSession().createQuery(sql );
		q1.setParameter("groupTovar", mainFeature.getGroupTovarMainFeature().getGroupTovar());
		q1.setParameter("groupTovarMainFeature", mainFeature.getGroupTovarMainFeature());
		q1.setParameter("mainFeature", mainFeature);
		List list = q1.list();
		return list;
	}

}