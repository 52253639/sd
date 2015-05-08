package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.pm.DrawingManagerEntryFactory;
import com.kingdee.eas.fdc.pm.DrawingManagerEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskWorkResultEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileFactory;
import com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileInfo;
import com.kingdee.eas.fdc.schedule.TaskWorkResultEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskWorkResultInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public class WorkResultPanelHelper {
	private final TaskExtProPanelHelper taskExtPropHelper;
	private KDTable tblWorkResult;
	private KDContainer conWorkResult;
	private TaskWorkResultInfo workResultInfo;
	private final String COL_NUM = "number";
	private final String COL_NAME = "name";
	private final String COL_TYPE = "type";
	private final String COL_CREATOR = "creator";
	public WorkResultPanelHelper(TaskExtProPanelHelper helper) {
		this.taskExtPropHelper = helper;
		tblWorkResult = helper.getFDCSCHTaskPropUI().tblWorkResult;
		conWorkResult = helper.getFDCSCHTaskPropUI().conWorkResult;
	} 
	public void load(){
		tblWorkResult.checkParsed();
		workResultInfo = (TaskWorkResultInfo) taskExtPropHelper.getExtProperties().get("workResult");
		if(workResultInfo == null){
			workResultInfo = new TaskWorkResultInfo();
		}
		fillTable();
		initTbl();
	}
	public Map store(){
		Map map = new HashMap();
		TaskWorkResultEntryCollection resultEntryCol = new TaskWorkResultEntryCollection();
		for(int i=0;i<tblWorkResult.getRowCount();i++){
			TaskWorkResultEntryInfo resultEntryInfo = new TaskWorkResultEntryInfo();
			IRow row = tblWorkResult.getRow(i);
			if(row.getCell("number").getValue() == null){
				FDCMsgBox.showError("工作成果编号不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("name").getValue() == null){
				FDCMsgBox.showError("工作成果名称不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("id").getValue() != null){
				resultEntryInfo.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
			}else{
				resultEntryInfo.setId(null);
			}
			if(row.getCell("number").getValue() != null){
				resultEntryInfo.setNumber(row.getCell("number").getValue().toString());
			}else{
				resultEntryInfo.setNumber(null);
			}
			if(row.getCell("name").getValue() != null){
				resultEntryInfo.setName(row.getCell("name").getValue().toString());
			}else{
				resultEntryInfo.setName(null);
			}
			if(row.getCell("type").getValue() != null){
				resultEntryInfo.setResultType(row.getCell("type").getValue().toString());
			}else{
				resultEntryInfo.setResultType(null);
			}
			if(row.getCell("creator").getValue() != null){
				resultEntryInfo.setCreator((UserInfo) row.getCell("creator").getValue());
			}else{
				resultEntryInfo.setCreator(null);
			}
			if(row.getCell("createTime").getValue() != null){
				resultEntryInfo.setCreateTime((Date) row.getCell("createTime").getValue());
			}else{
				resultEntryInfo.setCreateTime(null);
			}
			if(row.getCell("adminDept").getValue() != null){
				resultEntryInfo.setAdminDept((FullOrgUnitInfo) row.getCell("adminDept").getValue());
			}else{
				resultEntryInfo.setAdminDept(null);
			}
			if(row.getCell("attachmentID").getValue() != null){
				TaskWorkResultEntryFileInfo file = new TaskWorkResultEntryFileInfo();
				file.setId(BOSUuid.read(row.getCell("attachmentID").getValue().toString()));
				resultEntryInfo.setFile(file);
			}else{
				resultEntryInfo.setFile(null);
			}
			resultEntryInfo.setParent(workResultInfo);
			resultEntryCol.add(resultEntryInfo);
		}
		workResultInfo.getEntrys().clear();
		workResultInfo.getEntrys().addCollection(resultEntryCol);
		map.put("workResult", workResultInfo);
		return map;
	}
	
	KDWorkButton btnAddLine = new KDWorkButton("新增行",	SCHClientHelper.ICON_ADDLINE);
	KDWorkButton btnDelLine = new KDWorkButton("删除行",	SCHClientHelper.ICON_REMOVELINE);
	KDWorkButton btnAddFile = new KDWorkButton("添加附件",	SCHClientHelper.ICON_ADDFILE);
	KDWorkButton btnDownFile = new KDWorkButton("下载附件",	SCHClientHelper.ICON_DOWNFILE);
	KDWorkButton btnDelFile = new KDWorkButton("删除附件",	SCHClientHelper.ICON_DELFILE);
	public void initTbl(){
		conWorkResult.addButton(btnAddLine);
		conWorkResult.addButton(btnDelLine);
		btnAddLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				checkAdminPerson();
				IRow row = tblWorkResult.addRow();
				row.getCell("adminDept").setValue(
						((FDCScheduleTaskInfo)taskExtPropHelper.getTaskInfo()).getAdminDept());
			}
		});
		btnDelLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				checkAdminPerson();
				int actRowIdx = tblWorkResult.getSelectManager().getActiveRowIndex();
				tblWorkResult.removeRow(actRowIdx);
			}
		});
		KDDatePicker prmtCreatTime = new KDDatePicker();
		KDBizPromptBox prmtPerson = new KDBizPromptBox();
		prmtPerson.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		KDBizPromptBox prmtAdminDept = new KDBizPromptBox();
		prmtAdminDept.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");
		tblWorkResult.getColumn("createTime").setEditor(new KDTDefaultCellEditor(prmtCreatTime));
		prmtPerson.setEditable(false);
		tblWorkResult.getColumn("creator").setEditor(new KDTDefaultCellEditor(prmtPerson));
		tblWorkResult.getColumn("adminDept").setEditor(new KDTDefaultCellEditor(prmtAdminDept));
		tblWorkResult.getColumn("adminDept").getStyleAttributes().setLocked(true);
		conWorkResult.addButton(btnAddFile);
		conWorkResult.addButton(btnDownFile);
		conWorkResult.addButton(btnDelFile);
		btnAddFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				checkAdminPerson();
				int actRowIdx = tblWorkResult.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				TaskWorkResultEntryFileInfo fileInfo;
				fileInfo = uploadFile();
				tblWorkResult.getCell(actRowIdx, "attachmentID").setValue(fileInfo.getId());
				tblWorkResult.getCell(actRowIdx, "attachmentName").setValue(fileInfo.getName());
			}
		});
		btnDownFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblWorkResult.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				downloadFile(actRowIdx);
			}
		});
		btnDelFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				checkAdminPerson();
				int actRowIdx = tblWorkResult.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String fileID = null;
				if(tblWorkResult.getCell(actRowIdx, "attachmentID").getValue() != null){
					fileID = tblWorkResult.getCell(actRowIdx, "attachmentID").getValue().toString();
				}
				if(fileID == null ||fileID.trim().length()<1){
					FDCMsgBox.showError("该记录没有对应的附件！");
					SysUtil.abort();
				}
				try {
					TaskWorkResultEntryFileFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(fileID)));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				FDCMsgBox.showWarning("附件["+tblWorkResult.getCell(actRowIdx, "attachmentName").getValue()+"]已经删除！");
				tblWorkResult.getCell(actRowIdx, "attachmentID").setValue(null);
				tblWorkResult.getCell(actRowIdx, "attachmentName").setValue(null);
			}
		});
		IColumn colName = tblWorkResult.getColumn(COL_NAME);
		IColumn colNumber = tblWorkResult.getColumn(COL_NUM);
		IColumn colType = tblWorkResult.getColumn(COL_TYPE);
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(80);
		colName.setEditor(new KDTDefaultCellEditor(txtField));
		colNumber.setEditor(new KDTDefaultCellEditor(txtField));
		colType.setEditor(new KDTDefaultCellEditor(txtField));
		colName.getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		colNumber.getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		tblWorkResult.addKDTEditListener(new KDTEditListener(){
			public void editCanceled(KDTEditEvent e) {
			}
			public void editStarted(KDTEditEvent e) {
			}
			public void editStarting(KDTEditEvent e) {
			}
			public void editStopped(KDTEditEvent e) {
				int actRowIdx = tblWorkResult.getSelectManager().getActiveRowIndex();
				int actColIdx = tblWorkResult.getSelectManager().getActiveColumnIndex();
				if(actRowIdx < 0)	return;
				boolean nameEdited = false;
				boolean numberEdited = false;
				if(tblWorkResult.getColumnKey(actColIdx).equals(COL_NAME)){
					if(tblWorkResult.getCell(actRowIdx,COL_NAME).getValue() != null
							&& tblWorkResult.getCell(actRowIdx,COL_NAME).getValue().toString().trim().length() <1){
						return;
					}else{
						nameEdited = true;
					}
				}
				if(tblWorkResult.getColumnKey(actColIdx).equals(COL_NUM)){
					if(tblWorkResult.getCell(actRowIdx,COL_NUM).getValue() != null
							&& tblWorkResult.getCell(actRowIdx,COL_NUM).getValue().toString().trim().length() <1){
						return;
					}else{
						numberEdited = true;
					}						
				}
				if(!nameEdited && !numberEdited) return;
				String number = null;
				String name = null;
				if(numberEdited && tblWorkResult.getCell(actRowIdx, COL_NUM).getValue() != null)
					number = tblWorkResult.getCell(actRowIdx, COL_NUM).getValue().toString();
				if(nameEdited && tblWorkResult.getCell(actRowIdx, COL_NAME).getValue()!=null)
					name = tblWorkResult.getCell(actRowIdx, COL_NAME).getValue().toString();
				for(int i=0;i<tblWorkResult.getRowCount();i++){
					if(i == actRowIdx) continue;
					if(nameEdited && tblWorkResult.getCell(i,COL_NAME).getValue() != null
							&& tblWorkResult.getCell(i,COL_NAME).getValue().toString().equals(name)){
						FDCMsgBox.showError("第"+(i+1)+"行与第"+(actRowIdx+1)+"行的名称重复！");
						tblWorkResult.getCell(actRowIdx,COL_NAME).setValue(e.getOldValue());
					}
					if(numberEdited && tblWorkResult.getCell(i,COL_NUM).getValue() != null
							&& tblWorkResult.getCell(i,COL_NUM).getValue().toString().equals(number)){
						FDCMsgBox.showError("第"+(i+1)+"行与第"+(actRowIdx+1)+"行的编码重复！");
						tblWorkResult.getCell(actRowIdx,COL_NUM).setValue(e.getOldValue());
					}
				}
			}
			public void editStopping(KDTEditEvent e) {
			}
			public void editValueChanged(KDTEditEvent e) {
			}
		});
	}
	public void setEditStatus() {
		btnAddLine.setEnabled(false);
		btnDelLine.setEnabled(false);
		btnAddFile.setEnabled(false);
		btnDelFile.setEnabled(false);
		btnDownFile.setEnabled(false);
		tblWorkResult.getStyleAttributes().setLocked(true);
	}
	public void setViewStatus() {
		btnAddLine.setEnabled(false);
		btnDelLine.setEnabled(false);
		btnAddFile.setEnabled(false);
		btnDelFile.setEnabled(false);
		btnDownFile.setEnabled(false);
		tblWorkResult.getStyleAttributes().setLocked(true);
	}
	public void setExecutingStatus() {
		btnAddLine.setEnabled(true);
		btnDelLine.setEnabled(true);
		btnAddFile.setEnabled(true);
		btnDelFile.setEnabled(true);
		btnDownFile.setEnabled(true);
		tblWorkResult.getStyleAttributes().setLocked(false);
	}
	
	private void fillTable() {
		TaskWorkResultEntryCollection workResulCol = workResultInfo.getEntrys();
		for(int i=0;i<workResulCol.size();i++){
			TaskWorkResultEntryInfo workResultInfo = workResulCol.get(i);
			IRow row = tblWorkResult.addRow();
			row.getCell("id").setValue(workResultInfo.getId());
			row.getCell("name").setValue(workResultInfo.getName());
			row.getCell("number").setValue(workResultInfo.getNumber());
			row.getCell("type").setValue(workResultInfo.getResultType());
			if(workResultInfo.getCreator() != null){
				row.getCell("creator").setValue(workResultInfo.getCreator());
			}
			row.getCell("createTime").setValue(workResultInfo.getCreateTime());
			if(workResultInfo.getAdminDept() != null){
				row.getCell("adminDept").setValue(workResultInfo.getAdminDept());
			}
			if(workResultInfo.getFile() != null){
				row.getCell("attachmentName").setValue(workResultInfo.getFile().getName());
				row.getCell("attachmentID").setValue(workResultInfo.getFile().getId());
			}else{
				row.getCell("attachmentName").setValue(null);
				row.getCell("attachmentID").setValue(null);
			}
		}
	}
	
	private TaskWorkResultEntryFileInfo uploadFile(){
		File file = chooseFileByDialog();
		TaskWorkResultEntryFileInfo fileEntry = new TaskWorkResultEntryFileInfo();
		if (file == null) {
			FDCMsgBox.showWarning("请选择附件！");
			SysUtil.abort();
		}
		if (!file.canRead()) {
			FDCMsgBox.showWarning(ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		String fullname = file.getName();
		byte content[] = (byte[]) null;
		try {
			content = FileGetter.getBytesFromFile(file);
			if (content == null || content.length == 0) {
				MsgBox.showWarning("所添加的附件大小不能为0字节！");
				SysUtil.abort();
			}
			fileEntry.setName(fullname);
			fileEntry.setUploadDate(new Date());
			fileEntry.setData(content);
				fileEntry.setId(BOSUuid.read(TaskWorkResultEntryFileFactory
						.getRemoteInstance().addnew(fileEntry).toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileEntry;
	}
	private File chooseFileByDialog() {
		File retFile = null;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		int retVal = fc.showOpenDialog(this.conWorkResult);
		if (retVal == 1)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > 0x3200000L) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		} else {
			return retFile;
		}
	}
	private void checkSelected(int actRowIdx) {
		if(actRowIdx < 0){
			FDCMsgBox.showError("请先选中行！");
			SysUtil.abort();
		}
	}
	private void downloadFile(int actRowIdx) {
		String fileID = null;
		if(tblWorkResult.getCell(actRowIdx, "attachmentID").getValue() != null){
			fileID = tblWorkResult.getCell(actRowIdx, "attachmentID").getValue().toString();
		}
		if(fileID == null || fileID.trim().length() < 1){
			FDCMsgBox.showError("该记录没有上传附件！");
			SysUtil.abort();
		}
		TaskWorkResultEntryFileInfo fileInfo = null;
		String fileNameUser = null;
		String path = null;
		try {
			fileInfo = TaskWorkResultEntryFileFactory
				.getRemoteInstance().getTaskWorkResultEntryFileInfo(
						new ObjectUuidPK(BOSUuid.read(fileID)));
			KDFileChooser fc = new KDFileChooser();
			fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
			fc.setSelectedFile(new File(fileInfo.getName()));
			fc.setMultiSelectionEnabled(false);
			fc.setCurrentDirectory(null);
			fc.setAcceptAllFileFilterUsed(true);
			
			// 显示选择路径的对话框
			int rv = fc.showSaveDialog(conWorkResult);
			if (rv == KDFileChooser.CANCEL_OPTION)
				return;
			File rf = fc.getSelectedFile();
			if (rf.exists()) {
				path = fc.getCurrentDirectory().getPath();
				String tempMayPath = fc.getSelectedFile().toString();
				fileNameUser = tempMayPath.substring(tempMayPath
						.lastIndexOf(File.separator) + 1, tempMayPath.length());
			} else {
				// 需要查找最后一个分隔符,判断前面是否是路径,如果是则返回路径和文件名称,否则退出
				String tempMayPath = rf.getPath();
				int lastPos = tempMayPath.lastIndexOf(File.separator);
				String tempPath = tempMayPath.substring(0, lastPos);

				File tempFile = new File(tempPath);

				if (tempFile.exists() && !tempFile.isFile()) {
					path = tempPath;
					fileNameUser = tempMayPath.substring(lastPos + 1,
							tempMayPath.length());
				} else {
					MsgBox.showInfo(Resrcs.getString("PathNotExisted"));
				}
			}
			File file = new File(path + "\\" + fileNameUser);
			FileOutputStream fos;
			fos = new FileOutputStream(file);
			fos.write(fileInfo.getData());
			fos.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		FDCMsgBox.showWarning("文件[" + fileNameUser + "]下载文件成功！\n文件保存在["	+ path + "]下！");
	}
	
	private void checkAdminPerson(){
		FDCScheduleTaskInfo taskInfo = null;
		if(taskExtPropHelper.getTaskInfo() != null){
			taskInfo = (FDCScheduleTaskInfo) taskExtPropHelper.getTaskInfo();
		}
		if(taskInfo != null && taskInfo.getAdminPerson() != null
				&& SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString().equals(
						taskInfo.getAdminPerson().getId().toString())){
		}else{
			FDCMsgBox.showError("当前用户不是任务责任人，只能进行下载附件操作！");
			SysUtil.abort();
		}
	}
}
