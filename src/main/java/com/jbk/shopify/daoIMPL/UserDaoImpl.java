package com.jbk.shopify.daoIMPL;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.shopify.dao.UserDao;
import com.jbk.shopify.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Object loginProcess(User user) {

		String msg = null;
		try (Session session = sessionFactory.openSession()) {

			User dbUser = session.get(User.class, user.getUsername());
			if (dbUser != null) {

				if (user.getPassword().equals(dbUser.getPassword())) {
					return dbUser;
				} else {
					msg = "Invalid Password";
					return msg;
				}
			} else {
				msg = "Invalid Username";
				return msg;
			}

		} catch (Exception e) {
			e.printStackTrace();
			
			
		}

		return null;
	}

	@Override
	public boolean addUser(User user) {
		User dbUser = getUser(user.getUsername());
		if (dbUser == null) {
			try (Session session = sessionFactory.openSession()) {
				session.save(user);
				session.beginTransaction().commit();
				return true;

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}

	}

	@Override
	public boolean deleteUser(String usename) {
		User user = getUser(usename);
		if (user != null) {

			try (Session session = sessionFactory.openSession()) {
				session.delete(user);
				session.beginTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public User getUser(String username) {
		User user = null;
		try (Session session = sessionFactory.openSession()) {
			user = session.get(User.class, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean updateUser(User user) {
		try (Session session = sessionFactory.openSession()) {

			session.update(user);
			session.beginTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<User> getAllUser() {

		List<User> list = null;
		try (Session session = sessionFactory.openSession()) {
			CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
			query.from(User.class);
			list = session.createQuery(query).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
