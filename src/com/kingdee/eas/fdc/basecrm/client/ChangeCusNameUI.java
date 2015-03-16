/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ChangeCusNameUI extends AbstractChangeCusNameUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeCusNameUI.class);
    
    /**
     * output class constructor
     */
    public ChangeCusNameUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	this.txtOldName.setEnabled(false);
    	super.onLoad();
    	
    	FDCBaseCustomerInfo cus = (FDCBaseCustomerInfo) this.getUIContext().get("srcCus");
    	this.txtOldName.setText(cus.getName());
    	
    }
    
    protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
    	if(this.txtNewName.getText() == null  ||  this.txtNewName.getText().trim().equals("")){
    		MsgBox.showInfo(this, "ÇëÂ¼ÈëÐÂÃû³Æ£¡");
    		this.abort();
    	}
    	
    	this.getUIContext().put("newName", this.txtNewName.getText().trim());
    	this.getUIContext().put("continue", Boolean.TRUE);
    	this.destroyWindow();
    }
    
    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIContext().put("continue", Boolean.FALSE);
    	this.destroyWindow();
    }
    
}