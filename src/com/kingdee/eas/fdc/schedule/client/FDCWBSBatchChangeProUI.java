/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class FDCWBSBatchChangeProUI extends AbstractFDCWBSBatchChangeProUI {
	private static final Logger logger = CoreUIObject.getLogger(FDCWBSBatchChangeProUI.class);

	/**
	 * output class constructor
	 */
	public FDCWBSBatchChangeProUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		prmtTaskPro.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7TaskTypeQuery");
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnOK.setEnabled(true);
		btnCancel.setEnabled(true);
	}

	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		Set wbsIDs = new HashSet();
		if (getUIContext().get("wbsIDs") != null) {
			wbsIDs = (Set) getUIContext().get("wbsIDs");
		} else {
			FDCMsgBox.showError("所选项目WBS不适合此操作!");
			SysUtil.abort();
		}
		;
		if (wbsIDs.size() < 1) {
			FDCMsgBox.showError("所选项目WBS不适合此操作!");
			SysUtil.abort();
		}
		;
		String taskProID;
		if (prmtTaskPro.getValue() != null && prmtTaskPro.getValue() instanceof TaskTypeInfo) {
			taskProID = ((TaskTypeInfo) prmtTaskPro.getValue()).getId().toString();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsIDs,CompareType.INCLUDE));
//			if(FDCScheduleTaskFactory.getRemoteInstance().exists(filter)){
//				int result = FDCMsgBox.showConfirm2("将会同步修改进度任务中的相关信息！");
//				if(result == FDCMsgBox.CANCEL){
//					return;
//				}
//			}
			FDCWBSFactory.getRemoteInstance().batChangeTaskPro(wbsIDs, taskProID);
		}
		this.disposeUIWindow();
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.disposeUIWindow();
	}
}