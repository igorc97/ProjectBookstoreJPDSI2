package com.bookstore.book;

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

import bookstore_project.dao.ProductDAO;
import bookstore_project.dao.UserDAO;
import bookstore_project_ejbb.entities.Book;
import bookstore_project_ejbb.entities.User;
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@ViewScoped
public class BookEditBB implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "table?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Book book = new Book();
	private Book loaded = null;

	@EJB
	ProductDAO productDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Book getBook() {
		return book;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Book) flash.get("book");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			book = loaded;
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

		try {
			if (book.getIdBook() == null) {
				// new record
				productDAO.create(book);
			} else {
				// existing record
				productDAO.merge(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
}
