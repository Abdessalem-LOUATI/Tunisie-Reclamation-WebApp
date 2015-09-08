package com.sifast.dao;

import java.util.List;

import com.sifast.model.Institution;

public interface IInstitutionDAO {
	
	public List<Institution> getListInstitution();
	
	public List<Institution> getListInstitutionByName(String nomInstit);
 	
	public Institution getInstitutionByName(String nomInstit);

	public void insertInstitution(Institution institution);

	public void deleteInstitution(Institution institution);
	
	public void updateInstitution(Institution institution);
}