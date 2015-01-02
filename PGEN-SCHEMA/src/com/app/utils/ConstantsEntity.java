package com.app.utils;

public class ConstantsEntity {

	/**
	 * <p>
	 * Nombre de la unidad de Persistencia a utilizar en el proyecto
	 * </p>
	 */
	public static final String persistencesName = "PGEN-SCHEMA";

	// Campos Generales para el Historico
	public static final String StartDateName = "START_DATE";
	public static final String EndDateName = "END_DATE";

	/*
	 * Expresion regulares
	 */

	public static final String erAlfabetico = "";
	public static final String erNumerico = "";
	public static final String erAlfaNumerico = "";

	/*
	 * CONSTANTES GENERALES
	 */

	/**
	 * <p>
	 * Escala para el manejo de efectivo
	 * </p>
	 */
	public static final int escala = 2;

	/**
	 * <p>
	 * Precicios para el manejo de efetivo
	 * </p>
	 */
	public static final int precision = 18;

	/*
	 * Constantes de la Entidad GrupoE
	 */
	public static final int grupoSecuenciaInit = 20;
	public static final int grupoSecuenciaAllocation = 1;

	public static final int grupoNombreSize = 50;

	/*
	 * Constantes de la Entidad PerfilE
	 */
	public static final int perfilSecuenciaInit = 20;
	public static final int perfilSecuenciaAllocation = 1;

	public static final int perfilNombreSize = 50;

	/*
	 * Constantes de la Entidad accesoE
	 */
	public static final int accesoSecuenciaInit = 20;
	public static final int accesoSecuenciaAllocation = 1;

	public static final int accesoTituloSize = 25;

	/*
	 * Constantes de la Entidad accesosGrupoE
	 */
	public static final int accesoGrupoSecuenciaInit = 20;
	public static final int accesoGrupoAllocation = 1;

	/*
	 * Constantes de la Entidad accesosPerfilE
	 */
	public static final int accesoPerfilSecuenciaInit = 20;
	public static final int accesoPerfilAllocation = 1;

	public static final int accesoPerfilAccionesSize = 20;

	/*
	 * Constantes de la Entidad UsuarioE
	 */
	public static final int usuarioSecuenciaInit = 20;
	public static final int usuarioSecuenciaAllocation = 1;

	/*
	 * Constantes de la Entidad catalago documentos
	 */
	public static final int catgDocumentoSecuenciaInit = 20;
	public static final int catgDocumentoSecuenciaAllocation = 1;

	public static final int documentoNombreSize = 25;

	/*
	 * Constantes de la Entidad catalogo monedas
	 */
	public static final int catgMonedaSecuenciaInit = 20;
	public static final int catgMonedaSecuenciaAllocation = 1;

	/*
	 * Constantes de la Entidad persona
	 */
	public static final int personaSecuenciaInit = 20;
	public static final int personaSecuenciaAllocation = 1;

	public static final int personaNombreSize = 25;

	/*
	 * Constantes de la entidad de Bancos
	 */

	public static final int bancoSecuenciaInit = 20;
	public static final int bancoSecuenciaAllocation = 1;

	/*
	 * Constantes de la entidad de cuentas
	 */

	public static final int cuentaSecuenciaInit = 20;
	public static final int cuentaSecuenciaAllocation = 1;

	/*
	 * Constantes de la entidad de CHEQUERAS
	 */

	public static final int chequeraSecuenciaInit = 20;
	public static final int chequeraSecuenciaAllocation = 1;
	public static final String CHEQUERA_HISTORY_NAME= "CHEQUERAS_HISTORY";

	/*
	 * Constantes de la entidad de proveedores
	 */

	public static final int proveedorSecuenciaInit = 20;
	public static final int proveedorSecuenciaAllocation = 1;
	public static final String PROVEEDOR_SEQUENCE_NAME= "SEQ_PROV_ID";
	public static final String PROVEEDOR_HISTORY_NAME = "PROVEEDORES_HISTORY";

	/*
	 * Constantes de la entidad de Compras a un proveedor
	 */

	public static final int compraSecuenciaInit = 20;
	public static final int compraSecuenciaAllocation = 1;
	public static final int comprasEstado = 25;
	public static final String compraHistoryName = "COMPRAS_PROVEEDORES_HISTORY";
	public static final String COMPRA_SEQUENCE_NAME= "SEQ_COMPRA_ID";

	/*
	 * Constantes de la entidad de CHEQUES
	 */

	public static final int chequeSecuenciaInit = 20;
	public static final int chequeSecuenciaAllocation = 1;
	public static final int CHEQUE_NO_SIZE = 20;
	public static final int CHEQUE_STATE_SIZE = 25;
	public static final String CHEQUE_SEQUENCE_NAME = "SEQ_CHEQUE_ID";
	public static final String CHEQUE_HISTORY_NAME = "CHEQUES_HISTORY";

}
