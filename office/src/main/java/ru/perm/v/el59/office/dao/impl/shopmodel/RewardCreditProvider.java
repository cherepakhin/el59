package ru.perm.v.el59.office.dao.impl.shopmodel;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.iproviders.critery.RewardCreditCritery;
import ru.perm.v.el59.office.shopmodel.RewardCredit;
import ru.perm.v.el59.office.iproviders.ICreditBankProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.IUserShopProvider;
import ru.perm.v.el59.office.iproviders.shopmodel.IRewardCreditProvider;
import ru.perm.v.el59.office.shopmodel.RewardCreditXLS;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.*;

public class RewardCreditProvider extends GenericDaoMessageImpl<RewardCredit, Long>
		implements IRewardCreditProvider {

	private IUserShopProvider userShopProvider;
	private ICreditBankProvider creditBankProvider;
	private IShopProvider shopProvider;
	private String fileNameTemplXls="";
	
	public RewardCreditProvider(Class<RewardCredit> type) {
		super(type);
	}

	@Override
	public List<RewardCredit> loadXlsFile(byte[] filedata) throws Exception {
		if(fileNameTemplXls.isEmpty()) {
			throw new Exception("Не установлен файл описания структуры xls-файла");
		}
		if(filedata.length==0) {
			throw new Exception("Пусто в xls-файле");
		}
		
		File templ = new File(getFileNameTemplXls());
		XLSReader mainReader = ReaderBuilder.buildFromXML(templ);
		Map<String, List<?>> beans = new HashMap<String, List<?>>();
		List<RewardCreditXLS> listRewardContract = new ArrayList<RewardCreditXLS>();
		beans.put("arr", listRewardContract);
//		BufferedInputStream inputXLS = new BufferedInputStream(new ByteInputStream(filedata, filedata.length));
		BufferedInputStream inputXLS = null;
		
		
		mainReader.read(inputXLS, beans);

		List<RewardCredit> ret=new ArrayList<RewardCredit>();
		for (RewardCreditXLS xls : listRewardContract) {
			RewardCredit r = new RewardCredit(
					xls.getDdate(),
					xls.getNumberContract(),
					xls.getPercent(),
					xls.getSumCredit(),
					xls.getSumReward());
			r.setUserShop(getUserShopProvider().getNullUserShop());
			r.setShop(getShopProvider().getNullShop());
			RewardCredit rewardDB=getByContract(r.getNumberContract(),r.getDdate());
			if(rewardDB!=null) {
				rewardDB.setDdate(r.getDdate());
				rewardDB.setBank(r.getBank());
				rewardDB.setNumberContract(r.getNumberContract());
				rewardDB.setPercent(r.getPercent());
				rewardDB.setShop(r.getShop());
				rewardDB.setSumCredit(r.getSumCredit());
				rewardDB.setSumReward(r.getSumReward());
				rewardDB.setUserShop(r.getUserShop());
				update(rewardDB);
			} else {
				Long n = create(r);
				r.setN(n);
			}
			ret.add(r);
		}
		return ret;
	}


	private RewardCredit getByContract(String numberContract, Date ddate) {
		RewardCreditCritery critery = new RewardCreditCritery();
		critery.numberContract=numberContract;
		critery.fromDate=ddate;
		critery.toDate=ddate;
		List<RewardCredit> list = getByCritery(critery);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<RewardCredit> getByCritery(Object critery) {
		RewardCreditCritery c = (RewardCreditCritery) critery;
		Criteria criteria = getSession().createCriteria(RewardCredit.class);
		if ((c.fromDate != null) && (c.toDate != null)) {
			criteria.add(Restrictions.ge("ddate", c.fromDate));
			criteria.add(Restrictions.le("ddate", c.toDate));
		}
		if (c.numberContract != null && !c.numberContract.isEmpty()) {
			criteria.add(Restrictions.like("name", c.numberContract, MatchMode.ANYWHERE).ignoreCase());
		}
		if (c.shops.size() > 0) {
			criteria.add(Restrictions.in("shop", c.shops));
		}
		if (c.users.size() > 0) {
			criteria.add(Restrictions.in("userShop", c.users));
		}
		if (c.banks.size() > 0) {
			criteria.add(Restrictions.in("bank", c.banks));
		}
		List<RewardCredit> list = criteria.list();
		return list;
	}

	public ICreditBankProvider getCreditBankProvider() {
		return creditBankProvider;
	}

	public void setCreditBankProvider(ICreditBankProvider creditBankProvider) {
		this.creditBankProvider = creditBankProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public String getFileNameTemplXls() {
		return getClass().getResource(fileNameTemplXls).getFile();
	}

	public void setFileNameTemplXls(String fileNameTemplXls) {
		this.fileNameTemplXls = fileNameTemplXls;
	}

	public IUserShopProvider getUserShopProvider() {
		return userShopProvider;
	}

	public void setUserShopProvider(IUserShopProvider userShopProvider) {
		this.userShopProvider = userShopProvider;
	}

}
