package ru.perm.v.el59.office.analisebest;

import ru.perm.v.el59.office.dto.BestTag;

public interface ICreatotBestTag {

    /**
     * Получение ценника из офисной базы
     *
     * @param nnum
     * @return
     * @throws Exception
     */
    public BestTag getBestTag(Integer nnum, String shopCod) throws Exception;

}
