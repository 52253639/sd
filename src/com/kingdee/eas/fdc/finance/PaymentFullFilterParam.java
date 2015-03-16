package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import java.util.Date;

import com.kingdee.util.DateTimeUtils;

public class PaymentFullFilterParam implements Serializable {

	private String[] compayIds;

	private String[] projectIds;

	private String[] contractIds;

	private String payeeId;

	private Date dateFrom;

	private Date dateTo;

	private int payState;


	public String[] getCompayIds() {
		if(compayIds==null)
			return new String[0];
		else
			return (String[])compayIds.clone();
	}

	public void setCompayIds(String[] compayIds) {
		if(compayIds==null)
			this.compayIds = new String[0];
		else
			this.compayIds = (String[])compayIds.clone();
	}

	public int getPayState() {
		return payState;
	}

	public void setPayState(int contractState) {
		this.payState = contractState;
	}


	public Date getDateFrom() {
		return DateTimeUtils.truncateDate(dateFrom);
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return DateTimeUtils.truncateDate(dateTo);
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String[] getProjectIds() {
		if(projectIds==null)
			return new String[0];
		else
			return (String[])projectIds.clone();
	}

	public void setProjectIds(String[] projectIds) {
		if(projectIds==null)
			this.projectIds = new String[0];
		else
			this.projectIds = (String[])projectIds.clone();
	}

	public String[] getContractIds() {
		if(contractIds==null)
			return new String[0];
		else
			return (String[])contractIds.clone();
	}

	public void setContractIds(String[] changeTypeIds) {
		if(changeTypeIds==null)
			this.contractIds = new String[0];
		else
			this.contractIds = (String[])changeTypeIds.clone();
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String partBId) {
		this.payeeId = partBId;
	}

}
