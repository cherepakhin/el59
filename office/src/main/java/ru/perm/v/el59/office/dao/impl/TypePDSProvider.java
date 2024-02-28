package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.List;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypePDSProvider;
import ru.perm.v.el59.office.shopmodel.TypePDS;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypePDSProvider extends GenericDaoMessageImpl<TypePDS, Long>
		implements ITypePDSProvider {

	private List<Integer> listNnumPDS = new ArrayList<Integer>();
	
	public TypePDSProvider(Class<TypePDS> type) {
		super(type);
	}

	@Override
	public List<TypePDS> getAll() {
		return getByCritery(new CommonCritery(""));
	}

	@Override
	public boolean isPDS(Integer nnum) {
		return listNnumPDS.contains(nnum);
	}
	
	@Override
	public List<Integer> getListNnumPDS() {
		return listNnumPDS;
	}

	public void setListNnumPDS(List<Integer> listNnumPDS) {
		this.listNnumPDS = listNnumPDS;
	}
}
