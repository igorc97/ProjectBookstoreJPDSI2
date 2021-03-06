package bookstore_project.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore_project_ejbb.entities.Role;
import bookstore_project_ejbb.entities.User;

@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "bookstore_project-simplePU";
	private Query query;
	
	
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
	
	public User findByIdUser(int idUser){
		User u = null;
		Query query = em.createQuery("Select u from User u where u.idUser = :idUser");
		query.setParameter("idUser", idUser);
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
	public List<String> getRolesFromDB(Role role){
		 ArrayList<String> roles = new ArrayList<String>();
		 return roles;
	 }
	
	public User findByLoginAndPass(String login, String pass){
		User u = null;
		Query query = em.createQuery("Select u from User u where u.login = :login and u.pass = :pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);
		try {
			
			u = (User) query.getSingleResult();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return u;
	}
	
	public int getUserID(String login) {
		int id = 0;
		String where = "";

		where = createWhere("login", login, where);
		query = em.createQuery("SELECT u.idUser FROM User u " + where);
		query.setParameter("login", login);

		try {
			id = (int) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}
	
	private String createWhere(String paramName, String param, String currentWhere) {
		String where = currentWhere;

		if (param != null) {
			if (where.isEmpty()) {
				where = "WHERE ";
			} else {
				where += "AND ";
			}
			if (paramName.equals("login")) {
				where += "u." + paramName + " like :" + paramName + " ";
			} else if (paramName.equals("name")) {
				where += "r." + paramName + " like:" + paramName + " ";
			}
		}

		return where;
	}

}

