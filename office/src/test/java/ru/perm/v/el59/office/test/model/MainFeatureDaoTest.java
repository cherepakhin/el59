package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import ru.el59.office.dao.impl.subs.MainFeatureProvider;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.subs.CorrectionName;
import ru.el59.office.db.subs.CorrectionVal;
import ru.el59.office.db.subs.GroupTovarMainFeature;
import ru.el59.office.db.subs.MainFeature;
import ru.el59.office.db.subs.ValFeature;
import ru.el59.office.iproviders.IGroupTovarProvider;
import ru.el59.office.iproviders.subs.IGroupTovarMainFeatureProvider;
import ru.el59.office.iproviders.subs.IMainFeatureProvider;
import ru.el59.office.iproviders.subs.IValFeatureProvider;

public class MainFeatureDaoTest extends DaoTest<MainFeature, Long> {

	private final static String NAME_MAINFEATURE = "_Диагональ";
	private final static String NAME_MAINFEATURE_FOR_TAG = "_диагональ";
	private final static String NAME_MAINFEATURE_FROM_SITE = "_Диагональ экрана";

	private final static String NAME_VALFEATURE = "_15.6";
	private final static String NAME_VALFEATURE_FOR_TAG = "_15\"6'";
	private final static String NAME_VALFEATURE_FROM_SITE = "_15.6 дюйма";

