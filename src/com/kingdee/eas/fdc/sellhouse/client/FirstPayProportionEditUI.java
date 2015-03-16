/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionFactory;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class FirstPayProportionEditUI extends AbstractFirstPayProportionEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FirstPayProportionEditUI.class);
    
    /**
     * output class constructor
     */
    public FirstPayProportionEditUI() throws Exception
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
		FirstPayProportionInfo value = new FirstPayProportionInfo();
		return value;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FirstPayProportionFactory.getRemoteInstance();
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
}