package ru.perm.v.el59.office.util;

import java.util.logging.Logger; 
import ru.perm.v.el59.dto.office.util.IRecognizerInvoice;
import ru.perm.v.el59.office.db.Doc;
import ru.perm.v.el59.office.db.dto.TTovar;
import ru.perm.v.el59.office.iproviders.IAnalogProvider;
import ru.perm.v.el59.office.iproviders.IDocItemProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Распознать товары в накладной поставщика и заполнить накладную
 * 
 * @author vasi
 * 
 */
public class RecognizerInvoice implements IRecognizerInvoice {

	private static Logger LOGGER = Logger.getLogger(RecognizerInvoice.class);
	private IAnalogProvider analogProvider;
	private IDocItemProvider docItemProvider;
	private int maxQtyThread = 4;
	private int minQtyTTovarByThread = 4;

	@Override
	public List<TTovar> recognize(List<TTovar> listTTovar, Doc doc) throws Exception {
		listTTovar = fillListTTovar(listTTovar);
		getDocItemProvider().createByListTTovar(doc, listTTovar);
		return listTTovar;
	}

	@Override
	public List<TTovar> fillListTTovar(List<TTovar> listTTovar) {
		List<TTovar> ret = new ArrayList<TTovar>();
		// Расчет к-ва потоков и элементов на поток
		CalculatorQtyThread calculator = new CalculatorQtyThread();
		calculator.calc(listTTovar.size(), getMaxQtyThread(),
				getMinQtyTTovarByThread());
		int qtyThread = calculator.getQtyThread();
		int qtyTTovarByThread = calculator.getQtyTTovarByThread();
		getAnalogProvider().resetCounter();

		ExecutorService exec = Executors.newFixedThreadPool(qtyThread);
		ArrayList<Future<List<TTovar>>> results = new ArrayList<Future<List<TTovar>>>();
		for (int i = 0; i < qtyThread; i++) {
			int fromIndex = (i == 0 ? 0 : i * qtyTTovarByThread + 1);
			int toIndex = (i == (qtyThread - 1) ? listTTovar.size() : (i + 1)
					* qtyTTovarByThread + 1);
			/*
			 * int fromIndex = (i == 0 ? 0 : i * qtyTTovarByThread + 1); int
			 * toIndex = (i == (qtyThread - 1) ? listTTovar.size() : (i + 1) *
			 * qtyTTovarByThread)+1;
			 */
			List<TTovar> list = listTTovar.subList(fromIndex, toIndex);
			// Возникает ошибка при сериализации subList.
			// Поэтому создаю промежуточный список _list для передачи.
			ArrayList<TTovar> _list = new ArrayList<TTovar>();
			_list.addAll(list);
			results.add(exec.submit(new RecognizerThread(getAnalogProvider(),
					_list, "Thread#" + i)));
		}
		for (Future<List<TTovar>> future : results) {
			try {
				ret.addAll(future.get());
			} catch (InterruptedException e) {
				LOGGER.error(e);
				e.printStackTrace();
			} catch (ExecutionException e) {
				LOGGER.error(e);
				e.printStackTrace();
			} finally {
				exec.shutdown();
			}
		}
		return ret;

	}

	public IAnalogProvider getAnalogProvider() {
		return analogProvider;
	}

	public void setAnalogProvider(IAnalogProvider analogProvider) {
		this.analogProvider = analogProvider;
	}

	public IDocItemProvider getDocItemProvider() {
		return docItemProvider;
	}

	public void setDocItemProvider(IDocItemProvider docItemProvider) {
		this.docItemProvider = docItemProvider;
	}

	@Override
	public int getCounter() {
		return getAnalogProvider().getCounter();
	}

	public int getMaxQtyThread() {
		return maxQtyThread;
	}

	public void setMaxQtyThread(int maxQtyThread) {
		this.maxQtyThread = maxQtyThread;
	}

	public int getMinQtyTTovarByThread() {
		return minQtyTTovarByThread;
	}

	public void setMinQtyTTovarByThread(int minQtyTTovarByThread) {
		this.minQtyTTovarByThread = minQtyTTovarByThread;
	}
}
