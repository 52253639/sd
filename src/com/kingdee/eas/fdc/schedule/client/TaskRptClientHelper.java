package com.kingdee.eas.fdc.schedule.client;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;

public class TaskRptClientHelper {
	private TaskRptClientHelper(){};
	
	public static String getScheduleDigest(FDCScheduleInfo info){
		StringBuffer digest=new StringBuffer();
		digest.append("计划编码: ");
		digest.append(info.getNumber());
		digest.append(";计划名称: ");
		digest.append(info.getName());
		digest.append(";所属工程项目: ");
		digest.append(info.getProject()!=null?info.getProject().getName():"");
		digest.append(";计划负责人: ");
		digest.append(info.getAdminPerson()!=null?info.getAdminPerson().getName():"");
		return digest.toString();
	}
	
	public static String getProjectFilterSql(){
		//取当前财务组织内的工程项目
		OrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		if(currentFIUnit==null){
			//取不到当前财务组织的话只能看当前组织内是否有财务组织了
			currentFIUnit=SysContext.getSysContext().getCurrentOrgUnit();
		}
		StringBuffer sql=new StringBuffer();
		sql.append("select fid from T_fdc_curProject where ffullorgunit in ( " );
		sql.append("   select company.fid from t_org_baseunit parent ,T_org_company company ");
		sql.append("	where parent.fid='"+currentFIUnit.getId().toString()+"'");
		sql.append("      and charindex(parent.flongnumber||'!',company.flongnumber||'!')=1  and company.fisbizunit=1 ");
		sql.append(") 	");
		return sql.toString();
	}
	
	public static String getProjectByTaskAdminDempFilterSql(){
//		//取当前财务组织内的工程项目
//		OrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
//		if(currentFIUnit==null){
			//取不到当前财务组织的话只能看当前组织内是否有财务组织了
		OrgUnitInfo currentFIUnit=SysContext.getSysContext().getCurrentOrgUnit();
//		}
		StringBuffer sql=new StringBuffer();
		sql.append("select project.fid from T_fdc_curProject project,T_SCH_FDCSchedule schedule,T_SCH_FDCScheduleTask task ");
		sql.append(" where project.fid=schedule.FProjectID and schedule.fid=task.fscheduleid ");
		sql.append(" and schedule.FIsLatestVer=1 and task.FAdminDeptID='"+currentFIUnit.getId().toString()+"'");
		return sql.toString();
	}
	public static String getProjectByTaskAdminPersonFilterSql(){
		UserInfo user=SysContext.getSysContext().getCurrentUserInfo();
		PersonInfo person = user.getPerson();
		StringBuffer sql=new StringBuffer();
		sql.append("select project.fid from T_fdc_curProject project,T_SCH_FDCSchedule schedule,T_SCH_FDCScheduleTask task ");
		sql.append(" where project.fid=schedule.FProjectID and schedule.fid=task.fscheduleid ");
		sql.append(" and schedule.FIsLatestVer=1 and task.FAdminPersonID='"+person.getId().toString()+"'");
		return sql.toString();
	}
}
