package com.app.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import com.app.entity.providers.ComprasPrvE;
import com.app.entity.providers.ProveedorE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "comprasProveedorPB")
public class ComprasProveedorPageBean extends JPAEntityBean<ProveedorE>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String navigationOption() {
		if (isNew()) {
			infMsg(getMsg().getString("message.purchase.new"));
			RequestContext.getCurrentInstance().update("f1");
			RequestContext.getCurrentInstance().execute("dlg1.hide()");
			return newNR;
		} else {
			infMsg(getMsg().getString("message.purchase.edit"));
			RequestContext.getCurrentInstance().update("f1");
			RequestContext.getCurrentInstance().execute("dlg1.hide()");
			return updateNR;
		}
	}
	
	
	private List<ComprasPrvE> comprasProveedor;
	private JPABase<ComprasPrvE> comprasOpt = new JPABase<ComprasPrvE>();
	private static final String QL_COMPRAS_PROVEEDOR = "SELECT e from ComprasPrvE e where e.proveedorID.ID = :proveedorID";
	
	public List<ComprasPrvE> getCompras(long idProveedor){
		EntityManager em = getEM();
		comprasProveedor = new ArrayList<ComprasPrvE>();
		try{
			if (getEntity() != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("proveedorID", idProveedor);
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
