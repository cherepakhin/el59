package ru.perm.v.el59.office.commerceml.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Товар")
public class Good extends AEntityCommerce {
	@XStreamAlias("Штрихкод")
	private String stroke;
	@XStreamAlias("Артикул")
	private String articul;
	@XStreamAlias("БазоваяЕдиница")
	private String baseUnit;
	@XStreamAlias("Картинка")
	private String picture;
	@XStreamAlias("Группы")
	private IdGroup idGroups;
	@XStreamAlias("СтавкиНалогов")
	private List<Tax> taxes = new ArrayList<Tax>();
	@XStreamAlias("ЗначенияРеквизитов")
	private List<ValProp> props = new ArrayList<ValProp>();
	@XStreamAlias("ЗначенияСвойств")
	private List<Property> listProperty = new ArrayList<Property>();

	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	public String getArticul() {
		return articul;
	}

	public void setArticul(String articul) {
		this.articul = articul;
	}

	public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}

	public List<ValProp> getProps() {
		return props;
	}

	public void setProps(List<ValProp> props) {
		this.props = props;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public IdGroup getIdGroups() {
		return idGroups;
	}

	public void setIdGroups(IdGroup idGroups) {
		this.idGroups = idGroups;
	}

	public List<Property> getListProperty() {
		return listProperty;
	}

	public void setListProperty(List<Property> listProperty) {
		this.listProperty = listProperty;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
