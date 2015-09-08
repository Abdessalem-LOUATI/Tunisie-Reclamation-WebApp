package com.sifast.service.filter.pattern;

import java.util.List;

import com.sifast.model.Reclamation;

public class AndCritere implements Critere {

	private Critere critere;
	private Critere autreCritere1;
	private Critere autresCritere2;

	public AndCritere(Critere critere, Critere autreCritere1, Critere autresCritere2) {
		this.critere = critere;
		this.autreCritere1 = autreCritere1;
		this.autresCritere2 = autresCritere2;
	}

	@Override
	public List<Reclamation> execute(List<Reclamation> reclamations) {
		List<Reclamation> critereReclamations = critere.execute(reclamations);
		critereReclamations = autreCritere1.execute(critereReclamations);
		return autresCritere2.execute(critereReclamations);
	}
}