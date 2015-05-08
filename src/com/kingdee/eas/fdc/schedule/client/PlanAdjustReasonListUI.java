/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.PlanAdjustReasonFactory;
import com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class PlanAdjustReasonListUI extends AbstractPlanAdjustReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PlanAdjustReasonListUI.class);
    
    /**
     * output class constructor
     */
    public PlanAdjustReasonListUI() throws Exception
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

    protected FDCDataBaseInfo getBaseDataInfo() {
		return new PlanAdjustReasonInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PlanAdjustReasonFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return PlanAdjustReasonEditUI.class.getName();
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		FDCSCHClientHelper.checkIsEnabled(tblMain, "计划调整原因已启用，不能修改！");
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		FDCSCHClientHelper.checkIsEnabled(tblMain, "计划调整原因已启用，不能删除！");
		super.actionRemove_actionPerformed(e);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initButtonStyle();
	}

	private void initButtonStyle() {

		if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionCancel.setEnabled(true);
			this.actionCancelCancel.setEnabled(true);
		}
		
	}
}