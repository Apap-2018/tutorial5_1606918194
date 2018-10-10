package com.apap.tutorial5.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;

/**
 * 
 * @author saffana.dira
 * CarController
 *
 */
@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).orElse(null);
		dealer.setListCar(new ArrayList<CarModel>());
		dealer.getListCar().add(new CarModel());
		
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Add Car");
		return "addCar";
	}
	 
	@RequestMapping(value="/car/add/{dealerId}", params={"add"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute DealerModel dealer, Model model) {
		dealer.getListCar().add(new CarModel());
		
	    model.addAttribute("dealer", dealer);
	    model.addAttribute("title", "Add Car");
	    return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"delete"}, method=RequestMethod.POST)
	public String removeRow(@ModelAttribute DealerModel dealer, Model model, HttpServletRequest req) {
		int indexRow = Integer.parseInt(req.getParameter("delete"));
		dealer.getListCar().remove(indexRow);
		
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Add Car");
		return "addCar";		
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", params={"save"}, method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute DealerModel dealer, Model model) {
		DealerModel currentDealer = dealerService.getDealerDetailById(dealer.getId()).orElse(null);
		for (CarModel car : dealer.getListCar()) {
			car.setDealer(currentDealer);
			carService.addCar(car);
		}
		
		model.addAttribute("title", "Updated Data");
		return "add";
	}
	
	@RequestMapping(value = "/car/delete/{carId}", method = RequestMethod.GET)
	private String deleteDealerSubmit(@PathVariable(value = "carId") String id, Model model) {
		Long dealerId = Long.parseLong(id);
		DealerModel updated = dealerService.getDealerDetailById(dealerId).orElse(null);
		List<CarModel> theCars = carService.allCars(updated);
		
		model.addAttribute("theCars", theCars);
		model.addAttribute("title", "Delete Car");
		
		return "deleteCar";
	}
	
	@RequestMapping(value = "/car/delete/id={carId}", method = RequestMethod.POST)
	private String deleteCarSubmit(@PathVariable(value = "carId") String id, Model model) {
		Long carId = Long.parseLong(id);
		CarModel deleted = carService.getCarDetailById(carId).orElse(null);
		
		carService.deleteCar(deleted);
		
		return "update";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(car);
		}
		
		model.addAttribute("title", "Deleted Car");
		return "update";	
	}
	
	@RequestMapping(value = "/car/update/{dealerId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "dealerId") String id, Model model) {
		Long dealerId = Long.parseLong(id);
		DealerModel updated = dealerService.getDealerDetailById(dealerId).orElse(null);
		List<CarModel> theCars = carService.allCars(updated);
		
		model.addAttribute("theCars", theCars);
		model.addAttribute("title", "Update Car");
		
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update/id={carId}", method = RequestMethod.POST)
	private String updateCarSubmit(@PathVariable(value = "carId") String id, @ModelAttribute CarModel car) {
		Long carId = Long.parseLong(id);
		CarModel updated = carService.getCarDetailById(carId).orElse(null);
	
		updated.setBrand(car.getBrand());
		updated.setType(car.getType());
		updated.setPrice(car.getPrice());
		updated.setAmount(car.getAmount());
		
		carService.addCar(updated);
		
		return "update";
	}
}