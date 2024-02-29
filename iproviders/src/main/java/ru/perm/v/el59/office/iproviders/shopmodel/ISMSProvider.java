package ru.perm.v.el59.office.iproviders.shopmodel;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.dto.SMSDTO;
import ru.perm.v.el59.office.shopmodel.SMS;

public interface ISMSProvider extends IGenericDao<SMS, Long> {
   SMS getByDTO(SMSDTO var1, String var2);
}
