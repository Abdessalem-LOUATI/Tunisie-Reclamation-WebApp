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
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.dao.DataAccessException;
import com.sifast.model.Admin;
import com.sifast.model.Agent;
import com.sifast.model.Reclamation;
import com.sifast.service.IClientService;
import com.sifast.service.IReclamationService;
import com.sifast.service.ITypeReclamationService;
import com.sifast.service.impl.NotifierClientService;

@ManagedBean(name = "reclamationMB")
@SessionScoped
public class ReclamationManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 207017990356910875L;
	static final Logger logger = Logger.getLogger(ReclamationManagedBean.class);

	@ManagedProperty(value = "#{ReclamationService}")
	private IReclamationService reclamationService;

	@ManagedProperty(value = "#{ClientService}")
	private IClientService clientService;

	@ManagedProperty(value = "#{TypeReclamationService}")
	private ITypeReclamationService typeReclamationService;

	private List<Reclamation> listReclamation;
	private List<Reclamation> listReclamationFiltred;
	private Reclamation selectedReclam = null;
	private MapModel simpleModel;
	private String center;
	private Admin admin;
	private Agent agent;

	public IReclamationService getReclamationService() {
		return reclamationService;
	}

	public void setReclamationService(IReclamationService reclamationService) {
		this.reclamationService = reclamationService;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	public ITypeReclamationService getTypeReclamationService() {
		return typeReclamationService;
	}

	public void setTypeReclamationService(ITypeReclamationService typeReclamationService) {
		this.typeReclamationService = typeReclamationService;
	}

	public List<Reclamation> getListReclamation() {
		logger.debug("Get list des réclamation");
		if (agent != null)
		{
			if (listReclamation == null)
			{
				listReclamation = getReclamationService().getListReclamationByInstitution(agent.getInstitution().getNomInstit());
				logger.debug("Get list des réclamation 222");
			}
		}
		else
		{
			if (listReclamation == null)
			{
				listReclamation = getReclamationService().getListReclamation();
				logger.debug("Get list des réclamation 222");
			}
		}
		return listReclamation;
	}

	public List<Reclamation> getListReclamationFiltred() {
		return listReclamationFiltred;
	}

	public void setListReclamationFiltred(List<Reclamation> listReclamationFiltred) {
		this.listReclamationFiltred = listReclamationFiltred;
	}

	public void onRowEdit(RowEditEvent event) {
		Reclamation reclamationSelected = (Reclamation) event.getObject();
		logger.debug("edit reclam email client: " + reclamationSelected.getClient());
		getReclamationService().updateReclamation(reclamationSelected);
		listReclamation = null;
		if (reclamationSelected.isEtatReclam())
		{
			NotifierClientService.notifyClientThatClaimFenced(reclamationSelected);
		}
	}

	public void onRowCancel(RowEditEvent event) {
		logger.debug("cancel reclam");
	}

	public void deleteReclamation(Reclamation reclamation)
	{
		try
		{
			logger.debug("bonjour slouma louati From Réclamation");
			getReclamationService().deleteReclamation(reclamation);
			listReclamation = null;
		} catch (DataAccessException e)
		{
			logger.debug(e.getMessage());
		}
	}

	public void refresh()
	{
		logger.debug("refresh !");
		logger.debug("selectedReclam: " + selectedReclam);
		listReclamation = null;
	}

	public Reclamation getSelectedReclam() {
		logger.debug("selectedReclam from simple Model: " + selectedReclam);
		return selectedReclam;
	}

	public void setSelectedReclam(Reclamation selectedReclam) {
		logger.debug("set selected reclam");
		this.selectedReclam = selectedReclam;
	}

	public MapModel getSimpleModel() {

		logger.debug("**** From simple model");
		simpleModel = new DefaultMapModel();
		// Shared coordinates
		LatLng coord = new LatLng(selectedReclam.getLatitude(), selectedReclam.getLongitude());
		// Basic marker
		simpleModel.addOverlay(new Marker(coord, selectedReclam.getAdresseAnomalie()));
		return simpleModel;
	}

	// c'est pour initialiser l'endroit par défaut (la position actuelle) lors du chargement du Map
	public String getCenter() {
		logger.debug("----- From center");
		center = selectedReclam.getLatitude() + "," + selectedReclam.getLongitude();
		return center;
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

	/**
	 * admin et agent: ce sont deux objets récupérés à partir de la session lors de l'excution de la méthode init().
	 * La méthode init() est executer dés le démarrage du page statique.xhtml car elle est annotée par @PostConstruct dans son backing bean.
	 * Ils seront utilisés pour tester si l'utilisateur est connecter en tantqu'admin ou agent (voir le bout de code du test dans la méthode init)
	 * ==> Voir les deux types de traitement dans la méthode: public List<Reclamation> getListReclamation()
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