package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.pm.QualityCheckPointEntryInfo;
import com.kingdee.eas.fdc.pm.QualityCheckPointInfo;
import com.kingdee.eas.fdc.pm.QualityTaskAssignCollection;
import com.kingdee.eas.fdc.pm.QualityTaskAssignInfo;
import com.kingdee.eas.fdc.pm.QualityTaskCheckEntryCollection;
import com.kingdee.eas.fdc.pm.QualityTaskCheckEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskQualityPlanEventEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskQualityPlanEventEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo;
import com.kingdee.eas.fdc.schedule.TaskQualityPreventionEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskQualityPreventionEntryInfo;
import com.kingdee.eas.util.SysUtil;

public class QualityPlanPanelHelper {
	private final TaskExtProPanelHelper taskExtPropHelper;
	private TaskQualityPlanInfo qualityPlanInfo;
	private KDTable tblPrevention;
	private KDTable tblBigEvent;
	private KDTable tblCheckItem;
	private KDTable tblCheckPosition;
	private KDContainer conPrevention;
	private KDContainer conBigEvent;
//	private KDPanel panelQualityFault;
	private KDTable tblCheckResult;
	private final String COL_ENT_NUM = "number";
	private final String COL_ENT_NAME = "name";
	private final String COL_ENT_OPIN = "suggestion";
	private final String COL_PRE_NAME = "name";
	private final String COL_PRE_MEA = "prevention";
	
	public QualityPlanPanelHelper(TaskExtProPanelHelper helper) {
		this.taskExtPropHelper = helper;
		this.tblPrevention = helper.getFDCSCHTaskPropUI().tblPrevention;
		this.tblBigEvent = helper.getFDCSCHTaskPropUI().tblQualityBigEvent;
		this.tblCheckItem = helper.getFDCSCHTaskPropUI().tblQCItem;
		this.tblCheckPosition = helper.getFDCSCHTaskPropUI().tblQCPosition;
//		this.panelQualityFault = helper.getFDCSCHTaskPropUI().qualityFaultPanel;
		this.tblCheckResult = helper.getFDCSCHTaskPropUI().tblQtyRelt;
		this.conPrevention = helper.getFDCSCHTaskPropUI().conPrevention;
		this.conBigEvent = helper.getFDCSCHTaskPropUI().conBigEvent;
	}

