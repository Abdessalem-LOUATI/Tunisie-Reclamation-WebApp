package com.sifast.service.filter.pattern;

import java.util.ArrayList;
import java.util.List;

import com.sifast.model.Reclamation;

public class CritereType implements Critere {

	private String type;

	public CritereType(String type) {
		this.type = type;
	}

	@Override
	public List<Reclamation> execute(List<Reclamation> reclamations) {
		List<Reclamation> typeReclamations = new ArrayList<>();
		for (Reclamation reclamation : reclamations) {
			if (reclamation.getTypeReclamation().getType().equals(type))
			{
				typeReclamations.add(reclamation);
			}
		}
		return typeReclamations;
	}
}