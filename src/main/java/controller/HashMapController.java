package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.HashService;

public class HashMapController {

	@Autowired
	HashService hashservice;

	@Autowired
	@RequestMapping(value = "/hashmap/{service_instance_id}", method = RequestMethod.PUT)
	public ResponseEntity<String> put(
			@PathVariable("service_instance_id") String serviceInstanceId,
			@RequestBody KeyValuePair kp) {
		hashservice.put(serviceInstanceId, kp.getKey(), kp.getValue());
		return new ResponseEntity<String>("{}", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/hahsmap/{service_instance_id}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(
			@PathVariable("service_instance_id") String serviceInstanceId,
			@RequestBody KeyValuePair kp) {
		Object value = hashservice.get(serviceInstanceId, kp.getKey());
		if (value != null) {
			return new ResponseEntity<Object>(value, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("{}", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/hashmap/{service_instance_id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(
			@PathVariable("service_instance_id") String serviceInstanceId,
			@RequestBody KeyValuePair kp) {
		Object value = hashservice.get(serviceInstanceId, kp.getKey());
		if (value != null) {
			hashservice.remove(serviceInstanceId, kp.getKey());
			return new ResponseEntity<String>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("{}", HttpStatus.GONE);
		}
	}
}
