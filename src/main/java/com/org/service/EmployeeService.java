package com.org.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.org.model.Employee;
import com.org.repository.EmployeeRepository;

@Service
public class EmployeeService implements EmployeeServiceI{
	public static final String path="D:\\Files/";
	Logger log=LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public Employee saveEmployee(Employee e) {
		// TODO Auto-generated method stub
		Employee e1=employeeRepository.save(e);
		System.out.println("e1-->"+ e1);
		return e1;
	}
	@Override
	public List<Employee> saveAllEmployees(List<Employee> e) {
		// TODO Auto-generated method stub
		Iterable<Employee> employees=e;
		Iterable<Employee> empItrl=employeeRepository.saveAll(employees);
		return (List<Employee>) empItrl;
	}
	@Override
	public Resource getFileData() {
		// TODO Auto-generated method stub
		File file=new File("E:\\GoodUrls.txt");
		Resource r=new FileSystemResource(file);
		return r;
	}
	@Override
	public MultiValueMap<String, Object> getFilesData() {
		// TODO Auto-generated method stub
		MultiValueMap<String, Object> multiValueMap=new LinkedMultiValueMap<>();
		try {
			Path path=Paths.get(EmployeeService.path);
			Files.list(path).forEach(element->{
				log.info("file path: " +element.getFileName().toString());
				String pathToBuild=EmployeeService.path+element.getFileName().toString();
				log.info("Final Path : " +pathToBuild);
				File file=new File(pathToBuild);
				multiValueMap.add("file-upload", new FileSystemResource(file));
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return multiValueMap;
	}
	@Override
	public List<byte[]> getFilesDataForStream() {
		// TODO Auto-generated method stub
		List<byte[]> listOfData=new ArrayList<>();
		try {
			Path path=Paths.get(EmployeeService.path);
			Files.list(path).forEach(e->{
				try {
					System.out.println(e.toString());
					listOfData.add(Files.readAllBytes(e));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		return listOfData;
	}

}
