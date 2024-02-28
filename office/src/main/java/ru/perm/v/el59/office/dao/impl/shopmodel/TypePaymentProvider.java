package ru.perm.v.el59.office.dao.impl.shopmodel;

import java.util.List;

import ru.perm.v.el59.dto.dao.CommonCritery;
import ru.perm.v.el59..office.iproviders.shopmodel.ITypePaymentProvider;
import ru.el59.office.shopmodel.TypePayment;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypePaymentProvider extends
		GenericDaoMessageImpl<TypePayment, Long> implements
		ITypePaymentProvider {

	public TypePaymentProvider(Class<TypePayment> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public List<TypePayment> getAll() {
		List<TypePayment> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

}
