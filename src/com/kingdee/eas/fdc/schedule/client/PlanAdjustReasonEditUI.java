/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.PlanAdjustReason;
import com.kingdee.eas.fdc.schedule.PlanAdjustReasonFactory;
import com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class PlanAdjustReasonEditUI extends AbstractPlanAdjustReasonEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PlanAdjustReasonEditUI.class);
    
    /**
     * output class constructor
     */
    public PlanAdjustReasonEditUI() throws Exception
    {
        super();
    }
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if(editData != null && editData.isIsEnabled()){
    		FDCMsgBox.showError("计划调整原因已启用，不能修改！");
    		SysUtil.abort();
    	}
    	super.actionEdit_actionPerformed(e);
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	txtName.setMaxLength(80);
    	txtNumber.setMaxLength(80);
    	bizDescription.setMaxLength(250);
    	btnCancel.setVisible(false);
    	btnCancelCancel.setVisible(false);
    	btnRemove.setVisible(false);
    }
    
    protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		PlanAdjustReasonInfo planAdjRsnInfo = new PlanAdjustReasonInfo();
		planAdjRsnInfo.setIsEnabled(true);
		return planAdjRsnInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PlanAdjustReasonFactory.getRemoteInstance();
	}
	
    public SelectorItemCollection getSelectors(){
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("*"));
        return sic;
    }   
	
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.PLANADJUSTREASON));
	}
    public void setOprtState(String oprtType) {
    	super.setOprtState(oprtType);
    	setTitle();
    }
}