package com.bookstore.orderbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import bookstore_project.dao.OrderbookDAO;
import bookstore_project_ejbb.entities.Orderbook;
import bookstore_project_ejbb.entities.Role;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@ViewScoped
public class OrderBookEditBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_PERSON_LIST = "/pages/shop/table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private Orderbook orderbook = new Orderbook();
	private Orderbook loaded = null;
	
	@EJB
	OrderbookDAO orderbookDAO;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	public Orderbook getOrderbook() {
		return orderbook;
	}
	
	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession)
		// context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Orderbook) flash.get("orderbook");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			orderbook = loaded;
			// session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}
	
	public String saveData() {

		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		//Role role = new Role();
		//role.setIdRole(2);
		
		//Role role = roleDAO.findByName("User");
		//user.setRole(role);

		try {
			// always a new record
			orderbookDAO.create(orderbook);
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
}
