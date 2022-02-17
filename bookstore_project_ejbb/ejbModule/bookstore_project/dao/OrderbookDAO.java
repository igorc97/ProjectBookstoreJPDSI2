package bookstore_project.dao;


import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore_project_ejbb.entities.Orderbook;


@Stateless
public class OrderbookDAO {

	@PersistenceContext
	EntityManager em;
	
	public void create(Orderbook orderbook) {
		em.persist(orderbook);
	}
	
	public Orderbook merge(Orderbook orderbook) {
		return em.merge(orderbook);
	}
	
	public void remove(Orderbook orderbook) {
		em.remove(em.merge(orderbook));
	}
	
	public Orderbook find(Object id) {
		Orderbook o = em.find(Orderbook.class, id);
		return o;
	}
	
	public Orderbook findByIdOrderBook(int idOrderBook){
		Orderbook o = null;
		Query query = em.createQuery("Select o from Orderbook u where o.idOrderBook = :idOrderBook");
		query.setParameter("idOrderBook", idOrderBook);
		try {
			
			o = (Orderbook) query.getSingleResult();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return o;
	}
	
	public List<Orderbook> getList(Map<String, Object> searchParams) {
		List<Orderbook> list = null;

		// 1. Build query string with parameters
		String select = "select o ";
		String from = "from Orderbook o ";
		String where = "";
		String orderby = "order by o.idOrderBook asc, o.price";

		// search for surname
		String idOrderBook = (String) searchParams.get("idOrderBook");
		if (idOrderBook != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "o.idOrderBook like :idOrderBook ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (idOrderBook != null) {
			query.setParameter("idOrderBook", idOrderBook+"%");
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
	//public Orderbook getBookPrice(Book book) {
		//return book.getPrice();
	//}
}
