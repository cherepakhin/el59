package ru.perm.v.el59.office.dao.impl.service;

import java.util.logging.Logger; 
import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.critery.TDocCritery;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.TypeDoc;
import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.ITDoc;
import ru.perm.v.el59.office.db.service.LOPT;
import ru.perm.v.el59.office.db.service.TDoc;
import ru.perm.v.el59.office.iproviders.IManagerProvider;
import ru.perm.v.el59.office.iproviders.ITypeDocProvider;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;
import ru.perm.v.el59.office.iproviders.service.IControllerTDoc;
import ru.perm.v.el59.office.iproviders.service.ILOPTDao;
import ru.perm.v.el59.office.iproviders.service.ITDocProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ControllerTDoc implements Serializable, IControllerTDoc {
	private static final long serialVersionUID = 8647937182689930924L;
	private List<IHandlerTDoc> listHandler;
	private HashMap<String, IHandlerTDoc> hashTypedocHandler;
	private ITypeDocProvider typedocProvider;
	private ITDocProvider tdocProvider;
	private IManagerProvider managerProvider;
	private ILOPTDao loptProvider;
	private List<IHandlerTDoc> firstHandlers = new ArrayList<IHandlerTDoc>();

	public ControllerTDoc() {
		super();
	}

	public List<IHandlerTDoc> getListHandler() {
		return listHandler;
	}

	public void setListHandler(List<IHandlerTDoc> listHandler) {
		this.listHandler = listHandler;
		hashTypedocHandler = new HashMap<String, IHandlerTDoc>();
		for (IHandlerTDoc handler : listHandler) {
			hashTypedocHandler.put(handler.getTypeDocName(), handler);
		}
	}

	public List<IHandlerTDoc> getFirstHandlers() {
		return firstHandlers;
	}

	public void setFirstHandlers(List<IHandlerTDoc> firstHandlers) {
		this.firstHandlers = firstHandlers;
	}


	// Основной цикл обработки док-ов
	@Override
	public RequestMessage getRequestMessage(LOPT lopt,
			RequestMessage requestMessage, String nameManager) throws Exception {

		lopt = (LOPT) getLoptProvider().read(lopt.getN());
		TDoc lasttdoc = lopt.getTdoc().getLastTDoc();
		if (lasttdoc.equals(lopt.getTdoc())) { // у первого документа
												// усанавливаю последний док-т
												// сам на себя
			if (requestMessage == null) {
				return doFirstTDoc(lopt, requestMessage, nameManager);
			}
			if (requestMessage.typeRequest.equals(RequestMessage.TDOCTYPE)) {
				return doCreateContent(lopt, requestMessage, nameManager);
			}
			/*
			 * Почему-то не понадобилось
			 * if(requestMessage.typeRequest.equals(RequestMessage.VALUETYPE)) {
			 * }
			 */} else {
			ITDoc content = loadContent(lasttdoc);
			IHandlerTDoc handler = hashTypedocHandler.get(lasttdoc.getTypeDoc()
					.getName());
			if (requestMessage == null) {
				if (!handler.isComplete((ATDoc) content)) {
					// Данные последнего док-та не заполнены
					return handler.tune((ATDoc) content, requestMessage);
				} else {
					return getNextTDoc(handler, lopt, (ATDoc) content);
				}
			}
			if (requestMessage.typeRequest.equals(RequestMessage.TDOCTYPE)) {
				return doCreateContent(lopt, requestMessage, nameManager);
			}
			if (requestMessage.typeRequest.equals(RequestMessage.VALUETYPE)) {
				if (!handler.isComplete((ATDoc) content)) {
					// Данные последнего док-та не заполнены
					return handler.tune((ATDoc) content, requestMessage);
				} else {
					return getNextTDoc(handler, lopt, (ATDoc) content);
				}
			}
		}
		return null;
	}

	private RequestMessage getNextTDoc(IHandlerTDoc handler, LOPT lopt,
			ATDoc content) {
		List<IHandlerTDoc> listNextHandler = handler.getNextTDoc(lopt, content);
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.typeRequest = RequestMessage.TDOCTYPE;
		for (IHandlerTDoc h : listNextHandler) {
			requestMessage.listRequestItem.add(new RequestItem(h
					.getTypeDocName(), false));
		}
		return requestMessage;
	}

	private RequestMessage doCreateContent(LOPT lopt,
			RequestMessage requestMessage, String nameManager) throws Exception {
		for (RequestItem requestItem : requestMessage.listRequestItem) {
			if ((Boolean) requestItem.request) {
				IHandlerTDoc handler = hashTypedocHandler
						.get(requestItem.title);
				ATDoc content = handler.createContent();
				content = (ATDoc) _createTDoc(lopt, content, nameManager);
				return handler.create(content);
			}
		}
		return null;
	}

	private RequestMessage doFirstTDoc(LOPT lopt,
			RequestMessage requestMessage, String nameManager) {
		// Запрос самого первого документа
		requestMessage = new RequestMessage();
		requestMessage.typeRequest = RequestMessage.TDOCTYPE;
		for (IHandlerTDoc handler : getFirstHandlers()) {
			requestMessage.listRequestItem.add(new RequestItem(handler
					.getTypeDocName(), false));
		}
		return requestMessage;
	}

	/*
	 * // ебля с ебаной схемой с непонятной хуйней после вызова мастера
	 * if(tdocs.size()>0) { TDocCallMaster callmaster = new TDocCallMaster();
	 * if(
	 * tdocs.get(0).getTypedoc().getName().equals(callmaster.getDescriptionClass
	 * ())) { callmaster = (TDocCallMaster)
	 * ControllerTDoc.get().getContent(tdocs.get(0));
	 * if((callmaster.getResult()==null) || (callmaster.getResult().equals("")))
	 * { if(new TDocCallMasterUI(callmaster).editElement()) { try {
	 * callmaster.getProvider().save(callmaster);
	 * Emailer.get().sendTDoc(callmaster.getTdoc()); } catch (Exception e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } } return; } } }
	 * return null; }
	 */

	@Override
	public List<TDoc> getChilds(TDoc tdoc) {
		TDocCritery critery = new TDocCritery();
		critery.root = tdoc;
		List<TDoc> childs = getTdocProvider().getByCritery(critery);
		for (TDoc td : childs) {
			ATDoc content = (ATDoc) loadContent(td);
			String _content = "";
			if(content != null) {
				_content=content.toString();
			}
			td.setContent(_content);
		}
		return childs;
	}

	private ITDoc _createTDoc(LOPT lopt, ATDoc content, String nameManager)
			throws Exception {
		Manager manager = (Manager) getManagerProvider().getByCritery(
				new CommonCritery(nameManager)).get(0);
		TDoc tdoc = content.getTdoc();
		TypeDoc typedoc = content.getTdoc().getTypeDoc();
		tdoc.setTypeDoc(typedoc);
		tdoc.setAutor(manager);
		tdoc.setDdate(new Date());
		TDoc rootparent = (TDoc) getTdocProvider().read(lopt.getTdoc().getN());
		tdoc.setParent(lopt.getTdoc().getLastTDoc());
		tdoc.setRootDoc(rootparent);
		getTdocProvider().create(tdoc);
		content.setTdoc(tdoc);
		content.setN(tdoc.getN());
		lopt.getTdoc().setLastTDoc(tdoc);
		getTdocProvider().update(lopt.getTdoc());
		return content;
	}

	@Override
	public void delete(LOPT lopt, ITDoc content) throws Exception {
		String description = ((ATDoc) content).getTdoc().getTypeDoc().getName();
		TDoc rootTDoc = getTdocProvider().read(lopt.getTdoc().getN());
		TDoc prevTDoc = getTdocProvider().read(content.getTdoc().getParent().getN());
		rootTDoc.setLastTDoc(prevTDoc);
		getTdocProvider().update(rootTDoc);
//		getLoptProvider().update(lopt);
		IHandlerTDoc handler = hashTypedocHandler.get(description);
		handler.loadContent(content.getTdoc());
		handler.delete(content);
	}

	@Override
	public ITDoc loadContent(TDoc tdoc) {
		if(tdoc.getTypeDoc()==null) {
			Logger.getLogger(this.getClass().getName()).error(String.format("tdoc=%d getTypeDoc()=null", tdoc.getN()));
		}
		IHandlerTDoc handler = hashTypedocHandler.get(tdoc.getTypeDoc()
				.getName());
		ITDoc ret = handler.loadContent(tdoc);
		return ret;
	}

	public ITypeDocProvider getTypedocProvider() {
		return typedocProvider;
	}

	public void setTypedocProvider(ITypeDocProvider typedocProvider) {
		this.typedocProvider = typedocProvider;
	}

	public ITDocProvider getTdocProvider() {
		return tdocProvider;
	}

	public void setTdocProvider(ITDocProvider tdocProvider) {
		this.tdocProvider = tdocProvider;
	}

	public IManagerProvider getManagerProvider() {
		return managerProvider;
	}

	public void setManagerProvider(IManagerProvider managerProvider) {
		this.managerProvider = managerProvider;
	}

	public ILOPTDao getLoptProvider() {
		return loptProvider;
	}

	public void setLoptProvider(ILOPTDao loptProvider) {
		this.loptProvider = loptProvider;
	}

}
