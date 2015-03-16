/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 部门月度计划申报表
 * 与项目月度计划申报表相同，维护请两边同时进行，后续再重构
 */
public class FDCDepMonBudgetAcctListUI extends AbstractFDCDepMonBudgetAcctListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCDepMonBudgetAcctListUI.class);
    
    /**
     * output class constructor
     */
    public FDCDepMonBudgetAcctListUI() throws Exception
    {
        super();
    }
	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCDepMonBudgetAcctFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return FDCDepMonBudgetAcctEditUI.class.getName();
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionRecension.setVisible(false);
		actionRecension.setEnabled(false);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		//版本号保留一位
		tblMain.getColumn("verNumber").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(1));
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true")){
    		actionRemove.setEnabled(true);
    	}
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionAddNew_actionPerformed(e);
	}
}