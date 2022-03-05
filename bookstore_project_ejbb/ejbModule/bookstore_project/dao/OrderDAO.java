package bookstore_project.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore_project_ejbb.entities.Order;
import bookstore_project_ejbb.entities.Role;
import bookstore_project_ejbb.entities.User;


@Stateless
public class OrderDAO {
		private Query query;
	@PersistenceContext
	EntityManager em;
	public void create(Order order) {
		em.persist(order);
	}
	public Order merge(Order order) {
		return em.merge(order);
	}
	public void remove(Order order) {
		em.remove(em.merge(order));
	}
	public Order find(Object id) {
		return em.find(Order.class, id);
	}
	
	
	
	public List<Order> getFullList(){
		List<Order> list = null;
		Query query = em.createQuery("Select o from order o");
		try {
			list = query.getResultList();
			}catch (Exception e) {
				e.printStackTrace();
			}
		return list;
	}
	public List<Order> getList(Map<String, Object> searchParams) {
		List<Order> list = null;

		// 1. Build query string with parameters
		String select = "select o ";
		String from = "from Order o ";
		String where = "";
		String orderby = "order by o.idOrder asc";

		// search for surname
		String idOrder = (String) searchParams.get("idOrder");
		if (idOrder != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.idOrder like :idOrder ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (idOrder != null) {
			query.setParameter("idOrder", idOrder+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	/*
	 * public int getOrderID(int idUser) { int id = 0; String where = "";
	 * 
	 * where = createWhere("idUser", idUser, where); query =
	 * em.createQuery("SELECT o.idUser FROM Order o " + where);
	 * query.setParameter("idUser", idUser);
	 * 
	 * try { id = (int) query.getSingleResult(); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return id; }
	 */
	
	private String createWhere(String paramName, String param, String currentWhere) {
		String where = currentWhere;

		if (param != null) {
			if (where.isEmpty()) {
				where = "WHERE ";
			} else {
				where += "AND ";
			}
			if (paramName.equals("idUser")) {
				where += "u." + paramName + " like :" + paramName + " ";
			} else if (paramName.equals("name")) {
				where += "r." + paramName + " like:" + paramName + " ";
			}
		}

		return where;
	}

	public Order findById(int idOrder){
		Order o = null;
		Query query = em.createQuery("Select o from Order o where o.idOrder = :idOrder");
		query.setParameter("idOrder", idOrder);
		try {
			
			o = (Order) query.getSingleResult();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return o;
	}
	
	public List<Order> getListWhereUserId(Map<String, Object> searchParams) {
		List<Order> list = null;
		//User remoteClient;
		//int userId = remoteClient.getIdUser();
		// 1. Build query string with parameters
		String select = "select o ";
		String from = "from Order o ";
		String where = "User_idUser="; //+ userId ;
		String orderby = "order by o.idOrder asc";

		// search for surname
		String idOrder = (String) searchParams.get("idOrder");
		if (idOrder != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.idOrder like :idOrder ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (idOrder != null) {
			query.setParameter("idOrder", idOrder+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Order findByIdUser(int idUser){
		Order o = null;
		Query query = em.createQuery("Select o from myorder o where o.User_idUser = :User_idUser");
		query.setParameter("User_idUser", idUser);
		try {
			
			o = (Order) query.getSingleResult();
			
			}catch (Exception e) {
				e.printStackTrace();		
			}
		return o;
	}
	
	public List<Order> getUserOrders(User remoteClient){
		List<Order> listUserOrders = null;
		int idUserek = remoteClient.getIdUser(); 
		query = em.createQuery("Select o from Order o where o.user = :user");
		query.setParameter("user", remoteClient);
		try {
			
			listUserOrders = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return listUserOrders;
	}
}
