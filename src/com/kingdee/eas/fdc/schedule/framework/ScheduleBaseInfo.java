package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import java.util.Iterator;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.util.BOSUuid;

public class ScheduleBaseInfo extends AbstractScheduleBaseInfo implements Serializable 
{
	public static final int WEAK_MODE_SIZE = 50;
    public ScheduleBaseInfo()
    {
        super();
    }
    protected ScheduleBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * 因为元数据发布的AbstractXXXInfo 类无法修改，所以这里不能用抽象的方法，提供了默认的实现，子类重载
     * @return ScheduleTaskBaseCollection
     */
    public ScheduleTaskBaseCollection getScheduleTasks(){
    	AbstractObjectCollection coll=(AbstractObjectCollection) get("taskEntrys");
    	return (ScheduleTaskBaseCollection)coll.cast(ScheduleTaskBaseCollection.class);
    }
    
    /**
     * 因为元数据发布的AbstractXXXInfo 类无法修改，所以这里不能用抽象的方法，提供了默认的实现，子类可重载
     * Object: 房地产进度 's 列显示 property 
     */
    public ScheduleTaskdisplaycolumnsCollection getScheduleDispColumns(){
    	AbstractObjectCollection coll=(AbstractObjectCollection) get("dispColumns");
    	return (ScheduleTaskdisplaycolumnsCollection)coll.cast(ScheduleTaskdisplaycolumnsCollection.class);
    }
    
    /**
     * 因为元数据发布的AbstractXXXInfo 类无法修改，所以这里不能用抽象的方法，提供了默认的实现，子类可重载
     * @return
     */
    public ScheduleCalendarInfo getCalendar(){
    	return (ScheduleCalendarInfo)get("calendar");
    }
/*    *//**
     * 因为元数据发布的AbstractXXXInfo 类无法修改，所以这里不能用抽象的方法，提供了默认的实现，子类可重载
     * @return
     *//*
    public ScheduleTaskPropertyCollection getScheduleTaskPropertys(){
    	AbstractObjectCollection coll=(AbstractObjectCollection) get("taskPropertys");
    	return (ScheduleTaskPropertyCollection)coll.cast(ScheduleTaskPropertyCollection.class);
    }*/
    
    private ScheduleTaskPropertyCollection  taskPropertys=null;
    public ScheduleTaskPropertyCollection getTaskPropertys(){
    	return this.taskPropertys;
    }
    
    public void setTaskPropertys(ScheduleTaskPropertyCollection taskPropertys){
    	this.taskPropertys=taskPropertys;
    }
    
    public void setDisplayColumn(String [] columnPropertyIds){
    	this.getScheduleDispColumns().clear();
    	for(int i=0;i<columnPropertyIds.length;i++){
        	ScheduleTaskdisplaycolumnsInfo item=new ScheduleTaskdisplaycolumnsInfo();
    		item.setId(BOSUuid.create(item.getBOSType()));
    		ScheduleTaskPropertyInfo taskProperty = getTaskProperty(columnPropertyIds[i]);
			item.setProperty(taskProperty);
    		item.setOrder(i);
			// item.setWidth(taskProperty.getWidth());
			this.getScheduleDispColumns().add(item);
		}
    }
    
	private ScheduleTaskPropertyInfo getTaskProperty(String properityId){
		for(Iterator iter=this.getTaskPropertys().iterator();iter.hasNext();){
			ScheduleTaskPropertyInfo item=(ScheduleTaskPropertyInfo)iter.next();
			if(item.getPropertyId().equals(properityId)){
				return item;
			}
		}
		return null;
	}
	
	/**
	 * 控制计划是否可以编辑
	 * @return
	 *  by sxhong 2009-09-17 14:24:26
	 *  @see com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt.isKDTaskCellEditable(KDTask task, int column)
	 */
	public boolean isEditable(){
		return this.getBoolean("isEditable");
	}
	public void setEditable(boolean isEditable){
		this.setBoolean("isEditable", isEditable);
	}
	/**
	 * 
	 * 描述：计划是否只读; (计划执行，总进度计划， 非保存、提交状态的计划都只读)
	 * @param isReadOnly
	 * 创建时间：2010-8-17
	 * 创建人：zhiqiao_yang
	 *
	 */
	public void setReadOnly(boolean isReadOnly){
		setBoolean("ScheduleIsReadOnly", isReadOnly);
	}
	public boolean isReadOnly(){
		return getBoolean("ScheduleIsReadOnly");
	}
}