package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.Administrator;
import com.kingdee.eas.base.permission.IUserRoleOrgManager;
import com.kingdee.eas.base.permission.OrgRangeCollection;
import com.kingdee.eas.base.permission.OrgRangeInfo;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.base.permission.RoleCollection;
import com.kingdee.eas.base.permission.RoleInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserRoleOrgManagerFactory;
import com.kingdee.eas.base.permission.util.PermissionRangeHelper;
import com.kingdee.eas.base.permission.util.ToolUtils;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.IPositionMember;
import com.kingdee.eas.basedata.org.JobInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.PositionFactory;
import com.kingdee.eas.basedata.org.PositionHierarchyCollection;
import com.kingdee.eas.basedata.org.PositionHierarchyFactory;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFacadeFactory;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.hr.emp.IPersonPosition;
import com.kingdee.eas.hr.emp.PersonPositionCollection;
import com.kingdee.eas.hr.emp.PersonPositionFactory;
import com.kingdee.eas.hr.emp.PersonPositionInfo;
import com.kingdee.eas.hr.org.JobGradeInfo;
import com.kingdee.eas.hr.org.JobLevelInfo;
import com.kingdee.eas.scm.common.SCMBillCommonFacadeFactory;
import com.kingdee.eas.scm.im.inv.client.InvClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class PersonHelper {
	
	/**
	 * 判断当前登录人的部门
	 * */
	private AdminOrgUnitInfo getDefaultOrgUnit() throws BOSException, EASBizException
	{
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		IPerson iPerson = PersonFactory.getRemoteInstance();
		AdminOrgUnitInfo primaryAdmin = null;
		CompanyOrgUnitInfo tempCompany = null;
		CompanyOrgUnitInfo cou = null;
		if(user.getPerson() != null && user.getPerson().getId() != null)
	    {
			primaryAdmin = iPerson.getPrimaryAdminOrgUnit(user.getPerson().getId());
			UserInfo userMG = ToolUtils.getUserAllInfo(null, SysContext.getSysContext().getCurrentUserInfo().getId().toString());
			OrgRangeCollection userRangeCollection = userMG.getOrgRange();
			FilterInfo filter = new FilterInfo();
			AdminOrgUnitInfo adminOrgUnitInfo = null;
			AdminOrgUnitInfo admTempInfo = null;
			OrgRangeInfo orgRageInfo = null;
			EntityViewInfo viewInfo = new EntityViewInfo();
			FullOrgUnitInfo fullInfo = null;
			IAdminOrgUnit iAdmin = AdminOrgUnitFactory.getRemoteInstance();
			HashSet set = new HashSet();
			boolean isHasPermission = false;
			if(userRangeCollection.size() > 0)
	        {
				for(int i = 0; i < userRangeCollection.size(); i++)
	            {
					orgRageInfo = userRangeCollection.get(i);
					if(orgRageInfo.getType().equals(OrgRangeType.ADMIN_ORG_TYPE))
	                {
						fullInfo = orgRageInfo.getOrg();
						set.add(fullInfo.getId().toString());
	                }
	            }
	
				filter.getFilterItems().add(new FilterItemInfo("ID", set, CompareType.INCLUDE));
				viewInfo.setFilter(filter);
				AdminOrgUnitCollection aouCol = iAdmin.getAdminOrgUnitCollection(viewInfo);
				int i = 0;
				int size = aouCol.size();
				do
	            {
					if(i >= size)
						break;
					adminOrgUnitInfo = aouCol.get(i);
					if(adminOrgUnitInfo.getId() != null && primaryAdmin != null && primaryAdmin.getId().toString().equals(adminOrgUnitInfo.getId().toString()))
	                {
						admTempInfo = adminOrgUnitInfo;
						isHasPermission = true;
						break;
	                }
					i++;
	            } while(true);
	        }
			if(primaryAdmin != null)
				if(isHasPermission)
	            {
					tempCompany = InvClientUtils.getCompanyInfo(primaryAdmin);
					if(tempCompany != null)
	                {
						if(cou == null)
							cou = tempCompany;
						return primaryAdmin;
	                }
	            } else
	            {
	            	tempCompany = InvClientUtils.getCompanyInfo(admTempInfo);
	            	if(tempCompany != null)
	                {
	            		if(cou == null)
	            			cou = tempCompany;
	            		return admTempInfo;
	                }
	            }
	    }
		if(tempCompany == null)
	    {
			String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
			ArrayList adminList = null;
			adminList = (ArrayList)PermissionRangeHelper.getUserOrgId(null, new ObjectUuidPK(BOSUuid.read(userID)), OrgRangeType.ADMIN_ORG_TYPE);
			AdminOrgUnitInfo adminInfo = SysContext.getSysContext().getCurrentAdminUnit();
			if(adminList != null && adminList.size() > 0)
	        {
				if(adminInfo != null && adminList.contains(adminInfo.getId().toString()))
	            {
					tempCompany = InvClientUtils.getCompanyInfo(adminInfo);
					if(tempCompany != null)
	                {
						cou = tempCompany;
						return adminInfo;
	                }
	            }
				if(tempCompany == null)
	            {
					for(int i = 0; i < adminList.size(); i++)
	                {
						Map couMap = SCMBillCommonFacadeFactory.getRemoteInstance().getCompanyInfos((String[])adminList.toArray(new String[0]), OrgType.Admin, OrgType.Company);
						for(Iterator iter = couMap.keySet().iterator(); iter.hasNext();)
	                    {
							String key = (String)iter.next();
							if(couMap.get(key) != null)
	                        {
								cou = (CompanyOrgUnitInfo)couMap.get(key);
								return AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(key));
	                        }
	                    }
	
	                }
	
	            }
	        }
	    }
		if(tempCompany == null)
			return null;
		else
			return null;
	}
	/**
	 * 根据行政组织，得到组织下人员ID（Set）
	 * 
	 * */
	public static Set getPersonIds(Context ctx, String adminOrgUnitId)
    throws BOSException
	{
		Set personIds = new HashSet();
		if(CommonHelper.isEmpty(adminOrgUnitId))
			return personIds;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("position.adminOrgUnit.id", adminOrgUnitId));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		IPositionMember iPositionMember = null;
		if(ctx == null)
			iPositionMember = PositionMemberFactory.getRemoteInstance();
		else
			iPositionMember = PositionMemberFactory.getLocalInstance(ctx);
		PositionMemberCollection coll = iPositionMember.getPositionMemberCollection(evi);
		if(coll == null || coll.isEmpty())
			return personIds;
		int i = 0;
		for(int size = coll.size(); i < size; i++)
			personIds.add(coll.get(i).getPerson().getId().toString());
		return personIds;
	}
	/**
	 * 根据行政组织，得到组织下人员对象集合
	 * 
	 * */
	public static PersonCollection getPersonColl(Context ctx, String adminOrgUnitId)
	    throws BOSException, EASBizException
	{
		Set ids = getPersonIds(ctx, adminOrgUnitId);
		if(ids == null || ids.isEmpty())
			return new PersonCollection();
		IPerson iPerson = null;
		if(ctx == null)
			iPerson = PersonFactory.getRemoteInstance();
		else
			iPerson = PersonFactory.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		return iPerson.getPersonCollection(evi);
	}
	/**
	 * 根据user 获取CU过滤
	 * 
	 * */
	public static FilterInfo getCuFilterByUser(UserInfo user) throws Exception
	  {
	    FilterInfo filterInfo = new FilterInfo();
	    filterInfo.getFilterItems().add(new FilterItemInfo("cu.id", "11111111-1111-1111-1111-111111111111CCE7AED4"));
	    filterInfo.getFilterItems().add(new FilterItemInfo("cu.id", "00000000-0000-0000-0000-000000000000CCE7AED4"));
	    if (Administrator.isCUAdmin(null, new ObjectUuidPK(user.getId()))) {
	      filterInfo.getFilterItems().add(new FilterItemInfo("cu.id", ToolUtils.aryToStr(PermissionRangeHelper.getBizUserCUOrgRange(null, ToolUtils.getCurrentUserPK()), false), CompareType.INCLUDE));
	    }
	    else if (user.isBizAdmin()) {
	      filterInfo.getFilterItems().add(new FilterItemInfo("cu.id", ToolUtils.aryToStr(PermissionRangeHelper.getUserOrgId(null, new ObjectUuidPK(user.getId()), OrgRangeType.MANAGE_ORG_TYPE), false), CompareType.INCLUDE));
	    }
	    else {
	      filterInfo.getFilterItems().add(new FilterItemInfo("cu.id", user.getCU().getId()));
	    }
	    filterInfo.setMaskString("#0 OR #1 OR #2");
	    return filterInfo;
	  }
	/**
	 * 判断当前登录人是否系统用户
	 * */
    public static boolean isCurrentUserAdministrator()
    throws EASBizException, BOSException
	{
	    UserInfo currentUser = SysContext.getSysContext().getCurrentUserInfo();
	    return Administrator.isCUAdmin(null, new ObjectUuidPK(currentUser.getId()));
	}
	/**
	 * 根据personId 获取员工职务
	 * */
	public static JobInfo getJobByPersonId(Context ctx, String personId)
    throws BOSException, EASBizException
	{
	    JobInfo info = null;
	    String oql = "select position.job.*, position.job.jobType.*, position.job.jobCategory.* where isPrimary = 1 and person.id = '" + personId + "'";
	    IPositionMember pm = null;
	    if(ctx!=null)
	    	pm = PositionMemberFactory.getLocalInstance(ctx);
	    else
	    	pm = PositionMemberFactory.getRemoteInstance();
	    PositionMemberCollection c = pm.getPositionMemberCollection(oql);
	    if(c.size() > 0)
	        info = c.get(0).getPosition().getJob();
	    return info;
	}
	/**
	 * 根据personId 获取员工职级
	 * */
	public static JobLevelInfo getJobLevelByPersonId(Context ctx, String personId)
	    throws BOSException, EASBizException
	{
	    JobLevelInfo info = null;
	    IPersonPosition ipp = null;
	    if(ctx!=null)
	    	ipp = PersonPositionFactory.getLocalInstance(ctx);
	    else
	    	ipp = PersonPositionFactory.getRemoteInstance();
	    String oql = "select jobLevel.* where person.id = '" + personId + "'";
	    PersonPositionCollection c = ipp.getPersonPositionCollection(oql);
	    if(c.size() > 0)
	        info = c.get(0).getJobLevel();
	    return info;
	}
	/**
	 * 根据personId 获取员工职位
	 * */
	public static PositionInfo getPositionByPersonId(Context ctx, String personId)
	    throws BOSException, EASBizException
	{
	    PositionInfo info = null;
	    String oql = "select position.* where isPrimary = 1 and person.id = '" + personId + "'";
	    IPositionMember ipp = null;
	    if(ctx!=null)
	    	ipp = PositionMemberFactory.getLocalInstance(ctx);
	    else
	    	ipp = PositionMemberFactory.getRemoteInstance();
	    PositionMemberCollection c = ipp.getPositionMemberCollection(oql);
	    if(c.size() > 0)
	        info = c.get(0).getPosition();
	    return info;
	}
	/**
	 * 根据personId 获取员工主要职位Id
	 * */
	public static String getPrimaryPositionByPersonId(Context ctx, String personId)
	    throws BOSException, EASBizException
	{
	    String oql = "select position.id where isPrimary = 1 and person.id = '" + personId + "'";
	    IPositionMember ipp = null;
	    if(ctx!=null)
	    	ipp = PositionMemberFactory.getLocalInstance(ctx);
	    else
	    	ipp = PositionMemberFactory.getRemoteInstance();
	    PositionMemberCollection c = ipp.getPositionMemberCollection(oql);
	    if(c.size() > 0)
	        return c.get(0).getPosition().getId().toString();
	    else
	        return null;
	}
	/**
	 * 根据personId 获取员工职等
	 * */
	 public static JobGradeInfo getJobGradeByPersonId(Context ctx, String personId)
     throws BOSException, EASBizException
	 {
	     JobGradeInfo info = null;
	     IPersonPosition ipp = null;
	     if(ctx!=null)
	    	 ipp = PersonPositionFactory.getLocalInstance(ctx);
	     else
	    	 ipp = PersonPositionFactory.getRemoteInstance();
	     String oql = "select jobGrade.* where person.id = '" + personId + "'";
	     PersonPositionCollection c = ipp.getPersonPositionCollection(oql);
	     if(c.size() > 0)
	         info = c.get(0).getJobGrade();
	     return info;
	 }
	 
	 /**
		 * 根据positionId 获取hr组织单元id
		 * */
		public static BOSUuid getHROrgIdByPosition(Context ctx, BOSUuid positionId)
	    throws BOSException, EASBizException
		{
		    String oql = "select hrOrgUnit.id where id = '" + positionId + "'";
		    if(ctx!=null)
		    	return PositionFactory.getLocalInstance(ctx).getPositionCollection(oql).get(0).getHrOrgUnit().getId();
		    else
		    	return PositionFactory.getRemoteInstance().getPositionCollection(oql).get(0).getHrOrgUnit().getId();
		}
		/**
		 * 根据personId 获取usrId
		 * @throws BOSException 
		 * */
		public static String getUserId(Context ctx,String personId) throws BOSException{
			String userId = "";
			String sql = "select fid from t_pm_user where fpersonid='"+personId+"'";
			IRowSet rowset = null;
			if(ctx==null) //客户端调用
				rowset = new FDCSQLBuilder().appendSql(sql).executeQuery();
			else  //服务端调用
				rowset = DbUtil.executeQuery(ctx, sql);
			try {
				while(rowset.next()){
					userId = rowset.getString("fid");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return userId;
		}
		/**
		 * 获取用户所在的部门信息列表
		 * @return
		 */
		public static AdminOrgUnitCollection getDepartmentByUserCollection(PersonInfo person) {
			
			try {
				return PersonFacadeFactory.getRemoteInstance().getAdminOrgUnitByPerson(SysContext.getSysContext().getCurrentUserInfo().getPerson().getId());
			} catch (Exception e1) {
			}
			return null;
		}
		
		/**
		 * 获得用户的所有信息
		 * @return
		 */
		public static PersonInfo getPerson() {
			return SysContext.getSysContext().getCurrentUserInfo().getPerson();
		}
		
		/**
		 * 根据当前的登陆用户 获得职员_任职情况的职位列表
		 * @return
		 */
		public static PositionMemberCollection getPositionMemberByUser() {
			try {
				return getPositionMemberByUser(SysContext.getSysContext().getCurrentUserInfo().getPerson());
			} catch (Exception e1) {
			}
			return null;
		}
		
		
		/**
		 * 根据用户 获得职员_任职情况 职位列表
		 * 
		 * @param person
		 * 
		 * @return
		 */
		public static PositionMemberCollection getPositionMemberByUser(PersonInfo person) {
			
			try {
				String oql = "select *, person.*, position.adminOrgUnit.* where person.id = '" + person.getId() + "'" ;
				return PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(oql);
			} catch (Exception e1) {
			}
			return null;
		}
		
		
		/**
		 * 根据用户信息 获得职员_任职情况 主要职位的部门信息
		 * @return
		 */
		public static AdminOrgUnitInfo getPosiMemByDeptUser(PersonInfo person) {
			
			AdminOrgUnitInfo workDept = null;
			PositionMemberCollection positMems = getPositionMemberByUser(person);
			
			if (positMems == null)
				return workDept;
			
			for (int i = 0; i < positMems.size(); i++) {
				if (positMems.get(i).isIsPrimary()) {
					workDept = positMems.get(i).getPosition().getAdminOrgUnit();
					return workDept;
				}
			}
			return workDept;
		}
		
		/**
		 * 根据当前登陆的用户 获得职员_任职情况 主要职位的部门信息
		 * @return
		 */
		public static AdminOrgUnitInfo getPosiMemByDeptUser() {
			
			AdminOrgUnitInfo workDept = null;
			PositionMemberCollection positMems = getPositionMemberByUser();
					
			if (positMems == null)
				return workDept;
			
			for (int i = 0; i < positMems.size(); i++) {
				if (positMems.get(i).isIsPrimary()) {
					workDept = positMems.get(i).getPosition().getAdminOrgUnit();
					return workDept;
				}
			}
			return workDept;
		}
		/** 
		 * 始终是以角色的编码来进行判断的
		 * 各专业部门管理(编码:dep_admin)
		 * 财务综合会计主管(编码:cw_zhkj)
		 * 生产管理主管(编码:sc_scgl)
		 * 计量管理(编码：JL_GL)
		 * @return 是否有权限
		 * @param roleNumber 角色的编码
		 * 
		 */
		public static boolean judgeRole(String roleNumber) {
			
			boolean flag = false;
			
			if (SysContext.getSysContext().getCurrentUserInfo().getId() != null) {
				try {
					BOSUuid uuid = SysContext.getSysContext().getCurrentUserInfo().getId();
					// 得到当前人在系统中的角色的列表
					IUserRoleOrgManager empFacd = UserRoleOrgManagerFactory.getRemoteInstance();
					RoleCollection manager = empFacd.getAllUserRole(new ObjectUuidPK(uuid));
					for (int i = 0; i < manager.size(); i++) {
						RoleInfo info = (RoleInfo) manager.get(i);
						if (info.getNumber().equals(roleNumber))
							return flag = true;
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			return flag;
		}
		
		/**
		 * 判断当前人员是否属于部门中
		 * @param deptNumber 传入的部门编码
		 * @return 是否属于部门
		 * @throws EASBizException
		 * @throws BOSException
		 */
		public static boolean judgeDept(String deptNumber) throws EASBizException,
				BOSException {
			boolean flag = false;
			
			//	获得当前人所在的部门
			AdminOrgUnitCollection admins = PersonFacadeFactory.getRemoteInstance().getAdminOrgUnitByPerson(SysContext.getSysContext().getCurrentUserInfo().getPerson().getId());
			if ( admins != null && admins.size() > 0 ) {
				for (int i = 0; i < admins.size(); i++){
					if(admins.get(i).getNumber().equals(deptNumber)){
						return flag = true;
					}
				}
			}
			return flag;
		}
		/**
		 * 获得某职位上的一个人（主职和兼职）
		 * @param positionID 职位ID
		 * @return
		 * @throws BOSException
		 */
		public  static PersonInfo getPersonByPositionID(String positionID) throws BOSException{
			FilterInfo filter = new FilterInfo();
			EntityViewInfo viewInfo = new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("position.id", positionID));
			viewInfo.setFilter(filter);

			SelectorItemCollection sic = viewInfo.getSelector();
			sic.add(new SelectorItemInfo("person.id"));
			sic.add(new SelectorItemInfo("person.name"));
			sic.add(new SelectorItemInfo("person.number"));

			PositionMemberCollection pmColl = PositionMemberFactory
			.getRemoteInstance().getPositionMemberCollection(viewInfo);

			if(pmColl != null && pmColl.size() > 0){
				return pmColl.get(0).getPerson();
			}
			
			return null;
		}

		/**
		 * 获取主要职位人员
		 * 
		 * @param personId
		 * @return
		 * @throws BOSException
		 */
		public static PersonPositionInfo getPersonPositionInfoByPosition(String id) throws BOSException {
			FilterInfo filter = new FilterInfo();
			EntityViewInfo viewInfo = new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("primaryPosition.id", id));
			viewInfo.setFilter(filter);

			SelectorItemCollection sic = viewInfo.getSelector();
			sic.add(new SelectorItemInfo("person.id"));
			sic.add(new SelectorItemInfo("person.name"));
			sic.add(new SelectorItemInfo("person.number"));

			PersonPositionCollection positionColl = PersonPositionFactory.
			getRemoteInstance().getPersonPositionCollection(viewInfo);

			return positionColl.get(0);
		}

		/**
		 * 获取上级职位
		 * 
		 * @param id 职位ID
		 * @return
		 * @throws BOSException
		 */
		public static PositionInfo getSuperiorPosition(String positionId) throws BOSException {
			FilterInfo filter = new FilterInfo();
			EntityViewInfo viewInfo = new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("child.id", positionId));
			viewInfo.setFilter(filter);

			SelectorItemCollection sic = viewInfo.getSelector();
			sic.add(new SelectorItemInfo("level"));
			sic.add(new SelectorItemInfo("parent.id"));
			sic.add(new SelectorItemInfo("parent.name"));
			sic.add(new SelectorItemInfo("parent.number"));
			sic.add(new SelectorItemInfo("parent.isAudit"));

			PositionHierarchyCollection hierarchyColl = PositionHierarchyFactory.getRemoteInstance().getPositionHierarchyCollection(viewInfo);
			
			if(hierarchyColl == null || hierarchyColl.size() < 1){
				return null;
			}
			
			PositionInfo sPosition = hierarchyColl.get(0).getParent();
			
			if(positionId.equals(sPosition.getId().toString())){
				return null; //最高级职位，避免死循环
			}
			return sPosition;
		}
		/**
		* 初始化申请人F7
		* 
		* @param coreui
		*            所属UI
		* @param prmtPerson
		*            申请人F7控件
		* @param hasCUFilter
		*            是否CU隔离
		* @throws BOSException
		* @throws Exception
		*/
		public static void initPersonF7(CoreUIObject coreui,
			KDBizPromptBox prmtPerson, boolean hasCUFilter)throws BOSException, Exception {
			prmtPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
			HashMap map = new HashMap();
			map.put("All_Admins", "YES");
			map.put("DEFAULT_SHOW_ALL", "AAA");
			
			PersonPromptBox select = new PersonPromptBox(coreui, map);
			prmtPerson.setSelector(select);
			prmtPerson.setHasCUDefaultFilter(hasCUFilter);
			prmtPerson.setDisplayFormat("$name$");
			prmtPerson.setEditFormat("$number$");
			prmtPerson.setCommitFormat("$number$");
			FilterInfo filter = new FilterInfo();
			PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
			filter = new FilterInfo();
			EntityViewInfo viewInfo = new EntityViewInfo();
			if(person!=null)
				filter.getFilterItems().add(new FilterItemInfo("id", person.getId().toString()));
			viewInfo.setFilter(filter);
			prmtPerson.setEntityViewInfo(viewInfo);
		}
		/**
		 * 初始化申请人控件
		 * 
		 * @param prmtproposer
		 *            申请人控件名称
		 * @throws BOSException
		 */
		public static void initProposer(KDBizPromptBox prmtproposer) throws BOSException {
			prmtproposer.setQueryInfo("com.kingdee.eas.basedata.person.app.AllPersonQuery");
			prmtproposer.setVisible(true);
			prmtproposer.setEditable(true);
			prmtproposer.setDisplayFormat("$name$");
			prmtproposer.setEditFormat("$number$");
			prmtproposer.setCommitFormat("$number$");

			FilterInfo filter = new FilterInfo();
			PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
			filter = new FilterInfo();
			EntityViewInfo viewInfo = new EntityViewInfo();
			if(person!=null)
				filter.getFilterItems().add(new FilterItemInfo("id", person.getId().toString()));
			viewInfo.setFilter(filter);
			prmtproposer.setEntityViewInfo(viewInfo);
			prmtproposer.setValue(person);
		}
		
		
		/**
		 * 通过申请人初始化部门编号字段，当申请人只存在一个部门时，则自动带出该部门，如果有多个部门时，则提供在此范围内选择。
		 * @author  
		 * @param prmtDeptNum
		 * @param proposerID
		 * @throws BOSException
		 */
		public static AdminOrgUnitInfo initDeptByProposer(KDBizPromptBox prmtDeptNum,String proposerID) throws BOSException {
			FilterInfo filter = new FilterInfo();
			EntityViewInfo viewInfo = new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("person.id", proposerID));
			viewInfo.setFilter(filter);
			SelectorItemCollection sic = viewInfo.getSelector();
			sic.add(new SelectorItemInfo("position.adminOrgUnit.id"));
			PositionMemberCollection pmColl = PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(viewInfo);

			Set set = new HashSet();
			if (pmColl != null && pmColl.size() > 0) {
				for (int i = 0, size = pmColl.size(); i < size; i++) {
					set.add(pmColl.get(i).getPosition().getAdminOrgUnit().getId().toString());
				}
			} else {
				set.add("111111111111111111111111111=");
			}
			filter = new FilterInfo();
			viewInfo = new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
			viewInfo.setFilter(filter);

			AdminOrgUnitCollection coll = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitCollection(viewInfo);
			AdminOrgUnitInfo defOrgUnit = null;
			set = new HashSet();
			if (coll != null && coll.size() > 0) {
				if (coll.size() == 1) {
					defOrgUnit = coll.get(0);
					set.add(coll.get(0).getId().toString());
				} else {
					for (int i = 0, size = coll.size(); i < size; i++) {
						set.add(coll.get(i).getId().toString());
					}
				}
			}

			filter = new FilterInfo();
			viewInfo = new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
			viewInfo.setFilter(filter);

			prmtDeptNum.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
			prmtDeptNum.setDisplayFormat("$name$");
			prmtDeptNum.setEditFormat("$number$");
			prmtDeptNum.setCommitFormat("$number$");
			prmtDeptNum.setEntityViewInfo(viewInfo);
			
			prmtDeptNum.setValue(defOrgUnit);
			return defOrgUnit;
		}
		
		/**
		 * 检查当前登录用户是否有对应职员
		 */
		public static void checkUserHavePerson() {
			PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
			if (person == null) {
				MsgBox.showError("该用户没有对应职员，不能执行该操作！");
				SysUtil.abort();
			}
		}
		public static void main(String[] args){
		}
		
}
