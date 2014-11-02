package com.app.entity.banks;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.app.entity.embedded.registroEMB;

/**
 * Entity implementation class for Entity: Bancos
 *
 */
@Entity
@Table(name = "BANCOS")
@SequenceGenerator(name = "seq_banco_id", sequenceName = "seq_banco_id")
public class BancoE implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_BANCO")
	@GeneratedValue(generator = "seq_banco_id")
	private long ID;

	@Column(name = "NOMBRE", length = 50, nullable = false)
	private String nombre;

	@Column(name = "PAGINA_WEB", length = 50, nullable = true)
	private String paginaWeb;

	@Column(name = "ESTADO")
	private boolean estado;
	
	@Embedded
	private registroEMB registro;

	@OneToMany(mappedBy = "bancoID", fetch = FetchType.LAZY, orphanRemoval = false, cascade = CascadeType.ALL)
	private List<CuentaE> cuentas;
	
	@Version
	private long version;

	public BancoE() {
		super();
	}

	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
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

	@PrePersist
	private void per() {
		setRegistro(new registroEMB());
		getRegistro().setRegCreacion(new Date());
	}

	@PreUpdate
	private void upd() {
		getRegistro().setRegModificación(new Date());
	}

	public List<CuentaE> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<CuentaE> cuentas) {
		this.cuentas = cuentas;
	}

}
