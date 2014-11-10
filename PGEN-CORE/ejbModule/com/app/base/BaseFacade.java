package com.app.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.app.Interface.Local.BaseFacadeLocal;

/**
 * Session Bean implementation class BaseFacade
 */
@Local(BaseFacadeLocal.class)
@Stateless(mappedName="ejb/BaseFacade")
@TransactionManagement(TransactionManagementType.BEAN)
public class BaseFacade<E> implements BaseFacadeLocal<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected Class<E> className;

	/**
	 * Default constructor.
	 */
	public BaseFacade() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see BaseFacadeLocal#remove(long)
	 */
	public void remove(Object id, EntityManager em) {
		// TODO Auto-generated method stub
		em.remove(find(id, em));
	}

	/**
	 * @see BaseFacadeLocal#find(Object)
	 */
	public E find(Object id, EntityManager em) {
		// TODO Auto-generated method stub
		return (E) em.find(getClassName(), id);
	}

	/**
	 * @see BaseFacadeLocal#createE(E)
	 */
	public E createE(E entity, EntityManager em) {
		// TODO Auto-generated method stub
		em.persist(entity);
		em.flush();
		return entity;
	}

	/**
	 * @see BaseFacadeLocal#update(E)
	 */
	public E update(E entity, EntityManager em) {
		// TODO Auto-generated method stub
		em.merge(entity);
		em.flush();
		return entity;
	}

	@SuppressWarnings("unchecked")
	public Class<E> getClassName() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			className = (Class<E>) paramType.getActualTypeArguments()[0];
		}
		return className;
	}

	public int countE(String QL, EntityManager em) throws Exception {
		return ((Number) createQuery(QL, 0, 0, null, em).getSingleResult())
				.intValue();
	}
	
	protected Query createQuery(String QL, int init, int maxResult,
			Map<String, Object> params, EntityManager em) {
		Query query = em.createQuery(QL, getClassName());
		if (init > 0 && maxResult > 0) {
			query.setFirstResult(maxResult * (init - 1));
			query.setMaxResults(maxResult);
		}

		if (params != null && !params.isEmpty()) {
			for (Iterator<Entry<String, Object>> i = params.entrySet()
					.iterator(); i.hasNext();) {
				Map.Entry<String, Object> e = i.next();
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		return query;
	}
}
