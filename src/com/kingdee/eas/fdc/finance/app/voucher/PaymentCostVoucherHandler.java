package com.kingdee.eas.fdc.finance.app.voucher;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.finance.FDCCostVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.FDCCostVoucherEntryInfo;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class PaymentCostVoucherHandler extends AbstractFDCVoucherHandler implements IFDCCostVoucherHandler{
	public PaymentCostVoucherHandler(Context ctx,IFDCVoucherEntryCreator creator) {
		setCtx(ctx);
		setFDCVoucherEntryCreator(creator);
	}

	public  IObjectCollection createCostEntrys(Map dataMap) throws EASBizException, BOSException {
		init(dataMap);
		IObjectCollection coll = null;
		/***
		 * 奖励单独拉出来同步
		 */
		coll = createGuerdonCostEntrys(dataMap);
		
		//TODO 简单模式处理发票：单边凭证处理(1)
		if(isSimpleInvoice){
			if(isPaymentSplit){
				IObjectCollection paySplitInfos=(IObjectCollection)dataMap.get("paySplitInfos");
				for(Iterator iter=paySplitInfos.iterator();iter.hasNext();){
					IObjectCollection onecoll=createSimpleInvoiceOnePaymentCostEntry(dataMap,(IObjectValue)iter.next());
					if(coll==null)
						coll = onecoll;
					else
						coll.addObjectCollection(onecoll);
				}
			}else if(!isPaymentSplit&&isInvoiceOffset){
				coll = createSimpleSideInvoiceCostEntrys(dataMap);
				
			}
			return coll;
		}
		IObjectCollection paySplitInfos=(IObjectCollection)dataMap.get("paySplitInfos");
		for(Iterator iter=paySplitInfos.iterator();iter.hasNext();){			
			IObjectCollection onecoll=createOnePaymentCostEntry((IObjectValue)iter.next());
			if(coll==null)
				coll = onecoll;
			else
				coll.addObjectCollection(onecoll);
		}
		return coll;
	}

	protected IObjectCollection createGuerdonCostEntrys(Map dataMap) throws EASBizException, BOSException{
		IObjectCollection coll = new FDCCostVoucherEntryCollection();
		/**
		 * 如果是简单模式一体化，且不处理
		 */
		if(financial.equals(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)&&!financialExtend){
			return coll;
		}
		/***
		 * 处理奖励
		 * 同步奖励单到借方分录
		 */
		
		GuerdonOfPayReqBillCollection guerdonBills = 
			(GuerdonOfPayReqBillCollection)dataMap.get(GUERDONBILLS);
		for(Iterator it=guerdonBills.iterator();it.hasNext();){
			GuerdonOfPayReqBillInfo info = (GuerdonOfPayReqBillInfo)it.next();
			if(info.getPaymentBill()!=null){
				FDCCostVoucherEntryInfo costEntryInfo = new FDCCostVoucherEntryInfo();
				costEntryInfo.setId(BOSUuid.create(costEntryInfo.getBOSType()));
				costEntryInfo.setAmount(info.getGuerdon().getOriginalAmount());
				costEntryInfo.setLocateAmount(info.getGuerdon().getAmount());
				costEntryInfo.setExchangeRate(info.getPaymentBill().getExchangeRate());
				costEntryInfo.setDesc(info.getGuerdon().getGuerdonDes());
				costEntryInfo.setSupplier(info.getPaymentBill().getActFdcPayeeName());
				costEntryInfo.setCurrency(getBaseCurrency());
				costEntryInfo.setParent(info.getPaymentBill());
				//
				costEntryInfo.setType(FDCConstants.FDC_INIT_GUERDON);
				//工程项目
				costEntryInfo.setCurProject(info.getPaymentBill().getCurProject());
				if(beforeAccountViewInfo!=null){
					costEntryInfo.setAccountView(beforeAccountViewInfo.getGuerdonAccount());
				}
				coll.addObject(costEntryInfo);
			}
		}
		return coll;
	}

	/**
	 * 根据一个付款拆分生成一个付款的单借方科目，凭证的科目通过调用getAccountView()方法取得
	 * 拆分上的付款是全的
	 * 注意：付款拆分有文本以及非文本
	 * @param splitInfo
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws Exception 
	 */
	protected IObjectCollection createSimpleInvoiceOnePaymentCostEntry(Map dataMap,IObjectValue splitInfo) throws EASBizException, BOSException{
		/***
		 * 传入的splitInfo需要instanceof
		 * isCost  or   NoCost
		 * 周勇
		 */
		IObjectCollection coll = new FDCCostVoucherEntryCollection();
		if(splitInfo instanceof PaymentSplitInfo){
			PaymentSplitInfo paymentSplitInfo = (PaymentSplitInfo)splitInfo;
			for(Iterator it=paymentSplitInfo.getEntrys().iterator();it.hasNext();){
				PaymentSplitEntryInfo entryInfo = (PaymentSplitEntryInfo)it.next();
				
				if(entryInfo.isIsLeaf()&&entryInfo.getCostAccount()!=null){
					boolean isConWithOutText = ((PaymentSplitInfo)splitInfo).isIsConWithoutText();
					
					/***
					 * 
					 * 暂估款
					 * 应付账款_暂估（财务成本一体化科目设置上“应付帐款_开发成本_暂估款”对应科目）  发票拆分金额
					 */
					
					if(paymentSplitInfo.getPaymentBill().getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.tempID))
					{
						IObjectValue entry = createTempCostEntry(entryInfo,paymentSplitInfo.getPaymentBill(), paymentSplitInfo);
						if(entry!=null)
							coll.addObject(entry);
						continue;
					}
					// 简单模式处理发票
					if (!isConWithOutText) {
						// 存在拆分
						if (isInvoiceOffset) {
							// 有冲抵过程
							addSimpleInvoiceOffsetVoucherCostEntry(dataMap,coll,
									paymentSplitInfo.getPaymentBill(),
									entryInfo);
						} else {
							// 无总冲抵过程
							addSimpleInvoiceCostEntry(dataMap,coll,
									paymentSplitInfo.getPaymentBill(),
									entryInfo);
						}
						continue;// 简单模式处理发票不考虑款类型,不处理无文本
					}

					IObjectValue entry = createCostEntry(entryInfo,paymentSplitInfo.getPaymentBill(),paymentSplitInfo);
					if(entry!=null){
						coll.addObject(entry);
					}
				}
			}
		}
		else if(splitInfo instanceof PaymentNoCostSplitInfo){
			PaymentNoCostSplitInfo paymentNoCostSplitInfo = (PaymentNoCostSplitInfo)splitInfo;
			for(Iterator it=((PaymentNoCostSplitInfo)splitInfo).getEntrys().iterator();it.hasNext();){
				PaymentNoCostSplitEntryInfo entryInfo = (PaymentNoCostSplitEntryInfo)it.next();
				if(entryInfo.isIsLeaf()&&entryInfo.getAccountView()!=null){
					
					boolean isConWithOutText = ((PaymentNoCostSplitInfo)splitInfo).isIsConWithoutText();
					
					/***
					 * 
					 * 暂估款
					 * 应付账款_暂估（财务成本一体化科目设置上“应付帐款_开发成本_暂估款”对应科目）  发票拆分金额
					 */
					
					if(paymentNoCostSplitInfo.getPaymentBill().getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.tempID))
					{
						IObjectValue entry = createTempCostEntry(entryInfo,paymentNoCostSplitInfo.getPaymentBill(), paymentNoCostSplitInfo);
						if(entry!=null)
							coll.addObject(entry);
						continue;
					}
					if (!isConWithOutText) {
						if (isInvoiceOffset) {
							addSimpleInvoiceOffsetVoucherCostEntry(dataMap,coll,
									paymentNoCostSplitInfo.getPaymentBill(),
									entryInfo);
						} else {
							addSimpleInvoiceCostEntry(dataMap,coll,
									paymentNoCostSplitInfo.getPaymentBill(),
									entryInfo);
						}
						continue;// 简单模式处理发票不考虑款类型,不处理无文本
					}
					coll.addObject(createCostEntry(entryInfo,paymentNoCostSplitInfo.getPaymentBill(),paymentNoCostSplitInfo));
				}
			}
		}
		
		return coll;
	}
	
	/**
	 * 根据一个付款拆分生成一个付款的单借方科目，凭证的科目通过调用getAccountView()方法取得
	 * 拆分上的付款是全的
	 * 注意：付款拆分有文本以及非文本
	 * @param splitInfo
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws Exception 
	 */
	protected IObjectCollection createOnePaymentCostEntry(IObjectValue splitInfo) throws EASBizException, BOSException{
		/***
		 * 传入的splitInfo需要instanceof
		 * isCost  or   NoCost
		 * 周勇
		 */
		IObjectCollection coll = new FDCCostVoucherEntryCollection();
		if(splitInfo instanceof PaymentSplitInfo){
			PaymentSplitInfo paymentSplitInfo = (PaymentSplitInfo)splitInfo;
			for(Iterator it=paymentSplitInfo.getEntrys().iterator();it.hasNext();){
				PaymentSplitEntryInfo entryInfo = (PaymentSplitEntryInfo)it.next();
				
				if(entryInfo.isIsLeaf()&&entryInfo.getCostAccount()!=null){
					
					IObjectValue entry = createCostEntry(entryInfo,paymentSplitInfo.getPaymentBill(),paymentSplitInfo);
					if(entry!=null){
						coll.addObject(entry);
					}
					if(isInvoiceMrg){
						entry = createInvoiceCostEntry(entryInfo, paymentSplitInfo.getPaymentBill(), paymentSplitInfo);
						if(entry!=null){
							coll.addObject(entry);
						}
					}
				}
			}
			/***
			 * 预付款为正数时，增加一分录，预付款借方
			 */
			if(paymentSplitInfo!=null&&paymentSplitInfo.getPaymentBill()!=null&&paymentSplitInfo.getPaymentBill().getContractBillId()!=null){
				if(this.isAdvance(paymentSplitInfo.getPaymentBill().getContractBillId())){
					if(paymentSplitInfo.getPaymentBill().getPrjPayEntry()!=null){
						if(FDCHelper.toBigDecimal(paymentSplitInfo.getPaymentBill().getPrjPayEntry().getAdvance()).signum()>0){
							coll.addObject(createAdvanceCostEntry(paymentSplitInfo.getPaymentBill()));
						}
					}
				}
			}
		}
		else if(splitInfo instanceof PaymentNoCostSplitInfo){
			PaymentNoCostSplitInfo paymentNoCostSplitInfo = (PaymentNoCostSplitInfo)splitInfo;
			for(Iterator it=((PaymentNoCostSplitInfo)splitInfo).getEntrys().iterator();it.hasNext();){
				PaymentNoCostSplitEntryInfo entryInfo = (PaymentNoCostSplitEntryInfo)it.next();
				if(entryInfo.isIsLeaf()&&entryInfo.getAccountView()!=null){
					coll.addObject(createCostEntry(entryInfo,paymentNoCostSplitInfo.getPaymentBill(),paymentNoCostSplitInfo));
				}
			}
			/***
			 * 预付款为正数时，增加一分录，预付款借方
			 */
			if(paymentNoCostSplitInfo!=null&&paymentNoCostSplitInfo.getPaymentBill()!=null&&paymentNoCostSplitInfo.getPaymentBill().getContractBillId()!=null){
				if(this.isAdvance(paymentNoCostSplitInfo.getPaymentBill().getContractBillId())){
					if(paymentNoCostSplitInfo.getPaymentBill().getPrjPayEntry()!=null){
						if(FDCHelper.toBigDecimal(paymentNoCostSplitInfo.getPaymentBill().getPrjPayEntry().getAdvance()).signum()>0){
							coll.addObject(createAdvanceCostEntry(paymentNoCostSplitInfo.getPaymentBill()));
						}
					}
				}
			}
		}
		
		return coll;
	}
	
	/**
	 * 单边发票凭证
	 * @param dataMap
	 * @return
	 */
	private IObjectCollection createSimpleSideInvoiceCostEntrys(Map dataMap) throws EASBizException, BOSException {
		IObjectCollection coll = new FDCCostVoucherEntryCollection();
		IObjectCollection payInfos = (IObjectCollection)dataMap.get("payInfos");
		for(Iterator iter=payInfos.iterator();iter.hasNext();){
			PaymentBillInfo info = (PaymentBillInfo)iter.next();
			addSimpleInvoiceSideVoucherCostEntry(coll,info);
		}
		return coll;
	}

	
	private void addSimpleInvoiceSideVoucherCostEntry(IObjectCollection coll,
			PaymentBillInfo info) throws EASBizException, BOSException{
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(info.get("totalInvoice"), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(info.get("totalPay"), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(info.get("invoiceAmt"), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(info.getLocalAmt(), 2);
		FDCCostVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 银行存款（付款单上付款科目） 付款拆分金额  付款科目 (注释原因：付款单付款科目凭证会自动生成一条分录，不需要处理)
		// 开发成本（成本科目对应会计科目） 发票拆分金额
//		voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,
//				invoiceAmt);
//		if (voucherEntryInfo != null) {
//			coll.addObject(voucherEntryInfo);
//		}

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,splitedInvoiceAmt.add(invoiceAmt).subtract(splitedPayAmt));
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,beforeOtherAccount,splitedPayAmt.add(payAmt).subtract(splitedInvoiceAmt.add(invoiceAmt)));
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,payAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,payAmt.add(splitedPayAmt).subtract(splitedInvoiceAmt));
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
				

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
				if(invoiceAmt.compareTo(payAmt)<0){
					voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,beforeOtherAccount,payAmt.subtract(invoiceAmt));
					if(voucherEntryInfo!=null){
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,payAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}

			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,beforeOtherAccount,payAmt.subtract(invoiceAmt));
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleSideInvoiceCostEntry(info,proAccount,payAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			}
		}
		
	}
	
	private FDCCostVoucherEntryInfo createSimpleSideInvoiceCostEntry(
			PaymentBillInfo paymentBillInfo,  AccountViewInfo accountView,
			BigDecimal amount) throws EASBizException, BOSException{
		if(FDCHelper.toBigDecimal(amount).signum()==0){
			return null;
		}
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCurProject(paymentBillInfo.getCurProject());
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(amount).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setLocateAmount(amount);
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setAccountView(accountView);
		return voucherEntryInfo;
	}
	
	private IObjectCollection addSimpleInvoiceOffsetVoucherCostEntry(Map dataMap,IObjectCollection coll,
			PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo) throws EASBizException, BOSException{
		Map payedAmtMap = (Map)dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map)dataMap.get("invoiceAmtMap");
		String key = paymentBillInfo.getContractBillId() + entryInfo.getCostAccount().getCurProject().getId().toString()
				+ entryInfo.getCostAccount().getId().toString();
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCCostVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 开发成本（成本科目对应会计科目） 发票拆分金额
		voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
				entryInfo,
				getAccountWithCostAccount(entryInfo.getCostAccount()),
				entryInfo.getInvoiceAmt());
		
		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

					voucherEntryInfo = createSimpleInvoiceCostEntry(
							paymentBillInfo, entryInfo, proAccount, payAmt);
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, splitedInvoiceAmt.add(invoiceAmt)
								.subtract(splitedPayAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.add(payAmt).subtract(splitedInvoiceAmt)
								.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {

			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount,
						payAmt.add(splitedPayAmt).subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount,
						invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				if (invoiceAmt.compareTo(payAmt) < 0) {
					voucherEntryInfo = createSimpleInvoiceCostEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount,
						invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount,invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount,payAmt.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		}
		return coll;
	}
	
	private IObjectCollection addSimpleInvoiceCostEntry(Map dataMap, IObjectCollection coll, PaymentBillInfo paymentBillInfo,
			PaymentSplitEntryInfo entryInfo) throws EASBizException, BOSException {
		this.initSplitBillInfos(dataMap, paymentBillInfo.getId().toString());
		Map payedAmtMap = (Map)dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map)dataMap.get("invoiceAmtMap");
		String key = paymentBillInfo.getContractBillId() + entryInfo.getCostAccount().getCurProject().getId().toString()
				+ entryInfo.getCostAccount().getId().toString();
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCCostVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 开发成本（成本科目对应会计科目） 发票拆分金额
		voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
				entryInfo,
				getAccountWithCostAccount(entryInfo.getCostAccount()),
				entryInfo.getInvoiceAmt());
		
		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				if (invoiceAmt.compareTo(payAmt) < 0) {

					voucherEntryInfo = createSimpleInvoiceCostEntry(
							paymentBillInfo, entryInfo, proAccount, FDCHelper
									.subtract(invoiceAmt, payAmt).abs());

					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, splitedInvoiceAmt
								.subtract(splitedPayAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.add(payAmt).subtract(splitedInvoiceAmt)
								.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, payAmt.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {

			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				if (invoiceAmt.compareTo(payAmt) < 0) {
					voucherEntryInfo = createSimpleInvoiceCostEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {

			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, payAmt
								.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {

			}
		}
		return coll;
	}

	private IObjectCollection addSimpleInvoiceCostEntry(Map dataMap,IObjectCollection coll,
			PaymentBillInfo paymentBillInfo, PaymentNoCostSplitEntryInfo entryInfo) throws EASBizException, BOSException{
		this.initSplitBillInfos(dataMap, paymentBillInfo.getId().toString());
		Map payedAmtMap = (Map)dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map)dataMap.get("invoiceAmtMap");
		String key = paymentBillInfo.getContractBillId() + entryInfo.getCurProject().getId().toString()
				+ entryInfo.getAccountView().getId().toString();
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCCostVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 开发成本（成本科目对应会计科目） 发票拆分金额
		voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
				entryInfo,
				entryInfo.getAccountView(),
				entryInfo.getInvoiceAmt());
		
		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				if (invoiceAmt.compareTo(payAmt) < 0) {

					voucherEntryInfo = createSimpleInvoiceCostEntry(
							paymentBillInfo, entryInfo, proAccount, FDCHelper
									.subtract(invoiceAmt, payAmt).abs());

					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, splitedInvoiceAmt
								.subtract(splitedPayAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.add(payAmt).subtract(splitedInvoiceAmt)
								.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, proAccount, payAmt.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {

			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				if (invoiceAmt.compareTo(payAmt) < 0) {
					voucherEntryInfo = createSimpleInvoiceCostEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {

			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, payAmt
								.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {

			}
		}
		return coll;
	}

	
	private IObjectCollection addSimpleInvoiceOffsetVoucherCostEntry(
			Map dataMap, IObjectCollection coll,
			PaymentBillInfo paymentBillInfo,
			PaymentNoCostSplitEntryInfo entryInfo) throws EASBizException,
			BOSException {
		String key = paymentBillInfo.getContractBillId()
				+ entryInfo.getCurProject().getId().toString()
				+ entryInfo.getAccountView().getId().toString();
		Map payedAmtMap = (Map) dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map) dataMap.get("invoiceAmtMap");
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap
				.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key),
				2);

		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo
				.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCCostVoucherEntryInfo voucherEntryInfo = null;

		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();

		// 开发成本（成本科目对应会计科目） 发票拆分金额
		voucherEntryInfo = createSimpleInvoiceCostEntry(paymentBillInfo,
				entryInfo, entryInfo.getAccountView(), entryInfo
						.getInvoiceAmt());

		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount,
						splitedInvoiceAmt.add(invoiceAmt).subtract(
								splitedPayAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, beforeOtherAccount,
						splitedPayAmt.add(payAmt).subtract(splitedInvoiceAmt)
								.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {

			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, payAmt.add(
								splitedPayAmt).subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				if (invoiceAmt.compareTo(payAmt) < 0) {
					voucherEntryInfo = createSimpleInvoiceCostEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, beforeOtherAccount, payAmt
								.subtract(invoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleInvoiceCostEntry(
						paymentBillInfo, entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		}
		return coll;
	}
	
	private FDCCostVoucherEntryInfo createSimpleInvoiceCostEntry(
			PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo,AccountViewInfo accountView,
			BigDecimal amount) throws EASBizException, BOSException{
		if(FDCHelper.toBigDecimal(amount).signum()==0){
			return null;
		}
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(amount).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setLocateAmount(amount);
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setAccountView(accountView);
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		//产品为空了
		voucherEntryInfo.setProductType(entryInfo.getProduct());
		return voucherEntryInfo;
	}

	private FDCCostVoucherEntryInfo createSimpleInvoiceCostEntry(
			PaymentBillInfo paymentBillInfo, PaymentNoCostSplitEntryInfo entryInfo,AccountViewInfo accountView,
			BigDecimal amount) throws EASBizException, BOSException{
		if(FDCHelper.toBigDecimal(amount).signum()==0){
			return null;
		}
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCurProject(entryInfo.getCurProject());
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(amount).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setLocateAmount(amount);
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setAccountView(accountView);
		return voucherEntryInfo;
	}
	
	protected IObjectValue createAdvanceCostEntry(PaymentBillInfo paymentBillInfo) throws EASBizException, BOSException{
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCostAmt(FDCHelper.ZERO);
		voucherEntryInfo.setPayAmt(FDCHelper.ZERO);
		if(paymentBillInfo.getPrjPayEntry()!=null){
			voucherEntryInfo.setAmount(paymentBillInfo.getPrjPayEntry().getAdvance());
			voucherEntryInfo.setLocateAmount(paymentBillInfo.getPrjPayEntry().getLocAdvance());
		}else{
			voucherEntryInfo.setAmount(FDCHelper.ZERO);
			voucherEntryInfo.setLocateAmount(FDCHelper.ZERO);
		}
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setAccountView(beforeAccountViewInfo.getBeforeOtherAccount());
		//voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		voucherEntryInfo.setParent(paymentBillInfo);
		//TODO
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_ADVANCE);
		voucherEntryInfo.setCurProject(paymentBillInfo.getCurProject());
		return voucherEntryInfo;
	}
	
	/***
	 * 同步PaymentSplitEntry
	 * AccountViewInfo voucherAcct = getAccountView();
	 * @param entryInfo
	 * @param paymentBillInfo
	 * @param splitInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected IObjectValue createCostEntry(PaymentSplitEntryInfo entryInfo,PaymentBillInfo paymentBillInfo,PaymentSplitInfo splitInfo) throws EASBizException, BOSException{ 
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();		
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)||
				(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)&&
						!splitInfo.isIsProgress())){
			voucherEntryInfo.setCostAmt(entryInfo.getCostAmt());
			voucherEntryInfo.setPayAmt(entryInfo.getPayedAmt());
			BigDecimal oriAmount = FDCHelper.ZERO;
			if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
				oriAmount = FDCHelper.toBigDecimal(entryInfo.getPayedAmt()).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
			voucherEntryInfo.setAmount(oriAmount);
			voucherEntryInfo.setLocateAmount(entryInfo.getPayedAmt());
		}else{
			voucherEntryInfo.setCostAmt(entryInfo.getCostAmt());
			voucherEntryInfo.setPayAmt(entryInfo.getPayedAmt());
			BigDecimal oriAmount = FDCHelper.ZERO;
			if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
				oriAmount = FDCHelper.toBigDecimal(entryInfo.getAmount()).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
			voucherEntryInfo.setAmount(oriAmount);
			voucherEntryInfo.setLocateAmount(entryInfo.getAmount());
		}
		
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		
//		String key = entryInfo.getCostAccount().getCurProject().getId().toString() ;
//		key += "_" + entryInfo.getCostAccount().getId().toString();
		AccountViewInfo voucherAcct = getAccountView(entryInfo.getCostAccount(),paymentBillInfo,splitInfo.isIsProgress(),splitInfo.isIsConWithoutText());
		if(isPaymentSplit){
			voucherEntryInfo.setAccountView(voucherAcct);
		}
		
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setProductType(entryInfo.getProduct());
		return voucherEntryInfo;
	}
	/***
	 * 同步PaymentNoCostSplitEntry
	 * @param entryInfo
	 * @param paymentBillInfo
	 * @param splitInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected IObjectValue createCostEntry(PaymentNoCostSplitEntryInfo entryInfo,PaymentBillInfo paymentBillInfo,PaymentNoCostSplitInfo splitInfo) throws EASBizException, BOSException{ 
		 
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		//对于非成本类合同第二笔结算款，应付账款为付款金额。
		if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)||
				(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)&&
						!splitInfo.isIsProgress())){
			BigDecimal oriAmount = FDCHelper.ZERO;
			if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
				oriAmount = FDCHelper.toBigDecimal(entryInfo.getPayedAmt()).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
			voucherEntryInfo.setAmount(oriAmount);
			voucherEntryInfo.setLocateAmount(entryInfo.getPayedAmt());			
		}else{
			BigDecimal oriAmount = FDCHelper.ZERO;
			if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
				oriAmount = FDCHelper.toBigDecimal(entryInfo.getAmount()).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
			voucherEntryInfo.setAmount(oriAmount);
			voucherEntryInfo.setLocateAmount(entryInfo.getAmount());	
		}
		voucherEntryInfo.setCostAmt(entryInfo.getCostAmt());
		voucherEntryInfo.setPayAmt(entryInfo.getPayedAmt());
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		
		voucherEntryInfo.setAccountView(entryInfo.getAccountView());
		/**
		 * 当付非第一笔结算款时，需要取应付帐款-其它-进度款
		 * 当付保修款时，需要取应付帐款-其它-保修款
		 */
		if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)){
			/***
			 * 保修款
			 */
			if(beforeAccountViewInfo!=null){
				voucherEntryInfo.setAccountView(this.beforeAccountViewInfo.getOtherSettAccount());
			}
		}else if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)&&
				!splitInfo.isIsProgress()){
			/***
			 * 非第一笔结算款
			 */
			if(beforeAccountViewInfo!=null){
				voucherEntryInfo.setAccountView(this.beforeAccountViewInfo.getOtherProAccount());
			}
		}
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setCurProject(entryInfo.getCurProject());
		voucherEntryInfo.setProductType(entryInfo.getProduct());
		
		return voucherEntryInfo;
	
	}
	
	/***
	 * 开发成本(成本科目对应会计科目)   发票拆分金额
	 * AccountViewInfo voucherAcct = getAccountView();
	 * @param entryInfo
	 * @param paymentBillInfo
	 * @param splitInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected IObjectValue createTempCostEntry(PaymentSplitEntryInfo entryInfo,PaymentBillInfo paymentBillInfo,PaymentSplitInfo splitInfo) throws EASBizException, BOSException{ 
		if(FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt()).signum()==0){
			return null;
		}
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();		
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCostAmt(entryInfo.getCostAmt());
		voucherEntryInfo.setPayAmt(entryInfo.getPayedAmt());
		BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt());
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(amount).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setLocateAmount(amount);
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		AccountViewInfo voucherAcct = getAccountWithCostAccount(entryInfo.getCostAccount());
		voucherEntryInfo.setAccountView(voucherAcct);
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_INVOICE);
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setProductType(entryInfo.getProduct());
		return voucherEntryInfo;
	}
	/***
	 * 开发成本(成本科目对应会计科目)   发票拆分金额
	 * AccountViewInfo voucherAcct = getAccountView();
	 * @param entryInfo
	 * @param paymentBillInfo
	 * @param splitInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected IObjectValue createTempCostEntry(PaymentNoCostSplitEntryInfo entryInfo,PaymentBillInfo paymentBillInfo,PaymentNoCostSplitInfo splitInfo) throws EASBizException, BOSException{ 
		if(FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt()).signum()==0){
			return null;
		}
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();		
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCostAmt(entryInfo.getCostAmt());
		voucherEntryInfo.setPayAmt(entryInfo.getPayedAmt());
		BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt());
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(amount).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setLocateAmount(amount);
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setAccountView(entryInfo.getAccountView());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_INVOICE);
		voucherEntryInfo.setCurProject(entryInfo.getCurProject());
		voucherEntryInfo.setProductType(entryInfo.getProduct());
		return voucherEntryInfo;
	}
	
	
	/***
	 * 生成发票科目分录，发票科目为成本科目对应的发票科目
	 * AccountViewInfo voucherAcct = getAccountView();
	 * @param entryInfo
	 * @param paymentBillInfo
	 * @param splitInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected IObjectValue createInvoiceCostEntry(PaymentSplitEntryInfo entryInfo,PaymentBillInfo paymentBillInfo,PaymentSplitInfo splitInfo) throws EASBizException, BOSException{ 
		if(FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt()).signum()==0){
			return null;
		}
		FDCCostVoucherEntryInfo voucherEntryInfo = new FDCCostVoucherEntryInfo();		
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
	
		voucherEntryInfo.setCostAmt(entryInfo.getCostAmt());
		voucherEntryInfo.setPayAmt(entryInfo.getPayedAmt());
		voucherEntryInfo.setAmount(entryInfo.getInvoiceAmt());
		voucherEntryInfo.setLocateAmount(entryInfo.getInvoiceAmt());
		
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		
		AccountViewInfo voucherAcct = getInvoiceAccountView(entryInfo.getCostAccount());
		voucherEntryInfo.setAccountView(voucherAcct);
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_INVOICE);
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setProductType(entryInfo.getProduct());
		return voucherEntryInfo;
	}
	
	/***
	 * 成本分录的插入SQL语句
	 */
	private final static String COSTENTRY_DELETE_SQL = "DELETE FROM T_FNC_FDCCostVoucherEntry where ";
	private final static String COSTENTRY_INSERT_SQL = "INSERT INTO T_FNC_FDCCostVoucherEntry" +
			"(FID,FSeq,FCurrencyid,FSupplierid,FAccountViewID,FDesc,FExchangeRate,FLocateAmount,FAmount,FType,FParentID,FProductTypeID,FCurProjectID,FCostAmt,FPayAmt,fcostAccountid) values" +
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final static String PAYENTRY_DELETE_SQL = "DELETE FROM T_FNC_FDCPayVoucherEntry where ";
	
	/***
	 * 更新付款拆分生成凭证的引用属性的SQL 
	 */
	private final static String PAYMENTSPLIT_UPDATE_SQL = "update t_fnc_paymentsplit set FVoucherRefer=?,FVoucherReferId=FPaymentBillId ";
	private final static String PAYMENTNOCOSTSPLIT_UPDATE_SQL = "update t_fnc_paymentnocostsplit set FVoucherRefer=?,FVoucherReferId=FPaymentBillId ";
	
	public void save(Map param, IObjectCollection costEntrys) throws BOSException {
		//TODO
		String paymentBillId ;
		PaymentBillInfo paymentBillInfo = null;
		Map paymentBills = (HashMap)param.get("paymentBills");
		FDCSQLBuilder builder = new FDCSQLBuilder(getCtx());
		beforeSave(paymentBills);
		
		/****
		 * 如果是简单模式一体化，直接将旧的分录删除，然后写新的分录进去
		 * 如果是复杂模式一体化，不更新
		 * 可以多选，可能存在有的已经同步过了，有的未同步
		 * 如果是复杂模式，同步未同步的；
		 */
		
		//cost
		
		builder.setPrepareStatementSql(COSTENTRY_INSERT_SQL);
		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		if(costEntrys!=null){
			for(Iterator it = costEntrys.iterator();it.hasNext();){
				FDCCostVoucherEntryInfo entryInfo = (FDCCostVoucherEntryInfo)it.next();
				paymentBillId = entryInfo.getParent().getId().toString();
				/***
				 * 如果是复杂模式，且已经同步过了
				 * 就不再同步了，直接跳过
				 */
				if(financial.equals(FDCConstants.FDC_PARAM_FINACIAL)&&hasSynchBills.containsKey(paymentBillId)){
					continue;
				}
				if(paymentBills.containsKey(paymentBillId))
				{
					paymentBillInfo = (PaymentBillInfo)paymentBills.get(paymentBillId);
					entryInfo.setSeq(paymentBillInfo.getFdcCostVoucherEntry().size()+1);
					paymentBillInfo.getFdcCostVoucherEntry().add(entryInfo);
					builder.addParam(entryInfo.getId().toString());
					builder.addParam(new Integer(entryInfo.getSeq()));
					builder.addParam(entryInfo.getCurrency().getId().toString());
					builder.addParam(entryInfo.getSupplier().getId().toString());
					//未购买成本系统的借方会计科目为空 by hpw
					builder.addParam(entryInfo.getAccountView()==null?null:entryInfo.getAccountView().getId().toString());
					builder.addParam(entryInfo.getDesc());
					builder.addParam(entryInfo.getExchangeRate());
					builder.addParam(entryInfo.getLocateAmount());
					builder.addParam(entryInfo.getAmount());
					builder.addParam(entryInfo.getType());
					builder.addParam(entryInfo.getParent().getId().toString());
					if(entryInfo.getProductType()==null)
						builder.addParam(null);
					else
						builder.addParam(entryInfo.getProductType().getId().toString());
					builder.addParam(entryInfo.getCurProject().getId().toString());
					builder.addParam(entryInfo.getCostAmt());
					builder.addParam(entryInfo.getPayAmt());
					builder.addParam(entryInfo.getCostAccount()==null?null:entryInfo.getCostAccount().getId().toString());
					builder.addBatch();
				}
			}
			builder.executeBatch();
		}
		

		
		afterSave(param);
		
		builder = null;
	}

	public void beforeSave(Map paymentBills) throws BOSException {
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
//		Map paymentBills = (HashMap)param.get("paymentBills");
		PaymentBillInfo paymentBillInfo;
		/**
		 * 简单模式一体化先删除 
		 */
		builder.clear();
		
		if(financial.equals(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)){
			builder.setPrepareStatementSql(COSTENTRY_DELETE_SQL);
			builder.appendParam("FParentId",paymentBills.keySet().toArray());
			builder.execute();
			builder.clear();
			builder.setPrepareStatementSql(PAYENTRY_DELETE_SQL);
			builder.appendParam("FParentId",paymentBills.keySet().toArray());
			builder.execute();
			builder.clear();
			
			/***
			 * 如果是简单模式，先clear
			 */
			for(Iterator it=paymentBills.keySet().iterator();it.hasNext();){
				String key = (String)it.next();
				paymentBillInfo = (PaymentBillInfo)paymentBills.get(key);
				paymentBillInfo.getFdcCostVoucherEntry().clear();
				paymentBillInfo.getFdcPayVoucherEntry().clear();
			}
		}
	}
	
	public void afterSave(Map param) throws BOSException {
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		
		/***
		 * 更新付款拆分上的字段,凭证引用源单据字段
		 */
		if(needUpdatePaymentSplitSet.size()>0){
			builder.setPrepareStatementSql(PAYMENTSPLIT_UPDATE_SQL);
			builder.addParam(PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE);
			builder.appendSql(" where ");
			builder.appendParam("fid",needUpdatePaymentSplitSet.toArray());
			builder.appendSql(" and ( FVoucherRefer is null or ");			
			builder.appendParam("FVoucherRefer",PaySplitVoucherRefersEnum.NOTREFER_VALUE);
			builder.appendSql(" ) ");
			builder.executeUpdate();
		}
		builder.clear();
		if(needUpdatePaymentNoCostSplitSet.size()>0){
			builder.setPrepareStatementSql(PAYMENTNOCOSTSPLIT_UPDATE_SQL);
			builder.addParam(PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE);
			builder.appendSql(" where ");
			builder.appendParam("fid",needUpdatePaymentNoCostSplitSet.toArray());
			builder.appendSql(" and ( FVoucherRefer is null or ");		
			builder.appendParam("FVoucherRefer",PaySplitVoucherRefersEnum.NOTREFER_VALUE);
			builder.appendSql(" ) ");
			builder.executeUpdate();
		}
		builder.clear();
	}
	
}
