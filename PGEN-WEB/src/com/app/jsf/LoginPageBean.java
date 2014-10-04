package com.app.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.app.entity.security.AccesoE;
import com.app.entity.security.UsuarioE;
import com.app.jsf.base.LoginBeanBase;
import com.app.utils.Constants;
import com.app.utils.Utils;

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

	public List<AccesoE> getPadres() {

		List<AccesoE> urls = new ArrayList<AccesoE>();

		AccesoE admin = new AccesoE();
		admin.setTitulo("Administración");
		admin.setUrl("/seguridad/admin.xhtml?faces-redirect=true");

		urls.add(admin);
		return urls;
	}

	public List<AccesoE> getHijos() {
		List<AccesoE> urls = new ArrayList<AccesoE>();
		AccesoE grupo = new AccesoE();
		grupo.setTitulo("Grupos");
		grupo.setUrl("/seguridad/grupos.xhtml?faces-redirect=false");

		AccesoE accesos = new AccesoE();
		accesos.setTitulo("Accesos");
		accesos.setUrl("/seguridad/accesos.xhtml?faces-redirect=false");

		AccesoE perfil = new AccesoE();
		perfil.setTitulo("Perfiles");
		perfil.setUrl("/seguridad/perfiles.xhtml?faces-redirect=false");
		
		urls.add(accesos);
		urls.add(grupo);
		urls.add(perfil);

		return urls;
	}

	private String action;

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public String navegacion() {
		return getAction();
	}
}
