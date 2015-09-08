package com.sifast.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sifast.dao.IAgentDAO;
import com.sifast.model.Agent;
import com.sifast.service.util.CastClass;

/**
 * @author Slouma
 *
 */
@Repository
public class AgentDAO implements IAgentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.dao.IAgentDAO#getAgent()
	 */
	public List<Agent> getListAgent() {
		return CastClass.castList(Agent.class, getSessionFactory().getCurrentSession().createQuery("from Agent").list());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.dao.IAgentDAO#insertAgent(model.pojo.Agent)
	 */
	public void insertAgent(Agent agent) {
		getSessionFactory().getCurrentSession().save(agent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.dao.IAgentDAO#suppAgent(model.pojo.Agent)
	 */
	public void deleteAgent(Agent agent) {
		getSessionFactory().getCurrentSession().delete(agent);
	}

	@Override
	public void updateAgent(Agent agent) {
		getSessionFactory().getCurrentSession().update(agent);
	}

	@Override
	public Agent getAgentByEmailAndPassword(String email, String password) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from Agent A where A.email=:EMAIL and A.motPass=:PASSWORD");
		q.setString("EMAIL", email);
		q.setString("PASSWORD", password);
		List<Agent> agent = CastClass.castList(Agent.class, q.list());
		return agent.isEmpty() ? null : agent.get(0);
	}
}