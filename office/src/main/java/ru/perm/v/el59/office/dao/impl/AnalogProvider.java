package ru.perm.v.el59.office.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.el.parser.ParseException;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.dto.office.critery.AnalogCritery;
import ru.perm.v.el59.dto.office.critery.TovarCritery;
import ru.el59.office.db.Analog;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.dto.TTovar;
import ru.perm.v.el59.dto.office.iproviders.IAnalogProvider;
import ru.perm.v.el59.dto.office.iproviders.IManagerProvider;
import ru.perm.v.el59.dto.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.util.ILuceneSearcher;

public class AnalogProvider extends GenericDaoHibernateImpl<Analog, Long>
		implements IAnalogProvider {

	private ITovarProvider tovarProvider;
	private IManagerProvider managerProvider;
	private ILuceneSearcher luceneSearcher;

	private Tovar nullTovar;
	private Integer nullTovarNnum;
	private static Logger LOGGER = Logger.getLogger(AnalogProvider.class.getName());
	private int counter = 0;

	public AnalogProvider(Class<Analog> type) {
		super(type);
	}
	
	@Override
	public void recreateIndex() throws IOException {
		TovarCritery tovarCritery = new TovarCritery();
		List<Tovar> listTovar = getTovarProvider().getByCritery(tovarCritery);
		getLuceneSearcher().addListTovar(listTovar);
	}

	@Override
	public List<Analog> getByCritery(Object critery) {
		if (critery.getClass().equals(AnalogCritery.class)) {
			AnalogCritery analogCritery = (AnalogCritery) critery;
			Criteria analogCriteria = getSession().createCriteria(Analog.class);
			if (!analogCritery.getName().isEmpty()) {
				analogCriteria.add(Restrictions.like("name",
						analogCritery.getName(), MatchMode.ANYWHERE)
						.ignoreCase());
			}
			if (!analogCritery.tovarDescription.isEmpty()) {
				Criteria tovarCriteria = analogCriteria.createCriteria("tovar");
				tovarCriteria.add(Restrictions.like("name",
						analogCritery.tovarDescription, MatchMode.ANYWHERE)
						.ignoreCase());
			}
			analogCriteria.addOrder(Order.asc("name"));
			ArrayList<Analog> list = (ArrayList<Analog>) analogCriteria.list();
			return list;

		} else {
			return super.getByCritery(critery);
		}
	}

	@Override
	public Tovar getByNameAnalog(String description) {
		Criteria analogCriteria = getSession().createCriteria(Analog.class);
		analogCriteria.add(Restrictions.eq("name",
				description)
				.ignoreCase());
		ArrayList<Analog> analogs = (ArrayList<Analog>) analogCriteria.list();
//		List<Analog> analogs = getByLikeName(description);
		if(analogs.size()>0) {
			return analogs.get(0).getTovar();
		}
/*		Analog analog = getByEqName(critery.getName());
		if (analog != null) {
			return analog.getTovar();
		}
*/		return null;
	}

	@Override
	public void change(String name, Tovar newTovar, Manager manager)
			throws Exception {
		// CommonCritery critery = new CommonCritery(name);
		Logger.getLogger(this.getClass().getName()).info(
				"Поиск в аналогах:" + name + " nnum:" + newTovar.getNnum());
//		Analog a = getByEqName(name);
		Analog analog = getByEqName(name);
//		Analog a = null;
		if (analog != null) {
			Logger.getLogger(this.getClass().getName()).info("Обновление:" + name);
			analog.setTovar(newTovar);
			analog.setManager(manager);
			analog.setDdate(new Date());
			update(analog);
		} else {
			Logger.getLogger(this.getClass().getName()).info(
					"Создание аналога:" + name + " nnum:" + newTovar.getNnum());
			analog = new Analog();
			analog.setName(name);
			analog.setTovar(newTovar);
			analog.setManager(manager);
			create(analog);
		}
	}

	/**
	 * Процедура распознавания
	 * 
	 * @throws IOException
	 * @throws .
	 */
	@Override
	public TTovar fillTTovar(TTovar t) throws IOException {
		TTovar ret = findAnalog(t);
		if (ret == null) {
			try {
				fillListTovar(t);
			} catch (ParseException | java.text.ParseException e) {
				e.printStackTrace();
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
			fillSeletedTovar(t);
			return t;
		} else {
//			t.setSelected(getNullTovar());
			return ret;
		}
	}

	@Override
	public List<TTovar> fillListTTovar(List<TTovar> listTTovar)
			throws IOException {
		List<TTovar> ret = new ArrayList<TTovar>();
		for (TTovar t : listTTovar) {
			t = fillTTovar(t);
			incrementCounter();
			// LOGGER.info(String.format("counter: %d", counter));
			ret.add(t);
		}
		return ret;
	}

	@Override
	public synchronized void incrementCounter() {
		counter++;
	}

	@Override
	public synchronized void resetCounter() {
		counter = 0;
	}

	@Override
	public synchronized int getCounter() {
		return counter;
	}

	public TTovar findAnalog(TTovar t) {
		Tovar tovar = (Tovar) getByNameAnalog(t.getItemDTO().getDescription());
		if (tovar != null) {
			t.setLabel(tovar.getName());
			t.setSelected(tovar);
			t.getListTovar().add(tovar);
			return t;
		} else {
			return null;
		}
	}

	protected void fillSeletedTovar(TTovar t) {
		// Если ничего не найдено , то заполнить список кандидатов и выбранный
		// товар специальным товаром "Аналогов не найдено"
		if (t.getListTovar().size() == 0) {
			Tovar _nullTovar = getNullTovar();
			t.getListTovar().add(_nullTovar);
			t.setSelected(_nullTovar);
			return;
		} else {
			t.setSelected(t.getListTovar().get(0));
		}
		if(t.getSelected()==null) {
			t.setSelected(getNullTovar());
		}
	}

	private Tovar getNullTovar() {
		if (nullTovar == null) {
			nullTovar = (Tovar) getTovarProvider().read(nullTovarNnum);
		}
		return nullTovar;
	}

	protected void fillListTovar(TTovar t) throws IOException, ParseException, java.text.ParseException {
		List<Integer> listNnum = getLuceneSearcher().getAnalog(
				t.getItemDTO().getDescription());
		TovarCritery tovarCritery = new TovarCritery();
		tovarCritery.nnum.addAll(listNnum);
		tovarCritery.deleted=false;
		List<Tovar> listTovar = getTovarProvider().getByCritery(tovarCritery);
		// Сортировка по индексу поисковика
		// Hash<Nnum,Tovar>
		HashMap<Integer, Tovar> hash = new HashMap<Integer, Tovar>();
		for (Tovar tovar : listTovar) {
			hash.put(tovar.getNnum(), tovar);
		}
		for (Integer nnum : listNnum) {
			if (hash.keySet().contains(nnum)) {
				t.getListTovar().add(hash.get(nnum));
			}
		}

		if (t.getListTovar() == null || t.getListTovar().size() == 0) {
			Tovar tovar = new Tovar();
			tovar.setName("-");
			tovar.setNnum(1);
			t.getListTovar().add(tovar);
			t.setSelected(tovar);
		}
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public Integer getNullTovarNnum() {
		return nullTovarNnum;
	}

	public void setNullTovarNnum(Integer nullTovarNnum) {
		this.nullTovarNnum = nullTovarNnum;
	}

	public IManagerProvider getManagerProvider() {
		return managerProvider;
	}

	public void setManagerProvider(IManagerProvider managerProvider) {
		this.managerProvider = managerProvider;
	}

	public ILuceneSearcher getLuceneSearcher() {
		return luceneSearcher;
	}

	public void setLuceneSearcher(ILuceneSearcher luceneSearcher) {
		this.luceneSearcher = luceneSearcher;
	}

}
