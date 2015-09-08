package com.sifast.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sifast.dao.IAdminDAO;
import com.sifast.model.Admin;
import com.sifast.service.util.CastClass;

/**
 * @author Slouma
 *
 */
@Repository
public class AdminDAO implements IAdminDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Admin> getListAdmin() {
		return CastClass.castList(Admin.class, getSessionFactory().getCurrentSession().createQuery("from Admin").list());
	}

	public void insertAdmin(Admin admin) {
		getSessionFactory().getCurrentSession().save(admin);
	}

	public void deleteAdmin(Admin admin) {
		getSessionFactory().getCurrentSession().delete(admin);
	}

	@Override
	public void updateAdmin(Admin admin) {
		getSessionFactory().getCurrentSession().update(admin);
	}

	@Override
	public Admin getAdminByEmailAndPassword(String email, String password) {
		Query q = getSessionFactory().getCurrentSession().createQuery("from Admin A where A.email=:EMAIL and A.motPass=:PASSWORD");
		q.setString("EMAIL", email);
		q.setString("PASSWORD", password);
		List<Admin> admin = CastClass.castList(Admin.class, q.list());
		return admin.isEmpty() ? null : admin.get(0);
	}
}