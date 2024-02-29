package ru.perm.v.el59.office.parser;

import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.iproviders.web.IParserController;
import ru.perm.v.el59.office.iproviders.web.IParserSite;

import java.util.List;

public class ParserController implements IParserController {
	private List<IParserSite> listParser;

	/*
	 * (non-Javadoc)
	 * 
	 * @see parser.IParserController#parseTovar(java.lang.Integer,
	 * java.lang.String)
	 * url - может быть строкой с содержимыи html-страницы
	 */
	public String parseTovar(Integer nnum, String url, Manager manager,Boolean parseProperty,Boolean parsePicture)
			throws Exception {
		IParserSite selectedParser = null;
		for (IParserSite _parser : getListParser()) {
//			System.out.println(_parser.getSampleLink());
			if (url.contains(_parser.getSampleLink())) {
				selectedParser = _parser;
				break;
			}
		}

		if (selectedParser == null) {
			String err = "Под выбранную ссылку не подходит ни один парсер.\n"
					+ "Возможные ссылки:\n";
			for (IParserSite _parser : getListParser()) {
				err = err + _parser.getSampleLink() + "\n";
			}
			return err;
		} else {
			selectedParser.parseTovar(nnum, url, manager,parseProperty,parsePicture);
			return "";
		}
	}

	public List<IParserSite> getListParser() {
		return listParser;
	}

	public void setListParser(List<IParserSite> listParser) {
		this.listParser = listParser;
	}

}
