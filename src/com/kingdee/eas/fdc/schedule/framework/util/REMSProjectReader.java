package com.kingdee.eas.fdc.schedule.framework.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mpxj.Duration;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Task;
import net.sf.mpxj.TimeUnit;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.util.StringUtils;

/**
 * 读取MS Project类 必须调用parse方法后，再调用getTasks方法即可获得所有已经解析的任务
 * 
 * @author zhiqiao_yang
 * 
 */
public class REMSProjectReader {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.framework.util.REMSProjectReader");
	private static NumberFormat NUMBER_FORMAT = new DecimalFormat("000");
	private ProjectFile mpx = null;
	private IRESchTaskCreator taskCreator;
	private IRESchTaskPredecessorCreator predecessorCreator;
	private List schTasks;
	private ScheduleCalendarInfo calendarInfo;
	private Map msTaskId2SchTask;
	private Map adminDept2MSTaskIds;
	private Map adminPerson2MSTaskIds;
	
	public static List pasreMSProject(File projectFile, IRESchTaskCreator taskCreator, IRESchTaskPredecessorCreator predecessorCreator)
			throws Exception {
		REMSProjectReader reader = new REMSProjectReader(projectFile, taskCreator, predecessorCreator);
		reader.parse();
		return reader.getTasks();
	}
	
	public REMSProjectReader(File projectFile, IRESchTaskCreator taskCreator, IRESchTaskPredecessorCreator predecessorCreator)
			throws Exception {
		try {
			this.taskCreator = taskCreator;
			this.predecessorCreator = predecessorCreator;
			mpx = new MPPReader().read(projectFile);
		} catch (Exception e) {
			try {
				mpx = new MPXReader().read(projectFile);
			} catch (Exception ex) {
				mpx = new MSPDIReader().read(projectFile);
			}
		}
	}
	
	public void parse() throws BOSException {		
		init();
		// 必存在第一个任务，且其任务名称为Project文件名, 需忽略此任务
		Task rootTask = (Task) mpx.getChildTasks().get(0);
		processTasks(rootTask.getChildTasks(), null);
		processPredecessors();
		processTaskExtProperties();
	}

	private void processTaskExtProperties() throws BOSException {
		fillAdminDept();
		fillAdminPerson();
	}

	private void fillAdminPerson() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("name", adminPerson2MSTaskIds.keySet(), CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("name");
		PersonCollection adminPersons = PersonFactory.getRemoteInstance().getPersonCollection(view);
		Map name2Person = new HashMap();
		for (int i = 0; i < adminPersons.size(); ++i) {
			PersonInfo person = adminPersons.get(i);
			if (name2Person.get(person.getName()) == null) {
				name2Person.put(person.getName(), new ArrayList());
			}
			List list = (List) name2Person.get(person.getName());
			list.add(person);
		}
		Set names = name2Person.keySet();
		for (Iterator iter = names.iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			List persons = (List) name2Person.get(name);
			if (persons.size() == 1) {// 若persons.size不等于1说明根据名称找到多个或没有找到职员
				List msTaskIds = (List) adminDept2MSTaskIds.get(name);
				for (int i = 0; i < msTaskIds.size(); ++i) {
					IRESchTask schTask = (IRESchTask) msTaskId2SchTask.get(msTaskIds.get(i));
					schTask.setAdminPerson((PersonInfo) persons.get(0));
				}
			}
		}
	}

	private void fillAdminDept() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("name", adminDept2MSTaskIds.keySet(), CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("name");
		FullOrgUnitCollection adminDepts = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		Map name2AdminDepts = new HashMap();
		for (int i = 0; i < adminDepts.size(); ++i) {
			FullOrgUnitInfo adminDept = adminDepts.get(i);
			if (name2AdminDepts.get(adminDept.getName()) == null) {
				name2AdminDepts.put(adminDept.getName(), new ArrayList());
			}
			List list = (List) name2AdminDepts.get(adminDept.getName());
			list.add(adminDept);
		}
		Set names = name2AdminDepts.keySet();
		for (Iterator iter = names.iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			List depts = (List) name2AdminDepts.get(name);
			if (depts.size() == 1) {// 若depts.size不等于1说明根据名称找到多个或没有找到组织
				List msTaskIds = (List) adminDept2MSTaskIds.get(name);
				for (int i = 0; i < msTaskIds.size(); ++i) {
					IRESchTask schTask = (IRESchTask) msTaskId2SchTask.get(msTaskIds.get(i));
					schTask.setAdminDept((FullOrgUnitInfo) depts.get(0));
				}
			}
		}
	}
	
	
	
	private void init() {
		schTasks = new ArrayList();
		msTaskId2SchTask = new HashMap();
		adminDept2MSTaskIds = new HashMap();
		adminPerson2MSTaskIds = new HashMap();
	}

	private void processTasks(List children, IRESchTask father) {
		if (children != null && !children.isEmpty()) {
			for (int i = 0; i < children.size(); ++i) {
				Task task = (Task) children.get(i);
				IRESchTask schTask = taskCreator.createSchTask();
				schTask.setNumber(NUMBER_FORMAT.format(i + 1));	
				fillSchTask(task, schTask, father);
				processTasks(task.getChildTasks(), schTask);				
			}
		}
	}

