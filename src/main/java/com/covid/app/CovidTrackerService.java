package com.covid.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.covid.app.common.CommonConstants;
import com.covid.app.models.StateWiseData;

@Service
public class CovidTrackerService {

	public static String COVID_DETAILS="https://github.com/CSSEGISandData/COVID-19/raw/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	public static String CSV_LOCAL_STORAGE_PATH="time_series_covid19_confirmed_global.csv";
	
	List<StateWiseData> overallStatesData=new ArrayList<StateWiseData>();
	
	@PostConstruct
	public void getCovidLatestCounts() throws MalformedURLException, IOException {
		BufferedInputStream inputStream = null ;
		try {
			inputStream = new BufferedInputStream(new URL(COVID_DETAILS).openStream());

			FileOutputStream fileOS = new FileOutputStream(CSV_LOCAL_STORAGE_PATH);
			byte data[] = new byte[1024];
			int byteContent;
			while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				fileOS.write(data, 0, byteContent);
			}
		} catch (Exception e) {
				    // handles IO exceptions
					System.out.println("Exception Occured "+e);
				}finally {
					inputStream.close();
				}
		BufferedReader in = new BufferedReader(new FileReader(CSV_LOCAL_STORAGE_PATH)); 
        Iterable<CSVRecord> records=CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        List <StateWiseData> stateWiseList=new ArrayList<StateWiseData>();
        for (CSVRecord record: records) {
        	StateWiseData stateWiseData= new StateWiseData();
        	stateWiseData.setCountry(record.get(CommonConstants.COUNTRY));
        	stateWiseData.setState(record.get(CommonConstants.STATE));
        	long todayCount=Long.parseLong(record.get(record.size()-1));
        	long previousDayCount=Long.parseLong(record.get(record.size()-2));
        	stateWiseData.setTodayCount(todayCount);
        	stateWiseData.setDeltaCount(todayCount-previousDayCount);
        	stateWiseList.add(stateWiseData);
  	
        }
        overallStatesData=stateWiseList;
	}
}
