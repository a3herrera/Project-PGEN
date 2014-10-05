package com.app.entity.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.app.entity.embedded.registroEMB;
import com.app.utils.ConstantsEntity;

@Entity
@Table(name = "PERFILES")
@SequenceGenerator(name = "SEQ_PERFIL_ID", allocationSize = ConstantsEntity.perfilSecuenciaAllocation, initialValue = ConstantsEntity.perfilSecuenciaInit)
public class PerfilE implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4589846730694405821L;

	private long ID;
	private String nombre;
	private boolean estado;

	private GrupoE grupoID;

	private List<UsuarioE> usuarios;

	private registroEMB registro;

	private long version;

	@Id
	@Column(name = "ID_PERFIL")
	@GeneratedValue(generator = "SEQ_PERFIL_ID", strategy = GenerationType.SEQUENCE)
	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	@Column(name = "NOMBRE", nullable = false, length = ConstantsEntity.perfilNombreSize)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "ESTADO", nullable = false)
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@ManyToOne
	@JoinColumn(name = "ID_GRUPO", nullable = false, insertable = true, updatable = false)
	public GrupoE getGrupoID() {
		return grupoID;
	}

	public void setGrupoID(GrupoE grupoID) {
		this.grupoID = grupoID;
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

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH }, orphanRemoval = false, fetch = FetchType.LAZY, mappedBy = "perfilID")
	public List<UsuarioE> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioE> usuarios) {
		this.usuarios = usuarios;
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
