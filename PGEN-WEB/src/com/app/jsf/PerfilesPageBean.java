package com.app.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import com.app.entity.security.AccesoE;
import com.app.entity.security.AccesosPerfilE;
import com.app.entity.security.GrupoE;
import com.app.entity.security.PerfilE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "perfilPB")
public class PerfilesPageBean extends JPAEntityBean<PerfilE> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerfilesPageBean() {
		// TODO Auto-generated constructor stub
		super.add = null;
		super.newNR = null;
		super.updateNR = null;
		super.removeNR = null;
	}

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

	private List<GrupoE> grupos;
	private JPABase<GrupoE> optionsGrp = new JPABase<GrupoE>();
	private static final String GRUPOS_QL = "Select e from GrupoE e";

	/**
	 * <p>
	 * Crea un listado de los Grupos que actualmente se encuentran registrados
	 * </p>
	 * 
	 * @return devuelve el listado de los grupos que actualmente existen
	 * @throws Exception
	 */
	private List<GrupoE> getGrupos() throws Exception {
		EntityManager em = getEM();
		try {
			grupos = optionsGrp.findListE(GRUPOS_QL, em);
		} finally {
			em.close();
		}
		return grupos;
	}

	/**
	 * <p>
	 * Arma el listado de tipo <code>SelectItem</code> con la información del
	 * listado de grupos que se encuentran registrados en el sistema
	 * </p>
	 * 
	 * @return listado de item a mostrar en la vista
	 * @throws Exception
	 */
	public List<SelectItem> getGrupoUI() throws Exception {
		List<SelectItem> listUI = new ArrayList<SelectItem>();
		if (getGrupos() != null) {
			boolean check = true;
			for (GrupoE grupo : getGrupos()) {
				if (check) {
					validateEntityGroup(grupo);
					check = false;
				}
				listUI.add(new SelectItem(grupo.getID(), grupo.getNombre()));
			}

		}
		return listUI;
	}

	/**
	 * 
	 * @return el ID del grupo asociado a la entidad, de lo contrario devuelve
	 *         -1
	 */
	public long getGrupoID() {
		return (getEntity() != null && getEntity().getGrupoID() != null) ? getEntity()
				.getGrupoID().getID() : -1;
	}

	/**
	 * <p>
	 * Setea a la entidad el grupo seleccionado
	 * </p>
	 * 
	 * @param idGrupo
	 *            identificador del grupo que se le esta asignando a la entidad
	 * @throws Exception
	 */
	public void setGrupoID(long idGrupo) throws Exception {
		for (GrupoE grupo : getGrupos()) {
			if (grupo.getID() == idGrupo) {
				getEntity().setGrupoID(grupo);
				break;
			}
		}
	}

	/*
	 * CONTROLES DE LOS ACCESOS PADRES A ASIGNAR AL PERFIL
	 */

	
	
	
	private static final String QL_ACC_PADRE = "select e from AccesoE e where "
			+ "e.ID in (Select a.accesoID.ID from AccesosGrupoE a where a.grupoID = :grupoID)  "
			+ "and e.ID not in :accPerfil";
	private static final String QL_ACC_PADRE_NEW = "select e from AccesoE e where "
			+ "e.ID in (Select a.accesoID.ID from AccesosGrupoE a where a.grupoID = :grupoID)";
	private JPABase<AccesoE> optAcc = new JPABase<AccesoE>();
	
	private Map<String, Object> parameters;

	/**
	 * <p>
	 * Valida si se esta creando una nueva entidad para setear el id del primer
	 * Grupo que se carga en memoria
	 * </p>
	 * 
	 * @param grupo
	 */
	private void validateEntityGroup(GrupoE grupo) {
		if (isNew() && getEntity() != null) {
			getEntity().setGrupoID(grupo);
		}
	}

	/**
	 * <p>
	 * Actualmente es el metodo utilizado para que acepte el valueChangeListener
	 * del SelectOneMenu, que contiene los grupos existentes
	 * </p>
	 * 
	 * @param e
	 */
	public void valueGrupoChange(AjaxBehaviorEvent e) {
		if(getEntity()!=null && !Utils.isEmptyList(getEntity().getAccesosPerfil())){
			getEntity().setAccesosPerfil(new ArrayList<AccesosPerfilE>());
		}
	}

	/**
	 * @return Devuelve el listado de accesos principales que se le pueden
	 *         agregar al Perfil, dependiendo del grupo al que se le quiera
	 *         asignar.
	 */
	public List<AccesoE> getAccesosPadre() {
		List<AccesoE> accPadres = new ArrayList<AccesoE>();
		EntityManager em = getEM();
		try {
			if (getEntity() != null && getEntity().getGrupoID() != null) {
				parameters = new HashMap<String, Object>();
				parameters.put("grupoID", getEntity().getGrupoID());
				if (Utils.isEmptyList(getEntity().getAccesosPerfil())) {
					accPadres = optAcc.findListE(QL_ACC_PADRE_NEW, parameters,
							em);
				} else {
					parameters.put("accPerfil", getAccesPerfil(getEntity()));
					accPadres = optAcc.findListE(QL_ACC_PADRE, parameters, em);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return accPadres;
	}

	/**
	 * <p>
	 * Crea un listado con los identificadores de los accesos que se han
	 * asignado al perfil
	 * </p>
	 * 
	 * @param perfil
	 * @return los arreglo de tipo long con los identicadores de los accesos que
	 *         ya se le han asignado al perfil
	 */
	private ArrayList<Long> getAccesPerfil(PerfilE perfil) {
		ArrayList<Long> idsPerfil = new ArrayList<Long>();
		if (!Utils.isEmptyList(perfil.getAccesosPerfil())) {
			List<AccesosPerfilE> accGrp = perfil.getAccesosPerfil();
			for (AccesosPerfilE acc : accGrp) {
				idsPerfil.add(acc.getAcceso().getID());
			}
		}
		return idsPerfil;
	}

	private AccesoE acceso;
	
	public AccesoE getAcceso() {
		return acceso;
	}

	public void setAcceso(AccesoE acceso) {
		this.acceso = acceso;
	}

	
	/**
	 * <p>
	 * Agrega el acceso al listado del perfil
	 * </p>
	 */
	public void addAcceso(){
		if(getAcceso()!=null && getAcceso().getID()>0){
			AccesosPerfilE accPrf = new AccesosPerfilE();
			accPrf.setPerfilID(getEntity());
			accPrf.setAcceso(getAcceso());
			if(Utils.isEmptyList(getEntity().getAccesosPerfil())){
				getEntity().setAccesosPerfil(new ArrayList<AccesosPerfilE>());
			}
			getEntity().getAccesosPerfil().add(accPrf);
		}
		setAcceso(null);
	}
	
	public List<AccesoE> getAccesosPadreEntity(){
		List<AccesoE> acc = new ArrayList<AccesoE>();
		if(getEntity()!=null && !Utils.isEmptyList(getEntity().getAccesosPerfil())){
			for(AccesosPerfilE accPrf : getEntity().getAccesosPerfil()){
				if(accPrf.getAcceso().isEsPadre()){
					acc.add(accPrf.getAcceso());
				}
			}
		}
		return acc;
	}
	
	
	
	
	/*
	 * 	MANEJO DE LOS ACCESOS LIGADOS A UN ACCESO PADRE
	 */
	
	private ArrayList<Long> accesosAsignados(PerfilE perfil, long idPadre){
		ArrayList<Long> accAsignados = new ArrayList<Long>();
		if(!Utils.isEmptyList(perfil.getAccesosPerfil())){
			List<AccesosPerfilE> accGrp = perfil.getAccesosPerfil();
			for(AccesosPerfilE acc : accGrp){
				if(acc.getAcceso().getPadreID() != null && acc.getAcceso().getPadreID().getID() == idPadre){
					accAsignados.add(acc.getAcceso().getID());
				}
			}
		}
		return accAsignados;
	}
	
	
	private static final String QL_ACCESOS_HIJOS_NOT_IN = "Select e  from  AccesoE e where e.padreID.ID = :idPadre";
	
	public List<AccesoE> getAccesosHijos() throws Exception {
		List<AccesoE> accHijos = new ArrayList<AccesoE>();
		if (getIdAccesoPadre() > 0) {
			EntityManager em = getEM();
			try {
				parameters = new HashMap<String, Object>();
				parameters.put("idPadre", getIdAccesoPadre());
				String query = QL_ACCESOS_HIJOS_NOT_IN;
				ArrayList<Long> ids = accesosAsignados(getEntity(), getIdAccesoPadre());
				if(ids.size()>0){
					query+= " and e.ID NOT IN :asignados";
					parameters.put("asignados", ids);
				}
				accHijos = optAcc.findListE(query, parameters, em);
			} finally {
				em.close();
			}
		}
		return accHijos;
	}
	
	public List<AccesoE> getAccesosAsignados(){
		List<AccesoE> accesos = new ArrayList<AccesoE>();
		if(getEntity()!=null && !Utils.isEmptyList(getEntity().getAccesosPerfil())){
			List<AccesosPerfilE> accGrp = getEntity().getAccesosPerfil();
			for(AccesosPerfilE acc : accGrp){
				if(acc.getAcceso().getPadreID() != null && acc.getAcceso().getPadreID().getID() == getIdAccesoPadre()){
					accesos.add(acc.getAcceso());
				}
			}
		}
		return accesos;
	}
	
	private AccesoE accesoHijo;
	
	public AccesoE getAccesoHijo() {
		return accesoHijo;
	}
	
	public void setAccesoHijo(AccesoE accesoHijo) {
		this.accesoHijo = accesoHijo;
	}
	
	public void AsignarAcceso(){
		if(getAccesoHijo()!=null && getAccesoHijo().getID()>0){
			AccesosPerfilE accPr = new AccesosPerfilE();
			accPr.setPerfilID(getEntity());
			accPr.setAcceso(getAccesoHijo());
			if (Utils.isEmptyList(getEntity().getAccesosPerfil())) {
				getEntity().setAccesosPerfil(new ArrayList<AccesosPerfilE>());
			}
			getEntity().getAccesosPerfil().add(accPr);
		}
		setAccesoHijo(null);
	
	}
	
	public void DesasignarAcceso(){
		if(getAccesoHijo()!=null && getAccesoHijo().getID()>0){
			AccesosPerfilE accPrf = null;
			if(getEntity()!=null && !Utils.isEmptyList(getEntity().getAccesosPerfil())){
				List<AccesosPerfilE> accGrp = getEntity().getAccesosPerfil();
				for(AccesosPerfilE acc : accGrp){
					if(acc.getAcceso().getID()== getAccesoHijo().getID()){
						accPrf = acc;
						break;
					}
				}
			}
			if (accPrf instanceof AccesosPerfilE && accPrf!=null) {
				getEntity().getAccesosPerfil().remove(accPrf);
			}
		}
		setAccesoHijo(null);
	}
	 
	
	private long idAccesoPadre;

	public long getIdAccesoPadre() {
		return idAccesoPadre;
	}

	public void setIdAccesoPadre(long idAccesoPadre) {
		this.idAccesoPadre = idAccesoPadre;
	}
	

}
