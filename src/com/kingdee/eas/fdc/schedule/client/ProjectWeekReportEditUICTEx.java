package com.kingdee.eas.fdc.schedule.client;

public class ProjectWeekReportEditUICTEx extends ProjectWeekReportEditUI{

	public ProjectWeekReportEditUICTEx() throws Exception {
		super();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAttachment.setEnabled(true);
		this.btnAttachment.setEnabled(true);
	}

}
