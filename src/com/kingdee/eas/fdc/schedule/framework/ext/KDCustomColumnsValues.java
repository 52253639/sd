package com.kingdee.eas.fdc.schedule.framework.ext;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.CustomColumnsStorage;
import net.sourceforge.ganttproject.task.CustomColumnsValues;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.util.StringUtils;
import com.kingdee.util.enums.Enum;

/**
 * 扩展自CustomColumnsValues
 * @author xiaohong_shi
 *
 */
public class KDCustomColumnsValues extends CustomColumnsValues {
	private final CustomColumnsStorage myColumnStorage;
    private final Map mapCustomColumnValue = new HashMap();
    private final KDTask task;
    private final ScheduleTaskBaseInfo scheduleTaskInfo;
    private boolean afterInit=false;
	/**
	 * @param columnStorage
	 */
	public KDCustomColumnsValues(KDTask task,CustomColumnsStorage columnStorage,ScheduleTaskBaseInfo scheduleTaskInfo) {
		super(columnStorage);
		myColumnStorage=columnStorage;
		this.task=task;
		this.scheduleTaskInfo=scheduleTaskInfo;
	}
	
	protected void setAfterInit(boolean afterInit){
		this.afterInit=afterInit;
	}
	
    /**
     * Set the value for the customColumn whose name is given.
     *
     * @param customColName
     *            The name of the CustomColumn.
     * @param value
     *            The associated value.
     * @throws CustomColumnsException
     *             Throws if <code>customColName</code> does not exist or
     *             <code>value</code> class does not match the CustomColum
     *             class.
     */
    public void setValue(String customColName, Object value)
            throws CustomColumnsException {
    	// 进度性能优化： 性能瓶颈 6.0% - 5,263 ms - 11,937 inv by skyiter_wang 2014-06-11
    	
        if (!myColumnStorage.exists(customColName)) {
			// 进度性能优化： 性能瓶颈 43.8% - 41,939 ms - 3,734,205 inv by skyiter_wang 2014-06-11

			// 进度性能优化: 此处是可以优化的 by skyiter_wang 2014-06-11

			// ////////////////////////////////////////////////////////////////////////
			// 抛出异常会带来大量的CPU消耗。
			// 1、优化方案1：改为打印日志(待定)
			// 2、优化方案2：TaskHandler.handle中已经设置uiMark，
			// 那么FDCScheduleTaskInfo.getCustomPropertys-->ActivityCache.
			// getDisplayColumn中生成的缓存对象myCustomerColoumns就是正确的，见：myCustomerColoumns.put(mark,
			// allColumns)
			// 此处就不会抛出异常
			// 3、所以采用方案2
			// ////////////////////////////////////////////////////////////////////////
			throw new CustomColumnsException(CustomColumnsException.DO_NOT_EXIST, customColName);
		}

        if (value==null) {
        	mapCustomColumnValue.remove(customColName);
        	try {
				this.scheduleTaskInfo.setCustomPropertyValue(customColName, value);
			} catch (BOSException e) {
				Logger.info("error ocatual in set value to FDCScheduleTaskInfo!" + this.getClass().getName());
			}
        	return;
        }
        if(value instanceof Date){
        	value=ScheduleParserHelper.parseDateToGanttCalendar((Date)value);
        }
        
        Class c1 = myColumnStorage.getCustomColumn(customColName).getType();
        Class c2 = value.getClass();
		if (value != null && !c1.isAssignableFrom(c2)) {
			// 如果是Info，但是只显示String，就默认取name
			if (c1 == String.class && IObjectValue.class.isAssignableFrom(c2)) {
				mapCustomColumnValue.put(customColName, ((IObjectValue) value)
						.getString("name"));
			}
			// 如果是枚举，则取枚举名称
			else if (Enum.class.isAssignableFrom(c2)) {
				mapCustomColumnValue.put(customColName, ((Enum) value)
						.getAlias());
			} else if (ScheduleTaskBizTypeCollection.class.equals(c2)) {
				StringBuffer strBizType = new StringBuffer();
				ScheduleTaskBizTypeCollection values = (ScheduleTaskBizTypeCollection) value;
				for (int i = 0; i < values.size(); i++) {
					ScheduleBizTypeInfo item = ((ScheduleTaskBizTypeInfo) values.get(i)).getBizType(); 
					if (item == null || StringUtils.isEmpty(item.getName())) {
						continue;
					}
					if (i > 0) {
						strBizType.append("," + item.getName());
					} else {
						strBizType.append(item.getName());
					}
				}
				
				mapCustomColumnValue.put(customColName, strBizType);
			} else {
				throw new CustomColumnsException(
						CustomColumnsException.CLASS_MISMATCH, null);
			}
		} else {
			mapCustomColumnValue.put(customColName, value);
		}
		// 将值存储到Info
        
        if(afterInit){
        	if(value instanceof GanttCalendar){
        		value=ScheduleParserHelper.parseGanttCalendarToDate((GanttCalendar)value);
        	}
        	try {
				this.scheduleTaskInfo.setCustomPropertyValue(customColName,
						value);
			} catch (BOSException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * Returns the value for the given customColName.
     *
     * @param customColName
     *            The name of the custom column we want to get the value.
     * @return The value for the given customColName.
     */
    public Object getValue(String customColName) {
    	if(customColName!=null&&customColName.equals(GanttTreeTableModelExt.strColPrefixNode)){
    		TaskDependency[] array = task.getDependenciesAsDependant().toArray();
    		String prefix=null;
    		for(int i=0;i<array.length;i++){
    			String prefixName = null;
    			if(array[i].getDependee() instanceof KDTask){
					KDTask task = (KDTask)array[i].getDependee();
					prefixName = task.getScheduleTaskInfo().getName();
				}
				else{
					prefixName = array[i].getDependee().getName();
				}
    			if(prefix==null){
    				prefix = prefixName;
    			}else{
    				
    				prefix = prefix+","+prefixName;
    			}
    		}
    		return prefix;
    	}
        return mapCustomColumnValue.get(customColName);
    }

    /**
     * Remove the custom column (and also its value) from this
     * CustomColumnValues.
     *
     * @param colName
     *            Name of the column to remove.
     */
    public void removeCustomColumn(String colName) {
        mapCustomColumnValue.remove(colName);
    }

    public void renameCustomColumn(String oldName, String newName) {
        Object o = mapCustomColumnValue.get(oldName);
        mapCustomColumnValue.put(newName, o);
        mapCustomColumnValue.remove(oldName);
    }

    public Object clone() {
    	KDCustomColumnsValues res = new KDCustomColumnsValues(task,myColumnStorage,scheduleTaskInfo);
        Iterator it = mapCustomColumnValue.keySet().iterator();
        while (it.hasNext()) {
            Object k = it.next();
            res.mapCustomColumnValue.put(k, mapCustomColumnValue.get(k));
        }
        return res;
    }

    public String toString() {
        return mapCustomColumnValue.toString();
    }

}
