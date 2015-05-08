/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.SCHHelper;
import com.kingdee.eas.fdc.schedule.WBSCodeRuleEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplateFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplateTypeInfo;
import com.kingdee.eas.fdc.schedule.WorkTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class WBSTemplateEditUI extends AbstractWBSTemplateEditUI {
	private static final String SYS_DEFAULT_NUM = "00";
	private static final Logger logger = CoreUIObject.getLogger(WBSTemplateEditUI.class);
	private static final String COL_NUMBER = "number";
	private static final String COL_NAME = "name";
	private static final String COL_LEVEL = "level";
	private static final String COL_ESTIMATEDAYS = "estimateDays";
	private static final String COL_ISLOCKED = "isLocked";
	private static final String COL_PRETASK = "prefixTask";
	private static final String COL_ID = "id";
	private boolean isFromFDCWBS = false;
	public WBSTemplateEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
		WBSTemplateEntryInfo tempEntryInfo, defaultEntryInfo = new WBSTemplateEntryInfo();
		for (int i = 0; i < editData.getEntrys().size(); i++) {
			tempEntryInfo = editData.getEntrys().get(i);
			if (tempEntryInfo.getNumber().equals(SYS_DEFAULT_NUM)) {
				defaultEntryInfo = tempEntryInfo;
				continue;
			}
			Object preTaskInfo[] = (Object[]) kdtEntry.getCell(i, COL_PRETASK).getValue();
			if (preTaskInfo == null || preTaskInfo.length < 1){
				editData.getEntrys().get(i).getPrefixEntrys().clear();
				continue;
			}
			for (int j = 0; j < preTaskInfo.length; j++) {
				editData.getEntrys().get(i).getPrefixEntrys().clear();
				editData.getEntrys().get(i).getPrefixEntrys().add(
						(WBSTemplatePrefixEntryInfo) preTaskInfo[j]);
			}
		}
		editData.getEntrys().remove(defaultEntryInfo);

	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		WBSTemplateInfo wbsTPInfo = new WBSTemplateInfo();
		WBSTemplateTypeInfo typeInfo = null;
		if (getUIContext().get("templateType") != null) {
			typeInfo = (WBSTemplateTypeInfo) getUIContext().get("templateType");
		} else if (editData != null) {
			typeInfo = editData.getTemplateType();
		}

		wbsTPInfo.setTemplateType(typeInfo);
		wbsTPInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		try {
			wbsTPInfo.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			this.handleException(e);
		}
		wbsTPInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		prmtType.setEnabled(false);
		return wbsTPInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WBSTemplateFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		kdtEntry.checkParsed();
		txtName.setMaxLength(80);
		txtNumber.setMaxLength(80);
		txtDescription.setMaxLength(250);
		if(getUIContext().get("isFromFDCWBS") != null 
				&& Boolean.TRUE.equals(getUIContext().get("isFromFDCWBS"))){
			isFromFDCWBS = true;
		}else{
			isFromFDCWBS = false;
		}
		super.onLoad();
		initTable();
		if (kdtEntry.getCell(0, COL_NUMBER).getValue().toString().equals(SYS_DEFAULT_NUM)) {
			kdtEntry.getRow(0).getStyleAttributes().setLocked(true);
		}
		contCreateTime.setVisible(false);
		contCreator.setVisible(false);
		contLastUpdateTime.setVisible(false);
		contLastUpdateUser.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		initWorkButton();
		if(isFromFDCWBS){
			fillTable();
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionRemove.setVisible(false);
		actionInsertLine.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		if (OprtState.VIEW.equals(getOprtState())) {
			Object[] btns = conEntry.getButtons();
			for (int i = 0; i < btns.length; i++) {
				((KDWorkButton) btns[i]).setEnabled(false);
			}
		} else {
			Object[] btns = conEntry.getButtons();
			for (int i = 0; i < btns.length; i++) {
				((KDWorkButton) btns[i]).setEnabled(true);
			}
		}
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if (kdtEntry == null)
			return;
		String action = null;
		if(OprtState.ADDNEW.equals(getOprtState())){
			action = FDCBaseDataClientCtrler.ACTION_ADDNEW;
		}else if(OprtState.EDIT.equals(getOprtState())){
			action = FDCBaseDataClientCtrler.ACTION_MODIFY;
		}
		getCtrler().checkPermission(action);
		int actRowIdx = kdtEntry.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0) {
			FDCMsgBox.showWarning("请先选中要添加到其下的行！");
			SysUtil.abort();
		}
		int parentLevl = Integer.parseInt(kdtEntry.getCell(actRowIdx, COL_LEVEL).getValue().toString());
		WBSTemplateEntryInfo detailData = new WBSTemplateEntryInfo();
		detailData.setId(BOSUuid.create(detailData.getBOSType()));
		detailData.setLevel(parentLevl + 1);
		detailData.setEstimateDays(1);
		// 构造新编码开始
		String newNum = new String();
		int lastSibIdx = -1;
		int lastSibChildIdx = -1;
		String lastSibNum;
		String parentNum = kdtEntry.getCell(actRowIdx, COL_NUMBER).getValue().toString();
		WBSCodeRuleEntryInfo codeRule = getWBSCodeRule(parentLevl + 1);
		for (int i = actRowIdx + 1; i < kdtEntry.getRowCount(); i++) {
			if (parentNum != null
					&& parentNum.equals(SYS_DEFAULT_NUM)
					&& (Integer.parseInt(kdtEntry.getCell(i, COL_LEVEL)	.getValue().toString())	- parentLevl == 1)) {
				lastSibIdx = i;
			} else if (kdtEntry.getCell(i, COL_NUMBER).getValue().toString().indexOf(parentNum) == 0	//>1有问题(03！0001，01)也会成立
					&& (Integer.parseInt(kdtEntry.getCell(i, COL_LEVEL).getValue().toString())- parentLevl == 1)) {
				lastSibIdx = i;
			}else if(kdtEntry.getCell(i, COL_NUMBER).getValue().toString().indexOf(parentNum) == 0
					&& (Integer.parseInt(kdtEntry.getCell(i, COL_LEVEL).getValue().toString()) > parentLevl - 1)) {
				lastSibChildIdx = i;
			}
		}
		if (lastSibIdx < 0) {
			newNum = SCHHelper.getFixLenNum(codeRule.getLength(), 1);
		} else {
			lastSibNum = kdtEntry.getCell(lastSibIdx, COL_NUMBER).getValue().toString();
			if (parentNum.equals(SYS_DEFAULT_NUM)) {
				newNum = SCHHelper.getFixLenNum(codeRule.getLength(), Integer.parseInt(lastSibNum) + 1);
			} else {
				newNum = SCHHelper.getFixLenNum(codeRule.getLength(),
						Integer.parseInt(lastSibNum.substring(parentNum.length() + 1)) + 1);
			}
		}
		if (!parentNum.equals(SYS_DEFAULT_NUM)) {
			detailData.setNumber(parentNum + "!" + newNum);
		} else {
			detailData.setNumber(newNum);
		}

		// 构造新编码结束
		IRow row;
		if (lastSibIdx >= 0) {
			if (parentLevl == 0) {
				row = kdtEntry.addRow();
			} else if(lastSibChildIdx >= 0 && lastSibChildIdx > lastSibIdx){
				row = kdtEntry.addRow(lastSibChildIdx + 1);
			}else {
				row = kdtEntry.addRow(lastSibIdx + 1);
			}
		} else
			row = kdtEntry.addRow(actRowIdx + 1);
		 row.getCell(COL_ESTIMATEDAYS).setValue(FDCNumberHelper.ONE);
		// row.getCell(COL_LEVEL).setValue(new Integer(parentLev + 1));
		getUILifeCycleHandler().fireOnAddNewLine(kdtEntry, detailData);
		dataBinder.loadLineFields(kdtEntry, row, detailData);
	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
//		if (kdtEntry == null)
//			return;
//		WBSTemplateEntryInfo detailData = new WBSTemplateEntryInfo();
//		IRow row = null;
//		if (kdtEntry.getSelectManager().size() > 0) {
//			int top = kdtEntry.getSelectManager().get().getTop();
//			row = kdtEntry.addRow(top);
//		} else {
//			row = kdtEntry.addRow();
//		}
//		row.getCell(COL_ESTIMATEDAYS).setValue(null);
//		row.getCell(COL_LEVEL).setValue(null);
//		getUILifeCycleHandler().fireOnAddNewLine(kdtEntry, detailData);
//		dataBinder.loadLineFields(kdtEntry, row, detailData);
	}

	/**
	 * 删除的时候要把下级都删除掉
	 */
	public void actionDeleteLine_actionPerformed(ActionEvent e)
			throws Exception {
		if (kdtEntry == null)
			return;
		String action = null;
		if(OprtState.ADDNEW.equals(getOprtState())){
			action = FDCBaseDataClientCtrler.ACTION_ADDNEW;
		}else if(OprtState.EDIT.equals(getOprtState())){
			action = FDCBaseDataClientCtrler.ACTION_MODIFY;
		}
		getCtrler().checkPermission(action);
		getCtrler().checkPermission(action);
//		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		int activeRowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0) {
			FDCMsgBox.showWarning("请先选中行！");
			SysUtil.abort();
		}
		String number = kdtEntry.getCell(activeRowIndex, "number").getValue().toString();
		if (number.equals(SYS_DEFAULT_NUM))
			return;
		Set ids = new HashSet();
		for (int i = kdtEntry.getRowCount() - 1; i > activeRowIndex - 1; i--) {
			if (kdtEntry.getCell(i, "number").getValue().toString().startsWith(	number)) {
				ids.add(kdtEntry.getCell(i, COL_ID).getValue().toString());
			}
		}
		for(int i=1;i<kdtEntry.getRowCount();i++){
			if(kdtEntry.getCell(i, COL_PRETASK).getValue() != null
					&& (!(kdtEntry.getCell(i,COL_PRETASK).getValue() instanceof String))){
				Object[] preTasks = (Object[]) kdtEntry.getCell(i,COL_PRETASK).getValue();
				for(int j=0;j<preTasks.length;j++){
					Object preTask = preTasks[j];
					if(preTask instanceof WBSTemplatePrefixEntryInfo
							&& ids.contains(
								((WBSTemplatePrefixEntryInfo) preTask).getPrefixNode().getId().toString())){
						FDCMsgBox.showError("该节点已被引用，不能删除！");
						SysUtil.abort();
					}
				}
			}
		}
		for (int i = kdtEntry.getRowCount() - 1; i > activeRowIndex - 1; i--) {
			if (kdtEntry.getCell(i, "number").getValue().toString().startsWith(	number)) {
				kdtEntry.removeRow(i);
			}
		}
	}

	/**
	 * 方法中实现的功能有点多，以后有空抽取一下方法 -by neo
	 */
	private void initTable() {
		KDWorkButton btnAddLine = new KDWorkButton("新增行",	SCHClientHelper.ICON_ADDLINE);
		KDWorkButton btnInsertLine = new KDWorkButton("插入行",	SCHClientHelper.ICON_INSERTLINE);
		KDWorkButton btnDeleteLine = new KDWorkButton("删除行",	SCHClientHelper.ICON_REMOVELINE);
		if (conEntry.getButtons().length > 0)	return;
		if (kdtEntry.getRowCount() > 0
				&& kdtEntry.getCell(0, COL_NUMBER).getValue().toString().equals(SYS_DEFAULT_NUM)) {
			kdtEntry.getRow(0).getStyleAttributes().setLocked(true);
		}
		conEntry.addButton(btnAddLine);
		// conEntry.addButton(btnInsertLine);
		conEntry.addButton(btnDeleteLine);
		btnAddLine.addActionListener(actionAddLine);
		btnInsertLine.addActionListener(actionInsertLine);
		btnDeleteLine.addActionListener(actionDeleteLine);

		IColumn colNum = kdtEntry.getColumn(COL_NUMBER);
		IColumn colName = kdtEntry.getColumn(COL_NAME);
		IColumn colPreTask = kdtEntry.getColumn(COL_PRETASK);
		IColumn colEstDay = kdtEntry.getColumn(COL_ESTIMATEDAYS);
		IColumn colLevel = kdtEntry.getColumn(COL_LEVEL);
		KDFormattedTextField formattedNum = new KDFormattedTextField();
		formattedNum.setMaximumValue(new BigDecimal("10000"));
//		formattedNum.setMinimumValue(new BigDecimal("0"));
		formattedNum.setRemoveingZeroInDispaly(true);
		formattedNum.setRemoveingZeroInEdit(true);
		formattedNum.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		formattedNum.setMinimumValue(new BigDecimal("1"));
		colEstDay.setEditor(new KDTDefaultCellEditor(formattedNum));
		colLevel.setEditor(new KDTDefaultCellEditor(formattedNum));
		colLevel.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		colLevel.getStyleAttributes().setLocked(true);

		ObjectValueRender numRender = new ObjectValueRender();
		numRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof String) {
					return o.toString().replace('!', '.');
				} else
					return o.toString();
			}
		});
		colNum.setRenderer(numRender);
		colNum.getStyleAttributes().setLocked(true);
		// 应李涛要求，模板名称支持从工作任务的F7选择
		KDBizPromptBox prmtWorkTask = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String name = null;
				if (o != null && o instanceof WorkTaskInfo) {
					name = ((WorkTaskInfo) o).getName();
				} else if (o != null && o instanceof String)
					name = o.toString();
				return name;
			}
		};
		prmtWorkTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7WorkTaskQuery");
		prmtWorkTask.setDisplayFormat("$name$");
		prmtWorkTask.setEditFormat("$name$");
		prmtWorkTask.setEditable(true);
		prmtWorkTask.setMaxLength(80);
		prmtWorkTask.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				int actRowIdx = kdtEntry.getSelectManager().getActiveRowIndex();
				WorkTaskInfo workTaskInfo;
				if(eventObj.getNewValue() != null && eventObj.getNewValue() instanceof  WorkTaskInfo){
					workTaskInfo = (WorkTaskInfo)eventObj.getNewValue(); 
//					kdtEntry.getCell(actRowIdx,COL_LEVEL).setValue(new Integer(workTaskInfo.getLevel()));
					kdtEntry.getCell(actRowIdx, COL_ESTIMATEDAYS).setValue(new Integer(workTaskInfo.getEstimateDays()));
					kdtEntry.getCell(actRowIdx,COL_ISLOCKED).setValue(Boolean.valueOf(workTaskInfo.isIsEstDaysLocked()));
				}
			}
		});
		colName.setEditor(new KDTDefaultCellEditor(prmtWorkTask));
		// 传入当前的表格信息
		WBSTemplatePromptBox selector = new WBSTemplatePromptBox(this,
				kdtEntry, true);
		KDBizPromptBox preTaskPrmtBox = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				Object[] os = (Object[]) o;
				String str = "";
				if (o != null && os.length > 1) {
					for (int i = 0; i < os.length; i++) {
						str = str + ((WBSTemplatePrefixEntryInfo) os[i]).getPrefixNode().getNumber() + ";";
					}
					str = str.substring(0,str.length()-1);
					str = str.replace('!','.');
				}
				return str;
			}
		};
		preTaskPrmtBox.setSelector(selector);
		preTaskPrmtBox.setEditable(false);
		preTaskPrmtBox.setEnabledMultiSelection(true);
		KDTDefaultCellEditor preTaskEditor = new KDTDefaultCellEditor(preTaskPrmtBox);
		colPreTask.setEditor(preTaskEditor);
		ObjectValueRender objValueRender = new ObjectValueRender();
		objValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				Object[] os = (Object[]) o;
				if (o != null && os.length >= 1) {
					String str = new String();
					for (int i = 0; i < os.length; i++) {
						if (os[i] instanceof WBSTemplatePrefixEntryInfo
								&& ((WBSTemplatePrefixEntryInfo) os[i]).getPrefixNode() != null) {
							str = str + ((WBSTemplatePrefixEntryInfo) os[i]).getPrefixNode().getNumber() + ";";
						}
					}
					str = str.substring(0, str.lastIndexOf(";"));
					return str.replace('!', '.');
				} else
					return "";
			}
		});
		colPreTask.setRenderer(objValueRender);
		preTaskPrmtBox.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				eventObj.getNewValue();
			}
		});
		preTaskPrmtBox.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				for(int i=0;i<kdtEntry.getRowCount();i++){
					if(kdtEntry.getCell(i, COL_NAME).getValue() == null
							||kdtEntry.getCell(i, COL_NAME).getValue().toString().trim().length() <1){
						FDCMsgBox.showWarning("请先填入名称再进行前置任务选择操作！");
						SysUtil.abort();
					}
				}
				KDBizPromptBox bizBox = (KDBizPromptBox) e.getSource();
				WBSTemplatePromptBox myBox = (WBSTemplatePromptBox) bizBox.getSelector();
				int actRowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
				String selectedNum = null;
				if(kdtEntry.getCell(actRowIndex,COL_NUMBER).getValue() != null){
					selectedNum = kdtEntry.getCell(actRowIndex,COL_NUMBER).getValue().toString();
				}
				myBox.setSelectedNum(selectedNum);
				myBox.setWBSTMPEntry((Object[]) kdtEntry.getCell(actRowIndex,COL_PRETASK).getValue());
			}
		});
	}

	public String getNewNum() {
		String newNum = new String();
		return newNum;
	}

	public WBSCodeRuleEntryInfo getWBSCodeRule(int level)
			throws EASBizException, BOSException, SQLException {
		WBSCodeRuleEntryInfo codeRuleInfo = new WBSCodeRuleEntryInfo();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select top 1 (*) from t_sch_wbscoderuleentry "
				+ "where flevel=? or flevel=0 order by flevel desc");
		builder.addParam(new Integer(level));
		IRowSet rowSet = builder.executeQuery();
		int length = 0;
		while (rowSet.next()) {
			length = rowSet.getInt("flength");
		}
		codeRuleInfo.setLength(length);
		codeRuleInfo.setLevel(level);
		return codeRuleInfo;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(prmtType.getValue()==null||"".equals(prmtType.getValue())){
			FDCMsgBox.showError("模板类型不能为空！");
			SysUtil.abort();
		}
		getUIContext().put("taskType", editData.getType());
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			if (kdtEntry.getCell(i, "name").getValue() == null
					|| kdtEntry.getCell(i, "name").getValue().toString().length() < 1) {
				FDCMsgBox.showError("第" + (i + 1) + "行的WBS名称不能为空！");
				SysUtil.abort();
			}
		}
		super.actionSubmit_actionPerformed(e);
		handleOldData();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		// sic.add(new SelectorItemInfo("entry.prefixEntrys.id"));
		sic.add(new SelectorItemInfo("entry.prefixEntrys.*"));
		sic.add(new SelectorItemInfo("entry.prefixEntrys.prefixNode.id"));
		sic.add(new SelectorItemInfo("entry.prefixEntrys.prefixNode.name"));
		sic.add(new SelectorItemInfo("entry.prefixEntrys.prefixNode.number"));
		sic.add(new SelectorItemInfo("isEnabled"));
		// sic.add(new SelectorItemInfo("entry.prefixEntrys.number"));
		return sic;
	}

	protected boolean isSystemDefaultData() {
		return false;
	}

	// 因为取不到前置任务的属性，在loadFields中再取一次数据，以后优化处理
	public void loadFields() {
		WBSTemplateEntryInfo wbsTPEntryInfo = new WBSTemplateEntryInfo();
		wbsTPEntryInfo.setNumber(SYS_DEFAULT_NUM);
		wbsTPEntryInfo.setName("任务模板");
		wbsTPEntryInfo.setLevel(0);
		editData.getEntrys().addObject(0, wbsTPEntryInfo);
		super.loadFields();

		if (!OprtState.ADDNEW.equals(getOprtState())) {
			Set entryIds = new HashSet();
			Map preTasks = new HashMap();
			for (int i = 1; i < editData.getEntrys().size(); i++) {
				entryIds.add(editData.getEntrys().get(i).getId().toString());
			}
			try {
				preTasks = WBSTemplatePrefixEntryFactory.getRemoteInstance().getPreTasks(entryIds);
			} catch (Exception e) {
				this.handleException(e);
			} 
			for (int i = 1; i < editData.getEntrys().size(); i++) {
				WBSTemplateEntryInfo entryInfo = editData.getEntrys().get(i);
				entryInfo.getPrefixEntrys().addCollection(
						(WBSTemplatePrefixEntryCollection) preTasks.get(entryInfo.getId().toString()));
			}
			// IRow row = kdtEntry.addRow(0);
			// row.getCell(COL_NAME).setValue("任务模板");
			// row.getCell(COL_NUMBER).setValue("0");
			// row.getCell(COL_LEVEL).setValue("0");
			// row.getCell(COL_ISLOCKED).setValue(Boolean.FALSE);
			for (int i = 1; i < kdtEntry.getRowCount(); i++) {
				if (((WBSTemplatePrefixEntryCollection) preTasks.get(kdtEntry.getCell(i, COL_ID).getValue().toString())).size() > 0) {
					kdtEntry.getCell(i, COL_PRETASK).setValue(
							((WBSTemplatePrefixEntryCollection) preTasks.get(kdtEntry.getCell(i, COL_ID).getValue().toString())).toArray());
				}
			}
		}else if(getUIContext().get(UIContext.OWNER) != null && 
				FDCWBSListUI.class.getName().equals(getUIContext().get(UIContext.OWNER).getClass().getName())){
			Map entryMap = (Map) getUIContext().get("entrys");
			WBSTemplateEntryCollection tempCol = (WBSTemplateEntryCollection) entryMap.get("tempWBSEntrys");
			for(int i = 0;i<tempCol.size();i++){
				WBSTemplateEntryInfo entryInfo = tempCol.get(i);
//				entryInfo.getPrefixEntrys().addCollection(item)
				IRow row = kdtEntry.addRow();
				row.getCell(COL_ID).setValue(entryInfo.getId().toString());
				row.setUserObject(entryInfo);
				row.getCell(COL_NAME).setValue(entryInfo.getName());
				row.getCell(COL_NUMBER).setValue(entryInfo.getNumber());
				row.getCell(COL_LEVEL).setValue(new Integer(entryInfo.getLevel()));
				row.getCell(COL_ESTIMATEDAYS).setValue(new Integer(entryInfo.getEstimateDays()));
				row.getCell(COL_ISLOCKED).setValue(new Boolean(entryInfo.isIsLocked()));
				if(entryInfo.getPrefixEntrys() != null){
					row.getCell(COL_PRETASK).setValue(entryInfo.getPrefixEntrys().toArray());
				}
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.isIsEnabled()) {
			FDCMsgBox.showError("WBS模板已启动，不能修改！");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
		initWorkButton();
	}

	protected void handleOldData() {
		if (!(getOprtState() == "FINDVIEW" || getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
	protected void kdtEntry_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		int actRowIdx = kdtEntry.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		if(kdtEntry.getCell(actRowIdx,COL_NUMBER).getValue() != null
				&&SYS_DEFAULT_NUM.equals(kdtEntry.getCell(actRowIdx,COL_NUMBER).getValue().toString()))
			return;
		if(kdtEntry.getCell(actRowIdx, "isLocked").getValue() != null
				&&Boolean.TRUE.equals(
						Boolean.valueOf(kdtEntry.getCell(actRowIdx, "isLocked").getValue().toString()))){
			kdtEntry.getCell(actRowIdx, "estimateDays").getStyleAttributes().setLocked(true);
		}else{
			kdtEntry.getCell(actRowIdx, "estimateDays").getStyleAttributes().setLocked(false);
		}
		
	}
	
	//前置任务处理起来太麻烦，暂时不处理 -by neo
	public boolean isModify() {
		if (OprtState.VIEW.equals(this.getOprtState())) {
			return false;
		}
		if(!isEquals(editData.getNumber(),txtNumber.getText()))
			return true; 
		if(!isEquals(editData.getName(),txtName.getSelectedItemData()))
			return true;
		if(!isEquals(editData.getDescription(), txtDescription.getText()))
			return true;
		Map entryMap = new HashMap();
		for(int i=0;i<editData.getEntrys().size();i++){
			WBSTemplateEntryInfo info = editData.getEntrys().get(i);
			entryMap.put(info.getNumber(), info);
		}
		if(editData.getEntrys().size() != kdtEntry.getRowCount())
			return true;
		for(int i=0;i<kdtEntry.getRowCount();i++){
			String number = null;
			if(kdtEntry.getCell(i, "number").getValue() != null)
				number = kdtEntry.getCell(i, "number").getValue().toString();
			WBSTemplateEntryInfo info = null;
			if(entryMap.get(number) != null)
				info = (WBSTemplateEntryInfo) entryMap.get(number);
			if(info == null) return true;
			if(!isEquals(info.getName(),kdtEntry.getCell(i, "name").getValue()))
				return true;
			if(!isEquals(info.getNumber(),kdtEntry.getCell(i, "number").getValue()))
				return true;
			if(info.getLevel() != Integer.parseInt(kdtEntry.getCell(i, "level").getValue().toString()))
				return true;
			if(info.getEstimateDays() != Integer.parseInt(kdtEntry.getCell(i, "estimateDays").getValue().toString()))
				return true;
			if(info.isIsLocked() != Boolean.valueOf(kdtEntry.getCell(i, "isLocked").getValue().toString()).booleanValue())
				return true;
		}
		return false;
	}
	private boolean isEquals(Object obj1,Object obj2){
		if(obj1 == null){
			if(obj2 == null) return true;
			else if(obj2.toString().trim().length() <1)		return true;
			else return false;
		}else if(obj1.toString().trim().length() <1){
			if(obj2 == null) return true;
			else if(obj2.toString().trim().length() <1) return true;
			else return false;
		}else{
			if(obj2 == null) return false;
			else if(obj2.toString().trim().length() <1) return false;
			else if(obj1.toString().equals(obj1.toString())) return true;
			else return false;
		}
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
			FDCMsgBox.showWarning("当前组织不是财务组织，不能新增WBS模板！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
		if (kdtEntry.getCell(0, COL_NUMBER).getValue().toString().equals(SYS_DEFAULT_NUM)) {
			kdtEntry.getRow(0).getStyleAttributes().setLocked(true);
		}
		Object[] btns = conEntry.getButtons();
		for (int i = 0; i < btns.length; i++) {
			((KDWorkButton) btns[i]).setEnabled(true);
		}
	}
    public void setOprtState(String oprtType) {
    	super.setOprtState(oprtType);
    	setTitle();
    }
    
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, "WBS模板");
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		int actColIdx = kdtEntry.getSelectManager().getActiveColumnIndex();
		int actRowIdx = kdtEntry.getSelectManager().getActiveRowIndex();
		if(kdtEntry.getSelectManager().getActiveRowIndex() < 0) return;
		IRow actRow = kdtEntry.getRow(actRowIdx);
		String longNum = actRow.getCell(COL_NUMBER).getValue().toString();
		int estDay = Integer.parseInt(actRow.getCell(COL_ESTIMATEDAYS).getValue().toString());
		if(actColIdx == 4){
			for(int i=actRowIdx-1;i>0;i--){
				IRow row = kdtEntry.getRow(i);
				String pLongNum = row.getCell(COL_NUMBER).getValue().toString();
				int pEstDay = Integer.parseInt(row.getCell(COL_ESTIMATEDAYS).getValue().toString());
				if(longNum.startsWith(pLongNum)){
					if(pEstDay < estDay){
						kdtEntry.getCell(actRowIdx, COL_ESTIMATEDAYS).setValue(new BigDecimal("1"));
						FDCMsgBox.showError("下级预估工期不能大于上级预估工期！");
						SysUtil.abort();
					}
					break;
				}
			}
		}
	}
	
	private void fillTable() throws BOSException{
		if(getUIContext().get("curProjectId") != null){
			prmtType.setEnabled(true);
			prmtType.setEditable(false);
			String prjId = (String) getUIContext().get("curProjectId");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",prjId,CompareType.EQUALS));
			view.setFilter(filter);
			FDCWBSCollection fdcWBSCol = FDCWBSFactory.getRemoteInstance().getFDCWBSCollection(view);
		}
	}
	
}