package de.kjaduesseldorf.sportsunite.server.resources.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kjaduesseldorf.sportsunite.server.dbi.DistrictDao;
import de.kjaduesseldorf.sportsunite.server.models.District;

@Path("/service/v1/district")
@Produces(MediaType.APPLICATION_JSON)
public class DistrictResource {
	
	private DistrictDao districtDao;

	public DistrictResource(DistrictDao districtDao) {
		super();
		this.districtDao = districtDao;
	}
	
	@GET
	public List<District> getAllDistricts() {
		return districtDao.getAllDistricts();
	}

}
