package com.app.jsf.base;

import javax.persistence.EntityManager;

import com.app.entity.security.UsuarioE;
import com.app.utils.Constants;

public class LoginBeanBase<E> extends JPAEntityBean<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected E resultEntity;

	protected boolean logged;
	protected String isLoggedNR = "main";
	protected String notLoggedNR = "login";

	protected String msgError = "error";
	protected String msgNotResult = "not Result";

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	@Override
	public void clear() {
		logged = false;
		params = null;
		resultEntity = null;
		super.clear();
	}

	@Override
	protected void find() {
		EntityManager em = getEM();
		try {
			resultEntity = findEntity(getListQL(), getParams(), em);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity = null;
		} finally {
			em.close();
		}
	}

	protected boolean validations() throws Exception {
		return true;
	}

	public String login() throws Exception {
		clear();
		beforeFind();
		int countResult = getEntityCount();

		if (countResult == 0) {
			warnMsg(msgNotResult);
			return null;
		}
		if (countResult == 1) {
			find();
		}
		if (countResult > 1) {
			getPaginatedList();
		}
		if (!validations()) {
			entity = newInstace();
			resultEntity= null;
			clear();
			
			return null;
		}
		setLogged(true);
		getSessionScope().put(Constants.LOGGED, isLogged());
		getSessionScope().put(Constants.USER, resultEntity);
		return isLoggedNR;
	}

	public String logOut() {
		setLogged(false);
		getSessionScope().clear();
		getSessionScope().put(Constants.LOGGED, isLogged());
		clear();
		return notLoggedNR;
	}

	public E getResultEntity() {
		return resultEntity;
	}

	
}
