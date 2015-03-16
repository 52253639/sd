/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexTreeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;

/**
 * output class name
 */
public class MarketSplAuditIndexEditUI extends AbstractMarketSplAuditIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSplAuditIndexEditUI.class);
    
    /**
     * output class constructor
     */
    public MarketSplAuditIndexEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	this.prmtAccreditationWD.setEnabled(false);
    	super.onLoad();
    	this.txtNumber.setRequired(true);
    	this.txtName.setRequired(true);
    	this.chkisEnable.setVisible(false);
    	this.menuBar.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	actionEdit.setVisible(false);
    	actionRemove.setVisible(false);
    	actionSave.setVisible(false);
    	actionCopy.setVisible(false);
    	actionCancel.setVisible(false);
    	actionCancelCancel.setVisible(false);
    	this.actionFirst.setVisible(false);//第一
		this.actionPre.setVisible(false);//前一
		this.actionNext.setVisible(false);//后一
		this.actionLast.setVisible(false);//最后一个
		this.kDLabelContainer3.setVisible(false);
		this.kDLabelContainer4.setVisible(false);
		if(OprtState.ADDNEW.equals(getOprtState())){
			this.chkisEnable.setSelected(true);
		}
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    }
    protected void verifyInput(ActionEvent e) throws Exception {
    	if(UIRuleUtil.isNull(txtNumber.getText())){
    		MsgBox.showWarning("编码不能为空！");
    		SysUtil.abort();
    	}if(UIRuleUtil.isNull(editData.getName())){
    		MsgBox.showWarning("名称不能为空！");
    		SysUtil.abort();
    	}
    	super.verifyInput(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexFactory.getRemoteInstance();
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setAccreditationWD((MarketSplAuditIndexTreeInfo) getUIContext().get("group"));
        return objectValue;
    }

}