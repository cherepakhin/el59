package ru.perm.v.el59.office.test.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ru.perm.v.el59.office.test.model.docw.CommentDocWDaoTest;
import ru.perm.v.el59.office.test.model.docw.DocWDaoTest;
import ru.perm.v.el59.office.test.model.docw.DocWItemDaoTest;
import ru.perm.v.el59.office.test.model.docw.SubsFeatureDaoTest;
import ru.perm.v.el59.office.test.model.plan.PlanDaoTest;
import ru.perm.v.el59.office.test.model.plan.RestForPlanDaoTest;
import ru.perm.v.el59.office.test.model.plan.SmenaDaoTest;
import ru.perm.v.el59.office.test.model.plan.TDocTabelDaoTest;
import ru.perm.v.el59.office.test.model.plan.TabelDaoTest;
import ru.perm.v.el59.office.test.model.plan.TypeTabelDaoTest;
import ru.perm.v.el59.office.test.model.plan.TypeTabelValDaoTest;
import ru.perm.v.el59.office.test.model.plan.UserZPDaoTest;
import ru.perm.v.el59.office.test.model.service.LOPTDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocActDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocCallMasterDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocFromSupplierDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocImageDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocObmenDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocOrderDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocReceiveFromSCDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocSendToSCDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocServicePDSDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocStatementCustomerDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocToCustomerDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocToSCDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocToShopDaoTest;
import ru.perm.v.el59.office.test.model.service.TDocToSupplierDaoTest;
import ru.perm.v.el59.office.test.model.service.TypeTDocImageDaoTest;
import ru.perm.v.el59.office.test.model.service.VneshVidDaoTest;
import ru.perm.v.el59.office.test.model.service.WarrantyDaoTest;
import ru.perm.v.el59.office.test.routedoc.DispatcherDaoTest;
import ru.perm.v.el59.office.test.routedoc.DriverDaoTest;
import ru.perm.v.el59.office.test.routedoc.MachineDaoTest;
import ru.perm.v.el59.office.test.routedoc.PathPageDaoTest;
import ru.perm.v.el59.office.test.routedoc.PlanDownloadDaoTest;
import ru.perm.v.el59.office.test.routedoc.PlanDownloadSumDaoTest;
import ru.perm.v.el59.office.test.routedoc.ProcuratoryDaoTest;
import ru.perm.v.el59.office.test.routedoc.RouteJobDaoTest;
import ru.perm.v.el59.office.test.routedoc.TemplatePathPageDaoTest;
import ru.perm.v.el59.office.test.shopmodel.BankActionDaoTest;
import ru.perm.v.el59.office.test.shopmodel.BonusCardMoveDaoTest;
import ru.perm.v.el59.office.test.shopmodel.DiscountDaoTest;
import ru.perm.v.el59.office.test.shopmodel.DocDetailDaoTest;
import ru.perm.v.el59.office.test.shopmodel.DocTitleDaoTest;
import ru.perm.v.el59.office.test.shopmodel.ExpenseDaoTest;
import ru.perm.v.el59.office.test.shopmodel.ReasonDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TovarBonusDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypeCashDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypeDocShopDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypeDocStatusShopDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypeOperationDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypePDSDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypePaymentDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypePriceDaoTest;
import ru.perm.v.el59.office.test.shopmodel.TypeSertDaoTest;

@RunWith(Suite.class)
@SuiteClasses({ ComplaintDaoTest.class, ShopRightDaoTest.class,
		DolgnostDaoTest.class, ShopDaoTest.class, UserShopDaoTest.class,
		BonusKDaoTest.class, VarDaoTest.class, TovarDaoTest.class,
		ManagerDaoTest.class, CorrectionNameDaoTest.class,
		CorrectionValDaoTest.class, ValFeatureDaoTest.class,
		MainFeatureDaoTest.class, 
		FeatureOldDaoTest.class, FeatureOldDaoTest.class,
		ContragentDaoTest.class, GroupContragentDaoTest.class,
		PriceTypeDaoTest.class, AnalogDaoTest.class, RestSupplierDaoTest.class,
		SetTovarDaoTest.class, FormulaDaoTest.class, ErrDaoTest.class,
		TypeStockDaoTest.class, OperationDaoTest.class, OpGroupDaoTest.class,
		SetTypeStockDaoTest.class, SetGroupTovarDaoTest.class,
		AgentDaoTest.class, MoveDaoTest.class, RestCurDaoTest.class,
		RestDaoTest.class, TypeDocDaoTest.class, TypeErrDaoTest.class,
		TypeTDocImageDaoTest.class, TDocImageDaoTest.class, TDocDaoTest.class,
		TDocCallMasterDaoTest.class, TDocSendToSCDaoTest.class,
		TDocOrderDaoTest.class, TDocToSCDaoTest.class,
		TDocReceiveFromSCDaoTest.class, TDocActDaoTest.class,
		TDocToCustomerDaoTest.class, TDocServicePDSDaoTest.class,
		TDocStatementCustomerDaoTest.class, TDocToShopDaoTest.class,
		TDocObmenDaoTest.class, TDocFromSupplierDaoTest.class,
		TDocToSupplierDaoTest.class, VneshVidDaoTest.class,
		ComplectDaoTest.class, WarrantyDaoTest.class, ClientDaoTest.class,
		DiapazonKDaoTest.class, LOPTDaoTest.class, PeopleCountDaoTest.class,
		PlanDaoTest.class, RestForPlanDaoTest.class, TypeTabelValDaoTest.class,
		TypeTabelDaoTest.class, SmenaDaoTest.class, UserZPDaoTest.class,
		TDocTabelDaoTest.class, TabelDaoTest.class, TypeFileDaoTest.class,
		TypeDocStatusDaoTest.class, CauseNoPayDaoTest.class,
		CreditBankDaoTest.class, BrandDaoTest.class,
		ThingDaoTest.class, TypeDocShopDaoTest.class, TypePaymentDaoTest.class,
		DiscountDaoTest.class, TypeSertDaoTest.class,
		TypeDocStatusShopDaoTest.class, ExpenseDaoTest.class,
		ReasonDaoTest.class, TypeCashDaoTest.class, TypePDSDaoTest.class,
		TypePriceDaoTest.class, BankActionDaoTest.class,
		TovarBonusDaoTest.class, TypeOperationDaoTest.class, DocDaoTest.class,
		DocFileDaoTest.class, DocItemDaoTest.class, DispatcherDaoTest.class,
		DriverDaoTest.class, MachineDaoTest.class, RouteJobDaoTest.class,
		TemplatePathPageDaoTest.class, PathPageDaoTest.class,
		PlanDownloadDaoTest.class, RouteJobDaoTest.class,
		PlanDownloadSumDaoTest.class, ProcuratoryDaoTest.class,
		DocWDaoTest.class, DocTitleDaoTest.class, DocDetailDaoTest.class,
		DocWItemDaoTest.class, CommentDocWDaoTest.class,
		BonusCardMoveDaoTest.class, SubsFeatureDaoTest.class })
public class AllTests {

}
