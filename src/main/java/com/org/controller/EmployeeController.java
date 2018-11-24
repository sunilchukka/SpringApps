package com.org.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.hibernate.result.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.org.model.Employee;
import com.org.service.EmployeeServiceI;

@RestController
public class EmployeeController {
	Logger log = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeServiceI employeeServcie;

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public String saveEmployee(@RequestBody Employee employee) {
		Employee e1 = employeeServcie.saveEmployee(employee);
		log.info("employee object at controller-->" + e1);
		return e1 != null ? "SUCCESS" : "FAIL";
	}

	@RequestMapping(value = "/saveEmployees", method = RequestMethod.POST)
	public String saveAllEmployee(@RequestBody List<Employee> employees) {
		return !employeeServcie.saveAllEmployees(employees).isEmpty() ? "SUCCESS" : "FAIL";
	}

	@PostMapping(value = "/fileUploadExmp")
	public String fileUpload(@RequestParam("file") MultipartFile file) {
		// Setting the headers
		if (!file.isEmpty()) {
			log.info("File Name: " + file.getName());
			log.info("File Size: " + file.getSize());
		}
		log.info("---Rest Template Starts----");
		// HttpHeaders headers =new HttpHeaders();
		try {
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
			String url = "http://localhost:8089/fileReceiver";
			// headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> reqBody = employeeServcie.getFilesData();
			// body.add("file-upload", employeeServcie.getFileData());
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
					reqBody, headers);
			RestTemplate template = new RestTemplate();
			ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, requestEntity,
					String.class);
			log.info("out from the service-->" + responseEntity.getBody());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "SUCCESS";
	}

	@GetMapping(value = "/fileUploadExmpWithStram")
	public String fileUpLoadWithStream() {
		List<Future<String>> outPut=new ArrayList<>();
		try {
			
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
			String url = "http://localhost:8089/fileReceiverStream";
			List<byte[]> reqBody = employeeServcie.getFilesDataForStream();
			ExecutorService executor = Executors.newFixedThreadPool(5);
			reqBody.forEach(element -> {
				Callable<String> thread = new Callable<String>() {
					@Override
					public String call() throws Exception {
						RestTemplate template = new RestTemplate();
						HttpEntity<byte[]> reqEntity = new HttpEntity<>(element, headers);
						ResponseEntity<String> response=template.exchange(url, HttpMethod.POST, reqEntity, String.class);
						return response.getBody();
					}

				};
				outPut.add(executor.submit(thread));
			});

		} catch (Exception e) {
			// TODO: handle exception
		}
		// To check all responses are Success or failure
		outPut.stream().forEach(action-> {
			try {
				System.out.println(action.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		return "SUCCESS";
	}
}
