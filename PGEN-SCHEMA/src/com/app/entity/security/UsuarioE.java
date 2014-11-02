package com.app.entity.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.app.entity.embedded.registroEMB;
import com.app.entity.employees.EmpleadoE;
import com.app.entity.enums.EstadosUsuario;
import com.app.utils.ConstantsEntity;

@Entity
@Table(name = "USUARIOS", uniqueConstraints = { @UniqueConstraint(columnNames = "USUARIO", name = "CNN_UN_USARIO_NOMBRE") })
@SequenceGenerator(name = "SEQ_USUARIOS_ID", allocationSize = ConstantsEntity.usuarioSecuenciaAllocation, initialValue = ConstantsEntity.usuarioSecuenciaInit)
public class UsuarioE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long ID;

	private String usuario;
	private String contrasena;

	private EstadosUsuario estado;

	private PerfilE perfilID;
	private EmpleadoE empleadoID;

	private registroEMB registro;

	private long version;

	@Id
	@Column(name = "ID_USUARIO")
	@GeneratedValue(generator = "SEQ_USUARIOS_ID", strategy = GenerationType.SEQUENCE)
	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	@Column(name = "USUARIO", nullable = false)
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "CONTRASENA", nullable = false)
	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false)
	public EstadosUsuario getEstado() {
		return estado;
	}

	public void setEstado(EstadosUsuario estado) {
		this.estado = estado;
	}

	@ManyToOne
	@JoinColumn(name = "ID_PERFIL", nullable = false, updatable = true, insertable = true)
	public PerfilE getPerfilID() {
		return perfilID;
	}

	public void setPerfilID(PerfilE perfilID) {
		this.perfilID = perfilID;
	}

	@OneToOne()
	@JoinColumn(name = "ID_PERSONA", nullable = true)
	public EmpleadoE getEmpleadoID() {
		return empleadoID;
	}

	public void setEmpleadoID(EmpleadoE empleadoID) {
		this.empleadoID = empleadoID;
	}

	@Embedded
	public registroEMB getRegistro() {
		return registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

	@Version
	public long getVersion() {
		return version;
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
