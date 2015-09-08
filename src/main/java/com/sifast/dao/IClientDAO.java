package com.sifast.dao;

import java.util.List;
import com.sifast.model.Client;

public interface IClientDAO {

	public List<Client> getListClient();
	
	public Client getClientByEmail(String email);
 
	public void insertClient(Client client);

	public void deleteClient(Client client);
	
	public void updateClient(Client client);
}