package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.IFDCDepConPayPlanContract;
import com.kingdee.eas.fdc.finance.IFDCDepConPayPlanNoContract;
import com.kingdee.eas.fdc.finance.IFDCDepConPayPlanUnsettledCon;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCDepConPayPlanBillControllerBean extends
		AbstractFDCDepConPayPlanBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.FDCDepConPayPlanBillControllerBean");

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCDepConPayPlanBillInfo info = (FDCDepConPayPlanBillInfo) model;
		if (!info.getVersion().equals("1.0")) {
			return;
		}
		super.checkBill(ctx, model);
	}

//	private void checkNumberDup(Context ctx, FDCBillInfo billInfo)
//			throws BOSException, EASBizException {
//		if (!isUseNumber())
//			return;
//		FilterInfo filter = new FilterInfo();
//
//		filter.getFilterItems().add(
//				new FilterItemInfo("number", billInfo.getNumber()));
//		filter.getFilterItems().add(
//				new FilterItemInfo("state", FDCBillStateEnum.INVALID,
//						CompareType.NOTEQUALS));
//		filter.getFilterItems().add(
//				new FilterItemInfo("cu.id", billInfo.getCU().getId()));
//		if (billInfo.getId() != null) {
//			filter.getFilterItems().add(
//					new FilterItemInfo("id", billInfo.getId().toString(),
//							CompareType.NOTEQUALS));
//		}
//
//		if (_exists(ctx, filter)) {
//			throw new ContractException(ContractException.NUMBER_DUP);
//		}
//	}

	private FDCDepConPayPlanBillInfo getFDCDepConPayPlanBill(Context ctx,
			String id) throws BOSException, EASBizException {
		FDCDepConPayPlanBillInfo bill;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("deptment.number");
		selector.add("deptment.longNumber");
		selector.add("deptment.name");
		selector.add("curProject.number");
		selector.add("curProject.longNumber");
		selector.add("curProject.name");
		selector.add("*");
		selector.add("entrys.*");
		selector.add("entrys.items.*");
		// getValue会取出所有属性？
		if (id != null) {
			bill = (FDCDepConPayPlanBillInfo) FDCDepConPayPlanBillFactory
					.getLocalInstance(ctx).getValue(
							new ObjectUuidPK(BOSUuid.read(id)));
		} else {
			bill = new FDCDepConPayPlanBillInfo();
		}
		return bill;
	}

	protected Map _fetchData(Context ctx, Map param) throws BOSException,
			EASBizException {
		Map retMap = new HashMap();

		// 工程项目ID 注意处理空指针！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
		String id = (String) param.get("id");
		String prjId = (String) param.get("projectId");

		FDCDepConPayPlanBillInfo bill = null;

		bill = getFDCDepConPayPlanBill(ctx, id);

		if (prjId == null
				&& (bill.getCurProject() == null || bill.getCurProject()
						.getId() == null)) {
			throw new NullPointerException("cann't get prjId");
		}

		prjId = prjId == null ? bill.getCurProject().getId().toString() : prjId;
		if (bill.getCurProject() == null) {
			CurProjectInfo curProjectInfo = new CurProjectInfo();
			curProjectInfo.setId(BOSUuid.read(prjId));
			// bill.setCurProject(curProjectInfo);
		}

		// FDCDepConPayPlanEntryCollection depConPayPlanEntrys = new
		// FDCDepConPayPlanEntryCollection();
		retMap.put("editData", bill);
		// retMap.put("entrys", bill.getEntrys());
		// retMap.put("contract", conColl);

		return retMap;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("version");
		sic.add("state");
		FDCDepConPayPlanBillInfo info = FDCDepConPayPlanBillFactory
				.getLocalInstance(ctx).getFDCDepConPayPlanBillInfo(
						new ObjectUuidPK(billId.toString()), sic);
		String number = info.getNumber();
		double versions = Double.parseDouble(info.getVersion()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& info.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			DbUtil db = new DbUtil();
			IRowSet rowSet = db.executeQuery(ctx,
					"select fid as id from T_FNC_FDCDepConPayPlanBill "
							+ "where Fnumber = '" + number
							+ "' and Fversion ='" + version + "'  ");
			if (rowSet != null && rowSet.size() >= 1) {
				try {
					rowSet.next();
					String id = rowSet.getString("id");
					db = new DbUtil();
					db.execute(ctx,
							"update T_FNC_FDCDepConPayPlanBill set  FState='12REVISE' where fid ='"
									+ id + "'  ");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		super._audit(ctx, billId);
		// 暂时不用 by zgy
		// 同步合同付款计划
		// FDCDepConPayPlanFacadeFactory.getLocalInstance(ctx).
		// autoUpdateConPayPlan(billId.toString(), true);
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("version");
		sic.add("state");
		FDCDepConPayPlanBillInfo info = FDCDepConPayPlanBillFactory
				.getLocalInstance(ctx).getFDCDepConPayPlanBillInfo(
						new ObjectUuidPK(billId.toString()), sic);
		String number = info.getNumber();
		double versions = Double.parseDouble(info.getVersion()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& info.getState().equals(FDCBillStateEnum.AUDITTED)) {
			DbUtil db = new DbUtil();
			IRowSet rowSet = db.executeQuery(ctx,
					"select fid as id from T_FNC_FDCDepConPayPlanBill "
							+ "where Fnumber = '" + number
							+ "' and Fversion ='" + version + "'  ");
			if (rowSet != null && rowSet.size() >= 1) {
				try {
					rowSet.next();
					String id = rowSet.getString("id");
					db = new DbUtil();
					db.execute(ctx,
							"update T_FNC_FDCDepConPayPlanBill set  FState='"
									+ FDCBillStateEnum.REVISING_VALUE
									+ "' where fid ='" + id + "'  ");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 同步合同付款计划
		super._unAudit(ctx, billId);
		// 暂时不用 by zgy
		// FDCDepConPayPlanFacadeFactory.getLocalInstance(ctx).
		// autoUpdateConPayPlan(billId.toString(), false);
	}

	/**
	 * 此处取合同的累计完工金额、累计已付工程款、累计未付工程款
	 */
	protected Map _getPlanPay(Context ctx, Map value) throws BOSException {
		Map values = new HashMap();
		String date = (String) value.get("date");
		String conID = (String) value.get("conID");
		String flag = (String) value.get("flag");
		String sql = "";
		IRowSet rs = null;
		if ("true".equals(flag)) {
			sql = "select sum(FWorkLoad) from T_FNC_WorkLoadConfirmBill where FContractBillId ='"
					+ conID
					+ "' and fstate ='4AUDITTED' and  Fbizdate < "
					+ date;
			rs = DbUtil.executeQuery(ctx, sql);
		}
		// else {
		// sql =
		// "select sum(FCompletePrjAmt) from T_CON_PayRequestBill where FContractId = ? and  FBookeddate < "
		// + date;
		// rs = DbUtil.executeQuery(ctx, sql, new String[] { conID });
		// }

		StringBuffer sql1 = new StringBuffer();
		sql1
				.append(" select isnull(sum(pb.FProjectPriceInContract),0) + isnull(sum(ppe.FLocAdvance),0) ");
		sql1.append(" from T_CAS_PaymentBill as pb ");
		sql1.append(" left join T_FNC_PaymentPrjPayEntry as ppe ");
		sql1.append(" on pb.FPrjPayEntryID = ppe.FID ");
		sql1.append(" where pb.FContractbillId =? ");
		sql1.append(" and pb.fbillstatus = 15 ");
		sql1.append(" and pb.FBizDate < ").append(date);
		IRowSet rs1 = DbUtil.executeQuery(ctx, sql1.toString(),
				new String[] { conID });

		Set payStates = new HashSet();
		payStates.add(FDCBillStateEnum.SUBMITTED_VALUE);
		payStates.add(FDCBillStateEnum.AUDITTING_VALUE);
		payStates.add(FDCBillStateEnum.AUDITTED_VALUE);
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund.appendSql(" select ");
		builderFloatFund
				.appendSql(" sum(case reqC when 1 then (case payS when 15 then 0 else payA end ) ");
		builderFloatFund
				.appendSql(" else (case payS when 15 then reqA-payA else reqA end) end) as floatFund ");
		builderFloatFund.appendSql(" from ( select  ");
		builderFloatFund.appendSql(" req.FAmount as reqA, ");
		builderFloatFund.appendSql(" req.FHasClosed as reqC, ");
		builderFloatFund.appendSql(" pay.FAmount as payA, ");
		builderFloatFund.appendSql(" pay.FBillStatus as payS ");
		builderFloatFund.appendSql(" from T_CON_PayRequestBill as req ");
		builderFloatFund.appendSql(" left join T_CAS_PaymentBill as pay ");
		builderFloatFund.appendSql(" on pay.FFdcPayReqID = req.FID ");
		builderFloatFund.appendSql(" where req.FState in ");
		builderFloatFund.appendSql(FMHelper.setTran2String(payStates));
		builderFloatFund.appendSql(" and req.FBookedDate <  ");
		builderFloatFund.appendSql(date);
		builderFloatFund.appendSql(" and req.FContractID = ? ");
		builderFloatFund.addParam(conID);
		// builderFloatFund.appendSql(" and (pay.FBillStatus != ? ");
		// builderFloatFund.addParam(new Integer(BillStatusEnum.PAYED_VALUE));
		// builderFloatFund.appendSql(" or pay.FID is null) ");
		builderFloatFund.appendSql(" ) as tmp ");
		//		
		// String sql2 =
		// "select sum(FProjectPriceInContract) from T_CON_PayRequestBill  where  FContractId ='"
		// + conID
		// + "' and  "
		// + "FBookeddate < "
		// + date
		// + " and (fstate = '2SUBMITTED' or  fstate = '3AUDITTING')";
		IRowSet rs2 = builderFloatFund.executeQuery(ctx);
		
		BigDecimal payable = new BigDecimal(0);
		BigDecimal pay = new BigDecimal(0);
		BigDecimal enroute = new BigDecimal(0);
		try {
			if (rs != null && rs.next()) {
				payable = rs.getBigDecimal(1) != null ? rs.getBigDecimal(1)
						: payable;
			}
			if (rs1.next()) {
				pay = rs1.getBigDecimal(1) != null ? rs1.getBigDecimal(1) : pay;
			}
			if (rs2.next()) {
				enroute = rs2.getBigDecimal(1) != null ? rs2.getBigDecimal(1)
						: enroute;
			}
		} catch (SQLException e) {
			throw new BOSException();
		}
		BigDecimal noPay = payable.subtract(pay);
		values.put("payable", payable);
		values.put("pay", pay);
		values.put("noPay", noPay);
		values.put("enRoute", enroute);
		return values;
	}

	protected Map _statisticsPay(Context ctx, Map valuse) throws BOSException {
		String index[] = (String[]) valuse.get("index");
		String date[] = (String[]) valuse.get("date");
		String conID = (String) valuse.get("conID");
		IRow ir = (IRow) valuse.get("ir");
		for (int i = 0; i < index.length; i++) {
			StringBuffer sql = new StringBuffer();
			sql
					.append("select t2.FPaymentAmount from T_FNC_NewContractPayPlan as t1 ");
			sql.append("LEFT OUTER JOIN T_FNC_NewContractPayPlanEntry as t2 ");
			sql.append("on t1.fid = t2.FParentID");
			sql
					.append("where  tochar(year(t2.FPaymentDate))||'-'||tochar(MONTH(t2.FPaymentDate)) = ? ");
			sql
					.append("and t1.FContractIdID = ?  and t1.FBillState = 'audited'");
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(), new String[] {
					date[i], conID });
			try {
				if (rs.next()) {
					BigDecimal planPay = new BigDecimal(0);
					planPay = rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : planPay;
					ir.getCell(index[i]).setValue(planPay);
				}

			} catch (SQLException e) {
				throw new BOSException();
			}

		}

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

	protected void _setBackByEntryID(Context ctx, String entryID)
			throws BOSException {

		if (new FDCDepConPayPlanContractInfo().getBOSType().equals(
				BOSUuid.read(entryID).getType())) {
			try {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("head.id");
				sic.add("isBack");
				sic.add("isEdit");
				IFDCDepConPayPlanContract is = FDCDepConPayPlanContractFactory
						.getLocalInstance(ctx);
				FDCDepConPayPlanContractInfo info = is
						.getFDCDepConPayPlanContractInfo(new ObjectUuidPK(
								entryID), sic);
				info.setIsBack(true);
				info.setIsEdit(false);
				// 将分录打回后，将表头也打回
				is.updatePartial(info, sic);
				_setBack(ctx, info.getHead().getId());
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		} else if (new FDCDepConPayPlanUnsettledConInfo().getBOSType().equals(
				BOSUuid.read(entryID).getType())) {
			try {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("parent.id");
				sic.add("isBack");
				sic.add("isEdit");
				IFDCDepConPayPlanUnsettledCon is = FDCDepConPayPlanUnsettledConFactory
						.getLocalInstance(ctx);
				FDCDepConPayPlanUnsettledConInfo info = is
						.getFDCDepConPayPlanUnsettledConInfo(new ObjectUuidPK(
								entryID), sic);
				info.setIsBack(true);
				info.setIsEdit(false);
				// 将分录打回后，将表头也打回
				is.updatePartial(info, sic);
				_setBack(ctx, info.getParent().getId());
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		} else if (new FDCDepConPayPlanNoContractInfo().getBOSType().equals(
				BOSUuid.read(entryID).getType())) {
			try {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("head.id");
				sic.add("isBack");
				sic.add("isEdit");
				IFDCDepConPayPlanNoContract is = FDCDepConPayPlanNoContractFactory
						.getLocalInstance(ctx);
				FDCDepConPayPlanNoContractInfo info = is
						.getFDCDepConPayPlanNoContractInfo(new ObjectUuidPK(
								entryID), sic);
				info.setIsBack(true);
				info.setIsEdit(false);
				// 将分录打回后，将表头也打回
				is.updatePartial(info, sic);
				_setBack(ctx, info.getHead().getId());
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
	}
}