package com.apap.tutorial5.service;

import java.util.List;
import java.util.Optional;

//import java.util.List;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;

/**
 * 
 * @author saffana.dira
 * CarService
 *
 */
public interface CarService {
	Optional<CarModel> getCarDetailById(long id);
	
	void addCar(CarModel car);
	void deleteCar(CarModel car);
	List<CarModel> allCars(DealerModel dealer);
}