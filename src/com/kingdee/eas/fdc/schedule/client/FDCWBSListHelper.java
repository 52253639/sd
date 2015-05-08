package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.WBSAdjustManager;
import com.kingdee.eas.fdc.schedule.FDCWBSTree.FDCWBSTreeNode;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public class FDCWBSListHelper {
	private static final String COL_PARENT_ID = "parent.id";
	private static final String COL_PROJECT_ID = "curProject.id";
	private static final String COL_ID = "id";
	private static final String COL_TASKTYPE_ID = "taskType.id";
	private static final String COL_TASKTYPE_NAME = "taskType.name";
	private static final String COL_ADMIN_DEPT_NAME = "adminDept.name";
	private static final String COL_RESP_DEPT_NAME = "respDept.name";
	private static final String COL_ADMIN_PERSON_NAME = "adminPerson.name";
	private static final String COL_ESTIMATE_DAYS = "estimateDays";
	private static final String COL_ISENABLED = "isEnabled";
	private static final String TYPE_DOWN = "DOWN";
	private static final String TYPE_UP = "UP";
	private static final String COL_NAME = "name";
	private static final String COL_LONG_NUMBER = "longNumber";
	private static final String COL_LEVEL = "level";
	private static final String SYS_DEFAULT_NUM = "000";
	private final FDCWBSListUI ui;
	private KDTable tblMain;
	private FDCWBSTree wbsTree;
	private boolean isChanged = false;
	public FDCWBSListHelper(FDCWBSListUI ui){
		this.ui = ui; 
		this.tblMain = ui.getMainTable();
		this.tblMain.addKDTDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				initTree();
			}});
	}
	public void setWBSTree(FDCWBSTree wbsTree){
		this.wbsTree = wbsTree;
	}
//	获取上级的RowIndex
	private int getParentRowIdx(int rowIdx){
		int parentIdx = 0;
		IRow row = tblMain.getRow(rowIdx);
		int level = Integer.parseInt(row.getCell(COL_LEVEL).getValue().toString());
		for(int i=rowIdx;i>0;i--){
			IRow tempRow = tblMain.getRow(i);
			int tempLevel = Integer.parseInt(tempRow.getCell(COL_LEVEL).getValue().toString());
			if(tempLevel<level)	return i;
		}
		return parentIdx;
	}

	
//	获取上一个兄弟节点
	private int getSibRowIdx(int rowIdx,String type){
		int sibRowIdx = 0;
		IRow row = tblMain.getRow(rowIdx);
		int level = Integer.parseInt(row.getCell(COL_LEVEL).getValue().toString());
		if(type.equals(TYPE_UP)){
			for(int i=rowIdx-1;i>0;i--){
				IRow tempRow = tblMain.getRow(i);
				int tempLevel = Integer.parseInt(tempRow.getCell(COL_LEVEL).getValue().toString());
				if(tempLevel < level) return 0;
				if(tempLevel == level)	return i;
			}
		}else if(type.equals(TYPE_DOWN)){
			for(int i=rowIdx+1;i<tblMain.getRowCount();i++){
				IRow tempRow = tblMain.getRow(i);
				int tempLevel = Integer.parseInt(tempRow.getCell(COL_LEVEL).getValue().toString());
				if(tempLevel < level) return rowIdx;
				if(tempLevel == level)	return i;
			}
		}
		return sibRowIdx;
	}
//	获取上级兄弟节点的RowIndex
	private int getUncleRowIdx(int rowIdx){
		int uncleIdx = tblMain.getRowCount();
		IRow row = tblMain.getRow(rowIdx);
		int level = Integer.parseInt(row.getCell(COL_LEVEL).getValue().toString());
		for(int i=rowIdx+1;i<tblMain.getRowCount();i++){
			IRow tempRow = tblMain.getRow(i);
			int tempLevel = Integer.parseInt(tempRow.getCell(COL_LEVEL).getValue().toString());
			if(tempLevel<level){
				return i;
//				if(tempLevel == level-1) return i;		//父节点有下一个兄弟
//				else if(tempLevel < level - 1) return i;	//父节点没有下一个兄弟
			}
		}
		return uncleIdx;
	}
