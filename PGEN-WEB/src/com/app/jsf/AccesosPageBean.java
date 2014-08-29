package com.app.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.app.entity.security.AccesoE;
import com.app.jsf.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "accesosPB")
public class AccesosPageBean extends JPAEntityBean<AccesoE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
