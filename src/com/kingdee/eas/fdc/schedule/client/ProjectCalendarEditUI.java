/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.assistant.client.AssistantClientUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryCollection;
import com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.HolidayEntryCollection;
import com.kingdee.eas.fdc.schedule.framework.HolidayEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ProjectCalendarEditUI extends AbstractProjectCalendarEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectCalendarEditUI.class);
	private ScheduleCalendarInfo calendarInfo = null;

	private Set scheduleIDs;

	public ProjectCalendarEditUI() throws Exception {
		super();
	}

	private void verify() throws EASBizException, BOSException {
		if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)
				&& this.kDRadioButton1.isSelected()) {
			FDCMsgBox.showError("默认日历，只能在集团下才能修改！");
			abort();

		}
		
		if (txtName.getText() == null || txtName.getText().trim().length() == 0) {
			FDCMsgBox.showWarning("日历名称不能为空");
			SysUtil.abort();
		}
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() == 0) {
			FDCMsgBox.showWarning("日历编码不能为空");
			SysUtil.abort();
		}
		String num = this.txtNumber.getText();
		boolean flag = num.matches("\\d+");
		// 判断输入的编码
		if (flag == false) {
			FDCMsgBox.showInfo("只能输入数字型编码");
			SysUtil.abort();
		}
		// 长度不能超过3位
		if (num.length() > 3) {
			FDCMsgBox.showInfo("日历编码长度应该小于三位");
			SysUtil.abort();
		}
		if (!monday.isSelected() && !tuesday.isSelected() && !wednesday.isSelected() && !thursday.isSelected() && !friday.isSelected()
				&& !saturday.isSelected() && !sunday.isSelected()) {
			FDCMsgBox.showInfo("请选择工作日");
			abort();
		}
		ScheduleCalendarInfo calendarInfo22 = new ScheduleCalendarInfo();
		if (!FDCHelper.isEmpty(calendarInfo.getId())) {
			calendarInfo22.setId(calendarInfo.getId());
		}
		calendarInfo22.setName(txtName.getText());
		calendarInfo22.setNumber(txtNumber.getText());
		
		// 一位或者两位自动弄成三位
		String numnew = null;
		if (txtNumber.getText().trim().length() == 1) {
			numnew = "00" + txtNumber.getText().trim();
		}
		if (txtNumber.getText().trim().length() == 2) {
			numnew = "0" + txtNumber.getText().trim();
		}
		if (txtNumber.getText().trim().length() == 3) {
			numnew = txtNumber.getText().trim();
		}
		calendarInfo22.setNumber(numnew);
		
		 // 新增进入
		if (FDCHelper.isEmpty(calendarInfo22.getId())) {
			String flag1 = ScheduleCalendarFactory.getRemoteInstance().verifyremote(calendarInfo22);
			if (flag1.equals("1")) {
				FDCMsgBox.showInfo("名称不能重复！");
				abort();
			}
			if (flag1.equals("2")) {
				FDCMsgBox.showInfo("编码不能重复！");
				abort();
			}
		}
		// 修改
		if (!FDCHelper.isEmpty(calendarInfo22.getId())) {
			String flag2 = ScheduleCalendarFactory.getRemoteInstance().verifyremoteupdate(calendarInfo22);
			if (flag2.equals("1")) {
				FDCMsgBox.showInfo("名称不能重复！");
				abort();
			}
			if (flag2.equals("2")) {
				FDCMsgBox.showInfo("编码不能重复！");
				abort();
			}
		}

		// 注释掉 update by libing at 2011-9-15
		// if(prmtProject.getValue() == null
		// || prmtProject.getValue().toString().trim().length() == 0){
		// FDCMsgBox.showWarning("请选择工程项目！");
		// SysUtil.abort();
		// }

