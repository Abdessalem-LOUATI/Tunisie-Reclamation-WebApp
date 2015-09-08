package com.sifast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifast.dao.IClientDAO;
import com.sifast.model.Client;
import com.sifast.service.IClientService;

@Service("ClientService")
public class ClientService implements IClientService {

	@Autowired
	private IClientDAO clientDAO;

	public IClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(IClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	@Transactional
	public List<Client> getListClient() {
		return getClientDAO().getListClient();
	}

	@Transactional
	public Client getClientByEmail(String email) {
		return getClientDAO().getClientByEmail(email);
	}

	@Transactional
	public void insertClient(Client client) {
		getClientDAO().insertClient(client);
	}

	@Transactional
	public void deleteClient(Client client) {
		getClientDAO().deleteClient(client);
	}

	@Transactional
	public void updateClient(Client client) {
		getClientDAO().updateClient(client);
	}
}