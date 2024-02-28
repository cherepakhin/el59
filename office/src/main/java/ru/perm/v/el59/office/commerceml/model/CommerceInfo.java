package ru.perm.v.el59.office.commerceml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("КоммерческаяИнформация")
public class CommerceInfo extends AEntityCommerce {
	@XStreamAlias("Классификатор")
	private Classificator classificator;
	@XStreamAlias("Каталог")
	private Catalog catalog;

	public Classificator getClassificator() {
		return classificator;
	}

	public void setClassificator(Classificator classificator) {
		this.classificator = classificator;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

}
