package com.app.jsf.base;

import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JPAEntityBean<E> extends JPAEntityBase<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPAEntityBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected EntityManagerFactory getEMF() {
		// TODO Auto-generated method stub
		return (EntityManagerFactory) getValue("#{APP.emf}");
	}

	@Override
	protected EntityManager getEM() {
		// TODO Auto-generated method stub
		return getEMF().createEntityManager();
	}

	
	public void edicion() {
	}
	
	// Propiedad para obtener las etiquetas de un archivo .properties es
	// necesario el set de la variable
	@ManagedProperty(value = "#{msg}")
	private ResourceBundle msg;
	
	@ManagedProperty(value="#{content}")
	private ResourceBundle content;

	public void setMsg(ResourceBundle msg) {
		this.msg = msg;
	}

	public ResourceBundle getMsg() {
		return msg;
	}

	public ResourceBundle getContent() {
		return content;
	}

	public void setContent(ResourceBundle content) {
		this.content = content;
	}
	
	
	
}
