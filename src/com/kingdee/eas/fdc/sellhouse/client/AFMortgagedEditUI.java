/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.IAFMortgaged;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AFMortgagedEditUI extends AbstractAFMortgagedEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(AFMortgagedEditUI.class);
	
	private KDComboBox refDateCombo;

	public AFMortgagedEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
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
			if (AFMortgagedFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
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
			
				if (AFMortgagedFactory.getRemoteInstance().exists(filter)) {
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
			if (AFMortgagedFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
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
				
				if (AFMortgagedFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkDumpName();
		super.actionSave_actionPerformed(e);
		if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkDumpName();
		super.actionSubmit_actionPerformed(e);
		if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AFMortgagedFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		AFMortgagedInfo objectValue = new AFMortgagedInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext()
				.getCurrentUser()));
		objectValue.setProject((SellProjectInfo) getUIContext().get(
				"sellProject"));
		objectValue.setMmType(AFMmmTypeEnum.LoanAmount);
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return objectValue;
	}

	protected void initDataStatus() {
		super.initDataStatus();

		AFMortgagedInfo info = (AFMortgagedInfo) getDataObject();
		if (OprtState.ADDNEW.equals(getOprtState())) {
			actionCancel.setEnabled(false);
			actionCancelCancel.setEnabled(false);
		} else if (OprtState.EDIT.equals(getOprtState())
				|| OprtState.VIEW.equals(getOprtState())) {
			actionCancel.setEnabled(info.isIsEnabled());
			actionCancelCancel.setEnabled(!info.isIsEnabled());
		}
		combommType.setRequired(true);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("simpleName"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("ApproachEntrys.*"));
		sic.add(new SelectorItemInfo("ApproachEntrys.number"));
		sic.add(new SelectorItemInfo("ApproachEntrys.name"));
		sic.add(new SelectorItemInfo("ApproachEntrys.remark"));
		sic.add(new SelectorItemInfo("DataEntrys.*"));
		sic.add(new SelectorItemInfo("DataEntrys.number"));
		sic.add(new SelectorItemInfo("DataEntrys.name"));
		sic.add(new SelectorItemInfo("DataEntrys.remark"));
		sic.add(new SelectorItemInfo("project.id"));
		sic.add(new SelectorItemInfo("project.number"));
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("mmType"));
		sic.add(new SelectorItemInfo("CU.*"));
		return sic;
	}

	/**
	 * 修改
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();
		IAFMortgaged vgd = (IAFMortgaged) getBizInterface();
		AFMortgagedInfo afmInfo = vgd.getAFMortgagedInfo(new ObjectUuidPK(id));
		if (afmInfo != null) {
			if (afmInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法修改！");
			}
			else {
				super.actionEdit_actionPerformed(e);
			}
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();
		IAFMortgaged vgd = (IAFMortgaged) getBizInterface();
		AFMortgagedInfo afmInfo = vgd.getAFMortgagedInfo(new ObjectUuidPK(id));
		if (afmInfo != null) {
			if (afmInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法删除！");
			} 
			else if(this.isAFMortgagedUsed(id)){
				MsgBox.showWarning(this, "所选方案已被使用，无法删除！");
			}
			else {
				super.actionRemove_actionPerformed(e);
			}
		}
	}

	/**
	 * 禁用
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();
		IAFMortgaged vgd = (IAFMortgaged) getBizInterface();
		AFMortgagedInfo afmInfo = vgd.getAFMortgagedInfo(new ObjectUuidPK(id));
		if (afmInfo != null) {
			if (!afmInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法再禁用！");
			} else {
				afmInfo.setIsEnabled(false);
				vgd.save(afmInfo);
				doAfterSave(new ObjectUuidPK(id));
				MsgBox.showWarning(this, "禁用成功！");
			}
		}
	}

	/**
	 * 启用
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		String id = editData.getId().toString();
		IAFMortgaged vgd = (IAFMortgaged) getBizInterface();
		AFMortgagedInfo vgdInfo = vgd.getAFMortgagedInfo(new ObjectUuidPK(id));

		if (vgdInfo.isIsEnabled()) {
			MsgBox.showWarning(this, "方案已经启用，请不要重复提交！");
			SysUtil.abort();
		}

		AFMortgagedCollection coll = vgd
				.getAFMortgagedCollection(" where project.id = '"
						+ vgdInfo.getProject().getId().toString() + "'");
		for (int i = 0; i < coll.size(); i++) {
			AFMortgagedInfo info = coll.get(i);
			if (info.isIsEnabled() && !info.getId().equals(vgdInfo.getId())
					&& info.getMmType().equals(vgdInfo.getMmType())) {
				MsgBox.showWarning(this, "同一项目只能启用一个方案！");
				SysUtil.abort();
			}
		}
		vgdInfo.setIsEnabled(true);
		vgd.save(vgdInfo);
		doAfterSave(new ObjectUuidPK(id));
		MsgBox.showWarning(this, "启用成功！");
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.btnApproachAddRow.setEnabled(true);
		this.btnApproachInsertRow.setEnabled(true);
		this.btnApproachDeleteRow.setEnabled(true);
		this.btnDataAddRow.setEnabled(true);
		this.btnDataInsertRow.setEnabled(true);
		this.btnDataDeleteRow.setEnabled(true);
		
		//非售楼组织不能操作
		if(!FDCSysContext.getInstance().checkIsSHEOrg()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionRemove.setEnabled(false);
			
			this.btnApproachAddRow.setEnabled(false);
			this.btnApproachInsertRow.setEnabled(false);
			this.btnApproachDeleteRow.setEnabled(false);
			this.btnDataAddRow.setEnabled(false);
			this.btnDataInsertRow.setEnabled(false);
			this.btnDataDeleteRow.setEnabled(false);
		}

		initTableLength();

		this.kdtApproachEntries.addKDTEditListener(new KDTEditListener() {
			public void editCanceled(KDTEditEvent e) {

			}

			public void editStarting(KDTEditEvent e) {
				// 组装参照时间下拉框选项
				if (e.getColIndex() == kdtApproachEntries
						.getColumnIndex("refDate")) {
					setRefDateEditor(kdtApproachEntries.getRow(e.getRowIndex()));
					kdtApproachEntries.getRow(e.getRowIndex()).getCell(
							"refDate").setValue(e.getValue());
				}
				// 判断流程名称是否可修改
				else if (e.getColIndex() == kdtApproachEntries
						.getColumnIndex("name")) {
					KDTextField nameField = new KDTextField();
					if (!isNameUsed(e.getRowIndex())) {
						MsgBox.showInfo("流程名称已被引用，不能修改");
						nameField.setEditable(false);
						KDTDefaultCellEditor nameEditor = new KDTDefaultCellEditor(
								nameField);
						kdtApproachEntries.getRow(e.getRowIndex()).getCell(
								"name").setEditor(nameEditor);
						return;
					} else {
						nameField.setEditable(true);
						KDTDefaultCellEditor nameEditor = new KDTDefaultCellEditor(
								nameField);
						kdtApproachEntries.getRow(e.getRowIndex()).getCell(
								"name").setEditor(nameEditor);
					}
				}
				//编辑前检查参照时间的值，确定【间隔月】、【间隔日】和【指定日期】是否可编辑
				else if (e.getColIndex() == kdtApproachEntries.getColumnIndex("intervalMon")
						|| e.getColIndex() == kdtApproachEntries.getColumnIndex("intervalDay")
						|| e.getColIndex() == kdtApproachEntries.getColumnIndex("appointedDate")) {
					Object refDateObj = kdtApproachEntries.getRow(e.getRowIndex()).getCell("refDate").getValue();
					ICell intervalMonCell = kdtApproachEntries.getRow(e.getRowIndex()).getCell("intervalMon");
					ICell intervalDayCell = kdtApproachEntries.getRow(e.getRowIndex()).getCell("intervalDay");
					ICell appointedDateCell = kdtApproachEntries.getRow(e.getRowIndex()).getCell("appointedDate");
					
					AFMortgagedApproachEntryInfo apprEntryInfo = editData.getApproachEntrys().get(e.getRowIndex());
					
					if (refDateObj != null && refDateObj.equals("指定日期")) {
						// 清空单元格
						intervalMonCell.setValue(null);
						intervalDayCell.setValue(null);
						
						// 清空字段值
						if(apprEntryInfo != null){
							apprEntryInfo.setIntervalMonth(0);
							apprEntryInfo.setIntervalDays(0);
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
						if(apprEntryInfo != null){
							apprEntryInfo.setScheduledDate(null);
						}
					}
				}
			}

			public void editStarted(KDTEditEvent e) {

			}

			public void editStopped(KDTEditEvent e) {
				// 根据参照时间的值设置【间隔月】、【间隔日】和【指定日期】是否可编辑
				if (e.getColIndex() == kdtApproachEntries.getColumnIndex("refDate")) {
					ICell intervalMonCell = kdtApproachEntries.getRow(e.getRowIndex()).getCell("intervalMon");
					ICell intervalDayCell = kdtApproachEntries.getRow(e.getRowIndex()).getCell("intervalDay");
					ICell appointedDateCell = kdtApproachEntries.getRow(e.getRowIndex()).getCell("appointedDate");
					
					AFMortgagedApproachEntryInfo apprEntryInfo = editData.getApproachEntrys().get(e.getRowIndex());
					
					if (e.getValue() != null && e.getValue().equals("指定日期")) {
						// 清空单元格
						intervalMonCell.setValue(null);
						intervalDayCell.setValue(null);
						
						// 清空字段值
						if(apprEntryInfo != null){
							apprEntryInfo.setIntervalMonth(0);
							apprEntryInfo.setIntervalDays(0);
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
						if(apprEntryInfo != null){
							apprEntryInfo.setScheduledDate(null);
						}
					}
				}
			}

			public void editStopping(KDTEditEvent e) {

			}

			public void editValueChanged(KDTEditEvent e) {
				// 若当前行的是否结束字段为勾选状态，则取消其他分录的勾选状态
				if (e.getColIndex() == kdtApproachEntries
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
	}

	public void initTableLength() {
		kdtApproachEntries.checkParsed();
		KDTextField formattedTextField = new KDTextField();
		formattedTextField.setMaxLength(30);
		KDTDefaultCellEditor Editor = new KDTDefaultCellEditor(
				formattedTextField);
		kdtApproachEntries.getColumn("name").setEditor(Editor);

		KDTextField remarkField = new KDTextField();
		remarkField.setMaxLength(255);
		kdtApproachEntries.getColumn("remark").setEditor(
				new KDTDefaultCellEditor(remarkField));

		refDateCombo = new KDComboBox();
		kdtApproachEntries.getColumn("refDate").setEditor(new KDTDefaultCellEditor(refDateCombo));
		
		KDNumberTextField monField = new KDNumberTextField();
		monField.setMinimumNumber(new Integer(1));
		monField.setMaximumNumber(new Integer(12));
		kdtApproachEntries.getColumn("intervalMon").setEditor(
				new KDTDefaultCellEditor(monField));

		KDNumberTextField dayField = new KDNumberTextField();
		dayField.setMinimumNumber(new Integer(1));
		dayField.setMaximumNumber(new Integer(31));
		kdtApproachEntries.getColumn("intervalDay").setEditor(
				new KDTDefaultCellEditor(dayField));

		kdtDataEntries.checkParsed();
		kdtDataEntries.getColumn("name").setEditor(Editor);
		kdtDataEntries.getColumn("remark").setEditor(new KDTDefaultCellEditor(remarkField));

		for (int i = 0; i < kdtApproachEntries.getRowCount(); i++) {
			setRefDateEditor(kdtApproachEntries.getRow(i));
			
			//填充参照日期
			Object refDate = this.editData.getApproachEntrys().get(
					kdtApproachEntries.getRow(i).getCell("id").getValue())
					.getReferenceTime();
			kdtApproachEntries.getRow(i).getCell("refDate").setValue(refDate);
			//初始化指定日期的编辑状态
			ICell appointedDateCell = kdtApproachEntries.getRow(i).getCell("appointedDate");
			if(refDate!=null && refDate.toString().equals("指定日期")){
				appointedDateCell.getStyleAttributes().setLocked(false);  //可编辑
			}
			else{
				appointedDateCell.getStyleAttributes().setLocked(true);  //不可编辑
			}
		}
	}

	protected void kdtApproachEntries_editStopped(KDTEditEvent e)
			throws Exception {
		checkRepeat(kdtApproachEntries, "步骤", e);
	}

	protected void kdtDataEntries_editStopped(KDTEditEvent e) throws Exception {
		checkRepeat(kdtDataEntries, "资料", e);
	}

	public void checkRepeat(KDTable table, String str, KDTEditEvent e) {
		IRow row = table.getRow(e.getRowIndex());
		if (row != null) {
			if (e.getColIndex() == 0) {
				if (row.getCell("number").getValue() != null) {
					String number = row.getCell("number").getValue().toString();
					for (int i = 0; i < table.getRowCount(); i++) {
						if (table.getRow(i).getRowIndex() != e.getRowIndex()) {
							IRow row2 = table.getRow(i);
							if (row2.getCell("number").getValue() != null) {
								String number2 = row2.getCell("number")
										.getValue().toString();
								if (number2.equals(number)) {
									MsgBox.showWarning(this, str + "编码不能重复");
									row.getCell("number").setValue("");
								}
							}
						}
					}
				}
			} else if (e.getColIndex() == 1) {
				if (row.getCell("name").getValue() != null) {
					String name = row.getCell("name").getValue().toString();
					for (int i = 0; i < table.getRowCount(); i++) {
						if (table.getRow(i).getRowIndex() != e.getRowIndex()) {
							IRow row2 = table.getRow(i);
							if (row2.getCell("name").getValue() != null) {
								String name2 = row2.getCell("name").getValue()
										.toString();
								if (name2.equals(name)) {
									MsgBox.showWarning(this, str + "名称不能重复");
									row.getCell("name").setValue("");
								}
							}
						}
					}
				}
			}
		}
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
				SysUtil.abort();
			}
		}
		if (this.txtName.getSelectedItem() == null
				|| this.txtName.getSelectedItem().toString().trim().equals("")) {
			MsgBox.showWarning(this, "名称不能为空！");
			SysUtil.abort();
		}
		if (this.combommType.getSelectedItem() == null
				|| this.combommType.getSelectedItem().toString().trim().equals(
						"")) {
			MsgBox.showWarning(this, "款项类别不能为空！");
			SysUtil.abort();
		}
		if (this.kdtApproachEntries.getRowCount() > 0) {
			int isFinishCheckedCount = 0;
			for (int i = 0; i < kdtApproachEntries.getRowCount(); i++) {
				if (kdtApproachEntries.getRow(i).getCell("name").getValue() == null
						|| kdtApproachEntries.getRow(i).getCell("name")
								.getValue().toString().trim().equals("")) {
					MsgBox.showWarning(this, "按揭办理流程-名称不能为空！");
					SysUtil.abort();
				}
				if (kdtApproachEntries.getRow(i).getCell("isFinish").getValue() != null
						&& kdtApproachEntries.getRow(i).getCell("isFinish")
								.getValue().equals(new Boolean(true))) {
					isFinishCheckedCount++;
				}
			}
			if (isFinishCheckedCount == 0) {
				MsgBox.showWarning(this, "按揭办理流程-是否结束不能为空");
				SysUtil.abort();
			} else if (isFinishCheckedCount > 1) {
				MsgBox.showWarning(this, "按揭办理流程-是否结束一栏只能有一条记录勾选");
				SysUtil.abort();
			}
		}
		if (this.kdtDataEntries.getRowCount() > 0) {
			for (int i = 0; i < kdtDataEntries.getRowCount(); i++) {
				if (kdtDataEntries.getRow(i).getCell("name").getValue() == null
						|| kdtDataEntries.getRow(i).getCell("name").getValue()
								.toString().trim().equals("")) {
					MsgBox.showWarning(this, "按揭办理资料-名称不能为空！");
					SysUtil.abort();
				}
			}
		}
		if (this.kdtDataEntries.getRowCount() == 0
				&& this.kdtApproachEntries.getRowCount() == 0) {
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
		for(int i=0; i<this.kdtApproachEntries.getRowCount(); i++){
			ICell name = this.kdtApproachEntries.getCell(i, "name");
			if(name.getValue()!=null && name.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的流程名称长度超过255个字符");
				SysUtil.abort();
			}
			ICell remark = this.kdtApproachEntries.getCell(i, "remark");
			if(remark.getValue()!=null && remark.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的流程说明长度超过255个字符");
				SysUtil.abort();
			}
			ICell refDate = this.kdtApproachEntries.getCell(i, "refDate");
			if(refDate.getValue() == null){
				MsgBox.showInfo("请输入第" + (i+1) + "行的参照日期");
				SysUtil.abort();
			}
			else if("指定日期".equals(refDate.getValue().toString())){
				ICell appointedDate = this.kdtApproachEntries.getCell(i, "appointedDate");
				if(appointedDate.getValue()==null || appointedDate.getValue().toString().length()==0){
					MsgBox.showInfo("请输入第" + (i+1) + "行的指定日期");
					SysUtil.abort();
				}
			}
			else{//参照日期的值不是"指定日期"，检查间隔月和间隔天
				ICell intervalMon = this.kdtApproachEntries.getCell(i, "intervalMon");
				if(intervalMon.getValue()!=null && !"".equals(intervalMon.getValue().toString())){
					int mon = Integer.parseInt(intervalMon.getValue().toString());
					if(mon<0 || mon >12){
						MsgBox.showInfo("第" + (i+1) + "行的间隔月的取值应为0~12");
						SysUtil.abort();
					}
				}
				ICell intervalDay = this.kdtApproachEntries.getCell(i, "intervalDay");
				if(intervalDay.getValue()!=null && !"".equals(intervalDay.getValue().toString())){
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

	/*
	 * 启用了编码规则
	 * 
	 * @seecom.kingdee.eas.fdc.sellhouse.client.AbstractAFMortgagedEditUI#
	 * setAutoNumberByOrg(java.lang.String)
	 */
	protected void setAutoNumberByOrg(String orgType) {
		if (editData.getNumber() == null) {
			try {
				String companyID = null;
				if (!com.kingdee.util.StringUtils.isEmpty(orgType)
						&& !"NONE".equalsIgnoreCase(orgType)
						&& com.kingdee.eas.common.client.SysContext
								.getSysContext().getCurrentOrgUnit(
										com.kingdee.eas.basedata.org.OrgType
												.getEnum(orgType)) != null) {
					companyID = com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit(
									com.kingdee.eas.basedata.org.OrgType
											.getEnum(orgType)).getString("id");
				} else if (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit())
							.getString("id");
				}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
						.getRemoteInstance();
				if (iCodingRuleManager.isExist(editData, companyID)) {// 是否存在指定条件的编码规则对象
					if (iCodingRuleManager.isAddView(editData, companyID)) {// 是否是新增显示
						editData.setNumber(iCodingRuleManager.getNumber(
								editData, companyID));
						// }else
						// if(iCodingRuleManager.isUseIntermitNumber(editData,
						// companyID)){//指定的编码规则是否启用断号支持功能
						// editData.setNumber(iCodingRuleManager.readNumber(
						// editData,companyID));
					}
					txtNumber.setEnabled(false);
				}
			} catch (Exception e) {
				handUIException(e);
				this.oldData = editData;
				com.kingdee.eas.util.SysUtil.abort();
			}
		} else {
			if (editData.getNumber().trim().length() > 0) {
				txtNumber.setText(editData.getNumber());
			}
		}
	}

	public void actionApproachAddRow_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = this.kdtApproachEntries.addRow();
		// 显示设置为false，让界面显示勾选框
		row.getCell("isFinish").setValue(new Boolean(false));
		row.getCell("intervalMon").setValue(new Integer(0));
		row.getCell("intervalDay").setValue(new Integer(0));
		// 设置参照日期
		setRefDateEditor(row);
		// 设置分录是否结束字段的选择状态
		this.autoSelectIsFinishField();
		super.actionApproachAddRow_actionPerformed(e);
	}

	public void actionApproachInsertRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionApproachInsertRow_actionPerformed(e);

		int activeRowIndex = kdtApproachEntries.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0) {
			actionApproachAddRow_actionPerformed(e);
		}
		else{
			IRow row = kdtApproachEntries.addRow(activeRowIndex);
			row.getCell("isFinish").setValue(new Boolean(false));
			row.getCell("intervalMon").setValue(new Integer(0));
			row.getCell("intervalDay").setValue(new Integer(0));
			this.autoSelectIsFinishField();
			
			//初始化参照时间选项
			for (int i = activeRowIndex; i < kdtApproachEntries.getRowCount(); i++) {
				setRefDateEditor(kdtApproachEntries.getRow(i));
			}
		}
	}

	public void actionApproachDeleteRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionApproachDeleteRow_actionPerformed(e);

		int activeRowIndex = kdtApproachEntries.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0) {
			return;
		} else if (!this.isNameUsed(activeRowIndex)) {
			MsgBox.showInfo("流程名称已被引用，不能删除");
			SysUtil.abort();
		}
		kdtApproachEntries.removeRow(activeRowIndex);

		for (int i = activeRowIndex; i < kdtApproachEntries.getRowCount(); i++) {
			setRefDateEditor(kdtApproachEntries.getRow(i));
		}
		this.autoSelectIsFinishField();
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
				if (kdtApproachEntries.getRow(i).getCell("name").getValue() != null) {
					objArray[i + 2] = kdtApproachEntries.getRow(i).getCell("name").getValue();
				}
			}
		}
		refDateCombo.addItems(objArray);

		ICell refDateCell = row.getCell("refDate");
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
		String currentName = (String) kdtApproachEntries.getRow(rowIndex)
				.getCell("name").getValue();
		for (int i = rowIndex + 1; i < kdtApproachEntries.getRowCount(); i++) {
			Object refDateValue = kdtApproachEntries.getRow(i).getCell(
					"refDate").getValue();
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
		int rowCount = this.kdtApproachEntries.getRowCount();
		if (rowCount <= 0) {
			return;
		}
		Boolean trueObj = new Boolean(true);
		for (int i = 0; i < rowCount; i++) {
			if (this.kdtApproachEntries.getRow(i).getCell("isFinish")
					.getValue() != null
					&& this.kdtApproachEntries.getRow(i).getCell("isFinish")
							.getValue().equals(trueObj)) {
				isHasSelected = true;
				break;
			}
		}
		if (!isHasSelected) {
			this.kdtApproachEntries.getRow(rowCount - 1).getCell("isFinish")
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
		for (int i = 0; i < this.kdtApproachEntries.getRowCount(); i++) {
			if (i != currentRowIndex) {
				this.kdtApproachEntries.getRow(i).getCell("isFinish").setValue(
						new Boolean(false));
			}
		}
	}

	public void actionDataAddRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDataAddRow_actionPerformed(e);
		this.kdtDataEntries.addRow();
	}

	public void actionDataInsertRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDataInsertRow_actionPerformed(e);

		int activeRowIndex = kdtDataEntries.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0) {
			actionDataAddRow_actionPerformed(e);
		}
		kdtDataEntries.addRow(activeRowIndex);
	}

	public void actionDataDeleteRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDataDeleteRow_actionPerformed(e);

		int activeRowIndex = kdtDataEntries.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0) {
			return;
		}
		kdtDataEntries.removeRow(activeRowIndex);
	}
	
	/**
	 * 检测方案是否被使用 
	 * @param id - 方案Id
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isAFMortgagedUsed(String id) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("oRSOMortgaged.id", id));
		if(RoomLoanFactory.getRemoteInstance().exists(filter)){
			return true;
		}
		else{
			return false;
		}
	}
}