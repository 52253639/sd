package com.kingdee.eas.fdc.invite.markesupplier.app;

import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSUuid;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.app.FDCSplAuditBaseBillControllerBean;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationAuditResultCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationAuditResultFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationAuditResultInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpCollection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class MarketSupplierEvaluationControllerBean extends
		AbstractMarketSupplierEvaluationControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierEvaluationControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);

	}

	private List exequeSQl(Context ctx, String sql, List valueList,
			boolean boo, int valueSize) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		List list = new ArrayList();
		valueList.size();
		builder.appendSql(sql);
		for (int i = 0; i < valueList.size(); i++) {
			builder.addParam(valueList.get(i).toString().trim());
		}
		if (boo) {
			IRowSet irowSet = builder.executeQuery();
			while (irowSet.next()) {
				int q = 1;
				List lists = new ArrayList();
				for (int j = 0; j < valueSize; j++) {
					lists.add(irowSet.getString(q++));
				}
				list.add(lists);

			}
			return list;
		} else {
			builder.executeUpdate();
			return null;
		}
	}

	/**
	 * @description
	 * @author
	 * @createDate 2010-11-30
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	protected boolean isNeedWriteBack(Context ctx, FDCSplAuditBaseBillInfo info)
			throws BOSException, EASBizException {

		// 获取全部的合格供应商登记
		HashMap gradesMap = new HashMap();
		MarketGradeSetUpCollection grades = MarketGradeSetUpFactory.getLocalInstance(ctx)
				.getMarketGradeSetUpCollection(new EntityViewInfo());
		MarketGradeSetUpInfo grade = null;
		for (Iterator it = grades.iterator(); it.hasNext();) {
			grade = (MarketGradeSetUpInfo) it.next();
			if (grade != null) {
				// gradesMap.put(grade.getName(), grade.getIsGrade());
			}
		}

		// 如果该单据上有对供应商的服务进行评价，并且至少有一项服务是合格的，则需要向供应商主数据回写
		if (info instanceof MarketSupplierEvaluationInfo) {
			MarketSupplierEvaluationInfo qualificationInfo = (MarketSupplierEvaluationInfo) info;
			MarketSupplierEvaluationAuditResultCollection  entrys = qualificationInfo.getAuditResult();
			if (entrys != null) {
				for (Iterator it = entrys.iterator(); it.hasNext();) {
					MarketSupplierEvaluationAuditResultInfo entry = (MarketSupplierEvaluationAuditResultInfo) it.next();
					// 直接取entry.getGrade()取不到值，是因为前面的sic没有加上要取Grade字段，不得已，再取一次。
					// added by owen_wen 2011-05-27
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add(new SelectorItemInfo("*"));
					entry = MarketSupplierEvaluationAuditResultFactory.getLocalInstance(ctx).getMarketSupplierEvaluationAuditResultInfo(new ObjectUuidPK(entry.getId()), sic);
					if (entry.isAvailable()) {
						IsGradeEnum isGrade = (IsGradeEnum) gradesMap.get(entry.getGrade());
						if (isGrade != null&& IsGradeEnum.ELIGIBILITY.equals(isGrade)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public IObjectPK save(Context ctx, CoreBaseInfo model) throws BOSException,
			EASBizException {
		MarketSupplierEvaluationInfo info = (MarketSupplierEvaluationInfo) model;
		updateSupplierInfo(ctx, info.getMarketsupplier());
		return super.save(ctx, model);
	}

	public void save(Context ctx, IObjectPK pk, CoreBaseInfo model)
			throws BOSException, EASBizException {
		MarketSupplierEvaluationInfo info = (MarketSupplierEvaluationInfo) model;
		updateSupplierInfo(ctx, info.getMarketsupplier());
		super.save(ctx, pk, model);
	}

	public IObjectPK submit(Context ctx, CoreBaseInfo model)
			throws BOSException, EASBizException {
		MarketSupplierEvaluationInfo info = (MarketSupplierEvaluationInfo) model;
		updateSupplierInfo(ctx, info.getMarketsupplier());
		return super.submit(ctx, model);
	}

	public void submit(Context ctx, IObjectPK arg1, CoreBaseInfo model)
			throws BOSException, EASBizException {
		MarketSupplierEvaluationInfo info = (MarketSupplierEvaluationInfo) model;
		updateSupplierInfo(ctx, info.getMarketsupplier());
		super.submit(ctx, arg1, model);
	}

	protected void updateSupplierInfo(Context ctx, MarketSupplierStockInfo info)
			throws EASBizException, BOSException {
		if (info != null) {
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("partProject");
			sel.add("authorizePerson");
			sel.add("authorizePhone");
			sel.add("authorizeJob");
			sel.add("contractor");
			sel.add("contractorPhone");
			sel.add("manager");
			sel.add("managerPhone");
			MarketSupplierStockFactory.getLocalInstance(ctx).updatePartial(info, sel);
		}
	}

	protected boolean isUseName() {
		return false;
	}

	protected void _isReferenced(Context arg0, IObjectPK arg1)
			throws BOSException, EASBizException {
		Set entryId = new HashSet();
		MarketSupplierEvaluationInfo info = (MarketSupplierEvaluationInfo) this
				.getValue(arg0, arg1);
		for (int i = 0; i < info.getIndexValue().size(); i++) {
			entryId.add(info.getIndexValue().get(i).getId().toString());
		}
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("auditTemplate.id", entryId,
//						CompareType.INCLUDE));
//		if (SupplierReviewGatherEntryFactory.getLocalInstance(arg0).exists(
//				filter)) {
//			throw new EASBizException(new NumericExceptionSubItem("100",
//					"已经被供应商评审汇总引用，不能进行删除操作！"));
//		}
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("supplierEvaluation.id", billId.toString()));
//		if (SupplierReviewGatherContractEntryFactory.getLocalInstance(ctx)
//				.exists(filter)) {
//			throw new EASBizException(new NumericExceptionSubItem("100",
//					"已经被供应商评审汇总引用，不能进行反审批操作！"));
//		}
//		Set entryId = new HashSet();
//		FDCSplQualificationAuditBillInfo info = (FDCSplQualificationAuditBillInfo) this
//				.getValue(ctx, new ObjectUuidPK(billId));
//		for (int i = 0; i < info.getIndexValue().size(); i++) {
//			entryId.add(info.getIndexValue().get(i).getId().toString());
//		}
//		filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("auditTemplate.id", entryId,
//						CompareType.INCLUDE));
//		if (SupplierReviewGatherEntryFactory.getLocalInstance(ctx).exists(
//				filter)) {
//			throw new EASBizException(new NumericExceptionSubItem("100",
//					"已经被供应商评审汇总引用，不能进行反审批操作！"));
//		}
		super._unAudit(ctx, billId);
	}
}