	public void load() {
		tblPrevention.checkParsed();
		tblBigEvent.checkParsed();
		tblCheckItem.checkParsed();
		tblCheckPosition.checkParsed();
		tblCheckResult.checkParsed();
		qualityPlanInfo = (TaskQualityPlanInfo) taskExtPropHelper
				.getExtProperties().get("qualityPlan");
		if (taskExtPropHelper.getExtProperties().get("qualityPlan") == null) {
			qualityPlanInfo = new TaskQualityPlanInfo();
		}
		Map qtyCheckPointHead;
		Map qtyCheckPointEntrys;
		QualityTaskCheckEntryCollection qtyCheckResultEntrys;
		if (taskExtPropHelper.getExtProperties().get("qualityCheckPoint") == null) {
			qtyCheckPointHead = new HashMap();
		}else{
			qtyCheckPointHead =  (Map) taskExtPropHelper.getExtProperties().get("qualityCheckPoint");
		}
		if (taskExtPropHelper.getExtProperties().get("qualityCheckPointEntrys") == null) {
			qtyCheckPointEntrys = new HashMap();
		}else{
			qtyCheckPointEntrys = (Map) taskExtPropHelper.getExtProperties().get("qualityCheckPointEntrys");
		}
		if (taskExtPropHelper.getExtProperties().get("qualityResultEntrys") == null) {
			qtyCheckResultEntrys = new QualityTaskCheckEntryCollection();
		}else{
			qtyCheckResultEntrys =  (QualityTaskCheckEntryCollection) taskExtPropHelper.getExtProperties().get("qualityResultEntrys");
		}
		initTbl(qtyCheckPointHead,qtyCheckPointEntrys,qtyCheckResultEntrys);
		initTbl();
//		UIContext uiContext = new UIContext(this);
//		uiContext.put("wbs",taskExtPropHelper.getWBS());
//		CoreUIObject qualityBugUI;
//		try {
//			qualityBugUI = (CoreUIObject) UIFactoryHelper.initUIObject(QualityBugListUI.class.getName(), uiContext,
//					null, "VIEW");
//			panelQualityFault.setLayout(new BorderLayout());
//			panelQualityFault.add(qualityBugUI,BorderLayout.CENTER);
//		} catch (UIException e1) {
//			e1.printStackTrace();
//		}
		tblBigEvent.addKDTEditListener(new KDTEditListener(){
			public void editCanceled(KDTEditEvent e) {
			}
			public void editStarted(KDTEditEvent e) {
			}
			public void editStarting(KDTEditEvent e) {
			}
			public void editStopped(KDTEditEvent e) {
				int actRowIdx = tblBigEvent.getSelectManager().getActiveRowIndex();
				int actColIdx = tblBigEvent.getSelectManager().getActiveColumnIndex();
				if(actRowIdx < 0)	return;
				boolean nameEdited = false;
				boolean numberEdited = false;
				if(tblBigEvent.getColumnKey(actColIdx).equals(COL_ENT_NAME)){
					if(tblBigEvent.getCell(actRowIdx,COL_ENT_NAME).getValue() != null
							&& tblBigEvent.getCell(actRowIdx,COL_ENT_NAME).getValue().toString().trim().length() <1){
						return;
					}else{
						nameEdited = true;
					}
				}
				if(tblBigEvent.getColumnKey(actColIdx).equals(COL_ENT_NUM)){
					if(tblBigEvent.getCell(actRowIdx,COL_ENT_NUM).getValue() != null
							&& tblBigEvent.getCell(actRowIdx,COL_ENT_NUM).getValue().toString().trim().length() <1){
						return;
					}else{
						numberEdited = true;
					}						
				}
				if(!nameEdited && !numberEdited) return;
				String number = null;
				String name = null;
				if(numberEdited && tblBigEvent.getCell(actRowIdx, COL_ENT_NUM).getValue() != null)
					number = tblBigEvent.getCell(actRowIdx, COL_ENT_NUM).getValue().toString();
				if(nameEdited && tblBigEvent.getCell(actRowIdx, COL_ENT_NAME).getValue() != null)
					name = tblBigEvent.getCell(actRowIdx, COL_ENT_NAME).getValue().toString();
				for(int i=0;i<tblBigEvent.getRowCount();i++){
					if(i == actRowIdx) continue;
					if(nameEdited && tblBigEvent.getCell(i,COL_ENT_NAME).getValue() != null
							&& name.equals(tblBigEvent.getCell(i,COL_ENT_NAME).getValue().toString())){
						FDCMsgBox.showError("第"+(i+1)+"行与第"+(actRowIdx+1)+"行的名称重复！");
						tblBigEvent.getCell(actRowIdx,COL_ENT_NAME).setValue(e.getOldValue());
					}
					if(numberEdited && tblBigEvent.getCell(i,COL_ENT_NUM).getValue() != null
							&& number.equals(tblBigEvent.getCell(i,COL_ENT_NUM).getValue().toString())){
						FDCMsgBox.showError("第"+(i+1)+"行与第"+(actRowIdx+1)+"行的编码重复！");
						tblBigEvent.getCell(actRowIdx,COL_ENT_NUM).setValue(e.getOldValue());
					}
				}
			
			}
			public void editStopping(KDTEditEvent e) {
			}
			public void editValueChanged(KDTEditEvent e) {
			}
		});
		tblPrevention.addKDTEditListener(new KDTEditListener(){
			public void editCanceled(KDTEditEvent e) {
			}
			public void editStarted(KDTEditEvent e) {
			}
			public void editStarting(KDTEditEvent e) {
			}
			public void editStopped(KDTEditEvent e) {
				int actRowIdx = tblPrevention.getSelectManager().getActiveRowIndex();
				int actColIdx = tblPrevention.getSelectManager().getActiveColumnIndex();
				if(actRowIdx < 0)	return;
				boolean nameEdited = false;
				if(tblPrevention.getColumnKey(actColIdx).equals(COL_PRE_NAME)){
					if(tblPrevention.getCell(actRowIdx,COL_PRE_NAME).getValue() != null
							&& tblPrevention.getCell(actRowIdx,COL_PRE_NAME).getValue().toString().trim().length() <1){
						return;
					}else{
						nameEdited = true;
					}
				}
				if(!nameEdited) return;
				String name = null;
				if(nameEdited && tblPrevention.getCell(actRowIdx, COL_PRE_NAME).getValue() != null)
					name = tblPrevention.getCell(actRowIdx, COL_PRE_NAME).getValue().toString();
				for(int i=0;i<tblPrevention.getRowCount();i++){
					if(i == actRowIdx) continue;
					if(nameEdited && tblPrevention.getCell(i,COL_PRE_NAME).getValue() != null
							&& name.equals(tblPrevention.getCell(i,COL_PRE_NAME).getValue().toString())){
						FDCMsgBox.showError("第"+(i+1)+"行与第"+(actRowIdx+1)+"行的名称重复！");
						tblPrevention.getCell(actRowIdx,COL_PRE_NAME).setValue(e.getOldValue());
					}
				}
			}
			public void editStopping(KDTEditEvent e) {
			}
			public void editValueChanged(KDTEditEvent e) {
			}
		});
	}

