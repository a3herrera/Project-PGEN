package com.app.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import com.app.entity.security.AccesoE;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "accesosPB")
public class AccesosPageBean extends JPAEntityBean<AccesoE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccesosPageBean() {
		// TODO Auto-generated constructor stub
		super.add = null;
		super.newNR = null;
		super.updateNR = null;
		super.removeNR = null;
		setWhereQL(" where e.padreID is null and e.esPadre = true");
	}

	@Override
	public String newEntity() throws Exception {
		entity = newInstace();
		idEntity = null;
		return null;
	}

	

	private Map<String, Object> parameters;
	private static final String QL_ACCESO = "select count(e) from AccesoE e where upper(e.titulo) = :titulo and e.padreID is null and e.esPadre = true";
	private static final String QL_ACCESO_EDITAR = "select count(e) from AccesoE e where upper(e.titulo) = :titulo and e.padreID is null and e.esPadre = true and e.ID = :ID";

	@Override
	protected boolean beforeSave(EntityManager em) {
		// TODO Auto-generated method stub
		try {
			parameters = new HashMap<String, Object>();
			parameters.put("titulo", entity.getTitulo().toUpperCase());
			int num = countE(QL_ACCESO, parameters, em);
			if (num > 0 && isNew()) {
				errorMsg(null, "El titulo ingresado ya se encuentra registrado");
				return false;
			}
			if (!isNew()) {
				parameters.put("ID", entity.getID());
				int numEdit = countE(QL_ACCESO_EDITAR, parameters, em);
				if (num > 0 && numEdit == 0) {
					errorMsg(null,
							"El titulo ingresado ya se encuentra registrado");
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		entity.setEsPadre(true);
		return super.beforeSave(em);
	}

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

	/*
	 * Manejo de los SubAccesos
	 */

	private AccesoE subAcceso;
	private Object idSubAcceso;

	public AccesoE getSubAcceso() {

		return subAcceso;
	}

	public void setSubAcceso(AccesoE subAcceso) {
		this.subAcceso = subAcceso;
	}

	public Object getIdSubAcceso() {
		return idSubAcceso;
	}

	public void setIdSubAcceso(Object idSubAcceso) throws Exception {
		this.idSubAcceso = idSubAcceso;
		if (idSubAcceso != null) {
			if ((Long) idSubAcceso > 0) {
				EntityManager em = getEM();
				try {
					subAcceso = findEntity(getIdSubAcceso(), em);
				} finally {
					em.close();
				}
			}
			if ((Long) idSubAcceso == 0) {
				setSubAcceso(newInstace());

				subAcceso.setTitulo(getTempAcc().getTitulo());
				subAcceso.setUrl(getTempAcc().getUrl());
				subAcceso.setEstado(getTempAcc().getEstado());
				subAcceso.setRedireccionamiento(getTempAcc()
						.getRedireccionamiento());

			}

		}
	}

	/**
	 * Indica si el subacceso que se esta manejando es nuevo o se esta editando
	 * uno
	 * */
	public boolean isNewSub() {
		return getTempAcc() == null;
	}

	/**
	 * Intancia el nuevo Subacceso, y pone el accesoTemporal como nulo
	 * */
	public void newSubAcceso() throws Exception {
		setSubAcceso(newInstace());
		setTempAcc(null);
	}

	/**
	 * Realiza el procedimiento de agregar el subacceso al accesoPadre, para
	 * cuando se estaba editando o cuando se estaba trabajando con uno nuevo
	 * */
	public void addSubAcceso() {
		if (Utils.isEmptyList(entity.getAccesoE())) {
			entity.setAccesoE(new ArrayList<AccesoE>());
		}
		if (isNewSub()) {
			if (!Utils.isEmptyList(entity.getAccesoE())) {
				List<AccesoE> accPdr = entity.getAccesoE();
				boolean exist = false;
				for (AccesoE acc : accPdr) {
					if (acc.getTitulo().equalsIgnoreCase(
							getSubAcceso().getTitulo())) {
						exist = true;
						break;
					}
				}
				if (exist) {
					errorMsg(null, "El titulo ya se existe");
					return;
				}
			}
			getSubAcceso().setPadreID(entity);
			getEntity().getAccesoE().add(subAcceso);
		} else {
			if (!Utils.compareString(tempAcc.getTitulo(),
					subAcceso.getTitulo(), false)) {
				if (!Utils.isEmptyList(entity.getAccesoE())) {
					List<AccesoE> accPdr = entity.getAccesoE();
					boolean exist = false;
					for (AccesoE acc : accPdr) {
						if (acc.getTitulo().equalsIgnoreCase(
								getSubAcceso().getTitulo())) {
							exist = true;
							break;
						}
					}
					if (exist) {
						errorMsg(null, "El titulo ya se existe");
						return;
					}
				}
			}
			cambios();
		}
		RequestContext.getCurrentInstance().update("f2:tbl2");
		RequestContext.getCurrentInstance().execute("dlg2.hide()");
	}

	private List<AccesoE> eliminados;

	public void removeSubAcceso() {

		if (eliminados == null) {
			eliminados = new ArrayList<AccesoE>();
		}
		eliminados.add(tempAcc);
		entity.getAccesoE().remove(tempAcc);
		RequestContext.getCurrentInstance().update("f2:tbl2");
	}

	public void cancelar() {
		eliminados.clear();
		RequestContext.getCurrentInstance().execute("dlg1.hide()");
	}

	private void cambios() {
		getTempAcc().setTitulo(getSubAcceso().getTitulo());
		getTempAcc().setUrl(getSubAcceso().getUrl());
		getTempAcc().setEstado(getSubAcceso().getEstado());
		getTempAcc().setRedireccionamiento(
				getSubAcceso().getRedireccionamiento());
	}

	private AccesoE tempAcc;
	private List<AccesoE> tempList;

	public AccesoE getTempAcc() {
		return tempAcc;
	}

	public void setTempAcc(AccesoE tempAcc) {
		this.tempAcc = tempAcc;
	}

	public List<AccesoE> getTempList() {
		return tempList;
	}


}
