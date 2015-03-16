/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.InviteFormFactory;
import com.kingdee.eas.fdc.invite.InviteFormInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class InviteFormEditUI extends AbstractInviteFormEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteFormEditUI.class);
    
    /**
     * output class constructor
     */
    public InviteFormEditUI() throws Exception
    {
        super();
    }

	@Override
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	@Override
	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	@Override
	protected IObjectValue createNewData() {
		InviteFormInfo info=new InviteFormInfo();
		info.setIsEnabled(true);
		return info;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return InviteFormFactory.getRemoteInstance();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboType);
		super.verifyInput(e);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.txtDesc.setMaxLength(250);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.comboType.setRequired(true);
	}


}