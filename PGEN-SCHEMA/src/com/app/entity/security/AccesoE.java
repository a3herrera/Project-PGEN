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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.app.entity.embedded.registroEMB;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: AccesoE
 *
 *
 * <p>En esta clase se van a estar manejando los diferentes accesos que va a tener el sistema</p>
 * <p>En esta clase se van a estar manejando los diferentes accesos que va a tener el sistema</p>
 * <p>En esta clase se van a estar manejando los diferentes accesos que va a tener el sistema</p>
 * <p>En esta clase se van a estar manejando los diferentes accesos que va a tener el sistema</p>
 * @author Raul Monzon
 */
@Entity
@Table(name = "ACCESOS")
@SequenceGenerator(name = "SEQ_ACCESO_ID", allocationSize = ConstantsEntity.perfilSecuenciaAllocation, initialValue = ConstantsEntity.perfilSecuenciaInit)
public class AccesoE implements Serializable {
	private static final long serialVersionUID = 1L;

	private long ID;
	private String url;
	private boolean redireccionamiento;
	private String titulo;
	private boolean estado;
	private long version;

	private AccesoE padreID;
	private boolean esPadre;

	private List<AccesoE> accesoE;

	private registroEMB registro;

	public AccesoE() {
		super();
	}

	@Id
	@Column(name = "ID_ACCESO", updatable = false)
	@GeneratedValue(generator = "SEQ_ACCESO_ID", strategy = GenerationType.SEQUENCE)
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	@Column(name = "URL", nullable = true)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "REDIRECCIONAMIENTO", nullable = false)
	public boolean getRedireccionamiento() {
		return this.redireccionamiento;
	}

	public void setRedireccionamiento(boolean redireccionamiento) {
		this.redireccionamiento = redireccionamiento;
	}

	@Column(name = "TITULO", nullable = false, length = ConstantsEntity.accesoTituloSize)
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "ESTADO", nullable = false)
	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Version
	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Embedded
	public registroEMB getRegistro() {
		return registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

	@ManyToOne
	@JoinColumn(name = "ID_ACCESO_PADRE", updatable = false, insertable = true)
	public AccesoE getPadreID() {
		return padreID;
	}

	public void setPadreID(AccesoE padreID) {
		this.padreID = padreID;
	}

	@Column(name = "ES_PADRE", nullable = false)
	public boolean isEsPadre() {
		return esPadre;
	}

	public void setEsPadre(boolean esPadre) {
		this.esPadre = esPadre;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "padreID", orphanRemoval = true)
	public List<AccesoE> getAccesoE() {
		return accesoE;
	}

	public void setAccesoE(List<AccesoE> accesoE) {
		this.accesoE = accesoE;
	}

}
