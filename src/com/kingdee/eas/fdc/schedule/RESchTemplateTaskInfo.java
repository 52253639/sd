package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.util.StringUtils;

public class RESchTemplateTaskInfo extends AbstractRESchTemplateTaskInfo implements Serializable,IRESchTask 
{
	private String msProjectId;
	private String msParentPrjId;
	public RESchTemplateTaskInfo() {
		super();
	}

	protected RESchTemplateTaskInfo(String pkField) {
		super(pkField);
	}

	public void addPredecessor(IRESchTaskPredecessor predecessor) {
		getPredecessors().add((RESchTemplateTaskPredecessorInfo) predecessor);
	}

//	public FullOrgUnitInfo getAdminDept() {
//		return super.getAdminDept();
//	}
//
//	public PersonInfo getAdminPerson() {
//		return super.getAdminPerson();
//	}

	public BigDecimal getDay() {
		return null;
	}

	public IRESchTask getParentTask() {
		return null;
	}

	public Date getPlanEnd() {
		return null;
	}

	public Date getPlanStart() {
		return null;
	}

	public String getSrcID() {
		return null;
	}

	public void setAdminDept(FullOrgUnitInfo dept) {
		super.setAdminDept(dept);
	}

	public void setAdminPerson(PersonInfo person) {
		super.setAdminPerson(person);
	}

	public void setDay(BigDecimal day) {
		
	}

	public void setParentTask(IRESchTask item) {

	}

	public void setPlanEnd(Date end) {

	}

	public void setPlanStart(Date start) {

	}

	public void setSrcID(String srcId) {

	}

	public String getMsProjectId() {
		return this.msProjectId;
	}

	public void setMsProjectId(String msProjectId) {
		this.msProjectId = msProjectId;
	}

	public void setMsParentPrjId(String msParentPrjId) {
		this.msParentPrjId = msParentPrjId;
	}

	public String getMsParentPrjId() {
		return msParentPrjId;
	}

	public RESchTemplateTaskBizTypeCollection getRESchBusinessType() {
		return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection)get("businessType");
	}

	public String getRESchBusinessTypeDesc() {
		return getString("businessTypeDesc");
	}

	public void setRESchBusinessTypeDesc(String item) {
		if(!StringUtils.isEmpty(item) && item.length()>80){
			item = item.substring(0, 80);
		}
		setString("businessTypeDesc", item);
	}

	public Date checkDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCheckDate(Date checkDate) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setDisplayName(String item) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(item) && item.length()>80){
			item = item.substring(0, 80);
		}
		setString("businessTypeDesc", item);
		super.setDisplayName(item);
	}
	
	@Override
	public void setDisplayName(String item, Locale local) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(item) && item.length()>80){
			item = item.substring(0, 80);
		}
		super.setDisplayName(item, local);
	}
}