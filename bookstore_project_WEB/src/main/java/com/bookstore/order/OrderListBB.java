package com.bookstore.order;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import bookstore_project.dao.UserDAO;
import bookstore_project.dao.ProductDAO;
import bookstore_project.dao.OrderbookDAO;
import bookstore_project.dao.OrderDAO;
import bookstore_project_ejbb.entities.Order;
import bookstore_project_ejbb.entities.Orderbook;
import bookstore_project_ejbb.entities.Book;
import bookstore_project_ejbb.entities.User;


@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class OrderListBB {
	private static final String PAGE_CART = "/pages/shop/cart?faces-redirect=true";
	private static final String PAGE_PERSON_EDIT = "table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_INDEX = "/public/index?faces-redirect=true";
	private String isbn;
	private String idOrder;
	private String login;
	private Book book;
	private Order order;
	private Orderbook orderbook;
	private User user;
	private List<Orderbook> orderbooks;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	OrderDAO orderDAO;
	@EJB
	OrderbookDAO orderbookDAO;
	@EJB
	ProductDAO productDAO;
	@EJB
	UserDAO	userDAO;
	
	public String getidOrder() {
		return idOrder;
	}
	
	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}
	
	public List<Order> getFullList(){
		return orderDAO.getFullList();
	}
	
	public List<Order> getList(){
		List<Order> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (idOrder != null && idOrder.length() > 0){
			searchParams.put("idOrder", idOrder);
		}
		
		//2. Get list
		list = orderDAO.getList(searchParams);
		
		return list;
	}

	public String newOrder(Book u, User remoteClient){
			
					
	book = u;
	order = new Order();
	orderbook = new Orderbook();
		//long millis = System.currentTimeMillis();
		//java.sql.Date date = new java.sql.Date(millis);
	
	Date today = java.sql.Date.valueOf(java.time.LocalDate.now());
	Date receivedate = java.sql.Date.valueOf(java.time.LocalDate.now().plusWeeks(2));
		order.setDateOfOrder(today);
		order.setDateOfReceive(receivedate);
		
	//order.setDateOfOrder(date); //DATE OF ORDER
		//long newmillis = millis + 1209600000;
		//java.sql.Date nowadata = new java.sql.Date(newmillis);
	
	
	//order.setDateOfReceive(nowadata); //DATE OF RECEIVE
		//order.setUser(remoteClient); //idUser
		//int a = remoteClient.getIdUser();
		String login2 = remoteClient.getLogin();
		//User idusera = userDAO.findByIdUser(a);
		
		//User user = userDAO.find(userDAO.getUserID(login2));
	//System.out.println(user.getName());
		User user = userDAO.findBySurname(remoteClient.getSurname());
		order.setUser(user);
	//
		//order.setOrderbooks(orderbooks);
		/*
		 * SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd"); Calendar
		 * calendar = Calendar.getInstance(); Date data1; try { data1 =
		 * format.parse(Calendar.getInstance().toString());
		 * 
		 * order.setDateOfOrder(data1); } catch (ParseException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
			
		
		
			
					//String sDate1 = calendar.toString();
					//DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					//LocalDate localDate = LocalDate.parse(sDate1, format);
					//java.sql.Date dob   = java.sql.Date.valueOf(localDate);
			
		
		
		
					/*
					 * calendar.add(Calendar.DATE, noOfDays); try { Date data2 =
					 * format.parse(calendar.getInstance().toString()); } catch (ParseException e) {
					 * // TODO Auto-generated catch block e.printStackTrace(); }
					 */
			//String sDate2 = calendar.toString();
			//LocalDate DateOfRec = LocalDate.parse(sDate2, format);
			//java.sql.Date dob2 = java.sql.Date.valueOf(DateOfRec);
			
			//String nowy = calendar.toString();
			//Date newDate = new java.sql.Date(new wwSimpleDateFormat("yyyy-MM-dd").parse(nowy).getTime());
			//calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
		//order.setDateOfReceive(calendar.getTime());
		//order.setOrderbooks(orderbooks);
		
		//orderbook = b;
		orderbook.setBook(book); //idBook
		orderbook.setOrder(order); //idOrder
		orderbook.setPrice(book.getPrice()); //price
		order.setOrderbooks(orderbooks);
		///orderbook.setIdOrderBook(0);             //??
		//Order order = new Order();
		//Orderbook orderbook = new Orderbook();
		//pobierz id usera
		//order.getUser().getIdUser();
		
		//pobierz
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		//flash.put("order", order);
		//flash.put("orderbook", orderbook);
		try {
			// always a new record
			orderDAO.create(order);
			orderbookDAO.create(orderbook);
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu zamówienia", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_INDEX;
	
		
		
	}

	public String editOrder(Order order){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("order", order);
		
		return PAGE_PERSON_EDIT;
	}

	public String deleteOrder(Order order){
		orderDAO.remove(order);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	// GETTER && SETTER
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Orderbook getOrderbook() {
		return orderbook;
	}
	
	public void setOrderbook(Orderbook orderbook) {
		this.orderbook = orderbook;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public List<Orderbook> getOrderbooks() {
		return this.orderbooks;
	}

	public void setOrderbooks(List<Orderbook> orderbooks) {
		this.orderbooks = orderbooks;
	}
}
