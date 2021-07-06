package com.km.pofol.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.km.pofol.model.Blog;
import com.km.pofol.service.CrawlingService;

@RestController
public class CrawlingController {
	
	Gson gson = new Gson();
	
	@Autowired
	private CrawlingService crawService;
	
	@GetMapping("/")
	public String test() {
		return "hi";
	}
	
	@GetMapping("search")
	public String search(@RequestParam("food") String food) {
		List<Blog> res = crawService.craw(food);
		return gson.toJson(res);
	}
		
}
