package com.app.jsf.base;

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

}
