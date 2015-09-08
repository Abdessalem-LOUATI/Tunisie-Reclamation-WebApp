package com.sifast.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sifast.dao.IReclamationDAO;
import com.sifast.model.Reclamation;
import com.sifast.service.util.CastClass;

/**
 * @author Slouma
 *
 */
@Repository
public class ReclamationDAO implements IReclamationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Reclamation> getListReclamation() {
		return CastClass.castList(Reclamation.class, getSessionFactory().getCurrentSession().createQuery("from Reclamation").list());
	}

	public Reclamation getReclamationByRef(String refReclam) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from Reclamation R where R.refReclam=:refRec");
		q.setString("refRec", refReclam);
		List<Reclamation> reclamation = CastClass.castList(Reclamation.class, q.list());
		return reclamation.isEmpty() ? null : reclamation.get(0);
	}

	public void insertReclamation(Reclamation reclamation) {
		getSessionFactory().getCurrentSession().save(reclamation);
	}

	public void deleteReclamation(Reclamation reclamation) {
		getSessionFactory().getCurrentSession().delete(reclamation);
	}

	public int getNumberReclamation() {
		//Long result = (Long) (getSessionFactory().getCurrentSession().createQuery("select count(*) from Reclamation R").uniqueResult());
		 List<Reclamation> listReclamation = CastClass.castList(Reclamation.class,getSessionFactory().getCurrentSession().createQuery("from Reclamation").list());
		 return listReclamation.get(listReclamation.size()-1).getIdReclam();
		//return result.intValue();
	}

	@Override
	public void updateReclamation(Reclamation reclamation) {
		getSessionFactory().getCurrentSession().update(reclamation);
	}

	@Override
	public List<Reclamation> getListReclamationToDispatcher() {
		return CastClass.castList(Reclamation.class, getSessionFactory().getCurrentSession().createQuery("from Reclamation R where R.etatEnvoi=0").list());
	}

	@Override
	public int countReclamationByInstitution(String nomInstit) {
		Query q = getSessionFactory().getCurrentSession().createQuery("select count(*) from Reclamation R where R.typeReclamation.institution.nomInstit=:nomInstit");
		q.setString("nomInstit", nomInstit);
		Long result = (Long) (q.uniqueResult());
		return result.intValue();
	}

	@Override
	public int countReclamationByInstitutionAndType(String nomInstit, String type) {
		Query q = getSessionFactory().getCurrentSession().createQuery("select count(*) from Reclamation R where R.typeReclamation.institution.nomInstit=:nomInstit and R.typeReclamation.type=:type");
		q.setString("nomInstit", nomInstit);
		q.setString("type", type);
		Long result = (Long) (q.uniqueResult());
		return result.intValue();
	}

	@Override
	public List<Reclamation> getListReclamationByInstitution(String nomInstit) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from Reclamation R where R.typeReclamation.institution.nomInstit=:nomInstit");
		q.setString("nomInstit", nomInstit);
		return CastClass.castList(Reclamation.class, q.list());
	}

	@Override
	public List<Reclamation> getListReclamationLastXDays(int xDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, xDays);
		Date date = c.getTime();
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Reclamation.class);
		criteria.add(Restrictions.ge("dateReclam", date)); 
		return CastClass.castList(Reclamation.class,criteria.list());
	}
}