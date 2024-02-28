package ru.perm.v.el59.office.commerceml;

import ru.perm.v.el59.office.db.Photo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Картинка")
public class PhotoXml {
	@XStreamAlias("Наименование")
	private String name;
	@XStreamAlias("Файл")
	private String path = "";
	@XStreamAlias("Индекс")
	private Integer ord;

	public PhotoXml(Photo p, Integer ind) {
		super();
		if (p.getPath() != null) {
			path = p.getPath();
		}
		name = p.getName();
		ord = ind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

}
