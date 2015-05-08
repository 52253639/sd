/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * ������������ҳǩ�Ľ�������㱨
 */
public class ProgressReportBaseSchTaskPropertiesNewUI extends AbstractProgressReportBaseSchTaskPropertiesNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgressReportBaseSchTaskPropertiesNewUI.class);
    
    public ProgressReportBaseSchTaskPropertiesNewUI() throws Exception
    {
        super();
    }
    
    public boolean isFromExecuteUI() {
		return true;
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		setUITitle("����㱨");
	}

	public void onShow() throws Exception {
		super.onShow();
		kDLabelContainer1.setEnabled(true);
	}
}