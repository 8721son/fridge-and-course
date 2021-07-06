package com.km.pofol.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.km.pofol.model.City;
import com.km.pofol.model.Tourist;

@Repository
public interface DijkstraRepository {
	List<City> findAllCity();
	List<Tourist> findTouristByCity(int city);
	Tourist findTouristById(int id);
}
