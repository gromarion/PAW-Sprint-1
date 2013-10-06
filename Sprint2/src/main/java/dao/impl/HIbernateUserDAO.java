package dao.impl;

import java.util.List;

import model.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import dao.UserDAO;

public class HIbernateUserDAO extends HibernateGenericDAO<User> implements
		UserDAO {

	@Autowired
	public HIbernateUserDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User authenticate(String username, String password) {
		Session session = getSession();
		Query query = session
				.createQuery(" from Users where username = ? and password = ?");
		query.setParameter(0, username);
		query.setParameter(1, password);
		List<User> result = (List<User>) query.list();
		return result.size() > 0 ? result.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUser(String username) {
		Session session = getSession();
		Query query = session.createQuery(" from Users where username = ?");
		query.setParameter(0, username);
		List<User> result = (List<User>) query.list();
		return result.size() > 0 ? result.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersWithName(String name) {
		Session session = getSession();
		Query query = session.createQuery(" from Users where username like '%"
				+ name + "%' or (name like '%" + name
				+ "%') or (surname like '%" + name + "%')");		
		return (List<User>) query.list();
	}
}