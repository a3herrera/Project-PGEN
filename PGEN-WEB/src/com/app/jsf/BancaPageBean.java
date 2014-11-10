package com.app.jsf;

import java.text.MessageFormat;
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

import com.app.Interface.Local.RequestsHandlerLocal;
import com.app.entity.banks.BancoE;
import com.app.entity.banks.CuentaE;
import com.app.jsf.base.JPABase;
import com.app.jsf.base.JPAEntityBean;
import com.app.utils.Utils;

@SessionScoped
@ManagedBean(name = "bancaPB")
public class BancaPageBean extends JPAEntityBean<BancoE> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BancaPageBean() {
		// TODO Auto-generated constructor stub
		setWhereQL(" where e.cuentas is not empty");
	}

	@EJB
	private RequestsHandlerLocal request;

	private long cuentaId;
	private double monto;
	private String documentoNo;
	private BancoE banco;

	public long getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(long cuentaId) {
		System.out.println("Cuenta " + cuentaId);
		this.cuentaId = cuentaId;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		System.out.println("Monto " + monto);
		this.monto = monto;
	}

	public String getDocumentoNo() {
		return documentoNo;
	}

	public void setDocumentoNo(String documentoNo) {
		System.out.println("Documento " + documentoNo);
		this.documentoNo = documentoNo;
	}

	public BancoE getBanco() {
		return banco;
	}

	public void setBanco(BancoE banco) {
		this.banco = banco;
	}

	private JPABase<CuentaE> optCuenta = new JPABase<CuentaE>();
	private static final String QL_CUENTAS = "Select e from CuentaE e where e.bancoID = :banco";
	private Map<String, Object> parameters;

	private List<CuentaE> getCuentas() {
		EntityManager em = getEM();
		List<CuentaE> cuentas = new ArrayList<CuentaE>();
		try {
			parameters = new HashMap<String, Object>();
			parameters.put("banco", getBanco());
			try {
				cuentas = optCuenta.findListE(QL_CUENTAS, parameters, em);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				cuentas = null;
			}
		} finally {
			em.close();
		}
		return cuentas;
	}

	public List<SelectItem> getCuentasUI() {
		List<CuentaE> cuentas = getCuentas();
		List<SelectItem> items = new ArrayList<SelectItem>();
		if (!Utils.isEmptyList(cuentas)) {
			for (CuentaE e : cuentas) {
				items.add(new SelectItem(e.getID(), e.getNumero() + " / "
						+ e.getMoneda().getMoneda()));
			}
		}
		return items;
	}
	
	public void enviarDebito(){
		System.out.println("INGRESANDO A ENVIAR DEBITO");
		/*long idTransaccion = request.debit(get, amount, noDocument)*/
		long idTransaccion = request.debit(getCuentaId(), getMonto(), getDocumentoNo());
		String message = MessageFormat.format(getMsg().getString("message.transaction.debit"), idTransaccion);
		infMsg(message);
		clear();
		RequestContext.getCurrentInstance().update("f1");
		RequestContext.getCurrentInstance().execute("dlg1.hide()");
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		banco = null;
		documentoNo = new String();
		cuentaId = 0;
		monto = 0;
		super.clear();
				
	}
	

}
