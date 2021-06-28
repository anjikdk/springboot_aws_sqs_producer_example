package com.example.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.entity.EmployeeEntity;
import com.example.springboot.exception.DataNotFoundException;
import com.example.springboot.repository.EmployeeRepository;
import com.example.springboot.resource.EmployeeResource;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public EmployeeResource saveEmp(EmployeeResource employeeResource) {

		EmployeeEntity employeeEntity = convertFromEmpResourceToEmpEntity(employeeResource);

		employeeEntity = employeeRepository.save(employeeEntity);
//		employeeResource = convertFromEmpEntityToEmpResource(employeeEntity);
		
		BeanUtils.copyProperties(employeeEntity, employeeResource);
		
		return employeeResource;
	}

	private EmployeeEntity convertFromEmpResourceToEmpEntity(EmployeeResource employeeResource)
	{
		EmployeeEntity employeeEntity = new EmployeeEntity();

		employeeEntity.setAddress(employeeResource.getAddress());
		employeeEntity.setAddress(employeeResource.getAddress());
		employeeEntity.setFname(employeeResource.getFname());
		employeeEntity.setLname(employeeResource.getLname());
		employeeEntity.setSalary(employeeResource.getSalary());
		
		return employeeEntity;
	}
	
	private EmployeeResource convertFromEmpEntityToEmpResource(EmployeeEntity employeeEntity)
	{
		EmployeeResource employeeResource = new EmployeeResource();

		employeeResource.setEmpId(employeeEntity.getEmpId());
		employeeResource.setAddress(employeeEntity.getAddress());
		employeeResource.setAddress(employeeEntity.getAddress());
		employeeResource.setFname(employeeEntity.getFname());
		employeeResource.setLname(employeeEntity.getLname());
		employeeResource.setSalary(employeeEntity.getSalary());
		
		return employeeResource;
	}

	@Override
	public EmployeeResource getEmployeeDetailsById(Integer empId) {
		Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(empId);
		EmployeeResource employeeResource = null;
		if(employeeEntity.isPresent())
		{
			employeeResource = convertFromEmpEntityToEmpResource(employeeEntity.get());
		}
		else
		{
			throw new DataNotFoundException("Employee Data is not available for given Id: " + empId);
		}
		
		return employeeResource;
	}

	@Override
	public List<EmployeeResource> getEmployeeDetailsByFnameOrLname(String name) {
		List<EmployeeEntity> empList = employeeRepository.findByFnameOrLname(name, name);
		List<EmployeeResource> empListResources = new ArrayList<EmployeeResource>();
		
		for(EmployeeEntity employeeEntity : empList)
		{
			empListResources.add(convertFromEmpEntityToEmpResource(employeeEntity));
		}
		
		return empListResources;
	}

	@Override
	public void deleteEmp(Integer empId) {
		
		Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(empId);
		if(employeeEntity.isPresent())
		{
			employeeRepository.delete(employeeEntity.get());
		}
		else
		{
			throw new DataNotFoundException("Employee Data is not available for given Id: " + empId);
		}
	}

	@Override
	public List<EmployeeResource> getEmployeeDetailsByFname(String fname) {
		
		List<EmployeeEntity> empList = employeeRepository.findEmpDetailsByFname(fname);
		List<EmployeeResource> empListResources = new ArrayList<EmployeeResource>();
		
		for(EmployeeEntity employeeEntity : empList)
		{
			empListResources.add(convertFromEmpEntityToEmpResource(employeeEntity));
		}
		
		return empListResources;
	}
	
	@Override
	public List<EmployeeResource> getEmployeeDetailsByLname(String lname) {
		
		List<EmployeeEntity> empList = employeeRepository.findEmpDetailsByLname(lname);
		List<EmployeeResource> empListResources = new ArrayList<EmployeeResource>();
		
		for(EmployeeEntity employeeEntity : empList)
		{
			empListResources.add(convertFromEmpEntityToEmpResource(employeeEntity));
		}
		
		return empListResources;
	}
}
