package com.app.jsf;

import java.security.NoSuchAlgorithmException;
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

import com.app.entity.enums.EstadosUsuario;
import com.app.entity.security.GrupoE;
import com.app.entity.security.PerfilE;
import com.app.entity.security.UsuarioE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.EncryptionType;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name="usuarioPB")
public class UsuariosPageBean extends JPAEntityBean<UsuarioE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuariosPageBean() {
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
		setIdGrupo(0);
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
	
	public List<SelectItem> getEstados(){
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (EstadosUsuario e : EstadosUsuario.values()) {
			items.add(new SelectItem(e.toString()));		
		}
		return items;
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
	
	private long idGrupo;

	public long getIdGrupo() {
		return (getEntity()!=null && getEntity().getPerfilID()!=null)? getEntity().getPerfilID().getGrupoID().getID():idGrupo;
	}

	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	/**
	 * <p>
	 * Valida si se esta creando una nueva entidad para setear el id del primer
	 * Grupo que se carga en memoria
	 * </p>
	 * 
	 * @param grupo
	 */
	private void validateEntityGroup(GrupoE grupo) {
		if (isNew() && getEntity() != null && idGrupo == 0) {
			setIdGrupo(grupo.getID());
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
		if(getEntity()!=null && getEntity().getPerfilID()!=null){
			getEntity().setPerfilID(null);
		}
		RequestContext.getCurrentInstance().update("f2:som3");
	}
	
	
	private JPABase<PerfilE> optPrf = new JPABase<PerfilE>();
	private static final String QL_PERFILES = "SELECT  e from PerfilE e where e.grupoID.ID = :ID";
	
	
	private List<PerfilE> getPerfiles() {
		 List<PerfilE> perfiles =new ArrayList<PerfilE>();
		 if(getIdGrupo()>0){
			 EntityManager em = getEM();
			 try{
				 Map<String, Object> parameters = new HashMap<String, Object>();
				 parameters.put("ID", getIdGrupo());
				 perfiles = optPrf.findListE(QL_PERFILES, parameters, em);
			 }catch(Exception e){
				 perfiles = null;
			 }
			 finally{
				 em.close();
			 }
		 }
		return perfiles;
	}


	public List<SelectItem> getPerfilesUI() throws Exception {
		List<SelectItem> listUI = new ArrayList<SelectItem>();
		List<PerfilE> prf = getPerfiles();
		if (!Utils.isEmptyList(prf)) {
			for (PerfilE perfil : prf) {
				listUI.add(new SelectItem(perfil.getID(), perfil.getNombre()));
			}
		}
		return listUI;
	}
	
	/**
	 * 
	 * @return el identificador del perfil asociado al perfil, en caso de no
	 *         tener un perfil asociado devuelve el valor -1
	 */
	public long getIDPerfil(){
		return (getEntity() != null && getEntity().getPerfilID() != null) ? getEntity()
				.getPerfilID().getID() : -1;
	}
	
	public void setIDPerfil(long idPerfil){	
		List<PerfilE> perfiles = getPerfiles();
		for(PerfilE perfil: perfiles){
			if(perfil.getID()== idPerfil){
				getEntity().setPerfilID(perfil);
				break;
			}
		}
	}
	
	
	private static final String QL_PASSWORD="Select count(e) from UsuarioE e where e.ID = :ID and e.contrasena = :contrasena";
	
	@Override
	protected boolean beforeSave(EntityManager em) {
		// TODO Auto-generated method stub
		int count = 0;
		if (!isNew()) {
			
			try {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("ID", getEntity().getID());
				parameters.put("contrasena", getEntity().getContrasena());
				count = countE(QL_PASSWORD, parameters, em);
			} catch (Exception e) {
				count = -1;
				e.printStackTrace();
			}
		}
		
		// Verifica si es necesario encriptar la contrasena tanto como si fuera
		// un nuevo usuario o si se cambio la contrasena actual cuando se edita
		if(isNew() || count<=0){
			try {
				String encryption = Utils.getEncriptyonMessage(getEntity().getContrasena(), EncryptionType.MD5);
				getEntity().setContrasena(encryption);
			} catch (NoSuchAlgorithmException e) {
			
			}
		}
		
		return super.beforeSave(em);
	}
	
	
}
