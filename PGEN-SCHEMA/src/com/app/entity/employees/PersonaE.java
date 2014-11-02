package com.app.entity.employees;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.app.entity.embedded.registroEMB;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: PersonaE
 *
 */
@Entity
@Inheritance
@Table(name = "INFO_PERSONAS")
@DiscriminatorValue("INDIVIDUAL")
@DiscriminatorColumn(name = "TYPE_")
@SequenceGenerator(name = "SEQ_PERSONA_ID", sequenceName = "SEQ_PERSONA_ID", allocationSize = ConstantsEntity.personaSecuenciaAllocation, initialValue = ConstantsEntity.personaSecuenciaInit)
public class PersonaE implements Serializable {

	private long ID;
	private String primerNombre;
	private String segundoNombre;
	private String tercerNombre;
	private String primerApellido;
	private String segundoApellido;
	private Date fechaNacimiento;
	private registroEMB registro;
	private long version;
	private static final long serialVersionUID = 1L;

	public PersonaE() {
		super();
	}

	@Id
	@Column(name = "ID_PERSONA")
	@GeneratedValue(generator = "SEQ_PERSONA_ID", strategy = GenerationType.SEQUENCE)
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	@Column(name = "PRIMER_NOMBRE", nullable = false, length = ConstantsEntity.personaNombreSize)
	public String getPrimerNombre() {
		return this.primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	@Column(name = "SEGUNDO_NOMBRE", nullable = true, length = ConstantsEntity.personaNombreSize)
	public String getSegundoNombre() {
		return this.segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	@Column(name = "TERCER_NOMBRE", nullable = true, length = ConstantsEntity.personaNombreSize)
	public String getTercerNombre() {
		return this.tercerNombre;
	}

	public void setTercerNombre(String tercerNombre) {
		this.tercerNombre = tercerNombre;
	}

	@Column(name = "PRIMER_APELLIDO", nullable = true, length = ConstantsEntity.personaNombreSize)
	public String getPrimerApellido() {
		return this.primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	@Column(name = "SEGUNDO_APELLIDO", nullable = true, length = ConstantsEntity.personaNombreSize)
	public String getSegundoApellido() {
		return this.segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_NACIMIENTO")
	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Embedded
	public registroEMB getRegistro() {
		return this.registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

	@Version
	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@PrePersist
	private void per() {
		setRegistro(new registroEMB());
		getRegistro().setRegCreacion(new Date());
	}

	@PreUpdate
	private void upd() {
		getRegistro().setRegModificación(new Date());
	}
}
