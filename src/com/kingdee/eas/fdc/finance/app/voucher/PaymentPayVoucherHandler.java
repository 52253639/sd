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
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.FDCPayVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.FDCPayVoucherEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class PaymentPayVoucherHandler extends AbstractFDCVoucherHandler implements IFDCPayVoucherHandler{
	public PaymentPayVoucherHandler(Context ctx,IFDCVoucherEntryCreator creator) {
		setCtx(ctx);
		setFDCVoucherEntryCreator(creator);
	}

	public IObjectCollection createPayEntrys(Map dataMap) throws BOSException, EASBizException{
		init(dataMap);
		IObjectCollection coll = null;
		/***
		 * 扣款，违约单独拉出来同步
		 */
		coll = createDeductCompensationPayEntrys(dataMap);
		
		//TODO 简单模式处理发票：单边凭证处理(1)
		if(isSimpleInvoice){
			if(isPaymentSplit){
				IObjectCollection paySplitInfos=(IObjectCollection)dataMap.get("paySplitInfos");
				for(Iterator iter=paySplitInfos.iterator();iter.hasNext();){
					IObjectCollection onecoll=createSimpleInvoiceOnePaymentPayEntrys(dataMap,(IObjectValue)iter.next());
					if(coll==null)
						coll = onecoll;
					else
						coll.addObjectCollection(onecoll);
				}
			}else if(!isPaymentSplit&&isInvoiceOffset){
				coll = createSimpleSideInvoicePayEntrys(dataMap);
				
			}
			return coll;
		}
		
		IObjectCollection paySplitInfos=(IObjectCollection)dataMap.get("paySplitInfos");
		for(Iterator iter=paySplitInfos.iterator();iter.hasNext();){
			IObjectCollection onecoll=createOnePaymentPayEntrys((IObjectValue)iter.next());
			if(coll==null)
				coll = onecoll;
			else
				coll.addObjectCollection(onecoll);
		}
		return coll;
	}
	
	/****
	 * 扣款，违约单独拉出来同步
	 * 节省资源
	 * @param dataMap
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected IObjectCollection createDeductCompensationPayEntrys(Map dataMap) throws EASBizException, BOSException{
		IObjectCollection coll = new FDCPayVoucherEntryCollection();
		/**
		 * 如果是简单模式一体化，且不处理扣款
		 */
		if(financial.equals(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)&&!financialExtend){
			return coll;
		}
		/*****
		 * 处理扣款
		 * 同步扣款分录到贷方分录
		 */
		
		DeductOfPayReqBillEntryCollection deductBills = 
			(DeductOfPayReqBillEntryCollection)dataMap.get(DEDUCTBILLS);
		for(Iterator it=deductBills.iterator();it.hasNext();){
			DeductOfPayReqBillEntryInfo deductInfo = (DeductOfPayReqBillEntryInfo)it.next();
			if(deductInfo.getDeductBillEntry()!=null
					&&deductInfo.getParent()!=null
					&&deductInfo.getParent().getPaymentBill()!=null){
				FDCPayVoucherEntryInfo payEntryInfo = new FDCPayVoucherEntryInfo();
				payEntryInfo.setId(BOSUuid.create(payEntryInfo.getBOSType()));
				payEntryInfo.setAmount(deductInfo.getDeductBillEntry().getDeductAmt());
				payEntryInfo.setLocateAmount(deductInfo.getDeductBillEntry().getDeductAmt());
				payEntryInfo.setExchangeRate(deductInfo.getParent().getPaymentBill().getExchangeRate());
				payEntryInfo.setDesc(deductInfo.getDeductBillEntry().getDeductItem());
				payEntryInfo.setSupplier(deductInfo.getParent().getPaymentBill().getActFdcPayeeName());
				payEntryInfo.setCurrency(getBaseCurrency());
				//TODO
				payEntryInfo.setType(deductInfo.getDeductBillEntry().getDeductType().getId().toString());
				payEntryInfo.setParent(deductInfo.getParent().getPaymentBill());
				String key = String.valueOf(deductInfo.getDeductBillEntry().getDeductType().getId());
				
				//工程项目
				payEntryInfo.setCurProject(deductInfo.getParent().getPaymentBill().getCurProject());
				if(deductAccountMap.containsKey(key))
					payEntryInfo.setAccountView((AccountViewInfo)deductAccountMap.get(key));
				else
					//TODO throw exception
					payEntryInfo.setAccountView(null);
				coll.addObject(payEntryInfo);
			}
		}
		/***
		 * 处理甲供的扣款记录
		 */
		PartAOfPayReqBillCollection partABills = 
			(PartAOfPayReqBillCollection)dataMap.get(PARTAOFBILLS);
		for(Iterator it=partABills.iterator();it.hasNext();){
			PartAOfPayReqBillInfo partAInfo = (PartAOfPayReqBillInfo)it.next();
			if(partAInfo.getDeductBill()!=null
					&&partAInfo.getDeductBill().getEntrys()!=null
					&&partAInfo.getPaymentBill()!=null){
				for(int i=0;i<partAInfo.getDeductBill().getEntrys().size();i++){
					DeductBillEntryInfo deductBillEntryInfo = partAInfo.getDeductBill().getEntrys().get(i);
					/***
					 * 这里关联的甲供合同扣款分录有多余数据
					 * 可能包含非本付款关联的数据
					 * 故这里需要判断合同ID相等
					 */
					if(deductBillEntryInfo.getContractId().equals(partAInfo.getPaymentBill().getContractBillId())){
						FDCPayVoucherEntryInfo payEntryInfo = new FDCPayVoucherEntryInfo();
						payEntryInfo.setId(BOSUuid.create(payEntryInfo.getBOSType()));
						//从中间表取数据，支持多次扣款 by hpw
						payEntryInfo.setAmount(partAInfo.getOriginalAmount());
						payEntryInfo.setLocateAmount(partAInfo.getAmount());
						payEntryInfo.setExchangeRate(partAInfo.getPaymentBill().getExchangeRate());
						payEntryInfo.setDesc(deductBillEntryInfo.getDeductItem());
						/****
						 * 这里应该取扣款单的扣款单位
						 * old::::
						 * payEntryInfo.setSupplier(partAInfo.getPaymentBill().getActFdcPayeeName());
						 * 2010-8-12 by yong_zhou
						 * 相关提单：R100719-304
						 * BOTP中只能取到对应付款的总包合同的乙方，而取不到扣甲供付款的材料合同的乙方
						 */
						payEntryInfo.setSupplier(deductBillEntryInfo.getDeductUnit());
						payEntryInfo.setCurrency(getBaseCurrency());
						//TODO
						payEntryInfo.setType(deductBillEntryInfo.getDeductType().getId().toString());
						payEntryInfo.setParent(partAInfo.getPaymentBill());
						String key = String.valueOf(deductBillEntryInfo.getDeductType().getId());
						
						//工程项目
						payEntryInfo.setCurProject(partAInfo.getPaymentBill().getCurProject());
						if(deductAccountMap.containsKey(key))
							payEntryInfo.setAccountView((AccountViewInfo)deductAccountMap.get(key));
						else
							//TODO throw exception
							payEntryInfo.setAccountView(null);
						coll.addObject(payEntryInfo);
					}
				}
				
			}
		}
		/***
		 * 处理甲供确认单的扣款
		 */
		PartAConfmOfPayReqBillCollection partAConfirmBills = 
			(PartAConfmOfPayReqBillCollection)dataMap.get(PARTACONFIRMBILLS);
		for(Iterator it=partAConfirmBills.iterator();it.hasNext();){
			PartAConfmOfPayReqBillInfo partAInfo = (PartAConfmOfPayReqBillInfo)it.next();
			if(partAInfo!=null
					&&partAInfo.getPaymentBill()!=null){
				FDCPayVoucherEntryInfo payEntryInfo = new FDCPayVoucherEntryInfo();
				payEntryInfo.setId(BOSUuid.create(payEntryInfo.getBOSType()));
				payEntryInfo.setAmount(partAInfo.getAmount());
				payEntryInfo.setLocateAmount(partAInfo.getAmount());
				payEntryInfo.setExchangeRate(partAInfo.getPaymentBill().getExchangeRate());
				//payEntryInfo.setDesc();
				payEntryInfo.setSupplier(partAInfo.getPaymentBill().getActFdcPayeeName());
				payEntryInfo.setCurrency(getBaseCurrency());
				//TODO
				payEntryInfo.setType(DeductTypeInfo.partAMaterialType);
				payEntryInfo.setParent(partAInfo.getPaymentBill());
				String key = String.valueOf(DeductTypeInfo.partAMaterialType);
				
				//工程项目
				payEntryInfo.setCurProject(partAInfo.getPaymentBill().getCurProject());
				if(deductAccountMap.containsKey(key))
					payEntryInfo.setAccountView((AccountViewInfo)deductAccountMap.get(key));
				else
					//TODO throw exception
					payEntryInfo.setAccountView(null);
				coll.addObject(payEntryInfo);
			}
		}
		/***
		 * 处理违约
		 * 同步违约单到贷方分录
		 */
		
		CompensationOfPayReqBillCollection compensationBills = 
			(CompensationOfPayReqBillCollection)dataMap.get(COMPENSATIONBILLS);
		for(Iterator it=compensationBills.iterator();it.hasNext();){
			CompensationOfPayReqBillInfo info = (CompensationOfPayReqBillInfo)it.next();
			if(info.getPaymentBill()!=null){
				FDCPayVoucherEntryInfo payEntryInfo = new FDCPayVoucherEntryInfo();
				payEntryInfo.setId(BOSUuid.create(payEntryInfo.getBOSType()));
				payEntryInfo.setAmount(info.getCompensation().getOriginalAmount());
				payEntryInfo.setLocateAmount(info.getCompensation().getAmount());
				payEntryInfo.setExchangeRate(info.getPaymentBill().getExchangeRate());
				payEntryInfo.setDesc(info.getCompensation().getMoneyDes());
				payEntryInfo.setSupplier(info.getPaymentBill().getActFdcPayeeName());
				payEntryInfo.setCurrency(getBaseCurrency());
				payEntryInfo.setParent(info.getPaymentBill());
				//
				payEntryInfo.setType(FDCConstants.FDC_INIT_COMPENSATION);
				//工程项目
				payEntryInfo.setCurProject(info.getPaymentBill().getCurProject());
				if(beforeAccountViewInfo!=null){
					payEntryInfo.setAccountView(beforeAccountViewInfo.getCompensationAccount());
				}
				coll.addObject(payEntryInfo);
			}
		}
		
		return coll;
	}
	protected IObjectCollection createSimpleInvoiceOnePaymentPayEntrys(Map dataMap,IObjectValue splitInfo) throws BOSException, EASBizException{
		IObjectCollection coll = null;
		/***
		 * 将扣款，违约拉出去执行了
		 */
		IObjectCollection onecoll = createSimpleInvoiceOnePaymentOtherPayEntrys(dataMap,splitInfo);
		if(coll==null)
			coll = onecoll;
		else
			coll.addObjectCollection(onecoll);
		return coll;
		
	}
	
	protected IObjectCollection createOnePaymentPayEntrys(IObjectValue splitInfo) throws BOSException, EASBizException{
		IObjectCollection coll = null;
		/***
		 * 将扣款，违约拉出去执行了
		 */
		IObjectCollection onecoll = createOnePaymentOtherPayEntrys(splitInfo);
		if(coll==null)
			coll = onecoll;
		else
			coll.addObjectCollection(onecoll);
		return coll;
		
	}
	/**
	 * 子类通过拆分生成其它分录，如应付账款，保修款
	 * @param splitInfo
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected IObjectCollection createSimpleInvoiceOnePaymentOtherPayEntrys(Map dataMap,IObjectValue splitInfo) throws EASBizException, BOSException{
		IObjectCollection coll = new FDCPayVoucherEntryCollection();
		PaymentBillInfo paymentBillInfo = null;
		if(splitInfo instanceof PaymentSplitInfo){
			paymentBillInfo = ((PaymentSplitInfo)splitInfo).getPaymentBill();
			for(Iterator it=((PaymentSplitInfo)splitInfo).getEntrys().iterator();it.hasNext();){
				PaymentSplitEntryInfo entryInfo = (PaymentSplitEntryInfo)it.next();
				FDCPayVoucherEntryInfo voucherEntryInfo = null;
				/***
				 * 银行科目（付款单付款科目）
				 */
				if(entryInfo.isIsLeaf()){
					
					boolean isConWithOutText = ((PaymentSplitInfo)splitInfo).isIsConWithoutText();
					
					/***
					 * 暂估款
					 * 应付账款_暂估（财务成本一体化科目设置上“应付帐款_开发成本_暂估款”对应科目）  发票拆分金额
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.tempID))
					{
						voucherEntryInfo = createPayEntryTempAccount(paymentBillInfo, entryInfo,((PaymentSplitInfo)splitInfo).isIsProgress());
						if(voucherEntryInfo!=null)
							coll.addObject(voucherEntryInfo);
						continue;
					}
					
					if (!isConWithOutText) {
						if (isInvoiceOffset) {
							// 简单模式处理发票：标准方式冲抵
							addSimpleInvoiceOffsetVoucherPayEntry(dataMap,coll,
									paymentBillInfo, entryInfo);
						} else {
							// 简单模式处理发票:标准
							addSimpleInvoicePayEntry(dataMap,coll,
									paymentBillInfo, entryInfo);
						}
						continue;// 简单模式处理发票不考虑款类型,不处理无文本
					}
					/***
					 * 
					 * 进度款，结算款
					 * 应付账款_开发成本_进度款=归属金额-归属付款金额 （付款拆分）
					 * 周勇
					 */
					
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID))
					{
						voucherEntryInfo = createPayEntryProAccount(paymentBillInfo, entryInfo,((PaymentSplitInfo)splitInfo).isIsProgress());
						coll.addObject(voucherEntryInfo);
					}
					
					/****
					 * 
					 * 如果是结算款且是第一笔
					 * 应付账款_开发成本_进度款=归属金额-归属付款金额 （付款拆分）
					 * 应付账款_开发成本_保修款：付款拆分上的保修款拆分金额
					 * 周勇
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)
							&&((PaymentSplitInfo)splitInfo).isIsProgress()){
						voucherEntryInfo = createPayEntryProAccount(paymentBillInfo, entryInfo,((PaymentSplitInfo)splitInfo).isIsProgress());
						coll.addObject(voucherEntryInfo);
						voucherEntryInfo = createPayEntrySettAccount(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
					}
				}
			}
			
		}
		else if(splitInfo instanceof PaymentNoCostSplitInfo){ 
			paymentBillInfo = ((PaymentNoCostSplitInfo)splitInfo).getPaymentBill();
			for(Iterator it=((PaymentNoCostSplitInfo)splitInfo).getEntrys().iterator();it.hasNext();){
				PaymentNoCostSplitEntryInfo entryInfo = (PaymentNoCostSplitEntryInfo)it.next();
				FDCPayVoucherEntryInfo voucherEntryInfo = null;
				if(entryInfo.isIsLeaf()){
					
					boolean isConWithOutText = ((PaymentNoCostSplitInfo)splitInfo).isIsConWithoutText();
					
					/***
					 * 暂估款
					 * 应付账款_暂估（财务成本一体化科目设置上“应付帐款_开发成本_暂估款”对应科目）  发票拆分金额
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.tempID))
					{
						voucherEntryInfo = createPayEntryTempAccount(paymentBillInfo, entryInfo,((PaymentNoCostSplitInfo)splitInfo).isIsProgress());
						if(voucherEntryInfo!=null)
							coll.addObject(voucherEntryInfo);
						continue;
					}
					if (!isConWithOutText) {
						if (isInvoiceOffset) {
							addSimpleInvoiceOffsetVoucherPayEntry(dataMap,coll,
									paymentBillInfo, entryInfo);
						} else {
							// 简单模式处理发票:标准
							addSimpleInvoicePayEntry(dataMap,coll,
									paymentBillInfo, entryInfo);
						}
						continue;// 简单模式处理发票不考虑款类型,不处理无文本
					}
					/***
					 * 
					 * 应付账款_其它_进度款=归属成本金额-归属付款金额 （付款拆分）
					 * 周勇
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID))
					{
						voucherEntryInfo = createPayEntryAccountView(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
					}
					/****
					 * 
					 * 应付账款_其它_进度款=归属成本金额-归属付款金额 （付款拆分）
					 * 应付账款_其它_保修款：付款拆分上的保修款拆分金额
					 * 周勇
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)
							&&((PaymentNoCostSplitInfo)splitInfo).isIsProgress()){
						voucherEntryInfo = createPayEntryAccountView(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
						voucherEntryInfo = createPayEntrySettAccount(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
					}
				}
			}
		}
		return coll;
	}
	
	/**
	 * 子类通过拆分生成其它分录，如应付账款，保修款
	 * @param splitInfo
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected IObjectCollection createOnePaymentOtherPayEntrys(IObjectValue splitInfo) throws EASBizException, BOSException{
		IObjectCollection coll = new FDCPayVoucherEntryCollection();
		PaymentBillInfo paymentBillInfo = null;
		if(splitInfo instanceof PaymentSplitInfo){
			paymentBillInfo = ((PaymentSplitInfo)splitInfo).getPaymentBill();
			for(Iterator it=((PaymentSplitInfo)splitInfo).getEntrys().iterator();it.hasNext();){
				PaymentSplitEntryInfo entryInfo = (PaymentSplitEntryInfo)it.next();
				FDCPayVoucherEntryInfo voucherEntryInfo = null;
				/***
				 * 银行科目（付款单付款科目）
				 */
				//coll.addObject(createPayEntryPayAccount(paymentBillInfo));
				if(entryInfo.isIsLeaf()){
					
					/***
					 * 
					 * 进度款，结算款
					 * 应付账款_开发成本_进度款=归属金额-归属付款金额 （付款拆分）
					 * 周勇
					 */
					
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID))
					{
						voucherEntryInfo = createPayEntryProAccount(paymentBillInfo, entryInfo,((PaymentSplitInfo)splitInfo).isIsProgress());
						coll.addObject(voucherEntryInfo);
					}
					
					/****
					 * 
					 * 如果是结算款且是第一笔
					 * 应付账款_开发成本_进度款=归属金额-归属付款金额 （付款拆分）
					 * 应付账款_开发成本_保修款：付款拆分上的保修款拆分金额
					 * 周勇
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)
							&&((PaymentSplitInfo)splitInfo).isIsProgress()){
						voucherEntryInfo = createPayEntryProAccount(paymentBillInfo, entryInfo,((PaymentSplitInfo)splitInfo).isIsProgress());
						coll.addObject(voucherEntryInfo);
						voucherEntryInfo = createPayEntrySettAccount(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
					}
					
					//生成发票的贷方
					if(isInvoiceMrg){
						FDCPayVoucherEntryInfo entry = createInvoicePayEntry(paymentBillInfo, entryInfo);
						if(entry!=null){
							coll.addObject(entry);
						}
					}
					
				}
			}
			if(paymentBillInfo!=null&&paymentBillInfo.getContractBillId()!=null){
				if(isAdvance(paymentBillInfo.getContractBillId())){
					if(paymentBillInfo.getPrjPayEntry()!=null){
						if(FDCHelper.toBigDecimal(paymentBillInfo.getPrjPayEntry().getAdvance()).signum()<0){
							coll.addObject(createAdvancePayEntry(paymentBillInfo));	
						}
					}
				}
			}
		}
		else if(splitInfo instanceof PaymentNoCostSplitInfo){ 
			paymentBillInfo = ((PaymentNoCostSplitInfo)splitInfo).getPaymentBill();
			for(Iterator it=((PaymentNoCostSplitInfo)splitInfo).getEntrys().iterator();it.hasNext();){
				PaymentNoCostSplitEntryInfo entryInfo = (PaymentNoCostSplitEntryInfo)it.next();
				FDCPayVoucherEntryInfo voucherEntryInfo = null;
				if(entryInfo.isIsLeaf()){
										
					/***
					 * 
					 * 应付账款_其它_进度款=归属成本金额-归属付款金额 （付款拆分）
					 * 周勇
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID))
					{
						voucherEntryInfo = createPayEntryAccountView(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
					}
					
					
					/****
					 * 
					 * 应付账款_其它_进度款=归属成本金额-归属付款金额 （付款拆分）
					 * 应付账款_其它_保修款：付款拆分上的保修款拆分金额
					 * 周勇
					 */
					if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)
							&&((PaymentNoCostSplitInfo)splitInfo).isIsProgress()){
						voucherEntryInfo = createPayEntryAccountView(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
						voucherEntryInfo = createPayEntrySettAccount(paymentBillInfo, entryInfo);
						coll.addObject(voucherEntryInfo);
					}
					
				}
			}
			if(paymentBillInfo!=null&&paymentBillInfo.getContractBillId()!=null){
				if(isAdvance(paymentBillInfo.getContractBillId())){
					if(paymentBillInfo.getPrjPayEntry()!=null){
						if(FDCHelper.toBigDecimal(paymentBillInfo.getPrjPayEntry().getAdvance()).signum()<0){
							coll.addObject(createAdvancePayEntry(paymentBillInfo));	
							
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
	private IObjectCollection createSimpleSideInvoicePayEntrys(Map dataMap) throws EASBizException, BOSException {
		IObjectCollection coll = new FDCPayVoucherEntryCollection();
		IObjectCollection payInfos = (IObjectCollection)dataMap.get("payInfos");
		for(Iterator iter=payInfos.iterator();iter.hasNext();){
			PaymentBillInfo info = (PaymentBillInfo)iter.next();
			addSimpleInvoiceSideVoucherPayEntry(coll,info);
		}
		return coll;
	}

	
	private void addSimpleInvoiceSideVoucherPayEntry(IObjectCollection coll,
			PaymentBillInfo info) throws EASBizException, BOSException{
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(info.get("totalInvoice"), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(info.get("totalPay"), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(info.get("invoiceAmt"), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(info.getLocalAmt(), 2);
		FDCPayVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 银行存款（付款单上付款科目） 付款拆分金额  付款科目 (注释原因：付款单付款科目凭证会自动生成一条分录，不需要处理)
		/*voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
				entryInfo, paymentBillInfo.getPayerAccount(), entryInfo
						.getPayedAmt());
		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}*/

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,beforeOtherAccount,splitedPayAmt.subtract(splitedInvoiceAmt));
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
				if (invoiceAmt.compareTo(payAmt) > 0) {
					voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt.subtract(payAmt));
					if(voucherEntryInfo!=null){
						coll.addObject(voucherEntryInfo);
					}
				}
				

			} else {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,beforeOtherAccount,splitedPayAmt.subtract(splitedInvoiceAmt));
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,invoiceAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleSideInvoicePayEntry(info,proAccount,payAmt);
				if(voucherEntryInfo!=null){
					coll.addObject(voucherEntryInfo);
				}
			}
		}
		
	}
	
	private FDCPayVoucherEntryInfo createSimpleSideInvoicePayEntry(
			PaymentBillInfo paymentBillInfo,  AccountViewInfo accountView,
			BigDecimal amount) throws EASBizException, BOSException{
		if(FDCHelper.toBigDecimal(amount).signum()==0){
			return null;
		}
		FDCPayVoucherEntryInfo voucherEntryInfo = new FDCPayVoucherEntryInfo();
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
	
	private IObjectCollection addSimpleInvoiceOffsetVoucherPayEntry(Map dataMap,IObjectCollection coll,
			PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo)
			throws EASBizException, BOSException {
		String key = paymentBillInfo.getContractBillId()
				+ entryInfo.getCostAccount().getCurProject().getId().toString()
				+ entryInfo.getCostAccount().getId().toString();
		Map payedAmtMap = (Map)dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map)dataMap.get("invoiceAmtMap");
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCPayVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 银行存款（付款单上付款科目） 付款拆分金额  付款科目 (注释原因：付款单付款科目凭证会自动生成一条分录，不需要处理)
		/*voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
				entryInfo, paymentBillInfo.getPayerAccount(), entryInfo
						.getPayedAmt());
		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}*/

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, proAccount, invoiceAmt);
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleInvoicePayEntry(
						paymentBillInfo, entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(
						paymentBillInfo, entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleInvoicePayEntry(
						paymentBillInfo, entryInfo, proAccount,
						invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				
				if (invoiceAmt.compareTo(payAmt) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		}
		return coll;
	}
	
	private IObjectCollection addSimpleInvoiceOffsetVoucherPayEntry(Map dataMap,IObjectCollection coll,
			PaymentBillInfo paymentBillInfo, PaymentNoCostSplitEntryInfo entryInfo)
			throws EASBizException, BOSException {
		String key = paymentBillInfo.getContractBillId()
				+ entryInfo.getCurProject().getId().toString()
				+ entryInfo.getAccountView().getId().toString();
		Map payedAmtMap = (Map)dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map)dataMap.get("invoiceAmtMap");
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCPayVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 银行存款（付款单上付款科目） 付款拆分金额  付款科目 (注释原因：付款单付款科目凭证会自动生成一条分录，不需要处理)
		/*voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
				entryInfo, paymentBillInfo.getPayerAccount(), entryInfo
						.getPayedAmt());
		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}*/

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, proAccount, invoiceAmt);
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleInvoicePayEntry(
						paymentBillInfo, entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(
						paymentBillInfo, entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {
				voucherEntryInfo = createSimpleInvoicePayEntry(
						paymentBillInfo, entryInfo, proAccount,
						invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				
				if (invoiceAmt.compareTo(payAmt) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else if (invoiceAmt.compareTo(payAmt) < 0) {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, payAmt);
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}
			}
		}
		return coll;
	}
	
	/**
	 * 付款单生成凭证
	 * </p>
	 * 当参数“财务帐务以发票金额为准计开发成本”启用时，则付款单生成凭证如下，当为其他付款类型时，则根据以下业务场景进行判断（注明：以下的累计都是不包括本次的）： 
	 * </p>
	 * 1、当累计发票金额>累计付款金额，(累计发票金额+本次发票金额)>(累计付款金额+本次付款金额)，账务如何处理？
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额 
	 * 借/贷（若本次付款金额>本次发票金额为借，否则，为贷）：应付账款（财务成本一体化科目设置上“应付帐款_开发成本_进度款”对应科目） |发票拆分金额-付款拆分金额|
	 * </p>
	 * 2、当累计发票金额>累计付款金额，(累计发票金额+本次发票金额)<(累计付款金额+本次付款金额)，账务如何处理？
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额
	 * 借：应付账款（财务成本一体化科目设置上“应付帐款_开发成本_进度款”对应科目） 累计发票拆分金额-累计付款拆分金额
	 * 借：预付账款（财务成本一体化科目设置上“预付帐款_开发成本_其他成本”对应科目） (累计拆分付款金额+付款拆分金额) -(累计发票拆分金额+发票拆分金额)
	 * </p>
	 * 3、当累计发票金额>累计付款金额，(累计发票金额+本次发票金额)=(累计付款金额+本次付款金额)，账务如何处理？
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额
	 * 借：应付账款（财务成本一体化科目设置上“应付帐款_开发成本_进度款”对应科目） 付款拆分金额-发票拆分金额
	 * </p>
	 * 4、当累计发票金额<累计付款金额，(累计发票金额+本次发票金额)>(累计付款金额+本次付款金额)，账务如何处理？
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额
	 * 贷：应付账款（财务成本一体化科目设置上“应付帐款_开发成本_进度款”对应科目） (累计发票拆分金额+发票拆分金额) -(累计付款拆分金额+付款拆分金额) 
	 * 贷：预付账款（财务成本一体化科目设置上“预付帐款_开发成本_其他成本”对应科目） 累计付款拆分金额 -累计发票拆分金额 
	 *</p>
	 * 5、当累计发票金额<累计付款金额，(累计发票金额+本次发票金额)<(累计付款金额+本次付款金额)，账务如何处理？
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额 
	 * 借/贷（若 本次付款金额>本次发票金额为借，否则，为贷）：预付账款（财务成本一体化科目设置上“预付帐款_开发成本_其他成本”对应科目） |付款拆分金额-发票拆分金额|
	 * </p>
	 * 6、当累计发票金额<累计付款金额，(累计发票金额+本次发票金额)=(累计付款金额+本次付款金额)，账务如何处理？
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额
	 * 贷：预付账款（财务成本一体化科目设置上“预付帐款_开发成本_其他成本”对应科目） 累计付款拆分金额-累计发票拆分金额=发票拆分金额-付款拆分金额
	 * </p>
	 * 7、当累计发票金额=累计付款金额，本次发票金额>本次付款金额，账务如何处理？
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额
	 * 贷：应付账款（财务成本一体化科目设置上“应付帐款_开发成本_进度款”对应科目） 发票拆分金额-付款拆分金额
	 * </p>
	 * 8、当累计发票金额=累计付款金额，本次发票金额<本次付款金额，账务如何处理？ 
	 * 借：开发成本（成本科目对应会计科目） 发票拆分金额
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额 
	 * 借：预付账款（财务成本一体化科目设置上“预付帐款_开发成本_其他成本”对应科目）付款拆分金额-发票拆分金额 
	 * </p>
	 * 9、当累计发票金额=累计付款金额，本次发票金额=本次付款金额，账务如何处理？ 
	 * 借：开发成本（成本科目对应会计科目）发票拆分金额 
	 * 贷：银行存款（付款单上付款科目） 付款拆分金额
	 * 
	 * 
	 * 
	 */
	private IObjectCollection addSimpleInvoicePayEntry(Map dataMap,IObjectCollection coll,
			PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo)
			throws EASBizException, BOSException {
		this.initSplitBillInfos(dataMap, paymentBillInfo.getId().toString());
		Map payedAmtMap = (Map) dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map) dataMap.get("invoiceAmtMap");
		String key = paymentBillInfo.getContractBillId() + entryInfo.getCostAccount().getCurProject().getId().toString()
				+ entryInfo.getCostAccount().getId().toString();
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCPayVoucherEntryInfo voucherEntryInfo = null;
		
		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();
		
		// 银行存款（付款单上付款科目） 付款拆分金额  付款科目 (注释原因：付款单付款科目凭证会自动生成一条分录，不需要处理)
		/*voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
				entryInfo, paymentBillInfo.getPayerAccount(), entryInfo
						.getPayedAmt());
		if (voucherEntryInfo != null) {
			coll.addObject(voucherEntryInfo);
		}*/

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				if (invoiceAmt.compareTo(payAmt) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, proAccount, FDCHelper
									.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

			} else {

			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, splitedInvoiceAmt
								.add(invoiceAmt).subtract(splitedPayAmt)
								.subtract(payAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

				if (invoiceAmt.compareTo(payAmt) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt.subtract(payAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else if (invoiceAmt.compareTo(payAmt) < 0) {

			} else {

			}
		}
		return coll;
	}
	
	private IObjectCollection addSimpleInvoicePayEntry(Map dataMap, IObjectCollection coll, PaymentBillInfo paymentBillInfo,
			PaymentNoCostSplitEntryInfo entryInfo) throws EASBizException, BOSException {
		this.initSplitBillInfos(dataMap, paymentBillInfo.getId().toString());
		Map payedAmtMap = (Map) dataMap.get("payedAmtMap");
		Map invoiceAmtMap = (Map) dataMap.get("invoiceAmtMap");
		String key = paymentBillInfo.getContractBillId() + entryInfo.getCurProject().getId().toString()
				+ entryInfo.getAccountView().getId().toString();
		BigDecimal splitedInvoiceAmt = FDCHelper.toBigDecimal(invoiceAmtMap.get(key), 2);
		BigDecimal splitedPayAmt = FDCHelper.toBigDecimal(payedAmtMap.get(key), 2);
		BigDecimal invoiceAmt = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt(), 2);
		BigDecimal payAmt = FDCHelper.toBigDecimal(entryInfo.getPayedAmt(), 2);
		FDCPayVoucherEntryInfo voucherEntryInfo = null;

		// 应付帐款_开发成本_进度款
		AccountViewInfo proAccount = beforeAccountViewInfo.getProAccount();
		// 预付帐款_开发成本_其他成本
		AccountViewInfo beforeOtherAccount = beforeAccountViewInfo
				.getBeforeOtherAccount();

		// 银行存款（付款单上付款科目） 付款拆分金额 付款科目 (注释原因：付款单付款科目凭证会自动生成一条分录，不需要处理)
		/*
		 * voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
		 * entryInfo, paymentBillInfo.getPayerAccount(), entryInfo
		 * .getPayedAmt()); if (voucherEntryInfo != null) {
		 * coll.addObject(voucherEntryInfo); }
		 */

		if (splitedInvoiceAmt.compareTo(splitedPayAmt) > 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {
				if (invoiceAmt.compareTo(payAmt) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, proAccount, FDCHelper
									.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

			} else {

			}
		} else if (splitedInvoiceAmt.compareTo(splitedPayAmt) < 0) {
			if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, splitedInvoiceAmt
								.add(invoiceAmt).subtract(splitedPayAmt)
								.subtract(payAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else if (splitedInvoiceAmt.add(invoiceAmt).compareTo(
					splitedPayAmt.add(payAmt)) < 0) {

				if (invoiceAmt.compareTo(payAmt) > 0) {
					voucherEntryInfo = createSimpleInvoicePayEntry(
							paymentBillInfo, entryInfo, beforeOtherAccount,
							FDCHelper.subtract(invoiceAmt, payAmt).abs());
					if (voucherEntryInfo != null) {
						coll.addObject(voucherEntryInfo);
					}
				}

			} else {
				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, beforeOtherAccount, splitedPayAmt
								.subtract(splitedInvoiceAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			}
		} else {
			if (invoiceAmt.compareTo(payAmt) > 0) {

				voucherEntryInfo = createSimpleInvoicePayEntry(paymentBillInfo,
						entryInfo, proAccount, invoiceAmt.subtract(payAmt));
				if (voucherEntryInfo != null) {
					coll.addObject(voucherEntryInfo);
				}

			} else if (invoiceAmt.compareTo(payAmt) < 0) {

			} else {

			}
		}
		return coll;
	}
	
	private FDCPayVoucherEntryInfo createSimpleInvoicePayEntry(
			PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo,AccountViewInfo accountView,
			BigDecimal amount) throws EASBizException, BOSException{
		if(FDCHelper.toBigDecimal(amount).signum()==0){
			return null;
		}
		FDCPayVoucherEntryInfo voucherEntryInfo = new FDCPayVoucherEntryInfo();
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
		return voucherEntryInfo;
	}
	
	private FDCPayVoucherEntryInfo createSimpleInvoicePayEntry(
			PaymentBillInfo paymentBillInfo, PaymentNoCostSplitEntryInfo entryInfo,AccountViewInfo accountView,
			BigDecimal amount) throws EASBizException, BOSException{
		if(FDCHelper.toBigDecimal(amount).signum()==0){
			return null;
		}
		FDCPayVoucherEntryInfo voucherEntryInfo = new FDCPayVoucherEntryInfo();
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
	

	/**
	 * 预付款为负时在贷方 
	 * @param paymentBillInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected IObjectValue createAdvancePayEntry(PaymentBillInfo paymentBillInfo) throws EASBizException, BOSException{
		FDCPayVoucherEntryInfo voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		if(paymentBillInfo.getPrjPayEntry()!=null){
			voucherEntryInfo.setAmount(FDCHelper.toBigDecimal(paymentBillInfo.getPrjPayEntry().getAdvance()).abs());
			voucherEntryInfo.setLocateAmount(FDCHelper.toBigDecimal(paymentBillInfo.getPrjPayEntry().getLocAdvance()).abs());
		}else {
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
	
	/**
	 * 与发票一致的贷方科目，科目为成本科目对应的会计科目
	 *TODO 是否要按项目状态来生成预付的科目待确定，如果生成预付的科目的话，项目状态变化生成的凭证有可能不平
	 *  by sxhong 2009-07-21 20:23:25 
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createInvoicePayEntry(PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo) throws BOSException, EASBizException {
		if(FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt()).signum()==0){
			return null;
		}
		FDCPayVoucherEntryInfo voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setAmount(entryInfo.getInvoiceAmt());
		voucherEntryInfo.setLocateAmount(entryInfo.getInvoiceAmt());
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_INVOICE);
		voucherEntryInfo.setAccountView(getAccountWithCostAccount(entryInfo.getCostAccount()));
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		return voucherEntryInfo;
	}
	
	/***
	 * 付款分录的插入SQL语句
	 */
	private final static String PAYENTRY_INSERT_SQL = "INSERT INTO T_FNC_FDCPayVoucherEntry" +
			"(FID,FSeq,FCurrencyid,FSupplierid,FAccountViewID,FDesc,FExchangeRate,FLocateAmount,FAmount,FType,FParentID,FCurProjectID,fcostAccountId) values " +
			"(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public void save(Map param, IObjectCollection payEntrys) throws BOSException {
		String paymentBillId ;
		//TODO
		PaymentBillInfo paymentBillInfo = null;
		Map paymentBills = (HashMap)param.get("paymentBills");
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.setPrepareStatementSql(PAYENTRY_INSERT_SQL);
		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		if(payEntrys!=null){
			for(Iterator it = payEntrys.iterator();it.hasNext();){
				FDCPayVoucherEntryInfo entryInfo = (FDCPayVoucherEntryInfo)it.next();
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
					entryInfo.setSeq(paymentBillInfo.getFdcPayVoucherEntry().size()+1);
					paymentBillInfo.getFdcPayVoucherEntry().add(entryInfo);
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
					builder.addParam(entryInfo.getCurProject()==null?null:entryInfo.getCurProject().getId().toString());
					builder.addParam(entryInfo.getCostAccount()==null?null:entryInfo.getCostAccount().getId().toString());
					builder.addBatch();
				}
			}
			builder.executeBatch();
		}
		
	}

	/**
	 * 应付账款_其他_进度款=归属成本金额-归属付款金额 （付款拆分）
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createPayEntryAccountView(PaymentBillInfo paymentBillInfo, PaymentNoCostSplitEntryInfo entryInfo) throws BOSException, EASBizException {
		FDCPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		//	应付账款_其他_进度款=归属成本金额-归属付款金额 （付款拆分）
		BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getAmount())
							.subtract(FDCHelper.toBigDecimal(entryInfo.getPayedAmt()))
							.subtract(FDCHelper.toBigDecimal(entryInfo.getQualityAmount()));
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(amount).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setCurProject(entryInfo.getCurProject());
		voucherEntryInfo.setLocateAmount(amount);
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		if(beforeAccountViewInfo!=null){//简单模式为空
			voucherEntryInfo.setAccountView(this.beforeAccountViewInfo.getOtherProAccount());
		}
		return voucherEntryInfo;
	}

	/**
	 * 银行存款 或 资金中心存款（付款单付款科目）
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createPayEntryPayAccount(PaymentBillInfo paymentBillInfo) throws BOSException, EASBizException {
		FDCPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		//
		BigDecimal amount = FDCHelper.toBigDecimal(paymentBillInfo.getActPayLocAmt());
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
		voucherEntryInfo.setAccountView(paymentBillInfo.getPayerAccount());
		return voucherEntryInfo;
	}
	/**
	 * 应付账款_暂估（财务成本一体化科目设置上“应付帐款_开发成本_暂估款”对应科目）  发票拆分金额
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createPayEntryTempAccount(PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo,boolean isFirstSett) throws BOSException, EASBizException {
		if(FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt()).signum()==0){
			return null;
		}
		FDCPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt());
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
		voucherEntryInfo.setAccountView(beforeAccountViewInfo.getTempAccount());
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		return voucherEntryInfo;
	}
	
	/**
	 * 应付账款_暂估（财务成本一体化科目设置上“应付帐款_开发成本_暂估款”对应科目）  发票拆分金额
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createPayEntryTempAccount(PaymentBillInfo paymentBillInfo, PaymentNoCostSplitEntryInfo entryInfo,boolean isFirstSett) throws BOSException, EASBizException {
		if(FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt()).signum()==0){
			return null;
		}
		FDCPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		voucherEntryInfo.setCurProject(entryInfo.getCurProject());
		BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getInvoiceAmt());
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
		voucherEntryInfo.setAccountView(beforeAccountViewInfo.getTempAccount());
		return voucherEntryInfo;
	}
	
	/**
	 * 应付账款_开发成本_进度款=归属成本金额-归属付款金额 （付款拆分）
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createPayEntryProAccount(PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo,boolean isFirstSett) throws BOSException, EASBizException {
		FDCPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		
		//应付账款_开发成本_进度款=归属成本金额 - 归属付款金额 （付款拆分）
		//第一笔结算款 还要再 - 保修金金额
		
		BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getAmount())
							.subtract(FDCHelper.toBigDecimal(entryInfo.getPayedAmt()));
		if(isFirstSett)
			amount = amount.subtract(FDCHelper.toBigDecimal(entryInfo.getQualityAmount()));
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
		if(beforeAccountViewInfo!=null){
			voucherEntryInfo.setAccountView(beforeAccountViewInfo.getProAccount());
		}
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		return voucherEntryInfo;
	}

	/**
	 * 应付账款_其它_保修款 : 付款拆分上的保修款拆分金额
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createPayEntrySettAccount(PaymentBillInfo paymentBillInfo, PaymentNoCostSplitEntryInfo entryInfo) throws BOSException, EASBizException {
		FDCPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		//付款拆分上的保修款拆分金额
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(entryInfo.getQualityAmount()).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setCurProject(entryInfo.getCurProject());
		voucherEntryInfo.setLocateAmount(entryInfo.getQualityAmount());
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		if(beforeAccountViewInfo!=null){
			voucherEntryInfo.setAccountView(beforeAccountViewInfo.getOtherSettAccount());
		}
		return voucherEntryInfo;
	}

	/**
	 * 应付账款_开发成本_保修款 : 付款拆分上的保修款拆分金额
	 * @param paymentBillInfo
	 * @param entryInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected FDCPayVoucherEntryInfo createPayEntrySettAccount(PaymentBillInfo paymentBillInfo, PaymentSplitEntryInfo entryInfo) throws BOSException, EASBizException {
		FDCPayVoucherEntryInfo voucherEntryInfo;
		voucherEntryInfo = new FDCPayVoucherEntryInfo();
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		//付款拆分上的保修款拆分金额
		BigDecimal oriAmount = FDCHelper.ZERO;
		if(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()).compareTo(FDCHelper.ZERO)!=0)
			oriAmount = FDCHelper.toBigDecimal(entryInfo.getQualityAmount()).divide(FDCHelper.toBigDecimal(paymentBillInfo.getExchangeRate()),2);
		voucherEntryInfo.setAmount(oriAmount);
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setLocateAmount(entryInfo.getQualityAmount());
		voucherEntryInfo.setExchangeRate(paymentBillInfo.getExchangeRate());
		voucherEntryInfo.setSupplier(paymentBillInfo.getActFdcPayeeName());
		voucherEntryInfo.setCurrency(getBaseCurrency());
		voucherEntryInfo.setParent(paymentBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		if(beforeAccountViewInfo!=null){
			voucherEntryInfo.setAccountView(beforeAccountViewInfo.getSettAccount());
		}
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		return voucherEntryInfo;
	}
	
}
