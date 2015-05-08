package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sourceforge.ganttproject.cache.ActivityCache;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTaskAdjustEvent;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleTaskChangeHelper;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;

public class FDCScheduleTaskInfo extends AbstractFDCScheduleTaskInfo implements Serializable,IRESchTask 
{
	private String msProjectId;
	private String msParentPrjId;
	
    public FDCScheduleTaskInfo()
    {
        super();
        this.setNotes("");
        this.setEffectTimes(FDCHelper.ONE);
    }
    protected FDCScheduleTaskInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * TODO 应该使用常量定义
     */
    private static ScheduleTaskPropertyCollection customPropertys=null;
    
    /**
	 * 任务属性Map <br>
	 * key: scheduleTaskPropertyInfo.getName()，value: scheduleTaskPropertyInfo
	 */
	private static Map scheduleTaskPropertyMap = new LinkedHashMap();
	
	private static boolean hasInitCache = false;
    
    public ScheduleTaskPropertyCollection getCustomPropertys(int uiMark)
			throws BOSException {
    	customPropertys=null;
    	if(customPropertys==null){
    		customPropertys = ActivityCache.getInstance().getDisplayColumn(uiMark);
			// setCustomPropertysDebug(uiMark);

    		// 初始化任务属性Cache
			initScheduleTaskPropertyCache(uiMark);
    	}
    	
    	return customPropertys;
    }

    /**
	 * 描述：初始化任务属性Cache
	 * 
	 * @param uiMark
	 * @author rd_skyiter_wang
	 * @createDate 2014-6-12
	 */
	public void initScheduleTaskPropertyCache(int uiMark) {
    	if (customPropertys == null) {
			customPropertys = ActivityCache.getInstance().getDisplayColumn(uiMark);
		}
    	
		ScheduleTaskPropertyInfo scheduleTaskPropertyInfo = null;
		String name = null;
		for (int i = 0, size = customPropertys.size(); i < size; i++) {
			scheduleTaskPropertyInfo = customPropertys.get(i);
			name = scheduleTaskPropertyInfo.getName();
			
			if (null != name) {
				scheduleTaskPropertyMap.put(name.toUpperCase(), scheduleTaskPropertyInfo);
			}
		}
		
		hasInitCache = true;
	}

	/**
	 * 描述：重设任务属性Cache
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-6-11
	 */
	public void resetScheduleTaskPropertyCache() {
		customPropertys = null;
		scheduleTaskPropertyMap.clear();

		hasInitCache = false;
	}

	/**
	 * 根据名称取得属性
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo#getCustomPropertyByName(java.lang.String)
	 */
	public ScheduleTaskPropertyInfo getCustomPropertyByName(String name) throws BOSException {
		ScheduleTaskPropertyInfo scheduleTaskPropertyInfo = null;
		
		if (!hasInitCache) {
			scheduleTaskPropertyInfo = super.getCustomPropertyByName(name);
		} else {
			// 从缓存中取数
			scheduleTaskPropertyInfo = null;
			if (null != name) {
				scheduleTaskPropertyInfo = (ScheduleTaskPropertyInfo) scheduleTaskPropertyMap.get(name.toUpperCase());
			}
			
		}
		
		return scheduleTaskPropertyInfo;
	}


	
	/**
	 * 自定义列调试方法
	 * 
	 * @throws BOSException
	 */
	// public static void setCustomPropertysDebug(int uiMark) {
	// customPropertys=new ScheduleTaskPropertyCollection();
	// ScheduleTaskPropertyCollection taskPropertys = ScheduleTaskPropertyInfo
	// .getDefaultScheduleTaskProperty(uiMark);
	// for (int i = 0; i < taskPropertys.size(); i++) {
	// ScheduleTaskPropertyInfo propInfo = taskPropertys.get(i);
	// if (ScheduleTaskPropertyHelper.isIncludeUI(uiMark, propInfo
	// .getDisplayUI())) {
	// if (propInfo.getType() == TaskPropertyTypeEnum.CUSTOM) {
	// customPropertys.add(propInfo);
	// }
	// }
	// }
	// }
    
