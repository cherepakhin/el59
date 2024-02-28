package ru.perm.v.el59.office.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ru.perm.v.el59.office.test.analisebest.DbfToBestTagConvertorTest;
import ru.perm.v.el59.office.test.analisebest.FormulaBestTagTest;
import ru.perm.v.el59.office.test.analisebest.ProtocolForTagTest;

@RunWith(Suite.class)
@SuiteClasses({ DbfToBestTagConvertorTest.class, ProtocolForTagTest.class,
		FormulaBestTagTest.class })
public class AllTests {

}
