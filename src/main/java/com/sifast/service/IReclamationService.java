package com.sifast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Reclamation;

@Transactional
public interface IReclamationService {

	public List<Reclamation> getListReclamation();
	
	public List<Reclamation> getListReclamationByInstitution(String nomInstit);
	
	public List<Reclamation> getListReclamationLastXDays(int xDays);

	public Reclamation getReclamationByRef(String refReclam);
	
	public int getNumberReclamation();

	public void insertReclamation(Reclamation reclamation);

	public void deleteReclamation(Reclamation reclamation);
	
	public void updateReclamation(Reclamation reclamation);
	
	public List<Reclamation> getListReclamationToDispatcher();
	
	public int countReclamationByInstitution(String nomInstit);
	
	public int countReclamationByInstitutionAndType(String nomInstit, String type);
}