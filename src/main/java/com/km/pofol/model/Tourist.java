package com.km.pofol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tourist {
	private int id;
	private int city;
	private String name;
	private double gpsX;
	private double gpsY;
	private String image;
}
