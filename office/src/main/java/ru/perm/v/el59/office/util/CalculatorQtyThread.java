package ru.perm.v.el59.office.util;

/**
 * Калькулятор для расчета кол-ва потоков и кол-ва элементов в потоке, при
 * условии , что кол-во потоков не должно привышать qtyThread и к-во элементов в
 * потоке должно быть не меньше qtyTTovarByThread
 * 
 * @author vasi
 * 
 */
public class CalculatorQtyThread {

	private int qtyThread = 4;
	private int qtyTTovarByThread = 4;

	/**
	 * Расчет потоков и кол-ва элементов в потоке для распознавания накладной
	 * @param listsize - расзмер массива
	 * @param _qtyThread - максимальное ко-во потоков
	 * @param _qtyTTovarByThread - минимальное кол-во элементов в потоке
	 */
	public void calc(int listsize, int _qtyThread, int _qtyTTovarByThread) {
		qtyThread = _qtyThread;
		qtyTTovarByThread = _qtyTTovarByThread;

		if (listsize / (qtyThread * qtyTTovarByThread) == 0) {
			if (listsize / qtyTTovarByThread < 4) {
				qtyThread = listsize / qtyTTovarByThread;
				if (listsize % qtyTTovarByThread > 0) {
					qtyThread++;
				}
			}
		} else {
			qtyTTovarByThread = listsize / qtyThread;
		}

	}

	/**
	 * Расчитанное к-во потоков
	 * @return
	 */
	public int getQtyThread() {
		return qtyThread;
	}

	/**
	 * Расчитанное к-во элементов в потоке
	 * @return
	 */
	public int getQtyTTovarByThread() {
		return qtyTTovarByThread;
	}

}
