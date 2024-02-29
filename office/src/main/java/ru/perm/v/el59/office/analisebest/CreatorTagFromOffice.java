package ru.perm.v.el59.office.analisebest;

import ru.perm.v.el59.dto.BestTag;
import ru.perm.v.el59.office.db.Formula;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.dto.PriceDbf;
import ru.perm.v.el59.office.iproviders.IFormulaProvider;
import ru.perm.v.el59.office.iproviders.IPriceProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Подгружает прайсы для магазина
 *
 * @author vasi
 */
public class CreatorTagFromOffice implements ICreatotBestTag {

    private IPriceProvider priceProvider;
    private IShopProvider shopProvider;
    private IFormulaProvider formulaProvider;
    private ITovarInfoProvider tovarInfoProvider;

    private String nameFormula = "";
    private Formula formula;
    private String shopCod = "";
    private List<PriceDbf> prices;

    private Map<Integer, PriceDbf> mapPrices = new HashMap<Integer, PriceDbf>();

    @Override
    public BestTag getBestTag(Integer nnum, String _shopCod) throws Exception {
        if (!shopCod.equals(_shopCod)) {
            // Загрузка прайсов для магазина
            Shop shop = getShopProvider().read(_shopCod);
            if (shop == null) {
                throw new Exception(String.format("Не найден магазин с кодом %s ", _shopCod));
            }
            Formula formula = getFormula();
            prices = getPriceProvider().getPriceDbf(null, shop, null, null, formula);
            mapPrices = new HashMap<Integer, PriceDbf>();
            for (PriceDbf price : prices) {
                mapPrices.put(Integer.parseInt(price.getNnum()), price);
            }
            shopCod = _shopCod;
        }

        if (mapPrices.containsKey(nnum)) {
            PriceDbf priceDbf = mapPrices.get(nnum);
            BestTag betsTag = new BestTag(
                    priceDbf.getGrup(),
                    nnum,
                    priceDbf.getName(),
                    getTovarInfoProvider().getAnnotation(nnum),
                    priceDbf.getCena(),
                    priceDbf.getCena1(),
                    priceDbf.getName()
            );
            return betsTag;
        }
        return null;
    }

    private Formula getFormula() throws Exception {
        if (formula == null) {
            formula = getFormulaProvider().getByEqName(getNameFormula());
            if (formula == null) {
                throw new Exception(String.format("Не найдена формула расчета %s ", getNameFormula()));
            }
        }
        return formula;
    }

    public IPriceProvider getPriceProvider() {
        return priceProvider;
    }

    public void setPriceProvider(IPriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public IShopProvider getShopProvider() {
        return shopProvider;
    }

    public void setShopProvider(IShopProvider shopProvider) {
        this.shopProvider = shopProvider;
    }

    public String getNameFormula() {
        return nameFormula;
    }

    public void setNameFormula(String nameFormula) {
        this.nameFormula = nameFormula;
    }

    public IFormulaProvider getFormulaProvider() {
        return formulaProvider;
    }

    public void setFormulaProvider(IFormulaProvider formulaProvider) {
        this.formulaProvider = formulaProvider;
    }

    public ITovarInfoProvider getTovarInfoProvider() {
        return tovarInfoProvider;
    }

    public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
        this.tovarInfoProvider = tovarInfoProvider;
    }

}
