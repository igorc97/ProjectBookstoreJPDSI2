package bookstore_project.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore_project_ejbb.entities.Book;

@Stateless
public class ProductDAO {

	@PersistenceContext
	EntityManager em;
	public void create(Book book) {
		em.persist(book);
	}
	public Book merge(Book book) {
		return em.merge(book);
	}
	public void remove(Book book) {
		em.remove(em.merge(book));
	}
	public Book find(Object id) {
		return em.find(Book.class, id);
	}
	public List<Book> getFullList(){
		List<Book> list = null;
		Query query = em.createQuery("Select b from book b");
		try {
			list = query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Book> getList(Map<String, Object> searchParams) {
		List<Book> list = null;

		// 1. Build query string with parameters
		String select = "select b ";
		String from = "from Book b ";
		String where = "";
		String orderby = "order by b.title asc, b.author";

		// search for surname
		String title = (String) searchParams.get("title");
		if (title != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "b.title like :title ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (title != null) {
			query.setParameter("title", title+"%");
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
}