    public void setCustomPropertyValue(String name, Object value)
			throws BOSException {
    	super.setCustomPropertyValue(name, value);
    }
    public Object getCustomPropertyValue(String mapKey) {
    	if(mapKey!=null&&mapKey.equals("prefixNode")){
    		return null;
    	}
    	return super.getCustomPropertyValue(mapKey);
    }
    
    /**
     * 设置进度任务属性扩展
     * @param taskExt
     */
    public void setTaskExt(FDCScheduleTaskExt taskExt){
    	this.put("taskExt", taskExt);
    }
    
    public FDCScheduleTaskExtInfo getTaskExt(){
    	return (FDCScheduleTaskExtInfo)this.get("taskExt");
    }
    
    public boolean adjustable(KDTaskAdjustEvent event) {
    	//可以调整
    	return ScheduleTaskChangeHelper.isTaskAdjustable(this,event);
    }
    
    /**
     * 是否主项节点
     * @return
     */
    public boolean isMainTask(){
    	if(this.getWbs()!=null&&this.getWbs().getTaskType()!=null&&this.getWbs().getTaskType().getId().toString().equals(TaskTypeInfo.TASKTYPE_MAINTASK)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getPlanDept() {
		Object temp = get("planDept");
		if (temp instanceof CostCenterOrgUnitInfo) {
			return ((CostCenterOrgUnitInfo) temp).castToFullOrgUnitInfo();
		}
		return (com.kingdee.eas.basedata.org.FullOrgUnitInfo) temp;
	}

	public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept() {
		Object temp = get("adminDept");
		if (temp instanceof CostCenterOrgUnitInfo) {
			return ((CostCenterOrgUnitInfo) temp).castToFullOrgUnitInfo();
		} else if (temp instanceof AdminOrgUnitInfo) {
			return ((AdminOrgUnitInfo) temp).castToFullOrgUnitInfo();
		}
		return (com.kingdee.eas.basedata.org.FullOrgUnitInfo) temp;
	}
	
	public void addPredecessor(IRESchTaskPredecessor predecessor) {
		getDependEntrys().add((FDCScheduleTaskDependInfo)predecessor);
	}
	public String getBusinessTypeDesc() {
		// TODO Auto-generated method stub
		return null;
	}
	public BigDecimal getDay() {
		// TODO Auto-generated method stub
		return null;
	}
	public IRESchTask getParentTask() {
		// TODO Auto-generated method stub
		return null;
	}
	public Date getPlanEnd() {
		return getDate("end");
	}
	public Date getPlanStart() {
		return getDate("start");
	}
	public String getPredecessorDesc() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getReferenceDay() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setBusinessTypeDesc(String item) {
		// TODO Auto-generated method stub
		
	}
	public void setDay(BigDecimal day) {
		// TODO Auto-generated method stub
		
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
	public void setParentTask(IRESchTask item) {
		// TODO Auto-generated method stub
		
	}
	public void setPlanEnd(Date end) {
		setDate("end", end);
	}
	public void setPlanStart(Date start) {
		setDate("start", start);
	}
	public void setPredecessorDesc(String item) {
		// TODO Auto-generated method stub
		
	}
	public void setReferenceDay(int referenceDay) {
		// TODO Auto-generated method stub
		
	}
	
	public RESchTemplateTaskBizTypeCollection getRESchBusinessType() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getRESchBusinessTypeDesc() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setRESchBusinessTypeDesc(String item) {
		// TODO Auto-generated method stub
		
	}
	public Date checkDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getLevel() {
		return getInt("level");
	}

	public void setLevel(int item) {
		setInt("level", item);
	}

	public boolean isIsLeaf() {
		return getBoolean("isLeaf");
	}

	public void setIsLeaf(boolean item) {
		setBoolean("isLeaf", item);
	}
}