//	获取下级节点(包括本身)
	private ArrayList getChildrenRowIdx(int rowIdx){
		ArrayList childrenIdxs = new ArrayList();
		IRow row = tblMain.getRow(rowIdx);
		int level = Integer.parseInt(row.getCell(COL_LEVEL).getValue().toString());
		childrenIdxs.add(new Integer(rowIdx));
		for(int i=rowIdx+1;i<tblMain.getRowCount();i++){
			IRow tempRow = tblMain.getRow(i);
			int tempLevel = Integer.parseInt(tempRow.getCell(COL_LEVEL).getValue().toString());
			if(tempLevel == level) break;
			if(tempLevel>level)	childrenIdxs.add(new Integer(i));
		}
		return childrenIdxs;
	}
//	升级:插入受影响的行并删除受影响的行
	private void _upgradeAffectedRows(int uncleRowIdx,ArrayList affectRowIdxs){
//		插入受影响的行
		for(int i=0;i<affectRowIdxs.size();i++){
			int affectRoxIdx = Integer.parseInt(affectRowIdxs.get(i).toString());
			IRow affectRow = tblMain.getRow(affectRoxIdx);
			// 升级的时候不允许上级工期小于下级工期  by cassiel 2010-06-25
			String selfLgNum = affectRow.getCell(COL_LONG_NUMBER).getValue().toString();
			if(selfLgNum.lastIndexOf('!')==-1){
				return ;
			}
			String desLgNum = selfLgNum.substring(0,selfLgNum.lastIndexOf('!'));
			int selfEstimateDays = ((Integer)tblMain.getCell(affectRoxIdx,"estimateDays").getValue()).intValue();
			String prjId = tblMain.getCell(affectRoxIdx,"curProject.id").getValue().toString();
			// 找到升级后的目标WBS的预估工期
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("estimateDays"));
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("longNumber",desLgNum));
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",prjId));
			view.setFilter(filter);
			view.setSelector(selector);
			FDCWBSCollection wsbColl = null;
			try {
				wsbColl = FDCWBSFactory.getRemoteInstance().getFDCWBSCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(wsbColl!=null&&wsbColl.size()>0){
				FDCWBSInfo wbs = wsbColl.get(0);
				int desEstimateDays = wbs.getEstimateDays();
				if(selfEstimateDays>desEstimateDays){
					MsgBox.showInfo("存在部分下级WBS预估工期大于上级WBS预估工期的数据，不能进行此操作！");
					SysUtil.abort();
				}
			}
			int newLevel = Integer.parseInt(affectRow.getCell(COL_LEVEL).getValue().toString()) - 1;
			if(newLevel <= 0) {
				affectRowIdxs.set(i, Integer.valueOf("-1"));
				return;
			}
			tblMain.addRow(uncleRowIdx+i,(IRow) affectRow.clone());
			tblMain.getCell(uncleRowIdx+i, COL_LEVEL).setValue(new Integer(newLevel));
			tblMain.getRow(uncleRowIdx + i).setTreeLevel(affectRow.getTreeLevel()-1);
		}
//		删除受影响的行（从下往上删）
		for(int i=affectRowIdxs.size()-1;i>=0;i--){
			int delRowIdx = Integer.parseInt(affectRowIdxs.get(i).toString());
			if(delRowIdx > 0){
				tblMain.removeRow(delRowIdx);
			}
		}
	}
	
