package com.kingdee.eas.fdc.finance.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBaseData;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.IPayNoCostSplit4Voucher;
import com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.util.DateTimeUtils;

public class PaymentNoCostSplitControllerBean extends
		AbstractPaymentNoCostSplitControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.PaymentNoCostSplitControllerBean");

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._save(ctx, pk, model);
	}

	protected String _getLogInfo(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		PaymentNoCostSplitInfo info = (PaymentNoCostSplitInfo) super._getValue(
				ctx, pk);
		String retValue = "";
		if (info.getPaymentBill() != null) {
			String id = info.getPaymentBill().getId().toString();
			PaymentBillInfo test = PaymentBillFactory.getLocalInstance(ctx)
					.getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			if (test.getNumber() != null) {
				retValue = test.getNumber();
			}
		}
		return retValue;
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PaymentNoCostSplitInfo bill = (PaymentNoCostSplitInfo) model;
		if (bill.getPaymentBill() != null
				&& bill.getPaymentBill().getCompany() != null
				&& bill.getPaymentBill().getCompany().getId() != null) {
			String id = bill.getPaymentBill().getCompany().getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("company.id", id));
			view.setFilter(filter);
			IBeforeAccountView iBefore = BeforeAccountViewFactory
					.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				BeforeAccountViewInfo info = iBefore
						.getBeforeAccountViewCollection(view).get(0);
				bill.setBeAccount(info);
			}
			//设置orgUnit为付款单的公司ID by sxhong 2008-11-03 16:28:03
			FullOrgUnitInfo orgUnit=new FullOrgUnitInfo();
			orgUnit.setId(BOSUuid.read(id));
			bill.setOrgUnit(orgUnit);
		}

		if (bill.getPaymentBill() != null
				&& bill.getPaymentBill().getContractBillId() != null) {
			String contractId = bill.getPaymentBill().getContractBillId();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractId", contractId));
			view.setFilter(filter);
			IContractBaseData iBefore = ContractBaseDataFactory
					.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				ContractBaseDataInfo info = iBefore
						.getContractBaseDataCollection(view).get(0);
				bill.setContractBaseData(info);
			}
			if (isConWithoutTxt(bill)) {
				bill.setConWithoutText(ContractWithoutTextFactory
						.getLocalInstance(ctx).getContractWithoutTextInfo(
								new ObjectUuidPK(BOSUuid.read(contractId))));
			} else {
				bill.setContractBill(ContractBillFactory.getLocalInstance(ctx)
						.getContractBillInfo(
								new ObjectUuidPK(BOSUuid.read(contractId))));
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("paymentBill.id", bill.getPaymentBill()
							.getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("Fivouchered", Boolean.TRUE));
			view.setFilter(filter);
			int voucherSize = super.getPaymentNoCostSplitCollection(ctx, view)
					.size();
			bill.setVoucherTimes(voucherSize);
		}

		if (bill.getPaymentBill() != null
				&& bill.getPaymentBill().getPayerAccount() != null) {
			if (bill.getPaymentBill().getPayerAccount().isIsBank()
					|| bill.getPaymentBill().getPayerAccount().isIsCash()
					|| bill.getPaymentBill().getPayerAccount()
							.isIsCashEquivalent()) {
				bill.setIsNeedTransit(true);
				bill.setTransitAccount(FDCUtils.getDefaultFDCParamAccount(ctx,
						bill.getPaymentBill().getCompany().getId().toString()));
			}
		}
		IObjectPK pk = super._save(ctx, bill);
		PaymentNoCostSplitInfo splitBillInfo = null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("paymentBill.curProject.id");
		selectorGet.add("paymentBill.fdcPayReqID");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		selectorGet.add("period.beginDate");
		selectorGet.add("period.endDate");
		selectorGet.add("createTime");
		selectorGet.add("paymentBill.id");
		splitBillInfo = super.getPaymentNoCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getPaymentBill().getCurProject().getId()
				.toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjID, false);
		Date bookedDate = DateTimeUtils.truncateDate(splitBillInfo
				.getCreateTime());
		if (currentPeriod != null && splitBillInfo.getPeriod() == null) {
			String payreqID = splitBillInfo.getPaymentBill().getFdcPayReqID();
			SelectorItemCollection reqPer = new SelectorItemCollection();
			reqPer.add("id");
			reqPer.add("period.number");
			reqPer.add("period.beginDate");
			reqPer.add("period.endDate");
			PayRequestBillInfo reqInfo = PayRequestBillFactory
					.getLocalInstance(ctx).getPayRequestBillInfo(
							new ObjectUuidPK(BOSUuid.read(payreqID)),
							selectorGet);
			PeriodInfo contractPeriod = reqInfo.getPeriod();
			if (contractPeriod != null
					&& contractPeriod.getNumber() > currentPeriod.getNumber()) {
				if (bookedDate.before(contractPeriod.getBeginDate())) {
					bookedDate = contractPeriod.getBeginDate();
				} else if (bookedDate.after(contractPeriod.getEndDate())) {
					bookedDate = contractPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder
						.appendSql("update T_FNC_PaymentNoCostSplit set FPeriodID=?, FBookedDate=? where fid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();
			} else if (currentPeriod != null) {
				if (bookedDate.before(currentPeriod.getBeginDate())) {
					bookedDate = currentPeriod.getBeginDate();
				} else if (bookedDate.after(currentPeriod.getEndDate())) {
					bookedDate = currentPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder
						.appendSql("update T_FNC_PaymentNoCostSplit set FPeriodID=?, FBookedDate=? where fid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();
			}
		} else if (splitBillInfo.getPeriod() != null) {
			if (bookedDate.before(splitBillInfo.getPeriod().getBeginDate())) {
				bookedDate = splitBillInfo.getPeriod().getBeginDate();
			} else if (bookedDate.after(splitBillInfo.getPeriod().getEndDate())) {
				bookedDate = splitBillInfo.getPeriod().getEndDate();
			}
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder
					.appendSql("update T_FNC_PaymentNoCostSplit set FBookedDate=? where FID=?");
			builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
			builder.addParam(splitBillInfo.getId().toString());
			builder.executeUpdate();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.id", pk.toString()));
		view.getSorter().add(new SorterItemInfo("seq"));
		view.setFilter(filter);
		SelectorItemCollection selector = view.getSelector();
		selector.add("id");
		selector.add("seq");
		selector.add("index");
		// selector.add("*");

		PaymentNoCostSplitEntryCollection entrys = PaymentNoCostSplitEntryFactory
				.getLocalInstance(ctx).getPaymentNoCostSplitEntryCollection(
						view);
		PaymentNoCostSplitEntryInfo entry = null;

		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (PaymentNoCostSplitEntryInfo) iter.next();
			setEntrySeq(entry);
			_updatePartial(ctx, entry, selector);
		}
		// 处理拆分汇总
		return pk;
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		super._delete(ctx, arrayPK);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		super._delete(ctx, pk);
	}

	protected void _reverseSave(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._reverseSave(ctx, pk, model);
	}

	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {

		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("paymentBill.id");
		sic.add("paymentBill.fiVouchered");
		sic.add("paymentBill.accountant");
		sic.add("paymentBill.voucher");
		sic.add("paymentBill.voucherType");
		sic.add("paymentBill.company");
		PaymentNoCostSplitInfo payBillInfo = (PaymentNoCostSplitInfo) getValue(
				ctx, srcBillPK, sic);
		IPaymentBill paymentBill = PaymentBillFactory.getLocalInstance(ctx);
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			// String companyId =
			// payBillInfo.getPaymentBill().getCompany().getId().toString();
			UserInfo userInfo = ContextHelperFactory.getLocalInstance(ctx)
					.getCurrentUser();
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				payBillInfo.getPaymentBill().setFiVouchered(true);
				payBillInfo.getPaymentBill().setAccountant(userInfo);
				// payBillInfo.setBillStatus(BillStatusEnum.VOUCHERED);
				VoucherInfo voucherInfo = (VoucherInfo) VoucherFactory
						.getLocalInstance(ctx).getValue(
								new ObjectStringPK(botRelation
										.getDestObjectID()));
				if(payBillInfo.getVoucherTimes()>0){
					VoucherTypeInfo type = FDCUtils.getDefaultFDCParamVoucherType(
							ctx, payBillInfo.getPaymentBill().getCompany().getId().toString());
					if(type!=null){
						FDCSQLBuilder builderUpdate = new FDCSQLBuilder(ctx);
						builderUpdate.clear();
						builderUpdate
								.appendSql("update t_gl_voucher set FVoucherTypeID=? where fid=?");
						builderUpdate.addParam(type.getId().toString());
						builderUpdate.addParam(voucherInfo.getId().toString());
						builderUpdate.executeUpdate();
					}
				}
				payBillInfo.getPaymentBill().setVoucher(voucherInfo);
				payBillInfo.getPaymentBill().setVoucherType(
						voucherInfo.getVoucherType());
				payBillInfo.getPaymentBill().setVoucherNumber(voucherInfo.getNumber());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("accountant");
				selector.add("voucher");
				selector.add("voucherType");
				selector.add("voucherNumber");
				paymentBill.updatePartial(payBillInfo.getPaymentBill(),
						selector);
			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				payBillInfo.getPaymentBill().setFiVouchered(false);
				payBillInfo.getPaymentBill().setAccountant(null);
				// payBillInfo.setBillStatus(BillStatusEnum.PAYED);
				payBillInfo.getPaymentBill().setVoucher(null);
				payBillInfo.getPaymentBill().setVoucherType(null);
				payBillInfo.getPaymentBill().setVoucherNumber(null);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("accountant");
				selector.add("voucher");
				selector.add("voucherType");
				selector.add("voucherNumber");
				paymentBill.updatePartial(payBillInfo.getPaymentBill(),
						selector);
			}
		}
	}

	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		DAPTransformResult result = super._generateVoucher(ctx, sourceBillCollection, botMappingPK);
		_afterVoucher(ctx, sourceBillCollection);
		return result;
	}

	protected void _generateVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {
		super._generateVoucher(ctx, sourceBillPk);
		IObjectCollection sourceBillCollection = new CoreBillBaseCollection();
		PaymentNoCostSplitInfo info = super.getPaymentNoCostSplitInfo(ctx,
				sourceBillPk);
		sourceBillCollection.addObject(info);
		_afterVoucher(ctx, sourceBillCollection);
	}

	protected DAPTransformResult _generateVoucher(Context ctx, IObjectPK[] sourceBillPkList,
			IObjectPK botMappingPK, SelectorItemCollection botpSelectors)
			throws BOSException, EASBizException {
		return super._generateVoucher(ctx, sourceBillPkList, botMappingPK,
				botpSelectors);
	}

	protected void _generateVoucher(Context ctx, IObjectPK[] sourceBillPkList)
			throws BOSException, EASBizException {
		super._generateVoucher(ctx, sourceBillPkList);
		int size = sourceBillPkList.length;
		IObjectCollection sourceBillCollection = new CoreBillBaseCollection();
		for (int i = 0; i < size; i++) {
			PaymentNoCostSplitInfo info = super.getPaymentNoCostSplitInfo(ctx,
					sourceBillPkList[i]);
			sourceBillCollection.addObject(info);
		}
		_afterVoucher(ctx, sourceBillCollection);
	}

	/**
	 * 判断选择行是不是无文本合同，选择多行返回false
	 * 
	 * @author sxhong Date 2006-12-1
	 * @param table
	 * @return isConWithoutTxt
	 */
	private boolean isConWithoutTxt(PaymentNoCostSplitInfo billInfo) {
		BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
				.getBOSType();
		boolean isConWithoutTxt = false;
		isConWithoutTxt = BOSUuid.read(
				billInfo.getPaymentBill().getContractBillId()).getType()
				.equals(withoutTxtConBosType);
		return isConWithoutTxt;
	}

	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("paymentBill.id");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.fiVouchered");
		PaymentNoCostSplitInfo payBillInfo = super.getPaymentNoCostSplitInfo(
				ctx, sourceBillPk, sic);
		if (!isConWithoutTxt(payBillInfo)) {
			if (payBillInfo.getPaymentBill() != null
					&& payBillInfo.getPaymentBill().getFdcPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType().getId().toString().equals(
									PaymentTypeInfo.progressID)) {
				FilterInfo filter = new FilterInfo();
				// filter.getFilterItems().add(new
				// FilterItemInfo("paymentBill.fdcPayType..payType.id",payBillInfo.getPaymentBill().getContractBillId()));
				filter.getFilterItems().add(
						new FilterItemInfo("isProgress", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId",
								payBillInfo.getPaymentBill()
										.getContractBillId()));
				if (super.exists(ctx, filter)) {
					throw new com.kingdee.eas.fdc.finance.PaymentSplitException(
							PaymentSplitException.HAD_SETTLE);
				}
			}
		}
		boolean success = super._deleteVoucher(ctx, sourceBillPk);
		if (success) {
			payBillInfo.setHisStatus(null);
			payBillInfo.setVoucherTimes(0);
			payBillInfo.setHisVoucher(null);
			SelectorItemCollection sele = new SelectorItemCollection();
			sele.add("hisStatus");
			sele.add("voucherTimes");
			sele.add("hisVoucher");
			PaymentNoCostSplitFactory.getLocalInstance(ctx).updatePartial(
					payBillInfo, sele);
		}
		return success;
	}

	protected void _afterVoucher(Context ctx,
			IObjectCollection sourceBillCollection) throws BOSException,
			EASBizException {
		CoreBillBaseCollection coll = (CoreBillBaseCollection) sourceBillCollection;
		int size = coll.size();
		PaymentNoCostSplitInfo info = new PaymentNoCostSplitInfo();
		for (int i = 0; i < size; i++) {
			info = (PaymentNoCostSplitInfo) coll.get(i);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("hisStatus.*");
			sic.add("hisVoucher.*");
			sic.add("beAccount.*");
			sic.add("beAccount.proAccount.*");
			sic.add("paymentBill.id");
			sic.add("paymentBill.contractBillId");
			sic.add("paymentBill.fdcPayType.payType.id");
			sic.add("paymentBill.fiVouchered");
			sic.add("paymentBill.accountant");
			sic.add("paymentBill.voucher.*");
			sic.add("paymentBill.voucherType");
			sic.add("paymentBill.company");
			sic.add("paymentBill.curProject.projectStatus.*");
			PaymentNoCostSplitInfo payBillInfo = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).getPaymentNoCostSplitInfo(
							new ObjectUuidPK(info.getId()), sic);
			payBillInfo.setHisStatus(payBillInfo.getPaymentBill()
					.getCurProject().getProjectStatus());
			int times = payBillInfo.getVoucherTimes();
			payBillInfo.setVoucherTimes(times + 1);
			payBillInfo
					.setHisVoucher(payBillInfo.getPaymentBill().getVoucher());
			SelectorItemCollection sele = new SelectorItemCollection();
			sele.add("hisStatus");
			sele.add("voucherTimes");
			sele.add("hisVoucher");
			super.updatePartial(ctx, payBillInfo, sele);
			IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
			// if (payBillInfo.getVoucherTimes() == 0
			// || !payBillInfo.isFivouchered()) {
			if (!payBillInfo.isIsConWithoutText()) {
				if (payBillInfo.getPaymentBill() != null
						&& payBillInfo.getPaymentBill().getFdcPayType() != null) {
					PaymentTypeInfo paymentType = PaymentTypeFactory
							.getLocalInstance(ctx).getPaymentTypeInfo(
									new ObjectUuidPK(payBillInfo
											.getPaymentBill().getFdcPayType()
											.getId()));
					if (paymentType.getPayType().getId().toString().equals(
							PaymentTypeInfo.progressID)) {
						continue;
					} else if (paymentType.getPayType().getId().toString()
							.equals(PaymentTypeInfo.settlementID)) {
						if (!payBillInfo.isIsProgress()) {
							continue;
						} else {
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(
									new FilterItemInfo(
											"paymentBill.contractBillId",
											payBillInfo.getPaymentBill()
													.getContractBillId()));
							// filter.getFilterItems()
							// .add(
							// new FilterItemInfo(
							// "isRedVouchered",
							// Boolean.TRUE,
							// CompareType.NOTEQUALS));
							filter.getFilterItems().add(
									new FilterItemInfo("state",
											FDCBillStateEnum.INVALID_VALUE,
											CompareType.NOTEQUALS));
							filter
									.getFilterItems()
									.add(
											new FilterItemInfo(
													"paymentBill.fdcPayType.payType.id",
													PaymentTypeInfo.progressID));
							view.setFilter(filter);
							view.getSelector().add("id");
							view.getSelector().add("hisVoucher.id");
							PaymentNoCostSplitCollection payColl = PaymentNoCostSplitFactory
									.getLocalInstance(ctx)
									.getPaymentNoCostSplitCollection(view);
							List idList = new ArrayList();
							VoucherInfo newVoucher = new VoucherInfo();
							for (Iterator iter = payColl.iterator(); iter
									.hasNext();) {
								PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
										.next();

								EntityViewInfo mapping = new EntityViewInfo();
								FilterInfo mappingFilter = new FilterInfo();
								mappingFilter.getFilterItems().add(
										new FilterItemInfo("srcEntityID",
												element.getBOSType()));
								mappingFilter.getFilterItems().add(
										new FilterItemInfo("destEntityID",
												newVoucher.getBOSType()));
								mappingFilter.getFilterItems().add(
										new FilterItemInfo("srcObjectID",
												element.getId().toString()));
								mapping.setFilter(mappingFilter);
								mapping.getSelector().add("id");
								mapping.getSelector().add("destObjectID");
								mapping.getSorter().add(
										new SorterItemInfo("date"));
								BOTRelationCollection relationColl = BOTRelationFactory
										.getLocalInstance(ctx).getCollection(
												mapping);
								if (relationColl.size() > 0) {
									BOTRelationInfo botInfo = relationColl
											.get(relationColl.size() - 1);
									SelectorItemCollection voucherSelector = new SelectorItemCollection();
									voucherSelector.add("id");
									voucherSelector.add("hasReversed");
									VoucherInfo oldInfo = (VoucherInfo) voucher
											.getValue(
													new ObjectUuidPK(
															BOSUuid
																	.read(botInfo
																			.getDestObjectID())),
													voucherSelector);
									if (!oldInfo.isHasReversed()) {
										idList.add(botInfo.getDestObjectID());
										element.setIsRedVouchered(true);
										SelectorItemCollection selector = new SelectorItemCollection();
										selector.add("hisVoucher");
										selector.add("isRedVouchered");
										super.updatePartial(ctx, element,
												selector);
									}
								}
							}
							if (idList.size() > 0) {
								IObjectPK pk = voucher.reverseSaveBatch(idList);
								PaySplitUtilFacadeFactory.getLocalInstance(ctx)
										.traceReverseVoucher(pk);
							}
						}
					}
				}
			}
			// }
		}
	}

	protected SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("contractBaseData.id"));
		sic.add(new SelectorItemInfo("contractBaseData.number"));
		sic.add(new SelectorItemInfo("contractBaseData.name"));
		sic.add(new SelectorItemInfo("paymentBill.*"));
		sic.add(new SelectorItemInfo("paymentBill.company.*"));
		sic.add(new SelectorItemInfo("paymentBill.currency.*"));
		sic.add(new SelectorItemInfo("paymentBill.payerAccount.*"));
		sic.add(new SelectorItemInfo("paymentBill.payerAccountBank.*"));
		sic.add(new SelectorItemInfo("paymentBill.actFdcPayeeName.*"));
		sic.add("paymentBill.fdcPayType.payType.id");
		sic
				.add(new SelectorItemInfo(
						"paymentBill.curProject.projectStatus.id"));
		sic.add(new SelectorItemInfo("beAccount.*"));
		sic.add(new SelectorItemInfo("beAccount.proAccount.*"));
		sic.add(new SelectorItemInfo("beAccount.beforeOtherAccount.*"));
		sic.add(new SelectorItemInfo("beAccount.proAccount.id"));
		sic.add(new SelectorItemInfo("beAccount.settAccount.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.curProject.*"));
		sic.add(new SelectorItemInfo("entrys.product.*"));
		sic.add(new SelectorItemInfo("entrys.apportionType.*"));
		sic.add(new SelectorItemInfo("entrys.accountView.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.bankAccount.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.accountView.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.currency.*"));
		return sic;
	}

	public SelectorItemCollection getSelectItemForBTP() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("contractBaseData.id"));
		sic.add(new SelectorItemInfo("contractBaseData.number"));
		sic.add(new SelectorItemInfo("contractBaseData.name"));
		sic.add(new SelectorItemInfo("paymentBill.*"));
		sic.add(new SelectorItemInfo("paymentBill.company.*"));
		sic.add(new SelectorItemInfo("paymentBill.currency.*"));
		sic.add(new SelectorItemInfo("paymentBill.payerAccount.*"));
		sic.add(new SelectorItemInfo("paymentBill.payerAccountBank.*"));
		sic.add(new SelectorItemInfo("paymentBill.actFdcPayeeName.*"));
		sic.add("paymentBill.fdcPayType.payType.id");
		sic
				.add(new SelectorItemInfo(
						"paymentBill.curProject.projectStatus.id"));
		sic.add(new SelectorItemInfo("beAccount.*"));
		sic.add(new SelectorItemInfo("beAccount.proAccount.*"));
		sic.add(new SelectorItemInfo("beAccount.beforeOtherAccount.*"));
		sic.add(new SelectorItemInfo("beAccount.proAccount.id"));
		sic.add(new SelectorItemInfo("beAccount.settAccount.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.curProject.*"));
		sic.add(new SelectorItemInfo("entrys.product.*"));
		sic.add(new SelectorItemInfo("entrys.apportionType.*"));
		sic.add(new SelectorItemInfo("entrys.accountView.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.bankAccount.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.accountView.*"));
		sic.add(new SelectorItemInfo("voucherEntrys.currency.*"));
		return sic;
	}

	protected void _traceData(Context ctx, List idList) throws BOSException,
			EASBizException {
		Set idSet = FDCHelper.list2Set(idList);
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("*");
		viewSplit.getSelector().add("voucherEntrys.*");
		viewSplit.getSelector().add("paymentBill.id");
		viewSplit.getSelector().add("paymentBill.payerAccount.id");
		viewSplit.getSelector().add("paymentBill.payerAccount.isBank");
		viewSplit.getSelector().add("paymentBill.payerAccount.isCash");
		viewSplit.getSelector()
				.add("paymentBill.payerAccount.isCashEquivalent");
		viewSplit.getSelector().add("paymentBill.company.id");
		viewSplit.getSelector().add("paymentBill.company.name");
		viewSplit.getSelector().add("paymentBill.company.number");
		viewSplit.getSelector().add("beAccount.*");
		viewSplit.getSelector().add("beAccount.proAccount.*");
		viewSplit.getSelector().add("beAccount.settAccount.*");
		PaymentNoCostSplitCollection splitColl = super
				.getPaymentNoCostSplitCollection(ctx, viewSplit);
		int splitSize = splitColl.size();
		PaymentNoCostSplitInfo splitInfo = new PaymentNoCostSplitInfo();
		IBeforeAccountView iBefore = BeforeAccountViewFactory
				.getLocalInstance(ctx);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("beAccount.*");
		selector.add("transitAccount.*");
		selector.add("isNeedTransit");
		selector.add("voucherTimes");
		SelectorItemCollection selectorEntry = new SelectorItemCollection();
		selectorEntry.add("transitAccount.*");
		selectorEntry.add("isNeedTransit");
		IPayNoCostSplit4Voucher voucherSplit = PayNoCostSplit4VoucherFactory
				.getLocalInstance(ctx);
		for (int i = 0; i < splitSize; i++) {
			splitInfo = (PaymentNoCostSplitInfo) splitColl.get(i);
			// if (splitInfo.getBeAccount() == null) {
			if (splitInfo.getPaymentBill() != null
					&& splitInfo.getPaymentBill().getCompany() != null
					&& splitInfo.getPaymentBill().getCompany().getId() != null) {
				String id = splitInfo.getPaymentBill().getCompany().getId()
						.toString();
				AccountViewInfo info = FDCUtils.getDefaultFDCParamAccount(ctx,
						id);
				if (splitInfo.getPaymentBill() != null
						&& splitInfo.getPaymentBill().getPayerAccount() != null
						&& (splitInfo.getPaymentBill().getPayerAccount()
								.isIsBank()
								|| splitInfo.getPaymentBill().getPayerAccount()
										.isIsCash() || splitInfo
								.getPaymentBill().getPayerAccount()
								.isIsCashEquivalent())) {
					splitInfo.setIsNeedTransit(true);
					if (info.getId() != null) {
						splitInfo.setTransitAccount(info);
					} else
						throw new FDCException(FDCException.NOACCOUNTVIEW);
				}
				for (int entry = 0, size = splitInfo.getVoucherEntrys().size(); entry < size; entry++) {
					if (splitInfo.getVoucherEntrys().get(entry)
							.isIsNeedTransit()) {
						if (info.getId() != null) {
							splitInfo.getVoucherEntrys().get(entry)
									.setTransitAccount(info);
							voucherSplit.updatePartial(splitInfo
									.getVoucherEntrys().get(entry),
									selectorEntry);
						} else
							throw new FDCException(FDCException.NOACCOUNTVIEW);
					}
				}
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("company.id", id));
				view.setFilter(filter);
				boolean has = iBefore.exists(filter);
				if (has) {
					BeforeAccountViewInfo account = iBefore
							.getBeforeAccountViewCollection(view).get(0);
					splitInfo.setBeAccount(account);
				} else
					throw new CostSplitException(CostSplitException.NOBEACCOUNT);
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.id", splitInfo
								.getPaymentBill().getId().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE));
				view.setFilter(filter);
				int voucherSize = super.getPaymentNoCostSplitCollection(ctx,
						view).size();
				splitInfo.setVoucherTimes(voucherSize);
				// }
			}
			super.updatePartial(ctx, splitInfo, selector);
		}
	}
}