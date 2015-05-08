package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.pm.QualityCheckPointInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointFactory;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.Uuid;

/**
 * 质量检查点 编辑界面 
 */
public class ScheduleQualityCheckPointEditUI extends AbstractScheduleQualityCheckPointEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ScheduleQualityCheckPointEditUI.class);

	public ScheduleQualityCheckPointEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
		FDCScheduleTaskInfo info = new FDCScheduleTaskInfo();
		info.setId(BOSUuid.read(getTaskInfo().getSrcID()));
		this.editData.setRelateTask(info);
	}

	public FDCScheduleTaskInfo getTaskInfo(){
		FDCScheduleTaskInfo scheduleTaskInfo =null;
		if (getUIContext().get("Owner") instanceof ScheduleTaskProgressReportEditUI) {
			ScheduleTaskProgressReportEditUI  reportEditUI=(ScheduleTaskProgressReportEditUI) getUIContext().get("Owner");
			scheduleTaskInfo=reportEditUI.scheduleTaskInfo;
		} else if (getUIContext().get("Owner") instanceof FDCScheduleTaskPropertiesNewUI) {
			FDCScheduleTaskPropertiesNewUI parentUI = (FDCScheduleTaskPropertiesNewUI) getUIContext().get("Owner");
			scheduleTaskInfo = parentUI.getCurrentTask();
		}
		return scheduleTaskInfo;
	}
	
	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		initQualityBtn();
		controlButtonState();
	}

	/**
	 * @discription  <如果是以主(专)项计划编制与调整进入，设置为不能进行编辑操作>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/09/27> <p>
	 */
	public void controlButtonState(){
		Object obj = this.getUIContext().get("enableState");
		if(null != obj && "enableState".equals(obj.toString().trim())){
			this.btnEdit.setEnabled(false);
		}
	}
	
	/**
	 * 初始化数据，按钮状态
	 * 
	 * @description
	 * @createDate 2011-8-24 void
	 * @version EAS7.0
	 * @see
	 */
	private void initQualityBtn() {
		this.btnAudit.setVisible(false);
		this.txtCheckDemo.setEnabled(false);
		prmtCheckPoint.setQueryInfo("com.kingdee.eas.fdc.pm.app.QualityCheckPointQuery");
		prmtCheckPoint.setDisplayFormat("$name$");
		prmtCheckPoint.setEditable(false);
		prmtCheckPost.setQueryInfo("com.kingdee.eas.fdc.pm.app.SpecialPostQuery");
		prmtCheckPost.setDisplayFormat("$name$");
		prmtCheckPost.setEditable(false);
		prmtCreate.setQueryInfo("com.kingdee.eas.base.forewarn.UserQuery");
		prmtCreate.setDisplayFormat("$name$");
		prmtCreate.setEditable(false);
		prmtCreate.setEnabled(false);
		txtSubDate.setEditable(false);
		txtSubDate.setEnabled(false);
		btnAttachment.setVisible(false);
		btnAuditResult.setVisible(false);
		if (getOprtState().equals(OprtState.ADDNEW)) {
			this.txtSubDate.setValue(new Date());
		}
        
		txtCheckPercent.setMaximumValue(new Integer("100"));
		txtCheckPercent.setPrecision(0);
		txtScore.setPrecision(0);
		
		
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */

	protected void verifyInputForSave() throws Exception {
	}


	/**
	 * 根据检查质量带出检查标准
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-24
	 * @version EAS7.0
	 * @see
	 */
	public void prmtCheckPoint_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
		if (prmtCheckPoint.getValue() != null) {
			QualityCheckPointInfo info = (QualityCheckPointInfo) prmtCheckPoint.getValue();
			txtCheckDemo.setText(info.getCheckCriterion());
		}
	}

	/**
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-24 void
	 * @version EAS7.0
	 * @see
	 */
	private boolean check() {
		if (prmtCheckPoint.getValue() == null) {
			FDCMsgBox.showWarning(this, "质量检查项不能为空！");
			return false;
		} else if (prmtCheckPost.getValue() == null) {
			FDCMsgBox.showWarning(this, "检查岗位不能为空！");
			return false;
		} else if (txtCheckResult.getText().length() > 500) {
			FDCMsgBox.showWarning(this, "检查结果不能大于500字！");
			return false;
		} else {
			return true;
		}
	}

	public void txtScore_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
		// write your code here
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (check() == true) {
			this.editData.setName(Uuid.randomUUID().toString());
			super.actionSave_actionPerformed(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		initQualityBtn();
	}
		
	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */

	protected void attachListeners() {
	}

	protected IObjectValue createNewData() {
		ScheduleQualityCheckPointInfo info = new ScheduleQualityCheckPointInfo();
		info.setNumber(Uuid.randomUUID().toString());
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return info;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */

	protected void detachListeners() {

	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */

	protected ICoreBase getBizInterface() throws Exception {
		return ScheduleQualityCheckPointFactory.getRemoteInstance();
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */

	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */

	protected KDTextField getNumberCtrl() {
		return null;
	}

	public boolean destroyWindow() {
		if(getUIContext().get("Owner") instanceof ScheduleTaskProgressReportEditUI){
			ScheduleTaskProgressReportEditUI ui = (ScheduleTaskProgressReportEditUI) getUIContext().get("Owner");
			if (ui != null) {
				ui.addQualityRow(editData);
			}
		} else if (getUIContext().get("Owner") instanceof FDCScheduleTaskPropertiesNewUI) {
			FDCScheduleTaskPropertiesNewUI parentUI = (FDCScheduleTaskPropertiesNewUI) getUIContext().get("Owner");
			parentUI.refreshQualityCheckPointTableData(editData);
		}
		return super.destroyWindow();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("creator.id");
		sic.add("creator.person.id");
		sic.add("creator.person.name");
		sic.add("creator.person.number");
		return sic;
	}
}