package com.sifast.service.filter.pattern;

import java.util.List;

import com.sifast.model.Reclamation;

public interface Critere {

	public List<Reclamation> execute(List<Reclamation> reclamations);
}
