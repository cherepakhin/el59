package ru.perm.v.el59.office.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Заказ web-магазина с фтп
 * 
 * @author vasi
 * 
 */
public class OrderDTO implements Serializable {
	private static final long serialVersionUID = -5676749591345618194L;

	private String number = "";
	private Date ddate = new Date();
	private ShopDTO shopDTO = new ShopDTO();
	private ContragentDTO contragentDTO = new ContragentDTO();
	private List<TovarDTO> listTovarDTO = new ArrayList<TovarDTO>();
	private String comment = "";
	private String terms = "";
	private BigDecimal sum = new BigDecimal("0.00");
	private String shippingAddress = "";
	// С какого сайта
	private String source;
	private String xml;

	public Date getDdate() {
		return ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public ShopDTO getShopDTO() {
		return shopDTO;
	}

	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}

	public ContragentDTO getContragentDTO() {
		return contragentDTO;
	}

	public void setContragentDTO(ContragentDTO contragentDTO) {
		this.contragentDTO = contragentDTO;
	}

	public List<TovarDTO> getListTovarDTO() {
		return listTovarDTO;
	}

	public void setListTovarDTO(List<TovarDTO> listTovarDTO) {
		this.listTovarDTO = listTovarDTO;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}
}
