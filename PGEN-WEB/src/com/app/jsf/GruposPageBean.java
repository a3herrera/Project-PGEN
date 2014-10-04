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
import com.app.entity.security.AccesosGrupoE;
import com.app.entity.security.GrupoE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "gruposPB")
public class GruposPageBean extends JPAEntityBean<GrupoE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GruposPageBean() {
		// TODO Auto-generated constructor stub
		super.add = null;
		super.newNR = null;
		super.updateNR = null;
		super.removeNR = null;
	}

	JPABase<AccesoE> accesOptions = new JPABase<AccesoE>();
	JPABase<AccesosGrupoE> groupOptions = new JPABase<AccesosGrupoE>();

	@Override
	public String newEntity() throws Exception {
		entity = newInstace();
		idEntity = null;
		return null;
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

	private static final String QL_ACCESOS1 = "Select e from AccesoE e where e.esPadre = true and e.ID not in :ids";
	private static final String QL_ACCESOS2 = "Select e from AccesoE e where e.esPadre = true";
	private Map<String, Object> parameters;

	/**
	 * <p>
	 * Obtiene los accesos que no estan ligados al grupo
	 * </p>
	 * */
	public List<AccesoE> getAccesos() throws Exception {
		List<AccesoE> accesos = new ArrayList<AccesoE>();
		EntityManager em = getEM();
		try {

			if (entity != null && entity.getAccesosGrupo() != null) {
				if (entity.getAccesosGrupo().size() > 0) {
					parameters = new HashMap<String, Object>();
					parameters.put("ids", getIdAcces(entity.getAccesosGrupo()));
					accesos = accesOptions.findListE(QL_ACCESOS1, parameters,
							em);
				} else {
					accesos = accesOptions.findListE(QL_ACCESOS2, em);
				}

			} else {
				accesos = accesOptions.findListE(QL_ACCESOS2, em);
			}
		} finally {
			em.close();
		}

		return accesos;
	}

	/**
	 * <p>
	 * Crea un listado de los ID de accesos que no estan ligados al grupo que se
	 * esta trabajando en el momento
	 * </p>
	 * */
	private ArrayList<Long> getIdAcces(List<AccesosGrupoE> accesosGrupo) {
		ArrayList<Long> list = new ArrayList<Long>();
		for (AccesosGrupoE group : accesosGrupo) {
			list.add(group.getAccesoID().getID());
		}
		return list;
	}

	/*
	 * Asignacion de nuevos accesos al grupo
	 */

	private AccesoE acceso;

	public AccesoE getAcceso() {
		return acceso;
	}

	public void setAcceso(AccesoE acceso) {
		this.acceso = acceso;
	}

	/**
	 * <p>
	 * Agrega el acceso al listado del grupo
	 * </p>
	 * */
	public void addAcceso() {
		if (getAcceso().getID() > 0) {
			AccesosGrupoE accGrp = new AccesosGrupoE();
			accGrp.setGrupoID(entity);
			accGrp.setAccesoID(getAcceso());
			if (entity.getAccesosGrupo() == null) {
				entity.setAccesosGrupo(new ArrayList<AccesosGrupoE>());
			}
			entity.getAccesosGrupo().add(accGrp);
		}
	}

	private AccesosGrupoE accGrp;
	private List<AccesosGrupoE> listTemp;

	public AccesosGrupoE getAccGrp() {
		return accGrp;
	}

	public void setAccGrp(AccesosGrupoE accGrp) {
		this.accGrp = accGrp;
	}

	/**
	 * Remueve del listado de la entidad el Acceso Grupo cargado en memoria.
	 */
	public void removerAcceso() {
		if (entity.getAccesosGrupo() != null && getAccGrp() != null) {
			if (entity.getAccesosGrupo().size() > 0) {
				entity.getAccesosGrupo().remove(getAccGrp());
				if (Utils.isEmptyList(listTemp)) {
					listTemp = new ArrayList<AccesosGrupoE>();
				}
				listTemp.add(accGrp);
				RequestContext.getCurrentInstance().update("f2:pg2");
			}
		}
	}

	@Override
	protected void afterSave(EntityManager em) {
		// TODO Auto-generated method stub
		setAccGrp(null);
		listTemp = null;
		super.afterSave(em);
	}

	public void cancelar() {
		setAccGrp(null);
		listTemp = null;
	}
}
