package com.app.entity.embedded;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * La clase <code>RegistroEMB</code> es de tipo embedida en el cual representa
 * la informacion de un registro en base de datos para el control del mismo
 * </p>
 * <p>
 * La informacion que maneja es:
 * </p>
 * <blockquote>
 * <ul>
 * <li>Alta: Fecha en la que el registro fue ingresado a la BD</li>
 * <li>Modificado: Fecha ultima en la que el registro fue modificado</li>
 * <ul>
 * </blockquote>
 * 
 * @author Angel Alfaro
 * */
@Embeddable
@Access(AccessType.PROPERTY)
public class registroEMB {

	private Date regCreacion;
	private Date regModificación;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_CREACION", updatable = false, insertable = true)
	public Date getRegCreacion() {
		return regCreacion;
	}

	public void setRegCreacion(Date regCreacion) {
		this.regCreacion = regCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_ULT_MODIFICACION", updatable = true, insertable = false)
	public Date getRegModificación() {
		return regModificación;
	}

	public void setRegModificación(Date regModificación) {
		this.regModificación = regModificación;
	}

}
