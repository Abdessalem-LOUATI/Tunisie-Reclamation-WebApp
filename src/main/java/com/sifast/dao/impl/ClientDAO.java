package com.sifast.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sifast.dao.IClientDAO;
import com.sifast.model.Client;
import com.sifast.service.util.CastClass;

/**
 * @author Slouma
 *
 */
@Repository
public class ClientDAO implements IClientDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Client> getListClient() {
		return CastClass.castList(Client.class, getSessionFactory().getCurrentSession().createQuery("from Client").list());
	}

	public Client getClientByEmail(String email) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from Client C where C.email=:EMAIL");
		q.setString("EMAIL", email);
		List<Client> client = CastClass.castList(Client.class, q.list());
		return client.isEmpty() ? null : client.get(0);
	}

	public void insertClient(Client client) {
		getSessionFactory().getCurrentSession().save(client);
	}

	public void deleteClient(Client client) {
		getSessionFactory().getCurrentSession().delete(client);
	}

	@Override
	public void updateClient(Client client) {
		getSessionFactory().getCurrentSession().update(client);
	}
}