//		if (prmtProject.getValue() != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("objectId", ((CurProjectInfo) prmtProject.getValue()).getId().toString(), CompareType.EQUALS));
//			if (calendarInfo != null && calendarInfo.getId() != null) {
//				filter.getFilterItems().add(new FilterItemInfo("id", calendarInfo.getId().toString(), CompareType.NOTEQUALS));
//			}
//			if (ScheduleCalendarFactory.getRemoteInstance().exists(filter)) {
//				FDCMsgBox.showWarning("该工程项目下已有项目日历！");
//				SysUtil.abort();
//			}
//		}

		// add by libing
		// 校验是否被引用
		if (calendarInfo.getId() != null) {
			scheduleIDs = new HashSet();
			Set prjsNames = new HashSet();
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select distinct head.FID as id,prj.FName_l2 as name ");
			sql.appendSql(" from T_SCH_FDCScheduleTask as task ");
			sql.appendSql(" left join T_SCH_FDCSchedule as head ");
			sql.appendSql(" on head.FID = task.FScheduleID ");
			sql.appendSql(" left join T_FDC_CurProject as prj ");
			sql.appendSql(" on prj.FID = head.FProjectID ");
			sql.appendSql(" where task.FCalendarID = ? ");
			sql.addParam(calendarInfo.getId().toString());
			IRowSet rs = sql.executeQuery();
			try {
				StringBuffer sb = new StringBuffer();
				while (rs.next()) {
					// 计划的id装入set
					scheduleIDs.add(rs.getString("id"));
					// 工程的名称装入set
					prjsNames.add(rs.getString("name"));
					// sb.append(rs.getString("name")).append("\n");
				}
				// 如果是项目默认日历，该项目存在计划的话，也要同步更新
				if (prmtProject.getValue() != null && kDRadioButton1.isSelected()) {
					CurProjectInfo prj = (CurProjectInfo) prmtProject.getValue();
					sql.clear();
					sql.appendSql(" select FID from T_SCH_FDCSchedule ");
					sql.appendSql(" where FProjectID = ? ");
					sql.addParam(prj.getId().toString());
					rs = sql.executeQuery();
					while (rs.next()) {
						scheduleIDs.add(rs.getString("FID"));
						prjsNames.add(prj.getName());
					}
				}
				if (prjsNames.size() > 0) {
					// if (isAllWeekend) {
					//FDCMsgBox.showWarning("日历已被引用，改为7天全是周末将造成任务有效工期为0，请重新修改！")
					// ;
					// SysUtil.abort();
					// }
					sb.append("涉及的项目：\n");
					Object[] array = prjsNames.toArray();
					for (int i = 0; i < prjsNames.size(); i++) {
						sb.append(array[i] + "\n");
					}
					int isOK = FDCMsgBox.showConfirm3a(this, "日历已修改，需要重新计算项目任务的有效工期！点击确定重新计算。", sb.toString());
					if (isOK == FDCMsgBox.OK) {
						// 本来重新计算写在这里，考虑到跟提交要放在同一个事务，所以移到controllerBean的提交方法里
						// 并且在提交添加等待进度框
					} else {
						SysUtil.abort();
					}
				}
			} catch (SQLException e) {
				handUIException(e);
			}
		}
	}

	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		
		verify();
		calendarInfo = new ScheduleCalendarInfo();
		if (OprtState.EDIT.equals(getOprtState())) {
			calendarInfo.setId(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
		}
		HolidayEntryCollection holidayEntrys = new HolidayEntryCollection();
		DefaultWeekendEntryCollection weekenEntrys = new DefaultWeekendEntryCollection();
		calendarInfo.setName(txtName.getText());
		// 一位或者两位自动弄成三位
		String numnew = null;
		if (txtNumber.getText().trim().length() == 1) {
			numnew = "00" + txtNumber.getText().trim();
		}
		if (txtNumber.getText().trim().length() == 2) {
			numnew = "0" + txtNumber.getText().trim();
		}
		if (txtNumber.getText().trim().length() == 3) {
			numnew = txtNumber.getText().trim();
		}
		calendarInfo.setNumber(numnew);
		// add by libing at 2011-8-3
		// 设置是否选中
		if (kDRadioButton2.isSelected()) {
			calendarInfo.setIsSys(false);
		} else {
			calendarInfo.setIsSys(true);
		}
		if (prmtProject.getValue() != null) {
			calendarInfo.setObjectId(((CurProjectInfo) prmtProject.getValue()).getId().toString());
			//修改日历报错，由于没有设置对应的成本中心，增加提示信息面非报错 Added By  Owen_wen  2014-10-26
			if(((CurProjectInfo) prmtProject.getValue()).getCostCenter()==null){
				FDCMsgBox.showInfo(this, "当前项目没有设置对应的成本中心，请先设置对应的成本中心。");
				abort();
			}else{
				calendarInfo.setOrgUnit(((CurProjectInfo) prmtProject.getValue()).getCostCenter().castToFullOrgUnitInfo());
			}
		} else {
			calendarInfo.setOrgUnit((FullOrgUnitInfo) SysContext.getSysContext().getCurrentOrgUnit());
		}

		if (!monday.isSelected()) {
			DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
			weekendInfo.setParent(calendarInfo);
			weekendInfo.setWeekend(ScheduleWeekendEnum.Monday);
			weekenEntrys.add(weekendInfo);
		}
		if (!tuesday.isSelected()) {
			DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
			weekendInfo.setParent(calendarInfo);
			weekendInfo.setWeekend(ScheduleWeekendEnum.Tuesday);
			weekenEntrys.add(weekendInfo);
		}
		if (!wednesday.isSelected()) {
			DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
			weekendInfo.setParent(calendarInfo);
			weekendInfo.setWeekend(ScheduleWeekendEnum.Wednesday);
			weekenEntrys.add(weekendInfo);
		}
		if (!thursday.isSelected()) {
			DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
			weekendInfo.setParent(calendarInfo);
			weekendInfo.setWeekend(ScheduleWeekendEnum.Thursday);
			weekenEntrys.add(weekendInfo);
		}
		if (!friday.isSelected()) {
			DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
			weekendInfo.setParent(calendarInfo);
			weekendInfo.setWeekend(ScheduleWeekendEnum.Friday);
			weekenEntrys.add(weekendInfo);
		}
		if (!saturday.isSelected()) {
			DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
			weekendInfo.setParent(calendarInfo);
			weekendInfo.setWeekend(ScheduleWeekendEnum.Saturday);
			weekenEntrys.add(weekendInfo);
		}
		if (!sunday.isSelected()) {
			DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
			weekendInfo.setParent(calendarInfo);
			weekendInfo.setWeekend(ScheduleWeekendEnum.Sunday);
			weekenEntrys.add(weekendInfo);
		}
		// if(noWeekend.isSelected()){
		// DefaultWeekendEntryInfo weekendInfo = new DefaultWeekendEntryInfo();
		// weekendInfo.setParent(calendarInfo);
		// weekendInfo.setWeekend(ScheduleWeekendEnum.None);
		// weekenEntrys.add(weekendInfo);
		// }
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			if (tblMain.getCell(i, "date").getValue() == null) {
				// FDCMsgBox.showWarning("第"+(i+1)+"行的节假日不能为空！");
				// SysUtil.abort();
				continue;
			}
			HolidayEntryInfo holidayInfo = new HolidayEntryInfo();
			holidayInfo.setParent(calendarInfo);
			holidayInfo.setDate((Date) tblMain.getCell(i, "date").getValue());
			// add by libing 2011-8-3
			holidayInfo.setHolidayName((String) tblMain.getCell(i, "holidayName").getValue());
			holidayEntrys.add(holidayInfo);
		}
		calendarInfo.getHolidayEntrys().addCollection(holidayEntrys);
		calendarInfo.getWeekendEntrys().addCollection(weekenEntrys);
		// 注销by libing
		// ScheduleCalendarFactory.getRemoteInstance().submit(calendarInfo);
		// 已经提交后了
		// add by libing
		LongTimeDialog dialog = UITools.getDialog(this);
		dialog.setTitle("重算受影响计划，可能需要几分钟时间，请耐心等待...");
		dialog.setLongTimeTask(new ILongTimeTask() {
			public void afterExec(Object result) throws Exception {
			}

			public Object exec() throws Exception {
				ScheduleCalendarFactory.getRemoteInstance().reCalculateSchedule(scheduleIDs, calendarInfo);
				return null;
			}
		});
		dialog.show();
		
		showSubmitSuccess();

		setOprtState(OprtState.ADDNEW);

		txtName.setText("");
