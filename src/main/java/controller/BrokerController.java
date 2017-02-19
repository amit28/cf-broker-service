package controller;

import model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import repository.ServiceRepository;

@RestController
public class BrokerController {

@Autowired
ServiceRepository serviceRepo;

@RequestMapping(value="/v2/catalog")
public Iterable<Service> catalog(){
	Iterable<Service> catalogs = serviceRepo.findAll();
	return catalogs;
}	
	
}
