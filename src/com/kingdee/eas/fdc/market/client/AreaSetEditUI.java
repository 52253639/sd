/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.AreaSetFactory;
import com.kingdee.eas.fdc.market.MarketCompareEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class AreaSetEditUI extends AbstractAreaSetEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AreaSetEditUI.class);
    
    /**
     * output class constructor
     */
    public AreaSetEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		this.btnSave.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
	}

	protected void initOldData(IObjectValue dataObject) {
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        String des = "";
        AdminOrgUnitInfo info = SysContext.getSysContext().getCurrentAdminUnit();
        editData.setCompanyId(info);
        editData.setCompanyNum(info.getNumber());
        if(this.txtArea1.getText() != null){
        	if(this.comboCompare1.getSelectedItem() != null){
        		if(((MarketCompareEnum)comboCompare1.getSelectedItem()).getName().equals(MarketCompareEnum.GREATEEQUALS.getName())){
        			des = txtArea1.getText()+ "m2" + MarketCompareEnum.LESSEQUALS_VALUE + "面积段";
        		}
        		else if(((MarketCompareEnum)comboCompare1.getSelectedItem()).getName().equals(MarketCompareEnum.GREATETHAN.getName())){
        			des = txtArea1.getText()+ "m2" + MarketCompareEnum.LESSTHAN_VALUE + "面积段";
        		}
        		else{
        			des = ((MarketCompareEnum)comboCompare1.getSelectedItem()).getValue() + txtArea1.getText() + "m2";
        		}
            	editData.setMinAreaVal(((MarketCompareEnum)comboCompare1.getSelectedItem()).getValue()+txtArea1.getText());
        	}
        }
        if(this.txtArea2.getText() != null){
        	if(this.comboCompare2.getSelectedItem() != null){
            	des = des + ((MarketCompareEnum)comboCompare2.getSelectedItem()).getValue() + txtArea2.getText() + "m2";
            	editData.setMaxAreaVal(((MarketCompareEnum)comboCompare2.getSelectedItem()).getValue()+txtArea2.getText());
        	}
        }
        editData.setDescription(des);
    }
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	if(comboCompare1.getSelectedItem().equals(MarketCompareEnum.LESSEQUALS) || comboCompare1.getSelectedItem().equals(MarketCompareEnum.LESSTHAN)){
    		FDCMsgBox.showInfo("比较符一选择错误！");
    		SysUtil.abort();
    	}
    	if(comboCompare2.getSelectedItem().equals(MarketCompareEnum.GREATEEQUALS) || comboCompare2.getSelectedItem().equals(MarketCompareEnum.GREATETHAN)){
    		FDCMsgBox.showInfo("比较符二选择错误！");
    		SysUtil.abort();
    	}
	}

	/**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

	protected IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.AreaSetInfo objectValue = new com.kingdee.eas.fdc.market.AreaSetInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        objectValue.setIsEnabled(true);
        return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AreaSetFactory.getRemoteInstance();
	}
}