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
import javax.faces.simplesecurity.RemoteClient;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bookstore_project.dao.RoleDAO;
import bookstore_project.dao.UserDAO;
import bookstore_project_ejbb.entities.User;
import bookstore_project_ejbb.entities.Role;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class UserListBB {
	private static final String PAGE_PERSON_EDIT = "/pages/admin/userEditPanel?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_PERSON_REGISTRATION = "/public/registrationView?faces-redirect=true";
	private static final String PAGE_PERSON_PROFILE = "/pages/userinfo/profileView?faces-redirect=true";
	private static final String PAGE_MAIN = "/public/index?faces-redirect=true";

	private String surname;
	private String login;
	private String pass;
	private String nameRole;
	

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@Inject
	FacesContext context;

	@EJB
	UserDAO userDAO;
	
	@EJB
	RoleDAO roleDAO;

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
	 
	 public String getNameRole() {
		 return nameRole;
	 }
	 
	 public void setNameRole(String nameRole) {
		 this.nameRole = nameRole;
	 }

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
	
	public List<Role> getList2() {
		List<Role> list = null;

		// 1. Prepare search params
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (surname != null && surname.length() > 0) {
			searchParams.put("surname", surname);
		}

		// 2. Get list
		list = roleDAO.getList2(searchParams);

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
		//User user = userDAO.findByLogin(login); 
		User user = userDAO.findByLoginAndPass(login, pass);
		if(user == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Niepoprawny login lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		Role role = new Role();
		//Role rolka = roleDAO.findByName("User");
		
		
		
		  RemoteClient<User> client = new RemoteClient<User>(); //create new
		  client.setDetails(user);
		  //client.getRoles().add(user.getRole(rolka));
		  List<String> roles = roleDAO.getRolesFromDB(role); //get User roles 
			
			if (roles != null) { //save roles in RemoteClient
				for (String role4: roles) {
					client.getRoles().add(role4);
					//client.getDetails().getIdUser();
				}
			}
		  
		  //store RemoteClient with request info in session (needed for SecurityFilter)
		 HttpServletRequest request = (HttpServletRequest)
		  ctx.getExternalContext().getRequest();
		  client.store(request);

		// and enter the system (now SecurityFilter will pass the request)
		/* return PAGE_MAIN; */
		  return PAGE_MAIN;
		  //String userus = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		  
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