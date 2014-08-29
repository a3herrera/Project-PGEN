package com.app.jsf.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.app.utils.Utils;

/**
 * <p>
 * <code>JPAEntityBase</code> provides the basic functions to be performed with
 * a JPA Entity in conjuction with a ManagedBean
 * </p>
 * */
public class JPAEntityBase<E> extends JPABase<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected E entity;
	protected Object idEntity;
	protected List<E> listEntity;
	protected Integer countEntity;

	protected int page = 1;
	protected int pageSize = 20;

	protected String listQL;
	protected String countQL;
	protected String whereQL;
	protected Map<String, Object> params = new HashMap<String, Object>();

	protected String msgCreate = "created";
	protected String msgUpdate = "updated";
	protected String msgRemove = "removed";

	protected String newNR = "new";
	protected String updateNR = "update";
	protected String removeNR = "remove";

	public void clear() {
		listEntity = null;
		countEntity = null;
		page = 1;
	}

	protected String getWhereQL() {
		return whereQL;
	}

	protected void setWhereQL(String whereQL) {
		this.whereQL = whereQL;
		listQL = null;
		countQL = null;
	}

	protected String getListQL() {
		if (Utils.isEmtpy(listQL)) {
			listQL = "Select e from " + getClassName().getSimpleName() + " e";
			if (Utils.isEmtpy(whereQL)) {
				listQL += whereQL;
			}
		}
		return listQL;
	}

	protected String getCountQL() {
		if (Utils.isEmtpy(countQL)) {
			countQL = "Select e from " + getClassName().getSimpleName() + " e";
			if (Utils.isEmtpy(whereQL)) {
				countQL += whereQL;
			}
		}
		return countQL;
	}

	protected Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		return params;
	}

	protected void setParam(String key, Object value) {
		getParams().put(key, value);
	}

	/*
	 * Functions of the entity
	 */

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public Object getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Object idEntity) {
		this.idEntity = idEntity;
		if (idEntity == null) {
			entity = null;
		} else {
			find();
		}
	}

	public String newEntity() throws Exception {
		entity = newInstace();
		idEntity = null;
		return newNR;
	}

	public boolean isNewEntity() {
		return getIdEntity() == null;
	}

	protected void beforeFind() {
	}

	protected void afterFind(EntityManager em) {
	}

	protected void find() {
		beforeFind();
		EntityManager em = getEM();
		try {
			entity = findEntity(getIdEntity(), em);
			if (entity == null) {
				setIdEntity(null);
			} else {
				afterFind(em);
			}
		} finally {
			em.close();
		}
	}

	protected boolean beforeSave(EntityManager em) {
		return true;
	}

	protected void afterSave(EntityManager em) {
		listEntity = null;
		countEntity = null;
	}

	public String saveEntity() {
		try {
			EntityManager em = getEM();
			try {
				if (beforeSave(em)) {
					em.getTransaction().begin();
					try {
						if (isNewEntity()) {
							createEntity(entity, em);
						} else {
							updateEntity(entity, em);
						}
						afterSave(em);
						em.getTransaction().commit();
					} catch (RuntimeException e) {
						if (em.getTransaction().isActive())
							em.getTransaction().rollback();
						throw e;
					}
				} else {
					return null;
				}
			} finally {
				em.close();
			}
			if (isNewEntity()) {
				infMsg(msgCreate);
				return newNR;
			} else {
				infMsg(msgUpdate);
				return updateNR;
			}
		} catch (Exception ex) {
			// logger la Exception
		}
		return null;
	}

	protected boolean beforeDelete(EntityManager em) {
		return true;
	}

	protected void afterDelete() {
		listEntity = null;
		countEntity = null;
		entity = null;
		idEntity = null;
	}

	public String delete() {
		try {
			EntityManager em = getEM();
			try {
				if (beforeDelete(em)) {
					em.getTransaction().begin();
					try {
						deleteEntity(idEntity, em);
						em.getTransaction().commit();
					} catch (RuntimeException e) {
						if (em.getTransaction().isActive())
							em.getTransaction().rollback();
						throw e;
					}
				} else {
					return null;
				}
			} finally {
				em.close();
			}
			infMsg(msgRemove);
			return removeNR;
		} catch (Exception e) {
			// logger la Exception
		}
		return null;
	}

	/*
	 * Consulta de registros
	 */

	protected void beforeList() {
	}

	public List<E> getPaginatedList() {
		if (listEntity == null) {
			EntityManager em = getEM();
			try {
				beforeList();
				listEntity = findListE(getListQL(), page, pageSize,
						getParams(), em);
				countEntity = countE(getCountQL(), em);
			} catch (Exception e) {
				// logger the Exception
				countEntity = 0;
			} finally {
				em.close();
			}
		}
		return listEntity;
	}

	public int getEntityCount() {
		if (countEntity == null) {
			EntityManager em = getEM();
			try {
				countEntity = countE(getCountQL(), em);
			} catch (Exception e) {
				// logger the Exception
			} finally {
				em.close();
			}
		}
		return countEntity;
	}

	public int getPageCount() {
		int count = getEntityCount();
		if (count % pageSize == 0) {
			return count / pageSize;
		}
		return (count / pageSize) + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		clear();
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return page;
	}

	public void setCurrPage(int currPageNo) {
		if (currPageNo < 0) {
			currPageNo = 1;
		} else if (currPageNo > getPageCount()) {
			currPageNo = getPageCount();
		}
		listEntity = null;
		page = currPageNo;
	}

	public String first() {
		setCurrPage(1);
		return null;
	}

	public String previous() {
		setCurrPage(--page);
		return null;
	}

	public String next() {
		setCurrPage(++page);
		return null;
	}

	public String last() {
		setCurrPage(getPageCount());
		return null;
	}

	public boolean getPreviousExists() {
		return page > 1;
	}

	public boolean getNextExists() {
		return page < getPageCount();
	}

}
