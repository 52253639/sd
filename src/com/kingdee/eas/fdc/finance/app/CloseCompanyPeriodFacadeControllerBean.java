package com.kingdee.eas.fdc.finance.app;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class CloseCompanyPeriodFacadeControllerBean extends
		AbstractCloseCompanyPeriodFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.CloseCompanyPeriodFacadeControllerBean");

	protected void _closeCompany(Context ctx, String companyId)
			throws BOSException, EASBizException {
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
		CurProjectCollection collPrj = CurProjectFactory.getLocalInstance(ctx)
				.getCurProjectCollection(viewPrj);
		for (int i = 0, size = collPrj.size(); i < size; i++) {
			curpojectIdSet.add(collPrj.get(i).getId().toString());
		}

		PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
				.getCurrentPeriod(ctx, SystemEnum.FDC, new ObjectUuidPK(BOSUuid
						.read(companyId)));
		String prjPeriod = Integer.toString(PeriodUtils.getNextPeriodInfo(ctx,
				companyCurrentPeriod).getNumber());
		FilterInfo filterClose = new FilterInfo();
		filterClose.getFilterItems().add(
				new FilterItemInfo("project.id", curpojectIdSet,
						CompareType.INCLUDE));
//		filterClose.getFilterItems().add(
//				new FilterItemInfo("finacialPeriod.number", Integer
//						.toString(companyCurrentPeriod.getNumber()),
//						CompareType.LESS_EQUALS));
		filterClose.getFilterItems().add(
				new FilterItemInfo("finacialPeriod.number", prjPeriod,
						CompareType.LESS));
		if (ProjectPeriodStatusFactory.getLocalInstance(ctx)
				.exists(filterClose)) {
			throw new PaymentSplitException(
					PaymentSplitException.HAS_NOFINCLOSEPRJ);
		}

		boolean isFinacial = FDCUtils.IsFinacial(ctx, companyId);
		if (isFinacial) {
			FilterInfo filterProduct = new FilterInfo();
			filterProduct.getFilterItems().add(
					new FilterItemInfo("curProjProductEntries.curProject.id",
							curpojectIdSet, CompareType.INCLUDE));
			filterProduct.getFilterItems().add(
					new FilterItemInfo("fiVouchered", Boolean.FALSE));
//			filterProduct.getFilterItems().add(
//					new FilterItemInfo("period.number", Integer
//							.toString(companyCurrentPeriod.getNumber()),
//							CompareType.LESS_EQUALS));
			filterClose.getFilterItems().add(
					new FilterItemInfo("finacialPeriod.number", prjPeriod,
							CompareType.LESS));
			if (ProductSettleBillFactory.getLocalInstance(ctx).exists(
					filterProduct)) {
				throw new PaymentSplitException(
						PaymentSplitException.HAS_PRODUCTSETT);
			}
		}

		ProjectPeriodStatusUtil.nextSystem(ctx, companyId);
	}

	protected void _antiCloseCompany(Context ctx, String companyId)
			throws BOSException, EASBizException {
		ProjectPeriodStatusUtil.preSystem(ctx, companyId);
	}

}