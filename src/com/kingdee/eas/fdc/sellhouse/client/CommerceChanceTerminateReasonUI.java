/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceTerminateReasonUI extends AbstractCommerceChanceTerminateReasonUI
{
    public CommerceChanceTerminateReasonUI() throws Exception {
		super();
	}

	private static final Logger logger = CoreUIObject.getLogger(CommerceChanceTerminateReasonUI.class);

	protected IObjectValue createNewData() {
		return new CommerceChanceInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		String txtArea = this.textAreaTerminateReason.getStringValue();
		if(txtArea==null || txtArea.trim().equals("")){
			MsgBox.showInfo("理由不能为空!");
			return;
		}
		
		
		
		CommerceChanceInfo comInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(this.editData.getId()));  //;
		if(comInfo==null) return;
		
		if(!comInfo.getCommerceStatus().equals(CommerceStatusEnum.Intent))  {
			MsgBox.showInfo("只有意向状态的商机可以终止!");
			return ;
		}
		
		this.editData.setCommerceStatus(CommerceStatusEnum.Finish);
		
		super.beforeStoreFields(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		
		((ListUI)this.getUIContext().get("ListUI")).refreshList();
		destroyWindow();
	}
    
    
	
	
	
	




}