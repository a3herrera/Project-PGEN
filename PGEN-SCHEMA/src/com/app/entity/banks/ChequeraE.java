package com.app.entity.banks;

import com.app.entity.banks.CuentaE;
import com.app.entity.embedded.registroEMB;
import com.app.entity.enums.EstadoChequera;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ChequeraE
 *
 */
@Entity
@Table(name = "CHEQUERAS")
@SequenceGenerator(name = "SEQ_CHEQUERA_ID", sequenceName = "SEQ_CHEQUERA_ID")
public class ChequeraE implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CHEQUERA")
	@GeneratedValue(generator = "SEQ_CHEQUERA_ID", strategy = GenerationType.SEQUENCE)
	private long ID;

	@ManyToOne
	@JoinColumn(name = "ID_CUENTA", nullable = false, updatable = false, insertable = true)
	private CuentaE cuentaID;

	@Column(name = "CODIGO", nullable = false, updatable = false, insertable = true,length=50)
	private String codigo;

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false, length = 20)
	private EstadoChequera estado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_AUTORIZACION", nullable = false, updatable = false)
	private Date fechaAutorización;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_BAJA", nullable = true)
	private Date fechaBaja;

	@Column(name = "TOTAL_CHEQUES", nullable = false)
	private int totalCheques;

	@Column(name = "NUMERACION_INICIAL", nullable = false, updatable = false, insertable = true)
	private long numeroInicial;

	@Column(name = "NUMERACION_FINAL", nullable = false, updatable = false, insertable = true)
	private long numeroFinal;

	@Embedded
	private registroEMB registro;

	@Version
	private long version;

	public ChequeraE() {
		super();
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public CuentaE getCuentaID() {
		return cuentaID;
	}

	public void setCuentaID(CuentaE cuentaID) {
		this.cuentaID = cuentaID;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EstadoChequera getEstado() {
		return estado;
	}

	public void setEstado(EstadoChequera estado) {
		this.estado = estado;
	}

	public Date getFechaAutorización() {
		return fechaAutorización;
	}

	public void setFechaAutorización(Date fechaAutorización) {
		this.fechaAutorización = fechaAutorización;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public int getTotalCheques() {
		return totalCheques;
	}

	public void setTotalCheques(int totalCheques) {
		this.totalCheques = totalCheques;
	}

	public long getNumeroInicial() {
		return numeroInicial;
	}

	public void setNumeroInicial(long numeroInicial) {
		this.numeroInicial = numeroInicial;
	}

	public long getNumeroFinal() {
		return numeroFinal;
	}

	public void setNumeroFinal(long numeroFinal) {
		this.numeroFinal = numeroFinal;
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
