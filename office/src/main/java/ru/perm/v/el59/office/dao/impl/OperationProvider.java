package ru.perm.v.el59.office.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.db.Operation;
import ru.perm.v.el59.office.iproviders.IOperationProvider;

public class OperationProvider extends GenericDaoHibernateImpl<Operation, Long>
		implements IOperationProvider {

	private List<Operation> listAll ;
	
	public OperationProvider(Class<Operation> type) {
		super(type);
	}

	public List<Operation> getAll() {
		if(listAll==null) {
			CommonCritery critery = new CommonCritery("");
			listAll=getByCritery(critery);
		}
		return listAll;
	}

	@Override
	public Map<String, Operation> getAllAsHashBestChr() {
		List<Operation> operations = getAll();
		HashMap<String, Operation> ret = new HashMap<String, Operation>();
		for (Operation operation : operations) {
			ret.put(operation.getBest(), operation);
		}
		return ret;
	}

	@Override
	public Map<String, Operation> getHashChrOperation() {
		List<Operation> operations = getAll();
		HashMap<String, Operation> ret = new HashMap<String, Operation>();
		for (Operation operation : operations) {
			ret.put(operation.getChr(), operation);
		}
		return ret;
	}
}
