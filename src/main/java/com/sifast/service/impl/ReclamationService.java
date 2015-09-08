package com.sifast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifast.dao.IReclamationDAO;
import com.sifast.model.Reclamation;
import com.sifast.service.IReclamationService;

@Service("ReclamationService")
public class ReclamationService implements IReclamationService {

	@Autowired
	private IReclamationDAO reclamationDAO;

	public IReclamationDAO getReclamationDAO() {
		return reclamationDAO;
	}

	public void setReclamationDAO(IReclamationDAO reclamationDAO) {
		this.reclamationDAO = reclamationDAO;
	}

	@Transactional
	public List<Reclamation> getListReclamation() {
		return getReclamationDAO().getListReclamation();
	}

	@Transactional
	public Reclamation getReclamationByRef(String refReclam) {
		return getReclamationDAO().getReclamationByRef(refReclam);
	}

	@Transactional
	public void insertReclamation(Reclamation reclamation) {
		getReclamationDAO().insertReclamation(reclamation);
	}

	@Transactional
	public void deleteReclamation(Reclamation reclamation) {
		getReclamationDAO().deleteReclamation(reclamation);
	}

	@Transactional
	public int getNumberReclamation() {
		return getReclamationDAO().getNumberReclamation();
	}

	@Transactional
	public void updateReclamation(Reclamation reclamation) {
		getReclamationDAO().updateReclamation(reclamation);
	}

	@Transactional
	public List<Reclamation> getListReclamationToDispatcher() {
		return getReclamationDAO().getListReclamationToDispatcher();
	}

	@Transactional
	public int countReclamationByInstitution(String nomInstit) {
		return getReclamationDAO().countReclamationByInstitution(nomInstit);
	}

	@Transactional
	public int countReclamationByInstitutionAndType(String nomInstit, String type) {
		return getReclamationDAO().countReclamationByInstitutionAndType(nomInstit, type);
	}

	@Transactional
	public List<Reclamation> getListReclamationByInstitution(String nomInstit) {
		return getReclamationDAO().getListReclamationByInstitution(nomInstit);
	}

	@Transactional
	public List<Reclamation> getListReclamationLastXDays(int xDays) {
		return getReclamationDAO().getListReclamationLastXDays(xDays);
	}
}