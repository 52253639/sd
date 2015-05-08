/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SpecialScheduleListUI extends AbstractSpecialScheduleListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialScheduleListUI.class);
    
    /**
     * output class constructor
     */
    public SpecialScheduleListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    protected FilterInfo getMainFilter() throws Exception {
    	FilterInfo mainFilter = super.getMainFilter();
    	FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("scheduleType.id",TaskTypeInfo.TASKTYPE_SPECIALTASK));
    	filter.getFilterItems().add(new FilterItemInfo("version",FDCHelper.ZERO,CompareType.GREATER));
    	mainFilter.mergeFilter(filter, "and");
		return mainFilter;
    }
    
    protected String getEditUIName() {
    	return SpecialScheduleEditUI.class.getName();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	btnCancelCancel.setText("执行");
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected void updateButtonStatus() {
    	super.updateButtonStatus();
    	//如果责任部门不是当前组织的话不允许编辑
    	IRow row = FDCTableHelper.getSelectedRow(getMainTable());
    	if(row!=null&&SysContext.getSysContext().getCurrentOrgUnit()!=null){
    		String currentOrgId=SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    		Object adminDeptId=row.getCell("adminDept.id").getValue();
    		if(currentOrgId!=null&&adminDeptId!=null&&adminDeptId.toString().equals(currentOrgId)){
    			actionEdit.setEnabled(true);
    			actionRemove.setEnabled(true);
    		}else{
    			actionEdit.setEnabled(false);
    			actionRemove.setEnabled(false);
    		}
    	}
    }
}