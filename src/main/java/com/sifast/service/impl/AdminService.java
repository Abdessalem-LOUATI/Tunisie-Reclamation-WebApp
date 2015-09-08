package com.sifast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifast.dao.IAdminDAO;
import com.sifast.model.Admin;
import com.sifast.service.IAdminService;

@Service("AdminService")
public class AdminService implements IAdminService {

	@Autowired
	private IAdminDAO adminDAO;

	public IAdminDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(IAdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	@Transactional
	public List<Admin> getListAdmin() {
		return getAdminDAO().getListAdmin();
	}

	@Transactional
	public void insertAdmin(Admin admin) {
		getAdminDAO().insertAdmin(admin);
	}

	@Transactional
	public void deleteAdmin(Admin admin) {
		getAdminDAO().deleteAdmin(admin);
	}

	@Transactional
	public void updateAdmin(Admin admin) {
		getAdminDAO().updateAdmin(admin);
	}

	@Transactional
	public Admin getAdminByEmailAndPassword(String email, String password) {
		return getAdminDAO().getAdminByEmailAndPassword(email, password);
	}
}