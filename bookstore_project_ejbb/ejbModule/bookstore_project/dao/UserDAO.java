package bookstore_project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore_project_ejbb.entities.User;

@Stateless
public class UserDAO {

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
		return em.find(User.class, id);
	}
	public List<User> getFullList(){
		List<User> list = null;
		Query query = em.createQuery("Select p from user p");
		try {
			list = query.getResultList();
			}catch (Exception e) {
				e.printStackTrace();
			}
		return list;
	}
}
