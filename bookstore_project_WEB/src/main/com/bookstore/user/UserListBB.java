package com.bookstore.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash
import javax.servlet.http.HttpSession;

import bookstore_project.dao.UserDAO;
import bookstore_project_ejbb.entities.User;

@Named
@RequestScoped
public class UserListBB {
	private static final String PAGE_PERSON_EDIT = "UserEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String surname;
		
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

	public List<User> getFullList(){
		return UserDAO.getFullList();
	}

	public List<User> getList(){
		List<User> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (surname != null && surname.length() > 0){
			searchParams.put("surname", surname);
		}
		
		//2. Get list
		list = UserDAO.getList(searchParams);
		
		return list;
	}

	public String newPerson(){
		Person person = new Person();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("person", person);
		
		return PAGE_PERSON_EDIT;
	}

	public String editPerson(Person person){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("person", person);
		
		return PAGE_PERSON_EDIT;
	}

	public String deletePerson(Person person){
		personDAO.remove(person);
		return PAGE_STAY_AT_THE_SAME;
	}
}