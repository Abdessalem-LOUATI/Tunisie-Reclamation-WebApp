package com.sifast.bean.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.PieChartModel;

import com.sifast.model.Admin;
import com.sifast.model.Agent;
import com.sifast.model.Institution;
import com.sifast.model.TypeReclamation;
import com.sifast.service.IInstitutionService;
import com.sifast.service.IReclamationService;
import com.sifast.service.ITypeReclamationService;

@ManagedBean(name = "statMB")
@ViewScoped
public class StatistiqueManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2066787516413278836L;
	static final Logger logger = Logger.getLogger(StatistiqueManagedBean.class);

	@ManagedProperty(value = "#{ReclamationService}")
	IReclamationService reclamationService;

	@ManagedProperty(value = "#{InstitutionService}")
	IInstitutionService institutionService;

	@ManagedProperty(value = "#{TypeReclamationService}")
	ITypeReclamationService typeReclamationService;

	private PieChartModel pieModel;
	private String choixStatSelected;
	private List<String> listChoix;
	private String institutionSelected;
	private List<Institution> listInstitution;
	private Admin admin;
	private Agent agent;

	public String getInstitutionSelected() {
		return institutionSelected;
	}

	public List<Institution> getListInstitution() {
		return listInstitution;
	}

	public void setChoixStatSelected(String choixStatSelected) {
		this.choixStatSelected = choixStatSelected;
	}

	public void setInstitutionSelected(String institutionSelected) {
		this.institutionSelected = institutionSelected;
	}

	public List<String> getListChoix() {
		return listChoix;
	}

	public String getChoixStatSelected() {
		return choixStatSelected;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public IReclamationService getReclamationService() {
		return reclamationService;
	}

	public void setReclamationService(IReclamationService reclamationService) {
		this.reclamationService = reclamationService;
	}

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

	@PostConstruct
	public void init() {
		listChoix = new ArrayList<String>();
		listChoix.add("institution");
		listChoix.add("type de réclamation");
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		logger.debug("Fatma louati");
		Object object = session.getAttribute("user");
		logger.debug("object user: " + object);
		if (object instanceof Admin)
		{
			setAdmin((Admin) object);
			createPieModelByInstitution();
		}
		else
		{
			setAgent((Agent) object);
			createPieModelByTypeReclamation(agent.getInstitution().getNomInstit());
		}
	}

	// c'est la méthode qui permet de générer le schéma des statistiques par institution (il est destinée que pour les administrateurs)
	private void createPieModelByInstitution() {
		pieModel = new PieChartModel();

		List<Institution> listInstit = getInstitutionService().getListInstitution();
		for (Institution institution : listInstit) {
			String nomInstit = institution.getNomInstit();
			pieModel.set(nomInstit, getReclamationService().countReclamationByInstitution(nomInstit));
		}

		pieModel.setTitle("Statistique des réclamations par institution");
		pieModel.setLegendPosition("e");
		pieModel.setFill(false);
		pieModel.setShowDataLabels(true);
		pieModel.setDiameter(150);
	}

	// c'est la méthode qui permet de générer le schéma des statistiques par type de réclamation pour une institution donnée
	private void createPieModelByTypeReclamation(String nomInstit) {
		pieModel = new PieChartModel();

		List<TypeReclamation> listTypeReclamation = getTypeReclamationService().getListTypeReclamationByInstitution(nomInstit);
		for (TypeReclamation typeReclamation : listTypeReclamation) {
			String type = typeReclamation.getType();
			pieModel.set(type, getReclamationService().countReclamationByInstitutionAndType(nomInstit, type));
		}
		
		pieModel.setTitle("Statistique des réclamations par type pour la " + nomInstit);
		pieModel.setLegendPosition("e");
		pieModel.setFill(false);
		pieModel.setShowDataLabels(true);
		pieModel.setDiameter(150);
	}

	public void onChoixStatChange()
	{
		logger.debug("onChange: " + choixStatSelected);
		if (choixStatSelected.equals("type de réclamation"))
		{
			logger.debug("onChangeStatistique == type réclamation");
			listInstitution = getInstitutionService().getListInstitution();
		}
		else
		{
			logger.debug("onChangeStatistique ------ ou institution");
			listInstitution = null;
			setInstitutionSelected("");
			createPieModelByInstitution();
		}
	}

	public void setListInstitution(List<Institution> listInstitution) {
		this.listInstitution = listInstitution;
	}

	public void onChoixInstitutionChange()
	{
		if (!institutionSelected.equals(""))
		{
			pieModel = null;
			createPieModelByTypeReclamation(institutionSelected);
		}
	}
	
	/**
	 * admin et agent: ce sont deux objets récupérés à partir de la session lors de l'excution de la méthode init().
	 * La méthode init() est executer dés le démarrage du page statique.xhtml car elle est annotée par @PostConstruct dans son backing bean.
	 * Ils seront utilisés pour tester si l'utilisateur est connecter en tantqu'admin ou agent (voir le bout de code du test dans la méthode init)
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
}