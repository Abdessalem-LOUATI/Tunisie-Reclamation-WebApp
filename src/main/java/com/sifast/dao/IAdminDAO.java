package com.sifast.dao;

import java.util.List;

import com.sifast.model.Admin;

public interface IAdminDAO {
	
	public List<Admin> getListAdmin();
    
    public void insertAdmin(Admin admin);
    
    public void deleteAdmin(Admin admin);
    
    public void updateAdmin(Admin admin);
    
    public Admin getAdminByEmailAndPassword(String email,String password);
}