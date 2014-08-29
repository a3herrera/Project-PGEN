package com.app.entity.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.Index;

import com.app.entity.embedded.registroEMB;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: Grupos
 *
 */
@Entity
@Index(name = "IDX_GRUPOS_NOMBRE", columnNames = { "NOMBRE" })
@Table(name = "GRUPOS", uniqueConstraints = { @UniqueConstraint(columnNames = "NOMBRE", name = "CNN_UN_GRUPOS_NOMBRE") })
@SequenceGenerator(name = "SEQ_GRUPO_ID", allocationSize = ConstantsEntity.grupoSecuenciaAllocation, initialValue = ConstantsEntity.grupoSecuenciaInit)
public class GrupoE implements Serializable {
	private static final long serialVersionUID = 1L;

	private long ID;

	private String nombre;
	private String descripción;

	private List<PerfilE> perfiles;
	private List<AccesosGrupoE> accesosGrupo;

	private registroEMB registro;

	private long version;

	public GrupoE() {
		super();
	}

	@Id
	@Column(name = "ID_GRUPO")
	@GeneratedValue(generator = "SEQ_GRUPO_ID", strategy = GenerationType.SEQUENCE)
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	@Column(name = "NOMBRE", length = ConstantsEntity.grupoNombreSize, nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Lob
	@Column(name = "DESCRIPCION", nullable = false)
	public String getDescripción() {
		return this.descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH }, orphanRemoval = false, fetch = FetchType.LAZY, mappedBy = "grupoID")
	public List<PerfilE> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<PerfilE> perfiles) {
		this.perfiles = perfiles;
	}

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH }, orphanRemoval = false, fetch = FetchType.LAZY, mappedBy = "grupoID")
	public List<AccesosGrupoE> getAccesosGrupo() {
		return accesosGrupo;
	}

	public void setAccesosGrupo(List<AccesosGrupoE> accesosGrupo) {
		this.accesosGrupo = accesosGrupo;
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

}
