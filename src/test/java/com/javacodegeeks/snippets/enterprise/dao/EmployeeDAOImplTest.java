package com.javacodegeeks.snippets.enterprise.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.javacodegeeks.snippets.enterprise.model.Employee;

@ContextConfiguration(locations = { "classpath*:/applicationContext-test.xml" })
public class EmployeeDAOImplTest extends AbstractTransactionalJUnit4SpringContextTests
{
	@Autowired
	private EmployeeDAO employeeDAO;

	@Before
	public void setUp() throws Exception
	{
		executeSqlScript("insert-data.sql",false);
	}

	@After
	public void tearDown() throws Exception
	{
		executeSqlScript("delete-data.sql",false);
	}

	@Test
	public void testPersistEmployee()
	{
		Employee dummyEmployee = buildEmployee();

		employeeDAO.persistEmployee(dummyEmployee);
		
		assertNotNull(employeeDAO.findEmployeeById(dummyEmployee.getId()));
	}

	@Test
	public void testFindEmployeeById()
	{
		assertNotNull(employeeDAO.findEmployeeById(1+""));
	}

	@Test
	public void testUpdateEmployee()
	{
		Employee dummyEmployee = buildEmployee();
		
		//update Age to 96
		dummyEmployee.setAge(96);
		
		employeeDAO.updateEmployee(dummyEmployee);
		
		assertTrue(employeeDAO.findEmployeeById(dummyEmployee.getId()).getAge() == 96);
	}

	@Test
	public void testDeleteEmployee()
	{
		employeeDAO.deleteEmployee(buildEmployee_delete());
		
		assertNull(employeeDAO.findEmployeeById(1+""));
	}

	private Employee buildEmployee()
	{
		Employee employee = new Employee();

		employee.setId(55+"");
		employee.setName("Test Employeee");
		employee.setAge(55);

		return employee;
	}
	
	private Employee buildEmployee_delete()
	{
		Employee employee = new Employee();

		employee.setId(1+"");
		employee.setName("Employee1");
		employee.setAge(10);

		return employee;
	}

}