	// 初始化质量检查点的两个表格
	private void initTbl(Map qtyCheckPointHead,Map qtyCheckPointEntrys, QualityTaskCheckEntryCollection qtyRsltEntrys) {
		Object[] keys = qtyCheckPointHead.keySet().toArray();
		for(int i=0;i<keys.length;i++){
			QualityCheckPointInfo head = (QualityCheckPointInfo) qtyCheckPointHead.get(keys[i]);
			IRow row = tblCheckItem.addRow();
			row.setUserObject(qtyCheckPointEntrys.get(keys[i]));
			row.getCell("longNumber").setValue(head.getLongNumber());
			row.getCell("name").setValue(head.getName());
			row.getCell("id").setValue(head.getId());
			row.getCell("chkCriterion").setValue(head.getCheckCriterion());
			row.getCell("description").setValue(head.getDescription());
		}
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat(){
			public String format(Object o) {
				if(o != null && o instanceof String)
					return o.toString().replace('!','.');
				return null;
			}
		});
		tblCheckItem.getColumn("longNumber").setRenderer(render);
		tblCheckItem.addKDTSelectListener(new KDTSelectListener(){
			public void tableSelectChanged(KDTSelectEvent e) {
				int actRowIdx = tblCheckItem.getSelectManager().getActiveRowIndex();
				if(actRowIdx < 0) return;
				if(tblCheckItem.getCell(actRowIdx, "id").getValue()!=null){
					tblCheckPosition.removeRows();
					QualityTaskAssignCollection subCol = null;
					if(tblCheckItem.getRow(actRowIdx).getUserObject() != null){
						subCol = (QualityTaskAssignCollection) tblCheckItem.getRow(actRowIdx).getUserObject();
					}
					for(int j=0;j<subCol.size();j++){
						QualityTaskAssignInfo subInfo = subCol.get(j);
						IRow row  = tblCheckPosition.addRow();
						QualityCheckPointEntryInfo subEntry = subInfo.getCheckPointEntry();
						if(subEntry.getCheckProp() != null)
								row.getCell("samplePropotion").setValue(subEntry.getCheckProp());
						if(subEntry.getCheckPost() != null)
							row.getCell("position").setValue(subEntry.getCheckPost().getName());
						if(subInfo.getCheckPerson() != null)
							row.getCell("person").setValue(subInfo.getCheckPerson().getName());
						row.getCell("checkFrequency").setValue(new Integer(subInfo.getCheckCount()));
					}
				}
			}
		});
		
