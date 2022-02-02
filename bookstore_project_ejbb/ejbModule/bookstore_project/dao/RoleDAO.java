package bookstore_project.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore_project_ejbb.entities.Role;


@Stateless
public class RoleDAO {

	@PersistenceContext
	EntityManager em;
	public void create(Role role) {
		em.persist(role);
	}
	public Role merge(Role role) {
		return em.merge(role);
	}
	public void remove(Role role) {
		em.remove(em.merge(role));
	}
	public Role find(Object id) {
		Role r = em.find(Role.class, id);
		return r;
	}
	public Role findByName(String nameRole){
		Role r = null;
		Query query = em.createQuery("Select r from Role r where r.nameRole = :nameRole");
		query.setParameter("nameRole", nameRole);
		try {
			
			r = (Role) query.getSingleResult();
			
			}catch (Exception e) {
				e.printStackTrace();		
			}
		return r;
	}
}
