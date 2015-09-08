package com.sifast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifast.dao.ITypeReclamationDAO;
import com.sifast.model.Institution;
import com.sifast.model.TypeReclamation;
import com.sifast.service.ITypeReclamationService;

@Service("TypeReclamationService")
public class TypeReclamationService implements ITypeReclamationService {

	@Autowired
	private ITypeReclamationDAO typeReclamationDAO;

	public ITypeReclamationDAO getTypeReclamationDAO() {
		return typeReclamationDAO;
	}

	public void setTypeReclamationDAO(ITypeReclamationDAO typeReclamationDAO) {
		this.typeReclamationDAO = typeReclamationDAO;
	}

	@Transactional
	public List<TypeReclamation> getListTypeReclamation() {
		return getTypeReclamationDAO().getListTypeReclamation();
	}

	@Transactional
	public TypeReclamation getTypeReclamationByType(String type) {
		return getTypeReclamationDAO().getTypeReclamationByType(type);
	}

	@Transactional
	public TypeReclamation getTypeReclamationByTypeAndInstitution(String type, Institution institution) {
		return getTypeReclamationDAO().getTypeReclamationByTypeAndInstitution(type, institution);

	}

	@Transactional
	public void insertTypeReclamation(com.sifast.model.TypeReclamation typeReclamation) {
		getTypeReclamationDAO().insertTypeReclamation(typeReclamation);
	}

	@Transactional
	public void deleteTypeReclamation(com.sifast.model.TypeReclamation typeReclamation) {
		getTypeReclamationDAO().deleteTypeReclamation(typeReclamation);
	}

	@Transactional
	public void updateTypeReclamation(TypeReclamation typeReclamation) {
		getTypeReclamationDAO().updateTypeReclamation(typeReclamation);
	}

	@Transactional
	public List<TypeReclamation> getListTypeReclamationByInstitution(String nomInstit) {
		return getTypeReclamationDAO().getListTypeReclamationByInstitution(nomInstit);
	}
}