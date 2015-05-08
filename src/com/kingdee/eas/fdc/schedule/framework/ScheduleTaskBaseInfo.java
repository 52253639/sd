package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTaskAdjustEvent;

public class ScheduleTaskBaseInfo extends AbstractScheduleTaskBaseInfo implements Serializable 
{
    public ScheduleTaskBaseInfo()
    {
        super();
    }
    protected ScheduleTaskBaseInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * Object: 进度 
     */
    public ScheduleBaseInfo getScheduleBase()
    {
        return (ScheduleBaseInfo)get("schedule");
    }
    
    /**
     * 因为元数据发布的AbstractXXXInfo 类无法修改，所以这里不能用抽象的方法，提供了默认的实现，子类重载
     * @return ScheduleTaskDependCollection
     */
    public ScheduleTaskDependCollection getDepends(){
    	AbstractObjectCollection coll=(AbstractObjectCollection)get("dependEntrys");
    	return (ScheduleTaskDependCollection)coll.cast(ScheduleTaskDependCollection.class);
    }

	/**
	 * 子类重写此方法以定义自定义的列
	 * 
	 * @return
	 * @throws BOSException
	 */
    public ScheduleTaskPropertyCollection getCustomPropertys(int uiMark)
			throws BOSException {
    	return new ScheduleTaskPropertyCollection();
    }

	/**
	 * 通过名称取得自定义属性
	 * 
	 * @return
	 * @throws BOSException
	 */
    public ScheduleTaskPropertyInfo getCustomPropertyByName(String name)
			throws BOSException {
    	// 进度性能优化: 性能瓶颈 3.8% - 3,314 ms - 6,771 inv by skyiter_wang 2014-06-11
    	
    	// 进度性能优化: 此处是可以优化的 by skyiter_wang 2014-06-11
    	// T_SCH_ScheduleTaskProperty是一个列名属性常量表，总共24条记录。用于控制列隐藏、默认列宽、对齐等属性。
		// 因此可以放在一个缓存Map中。
		// 子类已经重写，见FDCScheduleTaskInfo.getCustomPropertyByName
    	ScheduleTaskPropertyCollection customPropertys = getCustomPropertys(0);
    	if (customPropertys == null)
			return null;
    	for(int i=0;i<customPropertys.size();i++){
    		ScheduleTaskPropertyInfo scheduleTaskPropertyInfo = customPropertys.get(i);
    		String myName = scheduleTaskPropertyInfo.getName();
			if(myName!=null&&myName.equalsIgnoreCase(name)){
    			return scheduleTaskPropertyInfo;
    		}
    	}
    	return null;
    }
    
    public Object getCustomPropertyValue(String mapKey){
/*    	Map customDispayMap=getCustomDispayMap();
    	String disPlayKey=(String)customDispayMap.get(key);
    	if(disPlayKey==null){
    		disPlayKey=key;
    	}*/
    	if (mapKey == null) {
			return null;
		}
    	return getValueByExtendKey(mapKey);
    }
    
    /**
	 * 取得枚举的名称
	 * <p>
	 * 根据枚举路径，以及枚举对应info中的字段，反射调用到枚举并取得名称
	 * 
	 * @author emanon
	 * @param enumClass
	 * @param mapKey
	 * @return
	 */
    public Object getCustomPropertyEnum(String enumClass, String mapKey) {
		if (mapKey == null) {
			return null;
		}
		Object obj = this;
		if ((this.get(mapKey)) instanceof String) {
			obj = ((IObjectValue) obj).get(mapKey);
			try {
				Method method = Class.forName(enumClass).getMethod("getEnum",new Class[]{String.class});
				obj = method.invoke(null, new Object[]{obj});
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				return obj;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			// 不是IObjectValue还有属性肯定是错误了，直接返回Null
			return ((IObjectValue) obj).get(mapKey) == null ? null : ((IObjectValue) obj).get(mapKey);
		}
		return obj;
	}
    
    public void setCustomPropertyValue(String name, Object value)
			throws BOSException {
    	// 进度性能优化： 性能瓶颈 4.2% - 3,697 ms - 6,771 inv by skyiter_wang 2014-06-11
    	
    	ScheduleTaskPropertyInfo prop = getCustomPropertyByName(name);
    	if(prop==null)
    		return;
    	String mapKey=prop.getMapKey();
    	if(mapKey==null){
    		return;
    	}
    	//自有属性
    	if(mapKey.indexOf('.')<0){
    		this.put(mapKey, value);
    		return;
    	}
    	//关联属性,找到他的上级，然后将他put进去
    	String parentMapKey=mapKey.substring(0,mapKey.lastIndexOf('.'));
    	String subMapKey=mapKey.substring(mapKey.lastIndexOf('.')+1);
    	Object obj = getValueByExtendKey(parentMapKey);
    	if(obj instanceof IObjectValue){
    		((IObjectValue)obj).put(subMapKey, value);
    	}
    }
    
    /**
     * adminUser.CU.number 将取得adminUser值对象的CU的number
     * @param extendKey 如adminUser.CU.number
     * @return
     */
    public Object getValueByExtendKey(String extendKey){
    	String splits[]=extendKey.split("\\.");
    	Object obj=this;
    	for(int i=0;i<splits.length;i++){
    		if(obj instanceof IObjectValue){
    			String key=splits[i];
    			obj=((IObjectValue)obj).get(key);
    		}else{
    			//不是IObjectValue还有属性肯定是错误了，直接返回Null
    			return null;
    		}
    	}
/*    	if(obj instanceof BigDecimal){
    		obj=obj.toString();
    	}*/
    	return obj;
    }
    
	/**
	 * 控制节点是否可以编辑，在任务界面使用
	 * @return
	 */
	public boolean isEditable(){
		return this.getBoolean("isEditable");
	}
	public void setEditable(boolean isEditable){
		this.setBoolean("isEditable", isEditable);
	}
	
    public boolean isAdd(){
    	return this.getBoolean("isAdd");
    }
    public void setIsAdd(boolean isAdd){
    	this.setBoolean("isAdd", isAdd);
    }
    /**
     * 是否是本计划的任务还是上级带出来的任务
     * @return
     */
    public boolean isScheduleTask(){
    	return this.getBoolean("isScheduleTask");
    }
    public void setIsScheduleTask(boolean isScheduleTask){
    	this.setBoolean("isScheduleTask", isScheduleTask);
    }
    
    /**
     * 开始时间的限制范围，用于控制下级节点的调整
     * @param start
     */
    public void setBoundStart(Date start){
    	this.put("boundStart", start);
    }
    
    /**
     * 结束时间的限制范围，用于控制下级节点的调整
     * @param end
     */
    public void setBoundEnd(Date end){
    	this.put("boundEnd", end);
    }
    
    public Date getBoundStart(){
    	return (Date)this.get("boundStart");
    }
    
    public Date getBoundEnd(){
    	return (Date)this.get("boundEnd");
    }
    
    /**
     * 是否可以调整
     * @param event
     * @return
     */
    public boolean adjustable(KDTaskAdjustEvent event){
    	return true;
    }
    
    public Date getStart() {
		if (getDate("start") == null) {
			return FDCDateHelper.getDayBegin(new Date());
		}
		return FDCDateHelper.getDayBegin(getDate("start"));
	}

	public Date getEnd() {
		if (getDate("end") == null) {
			return FDCDateHelper.getDayBegin(new Date());
		}
		return FDCDateHelper.getDayBegin(getDate("end"));
	}
}