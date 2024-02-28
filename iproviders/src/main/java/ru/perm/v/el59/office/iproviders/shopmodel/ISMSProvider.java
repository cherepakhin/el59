package ru.perm.v.el59.office.iproviders.shopmodel;

import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.SMS;
import ru.perm.v.el59.dto.SMSDTO;

public interface ISMSProvider extends IGenericDao<SMS, Long> {
   SMS getByDTO(SMSDTO var1, String var2);
}
