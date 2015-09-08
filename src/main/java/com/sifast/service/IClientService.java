package com.sifast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Client;

@Transactional
public interface IClientService {

	public List<Client> getListClient();
	
	public Client getClientByEmail(String email);

	public void insertClient(Client client);

	public void deleteClient(Client client);
	
	public void updateClient(Client client);
}