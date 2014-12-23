package com.app.entity.transactions;

import java.util.Date;
import java.util.List;

import com.app.entity.banks.BancoE;
import com.app.entity.history.AuditedEntity;

public class EjecutoresE extends AuditedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long ID;

	private Date executeDate;

	private boolean state;

	private boolean executedByTime;
	private boolean executeHourPeerDay;
	private boolean executeSpecifiedDays;
	private boolean executeSpecifiedDate;

	private List<BancoE> banks;
	private List<String> executeDays;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isExecutedByTime() {
		return executedByTime;
	}

	public void setExecutedByTime(boolean executedByTime) {
		this.executedByTime = executedByTime;
	}

	public boolean isExecuteHourPeerDay() {
		return executeHourPeerDay;
	}

	public void setExecuteHourPeerDay(boolean executeHourPeerDay) {
		this.executeHourPeerDay = executeHourPeerDay;
	}

	public boolean isExecuteSpecifiedDays() {
		return executeSpecifiedDays;
	}

	public void setExecuteSpecifiedDays(boolean executeSpecifiedDays) {
		this.executeSpecifiedDays = executeSpecifiedDays;
	}

	public boolean isExecuteSpecifiedDate() {
		return executeSpecifiedDate;
	}

	public void setExecuteSpecifiedDate(boolean executeSpecifiedDate) {
		this.executeSpecifiedDate = executeSpecifiedDate;
	}

	public List<BancoE> getBanks() {
		return banks;
	}

	public void setBanks(List<BancoE> banks) {
		this.banks = banks;
	}

	public List<String> getExecuteDays() {
		return executeDays;
	}

	public void setExecuteDays(List<String> executeDays) {
		this.executeDays = executeDays;
	}

}
