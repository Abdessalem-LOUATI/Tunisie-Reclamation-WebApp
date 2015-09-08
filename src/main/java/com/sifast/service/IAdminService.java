package com.sifast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Admin;

@Transactional
public interface IAdminService {
	
	public List<Admin> getListAdmin();
    
    public void insertAdmin(Admin admin);
    
    public void deleteAdmin(Admin admin);
    
    public void updateAdmin(Admin admin);
    
    public Admin getAdminByEmailAndPassword(String email,String password);
}