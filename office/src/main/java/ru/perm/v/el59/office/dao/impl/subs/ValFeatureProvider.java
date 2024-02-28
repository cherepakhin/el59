package ru.perm.v.el59.office.dao.impl.subs;

import java.util.Iterator;

import org.hibernate.Hibernate;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.subs.CorrectionVal;
import ru.perm.v.el59.office.db.subs.ValFeature;
import ru.perm.v.el59.office.iproviders.subs.ICorrectionValProvider;
import ru.perm.v.el59.office.iproviders.subs.IValFeatureProvider;

public class ValFeatureProvider extends
		GenericDaoHibernateImpl<ValFeature, Long> implements
		IValFeatureProvider {

	private ICorrectionValProvider correctionValProvider;

	public ValFeatureProvider(Class<ValFeature> type) {
		super(type);
	}

	public CorrectionVal addCorrectionVal(ValFeature valFeature, String name)
			throws Exception {
		valFeature = init(valFeature.getN());
		CorrectionVal correctionVal = new CorrectionVal();
		correctionVal.setValFeature(valFeature);
		correctionVal.setName(name.trim());
		if (valFeature.getListCorrectionVal().contains(correctionVal)) {
			throw new Exception("Значение " + name + " уже существует в "
					+ valFeature.getName());
		}
		Long n = getCorrectionValProvider().create(correctionVal);
		correctionVal.setN(n);
		valFeature.getListCorrectionVal().add(correctionVal);
		update(valFeature);
		return correctionVal;
	}

	public void delCorrectionVal(ValFeature valFeature, String name)
			throws Exception {
		Hibernate.initialize(valFeature.getListCorrectionVal());
		for (CorrectionVal c : valFeature.getListCorrectionVal()) {
			if(c.getName().equals(name)) {
				getCorrectionValProvider().delete(c);
				break;
			}
		}
//		valFeature.getListCorrectionVal().remove(correctionVal);
	}

	@Override
	public void delete(ValFeature o) throws Exception {
		Iterator<CorrectionVal> iterator = o.getListCorrectionVal().iterator();
		while (iterator.hasNext()) {
			CorrectionVal correctionVal = iterator.next();
			iterator.remove();
			getCorrectionValProvider().delete(correctionVal);
		}
		super.delete(o);
	}

	@Override
	public ValFeature init(Long n) {
		ValFeature valFeature = read(n);
		Hibernate.initialize(valFeature.getListCorrectionVal());
		return valFeature;
	}
	
	@Override
	public boolean existCorrectName(ValFeature valFeature, String correctionVal) {
		valFeature=init(valFeature.getN());
		Iterator<CorrectionVal> iter=valFeature.getListCorrectionVal().iterator();
		while(iter.hasNext()) {
			CorrectionVal c = iter.next();
			if(c.getName().equals(correctionVal)) {
				return true;
			}
		}
		return false;
	}
	
	public ICorrectionValProvider getCorrectionValProvider() {
		return correctionValProvider;
	}

	public void setCorrectionValProvider(
			ICorrectionValProvider correctionValProvider) {
		this.correctionValProvider = correctionValProvider;
	}


}