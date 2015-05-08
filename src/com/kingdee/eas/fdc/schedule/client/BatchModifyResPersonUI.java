/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class BatchModifyResPersonUI extends AbstractBatchModifyResPersonUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BatchModifyResPersonUI.class);

	private FDCScheduleInfo scheduleInfo = null;
	private FDCScheduleInfo newScheduleInfo = null;

	/**
	 * output class constructor
	 */
	public BatchModifyResPersonUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/*						
	  */
	public void onLoad() throws Exception {
		super.onLoad();
		initTableData();
		kDRightTable.removeRows(false);
		kDRightTable.getStyleAttributes().setLocked(true);
		// 基本信息行政组织F7责任人控件分层显示
		FDCClientUtils.setRespDeptF7(prmtDutyDep, this);
		FDCClientUtils.setPersonF7(prmtDutyPerson, this, null);
		FDCClientUtils.setPersonF7(prmtQualityAppraisePerson, this, null);
		FDCClientUtils.setPersonF7(prmtScheduleAppraisePerson, this, null);
		cboPanelPoint.setSelected(true);
	}

	protected void initWorkButton() {
		this.btnAllLeftMove.setEnabled(true);
		this.btnAllRightMove.setEnabled(true);
		this.btnLeftMove.setEnabled(true);
		this.btnRightMove.setEnabled(true);
		this.btnClose.setEnabled(true);
		this.btnmMakeSure.setEnabled(true);
		this.btnSaveAndClose.setEnabled(true);
		super.initWorkButton();
	}

	private Set idSet = new HashSet();
	/*所有的任务列表 */
	private List scheduleList = new ArrayList();
	/*责任人和责任部门为空的任务列表 */
	List taskList = new ArrayList();

	/**
	 * @description 获取当前计划的的有任务并填充到左边待选表格
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void initTableData() throws Exception {

		if (getUIContext().get("Owner") != null
				&& getUIContext().get("Owner") instanceof FDCScheduleBaseEditUI) {
			FDCScheduleBaseEditUI baseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			scheduleInfo = baseEditUI.scheduleInfo;

			if (baseEditUI.getScheduleGanttProject() == null) {
				FDCMsgBox.showWarning("该工程项目下没有任务！");
				SysUtil.abort();
			}
			List tasks = baseEditUI.getScheduleGanttProject().getSortKDTask();
			FDCScheduleTaskInfo taskInfo = null;
			for (int i = 0; i < tasks.size(); i++) {
				taskInfo = (FDCScheduleTaskInfo) ((KDTask) tasks.get(i)).getScheduleTaskInfo();
				scheduleInfo.getTaskEntrys().add(taskInfo);
				if (taskInfo.getAdminPerson() == null 
						|| taskInfo.getAdminDept() == null 
						|| taskInfo.getPlanEvaluatePerson() == null 
						|| taskInfo.getQualityEvaluatePerson() == null) {
					taskList.add(taskInfo);
				}
				scheduleList.add(taskInfo);
			}
		}
		fillTableData(taskList);
	}

	/**
	 * @description 为左边的表格填充数据
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void fillTableData(List list) {
		kDLeftTable.checkParsed();
		kDLeftTable.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		if (list.size() > 0) {
			IRow row  = null;
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) list.get(i);
				row = kDLeftTable.addRow();
				row.setUserObject(taskInfo);
				row.getCell("id").setValue(taskInfo.getId());
				row.getCell("taskName").setValue(taskInfo.getName());
				if (taskInfo.getAdminPerson() == null) {
					row.getCell("resPerson").setValue(null);
				} else {
					row.getCell("resPerson").setValue(taskInfo.getAdminPerson());
				}
				if (taskInfo.getAdminDept() == null) {
					row.getCell("resDep").setValue(null);
				} else {
					row.getCell("resDep").setValue(taskInfo.getAdminDept());
				}

				row.getCell("level").setValue(new Integer(taskInfo.getLevel()));
				row.getCell("isLeaf").setValue(Boolean.valueOf(taskInfo.isIsLeaf()));
				row.getCell("longNumber").setValue(taskInfo.getLongNumber());
				row.getCell("planEvaluatePerson").setValue(taskInfo.getPlanEvaluatePerson());
				row.getCell("qualityEvaluatePerson").setValue(taskInfo.getQualityEvaluatePerson());
			}
			initTree();
		}
	}

	/**
	 * @description 为左边的表格构建树结构
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void initTree() {
		int count = this.kDLeftTable.getRowCount();
		kDLeftTable.getStyleAttributes().setLocked(true);
		IRow row = null;
		String value = "";
		for (int i = 0; i < count; i++) {
			row = this.kDLeftTable.getRow(i);
			if (row.getCell("taskName").getValue() instanceof String) {
				value = (String) row.getCell("taskName").getValue();
			}
			// 取得树级次和是否明细节点
			boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();
			CellTreeNode treeNode = new CellTreeNode() {
				public void doTreeClick(KDTable table, ICell currCell) {
					/* TODO 自动生成方法存根 */
					super.doTreeClick(table, currCell);
					hideAlreadyInRightTable();
				}
			};
			treeNode.setValue(value);// 显示的值
			treeNode.setTreeLevel(level + 1);// 级次，
			treeNode.setHasChildren(!isLeaf);
			treeNode.isCollapse();
			row.getCell("taskName").setValue(treeNode);// 设置前面构建的单元格树到表
			row.getCell("taskName").getStyleAttributes().setLocked(false);
			
		}


	}
	
	
	public void hideAlreadyInRightTable() {
		List rightTask = getRightData();
		for (int i = 0; i < kDLeftTable.getRowCount(); i++) {
			IRow row = kDLeftTable.getRow(i);
			String id = row.getCell("id").getValue().toString();
			if (rightTask.contains(id)) {
				row.getStyleAttributes().setHided(true);
			}
		}

	}

	/**
	 * @description 修改后得到左表的数据
	 * @author 车忠伟
	 * @createDate 2011-9-23
	 * @version EAS7.0
	 * @see
	 */
	private Map getAllTask() {
		Map taskMap = new HashMap();
		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String entryInfoId = row.getCell("id").getValue().toString();
				taskInfo.setId(BOSUuid.read(entryInfoId));
				if (row.getCell("resPerson").getValue() != null) {
					taskInfo.setAdminPerson((PersonInfo) row.getCell("resPerson").getValue());
				}
				if (row.getCell("resDep").getValue() != null) {
					taskInfo.setAdminDept((FullOrgUnitInfo) row.getCell("resDep").getValue());
				}

				taskMap.put(entryInfoId, taskInfo);

			}

		}
		return taskMap;

	}

	/**
	 * @description 当数据全部显示或者部分数据隐藏时，对应改变后数据发生变化，隐藏或者全部显示的数据也要相应的变化
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void updateTableData(Map taskMap) {

		// 循环表格的数据
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String taskId = row.getCell("id").getValue().toString().trim();
				if (taskMap.size() > 0 && !taskMap.isEmpty()) {// 集合大于0
					if (taskMap.get(taskId) != null) {// 根据ID判断Map
						// 是否存在对象,存在则修改对应的数据
						FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) taskMap
								.get(taskId);
						if (scheduleTaskInfo.getAdminPerson() != null) {
							row.getCell("resPerson").setValue(
									scheduleTaskInfo.getAdminPerson());
						}
						if (scheduleTaskInfo.getAdminDept() != null) {
							row.getCell("resDep").setValue(
									scheduleTaskInfo.getAdminDept());
						}

					}

				}

			}
		}

	}

	/**
	 * @description 根据显示所有任务选择框的操作来显示数据
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	protected void cboTask_mouseClicked(MouseEvent e) throws Exception {
		// Map taskMap=getAllTask();
		if (cboTask.isSelected()) {
			kDLeftTable.removeRows(false);
			fillTableData(scheduleList);

		} else {
			kDLeftTable.removeRows(false);
			fillTableData(taskList);
		}
		// updateTableData(taskMap);

		// if(cboTask.isSelected()){
		// // kDLeftTable.removeRows(false);
		// // resetTable();
		// int count =kDLeftTable.getRowCount();
		// if(count>0){
		// for(int i=0;i<count;i++){
		// IRow row =kDLeftTable.getRow(i);
		// String entryId=row.getCell("id").getValue().toString().trim();
		// if(idSet.size()>0){
		// for(Iterator it=idSet.iterator();it.hasNext();){
		// String id=(String) it.next();
		// if(id.equals(entryId)){
		// row.getStyleAttributes().setHided(false);
		// }
		// }
		// }
		// }
		// }
		// }else{
		// int count =kDLeftTable.getRowCount();
		// if(count>0){
		// for(int i=0;i<count;i++){
		// IRow row =kDLeftTable.getRow(i);
		// String entryId=row.getCell("id").getValue().toString().trim();
		// if(idSet.size()>0){
		// for(Iterator it=idSet.iterator();it.hasNext();){
		// String id=(String) it.next();
		// if(id.equals(entryId)){
		// row.getStyleAttributes().setHided(true);
		// }
		// }
		// }
		// }
		// }
		//			
		// }
	}

	/**
	 * 得到右表所有数据的ID
	 * 
	 * @return
	 */
	public List getRightData() {
		List list = new ArrayList();
		int count = kDRightTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDRightTable.getRow(i);
				String entryId = row.getCell("id").getValue().toString().trim();
				list.add(entryId);
			}
		}
		return list;
	}

	/**
	 * @description 将右表的数据全部移到左表
	 * @author 车忠伟
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	public void actionAllLeftMove_actionPerformed(ActionEvent e)
			throws Exception {

		if (cboTask.isSelected()) {
			kDRightTable.removeRows(false);
			kDLeftTable.removeRows(false);
			fillTableData(scheduleList);
		} else {
			kDRightTable.removeRows(false);
			kDLeftTable.removeRows(false);
			fillTableData(taskList);
		}
		//		
		// kDLeftTable.removeRows(false);
		// fullTableData(scheduleList);
		// }else{
		// kDLeftTable.removeRows(false);
		// fullTableData(taskList);
	}

	/**
	 * @description 将左表的数据全部移动到右表,不就是全部任务吗？
	 * @author 车忠伟
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	public void actionAllRightMove_actionPerformed(ActionEvent e)
			throws Exception {
		List list = new ArrayList();
		int count = kDLeftTable.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = kDLeftTable.getRow(i);
			String entryInfoId = row.getCell("id").getValue().toString();
			String taskName = row.getCell("taskName").getValue().toString();
			row.getStyleAttributes().setHided(true);
			FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
			taskInfo.setId(BOSUuid.read(entryInfoId));
			taskInfo.setName(taskName);
			list.add(taskInfo);
		}
		//TODO fixerror 是不是只需要从editData中读取？？？
		fullRightTable(list);
	}

	/**
	 * @description 填充右表数据
	 * @author 车忠伟
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	public void fullRightTable(List list) {
		if (list.size() > 0 && list != null) {
			kDRightTable.removeRows(false);
			for (int i = 0; i < list.size(); i++) {
				FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list
						.get(i);
				IRow row = kDRightTable.addRow();
				row.getCell("id").setValue(taskinfo.getId());
				row.getCell("taskName").setValue(taskinfo.getName().trim());
				row.setUserObject(taskinfo);
			}
		}
	}

	/**
	 * @description 将右表的数据移动到左表
	 * @author 车忠伟
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */

	public void actionLeftMove_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		int rows[] = KDTableUtil.getSelectedRows(kDRightTable);

		if (rows.length <= 0) {
			FDCMsgBox.showWarning("您没有选择要移动的行或者已选任务列表没有任务");
		} else {
			for (int i = rows.length; i > 0; i--) {
				String entryInfoId = kDRightTable.getRow(rows[i - 1]).getCell("id").getValue().toString().trim();
				String taskName = kDRightTable.getRow(rows[i - 1]).getCell("taskName").getValue().toString().trim();
				kDRightTable.removeRow(rows[i - 1]);
				FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
				taskInfo.setId(BOSUuid.read(entryInfoId));
				taskInfo.setName(taskName);

				list.add(taskInfo);
			}
			if (list.size() > 0) {
				int count = this.kDLeftTable.getRowCount();
				for (int i = 0; i < count; i++) {
					IRow row = kDLeftTable.getRow(i);
					String entryID = row.getCell("id").getValue().toString().trim();
					for (Iterator it = list.iterator(); it.hasNext();) {
						FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) it.next();
						if (scheduleTaskInfo.getId().toString().trim().equals(// 如果ID相同则显示出来
								entryID)) {
							row.getStyleAttributes().setHided(false);
						}
					}
				}

			}
		}
	}

	/**
	 * @description 将左表的数据移动到右表
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionRightMove_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		int rows[] = KDTableUtil.getSelectedRows(kDLeftTable);
		if (rows.length <= 0) {
			FDCMsgBox.showWarning("请选你要移动的行");
		} else {
			getAllSelectedTask(list, rows);
			fillRightTalbe(list);
		}

	}
    /**
     * 
     * 描述：获取左表中选取的所有的任务
     * @param list
     * @param rows
     * @throws Exception
     * 创建人：yuanjun_lan
     * 创建时间：2012-2-27
     */
	private void getAllSelectedTask(List list, int[] rows) throws Exception {
		FDCScheduleTaskInfo taskInfo = null;
		for (int i = 0; i < rows.length; i++) {
//				String entryInfoId = kDLeftTable.getRow(rows[i]).getCell("id").getValue().toString();
//				String taskName = kDLeftTable.getRow(rows[i]).getCell("taskName").getValue().toString();
//				String longNumber = "";
//				if (kDLeftTable.getRow(rows[i]).getCell("longNumber").getValue() != null) {
//					longNumber = kDLeftTable.getRow(rows[i]).getCell("longNumber").getValue().toString();
//				}
			kDLeftTable.getRow(rows[i]).getStyleAttributes().setHided(true);// 隐藏
			taskInfo = (FDCScheduleTaskInfo) kDLeftTable.getRow(rows[i]).getUserObject();
//				taskInfo.setId(BOSUuid.read(entryInfoId));
//				taskInfo.setLongNumber(longNumber);
//				taskInfo.setName(taskName);
			if (cboPanelPoint.isSelected()) {// 选中包含子叶节点
				if (!taskInfo.isIsLeaf()) {// 非子叶节点
					List entryList = getChildenNode(taskInfo);
					if (entryList.size() > 0) {
						for (Iterator entry = entryList.iterator(); entry.hasNext();) {
							FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) entry.next();
							for (int j = 0; j < kDLeftTable.getRowCount(); j++) {
								IRow row = kDLeftTable.getRow(j);
								if (scheduleTaskInfo.getId().toString().trim().equals(row.getCell("id").getValue().toString().trim())) {// ID相同隐藏
									row.getStyleAttributes().setHided(true);
									if(!list.contains(scheduleTaskInfo)){
										list.add(scheduleTaskInfo);
									}
								
								}
							}
						}
					}
				}else{
				  list.add(taskInfo);
				}
			} else {
				list.add(taskInfo);
			}
		}
	}
    /**
     * 
     * 描述：根据左表数据填充右表
     * @param list
     * 创建人：yuanjun_lan
     * 创建时间：2012-2-27
     */
	private void fillRightTalbe(List list) {
		if (list.size() > 0) {
			int rightCount = kDRightTable.getRowCount();
			if (rightCount > 0) {
				Map idMap = new HashMap();
				for (int j = 0; j < rightCount; j++) {
					IRow rowNew = kDRightTable.getRow(j);
					String entryId = rowNew.getCell("id").getValue().toString().trim();
					idMap.put(entryId, entryId);
				}
				if (idMap.size() > 0 && !idMap.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list.get(i);
						if (!idMap.containsKey(taskinfo.getId().toString())) {
							IRow row = kDRightTable.addRow();
							row.getCell("id").setValue(taskinfo.getId());
							row.getCell("taskName").setValue(taskinfo.getName().trim());
							row.setUserObject(list.get(i));
						}
					}
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					FDCScheduleTaskInfo taskinfo = (FDCScheduleTaskInfo) list.get(i);
					IRow row = kDRightTable.addRow();
					row.getCell("id").setValue(taskinfo.getId());
					row.getCell("taskName").setValue(taskinfo.getName().trim());
					row.setUserObject(taskinfo);
				}
			}

		}
	}

	/**
	 * @description 根据实体对象得到给对象的所有子节点
	 * 
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public List getChildenNode(FDCScheduleTaskInfo taskInfo) throws Exception {
		List list = new ArrayList();
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			FDCScheduleTaskInfo entryInfo = null;
			IRow row = null;
			for (int i = 0; i < count; i++) {
				row = kDLeftTable.getRow(i);
				entryInfo = (FDCScheduleTaskInfo) row.getUserObject();
				if (entryInfo.getLongNumber().startsWith(taskInfo.getLongNumber().trim())) {
					list.add(entryInfo);
				}
			}
		}

		return list;
	}
	
	public void getChildrenNode(Set childrenSet,FDCScheduleTaskInfo taskInfo){
		int count = kDLeftTable.getRowCount();
		childrenSet.add(taskInfo);
		if(count>0){
			FDCScheduleTaskInfo entryInfo = null;
			IRow row = null;
			for(int i=0;i<count;i++){
				row = kDLeftTable.getRow(i);
				entryInfo = (FDCScheduleTaskInfo) row.getUserObject();
				if(entryInfo.getId().equals(taskInfo.getId())){
					continue;
				}
				if(entryInfo.isIsLeaf()){
					if(entryInfo.getLongNumber().startsWith(taskInfo.getLongNumber())){
						childrenSet.add(entryInfo);
					}
				}else{
					if(childrenSet.contains(entryInfo)||entryInfo.getId().equals(taskInfo.getId())){
						continue;
					}
					childrenSet.add(entryInfo);
					getChildrenNode(childrenSet,entryInfo);
				}
			}
		}
		
	}
	

	/**
	 * @description 关闭操作
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}

	/**
	 * @description 保存与关闭操作
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionSaveAndClose_actionPerformed(ActionEvent e)
			throws Exception {
		FDCScheduleBaseEditUI editUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
		ScheduleStateEnum state = editUI.editData.getState();
		boolean mustUpdateBySql = true;
	    if(!state.equals(ScheduleStateEnum.AUDITTED)
	    		&& !state.equals(ScheduleStateEnum.AUDITTING)){
//	    	editUI.actionSave_actionPerformed(e);
	    	mustUpdateBySql = false;
	    }
		 
		if (prmtDutyPerson.getValue() == null 
				&& prmtDutyDep.getValue() == null
				&& prmtScheduleAppraisePerson.getValue() == null
				&& prmtQualityAppraisePerson.getValue() == null) {
			FDCMsgBox.showWarning("请选择需要修改的信息");
			SysUtil.abort();
		}
		PersonInfo dutyPerson = (PersonInfo) prmtDutyPerson.getValue();
		AdminOrgUnitInfo dutyDep = (AdminOrgUnitInfo) prmtDutyDep.getValue();
		PersonInfo scheduleAppraisePerson = (PersonInfo) prmtScheduleAppraisePerson.getValue();
		PersonInfo qualityAppraisePerson = (PersonInfo) prmtQualityAppraisePerson.getValue();
		int count = kDRightTable.getRowCount();
		if (count <= 0) {
			FDCMsgBox.showWarning("已选任务列表不能为空");
			SysUtil.abort();
		}
		
		FDCScheduleTaskInfo taskInfo = null;
		CoreBaseCollection collection = new CoreBaseCollection();
		for (int i = 0; i < count; i++) {
			IRow row = kDRightTable.getRow(i);
//			String entryPk = row.getCell("id").getValue().toString().trim();
//			FDCScheduleTaskInfo taskInfo = FDCScheduleTaskFactory
//					.getRemoteInstance().getFDCScheduleTaskInfo(
//							new ObjectStringPK(entryPk));
			taskInfo = (FDCScheduleTaskInfo) row.getUserObject();
			if (dutyPerson != null) {
				taskInfo.setAdminPerson(dutyPerson);
			}
			if (dutyDep != null) {
				taskInfo.setAdminDept(dutyDep.castToFullOrgUnitInfo());
			}
			if (scheduleAppraisePerson != null) {
				taskInfo.setPlanEvaluatePerson(scheduleAppraisePerson);
			}
			if (qualityAppraisePerson != null) {
				taskInfo.setQualityEvaluatePerson(qualityAppraisePerson);
			}
			collection.add(taskInfo);

		}
		
		if(mustUpdateBySql){
			updateInfoWhenAuditted();
		}
		
		newScheduleInfo = getSaveAndCloseTableData();
		// FDCScheduleTaskCollection cols = new FDCScheduleTaskCollection();
		// cols = ScheduleTaskHelper.getRelationReverseTasks(cols,
		// newScheduleInfo.getTaskEntrys());
		// newScheduleInfo.getTaskEntrys().clear();
		// newScheduleInfo.getTaskEntrys().addCollection(cols);
		editUI.loadData2Gantt(newScheduleInfo);
		destroyWindow();
	}

	/**
	 * @description 保存
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void actionMakeSure_actionPerformed(ActionEvent e) throws Exception {
		
		FDCScheduleBaseEditUI editUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
		ScheduleStateEnum state = editUI.editData.getState();
		boolean mustUpdateBySql = true;
	    if(!state.equals(ScheduleStateEnum.AUDITTED)
	    		&& !state.equals(ScheduleStateEnum.AUDITTING)){
	    	editUI.actionSave_actionPerformed(e);
	    	mustUpdateBySql = false;
	    }
		// 责任不，责任部门等不为空
		if (prmtDutyPerson.getValue() == null && prmtDutyDep.getValue() == null
				&& prmtScheduleAppraisePerson.getValue() == null
				&& prmtQualityAppraisePerson.getValue() == null) {
			FDCMsgBox.showWarning("请选择需要修改的信息");
			SysUtil.abort();
		}
		
		PersonInfo dutyPerson = (PersonInfo) prmtDutyPerson.getValue();
		AdminOrgUnitInfo dutyDep = (AdminOrgUnitInfo) prmtDutyDep.getValue();
		PersonInfo scheduleAppraisePerson = (PersonInfo) prmtScheduleAppraisePerson
				.getValue();
		PersonInfo qualityAppraisePerson = (PersonInfo) prmtQualityAppraisePerson
				.getValue();
		int count = kDRightTable.getRowCount();
		if (count <= 0) {
			FDCMsgBox.showWarning("已选任务列表不能为空");
			SysUtil.abort();
		}
		
		
		List entryList = new ArrayList();
		CoreBaseCollection collection = new CoreBaseCollection();
		for (int i = 0; i < count; i++) {
			IRow row = kDRightTable.getRow(i);
			String entryPk = row.getCell("id").getValue().toString().trim();
			// 修改数据
			FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
			taskInfo.setId(BOSUuid.read(entryPk));
			if (dutyPerson != null) {
				taskInfo.setAdminPerson(dutyPerson);
			}
			if (dutyDep != null) {
				taskInfo.setAdminDept(dutyDep.castToFullOrgUnitInfo());
			}
			if (scheduleAppraisePerson != null) {
				taskInfo.setPlanEvaluatePerson(scheduleAppraisePerson);
			}
			if (qualityAppraisePerson != null) {
				taskInfo.setQualityEvaluatePerson(qualityAppraisePerson);
			}
			collection.add(taskInfo);
			entryList.add(taskInfo);

		}
		if(mustUpdateBySql){
			updateInfoWhenAuditted();
		}
		saveUpdateData(entryList);
		
		
		// if(collection.size()>0){//批量修改
		// FDCScheduleTaskFactory.getRemoteInstance().update(collection);
		// }
		FDCScheduleInfo fdcScheduleInfo = getDataFormLeftTable();
		editUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
		editUI.loadData2Gantt(fdcScheduleInfo);
	}

	/**
	 * @description 保持操作时清空右表的数据，并将左表的数据恢复
	 * @author 车忠伟
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */
	public void saveUpdateData(List list) {
		if (list.size() > 0 && list != null) {
			kDRightTable.removeRows(false);
			int count = kDLeftTable.getRowCount();
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String entryID = row.getCell("id").getValue().toString().trim();
				for (int j = 0; j < list.size(); j++) {
					FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) list.get(j);
					if (entryID.equals(scheduleTaskInfo.getId().toString().trim())) {
						row.getStyleAttributes().setHided(false);
						if (scheduleTaskInfo.getAdminPerson() != null) {
							row.getCell("resPerson").setValue(scheduleTaskInfo.getAdminPerson());
						}
						if (scheduleTaskInfo.getAdminDept() != null) {
							row.getCell("resDep").setValue(scheduleTaskInfo.getAdminDept());
						}
						if (scheduleTaskInfo.getPlanEvaluatePerson() != null) {
							row.getCell("planEvaluatePerson").setValue(scheduleTaskInfo.getPlanEvaluatePerson());
						}
						if (scheduleTaskInfo.getQualityEvaluatePerson() != null) {
							row.getCell("qualityEvaluatePerson").setValue(scheduleTaskInfo.getQualityEvaluatePerson());
						}

					}
				}
			}

		}
	}
	
	/**
	 * 
	 * 描述：当进度计划已经被审批后，通过脚本直接更新对应任务的相关信息
	 * @param taskList
	 * 创建人：yuanjun_lan
	 * 创建时间：2012-2-27
	 * @throws BOSException 
	 */
	private void updateInfoWhenAuditted() throws BOSException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		StringBuffer sql = new StringBuffer("update t_sch_fdcscheduleTask set ");
		boolean [] isModify = new boolean[4];
		int index = 0;
		String dutyDeptId = null;
		String dutyPersonId = null;
		String appraisePerson = null;
		String schedulePerson = null;
		
		//判断那些需要更新
		if(prmtDutyDep.getValue() != null){
			isModify[IndexModify.DUTYDEP] = true;
			sql.append("  FAdminDeptID = ? , ");
			dutyDeptId = ((AdminOrgUnitInfo)prmtDutyDep.getValue()).getId().toString();
		}
		
		if(prmtDutyPerson.getValue() != null){
			isModify[IndexModify.DUTYPERSON] = true;
			sql.append(" FAdminPersonID  = ? , ");
			dutyPersonId = ((PersonInfo)prmtDutyPerson.getValue()).getId().toString();
		}
		
		if(prmtQualityAppraisePerson.getValue() != null){
			isModify[IndexModify.QUALITYPERSON] = true;
			sql.append(" FQualityEvaluatePersonID  = ? , ");
			appraisePerson = ((PersonInfo)prmtQualityAppraisePerson.getValue()).getId().toString();
		}
		
		if(prmtScheduleAppraisePerson.getValue() != null){
			isModify[IndexModify.SCHEDULEPERSON] = true;
			sql.append(" FPlanEvaluatePersonID  = ? , ");
			schedulePerson = ((PersonInfo)prmtScheduleAppraisePerson.getValue()).getId().toString();
		}
		int lastChar = sql.lastIndexOf(",");
		sql.delete(lastChar,lastChar+1);
		sql.append(" where fid = ? ");
		
	    FDCScheduleTaskInfo task = null;
	    List paramList = new ArrayList(taskList.size());
	    List valueList = null;
	    int rightTableCount = kDRightTable.getRowCount();
		for(int i=0;i<rightTableCount;i++){
			
			valueList = new ArrayList();
			task = (FDCScheduleTaskInfo) kDRightTable.getRow(i).getUserObject();
			if(isModify[IndexModify.DUTYDEP]){
				valueList.add(dutyDeptId == null?task.getAdminDept().getId().toString():dutyDeptId);
			}
			
			if(isModify[IndexModify.DUTYPERSON]){
				valueList.add(dutyPersonId == null?task.getAdminPerson().getId().toString():dutyPersonId);
			}
			
			if(isModify[IndexModify.QUALITYPERSON]){
				valueList.add(appraisePerson == null?task.getQualityEvaluatePerson().getId().toString():appraisePerson);
			}
			
			if(isModify[IndexModify.SCHEDULEPERSON]){
				valueList.add(schedulePerson == null?task.getPlanEvaluatePerson().getId().toString():schedulePerson);
			}
			valueList.add(task.getId().toString());
			
			paramList.add(valueList);
		}
		
	    builder.executeBatch(sql.toString(), paramList);
		
		
	
		
	}

	private Map map;

	/**
	 * 得到所有的任务放在Map中
	 * 
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-9
	 * @throws Exception void
	 * @version EAS7.0
	 * @see
	 */
	public void getAllTaskInfo() throws Exception {
		map = new HashMap();
		if (scheduleList.size() > 0) {
			for (int i = 0; i < scheduleList.size(); i++) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) scheduleList.get(i);
				if (!map.containsKey(taskInfo.getId().toString())) {
					map.put(taskInfo.getId().toString(), taskInfo);
				}

			}
		}
	}

	/**
	 * 保存并关闭
	 * 
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-9
	 * @return
	 * @throws Exception
	 *             FDCScheduleInfo
	 * @version EAS7.0
	 * @see
	 */
	public FDCScheduleInfo getSaveAndCloseTableData() throws Exception {
		getAllTaskInfo();
		// scheduleInfo.getTaskEntrys().clear();
		int count = kDRightTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDRightTable.getRow(i);
				String entryID = row.getCell("id").getValue().toString();
				if (map.get(entryID) != null) {
					FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) map.get(entryID);
					PersonInfo resPerson = (PersonInfo) prmtDutyPerson.getValue();
					AdminOrgUnitInfo resDep = (AdminOrgUnitInfo) prmtDutyDep.getValue();
					PersonInfo planEvaluatePerson = (PersonInfo) prmtScheduleAppraisePerson.getValue();
					PersonInfo qualityEvaluatePerson = (PersonInfo) prmtQualityAppraisePerson.getValue();
					if (resPerson != null) {
						taskInfo.setAdminPerson(resPerson);
					}
					if (resDep != null) {
						taskInfo.setAdminDept(resDep.castToFullOrgUnitInfo());
					}
					if (planEvaluatePerson != null) {
						taskInfo.setPlanEvaluatePerson(planEvaluatePerson);
					}
					if (qualityEvaluatePerson != null) {
						taskInfo.setQualityEvaluatePerson(qualityEvaluatePerson);
					}

					for (int j = 0; j < scheduleInfo.getTaskEntrys().size(); j++) {
						FDCScheduleTaskInfo taskEntryInfo = scheduleInfo.getTaskEntrys().get(j);
						if (taskEntryInfo.getId().toString().equals(entryID)) {
							scheduleInfo.getTaskEntrys().removeObject(j);
							scheduleInfo.getTaskEntrys().insertObject(j - 1, taskInfo);
						}
					}
				}
			}
		}
		return scheduleInfo;
	}

	public Map setFdcschedul(List scheduleList) {
		Map taskMap = new HashMap();
		if (scheduleList.size() > 0) {
			for (int i = 0; i < scheduleList.size(); i++) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) scheduleList
						.get(i);
				if (!taskMap.containsKey(taskInfo.getId().toString())) {
					taskMap.put(taskInfo.getId().toString(), taskInfo);
				}

			}
		}
		return taskMap;
	}

	/**
	 * 确定
	 * 
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-9
	 * @return
	 * @throws Exception
	 *             FDCScheduleInfo
	 * @version EAS7.0
	 * @see
	 */
	public FDCScheduleInfo getDataFormLeftTable() throws Exception {
		getAllTaskInfo();
		int count = kDLeftTable.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				IRow row = kDLeftTable.getRow(i);
				String entryID = "";
				if (row.getCell("id").getValue() != null) {
					entryID = row.getCell("id").getValue().toString().trim();
				}
				if (map.get(entryID) != null) {
					FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) map.get(entryID);
					PersonInfo resPerson = (PersonInfo) row.getCell("resPerson").getValue();
					FullOrgUnitInfo resDep = (FullOrgUnitInfo) row.getCell("resDep").getValue();
					PersonInfo planEvaluatePerson = (PersonInfo) row.getCell("planEvaluatePerson").getValue();
					PersonInfo qualityEvaluatePerson = (PersonInfo) row.getCell("qualityEvaluatePerson").getValue();
					if (resPerson != null) {
						taskInfo.setAdminPerson(resPerson);
					}
					if (resDep != null) {
						taskInfo.setAdminDept(resDep);
					}
					if (planEvaluatePerson != null) {
						taskInfo.setPlanEvaluatePerson(planEvaluatePerson);
					}
					if (qualityEvaluatePerson != null) {
						taskInfo.setQualityEvaluatePerson(qualityEvaluatePerson);
					}
					FDCScheduleTaskCollection collection = scheduleInfo.getTaskEntrys();

					for (int j = 0; j < collection.size(); j++) {
						FDCScheduleTaskInfo taskEntryInfo = collection.get(j);
						if (taskEntryInfo.getId().toString().equals(entryID)) {
							scheduleInfo.getTaskEntrys().remove(taskEntryInfo);
							
							/* modified by zhaqoin for R140318-0254 on 2014/03/26 start */
							//scheduleInfo.getTaskEntrys().add(taskInfo);
							scheduleInfo.getTaskEntrys().insertObject(j - 1, taskInfo);
							/* modified by zhaqoin for R140318-0254 on 2014/03/26 end */
						}

					}

					// scheduleInfo.getTaskEntrys().add(taskInfo);
				}

			}
		}

		return scheduleInfo;
	}
	
   static class IndexModify{
	   /**责任人*/
	   public static final int DUTYDEP = 0;
	   /**责任部门*/
	   public static final int DUTYPERSON = 1;
	   /**责任质量评价人*/
	   public static final int QUALITYPERSON = 2;
	   /**进度评价人*/
	   public static final int SCHEDULEPERSON = 3;
   }
}