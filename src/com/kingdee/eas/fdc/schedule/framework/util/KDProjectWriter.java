package com.kingdee.eas.fdc.schedule.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mpxj.CodePage;
import net.sf.mpxj.ConstraintType;
import net.sf.mpxj.Duration;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectHeader;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Task;
import net.sf.mpxj.TimeUnit;
import net.sf.mpxj.mpx.MPXWriter;
import net.sf.mpxj.mspdi.MSPDIWriter;

import org.apache.log4j.Logger;

import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.util.KDEntityTree.KDEntityTreeNode;
import com.kingdee.util.StringUtils;
/**
 * 
 * 描述：导出计划为MSPDI格式文件
 * 修改人：zhiqiao_yang <p>
 * 修改时间：2010-7-23 <p>
 */
public class KDProjectWriter {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.framework.util.KDProjectWriter");
	/**
	 * 需导出的根节点，list中存储的是KDEntityTreeNode对象
	 */
	private List exportRootNodes;
	/**
	 * FDCScheduleTaskInfo的id到MS Project任务的映射。搭建关系时需用到
	 */
	private Map taskId2ProjectTask = new HashMap();
	private String headTitle = "";
	private String headDescription = "";
	private String headCompany = "";

	public KDProjectWriter(FDCScheduleTaskCollection tasks, FDCScheduleTaskCollection selectedTasks) {
		prepareData(tasks, selectedTasks);
	}
	//考核模板也要导入导出
	public KDProjectWriter(RESchTemplateTaskCollection tasks, FDCScheduleTaskCollection selectedTasks) {
		prepareData(tasks, selectedTasks);
	}
	
	private void prepareData(RESchTemplateTaskCollection tasks, FDCScheduleTaskCollection selectedTasks) {
		KDEntityTree entityTree = new KDEntityTree(tasks, null);
		this.exportRootNodes = entityTree.getRoots();
		if (selectedTasks != null && !selectedTasks.isEmpty()) {
			exportRootNodes.clear();
			for (int i = 0; i < selectedTasks.size(); ++i) {
				exportRootNodes.add(entityTree.getNodeById(selectedTasks.get(i).getId().toString()));
			}
		}		
	}
	
	public void write(File file) throws MPXJException, IOException {
		ProjectFile pf = createProjectFile();
		String fileName = file.getName();
		if (fileName.endsWith("mpx")) {
			MPXWriter writer = new MPXWriter();
			writer.write(pf, file);
		} else {
			MSPDIWriter writer = new MSPDIWriter();
			writer.write(pf, file);
		}
	}

	private void prepareData(FDCScheduleTaskCollection tasks, FDCScheduleTaskCollection selectedTasks) {
		KDEntityTree entityTree = new KDEntityTree(tasks, null);
		this.exportRootNodes = entityTree.getRoots();
		if (selectedTasks != null && !selectedTasks.isEmpty()) {
			exportRootNodes.clear();
			for (int i = 0; i < selectedTasks.size(); ++i) {
				exportRootNodes.add(entityTree.getNodeById(selectedTasks.get(i).getId().toString()));
			}
		}
	}

	private ProjectFile createProjectFile() throws MPXJException {
		ProjectFile pf = new ProjectFile();
		
		pf.getFileCreationRecord().setCodePage(CodePage.US);
		pf.setAutoCalendarUniqueID(true);
		pf.setAutoOutlineLevel(true);
		pf.setAutoOutlineNumber(true);
		pf.setAutoResourceID(true);
		pf.setAutoResourceUniqueID(true);
		pf.setAutoTaskID(true);
		pf.setAutoTaskUniqueID(true);
		pf.setAutoWBS(true);
		pf.addDefaultBaseCalendar();
		ProjectHeader header = pf.getProjectHeader();
		header.setProjectTitle(headTitle);
		header.setComments(headDescription);
		header.setCompany(headCompany);

		processTasks(pf);
		processRelationships(pf);
		return pf;
	}

	private void processRelationships(ProjectFile pf) {
		for (int i = 0; i < exportRootNodes.size(); ++i) {
			KDEntityTreeNode treeNode = (KDEntityTreeNode) exportRootNodes.get(i);
			processRelationships(pf, treeNode, null);
		}
	}

