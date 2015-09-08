package com.sifast.dao;

import java.util.List;

import com.sifast.model.Reclamation;

public interface IReclamationDAO {

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