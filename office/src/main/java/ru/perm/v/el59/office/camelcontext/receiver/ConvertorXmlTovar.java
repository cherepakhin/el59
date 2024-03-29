package ru.perm.v.el59.office.camelcontext.receiver;

import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.iproviders.critery.TovarCritery;

import java.util.List;

public class ConvertorXmlTovar extends ConvertorFromXmlDefault {
	
	private ITovarInfoProvider tovarInfoProvider;

	@Override
	protected void create(Object o) {
	}

	@Override
	protected void delete(Object o) {
	}

	@Override
	protected void update(Object o) {
	}

	@Override
	protected void get(Object o) {
		TovarCritery critery = (TovarCritery) o;
		String shopcod = getMessage().getShopCod();
		ITovarProvider provider = (ITovarProvider) getDao();
		List<Tovar> list = provider.getByCritery(critery);
		for (Tovar tovar : list) {
			getCommander().update(tovar, shopcod);
			TovarInfo tovarInfo = getTovarInfoProvider().read(tovar.getNnum());
			if(tovarInfo!=null) {
				getCommander().update(tovarInfo, shopcod);
			}
		}
	}

	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

}
