package com.sifast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Institution;

@Transactional
public interface IInstitutionService {

	public List<Institution> getListInstitution();
	
	public List<Institution> getListInstitutionByName(String nomInstit);

	public Institution getInstitutionByName(String nomInstit);

	public void insertInstitution(Institution institution);

	public void deleteInstitution(Institution institution);
	
	public void updateInstitution(Institution institution);
}