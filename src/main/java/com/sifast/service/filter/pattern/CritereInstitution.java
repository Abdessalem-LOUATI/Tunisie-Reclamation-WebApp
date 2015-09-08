package com.sifast.service.filter.pattern;

import java.util.ArrayList;
import java.util.List;

import com.sifast.model.Reclamation;

public class CritereInstitution implements Critere {

	private String nomInstit;

	public CritereInstitution(String nomInstit) {
		this.nomInstit = nomInstit;
	}

	@Override
	public List<Reclamation> execute(List<Reclamation> reclamations) {
		List<Reclamation> institutionReclamations = new ArrayList<Reclamation>();
		for (Reclamation reclamation : reclamations) {
			if(reclamation.getTypeReclamation().getInstitution().getNomInstit().equals(nomInstit))
			{
				institutionReclamations.add(reclamation);
			}
		}
		return institutionReclamations;
	}

}
