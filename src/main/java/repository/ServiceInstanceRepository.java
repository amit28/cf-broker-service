package repository;

import java.io.Serializable;

import model.ServiceInstance;

import org.springframework.data.repository.CrudRepository;

public interface ServiceInstanceRepository extends CrudRepository<ServiceInstance, Serializable>{

}
