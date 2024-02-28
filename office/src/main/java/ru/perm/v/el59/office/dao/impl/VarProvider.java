package ru.perm.v.el59.office.dao.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.db.TypeVar;
import ru.perm.v.el59.office.db.Var;
import ru.perm.v.el59.office.iproviders.IVarProvider;

public class VarProvider extends GenericDaoHibernateImpl<Var, Long> implements
		IVarProvider {

	public VarProvider(Class<Var> type) {
		super(type);
	}

	@Override
	public Object getValue(String name) {
		Var var = (Var) getByEqName(name);
		return convert(var);
	}

	private Object convert(Var var) {
		Object ret = null;
		if (var != null) {
			if (var.getTypevar().equals(TypeVar.T_DATE)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				try {
					ret = sdf.parse(var.getVal());
					return ret;
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}
			if (var.getTypevar().equals(TypeVar.T_BigDecimal)) {
				ret = new BigDecimal(var.getVal());
				return ret;
			}
			if (var.getTypevar().equals(TypeVar.T_STRING)) {
				return var.getVal();
			}
			if (var.getTypevar().equals(TypeVar.T_INTEGER)) {
				return Integer.parseInt(var.getVal());
			}
		}
		return ret;
	}

	@Override
	public Object setValue(String name, Object val) throws Exception {
		Var var = (Var) getByEqName(name);
		if (var != null) {
			String s = var.getVal();
			if (s == null)
				s = "";
			if (var.getTypevar().equals(TypeVar.T_DATE)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				s = sdf.format(val);
			}
			if (var.getTypevar().equals(TypeVar.T_BigDecimal)) {
				DecimalFormat f = new DecimalFormat("0.00");
				s = f.format(val);
			}
			if (var.getTypevar().equals(TypeVar.T_STRING)) {
				s = (String) val;
			}
			if (var.getTypevar().equals(TypeVar.T_INTEGER)) {
				Integer i = (Integer) val;
				s = i.toString();
			}
			var.setVal(s);
			update(var);
		}
		return var;
	}

	@Override
	public List<Var> getAll() {
		List<Var> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

}
