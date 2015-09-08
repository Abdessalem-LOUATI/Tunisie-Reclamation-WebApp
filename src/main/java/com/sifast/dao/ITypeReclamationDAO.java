package com.sifast.dao;

import java.util.List;

import com.sifast.model.Institution;
import com.sifast.model.TypeReclamation;

public interface ITypeReclamationDAO {

	public List<TypeReclamation> getListTypeReclamation();
	
	public List<TypeReclamation> getListTypeReclamationByInstitution(String nomInstit);
	
	public TypeReclamation getTypeReclamationByType(String type);
	
	public TypeReclamation getTypeReclamationByTypeAndInstitution(String type, Institution institution);

	public void insertTypeReclamation(TypeReclamation typeReclamation);

	public void deleteTypeReclamation(TypeReclamation typeReclamation);
	
	public void updateTypeReclamation(TypeReclamation typeReclamation);
}