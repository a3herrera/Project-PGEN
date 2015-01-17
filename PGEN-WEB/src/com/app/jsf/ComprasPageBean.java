package com.app.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import com.app.entity.enums.EstadoCompra;
import com.app.entity.enums.EstadoProveedor;
import com.app.entity.providers.ComprasPrvE;
import com.app.entity.providers.ProveedorE;
import com.app.entity.security.UsuarioE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Constants;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "comprasPB")
public class ComprasPageBean extends JPAEntityBean<ComprasPrvE> {
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

	
	
	@Override
	protected boolean beforeSave(EntityManager em) {
		// TODO Auto-generated method stub
		if (getEntity().getModificadoPor() < 0) {
			warnMsg(getMsg().getString("message.providers.nit.exist"));
			return false;
		}
		
		if(getEntity().getProveedorID().getEstado() != EstadoProveedor.Activo){
			warnMsg(getMsg().getString("message.purchase.provider.state"));
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

	public List<SelectItem> getEstadosSearchUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(null, "Todos"));
		for (EstadoCompra e : EstadoCompra.values()) {
			items.add(new SelectItem(e));
		}
		return items;
	}

	public void nuevaCompraGeneral() {
		entity.setEstado(EstadoCompra.Activa);
		entity.setFechaCompra(new Date());
		saveEntity();
	}

	private static final String QL_PROVEEDORES = "Select e from ProveedorE e";
	private JPABase<ProveedorE> optProveedor = new JPABase<ProveedorE>();

	private List<ProveedorE> getListProviders() {
		List<ProveedorE> proveedores = new ArrayList<ProveedorE>();
		EntityManager em = getEM();
		try {
			proveedores = optProveedor.findListE(QL_PROVEEDORES, em);
		} catch (Exception e) {
			e.printStackTrace();
			proveedores = null;
		} finally {
			em.close();
		}
		return proveedores;
	}

	public List<SelectItem> getProvidersInfoUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		List<ProveedorE> proveedores = getListProviders();

		if (!Utils.isEmptyList(proveedores)) {
			for (ProveedorE e : proveedores) {
				items.add(new SelectItem(e.getID(), e.getNombre()));
			}
		}
		return items;
	}

	public long getProveedorID() {
		return (getEntity() != null && getEntity().getProveedorID() != null) ? getEntity()
				.getProveedorID().getID() : -1;
	}

	public void setProveedorID(long proveedorID) {
		List<ProveedorE> proveedores = getListProviders();
		if (!Utils.isEmptyList(proveedores)) {
			for (ProveedorE e : proveedores) {
				if (e.getID() == proveedorID) {
					getEntity().setProveedorID(e);
					break;
				}
			}
		}
	}
	
	//Filtros de Busqueda

	private String number;
	private String providerName;
	private EstadoCompra estado;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public EstadoCompra getEstado() {
		return estado;
	}

	public void setEstado(EstadoCompra estado) {
		this.estado = estado;
	}
	
	@Override
	protected void beforeList() {
		// TODO Auto-generated method stub
		whereQL = new String();
		newParams();
		if(!Utils.isEmtpy(getNumber())){
			setWhereQL(" where e.ID like :ID ");
			this.params.put("ID", getNumber());
		}
		if(!Utils.isEmtpy(getProviderName())){
			if(Utils.isEmtpy(getWhereQL())){
				setWhereQL(" where upper(e.proveedorID.nombre) like :proveedor ");
			}else{
				setWhereQL(getWhereQL() + " and upper(e.proveedorID.nombre) like :proveedor ");
			}
			this.params.put("proveedor", "%" + getProviderName().toUpperCase() + "%");
		}
		if(getEstado()!=null){
			if(Utils.isEmtpy(getWhereQL())){
				setWhereQL(" where e.estado = :estado ");
			}else{
				setWhereQL(getWhereQL() + " and e.estado = :estado ");
			}
			this.params.put("estado", getEstado());
		}
		super.beforeList();
	}
	
	public void buscar(){
		clear();
		this.listQL = null;
		this.countQL = null;
	}

}
