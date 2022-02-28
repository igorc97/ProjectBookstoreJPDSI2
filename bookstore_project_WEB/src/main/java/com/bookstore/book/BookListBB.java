package com.bookstore.book;

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

import bookstore_project.dao.ProductDAO;
import bookstore_project_ejbb.entities.Book;
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class BookListBB {
	private static final String PAGE_PERSON_EDIT = "table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_NEW_BOOK = "newBook?faces-redirect=true";
	
	private String title;
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	ProductDAO productDAO;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Book> getFullList(){
		return productDAO.getFullList();
	}
	public List<Book> getList(){
		List<Book> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (title != null && title.length() > 0){
			searchParams.put("title", title);
		}
		
		//2. Get list
		list = productDAO.getList(searchParams);
		
		return list;
	}

	public String newBook(){
		Book book = new Book();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("book", book);
		
		return PAGE_NEW_BOOK;
	}

	public String editBook(Book book){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("book", book);
		
		return PAGE_PERSON_EDIT;
	}

	public String deleteBook(Book book){
		productDAO.remove(book);
		return PAGE_STAY_AT_THE_SAME;
	}
}
