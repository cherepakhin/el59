package ru.perm.v.el59.office.dao.impl.subs;

import org.hibernate.Query;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.subs.CorrectionVal;
import ru.perm.v.el59.office.db.subs.ValFeature;
import ru.perm.v.el59.office.iproviders.subs.ICorrectionValProvider;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CorrectionValProvider extends
		GenericDaoHibernateImpl<CorrectionVal, Long> implements
		ICorrectionValProvider {

	private ICorrectionValProvider correctionValProvider;

	public CorrectionValProvider(Class<CorrectionVal> type) {
		super(type);
	}

	public ICorrectionValProvider getCorrectionValProvider() {
		return correctionValProvider;
	}

	public void setCorrectionValProvider(
			ICorrectionValProvider correctionValProvider) {
		this.correctionValProvider = correctionValProvider;
	}

	@Override
	public void delete(Set<CorrectionVal> setCorrectionVal) throws Exception {
		Iterator<CorrectionVal> iterator = setCorrectionVal.iterator();
		while (iterator.hasNext()) {
			CorrectionVal correctionVal = iterator.next();
			iterator.remove();
			delete(correctionVal);
		}
	}

	@Override
	public Long create(CorrectionVal o) throws Exception {
		o.setName(o.getName().trim());
		return super.create(o);
	}

	@Override
	public void update(CorrectionVal o) throws Exception {
		o.setName(o.getName().trim());
		super.update(o);
	}

	@Override
	public List<String> getPossible(ValFeature valFeature){
		String sql = "select distinct f.oldval from TovarInfo ti,RestSupplier r " +
				"inner join ti.listFeature f " +
				"where ti.nnum=r.tovar.nnum and ti.tovar.group=:groupTovar and " +
				"f.oldname in (select distinct name from CorrectionName where mainFeature=:mainFeature) and " +
				"f.oldval not in (" +
				"select distinct name from CorrectionVal where valFeature.mainFeature=:mainFeature and valFeature!=:valFeature" +
				") " +
				"order by f.oldval";
		Query q1 = getSession().createQuery(sql );
		q1.setParameter("groupTovar", valFeature.getMainFeature().getGroupTovarMainFeature().getGroupTovar());
		q1.setParameter("mainFeature", valFeature.getMainFeature());
		q1.setParameter("valFeature", valFeature);
		List list = q1.list();
		return list;
	}
	
}