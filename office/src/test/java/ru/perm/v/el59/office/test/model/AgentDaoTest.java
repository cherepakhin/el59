package ru.perm.v.el59.office.test.model;

import ru.el59.office.db.Agent;

public class AgentDaoTest extends DaoTest<Agent, String> {

	@Override
	protected String getNameDao() {
		return "agentDao";
	}

}
