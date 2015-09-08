package com.sifast.bean.managed;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;
import com.sifast.model.Admin;
import com.sifast.model.Agent;
import com.sifast.model.Institution;
import com.sifast.model.TypeReclamation;
import com.sifast.service.IInstitutionService;
import com.sifast.service.ITypeReclamationService;

@ManagedBean(name = "typeReclamMB")
@SessionScoped
public class TypeReclamationManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4653688074217199252L;
	static final Logger logger = Logger.getLogger(TypeReclamationManagedBean.class);
	
	private TypeReclamation typeReclamation = new TypeReclamation();
	private List<TypeReclamation> listTypeReclamation;
	private List<TypeReclamation> listTypeReclamationFiltred;
	private List<Institution> listInstitution;
	private String nomInstit;
	private Admin admin;
	private Agent agent;

	@ManagedProperty(value = "#{TypeReclamationService}")
	private ITypeReclamationService typeReclamationService;

	@ManagedProperty(value = "#{InstitutionService}")
	private IInstitutionService institutionService;

	public IInstitutionService getInstitutionService() {
		return institutionService;
	}

	public void setInstitutionService(IInstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	public ITypeReclamationService getTypeReclamationService() {
		return typeReclamationService;
	}

	public void setTypeReclamationService(ITypeReclamationService typeReclamationService) {
		this.typeReclamationService = typeReclamationService;
	}

	public List<Institution> getListInstitution() {
		if (agent != null)
		{
			listInstitution = getInstitutionService().getListInstitutionByName(agent.getInstitution().getNomInstit());
		}
		else
		{
			listInstitution = getInstitutionService().getListInstitution();
		}
		logger.debug("Instit: " + listInstitution.size());
		return listInstitution;
	}

	public void setListInstitution(List<Institution> listInstitution) {
		this.listInstitution = listInstitution;
	}

	public String getNomInstit() {
		return nomInstit;
	}

	public void setNomInstit(String nomInstit) {
		this.nomInstit = nomInstit;
	}

	public TypeReclamation getTypeReclamation() {
		return typeReclamation;
	}

	public void setTypeReclamation(TypeReclamation typeReclamation) {
		this.typeReclamation = typeReclamation;
	}

	public List<TypeReclamation> getListTypeReclamationFiltred() {
		return listTypeReclamationFiltred;
	}

	public void setListTypeReclamationFiltred(List<TypeReclamation> listTypeReclamationFiltred) {
		this.listTypeReclamationFiltred = listTypeReclamationFiltred;
	}

	public List<TypeReclamation> getListTypeReclamation() {
		if (agent != null)
		{
			if (listTypeReclamation == null)
			{
				listTypeReclamation = getTypeReclamationService().getListTypeReclamationByInstitution(agent.getInstitution().getNomInstit());
			}
		} else
		{
			if (listTypeReclamation == null)
			{
				listTypeReclamation = getTypeReclamationService().getListTypeReclamation();
			}
		}

		return listTypeReclamation;
	}

	public void onRowEdit(RowEditEvent event) {
		TypeReclamation typeReclamationtSelected = (TypeReclamation) event.getObject();
		logger.debug("Type Réclamation Edited Nom instit: " + typeReclamationtSelected.getInstitution().getNomInstit());
		Institution institutionSelected = getInstitutionService().getInstitutionByName(getNomInstit());
		typeReclamationtSelected.setInstitution(institutionSelected);
		getTypeReclamationService().updateTypeReclamation(typeReclamationtSelected);
		// supprimer la list des types des réclamations afin de la recharger lors de l'appel de la méthode "getListTypeReclamation()"
		listTypeReclamation = null;
	}

	public void onRowCancel(RowEditEvent event) {
		logger.debug("Type Réclamation Cancelled Type: " + ((TypeReclamation) event.getObject()).getType());
	}

	public void deleteTypeReclamation(TypeReclamation typeReclamation)
	{
		try
		{
			logger.debug("bonjour slouma louati From Agent");
			getTypeReclamationService().deleteTypeReclamation(typeReclamation);
			listTypeReclamation = null;
		} catch (DataAccessException e)
		{
			logger.debug(e.getMessage());
		}
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	public void addTypeReclamation()
	{
		Institution institution = getInstitutionService().getInstitutionByName(getNomInstit());
		getTypeReclamation().setInstitution(institution);
		getTypeReclamationService().insertTypeReclamation(getTypeReclamation());
		typeReclamation.setType("");
		setNomInstit("");
		listTypeReclamation = null;
	}

	/**
	 * admin et agent: ce sont deux objets récupérés à partir de la session lors de l'excution de la méthode init(). 
	 * La méthode init() est executer dés le démarrage du page statique.xhtml car elle est annotée par @PostConstruct dans son backing bean.
	 * Ils seront utilisés pour tester si l'utilisateur est connecter en tantqu'admin ou agent (voir le bout de code du test dans la méthode init) 
	 * ==> Voir les deux types de traitement dans la méthode: public List<TypeReclamation> getListTypeReclamation()
	 * */
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@PostConstruct
	public void init()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		logger.debug("Fatma louati");
		Object object = session.getAttribute("user");
		logger.debug("object user: " + object);
		if (object instanceof Admin)
		{
			setAdmin((Admin) object);
		}
		else
		{
			setAgent((Agent) object);
		}
	}
}