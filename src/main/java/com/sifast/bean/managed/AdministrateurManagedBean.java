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

import com.sifast.model.Admin;
import com.sifast.service.IAdminService;

@ManagedBean(name = "adminMB")
@SessionScoped
public class AdministrateurManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -973406799310221473L;
	static final Logger logger = Logger.getLogger(AdministrateurManagedBean.class);
	private Admin admin = new Admin();
	private boolean skip;

	@ManagedProperty(value = "#{AdminService}")
	private IAdminService adminService;

	private List<Admin> listAdmin;
	private List<Admin> listAdminFiltred;

	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public void addAdmin() {
		logger.debug("ajout admin");
		getAdminService().insertAdmin(getAdmin());
		admin.setNom("");
		admin.setPrenom("");
		admin.setEmail("");
		admin.setLogin("");
		admin.setMotPass("");
		listAdmin = null;
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			// reset in case user goes back
			skip = false; 
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}

	public List<Admin> getListAdmin() {
		if (listAdmin == null)
		{
			listAdmin = getAdminService().getListAdmin();
		}
		return listAdmin;
	}

	public void deleteAdmin(Admin admin)
	{
		try
		{
			getAdminService().deleteAdmin(admin);
			listAdmin = null;
		} catch (DataAccessException e)
		{
			logger.debug(e.getMessage());
		}
	}

	public List<Admin> getListAdminFiltred() {
		return listAdminFiltred;
	}

	public void setListAdminFiltred(List<Admin> listAdminFiltred) {
		this.listAdminFiltred = listAdminFiltred;
	}

	public void onRowEdit(RowEditEvent event) {
		Admin adminSelected = (Admin) event.getObject();
		logger.debug("Admin Edited Email: " + adminSelected.getEmail());
		getAdminService().updateAdmin(adminSelected);
		// supprimer la list des Administrateurs afin de la recharger lors de l'appel de la méthode "getListAdmin()"    
		listAdmin = null;
	}

	public void onRowCancel(RowEditEvent event) {
		logger.debug("Admin Cancelled Email: " + ((Admin) event.getObject()).getEmail());
	}
}