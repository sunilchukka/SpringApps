package com.att.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.att.Model.Chanel;

@RestController
public class FileRead {
	@RequestMapping(value="/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> downlaodExcel() throws IOException {
		String FILE_NAME = "SourceCompare.xlsx";
		String path1="TestFile1.log";
		String path2="TestFile2.log";
		List<String> startTimeList=new ArrayList<>();
		List<String> gpIdList=new ArrayList<>();
		List<String> chanelList=new ArrayList<>();
		List<String> dtBroswerSdkTestRun=new ArrayList<>();
		List<List<String>> lists = new ArrayList<>();
		Set<String> sPlayerIdSet=new LinkedHashSet<>();
		
		// Reads data from execSourceCompare_01282018_2108. In this case it is NewFile.txt
		List<String> fileData = FileUtils.readLines(new File(path1));
		for (String data : fileData) {
			if (data.startsWith("796C9D0A")) {
				gpIdList.add(data);
			}
			else if(data.startsWith("Recording Destination File:"))
			{
				getChanelAndDate(data,chanelList,startTimeList);
			}
			
		}
		List<String> fileData2=FileUtils.readLines(new File(path2));
		for(String data2 : fileData2)
		{
			
			if(data2.startsWith("./HttpArchives"))
			{
				dtBroswerSdkTestRun.add(processDtOfBroswerTestRun(data2));
				processDtOfBroswerTestRun(data2);
			}
			if(data2.endsWith("=="))
			{
				int val=data2.trim().indexOf(" ");
				sPlayerIdSet.add(data2.substring(0, val).trim());
			}
		}
		List<String> sPlayerIdList=new ArrayList<>(sPlayerIdSet);
		List<String> headers=new ArrayList<>();
		headers.add("Start Time");
		headers.add("Chanel");
		headers.add("GPID");
		headers.add("Date of broswer SDK test run");
		headers.add("SplayerId");
		lists.add(headers);
		// Preparing Excel data.
		for(int i=0; i<gpIdList.size();i++)
		{
			List<String> cell=new ArrayList<>();
			cell.add(startTimeList.get(i));
			cell.add(chanelList.get(i));
			cell.add(gpIdList.get(i));
			cell.add(dtBroswerSdkTestRun.get(i));
			cell.add(sPlayerIdList.get(i));
			lists.add(cell);
		}
		createExcelFile(lists,FILE_NAME );
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	public void createExcelFile(List<List<String>> lists, String FILE_NAME) {
		// TODO Auto-generated method stub
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		SXSSFSheet sheet = workbook.createSheet("Sheet1");
		CellStyle style = workbook.createCellStyle();// Create style
		Font font = workbook.createFont();// Create font
		font.setBold(true); // Make font bold
		font.setFontName("CALIBRI");
		style.setFont(font);// set it to bold
		int rowCount = -1;
		for (List<String> aBook : lists) {
			Row row = sheet.createRow(++rowCount);
			int columnCount = -1;
			for (Object field : aBook) {
				Cell cell = row.createCell(++columnCount);

				if (rowCount == 0) {
					if (field instanceof String) {
						cell.setCellStyle(style);
						cell.setCellValue((String) field);
					} else if (field instanceof Integer) {
						cell.setCellStyle(style);
						cell.setCellValue((Integer) field);
					}
				}

				else if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
		}
		try {
			FileOutputStream output=new FileOutputStream(FILE_NAME);
			workbook.write(output);
			workbook.close();
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public String processDtOfBroswerTestRun(String data2) {
		// TODO Auto-generated method stub
		String s3=data2.substring(data2.indexOf("_")+1, data2.lastIndexOf("_")).split("_")[1];
		String date=s3.split("-")[0];
		String timeStamp=s3.split("-")[1];
		String formatedDate=dateFormatter(date);
		String time=timeFormatter(timeStamp);
		return formatedDate+" "+ time;
	}
	public void getChanelAndDate(String data, List<String> chanelList, List<String> dateList) {
		String subString=data.substring(data.indexOf("_")+1, data.length()-4);
		 String date=subString.split("_")[0].split("-")[0];  // 01282018
	     String time=subString.split("_")[0].split("-")[1];  // 2109 
	     String formatedDate=dateFormatter(date);
	     String timeFormatter=timeFormatter(time);
	     String finalDate=formatedDate+" "+timeFormatter;
	     dateList.add(dateManipulation(finalDate));
	    // 8_ESPN_1
	       String tail=subString.split("_",2)[1];  // 8_ESPN_1
	       System.out.println(tail);
	       // ESPN_1
	      String endValue=tail.split("_",2)[1].replace("_", "");  // ESPN1
	      System.out.println("endValue-->"+ endValue);
       chanelList.add(endValue);
	}

	public  String  dateFormatter(String date) {
		// TODO Auto-generated method stub
		String finalDate="";
		if(date!=null && !date.isEmpty()  && date.length()==8)
		{
			String s1=date.substring(0,2);
			String s2=date.substring(2, 4);
			String s3=date.substring(4, 8);
			finalDate=s1+"/"+s2+"/"+s3;
		}
		return finalDate;
		
	}
	// 2109 input
	// 21:09:00  output
	public String timeFormatter(String timeStamp) {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder(timeStamp);
		return sb.substring(0,2)+":"+sb.substring(2,4)+ ":"+ "00";
	}
	//01/28/2018 21:09:00
	// Sun, 28 Jan 2018 21:09:00
	public String dateManipulation(String date) {
		// TODO Auto-generated method stub
		String formatedDate = "";
		DateFormat format1=new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		DateFormat format2=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		try {
			Date d=format1.parse(date);
		 formatedDate=format2.format(d);
		System.out.println("date1-->"+ formatedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatedDate;
	}
	

}
