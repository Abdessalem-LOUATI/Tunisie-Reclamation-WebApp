package com.sifast.bean.managed;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.sifast.model.Admin;
import com.sifast.model.Agent;

@ManagedBean(name = "accueilMB")
@SessionScoped
public class AccueilManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1749561790915926705L;
	static final Logger logger = Logger.getLogger(AccueilManagedBean.class);
	
	private Admin admin;
	private Agent agent;

	public void logout() throws IOException
	{
		logger.debug("hello slouma");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		logger.debug("Login: "+session.getAttribute("login"));
		session.invalidate();
		facesContext.getExternalContext().redirect("/reclamation/index.jsp");
		logger.debug("logout !");
	}
	
	@PostConstruct
	public void init()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		logger.debug("Achraf louati");
		Object object = session.getAttribute("user");
		logger.debug("object user: "+object);
		if(object instanceof Admin)
		{
			setAdmin((Admin)object);
		}
		else
		{
			setAgent((Agent)object);
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