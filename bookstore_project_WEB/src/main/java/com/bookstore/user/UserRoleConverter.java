package com.bookstore.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import bookstore_project_ejbb.entities.Role;


@FacesConverter("uRConverter")
public class UserRoleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return new Role();
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		return value.toString();
		
	}
	
}
