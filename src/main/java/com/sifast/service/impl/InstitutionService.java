package com.sifast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifast.dao.IInstitutionDAO;
import com.sifast.model.Institution;
import com.sifast.service.IInstitutionService;

@Service("InstitutionService")
public class InstitutionService implements IInstitutionService {

	@Autowired
	IInstitutionDAO institutionDAO;

	public IInstitutionDAO getInstitutionDAO() {
		return institutionDAO;
	}

	public void setInstitutionDAO(IInstitutionDAO institutionDAO) {
		this.institutionDAO = institutionDAO;
	}

	@Transactional
	public List<Institution> getListInstitution() {
		return getInstitutionDAO().getListInstitution();
	}

	@Transactional
	public Institution getInstitutionByName(String nomInstit) {
		return getInstitutionDAO().getInstitutionByName(nomInstit);
	}

	@Transactional
	public void insertInstitution(Institution institution) {
		getInstitutionDAO().insertInstitution(institution);
	}

	@Transactional
	public void deleteInstitution(Institution institution) {
		getInstitutionDAO().deleteInstitution(institution);
	}

	@Transactional
	public void updateInstitution(Institution institution) {
		getInstitutionDAO().updateInstitution(institution);
	}

	@Transactional
	public List<Institution> getListInstitutionByName(String nomInstit) {
		return getInstitutionDAO().getListInstitutionByName(nomInstit);
	}
}