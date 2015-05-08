/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.IUIManager;
import com.kingdee.eas.base.uiframe.client.SystemEntry;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.base.uiframe.client.UIWinManager;
import com.kingdee.eas.base.uiframe.client.ui.IMainUIObject;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleBaseUI;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FDCWBSListUI extends AbstractFDCWBSListUI {
	private static final String COL_PROJECT_ID = "curProject.id";
	private static final String COL_PARENT_ID = "parent.id";
	private static final String COL_LONG_NUMBER = "longNumber";
	private static final String COL_ID = "id";
	private static final String COL_LEVEL = "level";
	private static final String SYS_DEFAULT_NUM = "000";
	private static final Logger logger = CoreUIObject.getLogger(FDCWBSListUI.class);
	private FDCWBSListHelper helper = null;
	public FDCWBSListUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		actionCancelCancel.setEnabled(true);
		actionCancel.setEnabled(true);
	}

	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		//saveConfirm(e.getOldLeadSelectionPath());
		if(getSelectInfo() != null){
			//当点击树上的+-折叠图标时，会触发两次valueChanged事件，第一次时获取选择节点为null（这个比较操蛋）。因此做次判断by yangzhiqiao
			tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
			refresh(null);
		}
		/*
		 * IObjectValue info=getSelectInfo(); if(info != null && info instanceof
		 * CurProjectInfo){ IRow row = tblMain.addRow(0);
		 * row.getCell("name").setValue(((CurProjectInfo)info).getName());
		 * row.getCell("level").setValue(FDCHelper.ZERO);
		 * row.getCell("longNumber").setValue(SYS_DEFAULT_NUM);
		 * row.getCell("isEnabled").setValue(Boolean.TRUE); }
		 */
	}

	protected void refresh(ActionEvent e) throws Exception {
		IObjectValue info = getSelectInfo();
		CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
		this.mainQuery.setFilter(getMainFilter());
		this.mainQuery.getSorter().clear();
		this.mainQuery.getSorter().add(new SorterItemInfo(COL_LONG_NUMBER));
		execQuery();
		if (info != null && info instanceof CurProjectInfo)		
			
			if (tblMain.getRowCount() == 0 || (tblMain.getRowCount() > 0 && !SYS_DEFAULT_NUM.equals(tblMain.getCell(0, COL_LONG_NUMBER).getValue().toString()))) {				
				IRow row = tblMain.addRow(0);
				row.getCell("name").setValue(((CurProjectInfo) info).getName());
				row.getCell(COL_LEVEL).setValue(FDCHelper.ZERO);
				row.getCell(COL_LONG_NUMBER).setValue(SYS_DEFAULT_NUM);
				row.getCell("isEnabled").setValue(Boolean.TRUE);
			}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				getMainTable().getSelectManager().removeAll();
			}
		});
	}

	protected void execQuery() {
		super.execQuery();
		IObjectValue info = getSelectInfo();
		if (info instanceof CurProjectInfo) {
			// get max level
			CurProjectInfo prj = (CurProjectInfo) info;
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select max(flevel) as flevel from T_SCH_FDCWBS where fcurprojectid=?");
			builder.addParam(prj.getId().toString());
			try {
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.next()) {
					tblMain.getTreeColumn().setDepth(rowSet.getInt("flevel"));
				}
			} catch (Exception e) {
				this.handUIException(e);
			}
		}

	}

	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		int start = e.getFirstRow();
		int end = e.getLastRow();
		setTableRowLevel(start, end);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof String) {
					return o.toString().replace('!', '.');
				} else
					return null;
			}
		});
		tblMain.getColumn(COL_LONG_NUMBER).setRenderer(render);
	}

	private void setTableRowLevel(int start, int end) {
		for (int i = start; i <= end; i++) {
			IRow row = tblMain.getRow(i);
			int level = Integer.parseInt(row.getCell(COL_LEVEL).getValue().toString());
			row.setTreeLevel(level - 1);
		}
	}

	protected FDCTreeBaseDataInfo getBaseDataInfo() {
		return new FDCWBSInfo();
	}

	protected String getEditUIName() {
		return FDCWBSEditUI.class.getName();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return FDCWBSFactory.getRemoteInstance();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo) {
			BOSUuid prjID = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
			uiContext.put("projectID", prjID.toString());
			// 只在新增的时候才传ParentNode rowIndex的索引是从0开始的 by sxhong 2009-09-23
			// 15:52:34
			if (tblMain.getSelectManager().getActiveRowIndex() == 0) {
				uiContext.put(UIContext.PARENTNODE, null);
				return;
			}
			if ((tblMain.getSelectManager() != null) && (tblMain.getSelectManager().getActiveRowIndex() > 0) && act != null && act.equals(actionAddNew)) {
				String parentID = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), COL_ID).getValue().toString();
				FDCWBSInfo parent = new FDCWBSInfo();
				parent.setId(BOSUuid.read(parentID));
				uiContext.put(UIContext.PARENTNODE, parent);
			} else {
				uiContext.put(UIContext.PARENTNODE, null);
			}
		} else {
			uiContext.put(UIContext.PARENTNODE, null);
		}

	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}

	protected String getRootName() {
		return "项目WBS";
	}

	public void onLoad() throws Exception {
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("当前组织不是成本中心，不能进入！");
			SysUtil.abort();
		}

		super.onLoad();
		mainQuery.getFilter();
		if(helper == null){
			helper = new FDCWBSListHelper(this);
		}
	}

	private void buildProjectTree() throws Exception {
//		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
//		treeMain.setShowsRootHandles(true);
//		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try{
			//用财务组织构造树保证下级组织能够看到其他的项目
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}
		treeMain.setShowsRootHandles(true);
	}

	protected void initTree() throws Exception {
		buildProjectTree();
	}

	public FilterInfo getMainFilter() {
		FilterInfo filter = new FilterInfo();
		IObjectValue info = getSelectInfo();
		if (info instanceof OrgStructureInfo) {
			OrgStructureInfo orgUnit = (OrgStructureInfo) info;
			// String innerSQL =
			// "select prj.fid,prj.fname_l2 from t_fdc_curproject prj " +
			// "	inner join t_org_baseunit org on prj.ffullorgunit=org.fid " +
			//"	where org.flongnumber like '"+orgUnit.getUnit().getLongNumber()+
			// "%'" ;

			filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", "11", CompareType.EQUALS));
		} else if (info instanceof CurProjectInfo) {
			// 选的是项目
			CurProjectInfo prj = (CurProjectInfo) info;
			filter.getFilterItems().add(new FilterItemInfo(COL_PROJECT_ID, prj.getId().toString(), CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("isUnVisible",Boolean.FALSE,CompareType.EQUALS));
		return filter;
	}

	protected IObjectValue getSelectInfo() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof IObjectValue) {
			IObjectValue info = (IObjectValue) node.getUserObject();
			return info;
		}
		return null;
	}

	protected FDCBaseDataClientCtrler getCtrler() {
		return null;
	}
	private void checkAddEnable(CurProjectInfo project, String msg) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.AUDITTING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.EXETING_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3) ");
		if (FDCScheduleFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this, msg);
			SysUtil.abort();
		}
	}
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception{
		this.saveConfirm(null);
		super.actionRefresh_actionPerformed(e);
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		CurProjectInfo project = null;
		if ((getProjSelectedTreeNode().getUserObject() == null) || (!(getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo))) {
			FDCMsgBox.showWarning("请先选择工程项目！");
			SysUtil.abort();
		}
		project = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
		if (!project.isIsLeaf()) {
			FDCMsgBox.showWarning("只有明细工程项目才能新增项目WBS！");
			SysUtil.abort();
		}
		checkAddEnable(project, "已经存在审批或执行的计划，不能新增WBS\n如果计划已审批，请反审批相应计划后，可新增WBS；\n如果计划已执行，并需调整计划，请通过计划调整申请【甘特图模式】进行！");
		super.actionAddNew_actionPerformed(e);
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		menuBiz.setVisible(true);
		menuBiz.setEnabled(true);
		actionImportFromTemplate.setEnabled(true);
		actionImportData.setVisible(false);
		actionBatChangeTaskPro.setEnabled(true);
		actionBatChangeRespDept.setEnabled(true);
		btnBatChangeTaskPro.setEnabled(true);
		btnBatChangeRespDept.setEnabled(true);
		actionOutputToTemplate.setEnabled(true);
		btnOutputToTemplate.setEnabled(true);
		// try {
		// String filePath = System.getProperty("EAS_HOME");
		//			
		// URL imgURL = new URL(filePath +
		// "lib/common/trd/ganttproject_resources");
		// Icon forwardIcon = new ImageIcon(imgURL);
		// Icon backwardIcon = new ImageIcon(imgURL);
		// KDWorkButton btnForward = new KDWorkButton("取消缩进",forwardIcon);
		// KDWorkButton btnBackward = new KDWorkButton("缩进",backwardIcon);
		// } catch (MalformedURLException e) {
		// this.handleException(e);
		// }

		KDWorkButton btnForward = new KDWorkButton("升级", SCHClientHelper.ICON_FORWARD);
		KDWorkButton btnBackward = new KDWorkButton("降级", SCHClientHelper.ICON_BACKWARD);
		KDWorkButton btnUp = new KDWorkButton("上移", SCHClientHelper.ICON_UP);
		KDWorkButton btnDown = new KDWorkButton("下移", SCHClientHelper.ICON_DOWN);
		KDWorkButton btnReCalNumber = new KDWorkButton("保存更改");
		btnForward.addActionListener(actionForward);
		btnBackward.addActionListener(actionBackward);
		btnUp.addActionListener(actionUp);
		btnDown.addActionListener(actionDown);
		btnReCalNumber.addActionListener(actionReCalcuNumber);
		conTable.addButton(btnForward);
		conTable.addButton(btnBackward);
		conTable.addButton(btnUp);
		conTable.addButton(btnDown);
		conTable.addButton(btnReCalNumber);
	}

	/**
	 */
	public void actionImportFromTemplate_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo prjInfo = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
			checkAddEnable(prjInfo, "已经存在审批或执行的计划，不能新增WBS\n如果计划已审批，请反审批相应计划后，可新增WBS；\n如果计划已执行，并需调整计划，请通过计划调整申请【甘特图模式】进行！");
			uiContext.put("prj4Import", prjInfo);
		} else {
			FDCMsgBox.showWarning("请先选中工程项目！");
			SysUtil.abort();
		}
		if (tblMain.getSelectManager().getActiveRowIndex() > 0 && tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), COL_ID).getValue() != null) {
			FDCWBSInfo parentInfo = new FDCWBSInfo();
			parentInfo.setId(BOSUuid.read(tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), COL_ID).getValue().toString()));
			parentInfo.setLevel(Integer.parseInt(tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), COL_LEVEL).getValue().toString()));
			parentInfo.setLongNumber(tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "number").getValue().toString());
			//添加所选择的预估工期   add by warship at 2010/07/08
			Integer estimateDay =(Integer)tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "estimateDays").getValue();
			parentInfo.setEstimateDays(estimateDay.intValue());
			uiContext.put("parent4Import", parentInfo);
		}
		IUIWindow WBSTemplateUI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(WBSTemplateReferListUI.class.getName(), uiContext, null, OprtState.VIEW);
		WBSTemplateUI.show();
		// FDCWBSFactory.getRemoteInstance().importWBSTemplate(param);
	}

	protected boolean isSystemDefaultData(int activeRowIndex) {
		return false;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		checkIsSysRow(activeRowIndex);
		if (tblMain.getCell(activeRowIndex, "isEnabled").getValue() != null && Boolean.TRUE.equals(Boolean.valueOf(tblMain.getCell(activeRowIndex, "isEnabled").getValue().toString()))) {
			FDCMsgBox.showWarning("该WBS已启用，不允许修改！");
			SysUtil.abort();
		}
		if(!checkCanOperateWBS()){
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	public void fillTable(KDTable table) {

	}

	protected String[] getNotOrderColumns() {
		return new String[] { COL_LONG_NUMBER, "name", COL_LEVEL, "adminDept.name", "adminPerson.name", "estimateDays", "isLocked", "isEnabled", "taskType.name" };
	}

	// 批量修改任务级别
	public void actionBatChangeTaskPro_actionPerformed(ActionEvent e) throws Exception {
		ArrayList selectedBlocks = tblMain.getSelectManager().getBlocks();
		if (selectedBlocks.size() < 1) {
			FDCMsgBox.showError("未选择项目WBS！");
			SysUtil.abort();
		}
		if (selectedBlocks.size() < 1)
			return;
		Set WBSIDs = new HashSet();
		for (int i = 0; i < selectedBlocks.size(); i++) {
			KDTSelectBlock block = (KDTSelectBlock) selectedBlocks.get(i);
			for (int j = block.getTop(); j <= block.getBottom(); j++) {
				checkIsSysRow(j);
				if(tblMain.getCell(j, "adminDept.name").getValue() != null){
					MsgBox.showWarning("有部分项目WBS的计划部门已经存在，不适合进行此操作！");
					SysUtil.abort();
				}
				if (tblMain.getCell(j, "isEnabled").getValue() != null && Boolean.TRUE.equals(Boolean.valueOf(tblMain.getCell(j, "isEnabled").getValue().toString())))
					continue;
				WBSIDs.add(tblMain.getCell(j, COL_ID).getValue().toString());
			}
		}
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		// uicontext.put("changeType", "Task");
		uicontext.put("wbsIDs", WBSIDs);
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.schedule.client.FDCWBSBatchChangeProUI", uicontext, null, OprtState.EDIT);
		uiWindow.show();
		refresh(e);
	}

	// 批量修改责任部门
	public void actionBatChangeRespDept_actionPerformed(ActionEvent e) throws Exception {
		ArrayList selectedBlocks = tblMain.getSelectManager().getBlocks();
		if (selectedBlocks.size() < 1) {
			FDCMsgBox.showError("未选择项目WBS！");
			SysUtil.abort();
		}
		Set WBSIDs = new HashSet();
		for (int i = 0; i < selectedBlocks.size(); i++) {
			KDTSelectBlock block = (KDTSelectBlock) selectedBlocks.get(i);
			for (int j = block.getTop(); j <= block.getBottom(); j++) {
				checkIsSysRow(j);
				if(tblMain.getCell(j, "taskType.name").getValue() == null ||"".equals(tblMain.getCell(j, "taskType.name").getValue().toString())){
					MsgBox.showWarning("有部分项目WBS的任务级别为空，不适合进行此操作，请先设置任务级别！");
					SysUtil.abort();
				}
				if (tblMain.getCell(j, "isEnabled").getValue() != null && Boolean.TRUE.equals(Boolean.valueOf(tblMain.getCell(j, "isEnabled").getValue().toString())))
					continue;
				WBSIDs.add(tblMain.getCell(j, COL_ID).getValue().toString());
			}
		}
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		Boolean isMainOrSpecial = Boolean.TRUE;//True:主项 False:专项
		Boolean isWbsOrSchedule = Boolean.TRUE;//True:Wbs批量修改责任部门 False:计划编制（主项和专项）批量修改责任部门  add by warship at 2010/08/05
		//只要选择的WBS中有专项任务类型的就给出提示，并且不允许修改计划编制部门 by cassiel_peng 2010-05-07
		if(WBSIDs!=null&&WBSIDs.size()>=1){
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendParam("select ftasktypeid from t_sch_fdcwbs where fid  ",WBSIDs.toArray());
			IRowSet rowSet = builder.executeQuery();
			while (rowSet != null && rowSet.next()) {
				String ftasktypeid = rowSet.getString("ftasktypeid");
				if (TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(ftasktypeid)) {// 如果是主项
					isMainOrSpecial = Boolean.FALSE;
					break;
				}
			}
		}
		if(!isMainOrSpecial.booleanValue()){
			MsgBox.showWarning("有部分项目WBS的任务级别为专项任务，不能修改计划部门！");
		}
		UIContext uicontext = new UIContext(this);
		// uicontext.put("changeType", "Dept");
		uicontext.put("wbsIDs", WBSIDs);
		uicontext.put("isMainOrSpecial", isMainOrSpecial);
		uicontext.put("isWbsOrSchedule", isWbsOrSchedule);//add by warship at 2010/08/05
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.schedule.client.FDCWBSBatchChangeRespDeptUI", uicontext, null, OprtState.EDIT);
		uiWindow.show();
		refresh(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		for (int i = 0; i < blocks.size(); i++) {
			KDTSelectBlock block = (KDTSelectBlock) blocks.get(i);
			for (int j = block.getTop(); j <= block.getBottom(); j++) {
				checkIsSysRow(j);
				if (tblMain.getCell(j, "isEnabled").getValue() != null && Boolean.TRUE.equals(Boolean.valueOf(tblMain.getCell(j, "isEnabled").getValue().toString()))) {
					FDCMsgBox.showError("已启用项目wbs不能删除！");
					SysUtil.abort();
				}
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo(COL_PARENT_ID, tblMain.getCell(j, COL_ID).getValue().toString()));
				if (FDCWBSFactory.getRemoteInstance().exists(filter)) {
					FDCMsgBox.showError("非明细节点不能删除！");
					SysUtil.abort();
				}
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// Map selectedIds = new HashMap();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		if(!checkCanOperateWBS()){
			return;
		}
		if(blocks.size()<1) return;
		Set longNumbers = new HashSet();
		checkIsSysRow(tblMain.getSelectManager().getActiveRowIndex());
		String prjID = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), COL_PROJECT_ID).getValue().toString();
		for (int i = 0; i < blocks.size(); i++) {
			KDTBlock block = (KDTBlock) blocks.get(i);
			for (int j = block.getTop(); j <= block.getBottom(); j++) {
				if (tblMain.getCell(j, COL_ID).getValue() != null && tblMain.getCell(j, COL_LONG_NUMBER).getValue() != null
						&& !SYS_DEFAULT_NUM.equals(tblMain.getCell(j, COL_LONG_NUMBER).getValue().toString())) {
					//selectedIds.put(tblMain.getCell(j,"id").getValue().toString
					// (
					// ),tblMain.getCell(j,"longNumber").getValue().toString());
					longNumbers.add(tblMain.getCell(j, COL_LONG_NUMBER).getValue().toString());
				}
			}
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select count(wbs.fid) count from  T_SCH_FDCWBS wbs inner join T_SCH_FDCScheduleTask task  on wbs.fid=task.fwbsID"
				+ "	inner join T_SCH_FDCSchedule schedule on task.FScheduleID=schedule.fid" +
				// "	where schedule.FState=? and wbs.FCurProjectID=? and (");
				"	where wbs.FCurProjectID=? and (");
		// builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.addParam(prjID);
		Object[] nodeLongNums = longNumbers.toArray();
		for (int i = 0; i < nodeLongNums.length; i++) {
			if (i != nodeLongNums.length - 1)
				builder.appendSql("	wbs.FLongNumber like '" + nodeLongNums[i] + "%'	or");
			else
				builder.appendSql("	wbs.FLongNumber like '" + nodeLongNums[i] + "%')");
		}
		builder.appendSql("and schedule.FState in ('" + ScheduleStateEnum.AUDITTED_VALUE + "','" + ScheduleStateEnum.EXETING_VALUE + "','" + ScheduleStateEnum.CLOSED_VALUE + "')");

		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getInt("count") > 0) {
				FDCMsgBox.showError("对应该WBS的计划已被引用，不能禁用！");
				SysUtil.abort();
			}
		}
		
		
		builder.clear();
		builder.appendSql("update t_sch_fdcwbs set fisEnabled=0 where FCurProjectID=? and (");
		builder.addParam(prjID);
		for (int i = 0; i < nodeLongNums.length; i++) {
			if (i != nodeLongNums.length - 1)
				builder.appendSql("	FLongNumber like '" + nodeLongNums[i] + "%'	or	");
			else
				builder.appendSql("	FLongNumber like '" + nodeLongNums[i] + "%'	");
		}
		builder.appendSql(")");
		builder.executeUpdate();
		setMessageText("禁用成功！");
		showMessage();
		tblMain.refresh();
		refresh(null);
	}
	public boolean checkCanOperateWBS(){
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		ScheduleGanttProject project = null;
		IUIObject uiObject = null;
		IUIManager uiManager = null;
		IMainUIObject mainUI = null;

		try {
			mainUI = UIFactoryHelper.getMainUIObject(this.getUIContext());
		} catch (UIException e) {
		}
		uiManager = mainUI.getUiManager();
	
		for(Iterator it = uiManager.getWinUiObjects().iterator();it.hasNext();){
			uiObject = (IUIObject)it.next();
			if(uiObject instanceof ScheduleBaseUI){
				project = ((ScheduleBaseUI)uiObject).getScheduleGanttProject();
				break;
			}
		}
		
		if(project!=null){
			/**
			 * 计划界面被打开，还需要判断界面打开的状态是否是编辑状态，如果是，此时不能禁用WBS
			 */
			if("ADDNEW".equals(project.getOprtState())||
					"EDIT".equals(project.getOprtState())){
				/***
				 * 计划是编辑或者是新增状态
				 */
				Map kdTaskMap = project.getKDTaskMaps();
				Map editingWbsMap = new HashMap();
				Set kdTaskIdSet = kdTaskMap==null?new HashSet():kdTaskMap.keySet();
				for(Iterator it = kdTaskIdSet.iterator();it.hasNext();){
					KDTask taskInfo =  (KDTask)kdTaskMap.get(it.next());
					if(((FDCScheduleTaskInfo)(taskInfo.getScheduleTaskInfo())).getWbs()!=null)
						editingWbsMap.put(((FDCScheduleTaskInfo)(taskInfo.getScheduleTaskInfo())).getWbs().getId().toString(), Boolean.TRUE);
				}
				for(int i=0;i<blocks.size();i++){
					KDTBlock block = (KDTBlock) blocks.get(i);
					for(int j=block.getTop();j<=block.getBottom();j++){
						if(tblMain.getCell(j,COL_ID).getValue() != null
								&& tblMain.getCell(j,COL_LONG_NUMBER).getValue() != null
								&& !SYS_DEFAULT_NUM.equals(tblMain.getCell(j,COL_LONG_NUMBER).getValue().toString())){
							String selectid = tblMain.getCell(j,COL_ID).getValue().toString();
							if(editingWbsMap.containsKey(selectid)){
								FDCMsgBox.showWarning(this, "计划编制正在进行中，不允许进行此操作。");
								return false;
							}
							
						}
					}
				}
			}
		}
		return true;
	}
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		Map selectedIds = new HashMap();
		Set _selectedIds = new HashSet();
		Set _selectedLongNum = new HashSet();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		
		if (blocks.size() < 1)
			return;
		checkIsSysRow(tblMain.getSelectManager().getActiveRowIndex());
		String prjID = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), COL_PROJECT_ID).getValue().toString();
		Set longNums = new HashSet();
		Set parentLongNums = new HashSet();
		Set selfAndChildLgNums = new HashSet();
		for (int i = 0; i < blocks.size(); i++) {
			KDTBlock block = (KDTBlock) blocks.get(i);
			for (int j = block.getTop(); j <= block.getBottom(); j++) {
				if (tblMain.getCell(j, COL_ID).getValue() != null && !SYS_DEFAULT_NUM.equals(tblMain.getCell(j, COL_LONG_NUMBER).getValue().toString())) {
					selectedIds.put(tblMain.getCell(j, COL_ID).getValue().toString(), tblMain.getCell(j, COL_LONG_NUMBER).getValue().toString());
					_selectedIds.add(tblMain.getCell(j, COL_ID).getValue().toString());
					_selectedLongNum.add(tblMain.getCell(j, COL_LONG_NUMBER).getValue().toString());
				}
			}
		}
		Object[] keys = selectedIds.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			String longNum = selectedIds.get(keys[i]).toString();
			String longNum2 = selectedIds.get(keys[i]).toString();
			longNums.add(longNum);
			int tempParentNums = longNum.split("!").length;
			for (int j = 0; j < tempParentNums && longNum.lastIndexOf("!") > 0; j++) {
				longNum = longNum.substring(0, longNum.lastIndexOf("!"));
				parentLongNums.add(longNum);
			}
			for (int j = 0; j < tblMain.getRowCount(); j++) {
				if (tblMain.getCell(j, COL_LONG_NUMBER).getValue() != null && tblMain.getCell(j, COL_LONG_NUMBER).getValue().toString().startsWith(longNum2)) {
					selfAndChildLgNums.add(tblMain.getCell(j, COL_LONG_NUMBER).getValue().toString());
				}
			}
		}
		longNums.addAll(parentLongNums);
		longNums.addAll(selfAndChildLgNums);
		Object[] nodeLongNums = longNums.toArray();
		Map paramMap = new HashMap();
		paramMap.put("parentLongNums", parentLongNums);
		paramMap.put("selfAndChildLgNums", selfAndChildLgNums);
		paramMap.put("nodeLongNums", nodeLongNums);
		paramMap.put("selectedIds", _selectedIds);
		paramMap.put("selectedLongNum", _selectedLongNum);
		paramMap.put("prjID", prjID);
		
		FDCWBSFactory.getRemoteInstance().handleCancleCancle(paramMap);
		if(!checkCanOperateWBS()){
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("update t_sch_fdcwbs set fisEnabled=1 where FCurProjectID=? and  ");
		builder.addParam(prjID);
		builder.appendParam("FlongNumber", longNums.toArray());
		builder.executeUpdate();
		setMessageText("启用成功！");
		showMessage();
		tblMain.refresh();
		refresh(null);
		// loadFields();
	}

	private void checkIsSysRow(int rowIdx) {
		if (rowIdx < 0)
			return;
		if (SYS_DEFAULT_NUM.equals(tblMain.getCell(rowIdx, COL_LONG_NUMBER).getValue().toString())) {
			FDCMsgBox.showError("系统预设数据，不允许操作");
			SysUtil.abort();
		}
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0)
			return;
		checkIsSysRow(actRowIdx);
		super.actionView_actionPerformed(e);
	}

	public void actionOutputToTemplate_actionPerformed(ActionEvent e) throws Exception {
		Object treeNode = treeMain.getLastSelectedPathComponent();
		Object userObj = null;
		if (treeNode != null && treeNode instanceof DefaultKingdeeTreeNode) {
			userObj = ((DefaultKingdeeTreeNode) treeNode).getUserObject();
		} else if (treeNode != null && treeNode instanceof KDTreeNode) {
			userObj = ((KDTreeNode) treeNode).getUserObject();
		}
		if (userObj != null && userObj instanceof CurProjectInfo) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("curProjectId", ((CurProjectInfo) userObj).getId().toString());
			uiContext.put("isFromFDCWBS", Boolean.TRUE);
			uiContext.put("entrys", FDCWBSFactory.getRemoteInstance().getTemplateFromFDCWBS(((CurProjectInfo) userObj).getId().toString()));
			IUIWindow wbsTemplateUI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(WBSTemplateEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			wbsTemplateUI.show();
		} else {
			FDCMsgBox.showError("请选择明细工程项目后，使用本功能！");
			SysUtil.abort();
		}
	}
	
	private void checkListBtnEnable() throws EASBizException, BOSException{
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo prjInfo = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
			checkAddEnable(prjInfo, "已生成的进度计划状态不适合此操作");
		}
	}
	public void actionForward_actionPerformed(ActionEvent e) throws Exception {
		checkListBtnEnable();
		helper.actionForward_actionPerformed(e);
	}
	public void actionDown_actionPerformed(ActionEvent e) throws Exception {
		checkListBtnEnable();
		helper.actionDown_actionPerformed(e);
	}
	public void actionUp_actionPerformed(ActionEvent e) throws Exception {
		checkListBtnEnable();
		helper.actionUp_actionPerformed(e);
	}
	public void actionBackward_actionPerformed(ActionEvent e) throws Exception {
		checkListBtnEnable();
		helper.actionBackward_actionPerformed(e);
	}
	public void actionReCalcuNumber_actionPerformed(ActionEvent e)	throws Exception {
		checkListBtnEnable();
		helper.actionReCalcuNumber_actionPerformed(e);
	}
	public boolean destroyWindow(){
		saveConfirm(null);
		return super.destroyWindow();
	}
	public void saveConfirm(TreePath oldPath){
		if(helper!=null && helper.isChanged()){
			int res = FDCMsgBox.showConfirm3("数据已修改， 是否保存并退出？");
			if(res == FDCMsgBox.YES){
				try {
					helper.setChanged(false);
					helper.actionReCalcuNumber_actionPerformed(null);
				} catch (Exception e) {
					this.handleException(e);
				}
			}else if(res == FDCMsgBox.NO){
				helper.setChanged(false);
			}else{
				if(oldPath != null){
					treeMain.setSelectionPath(oldPath);
				}
				SysUtil.abort();
			}
		}
	}
}