//	降级:直接修改level,注意判断主项不能降到专项的下面
//	降级的两个判断：1.主项不能降到专项下面；2.任务的前置任务不能成为上级
	private void _degradeAffectedRows(ArrayList affectRowIdxs) throws EASBizException, BOSException{
//		直接修改被降行的level，注意哪些情况不能降级：是本级的第一个
		int firstRowIdx = Integer.parseInt(affectRowIdxs.get(0).toString());
		int firstLevel = Integer.parseInt(tblMain.getCell(firstRowIdx, COL_LEVEL).getValue().toString());
		int parentRowIdx = firstRowIdx - 1;
		int parentLevel = Integer.parseInt(tblMain.getCell(parentRowIdx, COL_LEVEL).getValue().toString());
		if(parentLevel < firstLevel) return;
		for(int i=0;i<affectRowIdxs.size();i++){
			int affectIdx = Integer.parseInt(affectRowIdxs.get(i).toString());
			IRow parentRow = tblMain.getRow(affectIdx - 1);
			IRow affectRow = tblMain.getRow(affectIdx);
			if(parentRow.getCell(COL_TASKTYPE_ID).getValue()==null || parentRow.getCell(COL_TASKTYPE_ID).getValue()==null){
				FDCMsgBox.showWarning("请先设置上级及本级任务级别！");
				SysUtil.abort();
			}
			String parentTaskType = parentRow.getCell(COL_TASKTYPE_ID).getValue().toString();
			Object o = affectRow.getCell(COL_TASKTYPE_ID).getValue();
			String affectTaskType = "";
			if(o!=null){
				 affectTaskType = o.toString();
			}
			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(affectTaskType) 
					&& TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(parentTaskType)){
				FDCMsgBox.showWarning("主项不能降为专项的下级！");
				SysUtil.abort();
			}
			if(FDCScheduleTaskFactory.getRemoteInstance().existPreOrDep(
					parentRow.getCell(COL_ID).getValue().toString(), parentRow.getCell(COL_ID).getValue().toString())){
				FDCMsgBox.showWarning("不能具有前后置关系！");
				SysUtil.abort();
			}
			int newLevel = Integer.parseInt(affectRow.getCell(COL_LEVEL).getValue().toString()) + 1;
			
			// 降级的时候不允许上级工期小于下级工期  by cassiel 2010-06-25
			int desEstimateDays = ((Integer)tblMain.getCell(affectIdx,"estimateDays").getValue()).intValue();
			int selfEstimateDays = ((Integer)tblMain.getCell(affectIdx-1,"estimateDays").getValue()).intValue();
			if (selfEstimateDays < desEstimateDays) {
				MsgBox.showInfo("存在部分下级WBS预估工期大于上级WBS预估工期的数据，不能进行此操作！");
				SysUtil.abort();
			}
			
			affectRow.getCell(COL_LEVEL).setValue(new Integer(newLevel));
			affectRow.setTreeLevel(affectRow.getTreeLevel() + 1);
		}
	}

	private void _moveAffectedRows(ArrayList affectRowIdxs,int desRowIdx,String type){
		for(int i=0;i<affectRowIdxs.size();i++){
			int affectRowIdx = Integer.parseInt(affectRowIdxs.get(i).toString());
			IRow affectRow = tblMain.getRow(affectRowIdx);
			if(type != null && type.equals(TYPE_UP)){
				tblMain.addRow(desRowIdx + i, affectRow);
				tblMain.removeRow(affectRowIdx +1);
			}else if(type != null &&type.equals(TYPE_DOWN)){
				tblMain.addRow(desRowIdx + i, affectRow);
				tblMain.removeRow(affectRowIdx -i);
			}
		}
	}
	
//	上移	
	public void actionUp_actionPerformed(ActionEvent e) throws Exception {
		checkTaskAudit();
		List selectedWBSIds = getSelectedWBSIds();
		verifySelected(selectedWBSIds);
		for(int i = 0; i < selectedWBSIds.size(); ++i){
			String wbsId = (String) selectedWBSIds.get(i);
			FDCWBSTreeNode node = wbsTree.getNode(wbsId);
			List brothers = wbsTree.getRoot();
			if(node.getParent() != null){
				brothers = node.getParent().getChildren();
			}
			if(node.equals(brothers.get(0))){
				FDCMsgBox.showWarning("所选节点【"+node.getEntity().getLongNumber().replace('!', '.')+"】已经是第一个节点，不能上移！");
				return;
			}
		}

		for(int i=0;i<selectedWBSIds.size();i++){
			wbsTree.moveUp((String) selectedWBSIds.get(i));
		}
		wbsTree.reCalculateCode();
		reLoadTable();
		setSelectBlocks(selectedWBSIds);
		isChanged = true;
	}
	private void setSelectBlocks(List selectIds){
		tblMain.getSelectManager().removeAll();
		for(int i=1;i<tblMain.getRowCount();i++){
			if(selectIds.contains(tblMain.getCell(i, COL_ID).getValue().toString())){
				tblMain.getSelectManager().add(i, 0,i,35);
			}
		}
	}
	private void setSelectBlocks(Set selectIds) {
		tblMain.getSelectManager().removeAll();
		for(int i=1;i<tblMain.getRowCount();i++){
			if(selectIds.contains(tblMain.getCell(i, COL_ID).getValue().toString())){
				tblMain.getSelectManager().add(i, 0,i,35);
			}
		}
	}
