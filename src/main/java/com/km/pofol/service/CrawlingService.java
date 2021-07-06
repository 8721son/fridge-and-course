package com.km.pofol.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.km.pofol.model.Blog;

@Service
public class CrawlingService {

	public List<Blog> craw(String keyword){
		String url = "https://search.naver.com/search.naver?&date_option=0&date_to=&dup_remove=1&nso=&query="+keyword+"&sm=tab_pge&srchby=all&st=sim&where=post";
		List<Blog> blogs = new ArrayList<Blog>();
		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements els = doc.select(".lst_total .bx");
			System.out.println(els.get(0).select(".sub_time").text());
			for (int i=0; i<30; i++) {
				Blog blog = new Blog();
				blog.setTitle(els.get(i).select(".total_tit").text());
				blog.setContent(els.get(i).select(".total_group .dsc_txt").text());
				blog.setThumbnail(els.get(i).select(".thumb_single .thumb").attr("src"));
				blog.setUrl(els.get(0).select(".total_tit").attr("href"));
				blog.setDate(els.get(0).select(".sub_time").text());
				blogs.add(blog);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return blogs;
	}
}
