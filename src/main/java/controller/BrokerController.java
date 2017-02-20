package controller;

import java.util.List;
import java.util.UUID;

import model.Credentials;
import model.Service;
import model.ServiceBinding;
import model.ServiceInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import repository.ServiceBindingRepository;
import repository.ServiceInstanceRepository;
import repository.ServiceRepository;
import service.HashService;

@RestController
public class BrokerController {

	@Autowired
	ServiceRepository serviceRepo;

	@Autowired
	ServiceInstanceRepository serviceInstanceRepo;

	@Autowired
	ServiceBindingRepository serviceBindingRepository;

	@Autowired
	HashService hashService;

	@Autowired
	Cloud cloud;

	@Autowired
	Environment env;

	@RequestMapping(value = "/v2/catalog", method = RequestMethod.GET)
	public Iterable<Service> catalog() {
		Iterable<Service> catalogs = serviceRepo.findAll();
		return catalogs;
	}

	@RequestMapping(value = "/v2/service_instances/{instance_id}", method = RequestMethod.PUT)
	public ResponseEntity<String> put(
			@PathVariable("instance_id") String instanceId,
			@RequestBody ServiceInstance serviceInstance) {

		serviceInstance.setId(instanceId);
		ServiceInstance existing = serviceInstanceRepo.findOne(instanceId);
		if (existing != null) {
			if (existing.equals(serviceInstance)) {
				return new ResponseEntity<String>("{}", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{}", HttpStatus.CONFLICT);
			}
		} else {
			serviceInstanceRepo.save(serviceInstance);
			hashService.create(instanceId);
			return new ResponseEntity<String>("{}", HttpStatus.CREATED);
		}

	}

	@RequestMapping(value = "/v2/service_instances/{instance_id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteInstance(
			@PathVariable("isntance_id") String instanceId,
			@RequestParam("service_id") String serviceId,
			@RequestParam("plan_id") String planId) {
		if (serviceInstanceRepo.equals(instanceId)) {
			serviceInstanceRepo.delete(instanceId);
			hashService.delete(instanceId);
			return new ResponseEntity<String>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("{}", HttpStatus.GONE);
		}
	}

	@RequestMapping(value = "/v2/service_instances/{instance_id}/service_bindings/{binding_id}", method = RequestMethod.POST)
	public ResponseEntity<Object> createBinding(
			@PathVariable("instance_id") String instanceId,
			@PathVariable("binding_id") String bindingId,
			@RequestBody ServiceBinding serviceBinding) {
		if (!serviceInstanceRepo.exists(instanceId)) {
			return new ResponseEntity<Object>("{}", HttpStatus.BAD_REQUEST);
		}
		serviceBinding.setId(instanceId);
		serviceBinding.setServiceId(bindingId);
		if (serviceBindingRepository.exists(instanceId)) {
			ServiceBinding existing = serviceBindingRepository
					.findOne(instanceId);
			if (existing.equals(serviceBinding)) {
				return new ResponseEntity<Object>("{}", HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("{}", HttpStatus.CONFLICT);
			}
		} else {
			Credentials credential = new Credentials();
			credential.setId(UUID.randomUUID().toString());
			credential.setUsername(env.getProperty("username"));
			credential.setPassword(env.getProperty("password"));
			ApplicationInstanceInfo appInstance = cloud
					.getApplicationInstanceInfo();
			List<Object> uriList = (List<Object>) appInstance.getProperties()
					.get("uris");
			credential.setUri("https://" + uriList.get(0) + "/hashService/"
					+ instanceId);
			serviceBinding.setCredentials(credential);
			serviceBindingRepository.save(serviceBinding);
			return new ResponseEntity<Object>("{}", HttpStatus.CREATED);

		}

	}

	@RequestMapping(value = "/v2/service_instances/{instance_id}/service_bindings/{binding_id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBinding(
			@PathVariable("instance_id") String instanceId,
			@PathVariable("binding_id") String bindingId,
			@RequestParam("service_id") String serviceId,
			@RequestParam("plan_id") String planId) {
		if (serviceBindingRepository.exists(bindingId)) {
			serviceBindingRepository.delete(bindingId);
			return new ResponseEntity<String>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("{}", HttpStatus.GONE);
		}
	}

}
