package com.km.pofol.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.km.pofol.model.Tourist;
import com.km.pofol.repository.DijkstraRepository;

@Service
public class DijkstraService {
	
	@Autowired
	private DijkstraRepository dijkstraRepository;
	
	public Map<String,String> course(Map<String, int[]> map){
		List<Tourist> tourists = new ArrayList<Tourist>();
		double total = 0;
		Map<String,String> res = new HashMap<String, String>();
		String courseToString="";
		for (int i : map.get("tours")) {
			tourists.add(dijkstraRepository.findTouristById(i));
		}

		int cnt = tourists.size();
		double[][] distance = new double[cnt][cnt];

		for (int i = 0; i < tourists.size(); i++) {
			for (int j = 0; j < tourists.size(); j++) {
//				if(i==j) {
//					distance[i][j]=Integer.MAX_VALUE;
//				}else {
				distance[i][j] = distance(tourists.get(i).getGpsX(), tourists.get(i).getGpsY(),
						tourists.get(j).getGpsX(), tourists.get(j).getGpsY(), "kilometer");
				//}
			}
		}

		// double distanceKiloMeter = distance(37.504198, 127.047967, 37.501025,
		// 127.037701, "kilometer");

//		for (int i = 0; i < distance.length; i++) {
//			for (int j = 0; j < distance.length; j++) {
//				System.out.print(distance[i][j] + " ");
//			}
//			System.out.println();
//		}
		int[] course = res(cnt,distance);
		
		for (int i = 0; i < course.length-1; i++) {
			int fir = course[i];
			int sec = course[i+1];
			total+=distance[fir][sec];
		}
		for (int j = 0; j < course.length; j++) {
			courseToString+=tourists.get(course[j]).getName() + " > ";
		}
		res.put("distance", total+"");
		res.put("course", courseToString);
		return res;
	}
	
	public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		if (unit == "kilometer") {
			dist = dist * 1.609344;
		} else if (unit == "meter") {
			dist = dist * 1609.344;
		}

		return (dist);
	}

	// This function converts decimal degrees to radians
	public double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	public double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public int[] res(int cnt, double[][] array) {
		int start = 0;
		double[] distance = new double[cnt];
		double[] path = new double[cnt];
		boolean[] visited = new boolean[cnt];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[start] = 0;
		int[] course = new int[cnt];
		
		
		Map<String,Object> res = new HashMap<String, Object>();
		
//		for (int i = 0; i < array.length; i++) {
//			for (int j = 0; j < array.length; j++) {
//				System.out.print(array[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		double min = Integer.MAX_VALUE;
		int minIndex = 0;
	
		for (int i = 0; i < cnt; ++i) {
			min = Integer.MAX_VALUE;
			// step1. 미방문정점 중 최적비용이 최소인 정점 찾기
			for (int j = 0; j < cnt; ++j) {
				if (!visited[j] && distance[j] < min) {
					min = distance[j];
					minIndex = j;
				}
			}
			
			// step2. 선택정점 방문하고 경유지로 고려하여
			// 미방문정점들로 가는 최적비용을 최적화
			visited[minIndex] = true;
//			for (int j = 0; j < visited.length; j++) {
//				System.out.print(visited[j]+" ");
//			}
//			System.out.println();
			course[i] = minIndex;
			for (int j = 0; j < cnt; ++j) {
				if (!visited[j] && array[minIndex][j] != 0 && min + array[minIndex][j] < distance[j]) {
					distance[j] = min + array[minIndex][j];
					path[j] = minIndex;
				}
			}

		}
	
//		System.out.println(total);
//		for (int i = 0; i <	course.length; i++) {
//			System.out.print(course[i] + " ");
//		}
//		System.out.println();
//		System.out.println(total);
		
		return course;
	}
}