//		String newNum = null;
//		newNum = ScheduleCalendarFactory.getRemoteInstance().addOnetoNewNum();
//		Integer n = Integer.valueOf(newNum);
//		int m = n.intValue();
//		String item = null;
//		item = (m + 1) + "";
//		if (item.length() == 1) {
//			item = "00" + item;
//		} else if (item.length() == 2) {
//			item = "0" + item;
//		}
//		txtNumber.setText(item);
		txtNumber.setText(null);
		prmtProject.setValue(null);
		tblMain.removeRows();
		monday.setSelected(false);
		tuesday.setSelected(false);
		wednesday.setSelected(false);
		thursday.setSelected(false);
		friday.setSelected(false);
		saturday.setSelected(false);
		sunday.setSelected(false);
		// noWeekend.setSelected(false);
		calendarInfo = new ScheduleCalendarInfo();
	}

	private void showSubmitSuccess() {
		ProjectCalendarListUI parentUI = (ProjectCalendarListUI) getUIContext().get("Owner");
		parentUI.setMainStatusBar(parentUI.getMainStatusBar());
		parentUI.setShowMessagePolicy(0);
		parentUI.setMessageText("日历设置保存成功！");
		parentUI.showMessage();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		tblMain.addRow();
	}

	public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0) {
			FDCMsgBox.showWarning("请先选中行！");
			SysUtil.abort();
		}
		tblMain.removeRow(actRowIdx);
	}

	public void onLoad() throws Exception {
		tblMain.checkParsed();
		super.onLoad();
		 this.windowTitle = "日历设置";
		if (calendarInfo == null)
			calendarInfo = new ScheduleCalendarInfo();
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 默认设置星期一到5勾选 add by libing at 2011-9-26
			monday.setSelected(true);
			tuesday.setSelected(true);
			wednesday.setSelected(true);
			thursday.setSelected(true);
			friday.setSelected(true);

			DefaultWeekendEntryInfo saterday = new DefaultWeekendEntryInfo();
			saterday.setParent(calendarInfo);
			saterday.setWeekend(ScheduleWeekendEnum.Saturday);
			calendarInfo.getWeekendEntrys().add(saterday);
			DefaultWeekendEntryInfo sunday = new DefaultWeekendEntryInfo();
			sunday.setParent(calendarInfo);
			sunday.setWeekend(ScheduleWeekendEnum.Sunday);
			calendarInfo.getWeekendEntrys().add(sunday);
		}
		loadData();
		// 设置编码最大长度为三（好像没作用）
		this.txtNumber.setMaxLength(3);
		IColumn column = tblMain.getColumn("date");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(new KDDatePicker());
		column.setEditor(editor);
		FDCHelper.formatTableDate(tblMain, "date");
		txtName.setMaxLength(80);
		txtNumber.setMaxLength(80);
		// update by libing let it editable
		prmtProject.setEditable(true);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("id",TaskRptClientHelper
		// .getProjectFilterSql(),CompareType.INNER));
		view.getFilter().getFilterItems().add(new FilterItemInfo("id", TaskRptClientHelper.getProjectFilterSql(), CompareType.INNER));
		prmtProject.setEntityViewInfo(view);

		// add by libing at 2011-8-3 默认选中不是默认日历
		if (getOprtState().equals(OprtState.ADDNEW)) {
			kDRadioButton2.setSelected(true);
		}
		
		if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			kDRadioButton1.setEnabled(false);
			kDRadioButton2.setEnabled(false);
		}
		
		kDRadioButton1.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				if(kDRadioButton1.isSelected()){
					if(!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)){
						FDCMsgBox.showError("只能在集团下才能设置系统默认日历!");
						kDRadioButton2.setSelected(true);
						abort();
					}
					prmtProject.setDataNoNotify(null);
					prmtProject.setEnabled(false);
				}
				
			}});
		
		
		kDRadioButton2.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				if(kDRadioButton2.isSelected()){
					if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
						FDCMsgBox.showError("只能在集团下才能修改系统默认日历!");
						kDRadioButton1.setSelected(true, false);
						abort();
					}
					prmtProject.setEnabled(true);
				}
				
				
			}});
		// 如果工程项目为空，则灰显
