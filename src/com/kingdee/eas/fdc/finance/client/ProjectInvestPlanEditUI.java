/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.IProjectInvestPlan;
import com.kingdee.eas.fdc.finance.MonthEntryCollection;
import com.kingdee.eas.fdc.finance.MonthEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class ProjectInvestPlanEditUI extends AbstractProjectInvestPlanEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectInvestPlanEditUI.class);

	private List alreadySelectAccount, alreadyIncludeMonth,
			alreadySetValueAccount, entryList;
	private Map setValueMap;

	private Map accountSptMap = null;
	private Map paymentSptMap = null;

	// private KDTPropertyChangeListener accountSum = new
	// AccountSum(alreadySetValueAccount,this.kdtEntry,this);

	/**
	 * output class constructor
	 */
	public ProjectInvestPlanEditUI() throws Exception {
		super();
		alreadySelectAccount = new ArrayList();
	}

	/***
	 * 设置相关按钮的状态
	 */
	public void initButtonStyle() {
		// this.actionAddLine.setVisible(false);
		// this.actionRemoveLine.setVisible(false);
		// this.actionInsertLine.setVisible(false);

		this.actionRemove.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);

		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(true);
		this.actionCopyFrom.setVisible(false);

		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyLine.setVisible(false);

		this.btnOk.setEnabled(true);
		if (getOprtState().equals(OprtState.VIEW)) {
			this.btnSetAccountLevel.setEnabled(false);
			// this.btnOk.setEnabled(false);
		}

		kdtEntry.getColumn("account.number").getStyleAttributes().setLocked(
				true);
		kdtEntry.getColumn("account.name").getStyleAttributes().setLocked(true);
		if (getUIContext().get("recension") != null) {
			String recension = getUIContext().get("recension").toString();
			if (recension.equals("true")) {
				txtNumber.setEnabled(false);
				txtName.setEnabled(false);
				actionSave.setEnabled(false);
			}
		}
		if (getOprtState().equals(this.STATUS_VIEW)) {
			actionRemoveLine.setEnabled(false);
		}
		if (getOprtState().equals(this.STATUS_EDIT)
				&& this.editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			actionSave.setEnabled(false);
		}
		if (!getOprtState().equals(this.STATUS_ADDNEW)) {
			if (this.editData.getVersionNumber().compareTo(
					new BigDecimal("1.0")) != 0) {
				txtNumber.setEnabled(false);
				txtName.setEnabled(false);
				actionSave.setEnabled(false);
			}
		}
	}

	/**
	 * output storeFields method
	 */
	public void actionBtnOk_actionPerformed(ActionEvent e) throws Exception {
		addColumn(getValueByKDSpinner(kdsStartYear),
				getValueByKDSpinner(kdsEndYear),
				getValueByKDSpinner(kdsStartDay),
				getValueByKDSpinner(kdsEndDay));
		initAdjustAmountObjectCost();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.editData.setState(FDCBillStateEnum.SUBMITTED);
		if (getUIContext().get("recension") != null) {
			String recension = getUIContext().get("recension").toString();
			if (recension.equals("true")) {
				// 新增修订数据
				String oldID = this.editData.getId().toString();
				editData.setId(null);
				editData.setBizDate(new Date());
				editData.setAuditor(null);
				editData.setAuditTime(null);
				BigDecimal number = new BigDecimal("1.0");
				if (editData.getVersionNumber() != null) {
					number = editData.getVersionNumber();
				}
				editData.setVersionNumber(number.add(new BigDecimal(0.1)));
				for (int i = 0; i < editData.getEntry().size(); i++) {
					editData.getEntry().get(i).setId(null);
					for (int j = 0; j < editData.getEntry().get(i)
							.getMonthEntry().size(); j++) {
						editData.getEntry().get(i).getMonthEntry().get(j)
								.setId(null);
					}
				}
				super.actionSubmit_actionPerformed(e);
				// 修改旧数据
				IProjectInvestPlan ip = ProjectInvestPlanFactory
						.getRemoteInstance();
				ProjectInvestPlanInfo oldInfo = ip
						.getProjectInvestPlanInfo(new ObjectUuidPK(oldID));
				oldInfo.setState(FDCBillStateEnum.REVISING);
				ip.update(new ObjectUuidPK(oldID), oldInfo);
				getUIContext().put("recension", "false");
			} else {
				super.actionSubmit_actionPerformed(e);
			}
		} else {
			super.actionSubmit_actionPerformed(e);
		}
		// this.editData = null;
	}

	protected void afterSubmitEdit(IObjectPK pk) {
		super.afterSubmitEdit(pk);
		// this.kdtEntry.removeRows();
		// for(int j=kdtEntry.getColumnCount();j>=2;j--){
		// kdtEntry.removeColumn(j);
		// }
		//    	
		// this.txtName.setText(null);
		// this.txtNumber.setText(null);
		// this.editData = null;
		// this.setOprtState(OprtState.ADDNEW);
	}

	protected void afterSubmitAddNew() {
		super.afterSubmitAddNew();
		// this.txtProject.setText(editData.getProject().getName());
		for (int j = kdtEntry.getColumnCount(); j >= 2; j--) {
			kdtEntry.removeColumn(j);
		}

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if (getOprtState().equals(this.STATUS_ADDNEW)) {
			return;
		}
		Map uiContext = getUIContext();
		CurProjectInfo project = (CurProjectInfo) uiContext.get("curProject");
		if (isHavePlanByProjectId(project.getId().toString())) {
			super.actionAddNew_actionPerformed(e);
		} else {
			FDCMsgBox.showError("当前工程项目已存在项目全生命周期资金投入计划，不能重复新增");
			abort();
		}
	}

	protected boolean isHavePlanByProjectId(String projectId) {
		boolean isHavePlan = true;
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));
		viewInfo.setFilter(filter);
		ProjectInvestPlanCollection cols = null;
		try {
			cols = ProjectInvestPlanFactory.getRemoteInstance()
					.getProjectInvestPlanCollection(viewInfo);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (cols != null && cols.size() > 0) {
			isHavePlan = false;
		}
		return isHavePlan;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getState() != null
				&& !(this.editData.getState().equals(FDCBillStateEnum.SAVED)
						|| this.editData.getState().equals(
								FDCBillStateEnum.SUBMITTED) || this.editData
						.getState().equals(FDCBillStateEnum.AUDITTED))) {
			FDCMsgBox.showError("当前单据状态为" + editData.getState() + "不能执行修改操作！");
			abort();
		}
		if (editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			this.btnSetAccountLevel.setEnabled(false);
		} else {
			this.btnSetAccountLevel.setEnabled(true);
		}

		BOSUuid id = editData.getId();
		if (id != null && FDCUtils.isRunningWorkflow(id.toString())) {
			MsgBox.showWarning(this, "单据已在工作流中，不可修改!");
			SysUtil.abort();
		}

		super.actionEdit_actionPerformed(e);
		this.btnAttachment.setVisible(false);
		this.actionRemoveLine.setEnabled(true);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getState() != null
				&& !(this.editData.getState().equals(FDCBillStateEnum.SAVED) || this.editData
						.getState().equals(FDCBillStateEnum.SUBMITTED))) {
			FDCMsgBox.showError("当前单据状态为" + editData.getState() + "不能执行删除操作！");
			abort();
		}
		String id = this.editData.getId().toString();
		IProjectInvestPlan ip = ProjectInvestPlanFactory.getRemoteInstance();
		ProjectInvestPlanInfo info = ip
				.getProjectInvestPlanInfo(new ObjectUuidPK(id));
		BigDecimal versionNumber = info.getVersionNumber();
		String number = info.getNumber();
		String project = info.getProject().getId().toString();
		super.actionRemove_actionPerformed(e);
		// 修改旧版本状态
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("update T_FNC_ProjectInvestPlan set fstate='4AUDITTED' "
						+ "where FProjectID='"
						+ project
						+ "' and fnumber='"
						+ number
						+ "' "
						+ "and FVersionNumber="
						+ versionNumber.subtract(new BigDecimal("0.1")) + "");
		builder.execute();
	}

	IRow willDeleteRow = null;

	protected void afterRemoveLine(KDTable table, IObjectValue lineData) {
		System.out.println(lineData);
		IRow row = willDeleteRow;
		if (row != null) {
			CostAccountInfo info = (CostAccountInfo) row
					.getCell("account.name").getUserObject();
			CostAccountInfo pInfo = null;
			BigDecimal newValue = FDCHelper.ZERO;
			BigDecimal cellValue = FDCHelper.ZERO;
			Set pSet = new HashSet();
			getAllParentAccount(info, pSet);
			if (pSet.size() > 0) {
				for (int i = 2; i < kdtEntry.getColumnCount(); i++) {
					cellValue = FDCHelper.toBigDecimal(row.getCell(i)
							.getValue(), 2);
					newValue = FDCHelper.subtract(FDCHelper.ZERO, cellValue);
					for (Iterator it = pSet.iterator(); it.hasNext();) {
						pInfo = (CostAccountInfo) it.next();
						setData(pInfo, newValue, i);
					}

				}
			}
		}

		super.afterRemoveLine(table, lineData);

	}

	public void beforeActionPerformed(ActionEvent e) {
		if (e.getActionCommand().endsWith("ActionRemoveLine")) {
			willDeleteRow = kdtEntry.getRow(kdtEntry.getSelectManager()
					.getActiveRowIndex());
		}
		super.beforeActionPerformed(e);
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		int[] index = KDTableUtil.getSelectedRows(kdtEntry);
		for (int i = 0; i < index.length; i++) {
			IRow row = kdtEntry.getRow(index[i]);
			CostAccountInfo account = (CostAccountInfo) row.getCell(
					"account.name").getUserObject();
			String accountLongNumber = account.getLongNumber()
					.replace('!', '.');
			for (int j = 0; j < kdtEntry.getRowCount(); j++) {
				String accountNumber = kdtEntry.getCell(j, "account.number")
						.getValue().toString();
				if (accountNumber.indexOf(accountLongNumber) == 0
						&& index[i] != j) {
					MsgBox.showWarning("所选上级科目包含未删除的下级科目！");
					SysUtil.abort();
				}
			}
		}
		super.actionRemoveLine_actionPerformed(e);
		initAdjustAmountObjectCost();
	}

	/**
	 * 删除 或 更改科目后 重新初始化 目标成本 调整数
	 */
	public void initAdjustAmountObjectCost() {
		// 删除所有上级科目的 目标成本 调整数
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			IRow row = kdtEntry.getRow(i);
			CostAccountInfo account = (CostAccountInfo) row.getCell(
					"account.name").getUserObject();
			if (!account.isIsLeaf()) {
				row.getCell("objectCost").setValue(null);
				row.getCell("adjustAmount").setValue(null);
			}
		}
		// 初始化 目标成本
		initObjectCost();
		// 初始化上级科目的 调整数
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			IRow row = kdtEntry.getRow(i);
			CostAccountInfo account = (CostAccountInfo) row.getCell(
					"account.name").getUserObject();
			if (account.isIsLeaf()) {
				BigDecimal newValue = FDCHelper.ZERO;
				if (row.getCell("adjustAmount").getValue() != null) {
					newValue = FDCHelper.toBigDecimal(row.getCell(
							"adjustAmount").getValue(), 2);
				}
				CostAccountInfo info = (CostAccountInfo) kdtEntry.getCell(i,
						"account.name").getUserObject();
				CostAccountInfo pAccount = null;
				Set accountSet = new HashSet();
				getAllParentAccount(info, accountSet);
				if (accountSet.size() > 0) {
					for (Iterator it = accountSet.iterator(); it.hasNext();) {
						pAccount = (CostAccountInfo) it.next();
						setData(pAccount, newValue, 3);
					}
				}
			}
		}
	}

	public void storeFields() {

		super.storeFields();
		addColumn(getValueByKDSpinner(kdsStartYear),
				getValueByKDSpinner(kdsEndYear),
				getValueByKDSpinner(kdsStartDay),
				getValueByKDSpinner(kdsEndDay));

		Calendar cal = Calendar.getInstance();
		cal.set(getValueByKDSpinner(kdsStartYear),
				getValueByKDSpinner(kdsStartDay) - 1, 1);
		editData.setStartDate(cal.getTime());

		cal.set(getValueByKDSpinner(kdsEndYear),
				getValueByKDSpinner(kdsEndDay) - 1, 1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		editData.setEndDate((DateTimeUtils.truncateDate(cal.getTime())));
		IRow headRow = kdtEntry.getHeadRow(0);
		BigDecimal value = FDCHelper.ZERO;
		editData.getEntry().clear();
		ProjectInvestPlanEntryInfo entry = null;
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			IRow row = kdtEntry.getRow(i);
			CostAccountInfo account = (CostAccountInfo) row.getCell(
					"account.name").getUserObject();
			entry = new ProjectInvestPlanEntryInfo();
			entry.setAccount(account);
			MonthEntryInfo monthEntry = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			for (int j = 4; j < kdtEntry.getColumnCount(); j++) {
				monthEntry = new MonthEntryInfo();
				String monthString = headRow.getCell(j).getValue().toString();
				try {
					monthEntry.setMonth(sdf.parse(monthString + "01日"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// // 计划完工工程量
				// if (kdtEntry.getCell(i, j).getValue() != null&&!"".equals(
				// kdtEntry.getCell(i, j).getValue().toString())) {
				// value = FDCHelper.toBigDecimal(kdtEntry.getCell(i, j)
				// .getValue());
				// } else {
				// value = FDCHelper.ZERO;
				// }
				// monthEntry.setInvestValue(value);
				//
				// j++;

				// 计划投入资金
				if (kdtEntry.getCell(i, j).getValue() != null
						&& !"".equals(kdtEntry.getCell(i, j).getValue()
								.toString())) {
					value = FDCHelper.toBigDecimal(kdtEntry.getCell(i, j)
							.getValue());
				} else {
					value = FDCHelper.ZERO;
				}
				monthEntry.setInvestValue(value);
				entry.getMonthEntry().add(monthEntry);
			}
			editData.getEntry().add(entry);
		}
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	public List fechInitData() {
		List entryList = null;
		Map paramMap = new HashMap();
		if (editData.getId() != null) {
			paramMap.put("billId", editData.getId().toString());
			try {
				entryList = (List) ProjectInvestPlanFactory.getRemoteInstance()
						.fetchInitData(paramMap).get("entry");
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return entryList;
	}

	public void onShow() throws Exception {
		super.onShow();
		kdtEntry.setRefresh(false);
		kdtEntry.getSortMange().setSortAuto(false);
		kdtEntry.getHeadRow(0).getStyleAttributes().setLocked(true);

		kdtEntry.setSortMange(null);
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		initKDSpinner();
		if (editData.getStartDate() != null) {
			this.kdsStartYear.getModel().setValue(
					(new Integer(editData.getStartDate().getYear() + 1900)));
			this.kdsEndYear.getModel().setValue(
					new Integer(editData.getEndDate().getYear() + 1900));
			this.kdsStartDay.setValue(new Integer(editData.getStartDate()
					.getMonth() + 1));
			this.kdsEndDay.getModel().setValue(
					new Integer(editData.getEndDate().getMonth() + 1));
		}
		this.btnSetAccountLevel.setEnabled(true);
		Map uiContext = getUIContext();
		if (uiContext.get("curProject") != null
				&& getOprtState().equals(this.STATUS_ADDNEW)) {
			CurProjectInfo project = (CurProjectInfo) uiContext
					.get("curProject");
			this.editData.setProject(project);
			this.txtProject.setText(editData.getProject().getName());
		}

		FDCTableHelper.disableDelete(this.kdtEntry);
		FDCTableHelper.disableAutoAddLine(this.kdtEntry);
		FDCTableHelper.disableAutoAddLineDownArrow(this.kdtEntry);

		/**
		 * 增加统计事件
		 */

		// this.kdtEntry.addKDTPropertyChangeListener(accountSum);
		this.kdtEntry.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent e) {

			}

			public void editStarted(KDTEditEvent e) {

			}

			public void editStarting(KDTEditEvent e) {

			}

			public void editStopped(KDTEditEvent e) {

			}

			public void editStopping(KDTEditEvent e) {
				KDTable tb = (KDTable) e.getSource();
				BigDecimal oldValue = FDCHelper.ZERO;
				BigDecimal newValue = FDCHelper.ZERO;
				if (e.getOldValue() != null) {
					oldValue = FDCHelper.toBigDecimal(e.getOldValue(), 2);
				}
				if (e.getValue() != null) {
					newValue = FDCHelper.toBigDecimal(e.getValue(), 2);
				}
				newValue = FDCHelper.subtract(newValue, oldValue);
				int colIndex = tb.getSelectManager().getActiveColumnIndex();
				int rowIndex = tb.getSelectManager().getActiveRowIndex();
				CostAccountInfo info = (CostAccountInfo) tb.getCell(rowIndex,
						"account.name").getUserObject();
				CostAccountInfo pAccount = null;
				Set accountSet = new HashSet();
				getAllParentAccount(info, accountSet);
				if (accountSet.size() > 0) {
					for (Iterator it = accountSet.iterator(); it.hasNext();) {
						pAccount = (CostAccountInfo) it.next();
						setData(pAccount, newValue, colIndex);
					}
				}

			}

			public void editValueChanged(KDTEditEvent e) {

			}
		});

		initButtonStyle();
		// initHeadMergeManager();

	}

	protected void getActual(String startMonth, int count) {
		return;
	}

	protected void getMonthRoll(String startMonth, int count) {
		return;
	}

	protected void initDataStatus() {
		super.initDataStatus();
		if (getUIContext().get("recension") != null) {
			String recension = getUIContext().get("recension").toString();
			if (recension.equals("true")) {
				txtNumber.setEnabled(false);
				txtName.setEnabled(false);
				actionSave.setEnabled(false);
			}
		}
	}

	/***
	 * 获取该科目的所有上级科目
	 */
	protected void getAllParentAccount(CostAccountInfo costAccount, Set set) {
		CostAccountInfo info = costAccount;
		if (info.getParent() != null) {
			set.add(info.getParent());
			info = info.getParent();
			getAllParentAccount(info, set);
		}
	}

	public void setData(CostAccountInfo pAccount, BigDecimal newVal,
			int colIndex) {
		try {
			ICostAccount ic = CostAccountFactory.getRemoteInstance();
			pAccount = ic.getCostAccountInfo(new ObjectUuidPK(pAccount.getId()
					.toString()));
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			if (kdtEntry.getCell(i, "account.number").getValue().equals(
					pAccount.getLongNumber().replace('!', '.'))) {
				BigDecimal orginalVal = FDCHelper.toBigDecimal(kdtEntry
						.getCell(i, colIndex).getValue(), 2);
				kdtEntry.getCell(i, colIndex).setValue(
						FDCHelper.add(orginalVal, newVal).divide(
								new BigDecimal("1"), 2, 2));

			}
		}
	}

	public void initKDSpinner() {
		Calendar cal = Calendar.getInstance();
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1900));
		model.setMaximum(new Integer(3000));
		model.setStepSize(new Integer(1));
		model.setValue(new Integer(cal.get(Calendar.YEAR)));
		this.kdsStartYear.setModel(model);
		model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1900));
		model.setMaximum(new Integer(3000));
		model.setStepSize(new Integer(1));
		model.setValue(new Integer(cal.get(Calendar.YEAR)));
		this.kdsEndYear.setModel(model);
		model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		model.setValue(new Integer(cal.get(Calendar.MONTH) + 1));
		this.kdsStartDay.setModel(model);
		model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		model.setValue(new Integer(cal.get(Calendar.MONTH) + 1));
		this.kdsEndDay.setModel(model);

	}

	/**
	 * /** output actionSetCostAccountLevel_actionPerformed
	 */
	public void actionSetCostAccountLevel_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionSetCostAccountLevel_actionPerformed(e);
		setValueMap = getAlreadySetValueAccountList();
		if (editData.getProject() != null) {
			getUIContext().put("curProject", editData.getProject());
			getUIContext().put("parentUI", this);
			getUIContext().put("alreadySelectAccount", alreadySelectAccount);
			getUIContext()
					.put("alreadySetValueAccount", alreadySetValueAccount);
			getUIContext().put("setValueMap", setValueMap);
			IUIFactory uiFactory = UIFactory
					.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(SetCostAccountLevelUI.class
					.getName(), getUIContext());
			uiWindow.show();

		} else {
			return;
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	public boolean isModify() {
		return false;
	}

	public boolean destroyWindow() {
		return super.destroyWindow();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectInvestPlanFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		ProjectInvestPlanInfo objectValue = new ProjectInvestPlanInfo();
		objectValue.setVersionNumber(new BigDecimal("1.0"));
		objectValue.setBizDate(new Date());
		return objectValue;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		ProjectInvestPlanEntryInfo entry = new ProjectInvestPlanEntryInfo();
		return entry;
	}

	protected void fetchInitData() throws Exception {
		super.fetchInitData();
	}

	public void fillCostAccount(List accountList) {
		IRow row = null;
		kdtEntry.removeRows();
		int maxLevel = 0;
		for (Iterator it = accountList.iterator(); it.hasNext();) {
			CostAccountInfo account = (CostAccountInfo) it.next();
			if (account != null) {
				if (maxLevel < account.getLevel()) {
					maxLevel = account.getLevel();
				}
				if (setValueMap != null
						&& setValueMap.containsKey(account.getId().toString())) {
					IRow mapRow = (IRow) setValueMap.get(account.getId()
							.toString());
					row = this.kdtEntry.addRow();
					row.setTreeLevel(account.getLevel() - 1);
					for (int i = 0; i < kdtEntry.getColumnCount(); i++) {
						row.getCell(i).setValue(mapRow.getCell(i).getValue());
					}
					row.getCell("account.name").setUserObject(account);
				} else {
					row = this.kdtEntry.addRow();
					row.setTreeLevel(account.getLevel() - 1);
					row.getCell("account.name").setUserObject(account);
					row.getCell("account.number").setValue(
							account.getLongNumber().replace('!', '.'));
					row.getCell("account.name").setValue(account.getName());
				}
			}
		}
		kdtEntry.getTreeColumn().setDepth(maxLevel);
		initRowLocked();
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			try {
				initAdjustAmount(i);
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		initAdjustAmountObjectCost();
		alreadySelectAccount = accountList;
	}

	/**
	 * 
	 */
	private void initRowLocked() {
		int rowCount = kdtEntry.getRowCount();
		IRow row = kdtEntry.getRow(0);
		for (int i = 0; i < rowCount; i++) {
			if (i != rowCount - 1) {
				IRow nextRow = kdtEntry.getRow(i + 1);
				if (row.getTreeLevel() == nextRow.getTreeLevel() - 1) {
					row.getStyleAttributes().setLocked(true);
					row.getStyleAttributes().setBackground(
							FDCColorConstants.lockColor);
				}
				row = nextRow;
			}
		}
	}

	/**
	 * 初始化 目标成本
	 */
	public void initObjectCost() {

		Map param = new HashMap();
		if (editData.getProject() != null) {
			param.put("prjId", editData.getProject().getId().toString());
			try {
				Map costAccout = (Map) FDCCostRptFacadeFactory
						.getRemoteInstance().getCostMap(param).get("aimCost");
				for (int i = 0; i < kdtEntry.getRowCount(); i++) {
					IRow row = kdtEntry.getRow(i);
					CostAccountInfo account = (CostAccountInfo) row.getCell(
							"account.name").getUserObject();
					if (account != null) {
						if (account.isIsLeaf()) {
							Object costAmt = costAccout.get(account.getId()
									.toString());
							row.getCell("objectCost").setValue(costAmt);
						} else {
							EntityViewInfo view = new EntityViewInfo();
							SelectorItemCollection sic = new SelectorItemCollection();
							sic.add("id");
							view.setSelector(sic);
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(
									new FilterItemInfo("curProject.id", account
											.getCurProject().getId()));
							filter.getFilterItems().add(
									new FilterItemInfo("isEnabled",
											Boolean.TRUE));
							filter.getFilterItems().add(
									new FilterItemInfo("isLeaf", Boolean.TRUE));
							filter.getFilterItems().add(
									new FilterItemInfo("longNumber", account
											.getLongNumber()
											+ "%", CompareType.LIKE));
							view.setFilter(filter);
							CostAccountCollection col = CostAccountFactory
									.getRemoteInstance()
									.getCostAccountCollection(view);
							if (col != null && col.size() > 0) {
								BigDecimal total = FDCHelper.ZERO;
								for (int j = 0; j < col.size(); j++) {
									String leafID = col.get(j).getId()
											.toString();
									BigDecimal leafAmt = (BigDecimal) costAccout
											.get(leafID);
									if (leafAmt != null) {
										total = total.add(leafAmt);
									}
								}
								row.getCell("objectCost").setValue(total);
							}
						}
					} else {
						row.getCell("objectCost").setValue(FDCHelper.ZERO);
					}
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
		}
		//		
		// ICostEntry icost = null;
		// try {
		// icost = CostEntryFactory.getRemoteInstance();
		// } catch (BOSException e) {
		// e.printStackTrace();
		// }
		// CostEntryInfo costInfo = null;
		// CostEntryCollection costColl = null;
		// BigDecimal costAmount = null;
		// EntityViewInfo view = null;
		// FilterInfo filter = null;
		// for (int i = 0; i < kdtEntry.getRowCount(); i++) {
		// IRow row = kdtEntry.getRow(i);
		// CostAccountInfo account = (CostAccountInfo) row.getCell(
		// "account.name").getUserObject();
		// if (account.isIsLeaf()) {
		// view = new EntityViewInfo();
		// filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("costAccount.id", account.getId()
		// .toString()));
		// filter.getFilterItems().add(
		// new FilterItemInfo("head.isLastVersion", Boolean.TRUE));
		// filter.getFilterItems().add(
		// new FilterItemInfo("head.state",
		// FDCBillStateEnum.AUDITTED_VALUE));
		// Map uiContext = getUIContext();
		// if (uiContext.get("curProject") != null) {
		// CurProjectInfo project = (CurProjectInfo) uiContext
		// .get("curProject");
		// filter.getFilterItems().add(
		// new FilterItemInfo("head.orgOrProId", project
		// .getId().toString()));
		// }
		// view.setFilter(filter);
		// try {
		// costColl = icost.getCostEntryCollection(view);
		// if (costColl != null && costColl.size() > 0) {
		// costInfo = icost.getCostEntryCollection(view).get(0);
		// costAmount = costInfo.getCostAmount();
		// } else {
		// costAmount = new BigDecimal("0.00");
		// }
		// } catch (BOSException e) {
		// e.printStackTrace();
		// }
		//
		// row.getCell("objectCost").setValue(
		// costAmount.divide(new BigDecimal("1"), 2, 2));
		// CostAccountInfo pAccount = null;
		// Set accountSet = new HashSet();
		// getAllParentAccount(account, accountSet);
		// if (accountSet.size() > 0) {
		// for (Iterator it2 = accountSet.iterator(); it2.hasNext();) {
		// pAccount = (CostAccountInfo) it2.next();
		// setData(pAccount, costAmount, 2);
		// }
		// }
		// }
		// }
	}

	/***
	 * 获取所有已设置值的科目的行
	 */
	protected Map getAlreadySetValueAccountList() {
		Map map = null;
		int n = 0;
		String accountId = null;
		BigDecimal investValue = FDCHelper.ZERO;
		// 已有月份投资信息()
		if (this.kdtEntry.getColumnCount() > 2) {
			map = new HashMap();
			for (int i = 0; i < kdtEntry.getRowCount(); i++) {
				CostAccountInfo info = (CostAccountInfo) kdtEntry.getCell(i,
						"account.name").getUserObject();
				n = 0;
				// 循环所有的列，如果遇到任意一列有数据，由跳出循环，保存这个行
				for (int j = 2; j < kdtEntry.getColumnCount(); j++) {
					investValue = FDCHelper.toBigDecimal(kdtEntry.getCell(i, j)
							.getValue());
					accountId = info.getId().toString();
					if (investValue.compareTo(FDCHelper.ZERO) != 0) {
						map.put(accountId, kdtEntry.getRow(i));
						n++;
					}

					// 如果找到该行上有一个月份已经设置了投资数，则跳出循环！
					if (n > 0) {
						j = kdtEntry.getColumnCount() + 1;
					}
				}

			}
		}
		return map;
	}

	/**
	 * 从KDSpiner取值
	 * 
	 * @param s
	 * @return
	 */
	public int getValueByKDSpinner(KDSpinner s) {
		return ((Integer) s.getValue()).intValue();
	}

	/***
	 * 根据时间区间来添加相应的列
	 * 
	 * @param startYear
	 * @param endYear
	 * @param startDay
	 * @param endDay
	 */
	public void addColumn(int startYear, int endYear, int startDay, int endDay) {

		List removeList = new ArrayList();

		if (startYear > endYear || (startYear == endYear && startDay > endDay)) {
			FDCMsgBox.showError("计划截止时间必须大于开始时间");
			abort();
		}

		int newStartYear = startYear;
		List monthList = new ArrayList();
		if (startYear == endYear) {
			// 开始年份与结束年份在同一年
			for (int i = startDay; i <= endDay; i++) {
				monthList.add(startYear + "_" + i);
			}
		} else {
			// 不是同一年，循环年份，直到年份等于截止年份
			while (startYear <= endYear) {
				if (startYear == newStartYear) {
					for (int i = startDay; i <= 12; i++) {
						monthList.add(startYear + "_" + i);
					}
				} else if (startYear == endYear) {
					for (int i = 1; i <= endDay; i++) {
						monthList.add(startYear + "_" + i);
					}
				} else {
					for (int i = 1; i <= 12; i++) {
						monthList.add(startYear + "_" + i);
					}
				}
				startYear++;
			}

		}
		IColumn col2 = null;
		IRow row2 = null;
		boolean isNeedAddNewColumn = false;
		/**
		 * 修改的时候可能存在要添加月份
		 */
		if (alreadyIncludeMonth != null
				&& alreadyIncludeMonth.size() < monthList.size()) {

		}

		// 存在要删除的月份
		if (alreadyIncludeMonth != null) {
			for (Iterator it = alreadyIncludeMonth.iterator(); it.hasNext();) {
				String month = (String) it.next();
				if (!monthList.contains(month)) {
					removeList.add(month);
				}
			}
		}

		if (removeList.size() > 0) {
			for (int i = 0; i < removeList.size(); i++) {
				kdtEntry.removeColumn(kdtEntry.getColumnIndex(removeList.get(i)
						.toString()
						+ "investValue"));
			}
		}

		for (int i = 0; i < monthList.size(); i++) {

			if (alreadyIncludeMonth == null) {
				isNeedAddNewColumn = true;
			} else {
				if (alreadyIncludeMonth.contains(monthList.get(i))) {
					isNeedAddNewColumn = false;
				} else {
					isNeedAddNewColumn = true;
				}
			}

			if (isNeedAddNewColumn) {

				String month = ((String) monthList.get(i)).replace('_', '年')
						+ "月";

				col2 = kdtEntry.addColumn();
				// col2.setSortable(false);
				col2.setKey((String) monthList.get(i) + "investValue");
				col2.setUserObject(month);
				col2.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
				col2.getStyleAttributes().setNumberFormat("######.00");

				KDFormattedTextField ed = new KDFormattedTextField();
				ed.setDataType(BigDecimal.class);
				KDTDefaultCellEditor editor = new KDTDefaultCellEditor(ed);
				col2.setEditor(editor);
				row2 = kdtEntry.getHeadRow(0);
				row2.getCell(kdtEntry.getColumnCount() - 1).setValue(month);
			}
		}

		alreadyIncludeMonth = monthList;
	}

	public void initHeadMergeManager() {
		KDTMergeManager mm = kdtEntry.getHeadMergeManager();
		mm.removeAllMergeBlock();
		// mm.mergeBlock(0, 0, 1, 0, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(0, 1, 1, 1, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(0, 2, 1, 2, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(0, 3, 1, 3, KDTMergeManager.SPECIFY_MERGE);
		// 进行指定融合
		// for (int i = 0; i < (kdtEntry.getColumnCount() - 4) / 2; i++) {
		// mm.mergeBlock(0, 4 + i * 2, 0, 5 + i * 2,
		// KDTMergeManager.SPECIFY_MERGE);
		// }
	}

	/**
	 * 根据月度区间来添加列
	 * 
	 * @param monthList
	 */
	// public void fillEntryTable(List monthList){
	// if(monthList == null){
	// return ;
	// }
	// IColumn col = null;
	// IRow row = null;
	// for (int i = 0; i < monthList.size(); i++) {
	// col = kdtEntry.addColumn();
	// String month = (String) monthList.get(i);
	// col.setKey(month);
	// col.setUserObject(month);
	// col.getStyleAttributes().setHorizontalAlign(
	// HorizontalAlignment.RIGHT);
	// col.getStyleAttributes().setNumberFormat("######.00");
	// KDFormattedTextField ed = new KDFormattedTextField();
	// ed.setDataType(BigDecimal.class);
	// KDTDefaultCellEditor editor = new KDTDefaultCellEditor(ed);
	// col.setEditor(editor);
	// row = kdtEntry.getHeadRow(0);
	// row.getCell(kdtEntry.getColumnCount() - 1).setValue(month);
	// }
	//		
	// }
	public void loadFields() {
		super.loadFields();

		// 初始化科目拆分金额
		initAccountSptMap();
		// 初始化付款拆分金额
		initPaymentSptMap();

		entryList = fechInitData();
		editData.getEntry().clear();
		if (entryList != null) {
			for (Iterator it = entryList.iterator(); it.hasNext();) {
				editData.getEntry().add((ProjectInvestPlanEntryInfo) it.next());
			}
		}
		ProjectInvestPlanEntryCollection cols = editData.getEntry();
		if (editData.getStartDate() != null) {
			addColumn(editData.getStartDate().getYear() + 1900, editData
					.getEndDate().getYear() + 1900, editData.getStartDate()
					.getMonth() + 1, editData.getEndDate().getMonth() + 1);
		}

		if (cols.size() > 0) {
			fillEntryTableValue();
			for (int i = 0; i < cols.size(); i++) {

			}
		}

		initObjectCost();

		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			IRow row = kdtEntry.getRow(i);
			CostAccountInfo account = (CostAccountInfo) row.getCell(
					"account.name").getUserObject();
			if (account.isIsLeaf()) {
				try {
					initAdjustAmount(i);
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void fillEntryTableValue() {
		kdtEntry.removeRows();
		IRow row = null;
		Date d = null;
		int maxLevel = 0;
		java.beans.PropertyChangeListener[] listener = kdtEntry
				.getPropertyChangeListeners();
		for (Iterator it = editData.getEntry().iterator(); it.hasNext();) {
			ProjectInvestPlanEntryInfo info = (ProjectInvestPlanEntryInfo) it
					.next();
			if (maxLevel < info.getAccount().getLevel()) {
				maxLevel = info.getAccount().getLevel();
			}
			alreadySelectAccount.add(info.getAccount());
			row = kdtEntry.addRow();
			row.getCell("account.number").setValue(
					info.getAccount().getLongNumber().replace('!', '.'));
			row.getCell("account.name").setValue(info.getAccount().getName());
			row.getCell("account.name").setUserObject(info.getAccount());
			row.setTreeLevel(info.getAccount().getLevel() - 1);
			// if (!info.getAccount().isIsLeaf()) {
			// row.getStyleAttributes().setLocked(true);
			// row.getStyleAttributes().setBackground(
			// FDCColorConstants.lockColor);
			// }
			MonthEntryCollection mcols = info.getMonthEntry();
			for (Iterator mIt = mcols.iterator(); mIt.hasNext();) {
				MonthEntryInfo m = (MonthEntryInfo) mIt.next();
				d = m.getMonth();
				String month = d.getYear() + 1900 + "_" + (d.getMonth() + 1);

				BigDecimal investAmount = m.getInvestValue();
				investAmount = investAmount == null ? FDCHelper.ZERO
						: investAmount;
				row.getCell(month + "investValue").setValue(
						FDCHelper.ZERO.compareTo(investAmount) != 0 ? m
								.getInvestValue() : null);
			}
		}
		kdtEntry.getTreeColumn().setDepth(maxLevel);
		initRowLocked();
		// kdtEntry.addKDTPropertyChangeListener(accountSum);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("project.*"));
		sic.add(new SelectorItemInfo("*"));
		// sic.add(new SelectorItemInfo("entry.monthEntry.*"));
		sic.add(new SelectorItemInfo("entry.account.*"));
		return sic;
	}

	protected void verifyInputForSubmint() throws Exception {
		// super.verifyInputForSubmint();
		setValueMap = getAlreadySetValueAccountList();
		if (this.txtNumber.isEnabled()) {
			if (editData.getNumber() == null
					|| this.editData.getNumber().trim().length() == 0) {
				FDCMsgBox.showError("编号不能为空！");
				abort();
			}
		}

		if (editData.getName() == null || editData.getName().length() == 0) {
			FDCMsgBox.showError("单据名称不能为空！");
			abort();
		}

		if (this.txtProject.getText() == null
				|| this.txtProject.getText().trim().length() == 0) {
			FDCMsgBox.showError("工程项目为空或者已编制过该项目的投资计划！");
			abort();
		}

		if (this.kdtEntry.getRowCount() == 0) {
			FDCMsgBox.showError("分录为空！");
			abort();
		} else if (this.kdtEntry.getColumnCount() < 5) {
			FDCMsgBox.showError("至少需要编制一个月的资金投入计划！");
			abort();
		} else {
			int n = 0;
			StringBuffer str = new StringBuffer();
			str.append("当前分录中,有以下错误：\n");
			for (int i = 0; i < kdtEntry.getRowCount(); i++) {
				if (kdtEntry.getCell(i, "account.number").getValue() == null) {
					str.append("第");
					str.append(i + 1);
					str.append("行的科目编码为空\n");
					n++;
				}
			}
			if (n > 0) {
				FDCMsgBox.showError(str.toString());
				abort();
			}
			if (setValueMap == null || setValueMap.isEmpty()) {
				FDCMsgBox.showError("至少需要存在一个成本科目的计划投入资金不为空！");
				abort();
			}
		}
		Map uiContext = getUIContext();
		CurProjectInfo project = (CurProjectInfo) uiContext.get("curProject");
		if (project != null
				&& !isHavePlanByProjectId(project.getId().toString())
				&& getOprtState().equals(this.STATUS_ADDNEW)) {
			FDCMsgBox.showError("当前工程已有项目计划，不能重复编制！");
			abort();
		}
	}

	/**
	 * 初始化 调整数
	 * 
	 * @param index
	 * @throws BOSException
	 * @throws SQLException
	 */
	public void initAdjustAmount(int index) throws BOSException, SQLException {
		IRow headRow = kdtEntry.getHeadRow(0);
		IRow row = kdtEntry.getRow(index);
		CostAccountInfo account = (CostAccountInfo) row.getCell("account.name")
				.getUserObject();
		Calendar nowDate = Calendar.getInstance();
		BigDecimal sptPaySum = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		for (int i = 4; i < kdtEntry.getColumnCount(); i++) {
			String date = headRow.getCell(i).getValue().toString();
			BigDecimal year = new BigDecimal(date.substring(0, 4));
			BigDecimal month = new BigDecimal(date.substring(5,
					date.length() - 1));
			// 表头日期大于等于当前日期，取滚动计划拆分值
			BigDecimal sptPay = FDCHelper.ZERO;
			if (year.intValue() > nowDate.get(Calendar.YEAR)
					|| (year.intValue() == nowDate.get(Calendar.YEAR) && month
							.intValue() >= (nowDate.get(Calendar.MONTH) + 1))) {
				String key = account.getId().toString() + year + "" + month;
				sptPay = (BigDecimal) accountSptMap.get(key);
				sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
			}
			// 表头日期小于当前日期，取实际付款拆分值
			else {
				String key = account.getId().toString() + year + "" + month;
				sptPay = (BigDecimal) paymentSptMap.get(key);
				sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
			}
			sptPaySum = sptPaySum.add(sptPay);
			if (row.getCell(i).getValue() != null) {
				amount = amount.add(new BigDecimal(row.getCell(i).getValue()
						.toString()));
			}
		}
		// 调整数
		BigDecimal adjustAmount = (amount.subtract(sptPaySum)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		// 汇总到上级科目
		BigDecimal oldValue = FDCHelper.ZERO;
		BigDecimal newValue = FDCHelper.ZERO;
		if (row.getCell("adjustAmount").getValue() != null) {
			oldValue = FDCHelper.toBigDecimal(row.getCell("adjustAmount")
					.getValue(), 2);
		}
		newValue = FDCHelper.toBigDecimal(adjustAmount, 2);
		newValue = FDCHelper.subtract(newValue, oldValue);
		CostAccountInfo info = (CostAccountInfo) kdtEntry.getCell(index,
				"account.name").getUserObject();
		CostAccountInfo pAccount = null;
		Set accountSet = new HashSet();
		getAllParentAccount(info, accountSet);
		if (accountSet.size() > 0) {
			for (Iterator it = accountSet.iterator(); it.hasNext();) {
				pAccount = (CostAccountInfo) it.next();
				setData(pAccount, newValue, 3);
			}
		}
		row.getCell("adjustAmount").setValue(adjustAmount);
	}

	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
//		 initAdjustAmount(e.getRowIndex());
	}

	protected void verifyInputForSave() throws Exception {
		// super.verifyInputForSave();
		if (this.txtNumber.isEnabled()) {
			if (editData.getNumber() == null
					|| this.editData.getNumber().trim().length() == 0) {
				FDCMsgBox.showError("编号不能为空！");
				abort();
			}
		}

		if (editData.getName() == null || editData.getName().length() == 0) {
			FDCMsgBox.showError("单据名称不能为空！");
			abort();
		}

		if (this.txtProject.getText() == null
				|| this.txtProject.getText().trim().length() == 0) {
			FDCMsgBox.showError("工程项目为空或者已编制过该项目的投资计划！");
			abort();
		}

		Map uiContext = getUIContext();
		CurProjectInfo project = (CurProjectInfo) uiContext.get("curProject");
		if (project != null && project.getId() != null) {
			if (!isHavePlanByProjectId(project.getId().toString())
					&& getOprtState().equals(this.STATUS_ADDNEW)) {
				FDCMsgBox.showError("当前工程已有项目计划，不能重复编制！");
				abort();
			}
		} else if (this.editData.getProject() != null) {
			project = this.editData.getProject();
			if (!isHavePlanByProjectId(project.getId().toString())
					&& getOprtState().equals(this.STATUS_ADDNEW)) {
				FDCMsgBox.showError("当前工程已有项目计划，不能重复编制！");
				abort();
			}
		}

		// if(this.kdtEntry.getRowCount() == 0){
		// FDCMsgBox.showError("分录为空！");
		// abort();
		// }else{
		// int n = 0;
		// StringBuffer str = new StringBuffer();
		// str.append("当前分录中,有以下错误：\n");
		// for(int i=0;i<kdtEntry.getRowCount();i++){
		// if(kdtEntry.getCell(i,"account.number").getValue() == null){
		// str.append("第");
		// str.append(i+1);
		// str.append("行的科目编码为空\n");
		// n++;
		// }
		// }
		// if(n>0){
		// FDCMsgBox.showError(str.toString());
		// abort();
		// }
		// }

	}

	/**
	 *描述 :项目全生命周期资金投入计划
	 * 
	 * @param params
	 *            提供FDC数参数：<br>
	 *            key = orgUnit 组织 <br>
	 *            key = year 年度
	 * @return List <br>
	 *         返回该组织年度的项目全生命周期资金投入计划<br>
	 *         List保存所有数据的Map值 ，<br>
	 *         工程项目、成本科目 、期间对应的计划完工工程量和计划投入资金<br>
	 * 
	 * @author guangyue_liu
	 */
	public static List getFDCPaymengLifeData(Map params) {
		// FullOrgUnitInfo orgUnit=null;
		// if(params.get("orgUnit")!=null){
		// orgUnit = (FullOrgUnitInfo) params.get("orgUnit");
		// }
		// StringBuffer sb=new StringBuffer();
		// sb.append(
		// "select pro.forgunitid,pro.FProjectID,proEntry.FAccountID,month.FMonth"
		// +
		// ",month.FInvestValue,month.FBankroll " +
		// "from T_FNC_ProjectInvestPlan as pro " +
		// "left join T_FNC_ProjectInvestPlanEntry proEntry on pro.fid = proEntry.FParentID "
		// +
		// "left join T_FNC_MonthEntry as month on month.FParentID = proEntry.fid "
		// +
		// "where 1=1 " );
		// if(orgUnit!=null){
		// sb.append("and pro.forgunitid ='' ");
		// }
		// sb.append(
		// "order by pro.forgunitid,pro.FProjectID,proEntry.FAccountID,month.FMonth"
		// );
		// FDCSQLBuilder builder=new FDCSQLBuilder();
		// builder.appendSql(sb.toString());
		return null;
	}

	/**
	 * 初始化一个集合<br>
	 * key：科目+月份 like XQYO31mMR/GeycCmYXu97YQj/24=20113 <br>
	 * value：拆分金额
	 * 
	 * @return
	 */
	private void initAccountSptMap() {
		accountSptMap = new HashMap();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		// 汇总3个拆分页签该科目的拆分金额
		sql
				.appendSql(" select costAccount,sptMonth,sum(sptPay) as sptAmt from ( ");
		// 已签订合同拆分
		sql
				.appendSql(" select hc.FCostAccountID as costAccount,he.FSptPay as sptPay, year(he.FMonth) || month(he.FMonth) as sptMonth ");
		sql.appendSql(" from T_FNC_FDCProDepSplitHasConEty as he ");
		sql
				.appendSql(" left join T_FNC_FDCProDepSplitHasCon as hc on hc.FID = he.FParentID ");
		sql
				.appendSql(" left join T_FNC_FDCProDepSplit as spt on spt.FID = hc.FHeadID ");
		sql
				.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = spt.FFDCProDep ");
		sql.appendSql(" where hc.FCostAccountID is not null ");
		sql.appendSql(" and (spt.FState = ? or spt.FState = ?) ");
		sql.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		sql.addParam(FDCBillStateEnum.PUBLISH_VALUE);
		sql.appendSql(" and (pp.FState = ? or pp.FState = ?) ");
		sql.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		sql.addParam(FDCBillStateEnum.PUBLISH_VALUE);
		sql.appendSql(" and pp.FIsResum != 1 ");

		sql.appendSql(" union all ");
		// 待签订合同拆分
		sql
				.appendSql(" select uc.FCostAccountID as costAccount,ue.FSptPay as sptPay, year(ue.FMonth) || month(ue.FMonth) as sptMonth ");
		sql.appendSql(" from T_FNC_FDCProDepSplitUnConEntry as ue ");
		sql
				.appendSql(" left join T_FNC_FDCProDepSplitUnCon as uc on uc.FID = ue.FParentID ");
		sql
				.appendSql(" left join T_FNC_FDCProDepSplit as spt on spt.FID = uc.FHeadID ");
		sql
				.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = spt.FFDCProDep ");
		sql.appendSql(" where uc.FCostAccountID is not null ");
		sql.appendSql(" and (spt.FState = ? or spt.FState = ?) ");
		sql.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		sql.addParam(FDCBillStateEnum.PUBLISH_VALUE);
		sql.appendSql(" and (pp.FState = ? or pp.FState = ?) ");
		sql.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		sql.addParam(FDCBillStateEnum.PUBLISH_VALUE);
		sql.appendSql(" and pp.FIsResum != 1 ");

		sql.appendSql(" union all ");
		// 无合同拆分
		sql
				.appendSql(" select nc.FCostAccountID as costAccount,ne.FSptPay as sptPay, year(ne.FMonth) || month(ne.FMonth) as sptMonth ");
		sql.appendSql(" from T_FNC_FDCProDepSplitNoConEntry as ne ");
		sql
				.appendSql(" left join T_FNC_FDCProDepSplitNoCon as nc on nc.FID = ne.FParentID ");
		sql
				.appendSql(" left join T_FNC_FDCProDepSplit as spt on spt.FID = nc.FHeadID ");
		sql
				.appendSql(" left join T_FNC_FDCProDepConPlan as pp on pp.FID = spt.FFDCProDep ");
		sql.appendSql(" where nc.FCostAccountID is not null ");
		sql.appendSql(" and (spt.FState = ? or spt.FState = ?) ");
		sql.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		sql.addParam(FDCBillStateEnum.PUBLISH_VALUE);
		sql.appendSql(" and (pp.FState = ? or pp.FState = ?) ");
		sql.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		sql.addParam(FDCBillStateEnum.PUBLISH_VALUE);
		sql.appendSql(" and pp.FIsResum != 1 ");

		sql.appendSql(" ) group by costAccount,sptMonth ");
		sql.appendSql(" order by costAccount,sptMonth ");

		try {
			IRowSet rs = sql.executeQuery();
			while (rs.next()) {
				String costAccount = rs.getString("costAccount");
				String sptMonth = rs.getString("sptMonth");
				BigDecimal sptAmt = rs.getBigDecimal("sptAmt");
				accountSptMap.put(costAccount + sptMonth, sptAmt);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询当前项目下，付款单拆分到各个月的金额集合
	 */
	private void initPaymentSptMap(){
		paymentSptMap = new HashMap();
		CurProjectInfo prj = editData.getProject();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select payEntry.fCostAccountID as costAccountID, year(payment.fbizDate) || month(payment.fbizDate) as sptMonth,sum(payEntry.FPayedAmt) as FSptPay ");
		sql.appendSql(" from T_FNC_PaymentSplitEntry as payEntry ");
		sql.appendSql(" left join T_FNC_PaymentSplit as pay on pay.fid = payEntry.FParentID ");
		sql.appendSql(" left join T_CAS_PaymentBill as payment on payment.fid = pay.FPaymentBillID ");
		sql.appendSql(" left join T_FDC_CostAccount as acct on acct.FID = payEntry.fCostAccountID ");
		sql.appendSql(" where payment.fBillStatus in (12,15) ");
		sql.appendSql(" and pay.fisinvalid<>1 ");
		if (prj != null) {
			sql.appendSql(" and acct.FCurProject = ? ");
			sql.addParam(prj.getId().toString());
		}
		sql.appendSql(" group by payEntry.fCostAccountID,year(payment.fbizDate) || month(payment.fbizDate) ");
		try {
			IRowSet rs = sql.executeQuery();
			while (rs.next()) {
				String costAccount = rs.getString("costAccountID");
				String sptMonth = rs.getString("sptMonth");
				BigDecimal sptAmt = rs.getBigDecimal("FSptPay");
				paymentSptMap.put(costAccount + sptMonth, sptAmt);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
