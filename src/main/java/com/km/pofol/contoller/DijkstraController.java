package com.km.pofol.contoller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.km.pofol.model.City;
import com.km.pofol.model.Tourist;
import com.km.pofol.repository.DijkstraRepository;
import com.km.pofol.service.DijkstraService;

@RestController
@RequestMapping("/trip")
public class DijkstraController {

	@Autowired
	private DijkstraRepository dijkstraRepository;

	@Autowired
	private DijkstraService dijkstraService;
	
	Gson gson = new Gson();

	@GetMapping("/city")
	public String cityList() {
		List<City> cities = dijkstraRepository.findAllCity();
		return gson.toJson(cities);
	}

	@GetMapping("/tourist")
	public String touristList(@RequestParam("city") int city) {
		List<Tourist> tourists = dijkstraRepository.findTouristByCity(city);
		return gson.toJson(tourists);
	}

	@PostMapping("/course")
	public String course(@RequestBody Map<String, int[]> map) {
		Map<String,String> res = dijkstraService.course(map);
		return gson.toJson(res);
	}

}
