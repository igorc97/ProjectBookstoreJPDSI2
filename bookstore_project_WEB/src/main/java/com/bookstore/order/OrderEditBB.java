package com.bookstore.order;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import bookstore_project.dao.OrderbookDAO;
import bookstore_project.dao.ProductDAO;
import bookstore_project.dao.UserDAO;
import bookstore_project.dao.OrderDAO;
import bookstore_project_ejbb.entities.Order;
import bookstore_project_ejbb.entities.User;
import bookstore_project_ejbb.entities.Book;
import bookstore_project_ejbb.entities.Orderbook;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@ViewScoped
public class OrderEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "/pages/shop/table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_PERSON_PROFILE = "/pages/userinfo/profileView?faces-redirect=true";
	private static final String PAGE_INDEX = "/public/index?faces-redirect=true";
	private Order order = new Order();
	private Order loaded = null;
	private Book book;

	@EJB
	OrderDAO orderDAO;

	@EJB
	UserDAO userDAO;

	@EJB
	ProductDAO productDAO;

	@EJB
	OrderbookDAO orderbookDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Order getOrder() {
		return order;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession)
		// context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Order) flash.get("order");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			order = loaded;
			// session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}

	public String saveData() throws ParseException {

		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		// Role role = new Role();
		// role.setIdRole(2);

		// Role role = roleDAO.findByName("User");
		// user.setRole(role);
		Calendar calendar = Calendar.getInstance(); int noOfDays = 14; String
		  formatted = calendar.toString(); 
		  Date startDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(formatted).getTime()); //Date Hehe =
		  calendar.getTime(); 
		  order.setDateOfOrder(startDate);
		  
		  calendar.add(Calendar.DATE, noOfDays); String nowy = calendar.toString();
		  Date newDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(nowy).getTime()); //Date nowy =
		  calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
		  //calendar.add(Calendar.DAY_OF_YEAR, noOfDays); // date of order receive
		  order.setDateOfReceive(newDate); //User user = new User();
		  //order.setUser(user);
		  
		  String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(); 
		  User userek = userDAO.find(user); 
		  order.setUser(userek);
		  
		  Orderbook orderbook = new Orderbook();
		  flash.put("orderbook", orderbook);
		  
		  orderbook.setBook(book); 
		  orderbook.setOrder(order);
		  orderbook.setPrice(book.getPrice());
		  
		  //orderbook.setBook(); orderbook.setOrder(order);
		  order.addOrderbook(orderbook); //orderbook.setPrice(productDAO.);
		  
		 
		// User user = user.getIdUser();
		// order.setUser(user);
		// stworzenie orderbooka
		// Orderbook orderbook = new Orderbook();
		// orderbook.setPrice(orderbookDAO.);

		try {
			// always a new record
			orderDAO.create(order);
			orderbookDAO.create(orderbook);
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_INDEX;
	}
}
