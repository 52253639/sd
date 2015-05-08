package com.kingdee.eas.fdc.schedule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.util.marshal.Marshaller;
import com.kingdee.util.marshal.Unmarshaller;

public class FDCScheduleInfo extends AbstractFDCScheduleInfo implements Serializable {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.FDCScheduleInfo");

	public FDCScheduleInfo() {
		super();
	}

	/**
	 * 
	 * @param schedule
	 *            前一版本计划
	 */
	public void setPreSchedule(FDCScheduleInfo schedule) {
		this.put("PreSchedule", schedule);
	}

	public FDCScheduleInfo getPreSchedule() {
		return (FDCScheduleInfo) this.get("PreSchedule");
	}

	protected FDCScheduleInfo(String pkField) {
		super(pkField);
	}

	/**
	 * 对象序列化（clone及RPC时会用到对象序列化）时会使用此方法。重载父类方法，避免对象的循环依赖产生过多的数据量
	 */
	public void marshal(Marshaller marshaller) throws IOException {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ///////////////////////////////////////////////////////////////////////

		boolean isFdcDev = FDCHelper.isFDCDebug();

		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		if (isFdcDev) {
			startTime = System.currentTimeMillis();

			logger.info("FDCScheduleInfo.marshal begin...id: " + this.getId());
		}

		// ////////////////////////////////////////////////////////////////////////

		FDCScheduleTaskCollection tasks = getTaskEntrys();
		if (tasks != null) {
			for (int i = 0; i < tasks.size(); ++i) {
				tasks.get(i).setSchedule(null);
			}
		}
		super.marshal(marshaller);
		if (tasks != null) {
			for (int i = 0; i < tasks.size(); ++i) {
				tasks.get(i).setSchedule(this);
			}
		}

		// ////////////////////////////////////////////////////////////////////////

		if (isFdcDev) {
			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;

			logger.info("FDCScheduleInfo.marshal end...id: " + this.getId() + "; exeTime: " + exeTime);
		}

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ///////////////////////////////////////////////////////////////////////
	}

	/**
	 * 对象的反序列化， marshal相当于writeObject， unmarshal相当于readObject
	 */
	public void unmarshal(Unmarshaller unmarshaller) throws IOException, ClassNotFoundException {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ///////////////////////////////////////////////////////////////////////

		boolean isFdcDev = FDCHelper.isFDCDebug();

		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		if (isFdcDev) {
			startTime = System.currentTimeMillis();

			logger.info("FDCScheduleInfo.unmarshal begin...id: " + this.getId());
		}

		// ////////////////////////////////////////////////////////////////////////

		super.unmarshal(unmarshaller);
		FDCScheduleTaskCollection tasks = getTaskEntrys();
		if (tasks != null) {
			for (int i = 0; i < tasks.size(); ++i) {
				tasks.get(i).setSchedule(this);
			}
		}

		// ////////////////////////////////////////////////////////////////////////

		if (isFdcDev) {
			endTime = System.currentTimeMillis();
			exeTime = endTime - startTime;

			logger.info("FDCScheduleInfo.unmarshal end...id: " + this.getId() + "; exeTime: " + exeTime);
		}

		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		// ///////////////////////////////////////////////////////////////////////
	}

	/**
	 * 得到计划类型
	 * 
	 * @param taskTypeId
	 * @return 主项计划、专项计划
	 */
	public static TaskTypeInfo getScheduleType(String taskTypeId) {
		if (taskTypeId == null) {
			throw new NullPointerException("can't be null!");
		}
		TaskTypeInfo scheduleType = new TaskTypeInfo();
		taskTypeId = taskTypeId.equals(TaskTypeInfo.TASKTYPE_MAINTASK) ? taskTypeId : TaskTypeInfo.TASKTYPE_SPECIALTASK;
		scheduleType.setId(BOSUuid.read(taskTypeId));
		return scheduleType;
	}

	private void clearPropertiesExcludeId(DataBaseInfo billBase) {
		// TODO
		// Enumeration keys = billBase.keys();
		// while (keys.hasMoreElements()) {
		// String key = (String) keys.nextElement();
		// if (!"id".equalsIgnoreCase(key.toString()) &&
		// !"name".equalsIgnoreCase(key.toString())) {
		// billBase.put(key, null);
		// }
		// }
	}

	private void reduce4Schedule(Map id2Project, Map id2Person, Map id2Org) {

		CurProjectInfo project = getProject();
		clearPropertiesExcludeId(project);
		id2Project.put(project.getId().toString(), project);
		PersonInfo person = getAdminPerson();
		if (person != null) {
			clearPropertiesExcludeId(person);
			id2Person.put(person.getId().toString(), person);
		}
		// FullOrgUnitInfo org = getAdminDept();
		// clearPropertiesExcludeId(org);
		// id2Org.put(org.getId().toString(), org);
	}

