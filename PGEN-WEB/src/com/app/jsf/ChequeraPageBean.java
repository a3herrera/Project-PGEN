package com.app.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import com.app.bussiness.CheckBookBusinessLogicLocal;
import com.app.entity.banks.ChequeE;
import com.app.entity.banks.ChequeraE;
import com.app.entity.banks.CuentaE;
import com.app.entity.enums.EstadoChequera;
import com.app.entity.security.UsuarioE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Constants;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "chequeraPB")
public class ChequeraPageBean extends JPAEntityBean<ChequeraE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private CheckBookBusinessLogicLocal checkBookLogic;

	@Override
	protected String navigationOption() {
		if (isNew()) {
			infMsg(getMsg().getString("message.chequera.save"));
			RequestContext.getCurrentInstance().update("f1");
			RequestContext.getCurrentInstance().execute("dlg1.hide()");
			return newNR;
		} else {
			infMsg(getMsg().getString("message.chequera.save.edit"));
			RequestContext.getCurrentInstance().update("f1");
			RequestContext.getCurrentInstance().execute("dlg1.hide()");
			return updateNR;
		}
	}

	private Map<String, Object> params1;
	private static final String QL_CHEQUERA = "Select count(e) from ChequeraE e where e.cuentaID = :cuenta and upper(e.numero) = :numero";
	private static final String QL_CHEQUERA_EDIT = "Select count(e) from ChequeraE e where e.cuentaID = :cuenta and upper(e.numero) = :numero and e.ID = :ID";

	@Override
	protected boolean beforeSave(EntityManager em) {
		try {
			params1 = new HashMap<String, Object>();
			params1.put("cuenta", entity.getCuentaID());
			params1.put("numero", entity.getNumero().toUpperCase().trim());
			int num = countE(QL_CHEQUERA, params1, em);
			if (num > 0 && isNew()) {
				warnMsg(getMsg().getObject("message.chequera.exist").toString());
				return false;
			}
			if (!isNew()) {
				params1.put("ID", entity.getID());
				int numEdit = countE(QL_CHEQUERA_EDIT, params1, em);
				if (num > 0 && numEdit == 0) {
					warnMsg(getMsg().getObject("message.chequera.exist")
							.toString());
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// Total Cheques
		if (verifyNumbers() == false) {
			return false;
		}

		if (Utils.isEmptyList(entity.getCheques())) {
			List<ChequeE> cheques = checkBookLogic.createChecksList(entity.getEstado(), entity);
			if (!Utils.isEmptyList(cheques)) {
				entity.setCheques(cheques);
			}
		}

		// Realiza el Cambio de los estados de los cheques
		if (!isNew() && !Utils.isEmptyList(entity.getCheques())) {
			changeStateChecks(entity);
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

	private void changeStateChecks(ChequeraE chequera) {
		if (!Utils.isEmptyList(chequera.getCheques())) {
			for (ChequeE e : chequera.getCheques()) {
				e.setEstado(checkBookLogic.getNewStateCheck(
						chequera.getEstado(), e.getEstado()));
			}
		}

	}

	private boolean verifyNumbers() {
		boolean isOk = true;

		if (entity.getNumeroInicial() <= 0) {
			isOk = false;
			warningMsg("init", getMsg().getObject("message.chequera.number")
					.toString());
		}

		if (entity.getNumeroFinal() <= 0) {
			isOk = false;
			warningMsg("ending", getMsg().getObject("message.chequera.number")
					.toString());
		}

		if (isOk) {
			if (entity.getNumeroInicial() >= entity.getNumeroFinal()) {
				warnMsg(getMsg().getObject("message.chequera.total").toString());
				return false;
			} else {
				entity.setTotalCheques((entity.getNumeroFinal() - entity
						.getNumeroInicial()) + 1);
			}
		}
		return isOk;
	}


	private List<CuentaE> cuentas;
	private String listQLCuentas = "SELECT e from CuentaE e where e.estado = true";
	private JPABase<CuentaE> optCuentas = new JPABase<CuentaE>();

	private List<CuentaE> getCuentas() {
		if (cuentas == null) {
			cuentas = new ArrayList<CuentaE>();
			EntityManager em = getEM();
			try {
				cuentas = optCuentas.findListE(listQLCuentas, em);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				em.close();
			}
		}

		return cuentas;
	}

	public List<SelectItem> getCuentasUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		List<CuentaE> cuentas = getCuentas();
		if (!Utils.isEmptyList(cuentas)) {
			for (CuentaE e : cuentas) {
				String label = "Banco: " + e.getBancoID().getNombre()
						+ " Cuenta No. " + e.getNumero();
				items.add(new SelectItem(e.getID(), label));
			}
		}

		return items;
	}

	public List<SelectItem> getEstadosUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (EstadoChequera e : EstadoChequera.values()) {
			items.add(new SelectItem(e.toString()));
		}

		return items;
	}

	/**
	 * 
	 * @return el identificador del perfil asociado al perfil, en caso de no
	 *         tener un perfil asociado devuelve el valor -1
	 */
	public long getIDCuenta() {
		return (getEntity() != null && getEntity().getCuentaID() != null) ? getEntity()
				.getCuentaID().getID() : -1;
	}

	public void setIDCuenta(long idPerfil) {
		List<CuentaE> cuentas = getCuentas();
		for (CuentaE cuenta : cuentas) {
			if (cuenta.getID() == idPerfil) {
				getEntity().setCuentaID(cuenta);
				break;
			}
		}
	}

	public boolean isNumberReadOnly() {
		if (!isNew()) {
			return getEntity().getEstado() != EstadoChequera.NUEVA;
		}
		return false;
	}

	public boolean showGeneralInfo(EstadoChequera estado) {
		if (estado.equals(EstadoChequera.NUEVA)) {
			return false;
		}
		return true;
	}

	public boolean showChangeState(EstadoChequera estado) {
		if (estado.equals(EstadoChequera.CANCELADA)) {
			return false;
		}
		return true;
	}

	public List<SelectItem> getChangeStateUI() {
		List<SelectItem> items = new ArrayList<SelectItem>();

		if (entity != null && entity.getEstado() != null) {
			List<EstadoChequera> estados = getStates(entity.getEstado());
			for (EstadoChequera e : estados) {
				items.add(new SelectItem(e));
			}
		}
		return items;
	}

	private List<EstadoChequera> getStates(EstadoChequera state) {
		List<EstadoChequera> states = new ArrayList<EstadoChequera>();
		for (EstadoChequera e : EstadoChequera.values()) {
			states.add(e);
		}
		switch (state) {
		case HABILITIDA:
			states.remove(EstadoChequera.HABILITIDA);
			states.remove(EstadoChequera.LIBERADA);
			break;
		case CONGELADA:
			states.remove(EstadoChequera.CONGELADA);
			states.remove(EstadoChequera.LIBERADA);
			break;
		case LIBERADA:
			states.remove(EstadoChequera.LIBERADA);
			break;

		default:
			break;
		}
		states.remove(EstadoChequera.NUEVA);
		states.remove(EstadoChequera.INHABILITADA);
		return states;
	}

	private EstadoChequera changeState;

	public EstadoChequera getChangeState() {
		return changeState;
	}

	public void setChangeState(EstadoChequera changeState) {
		getEntity().setEstado(changeState);
	}

	public void saveChangeState() {
		saveEntity();
		infMsg(getMsg().getString("message.chequera.save.edit"));
		RequestContext.getCurrentInstance().update("f1");
		RequestContext.getCurrentInstance().execute("dlg4.hide()");
	}

}
