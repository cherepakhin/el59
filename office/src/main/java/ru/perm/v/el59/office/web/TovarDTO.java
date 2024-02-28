package ru.perm.v.el59.office.web;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Товар в выписке инетмагазина
 * 
 * @author vasi
 * 
 */
public class TovarDTO implements Serializable {
	private Integer nnum;
	private String name;
	private BigDecimal qty;
	private BigDecimal price;

	public Integer getNnum() {
		return nnum;
	}

	public void setNnum(Integer nnum) {
		this.nnum = nnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
