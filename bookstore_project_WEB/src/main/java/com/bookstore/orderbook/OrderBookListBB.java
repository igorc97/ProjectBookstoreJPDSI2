package com.bookstore.orderbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import bookstore_project.dao.OrderbookDAO;
import bookstore_project_ejbb.entities.Order;
import bookstore_project_ejbb.entities.Orderbook;
import bookstore_project.dao.ProductDAO;
import bookstore_project_ejbb.entities.Book;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class OrderBookListBB {

	private static final String PAGE_PERSON_EDIT = "table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_BUY_CONFIRM = "cart?faces-redirect=true";
	
	private int idOrderBook;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	OrderbookDAO orderbookDAO;
	
	@EJB
	ProductDAO productDAO;
	
	public int getIdOrderBook() {
		return idOrderBook;
	}
	
	public void setIdOrderBook(int idOrderBook) {
		this.idOrderBook = idOrderBook;
	}
	
	public List<Orderbook> getList(){
		List<Orderbook> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (Integer.toString(idOrderBook) != null && Integer.toString(idOrderBook).length() > 0){
			searchParams.put("idOrderBook", idOrderBook);
		}
		
		//2. Get list
		list = orderbookDAO.getList(searchParams);
		
		return list;
	}
	
	public String newOrderBook(){
		Orderbook orderbook = new Orderbook();
		
		orderbook.getBook().addOrderbook(orderbook);
		//pobierz cene produktu
		
		//pobierz id produktu
		
		//pobierz id order
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("orderbook", orderbook);
		
		return PAGE_BUY_CONFIRM;
	}
	
	public String editOrderBook(Orderbook orderbook){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("orderbook", orderbook);
		
		return PAGE_PERSON_EDIT;
	}

	public String deleteOrderBokk(Orderbook orderbook){
	
		orderbookDAO.remove(orderbook);
		return PAGE_STAY_AT_THE_SAME;
	}
}