//	下移
	public void actionDown_actionPerformed(ActionEvent e) throws Exception {
		checkTaskAudit();
		List selectedWBSIds = getSelectedWBSIds();
		verifySelected(selectedWBSIds);
		for(int i = 0; i < selectedWBSIds.size(); ++i){
			String wbsId = (String) selectedWBSIds.get(i);
			FDCWBSTreeNode node = wbsTree.getNode(wbsId);
			List brothers = wbsTree.getRoot();
			if(node.getParent() != null){
				brothers = node.getParent().getChildren();
			}
			if(node.equals(brothers.get(brothers.size()-1))){
				FDCMsgBox.showWarning("所选节点【"+node.getEntity().getLongNumber().replace('!', '.')+"】已经是最后一个节点，不能下移！");
				return;
			}
		}
		for(int i=selectedWBSIds.size()-1;i>=0;i--){
			wbsTree.moveDown((String) selectedWBSIds.get(i));
		}
		wbsTree.reCalculateCode();
		reLoadTable();
		setSelectBlocks(selectedWBSIds);
		isChanged = true;
	}
//	升级
	public void actionForward_actionPerformed(ActionEvent e) throws Exception {
		checkTaskAudit();
		List selectedWBSIds = getSelectedWBSIds();
		verifySelected(selectedWBSIds);
		for(int i = 0; i < selectedWBSIds.size(); ++i){
			String wbsId = (String) selectedWBSIds.get(i);
			FDCWBSTreeNode node = wbsTree.getNode(wbsId);
			String longNumber = node.getEntity().getLongNumber().replace('!', '.');
			if(node.getParent() == null){
				FDCMsgBox.showWarning("所选节点【"+longNumber+"】已经是顶级节点，不能升级！");
				SysUtil.abort();
			}else{
				IRow row = getRowByWBSId(wbsId);
				String parentWBSId = node.getParent().getEntity().getId().toString();
				Object parentTaskType = getTaskTypeByWBSId(parentWBSId, null);
				Object taskType = getTaskTypeByWBSId(wbsId, row);
				if(taskType == null || parentTaskType == null){
					FDCMsgBox.showWarning("请先设置节点【"+longNumber+"】及上级节点任务级别！");
					SysUtil.abort();
				}
				if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(taskType.toString()) 
						&& TaskTypeInfo.TASKTYPE_MAINTASK.equals(parentWBSId.toString())){
					FDCMsgBox.showWarning("所选节点【"+longNumber+"】已经是专项任务，其上级为主项任务，不能再升级！");
					SysUtil.abort();
				}	
				FDCWBSTreeNode willParentNode = node.getParent().getParent();
				if(willParentNode!=null){
					IRow willParentRow = getRowByWBSId(willParentNode.getEntity().getId().toString());
					int willParentEstimate = Integer.parseInt(willParentRow.getCell(COL_ESTIMATE_DAYS).getValue().toString());
					int selfEstimate = Integer.parseInt(row.getCell(COL_ESTIMATE_DAYS).getValue().toString());
					if(selfEstimate>willParentEstimate){
						MsgBox.showInfo("存在部分下级WBS预估工期大于上级WBS预估工期的数据，不能进行此操作！");
						SysUtil.abort();
					}
				}
			}
		}
		for(int i=selectedWBSIds.size()-1;i>=0;i--){
			wbsTree.upgrade((String)selectedWBSIds.get(i));
		}
		wbsTree.reCalculateCode();
		reLoadTable();
		setSelectBlocks(selectedWBSIds);
		isChanged = true;
	}
