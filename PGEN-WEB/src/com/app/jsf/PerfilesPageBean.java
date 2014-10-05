package com.app.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import com.app.entity.security.GrupoE;
import com.app.entity.security.PerfilE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;

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
		if (grupos == null) {
			EntityManager em = getEM();
			try {
				grupos = optionsGrp.findListE(GRUPOS_QL, em);
			} finally {
				em.close();
			}
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
			for (GrupoE grupo : getGrupos()) {
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

}
