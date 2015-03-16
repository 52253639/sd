package com.kingdee.eas.fdc.finance.app;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledInfo;
import com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanContract;
import com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanNoContract;
import com.kingdee.eas.fdc.finance.IFDCProDepConPayPlanUnsettled;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;

public class FDCProDepConPayPlanControllerBean extends
		AbstractFDCProDepConPayPlanControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.FDCProDepConPayPlanControllerBean");

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCProDepConPayPlanInfo info = (FDCProDepConPayPlanInfo) model;
		if (!info.getVersion().equals("1.0")) {
			return;
		}
		checkNumberDup(ctx, info);
		checkNameDup(ctx, info);
	}

//	/**
//	 * 涛哥要求，编码、名称整个集团都不允许重复
//	 */
//	private void checkNumberDup(Context ctx, FDCBillInfo billInfo)
//			throws BOSException, EASBizException {
//		FDCProDepConPayPlanInfo info = (FDCProDepConPayPlanInfo) billInfo;
//		String number = info.getNumber();
//		String version = info.getVersion();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("number", number));
//		filter.getFilterItems().add(new FilterItemInfo("version", version));
//		if (billInfo.getId() != null) {
//			filter.getFilterItems().add(
//					new FilterItemInfo("id", billInfo.getId().toString(),
//							CompareType.NOTEQUALS));
//		}
//		boolean isDup = _exists(ctx, filter);
//		if (isDup) {
//			throw new EASBizException(EASBizException.CHECKNUMDUP,
//					new Object[] { number });
//		}
//	}

	/**
	 * 涛哥要求，编码、名称整个集团都不允许重复
	 */
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		FDCProDepConPayPlanInfo info = (FDCProDepConPayPlanInfo) billInfo;
		String name = info.getName();
		String version = info.getVersion();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name));
		filter.getFilterItems().add(new FilterItemInfo("version", version));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}
		if (_exists(ctx, filter)) {
			throw new EASBizException(EASBizException.CHECKNAMEDUP,
					new Object[] { name });
		}
	}

	protected Map _fetchData(Context ctx, Map param) throws BOSException,
			EASBizException {
		return null;
	}

	protected Map _statisticsPay(Context ctx, Map valuse) throws BOSException {
		return null;
	}

	protected Map _getPlanPay(Context ctx, Map value) throws BOSException {
		return null;
	}

	protected String _revise(Context ctx, Map param) throws BOSException {
		return null;
	}

	protected void _back(Context ctx, List ids) throws BOSException,
			EASBizException {
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			setBack(ctx, BOSUuid.read(id));
		}
	}

	protected void _publish(Context ctx, List ids) throws BOSException,
			EASBizException {
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			setPublish(ctx, BOSUuid.read(id));
		}
	}

	protected void _setBack(Context ctx, BOSUuid billid) throws BOSException,
			EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billid);
		billInfo.setState(FDCBillStateEnum.BACK);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}

	protected void _setPublish(Context ctx, BOSUuid billid)
			throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billid);
		billInfo.setState(FDCBillStateEnum.PUBLISH);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}

	/**
	 * 更新合同滚动计划为打回后，将本分录置为打回
	 */
	protected void _setConPlanBack(Context ctx, String pk) throws BOSException {
		if (pk == null) {
			return;
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("depPlan.id");
		sic.add("isBack");
		if (new FDCProDepConPayPlanContractInfo().getBOSType().equals(
				BOSUuid.read(pk).getType())) {
			try {
				IFDCProDepConPayPlanContract is = FDCProDepConPayPlanContractFactory
						.getLocalInstance(ctx);
				FDCProDepConPayPlanContractInfo info = is
						.getFDCProDepConPayPlanContractInfo(
								new ObjectUuidPK(pk), sic);
				if (info.getDepPlan() != null) {
					String depPlanID = info.getDepPlan().getId().toString();
					FDCDepConPayPlanBillFactory.getLocalInstance(ctx)
							.setBackByEntryID(depPlanID);
				}
				info.setIsBack(true);
				is.updatePartial(info, sic);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		} else if (new FDCProDepConPayPlanUnsettledInfo().getBOSType().equals(
				BOSUuid.read(pk).getType())) {
			try {
				IFDCProDepConPayPlanUnsettled is = FDCProDepConPayPlanUnsettledFactory
						.getLocalInstance(ctx);
				FDCProDepConPayPlanUnsettledInfo info = is
						.getFDCProDepConPayPlanUnsettledInfo(new ObjectUuidPK(
								pk), sic);
				if (info.getDepPlan() != null) {
					String depPlanID = info.getDepPlan().getId().toString();
					FDCDepConPayPlanBillFactory.getLocalInstance(ctx)
							.setBackByEntryID(depPlanID);
				}
				info.setIsBack(true);
				is.updatePartial(info, sic);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		} else if (new FDCProDepConPayPlanNoContractInfo().getBOSType().equals(
				BOSUuid.read(pk).getType())) {
			try {
				IFDCProDepConPayPlanNoContract is = FDCProDepConPayPlanNoContractFactory
						.getLocalInstance(ctx);
				FDCProDepConPayPlanNoContractInfo info = is
						.getFDCProDepConPayPlanNoContractInfo(new ObjectUuidPK(
								pk), sic);
				if (info.getDepPlan() != null) {
					String depPlanID = info.getDepPlan().getId().toString();
					FDCDepConPayPlanBillFactory.getLocalInstance(ctx)
							.setBackByEntryID(depPlanID);
				}
				info.setIsBack(true);
				is.updatePartial(info, sic);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
	}
}