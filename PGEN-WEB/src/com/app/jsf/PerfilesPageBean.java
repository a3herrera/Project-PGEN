package com.app.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.app.entity.security.PerfilE;
import com.app.jsf.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "perfilPB")
public class PerfilesPageBean extends JPAEntityBean<PerfilE> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
