package com.sifast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Institution;
import com.sifast.model.TypeReclamation;

@Transactional
public interface ITypeReclamationService {
	
	public List<TypeReclamation> getListTypeReclamation();
	
	public List<TypeReclamation> getListTypeReclamationByInstitution(String nomInstit);
	
	public TypeReclamation getTypeReclamationByType(String type);
	
	public TypeReclamation getTypeReclamationByTypeAndInstitution(String type, Institution institution);
	
	public void insertTypeReclamation(TypeReclamation typeReclamation);
	
	public void deleteTypeReclamation(TypeReclamation typeReclamation);
	
	public void updateTypeReclamation(TypeReclamation typeReclamation);
}