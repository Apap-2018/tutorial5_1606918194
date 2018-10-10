package com.apap.tutorial5.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.repository.CarDb;

/**
 * 
 * @author saffana.dira
 * CarServiceImpl
 *
 */

@Service
@Transactional
public class CarServiceImp implements CarService {
	@Autowired
	private CarDb carDb;
	
	@Override
	public Optional<CarModel> getCarDetailById(long id) {
		return carDb.findById(id);
	}
	
	@Override
	public void addCar(CarModel car) {
		carDb.save(car);
	}
	
	@Override
	public void deleteCar(CarModel car) {
		carDb.delete(car);
	}
	
	@Override
	public List<CarModel> allCars(DealerModel dealer) {
		List<CarModel> cars = carDb.findAll();
		return cars.stream().filter(car -> car.getDealer().getId() == dealer.getId()).collect(Collectors.toList());
	}
}
