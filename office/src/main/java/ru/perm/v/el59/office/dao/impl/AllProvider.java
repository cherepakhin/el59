package ru.perm.v.el59.office.dao.impl;


import ru.el59.office.iproviders.*;
import ru.el59.office.iproviders.plan.IControllerZP;
import ru.el59.office.iproviders.plan.IPlanProvider;
import ru.el59.office.iproviders.service.IClientProvider;
import ru.el59.office.iproviders.service.ILOPTDao;
import ru.el59.office.iproviders.service.ITDocProvider;
import ru.el59.office.iproviders.shopmodel.*;
import ru.el59.office.iproviders.web.IDocWItemProvider;
import ru.el59.office.iproviders.web.IDocWProvider;
import ru.el59.office.iproviders.web.IRestWebProvider;
import ru.el59.office.wscommand.ICommander;

public class AllProvider {
    private IAnalogProvider analogProvider;
    private IBonusCardMoveProvider bonusCardMoveProvider;
    private IBonusCardProvider bonusCardProvider;
    private IBankActionProvider bankActionProvider;
    private ICauseNoPayProvider causeNoPayProvider;
    private IClientProvider clientProvider;
    private IContragentProvider contragentProvider;
    private IControllerZP controllerZP;
    private ICommander commander;
    private IDiscountProvider discountProvider;
    private IDiapazonkProvider diapazonkProvider;
    private IDocTitleProvider docTitleProvider;
    private IDocItemProvider docItemProvider;
    private IDocProvider docProvider;
    private IDocWItemProvider docWItemProvider;
    private IDocWProvider docWProvider;
    private IDolgnostProvider dolgnostProvider;
    private IFormulaProvider formulaProvider;
    private IGroupContragentProvider groupContragentProvider;
    private IGroupTovarProvider groupTovarProvider;
    private IGroupTProvider groupTProvider;
    private ILOPTDao loptDao;
    private ILowSaleProvider lowSaleProvider;
    private IManagerProvider managerProvider;
    private IMoveProvider moveProvider;
    private IOperationProvider operationProvider;
    private IOpGroupProvider opGroupProvider;
    private IPeopleCountProvider peopleCountProvider;
    private IPlanProvider planProvider;
    private IPriceProvider priceProvider;
    private IPriceTypeProvider pricetypeProvider;
    private IRestCurProvider restcurProvider;
    private IRestProvider restProvider;
    private IRestSupplierProvider restSupplierProvider;
    private IRestWebProvider restWebProvider;
    private ISetGroupTovarProvider setGroupTovarProvider;
    private ISetTovarProvider setTovarProvider;
    private ISetTypeStockProvider setTypeStockProvider;
    private IShopProvider shopProvider;
    private ITDocProvider tdocProvider;
    private IThingProvider thingProvider;
}
