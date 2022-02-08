package bookstore_project.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore_project_ejbb.entities.User;

@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "bookstore_project-simplePU";
	@PersistenceContext
	EntityManager em;
	public void create(User user) {
		em.persist(user);
	}
	public User merge(User user) {
		return em.merge(user);
	}
	public void remove(User user) {
		em.remove(em.merge(user));
	}
	public User find(Object id) {
		User u = em.find(User.class, id);
		return u;
	}
	public User findAndGetOrders(Object id) {
		User u = em.find(User.class, id);
		if (u != null) {
			u.getOrders().size();
		}
		return u;
	}
	public List<User> getFullList(){
		List<User> list = null;
		Query query = em.createQuery("Select u from user u");
		try {
			list = query.getResultList();
			}catch (Exception e) {
				e.printStackTrace();
			}
		return list;
	}

	public User findBySurname(String surname){
		User u = null;
		Query query = em.createQuery("Select u from User u where u.surname = :surname");
		query.setParameter("surname", surname);
		try {
			
			u = (User) query.getSingleResult();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return u;
	}
	
	public User findByLogin(String login){
		User u = null;
		Query query = em.createQuery("Select u from User u where u.login = :login");
		query.setParameter("login", login);
		try {
			
			u = (User) query.getSingleResult();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return u;
	}

	public List<User> getList(Map<String, Object> searchParams) {
		List<User> list = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from User u ";
		String where = "";
		String orderby = "order by u.surname asc, u.name";

		// search for surname
		String surname = (String) searchParams.get("surname");
		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.surname like :surname ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of User objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
}

