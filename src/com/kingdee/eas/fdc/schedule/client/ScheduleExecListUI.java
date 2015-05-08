/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ScheduleExecListUI extends AbstractScheduleExecListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleExecListUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleExecListUI() throws Exception
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

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
    }
    
    protected FilterInfo getMainFilter() {
    	FilterInfo mainFilter = super.getMainFilter();
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("isLatestVer", Boolean.TRUE);
    	//只显示执行了的任务
    	filter.appendFilterItem("state", ScheduleStateEnum.EXETING_VALUE);
    	try {
			mainFilter.mergeFilter(filter, "and");
		} catch (BOSException e) {
			this.handleException(e);
		}
		return mainFilter;
    }
    
    protected String getEditUIName() {
    	return ScheduleExecEditUI.class.getName();
    }
    
    protected boolean isTableTreeRow() {
    	return false;
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	ItemAction action = getActionFromActionEvent(e);
        if (action.equals(actionEdit)){
        	uiContext.put("isScheduleExec", Boolean.TRUE);
        }
    	
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWWIN;
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionAudit.setVisible(false);
    	actionUnAudit.setVisible(false);
    	actionViewWorkFlow.setVisible(false);
    }
    protected void initWorkButton() {
    	super.initWorkButton();
    	actionEdit.setVisible(true);
    	actionEdit.setEnabled(true);
    	actionEdit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_datatask_execute"));
    	menuEdit.setVisible(true);
    }
    
    protected void updateButtonStatus() {
    	super.updateButtonStatus();
    	actionEdit.setEnabled(true);
    }
}