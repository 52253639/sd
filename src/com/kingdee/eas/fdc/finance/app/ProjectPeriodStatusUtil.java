package com.kingdee.eas.fdc.finance.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.ISystemStatusCtrol;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FIProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.IProjectPeriodStatus;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusCollection;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyFactory;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectPeriodStatusUtil {

	// 是否已经结束初始化
	public static boolean _isClosed(Context ctx, String projectId)
			throws BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("isClosed"));
		view.setFilter(filter);

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		ProjectPeriodStatusCollection col = iProjectCrrol
				.getProjectPeriodStatusCollection(view);

		if (col != null && col.size() > 0) {
			ProjectPeriodStatusInfo info = col.get(0);

			return info.isIsClosed();
		}

		return false;
	}

	// 是否已经关闭
	public static boolean _isEnd(Context ctx, String projectId)
			throws BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("isEnd"));
		view.setFilter(filter);

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		ProjectPeriodStatusCollection col = iProjectCrrol
				.getProjectPeriodStatusCollection(view);

		if (col != null && col.size() > 0) {
			ProjectPeriodStatusInfo info = col.get(0);

			return info.isIsEnd();
		}

		return false;
	}

	/**
	 * 自动保存一份月结状态
	 * 
	 * @param ctx
	 * @param pk
	 *            工程项目
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", pk.toString()));
		if (iProjectCrrol.exists(filter)) {
			return;
		}

		// 是否存在
		CurProjectInfo curProject = (CurProjectInfo) model;
		if (model == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isLeaf");
			sic.add("isEnabled");
			sic.add("startDate");
			sic.add("fullOrgUnit.id");
			curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(pk, sic);
		}

		if (!curProject.isIsLeaf() || !curProject.isIsEnabled()) {
			return;
		}

		ProjectPeriodStatusInfo info = new ProjectPeriodStatusInfo();
		info.setProject(curProject);
		info.setIsClosed(false);

		String comId = curProject.getFullOrgUnit().getId().toString();

		CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx, comId,
				null, false);

		// 获取期间
		PeriodInfo period = SystemStatusCtrolUtils.getCurrentPeriod(ctx,
				SystemEnum.FDC, companyInfo);
		;

		// 启用成本财务一体化
		boolean isIncorporation = FDCUtils.IsInCorporation(ctx, comId);
		if (isIncorporation) {

			// 当前组织成本与财务一体化，系统设置的系统状态控制中，还没有设置房地产系统启用期间。

			if (period == null) {
				throw new ProjectPeriodStatusException(
						ProjectPeriodStatusException.NOT_COM_START);
			}

			PeriodInfo startperiod = FDCUtils.fetchPeriod(ctx, companyInfo
					.getAccountPeriodType().getId().toString(), curProject
					.getStartDate());
			if (startperiod != null
					&& startperiod.getBeginDate().after(period.getBeginDate())) {
				period = startperiod;
			}
		} else {
			if (period == null) {
				period = FDCUtils.fetchPeriod(ctx, companyInfo
						.getAccountPeriodType().getId().toString(), curProject
						.getStartDate());
			}

			if (period == null) {
				period = PeriodUtils
						.getPeriodInfo(ctx, new Date(), companyInfo);
			}
		}

		// 已经启用
		boolean isStart = SystemStatusCtrolUtils.isStart(ctx, SystemEnum.FDC,
				companyInfo);

		info.setStartPeriod(period);
		info.setCostPeriod(period);
		info.setFinacialPeriod(period);
		info.setOrgUnit(curProject.getFullOrgUnit());
		info.setIsClosed(isStart);
		IObjectPK prPpk = ProjectPeriodStatusFactory.getLocalInstance(ctx)
				.addnew(info);

		// //获取系统状态期间
		// if(SystemStatusCtrolUtils.isStart(ctx,SystemEnum.FDC, new
		// ObjectUuidPK(comId))){
		// info.setIsClosed(true);
		// info.setId(BOSUuid.read(prPpk.toString()));
		// ProjectPeriodStatusFactory.getLocalInstance(ctx).update(prPpk,info);
		// }

	}

	public static void _close(Context ctx, IObjectPK pk, IObjectValue model,
			boolean isClose) throws BOSException, EASBizException {
		// 是否存在
		CurProjectInfo curProject = (CurProjectInfo) model;
		if (model == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isLeaf");
			sic.add("startDate");
			sic.add("fullOrgUnit.id");
			curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(pk);
		}
		if (!curProject.isIsLeaf()) {
			return;
		}

		String comId = curProject.getFullOrgUnit().getId().toString();
		// 启用成本财务一体化
		boolean isIncorporation = FDCUtils.IsInCorporation(ctx, comId);
		if (!isIncorporation) {
			return;
		}

		List projectIds = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();
		sic.add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", pk.toString()));
		view.setFilter(filter);

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		ProjectPeriodStatusCollection col = iProjectCrrol
				.getProjectPeriodStatusCollection(view);

		if (col != null && col.size() > 0) {
			ProjectPeriodStatusInfo info = col.get(0);

			projectIds.add(info.getId().toString());
			if (isClose) {
				iProjectCrrol.closeProject(projectIds);
			} else {
				iProjectCrrol.closeUnProject(projectIds);
			}
		}
	}

	//
	public static void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		// 是否存在
		CurProjectInfo curProject = (CurProjectInfo) model;
		if (model == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isLeaf");
			sic.add("startDate");
			sic.add("isEnabled");
			sic.add("fullOrgUnit.id");
			curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(pk);
		}
		if (!curProject.isIsLeaf() || !curProject.isIsEnabled()) {
			return;
		}

		String comId = curProject.getFullOrgUnit().getId().toString();
		// 启用成本财务一体化
		boolean isIncorporation = FDCUtils.IsInCorporation(ctx, comId);
		if (!isIncorporation) {
			return;
		}

		CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx, comId,
				null, false);
		// 获取期间;
		PeriodInfo period = SystemStatusCtrolUtils.getStartPeriod(ctx,
				SystemEnum.FDC, companyInfo);
		// 当前组织成本与财务一体化，系统设置的系统状态控制中，还没有设置房地产系统启用期间。
		if (period == null) {
			throw new ProjectPeriodStatusException(
					ProjectPeriodStatusException.NOT_COM_START);
		}
		PeriodInfo startperiod = FDCUtils.fetchPeriod(ctx, companyInfo
				.getAccountPeriodType().getId().toString(), curProject
				.getStartDate());
		if (startperiod != null
				&& period.getBeginDate().after(startperiod.getBeginDate())) {
			startperiod = period;
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", pk.toString()));
		// filter.getFilterItems().add(new FilterItemInfo("isClosed",new
		// Integer(1)));
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();
		sic.add("project.displayName");
		sic.add("startPeriod.id");
		sic.add("startPeriod.number");
		sic.add("startPeriod.beginDate");
		sic.add("isClosed");
		view.setFilter(filter);

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		ProjectPeriodStatusCollection col = iProjectCrrol
				.getProjectPeriodStatusCollection(view);

		if (col != null && col.size() > 0) {
			ProjectPeriodStatusInfo info = col.get(0);

			if (info.isIsClosed()) {
				if (info.getStartPeriod() != null && startperiod!=null// && info.getStartPeriod().getBeginDate().after(period.getBeginDate())){
						&& !startperiod.getId().toString().equals(
								info.getStartPeriod().getId().toString())) {
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.CNT_DELPRO);
				}
			} else {

				if (info.getStartPeriod() != null && startperiod!=null
						&& !startperiod.getId().toString().equals(
								info.getStartPeriod().getId().toString())) {

					sic = new SelectorItemCollection();
					sic.add("startPeriod");
					sic.add("costPeriod");
					sic.add("finacialPeriod");
					info.setStartPeriod(startperiod);
					info.setCostPeriod(startperiod);
					info.setFinacialPeriod(startperiod);
					iProjectCrrol.updatePartial(info, sic);
				}
			}

		}
	}

	/**
	 * 删除月结状态
	 * 
	 * @param pk
	 *            工程项目
	 */
	public static void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {

		// 是否存在
		CurProjectInfo curProject = null;
//		if (model == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isLeaf");
			sic.add("startDate");
			sic.add("isEnabled");
			sic.add("fullOrgUnit.id");
			curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(pk);
//		}

		String comId = curProject.getFullOrgUnit().getId().toString();
			// 启用成本财务一体化
		boolean isIncorporation = FDCUtils.IsInCorporation(ctx, comId);
		if (isIncorporation) {	
			IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory.getLocalInstance(ctx);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id", pk.toString()));
			filter.getFilterItems().add(new FilterItemInfo("isClosed", new Integer(1))); 
	
			String sql = "delete from T_FNC_ProjectPeriodStatus  where fprojectid not in (select fid from t_fdc_curproject) ";
			DbUtil.execute(ctx, sql);
	
			if (iProjectCrrol.exists(filter)) {
				throw new ProjectPeriodStatusException(
						ProjectPeriodStatusException.CNT_DELPRO);
			} 
		}
		
		String deleteSql = "delete from T_FNC_ProjectPeriodStatus  where fprojectid=? and  FIsClosed=0 ";
		DbUtil.execute(ctx, deleteSql, new Object[] { pk.toString() });
		
	}

	/**
	 * 房地产系统进入下一期
	 * 
	 * @param ctx
	 * @param comId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void nextSystem(Context ctx, String comId)
			throws BOSException, EASBizException {
		// 如果当前组织的全部已经月结，那么系统状态控制ＦＤＣ系统进入下一期间
		PeriodInfo period = SystemStatusCtrolUtils.getCurrentPeriod(ctx,
				SystemEnum.FDC, new ObjectUuidPK(comId));

		// 找到该组织所有明细的工程项目
		StringBuffer selectCurProSql = new StringBuffer();
		selectCurProSql
				.append("select a.fid from T_FNC_ProjectPeriodStatus a 		\r\n");
		selectCurProSql
				.append("	inner join t_fdc_curproject b on a.fprojectId =b.fid									\r\n");
		selectCurProSql
				.append("	where a.FfinacialPeriodId = ? and  b.ffullorgunit=?	and b.fisleaf=1	");

		IRowSet rs2 = DbUtil.executeQuery(ctx, selectCurProSql.toString(),
				new Object[] { period.getId().toString(), comId });
		try {
			if (rs2 == null || !rs2.next()) {
				ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory
						.getLocalInstance(ctx);
				sysStatus.nextPeriod(SystemEnum.FDC, GlUtils.getCurrentCompany(
						ctx, comId, null, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

	}

	/**
	 * 房地产系统回到上一期
	 * 
	 * @param ctx
	 * @param comId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void preSystem(Context ctx, String comId)
			throws BOSException, EASBizException {

		ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory
				.getLocalInstance(ctx);
		sysStatus.prePeriod(SystemEnum.FDC, GlUtils.getCurrentCompany(ctx,
				comId, null, false));

	}

	/**
	 * 当前期间更换到下一期间,以及取消冻结
	 * 
	 * @param ctx
	 * @param projectId
	 *            工程项目
	 * @param isCost
	 *            True为成本 Fasle为财务
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void checkNext(Context ctx, String projectId, boolean isCost)
			throws BOSException, EASBizException {

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));

		if (iProjectCrrol.exists(filter)) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = view.getSelector();
			sic.add("project.displayName");
			sic.add("startPeriod.id");
			sic.add("costPeriod.id");
			sic.add("finacialPeriod.id");
			sic.add("costPeriod.number");
			sic.add("finacialPeriod.number");

			view.setFilter(filter);

			ProjectPeriodStatusCollection control = iProjectCrrol
					.getProjectPeriodStatusCollection(view);
			if (control != null && control.size() > 0) {
				ProjectPeriodStatusInfo info = control.get(0);

				PeriodInfo preperiod = null;
				PeriodInfo curperiod = null;
				if (isCost) {
					preperiod = info.getCostPeriod();
					// 这里校验如果是启动期间，继续，如果不是启动期间，需要财务是否已经先月结，是可以继续
					/* TODO checkFinaciaNext */
					if (!info.getCostPeriod().getId().toString().equals(
							info.getStartPeriod().getId().toString())) {
						if (!info.getCostPeriod().getId().toString().equals(
								info.getFinacialPeriod().getId().toString())) {
							throw new ProjectPeriodStatusException(
									ProjectPeriodStatusException.NT_NEXTCOST,
									new Object[] { info.getProject()
											.getDisplayName() });
						}
					}
					// 是否存在未审批的合同
					if (checkBill(ctx, projectId, info.getCostPeriod().getId()
							.toString(), true)) {
						throw new ProjectPeriodStatusException(
								ProjectPeriodStatusException.CHK_CONTRACT,
								new Object[] { info.getProject()
										.getDisplayName() });
					}

				} else {
					// 这里校验成本是否已经月结，是可以继续
					preperiod = info.getFinacialPeriod();
					/* TODO checkCostNext */
					if (info.getCostPeriod().getId().toString().equals(
							info.getFinacialPeriod().getId().toString())) {
						throw new ProjectPeriodStatusException(
								ProjectPeriodStatusException.NT_NEXTFINA,
								new Object[] { info.getProject()
										.getDisplayName() });
					}
					// 是否存在未审批的wuwenben合同
					if (checkBill(ctx, projectId, info.getFinacialPeriod()
							.getId().toString(), false)) {
						throw new ProjectPeriodStatusException(
								ProjectPeriodStatusException.CHK_CONTRACTWITHOUT,
								new Object[] { info.getProject()
										.getDisplayName() });
					}

				}

				curperiod = PeriodUtils.getNextPeriodInfo(ctx, preperiod);
				if (curperiod == null) {
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.NOTEXTPERIOD,
							new Object[] { new Integer(preperiod.getNumber()) });
				}

			}
		} else {
			// 工程已经关闭，不需要继续月结

		}
	}

	/**
	 * 当前期间更换到下一期间,以及取消冻结
	 * 
	 * @param ctx
	 * @param projectId
	 *            工程项目
	 * @param isCost
	 *            True为成本 Fasle为财务
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void next(Context ctx, String projectId, boolean isCost)
			throws BOSException, EASBizException {

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));
		// filter.getFilterItems().add(new
		// FilterItemInfo("isEnd",Boolean.FALSE));

		if (iProjectCrrol.exists(filter)) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = view.getSelector();
			sic.add("project.displayName");
			sic.add("startPeriod.id");
			sic.add("costPeriod.id");
			sic.add("finacialPeriod.id");
			sic.add("costPeriod.number");
			sic.add("finacialPeriod.number");

			view.setFilter(filter);

			ProjectPeriodStatusCollection control = iProjectCrrol
					.getProjectPeriodStatusCollection(view);
			if (control != null && control.size() > 0) {
				ProjectPeriodStatusInfo info = control.get(0);

				SelectorItemCollection selector = new SelectorItemCollection();
				PeriodInfo preperiod = null;
				PeriodInfo curperiod = null;
				if (isCost) {

					// 如果是成本月结，取消成本冻结标记，设置成本月结标记，取消财务月结标记
					info.setIsCostFreeze(false);
					info.setIsCostEnd(true);
					info.setIsFinacialEnd(false);

					selector.add("isCostFreeze");
					selector.add("costPeriod");
					selector.add("isCostEnd");
					selector.add("isFinacialEnd");

					preperiod = info.getCostPeriod();
					curperiod = PeriodUtils.getNextPeriodInfo(ctx, preperiod);
					info.setCostPeriod(curperiod);

				} else {

					// 如果是财务月结，取消财务冻结标记，设置成本月结标记，取消财务月结标记
					info.setIsFinaclaFreeze(false);
					info.setIsCostEnd(false);
					info.setIsFinacialEnd(true);

					selector.add("isFinaclaFreeze");
					selector.add("finacialPeriod");
					selector.add("isCostEnd");
					selector.add("isFinacialEnd");

					preperiod = info.getFinacialPeriod();
					curperiod = PeriodUtils.getNextPeriodInfo(ctx, preperiod);
					info.setFinacialPeriod(curperiod);

				}

				iProjectCrrol.updatePartial(info, selector);
			}
		} else {
			// 工程已经关闭，不需要继续月结

		}
	}

	/**
	 * 当前期间冻结
	 * 
	 * @param ctx
	 * @param projectId
	 *            工程项目
	 * @param isCost
	 *            True为成本 Fasle为财务
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void freeze(Context ctx, String projectId, boolean isCost)
			throws BOSException, EASBizException {
		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));

		if (iProjectCrrol.exists(filter)) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = view.getSelector();
			sic.add("project.displayName");
			sic.add("costPeriod.id");
			sic.add("finacialPeriod.id");
			view.setFilter(filter);

			ProjectPeriodStatusCollection control = iProjectCrrol
					.getProjectPeriodStatusCollection(view);
			if (control != null && control.size() > 0) {
				ProjectPeriodStatusInfo info = control.get(0);
				SelectorItemCollection selector = new SelectorItemCollection();

				if (isCost) {
					// 校验：财务和成本在同一期间，则可以冻结成本
					if (!info.getCostPeriod().getId().toString().equals(
							info.getFinacialPeriod().getId().toString())) {
						throw new ProjectPeriodStatusException(
								ProjectPeriodStatusException.NT_FREEZECOST,
								new Object[] { info.getProject()
										.getDisplayName() });
					}
					// 成本冻结
					info.setIsCostFreeze(true);
					selector.add("isCostFreeze");
				} else {
					// 校验：成本已经月结，则可以冻结财务
					if (info.getCostPeriod().getId().toString().equals(
							info.getFinacialPeriod().getId().toString())) {
						throw new ProjectPeriodStatusException(
								ProjectPeriodStatusException.NT_FREEZEFINA,
								new Object[] { info.getProject()
										.getDisplayName() });
					}
					// 财务冻结
					info.setIsFinaclaFreeze(true);
					selector.add("isFinaclaFreeze");
				}

				iProjectCrrol.updatePartial(info, selector);
			}
		} else {

		}
	}

	/**
	 * 当前期间更换到上一期间,以及取消冻结
	 * 
	 * @param ctx
	 * @param projectId
	 *            工程项目
	 * @param isCost
	 *            True为成本 Fasle为财务
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void last(Context ctx, String projectId, boolean isCost)
			throws BOSException, EASBizException {

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));

		if (iProjectCrrol.exists(filter)) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = view.getSelector();
			sic.add("project.displayName");
			sic.add("startPeriod.id");
			sic.add("costPeriod.id");
			sic.add("finacialPeriod.id");
			sic.add("isEnd");
			view.setFilter(filter);

			ProjectPeriodStatusCollection control = iProjectCrrol
					.getProjectPeriodStatusCollection(view);
			if (control != null && control.size() > 0) {
				ProjectPeriodStatusInfo info = control.get(0);

				SelectorItemCollection selector = new SelectorItemCollection();

				if (isCost) {
					PeriodInfo curperiod = PeriodUtils.getPrePeriodInfo(ctx,
							info.getCostPeriod());

					// 如果是成本反月结，取消成本冻结标记，取消成本月结标记，设置财务月结标记
					info.setIsCostFreeze(false);
					info.setIsCostEnd(false);

					if (!curperiod.getId().toString().equals(
							info.getStartPeriod().getId().toString())) {
						info.setIsFinacialEnd(true);
					}

					selector.add("isCostFreeze");
					selector.add("costPeriod");
					selector.add("isCostEnd");
					selector.add("isFinacialEnd");

					info.setCostPeriod(curperiod);

				} else {

					// 如果是财务反月结，取消财务冻结标记，设置成本月结标记，取消财务月结标记
					info.setIsFinaclaFreeze(false);
					info.setIsCostEnd(true);
					info.setIsFinacialEnd(false);

					selector.add("isFinaclaFreeze");
					selector.add("finacialPeriod");
					selector.add("isCostEnd");
					selector.add("isFinacialEnd");

					PeriodInfo curperiod = PeriodUtils.getPrePeriodInfo(ctx,
							info.getFinacialPeriod());
					info.setFinacialPeriod(curperiod);
				}

				iProjectCrrol.updatePartial(info, selector);
			}
		} else {

		}
	}

	/**
	 * 当前期间更换到上一期间,以及取消冻结
	 * 
	 * @param ctx
	 * @param projectId
	 *            工程项目
	 * @param isCost
	 *            True为成本 Fasle为财务
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void checkLast(Context ctx, String projectId, boolean isCost)
			throws BOSException, EASBizException {

		IProjectPeriodStatus iProjectCrrol = ProjectPeriodStatusFactory
				.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("project.id", projectId));

		if (iProjectCrrol.exists(filter)) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = view.getSelector();
			sic.add("project.displayName");
			sic.add("startPeriod.id");
			sic.add("costPeriod.id");
			sic.add("finacialPeriod.id");
			sic.add("isEnd");
			view.setFilter(filter);

			ProjectPeriodStatusCollection control = iProjectCrrol
					.getProjectPeriodStatusCollection(view);
			if (control != null && control.size() > 0) {
				ProjectPeriodStatusInfo info = control.get(0);
				// 已关闭
				if (info.isIsEnd()) {
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.NT_END,
							new Object[] { info.getProject().getDisplayName() });
				}

				if (info.getCostPeriod().getId().toString().equals(
						info.getStartPeriod().getId().toString())) {
					throw new ProjectPeriodStatusException(
							ProjectPeriodStatusException.NT_LAST,
							new Object[] { info.getProject().getDisplayName() });
				}
				if (isCost) {
					// 这里校验财务是否已经先反月结
					/* TODO checkFinaclaLast */
					// 在启动期间，不能继续反月结
					if (info.getCostPeriod().getId().toString().equals(
							info.getFinacialPeriod().getId().toString())) {
						throw new ProjectPeriodStatusException(
								ProjectPeriodStatusException.NT_LASTCOST,
								new Object[] { info.getProject()
										.getDisplayName() });
					}

				} else {
					// 这里校验财务是否和成本在同一个当前期间
					/* TODO checkIsSamePeriod */
					if (!info.getCostPeriod().getId().toString().equals(
							info.getFinacialPeriod().getId().toString())) {
						throw new ProjectPeriodStatusException(
								ProjectPeriodStatusException.NT_LASTFINA,
								new Object[] { info.getProject()
										.getDisplayName() });
					}

				}

			}
		} else {

		}
	}

	// 检查工程项目和期间,是否存在未审核的进入动态成本的单据
	private static boolean checkBill(Context ctx, String projectId,
			String periodId, boolean isWithText) throws BOSException {

		// 是否
		String checkSql = isWithText ? " Select top 1 Fid from t_con_contractBill where fcurProjectId=? and FPeriodId=? and fstate<>'4AUDITTED' -- and FIsCostSplit=1 "
				: "  Select top 1 Fid from t_con_contractwithouttext where fcurProjectId=? and FPeriodId =? and fstate<>'4AUDITTED'  -- and FIsCostSplit=1	";

		IRowSet rs = DbUtil.executeQuery(ctx, checkSql, new Object[] {
				projectId, periodId });
		try {
			if (rs != null && rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		return false;
	}

	// 该组织下的工程项目是否已经存在月结的数据,true代表存在月结数据，不能再加参数修改为不启用成本月结
	public static boolean checkPeriodClose(Context ctx, String companyId)
			throws BOSException, EASBizException {
		boolean isExist = false;
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", companyId));
		filterPrj.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filterPrj.getFilterItems().add(
				new FilterItemInfo("isLeaf", Boolean.TRUE));
		viewPrj.setFilter(filterPrj);
		viewPrj.getSelector().add("id");
		Set curpojectIdSet = new HashSet();
		CurProjectCollection collPrj = new CurProjectCollection();
		if (ctx != null) {
			collPrj = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectCollection(viewPrj);
		} else {
			collPrj = CurProjectFactory.getRemoteInstance()
					.getCurProjectCollection(viewPrj);
		}
		for (int i = 0, size = collPrj.size(); i < size; i++) {
			curpojectIdSet.add(collPrj.get(i).getId().toString());
		}

		if (curpojectIdSet.size() > 0) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("curProjectID", curpojectIdSet,
							CompareType.INCLUDE));
			if (ctx != null) {
				if (SettledMonthlyFactory.getLocalInstance(ctx).exists(filter)) {
					isExist = true;
				}
			} else {
				if (SettledMonthlyFactory.getRemoteInstance().exists(filter)) {
					isExist = true;
				}
			}
		}
		return isExist;
	}

	// 该组织下的工程项目是否存在已经生成凭证的付款拆分（成本及财务），以及竣工结算帐务处理
	public static boolean checkExistVoucher(Context ctx, String companyId)
			throws BOSException, EASBizException {
		boolean isExist = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("paymentbill.company.id", companyId));
		filter.getFilterItems().add(
				new FilterItemInfo("Fivouchered", Boolean.TRUE,
						CompareType.EQUALS));
		if (ctx != null) {
			if (PaymentSplitFactory.getLocalInstance(ctx).exists(filter)
					|| PaymentNoCostSplitFactory.getLocalInstance(ctx).exists(
							filter)) {
				isExist = true;
			}
		} else {
			if (PaymentSplitFactory.getRemoteInstance().exists(filter)
					|| PaymentNoCostSplitFactory.getRemoteInstance().exists(
							filter)) {
				isExist = true;
			}
		}
		if (!isExist) {
			FilterInfo filterProduct = new FilterInfo();
			filterProduct.getFilterItems().add(
					new FilterItemInfo(
							"curProjProductEntries.curProject.fullOrgUnit.id",
							companyId));
			filterProduct.getFilterItems().add(
					new FilterItemInfo("fiVouchered", Boolean.TRUE,
							CompareType.EQUALS));
			if(ctx!=null){
				if(FIProductSettleBillFactory.getLocalInstance(ctx).exists(filterProduct))
					isExist = true;
			}else{
				if(FIProductSettleBillFactory.getRemoteInstance().exists(filterProduct))
					isExist = true;
			}
		}
		return isExist;
	}
	
	
	//处理导入的数据
	public static void doImportData(Context ctx,String prjId,boolean isCost,PeriodInfo currentPeriod) throws BOSException{
		
		Object[] param = new Object[]{prjId};
		if(isCost){
			String sql = "update T_CON_ContractCostSplit  set FislastVerThisPeriod =1 where FislastVerThisPeriod is null " +
					"and FcontractBillId in (select fid from t_con_contractBill where fcurprojectId=?)";
			DbUtil.execute(ctx,sql,param);		
	
			sql = "update T_CON_ConChangeSplit  set FislastVerThisPeriod =1 where FislastVerThisPeriod is null " +
				"and FcontractChangeId in (select fid from t_con_contractChangeBill where fcurprojectId=?)";
			DbUtil.execute(ctx,sql,param);
			
			sql = "update T_CON_SettlementCostSplit  set FislastVerThisPeriod =1 where FislastVerThisPeriod is null "+
			"and FsettlementBillId in (select fid from t_con_contractsettlementBill where fcurprojectId=?)";
			DbUtil.execute(ctx,sql,param);		
		}else{
			
			String sql = "update T_FNC_PaymentSplit  set FislastVerThisPeriod =1 where FislastVerThisPeriod is null " +
			"and FPaymentBillID in (select fid from t_cas_paymentbill where fcurprojectId=?)";
			DbUtil.execute(ctx,sql,param);						
		}
		
		//同步期间为空的单据
		if(isCost){
			//在成本月结的时候同步即可，不需要财务成本月结的时候再同步
			// by sxhong 2009-07-30 21:54:27
			syscBill(ctx, prjId, currentPeriod.getId().toString());
		}
	}
	

	public static void syscBill(Context ctx,String projectId,String periodId) throws BOSException{
		//设置所有单据期间为空的数据
		
		//合同
		String contractSql = "update T_CON_ContractBill set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null) and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractSql,new Object[]{periodId,projectId});
		
		//无文本合同
		String contractWithSql = "update T_CON_ContractWithoutText set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractWithSql,new Object[]{periodId,projectId});
		
		//修订单据
		String contractRevSql = "update T_CON_ContractBillRevise set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractRevSql,new Object[]{periodId,projectId});	
				
		//变更单据
		String changeauditSql = "update t_con_changeauditbill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,changeauditSql,new Object[]{periodId,projectId});	
		
		String conchangeSql = "update t_con_contractchangebill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,conchangeSql,new Object[]{periodId,projectId});	
		
		//结算单
		String contractSettleSql = "update T_CON_ContractSettlementBill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null) and Fcurprojectid=?   ";
		DbUtil.execute(ctx,contractSettleSql,new Object[]{periodId,projectId});	
		
		//付款申请单
		String payRequestSql = "update T_CON_PayRequestBill set FBookedDate = FPayDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,payRequestSql,new Object[]{periodId,projectId});	
		
		//合同拆分
		String contractSplitSql = "update T_CON_ContractCostSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and FContractBillid in (select fid from t_con_contractbill where fcurprojectid=?) ";
		DbUtil.execute(ctx, contractSplitSql, new Object[] { periodId,
				projectId });

		// 变更拆分
		String conChangeSplitSql = "update T_CON_ConChangeSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and fcontractchangeid in (select fid from t_con_contractchangebill where fcurprojectid=?) ";
		DbUtil.execute(ctx, conChangeSplitSql, new Object[] { periodId,
				projectId });

		// 结算拆分
		String settlementSplitSql = "update t_Con_Settlementcostsplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and fsettlementbillid in (select fid from t_con_contractsettlementbill  where fcurprojectid=?) ";
		DbUtil.execute(ctx, settlementSplitSql, new Object[] { periodId,
				projectId });

		// 付款拆分
		String paymentSplitSql = "update T_FNC_PaymentSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and FPaymentBillid in (select fid from t_cas_paymentbill where fcurprojectid=?) ";
		DbUtil.execute(ctx, paymentSplitSql, new Object[] { periodId,
				projectId });
		
		//待发生成本调整
		String adjustSql = "update T_AIM_AdjustRecordEntry set FperiodId=? where FperiodId is null and FParentID in (select FID from T_AIM_DynamicCost where FAccountID in (select FID from T_FDC_CostAccount where FCurProject=?)) ";
		DbUtil.execute(ctx, adjustSql, new Object[] { periodId,
				projectId });
		
		//指标
		String prjIndexSql = "update T_FDC_ProjectIndexData set FperiodId=? where FperiodId is null and FProjOrOrgID=? ";
		DbUtil.execute(ctx, prjIndexSql, new Object[] { periodId,
				projectId });
		
		//目标成本
		String aimCostSql = "update T_AIM_AimCost set FperiodId=? where FperiodId is null and FOrgOrProId=? ";
		DbUtil.execute(ctx, aimCostSql, new Object[] { periodId,	projectId });
	}
	
}
