package com.app.jsf.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 * <p>
 * <code>JPABase</code> provides the basic operations to be performed on
 * DataBase with JPA
 * </p>
 * 
 * @author Angel Alfaro
 * */
public class JPABase<E> extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Class<E> className;

	protected EntityManagerFactory getEMF() {
		return null;
	}

	protected EntityManager getEM() {
		return getEMF().createEntityManager();
	}

	@SuppressWarnings("unchecked")
	protected Class<E> getClassName() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			className = (Class<E>) paramType.getActualTypeArguments()[0];
		}
		return className;
	}

	protected E newInstace() throws Exception {
		return getClassName().newInstance();
	}

	protected E createEntity(E entity, EntityManager em) throws Exception {
		em.persist(entity);
		em.refresh(entity);
		return entity;
	}

	/*
	 * PENDIENTE DE PROCESAR ESTE METODO public E createListEntity() throws
	 * Exception { return null; }
	 */

	protected E updateEntity(E entity, EntityManager em) throws Exception {
		em.persist(entity);
		em.refresh(entity);
		return entity;
	}

	protected void deleteEntity(Object id, EntityManager em) throws Exception {
		em.remove(findEntity(id, em));
	}

	protected E findEntity(Object id, EntityManager em) {
		return em.find(getClassName(), id);
	}

	@SuppressWarnings("unchecked")
	protected E findEntity(String QL, EntityManager em) throws Exception {
		return (E) createQuery(QL, 0, 0, null, em).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	protected E findEntity(String QL, Map<String, Object> params,
			EntityManager em) throws Exception {
		return (E) createQuery(QL, 0, 0, params, em).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	protected List<E> findListEntity(EntityManager em) throws Exception {
		CriteriaQuery<E> cq = (CriteriaQuery<E>) em.getCriteriaBuilder()
				.createQuery();
		cq.select(cq.from(getClassName()));
		return em.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected List<E> findListE(String QL, EntityManager em) throws Exception {
		return createQuery(QL, 0, 0, null, em).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected List<E> findListE(String QL, Map<String, Object> params,
			EntityManager em) throws Exception {
		return createQuery(QL, 0, 0, params, em).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected List<E> findListE(String QL, int init, int maxResult,
			EntityManager em) throws Exception {
		return createQuery(QL, init, maxResult, null, em).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected List<E> findListE(String QL, int init, int maxResult,
			Map<String, Object> params, EntityManager em) {
		return createQuery(QL, init, maxResult, params, em).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected int countE(EntityManager em) {
		CriteriaQuery<E> cq = (CriteriaQuery<E>) em.getCriteriaBuilder()
				.createQuery();
		Root<E> rt = cq.from(getClassName());
		cq.select((Selection<? extends E>) em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	protected int countE(String QL, EntityManager em) throws Exception {
		return ((Number) createQuery(QL, 0, 0, null, em).getSingleResult())
				.intValue();
	}

	protected int countE(String QL, Map<String, Object> params, EntityManager em)
			throws Exception {
		return ((Number) createQuery(QL, 0, 0, params, em).getSingleResult())
				.intValue();
	}

	protected int countE(String QL, int init, int maxResult, EntityManager em) {
		return ((Number) createQuery(QL, init, maxResult, null, em)
				.getSingleResult()).intValue();
	}

	protected int countE(String QL, int init, int maxResult,
			Map<String, Object> params, EntityManager em) {
		return ((Number) createQuery(QL, init, maxResult, params, em)
				.getSingleResult()).intValue();
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
