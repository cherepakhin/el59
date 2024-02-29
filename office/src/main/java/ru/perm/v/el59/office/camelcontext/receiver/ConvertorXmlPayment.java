package ru.perm.v.el59.office.camelcontext.receiver;

import ru.perm.v.el59.dto.PaymentDTO;
import ru.perm.v.el59.dto.message.TypeCommand;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.UserShop;
import ru.perm.v.el59.office.iproviders.IContragentProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.IUserShopProvider;
import ru.perm.v.el59.office.iproviders.critery.PaymentCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.*;
import ru.perm.v.el59.office.shopmodel.*;

import java.util.List;
import java.util.logging.Logger;

public abstract class ConvertorXmlPayment<DTO, VO> extends
		ConvertorFromXML<DTO, VO> {
	private IReasonProvider reasonProvider;
	private IExpenseProvider expenseProvider;
	private ITypeCashProvider typeCashProvider;
	private IContragentProvider contragentProvider;
	private IUserShopProvider usershopProvider;
	private ITypePaymentProvider typePaymentProvider;
	private IDocTitleProvider docTitleProvider;
	private IShopProvider shopProvider;
	private IPaymentProvider paymentProvider;

	@Override
	protected void create(VO o) throws Exception {
		log("Create", o);
		super.create(o);
	}

	private void log(String string, VO o) {
		Payment p = (Payment) o;
		Logger.getLogger(this.getClass()).info(
				String.format("Create %d", p.getN()));

	}

	@Override
	protected void delete(VO o) {
		log("Delete", o);
		VO p = getForUpdate(o);
		if (p != null) {
			super.delete(o);
		}
	}

	private VO getForUpdate(VO o) {
		PaymentCritery critery = new PaymentCritery();
		critery.nn = ((Payment) o).getNn();
		critery.listShop.add(((Payment) o).getShop());
		List<VO> list = getDao().getByCritery(critery);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	protected void update(VO o) throws Exception {
		/*
		 * System.out.println("update"); Payment p = (Payment) getForUpdate(o);
		 * if(p!=null) { ((Payment) o).setN(p.getN()); getDao().update(o); }
		 * else { getDao().create(o); }
		 */
		log("Update", o);
		super.update(o);
	}

	public VO fillFromDTO(PaymentDTO dto, Payment payment, String shopCod)
			throws Exception {

		Shop shop = (Shop) getShopProvider().read(shopCod);

		PaymentCritery paymentCritery = new PaymentCritery();
		paymentCritery.nn = dto.getN();
		paymentCritery.listShop.add(shop);
		List listPayment = getDao().getByCritery(paymentCritery);
		if (listPayment.size() > 0) {
			payment = (Payment) listPayment.get(0);
			if (!message.getTypeCommand().equals(TypeCommand.DELETE)) {
				message.setTypeCommand(TypeCommand.UPDATE);
			}
		} else {
			if (!message.getTypeCommand().equals(TypeCommand.DELETE)) {
				message.setTypeCommand(TypeCommand.CREATE);
			}
		}

		payment.setNn(dto.getN());

		payment.setShop(shop);

		TypePayment typePayment = (TypePayment) getTypePaymentProvider()
				.initialize(dto.getTypepayment().getN());
		payment.setTypePayment(typePayment);

		DocTitle docTitle = getDocTitleProvider().getByDTO(dto.getDoctitle(),
				shop);
		payment.setDocTitle(docTitle);

		payment.setDdate(dto.getDdate());

		Expense expense = (Expense) getExpenseProvider().initialize(
				dto.getExpense().getN());
		payment.setExpense(expense);

		Reason reason = (Reason) getReasonProvider().initialize(
				dto.getReason().getN());
		payment.setReason(reason);

		TypeCash typeCash = (TypeCash) getTypeCashProvider().initialize(
				dto.getTypecash().getN());
		payment.setTypeCash(typeCash);

		UserShop userShop = (UserShop) getUsershopProvider().initialize(
				dto.getUsershop().getN());
		payment.setUserShop(userShop);

		payment.setSumma(dto.getSumma());
		payment.setDdatecreate(dto.getDdatecreate());

		Contragent contragent = getContragentProvider().getByDTO(
				dto.getContragent(), shop);
		payment.setContragent(contragent);

		payment.setComment(dto.getComment());
		payment.setK1(dto.getK1());
		payment.setK2(dto.getK2());
		payment.setDeleted(dto.getDeleted());
		/*
		 * if(dto.getStornoPayment()!=null && dto.getStornoPayment()>0) {
		 * Payment storno=new Payment(); storno.setn dto.getStornoPayment() }
		 */
		return (VO) payment;
	}

	public IReasonProvider getReasonProvider() {
		return reasonProvider;
	}

	public void setReasonProvider(IReasonProvider reasonProvider) {
		this.reasonProvider = reasonProvider;
	}

	public IExpenseProvider getExpenseProvider() {
		return expenseProvider;
	}

	public void setExpenseProvider(IExpenseProvider expenseProvider) {
		this.expenseProvider = expenseProvider;
	}

	public ITypeCashProvider getTypeCashProvider() {
		return typeCashProvider;
	}

	public void setTypeCashProvider(ITypeCashProvider typeCashProvider) {
		this.typeCashProvider = typeCashProvider;
	}

	public IUserShopProvider getUsershopProvider() {
		return usershopProvider;
	}

	public void setUsershopProvider(IUserShopProvider usershopProvider) {
		this.usershopProvider = usershopProvider;
	}

	public ITypePaymentProvider getTypePaymentProvider() {
		return typePaymentProvider;
	}

	public void setTypePaymentProvider(ITypePaymentProvider typePaymentProvider) {
		this.typePaymentProvider = typePaymentProvider;
	}

	public IDocTitleProvider getDocTitleProvider() {
		return docTitleProvider;
	}

	public void setDocTitleProvider(IDocTitleProvider docTitleProvider) {
		this.docTitleProvider = docTitleProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public IContragentProvider getContragentProvider() {
		return contragentProvider;
	}

	public void setContragentProvider(IContragentProvider contragentProvider) {
		this.contragentProvider = contragentProvider;
	}

	public IPaymentProvider getPaymentProvider() {
		return paymentProvider;
	}

	public void setPaymentProvider(IPaymentProvider paymentProvider) {
		this.paymentProvider = paymentProvider;
	}

}