	private void processRelationships(ProjectFile pf, KDEntityTreeNode treeNode, Object object) {
		FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) treeNode.getUserObject();
		FDCScheduleTaskDependCollection depends = taskInfo.getDependEntrys();
		Task successorTask;
        Task predecessorTask;
//        logger.debug("&&&&&.....Depend.size:"+taskInfo.getWbs().getLongNumber().replace('!', '.')+" : "+taskInfo.getName()+" : "+depends.size());
		if(depends != null && !depends.isEmpty()){
			for(int i = 0; i < depends.size(); ++i){
				FDCScheduleTaskDependInfo depend = depends.get(i);
				predecessorTask = (Task) taskId2ProjectTask.get(depend.getTask().getId().toString());
				successorTask = (Task) taskId2ProjectTask.get(depend.getDependTask().getId().toString());
				if(predecessorTask != null && successorTask != null){
					RelationType type = null;
					TaskLinkTypeEnum linkType = depend.getType();
					if(TaskLinkTypeEnum.FINISH_FINISH.equals(linkType)){
						type = (RelationType.FINISH_FINISH);
					}else if(TaskLinkTypeEnum.FINISH_START.equals(linkType)){
						type = (RelationType.FINISH_START);
					}else if(TaskLinkTypeEnum.START_START.equals(linkType)){
						type = (RelationType.START_START);						
					}
					Duration lag = Duration.getInstance(depend.getDifference(), TimeUnit.DAYS);
					successorTask.addPredecessor(predecessorTask, type, lag);
				}
				//depend.getDifference();
			}
		}
		for (int i = 0; i < treeNode.getChildren().size(); ++i) {
			processRelationships(pf, (KDEntityTreeNode) treeNode.getChildren().get(i), null);
		}
	}

	private void processTasks(ProjectFile pf) {
		for (int i = 0; i < exportRootNodes.size(); ++i) {
			KDEntityTreeNode treeNode = (KDEntityTreeNode) exportRootNodes.get(i);
			processTasks(pf, treeNode, null);
		}
	}

	private void processTasks(ProjectFile pf, KDEntityTreeNode treeNode, Task parentTask) {
		if(treeNode.getUserObject() instanceof  FDCScheduleTaskInfo){			
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) treeNode.getUserObject();
			Task task = parentTask == null ? pf.addTask() : parentTask.addTask();
			task.setName(taskInfo.getName());
			task.setWBS(taskInfo.getLongNumber());
			// if(null != taskInfo.getWbs()){
			// task.setWBS(taskInfo.getWbs().getLongNumber().replace('!', '.'));
			// }else{
			// task.setWBS(taskInfo.getWbs() + "");
			// }
			/** 以前的实现方式，文本1为责任部门，文本2为责任人 */
			// FullOrgUnitInfo adminDept = taskInfo.getAdminDept();
			// if (adminDept != null) {
			// task.setText1(((FullOrgUnitInfo) adminDept).getName());
			// }
			// if (taskInfo.getAdminPerson() != null) {
			// task.setText2(taskInfo.getAdminPerson().getName());
			// }
			ScheduleTaskBizTypeCollection bizTypeCols = taskInfo.getBizType();
			int size = bizTypeCols.size();
			String strBiz = "";
			for (int i = 0; i < size; i++) {
				strBiz += bizTypeCols.get(i).getBizType().getName();
				if (i < size - 1) {
					strBiz += ",";
				}
			}
			
			if (!StringUtils.isEmpty(strBiz)) {
				task.setText1(strBiz);
			}
			
			task.setText2(taskInfo.getTaskType().getAlias());
			
			
			task.setActualFinish(taskInfo.getActualEndDate());
			task.setActualStart(taskInfo.getActualStartDate());
			
			task.setStart(taskInfo.getStart());
			
			//task.setMilestone(ganttTask.isMilestone());
			task.setConstraintDate(taskInfo.getStart());
			task.setConstraintType(ConstraintType.MUST_START_ON);
			task.setDuration(Duration.getInstance(taskInfo.getEffectTimes().intValue(), TimeUnit.DAYS));
			//task.setPercentageComplete(new Float(percentageComplete));
			task.setHyperlink(taskInfo.getWebLink());
			taskId2ProjectTask.put(taskInfo.getId().toString(), task);
			for (int i = 0; i < treeNode.getChildren().size(); ++i) {
				processTasks(pf, (KDEntityTreeNode) treeNode.getChildren().get(i), task);
			}
		}else if(treeNode.getUserObject() instanceof  RESchTemplateTaskInfo){
			RESchTemplateTaskInfo taskInfo = (RESchTemplateTaskInfo) treeNode.getUserObject();
			Task task = parentTask == null ? pf.addTask() : parentTask.addTask();
			task.setName(taskInfo.getName());
			task.setWBS(taskInfo.getLongNumber());
			// if(null != taskInfo.getWbs()){
			// task.setWBS(taskInfo.getWbs().getLongNumber().replace('!', '.'));
			// }else{
			// task.setWBS(taskInfo.getWbs() + "");
			// }
			/** 以前的实现方式，文本1为责任部门，文本2为责任人 */
			// FullOrgUnitInfo adminDept = taskInfo.getAdminDept();
			// if (adminDept != null) {
			// task.setText1(((FullOrgUnitInfo) adminDept).getName());
			// }
			// if (taskInfo.getAdminPerson() != null) {
			// task.setText2(taskInfo.getAdminPerson().getName());
			// }
//			ScheduleTaskBizTypeCollection bizTypeCols = taskInfo.getBizType();
//			int size = bizTypeCols.size();
//			String strBiz = "";
//			for (int i = 0; i < size; i++) {
//				strBiz += bizTypeCols.get(i).getBizType().getName();
//				if (i < size - 1) {
//					strBiz += ",";
//				}
//			}
			
//			if (!StringUtils.isEmpty(strBiz)) {
//				task.setText1(strBiz);
//			}
			
			task.setText2(taskInfo.getTaskType().getAlias());
			
			
//			task.setActualFinish(taskInfo.getActualEndDate());
//			task.setActualStart(taskInfo.getActualStartDate());
//			
//			task.setStart(taskInfo.getStart());
			
			//task.setMilestone(ganttTask.isMilestone());
//			task.setConstraintDate(taskInfo.getStart());
			task.setConstraintType(ConstraintType.MUST_START_ON);
//			task.setDuration(Duration.getInstance(taskInfo.getEffectTimes().intValue(), TimeUnit.DAYS));
			//task.setPercentageComplete(new Float(percentageComplete));
//			task.setHyperlink(taskInfo.getWebLink());
			taskId2ProjectTask.put(taskInfo.getId().toString(), task);
			for (int i = 0; i < treeNode.getChildren().size(); ++i) {
				processTasks(pf, (KDEntityTreeNode) treeNode.getChildren().get(i), task);
			}
		}
	}

	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}

	public void setHeadDescription(String headDescription) {
		this.headDescription = headDescription;
	}

	public void setHeadCompany(String headCompany) {
		this.headCompany = headCompany;
	}

	
	public static void main(String[] args){
		
		try{
			ProjectFile pf = new ProjectFile();
			
			pf.getFileCreationRecord().setCodePage(CodePage.US);
			pf.setAutoCalendarUniqueID(true);
			pf.setAutoOutlineLevel(true);
			pf.setAutoOutlineNumber(true);
			pf.setAutoResourceID(true);
			pf.setAutoResourceUniqueID(true);
			pf.setAutoTaskID(true);
			pf.setAutoTaskUniqueID(true);
			pf.setAutoWBS(true);
			pf.addDefaultBaseCalendar();
			ProjectHeader header = pf.getProjectHeader(); 
			header.setProjectTitle("test");
			header.setComments("test");
			header.setCompany("test");
			
			Task task =  pf.addTask() ;
			task.setName("name01");
			task.setWBS("wbs01");
			task.setActualFinish(new Date());
			task.setActualStart(new Date());
			
			task.setStart(new Date());
			
			//task.setMilestone(ganttTask.isMilestone());
			task.setConstraintDate(new Date());
			task.setConstraintType(ConstraintType.MUST_START_ON);
			task.setDuration(Duration.getInstance(1, TimeUnit.DAYS));
			
			
			File file = new File("C:/text.xml");
			String fileName = file.getName();
			if (fileName.endsWith("mpx")) {
				MPXWriter writer = new MPXWriter();
				writer.write(pf, file);
			} else {
				MSPDIWriter writer = new MSPDIWriter();
				writer.write(pf, file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
