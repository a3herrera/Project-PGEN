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

import com.app.entity.banks.ChequeE;
import com.app.entity.banks.ChequeraE;
import com.app.entity.banks.CuentaE;
import com.app.entity.enums.EstadoCheque;
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

		if (isNew()) {
			List<ChequeE> cheques = obtenerCheques(entity.getEstado());
			if (!Utils.isEmptyList(cheques)) {
				entity.setCheques(cheques);
			}

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

	private boolean verifyNumbers() {
		boolean isOk = true;

		if (entity.getNumeroInicial() <= 0) {
			isOk = false;
			warningMsg("init",getMsg().getObject("message.chequera.number").toString());
		}

		if (entity.getNumeroFinal() <= 0) {
			isOk = false;
			warningMsg("ending",getMsg().getObject("message.chequera.number").toString());
		}

		if (isOk) {
			if (entity.getNumeroInicial() >= entity.getNumeroFinal()) {
				warnMsg(getMsg().getObject("message.chequera.total").toString());
				return false;
			} else {
				entity.setTotalCheques((entity.getNumeroFinal() - entity
						.getNumeroInicial()));
			}
		}
		return isOk;
	}

	private List<ChequeE> obtenerCheques(EstadoChequera estado) {

		EstadoCheque state = EstadoCheque.GENERADO;
		switch (estado) {
		case HABILITIDA:
			state = EstadoCheque.DISPONIBLE;
			break;
		case LIBERADA:
			state = EstadoCheque.GENERADO;
			break;
		case NUEVA:
			state = null;
			break;
		case CANCELADA:
			state = EstadoCheque.CANCELADO;
			break;
		case CONGELADA:
			state = EstadoCheque.RETENIDO;
			break;
		case INHABILITADA:
			state = EstadoCheque.CANCELADO;
			break;

		}
		List<ChequeE> cheques = new ArrayList<ChequeE>();
		long initValue = entity.getNumeroInicial();
		long number = entity.getTotalCheques();
		if (number > 0 && state != null) {
			for (long x = 1; x <= number; x++) {
				cheques.add(new ChequeE(initValue, getEntity(), state));
				initValue++;
			}
		}
		return cheques;
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
				System.out.println("id cuenta seteada");
				break;
			}
		}
	}

	@Override
	public String saveEntity() {
		// TODO Auto-generated method stub
		System.out.println("EJECUTANDO LA ACCION ");
		return super.saveEntity();
	}

	public void test() {
		System.out.println("EJECUTANDO LA ACCION ");
	}
	
	public boolean isNumberReadOnly() {
		if (!isNew()) {
			return getEntity().getEstado() != EstadoChequera.NUEVA;
		}
		return false;
	}

}
