package com.kingdee.eas.fdc.schedule.framework.util;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;

public interface IRESchTask {
	// id
	public BOSUuid getId();

	public void setId(BOSUuid id);
	
	/**
	 * 源id
	 */
	public String getSrcID();
	public void setSrcID(String srcID);
	
	/**
	 * 任务名称
	 */
	public String getName();
	public void setName(String name);

	/**
	 * 任务编码
	 */
	public String getNumber();	
	public void setNumber(String number);

	/**
	 * 上级任务
	 */	
	public IRESchTask getParentTask();
	public void setParentTask(IRESchTask item);

	
	/**
	 * 任务类别
	 */
	public RESchTaskTypeEnum getTaskType();
	public void setTaskType(RESchTaskTypeEnum item);

	/**
	 * 有效工期
	 */
	public BigDecimal getDay();
	public void setDay(BigDecimal day);

	/**
	 * 前置任务
	 */
	public void addPredecessor(IRESchTaskPredecessor predecessor);

	/**
	 * 计划开始日期
	 * 
	 * @return
	 */
	public Date getPlanStart();
	public void setPlanStart(Date start);

	/**
	 * 计划结束日期
	 * @return
	 */
	public Date getPlanEnd();
	public void setPlanEnd(Date end);
	/**
	 * 责任人
	 */
	public void setAdminPerson(PersonInfo person);
	public PersonInfo getAdminPerson();

	/**
	 * 责任部门
	 */
	public void setAdminDept(FullOrgUnitInfo dept);
	public FullOrgUnitInfo getAdminDept();
    
	public BOSObjectType getBOSType();
	
	
	/**
	 *成果类别
	 */
	public AchievementTypeInfo getAchievementType();
	public void setAchievementType(AchievementTypeInfo getAchievementType);
	
	/**
	 * 标准任务
	 */
	public StandardTaskGuideInfo getStandardTask();
	public void setStandardTask(StandardTaskGuideInfo standardTaskGuideInfo);
	
	/**
	 * 模板任务业务类型 
	 */
	public RESchTemplateTaskBizTypeCollection getRESchBusinessType();
	public String getRESchBusinessTypeDesc();
	public void setRESchBusinessTypeDesc(String item);
	
	/**
	 * 备注
	 */
	public String getDescription();
	public void setDescription(String description);
	
	/**
	 * MsProjectId
	 */
	public String getMsProjectId();
	public void setMsProjectId(String msProjectId);
	
	public String getMsParentPrjId();
	public void setMsParentPrjId(String msParentPrjId);
	
	/**
	 * 前置任务描述
	 */
	public String getPredecessorDesc();
	public void setPredecessorDesc(String item);
	
	/**
	 * 参考工期
	 */
	public int getReferenceDay();
	public void setReferenceDay(int referenceDay);
	
	/**
	 *考核日期 	 
	 */
	public Date checkDate();
	public void setCheckDate(Date checkDate);
	
	/**
	 * 级次
	 */
	public int getLevel();

	public void setLevel(int level);

	/**
	 * 是否明细
	 */
	public boolean isIsLeaf();

	public void setIsLeaf(boolean isLeaf);
}
