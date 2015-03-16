/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.EventTypeFactory;
import com.kingdee.eas.fdc.sellhouse.EventTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class EventTypeEditUI extends AbstractEventTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EventTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public EventTypeEditUI() throws Exception
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
	}
    
	protected IObjectValue createNewData() {
		EventTypeInfo value = new EventTypeInfo();
		return value;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return EventTypeFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return kDBizMultiLangBox1;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String number = this.editData.getNumber();
		if(number == null){
			return;
		}
		for(int i=0; i<FDCCustomerConstant.CAN_NOT_DEL_NUMBERS.length; i++){
			if(FDCCustomerConstant.CAN_NOT_DEL_NUMBERS[i].equals(number)){
				MsgBox.showInfo(FDCCustomerConstant.CAN_NOT_EDIT_DES);
				this.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
	
}