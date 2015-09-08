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
import com.sifast.model.Agent;
import com.sifast.model.Institution;
import com.sifast.service.IAgentService;
import com.sifast.service.IInstitutionService;

@ManagedBean(name = "agentMB")
@SessionScoped
public class AgentManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5868093613441090563L;
	static final Logger logger = Logger.getLogger(AgentManagedBean.class);

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	@ManagedProperty(value = "#{InstitutionService}")
	private IInstitutionService institutionService;

	private boolean skip;
	private Agent agent = new Agent();
	private String nomInstit;
	private List<Agent> listAgent;
	private List<Agent> listAgentFilred;
	private List<Institution> listInstitution;

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public IAgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	public IInstitutionService getInstitutionService() {
		return institutionService;
	}

	public void setInstitutionService(IInstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<Agent> getListAgent() {
		if (listAgent == null)
		{
			listAgent = getAgentService().getListAgent();
		}
		return listAgent;
	}

	public void deleteAgent(Agent agent)
	{
		try
		{
			logger.debug("bonjour slouma louati From Agent");
			getAgentService().deleteAgent(agent);
			listAgent = null;
		} catch (DataAccessException e)
		{
			logger.debug(e.getMessage());
		}
	}

	public void addAgent()
	{
		Institution institution = getInstitutionService().getInstitutionByName(getNomInstit());
		getAgent().setInstitution(institution);
		getAgentService().insertAgent(getAgent());
		agent.setNom("");
		agent.setPrenom("");
		agent.setEmail("");
		agent.setLogin("");
		agent.setMotPass("");
		setNomInstit("");
		listAgent = null;
	}

	public String onFlowProcess(FlowEvent event) {
		if (nomInstit != null)
		{
			logger.debug("value instit: " + nomInstit);
		}
		else
		{
			logger.debug("institution is null");
		}
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}

	public String getNomInstit() {
		return nomInstit;
	}

	public void setNomInstit(String nomInstit) {
		this.nomInstit = nomInstit;
	}

	public List<Institution> getListInstitution() {
		logger.debug("Abdessalem louati hhhh");
		listInstitution = getInstitutionService().getListInstitution();
		logger.debug("Instit: " + listInstitution.size());
		return listInstitution;
	}

	public List<Agent> getListAgentFilred() {
		return listAgentFilred;
	}

	public void setListAgentFilred(List<Agent> listAgentFilred) {
		this.listAgentFilred = listAgentFilred;
	}

	public void onRowEdit(RowEditEvent event) {
		Agent agentSelected = (Agent) event.getObject();
		logger.debug("Agent Edited Email: " + agentSelected.getInstitution().getNomInstit());
		Institution institutionSelected = getInstitutionService().getInstitutionByName(getNomInstit());
		agentSelected.setInstitution(institutionSelected);
		getAgentService().updateAgent(agentSelected);
		// supprimer la list des Agents afin de la recharger lors de l'appel de la méthode "getListAgent()"
		listAgent = null;
	}

	public void onRowCancel(RowEditEvent event) {
		logger.debug("Agent Cancelled Email: " + ((Agent) event.getObject()).getEmail());
	}
}