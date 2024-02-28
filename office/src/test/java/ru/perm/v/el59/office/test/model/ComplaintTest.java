package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.db.service.Complaint;
import ru.el59.office.iproviders.service.IComplaintProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext-test.xml")
public class ComplaintTest {
	
	@Autowired
	private IComplaintProvider complaintDao;

	@Test
	public void testComplaintAction() {
		List<Complaint> list = complaintDao.getByLikeName("");
		for (Complaint complaint: list) {
			System.out.println(complaint);
		}
		assertTrue(list.size()>0);
	}

}
