/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketSupplierAppraiseTemplateTreeEditUI extends AbstractMarketSupplierAppraiseTemplateTreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierAppraiseTemplateTreeEditUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierAppraiseTemplateTreeEditUI() throws Exception
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
    	this.kDTextField1.setEditable(false);
    	this.actionAddNew.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionSave.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.kDTextField2.setRequired(true);
    	this.kDTextField3.setRequired(true);
    	
    }
    
    public void loadFields() {
    	super.loadFields();
    	this.kDTextField1.setText(editData.getParent()!=null?editData.getParent().getNumber():null);
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	if(UIRuleUtil.isNull(this.kDTextField2.getText())){
    		MsgBox.showWarning("本级编码不能为空！");
    		SysUtil.abort();
    	}
    	if(UIRuleUtil.isNull(this.kDTextField3.getText())){
    		MsgBox.showWarning("名称不能为空！");
    		SysUtil.abort();
    	}
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateTreeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateTreeInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateTreeInfo();
		
        return objectValue;
    }

}