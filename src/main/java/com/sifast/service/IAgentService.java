package com.sifast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Agent;

@Transactional
public interface IAgentService {

	public List<Agent> getListAgent();

	public void insertAgent(Agent agent);

	public void deleteAgent(Agent agent);
	
	public void updateAgent(Agent agent);
	
	public Agent getAgentByEmailAndPassword(String email, String password);
}