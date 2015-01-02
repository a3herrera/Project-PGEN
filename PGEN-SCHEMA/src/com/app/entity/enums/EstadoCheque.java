package com.app.entity.enums;

public enum EstadoCheque {
	/**
	 * Estado con el cual inicia un cheque al momento de que una chequera cambia
	 * a un nuevo estado distinto a Nuevo, y no haya tenido generado los cheques
	 */
	GENERADO,
	/**
	 * Estado que se utiliza cuando una chequera ya estaba habilitada para su
	 * uso
	 */
	DISPONIBLE,
	/**
	 * Mismo estado perteneciente a una chequera, cuando no se pueden hacer
	 * movimientos con la misma
	 */
	CONGELADO,
	/**
	 * Son aquellos cheques que ya se les ha asignado un monto, y un uso a
	 * realizarse para los mismo
	 */
	EMITIDO,
	/**
	 * Es cuando un cheque ya no se puede modificar, y el monto ha sido acpetado
	 * para su cobro
	 */
	LIBERADO,
	/**
	 * Estado en el cual se necesita una aprobación adicional para la liberación
	 * el cheque
	 */
	RETENIDO,
	/**
	 * Estado cuando un cheque no se puedo realizar el cobro por X o Y motivo
	 */
	RECHAZADO,
	/**
	 * Estado en el que un cheque ya ha sido cobrado exitosamente
	 */
	COBRADO,
	/**
	 * Mismo estado de un chequera cuando ya no se puede hacer uso del mismo
	 */
	CANCELADO
}
