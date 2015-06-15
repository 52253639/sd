/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.contract.EvaluationResultFactory;
import com.kingdee.eas.fdc.contract.EvaluationResultInfo;
import com.kingdee.eas.fdc.contract.client.EvaluationResultEditUI;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class EvaluationResultListUI extends AbstractEvaluationResultListUI
{
    private static final Logger logger = CoreUIObject.getLogger(EvaluationResultListUI.class);
    public EvaluationResultListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new EvaluationResultInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return EvaluationResultFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return EvaluationResultEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected boolean isSystemDefaultData(int activeRowIndex){
		return false;
    }
}