package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.ISystemStatusCtrol;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class SellProjectControllerBean extends
		AbstractSellProjectControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SellProjectControllerBean");

	/**
	 * 20110628,youzhen 注释,不在此验证
	 * 
	 * protected void _checkNameDup(Context ctx, IObjectValue model) throws
	 * BOSException, EASBizException { SellProjectInfo sellProject =
	 * (SellProjectInfo) model; FilterInfo filter = new FilterInfo();
	 * FilterItemInfo filterItem = new FilterItemInfo(
	 * IFWEntityStruct.dataBase_Number, sellProject.getNumber(),
	 * CompareType.EQUALS); filter.getFilterItems().add(filterItem); if
	 * (sellProject.getId() != null) { filterItem = new
	 * FilterItemInfo(IFWEntityStruct.coreBase_ID, sellProject.getId(),
	 * CompareType.NOTEQUALS); filter.getFilterItems().add(filterItem); }
	 * filterItem = new FilterItemInfo("orgUnit.id", sellProject.getOrgUnit()
	 * .getId().toString()); filter.getFilterItems().add(filterItem);
	 * EntityViewInfo view = new EntityViewInfo(); view.setFilter(filter);
	 * SorterItemCollection sorter = new SorterItemCollection(); sorter.add(new
	 * SorterItemInfo(IFWEntityStruct.coreBase_ID));
	 * 
	 * if (super._exists(ctx, filter)) { String number =
	 * this._getPropertyAlias(ctx, sellProject, IFWEntityStruct.dataBase_Number)
	 * + sellProject.getNumber(); throw new
	 * EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number
	 * }); } }
	 * 
	 * protected void _checkNumberDup(Context ctx, IObjectValue model) throws
	 * BOSException, EASBizException { SellProjectInfo sellProject =
	 * (SellProjectInfo) model; FilterInfo filter = new FilterInfo();
	 * FilterItemInfo filterItem = new FilterItemInfo(
	 * IFWEntityStruct.dataBase_Name, sellProject.getName(),
	 * CompareType.EQUALS); filter.getFilterItems().add(filterItem); if
	 * (sellProject.getId() != null) { filterItem = new
	 * FilterItemInfo(IFWEntityStruct.coreBase_ID, sellProject.getId(),
	 * CompareType.NOTEQUALS); filter.getFilterItems().add(filterItem); }
	 * filterItem = new FilterItemInfo("orgUnit.id", sellProject.getOrgUnit()
	 * .getId().toString()); filter.getFilterItems().add(filterItem);
	 * EntityViewInfo view = new EntityViewInfo(); view.setFilter(filter);
	 * SorterItemCollection sorter = new SorterItemCollection(); sorter.add(new
	 * SorterItemInfo(IFWEntityStruct.coreBase_ID));
	 * 
	 * if (super._exists(ctx, filter)) { String name =
	 * this._getPropertyAlias(ctx, sellProject, IFWEntityStruct.dataBase_Name) +
	 * sellProject.getName(); throw new
	 * EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
	 * } }
	 **/
	/**
	 * 重写校验: 20110628, youzhen
	 */
	protected void addnewCheck(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo) model);
		SellProjectInfo project = (SellProjectInfo) model;
		IObjectPK pk = super._submit(ctx, project);
		_projectDataUpdate(ctx, project);
		return pk;

	}

	protected IObjectCollection _getBaseCollection(Context ctx, Set idSet)
			throws BOSException {
		SellProjectCollection sellProColl = new SellProjectCollection();
		if (idSet == null || idSet.size() == 0)
			return sellProColl;

		String idsStr = "";
		Iterator iter = idSet.iterator();
		while (iter.hasNext()) {
			idsStr += ",'" + iter.next() + "'";
		}
		idsStr = idsStr.replaceFirst(",", "");

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select * ");
		sqlBuf.append(" from t_she_sellProject where fid in (" + idsStr + ") ");

		IRowSet rs = DbUtil.executeQuery(ctx, sqlBuf.toString());
		try {
			while (rs.next()) {
				SellProjectInfo sellProInfo = new SellProjectInfo();
				sellProInfo.setId(BOSUuid.read(rs.getString("Fid")));
				String FNumber = rs.getString("FNumber");
				sellProInfo.setNumber(FNumber);
				String FName = rs.getString("FName_l2");
				sellProInfo.setName(FName);
				String FOrgUnitID = rs.getString("FOrgUnitID");
				FullOrgUnitInfo orgUnitInfo = new FullOrgUnitInfo();
				orgUnitInfo.setId(BOSUuid.read(FOrgUnitID));
				sellProInfo.setOrgUnit(orgUnitInfo);
				String FProjectID = rs.getString("FProjectID");
				CurProjectInfo curProInfo = new CurProjectInfo();
				curProInfo.setId(BOSUuid.read(FProjectID));
				sellProInfo.setProject(curProInfo);
				sellProInfo.setTerraNumber(rs.getString("FTerraNumber"));
				sellProInfo.setTerraArea(rs.getBigDecimal("FTerraArea"));
				String FBuildingPropertyID = rs
						.getString("FBuildingPropertyID");
				BuildingPropertyInfo buildProInfo = new BuildingPropertyInfo();
				buildProInfo.setId(BOSUuid.read(FBuildingPropertyID));
				sellProInfo.setBuildingProperty(buildProInfo);
				sellProInfo.setTerraLicence(rs.getString("FTerraLicence"));
				sellProInfo.setTermBegin(rs.getDate("termBegin"));
				sellProInfo.setTermEnd(rs.getDate("FTermEnd"));
				sellProInfo.setTerraUse(rs.getString("FTerraUse"));
				// ......
				sellProInfo.setIsForSHE(rs.getBoolean("FIsForSHE"));
				sellProInfo.setIsForTen(rs.getBoolean("FIsForTen"));
				sellProInfo.setIsForPPM(rs.getBoolean("FIsForPPM"));
				sellProInfo.setBalanceEndDate(rs.getDate("FBalanceEndDate"));
				sellProColl.add(sellProInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sellProColl;
	}

	protected IObjectValue _getBaseValue(Context ctx, String idStr)
			throws BOSException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select * ");
		sqlBuf.append(" from t_she_sellProject where fid ='" + idStr + "'");
		SellProjectInfo sellProInfo = new SellProjectInfo();
		sellProInfo.setId(BOSUuid.read(idStr));
		IRowSet rs = DbUtil.executeQuery(ctx, sqlBuf.toString());
		try {
			if (rs.next()) {
				String FNumber = rs.getString("FNumber");
				sellProInfo.setNumber(FNumber);
				String FName = rs.getString("FName_l2");
				sellProInfo.setName(FName);
				String FOrgUnitID = rs.getString("FOrgUnitID");
				FullOrgUnitInfo orgUnitInfo = new FullOrgUnitInfo();
				orgUnitInfo.setId(BOSUuid.read(FOrgUnitID));
				sellProInfo.setOrgUnit(orgUnitInfo);
				String FProjectID = rs.getString("FProjectID");
				CurProjectInfo curProInfo = new CurProjectInfo();
				curProInfo.setId(BOSUuid.read(FProjectID));
				sellProInfo.setProject(curProInfo);
				sellProInfo.setTerraNumber(rs.getString("FTerraNumber"));
				sellProInfo.setTerraArea(rs.getBigDecimal("FTerraArea"));
				String FBuildingPropertyID = rs
						.getString("FBuildingPropertyID");
				BuildingPropertyInfo buildProInfo = new BuildingPropertyInfo();
				buildProInfo.setId(BOSUuid.read(FBuildingPropertyID));
				sellProInfo.setBuildingProperty(buildProInfo);
				sellProInfo.setTerraLicence(rs.getString("FTerraLicence"));
				sellProInfo.setTermBegin(rs.getDate("termBegin"));
				sellProInfo.setTermEnd(rs.getDate("FTermEnd"));
				sellProInfo.setTerraUse(rs.getString("FTerraUse"));
				// ......
				sellProInfo.setIsForSHE(rs.getBoolean("FIsForSHE"));
				sellProInfo.setIsForTen(rs.getBoolean("FIsForTen"));
				sellProInfo.setIsForPPM(rs.getBoolean("FIsForPPM"));
				sellProInfo.setBalanceEndDate(rs.getDate("FBalanceEndDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sellProInfo;
	}

	protected boolean _allEndInit(Context ctx, String orgUnitId,
			UserInfo userInfo) throws BOSException, EASBizException {
		// 找出实体财务组织或者实体销售组织下用户能看到的销售项目
		// List projectList = new ArrayList();//code...
		// PeriodInfo periodInfo =
		// SystemStatusCtrolFactory.getLocalInstance(ctx)
		// .getStartPeriod(1818,orgUnitId);
		// for(int i=0;i<projectList.size();i++)
		// {
		// String projectID = (String)projectList.get(i);
		// SelectorItemCollection sels2 = new SelectorItemCollection();
		// sels2.add("*");
		// SellProjectInfo project = SellProjectFactory.getLocalInstance(ctx)
		// .getSellProjectInfo(new ObjectUuidPK(projectID),sels2);
		// if(project.isIsEndInit())
		// {
		// continue;
		// }else
		// {
		// project.setIsEndInit(true);
		// project.setSaleTerm(new Integer(periodInfo.getNumber()).toString());
		// project.setSaleNowTerm(new
		// Integer(periodInfo.getNumber()).toString());
		// project.setDisposePerson(userInfo);
		// project.setDisposeDate(new Date());
		// SelectorItemCollection sels = new SelectorItemCollection();
		// sels.add("isEndInit");
		// sels.add("saleTerm");
		// sels.add("saleNowTerm");
		// sels.add("disposePerson.id");
		// sels.add("disposeDate");
		// SellProjectFactory.getLocalInstance(ctx).updatePartial(project,
		// sels);
		// }
		// }
		// //若实体财务组织下存在一条结束初始化的销售项目，
		// //则系统状态处对应实体财务组织的启用更新为是
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filterInfo = new FilterInfo();
		// filterInfo.getFilterItems().add(new FilterItemInfo("isEndInit", new
		// Boolean(true)));
		// filterInfo.getFilterItems().add(new
		// FilterItemInfo("orgUnit.id",orgUnitId));
		// view.setFilter(filterInfo);
		// SellProjectCollection projectColl =
		// SellProjectFactory.getLocalInstance
		// (ctx).getSellProjectCollection(view);
		// if(projectColl.size()>0)
		// {
		// CompanyOrgUnitInfo companyInfo =
		// GlUtils.getCurrentCompany(ctx,orgUnitId,null,false);
		// ISystemStatusCtrol sysStatus =
		// SystemStatusCtrolFactory.getLocalInstance(ctx);
		// sysStatus.start(SystemEnum.FDC_SHE, companyInfo,true);
		// }
		return true;
	}

	protected boolean _allUnInit(Context ctx, String orgUnitId,
			UserInfo userInfo) throws BOSException, EASBizException {
		// 找出实体财务组织或者实体销售组织下用户能看到的销售项目
		List projectList = new ArrayList();// code...
		return true;

	}

	protected boolean _endInit(Context ctx, List projectIds, String orgUnitId,
			UserInfo userInfo) throws BOSException, EASBizException {
		boolean boo = false;
		// 当前期间
		PeriodInfo periodInfo = SystemStatusCtrolUtils.getCurrentPeriod(ctx,
				SystemEnum.FDC_SHE, new ObjectUuidPK(orgUnitId));
		for (int i = 0; i < projectIds.size(); i++) {
			// 对应的项目结束初始化
			String projectID = (String) projectIds.get(i);
			SelectorItemCollection sels2 = new SelectorItemCollection();
			sels2.add("*");
			SellProjectInfo project = SellProjectFactory.getLocalInstance(ctx)
					.getSellProjectInfo(new ObjectUuidPK(projectID), sels2);
			if (project.isIsEndInit()) {
				continue;
			} else {
				project.setIsEndInit(true);
				// 把财务组织的当前期间设为项目的启用期间和当前期间
				project.setSaleTerm(new Integer(periodInfo.getNumber())
						.toString());
				project.setSaleNowTerm(new Integer(periodInfo.getNumber())
						.toString());
				project.setDisposePerson(userInfo);
				project.setDisposeDate(new Date());
				project.setSalePeriod(periodInfo);
				project.setSaleNowPeriod(periodInfo);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("isEndInit");
				sels.add("saleTerm");
				sels.add("saleNowTerm");
				sels.add("disposePerson.id");
				sels.add("disposeDate");
				sels.add("salePeriod.id");
				sels.add("saleNowPeriod.id");
				SellProjectFactory.getLocalInstance(ctx).updatePartial(project,
						sels);
				boo = true;
			}
		}
		// 能操作的组织只能是实体销售组织和虚体销售组织的实体财务组织
		SaleOrgUnitInfo orgUnitInfo = SaleOrgUnitFactory.getLocalInstance(ctx)
				.getSaleOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		Set orgUnitSet = new HashSet();
		try {
			orgUnitSet = getLeafCompanyIdSet(ctx, orgUnitInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 若实体财务组织下存在一条结束初始化的销售项目，
		// 则系统状态处对应实体财务组织的启用更新为是
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEndInit", new Boolean(true)));
		if (orgUnitSet.size() != 0) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", orgUnitSet,
							CompareType.INCLUDE));
		} else {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", orgUnitId));
		}
		view.setFilter(filterInfo);
		SellProjectCollection projectColl = SellProjectFactory
				.getLocalInstance(ctx).getSellProjectCollection(view);
		if (projectColl.size() > 0) {
			CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx,
					orgUnitId, null, false);
			ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory
					.getLocalInstance(ctx);
			sysStatus.start(SystemEnum.FDC_SHE, companyInfo, true);
		}
		return boo;
	}

	protected boolean _unInit(Context ctx, List projectIds, String orgUnitId,
			UserInfo userInfo) throws BOSException, EASBizException {
		boolean boo = false;
		for (int i = 0; i < projectIds.size(); i++) {
			// 对应的项目反初始化
			String projectID = (String) projectIds.get(i);
			SellProjectInfo project = SellProjectFactory.getLocalInstance(ctx)
					.getSellProjectInfo(new ObjectUuidPK(projectID));
			project.setIsEndInit(false);
			project.setSaleTerm(null);
			project.setSaleNowTerm(null);
			project.setDisposePerson(null);
			project.setDisposeDate(null);
			project.setSalePeriod(null);
			project.setSaleNowPeriod(null);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isEndInit");
			sels.add("saleTerm");
			sels.add("saleNowTerm");
			sels.add("disposePerson.id");
			sels.add("disposeDate");
			sels.add("salePeriod.id");
			sels.add("saleNowPeriod.id");
			SellProjectFactory.getLocalInstance(ctx).updatePartial(project,
					sels);
			boo = true;
		}
		// 能操作的组织只能是实体销售组织和虚体销售组织的实体财务组织
		SaleOrgUnitInfo orgUnitInfo = SaleOrgUnitFactory.getLocalInstance(ctx)
				.getSaleOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		Set orgUnitSet = new HashSet();
		try {
			orgUnitSet = getLeafCompanyIdSet(ctx, orgUnitInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 若实体财务组织下不存在任何一条结束初始化的销售项目，
		// 则系统状态处对应实体财务组织的启用更新为否
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEndInit", new Boolean(true)));
		if (orgUnitSet.size() != 0) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", orgUnitSet,
							CompareType.INCLUDE));
		} else {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", orgUnitId));
		}
		view.setFilter(filterInfo);
		SellProjectCollection projectColl = SellProjectFactory
				.getLocalInstance(ctx).getSellProjectCollection(view);
		if (projectColl.size() == 0) {
			CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx,
					orgUnitId, null, false);
			ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory
					.getLocalInstance(ctx);
			sysStatus.start(SystemEnum.FDC_SHE, companyInfo, false);
		}
		return boo;
	}

	/**
	 * 房地产系统进入下一期
	 * 
	 * @param ctx
	 * @param comId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _nextSystem(Context ctx, String comId, UserInfo userInfo)
			throws BOSException, EASBizException {
		// 如果当前组织的全部已经月结，那么系统状态控制ＦＤＣ系统进入下一期间
		PeriodInfo period = SystemStatusCtrolUtils.getCurrentPeriod(ctx,
				SystemEnum.FDC_SHE, new ObjectUuidPK(comId));

		// 找到当前用户在该组织下能看到的销售项目
		String str = MarketingUnitFactory.getLocalInstance(ctx)
				.getPermitSellProjectIdSql(userInfo);

		// 如果项目当前期间都不在公司当前期间内的话，说明都已经月结了。系统状态进入下一期间
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select fid from t_she_sellProject where FSaleNowPeriodID ='"
						+ period.getId().toString()
						+ "' and fid in ("
						+ str
						+ ")");
		IRowSet rs2 = builder.executeQuery();
		try {
			if (rs2 == null || !rs2.next()) {
				ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory
						.getLocalInstance(ctx);
				sysStatus.nextPeriod(SystemEnum.FDC_SHE, GlUtils
						.getCurrentCompany(ctx, comId, null, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}

	protected void _preSystem(Context ctx, String comId) throws BOSException,
			EASBizException {
		ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory
				.getLocalInstance(ctx);
		sysStatus.prePeriod(SystemEnum.FDC_SHE, GlUtils.getCurrentCompany(ctx,
				comId, null, false));
	}

	// 获取指定组织下的所有子公司id集合 (如果当前组织是实体组织，则包含自己)
	private Set getLeafCompanyIdSet(Context ctx, SaleOrgUnitInfo unitInfo)
			throws BOSException, SQLException {
		Set companyIdSet = new HashSet();
		if (unitInfo == null)
			return companyIdSet;

		StringBuffer sql = new StringBuffer();
		sql.append("select fid from  t_org_sale where ").append(
				" (FNumber = '" + unitInfo.getNumber() + "'").append(
				" or FLongNumber like '" + unitInfo.getLongNumber() + "!%') ")
				.append(" and FisLeaf =1 ");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		while (rs.next()) {
			companyIdSet.add(rs.getString("fid"));
		}
		return companyIdSet;
	}

	// 这个是为了当新增了一个新的档案的时候如果没有批量处理的话，项目就需要一个个重新保存
	// 一遍才能把新的档案保存起来
	protected void _allProjectDataUpdate(Context ctx) throws BOSException,
			EASBizException {
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("isForSHE",Boolean.TRUE));
		// view.setFilter(filter);
		SellProjectCollection sellProjectColl = SellProjectFactory
				.getLocalInstance(ctx).getSellProjectCollection();
		if (sellProjectColl.size() > 0) {
			for (int i = 0; i < sellProjectColl.size(); i++) {
				SellProjectInfo project = sellProjectColl.get(i);
				SellProjectFactory.getLocalInstance(ctx).submit(project);
			}
		}
	}

	// 单个项目管控档案升级，在项目保存时把需要管控的档案保存起来
	protected void _projectDataUpdate(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SellProjectInfo sellProject = (SellProjectInfo) model;
		// 在新建项目的时候默认把所有档案写入项目档案分录
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.id", sellProject.getId()
						.toString()));
		view.setFilter(filter);
		ProjectArchivesEntryCollection projectOldArchColl = ProjectArchivesEntryFactory
				.getLocalInstance(ctx).getProjectArchivesEntryCollection(view);
		CoreBaseCollection coreColl = new CoreBaseCollection();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select * from t_she_projectDataBase ");
		IRowSet projectSet = builder.executeQuery(ctx);
		try {
			while (projectSet.next()) {
				boolean isInclude = false;
				ProjectArchivesEntryInfo projectArchInfo = new ProjectArchivesEntryInfo();
				String name = projectSet.getString("fname_l2");
				if (projectOldArchColl.size() > 0) {
					// 找出项目原来的档案，如果原来存在就不重新保存
					for (int i = 0; i < projectOldArchColl.size(); i++) {
						ProjectArchivesEntryInfo projectOldArchInfo = projectOldArchColl
								.get(i);
						if (name.equals(projectOldArchInfo.getArchivesName())) {
							isInclude = true;
							break;
						}
					}

				}
				if (!isInclude) {
					projectArchInfo.setArchivesName(name);
					projectArchInfo.setName(projectSet.getString("FTable"));
					projectArchInfo.setScopeType(ScopeTypeEnum
							.getEnum((String) projectSet
									.getString("FDefScopeType")));
					projectArchInfo.setBaseDataProperty(BaseDataPropertyEnum
							.getEnum((String) projectSet
									.getString("FDefBaseDataPro")));
					projectArchInfo
							.setArchivesType(ArchivesTypeEnum
									.getEnum((String) projectSet
											.getString("FArchives")));
					projectArchInfo.setSellProject(sellProject);
					coreColl.add(projectArchInfo);
					// projectOldArchColl.add(projectArchInfo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ProjectArchivesEntryFactory.getLocalInstance(ctx).addnew(coreColl);
		// sellProject.getProjectArchEntrys().clear();
		// sellProject.getProjectArchEntrys().addCollection(projectOldArchColl);
	}

	protected void _updateToSellProject(Context ctx, BOSUuid id, String number,
			String name) throws BOSException, EASBizException {

		/*
		 * SellProjectInfo info = SellProjectFactory.getLocalInstance(ctx)
		 * .getSellProjectInfo( "select id,number,name,importID where number='"
		 * + number + "' and name='" + name + "'"); if (info != null) {
		 * FDCSQLBuilder sql = new FDCSQLBuilder(ctx); String sqlStr =
		 * "update t_she_sellproject set fisforshe=1 where fid='" +
		 * info.getId().toString() + "'"; sql.appendSql(sqlStr); sql.execute();
		 * } else { throw new BOSException("记录已经不存在!"); }
		 */

	}

	protected void _updateToSHEProject(Context ctx, BOSUuid id,
			BOSUuid orgUnitID, String longNumber) throws BOSException,
			EASBizException {
		String [] temp = longNumber.split("!");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", temp[0].toString() + "%",
						CompareType.LIKE));
		/*filter.getFilterItems().add(
				new FilterItemInfo("id", id.toString(), CompareType.EQUALS));*/
		view.setFilter(filter);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		view.setSelector(coll);
		SellProjectCollection sellProjectColl = SellProjectFactory
				.getLocalInstance(ctx).getSellProjectCollection(view);

		if (sellProjectColl != null && sellProjectColl.size() > 0) {
			try {
				String sql = "update t_she_sellproject set fisforshe=1,FOrgUnitID=?,fimportid=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < sellProjectColl.size(); i++) {
					SellProjectInfo info = (SellProjectInfo) sellProjectColl
							.get(i);
					sqlBuilder.addParam(orgUnitID.toString());
					sqlBuilder.addParam(id.toString());
					sqlBuilder.addParam(info.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (Exception ex) {
				logger.error(ex.getMessage() + "更新项目信息失败!");
				throw new BOSException(ex.getMessage() + "更新项目信息失败!");
			}

		}
	}

	protected ArrayList _getSellProTreeNodes(Context ctx, String type)
			throws BOSException, EASBizException {
		ArrayList muSellProNodes = new ArrayList();
		
		// 项目节点
		OrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx);
		String orgNumber = org.getLongNumber();
		Map sellProNodeMap = new HashMap(); 
		Map sellIdMap = new HashMap(); 
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		//builder.appendSql("select * from (");
		//builder.appendSql("select org.flongnumber,sp.fparentid,sp.fid,sp.fname_l2,sp.fnumber,sp.forgUnitid,sp.fisForSHE,sp.fisForTen,sp.fisForPPM from t_she_sellProject sp inner join T_ORG_BaseUnit org on sp.forgunitid = org.fid" );
		//builder.appendSql(" union ");
		//builder.appendSql("select org.flongnumber,sellPro.fparentid,sellPro.fid,sellPro.fname_l2,sellPro.fnumber,shareOrg.forgUnitid,sellPro.fisForSHE,sellPro.fisForTen,sellPro.fisForPPM from T_SHE_ShareOrgUnit shareOrg ");
		//builder.appendSql("inner join t_she_sellProject sellPro on shareOrg.FHeadID = sellPro.fid inner join T_ORG_BaseUnit org on shareOrg.forgUnitid = org.fid ");
		//builder.appendSql(") AAAA where AAAA.fisforshe=1  and AAAA.flongnumber like  '"+ orgNumber+"%'  and AAAA.fparentid is null order by AAAA.fnumber asc " );
		
		builder.appendSql(" select * ");
		builder.appendSql("   from (select org.flongnumber as flongnumber, ");
		builder.appendSql(" sp.fparentid as fparentid, ");
		builder.appendSql(" sp.fid as fid, ");
		builder.appendSql("  sp.fname_l2 as fname, ");
		builder.appendSql(" sp.fnumber as fnumber, ");
		builder.appendSql("  sp.forgUnitid as forgUnitid, ");
		builder.appendSql("  sp.fisForSHE as fisForSHE, ");
		builder.appendSql("  sp.fisForTen as fisForTen, ");
		builder.appendSql("  sp.fisForPPM as fisForPPM ");
		builder.appendSql("  from t_she_sellProject sp ");
		builder
				.appendSql(" inner join T_ORG_BaseUnit org on sp.forgunitid = org.fid ");
		builder.appendSql("  union ");
		builder.appendSql("  select org.flongnumber as flongnumber, ");
		builder.appendSql(" sellPro.fparentid as fparentid, ");
		builder.appendSql("  sellPro.fid as fid, ");
		builder.appendSql("  sellPro.fname_l2 as fname, ");
		builder.appendSql("  sellPro.fnumber as fnumber, ");
		builder.appendSql("  shareOrg.forgUnitid as forgUnitid, ");
		builder.appendSql("  sellPro.fisForSHE as fisForSHE, ");
		builder.appendSql("  sellPro.fisForTen as fisForTen, ");
		builder.appendSql("  sellPro.fisForPPM as fisForPPM ");
		builder.appendSql("  from T_SHE_ShareOrgUnit shareOrg ");
		builder
				.appendSql("  inner join t_she_sellProject sellPro on shareOrg.FHeadID = ");
		builder.appendSql("   sellPro.fid ");
		builder
				.appendSql("  inner join T_ORG_BaseUnit org on shareOrg.forgUnitid = org.fid) as AAAA ");
		builder.appendSql(" where AAAA.fisForSHE = 1 ");
		builder.appendSql("  and AAAA.flongnumber like '" + orgNumber + "'");
		builder.appendSql("  and AAAA.fparentid is null ");
		builder.appendSql(" order by AAAA.fnumber asc ");
		
		IRowSet tableSet = builder.executeQuery();
		try {
			while (tableSet.next()) {
				String FID = tableSet.getString("fid");
				String FName = tableSet.getString("fname");
				String FNumber = tableSet.getString("fnumber");
				String ForgUnitId = tableSet.getString("forgUnitid");
				SellProjectInfo thisInfo = new SellProjectInfo();
				thisInfo.setId(BOSUuid.read(FID));
				thisInfo.setName(FName);
				thisInfo.setNumber(FNumber);
				FullOrgUnitInfo orgUnitInfo = new FullOrgUnitInfo();
				orgUnitInfo.setId(BOSUuid.read(ForgUnitId));
				thisInfo.setOrgUnit(orgUnitInfo);
				thisInfo.setIsForSHE(tableSet.getBoolean("fisForSHE"));
				thisInfo.setIsForTen(tableSet.getBoolean("fisForTen"));
				thisInfo.setIsForPPM(tableSet.getBoolean("fisForPPM"));
				DefaultKingdeeTreeNode newNode = new DefaultKingdeeTreeNode(thisInfo.getName());
				newNode.setUserObject(thisInfo);
				if(newNode!=null){
					sellIdMap.put(FID, newNode);
				}
				if(!sellProNodeMap.containsKey(FID) ){
					sellProNodeMap.put(FID, newNode);
				}
			}
		} catch (Exception e) {
			logger.equals(e.getMessage() + "项目资料失败!");
		}

		// 这里返回的是一级节点，要继续挂下级节点
		KDTree tree = getAllSellprojectTree(ctx);
		Map idSPMap = (HashMap) tree.getUserObject();
		for (Iterator it = sellIdMap.keySet().iterator(); it.hasNext();) {
			String id = (String) it.next();
			// newNode
			DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode) sellIdMap
					.get(id);
			DefaultKingdeeTreeNode oriNode = (DefaultKingdeeTreeNode) idSPMap
					.get(id);
			cloneTree(newNode, oriNode);
			muSellProNodes.add(newNode);
		}
		return muSellProNodes;
	}

	/**
	 * 克隆所选树节点，包含子节点
	 */
	private void cloneTree(DefaultKingdeeTreeNode newNode,
			DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriNode
					.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}

	/**
	 * 获取完整的项目树
	 * 
	 * @throws BOSException
	 * 
	 */
	private KDTree getAllSellprojectTree(Context ctx) throws BOSException {
		Map idSPMap = new HashMap();
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode("KDTree");
		KDTree tree = new KDTree(new KingdeeTreeModel(root));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo sFilter = new FilterInfo();
		sFilter.getFilterItems().add(
				new FilterItemInfo("isForSHE", Boolean.TRUE));
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.getSorter().add(new SorterItemInfo("level"));
		sorter.getSorter().add(new SorterItemInfo("number"));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("parent.id");
		sel.add("name");
		view.setSorter(sorter);
		view.setSelector(sel);
		view.setFilter(sFilter);
		SellProjectCollection selColl = SellProjectFactory
				.getLocalInstance(ctx).getSellProjectCollection(view);
		// 在根节点上挂子节点
		DefaultKingdeeTreeNode parentNode = null;
		for (int i = 0; i < selColl.size(); i++) {
			SellProjectInfo sellProjectInfo = selColl.get(i);
			DefaultKingdeeTreeNode newNode = new DefaultKingdeeTreeNode(
					sellProjectInfo);
			idSPMap.put(sellProjectInfo.getId().toString(), newNode);
			String parentId = sellProjectInfo.getParent() != null ? sellProjectInfo
					.getParent().getId().toString()
					: null;
			if (idSPMap.containsKey(parentId)) {
				parentNode = (DefaultKingdeeTreeNode) idSPMap.get(parentId);
				parentNode.add(newNode);
			} else {
				root.add(newNode);
			}
		}
		// 项目与ID的mapping
		tree.setUserObject(idSPMap);
		return tree;

	}
	
	public void updatePartial(Context ctx, CoreBaseInfo model,
			SelectorItemCollection selector) throws BOSException,
			EASBizException {
		super.updatePartial(ctx, model, selector);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		this.isReferenced(ctx, pk);
		super._delete(ctx, pk);
	}

	protected void _updateDeleteStatus(Context ctx, BOSUuid billId)
	throws BOSException,EASBizException {
		
		String longNumber = "";
		
		//根据id拿到longnumber
		SellProjectInfo project = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select id,longnumber,parent.id where id='"+billId+"'");
		if(project!=null){
			longNumber = project.getLongNumber();
		}
		FilterInfo filter = null;
		if(project.getParent()!=null && project.getParent().getId()!=null){
			String[] temp = longNumber.split("!");
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < temp.length; i++) {
				sb.append("'");
				sb.append(temp[i]);
				sb.append("'");
				if(i!=temp.length-1){
					sb.append(",");
				}
			}
			
			if(sb.length()>0){
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("number", sb.toString(), CompareType.INNER));
			
			}else{
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("number", null, CompareType.EQUALS));
		
			}
			
		}else{
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber, CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber	+ "!%", CompareType.LIKE));
			filter.setMaskString("#0 or #1");
			
		}
		
		EntityViewInfo view = new EntityViewInfo();
		
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		view.setFilter(filter);
		view.setSelector(coll);
		SellProjectCollection projectColl = SellProjectFactory.getLocalInstance(ctx).getSellProjectCollection(
				view);
		
		for (int i = 0; i < projectColl.size(); i++) {
			 SellProjectInfo info= projectColl.get(i);
			 if(info!=null){
				 SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("isForSHE"));
					selector.add(new SelectorItemInfo("importID"));
					SellProjectInfo model = new SellProjectInfo();
					model.setId(info.getId());
					model.setIsForSHE(false);
					model.setImportID(null);
					SellProjectFactory.getLocalInstance(ctx).updatePartial(model, selector);
			 }
		}
		
	}

	protected void _deleteSellProject(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		String longNumber = "";

		// 根据id拿到longnumber
		SellProjectInfo project = SellProjectFactory.getLocalInstance(ctx)
				.getSellProjectInfo(
						"select id,longnumber where id='" + billId + "'");
		if (project != null) {
			longNumber = project.getLongNumber();
		}
		FilterInfo filter = null;

		filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("longNumber", longNumber,
								CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.setMaskString("#0 or #1");

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		view.setFilter(filter);
		view.setSelector(coll);
		SellProjectCollection projectColl = SellProjectFactory
				.getLocalInstance(ctx).getSellProjectCollection(view);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < projectColl.size(); i++) {
			SellProjectInfo info = projectColl.get(i);
			if (info != null) {
				sb.append("'");
				sb.append(info.getId().toString());
				sb.append("'");
				if(i!=projectColl.size()-1){
					sb.append(",");
				}
			}
			//靠，不这样写怎么更新父对象的是否叶子节点
			this.delete(ctx, new ObjectUuidPK(info.getId().toString()));
		}
		
		if(sb.length()>0){
			try {
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
//				sqlBuilder.addBatch("delete from t_she_sellproject where fid in "+"("+sb.toString()+")");
				sqlBuilder.addBatch("delete from T_SHE_ShareRoomModels where fheadid in "+"("+sb.toString()+")");
				sqlBuilder.addBatch("delete from T_SHE_RoomModel where FSellProjectID in "+"("+sb.toString()+")");
				sqlBuilder.executeBatch();
			} catch (Exception ex) {
				logger.error("删除户型失败！");
				throw new BOSException();
			}
		}
	}


	protected void _deleteProjectInSystem(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		String longNumber = "";

		// 根据id拿到longnumber
		SellProjectInfo project = SellProjectFactory.getLocalInstance(ctx)
				.getSellProjectInfo(
						"select id,longnumber,parent.id where id='" + billId
								+ "'");
		if (project != null) {
			longNumber = project.getLongNumber();
		}
		FilterInfo filter = null;
		SellProjectInfo rootProject = project.getParent();
		if (rootProject != null) {
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", project.getId().toString(), CompareType.EQUALS));


		} else {
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber,
							CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber + "!%",
							CompareType.LIKE));
			filter.setMaskString("#0 or #1");

		}

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		view.setFilter(filter);
		view.setSelector(coll);
		SellProjectCollection projectColl = SellProjectFactory
				.getLocalInstance(ctx).getSellProjectCollection(view);

		for (int i = 0; i < projectColl.size(); i++) {
			SellProjectInfo info = projectColl.get(i);
			if (info != null) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("isForSHE"));
				selector.add(new SelectorItemInfo("importID"));
				SellProjectInfo model = new SellProjectInfo();
				model.setId(info.getId());
				model.setIsForSHE(false);
				model.setImportID(null);
				SellProjectFactory.getLocalInstance(ctx).updatePartial(model,
						selector);
			}
		}

		//_deleteSellProject(ctx, billId);

	}

	protected void _updateRoomModelForChild(Context ctx, BOSUuid billId,
			List roomModelList) throws BOSException, EASBizException {
		if (roomModelList != null && roomModelList.size() > 0) {
			try {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < roomModelList.size(); i++) {
					sb.append("'");
					sb.append(roomModelList.get(i).toString());
					sb.append("'");
					if(i!=roomModelList.size()-1){
						sb.append(",");
					}
				}
				if(sb.length()>0){
					
					String sql  = "update T_SHE_RoomModel set FSellProjectID='"+billId+"' where fid in";
					sql = sql + "(" +sb.toString()+")";
					FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
					sqlBuilder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
					sqlBuilder.addBatch(sql);
					sqlBuilder.executeBatch();
				
				}
			} catch (Exception ex) {
				logger.error("更新户型失败！");
				throw new BOSException();
			}
		}
	}
}