	private void reduce4WBS(Map id2Project, Map id2Person, Map id2Org, FDCWBSInfo wbs) {
		// CurProjectInfo project = null;
		// // CurProjectInfo project = wbs.getCurProject();
		// if (project != null) {
		// CurProjectInfo project2 = (CurProjectInfo)
		// id2Project.get(project.getId().toString());
		// if (project2 == null) {
		// clearPropertiesExcludeId(project);
		// id2Project.put(project.getId().toString(), project);
		// } else {
		// wbs.setCurProject(project2);
		// }
		// }
		// PersonInfo person = wbs.getAdminPerson();
		// if (person != null) {
		// PersonInfo person2 = (PersonInfo)
		// id2Person.get(person.getId().toString());
		// if (person2 == null) {
		// clearPropertiesExcludeId(person);
		// id2Person.put(person.getId().toString(), person);
		// } else {
		// wbs.setAdminPerson(person2);
		// }
		// }
		// FullOrgUnitInfo adminDept = wbs.getAdminDept();
		// if (adminDept != null) {
		// FullOrgUnitInfo adminDept2 = (FullOrgUnitInfo)
		// id2Org.get(adminDept.getId().toString());
		// if (adminDept2 == null) {
		// clearPropertiesExcludeId(adminDept);
		// id2Org.put(adminDept.getId().toString(), adminDept);
		// } else {
		// wbs.setAdminDept(adminDept2);
		// }
		// }
		// FullOrgUnitInfo respDept = wbs.getRespDept();
		// if (respDept != null) {
		// FullOrgUnitInfo respDept2 = (FullOrgUnitInfo)
		// id2Org.get(respDept.getId().toString());
		// if (respDept2 == null) {
		// clearPropertiesExcludeId(respDept);
		// id2Org.put(respDept.getId().toString(), respDept);
		// } else {
		// wbs.setRespDept(respDept2);
		// }
		// }
	}

	private void reduce4Task(Map id2Project, Map id2Person, Map id2Org, FDCScheduleTaskInfo task) {
		PersonInfo person = task.getAdminPerson();
		if (person != null) {
			PersonInfo person2 = (PersonInfo) id2Person.get(person.getId().toString());
			if (person2 == null) {
				clearPropertiesExcludeId(person);
				id2Person.put(person.getId().toString(), person);
			} else {
				task.setAdminPerson(person2);
			}
		}
		FullOrgUnitInfo adminDept = task.getAdminDept();
		if (adminDept != null) {
			FullOrgUnitInfo adminDept2 = (FullOrgUnitInfo) id2Org.get(adminDept.getId().toString());
			if (adminDept2 == null) {
				clearPropertiesExcludeId(adminDept);
				id2Org.put(adminDept.getId().toString(), adminDept);
			} else {
				task.setAdminDept(adminDept2);
			}
		}
		FullOrgUnitInfo respDept = task.getPlanDept();
		if (respDept != null) {
			FullOrgUnitInfo respDept2 = (FullOrgUnitInfo) id2Org.get(respDept.getId().toString());
			if (respDept2 == null) {
				clearPropertiesExcludeId(respDept);
				id2Org.put(respDept.getId().toString(), respDept);
			} else {
				task.setPlanDept(respDept2);
			}
		}
		// reduce4WBS(id2Project, id2Person, id2Org, task.getWbs());
	}

	public void reduceData4Transfer2Remote() {
		Map id2Project = new HashMap();
		Map id2Person = new HashMap();
		Map id2Org = new HashMap();
		reduce4Schedule(id2Project, id2Person, id2Org);
		FDCScheduleTaskCollection taskEntrys = getTaskEntrys();
		for (int i = 0; i < taskEntrys.size(); ++i) {
			reduce4Task(id2Project, id2Person, id2Org, taskEntrys.get(i));
		}
	}

	public static long testObjectSize(Object obj) {
		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream(1024 * 128);
			ObjectOutputStream o = new ObjectOutputStream(buf);
			o.writeObject(obj);
			byte[] bytes = buf.toByteArray();
			long ret = bytes.length;
			// FileOutputStream fos = new FileOutputStream("C:/08-11-1.dat");
			// fos.write(bytes);
			// fos.close();
			bytes = null;
			buf.close();
			o.close();
			return ret;
		} catch (Exception e) {
			logger.error(e.getCause(), e);
		}
		return 0;
	}

}