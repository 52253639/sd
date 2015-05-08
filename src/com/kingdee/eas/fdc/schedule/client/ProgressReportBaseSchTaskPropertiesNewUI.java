/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * 基于任务属性页签的进度任务汇报
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
		setUITitle("任务汇报");
	}

	public void onShow() throws Exception {
		super.onShow();
		kDLabelContainer1.setEnabled(true);
	}
}