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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
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
	
	private String surname;
	/*
	 * private String name; private String login; private String pass; private
	 * String email; private Date birthdate;
	 */
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
		
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	

	
	/*
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 * 
	 * public String getLogin() { return login; }
	 * 
	 * public void setLogin(String login) { this.login = login; }
	 * 
	 * public String getPass() { return pass; }
	 * 
	 * public void setPass(String pass) { this.pass = pass; }
	 * 
	 * public String getEmail() { return email; }
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * public Date getBirthdate() { return birthdate; }
	 * 
	 * public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
	 */
	
	public List<User> getFullList(){
		return userDAO.getFullList();
	}

	public List<User> getList(){
		List<User> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (surname != null && surname.length() > 0){
			searchParams.put("surname", surname);
		}
		
		//2. Get list
		list = userDAO.getList(searchParams);
		
		return list;
	}

	public String newUser(){
		User user = new User();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("user", user);
		
		return PAGE_PERSON_REGISTRATION;
	}

	public String editUser(User user){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("user", user);
		
		return PAGE_PERSON_EDIT;
	}

	public String deleteUser(User user){
		userDAO.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}
}