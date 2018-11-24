package com.org.service;

import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.util.MultiValueMap;

import com.org.model.Employee;

public interface EmployeeServiceI {
public Employee saveEmployee(Employee e);
public List<Employee> saveAllEmployees(List<Employee> e);
public Resource getFileData();
public MultiValueMap<String, Object> getFilesData();
public List<byte[]> getFilesDataForStream();
}
