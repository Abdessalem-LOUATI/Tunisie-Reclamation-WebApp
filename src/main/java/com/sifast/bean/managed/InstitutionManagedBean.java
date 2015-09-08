package com.sifast.bean.managed;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;
import com.sifast.model.Institution;
import com.sifast.service.IInstitutionService;

@ManagedBean(name = "institMB")
@SessionScoped
public class InstitutionManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1346687999185954565L;
	static final Logger logger = Logger.getLogger(InstitutionManagedBean.class);
	
	private Institution institution = new Institution();
	private List<Institution> listInstitution;
	private List<Institution> listInstitutionFiltred;

	@ManagedProperty(value = "#{InstitutionService}")
	private IInstitutionService institutionService;

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Institution> getListInstitution() {
		if (listInstitution == null)
		{
			listInstitution = getInstitutionService().getListInstitution();
		}
		return listInstitution;
	}

	public IInstitutionService getInstitutionService() {
		return institutionService;
	}

	public void setInstitutionService(IInstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	public List<Institution> getListInstitutionFiltred() {
		return listInstitutionFiltred;
	}

	public void setListInstitutionFiltred(List<Institution> listInstitutionFiltred) {
		this.listInstitutionFiltred = listInstitutionFiltred;
	}

	public void onRowEdit(RowEditEvent event) {
		Institution institutionSelected = (Institution) event.getObject();
		logger.debug("Institution Edited Email: " + institutionSelected.getMailInstit());
		getInstitutionService().updateInstitution(institutionSelected);
		// supprimer la list des Institutions afin de la recharger lors de l'appel de la méthode "getListInstitution()"
		listInstitution = null;
	}

	public void onRowCancel(RowEditEvent event) {
		logger.debug("Institution Cancelled Email: " + ((Institution) event.getObject()).getMailInstit());
	}

	public void deleteInstitution(Institution institution)
	{
		try
		{
			getInstitutionService().deleteInstitution(institution);
			listInstitution = null;
		} catch (DataAccessException e)
		{
			logger.debug(e.getMessage());
		}
	}

	public void addInstitution()
	{
		logger.debug("bonjour institution: " + institution);
		getInstitutionService().insertInstitution(institution);
		institution.setNomInstit("");
		institution.setTelInstit(0);
		institution.setFaxInstit(null);
		institution.setMailInstit("");
		institution.setIpServeur("");
		listInstitution = null;
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}
}