package com.app.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import com.app.entity.enums.EstadoProveedor;
import com.app.entity.providers.ComprasPrvE;
import com.app.entity.providers.ProveedorE;
import com.app.entity.security.UsuarioE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Constants;

@SessionScoped
@ManagedBean(name = "proveedoresPB")
public class ProveedoresPageBean extends JPAEntityBean<ProveedorE> {
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

	private Map<String, Object> parameters ;
	private static final String QL_PROVIDERS = "Select count(e) from ProveedorE e where e.nombre = :nombre";
	private static final String QL_PROVIDERS_EDIT= "Select count(e) from ProveedorE e where e.nombre = :nombre and e.ID = :ID"; 
	
	private static final String QL_PROVIDERS_NIT = "Select count(e) from ProveedorE e where e.nit= :nit";
	private static final String QL_PROVIDERS_NIT_EDIT ="Select count(e) from ProveedorE e where e.nit = :nit and e.ID = :ID";
	
	@Override
	protected boolean beforeSave(EntityManager em) {
		// TODO Auto-generated method stub
		try{
			parameters = new HashMap<String, Object>();
			parameters.put("nombre", entity.getNombre());
			int num = countE(QL_PROVIDERS, parameters, em);
			if (num > 0 && isNew()) {
				warnMsg(getMsg().getString("message.providers.name.exist"));
				return false;
			}
			if (!isNew()) {
				parameters.put("ID", getEntity().getID());
				int numEdit = countE(QL_PROVIDERS_EDIT, parameters, em);
				if (num > 0 && numEdit == 0) {
					warnMsg(getMsg().getString("message.providers.name.exist"));
					return false;
				}
			}
			parameters = new HashMap<String, Object>();
			parameters.put("nit", getEntity().getNit());
			num = countE(QL_PROVIDERS_NIT, parameters, em);
			if (num > 0 && isNew()) {
				warnMsg(getMsg().getString("message.providers.nit.exist"));
				return false;
			}
			if (!isNew()) {
				parameters.put("ID", getEntity().getID());
				int numEdit = countE(QL_PROVIDERS_NIT_EDIT, parameters, em);
				if (num > 0 && numEdit == 0) {
					warnMsg(getMsg().getString("message.providers.nit.exist"));
					return false;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		UsuarioE user = (UsuarioE) getSessionScope().get(Constants.USER);
		if (isNew()) {
			entity.prePersist();
			entity.setCreadoPor(user.getID());
		} else {
			entity.preUpdate();
			entity.setModificadoPor(user.getID());
		}
		
		return super.beforeSave(em);
	}

	public List<SelectItem> getEstadosUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (EstadoProveedor e : EstadoProveedor.values()) {
			items.add(new SelectItem(e));
		}
		return items;
	}
	
	private List<ComprasPrvE> comprasProveedor;
	private JPABase<ComprasPrvE> comprasOpt = new JPABase<ComprasPrvE>();
	private static final String QL_COMPRAS_PROVEEDOR = "SELECT e from ComprasPrvE e where e.proveedorID = :proveedorID";
	
	public List<ComprasPrvE> getCompras(){
		EntityManager em = getEM();
		comprasProveedor = new ArrayList<ComprasPrvE>();
		try{
			if (getEntity() != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("proveedorID", getEntity());
				comprasProveedor = comprasOpt.findListE(QL_COMPRAS_PROVEEDOR,
						0, 10, params, em);
			}
		}catch(Exception e){
			e.printStackTrace();
			comprasProveedor = null;
		}finally{
			em.close();
		}
		
		return comprasProveedor;
	} 

}
