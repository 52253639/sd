/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.IJoinDoScheme;
import com.kingdee.eas.fdc.sellhouse.JoinApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class JoinDoSchemeEditUI extends AbstractJoinDoSchemeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(JoinDoSchemeEditUI.class);
	
	private KDComboBox refDateCombo;

	/**
	 * output class constructor
	 */
	public JoinDoSchemeEditUI() throws Exception {
		super();
	}
	
	public void loadFields() {
		this.initEntries();
		super.loadFields();
	}
	
	private void initEntries(){
		this.kdtApprEntries.checkParsed();
		KDTextField kdtApproachEntries_name_TextField = new KDTextField();
        kdtApproachEntries_name_TextField.setName("kdtApproachEntries_name_TextField");
        kdtApproachEntries_name_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtApproachEntries_name_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_name_TextField);
        this.kdtApprEntries.getColumn("name").setEditor(kdtApproachEntries_name_CellEditor);
        KDTextField kdtApproachEntries_remark_TextField = new KDTextField();
        kdtApproachEntries_remark_TextField.setName("kdtApproachEntries_remark_TextField");
        kdtApproachEntries_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtApproachEntries_remark_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_remark_TextField);
        this.kdtApprEntries.getColumn("remark").setEditor(kdtApproachEntries_remark_CellEditor);
        refDateCombo = new KDComboBox();
        refDateCombo.setVisible(true);
        refDateCombo.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ReferenceTimeEnum").toArray());
        KDTDefaultCellEditor kdtApproachEntries_refDate_CellEditor = new KDTDefaultCellEditor(refDateCombo);
        this.kdtApprEntries.getColumn("referenceTime").setEditor(kdtApproachEntries_refDate_CellEditor);
        KDFormattedTextField kdtApproachEntries_intervalMon_TextField = new KDFormattedTextField();
        kdtApproachEntries_intervalMon_TextField.setName("kdtApproachEntries_intervalMon_TextField");
        kdtApproachEntries_intervalMon_TextField.setVisible(true);
        kdtApproachEntries_intervalMon_TextField.setEditable(true);
        kdtApproachEntries_intervalMon_TextField.setHorizontalAlignment(2);
        kdtApproachEntries_intervalMon_TextField.setDataType(0);
        KDTDefaultCellEditor kdtApproachEntries_intervalMon_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_intervalMon_TextField);
        this.kdtApprEntries.getColumn("intervalMonth").setEditor(kdtApproachEntries_intervalMon_CellEditor);
        KDFormattedTextField kdtApproachEntries_intervalDay_TextField = new KDFormattedTextField();
        kdtApproachEntries_intervalDay_TextField.setName("kdtApproachEntries_intervalDay_TextField");
        kdtApproachEntries_intervalDay_TextField.setVisible(true);
        kdtApproachEntries_intervalDay_TextField.setEditable(true);
        kdtApproachEntries_intervalDay_TextField.setHorizontalAlignment(2);
        kdtApproachEntries_intervalDay_TextField.setDataType(0);
        KDTDefaultCellEditor kdtApproachEntries_intervalDay_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_intervalDay_TextField);
        this.kdtApprEntries.getColumn("intervalDays").setEditor(kdtApproachEntries_intervalDay_CellEditor);
        KDDatePicker kdtApproachEntries_appointedDate_DatePicker = new KDDatePicker();
        kdtApproachEntries_appointedDate_DatePicker.setName("kdtApproachEntries_appointedDate_DatePicker");
        kdtApproachEntries_appointedDate_DatePicker.setVisible(true);
        kdtApproachEntries_appointedDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtApproachEntries_appointedDate_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_appointedDate_DatePicker);
        this.kdtApprEntries.getColumn("scheduledDate").setEditor(kdtApproachEntries_appointedDate_CellEditor);
        KDCheckBox kdtApproachEntries_isFinish_CheckBox = new KDCheckBox();
        kdtApproachEntries_isFinish_CheckBox.setName("kdtApproachEntries_isFinish_CheckBox");
        KDTDefaultCellEditor kdtApproachEntries_isFinish_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_isFinish_CheckBox);
        this.kdtApprEntries.getColumn("isFinish").setEditor(kdtApproachEntries_isFinish_CellEditor);
        this.kdtApprEntries.getColumn("id").getStyleAttributes().setHided(true);
        
        this.kdtDataEntries.checkParsed();
        KDTextField kdtDataEntries_name_TextField = new KDTextField();
        kdtDataEntries_name_TextField.setName("kdtDataEntries_name_TextField");
        kdtDataEntries_name_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtDataEntries_name_CellEditor = new KDTDefaultCellEditor(kdtDataEntries_name_TextField);
        this.kdtDataEntries.getColumn("name").setEditor(kdtDataEntries_name_CellEditor);
        KDTextField kdtDataEntries_remark_TextField = new KDTextField();
        kdtDataEntries_remark_TextField.setName("kdtDataEntries_remark_TextField");
        kdtDataEntries_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtDataEntries_remark_CellEditor = new KDTDefaultCellEditor(kdtDataEntries_remark_TextField);
        this.kdtDataEntries.getColumn("remark").setEditor(kdtDataEntries_remark_CellEditor);
        this.kdtDataEntries.getColumn("id").getStyleAttributes().setHided(true);
	}

	private void checkDumpName() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData.getProject();
		if (info == null) {
			return;
		}
		
		String number = this.txtNumber.getText();
	
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
			if (JoinDoSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("number", number));
		
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
			
				if (JoinDoSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下编码不能重复！");
					SysUtil.abort();
				}
			}
	
		}
		String name = this.txtName.getSelectedItem().toString();
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
			if (JoinDoSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
				
				if (JoinDoSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();

		this.btnApprAddRow.setEnabled(true);
		this.btnApprInsertRow.setEnabled(true);
		this.btnApprDeleteRow.setEnabled(true);
		this.btnDataAddRow.setEnabled(true);
		this.btnDataInsertRow.setEnabled(true);
		this.btnDataDeleteRow.setEnabled(true);
		
		//非售楼组织不能操作
		if(!FDCSysContext.getInstance().checkIsSHEOrg()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			
			this.btnApprAddRow.setEnabled(false);
			this.btnApprInsertRow.setEnabled(false);
			this.btnApprDeleteRow.setEnabled(false);
			this.btnDataAddRow.setEnabled(false);
			this.btnDataInsertRow.setEnabled(false);
			this.btnDataDeleteRow.setEnabled(false);
		}
		
		initEntryTable();
		
		this.kdtApprEntries.addKDTEditListener(new KDTEditListener() {
			public void editCanceled(KDTEditEvent e) {

			}

			public void editStarting(KDTEditEvent e) {
				// 组装参照时间下拉框选项
				if (e.getColIndex() == kdtApprEntries
						.getColumnIndex("referenceTime")) {
					setRefDateEditor(kdtApprEntries.getRow(e.getRowIndex()));
					kdtApprEntries.getRow(e.getRowIndex()).getCell(
							"referenceTime").setValue(e.getValue());
				}
				// 判断流程名称是否可修改
				else if (e.getColIndex() == kdtApprEntries
						.getColumnIndex("name")) {
					KDTextField nameField = new KDTextField();
					if (!isNameUsed(e.getRowIndex())) {
						MsgBox.showInfo("流程名称已被引用，不能修改");
						nameField.setEditable(false);
						KDTDefaultCellEditor nameEditor = new KDTDefaultCellEditor(
								nameField);
						kdtApprEntries.getRow(e.getRowIndex()).getCell(
								"name").setEditor(nameEditor);
						return;
					} else {
						nameField.setEditable(true);
						KDTDefaultCellEditor nameEditor = new KDTDefaultCellEditor(
								nameField);
						kdtApprEntries.getRow(e.getRowIndex()).getCell(
								"name").setEditor(nameEditor);
					}
				} else if (e.getColIndex() == kdtApprEntries.getColumnIndex("intervalMonth")
						|| e.getColIndex() == kdtApprEntries.getColumnIndex("intervalDays")
						|| e.getColIndex() == kdtApprEntries.getColumnIndex("scheduledDate")) {
					Object refDateObj = kdtApprEntries.getRow(e.getRowIndex()).getCell("referenceTime").getValue();
					ICell intervalMonCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalMonth");
					ICell intervalDayCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalDays");
					ICell appointedDateCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("scheduledDate");
					
					JoinApproachEntryInfo entryInfo = editData.getApprEntries().get(e.getRowIndex());
					if (refDateObj != null && refDateObj.equals("指定日期")) {
						// 清空单元格
						intervalMonCell.setValue(null);
						intervalDayCell.setValue(null);
						
						// 清空字段值
						if(entryInfo != null){
							entryInfo.setIntervalMonth(0);
							entryInfo.setIntervalDays(0);
						}
						
						//禁用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(true);
						intervalDayCell.getStyleAttributes().setLocked(true);
						
						//使能指定日期
						appointedDateCell.getStyleAttributes().setLocked(false);
					} else {
						//启用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(false);
						intervalDayCell.getStyleAttributes().setLocked(false);
						
						//禁用指定日期
						appointedDateCell.getStyleAttributes().setLocked(true);
						appointedDateCell.setValue(null);
						if(entryInfo != null){
							entryInfo.setScheduledDate(null);
						}
					}
				}
			}

			public void editStarted(KDTEditEvent e) {

			}

			public void editStopped(KDTEditEvent e) {
				// 根据参照时间的值设置间隔月和间隔日是否可编辑
				if (e.getColIndex() == kdtApprEntries
						.getColumnIndex("referenceTime")) {
					ICell intervalMonCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalMonth");
					ICell intervalDayCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalDays");
					ICell appointedDateCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("scheduledDate");
					
					JoinApproachEntryInfo entryInfo = editData.getApprEntries().get(e.getRowIndex());
					if (e.getValue() != null && e.getValue().equals("指定日期")) {
						// 清空单元格
						intervalMonCell.setValue(null);
						intervalDayCell.setValue(null);
						
						// 清空字段值
						if(entryInfo != null){
							entryInfo.setIntervalMonth(0);
							entryInfo.setIntervalDays(0);
						}
						
						//禁用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(true);
						intervalDayCell.getStyleAttributes().setLocked(true);
						
						//使能指定日期
						appointedDateCell.getStyleAttributes().setLocked(false);
					} else {
						//启用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(false);
						intervalDayCell.getStyleAttributes().setLocked(false);
						
						//禁用指定日期
						appointedDateCell.getStyleAttributes().setLocked(true);
						appointedDateCell.setValue(null);
						if(entryInfo != null){
							entryInfo.setScheduledDate(null);
						}
					}
				}
			}

			public void editStopping(KDTEditEvent e) {

			}

			public void editValueChanged(KDTEditEvent e) {
				// 若当前行的是否结束字段为勾选状态，则取消其他分录的勾选状态
				if (e.getColIndex() == kdtApprEntries
						.getColumnIndex("isFinish")) {
					if (e.getValue() != null
							&& e.getValue().equals(new Boolean(true))) {
						cancelOtherIsFinishField(e.getRowIndex());
					}
				}
			}
		});

		// 设置分录是否结束字段的选择状态
		this.autoSelectIsFinishField();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	
	private void initEntryTable(){
		for(int i=0; i<kdtApprEntries.getRowCount(); i++){
			setRefDateEditor(kdtApprEntries.getRow(i));
			//填充参照日期
			Object refDate = this.editData.getApprEntries().get(kdtApprEntries.getRow(i).getCell("id").getValue()).getReferenceTime();
			kdtApprEntries.getRow(i).getCell("referenceTime").setValue(refDate);
			//初始化指定日期的编辑状态
			ICell appointedDateCell = kdtApprEntries.getRow(i).getCell("scheduledDate");
			if(refDate!=null && refDate.toString().equals("指定日期")){
				appointedDateCell.getStyleAttributes().setLocked(false);
			}
			else{
				appointedDateCell.getStyleAttributes().setLocked(true);
			}
		}
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	protected void btnApprAddRow_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = this.kdtApprEntries.addRow();
		// 显示设置为false，让界面显示勾选框
		row.getCell("isFinish").setValue(new Boolean(false));
		// 设置参照日期
		setRefDateEditor(row);
		// 设置分录是否结束字段的选择状态
		this.autoSelectIsFinishField();
		
		super.btnApprAddRow_actionPerformed(e);
	}
	
	protected void btnApprInsertRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnApprInsertRow_actionPerformed(e);
		
		int activeRowIndex = kdtApprEntries.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex < 0) {
			btnApprAddRow_actionPerformed(e);
		}
		else{
			IRow row = kdtApprEntries.addRow(activeRowIndex);
			row.getCell("isFinish").setValue(new Boolean(false));
			this.autoSelectIsFinishField();
			
			for (int i = activeRowIndex; i < kdtApprEntries.getRowCount(); i++) {
				setRefDateEditor(kdtApprEntries.getRow(i));
			}
		}
	}

	protected void btnApprDeleteRow_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnApprDeleteRow_actionPerformed(e);
		
		int activeRowIndex = kdtApprEntries.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex < 0) {
			return;
		} else if (!this.isNameUsed(activeRowIndex)) {
			MsgBox.showInfo("流程名称已被引用，不能删除");
			SysUtil.abort();
		}
		kdtApprEntries.removeRow(activeRowIndex);
		
		for (int i = activeRowIndex; i < kdtApprEntries.getRowCount(); i++) {
			setRefDateEditor(kdtApprEntries.getRow(i));
		}
		this.autoSelectIsFinishField();
	}
	
	protected void btnDataAddRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDataAddRow_actionPerformed(e);
		this.kdtDataEntries.addRow();
	}
	
	protected void btnDataInsertRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDataInsertRow_actionPerformed(e);
		int activeRowIndex = kdtDataEntries.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex < 0) {
			btnDataAddRow_actionPerformed(e);
		}
		else{
			kdtDataEntries.addRow(activeRowIndex);
		}
	}
	
	protected void btnDataDeleteRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDataDeleteRow_actionPerformed(e);
		
		int activeRowIndex = kdtDataEntries.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex < 0) {
			return;
		}
		kdtDataEntries.removeRow(activeRowIndex);
	}
	
	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkDumpName();
		super.actionSave_actionPerformed(e);
		if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkDumpName();
		super.actionSubmit_actionPerformed(e);
		if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();
		IJoinDoScheme joinDoScheme = (IJoinDoScheme)this.getBizInterface();
		JoinDoSchemeInfo joinDoSchemeInfo = joinDoScheme.getJoinDoSchemeInfo(new ObjectUuidPK(id));
		if (joinDoSchemeInfo != null) {
			if (!joinDoSchemeInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法再禁用！");
			} else {
				joinDoSchemeInfo.setIsEnabled(false);
				joinDoScheme.save(joinDoSchemeInfo);
				doAfterSave(new ObjectUuidPK(id));
				MsgBox.showWarning(this, "禁用成功！");
			}
		}
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		String id = editData.getId().toString();
		IJoinDoScheme joinDoScheme = (IJoinDoScheme)this.getBizInterface();
		JoinDoSchemeInfo joinDoSchemeInfo = joinDoScheme.getJoinDoSchemeInfo(new ObjectUuidPK(id));
		if (joinDoSchemeInfo.isIsEnabled()) {
			MsgBox.showWarning(this, "方案已经启用，请不要重复提交！");
			SysUtil.abort();
		}
		joinDoSchemeInfo.setIsEnabled(true);
		joinDoScheme.save(joinDoSchemeInfo);
		doAfterSave(new ObjectUuidPK(id));
		MsgBox.showWarning(this, "启用成功！");
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();
		JoinDoSchemeInfo joinDoSchemeInfo = ((IJoinDoScheme)this.getBizInterface()).getJoinDoSchemeInfo(new ObjectUuidPK(id));
		if (joinDoSchemeInfo != null) {
			if (joinDoSchemeInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法修改！");
			} else {
				super.actionEdit_actionPerformed(e);
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();
		JoinDoSchemeInfo joinDoSchemeInfo = ((IJoinDoScheme)this.getBizInterface()).getJoinDoSchemeInfo(new ObjectUuidPK(id));
		if (joinDoSchemeInfo != null) {
			if (joinDoSchemeInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法删除！");
			} else {
				super.actionRemove_actionPerformed(e);
			}
		}
	}

	protected IObjectValue createNewData() {
		JoinDoSchemeInfo objectValue = new JoinDoSchemeInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext()
				.getCurrentUser()));
		objectValue.setProject((SellProjectInfo) getUIContext().get(
				"sellProject"));
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return JoinDoSchemeFactory.getRemoteInstance();
	}
	
	protected void initDataStatus() {
		super.initDataStatus();

		JoinDoSchemeInfo info = (JoinDoSchemeInfo) getDataObject();
		if (OprtState.ADDNEW.equals(getOprtState())) {
			actionCancel.setEnabled(false);
			actionCancelCancel.setEnabled(false);
		} else if (OprtState.EDIT.equals(getOprtState())
				|| OprtState.VIEW.equals(getOprtState())) {
			actionCancel.setEnabled(info.isIsEnabled());
			actionCancelCancel.setEnabled(!info.isIsEnabled());
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("simpleName"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("apprEntries.*"));
		sic.add(new SelectorItemInfo("apprEntries.number"));
		sic.add(new SelectorItemInfo("apprEntries.name"));
		sic.add(new SelectorItemInfo("apprEntries.remark"));
		sic.add(new SelectorItemInfo("dataEntries.*"));
		sic.add(new SelectorItemInfo("dataEntries.number"));
		sic.add(new SelectorItemInfo("dataEntries.name"));
		sic.add(new SelectorItemInfo("dataEntries.remark"));
		sic.add(new SelectorItemInfo("project.id"));
		sic.add(new SelectorItemInfo("project.number"));
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("project.parent.id"));
		sic.add(new SelectorItemInfo("project.parent.name"));
		sic.add(new SelectorItemInfo("project.parent.number"));
		sic.add(new SelectorItemInfo("project.level"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("CU.*"));
		return sic;
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		// 启用了编码规则将不效验编码是否为空
		String companyID = null;
		if (com.kingdee.eas.common.client.SysContext.getSysContext()
				.getCurrentOrgUnit() != null) {
			companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.common.client.SysContext
					.getSysContext().getCurrentOrgUnit()).getString("id");
		}
		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
				.getRemoteInstance();
		if (!iCodingRuleManager.isExist(editData, companyID)) {
			if (this.txtNumber.getText() == null
					|| this.txtNumber.getText().trim().equals("")) {
				MsgBox.showWarning(this, "编码不能为空！");
				this.txtNumber.requestFocus();
				SysUtil.abort();
			}
			else if(this.txtNumber.getText().length() > 100){
				MsgBox.showWarning(this, "编码长度不能超过100个字符！");
				this.txtNumber.requestFocus();
				SysUtil.abort();
			}
		}
		if (this.txtName.getSelectedItem() == null
				|| this.txtName.getSelectedItem().toString().trim().equals("")) {
			MsgBox.showWarning(this, "名称不能为空！");
			this.txtName.requestFocus();
			SysUtil.abort();
		}
		else if(this.txtName.getSelectedItem().toString().length() > 255){
			MsgBox.showWarning(this, "名称长度不能超过255个字符！");
			this.txtName.requestFocus();
			SysUtil.abort();
		}
		
		if (this.kdtApprEntries.getRowCount() > 0) {
			int isFinishCheckedCount = 0;
			for (int i = 0; i < kdtApprEntries.getRowCount(); i++) {
				if (kdtApprEntries.getRow(i).getCell("name").getValue() == null
						|| kdtApprEntries.getRow(i).getCell("name")
								.getValue().toString().trim().equals("")) {
					MsgBox.showWarning(this, "第" + (i+1) + "行入伙办理流程-名称不能为空！");
					SysUtil.abort();
				}
				if (kdtApprEntries.getRow(i).getCell("isFinish").getValue() != null
						&& kdtApprEntries.getRow(i).getCell("isFinish")
								.getValue().equals(new Boolean(true))) {
					isFinishCheckedCount++;
				}
			}
			if (isFinishCheckedCount == 0) {
				MsgBox.showWarning(this, "入伙办理流程-是否结束不能为空");
				SysUtil.abort();
			} else if (isFinishCheckedCount > 1) {
				MsgBox.showWarning(this, "入伙办理流程-是否结束一栏只能有一条记录勾选");
				SysUtil.abort();
			}
		}
		if (this.kdtDataEntries.getRowCount() > 0) {
			for (int i = 0; i < kdtDataEntries.getRowCount(); i++) {
				if (kdtDataEntries.getRow(i).getCell("name").getValue() == null
						|| kdtDataEntries.getRow(i).getCell("name").getValue()
								.toString().trim().equals("")) {
					MsgBox.showWarning(this, "第" + (i+1) + "行入伙办理资料-名称不能为空！");
					SysUtil.abort();
				}
			}
		}
		if (this.kdtDataEntries.getRowCount() == 0
				&& this.kdtApprEntries.getRowCount() == 0) {
			MsgBox.showWarning(this, "流程/资料明细行里至少要有1条记录！");
			SysUtil.abort();
		}
		
		this.chkIsFieldValid();
	}
	
	/**
	 * 检查分录的字段是否满足限制条件
	 */
	private void chkIsFieldValid(){
		//流程分录
		for(int i=0; i<this.kdtApprEntries.getRowCount(); i++){
			ICell name = this.kdtApprEntries.getCell(i, "name");
			if(name.getValue()!=null && name.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的流程名称长度超过255个字符");
				SysUtil.abort();
			}
			ICell remark = this.kdtApprEntries.getCell(i, "remark");
			if(remark.getValue()!=null && remark.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的流程说明长度超过255个字符");
				SysUtil.abort();
			}
			ICell refDate = this.kdtApprEntries.getCell(i, "referenceTime");
			if(refDate.getValue() == null){
				MsgBox.showInfo("请输入第" + (i+1) + "行的参照日期");
				SysUtil.abort();
			}
			else if("指定日期".equals(refDate.getValue().toString())){
				ICell appointedDate = this.kdtApprEntries.getCell(i, "scheduledDate");
				if(appointedDate.getValue()==null || appointedDate.getValue().toString().length()==0){
					MsgBox.showInfo("请输入第" + (i+1) + "行的指定日期");
					SysUtil.abort();
				}
			}
			else{//参照日期的值不是"指定日期"，检查间隔月和间隔天
				ICell intervalMon = this.kdtApprEntries.getCell(i, "intervalMonth");
				if(intervalMon.getValue()!=null){
					int mon = Integer.parseInt(intervalMon.getValue().toString());
					if(mon<0 || mon >12){
						MsgBox.showInfo("第" + (i+1) + "行的间隔月的取值应为0~12");
						SysUtil.abort();
					}
				}
				ICell intervalDay = this.kdtApprEntries.getCell(i, "intervalDays");
				if(intervalDay.getValue()!=null){
					int day = Integer.parseInt(intervalDay.getValue().toString());
					if(day<0 || day >31){
						MsgBox.showInfo("第" + (i+1) + "行的间隔天的取值应为0~31");
						SysUtil.abort();
					}
				}				
			}
		}
		
		//资料分录
		for(int i=0; i<this.kdtDataEntries.getRowCount(); i++){
			ICell name = this.kdtDataEntries.getCell(i, "name");
			if(name.getValue() == null){
				MsgBox.showInfo("请输入第" + (i+1) + "行的资料名称");
				SysUtil.abort();
			}
			else if(name.getValue()!=null && name.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的资料名称长度超过255个字符");
				SysUtil.abort();
			}
			ICell remark = this.kdtDataEntries.getCell(i, "remark");
			if(remark.getValue()!=null && remark.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的资料说明长度超过255个字符");
				SysUtil.abort();
			}
		}
	}
	
	/**
	 * 设置参照日期的下拉框选项，由选中行前面分录的流程名称组成
	 * 
	 * @param row
	 *            新增行
	 */
	private void setRefDateEditor(IRow row) {
		refDateCombo.removeAllItems();
		
		int rowCount = 0;
		Object[] objArray = null;
		if (row.getRowIndex() < 0) {
			return;
		} else {
			rowCount = row.getRowIndex();
			objArray = new Object[rowCount + 2];
			objArray[0] = "指定日期";
			objArray[1] = "签约日期";
			for (int i = 0; i < rowCount; i++) {
				if (kdtApprEntries.getRow(i).getCell("name").getValue() != null) {
					objArray[i + 2] = kdtApprEntries.getRow(i).getCell("name").getValue();
				}
			}
		}
		refDateCombo.addItems(objArray);

		ICell refDateCell = row.getCell("referenceTime");
		refDateCell.setEditor(new KDTDefaultCellEditor(refDateCombo));
	}

	/**
	 * 判断选中行的流程名称是否被引用
	 * 
	 * @param rowIndex
	 *            - 选中行的索引
	 * @return
	 */
	private boolean isNameUsed(int rowIndex) {
		String currentName = (String) kdtApprEntries.getRow(rowIndex)
				.getCell("name").getValue();
		for (int i = rowIndex + 1; i < kdtApprEntries.getRowCount(); i++) {
			Object refDateValue = kdtApprEntries.getRow(i).getCell(
					"referenceTime").getValue();
			// 判断当前流程名称是否被引用
			if (refDateValue != null
					&& currentName != null
					&& currentName.equals((String) refDateValue)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 如果没有分录勾选是否结束，则默认勾选最后一行
	 */
	private void autoSelectIsFinishField() {
		boolean isHasSelected = false;
		int rowCount = this.kdtApprEntries.getRowCount();
		if (rowCount <= 0) {
			return;
		}
		Boolean trueObj = new Boolean(true);
		for (int i = 0; i < rowCount; i++) {
			if (this.kdtApprEntries.getRow(i).getCell("isFinish")
					.getValue() != null
					&& this.kdtApprEntries.getRow(i).getCell("isFinish")
							.getValue().equals(trueObj)) {
				isHasSelected = true;
				break;
			}
		}
		if (!isHasSelected) {
			this.kdtApprEntries.getRow(rowCount - 1).getCell("isFinish")
					.setValue(trueObj);
		}
	}

	/**
	 * 取消其他分录的是否结束字段的勾选状态
	 * 
	 * @param currentRowIndex
	 *            - 选中行
	 */
	private void cancelOtherIsFinishField(int currentRowIndex) {
		for (int i = 0; i < this.kdtApprEntries.getRowCount(); i++) {
			if (i != currentRowIndex) {
				this.kdtApprEntries.getRow(i).getCell("isFinish").setValue(
						new Boolean(false));
			}
		}
	}

}