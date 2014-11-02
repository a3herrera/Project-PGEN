package com.app.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import com.app.entity.enums.EstadosUsuario;
import com.app.entity.security.AccesoE;
import com.app.entity.security.AccesosPerfilE;
import com.app.entity.security.UsuarioE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.LoginBeanBase;
import com.app.utils.EncryptionType;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "SessionPB")
public class LoginPageBean extends LoginBeanBase<UsuarioE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginPageBean() {
		// TODO Auto-generated constructor stub
		entity = new UsuarioE();

	}

	@Override
	protected void beforeFind() {
		// TODO Auto-generated method stub
		setWhereQL(" where upper(e.usuario) = :usuario");
		setParam("usuario", entity.getUsuario().toUpperCase());
	}

	@Override
	protected boolean validations() throws Exception {
		// TODO Auto-generated method stub
		if (resultEntity != null) {
			if (resultEntity.getEstado() != EstadosUsuario.Activo) {
				warnMsg(getMsg().getString("message.login.error"));
				return false;
			}
			if (!resultEntity.getContrasena().equals(
					Utils.getEncriptyonMessage(entity.getContrasena(),
							EncryptionType.MD5))) {
				warnMsg(getMsg().getString("message.login.error"));
				return false;
			}

			if (resultEntity.getPerfilID() == null
					|| !resultEntity.getPerfilID().isEstado()) {
				errorMsg(getMsg().getString("message.login.profile.error"));
				return false;
			}
		} else {
			warnMsg(getMsg().getString("message.login.user.error"));
			return false;
		}
		return super.validations();
	}

	private long idPadre;
	private long idHijo;

	public long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(long idPadre) {
		this.idPadre = idPadre; 
		accesosHijos= null;
	}
	
	

	public long getIdHijo() {
		return idHijo;
	}

	public void setIdHijo(long idHijo) {
		this.idHijo = idHijo;
	}

	private List<AccesoE> accesosPadres;
	private List<AccesoE> accesosHijos;

	private Map<String, Object> parameters;
	private JPABase<AccesoE> optAcc = new JPABase<AccesoE>();
	private static final String QL_ACC_PADRES = "Select e from AccesoE e where e.esPadre = true and e.ID in :ids";
	private static final String QL_ACC_HIJOS = "Select e from AccesoE e where e.esPadre = false and e.ID in :ids and e.padreID.ID = :id";
	
	private List<Long> getIdListEntity() {
		List<AccesosPerfilE> accPerfil = resultEntity.getPerfilID()
				.getAccesosPerfil();
		List<Long> ids = new ArrayList<Long>();
		for (AccesosPerfilE e : accPerfil) {
			ids.add(e.getAcceso().getID());
		}

		return ids;
	}



	public List<AccesoE> getAccesosPadre() {
		if (accesosPadres == null && resultEntity != null) {
			if (!Utils.isEmptyList(resultEntity.getPerfilID()
					.getAccesosPerfil())) {
				EntityManager em = getEM();
				try {
					parameters = new HashMap<String, Object>();
					parameters.put("ids", getIdListEntity());
					accesosPadres = optAcc.findListE(QL_ACC_PADRES, parameters,
							em);
				} catch (Exception e) {
					e.printStackTrace();
					accesosPadres = null;
				} finally {
					em.close();
				}
				if (!Utils.isEmptyList(accesosPadres)) {
					for (AccesoE e : accesosPadres) {
						if (!e.getEstado()) {
							accesosPadres.remove(e);
						}
					}
				}
			}

		}
		return accesosPadres;
	}
	
	public List<AccesoE> getAccesosHijos() {
		if (getIdPadre() > 0) {
			if (Utils.isEmptyList(accesosHijos)) {
				EntityManager em = getEM();
				try {
					parameters = new HashMap<String, Object>();
					parameters.put("ids", getIdListEntity());
					parameters.put("id", getIdPadre());
					accesosHijos = optAcc.findListE(QL_ACC_HIJOS, parameters,
							em);
				} catch (Exception e) {
					e.printStackTrace();
					accesosHijos = null;
				} finally {
					em.close();
				}
				if (!Utils.isEmptyList(accesosHijos)) {
					for (AccesoE e : accesosHijos) {
						if (!e.getEstado()) {
							accesosHijos.remove(e);
						}
					}
				}
			}
		}
		return accesosHijos;
	}
	
	
	public List<AccesoE> getPadres() {
		return createAccesos();
	}

	private List<AccesoE> createAccesos() {

		AccesoE admin = new AccesoE();
		admin.setID(1);
		admin.setTitulo("Seguridad");
		admin.setUrl("/seguridad/admin.xhtml?faces-redirect=true");

		AccesoE ctg = new AccesoE();
		ctg.setID(2);
		ctg.setTitulo("Catalogos");
		ctg.setUrl("/catalogos/catalogos.xhtml?faces-redirect=true");

		List<AccesoE> urls = new ArrayList<AccesoE>();
		urls.add(admin);
		urls.add(ctg);
		return urls;
	}

	public List<AccesoE> getHijos() {
		List<AccesoE> urls = new ArrayList<AccesoE>();

		if (getIdPadre() == 1) {
			AccesoE grupo = new AccesoE();
			grupo.setTitulo("Grupos");
			grupo.setUrl("/seguridad/grupos.xhtml?faces-redirect=false");

			AccesoE accesos = new AccesoE();
			accesos.setTitulo("Accesos");
			accesos.setUrl("/seguridad/accesos.xhtml?faces-redirect=false");

			AccesoE perfil = new AccesoE();
			perfil.setTitulo("Perfiles");
			perfil.setUrl("/seguridad/perfiles.xhtml?faces-redirect=false");

			AccesoE usuario = new AccesoE();
			usuario.setTitulo("Usuarios");
			usuario.setUrl("/seguridad/usuarios.xhtml?faces-redirect=false");

			urls.add(accesos);
			urls.add(grupo);
			urls.add(perfil);
			urls.add(usuario);
		}

		if (getIdPadre() == 2) {

		}

		return urls;
	}

	private String getAction() {
		String action = null;
		if (!Utils.isEmptyList(accesosPadres)) {
			for (AccesoE acc : accesosPadres) {
				if (acc.getID() == getIdPadre()) {
					action = acc.getUrl();
					if (!action.startsWith("/")) {
						action = "/" + action;
					}
					if (!action.endsWith(".view")) {
						action += ".view";
					}
					if (acc.getRedireccionamiento()) {
						action += "?faces-redirect=true";
					} else {
						action += "?faces-redirect=false";
					}
				}
			}
		}
		return action;
	}
	
	private String getActionHijo(){
		String action = null;
		if (!Utils.isEmptyList(accesosHijos)) {
			for (AccesoE acc : accesosHijos) {
				if (acc.getID() == getIdHijo()) {
					action = acc.getUrl();
					if (!action.startsWith("/")) {
						action = "/" + action;
					}
					if (!action.endsWith(".view")) {
						action += ".view";
					}
					if (acc.getRedireccionamiento()) {
						action += "?faces-redirect=false";
					} else {
						action += "?faces-redirect=false";
					}
				}
			}
		}
		return action;
	}

	public String navegacion() {
		return getAction();
	}
	
	public String navegacionHijos(){
		return getActionHijo();
	}
	public String navegacionDashboard(){
		idPadre=0;
		idHijo=0;
		accesosHijos = null;
		return "main";
	}
	@Override
	public String logOut() {
		// TODO Auto-generated method stub
		return super.logOut();
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		accesosHijos= null;
		accesosPadres = null;
		super.clear();
	}
}