//	降级
	public void actionBackward_actionPerformed(ActionEvent e) throws Exception {
		checkTaskAudit();
		List selectedWBSIds = getSelectedWBSIds();
		verifySelected(selectedWBSIds);
		for(int i = 0; i < selectedWBSIds.size(); ++i){
			String wbsId = (String) selectedWBSIds.get(i);
			FDCWBSTreeNode node = wbsTree.getNode(wbsId);
			String longNumber = node.getEntity().getLongNumber().replace('!', '.');
			FDCWBSTreeNode willParentNode = node.getLeftBrother();
			if(willParentNode == null){
				FDCMsgBox.showWarning("所选节点【"+longNumber+"】已经是第一个节点，不能降级！");
				SysUtil.abort();
			}else{
				IRow row = getRowByWBSId(wbsId);
				String willParentWBSId = willParentNode.getEntity().getId().toString();
				IRow willParentRow = getRowByWBSId(willParentWBSId);
				Object willParentTaskType = getTaskTypeByWBSId(willParentWBSId, willParentRow);
				Object taskType = getTaskTypeByWBSId(wbsId, row);
				if(willParentTaskType == null || taskType == null){
					FDCMsgBox.showWarning("请先设置节点【"+longNumber+"】或上级节点任务级别！");
					SysUtil.abort();
				}
				if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(taskType.toString()) 
						&& TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(willParentTaskType.toString())){
					FDCMsgBox.showWarning("主项 节点【"+longNumber+"】不能降为专项的下级！");
					SysUtil.abort();
				}
				int willParentEstimate = Integer.parseInt(willParentRow.getCell(COL_ESTIMATE_DAYS).getValue().toString());
				int selfEstimate = Integer.parseInt(row.getCell(COL_ESTIMATE_DAYS).getValue().toString());
				if (selfEstimate > willParentEstimate) {
					MsgBox.showInfo("存在部分下级WBS预估工期大于上级WBS预估工期的数据，不能进行此操作！");
					SysUtil.abort();
				}
				if(FDCScheduleTaskFactory.getRemoteInstance().existPreOrDep(willParentWBSId, wbsId)){
					FDCMsgBox.showWarning("不能具有前后置关系！");
					SysUtil.abort();
				}
			}
			
		}		
		for(int i=0;i<selectedWBSIds.size();i++){
			wbsTree.degrade((String)selectedWBSIds.get(i));
		}
		wbsTree.reCalculateCode();
		reLoadTable();
		setSelectBlocks(selectedWBSIds);
		isChanged = true;
	}
	public boolean isChanged(){
		return isChanged;
	}
	public void setChanged(boolean isChanged){
		this.isChanged = isChanged;
	}
//	编码重算
	public void actionReCalcuNumber_actionPerformed(ActionEvent e)	throws Exception {
		checkTaskAudit();
		int res = FDCMsgBox.showConfirm2("保存更改将同步进度计划！");
		if(res == FDCMsgBox.CANCEL) return;
		FDCWBSFactory.getRemoteInstance().saveOrderWBS(wbsTree, false);
		isChanged = false;
//		WBSAdjustManager adjustManager = new WBSAdjustManager();
//		for(int i=1;i<tblMain.getRowCount();i++){
//			IRow row = tblMain.getRow(i);
//			IRow parentRow = tblMain.getRow(getParentRowIdx(i));
//			IRow nextRow = null;
//			int sibRowIdx = getSibRowIdx(i,TYPE_UP);
//			IRow sibRow = sibRowIdx == 0? null:tblMain.getRow(sibRowIdx);
//			if(i<tblMain.getRowCount()-1){
//				nextRow = tblMain.getRow(i+1);
//			}
//			FDCWBSInfo info = loadWBSfromRow(parentRow,row,nextRow);
//			FDCWBSInfo sibInfo = loadWBSfromRow(parentRow, sibRow, tblMain.getRow(+1));
//			adjustManager.addItem(info,sibInfo);
////			if(sibInfo != null){
////				System.out.println(info.getLongNumber()+":::::sib:::::"+sibInfo.getLongNumber());
////			}else{
////				System.out.println(info.getLongNumber()+":::::sib:::::null");
////			}
//		}
//		FDCWBSFactory.getRemoteInstance().reCalculateNumber(adjustManager);
		ui.refresh(null);
	}
	private FDCWBSInfo loadWBSfromRow(IRow parentRow,IRow row,IRow nextRow){
		if(row == null || row.getRowIndex() == 0) return null;
		int rowLevel = Integer.parseInt(row.getCell(COL_LEVEL).getValue().toString());
		int nextRowLevel = (nextRow==null)?-1:Integer.parseInt(nextRow.getCell(COL_LEVEL).getValue().toString());
		FDCWBSInfo info = new FDCWBSInfo();
		FDCWBSInfo parentInfo = null;
		if(row.getCell(COL_ID).getValue() != null){
			info.setId(BOSUuid.read(row.getCell(COL_ID).getValue().toString()));
		}
		if(parentRow.getCell(COL_ID).getValue() != null){
			parentInfo = new FDCWBSInfo();
			parentInfo.setId(BOSUuid.read(parentRow.getCell(COL_ID).getValue().toString()));
		}
		info.setLongNumber(row.getCell(COL_LONG_NUMBER).getValue().toString());
		info.setName(row.getCell(COL_NAME).getValue().toString());
		if(rowLevel < nextRowLevel) info.setIsLeaf(false);
		else info.setIsLeaf(true);
		info.setLevel(rowLevel);
		info.setParent(parentInfo);
		return info;
	}
	private void checkTaskAudit() throws EASBizException, BOSException{
		IObjectValue info = this.ui.getSelectInfo();
		if (info != null && info instanceof CurProjectInfo){
			String projectId = ((CurProjectInfo)info).getId().toString();
			Set unOprateState = new HashSet();
			unOprateState.add("'"+ScheduleStateEnum.AUDITTED_VALUE+"'");
			unOprateState.add("'"+ScheduleStateEnum.AUDITTING_VALUE+"'");
			unOprateState.add("'"+ScheduleStateEnum.CLOSED_VALUE+"'");
			unOprateState.add("'"+ScheduleStateEnum.EXETING_VALUE+"'");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id",projectId,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state",unOprateState,CompareType.INCLUDE));
