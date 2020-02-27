package com.nisum.demo.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.nisum.demo.entity.EmployeeEntity;
import com.nisum.demo.exception.RecordNotFoundException;
import com.nisum.demo.repository.EmployeeRepository;
import com.nisum.demo.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@Before
	public void setUp() {
		
		//setup for findById()
		EmployeeEntity dummyEmployee=new EmployeeEntity(1L, "Lucky", "Lakshmi", "Lucky.Lakshmi@gmail.com");
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(dummyEmployee));
		
		//setup for findAll()
		List<EmployeeEntity> dummyEmployees=Arrays.asList(dummyEmployee);
		when(employeeRepository.findAll()).thenReturn(dummyEmployees);
		
		//setup for deleteById()
		doNothing().when(employeeRepository).deleteById(Mockito.anyLong());
		doThrow(RecordNotFoundException.class).when(employeeRepository).deleteById(100L);
		
		//setup for createOrUpdate(): create operation
		EmployeeEntity dummyCreateEmployee=new EmployeeEntity("Lucky", "Lakshmi", "Lucky.Lakshmi@gmail.com");
		when(employeeRepository.save(Mockito.any(EmployeeEntity.class))).thenReturn(dummyEmployee);
		
		//setup for createOrUpdate(): update operation
		EmployeeEntity dummyUpdateEmployee=new EmployeeEntity(1L, "Vara", "Lakshmi", "Vara.Lakshmi@gmail.com");
		when(employeeRepository.save(Mockito.any(EmployeeEntity.class))).thenReturn(dummyUpdateEmployee);
	}
	
	@Test
	public void createOrUpdate_CreateTest() {
		
		EmployeeEntity returnedEntity=employeeService.createOrUpdateEmployee(new EmployeeEntity("Lucky", "Lakshmi", "Lucky.Lakshmi@gmail.com"));
		assertThat(returnedEntity).isNotNull();
		assertThat(returnedEntity.getId()).isEqualTo(1L);
	}
	
	@Test
	public void createOrUpdate_UpdateTest() {
		
		EmployeeEntity returnedEntity=employeeService.createOrUpdateEmployee(new EmployeeEntity(1L,"Vara", "Lakshmi", "Vara.Lakshmi@gmail.com"));
		assertThat(returnedEntity).isNotNull();
		assertThat(returnedEntity.getId()).isEqualTo(1L);
		assertThat(returnedEntity.getFirstName()).isEqualTo("Vara");
	}
	
	@Test
	public void getEmployeeByIdTest() throws RecordNotFoundException {
		
		EmployeeEntity returnedEmployee=employeeService.getEmployeeById(1L);
		assertThat(returnedEmployee.getFirstName()).isEqualTo("Lucky");
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void getEmployeeByIdExceptionTest() throws RecordNotFoundException {
		
		employeeService.getEmployeeById(100L);
	}
	
	@Test
	public void getAllEmployeesTest() {
		
		List<EmployeeEntity> allEmployeesReturned=employeeService.getAllEmployees();
		assertThat(allEmployeesReturned).isNotEmpty();
		assertThat(allEmployeesReturned.size()).isEqualTo(1);
		assertThat(allEmployeesReturned.get(0)).isEqualTo(new EmployeeEntity(1L, "Lucky", "Lakshmi", "Lucky.Lakshmi@gmail.com"));
	}
	
	@Test
	public void getAllEmployeesEmptyTest() {
		
		List<EmployeeEntity> dummyEmptyEmployees=new ArrayList<EmployeeEntity>();
		when(employeeRepository.findAll()).thenReturn(dummyEmptyEmployees);
		
		List<EmployeeEntity> allEmployeesReturned=employeeService.getAllEmployees();
		assertThat(allEmployeesReturned).isEmpty();
		assertThat(allEmployeesReturned.size()).isEqualTo(0);
	} 
	
	@Test
	public void deleteEmployeeByIdTest() throws RecordNotFoundException {
		
		employeeService.deleteEmployeeById(1L);
		Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(1L);
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void deleteEmployeeByIdExceptionTest() throws RecordNotFoundException {
		
		employeeService.deleteEmployeeById(100L);
		Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(100L);;
	}
}
