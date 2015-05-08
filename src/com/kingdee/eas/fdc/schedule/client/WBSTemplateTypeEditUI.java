/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.WBSTemplateFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class WBSTemplateTypeEditUI extends AbstractWBSTemplateTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(WBSTemplateTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public WBSTemplateTypeEditUI() throws Exception
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

    public void onLoad() throws Exception {
    	super.onLoad();
    	actionCancel.setVisible(false);
    	actionCancelCancel.setVisible(false);
    	btnCancel.setVisible(false);
    	btnCancelCancel.setVisible(false);
    	txtName.setMaxLength(80);
    	txtNumber.setMaxLength(80);
    	txtDescription.setMaxLength(255);
    }

    protected void initWorkButton() {
    	super.initWorkButton();
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
		return new WBSTemplateTypeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WBSTemplateTypeFactory.getRemoteInstance();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkIsRefered();
		super.actionRemove_actionPerformed(e);
	}
	
	private void checkIsRefered() throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("templateType.id",editData.getId().toString(),CompareType.EQUALS));
		if(WBSTemplateFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showError("模板类型被引用，不能删除！");
			SysUtil.abort();
		}
	}
	public boolean destroyWindow() {
		boolean flag = super.destroyWindow();
		WBSTemplateListUI ownerUI = (WBSTemplateListUI) getUIContext().get(UIContext.OWNER);
		try {
			ownerUI.loadFields();
		} catch (Exception e) {
			this.handleException(e);
		}
		return flag;
	}
}