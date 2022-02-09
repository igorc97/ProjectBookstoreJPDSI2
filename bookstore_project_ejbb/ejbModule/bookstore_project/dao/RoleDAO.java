package bookstore_project.dao;

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
	public List<Role> getList2(Map<String, Object> searchParams) {
		List<Role> list = null;

		// 1. Build query string with parameters
		String select = "select r ";
		String from = "from Role r ";
		String where = "";
		String orderby = "order by r.idRole asc, r.nameRole";

		// search for surname
		String idRole = (String) searchParams.get("idRole");
		if (idRole != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "r.idRole like :idRole ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (idRole != null) {
			query.setParameter("idRole", idRole+"%");
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
	 
}

