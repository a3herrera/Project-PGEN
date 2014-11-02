package com.app.jsf.catalog;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.app.entity.catalog.CatgTipoMoneda;
import com.app.jsf.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "tipoMonedaPB")
public class TipoMonedaPageBean extends JPAEntityBean<CatgTipoMoneda> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	protected String navigationOption() {
		if (isNew()) {
			infMsg(msgCreate);
			RequestContext.getCurrentInstance().update("f1");
			RequestContext.getCurrentInstance().execute("dlg1.hide()");
			return newNR;
		} else {
			infMsg(msgUpdate);
			RequestContext.getCurrentInstance().update("f1");
			RequestContext.getCurrentInstance().execute("dlg1.hide()");
			return updateNR;
		}
	}
}
