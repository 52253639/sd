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
import com.kingdee.eas.fdc.sellhouse.HopePitchFactory;
import com.kingdee.eas.fdc.sellhouse.HopePitchInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class HopePitchEditUI extends AbstractHopePitchEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(HopePitchEditUI.class);
    
    /**
     * output class constructor
     */
    public HopePitchEditUI() throws Exception
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
		HopePitchInfo hopePitchInfo = new HopePitchInfo();
		return hopePitchInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return HopePitchFactory.getRemoteInstance();
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