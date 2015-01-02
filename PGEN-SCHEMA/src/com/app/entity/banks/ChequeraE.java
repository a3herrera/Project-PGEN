package com.app.entity.banks;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.Customizer;

import com.app.entity.enums.EstadoChequera;
import com.app.entity.history.AuditedEntity;
import com.app.entity.history.ChequeraHistory;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: ChequeraE
 *
 */
@Entity
@Table(name = "CHEQUERAS")
@Customizer(ChequeraHistory.class)
@SequenceGenerator(name = "SEQ_CHEQUERA_ID", sequenceName = "SEQ_CHEQUERA_ID", allocationSize = ConstantsEntity.chequeraSecuenciaAllocation, initialValue = ConstantsEntity.chequeraSecuenciaInit)
public class ChequeraE extends AuditedEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CHEQUERA")
	@GeneratedValue(generator = "SEQ_CHEQUERA_ID", strategy = GenerationType.SEQUENCE)
	private long ID;

	@ManyToOne
	@JoinColumn(name = "ID_CUENTA", nullable = false, insertable = true)
	private CuentaE cuentaID;

	@NotNull(message = "{campo.not.null}")
	@Column(name = "NUMERO", nullable = false, length = 20)
	private String numero;

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false, length = 20)
	private EstadoChequera estado = EstadoChequera.NUEVA;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "{campo.not.null}")
	@Column(name = "FECHA_AUTORIZACION", nullable = false)
	private Date fechaAutorización;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_BAJA", nullable = true)
	private Date fechaBaja;

	@Column(name = "TOTAL_CHEQUES", nullable = false)
	private long totalCheques;

	@NotNull(message = "{campo.not.null}")
	@Column(name = "NUMERACION_INICIAL", nullable = false, insertable = true)
	private long numeroInicial;

	@NotNull(message = "{campo.not.null}")
	@Column(name = "NUMERACION_FINAL", nullable = false, insertable = true)
	private long numeroFinal;

	@Column(name = "CHEQUES_EMITIDOS")
	private int chequesEmitidos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "chequeraID", fetch = FetchType.LAZY, orphanRemoval = false)
	private List<ChequeE> cheques;

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

	public long getTotalCheques() {
		return totalCheques;
	}

	public void setTotalCheques(long totalCheques) {
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

	public List<ChequeE> getCheques() {
		return cheques;
	}

	public void setCheques(List<ChequeE> cheques) {
		this.cheques = cheques;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getChequesEmitidos() {
		return chequesEmitidos;
	}

	public void setChequesEmitidos(int chequesEmitidos) {
		this.chequesEmitidos = chequesEmitidos;
	}

}
