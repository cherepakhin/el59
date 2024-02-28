package ru.perm.v.el59.office.iproviders.shopmodel;

import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.RewardCredit;

public interface IRewardCreditProvider extends IGenericDao<RewardCredit, Long> {
   List<RewardCredit> loadXlsFile(byte[] var1) throws Exception;
}
