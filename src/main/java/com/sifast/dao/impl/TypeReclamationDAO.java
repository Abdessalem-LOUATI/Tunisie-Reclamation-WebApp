package com.sifast.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sifast.dao.ITypeReclamationDAO;
import com.sifast.model.Institution;
import com.sifast.model.TypeReclamation;
import com.sifast.service.util.CastClass;

/**
 * @author Slouma
 *
 */
@Repository
public class TypeReclamationDAO implements ITypeReclamationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<TypeReclamation> getListTypeReclamation() {
		return CastClass.castList(TypeReclamation.class, getSessionFactory().getCurrentSession().createQuery("from TypeReclamation").list());
	}

	public TypeReclamation getTypeReclamationByType(String type) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from TypeReclamation TR where TR.type=:TYPE");
		q.setString("TYPE", type);
		List<TypeReclamation> typeReclamation = CastClass.castList(TypeReclamation.class, q.list());
		return typeReclamation.isEmpty() ? null : typeReclamation.get(0);
	}

	public TypeReclamation getTypeReclamationByTypeAndInstitution(String type, Institution institution) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from TypeReclamation as TR where TR.institution=:nomInstit and TR.type=:TYPE");
		q.setString("TYPE", type);
		q.setString("nomInstit", institution.getNomInstit());
		List<TypeReclamation> typeReclamation = CastClass.castList(TypeReclamation.class, q.list());
		return typeReclamation.isEmpty() ? null : typeReclamation.get(0);
	}

	public void insertTypeReclamation(TypeReclamation typeReclamation) {
		getSessionFactory().getCurrentSession().save(typeReclamation);
	}

	public void deleteTypeReclamation(TypeReclamation typeReclamation) {
		getSessionFactory().getCurrentSession().delete(typeReclamation);
	}

	@Override
	public void updateTypeReclamation(TypeReclamation typeReclamation) {
		getSessionFactory().getCurrentSession().update(typeReclamation);
	}

	@Override
	public List<TypeReclamation> getListTypeReclamationByInstitution(String nomInstit) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from TypeReclamation TR where TR.institution.nomInstit=:nomInstit");
		q.setString("nomInstit", nomInstit);
		return CastClass.castList(TypeReclamation.class, q.list());
	}
}