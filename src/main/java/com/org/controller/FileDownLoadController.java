package com.org.controller;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileDownLoadController {
	Logger log=LoggerFactory.getLogger(FileDownLoadController.class);

	@PostMapping(value="/fileReceiver")
	public String fileReciver(@RequestParam("file-upload") MultipartFile[] files)
	{
		log.info("-----Received Request frm clinet---");
		
		if(files.length!=0)
		{
			for(MultipartFile file: files)
			{
				log.info("Original File Name :"+  file.getOriginalFilename());
				log.info("Original File Size :"+  file.getSize());
			}
			
			return "SUCCESS";
		}
		else
		{
			return "FAIL";
		}
		
	}
	// M
	@PostMapping(value="/fileReceiverStream")
	public String fileReceiverStream(InputStream stream) throws Exception
	{
		int k=stream.available();
		System.out.println("k-->" +k);
		return "SUCCESS";
		
	}
}
