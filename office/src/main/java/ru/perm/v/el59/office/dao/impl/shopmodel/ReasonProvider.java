package ru.perm.v.el59.office.dao.impl.shopmodel;

import org.hibernate.Query;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.IReasonProvider;
import ru.perm.v.el59.office.shopmodel.Reason;
import ru.perm.v.el59.office.shopmodel.TypePayment;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.ArrayList;
import java.util.List;


public class ReasonProvider extends GenericDaoMessageImpl<Reason, Long>
		implements IReasonProvider {

	private List<String> listRealReason = new ArrayList<String>();

	public ReasonProvider(Class<Reason> type) {
		super(type);
	}

	@Override
	public List<Reason> getAll() {
		List<Reason> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

	@Override
	public List<TypePayment> getRealReason() {
		String sql = "from Reason where name in (:listNameRealReson)";
		Query q1 = getSession().createQuery(sql);
		q1.setParameterList("listNameRealReson", getListRealReason());
		List<TypePayment> ret = q1.list();
		return ret;
	}

	public List<String> getListRealReason() {
		return listRealReason;
	}

	public void setListRealReason(List<String> listRealReason) {
		this.listRealReason = listRealReason;
	}

}
