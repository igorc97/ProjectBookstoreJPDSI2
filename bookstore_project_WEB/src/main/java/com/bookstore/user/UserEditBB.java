package com.bookstore.user;

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

import bookstore_project.dao.UserDAO;
import bookstore_project.dao.RoleDAO;
import bookstore_project_ejbb.entities.Role;
import bookstore_project_ejbb.entities.User;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@ViewScoped
public class UserEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_PERSON_PROFILE = "profileView?faces-redirect=true";

	private User user = new User();
	private User loaded = null;

	@EJB
	UserDAO userDAO;
	@EJB
	RoleDAO roleDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public User getUser() {
		return user;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession)
		// context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (User) flash.get("user");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			user = loaded;
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
		
	Role role = roleDAO.findByName("User");
		user.setRole(role);

		try {
			// always a new record
			userDAO.create(user);
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
	
	
}