//			filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.EXETING));
//			filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.CLOSED));
//			filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.AUDITTING));
//			filter.setMaskString("(#0 AND (#1 OR #2 OR #3))");
			if(FDCScheduleFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showError("已生成的进度计划状态不适合此操作！");
				SysUtil.abort();
			}
		}else{
			FDCMsgBox.showError("请先选择工程项目！");
			SysUtil.abort();
		}
	}	
	
	private void initTree(){
		int rowCount = tblMain.getRowCount();
		Map id2WBS = new HashMap();
		FDCWBSCollection col = new FDCWBSCollection();
		if(rowCount > 0){
			for(int i = 0; i < rowCount; ++i){
				IRow row = tblMain.getRow(i);
				if(!FDCHelper.ZERO.equals(row.getCell(COL_LEVEL).getValue())){
					FDCWBSInfo info = new FDCWBSInfo();	
					String wbsId = row.getCell(COL_ID).getValue().toString();
					id2WBS.put(wbsId, info);
					col.add(info);
					info.setId(BOSUuid.read(wbsId));
					info.setLongNumber(row.getCell(COL_LONG_NUMBER).getValue().toString());
					info.setName(row.getCell(COL_NAME).getValue().toString());
					Object parentWBSId = row.getCell(COL_PARENT_ID).getValue();
					if(parentWBSId != null){
						info.setParent((FDCWBSInfo) id2WBS.get(parentWBSId.toString()));
					}
					if(row.getCell(COL_TASKTYPE_ID).getValue() != null){
						TaskTypeInfo taskType = new TaskTypeInfo();
						taskType.setId(BOSUuid.read(row.getCell(COL_TASKTYPE_ID).getValue().toString()));
						taskType.setName(row.getCell(COL_TASKTYPE_NAME).getValue().toString());
						info.setTaskType(taskType);
					}
					info.put(COL_TASKTYPE_NAME, row.getCell(COL_TASKTYPE_NAME).getValue());
					info.put(COL_TASKTYPE_ID, row.getCell(COL_TASKTYPE_ID).getValue());
					info.put(COL_ADMIN_DEPT_NAME, row.getCell(COL_ADMIN_DEPT_NAME).getValue());
					info.put(COL_RESP_DEPT_NAME, row.getCell(COL_RESP_DEPT_NAME).getValue());
					info.put(COL_ADMIN_PERSON_NAME, row.getCell(COL_ADMIN_PERSON_NAME).getValue());
					info.put(COL_ESTIMATE_DAYS, row.getCell(COL_ESTIMATE_DAYS).getValue());
					info.put(COL_ISENABLED, row.getCell(COL_ISENABLED).getValue());		
					info.put(COL_PROJECT_ID, row.getCell(COL_PROJECT_ID).getValue());					
				}			
			}	
			wbsTree = FDCWBSTree.getTreeFromCollection(col, tblMain.getRow(0).getCell("curProject.id").getValue().toString());
			wbsTree.testPrint();
		}
	}
	private void reLoadTable(){
		int rowCount = tblMain.getRowCount();
		for(int i = rowCount; i > 0; --i){
			tblMain.removeRow(i);
		}
		tblMain.getTreeColumn().setDepth(getDepth());
		if(this.wbsTree != null){
			List nodes = wbsTree.getRoot();
			fillTable(nodes);
		}
		tblMain.repaint();
	}
	private int getDepth(){
		Collection collection = wbsTree.getId2WBSMap().values();
		int depth =1;
		for(Iterator iter = collection.iterator(); iter.hasNext();){
			FDCWBSInfo wbs = (FDCWBSInfo) iter.next();
			if(depth < wbs.getLevel()){
				depth = wbs.getLevel();
			}
		}
		return depth;
	}
	private void fillTable(List nodes){
		for(int i = 0; i < nodes.size(); ++i){
			FDCWBSTreeNode node = (FDCWBSTreeNode)nodes.get(i);
			addRow(node);
			fillTable(node.getChildren());
		}
	}
	private void addRow(FDCWBSTreeNode node){
		IRow row = tblMain.addRow();
		FDCWBSInfo info = node.getEntity();
		row.setTreeLevel(info.getLevel()-1);
		row.getCell(COL_ID).setValue(info.getId());
		row.getCell(COL_LONG_NUMBER).setValue(info.getLongNumber().replace('!', '.'));
		row.getCell(COL_LEVEL).setValue(new Integer(info.getLevel()));
		row.getCell(COL_NAME).setValue(info.getName());
		if(info.getParent()!=null){
			row.getCell(COL_PARENT_ID).setValue(info.getParent().getId());
		}
		row.getCell(COL_TASKTYPE_NAME).setValue(info.get(COL_TASKTYPE_NAME));
		row.getCell(COL_TASKTYPE_ID).setValue(info.get(COL_TASKTYPE_ID));
		row.getCell(COL_ADMIN_DEPT_NAME).setValue(info.get(COL_ADMIN_DEPT_NAME));
		row.getCell(COL_RESP_DEPT_NAME).setValue(info.get(COL_RESP_DEPT_NAME));
		row.getCell(COL_ADMIN_PERSON_NAME).setValue(info.get(COL_ADMIN_PERSON_NAME));
		row.getCell(COL_ESTIMATE_DAYS).setValue(info.get(COL_ESTIMATE_DAYS));
		row.getCell(COL_ISENABLED).setValue(info.get(COL_ISENABLED));
		row.getCell(COL_PROJECT_ID).setValue(info.get(COL_PROJECT_ID));
	}
	private String getWBSIdByRowIndex(int rowIndex){
		return tblMain.getCell(rowIndex, COL_ID).getValue().toString();		
	}
	private void verifySelected(List wbsIds){
		if(wbsIds.isEmpty()){
			FDCMsgBox.showWarning("未选择项目WBS！");
			SysUtil.abort();
		}
		if(!isSameParent(wbsIds)){
			FDCMsgBox.showWarning("只允许移动同级节点！");
			return;
		}
	}
	private List getSelectedWBSIds(){
		List selectedIndex = new ArrayList();
		ArrayList selectedBlocks = tblMain.getSelectManager().getBlocks();
		for(int i=0;i<selectedBlocks.size();i++){
			KDTBlock block = (KDTBlock) selectedBlocks.get(i);
			for(int j=block.getTop();j<=block.getBottom();j++){
				selectedIndex.add(new Integer(j));
			}
		}
		Collections.sort(selectedIndex);
		List ret = new ArrayList();
		for(int i = 0; i < selectedIndex.size(); ++i){
			int index = ((Integer)selectedIndex.get(i)).intValue();
			ret.add(getWBSIdByRowIndex(index));
		}
		return ret;
	}
	private boolean isSameParent(List wbsIds){
		Set parentNodes = new HashSet();
		for(int i = 0; i < wbsIds.size(); ++i){
			String wbsId = (String) wbsIds.get(i);
			FDCWBSTreeNode node = wbsTree.getNode(wbsId);
			parentNodes.add(node.getParent());
		}
		return parentNodes.size() == 1;
	}
	private IRow getRowByWBSId(String wbsId){
		int rowCount = tblMain.getRowCount();
		if(rowCount > 0){
			for(int i = 1; i < rowCount; ++i){
				IRow row = tblMain.getRow(i);
				if(wbsId.equals(row.getCell(COL_ID).getValue().toString())){
					return row;
				}
			}
		}
		return null;
	}
	private Object getTaskTypeByWBSId(String wbsId, IRow destRow){
		IRow row = destRow;
		if(row == null){
			row = getRowByWBSId(wbsId);
		}
		return row.getCell(COL_TASKTYPE_ID).getValue();	
		
	}
	
}
