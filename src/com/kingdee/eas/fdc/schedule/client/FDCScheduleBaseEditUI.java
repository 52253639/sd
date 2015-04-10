/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.Box.Filler;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.mpxj.MPXJException;
import net.sourceforge.ganttproject.GanttTreeTable;
import net.sourceforge.ganttproject.GanttTreeTable.DisplayedColumnsList;
import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollectionMutator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.base.param.ParamCollection;
import com.kingdee.eas.base.param.ParamFactory;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.CheckNodeCollection;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeCollection;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.eas.fdc.schedule.IFDCSchedule;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchCalendar;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskCreator;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskPredecessorCreator;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.client.FindTaskUI;
import com.kingdee.eas.fdc.schedule.framework.client.IScheduleUIFacade;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.eas.fdc.schedule.framework.util.KDProjectFileReader;
import com.kingdee.eas.fdc.schedule.framework.util.KDProjectWriter;
import com.kingdee.eas.fdc.schedule.framework.util.ScheduleTaskPropertyHelper;
import com.kingdee.eas.fdc.schedule.framework.util.StopWatch;
import com.kingdee.eas.fdc.schedule.param.ScheduleParamHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.ResponseConstant;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.AbortException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public  class FDCScheduleBaseEditUI extends AbstractFDCScheduleBaseEditUI implements IScheduleUIFacade {
	/**
	 * 上一次选择导入的Project文件，以便作为再次导入的默认文件
	 */
	private static String preProjectFileName = System.getProperty("user.home");
	private static final Logger logger = CoreUIObject.getLogger(FDCScheduleBaseEditUI.class);
	private static boolean isCanSaveAddNewTask = false;
	private CoreBaseCollection wbsCoreBaseCollection;
	private ScheduleTaskPropertyCollection columnsProperty = null;
	private boolean isNeedRemeber = true;

	/**
	 * output class constructoro
	 */
	public FDCScheduleBaseEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		//跟建筑第三方包，冲突。KD出品。
		 ClassLoaderUtil.modifyClassLoader(true);
		try {
			beforeOnload();
			isCanSaveAddNewTask = ScheduleParamHelper.getSchParamByKey(null, null, ScheduleParamHelper.FDCSCH_PARAM_CANSAVEADDNEWTASK);
			super.onLoad();
			cbState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum").toArray());
			if (editData != null && editData.getProject() != null) {
				loadProjectState();
				setVersion();
			}
		
			afterOnload();
			loadCurProject();
			if (null == editData || null == editData.getProject()) {
				txtVersion.setText("");
				cbState.setSelectedItem(null);
			}
			moveBtnToGantt();
			actionHistoryVersion();
			
			addExport();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
			isNeedRemeber = false;
			abort();
		}
		this.actionBatchModifyTaskType.setVisible(false);
	}
	public void addExport(){
		KDWorkButton exportXls = new KDWorkButton();
		exportXls.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		exportXls.setText("导出任务excel");
		this.toolBar.add(exportXls, 10);
		exportXls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExportManager exportM = new ExportManager();
			    String path = null;
			    File tempFile;
			    KDTable exportTable = new KDTable();
			    initExportTable(exportTable);
			    FDCScheduleTaskCollection taskColl = editData.getTaskEntrys();
			    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
			    for(int i = 0; i < taskColl.size(); i++) {
			    	FDCScheduleTaskInfo scheTaskInfo = taskColl.get(i);
			    	int seq = scheTaskInfo.getSeq();//序号
			    	String TaskName = addSpaceToTaskName(scheTaskInfo.getName(), scheTaskInfo.getLevel());//任务名称
			    	String type=scheTaskInfo.getTaskType().getAlias();
			    	BigDecimal NatureTimes = scheTaskInfo.getEffectTimes();//工期
			    	Date PlanStart = scheTaskInfo.getPlanStart();//计划开始日期
			    	Date PlanEnd = scheTaskInfo.getPlanEnd();//计划完成日期
			    	Date ActualStartDate = scheTaskInfo.getActualStartDate();//实际开始日期
			    	Date ActualEndDate = scheTaskInfo.getActualEndDate();//实际完成日期
			    	Date CheckDate = scheTaskInfo.getCheckDate();//考核日期
			    	PersonInfo AdminPerson = scheTaskInfo.getAdminPerson();//责任人
			    	FullOrgUnitInfo AdminDept = scheTaskInfo.getAdminDept();//责任部门
			    	
			    	String planStart = (PlanStart == null ? "" : simpleFormat.format(PlanStart));
			    	String planEnd = (PlanEnd == null ? "" : simpleFormat.format(PlanEnd));
			    	String actualStartDate = (ActualStartDate == null ? "" : simpleFormat.format(ActualStartDate));
			    	String actualEndDate = (ActualEndDate == null ? "" : simpleFormat.format(ActualEndDate));
			    	String checkDate = (CheckDate == null ? "" : simpleFormat.format(CheckDate));

			    	IRow rowAdd = exportTable.addRow();
			    	rowAdd.getCell(0).setValue(seq);
			    	rowAdd.getCell(1).setValue(type);
			    	rowAdd.getCell(2).setValue(TaskName);
			    	rowAdd.getCell(3).setValue(NatureTimes);
			    	rowAdd.getCell(4).setValue(planStart);
			    	rowAdd.getCell(5).setValue(planEnd);
			    	rowAdd.getCell(6).setValue(actualStartDate);
			    	rowAdd.getCell(7).setValue(actualEndDate);
			    	rowAdd.getCell(8).setValue(checkDate);
			    	rowAdd.getCell(9).setValue(AdminPerson);
			    	rowAdd.getCell(10).setValue(AdminDept);
			    }
				try {
					tempFile = File.createTempFile("eastemp",".xls");
					path = tempFile.getCanonicalPath();
					KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
					tablesVO[0]=new KDTables2KDSBookVO(exportTable);
					tablesVO[0].setTableName("任务导出表");
					KDSBook book = null;
				    book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
				    exportM.exportToExcel(book, path);
				    
				    KDFileChooser fileChooser = new KDFileChooser();
					fileChooser.setFileSelectionMode(0);
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setSelectedFile(new File("任务导出表.xls"));
					int result = fileChooser.showSaveDialog(null);
					
					if (result == KDFileChooser.APPROVE_OPTION){
						File dest = fileChooser.getSelectedFile();
						File src = new File(path);
						if (dest.exists())
							dest.delete();
						src.renameTo(dest);
						MsgBox.showInfo("导出成功！");
						KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
					}
					tempFile.delete();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	private void initExportTable(KDTable table) {
		table.addHeadRow(0);
		String[] head = new String[] {"序号","任务类别","任务名称", "任务工期", "计划开始日期", "计划完成日期",
									  "实际开始日期", "实际完成日期", "考核日期", "责任人", "责任部门"};
		for(int i = 0; i < head.length; i++) {
			IColumn col = table.addColumn();
			col.setKey("" + i);
			table.getHeadRow(0).getCell("" + i).setValue(head[i]);
		}
	}
	private String addSpaceToTaskName(String taskName, int level) {
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < level-1; i++)
			sb.append("    ");
		sb.append(taskName);
		return sb.toString();
	}
	/**
	 * 当前UI是否是计划执行
	 * 
	 * @return
	 */
	public boolean isFromExecuteUI() {
		Object metaPK = getUIContext().get("METADATA.PK");
		if (FDCHelper.isEmpty(metaPK)) {
			return false;
		} else if (metaPK.toString().endsWith("MainScheduleExecuteUI") || metaPK.toString().endsWith("SpecialScheduleExecuteUI")) {
			return true;
		}
		return false;
	}

	/**
	 * 当前UI是否是总进度计划
	 * 
	 * @return
	 */
	public boolean isFromTotalUI() {
		Object metaPK = getUIContext().get("METADATA.PK");
		if (FDCHelper.isEmpty(metaPK)) {
			return false;
		} else if (metaPK.toString().endsWith("TotalScheduleEditUI")) {
			return true;
		}
		return false;
	}

	/**
	 * 移动工具栏按钮到甘特图
	 * <p>
	 * 将与任务相关的功能，移到甘特图中<br>
	 * 如属性、批量修改责任人、定位、大纲级别
	 */
	protected void moveBtnToGantt() {
		toolBar.remove(btnProperty);
		btnProperty = new KDWorkButton(btnProperty.getAction());
		btnProperty.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnProperty);

		Dimension dms = new Dimension(3, 0);
		Filler filler = new Box.Filler(dms, dms, dms);
		ganttProject.getToolbarBox().add(filler);

		toolBar.remove(btnBatChangeRespDept);
		btnBatChangeRespDept = new KDWorkButton(btnBatChangeRespDept.getAction());
		// btnBatChangeRespDept.setBorder(btnShowByLevel.getBorder());
		btnBatChangeRespDept.setText("批量修改责任人");
		btnBatChangeRespDept.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
		btnBatChangeRespDept.setToolTipText("批量修改责任人");
		btnBatChangeRespDept.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnBatChangeRespDept);

		toolBar.remove(btnBatchModifyTaskType);
		btnBatChangeRespDept = new KDWorkButton(btnBatchModifyTaskType.getAction());
		// btnBatChangeRespDept.setBorder(btnShowByLevel.getBorder());
		btnBatchModifyTaskType.setText("批量修改业务类型");
		btnBatchModifyTaskType.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
		btnBatchModifyTaskType.setToolTipText("批量修改业务类型");
		btnBatchModifyTaskType.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnBatchModifyTaskType);

		filler = new Box.Filler(dms, dms, dms);
		ganttProject.getToolbarBox().add(filler);

		toolBar.remove(btnLocate);
		btnLocate = new KDWorkButton(btnLocate.getAction());
		btnLocate.setIcon(EASResource.getIcon("imgTbtn_speedgoto"));
		this.btnLocate.setText("定位");
		this.btnLocate.setToolTipText("定位");
		btnLocate.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnLocate);

		filler = new Box.Filler(dms, dms, dms);
		ganttProject.getToolbarBox().add(filler);

		ganttProject.getToolbarBox().add(btnShowByLevel);

	}

	private void loadProjectState() {
		cbState.setSelectedItem(editData.getState());
		cbState.setEnabled(false);
	}

	private void setVersion() {
		txtVersion.setText(editData.getVersionName());
	}

	protected void afterOnload() {
		FDCClientUtils.setRespDeptF7(prmtAdminDept, this);
		prmtAuditor.setEnabled(false);
		pkAuditTime.setEnabled(false);
		pkCreateTime.setEnabled(false);
		prmtAdminDept.setEnabled(false);
		prmtAdminDept.setRequired(true);
		// 保证提交后不新增
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.btnAdjust.setEnabled(true);
		this.btnRestore.setEnabled(true);
		txtVersion.setEnabled(false);
		cbState.setEnabled(false);
	}

	/**
	 * 
	 * 
	 * 描述： 修改根据F7数据过滤-添加当前用户组织
	 * 
	 * @author 周航建
	 * @version EAS7.0
	 * @modifyDate 2011-10-5
	 * 
	 */
	private void loadCurProject() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		String sql = "select fid from t_fdc_curproject where FCostCenterId in (select fid from t_org_baseunit where flongnumber like (select flongnumber||'%' from t_org_baseunit where fid ='"
				+ SysContext.getSysContext().getCurrentCostUnit().getId().toString()
				+ "') and fisfreeze = 0 and fisOuSealUp = 0  and fid in (select forgid from T_PM_OrgRange where fuserid = '"
				+ SysContext.getSysContext().getCurrentUserInfo().getId().toString() + "'))";
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", sql, CompareType.INNER));
		logger.error(sql);
		
		/* modified by zhaoqin on 2014/06/10 */
		getAuthorizedCostOrgs();
		filter.getFilterItems().add(new FilterItemInfo("costCenter.id", authorizedOrgs, CompareType.INCLUDE));
		
		view.setFilter(filter);

		prmtCurproject.setEntityViewInfo(view);
		prmtCurproject.setDataNoNotify(editData.getProject());
		prmtCurproject.setEditable(false);
		logger.error(view);
	}

	/**
	 * 导入project
	 */
	public void actionImportProject_actionPerformed(ActionEvent e) throws Exception {
		String oldState = getOprtState(); //modified by zhaoqin on 2014/06/17
		beforeCheck();

		/* 存储当前任务 */
		CoreBaseCollection currentAllTaskCollection = new CoreBaseCollection();
		CoreBaseCollection currentWbsCollection = new CoreBaseCollection();
		CoreBaseCollection currentDependTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < this.editData.getTaskEntrys().size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = this.editData.getTaskEntrys().get(k);
			currentAllTaskCollection.add(fDCScheduleTaskInfo);
			if (null != fDCScheduleTaskInfo.getWbs()) {
				currentWbsCollection.add(fDCScheduleTaskInfo.getWbs());
			}
			FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
			for (int m = 0; m < fDCScheduleTaskDependCollection.size(); m++) {
				currentDependTaskCollection.add(fDCScheduleTaskDependCollection.get(m));
			}
		}

		if (this.editData.getTaskEntrys().size() > 0) {
			int result = FDCMsgBox.showConfirm2("此操作将删除现有任务，是否继续");
			/* result==0 删除并新增任务 result==2 不执行导入操作 */
			if (result == 2) {
				return;
			}
		}

		/*
		 * 执行导入project操作
		 */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));
		/* 设置过虑器 */
		chooser.setFileFilter(new FilterType());
		/* 弹出对话框 */
		chooser.showDialog(null, "导入Project");
		/* 判断是否选中文件 */
		if (chooser.getSelectedFile() == null) {
			return;
		}
		/* 获得选取文件路径 */
		String filePath = chooser.getSelectedFile().getPath();
		File file = new File(filePath);

		/* 清空表格数据 */
		this.editData.getTaskEntrys().clear();

		/* 取数据 */
		List projectList = RESchMSProjectReader.pasreMSProject(file, new IRESchTaskCreator() {
			public IRESchTask createSchTask() {
				return (IRESchTask) new FDCScheduleTaskInfo();
			}
		}, new IRESchTaskPredecessorCreator() {
			public IRESchTaskPredecessor createSchTaskPredecessor() {
				return new FDCScheduleTaskDependInfo();
			}
		}, new IRESchCalendar() {
			public ScheduleCalendarInfo getSchedule() {
				return editData.getCalendar();
			}
		});

		/* 存入以本级任务id为键，本级任务为值。以便后面构建层次结构 */
		Map projectMap = new HashMap();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				if (null != fDCScheduleTaskInfo.getMsProjectId()) {
					projectMap.put(fDCScheduleTaskInfo.getMsProjectId().toString(), fDCScheduleTaskInfo);
				}
			}
		}

		wbsCoreBaseCollection = new CoreBaseCollection();
		ScheduleCalendarInfo calendar = editData.getCalendar();

		/* 存入模板任务数据，并设置层次结构 */
		CoreBaseCollection newTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				Object projectObj = projectMap.get(String.valueOf(null == fDCScheduleTaskInfo.getMsParentPrjId() ? "" : fDCScheduleTaskInfo.getMsParentPrjId()));
				if (null != projectObj && !"".equals(projectObj.toString().trim())) {
					if (projectObj instanceof FDCScheduleTaskInfo) {
						fDCScheduleTaskInfo.setParent((FDCScheduleTaskInfo) projectObj);
					}
				}
				/* 创建WBS */
				// fDCScheduleTaskInfo.setWbs(createWBS(fDCScheduleTaskInfo,
				// fDCScheduleTaskInfo.getParent()));
				Date closestStart = ScheduleCalendarHelper.getClosestWorkDay(fDCScheduleTaskInfo.getPlanStart(), calendar);
				Date closestEnd = ScheduleCalendarHelper.getClosestWorkDay(fDCScheduleTaskInfo.getPlanEnd(), calendar);
				fDCScheduleTaskInfo.setStart(closestStart);
				fDCScheduleTaskInfo.setEnd(closestEnd);
				/* 设置有效工期 */
				BigDecimal effDay = ScheduleCalendarHelper.getEffectTimes(closestStart, closestEnd, calendar);
				fDCScheduleTaskInfo.setDuration(effDay.intValue());
				fDCScheduleTaskInfo.setEffectTimes(effDay);
				fDCScheduleTaskInfo.setSchedule(this.editData);
				fDCScheduleTaskInfo.setComplete(FDCHelper.ZERO);
				fDCScheduleTaskInfo.setCalendar(this.editData.getCalendar());
				fDCScheduleTaskInfo.setNatureTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setIsScheduleTask(true);
				fDCScheduleTaskInfo.setPriority(1);
				fDCScheduleTaskInfo.setIsAdd(true);
				// 序号（千万重要，也跟前置任务显示有关）
				fDCScheduleTaskInfo.setSeq(k + 1);
				fDCScheduleTaskInfo.setSrcID(fDCScheduleTaskInfo.getId().toString());
				if (fDCScheduleTaskInfo.getParent() != null) {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getParent().getLongNumber() + "!" + fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel() + 1);
				} else {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(0);
				}
				// if (null != fDCScheduleTaskInfo.getWbs()) {
				// fDCScheduleTaskInfo.setLocked(fDCScheduleTaskInfo.getWbs().
				// isIsLocked());
				// }
				newTaskCollection.add((CoreBaseInfo) fDCScheduleTaskInfo);
				this.editData.getTaskEntrys().add(fDCScheduleTaskInfo);
			}
		}
		verifyIncludeGlobalTask(this.editData.getTaskEntrys());
		/* 刷新表 */
		/* 重新加载数据 */
		try {
			setDataObject(this.editData);
			load2Gantt(this.editData);
			this.setInitEnd(false);
			setMessageText("有" + newTaskCollection.size() + "条数据被成功导入！");
			showMessage();
			weakOnShow();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		this.oprtState = oldState; //modified by zhaoqin on 2014/06/17
		getScheduleGanttProject().setOprtState(this.getOprtState());
		changePageState();
	}
	
	private void verifyIncludeGlobalTask(FDCScheduleTaskCollection scheduleTaskCollection) {
		GlobalTaskNodeCollection cols = FDCScheduleBaseHelper.getGlobalTaskNode();
		Map newMap = new HashMap();
		for (int i = 0; i < cols.size(); i++) {
			GlobalTaskNodeInfo taskNode = cols.get(i);
			newMap.put(taskNode.getName().toString(), taskNode);
		}
		if (newMap.isEmpty()) {
			return;
		}
		FDCScheduleTaskInfo task = null;
		GlobalTaskNodeInfo nodeInfo = null;
		int size = scheduleTaskCollection.size();
		for (int i = 0; i < size; i++) {
			task = scheduleTaskCollection.get(i);
//			if (newMap.containsKey(task.getName())) {
//				nodeInfo = (GlobalTaskNodeInfo) newMap.get(task.getName());
//				task.setSrcGroupNode(nodeInfo.getId().toString());
//				newMap.remove(task.getName());
//			}
            if(newMap.containsKey(task.getName().toString())){
 				newMap.remove(task.getName().toString());
             }
		}

		if (!newMap.isEmpty()) {
			StringBuffer errMsg = new StringBuffer();
			Set set = newMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				errMsg.append(((GlobalTaskNodeInfo) ((Entry) it.next()).getValue()).getName());
				errMsg.append("\n");
			}

//			FDCMsgBox.showDetailAndOK(this, "当前project文件中未包含以下集团管控节点!是否继续提交", errMsg.toString(), MsgBox.ICONERROR);
			int  flag = FDCMsgBox.showConfirm3a(this, "当前project文件中未包含以下集团管控节点!是否继续提交", errMsg.toString());
			if(flag != 0){				
				abort();
			}
		}
		newMap.clear();
	}
	/**
	 * 导入Project
	 * <p>
	 * 
	 * @author Xiaolong_Luo
	 * @createDate 2011-08-20
	 */
	public void actionImportPlanTemplate_actionPerformed(ActionEvent e) throws Exception {
		String oState = getOprtState();
		beforeCheck();
		wbsCoreBaseCollection = new CoreBaseCollection();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(REScheduleImportPlanTemplateListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
//		IUIWindow uiWindow = uiFactory.create(F7RESchTemplateUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		getScheduleGanttProject().setOprtState(oState);
		oprtState = oState;
		changePageState();
	}

	/**
	 * @discription <导入计划模板>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/20>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @see <相关的类>
	 */
	public void importPlanTemplate(RESchTemplateInfo schTemplateInfo, Date planStateDate) {
		if (null == schTemplateInfo || null == schTemplateInfo.getId() || "".equals(schTemplateInfo.getId().toString().trim())) {
			return;
		}
		ScheduleCalendarInfo calendar = null;
		try {
			calendar = ScheduleCalendarFactory.getRemoteInstance().getDefaultCal(editData.getProject().getId().toString());
		} catch (EASBizException e1) {
			handUIException(e1);
		} catch (BOSException e1) {
			handUIException(e1);
		}
		editData.setCalendar(calendar);
		
		planStateDate = ScheduleCalendarHelper.getClosestWorkDay(planStateDate, calendar);
		/* 存储当前任务 */
		CoreBaseCollection currentAllTaskCollection = new CoreBaseCollection();
		CoreBaseCollection currentWbsCollection = new CoreBaseCollection();
		CoreBaseCollection currentDependTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < this.editData.getTaskEntrys().size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = this.editData.getTaskEntrys().get(k);
			currentAllTaskCollection.add(fDCScheduleTaskInfo);
			if (null != fDCScheduleTaskInfo.getWbs()) {
				currentWbsCollection.add(fDCScheduleTaskInfo.getWbs());
			}
			FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
			for (int m = 0; m < fDCScheduleTaskDependCollection.size(); m++) {
				currentDependTaskCollection.add(fDCScheduleTaskDependCollection.get(m));
			}
		}

		/* 清空表格数据 */
		editData.getTaskEntrys().clear();

		/* 根据模板id获得所有的模板任务信息，并同时导入模板任务 */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("template.id", schTemplateInfo.getId());
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("template.*"));
		view.getSelector().add(new SelectorItemInfo("parent.*"));
		view.getSelector().add(new SelectorItemInfo("standardTask.*"));
		view.getSelector().add(new SelectorItemInfo("businessType.*"));
		view.getSelector().add(new SelectorItemInfo("businessType.bizType.number"));
		view.getSelector().add(new SelectorItemInfo("businessType.bizType.id"));
		view.getSelector().add(new SelectorItemInfo("businessType.bizType.name"));
		view.getSelector().add(new SelectorItemInfo("predecessors.*"));
		view.getSelector().add(new SelectorItemInfo("achievementType.*"));
		try {
			RESchTemplateTaskCollection rESchTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);

			/* 取值 */
			Map sMap = new HashMap();
			Map pMap = new HashMap();
			Map handlerProcessorMap = new HashMap();
			Set needProcessSet = new HashSet();
			FDCScheduleTaskCollection fDCScheduleTaskCollection = new FDCScheduleTaskCollection();
			for (int k = 0; k < rESchTemplateTaskCollection.size(); k++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = new FDCScheduleTaskInfo();
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(k);
				/* 设置主键列 */
				fDCScheduleTaskInfo.setId(BOSUuid.create((new FDCScheduleTaskInfo()).getBOSType()));
				fDCScheduleTaskInfo.setSchTemplateTask(rESchTemplateTaskInfo);
				/* 设置任务名称 */
				fDCScheduleTaskInfo.setName(rESchTemplateTaskInfo.getName());
				// 序号（千万重要，也跟前置任务显示有关）
				fDCScheduleTaskInfo.setSeq(k + 1);
				/* 设置 任务类别 */
				fDCScheduleTaskInfo.setTaskType(rESchTemplateTaskInfo.getTaskType());
				/* 设置业务类型 */
				for (int s = 0; s < rESchTemplateTaskInfo.getBusinessType().size(); s++) {
					RESchTemplateTaskBizTypeInfo rESchTemplateTaskBizTypeInfo = rESchTemplateTaskInfo.getBusinessType().get(s);
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = new ScheduleTaskBizTypeInfo();
					scheduleTaskBizTypeInfo.setId(BOSUuid.create((new ScheduleTaskBizTypeInfo()).getBOSType()));
					scheduleTaskBizTypeInfo.setBizType(rESchTemplateTaskBizTypeInfo.getBizType());
					fDCScheduleTaskInfo.getBizType().add(scheduleTaskBizTypeInfo);
				}

				/* 设置工期 */
				fDCScheduleTaskInfo.setEffectTimes(BigDecimal.valueOf(rESchTemplateTaskInfo.getReferenceDay()));
				fDCScheduleTaskInfo.setDuration(rESchTemplateTaskInfo.getReferenceDay());
				/* 设置成果类别 */
				fDCScheduleTaskInfo.setAchievementType(rESchTemplateTaskInfo.getAchievementType());
				/* 设置标准任务 */
				fDCScheduleTaskInfo.setStandardTask(rESchTemplateTaskInfo.getStandardTask());
				/* 设置备注 */
				fDCScheduleTaskInfo.setDescription(rESchTemplateTaskInfo.getDescription());
				fDCScheduleTaskInfo.setComplete(FDCHelper.ZERO);
				/* 设置进度任务 */
				fDCScheduleTaskInfo.setSchedule(this.editData);
				/* 设置编码 */
				fDCScheduleTaskInfo.setNumber(rESchTemplateTaskInfo.getNumber());
				/* 设置长编码 */
				fDCScheduleTaskInfo.setLongNumber(rESchTemplateTaskInfo.getLongNumber());
				/* 设置级次 */
				fDCScheduleTaskInfo.setLevel(rESchTemplateTaskInfo.getLevel() - 1);
				/* 设置是否是子节点 */
				fDCScheduleTaskInfo.setIsLeaf(rESchTemplateTaskInfo.isIsLeaf());
				fDCScheduleTaskInfo.setSrcID(fDCScheduleTaskInfo.getId().toString());
				fDCScheduleTaskCollection.add(fDCScheduleTaskInfo);

				sMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo);
				if (null != rESchTemplateTaskInfo.getParent() && null != rESchTemplateTaskInfo.getParent().getId() && !"".equals(rESchTemplateTaskInfo.getParent().getId().toString().trim())) {
					pMap.put(fDCScheduleTaskInfo.getId().toString(), rESchTemplateTaskInfo.getParent().getId());
				}
				handlerProcessorMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo);
			}

			CoreBaseCollection newDependTaskCollection = new CoreBaseCollection();
			CoreBaseCollection newTaskCollection = new CoreBaseCollection();
			/* 存值 */
			for (int j = 0; j < fDCScheduleTaskCollection.size(); j++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(j);

				Object pObject = pMap.get(fDCScheduleTaskInfo.getId().toString());
				/* 判断是否存在父节点 */
				if (null != pObject && !"".equals(pObject.toString().trim())) {
					Object sObject = sMap.get(pObject.toString().trim());
					if (null != sObject && sObject instanceof FDCScheduleTaskInfo) {
						FDCScheduleTaskInfo dCScheduleTaskInfo = (FDCScheduleTaskInfo) sObject;
						fDCScheduleTaskInfo.setParent(dCScheduleTaskInfo);
					}
				}
				
				fDCScheduleTaskInfo.setWbs(createWBS(fDCScheduleTaskInfo, fDCScheduleTaskInfo.getParent()));
				fDCScheduleTaskInfo.setExpand(true);
				fDCScheduleTaskInfo.setCalendar(this.editData.getCalendar());
				// fDCScheduleTaskInfo.setEffectTimes(BigDecimal.valueOf(0));
				// fDCScheduleTaskInfo.setNatureTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setIsScheduleTask(true);
				fDCScheduleTaskInfo.setPriority(1);
				fDCScheduleTaskInfo.setIsAdd(true);

				// 修改了导入时没默认时间而导致
				// 鼠标移到相关任务上时报中断，请罗小龙确认，导入的计划任务是否需要根据工期来处理计划--lanyuanjun

				fDCScheduleTaskInfo.setStart(planStateDate);
				Date endDate = ScheduleCalendarHelper.getEndDate(planStateDate, fDCScheduleTaskInfo.getEffectTimes()
						.subtract(FDCHelper.ONE), calendar);
				// Date planEndDate = new Date();
				// if(fDCScheduleTaskInfo.getDuration() > 0){
				// planEndDate =
				// FDCDateHelper.addDays(planStateDate,fDCScheduleTaskInfo
				// .getDuration()-1);
				// }else{
				// planEndDate =
				// FDCDateHelper.addDays(planStateDate,fDCScheduleTaskInfo
				// .getDuration());
				// }

				fDCScheduleTaskInfo.setEnd(endDate);
				
				if ((fDCScheduleTaskInfo.getTaskType().equals(RESchTaskTypeEnum.KEY)) || 
					      (fDCScheduleTaskInfo.getTaskType().equals(RESchTaskTypeEnum.MILESTONE))){
					fDCScheduleTaskInfo.setIsCheckNode(true);
					CheckNodeCollection cols =CheckNodeFactory.getRemoteInstance().getCheckNodeCollection("select * from where name='"+fDCScheduleTaskInfo.getName()+"'");
					if(cols.size()>0){
						fDCScheduleTaskInfo.setCheckNode(cols.get(0));
					}else{
						FDCMsgBox.showWarning(this,"任务名称 '"+fDCScheduleTaskInfo.getName()+"' 没有设成考核节点，请先在考核节点中进行设置！");
						SysUtil.abort();
					}
					fDCScheduleTaskInfo.setCheckDate(endDate);
				}
				
				if (null != fDCScheduleTaskInfo.getWbs()) {
					fDCScheduleTaskInfo.setLocked(fDCScheduleTaskInfo.getWbs().isIsLocked());
					wbsCoreBaseCollection.add(fDCScheduleTaskInfo.getWbs());
				}

				/* 设置前置任务 */
				processDepends(fDCScheduleTaskInfo, rESchTemplateTaskCollection.get(j), handlerProcessorMap, newDependTaskCollection);
				FDCScheduleTaskDependCollection cols = fDCScheduleTaskInfo.getDependEntrys();
				FDCScheduleTaskDependInfo depend = null;
				for(Iterator it = cols.iterator();it.hasNext();){
					 depend = (FDCScheduleTaskDependInfo) it.next();
					 FDCScheduleTaskInfo beforeTask = depend.getTask();
					 if (beforeTask.getPlanStart() != null || beforeTask.getPlanEnd() != null) {
						fDCScheduleTaskInfo = ScheduleHelper.computeTaskDate(fDCScheduleTaskInfo, beforeTask, depend.getPredecessorType(),
								depend.getDifference(), needProcessSet);
					} else {
						needProcessSet.add(fDCScheduleTaskInfo.getId().toString());
					}
					 
					 
				}
				
				
				newTaskCollection.add(fDCScheduleTaskInfo);
				this.editData.getTaskEntrys().add(fDCScheduleTaskInfo);
			}
			
			
			for (int i = 0; i < editData.getTaskEntrys().size(); i++) {
				FDCScheduleTaskInfo myTask = editData.getTaskEntrys().get(i);
				if (needProcessSet.contains(myTask.getId().toString())) {
					FDCScheduleTaskDependCollection cols = myTask.getDependEntrys();
					FDCScheduleTaskDependInfo depend = null;
					for (Iterator it = cols.iterator(); it.hasNext();) {
						depend = (FDCScheduleTaskDependInfo) it.next();
						FDCScheduleTaskInfo beforeTask = depend.getTask();
						if (beforeTask.getPlanStart() != null || beforeTask.getPlanEnd() != null) {
							myTask = ScheduleHelper
									.computeTaskDate(myTask, beforeTask, depend.getPredecessorType(), depend.getDifference());
						}

					}
				}
			}
			
			

			/* 刷新表 */
			/* 重新加载数据 */
			try {
				setDataObject(this.editData);
				load2Gantt(this.editData);
				this.setInitEnd(false);
				setMessageText("有" + newTaskCollection.size() + "条数据被成功导入！");
				GanttTreeTable table = ganttProject.getGanttTreeTable(); 
				TaskDependencyCollectionMutator myMutator;
				Task[] tasks = ganttProject.getTaskManager().getTasks();
				Task task = null;
				for(int i=0;i<tasks.length;i++){
					task = tasks[i];
					if(task.getManager().getDependencyCollection()!=null){
						myMutator = task.getManager().getDependencyCollection().createMutator();
						myMutator.commit();
					}
					
					/* modified by zhaoqin for R140617-0259 on 2014/06/23 start */
					if(task instanceof KDTask) {
						KDTask t = (KDTask)task;
						ScheduleTaskBaseInfo info = t.getScheduleTaskInfo();
						if(null != info && !info.isIsLeaf()) {
							info.setStart(ScheduleParserHelper.parseGanttCalendarToDate(t.getStart()));
							info.setEnd(ScheduleParserHelper.parseGanttCalendarToDate(t.getEnd()));
						}
					}
					/* modified by zhaoqin for R140617-0259 on 2014/06/23 end */
					
				}
				ganttProject.getTree().getTable().repaint();
				showMessage();
				weakOnShow();

			} catch (Exception ex) {
				handUIException(ex);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
	}

	/**
	 * @discription <处理前置任务>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/31>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @see <相关的类>
	 */
	private void processDepends(FDCScheduleTaskInfo fDCScheduleTaskInfo, RESchTemplateTaskInfo rESchTemplateTaskInfo, Map handlerProcessorMap, CoreBaseCollection newDependTaskCollection) {
		FDCScheduleTaskDependCollection depends = fDCScheduleTaskInfo.getDependEntrys();

		RESchTemplateTaskPredecessorCollection rESchTemplateTaskPredecessorCollection = rESchTemplateTaskInfo.getPredecessors();
		for (int k = 0; k < rESchTemplateTaskPredecessorCollection.size(); k++) {
			RESchTemplateTaskPredecessorInfo rESchTemplateTaskPredecessorInfo = rESchTemplateTaskPredecessorCollection.get(k);

			RESchTemplateTaskInfo schTemplateTaskInfo = rESchTemplateTaskPredecessorInfo.getPredecessorTask();
			if (null == schTemplateTaskInfo) {
				continue;
			}
			Object proceObj = handlerProcessorMap.get(schTemplateTaskInfo.getId().toString());
			if (null == proceObj) {
				continue;
			}

			FDCScheduleTaskDependInfo depend = new FDCScheduleTaskDependInfo();
			depends.add(depend);
			// 设置当前任务
			depend.setDependTask(fDCScheduleTaskInfo);
			// 设置前置任务
			depend.setTask((FDCScheduleTaskInfo) handlerProcessorMap.get(schTemplateTaskInfo.getId().toString()));
			// 设置搭接关系
			depend.setType(rESchTemplateTaskPredecessorInfo.getPredecessorType());
			// 设置搭接天数
			depend.setDifference(rESchTemplateTaskPredecessorInfo.getDifferenceDay());
			depend.setHardness(DependHardnessEnum.Rubber);
			newDependTaskCollection.add(depend);
		}
	}

	// 创建WBS
	private FDCWBSInfo createWBS(FDCScheduleTaskInfo task, FDCScheduleTaskInfo parentTask) {
		if (null == parentTask) {
			parentTask = new FDCScheduleTaskInfo();
		}
		FDCWBSInfo wbs = new FDCWBSInfo();
		wbs.setId(BOSUuid.create(wbs.getBOSType()));
		wbs.setParent(parentTask.getWbs());
		wbs.setIsEnabled(true);
		wbs.setBoolean("isNew", true);
		wbs.setIsFromTemplate(false);
		wbs.setCurProject(this.editData.getProject());
		wbs.setName(task.getName());
		wbs.setTaskType(this.editData.getScheduleType());
		if (isSpecialSchedule()) {
			// 专项则继承已选节点的责任人和责任部门
			if (null != parentTask.getWbs()) {
				wbs.setAdminPerson(parentTask.getWbs().getAdminPerson());
				wbs.setRespDept(parentTask.getWbs().getRespDept());
			}
		} else {
			// 主项则设置计划部门为登陆用户的成本中心
			wbs.setAdminDept(SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo());
		}
		wbs.setIsUnVisible(false);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(task.getStart(), task.getEnd(), this.editData.getCalendar());
		wbs.setEstimateDays(effectTimes.intValue());
		wbsCoreBaseCollection.add(wbs);
		return wbs;
	}

	boolean isSpecialSchedule() {
		if (null != this.editData.getScheduleType()) {
			return TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(this.editData.getScheduleType().getId().toString());
		} else {
			return false;
		}
	}

	/**
	 * @discription <导出计划模板>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/20>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @see <相关的类>
	 */
	public void actionExportPlanTemplate_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("isCloseWindow", "true");
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(RESchTemplateEditOldUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * @discription <导出计划模板>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/20>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @see <相关的类>
	 */
	public void exportPlanTemplate(RESchTemplateInfo schTemplateInfo) {
		if (null == schTemplateInfo.getName() || "".equals(schTemplateInfo.getName().trim())) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("dependEntrys.*");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.id", this.editData.getId());
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		try {
			FDCScheduleTaskCollection fDCScheduleTaskCollection = editData.getTaskEntrys();

			Map ttMap = new HashMap();
			Map tpMap = new HashMap();
			RESchTemplateTaskCollection rESchTemplateTaskCollection = new RESchTemplateTaskCollection();

			/* 取数据 */
			for (int i = 0; i < fDCScheduleTaskCollection.size(); i++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = new RESchTemplateTaskInfo();
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(i);
				/* 设置主键 */
				rESchTemplateTaskInfo.setId(BOSUuid.create((new RESchTemplateTaskInfo()).getBOSType()));
				/* 顺序 */
				rESchTemplateTaskInfo.setSeq(i + 1);
				/* 设置任务名称 */
				rESchTemplateTaskInfo.setName(fDCScheduleTaskInfo.getName());
				/* 设置 任务类别 */
				rESchTemplateTaskInfo.setTaskType(fDCScheduleTaskInfo.getTaskType());
				/* 设置业务类型 */
				for (int s = 0; s < fDCScheduleTaskInfo.getBizType().size(); s++) {
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = fDCScheduleTaskInfo.getBizType().get(s);
					RESchTemplateTaskBizTypeInfo rESchTemplateTaskBizTypeInfo = new RESchTemplateTaskBizTypeInfo();
					rESchTemplateTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
					rESchTemplateTaskBizTypeInfo.setBizType(scheduleTaskBizTypeInfo.getBizType());
					rESchTemplateTaskInfo.getBusinessType().add(rESchTemplateTaskBizTypeInfo);
				}
				/* 设置工期 */
				rESchTemplateTaskInfo.setReferenceDay(fDCScheduleTaskInfo.getDuration());
				/* 设置成果类别 */
				rESchTemplateTaskInfo.setAchievementType(fDCScheduleTaskInfo.getAchievementType());
				/* 设置标准任务 */
				rESchTemplateTaskInfo.setStandardTask(fDCScheduleTaskInfo.getStandardTask());
				/* 设置备注 */
				rESchTemplateTaskInfo.setDescription(fDCScheduleTaskInfo.getDescription());
				/* 设置模板 */
				rESchTemplateTaskInfo.setTemplate(schTemplateInfo);
				/* 设置编码 */
				rESchTemplateTaskInfo.setNumber(fDCScheduleTaskInfo.getNumber());
				ttMap.put(fDCScheduleTaskInfo.getId().toString(), rESchTemplateTaskInfo);
				if (null != fDCScheduleTaskInfo.getParent() && null != fDCScheduleTaskInfo.getParent().getId()) {
					tpMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo.getParent().getId());
				}
				/* 设置前置 */
				FDCScheduleTaskDependCollection dependEntrys = fDCScheduleTaskInfo.getDependEntrys();
				if (dependEntrys != null && dependEntrys.size() > 0) {
					for (int j = 0; j < dependEntrys.size(); j++) {
						RESchTemplateTaskPredecessorInfo preInfo = new RESchTemplateTaskPredecessorInfo();
						preInfo.setParent(rESchTemplateTaskInfo);
						preInfo.setTask(rESchTemplateTaskInfo);
						preInfo.setPredecessorType(dependEntrys.get(j).getPredecessorType());
						preInfo.setDifferenceDay(dependEntrys.get(j).getDifference());
						preInfo.put("depTaskID", dependEntrys.get(j).getDependTask().getId().toString());
						rESchTemplateTaskInfo.getPredecessors().add(preInfo);
					}
				}
				/* 设置长编码 */
				String longNumber = fDCScheduleTaskInfo.getLongNumber();
				rESchTemplateTaskInfo.setLongNumber(longNumber.replaceAll("\\.", "!"));
				/* 设置级次 */
				rESchTemplateTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel());
				/* 设置是否是子节点 */
				rESchTemplateTaskInfo.setIsLeaf(fDCScheduleTaskInfo.isIsLeaf());
				rESchTemplateTaskCollection.add(rESchTemplateTaskInfo);
			}

			CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
			/* 存数据 */
			Set ids = new HashSet();
			for (int j = 0; j < rESchTemplateTaskCollection.size(); j++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(j);
				Object pObject = tpMap.get(rESchTemplateTaskInfo.getId().toString());
				/* 判断是否存在父节点 */
				if (null != pObject && !"".equals(pObject.toString().trim())) {
					Object sObject = ttMap.get(pObject.toString().trim());
					if (null != sObject && sObject instanceof RESchTemplateTaskInfo) {
						RESchTemplateTaskInfo chTemplateTaskInfo = (RESchTemplateTaskInfo) sObject;
						rESchTemplateTaskInfo.setParent(chTemplateTaskInfo);
						coreBaseCollection.add(chTemplateTaskInfo);
					}
				}
				/* 前置任务 */
				if (rESchTemplateTaskInfo.getPredecessors() != null && rESchTemplateTaskInfo.getPredecessors().size() > 0) {
					for (int k = 0; k < rESchTemplateTaskInfo.getPredecessors().size(); k++) {
						RESchTemplateTaskPredecessorInfo preInfo = rESchTemplateTaskInfo.getPredecessors().get(k);
						preInfo.setPredecessorTask(preInfo.getTask());
						String depTaskID = (String) preInfo.get("depTaskID");
						RESchTemplateTaskInfo depInfo = (RESchTemplateTaskInfo) ttMap.get(depTaskID);
						ids.add(depInfo.getId().toString());
						preInfo.setTask(depInfo);
						preInfo.setParent(depInfo);
					}
				}
				RESchTemplateTaskFactory.getRemoteInstance().addnew(rESchTemplateTaskInfo);
			}
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" update T_SCH_RESchTplTaskPredecessor set FParentID = FTaskID where FTaskID in ");
			sql.appendSql(FMHelper.setTran2String(ids));
			sql.executeUpdate();
			// RESchTemplateTaskFactory.getRemoteInstance().updateBatchData(
			// coreBaseCollection);
		} catch (BOSException e) {
			handUIException(e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
	}

	protected IObjectValue createNewData() {
		FDCScheduleInfo info = null;
		try {
			info = getNewData();
			info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			info.setViewDate(new Date());
		} catch (Exception e) {
			// 并发的时候显示错误会中断线程,先临时保存
			if (null == tempException) {
				tempException = e;
			}
		}
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return info;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		IFDCSchedule schedule = null;
		try {
			schedule = FDCScheduleFactory.getRemoteInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedule;
	}

	private void addEffectTable() {
		if (null != getScheduleGanttProject()) {
			EffectDegreePanel effectDegreePanel = new EffectDegreePanel();
			effectDegreePanel.getCBEffect().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox) e.getSource();
					filterEffectTableData((String) cb.getSelectedItem());
				}
			});
			if (kDPanel1.getComponentCount() < 2) {
				//当getComponentCount()==2时，说明已经添加了kdTable和effectDegreePanel，不需要再添加effectDegreePanel了
				kDPanel1.add(effectDegreePanel, BorderLayout.NORTH);
			}
			getScheduleGanttProject().getTabs().add(kDPanel1, "影响情况");
		}
	}

	/**
	 * 
	 * 根据影响程度中的下拉项，过滤影响情况表中的数据
	 * @Author：owen_wen
	 * @param effectDegree 影响程度：comboBox的下拉选项
	 * @CreateTime：2013-11-28
	 */
	private void filterEffectTableData(String effectDegree) {
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			FDCScheduleTaskInfo scheduleTask = (FDCScheduleTaskInfo) kDTable1.getRow(i).getUserObject();
			if ("所有任务".equals(effectDegree)) {
				kDTable1.getRow(i).getStyleAttributes().setHided(false);
			} else {
				kDTable1.getRow(i).getStyleAttributes().setHided(!scheduleTask.getTaskType().toString().equals(effectDegree));
			}
		}
	}

	/**
	 * 删除影响情况页签
	 */
	private void removeEffectTable() {
		if (null != getScheduleGanttProject()) {
			getScheduleGanttProject().getTabs().remove(kDPanel1);
		}
	}


	protected void initWorkButton() {
		super.initWorkButton();
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(false);
		actionClose.setVisible(false);
		actionUnClose.setVisible(false);
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		this.actionCritical.setVisible(false);
		this.actionCritical.setEnabled(false);
		this.actionDisplayAll.setVisible(false);
		this.actionHideOther.setVisible(false);

		actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		actionSaveNewTask.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_saveas"));
		actionPert.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_estatechart"));

		actionDisplayAll.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_entirely"));
		actionHideOther.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_partshow"));
		btnRestore.setIcon(EASResource.getIcon("imgTbtn_restore"));
		btnEdit.setIcon(EASResource.getIcon("imgTbtn_bluepencil"));
		btnAdjust.setIcon(EASResource.getIcon("imgTbtn_edit"));
		btnClose.setVisible(false);
		btnUnClose.setVisible(false);
		actionCompareVer.setEnabled(true);
		// btnImportPlanTemplate.setVisible(false);
		// btnImportProject.setVisible(false);
		// btnExportPlanTemplate.setVisible(false);
		// btnExportProject.setVisible(false);
		actionLocate.setEnabled(true);

		btnImportPlanTemplate.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		btnExportPlanTemplate.setIcon(EASResource.getIcon("imgTbtn_upenumdel"));
		btnImportProject.setIcon(EASResource.getIcon("imgTbtn_checklose"));
		btnExportProject.setIcon(EASResource.getIcon("imgTbtn_checkprofit"));
		btnShowByLevel.setVisible(true);
		btnShowByLevel.setText("大纲级别");
		btnHisVerion.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		btnCompareVer.setIcon(EASResource.getIcon("imgTbtn_differentia"));

		toolBar.remove(btnZoomLeft);
		toolBar.remove(btnZoomRight);
		KDMenuItem pre = new KDMenuItem(actionZoomLeft);
		pre.setText("前");
		pre.setToolTipText("甘特图往前查看");
		btnZoomCenter.addAssistMenuItem(pre);
		KDMenuItem back = new KDMenuItem(actionZoomRight);
		back.setText("后");
		back.setToolTipText("甘特图往后查看");
		btnZoomCenter.addAssistMenuItem(back);
	}

	public void onShow() throws Exception {
		if (this.getUIWindow() instanceof Window) {
			Window window = (Window) this.getUIWindow();
			// 工作流的审批界面，window为null
			if (window != null) {
				if (window instanceof JFrame) {
					((JFrame) window).setExtendedState(JFrame.MAXIMIZED_BOTH);
				} else {
					window.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					window.requestFocus();
				}
			}
		}
		super.onShow();
		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			this.contCurProject.setEnabled(false);
		}
		btnAttachment.setVisible(false);
		this.btnDisplayAll.setVisible(false);
		this.btnHideOther.setVisible(false);
	}

	public void loadFields() {
		super.loadFields();
	}

	protected FDCScheduleInfo getNewData() throws EASBizException, BOSException {
		/*
		 * FDCScheduleInfo info=new FDCScheduleInfo(); ScheduleDemo demo=new
		 * ScheduleDemo(); info=demo.getTestData();
		 */
		Map param = new HashMap();
		CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
		if (prj == null) {
			FDCMsgBox.showWarning(this, "工程项目为空！");
			SysUtil.abort();
		}
		param.put("adminDeptId", SysContext.getSysContext().getCurrentAdminUnit());// 归口部门
		param.put("prjId", prj.getId().toString());// 工程项目
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_MAINTASK);// 任务专业属性
		FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getNewData(param);
		return info;
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		
		if(getUIContext().get("isFromWorkflow") != null 
				&&	Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue() 
				&& getUIContext().get("DATAOBJECTS") != null){
			editData = (FDCScheduleInfo) ((Map)(getUIContext().get("DATAOBJECTS"))).get("billInfo");
			BOSUuid billId = null;
			if (editData == null) {
				billId = BOSUuid.read(getUIContext().get("ID").toString());
			} else {
				billId = editData.getId();
				if (editData.getProjectSpecial() != null) {

				}
			}
			FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(new ObjectUuidPK(billId));
			return info;
		}else{
			// 为了保证性能getValue只在打开的时候真真执行
			if (this.editData != null) {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("number");
				sic.add("state");
				FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getFDCScheduleInfo(pk, sic);
				if (info.getNumber() != null && info.getNumber().length() > 0) {
					editData.setNumber(info.getNumber());
				}
				return this.editData;
			}

			Map param = new HashMap();
			param.put("isFormExecuteUI", Boolean.valueOf(isFromExecuteUI()));
			param.put("isFormTotalUI", Boolean.valueOf(isFromTotalUI()));
			FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(isMainSchedule(), false, null, null, param);
			info.setEditable(true);
			return info;
			
		}
	}

	protected boolean isMainSchedule() {
		return true;
	}

	public void setOprtState(String oprtType) {
		if (oprtType == null) {
			oprtType = STATUS_VIEW;
		}
		super.setOprtState(oprtType);
		try {
			changePageState();
		} catch (BOSException e) {
			handleException(e);
		} catch (SQLException e) {
			handleException(e);
		}
	}

	/***
	 * 修改说明，按照下正常的业务逻辑来说： 在保存和提交时，当前单据不能被置为最终状态，只有审批的时候此时当前单据才能置为最终状态
	 * 此处不能用是否最终状态作为判断当前操作的依据。
	 * 
	 */
	private List canEditState() {
		List arrayList = new ArrayList();
		arrayList.add(FDCBillStateEnum.SAVED_VALUE);
		arrayList.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return arrayList;
	}

	private List canAuditState() {
		List arrayList = new ArrayList();
		arrayList.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return arrayList;
	}

	private float getMaxVersion(FDCScheduleInfo editData) throws BOSException, SQLException {
		float ver = 1.0f;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select max(fversion) from t_sch_fdcschedule where fprojectid = ?");
		if (editData == null || editData.getProject() == null) {
			return ver;
		} else {
			builder.addParam(editData.getProject().getId().toString());
		}
		if (editData.getProjectSpecial() != null) {
			builder.appendSql("and fprojectspecialid = ? ");
			builder.addParam(editData.getProjectSpecial().getId().toString());
		} else {
			// 解决SqlServer中出现的问题，增加一个条件
			builder.appendSql("and fprojectspecialid is null or (fprojectspecialid = '' and fprojectid =  ? )");
			builder.addParam(editData.getProject().getId().toString());
		}
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			ver = rs.getFloat(1);
		}
		return ver;
	}

	/**
	 * 界面状态改变时，设置所有按钮、页签、甘特图状态
	 * 
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected void changePageState() throws BOSException, SQLException {
		if (editData == null) {
			return;
		}
		boolean isTotalUI = isFromTotalUI();
		boolean isFromExUI = isFromExecuteUI();
		boolean isEdit = oprtState.equals(STATUS_EDIT);
		boolean isNewest = editData.getVersion() == 0 ? true : editData.getVersion() == getMaxVersion(editData);
		boolean isAudited = editData.getState() == ScheduleStateEnum.AUDITTED;
		// 影响情况页签
		if (isEdit) {
			addEffectTable();
		} else {
			removeEffectTable();
		}
		// 基本功能状态控制
		initBaseAction(isFromExUI, isTotalUI, isEdit, isNewest, isAudited);
		// 导入导出状态控制
		initImportExportAction(isTotalUI, isFromExUI, isEdit);
		// 甘特图状态控制
		initGanttActions(isEdit,isTotalUI,isFromExUI);
		
		if (isFromWf()) {
			this.btnSave.setEnabled(true);
			this.btnSave.setVisible(true);
			addEffectTable();
		}
	}

	private void initGanttActions(boolean isEdit, boolean isTotalUI, boolean isFromExUI) {
	    initGanttActions(isEdit);
	    boolean isShow = true;
	    if(isTotalUI){
	    	isShow = false;
	    }
	    
	    if(isFromExUI){
	    	isShow = false;
	    }

	    /* modified by zhaoqin for R140624-0238 on 2015/01/19 */
	   	actionBatChangeRespDept.setVisible(isShow);
	   	actionBatChangeRespDept.setEnabled(isEdit);
	   	
	    this.actionZoomCenter.setVisible(true);
	    this.menuItemZoomCenter.setEnabled(true);
		this.btnZoomCenter.setEnabled(true);
	    this.actionZoomCenter.setEnabled(true);
		this.actionZoomIn.setVisible(true);
		this.actionZoomOut.setVisible(true);
		this.actionZoomLeft.setVisible(true);
		this.actionZoomRight.setVisible(true);
		
	}

	/**
	 * 设置基本action状态
	 * 
	 * @param isFromExUI
	 *            是否执行UI
	 * @param isTotalUI
	 *            是否总进度计划UI
	 * @param isEdit
	 *            是否可编辑
	 * @param isNewest
	 *            是否最新计划
	 * @param isAudited
	 *            是否已审批
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected void initBaseAction(boolean isFromExUI, boolean isTotalUI, boolean isEdit, boolean isNewest, boolean isAudited) throws BOSException, SQLException {
		editData.setEditable(isEdit);
		if (isFromExUI || isTotalUI) {
			// 执行界面不可操作
			actionEdit.setVisible(false);
			actionAdjust.setVisible(false);
			actionRestore.setVisible(false);
			actionSave.setVisible(false);
			actionSubmit.setVisible(false);
			btnHisVerion.setVisible(false);
			actionCompareVer.setVisible(false);
		} else {
			float maxVer = getMaxVersion(editData);
			boolean hasHisVer = maxVer > 0;
			if (hasHisVer && !isNewest) {
				// 当前查看的是历史版本，不可操作
				actionEdit.setVisible(false);
				actionAdjust.setVisible(false);
				actionRestore.setVisible(false);
				actionSave.setVisible(false);
				actionSubmit.setVisible(false);
				actionCompareVer.setVisible(false);
			} else {
				// 编制调整
				actionAdjust.setVisible(isAudited);
				actionEdit.setVisible(!isAudited);

				// 提交还原
				actionSubmit.setVisible(isEdit);
				actionSubmit.setEnabled(isEdit);
				actionRestore.setVisible(isEdit);
				actionRestore.setEnabled(isEdit);
				// 保存审批
				if (editData.getState() != null && canAuditState().contains(editData.getState().getValue())) {
					actionSave.setVisible(false);
					actionSave.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				} else {
					actionSave.setVisible(isEdit);
					actionSave.setEnabled(isEdit);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				}
				// 版本对比
				btnHisVerion.setVisible(hasHisVer);
				btnHisVerion.setEnabled(hasHisVer);
				actionCompareVer.setVisible(hasHisVer);
				actionCompareVer.setEnabled(hasHisVer);
			}
		}
	}

	/**
	 * 设置甘特图功能
	 * 
	 * @param isEdit
	 */
	protected void initGanttActions(boolean isEdit) {
		actionLocate.setEnabled(true);
		getScheduleGanttProject().isOnlyViewer = !isEdit;
		actionBatchModifyTaskType.setEnabled(isEdit);
	}

	/**
	 * 导入导出action设置状态
	 * 
	 * @param isTotalUI
	 * @param isEdit
	 * @param isFromExUI
	 */
	protected void initImportExportAction(boolean isTotalUI, boolean isFromExUI, boolean isEdit) {
		if (isTotalUI) {
			actionExportPlanTemplate.setVisible(false);
			actionExportPlanTemplate.setEnabled(false);
			actionExportProject.setVisible(false);
			actionExportProject.setEnabled(false);
			actionImportPlanTemplate.setVisible(false);
			actionImportPlanTemplate.setEnabled(false);
			actionImportProject.setVisible(false);
			actionImportProject.setEnabled(false);
		} else {
			actionExportPlanTemplate.setVisible(!isFromExUI);
			actionExportPlanTemplate.setEnabled(!isFromExUI);
			actionExportProject.setVisible(!isFromExUI);
			actionExportProject.setEnabled(!isFromExUI);

			boolean canImport = !isFromExUI && isEdit;
			actionImportPlanTemplate.setVisible(canImport);
			actionImportPlanTemplate.setEnabled(canImport);
			actionImportProject.setVisible(canImport);
			actionImportProject.setEnabled(canImport);
		}
	}


	public boolean haveHistory(float version) {
		return version > 1f;
	}

	public boolean haveTaskEntrys(FDCScheduleInfo info) {
		return null != info && null != info.getTaskEntrys() && info.getTaskEntrys().size() > 0;
	}


	/**
	 * 直接使用EditUI的实现
	 * 
	 * @throws Exception
	 * @return IObjectPK
	 */
	public IObjectPK runSave() throws Exception {
		// 直接使用保存，不考虑BOTP相关的东西
		if (UtilRequest.isPrepare("ActionSave", this)) {// TODO 此处以后要修改
			Object pk = ActionCache.get(ResponseConstant.FRAMEWORK_PK);
			if (pk != null) {
				return (IObjectPK) pk;
			}
		}
		return getBizInterface().save(getSaveSchedule());
	}

	/**
	 * 直接使用EditUI的实现
	 * 
	 * @throws Exception
	 * @return IObjectPK
	 */
	public IObjectPK runSubmit() throws Exception {
		if (getBizInterface() == null) {
			throw new Exception("don't implement getBizInterface()  !");
		}
		if (UtilRequest.isPrepare("ActionSubmit", this)) {// TODO 此处以后要修改
			Object pk = ActionCache.get(ResponseConstant.FRAMEWORK_PK);
			if (pk != null) {
				return (IObjectPK) pk;
			}
		}
		return getBizInterface().submit(getSaveSchedule());
	}

	protected void initCtrlListner() {

	}

	/** 检查当前组织是否是实体成本中心 */
	protected void beforeOnload() {
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showError("当前组织不是成本中心组织，不能进入！");
			abort();
		}

		// 项目日历缓存在每次打开时清空，否则修改日历无作用
		ActivityCache cache = ActivityCache.getInstance();
		cache.clearScheduleCalendar();

		if (getColumnsProperty() == null) {
			try {
				columnsProperty = ScheduleTaskPropertyHelper.getAllColumns(ScheduleClientHelper.getUIMark(this));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	/** 由于本页面代码太多复杂,所以对于获得保存数据重新建了 'FDCScheduleBaseHelper' modify by duhongming **/
	protected FDCScheduleInfo getSaveSchedule() {
		editData.setProject((CurProjectInfo) prmtCurproject.getValue());
		return new FDCScheduleBaseHelper(editData, this).getSaveSchedule();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		// if (cbState != null || cbState.getSelectedItem() == null ||
		// cbState.getSelectedItem() == ScheduleStateEnum.SAVED) {
		// this.verifyInputForSave();
		// } else {
		// this.verifyInputForSubmit();
		// }
	}

	protected void verifyInputForSave() throws Exception {
		if (!haveTaskEntrys(editData)) {
			FDCMsgBox.showWarning(this, "没有任务记录，不允许保存。");
			abort();
		}
	}

	protected void verifyInputForSubmit() throws Exception {
		if (!haveTaskEntrys(editData)) {
			FDCMsgBox.showWarning(this, "没有任务记录，不允许提交。");
			abort();
		}
		int size = editData.getTaskEntrys().size();
		FDCScheduleTaskInfo taskInfo = null;
		StringBuffer str = new StringBuffer("行号\t任务名称\t错误类型\t\t\n");
		int err = 0;
		if (editData.getProjectSpecial() == null) {
			for (int i = 0; i < size; i++) {
				taskInfo = editData.getTaskEntrys().get(i);
				if (null == taskInfo.getBizType() || taskInfo.getBizType().isEmpty()) {
					continue;
				}
				for (int j = 0; j < taskInfo.getBizType().size(); j++) {
					ScheduleBizTypeInfo taskBizType = taskInfo.getBizType().get(j).getBizType();
					if (taskBizType.getId().toString().equals("Rz+dS7ECSfqM4kEJqGawYWLF6cA=") && taskInfo.getAchievementType() == null) {
						err++;
						str.append(i + 1 + "\t" + taskInfo.getName() + "\t成果类别为空\n");
					}

				}
				
			}
			if (err > 0) {
				FDCMsgBox.showDetailAndOK(this, "提交时发生错误，请查看详细信息！", str.toString(), MsgType.UPDATE_VALUE);
				abort();
			}
		}
		
		// 强管控
		// 专项任务的计划完成日期大于相关主项任务的计划完成日期
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("valueAlias");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", new String("主项计划与专项计划控制策略")));
		view.setFilter(filter);
		ParamCollection paramCollection = ParamFactory.getRemoteInstance().getParamCollection(view);
		if (paramCollection != null && paramCollection.size() > 0) {
			ParamInfo paramInfo = paramCollection.get(0);
			if (paramInfo.getValueAlias().equals("强管控")) {
				FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
				for (int i = 0; i < taskEntrys.size(); i++) {
					FDCScheduleTaskInfo spcailTask = taskEntrys.get(i);
					FDCScheduleTaskInfo mainTask = spcailTask.getDependMainTaskID();
					if (mainTask != null) {
						Date mainEnd = mainTask.getEnd();
						Date specailEnd = spcailTask.getEnd();
						if (specailEnd.after(mainEnd)) {
							FDCMsgBox.showWarning(this, spcailTask.getName() + " 专项任务的计划完成日期大于相关主项任务的计划完成日期！");
							SysUtil.abort();
						}

					}
				}
			}
		}

		FDCClientVerifyHelper.verifyRequire(this);
	}

	private boolean isSaveAction = false;

	public boolean isSaveAction() {
		return isSaveAction;
	}

	public void setSaveAction(boolean isSaveAction) {
		this.isSaveAction = isSaveAction;
	}

	private void importVerify(String taskType) {
		// if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(taskType)){
		// if(FDCMsgBox.CANCEL == FDCMsgBox.showConfirm2(this,
		// "导入主项任务将删除当前工程项目下的所有主项任务，并且不能撤销，您是否确认导入？")){
		// SysUtil.abort();
		// }
		// }
		if (TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(taskType)) {
			ScheduleGanttProject ganttProject = this.getScheduleGanttProject();
			DefaultMutableTreeNode[] selectedNodes = ganttProject.getTree().getSelectedNodes();
			boolean isSelectedOne = selectedNodes != null && selectedNodes.length == 1 && !selectedNodes[0].equals(ganttProject.getTree().getRoot());
			if (!isSelectedOne || !TaskTypeInfo.TASKTYPE_MAINTASK.equals(getSelectedTask().getWbs().getTaskType().getId().toString())) {
				FDCMsgBox.showWarning(this, "您需要选择一条主项任务后才可导入专项任务");
				SysUtil.abort();
			}
		}
	}

	private FDCScheduleTaskInfo getSelectedTask() {
		DefaultMutableTreeNode selectedTreeNode = getScheduleGanttProject().getSelectedNode();
		if (selectedTreeNode != null) {
			return (FDCScheduleTaskInfo) ((KDTask) selectedTreeNode.getUserObject()).getScheduleTaskInfo();
		}
		return null;
	}

	public void importProject(String taskType) {
		importVerify(taskType);
		KDFileChooser chooser = new KDFileChooser(new File(preProjectFileName));
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".mpp");
			}

			public String getDescription() {
				return "Project文件(*.mpp)";
			}
		});
		if (chooser.showOpenDialog(this) == KDFileChooser.APPROVE_OPTION) {
			StopWatch sw = new StopWatch();
			sw.start();
			File file = chooser.getSelectedFile();
			preProjectFileName = file.getParent();
			try {
				FDCWBSTree wbsTree = getScheduleGanttProject().getWbsTree();
				KDProjectFileReader reader = new KDProjectFileReader(editData, file, getSelectedTask(), wbsTree);
				if (reader.isEmptyTask()) {
					FDCMsgBox.showWarning(this, "没有可供导入的任务");
					SysUtil.abort();
				}
				if (reader.existEmptyName()) {
					FDCMsgBox.showWarning(this, "存在名称为空的任务， 请添加任务名称后再导入");
					SysUtil.abort();
				}
				boolean isRemoveRoot = false;
				if (TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(taskType)) {
					FDCScheduleTaskInfo selectedTask = getSelectedTask();
					if (!reader.verifyDateRange(selectedTask.getStart(), selectedTask.getEnd())) {
						FDCMsgBox.showWarning(this, "导入的开始时间/结束时间超过了可以导入的范围");
						SysUtil.abort();
					}
					String onlyRootName = reader.getOnlyRootName();
					if (onlyRootName != null) {
						int confirm = FDCMsgBox.showConfirm3(this, "存在单独的【" + onlyRootName + "】根任务，" + "根任务是否导入");
						if (confirm == FDCMsgBox.NO) {
							isRemoveRoot = true;
						} else if (confirm == FDCMsgBox.CANCEL) {
							return;
						}
					}
				}

				logger.info("Prepare import Time: " + sw.getLastTime());
				reader.getFDCScheduleTask(isRemoveRoot);
				reader = null;
				logger.info("Parse import Time: " + sw.getLastTime());
				this.editData.setEditable(true);
				setDataObject(this.editData);
				logger.info("setDate import Time: " + sw.getLastTime());
				load2Gantt(this.editData);
				logger.info("Load2Gantt import Time: " + sw.getLastTime());
				this.setInitEnd(false);
				setMessageText("正在初始化数据...");
				showMessage();
				weakOnShow();
				getScheduleGanttProject().setOprtState(this.getOprtState());
			} catch (MPXJException e) {
				logger.error(e.getMessage(), e);
				FDCMsgBox.showError(this, "解析文件" + file.getPath() + "失败，请选择正确的Project文件");
			} catch (BOSException e) {
				handleException(e);
			} catch (Exception e) {
				handleException(e);
			}
		}

	}

	/**
	 * @discription <导入project>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/22>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @see <相关的类>
	 */
	public List importProjectNew(String taskType, KDFileChooser chooser) {
		List resultList = new ArrayList();
		importVerify(taskType);
		StopWatch sw = new StopWatch();
		sw.start();
		File file = chooser.getSelectedFile();
		preProjectFileName = file.getParent();
		try {
			FDCWBSTree wbsTree = getScheduleGanttProject().getWbsTree();
			KDProjectFileReader reader = new KDProjectFileReader(editData, file, getSelectedTask(), wbsTree);
			if (reader.isEmptyTask()) {
				FDCMsgBox.showWarning(this, "没有可供导入的任务");
				SysUtil.abort();
			}
			if (reader.existEmptyName()) {
				FDCMsgBox.showWarning(this, "存在名称为空的任务， 请添加任务名称后再导入");
				SysUtil.abort();
			}
			boolean isRemoveRoot = false;
			if (TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(taskType)) {
				FDCScheduleTaskInfo selectedTask = getSelectedTask();
				if (!reader.verifyDateRange(selectedTask.getStart(), selectedTask.getEnd())) {
					FDCMsgBox.showWarning(this, "导入的开始时间/结束时间超过了可以导入的范围");
					SysUtil.abort();
				}
				String onlyRootName = reader.getOnlyRootName();
				if (onlyRootName != null) {
					int confirm = FDCMsgBox.showConfirm3(this, "存在单独的【" + onlyRootName + "】根任务，" + "根任务是否导入");
					if (confirm == FDCMsgBox.NO) {
						isRemoveRoot = true;
					} else if (confirm == FDCMsgBox.CANCEL) {
						return resultList;
					}
				}
			}

			logger.info("Prepare import Time: " + sw.getLastTime());
			FDCScheduleTaskCollection fDCScheduleTaskCollection = reader.getFDCScheduleTask(isRemoveRoot);
			resultList.add(reader.isPersonNulls);
			resultList.add(reader.isDeptNulls);
			resultList.add(reader.isPersonRepeats);
			resultList.add(reader.isDeptRepeats);
			reader = null;
			logger.info("Parse import Time: " + sw.getLastTime());
			this.editData.setEditable(true);
			setDataObject(this.editData);
			logger.info("setDate import Time: " + sw.getLastTime());
			load2Gantt(this.editData);
			logger.info("Load2Gantt import Time: " + sw.getLastTime());
			this.setInitEnd(false);
			setMessageText("正在初始化数据...");
			showMessage();
			weakOnShow();
			getScheduleGanttProject().setOprtState(this.getOprtState());
			setMessageText("成功导入" + fDCScheduleTaskCollection.size() + "条数据！");
			showMessage();

		} catch (MPXJException e) {
			logger.error(e.getMessage(), e);
			FDCMsgBox.showError(this, "解析文件" + file.getPath() + "失败，请选择正确的Project文件");
		} catch (BOSException e) {
			handleException(e);
		} catch (Exception e) {
			handleException(e);
		}
		return resultList;
	}

	/**
	 * @discription <保存责任人和责任部门>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/30>
	 *             <p>
	 * @param <空>
	 * @return <返回值描述>
	 * 
	 *         modifier <空>
	 *         <p>
	 *         modifyDate <空>
	 *         <p>
	 *         modifyInfo <空>
	 *         <p>
	 * @throws Exception
	 * @see <相关的类>
	 */
	public void saveDepAndPerson(FDCScheduleTaskCollection fDCScheduleTaskCollection) throws Exception {
		FDCScheduleTaskCollection scheduleTaskCollection = this.editData.getTaskEntrys();
		for (int k = 0; k < scheduleTaskCollection.size(); k++) {
			FDCScheduleTaskInfo scheduleTaskInfo = scheduleTaskCollection.get(k);
			for (int j = 0; j < fDCScheduleTaskCollection.size(); j++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(j);
				if (scheduleTaskInfo.getId().toString().equals(fDCScheduleTaskInfo.getId().toString())) {
					scheduleTaskInfo.setAdminPerson(fDCScheduleTaskInfo.getAdminPerson());
					scheduleTaskInfo.setAdminDept(fDCScheduleTaskInfo.getAdminDept());
				}
			}
		}

		/* 重新加载数据 */
		setDataObject(this.editData);
		load2Gantt(this.editData);
		this.setInitEnd(false);
		setMessageText("正在初始化数据...");
		showMessage();
		weakOnShow();
	}

	private FDCScheduleTaskCollection getSelectedTasks4Export() {
		FDCScheduleTaskCollection tasks = new FDCScheduleTaskCollection();
		DefaultMutableTreeNode[] selectedNodes = getScheduleGanttProject().getTree().getSelectedNodes();
		if (selectedNodes != null) {
			for (int i = 0; i < selectedNodes.length; ++i) {
				Object userObject = selectedNodes[i].getUserObject();
				if (userObject != null && userObject instanceof KDTask) {
					tasks.add((FDCScheduleTaskInfo) ((KDTask) userObject).getScheduleTaskInfo());
				}
			}
		}
		Set parentTasks = new HashSet();
		for (int i = 0; i < tasks.size(); ++i) {
			FDCScheduleTaskInfo parent = tasks.get(i).getParent();
			if (parent != null) {
				parentTasks.add(parent.getId().toString());
			} else {
				parentTasks.add(null);
			}
		}
		if (parentTasks.size() > 1) {
			FDCMsgBox.showWarning(this, "您选择的节点不是兄弟节点， 不能导出");
			SysUtil.abort();
		}
		return tasks;
	}

	public void actionExportProject_actionPerformed(ActionEvent e) throws Exception {
		beforeCheck();
		FDCScheduleTaskCollection selectedTasks = getSelectedTasks4Export();
		if ((selectedTasks == null || selectedTasks.isEmpty()) && (editData.getTaskEntrys() == null || editData.getTaskEntrys().isEmpty())) {
			FDCMsgBox.showWarning(this, "没有可供导出的任务");
			return;
		}
		KDFileChooser chooser = new KDFileChooser(new File(preProjectFileName));
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
			}

			public String getDescription() {
				return "XML 格式(*.xml)";
			}
		});
		String defaultName = editData.getName();
		if (FDCHelper.isEmpty(defaultName)) {
			defaultName = editData.getProject().getName();
		}
		chooser.setSelectedFile(new File(preProjectFileName + defaultName + ".xml"));
		if (chooser.showSaveDialog(this) == KDFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			KDProjectWriter projectWriter = new KDProjectWriter(this.editData.getTaskEntrys(), null);
			projectWriter.write(file);
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyInputForSave();
		logger.info("actionSave_actionPerformed begin...");
		if (FDCHelper.isEmpty(prmtCurproject.getValue())) {
			FDCMsgBox.showError(this, "保存失败,工程项目不能为空！");
			SysUtil.abort();
		}
		try {
			this.setSaveAction(true);
			editData.setEffectDegree(ganttProject.getTaskEffectDegree());
    		super.actionSave_actionPerformed(e);
			cbState.setSelectedItem(ScheduleStateEnum.SAVED);
			editData.setState(ScheduleStateEnum.SAVED);
			txtVersion.setText(editData.getVersionName());
		} catch (EASBizException ex) {
			throw ex;
		} catch (BOSException ex) {
			throw ex;
		} catch (AbortException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getCause(), ex);
			FDCMsgBox.showError("保存失败，Project搭接时间有误");
		}
		logger.info("actionSave_actionPerformed end...");
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInputForSubmit();
		logger.info("actionSubmit_actionPerformed begin...");
		try {
			btnAudit.setVisible(true);
			btnAudit.setEnabled(true);
			this.setSaveAction(false);
			editData.setState(ScheduleStateEnum.SUBMITTED);
			cbState.setSelectedItem(ScheduleStateEnum.SUBMITTED);
			if(!isFromExecuteUI() && isMainSchedule()){				
				verifyIncludeGlobalTask(editData.getTaskEntrys());
			}
			editData.setEffectDegree(ganttProject.getTaskEffectDegree());
			super.actionSubmit_actionPerformed(e);
			this.actionSave.setEnabled(false);
			setOprtState(oprtState);
		} catch (EASBizException ex) {
			throw ex;
		} catch (BOSException ex) {
			throw ex;
		} catch (AbortException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error(ex.getCause(), ex);
			FDCMsgBox.showError("保存失败，Project搭接时间有误");
		}
		logger.info("actionSubmit_actionPerformed end...");
	}

	/**
	 * @description 审批
	 * @author 杜红明
	 * @createDate 2011-9-19
	 * @version EAS7.0
	 * @see
	 */

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (!editData.getState().equals(ScheduleStateEnum.SUBMITTED)) {
			FDCMsgBox.showError("当前单据状态为" + editData.getState() + ",不能执行此操作！");
			abort();
		}
		cbState.setSelectedItem(ScheduleStateEnum.AUDITTED);
		editData.setState(ScheduleStateEnum.AUDITTED);
		/** 审批前 执行一次保存 **/
		runSave();
		FDCScheduleFactory.getRemoteInstance().audit(editData.getId());
		FDCMsgBox.showInfo(this, "操作成功!");
		setOprtState(STATUS_VIEW);
		ganttProject.setOprtState(STATUS_VIEW);
	}

	public void actionSaveNewTask_actionPerformed(ActionEvent e) throws Exception {
		super.actionSaveNewTask_actionPerformed(e);
		FDCScheduleFactory.getRemoteInstance().saveNewTask(getNewTaskScheduleInfo());
	}

	public FDCScheduleInfo getNewTaskScheduleInfo() {
		FDCScheduleInfo info = new FDCScheduleInfo();
		info.setId(this.editData.getId());
		info.setProject(this.editData.getProject());
		info.setCalendar(this.editData.getCalendar());
		for (Iterator iter = this.editData.getTaskEntrys().iterator(); iter.hasNext();) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) iter.next();
			if (task.isAdd()) {
				// verifyTaskRequired(task);
				info.getTaskEntrys().add(task);
			}
		}
		return info;
	}

	protected UIContext getPropertyUIContext() {
		UIContext uiContext = super.getPropertyUIContext();
		// 属性的状态默认为单据状态
		uiContext.put("OprtState", getOprtState());

		if (getOprtState() != null && getOprtState().equals(OprtState.VIEW)) {
			List selectedTasks = this.getScheduleGanttProject().getTaskSelectionContext().getSelectedTasks();
			if (selectedTasks.size() > 0) {
				KDTask task = (KDTask) selectedTasks.get(0);
				if (task.isEditable()) {
					uiContext.put("OprtState", OprtState.EDIT);
				}
			}
		}

		// 编辑状态下不可编辑的节点也不让修改属性
		if (getOprtState() != null && getOprtState().equals(OprtState.EDIT)) {
			List selectedTasks = this.getScheduleGanttProject().getTaskSelectionContext().getSelectedTasks();
			if (selectedTasks.size() > 0) {
				KDTask task = (KDTask) selectedTasks.get(0);
				if (!task.isEditable()) {
					uiContext.put("OprtState", OprtState.VIEW);
				}
			}
		}
		uiContext.put("isScheduleExec", getUIContext().get("isScheduleExec"));
		return uiContext;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		/* modified by zhaoqin for R20150105-0160 on 2015/01/15 */
		if(null == prmtCurproject.getValue()) {
			FDCMsgBox.showWarning("工程项目不能为空,请先选择工程项目!");
			abort();
		}
		
		if (this.editData != null && this.editData.getState() == ScheduleStateEnum.EXETING ) {
			FDCMsgBox.showInfo(this, "单据已执行,点击此按钮后只能录入执行信息");
			getUIContext().put("isScheduleExec", Boolean.TRUE);
			this.actionEdit.setVisible(true);
			this.actionEdit.setEnabled(false);
		} else if(this.editData != null && this.editData.getState() == ScheduleStateEnum.AUDITTING ){
			FDCMsgBox.showInfo(this, "单据已提交工作流，不能进行编制!");
			abort();
		}else{
			super.actionEdit_actionPerformed(e);
			this.setSaveAction(true);
			/*
			 * Added by Owen_wen 2013-12-16
			 * 猜想调用super.actionSave_actionPerformed(e)应该有两个作用：
			 *  1. 这里改第一次编辑，直接关闭不提示的bug
			 *  2. 在doAfterSave里会置editData.setEditable(true), 目的是让界面的控件可编辑
			 */
			super.actionSave_actionPerformed(e);
			editData.setState(ScheduleStateEnum.SAVED);
			cbState.setSelectedItem(ScheduleStateEnum.SAVED);
			txtVersion.setText(editData.getVersionName()); 
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("name");
		sic.add("number");
		sic.add("id");
		sic.add("*");
		sic.add("taskEntrys.dependMainTaskID.id");
		sic.add("taskEntrys.dependMainTaskID.name");
		sic.add("taskEntrys.dependMainTaskID.number");
		return sic;
	}

	/** 房地产进度计划 **/
	public FDCScheduleInfo scheduleInfo;
	/** 用于存放所有任务 **/
	private Map tasksMap;

	/**
	 * @description 得到所有任务
	 * @author 车忠伟
	 * @createDate 2011-9-9
	 * @version EAS7.0
	 * @see
	 */
	public void getAllTask() throws BOSException {
		tasksMap = new HashMap();
		FDCScheduleTaskCollection fDCScheduleTaskCollection = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection();
		for (int k = 0; k < fDCScheduleTaskCollection.size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(k);
			tasksMap.put(fDCScheduleTaskInfo.getId().toString(), fDCScheduleTaskInfo);
		}
	}

	/***
	 * 批量修改责任部门
	 * 
	 * isWbsOrSchedule=True:Wbs批量修改责任部门 False:计划编制（主项和专项）批量修改责任部门
	 * 
	 * add by warship at 2010/08/05
	 */
	public void actionBatChangeRespDept_actionPerformed(ActionEvent e) throws Exception {
        String oState = getOprtState();
        /* 现场可能是做做计划，审批后再确认责任人和责任部门。现场都未提这Bug，建议暂不处理。Added by Owen_wen 2013.11.1
		if (!(ScheduleStateEnum.SAVED.equals(editData.getState()) || ScheduleStateEnum.SUBMITTED.equals(editData.getState()))) {
			FDCMsgBox.showInfo(this, "只有保存或提交状态的计划，才能批量修改责任人。");
			SysUtil.abort();
		}*/
		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning("请选择工程项目");
			SysUtil.abort();
		}
		if (editData == null || editData.getTaskEntrys() == null || editData.getTaskEntrys().size() <= 0) {
			FDCMsgBox.showWarning("没有可选择的任务");
			SysUtil.abort();
		}
		this.scheduleInfo = editData;

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(BatchModifyResPersonUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		oprtState = oState;
		changePageState();
	}

	/**
	 * 
	 * @description 工程项目改变.重新加载数据
	 * @author 杜红明
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	protected void prmtCurproject_dataChanged(DataChangeEvent e) throws Exception {
		// TODO 在编辑状态 切换工程项目 应该给予提示
		Object newValue = e.getNewValue();
		Object oldValue = e.getOldValue();
//		if ((newValue == null && oldValue == null) || (newValue != null && newValue.equals(oldValue))) {
//			return;
//		}

		Map param = new HashMap();
		param.put("isFormExecuteUI", Boolean.valueOf(isFromExecuteUI()));
		if (oprtState == STATUS_EDIT && editData != null) {
			int showConfirm2 = FDCMsgBox.showConfirm2(this, "数据未保存，是否保存?");
			if (showConfirm2 == 0) {
				/* modified by zhaoqin for R140325-0293 on 2014/04/29 start */
				// 先把工程项目设置为旧的，保存后再设置回来，不触发监听
				//if(e.getOldValue() != null)
				//prmtCurproject.setDataNoNotify(e.getOldValue());
				prmtCurproject.setDataNoNotify(oldValue);
				if (ScheduleStateEnum.SUBMITTED.equals(editData.getState())) {
					actionSubmit_actionPerformed(null);
				}
				if (ScheduleStateEnum.SAVED.equals(editData.getState())) {
					actionSave_actionPerformed(null);
				}
				//prmtCurproject.setDataNoNotify(e.getNewValue());
				prmtCurproject.setDataNoNotify(newValue);
			}
			setOprtState(STATUS_VIEW);
			/* modified by zhaoqin for R140325-0293 on 2014/04/29 end */
		}
		CurProjectInfo curProject = null;
		if (prmtCurproject.getValue() instanceof CurProjectInfo)

			curProject = (CurProjectInfo) prmtCurproject.getValue();
		if (curProject == null) {
			editData = null;
			getScheduleGanttProject().close();
			return;
		}
		ProjectSpecialInfo projectSpecial = null;
		if (this instanceof SpecialScheduleEditUI) {
			projectSpecial = (ProjectSpecialInfo) ((SpecialScheduleEditUI) this).prmtPrjSpecial.getValue();
		}
		// if (editData != null) {
		// projectSpecial = editData.getProjectSpecial();
		// }

		editData = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(isMainSchedule(), false, curProject, projectSpecial, param);
		editData.setProject(curProject);
		loadData2Gantt(editData);
		this.setUserObject(editData);
		setOprtState(STATUS_VIEW);
		if (editData.getProject() != null && editData.getTaskEntrys() != null && editData.getTaskEntrys().size() > 0) {
			String versionName = editData.getVersionName();
			if (FDCHelper.isEmpty(versionName)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				if (projectSpecial != null) {
					txtVersion.setText(editData.getProject().getName() + projectSpecial + sdf.format(new Date()) + "_" + new Float(editData.getVersion()) + "版");
				} else {
					txtVersion.setText(editData.getProject().getName() + sdf.format(new Date()) + "_" + new Float(editData.getVersion()) + "版");
				}
			} else {
				txtVersion.setText(versionName);
			}
			// if (editData.getVersion() > 1f) {
			// btnHisVerion.setEnabled(true);
			// btnHisVerion.setVisible(true);
			// } else {
			// btnHisVerion.setVisible(false);
			// btnHisVerion.setEnabled(false);
			// }
		}
	}

	protected void loadData2Gantt(FDCScheduleInfo editData) throws Exception {
		// StopWatch sw = new StopWatch();
		// sw.start();
		// logger.info("Prepare import Time: " + sw.getLastTime());
		// logger.info("Parse import Time: " + sw.getLastTime());
		setDataObject(editData);
		// logger.info("setDate import Time: " + sw.getLastTime());
		load2Gantt(editData);
		// logger.info("Load2Gantt import Time: " + sw.getLastTime());
		this.setInitEnd(false);
		setMessageText("正在初始化数据...");
		showMessage();
		weakOnShow();
		if (haveTaskEntrys(editData)) {
			txtVersion.setText(editData.getVersionName());
			cbState.setSelectedItem(editData.getState());
		} else {
			txtVersion.setText(null);
			cbState.setSelectedItem(null);
		}
		setUserObject(editData);
		centerAndShowAll();
	}
	
	public boolean isFromWf() {
		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description 当界面关闭时,记忆当前项目
	 * @author 杜红明
	 * @createDate 2011-8-24
	 * @version EAS7.0
	 * @see
	 */

	public boolean destroyWindow() {
		
		
		if (isNeedRemeber && !isFromWf()) {
			try {
				IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
				UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
				OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
				CurProjectInfo curProject = ((CurProjectInfo) prmtCurproject.getValue());
				Object[] objs = new Object[] { user, orgUnit, curProject };
				if (exits(objs)) {
					String userID = user.getId().toString();
					String orgUnitID = orgUnit.getId().toString();
					String curProjectID = curProject.getId().toString();
					if (isMainSchedule()) {
						autoRemember.save(userID, orgUnitID, "MainScheduleProject", curProjectID);
					} else {
						if (editData != null && editData.getProjectSpecial() != null) {
							String projectSpecialId = editData.getProjectSpecial().getId().toString();
							autoRemember.save(userID, orgUnitID, "SpecialScheduleProject", curProjectID + "!" + projectSpecialId);
						} else {
							autoRemember.save(userID, orgUnitID, "SpecialScheduleProject", curProjectID);
						}

						// if (editData != null && editData.getProjectSpecial()
						// != null) {
						// autoRemember.save(userID, orgUnitID,
						// "SpecialSheduleProjectSpecial",
						// editData.getProjectSpecial().getId().toString());
						// }
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}
		}
		return super.destroyWindow();
	}

	/**
	 * @description 数组中的值是否存在
	 * @author 杜红明
	 * @createDate 2011-8-25 void
	 * @version EAS7.0
	 * @see
	 */

	private boolean exits(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (FDCHelper.isEmpty(objs[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @description 调整
	 * @author 杜红明
	 * @createDate 2011-8-26
	 * @version EAS7.0
	 * @see
	 */

	public void actionAdjust_actionPerformed(ActionEvent e) throws Exception {
		// if(editData.getVersion()<getMaxVersion(editData)){
		// FDCMsgBox.showError("只能用最新版本来进行调整。");
		// abort();
		// }
		if (editData == null) {
			FDCMsgBox.showError("任务加载错误。");
			abort();
		}
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = null;
		Map uiContext = getUIContext();
		uiContext.put("editUI", this);
		uiContext.put("editData", editData);
		uiWindow = uiFactory.create(SavePlanVersionUI.class.getName(), uiContext, null, oprtState);
		uiWindow.show();
		if (((SavePlanVersionUI) uiWindow.getUIObject()).isSave) {
			this.txtVersion.setText(editData.getVersionName());
			cbState.setSelectedItem(editData.getState());
			oprtState = STATUS_EDIT;
			loadData2Gantt(editData);
			this.editData.setId(BOSUuid.read(runSave().toString()));
			
			/* modified by zhaoqin for BT829954 on 2014/06/09 */
			this.editData.setIsCheckVersion(false);
		}
	}

	/**
	 * @description 还原
	 * @author 杜红明
	 * @createDate 2011-8-26
	 * @version EAS7.0
	 * @see 实现方式改为，如果当前版为1.0或者新增状态，直接清空分录，如果为后续版本，则还原为上一版本的数据。
	 */
	public void actionRestore_actionPerformed(ActionEvent e) throws Exception {
		int showConfirm2 = FDCMsgBox.showConfirm2(this, "计划将还原至上一版本数据，当前数据将丢失，您是否确定还原");
		if (showConfirm2 == 0 && editData != null && editData.getId() != null) {
			FDCScheduleInfo otherInfo = null;
			if (editData.getVersion() == 1) {
				// int cfm = FDCMsgBox.showConfirm2(this,
				// "当前计划不存在已审批的历史版本，是否清空数据？");
				// if (cfm == FDCMsgBox.OK) {
				if (prmtCurproject.getValue() == null) {
					FDCMsgBox.showWarning(this, "工程项目为空!");
					return;
				}
				getUIContext().put("CurProject", prmtCurproject.getValue());
				editData.getTaskEntrys().clear();
				// ganttProject.close();
				// }
				// return;
				// FDCMsgBox.showWarning(this, "当前计划不存在已审批的历史版本，无法还原");
				// return;
			} else {
				String scheduleid = null;
				CurProjectInfo curProject = editData.getProject();
				ProjectSpecialInfo projectSpecial = editData.getProjectSpecial();
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select fid from t_sch_fdcschedule where fprojectid = ?  and fislatestver = 1");
				if (projectSpecial != null) {
					builder.appendSql(" and fprojectspecialid is not null");
				} else {
					builder.appendSql(" and fprojectspecialid is null or FProjectSpecialID = ' ' ");
				}
				builder.addParam(editData.getProject().getId().toString());
				IRowSet rs = builder.executeQuery();
				while (rs.next()) {
					scheduleid = rs.getString(1);
				}
				
				
				
// try {
				// // FDCScheduleFactory.getRemoteInstance().delete(
				// // new ObjectStringPK(editData.getId().toString()));
				// // 暂时这么修改 by duhongming
				// if (editData.getVersion() > 1f) {
				// FDCSQLBuilder builder = new FDCSQLBuilder();
				// builder.appendSql(
				// "update T_SCH_FDCSchedule set FIsLatestVer=1 where ");
				// if (editData.getProjectSpecial() == null) {
				// builder.appendSql(
				// "(FProjectSpecialID is null or FProjectSpecialID = '') and "
				// );
				// } else {
				// builder.appendParam("FProjectSpecialID",
				// editData.getProjectSpecial().getId().toString());
				// builder.appendSql(" and ");
				// }
				// builder.appendSql("  FProjectID=? and fversion=?");
				// builder.addParam(curProject.getId().toString());
				// builder.addParam(new Integer(new
				// Float(editData.getVersion()).intValue() - 1));
				// builder.executeUpdate();
				// }
				// } catch (Exception e1) {
				// FDCMsgBox.showWarning(this, "不能还原尚未保存的数据！");
				// SysUtil.abort();
				// }
				// 3. 调用getScheduleInfo计划数据获取方法（isAdjust参数为true）,并返回数据
				FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(new ObjectUuidPK(BOSUuid.read(scheduleid)));
				// info.setId(editData.getId());
				// BOSObjectType type = new FDCScheduleTaskInfo().getBOSType();
				// // for (int i = 0; i < info.getTaskEntrys().size(); i++) {
				// // info.getTaskEntrys().get(i).setId(BOSUuid.create(type));
				// // }
				// info.setVersion(editData.getVersion());
				// info.setVersionName(editData.getVersionName());
				// info.setState(editData.getState());
				// editData = info;
				
				
				FDCScheduleTaskCollection tasks = (FDCScheduleTaskCollection) info.getTaskEntrys().clone();
				
				Map temp = new HashMap();
				ScheduleTaskBizTypeInfo bizType = null;
				for (int i = 0; i < tasks.size(); i++) {
					FDCScheduleTaskInfo scheduleTaskBaseInfo = (FDCScheduleTaskInfo) tasks.get(i);
					scheduleTaskBaseInfo.setSchedule(editData);
					String oldID = scheduleTaskBaseInfo.getSrcID();
					BOSUuid newID = BOSUuid.create(scheduleTaskBaseInfo.getBOSType());
					scheduleTaskBaseInfo.setId(newID);
					temp.put(oldID, scheduleTaskBaseInfo);
					// temp.put(newID.toString(), oldID);
					scheduleTaskBaseInfo.put("myOldStartDate", scheduleTaskBaseInfo.getStart());
					scheduleTaskBaseInfo.put("myOldEndDate", scheduleTaskBaseInfo.getEnd());
//					scheduleTaskBaseInfo.put("myOldCheckDate", ((FDCScheduleTaskInfo) scheduleTaskBaseInfo).getCheckDate());
//					scheduleTaskBaseInfo.put("myOldCheckDate", ((FDCScheduleTaskInfo) scheduleTaskBaseInfo).getCheckDate());

					if(((FDCScheduleTaskInfo) scheduleTaskBaseInfo).isIsCheckNode()){						
						scheduleTaskBaseInfo.put("myOldCheckDate", ((FDCScheduleTaskInfo) scheduleTaskBaseInfo).getCheckDate());
					}else{
						scheduleTaskBaseInfo.put("myOldCheckDate", null);
					}

				}

				for (int i = 0; i < tasks.size(); i++) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) tasks.get(i);

					ScheduleTaskBizTypeCollection bizTypeCollection = task.getBizType();
					ScheduleTaskBizTypeCollection newBizTypeCollection = new ScheduleTaskBizTypeCollection();
					for (Iterator it = bizTypeCollection.iterator(); it.hasNext();) {
						bizType = (ScheduleTaskBizTypeInfo) it.next();
						bizType.setId(BOSUuid.create(bizType.getBOSType()));
						newBizTypeCollection.add(bizType);
					}

					task.getBizType().clear();
					task.getBizType().addCollection(newBizTypeCollection);

					if (null != task.getParent() && temp.containsKey(task.getParent().getSrcID().toString())) {
						task.setParent((FDCScheduleTaskInfo) temp.get(task.getParent().getSrcID()));
					}

					FDCScheduleTaskDependCollection depends = task.getDependEntrys();
					if (depends == null || depends.size() < 1) {
						continue;
					}

					for (int j = 0; j < depends.size(); j++) {
						depends.get(j).setId(null);
						FDCScheduleTaskDependInfo depend = depends.get(j);
						String taskID = ((FDCScheduleTaskInfo) depend.getTaskBase()).getSrcID();
						String dependTaskId = ((FDCScheduleTaskInfo) depend.getDependTaskBase()).getSrcID();
						if (temp.containsKey(taskID)) {
							depend.put("task", temp.get(taskID));
							depend.put("dependTask", temp.get(dependTaskId));
						}
					}
				}
				
				
				
				editData.getTaskEntrys().clear();
				editData.getTaskEntrys().addCollection(tasks);
			}
			setDataObject(editData);
			loadData2Gantt(editData);
		}
	}
	
	


	/**
	 * @description 判断是否调整
	 * @author 杜红明
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public boolean isAdjustSchedule() {
		return false;
	}

	/**
	 * @description 让工程项目F7可以在任意状态下改变
	 * @author 杜红明
	 * @createDate 2011-9-3
	 * @version EAS7.0
	 * @see
	 */

	protected void lockContainer(Container container) {
		if (container != null && container.getName() != null) {
			if (container.getName().equals("contCurProject") || container.getName().equals("contSpecialProject")) {
				logger.info(container.getName());
			}
		} else {
			super.lockContainer(container);
		}
	}

	private void beforeCheck() {
		if (editData == null || editData.getProject() == null) {
			FDCMsgBox.showError("请先选择工程项目");
			abort();
		}
	}

	/**
	 * @description 版本对比
	 * @author 杜红明
	 * @createDate 2011-9-15
	 * @version EAS7.0
	 * @see
	 */

	public void actionCompareVer_actionPerformed(ActionEvent e) throws Exception {
		beforeCheck();
		if (editData.getVersion() == 1.0) {
			FDCMsgBox.showError("当前任务只存在一个版本，不能进行比对!");
			abort();
		}
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		IUIWindow uiWindow = null;
		Map uiContext = getUIContext();
		uiContext.put("editUI", this);
		uiContext.put("editData", editData);
		uiWindow = uiFactory.create(RESchCompareUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
	}

	/**
	 * 定位功能，仿照序时簿定位UI重写，只支持编码和名称
	 * 
	 * @author 管骥宇
	 * @createDate 2011-11-9
	 * @version EAS7.0
	 * @see
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		String destBillEditUIClassName = FindTaskUI.class.getName();
		Map ctx = new UIContext(this);
		IUIWindow uiWindow = null;
		// UIFactoryName.MODEL 为弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(destBillEditUIClassName, ctx, null, OprtState.VIEW);
		// 开始展现UI
		uiWindow.show();
	}

	protected void refresh() throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map refreshMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "refresh");
		
		DisplayedColumnsList disCols = getScheduleGanttProject().getGanttTreeTable().getDisplayColumns();
		Map param = new HashMap();
		param.put("isFormExecuteUI", Boolean.valueOf(isFromExecuteUI()));
		ProjectSpecialInfo projectSpecial = null;
		if (this instanceof SpecialScheduleEditUI) {
			projectSpecial = (ProjectSpecialInfo) ((SpecialScheduleEditUI) this).prmtPrjSpecial.getValue();
		}
		editData = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(isMainSchedule(), false, editData.getProject(), projectSpecial, param);
		load2Gantt(editData);
		getScheduleGanttProject().getGanttTreeTable().setDisplayedColumns(disCols);
		ganttProject.getGanttTreeTable().expandAll();
		if (getCurrentSelectLocation() != null && getCurrentSelectLocation().length > 0) {
			getScheduleGanttProject().getTree().selectTreeRow(getCurrentSelectLocation()[0]);
		}
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "refresh", refreshMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	protected void refresh(FDCScheduleInfo editData) throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map refreshMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "refresh");
		// ////////////////////////////////////////////////////////////////////////
		
		DisplayedColumnsList disCols = getScheduleGanttProject().getGanttTreeTable().getDisplayColumns();
		load2Gantt(editData);
		getScheduleGanttProject().getGanttTreeTable().setDisplayedColumns(disCols);
		ganttProject.getGanttTreeTable().expandAll();
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "refresh", refreshMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	public ScheduleTaskPropertyCollection getColumnsProperty() {
		return columnsProperty;
	}

	public void actionBatchModifyTaskType_actionPerformed(ActionEvent e) throws Exception {

		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning("请选择工程项目");
			SysUtil.abort();
		}
		if (editData == null || editData.getTaskEntrys() == null || editData.getTaskEntrys().size() <= 0) {
			FDCMsgBox.showWarning("没有可选择的任务");
			SysUtil.abort();
		}
		this.scheduleInfo = editData;
		UIContext uiContext = new UIContext(this);
		uiContext.put("schdule", editData);
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(BatchModifyTaskType.class.getName(), uiContext).show();
	}
	
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext();
		uiContext.put("value", editData);
		uiContext.put("editUI", this);
		UIFactory.createUIFactory().create(CopyScheduleUI.class.getName(), uiContext).show();
	}

	
	// 获取有权限的组织
	protected Set authorizedOrgs = null;

	/**
	 * 描述：获取有权限的成本中心组织
	 * @Author：zhaoqin
	 * @CreateTime：2014-6-10
	 */
	private void getAuthorizedCostOrgs() {
		authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			try {
				Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
				if (orgs != null) {
					authorizedOrgs = orgs.keySet();
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
}