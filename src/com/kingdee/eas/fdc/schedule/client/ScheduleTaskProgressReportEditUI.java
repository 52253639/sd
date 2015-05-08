/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.forewarn.ForewarnRunTimeFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.schedule.AchievementManagerCollection;
import com.kingdee.eas.fdc.schedule.AchievementManagerFactory;
import com.kingdee.eas.fdc.schedule.AchievementManagerInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport;
import com.kingdee.eas.fdc.schedule.ProjectImageCollection;
import com.kingdee.eas.fdc.schedule.ProjectImageFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageInfo;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointCollection;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointFactory;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.StringUtils;

/**
 * 进度汇报 编辑界面
 */
public class ScheduleTaskProgressReportEditUI extends AbstractScheduleTaskProgressReportEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ScheduleTaskProgressReportEditUI.class);

	/** 按钮 **/
	KDWorkButton btnAddForImg = null;
	KDWorkButton btnDelForImg = null;
	private BigDecimal oldPercent =FDCHelper.ZERO;
	private BigDecimal oldAmount = FDCHelper.ZERO;
	private boolean isNeedResize = false;
	
	private Set projetImageBillId = new HashSet();
	private Set achievementBillId = new HashSet();
	private Set checkPointBillId = new HashSet();

	public ScheduleTaskProgressReportEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
		editData.setDescription(txtDescription.getText().trim());
		editData.setReportor(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		//由于要实现删除功能，所以必须要计算出本次完工工程量，现在直接用实体上的amount属性来保存本次完工工程量<br/>
		//计算方法 :如果是第一次填报，则本次完完工百分比为本次填报数，否则为本次填报数减去上次填报数
		if(oldPercent != null){
			editData.setAmount(FDCHelper.subtract(editData.getCompletePrecent(), oldPercent));
		}else{
			editData.setAmount(editData.getCompletePrecent());
		}
		
		if (!projetImageBillId.isEmpty()) {
			editData.put("projectImageBillId", projetImageBillId);
		}
		if (!achievementBillId.isEmpty()) {
			editData.put("achievementBillId", achievementBillId);
		}
		if (!checkPointBillId.isEmpty()) {
			editData.put("checkPointBillId", checkPointBillId);
		}
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// verifyInput(e);
		// 实际开始日期不能为空
		if (FDCHelper.isEmpty(pkRealStartDate.getText())) {
			FDCMsgBox.showInfo("实际开始日期不能为空");
			abort();
		}
		String s = txtCompletePrecent.getText();
		if (FDCHelper.isEmpty(s)) {
			FDCMsgBox.showInfo("完成进度不能为空");
			abort();
		}
		String regex = "([0]|([1-9][0-9])|([1][0]{2}))|[0-9]";
		if (!s.matches(regex)) {
			FDCMsgBox.showInfo("完成进度只能输入0-100的整数");
			abort();
		}
		BigDecimal currPercent = FDCHelper.toBigDecimal(txtCompletePrecent.getValue(BigDecimal.class));
		if (oldPercent != null && currPercent.compareTo(oldPercent) < 0) {
			FDCMsgBox.showError("当前任务工程量目前已完工" + oldPercent + "%,本次进度不能低于上次汇报数！");
			abort();
		}
		//		BigDecimal currCompleteAmt = FDCHelper.toBigDecimal(txtCompleteAmount.getValue(BigDecimal.class));
		//		if(oldAmount != null && currCompleteAmt.compareTo(oldAmount)<0){
		//			FDCMsgBox.showError("当前任务工程量目前已完工"+oldAmount+"元,本次进度不能低于上次汇报数！");
		//			abort();
		//		}

		if (kDRadioButton2.isSelected() && currPercent.compareTo(FDCHelper.ONE_HUNDRED) == 0) {
			FDCMsgBox.showError("完成进度为100%，是否完成必须为是!");
			abort();
		}
		
		if (kDRadioButton1.isSelected()) {
			editData.setIsComplete(true);
		}
		if (kDRadioButton2.isSelected()) {
			editData.setIsComplete(false);
		}
		FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) getUIContext().get("taskinfo");
		if (info != null && info.getId() != null) {
			editData.setRelateTask(info);
		}
		if (!FDCHelper.isEmpty(prmtReportor.getValue())) {
			// com.kingdee.eas.basedata.person.app.PersonQuery
			editData.setReportor(((UserInfo) prmtReportor.getValue()).getPerson());
		} else {
			editData.setReportor(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		}
		info.setId(info.getId());
		BigDecimal completePercent = new BigDecimal(txtCompletePrecent.getText().toString());
		// 完成量
		info.setComplete(completePercent);
		info.setWorkLoad(StringUtils.isEmpty(txtCompleteAmount.getText())?FDCHelper.ZERO:FDCHelper.toBigDecimal(txtCompleteAmount.getText()));
		// 计划完成日期
		if (kDRadioButton2.isSelected()) {
			if (!FDCHelper.isEmpty(pkPlanEndDate.getValue())) {
				info.setEnd((Date) pkPlanEndDate.getValue());
			}
		}
		
		// 实际完成日期
		if (!FDCHelper.isEmpty(pkRealEndDate.getValue())) {
			info.setActualEndDate((Date) pkRealEndDate.getValue());
		}
		// 实际开始日期
		if (!FDCHelper.isEmpty(pkRealStartDate.getValue())) {
			info.setActualStartDate((Date) pkRealStartDate.getValue());
		}
		
		super.actionSave_actionPerformed(e);
		//由于BOS预警actionSave不能触发，直接手动调用
		ForewarnRunTimeFactory.getRemoteInstance().execRightnowForewarn(this.getClass().getName(), "ActionSave", editData.getId().toString());
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		txtName.setEditable(false);
		txtNumber.setEditable(false);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ScheduleTaskProgressReportFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kDTable1;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		ScheduleTaskProgressReportInfo info = new ScheduleTaskProgressReportInfo();
		try {
			info.setReportDate(SysUtil.getAppServerTime(null));
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		pkReportDate.setEditable(false);
		return info;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		// 阶段性成果面板加入button
		initachievementbtn();
		// 屏蔽与设置按钮
		setEnabledFalse();
		// 初始化F7
		initF7();

		// 判断是否为完成
		isComplete();
		
		// 填充任务和number和计划完成日期
		fillTaskNameandNumber();
		// 查看进来的时候初始化几个表格的数据
		if (getOprtState().equals(OprtState.VIEW)) {
			initTable();
			initProject();
			initQualityTable();
		}
		/*****
		 * 设置表格不能编辑 add by lijianbo
		 */
		setTblQualityMainLock();
		kDProject.checkParsed();
		txtCompletePrecent.setRemoveingZeroInDispaly(true);
		txtCompletePrecent.setPrecision(0);
		// 汇报日期不能编辑
		pkReportDate.setEditable(false);
		pkReportDate.setEnabled(false);
		// 计划完成日期
		pkPlanEndDate.setEditable(false);
		pkPlanEndDate.setEnabled(false);
		
		kDTabbedPane1.removeAll();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", ((FDCScheduleTaskInfo) getUIContext().get("taskinfo")).getId().toString()));
		view.setFilter(filter);
		ScheduleTaskBizTypeCollection cols = ScheduleTaskBizTypeFactory.getRemoteInstance().getScheduleTaskBizTypeCollection(view);
		if(cols.size()==0){
			isNeedResize = true;
		}else{
			addPanel(cols);
		}
		
		tblQualityMain.getStyleAttributes().setLocked(true);
		kDTable1.getStyleAttributes().setLocked(true);
		kDProject.getStyleAttributes().setLocked(true);
		
	}

	private void addPanel(ScheduleTaskBizTypeCollection cols) {
		ScheduleTaskBizTypeInfo bizType = null;
		int panelCount = 0;
		for(Iterator it = cols.iterator();it.hasNext();){
			bizType = (ScheduleTaskBizTypeInfo) it.next();
			if(bizType.getBizType().getId().toString().equalsIgnoreCase("Rz+dS7ECSfqM4kEJqGawYWLF6cA=")){
				 kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
				 panelCount++;
			}else if(bizType.getBizType().getId().toString().equalsIgnoreCase("8PR0EhHFTaiDGdtCmQ19SGLF6cA=")){
				 kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
				 panelCount++;
			}else if(bizType.getBizType().getId().toString().equalsIgnoreCase("JM2iYBxoReukgy90hZJbW2LF6cA=")){
				 kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
				 panelCount++;
			}
		}
		if(panelCount==0){
			isNeedResize = true;
		}
	}
	
	protected void txtCompletePrecent_keyReleased(KeyEvent e) throws Exception {
		super.txtCompletePrecent_keyReleased(e);
		if (txtCompletePrecent.getText().equals(new String("100"))) {
			kDRadioButton1.setSelected(true);
		} else {
			kDRadioButton2.setSelected(true);
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection ss = super.getSelectors();
		ss.add(new SelectorItemInfo("isComplete"));
		ss.add(new SelectorItemInfo("relateTask.*"));
		ss.add(new SelectorItemInfo("relateTask.adminPerson.id"));
		ss.add(new SelectorItemInfo("relateTask.adminPerson.name"));
		ss.add(new SelectorItemInfo("relateTask.adminPerson.number"));
		return ss;
	}

	private void isComplete() {
		FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) getUIContext().get("taskinfo");
		if (info != null) {
			if (!FDCHelper.isEmpty(info.getComplete())) {
				if (info.getComplete().compareTo(new BigDecimal(100)) == 0) {
					kDRadioButton1.setSelected(true);
					kDRadioButton2.setSelected(false);
					contIntendEndDate.setVisible(false);
					contRealEndDate.setVisible(true);
				} else {
					kDRadioButton1.setSelected(false);
					kDRadioButton2.setSelected(true);
					contIntendEndDate.setVisible(true);
					contRealEndDate.setVisible(false);
				}
			} else {
				kDRadioButton2.setSelected(true);
			}
		} else {
			kDRadioButton2.setSelected(true);
		}
	}
	
	protected void kDRadioButton1_stateChanged(ChangeEvent e) throws Exception {
		super.kDRadioButton1_stateChanged(e);
		if (kDRadioButton1.isSelected()) {
			contIntendEndDate.setVisible(false);
			contRealEndDate.setVisible(true);
			txtCompletePrecent.setValue(new BigDecimal("100"));
			try {
				pkRealEndDate.setValue(SysUtil.getAppServerTime(null));
				pkRealEndDate.setEditable(false);
			} catch (EASBizException e1) {
				e1.printStackTrace();
			}

		}
	}

	protected void kDRadioButton2_stateChanged(ChangeEvent e) throws Exception {
		super.kDRadioButton2_stateChanged(e);
		if (kDRadioButton2.isSelected()) {
			contIntendEndDate.setVisible(true);
			contRealEndDate.setVisible(false);
			// pkIntendEndDate.setValue(editData.getPlanEndDate());
			pkIntendEndDate.setValue(pkPlanEndDate.getValue());

		}
	}
	
	
	protected void kDRadioButton1_mouseClicked(MouseEvent e) throws Exception {
		super.kDRadioButton1_mouseClicked(e);
		if (kDRadioButton1.isSelected()) {
			contIntendEndDate.setVisible(false);
			contRealEndDate.setVisible(true);
			txtCompletePrecent.setValue(new BigDecimal("100"));
			try {
				pkRealEndDate.setValue(SysUtil.getAppServerTime(null));
				pkRealEndDate.setEditable(false);
			} catch (EASBizException e1) {
				e1.printStackTrace();
			}

		}
	}

	protected void kDRadioButton2_mouseClicked(MouseEvent e) throws Exception {
		super.kDRadioButton2_mouseClicked(e);
		if (kDRadioButton2.isSelected()) {
			contIntendEndDate.setVisible(true);
			contRealEndDate.setVisible(false);
			// pkIntendEndDate.setValue(editData.getPlanEndDate());
			pkIntendEndDate.setValue(pkPlanEndDate.getValue());

		}
	}

	/**
	 * 屏蔽
	 * 
	 * @description
	 * @author 李兵
	 * @createDate 2011-8-24 void
	 * @version EAS7.0
	 * @see
	 */
	private void setEnabledFalse() {
		btnEdit.setEnabled(true);
	}

	public void onShow() throws Exception {
		super.onShow();
		btnAttachment.setVisible(false);
		btnAuditResult.setVisible(false);
		btnAudit.setVisible(false);
		controlButtonState();
		
		this.remove(btnAdd);
		this.remove(btnDel);
		this.remove(kDButton1);
		this.remove(kDButton2);
		
		if(isNeedResize){
			this.kdsDescCont.setSize(kdsDescCont.getWidth(),(int) (kdsDescCont.getHeight()*1.5));
			remove(kDTabbedPane1);
			this.getParent().getParent().getParent().getParent().setSize(830,100);
		   Container c = this.getParent();
			while(c.getParent() != null){
			    if(c.getParent() instanceof UIModelDialog){
			    	c.getParent().setSize(c.getParent().getWidth()+10,this.getHeight()-200);
			    	break;
			    }else{
			    	c = c.getParent();
			    }
			    
			} 
		}		
	}

	/**
	 * @discription  <以主（专）项计划编制与调整的方式进入，现在将其状态设置为不能编辑>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/06/27> <p>
	 */
	public void controlButtonState(){
		Object obj = this.getUIContext().get("enableState");
		if(null == obj){
			return ;
		}
		if("enableState".equals(obj.toString().trim())){
			this.btnEdit.setEnabled(false);
			//设置形象进度按扭灰显
			this.btnAddForImg.setEnabled(false);
			this.btnDelForImg.setEnabled(false);
			//设置质量检查点按扭灰显
			this.btnAdd.setEnabled(false);
			this.btnDel.setEnabled(false);
			//设置阶段性成果按扭灰显
			kDButton1.setEnabled(false);
			kDButton2.setEnabled(false);
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
	private void setTblQualityMainLock() {
		this.tblQualityMain.getStyleAttributes().setLocked(true);
	}

	private void fillTaskNameandNumber() {
		FDCScheduleTaskInfo info2 = null;
		if (OprtState.ADDNEW.equals(getOprtState())) {
			info2 = (FDCScheduleTaskInfo) getUIContext().get("taskinfo");
		} else {
			info2 = editData.getRelateTask();
		}
		// 加载任务信息
		if (info2 != null) {
			// 任务名称
			loadCommonInfo(info2);

			loadOtherInfo(info2, getOprtState());
			
			oldPercent = info2.getComplete();
			oldAmount = info2.getWorkLoad();
		}
	}

	private void loadOtherInfo(FDCScheduleTaskInfo info2, String oprtState) {

		if (oprtState.equals(OprtState.ADDNEW)) {
			pkIntendEndDate.setValue(info2.getIntendEndDate() == null ? info2.getEnd() : info2.getIntendEndDate());
			txtCompletePrecent.setValue(info2.getComplete());
			editData.setCompletePrecent(info2.getComplete());
			pkRealStartDate.setValue(info2.getActualStartDate() == null ? info2.getStart() : info2.getActualStartDate());
			pkRealEndDate.setValue(info2.getActualEndDate());
			txtSrcTaskID.setText(info2.getSrcID().toString());

		} else {
			txtCompletePrecent.setValue(editData.getCompletePrecent());
			pkIntendEndDate.setValue(editData.getIntendEndDate());
			pkRealStartDate.setValue(editData.getRealStartDate());
			pkRealEndDate.setValue(editData.getRealEndDate());
			

		}
		if (editData.getCompletePrecent().compareTo(FDCHelper.ONE_HUNDRED) < 0) {
			this.contIntendEndDate.setVisible(true);
			this.contRealEndDate.setVisible(false);
		} else {
			this.contIntendEndDate.setVisible(false);
			this.contRealEndDate.setVisible(true);

		}
		
		// // 预计完成日期和实际完成日期需要做特殊处理
		// pkIntendEndDate.setValue(pkPlanEndDate.getValue());
		// // 最新一次进度汇报的进度完成率
		// if (editData.getCompletePrecent() != null) {
		// txtCompletePrecent.setValue(editData.getCompletePrecent());
		// } else if (info2.getComplete() != null) {
		// txtCompletePrecent.setValue(info2.getComplete());
		// editData.setCompletePrecent(info2.getComplete());
		// }
		// // if (info2.getWorkLoad() != null) {
		// // txtCompleteAmount.setValue(info2.getWorkLoad());
		// // }
		// // 实际开始日期
		// if (info2.getActualStartDate() != null) {
		// pkRealStartDate.setValue(info2.getActualStartDate());
		// }
		// // 实际完成日期
		// if (info2.getActualEndDate() != null) {
		// pkRealEndDate.setValue(info2.getActualEndDate());
		// }
		// // 任务Id设置到那个任务框中去
		// txtSrcTaskID.setText(info2.getSrcID().toString());
		// if(info2.getPlanStart() != null){
		// kdPlanStart.setValue(info2.getPlanStart());
		// }
		//
		// if (editData.getIntendEndDate() != null) {
		// this.pkIntendEndDate.setValue(editData.getIntendEndDate());
		// } else if (info2.getIntendEndDate() != null &&
		// editData.getCompletePrecent() != null
		// && editData.getCompletePrecent().compareTo(FDCHelper.ONE_HUNDRED) <
		// 0) {
		// this.contIntendEndDate.setVisible(true);
		// this.contRealEndDate.setVisible(false);
		// this.pkIntendEndDate.setValue(info2.getIntendEndDate());
		// }
	}

	private void loadCommonInfo(FDCScheduleTaskInfo info2) {
		txtName.setText(info2.getName());
		txtName.setEditable(false);
		// 任务编码
		txtNumber.setText(info2.getLongNumber().toString().replace("!", "."));
		txtNumber.setEditable(false);
		kdPlanStart.setValue(info2.getStart());
		// 计划完成日期
		pkPlanEndDate.setValue(info2.getEnd());
		pkPlanEndDate.setEditable(false);

		if (info2.getAdminPerson() != null) {
			prmtAdminPerson.setData(info2.getAdminPerson());
		}
		
		txtSrcTaskID.setText(info2.getSrcID().toString());
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		
		super.verifyInput(e);
		// 实际开始日期不能为空
		if (FDCHelper.isEmpty(pkRealStartDate.getText())) {
			FDCMsgBox.showInfo("实际开始日期不能为空");
			abort();
		}
		if (kDRadioButton1.isSelected()) {
			if (!((Date) pkRealStartDate.getValue()).before(FDCDateHelper.addDays(((Date) pkRealEndDate.getValue()), 1))) {
				FDCMsgBox.showInfo("实际开始日期不能晚于实际结束日期");
				abort();
			}
		}
		
		String s = txtCompletePrecent.getText();
		if (FDCHelper.isEmpty(s)) {
			FDCMsgBox.showInfo("完成进度不能为空");
			abort();
		}
		String regex = "([0]|([1-9][0-9])|([1][0]{2}))|[0-9]";
		if (!s.matches(regex)) {
			FDCMsgBox.showInfo("完成进度只能输入0-100的整数");
			abort();
		}
		
		if (!StringUtils.isEmpty(txtDescription.getText()) && txtDescription.getText().trim().length() > 500) {
			FDCMsgBox.showError("完成情况说明字段只允许录入500个汉字！");
			abort();
		}		
	} 
    //阶段性成果管理表格
	private void initTable() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("achievementType.*"));
		sic.add(new SelectorItemInfo("creator.number"));
		sic.add(new SelectorItemInfo("creator.person.name"));
		sic.add(new SelectorItemInfo("creator.person.number"));
		sic.add(new SelectorItemInfo("creator.person.id"));
		view.setSelector(sic);
		FilterInfo finfo = new FilterInfo();
		finfo.getFilterItems().add(new FilterItemInfo("sourcebillid", editData.getId().toString()));
		view.setFilter(finfo);
		AchievementManagerCollection col = AchievementManagerFactory.getRemoteInstance().getAchievementManagerCollection(view);
		for (int i = 0; i < col.size(); i++) {
			AttachmentCollection attCol = FDCAttachmentUtil.getAttachmentsInfo(col.get(i).getId().toString());
			String strName = "";
			for (int m = 0; m < attCol.size(); m++) {
				strName += attCol.get(m).getName().toString() + " ";
			}
			IRow row = kDTable1.addRow();
			AchievementManagerInfo aminfo = new AchievementManagerInfo();
			aminfo = col.get(i);
			row.getCell("state").setValue(aminfo.getState().toString());
			row.getCell("name").setValue(aminfo.getName());
			row.getCell("achievementType").setValue(aminfo.getAchievementType());
			row.getCell("achievementDoc").setValue(strName);
			row.getCell("creator").setValue(FDCScheduleTaskExecuteHelper.getDisplayPersonInfo(aminfo.getCreator()));
			row.getCell("createTime").setValue(aminfo.getCreateTime());
			row.getCell("id").setValue(aminfo.getId());
		}
	}

	private void initF7() {
		prmtReportor.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		prmtReportor.setEditable(false);
		prmtReportor.setEnabled(false);
		prmtReportor.setDisplayFormat("$name$");
		prmtReportor.setValue(SysContext.getSysContext().getCurrentUserInfo());
		
		prmtAdminPerson.setDisplayFormat("$name$");
	}

	private void initachievementbtn() {
		KDWorkButton btnAddNew = new KDWorkButton("新增", EASResource.getIcon("imgTbtn_new"));
		KDWorkButton btnRemove = new KDWorkButton("删除", EASResource.getIcon("imgTbtn_delete"));
		
		KDWorkButton btnAddButtonNew = new KDWorkButton("新增", EASResource.getIcon("imgTbtn_new"));
		KDWorkButton btnRemoveNew = new KDWorkButton("删除", EASResource.getIcon("imgTbtn_delete"));
		kDContainer1.addButton(btnAddNew);
		kDContainer1.addButton(btnRemove);
		kDContainer2.addButton(btnAddButtonNew);
		kDContainer2.addButton(btnRemoveNew);
		kDButton1.setIcon(EASResource.getIcon("imgTbtn_new"));
		kDButton2.setIcon(EASResource.getIcon("imgTbtn_delete"));
		kDButton1.setSize(73, 19);
		kDButton2.setSize(73, 19);
		kDButton1.setEnabled(true);
		kDButton2.setEnabled(true);
		// add by lijianbo
		
		if (getOprtState().equals(OprtState.VIEW)) {
			btnAddNew.setEnabled(false);
			btnAddButtonNew.setEnabled(false);
			btnRemove.setEnabled(false);
			btnRemoveNew.setEnabled(false);
			kDButton1.setEnabled(false);
			kDButton2.setEnabled(false);
		}
		
		
		btnAddButtonNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					actionQualityAddNew_actionPerformed(e);
				} catch (Exception e1) {
					handUIException(e1);
				}
			}

		});
		btnRemoveNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					actionQualityDelete_actionPerformed(e);
				} catch (Exception e1) {
					handUIException(e1);
				}
			}
		});
		
		btnAddNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					achievementAddNew_actionPerformed(e);
				} catch (Exception e1) {
					handUIException(e1);
				}
			}

		});

		btnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					achievementDel_actionPerformed(e);
				} catch (Exception e1) {
					handUIException(e1);
				}
			}
		});
		
		btnAdd.setEnabled(true);
		btnDel.setEnabled(true);
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */
	private void initQualityTable() throws Exception {

		IScheduleTaskProgressReport ir = ScheduleTaskProgressReportFactory.getRemoteInstance();
		if (editData == null) {
			return;
		} else {
		ScheduleTaskProgressReportInfo info = ir.getScheduleTaskProgressReportInfo(new ObjectUuidPK(editData.getId().toString()));
		String taskID = null;
		if (info.getRelateTask() != null) {
			taskID = info.getRelateTask().getId().toString();
		}
		
		try {
			
			 EntityViewInfo view = new EntityViewInfo();
			 SelectorItemCollection sic = new SelectorItemCollection();
			 sic.add(new SelectorItemInfo("*"));
			 sic.add(new SelectorItemInfo("checkPoint.*"));
			 sic.add(new SelectorItemInfo("creator.number"));
			 sic.add(new SelectorItemInfo("creator.id"));
			 sic.add(new SelectorItemInfo("creator.person.id"));
			 sic.add(new SelectorItemInfo("creator.person.name"));
			 sic.add(new SelectorItemInfo("creator.person.number"));
			 sic.add(new SelectorItemInfo("checkPost.*"));
			 view.setSelector(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sourcebillid", editData.getId()));
			view.setFilter(filter);
			ScheduleQualityCheckPointCollection col = ScheduleQualityCheckPointFactory.getRemoteInstance().getScheduleQualityCheckPointCollection(
					view);
			tblQualityMain.removeRows();
			tblQualityMain.checkParsed();
			for (int i = 0; i < col.size(); i++) {

				IRow row = tblQualityMain.addRow();
				ScheduleQualityCheckPointInfo qualityInfo = new ScheduleQualityCheckPointInfo();
				qualityInfo = col.get(i);

				// 质量检查项checkPoint
				if (null != row.getCell("checkPoint") && null != row.getCell("checkPost") && null != row.getCell("checkPercent")
						&& null != row.getCell("score") && null != row.getCell("checkResult") && null != row.getCell("subPerson")
						&& null != row.getCell("subDate")) {
					row.getCell("checkPoint").setValue(qualityInfo.getCheckPoint().toString());
					// 质量检查点
					row.getCell("checkPost").setValue(qualityInfo.getCheckPost().toString());
					// 检查比列
					row.getCell("checkPercent").setValue(qualityInfo.getCheckPercent());
					// 分值
					row.getCell("score").setValue(qualityInfo.getScore());
					// 检查结果
					row.getCell("checkResult").setValue(qualityInfo.getCheckResult());
					// 提交人
					row.getCell("subPerson").setValue(FDCScheduleTaskExecuteHelper.getDisplayPersonInfo(qualityInfo.getCreator()));
					// 提交日期
					row.getCell("subDate").setValue(new SimpleDateFormat("yyyy-MM-dd").format(qualityInfo.getCreateTime()));
					row.getCell("id").setValue(qualityInfo.getId());
				}
			}

		} catch (BOSException e) {
			e.printStackTrace();
		}
		}
	}

	/**
	 * 新建“质量检查” 
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */

	public void actionQualityAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionQualityAddNew_actionPerformed(e);
		tblQualityMain.checkParsed();
		this.scheduleTaskInfo = getTaskInfo();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ScheduleQualityCheckPointEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-24
	 * @version EAS7.0
	 * @see
	 */

	protected void tblQualityMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblQualityMain_tableClicked(e);

		if (e.getClickCount() == 2) {
			int i = tblQualityMain.getSelectManager().getActiveRowIndex();
			if (i < 0) {
				FDCMsgBox.showWarning("请先选择一行进行查看");
				SysUtil.abort();
			}
			IRow row = tblQualityMain.getRow(i);
			String projectId = row.getCell("id").getValue().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, projectId);
			Object obj = this.getUIContext().get("enableState");
			if(null != obj && "enableState".equals(obj.toString().trim())){
				uiContext.put("enableState", "enableState");
			}
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ScheduleQualityCheckPointEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */
	public void addQualityRow(ScheduleQualityCheckPointInfo qualityInfo) {
		if (qualityInfo != null && qualityInfo.getId() != null) {
			this.checkPointBillId.add(qualityInfo.getId().toString());
		}
		int n = tblQualityMain.getRowCount();
		if (qualityInfo.getId() != null) {
			
			for (int i = 0; i < n; i++) {
				if (qualityInfo.getId().toString().trim().equals(tblQualityMain.getRow(i).getCell("id").getValue().toString().trim())) {
					IRow row = tblQualityMain.getRow(i);
					// 质量检查项
					row.getCell("checkPoint").setValue(qualityInfo.getCheckPoint());
					// 质量检查点
					row.getCell("checkPost").setValue(qualityInfo.getCheckPost());
					// 检查比列
					row.getCell("checkPercent").setValue(qualityInfo.getCheckPercent());
					// 分值
					row.getCell("score").setValue(qualityInfo.getScore());
					// 检查结果
					row.getCell("checkResult").setValue(qualityInfo.getCheckResult());
					// 提交人
					row.getCell("subPerson").setValue(qualityInfo.getCreator().getPerson().getName());
					// 提交日期
					row.getCell("subDate").setValue(new SimpleDateFormat("yyyy-MM-dd").format(qualityInfo.getCreateTime()));
					row.getCell("id").setValue(qualityInfo.getId());
					return;
				}
			}
			IRow row = tblQualityMain.addRow();
			// 质量检查项
			row.getCell("checkPoint").setValue(qualityInfo.getCheckPoint());
			// 质量检查点
			row.getCell("checkPost").setValue(qualityInfo.getCheckPost());
			// 检查比列
			row.getCell("checkPercent").setValue(qualityInfo.getCheckPercent());
			// 分值
			row.getCell("score").setValue(qualityInfo.getScore());
			// 检查结果
			row.getCell("checkResult").setValue(qualityInfo.getCheckResult());
			// 提交人（如果用户没有绑定职员就显示用户名，如果绑定就显示职员名）
			row.getCell("subPerson").setValue(qualityInfo.getCreator().getPerson().getName());
			  
			// 提交日期
			row.getCell("subDate").setValue(new SimpleDateFormat("yyyy-MM-dd").format(qualityInfo.getCreateTime()));
			row.getCell("id").setValue(qualityInfo.getId());
		}
	}

	/**
	 * 新增 update by libing at 2011-09-15 新增阶段性成果可以不保存单据
	 */
	public void achievementAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) getUIContext().get("taskinfo");
		if (info != null && info.getAchievementType() == null) {
			FDCMsgBox.showError("当前任务没有设置成果类型，不能进行新增阶段性成果，请先指定任务的成果类型后，再进行填报！");
			abort();
		}
		// super.achievementAddNew_actionPerformed(e);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext ctx = new UIContext(this);
		ctx.put("taskinfo", getUIContext().get("taskinfo"));
		ctx.put("isFromSchReport", Boolean.TRUE);
		info.getId();
		IUIWindow uiwindow = null;
		uiwindow = uiFactory.create(AchievementManagerEditUI.class.getName(), ctx, null, OprtState.ADDNEW);
		uiwindow.show();

	}

	/**
	 * 查看
	 */
	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
		super.kDTable1_tableClicked(e);
		if (e.getClickCount() == 2) {
			int m = kDTable1.getSelectManager().getActiveRowIndex();
			if (m > -1) {
				String id = kDTable1.getRow(m).getCell("id").getValue().toString();
				IUIFactory uiFactory = null;
				uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
				UIContext uicontext = new UIContext(this);
				FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) getUIContext().get("taskinfo");
				uicontext.put("taskinfo", info);
				uicontext.put(UIContext.ID, id);
				Object obj = this.getUIContext().get("enableState");
				if(null != obj && "enableState".equals(obj.toString().trim())){
					uicontext.put("enableState", "enableState");
				}
				IUIWindow uiwindow = uiFactory.create(AchievementManagerEditUI.class.getName(), uicontext, null, OprtState.VIEW);
				uiwindow.show();
			}
		}

	}

	public void addAchieveRow(AchievementManagerInfo info, KDTable kdtable) {
		if (info != null && info.getId() != null) {
			this.achievementBillId.add(info.getId().toString());
		}
		int m = kdtable.getRowCount();
		String strName = "";
		for (int i = 0; i < m; i++) {
			String name = kdtable.getCell(i, "attName").getValue().toString();
			if(i<m-1)
			  strName += name + ";";
		}

		if (info.getId() != null) {
			int n = kDTable1.getRowCount();
			for (int i = 0; i < n; i++) {
				if (kDTable1.getRow(i).getCell("id").getValue()!= null && info.getId().toString().trim().equals(kDTable1.getRow(i).getCell("id").getValue().toString().trim())) {
					kDTable1.getRow(i).getCell("state").setValue(info.getState().toString());
					kDTable1.getRow(i).getCell("name").setValue(info.getName());
					kDTable1.getRow(i).getCell("achievementType").setValue(info.getAchievementType());
					kDTable1.getRow(i).getCell("achievementDoc").setValue(strName);
					kDTable1.getRow(i).getCell("creator").setValue(FDCScheduleTaskExecuteHelper.getDisplayPersonInfo(info.getCreator()));
					kDTable1.getRow(i).getCell("createTime").setValue(info.getCreateTime());
					kDTable1.getRow(i).getCell("id").setValue(info.getId());
					return;
				}
			}
			IRow row = kDTable1.addRow();
			row.getCell("state").setValue(info.getState().toString());
			row.getCell("name").setValue(info.getName());
			row.getCell("achievementType").setValue(info.getAchievementType());
			row.getCell("achievementDoc").setValue(strName);
			row.getCell("creator").setValue(FDCScheduleTaskExecuteHelper.getDisplayPersonInfo(info.getCreator()));
			row.getCell("createTime").setValue(info.getCreateTime());
			row.getCell("id").setValue(info.getId());
		}
	}
	
	public void actionQualityDelete_actionPerformed(ActionEvent e) throws Exception {
		tblQualityMain.checkParsed();
		int m[] = KDTableUtil.getSelectedRows(tblQualityMain);
		if (m.length < 0) {
			FDCMsgBox.showInfo("请选中行");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			SysUtil.abort();
		}
		IObjectPK[] pk = new IObjectPK[m.length];
		for (int i = m.length; i > 0; i--) {
			String id = tblQualityMain.getRow(m[i - 1]).getCell("id").getValue().toString();
			pk[i - 1] = new ObjectUuidPK(id);
			tblQualityMain.removeRow(m[i - 1]);

			// FDCAttachmentUtil.deleteAllAtt(null, strID);

		}

		// 多行删除

		ScheduleQualityCheckPointFactory.getRemoteInstance().delete(pk);
	}
	/**
	 * 删除阶段性成果
	 */
	public void achievementDel_actionPerformed(ActionEvent e) throws Exception {
		kDTable1.checkParsed();
		int m[] = KDTableUtil.getSelectedRows(kDTable1);
		if (m.length <= 0) {
			FDCMsgBox.showInfo("请选中行");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			SysUtil.abort();
		}
		IObjectPK[] pk = new IObjectPK[m.length];
		for (int i = m.length; i > 0; i--) {
			String id = kDTable1.getRow(m[i - 1]).getCell("id").getValue().toString();
			pk[i - 1] = new ObjectUuidPK(id);
			kDTable1.removeRow(m[i - 1]);
		}

		// 多行删除
		AchievementManagerFactory.getRemoteInstance().delete(pk);
	}

	public boolean destroyWindow() {
		Object obj = getUIContext().get("Owner");
		if (obj != null && obj instanceof FDCScheduleTaskPropertiesNewUI) {
			FDCScheduleTaskPropertiesNewUI ui = (FDCScheduleTaskPropertiesNewUI) obj;
		 	ui.addScheduleReportTableRow(editData);
		}
		return super.destroyWindow();
	}

	/**
	 * 得到相关任务
	 * @return 在被调用的界面中会用到，如形象进度、质量检查
	 */
	public  FDCScheduleTaskInfo getTaskInfo() throws Exception{
		return (FDCScheduleTaskInfo) getUIContext().get("taskinfo");
	}
	/**
	 * @description 初始化形象进度列表
	 * @author 车忠伟
	 * @createDate 2011-8-22
	 * @version EAS7.0
	 * @throws Exception 
	 * @see
	 */
	public void initProject() throws Exception {
		kDProject.checkParsed();
		if (editData == null) {
			return;
		} else {
		IScheduleTaskProgressReport ir = ScheduleTaskProgressReportFactory.getRemoteInstance();
		ScheduleTaskProgressReportInfo info = ir.getScheduleTaskProgressReportInfo(new ObjectUuidPK(editData.getId().toString()));
		String taskID = null;
		if (info.getRelateTask() != null) {
			taskID = info.getRelateTask().getId().toString();
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("creator.person.id"));
		view.getSelector().add(new SelectorItemInfo("creator.person.name"));
		view.getSelector().add(new SelectorItemInfo("creator.person.number"));
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sourcebillid", editData.getId().toString()));
		view.setFilter(filter);
		ProjectImageCollection collection;
		try {
			collection = ProjectImageFactory.getRemoteInstance().getProjectImageCollection(view);
			if (collection.size() > 0) {
				for (int i = 0; i < collection.size(); i++) {
					ProjectImageInfo projectImageInfo = collection.get(i);
					IRow row = kDProject.addRow();
					if(null == projectImageInfo){
						continue ;
					}
					row.getCell("name").setValue(projectImageInfo.getName());
					row.getCell("creator").setValue(FDCScheduleTaskExecuteHelper.getDisplayPersonInfo(projectImageInfo.getCreator()));
					row.getCell("createTime").setValue(projectImageInfo.getCreateTime());
					row.getCell("imgDescription").setValue(projectImageInfo.getImgDescription());
					row.getCell("id").setValue(projectImageInfo.getId());
					row.getStyleAttributes().setLocked(true);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		}

	}

	protected void initWorkButton() {
		btnAddForImg = ScheduleClientHelper.createButton("btnAdd", ScheduleClientHelper.BUTTON_ADD_NAME, "imgTbtn_new");
		btnDelForImg = ScheduleClientHelper.createButton("btnDel", ScheduleClientHelper.BUTTON_DEL_NAME, "imgTbtn_delete");
		kDContainer3.addButton(btnAddForImg);
		kDContainer3.addButton(btnDelForImg);
		// 为添加按钮增加监听事件
		btnAddForImg.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					openProjectImage();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		// 为删除按钮添加事件
		btnDelForImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteProject();
				} catch (Exception e1) {
				}
			}
		});
		super.initWorkButton();
		this.actionEdit.setVisible(false);
		if (getOprtState().equals(OprtState.VIEW)) {
			btnAddForImg.setEnabled(false);
			btnDelForImg.setEnabled(false);
		}
	}

	/**
	 * 进度任务信息，在被调用的界面中会用到，如形象进度、质量检查
	 */
   public FDCScheduleTaskInfo scheduleTaskInfo;
   
	/**
	 * @description 打开形象进度编辑界面
	 * @author 车忠伟
	 * @createDate 2011-8-22
	 * @version EAS7.0
	 * @see
	 */
	public void openProjectImage() throws Exception{
		this.scheduleTaskInfo = getTaskInfo();
		ScheduleTaskProgressReportEditUI scheduleUI;
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ProjectImageNewEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * @description 添加形象进度数据
	 * @author 车忠伟
	 * @createDate 2011-8-22
	 * @version EAS7.0
	 * @see
	 */
	public void addProjiectEntry(ProjectImageInfo info) {
		if (info != null && info.getId() != null) {
			this.projetImageBillId.add(info.getId().toString());
		}
		kDProject.checkParsed();
		if (info.getId() != null) {
			if (!judgExist(info)) {
				IRow row = kDProject.addRow();
				row.getCell("name").setValue(info.getName());
				row.getCell("creator").setValue(FDCScheduleTaskExecuteHelper.getDisplayPersonInfo(info.getCreator()));
				row.getCell("createTime").setValue(info.getCreateTime());
				row.getCell("imgDescription").setValue(info.getImgDescription());
				row.getCell("id").setValue(info.getId());
			}
		}
	}
	
	/**
	 * @description 判断数据是否存在，存在就修改
	 * @author 车忠伟
	 * @createDate 2011-8-7
	 * @version EAS7.0
	 * @see
	 */
	public boolean judgExist(ProjectImageInfo info) {

		int count = kDProject.getRowCount();
		for (int i = 0; i < count; i++) {
			if (info.getId().toString().trim().equals(kDProject.getRow(i).getCell("id").getValue().toString().trim())) {
				IRow row = kDProject.getRow(i);
				row.getCell("creator").setValue(FDCScheduleTaskExecuteHelper.getDisplayPersonInfo(info.getCreator()));
				row.getCell("name").setValue(info.getName());
				row.getCell("createTime").setValue(info.getCreateTime());
				row.getCell("imgDescription").setValue(info.getImgDescription());
				row.getCell("id").setValue(info.getId());
				return true;
			}
		}
		return false;

	}

	/**
	 * @description 删除形象进度列表的数据，支持多行删除
	 * @author 车忠伟
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */
	public void deleteProject() throws Exception {
		kDProject.checkParsed();
		int[] rows = KDTableUtil.getSelectedRows(kDProject);
		if (rows.length <= 0) {
			FDCMsgBox.showInfo("请选中要删除的行");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			SysUtil.abort();
		}
		// 将选择的行的ID放入到数组中
		IObjectPK[] pk = new IObjectPK[rows.length];
		for (int i = rows.length; i > 0; i--) {
			String strID = kDProject.getRow(rows[i - 1]).getCell("id").getValue().toString();
			pk[i - 1] = new ObjectUuidPK(strID);
			kDProject.removeRow(rows[i - 1]);

		}
		// 删除
		ProjectImageFactory.getRemoteInstance().delete(pk);
	}

	/**
	 * @description 查看数据
	 * @author 车忠伟
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */
	protected void kDProject_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int i = kDProject.getSelectManager().getActiveRowIndex();
			if (i < 0) {
				FDCMsgBox.showWarning("请先选择一行进行查看");
				SysUtil.abort();
			}
			IRow row = kDProject.getRow(i);
			String projectId = row.getCell("id").getValue().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, projectId);
			Object obj = this.getUIContext().get("enableState");
			if(null != obj && "enableState".equals(obj.toString().trim())){
				uiContext.put("enableState", "enableState");
			}
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ProjectImageNewEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}
	
	protected String getClassAlise() {
		return "任务进度汇报";
	}
}