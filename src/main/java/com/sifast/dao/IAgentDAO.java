package com.sifast.dao;

import java.util.List;

import com.sifast.model.Agent;

public interface IAgentDAO {

	public List<Agent> getListAgent();

	public void insertAgent(Agent agent);

	public void deleteAgent(Agent agent);
	
	public void updateAgent(Agent agent);
	
	public Agent getAgentByEmailAndPassword(String email,String password);
}