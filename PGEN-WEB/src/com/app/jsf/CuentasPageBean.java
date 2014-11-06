package com.app.jsf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.app.entity.banks.BancoE;
import com.app.entity.banks.CuentaE;
import com.app.entity.catalog.CatgTipoMoneda;
import com.app.entity.enums.TiposCuenta;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "cuentaPB")
public class CuentasPageBean extends JPAEntityBean<CuentaE> {
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

	public List<SelectItem> getTipoUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TiposCuenta e : TiposCuenta.values()) {
			items.add(new SelectItem(e.toString()));
		}
		return items;
	}

	private JPABase<BancoE> optBanco = new JPABase<BancoE>();
	private JPABase<CatgTipoMoneda> optMoneda = new JPABase<CatgTipoMoneda>();
	private static final String QL_BANCOS = "SELECT e from BancoE e where e.estado = true";
	private static final String QL_MONEDA = "SELECT e from CatgTipoMoneda e where e.estado = true";

	private List<BancoE> getBancos() {
		List<BancoE> bancos;
		EntityManager em = getEM();
		try {
			bancos = optBanco.findListE(QL_BANCOS, em);
		} catch (Exception e) {
			bancos = null;
		} finally {
			em.close();
		}
		return bancos;
	}

	private List<CatgTipoMoneda> getMoneda() {
		List<CatgTipoMoneda> moneda;
		EntityManager em = getEM();
		try {
			moneda = optMoneda.findListE(QL_MONEDA, em);
		} catch (Exception e) {
			moneda = null;
		} finally {
			em.close();
		}
		return moneda;
	}

	public List<SelectItem> getBancosUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		List<BancoE> bancos = getBancos();
		if (!Utils.isEmptyList(bancos)) {
			for (BancoE e : bancos) {
				items.add(new SelectItem(e.getID(), e.getNombre()));
			}
		}
		return items;
	}

	public List<SelectItem> getMonedaUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		List<CatgTipoMoneda> moneda = getMoneda();
		if (!Utils.isEmptyList(moneda)) {
			for (CatgTipoMoneda e : moneda) {
				String texto = "";
				if (!Utils.isEmtpy(e.getAbreviatura())) {
					texto += e.getAbreviatura() + " - ";
				}
				texto += e.getMoneda();
				items.add(new SelectItem(e.getID(), texto));
			}
		}
		return items;
	}
	
	

	public long getIdBanco() {
		return isNew() ? -1 : getEntity().getBancoID().getID();
	}

	public void setIdBanco(long idBanco) {
		List<BancoE> banco = getBancos();
		for(BancoE e: banco){
			if(e.getID() == idBanco){
				getEntity().setBancoID(e);;
				break;
			}
		}
	}

	public long getIdMoneda() {
		return isNew() ? -1 : getEntity().getMoneda().getID();
	}

	public void setIdMoneda(long idMoneda) {
		List<CatgTipoMoneda> moneda = getMoneda();
		for(CatgTipoMoneda e: moneda){
			if(e.getID() == idMoneda){
				getEntity().setMoneda(e);
				break;
			}
		}
	}
	
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
	
     
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
 
	
	

}
