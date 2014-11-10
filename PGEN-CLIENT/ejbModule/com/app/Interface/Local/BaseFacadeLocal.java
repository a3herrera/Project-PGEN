package com.app.Interface.Local;

import java.io.Serializable;

import javax.persistence.EntityManager;

public interface BaseFacadeLocal<E> extends Serializable {

	/**
	 * <p>
	 * Persist the entity send
	 * </p>
	 * 
	 * @param <code>entity</code> type E
	 * @throws Exception
	 * @return E
	 * */
	E createE(E entity, EntityManager em) throws Exception;

	/**
	 * <p>
	 * Merge the entity send
	 * </p>
	 * 
	 * @param <code>entity</code> type E
	 * @throws Exception
	 * @return E
	 * */
	E update(E entity, EntityManager em) throws Exception;

	/**
	 * <p>
	 * Remove the entity send
	 * </p>
	 * 
	 * @throws Exception
	 * @param <code>entity</code> type E
	 * 
	 * */
	void remove(Object id, EntityManager em) throws Exception;

	/**
	 * <p>
	 * Find the entity by primary key
	 * </p>
	 * 
	 * @param <code>ID</code> type E
	 * @throws Exception
	 * @return <E>
	 * 
	 * */
	E find(Object id, EntityManager em) throws Exception;

	
	 int countE(String QL, EntityManager em) throws Exception;
}
