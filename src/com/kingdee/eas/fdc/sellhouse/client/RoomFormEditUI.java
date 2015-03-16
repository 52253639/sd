/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.framework.*;

import com.kingdee.eas.fdc.sellhouse.RoomFormFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomFormEditUI extends AbstractRoomFormEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomFormEditUI.class);
    
    /**
     * output class constructor
     */
    public RoomFormEditUI() throws Exception
    {
        super();
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
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	if(this.txtDescription.getText().length() > 255)
    	{
    		MsgBox.showInfo("描述长度不能超过255!");
			abort();
    	}
    	if(this.txtSimpleName.getText().length() > 80)
    	{
    		MsgBox.showInfo("简称长度不能超过80!");
			abort();
    	}
    }

	protected IObjectValue createNewData() {
		return new RoomFormInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFormFactory.getRemoteInstance();
	}

}