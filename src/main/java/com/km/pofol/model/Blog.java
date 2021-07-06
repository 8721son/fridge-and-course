package com.km.pofol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
	private String title;
	private String content;
	private String url;
	private String thumbnail;
	private String date;
}