	@Override
	protected String getNameDao() {
		return "mainFeatureDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		if (n < 0) {
			assertTrue(true);
			return;
		}
		MainFeature e = getDao().read(n);
		System.out.println("CorrectionName:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

	@Ignore
	@Test
	public void createAndDelete() throws Exception {
		IGroupTovarProvider groupTovarProvider = context
				.getBean(IGroupTovarProvider.class);
		GroupTovar groupTovar = groupTovarProvider.read("0905000000");
		assertNotNull(groupTovar);

		IMainFeatureProvider mainFeatureProvider = context
				.getBean(IMainFeatureProvider.class);
		MainFeature mainFeature = mainFeatureProvider.createForGroupTovar(
				groupTovar, NAME_MAINFEATURE, NAME_MAINFEATURE_FOR_TAG);
		assertNotNull(mainFeature);
		assertTrue(mainFeature.getName().equals(NAME_MAINFEATURE));
		assertTrue(mainFeature.getNameForTag().equals(NAME_MAINFEATURE_FOR_TAG));
		assertTrue(mainFeature.getGroupTovarMainFeature().getGroupTovar()
				.equals(groupTovar));

		IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider = context
				.getBean(IGroupTovarMainFeatureProvider.class);
		GroupTovarMainFeature groupTovarMainFeature = groupTovarMainFeatureProvider
				.init(mainFeature.getGroupTovarMainFeature());
		assertTrue(groupTovarMainFeature.getMainFeatures()
				.contains(mainFeature));

		mainFeatureProvider.delete(mainFeature);
	}

	/**
	 * Не добавлять такую же хар-ку. Не создавать дубль
	 * @throws Exception
	 */
	@Test
	public void createDoubleAndDelete() throws Exception {
		IGroupTovarProvider groupTovarProvider = context
				.getBean(IGroupTovarProvider.class);
		GroupTovar groupTovar = groupTovarProvider.read("0905000000");
		assertNotNull(groupTovar);

		IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider = context
				.getBean(IGroupTovarMainFeatureProvider.class);
		
		IMainFeatureProvider mainFeatureProvider = context
				.getBean(IMainFeatureProvider.class);
		MainFeature mainFeature = mainFeatureProvider.createForGroupTovar(
				groupTovar, NAME_MAINFEATURE, NAME_MAINFEATURE_FOR_TAG);
		assertNotNull(mainFeature);
		assertTrue(mainFeature.getName().equals(NAME_MAINFEATURE));
		assertTrue(mainFeature.getNameForTag().equals(NAME_MAINFEATURE_FOR_TAG));
		assertTrue(mainFeature.getGroupTovarMainFeature().getGroupTovar()
				.equals(groupTovar));

		GroupTovarMainFeature groupTovarMainFeature = groupTovarMainFeatureProvider
				.init(mainFeature.getGroupTovarMainFeature());
		
		assertTrue(groupTovarMainFeature.getMainFeatures()
				.contains(mainFeature));

		int size =  groupTovarMainFeature.getMainFeatures().size();

		mainFeature = mainFeatureProvider.createForGroupTovar(
				groupTovar, NAME_MAINFEATURE, NAME_MAINFEATURE_FOR_TAG);

		groupTovarMainFeature = groupTovarMainFeatureProvider
				.init(mainFeature.getGroupTovarMainFeature());

		assertTrue(size==groupTovarMainFeature.getMainFeatures().size());
		mainFeatureProvider.delete(mainFeature);
	}

	@Test
	@Ignore
	public void addCorrectionName() throws Exception {
		IGroupTovarProvider groupTovarProvider = context
				.getBean(IGroupTovarProvider.class);
		GroupTovar groupTovar = groupTovarProvider.read("0905000000");
		assertNotNull(groupTovar);

		IMainFeatureProvider mainFeatureProvider = context
				.getBean(IMainFeatureProvider.class);
		MainFeature mainFeature = mainFeatureProvider.createForGroupTovar(
				groupTovar, NAME_MAINFEATURE, NAME_MAINFEATURE_FOR_TAG);
		assertNotNull(mainFeature);
		assertTrue(mainFeature.getName().equals(NAME_MAINFEATURE));
		assertTrue(mainFeature.getNameForTag().equals(NAME_MAINFEATURE_FOR_TAG));
		assertTrue(mainFeature.getGroupTovarMainFeature().getGroupTovar()
				.equals(groupTovar));

		IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider = context
				.getBean(IGroupTovarMainFeatureProvider.class);
		GroupTovarMainFeature groupTovarMainFeature = groupTovarMainFeatureProvider
				.init(mainFeature.getGroupTovarMainFeature());
		assertTrue(groupTovarMainFeature.getMainFeatures()
				.contains(mainFeature));

		CorrectionName correctioName = mainFeatureProvider.addCorrectionName(
				mainFeature, NAME_MAINFEATURE_FROM_SITE);
		assertTrue(correctioName.getName().equals(NAME_MAINFEATURE_FROM_SITE));
		assertTrue(correctioName.getMainFeature().equals(mainFeature));
		mainFeature = mainFeatureProvider.init(mainFeature);
		assertTrue(mainFeature.getListCorrectionName().contains(correctioName));
		mainFeatureProvider.delete(mainFeature);
	}

	@Test
	public void addCorrectionNameDouble() throws Exception {
		IGroupTovarProvider groupTovarProvider = context
				.getBean(IGroupTovarProvider.class);
		GroupTovar groupTovar = groupTovarProvider.read("0905000000");
		assertNotNull(groupTovar);

		IMainFeatureProvider mainFeatureProvider = context
				.getBean(IMainFeatureProvider.class);
		MainFeature mainFeature = mainFeatureProvider.createForGroupTovar(
				groupTovar, NAME_MAINFEATURE, NAME_MAINFEATURE_FOR_TAG);
		assertNotNull(mainFeature);
		assertTrue(mainFeature.getName().equals(NAME_MAINFEATURE));
		assertTrue(mainFeature.getNameForTag().equals(NAME_MAINFEATURE_FOR_TAG));
		assertTrue(mainFeature.getGroupTovarMainFeature().getGroupTovar()
				.equals(groupTovar));

		IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider = context
				.getBean(IGroupTovarMainFeatureProvider.class);
		GroupTovarMainFeature groupTovarMainFeature = groupTovarMainFeatureProvider
				.init(mainFeature.getGroupTovarMainFeature());
		assertTrue(groupTovarMainFeature.getMainFeatures()
				.contains(mainFeature));

		CorrectionName correctioName = mainFeatureProvider.addCorrectionName(
				mainFeature, NAME_MAINFEATURE_FROM_SITE);
		assertTrue(correctioName.getName().equals(NAME_MAINFEATURE_FROM_SITE));
		assertTrue(correctioName.getMainFeature().equals(mainFeature));
		mainFeature = mainFeatureProvider.init(mainFeature);
		assertTrue(mainFeature.getListCorrectionName().contains(correctioName));
		
		int size = mainFeature.getListCorrectionName().size();
		correctioName = mainFeatureProvider.addCorrectionName(
				mainFeature, NAME_MAINFEATURE_FROM_SITE);
		mainFeature = mainFeatureProvider.init(mainFeature);
		assertTrue(size==mainFeature.getListCorrectionName().size());
		
		mainFeatureProvider.delete(mainFeature);
	}

	@Test
	@Ignore
	public void createForGroupTovarNotExistMainFeature() throws Exception {
		IGroupTovarProvider groupTovarProvider = context
				.getBean(IGroupTovarProvider.class);
		GroupTovar groupTovar = groupTovarProvider.read("0905000000");
		assertNotNull(groupTovar);

		IMainFeatureProvider mainFeatureProvider = context
				.getBean(IMainFeatureProvider.class);
		// Проверка mainFeature
		MainFeature mainFeature = mainFeatureProvider.createForGroupTovar(
				groupTovar, NAME_MAINFEATURE, NAME_MAINFEATURE_FOR_TAG,
				NAME_MAINFEATURE_FROM_SITE, NAME_VALFEATURE,
				NAME_VALFEATURE_FOR_TAG, NAME_VALFEATURE_FROM_SITE);
		assertNotNull(mainFeature);
		assertTrue(mainFeature.getName().equals(NAME_MAINFEATURE));
		assertTrue(mainFeature.getNameForTag().equals(NAME_MAINFEATURE_FOR_TAG));
		assertTrue(mainFeature.getGroupTovarMainFeature().getGroupTovar()
				.equals(groupTovar));

		// Проверка CorrectionName
		Iterator<CorrectionName> iterCorrectionName = mainFeature
				.getListCorrectionName().iterator();
		boolean testCorrectionName = false;
		while (iterCorrectionName.hasNext()) {
			CorrectionName c = iterCorrectionName.next();
			if (c.getName().equals(NAME_MAINFEATURE_FROM_SITE)
					&& c.getMainFeature().equals(mainFeature)) {
				testCorrectionName = true;
				break;
			}
		}
		assertTrue(testCorrectionName);

		// Проверка ValFeature
		Iterator<ValFeature> iterValFeature = mainFeature.getListValFeature()
				.iterator();
		boolean testValFeature = false;
		while (iterValFeature.hasNext()) {
			ValFeature v = iterValFeature.next();
			if (v.getName().equals(NAME_VALFEATURE)
					&& v.getMainFeature().equals(mainFeature)
					&& v.getForTag().equals(NAME_VALFEATURE_FOR_TAG)) {
				testValFeature = true;
			}
		}
		assertTrue(testValFeature);

		// Проверка CorrectionVal
		iterValFeature = mainFeature.getListValFeature().iterator();
		boolean testCorrectionVal = false;
		IValFeatureProvider valFeatureProvider = context.getBean(IValFeatureProvider.class);
		while (iterValFeature.hasNext()) {
			ValFeature v = iterValFeature.next();
			if (v.getName().equals(NAME_VALFEATURE)
					&& v.getMainFeature().equals(mainFeature)
					&& v.getForTag().equals(NAME_VALFEATURE_FOR_TAG)) {
				v=valFeatureProvider.init(v.getN());
				Iterator<CorrectionVal> iterCorrectionVal = v
						.getListCorrectionVal().iterator();
				while (iterCorrectionVal.hasNext()) {
					CorrectionVal _correctionVal = iterCorrectionVal.next();
					if (_correctionVal.getName().equals(
							NAME_VALFEATURE_FROM_SITE) && _correctionVal.getValFeature().equals(v)) {
						testCorrectionVal = true;
					}
				}
			}
		}
		assertTrue(testCorrectionVal);
		mainFeatureProvider.delete(mainFeature);
	}
}
