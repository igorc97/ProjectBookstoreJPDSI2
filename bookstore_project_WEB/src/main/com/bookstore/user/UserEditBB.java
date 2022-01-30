package com.bookstore.user;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import bookstore_project.dao.UserDAO;
import bookstore_project_ejbb.entities.User;

@Named
@ViewScoped
public class PersonEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Person person = new Person();
	private Person loaded = null;

	@EJB
	PersonDAO personDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Person getPerson() {
		return person;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Person) flash.get("person");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			person = loaded;
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
			if (person.getIdperson() == null) {
				// new record
				personDAO.create(person);
			} else {
				// existing record
				personDAO.merge(person);
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