	private void processPredecessors() {
		List tasks = mpx.getAllTasks();
		for (int i = 1; i < tasks.size(); ++i) {
			Task msTask = (Task) tasks.get(i);
			List pres = msTask.getPredecessors();
			if (pres != null && !pres.isEmpty()) {
				IRESchTask schTask = (IRESchTask) msTaskId2SchTask.get(msTask.getUniqueID());
				for (Iterator iter = pres.iterator(); iter.hasNext();) {
					Relation rel = (Relation) iter.next();
					IRESchTaskPredecessor predecessor = predecessorCreator.createSchTaskPredecessor();
					schTask.addPredecessor(predecessor);
					predecessor.setCurrentTask((IRESchTask) msTaskId2SchTask.get(rel.getSourceTask().getUniqueID()));
					predecessor.setPredecessor((IRESchTask) msTaskId2SchTask.get(rel.getTargetTask().getUniqueID()));
					predecessor.setPredecessorType(convertPredecessorType(rel.getType()));
					predecessor.setDifferenceDay(getDay4Duration(rel.getLag()));
				}
			}
		}
	}

	
	private int getDay4Duration(Duration lag) {
		if (TimeUnit.DAYS.equals(lag.getUnits())) {
			return (int) lag.getDuration();
		}
		// TODO 其它搭建时间暂不支持
		return 0;
	}

	private TaskLinkTypeEnum convertPredecessorType(RelationType type) {
		if (RelationType.FINISH_FINISH.equals(type)) {
			return TaskLinkTypeEnum.FINISH_FINISH;
		} else if (RelationType.FINISH_START.equals(type)) {
			return TaskLinkTypeEnum.FINISH_START;
		}  else if (RelationType.START_START.equals(type)) {
			return TaskLinkTypeEnum.START_START;
		}
		throw new IllegalArgumentException(type + "is error.");
	}

	protected void fillSchTask(Task msTask, IRESchTask schTask, IRESchTask parentSchTask) {
		msTaskId2SchTask.put(msTask.getUniqueID(), schTask);
		schTasks.add(schTask);
		fillIds(msTask, schTask);
		schTask.setParentTask(parentSchTask);
		schTask.setName(msTask.getName());
		schTask.setPlanStart(clearTime(msTask.getStart()));
		schTask.setPlanEnd(clearTime(msTask.getFinish()));
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(schTask.getPlanStart(), schTask.getPlanEnd(), calendarInfo);
		if (effectTimes.intValue() == 0) {
			logger.error("error: duration=0;name=" + schTask.getName() + ";startDate=" + schTask.getPlanStart() + ";endDate="
					+ schTask.getPlanEnd());
			schTask.setDay(FDCHelper.ONE);
		} else {
			schTask.setDay(effectTimes);
		}

		schTask.setTaskType(convertTaskType(msTask.getText1()));
		fillExtendProperties(msTask, schTask);
	}

	protected void fillExtendProperties(Task msTask, IRESchTask schTask) {
		String adminDept = msTask.getText2();
		if (adminDept != null) {
			List ids = (List) this.adminDept2MSTaskIds.get(adminDept);
			if (ids == null) {
				ids = new ArrayList();
				this.adminDept2MSTaskIds.put(adminDept, ids);
			}
			ids.add(msTask.getUniqueID());
		}
		String adminPerson = msTask.getText3();
		if (adminPerson != null) {
			List ids = (List) this.adminPerson2MSTaskIds.get(adminPerson);
			if (ids == null) {
				ids = new ArrayList();
				this.adminPerson2MSTaskIds.put(adminPerson, ids);
			}
			ids.add(msTask.getUniqueID());
		}
	}

	protected void fillIds(Task msTask, IRESchTask schTask) {
		String taskId = msTask.getText17();
		schTask.setId(BOSUuid.create(schTask.getBOSType()));
		if (!StringUtils.isEmpty(taskId)) {
			try {
				schTask.setId(BOSUuid.read(taskId));
			} catch (Exception e) {
				logger.error("Id is error. " + taskId + " is error[ " + schTask.getName() + " ]", e);
			}
		}
		String srcTaskId = msTask.getText16();
		if (!StringUtils.isEmpty(srcTaskId)) {
			try {
				BOSObjectType bosType = BOSUuid.getBOSObjectType(srcTaskId, true);
				if (schTask.getBOSType().equals(bosType)) {
					schTask.setSrcID(srcTaskId);
				}
			} catch (Exception e) {
				logger.error("SrcId is error. " + srcTaskId + " is error[ " + schTask.getName() + " ]", e);
			}
		}
		if (schTask.getSrcID() == null) {
			schTask.setSrcID(schTask.getId().toString());
		}
	}
	public List getTasks() {
		return this.schTasks;
	}
	private Date clearTime(Date date) {
		Calendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MILLISECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}
	private RESchTaskTypeEnum convertTaskType(String text) {
		List list = RESchTaskTypeEnum.getEnumList();
		for (int i = 0; i < list.size(); ++i) {
			RESchTaskTypeEnum e = (RESchTaskTypeEnum) list.get(i);
			if (e.getAlias().equalsIgnoreCase(text)) {
				return e;
			}
		}
		return RESchTaskTypeEnum.COMMONLY;
	}
	public static void main(String[] args) throws Exception {
		REMSProjectReader reader = new REMSProjectReader(new File("T:\\1.导入模板任务模板.mpp"), new IRESchTaskCreator() {
			public IRESchTask createSchTask() {
				return (IRESchTask) new RESchTemplateTaskInfo();
			}
		}, new IRESchTaskPredecessorCreator() {
			public IRESchTaskPredecessor createSchTaskPredecessor() {
				return new RESchTemplateTaskPredecessorInfo();
			}
		});
		reader.parse();
		reader.getTasks();
	}
	
	public static interface IRESchTaskCreator {
		public IRESchTask createSchTask();
	}
	public static interface IRESchTaskPredecessorCreator {
		public IRESchTaskPredecessor createSchTaskPredecessor();
	}
}
