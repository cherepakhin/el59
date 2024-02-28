package ru.perm.v.el59.office.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import ru.perm.v.el59.office.db.dto.TTovar;
import ru.perm.v.el59.office.iproviders.IAnalogProvider;

public class RecognizerThread implements Callable<List<TTovar>> {

	// private IAnalogProvider analogProvider;
	private static Logger LOGGER = Logger.getLogger(RecognizerThread.class);

	private List<TTovar> listTTovar;
	private IAnalogProvider analogProvider;
	private String id;

	public RecognizerThread(IAnalogProvider analogProvider,
			List<TTovar> listTTovar, String id) {
		super();
		this.analogProvider = analogProvider;
		this.listTTovar = listTTovar;
		this.id = id;
	}

	@Override
	public List<TTovar> call() throws Exception {
		LOGGER.info(String.format("BEGIN.Thread: %s, Size list %d ",id, listTTovar.size()));
		List<TTovar> ret = new ArrayList<TTovar>();
		ret = analogProvider.fillListTTovar(listTTovar);
		LOGGER.info(String.format("END.Thread: %s, Size list %d ",id, listTTovar.size()));
		return ret;
	}

}
