package com.sifast.jersey.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.sifast.model.Institution;
import com.sifast.model.TypeReclamation;
import com.sifast.service.IInstitutionService;
import com.sifast.service.ITypeReclamationService;

@Path("/institution")
public class InstitutionRestServices {
	
	static final Logger logger = Logger.getLogger(InstitutionRestServices.class);
	private static final int STATUS_OK = 200;

	@Autowired
	private IInstitutionService institutionService;

	@Autowired
	private ITypeReclamationService typeReclamationService;

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listInstitution() {

		logger.debug("instit service IoC: " + institutionService);
		List<Institution> listInstitution = institutionService.getListInstitution();
		for (Institution institution : listInstitution) {
			institution.setAgents(null);
			institution.setTypeReclamations(null);
		}
		return Response.status(STATUS_OK).entity(listInstitution).build();
	}
	
	@GET
	@Path("/listNomInstitution")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listNomInstitution() {

		logger.debug("instit service IoC: " + institutionService);
		List<String> listNomInstitution = new ArrayList<>();
		List<Institution> listInstitution = institutionService.getListInstitution();
		for (Institution institution : listInstitution) {
			listNomInstitution.add(institution.getNomInstit());
		}
		return Response.status(STATUS_OK).entity(listNomInstitution).build();
	}

	@GET
	@Path("/typereclamation/{nomInstit}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listTypeReclamation(@PathParam("nomInstit") String nomInstit) {
		List<TypeReclamation> listTypeReclamation = typeReclamationService.getListTypeReclamationByInstitution(nomInstit);
		List<String> listType = new ArrayList<>();
		for (TypeReclamation typeReclamation : listTypeReclamation) {
			listType.add(typeReclamation.getType());
		}
		return Response.status(STATUS_OK).entity(listType).build();
	}
}