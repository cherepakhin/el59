package ru.perm.v.el59.office.dao.impl.routedoc;

import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.routedoc.RouteJob;
import ru.perm.v.el59.office.db.routedoc.SupplierPriority;
import ru.perm.v.el59.office.iproviders.routedoc.ISupplierPriorityProvider;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

public class RouteJobComparator implements Comparator<RouteJob> {

	private ISupplierPriorityProvider supplierPriorityProvider;

	@Override
	public int compare(RouteJob r1, RouteJob r2) {
		Map<Contragent, SupplierPriority> hash = getSupplierPriorityProvider()
				.getHashAll();

		BigDecimal p1 = new BigDecimal(100000);
		BigDecimal p2 = new BigDecimal(100000);
		if (r1.getLoading().equals(r2.getLoading())) {
			if (hash.keySet().contains(r1.getTargetContragent())) {
				p1 = hash.get(r1.getTargetContragent()).getPriority();
			}
			if (hash.keySet().contains(r2.getTargetContragent())) {
				p2 = hash.get(r2.getTargetContragent()).getPriority();
			}
			return p1.compareTo(p2);
		} else {
			return r1.getLoading().compareTo(r2.getLoading()) * (-1);
		}
	}

	public ISupplierPriorityProvider getSupplierPriorityProvider() {
		return supplierPriorityProvider;
	}

	public void setSupplierPriorityProvider(
			ISupplierPriorityProvider supplierPriorityProvider) {
		this.supplierPriorityProvider = supplierPriorityProvider;
	}

}
