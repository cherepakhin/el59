package ru.perm.v.el59.office.util;

import ru.el59.office.db.Tovar;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Поисковик, основанный на Lucene
 * @author vasi
 *
 */
public interface ILuceneSearcher {
	/**
	 * Добавить товар в индекс для поиска
	 * @param tovar
	 * @throws IOException
	 */
	public void addTovar(Tovar tovar) throws IOException;
	/**
	 * Поиск н.номера по названию товара поставщика
	 * @param description
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Integer> getAnalog(String description) throws IOException, ParseException, org.apache.el.parser.ParseException;
	/**
	 * Добавить в индекс поиска список товаров
	 * @param listTovar
	 * @throws IOException
	 */
	void addListTovar(List<Tovar> listTovar) throws IOException;
	/**
	 * Поиск по списку названий товаров поставщика
	 * @param listName
	 * @return map<Название товара поставщика, List<Ном.номера>>
	 * @throws ParseException
	 * @throws IOException
	 */
	Map<String, List<Integer>> getAnalogForListName(List<String> listName) throws ParseException, IOException;
}
