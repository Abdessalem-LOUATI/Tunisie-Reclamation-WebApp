package com.sifast.bean.managed;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;

import com.sifast.model.Client;
import com.sifast.service.IClientService;

@ManagedBean(name = "clientMB")
@SessionScoped
public class ClientManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8930303700556033653L;
	static final Logger logger = Logger.getLogger(ClientManagedBean.class);

	@ManagedProperty(value = "#{ClientService}")
	private IClientService clientService;

	private List<Client> listClient;
	private List<Client> listClientFilred;

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}
	
	public List<Client> getListClient() {
		if (listClient == null)
		{
			listClient = getClientService().getListClient();
		}
		return listClient;
	}

	public void deleteClient(Client client)
	{
		try
		{
			logger.debug("bonjour slouma louati From Client");
			getClientService().deleteClient(client);
			listClient = null;
		} catch (DataAccessException e)
		{
			logger.debug(e.getMessage());
		}
	}

	public List<Client> getListClientFilred() {
		return listClientFilred;
	}

	public void setListClientFilred(List<Client> listClientFilred) {
		this.listClientFilred = listClientFilred;
	}

	public void onRowEdit(RowEditEvent event) {
		Client clientSelected = (Client) event.getObject();
		logger.debug("Client Edited numTel: " + clientSelected.getNumTel());
		getClientService().updateClient(clientSelected);
		// supprimer la list des Clients afin de la recharger lors de l'appel de la méthode "getListClient()"
		listClient = null;
	}

	public void onRowCancel(RowEditEvent event) {
		logger.debug("Client Cancelled !");
	}
}