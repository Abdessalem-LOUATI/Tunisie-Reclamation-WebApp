package com.sifast.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sifast.dao.IInstitutionDAO;
import com.sifast.model.Institution;
import com.sifast.service.util.CastClass;

/**
 * @author Slouma
 *
 */
@Repository
public class InstitutionDAO implements IInstitutionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Institution> getListInstitution() {
		return CastClass.castList(Institution.class, getSessionFactory().getCurrentSession().createQuery("from Institution").list());
	}

	public Institution getInstitutionByName(String nomInstit) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from Institution I where I.nomInstit=:NOMINSTIT");
		q.setString("NOMINSTIT", nomInstit);
		List<Institution> institution = CastClass.castList(Institution.class, q.list());
		return institution.isEmpty() ? null : institution.get(0);
	}

	public void insertInstitution(Institution institution) {
		getSessionFactory().getCurrentSession().save(institution);
	}

	public void deleteInstitution(Institution institution) {
		getSessionFactory().getCurrentSession().delete(institution);
	}

	@Override
	public void updateInstitution(Institution institution) {
		getSessionFactory().getCurrentSession().update(institution);
	}

	@Override
	public List<Institution> getListInstitutionByName(String nomInstit) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from Institution I where I.nomInstit=:NOMINSTIT");
		q.setString("NOMINSTIT", nomInstit);
		return CastClass.castList(Institution.class, q.list());
	}
}