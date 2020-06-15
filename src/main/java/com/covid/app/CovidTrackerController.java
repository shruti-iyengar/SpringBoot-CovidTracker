package com.covid.app;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CovidTrackerController {
	
	@Autowired
	CovidTrackerService covidTrackerService;
	
	@GetMapping("/")
	public String getGlobalCovidCount(Model model) throws MalformedURLException, IOException {
		
		model.addAttribute("covidStatistics", covidTrackerService.overallStatesData);
		return "CovidCount";
	}

}
