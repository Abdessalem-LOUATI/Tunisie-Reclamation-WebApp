package com.sifast.bean.managed;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import com.sifast.model.Reclamation;
import com.sifast.service.IReclamationService;

@ManagedBean(name = "etatReclamMB")
@SessionScoped
public class EtatReclamationManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8846570763356956625L;
	static final Logger logger = Logger.getLogger(EtatReclamationManagedBean.class);
	
	@ManagedProperty(value = "#{ReclamationService}")
	IReclamationService reclamationService;

	private String refReclamation;
	private String etatReclamation;

	public String getEtatReclamation() {
		return etatReclamation;
	}

	public void setEtatReclamation(String etatReclamation) {
		this.etatReclamation = etatReclamation;
	}

	public String getRefReclamation() {
		return refReclamation;
	}

	public void setRefReclamation(String refReclamation) {
		this.refReclamation = refReclamation;
	}

	public IReclamationService getReclamationService() {
		return reclamationService;
	}

	public void setReclamationService(IReclamationService reclamationService) {
		this.reclamationService = reclamationService;
	}

	// Pour vérifier l'état d'une réclamation donnée par son référence (refReclam)
	public void verifEtatReclamation()
	{
		Reclamation reclamation = getReclamationService().getReclamationByRef(getRefReclamation());
		logger.debug("reclamation: " + getRefReclamation());
		if (reclamation == null)
		{
			setEtatReclamation("Cette réclamation n'existe plus !");
		}
		else if (reclamation.isEtatReclam())
		{
			setEtatReclamation("Votre réclamation est traitée");
		}
		else
		{
			setEtatReclamation("Votre réclamation faite le " + reclamation.getDateReclam() + " est en cours de traitement");
		}
	}
}