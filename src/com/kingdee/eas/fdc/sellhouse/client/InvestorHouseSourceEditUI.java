/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceFactory;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class InvestorHouseSourceEditUI extends AbstractInvestorHouseSourceEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvestorHouseSourceEditUI.class);
    
    public InvestorHouseSourceEditUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		
		//¿ØÖÆ×Ö·û³¤¶È
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.txtNumber.setMaxLength(80);
		this.txtSimpleName.setMaxLength(80);
    }
    
	protected IObjectValue createNewData() {
		return new InvestorHouseSourceInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InvestorHouseSourceFactory.getRemoteInstance();
	}

}