//		if (prmtProject.getText() == null || prmtProject.getText().equals("")) {
//			kDRadioButton1.setEnabled(false);
//			kDRadioButton2.setEnabled(false);
//		}
		// 增加监听事件判断工程项目是否有值
		prmtProject.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// 当存在工程项目时，可以编辑
//				if (prmtProject.getText() != null || prmtProject.getText() != "") {
//					kDRadioButton1.setEnabled(true);
//					kDRadioButton2.setEnabled(true);
//				}
//				// 不存在工程项目时，不可以编辑
//				if (prmtProject.getText() == null || prmtProject.getText().equals("")) {
//					kDRadioButton1.setEnabled(false);
//					kDRadioButton2.setEnabled(false);
//				}
				if(prmtProject.getValue() != null){
					kDRadioButton2.setSelected(true);
					kDRadioButton1.setEnabled(false);
					kDRadioButton2.setEnabled(false);
				}else{
					kDRadioButton1.setEnabled(true);
					kDRadioButton2.setEnabled(true);
				}
			}

		});
	}

	private void loadData() throws EASBizException, BOSException, UuidException {
		if (OprtState.ADDNEW.equals(getOprtState()))
			return;
		Map map = ScheduleCalendarFactory.getRemoteInstance().getCalendar((getUIContext().get(UIContext.ID).toString()));
		if (map.get("calendarInfo") != null) {
			calendarInfo = (ScheduleCalendarInfo) map.get("calendarInfo");
		}
		txtName.setText(calendarInfo.getName());
		txtNumber.setText(calendarInfo.getNumber());
		if (calendarInfo.getObjectId() != null) {
			prmtProject.setValue((CurProjectInfo) map.get("projectInfo"));
		}
		HolidayEntryCollection holidys = calendarInfo.getHolidayEntrys();
		DefaultWeekendEntryCollection weekends = calendarInfo.getWeekendEntrys();

		for (int i = 0; i < holidys.size(); i++) {
			IRow row = tblMain.addRow();
			row.getCell("date").setValue(holidys.get(i).getDate());
			row.getCell("date").setValue(holidys.get(i).getDate());
			// add by libing at 2011-8-3
			row.getCell("holidayName").setValue(holidys.get(i).getHolidayName());
		}
		if (weekends.size() <= 1) {
			saturday.setSelected(false);
			sunday.setSelected(false);
		}

		monday.setSelected(true);
		tuesday.setSelected(true);
		wednesday.setSelected(true);
		thursday.setSelected(true);
		friday.setSelected(true);
		saturday.setSelected(true);
		sunday.setSelected(true);

		for (int i = 0; i < weekends.size(); i++) {
			switch (weekends.get(i).getWeekend().getValue()) {
			// update by libing true to false
			case 1:
				monday.setSelected(false);
				break;
			case 2:
				tuesday.setSelected(false);
				break;
			case 3:
				wednesday.setSelected(false);
				break;
			case 4:
				thursday.setSelected(false);
				break;
			case 5:
				friday.setSelected(false);
				break;
			case 6:
				saturday.setSelected(false);
				break;
			case 7:
				sunday.setSelected(false);
				break;
			// case 8:
			// noWeekend.setSelected(true);
			// break;
			}
		}
		// add by libing
		if (calendarInfo.isIsSys()) {
			kDRadioButton1.setSelected(true);
		} else {
			kDRadioButton2.setSelected(true);
		}

		if (OprtState.VIEW.equals(getOprtState())) {
			txtName.setEditable(false);
			txtNumber.setEditable(false);
			prmtProject.setEditable(false);
			prmtProject.setEnabled(false);
			tblMain.setEditable(false);
			// 设置成不可编辑，使用setEnabled而不应该是用setEditable
			monday.setEnabled(false);
			tuesday.setEnabled(false);
			wednesday.setEnabled(false);
			thursday.setEnabled(false);
			friday.setEnabled(false);
			saturday.setEnabled(false);
			sunday.setEnabled(false);
			noWeekend.setEnabled(false);
			bAddLine.setEnabled(false);
			bDeleteLine.setEnabled(false);
			bOk.setEnabled(false);
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}

	protected IObjectValue createNewData() {
		ScheduleCalendarInfo schCalendarInfo = new ScheduleCalendarInfo();
		DefaultWeekendEntryInfo saterday = new DefaultWeekendEntryInfo();
		saterday.setParent(schCalendarInfo);
		saterday.setWeekend(ScheduleWeekendEnum.Saturday);
		schCalendarInfo.getWeekendEntrys().add(saterday);
		DefaultWeekendEntryInfo sunday = new DefaultWeekendEntryInfo();
		sunday.setParent(schCalendarInfo);
		sunday.setWeekend(ScheduleWeekendEnum.Sunday);
		schCalendarInfo.getWeekendEntrys().add(sunday);
		return schCalendarInfo;
	}

	protected void disposeUIWindow() {
		if (isModefied()) {
			int confirm = FDCMsgBox.showConfirm2("数据已修改，是否保存？");
			if (FDCMsgBox.YES == confirm) {
				try {
					actionOK_actionPerformed(null);
				} catch (Exception e) {
					this.handleException(e);
				}
			} else {
				super.disposeUIWindow();
			}
		} else {
			super.disposeUIWindow();
		}
	}

	private boolean isModefied() {
		if (OprtState.VIEW.equals(this.getOprtState())) {
			return false;
		}
		if (calendarInfo == null)
			return false;
		if (!isEqual(calendarInfo.getNumber(), txtNumber.getText()))
			return true;
		if (!isEqual(calendarInfo.getName(), txtName.getText()))
			return true;
		if (prmtProject.getValue() != null) {
			if (!isEqual(calendarInfo.getObjectId(), ((CurProjectInfo) prmtProject.getValue()).getId().toString()))
				return true;
		} else {
			if (!isEqual(calendarInfo.getObjectId(), null))
				return true;
		}
		DefaultWeekendEntryCollection weekends = calendarInfo.getWeekendEntrys();
		Set weekendSet = new HashSet();
		for (int i = 0; i < weekends.size(); i++) {
			/*
			 * switch(weekends.get(i).getWeekend().getValue()){ case 1: if(
			 * !monday.isSelected()) return true;break; case 2: if(
			 * !tuesday.isSelected()) return true;break; case 3: if(
			 * !wednesday.isSelected()) return true;break; case 4: if(
			 * !thursday.isSelected()) return true;break; case 5: if(
			 * !friday.isSelected()) return true;break; case 6: if(
			 * !saturday.isSelected()) return true;break; case 7: if(
			 * !sunday.isSelected()) return true;break; case 8: if(
			 * !noWeekend.isSelected()) return true;break; }
			 */
			weekendSet.add(weekends.get(i).getWeekend());
		}
		if (monday.isSelected()) {
			if (!weekendSet.contains(ScheduleWeekendEnum.Monday))
				return true;
		} else {
			if (weekendSet.contains(ScheduleWeekendEnum.Monday))
				return true;
		}
		if (tuesday.isSelected()) {
			if (!weekendSet.contains(ScheduleWeekendEnum.Tuesday))
				return true;
		} else {
			if (weekendSet.contains(ScheduleWeekendEnum.Tuesday))
				return true;
		}
		if (wednesday.isSelected()) {
			if (!weekendSet.contains(ScheduleWeekendEnum.Wednesday))
				return true;
		} else {
			if (weekendSet.contains(ScheduleWeekendEnum.Wednesday))
				return true;
		}
		if (thursday.isSelected()) {
			if (!weekendSet.contains(ScheduleWeekendEnum.Thursday))
				return true;
		} else {
			if (weekendSet.contains(ScheduleWeekendEnum.Thursday))
				return true;
		}
		if (friday.isSelected()) {
			if (!weekendSet.contains(ScheduleWeekendEnum.Friday))
				return true;
		} else {
			if (weekendSet.contains(ScheduleWeekendEnum.Friday))
				return true;
		}
		if (saturday.isSelected()) {
			if (!weekendSet.contains(ScheduleWeekendEnum.Saturday))
				return true;
		} else {
			if (weekendSet.contains(ScheduleWeekendEnum.Saturday))
				return true;
		}
		if (sunday.isSelected()) {
			if (!weekendSet.contains(ScheduleWeekendEnum.Sunday))
				return true;
		} else {
			if (weekendSet.contains(ScheduleWeekendEnum.Sunday))
				return true;
		}

		HolidayEntryCollection holidies = calendarInfo.getHolidayEntrys();
		Set holidySet = new HashSet();
		if (holidies.size() != tblMain.getRowCount())
			return true;
		for (int i = 0; i < holidies.size(); i++) {
			holidySet.add(holidies.get(i).getDate());
		}
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			Date date = null;
			if (tblMain.getCell(i, "date") != null)
				date = (Date) tblMain.getCell(i, "date").getValue();
			if (!holidySet.contains(date))
				return true;
		}
		return false;
	}

	private boolean isEqual(String obj1, String obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 == null && obj2 != null && obj2.trim().length() < 1)
			return true;
		if (obj2 == null && obj1 != null && obj1.trim().length() < 1)
			return true;
		if (obj1 != null && obj2 != null && obj1.trim().equals(obj2.trim()))
			return true;
		return false;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		setTitle();
	}

	private void setTitle() {
		if (getOprtState().equalsIgnoreCase(OprtState.ADDNEW)) {
			// 对象 - 新增
			setUITitle("项目日历" + AssistantClientUtils.UI_TITLE_SEPRATOR
					+ EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.ADDNEW));
		} else if (getOprtState().equalsIgnoreCase(OprtState.EDIT)) {
			// 对象 - 修改
			setUITitle("项目日历" + AssistantClientUtils.UI_TITLE_SEPRATOR
					+ EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.EDIT));
		} else if (getOprtState().equalsIgnoreCase(OprtState.VIEW)) {
			// 对象 - 查看
			setUITitle("项目日历" + AssistantClientUtils.UI_TITLE_SEPRATOR
					+ EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.VIEW));
		}

	}
}