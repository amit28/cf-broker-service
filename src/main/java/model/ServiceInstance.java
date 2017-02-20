package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "service_instances")
public class ServiceInstance {
	
	@Id
	private String id;
	
	@JsonProperty("service_id")
	@Column(nullable = false)
	private String serviceId;
	
	@JsonProperty("plan_id")
	@Column(nullable = false)
	private String planId;
	
	@JsonProperty("organisation_guid")
	@Column(nullable = false)
	private String organisationGuId;
	
	@JsonProperty("space_guid")
	@Column(nullable = false)
	private String spaceGuId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getOrganisationGuId() {
		return organisationGuId;
	}

	public void setOrganisationGuId(String organisationGuId) {
		this.organisationGuId = organisationGuId;
	}

	public String getSpaceGuId() {
		return spaceGuId;
	}

	public void setSpaceGuId(String spaceGuId) {
		this.spaceGuId = spaceGuId;
	}
}
