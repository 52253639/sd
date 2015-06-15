/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.EvaluationResultFactory;
import com.kingdee.eas.fdc.contract.EvaluationResultInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class EvaluationResultEditUI extends AbstractEvaluationResultEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EvaluationResultEditUI.class);
    public EvaluationResultEditUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return this.bizName;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		EvaluationResultInfo info=new EvaluationResultInfo();
		info.setIsEnabled(true);
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return EvaluationResultFactory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.bizName);
		super.verifyInput(e);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.bizDescription.setMaxLength(255);
		this.txtNumber.setRequired(true);
		this.bizName.setRequired(true);
	}

}