package com.app.jsf.base;

import javax.persistence.EntityManager;

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
		super.clear();
	}

	@Override
	protected void find() {
		beforeFind();
		EntityManager em = getEM();
		try {
			resultEntity = findEntity(getListQL(), getParams(), em);
		} catch (Exception e) {
			resultEntity = null;
		} finally {
			em.close();
		}
	}

	protected boolean validations() {
		return true;
	}

	public String login() {
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
}
