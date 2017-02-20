package repository;

import java.io.Serializable;

import model.ServiceBinding;

import org.springframework.data.repository.CrudRepository;

public interface ServiceBindingRepository extends CrudRepository<ServiceBinding, Serializable> {

}
