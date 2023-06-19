package com.ems.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/ems")
public class EmployeeDepartmentController {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping("/departmentslist")
	public ResponseEntity<List<Department>> getAllDepartments(){
		return new ResponseEntity<>(departmentRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/adddepartment")
	public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
		return new ResponseEntity<>(departmentRepository.save(department), HttpStatus.CREATED);
	}
	
	@GetMapping("/employeeslist")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/add-employee")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
			return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
	}
	
	@GetMapping("/employeebyid/{id}")
	public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable("id") int employeeId){
		Optional<Employee> emp=employeeRepository.findById(employeeId);
		if(emp==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			return new ResponseEntity<>(emp, HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/edit-employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int employeeId, @RequestBody Employee employee) {
		Employee emp=employeeRepository.getById(employeeId);
		
		if(emp==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			emp.setFirstName(employee.getFirstName());
			emp.setLastName(employee.getLastName());
			emp.setDateOfBirth(employee.getDateOfBirth());
			emp.setContactNo(employee.getContactNo());
			emp.setDepartment(employee.getDepartment());
			return new ResponseEntity<>(employeeRepository.save(emp), HttpStatus.CREATED);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete-employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") int employeeId){
		Employee employee= employeeRepository.getById(employeeId);
		if(employee==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			employee.setDepartment(null);
			employeeRepository.delete(employee);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
