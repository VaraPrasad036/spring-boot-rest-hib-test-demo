package com.nisum.demo.repository.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nisum.demo.entity.EmployeeEntity;
import com.nisum.demo.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
//@DataJpaTest //to autowire TestEntityManager this annon is mandatory
@SpringBootTest //to test DAO normally with H2 DB and data.sql script
public class EmployeeRepositoryTest {

	//@Autowired
	//private TestEntityManager entityManager;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//private EmployeeEntity employeeStored;
	private Optional<EmployeeEntity> employeeReturned;
	
	//manual data insertion method to test repository with H2 (without data.sql script)
	/*
	 * @Before public void beforeClass() {
	 * 
	 * //inserting test data to unit test EmployeeRepo //employeeStored=new
	 * EmployeeEntity(10L, "Vara", "Lakshmi", "Vara.Lakshmi@nisum.com");
	 * employeeStored=new EmployeeEntity("Vara", "Lakshmi",
	 * "Vara.Lakshmi@nisum.com"); entityManager.persist(employeeStored);
	 * entityManager.flush(); }
	 */
	 
	
	@Test
	public void testFindById() {
		
		//employeeReturned=employeeRepository.findById(employeeStored.getId());
		//assertThat(employeeReturned.get().getFirstName()).isEqualTo(employeeStored.getFirstName());
		
		employeeReturned=employeeRepository.findById(1L);
		System.out.println("employee id::::"+employeeReturned.get().getId());
		assertThat(employeeReturned).isNotNull();
		assertThat(employeeReturned).isNotEmpty();
		assertThat(employeeReturned.get().getFirstName()).isEqualTo("Vara");
		
	}
	
	@Test
	public void testFindAll() {
		List<EmployeeEntity> employeesReturned=employeeRepository.findAll();
		assertThat(employeesReturned.size()).isEqualTo(5);
	}
	
	/*
	 * @After public void afterClass() {
	 * 
	 * entityManager=null; employeeRepository=null; }
	 */
}
