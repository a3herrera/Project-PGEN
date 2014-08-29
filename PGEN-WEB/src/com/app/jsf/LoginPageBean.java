package com.app.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.app.entity.security.UsuarioE;
import com.app.jsf.base.LoginBeanBase;
import com.app.utils.Constants;

@SessionScoped
@ManagedBean(name = "SessionPB")
public class LoginPageBean extends LoginBeanBase<UsuarioE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String login() {
		// TODO Auto-generated method stub
		setLogged(true);
		getSessionScope().put(Constants.LOGGED, isLogged());
		return isLoggedNR;
	}
}
