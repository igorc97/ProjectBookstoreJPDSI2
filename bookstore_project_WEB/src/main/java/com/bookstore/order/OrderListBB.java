package com.bookstore.order;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.ExternalContext;

import javax.faces.context.Flash;


import bookstore_project.dao.OrderDAO;
import bookstore_project_ejbb.entities.Order;


@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class OrderListBB {
	private static final String PAGE_CART = "/pages/shop/cart?faces-redirect=true";
	private static final String PAGE_PERSON_EDIT = "table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private String idOrder;
	private 
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	OrderDAO orderDAO;
	
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

	public String newOrder(){
		Order order = new Order();
		//pobierz id usera
		//order.getUser().getIdUser();
		
		//pobierz
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("order", order);
		
		return PAGE_CART;
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
}