		for(int i=0;i<qtyRsltEntrys.size();i++){
			QualityTaskCheckEntryInfo info = qtyRsltEntrys.get(i);
			if(info != null){
				IRow row = tblCheckResult.addRow();
				row.getCell("id").setValue(info.getId());
				if(info.getCheckPoint() != null){
					row.getCell("checkItem").setValue(info.getCheckPoint().getName());
				}
				if(info.getSpecialPost() != null){
					row.getCell("checkPostion").setValue(info.getSpecialPost().getName());
				}
				row.getCell("checkPropotion").setValue(info.getCheckProp());
				row.getCell("score").setValue(info.getValue());
				row.getCell("result").setValue(info.getResult());
				row.getCell("memo").setValue(info.getDescription());
			}
		}
	}

	public Map store(boolean isExecuting) {
		Map map = new HashMap();
		if(isExecuting){
			TaskQualityPlanEventEntryCollection eventCol = new TaskQualityPlanEventEntryCollection();
			for (int i = 0; i < tblBigEvent.getRowCount(); i++) {
				TaskQualityPlanEventEntryInfo eventInfo = new TaskQualityPlanEventEntryInfo();
				IRow row = tblBigEvent.getRow(i);
				if(row.getCell("name").getValue() == null){
					FDCMsgBox.showError("重大质量事件名称不能为空");
					SysUtil.abort();
				}
				if(row.getCell("number").getValue() == null){
					FDCMsgBox.showError("重大质量 编码名称不能为空");
					SysUtil.abort();
				}
				if (row.getCell("id").getValue() != null) {
					eventInfo.setId(BOSUuid.read(row.getCell("id").getValue()
							.toString()));
				} else {
					eventInfo.setId(null);
				}
				if (row.getCell("name").getValue() != null) {
					eventInfo.setName(row.getCell("name").getValue().toString());
				} else {
					eventInfo.setName(null);
				}
				if (row.getCell("number").getValue() != null) {
					eventInfo
							.setNumber(row.getCell("number").getValue().toString());
				} else {
					eventInfo.setNumber(null);
				}
				if (row.getCell("happenTime").getValue() != null) {
					eventInfo.setHappenTime((Date) row.getCell("happenTime")
							.getValue());
				} else {
					eventInfo.setHappenTime(null);
				}
				if (row.getCell("directPerson").getValue() != null) {
					eventInfo.setDirectAdminPerson((PersonInfo) row.getCell(
							"directPerson").getValue());
				} else {
					eventInfo.setDirectAdminPerson(null);
				}
				if (row.getCell("indirectPerson").getValue() != null) {
					eventInfo.setIndirectAdminPerson((PersonInfo) row.getCell(
							"indirectPerson").getValue());
				} else {
					eventInfo.setIndirectAdminPerson(null);
				}
				if (row.getCell("suggestion").getValue() != null) {
					eventInfo.setSuggestion(row.getCell("suggestion").getValue()
							.toString());
				} else {
					eventInfo.setSuggestion(null);
				}
				eventInfo.setSeq(row.getRowIndex());
				eventInfo.setParent(qualityPlanInfo);
				eventCol.add(eventInfo);
			}
			qualityPlanInfo.getEventEntrys().clear();
			qualityPlanInfo.getEventEntrys().addCollection(eventCol);
//			map.put("qualityEvent", eventCol);
		}else{
			TaskQualityPreventionEntryCollection preventionCol = new TaskQualityPreventionEntryCollection();
			for (int i = 0; i < tblPrevention.getRowCount(); i++) {
				TaskQualityPreventionEntryInfo preventionInfo = new TaskQualityPreventionEntryInfo();
				IRow row = tblPrevention.getRow(i);
				if(row.getCell("name").getValue() == null){
					FDCMsgBox.showError("通病及防治名称不能为空");
					SysUtil.abort();
				}
				if (row.getCell("id").getValue() != null) {
					preventionInfo.setId(BOSUuid.read(row.getCell("id").getValue()
							.toString()));
				} else {
					preventionInfo.setId(null);
				}
				if (row.getCell("name").getValue() != null) {
					preventionInfo.setName(row.getCell("name").getValue()
							.toString());
				} else {
					preventionInfo.setName(null);
				}
				if (row.getCell("prevention").getValue() != null) {
					preventionInfo.setPrevention(row.getCell("prevention")
							.getValue().toString());
				} else {
					preventionInfo.setPrevention(null);
				}
				preventionInfo.setSeq(row.getRowIndex());
				preventionInfo.setParent(qualityPlanInfo);
				preventionCol.add(preventionInfo);
			}
			qualityPlanInfo.getPreventionEntrys().clear();
			qualityPlanInfo.getPreventionEntrys().addCollection(preventionCol);
//			map.put("qualityPrevention", preventionCol);
		}
		map.put("qualityPlan", qualityPlanInfo);
		return map;
	}

	// 初始化通病防治、重大事件两个表格 (表格的新增、插入、删除可以抽到其他方法中)
	KDWorkButton btnPreventAddLine = new KDWorkButton("新增行",	SCHClientHelper.ICON_ADDLINE);
	KDWorkButton btnPreventRmvLine = new KDWorkButton("删除行",	SCHClientHelper.ICON_REMOVELINE);
	KDWorkButton btnEventAddLine = new KDWorkButton("新增行",	SCHClientHelper.ICON_ADDLINE);
	KDWorkButton btnEventDelLine = new KDWorkButton("删除行",	SCHClientHelper.ICON_REMOVELINE);
	public void initTbl() {
		conPrevention.addButton(btnPreventAddLine);
		conPrevention.addButton(btnPreventRmvLine);
		btnPreventAddLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tblPrevention.addRow();
			}
		});
		btnPreventRmvLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblPrevention.getSelectManager().getActiveRowIndex();
				if(actRowIdx <0 ){
					FDCMsgBox.showError("请先选中行！");
					SysUtil.abort();
				}
				tblPrevention.removeRow(actRowIdx);
			}
		});
		tblPrevention.getColumn("prevention").setWidth(200);
		
		conBigEvent.addButton(btnEventAddLine);
		conBigEvent.addButton(btnEventDelLine);
		btnEventAddLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tblBigEvent.addRow();
			}
		});
		btnEventDelLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblBigEvent.getSelectManager().getActiveRowIndex();
				if(actRowIdx <0){
					FDCMsgBox.showError("请先选中行！");
					SysUtil.abort();
				}
				tblBigEvent.removeRow(actRowIdx);
			}
		});
		KDDatePicker prmtHappenTime = new KDDatePicker();
		KDBizPromptBox prmtPerson = new KDBizPromptBox();
		prmtPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
		prmtPerson.setEditable(false);
		tblBigEvent.getColumn("happenTime").setEditor(new KDTDefaultCellEditor(prmtHappenTime));
		tblBigEvent.getColumn("directPerson").setEditor(new KDTDefaultCellEditor(prmtPerson));
		tblBigEvent.getColumn("indirectPerson").setEditor(new KDTDefaultCellEditor(prmtPerson));
		tblBigEvent.getColumn("name").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		tblBigEvent.getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(80);
		tblBigEvent.getColumn(COL_ENT_NUM).setEditor(new KDTDefaultCellEditor(txtField));
		tblBigEvent.getColumn(COL_ENT_NAME).setEditor(new KDTDefaultCellEditor(txtField));
		tblPrevention.getColumn(COL_PRE_NAME).setEditor(new KDTDefaultCellEditor(txtField));
		txtField.setMaxLength(250);
		tblBigEvent.getColumn(COL_ENT_OPIN).setEditor(new KDTDefaultCellEditor(txtField));
		tblPrevention.getColumn(COL_PRE_MEA).setEditor(new KDTDefaultCellEditor(txtField));
		
		tblPrevention.getColumn(COL_PRE_NAME).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		
		fillTable();
	}

	public void setExecutingStatus() {
		btnPreventAddLine.setEnabled(false);
		btnPreventRmvLine.setEnabled(false);
		btnEventAddLine.setEnabled(true);
		btnEventDelLine.setEnabled(true);
		tblBigEvent.getStyleAttributes().setLocked(false);
		tblCheckItem.getStyleAttributes().setLocked(true);
		tblCheckPosition.getStyleAttributes().setLocked(true);
		tblPrevention.getStyleAttributes().setLocked(true);
		tblCheckResult.getStyleAttributes().setLocked(true);
	}

	public void setEditStatus() {
		btnPreventAddLine.setEnabled(true);
		btnPreventRmvLine.setEnabled(true);
		btnEventAddLine.setEnabled(false);
		btnEventDelLine.setEnabled(false);
		tblBigEvent.getStyleAttributes().setLocked(true);
		tblCheckItem.getStyleAttributes().setLocked(true);
		tblCheckPosition.getStyleAttributes().setLocked(true);
		tblPrevention.getStyleAttributes().setLocked(false);
		tblCheckResult.getStyleAttributes().setLocked(true);
	}

	public void setViewStatus() {
		btnPreventAddLine.setEnabled(false);
		btnPreventRmvLine.setEnabled(false);
		btnEventAddLine.setEnabled(false);
		btnEventDelLine.setEnabled(false);
		tblBigEvent.getStyleAttributes().setLocked(true);
		tblCheckItem.getStyleAttributes().setLocked(true);
		tblCheckPosition.getStyleAttributes().setLocked(true);
		tblPrevention.getStyleAttributes().setLocked(true);
		tblCheckResult.getStyleAttributes().setLocked(true);
	}

	private void fillTable() {
		TaskQualityPreventionEntryCollection preventionCol = qualityPlanInfo
				.getPreventionEntrys();
		TaskQualityPlanEventEntryCollection eventCol = qualityPlanInfo
				.getEventEntrys();
		for (int i = 0; i < preventionCol.size(); i++) {
			TaskQualityPreventionEntryInfo preventionInfo = preventionCol
					.get(i);
			IRow row = tblPrevention.addRow();
			row.getCell("id").setValue(preventionInfo.getId());
			row.getCell("name").setValue(preventionInfo.getName());
			row.getCell("prevention").setValue(preventionInfo.getPrevention());
		}
		for (int i = 0; i < eventCol.size(); i++) {
			TaskQualityPlanEventEntryInfo eventInfo = eventCol.get(i);
			IRow row = tblBigEvent.addRow();
			row.getCell("id").setValue(eventInfo.getId());
			row.getCell("name").setValue(eventInfo.getName());
			row.getCell("number").setValue(eventInfo.getNumber());
			row.getCell("happenTime").setValue(eventInfo.getHappenTime());
			row.getCell("directPerson").setValue(
					eventInfo.getDirectAdminPerson());
			row.getCell("indirectPerson").setValue(
					eventInfo.getIndirectAdminPerson());
			row.getCell("suggestion").setValue(eventInfo.getSuggestion());
		}
	}
	
	
}
