package com.sifast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifast.dao.IAgentDAO;
import com.sifast.model.Agent;
import com.sifast.service.IAgentService;

@Service("AgentService")
public class AgentService implements IAgentService {

	@Autowired
	private IAgentDAO agentDAO;

	public IAgentDAO getAgentDAO() {
		return agentDAO;
	}

	public void setAgentDAO(IAgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	@Transactional
	public List<Agent> getListAgent() {
		return getAgentDAO().getListAgent();
	}

	@Transactional
	public void insertAgent(Agent agent) {
		getAgentDAO().insertAgent(agent);
	}

	@Transactional
	public void deleteAgent(Agent agent) {
		getAgentDAO().deleteAgent(agent);
	}

	@Transactional
	public void updateAgent(Agent agent) {
		getAgentDAO().updateAgent(agent);
	}

	@Transactional
	public Agent getAgentByEmailAndPassword(String email, String password) {
		return getAgentDAO().getAgentByEmailAndPassword(email, password);
	}
}