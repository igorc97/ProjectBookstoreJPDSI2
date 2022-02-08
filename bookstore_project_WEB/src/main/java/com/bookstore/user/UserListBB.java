package com.bookstore.user;

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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bookstore_project.dao.UserDAO;
import bookstore_project_ejbb.entities.User;
import bookstore_project_ejbb.entities.Role;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class UserListBB {
	private static final String PAGE_PERSON_EDIT = "table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_PERSON_REGISTRATION = "registrationView?faces-redirect=true";
	private static final String PAGE_PERSON_PROFILE = "profileView?faces-redirect=true";
	private static final String PAGE_MAIN = "index?faces-redirect=true";

	private String surname;
	private String login;
	private String pass;
	

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@Inject
	FacesContext context;

	@EJB
	UserDAO userDAO;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	
	  public String getLogin() { return login; }
	  
	  public void setLogin(String login) { this.login = login; }
	  
	  public String getPass() { return pass; }
	  
	 public void setPass(String pass) { this.pass = pass; }
	 

	public List<User> getFullList() {
		return userDAO.getFullList();
	}

	public List<User> getList() {
		List<User> list = null;

		// 1. Prepare search params
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (surname != null && surname.length() > 0) {
			searchParams.put("surname", surname);
		}

		// 2. Get list
		list = userDAO.getList(searchParams);

		return list;
	}

	public String newUser() {
		User user = new User();

		// 1. Pass object through session
		// HttpSession session = (HttpSession) extcontext.getSession(true);
		// session.setAttribute("person", person);

		// 2. Pass object through flash
		flash.put("user", user);

		return PAGE_PERSON_REGISTRATION;
	}

	public String editUser(User user) {
		// 1. Pass object through session
		// HttpSession session = (HttpSession) extcontext.getSession(true);
		// session.setAttribute("person", person);

		// 2. Pass object through flash
		flash.put("user", user);

		return PAGE_PERSON_EDIT;
	}

	public String deleteUser(User user) {
		userDAO.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}

	public String loginUser() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		User user = userDAO.findByLogin(login);
		if(user == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Niepoprawny login lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		/*
		 * RemoteClient<User> client = new RemoteClient<User>(); //create new
		 * RemoteClient client.setDetails(user);
		 * 
		 * List<String> roles = userDAO.getUserRolesFromDatabase(user); //get User roles
		 * 
		 * if (roles != null) { //save roles in RemoteClient for (String role: roles) {
		 * client.getRoles().add(role); } }
		 * 
		 * //store RemoteClient with request info in session (needed for SecurityFilter)
		 * HttpServletRequest request = (HttpServletRequest)
		 * ctx.getExternalContext().getRequest(); client.store(request);
		 */

		// and enter the system (now SecurityFilter will pass the request)
		return PAGE_MAIN;
	}
	
	
	
	  public String logout() {
		  HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(true);
			//Invalidate session
			// - all objects within session will be destroyed
			// - new session will be created (with new ID)
			session.invalidate();
		  return PAGE_MAIN;
	  }
	 
	  public String actualUser() {
		  return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	  }
}