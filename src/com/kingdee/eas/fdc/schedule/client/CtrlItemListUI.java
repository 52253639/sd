/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.CtrlItemFactory;
import com.kingdee.eas.fdc.schedule.CtrlItemInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class CtrlItemListUI extends AbstractCtrlItemListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CtrlItemListUI.class);
    
    /**
     * output class constructor
     */
    public CtrlItemListUI() throws Exception
    {
        super();
    }

    protected FDCDataBaseInfo getBaseDataInfo() {
		return new CtrlItemInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CtrlItemFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CtrlItemEditUI.class.getName();
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
	}
}