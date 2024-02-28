package ru.perm.v.el59.office.dao.impl.service;

import java.math.BigDecimal;
import java.util.List;

import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.LOPT;
import ru.perm.v.el59.office.db.service.TDocCallMaster;
import ru.perm.v.el59.office.db.service.TDocOrder;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;
import ru.perm.v.el59.office.util.Helper;

public class HandlerTDocOrder extends HandlerTDoc<TDocOrder> {

	private final static String SUMMA = "Сумма ремонта";
	private List<IHandlerTDoc> requestAfterMaster;
	private List<IHandlerTDoc> requestAfterSC;

	public HandlerTDocOrder(Class<TDocOrder> type) {
		super(type);
	}
	
	public List<IHandlerTDoc> getRequestAfterMaster() {
		return requestAfterMaster;
	}

	public void setRequestAfterMaster(List<IHandlerTDoc> requestAfterMaster) {
		this.requestAfterMaster = requestAfterMaster;
	}

	public List<IHandlerTDoc> getRequestAfterSC() {
		return requestAfterSC;
	}

	public void setRequestAfterSC(List<IHandlerTDoc> requestAfterSC) {
		this.requestAfterSC = requestAfterSC;
	}

	@Override
	public RequestMessage afterCreate() {
		return createRequestSumma();
	}

	private RequestMessage createRequestSumma() {
		RequestMessage req = new RequestMessage(RequestMessage.VALUETYPE);
		req.listRequestItem.add(new RequestItem(SUMMA, new BigDecimal("0.00")));
		return req;
	}

	private boolean isCompeteSumma(TDocOrder order) {
		return order.getSumma().compareTo(BigDecimal.ZERO)>0;
	}

	@Override
	public boolean isComplete(ATDoc content) {
		TDocOrder order = (TDocOrder) content;
		return isCompeteSumma(order);
	}

	@Override
	public RequestMessage tune(ATDoc content, RequestMessage requestMessage)
			throws Exception {
		TDocOrder order = (TDocOrder) content;
		if (requestMessage == null) {
			if (!isCompeteSumma(order))
				return createRequestSumma();
			return null;
		}
		if (requestMessage.typeRequest.equals(RequestMessage.VALUETYPE)) {
			for (RequestItem requestItem : requestMessage.listRequestItem) {
				if (requestItem.title.equals(SUMMA)) {
					String summa = (String) requestItem.request;
					try {
						BigDecimal s = Helper.getBigDecimal(summa);
						order.setSumma(s);
						getDao().update(order);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
		return super.tune(content, requestMessage);
	}

	@Override
	public List<IHandlerTDoc> getNextTDoc(LOPT lopt, ATDoc content) {
		TDocOrder doc = (TDocOrder) content;
		TDocCallMaster callmaster = new TDocCallMaster();
		if (content.getTdoc().getParent().getTypeDoc().getName()
				.equals(callmaster.getDescriptionClass())) {
			return getRequestAfterMaster();
		} else {
			return getRequestAfterSC();
		}
	}

}
