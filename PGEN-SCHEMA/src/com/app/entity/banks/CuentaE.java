package com.app.entity.banks;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.app.entity.catalog.CatgTipoMoneda;
import com.app.entity.embedded.registroEMB;
import com.app.entity.enums.TiposCuenta;

/**
 * Entity implementation class for Entity: CuentaE
 *
 */
@Entity
@Table(name = "CUENTAS")
@SequenceGenerator(name = "SEQ_CUENTA_ID", sequenceName = "SEQ_CUENTA_ID")
public class CuentaE implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SEQ_CUENTA_ID", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_CUENTA")
	private long ID;

	@ManyToOne
	@JoinColumn(name = "ID_BANCO", nullable = false, updatable = false, insertable = true)
	private BancoE bancoID;

	@Column(name = "CUENTA_NO")
	private String numero;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO")
	private TiposCuenta tipo;

	@ManyToOne
	@JoinColumn(name = "MONEDA", nullable = false)
	private CatgTipoMoneda moneda;

	@Temporal(TemporalType.TIME)
	@Column(name = "FECHA_APERTURA")
	private Date fechaApertura;

	@Column(name = "SALDO", precision = 18, scale = 2, insertable = false)
	private double saldo;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "cuentaID", cascade = CascadeType.ALL)
	private List<ChequeraE> chequeras;

	@Embedded
	private registroEMB registro;

	@Version
	private long version;

	public CuentaE() {
		super();
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public BancoE getBancoID() {
		return bancoID;
	}

	public void setBancoID(BancoE bancoID) {
		this.bancoID = bancoID;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TiposCuenta getTipo() {
		return tipo;
	}

	public void setTipo(TiposCuenta tipo) {
		this.tipo = tipo;
	}

	public CatgTipoMoneda getMoneda() {
		return moneda;
	}

	public void setMoneda(CatgTipoMoneda moneda) {
		this.moneda = moneda;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public registroEMB getRegistro() {
		return registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
