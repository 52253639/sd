package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashRecCollection;
import com.kingdee.eas.fi.cas.AssItemsForCashRecInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillEntryCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class BalanceAdjustFacadeControllerBean extends AbstractBalanceAdjustFacadeControllerBean
{
    private static final String ADJUST_PREFIX = "Adjust_Prefix_";
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.BalanceAdjustFacadeControllerBean");
    
    /**
     * 月结调整：
     * 1.作废收款单(此作废仅限于售楼的收款单)
     * 2.只能对已月结期间的收款单做调整
     * 3.后台自动生成调整类型的收款单,收款单内容同原收款单完全一致但金额为负数.
     * 4.转款单的调整,后台要自动删除原转款单对应的红冲单,同时还原原收款单的可红冲金额和已红冲金额,实收金额待定.同时再反写收款单对应的收款明细.
     * 5.如果预收类型的收款单已存在红冲单则不能调整.
     * 6.调整单不能再被调整.
     * 7.调整单如果会改变原收款房间状态不允许调整(可以通过调整单据),目前暂不控制,现场人为控制..
     * 8.调整可以生成凭证.
     * @param id 出纳收款单ID
     * 
     * <br>
     * 1.房地产收款单增加字段，是否已作废。
     * 2.房地产收款单增加字段，原收款单ID，用来标识调整单是针对哪个收款单进行的调整。
     * 3.
     * 
     * @param isInv 是否反写相关收款明细的实收等数据
     * @param isVerify 是否校验能否调整
     * 
     * PS:我也不想这么写函数，但目前收款单就是这么恶心...
     * */
    protected void _blankOutRevBill(Context ctx, String id, boolean isInv, boolean isVerify)throws BOSException, EASBizException{
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("*");
    	sels.add("entries.*");
    	sels.add("fdcReceiveBill.*");
    	sels.add("fdcReceiveBill.purchase.*");
    	sels.add("fdcReceiveBill.customerEntrys.*");
    	sels.add("fdcReceiveBill.distillCommisionEntry.*");
    	sels.add("fdcReceiveBillEntry.*");
    	sels.add("fdcReceiveBillEntry.moneyDefine.*");
    	ReceivingBillInfo rev = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(id), sels);
    	
    	FDCReceiveBillInfo fdcRev = rev.getFdcReceiveBill();
    	if(fdcRev == null){
    		throw new EASBizException(new NumericExceptionSubItem("101","数据错误，收款单不完整！"));
    	}
    	
    	//调整单不能调整
    	if(ReceiveBillTypeEnum.adjust.equals(fdcRev.getBillTypeEnum())){
    		if(isVerify){
    			throw new EASBizException(new NumericExceptionSubItem("100","调整单不允许进行调整！"));
    		}else{
    			//调整单不允许进行调整！
    			return;
    		}
    	}
    	
    	//检查是否可以作废
    	if(!MoneySysTypeEnum.SalehouseSys.equals(fdcRev.getMoneySysType())){
    		throw new EASBizException(new NumericExceptionSubItem("102","只能对售楼系统的收款单进行调整！"));
    	}
    	
    	if(fdcRev.isIsBlankOut()){
    		if(isVerify){
    			throw new EASBizException(new NumericExceptionSubItem("104","已经调整的收款单不能再进行调整！"));
    		}else{//如果已经作废了,直接返回
    			return;
    		}
    	}
    	
    	if(ReceiveBillTypeEnum.settlement.equals(fdcRev.getBillTypeEnum())){
    		if(isVerify){
    			throw new EASBizException(new NumericExceptionSubItem("103","只能对该单据的转款单进行调整，系统会自动调整此红冲单！"));
    		}else{
    			//红冲单不单独作废，在作废对应转款单时同时作废红冲单.
    			return;
    		}
    	}
    	
    	//TODO
    	//如果是预定金的收款，如果这个预定金是否已经退款，或者已经转到定金上面，则该收款单不允许进行作废操作
    	
    	if(isVerify){
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			BigDecimal counteractAmount = fdcRevEntry.getCounteractAmount();
    			if(counteractAmount != null  &&  counteractAmount.compareTo(FDCHelper.ZERO) > 0){
    				//如果已退金额不会0，则不允许进行作废
//    				FilterInfo tmpFilter = new FilterInfo();
//    				tmpFilter.getFilterItems().add(new FilterItemInfo("FCounteractId", fdcRevEntry.getId().toString()));
//    				tmpFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.isBlankOut", Boolean.FALSE));
//    				if(FDCReceiveBillEntryFactory.getLocalInstance(ctx).exists(tmpFilter)){
    					throw new EASBizException(new NumericExceptionSubItem("105","该收款单被冲或者被退过，不允许调整！"));
//    				}
    			}
    		}
    	}
    	
    	//提交一张与原收款单收款金额相反的收款单
    	ReceivingBillInfo adjustRev = buildAdjustRev(ctx, rev);
    	FDCReceiveBillInfo adjustFdcRev = adjustRev.getFdcReceiveBill();
    	FDCReceiveBillFactory.getLocalInstance(ctx).submit(adjustFdcRev);
    	ReceivingBillFactory.getLocalInstance(ctx).submit(adjustRev);
    	
    	//将当前收款单置为作废
    	fdcRev.setIsBlankOut(true);
    	SelectorItemCollection blankOutSels = new SelectorItemCollection();
    	blankOutSels.add("isBlankOut");
    	FDCReceiveBillFactory.getLocalInstance(ctx).updatePartial(fdcRev, blankOutSels);
    	
    	//还要进行相关的反写操作。需要区分原收款单是收款，退款还是转款
    	ReceiveBillTypeEnum billType = fdcRev.getBillTypeEnum();
    	if(ReceiveBillTypeEnum.gathering.equals(billType)){
    		//收款需要反写对应付款明细的实收金额
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			String payId = fdcRevEntry.getPayListId();
    			BigDecimal amount = fdcRevEntry.getAmount();
    			if(amount == null) amount = FDCHelper.ZERO;
    			boolean isValidPayId = BOSUuid.isValid(payId, true);
    			
    			if(GatheringEnum.SinPurchase.equals(fdcRev.getGathering())){
    				if(!isValidPayId){
    					logger.error("错误的收款单分录：" + fdcRevEntry.getId().toString());
    					throw new EASBizException(new NumericExceptionSubItem("106","不完整的数据.revId" + id));
    				}
        			SincerReceiveEntryInfo sinPay = null;
					try {
						sinPay = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
					} catch (EASBizException e) {
						logger.error("错误的收款单分录：" + fdcRevEntry.getId().toString());
						throw new EASBizException(new NumericExceptionSubItem("106","不完整的数据.revId" + id));
					}
        			BigDecimal actAmount = sinPay.getActRevAmount();
        			if(actAmount == null) actAmount = FDCHelper.ZERO;
        			BigDecimal canRefundmentAmount = sinPay.getCanRefundmentAmount();
        			if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
        			
        			sinPay.setActRevAmount(actAmount.subtract(amount));
//        			sinPay.setLimitAmount(canRefundmentAmount.subtract(amount));
        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
        			tmpUpdateSels.add("actAmount");
        			tmpUpdateSels.add("canRefundmentAmount");
        			if(isInv)SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinPay, tmpUpdateSels);
    			}else{//收款对象为销售房间或者为空。为空时也当作销售房间处理
    				//销售房间的收款，可能有付款明细，其他明细，预定金及补差款
    				
    				PurchaseInfo purchase = fdcRev.getPurchase();
    				if(purchase == null){
    					logger.error("不完整的数据.revId" + id);
    					throw new EASBizException(new NumericExceptionSubItem("106","不完整的数据.revId" + id));
    				}
    				
    				//如果对应的认购单已经退房作废或者换房作废，则该收款单不允许再作废
    				if(isVerify  &&  (PurchaseStateEnum.QuitRoomBlankOut.equals(purchase.getPurchaseState())
    						|| PurchaseStateEnum.ChangeRoomBlankOut.equals(purchase.getPurchaseState()))){
    					throw new EASBizException(new NumericExceptionSubItem("107","认购单已经作废，其收款单不能再调整！"));
    				}
    				
    				if(isValidPayId){//
    					//有效的payId，可能是付款明细，其他明细。
    					PurchasePayListEntryInfo purPay = null;
						try {
							purPay = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryInfo(new ObjectUuidPK(payId));
						} catch (EASBizException e) {
							e.printStackTrace();
						}
    					if(purPay != null){
    						BigDecimal actAmount = purPay.getActPayAmount();
		        			if(actAmount == null) actAmount = FDCHelper.ZERO;
		        			
		        			purPay.setActRevAmount(actAmount.subtract(amount));
		        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
		        			tmpUpdateSels.add("actPayAmount");
//		        			if(isInv)PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purPay, tmpUpdateSels);
    					}else{
    						PurchaseElsePayListEntryInfo elsePay = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryInfo(new ObjectUuidPK(payId));
    						if(elsePay != null){
    							BigDecimal actAmount = elsePay.getActPayAmount();	
    		        			if(actAmount == null) actAmount = FDCHelper.ZERO;
    		        			
    		        			elsePay.setActRevAmount(actAmount.subtract(amount));
    		        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
    		        			tmpUpdateSels.add("actPayAmount");
//    		        			if(isInv)PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePay, tmpUpdateSels);
    						}else{
    							//找不到payId对应的明细，该收款可能是预定金，补差款
    							//TODO 
    						}
    					}
    				}else{//不合法的payId，该收款可能是预定金，补差款
    					//不用处理
    				}
    			}
    		}
    	}else if(ReceiveBillTypeEnum.refundment.equals(billType)){
    		//退款需要反写实收金额.以及可退金额和已退金额
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			String payId = fdcRevEntry.getPayListId();
    			
    			BigDecimal amount = fdcRevEntry.getAmount();
    			if(amount == null) amount = FDCHelper.ZERO;
    			boolean isValidPayId = BOSUuid.isValid(payId, true);
    			
    			if(GatheringEnum.SinPurchase.equals(fdcRev.getGathering())){
    				if(!isValidPayId){
    					logger.error("错误的收款单分录：" + fdcRevEntry.getId().toString());
    					throw new EASBizException(new NumericExceptionSubItem("106","不完整的数据.revId" + id));
    				}
    				SincerReceiveEntryInfo sinPay = null;
    				try{
    					sinPay = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
    				}catch(EASBizException e){
    				}
    				
    				if(sinPay == null){
    					throw new EASBizException(new NumericExceptionSubItem("106","不完整的数据.revId" + id));
    				}
    				
        			//诚意认购退款时，未对付款明细的实收做反写
        			//不用对实收做反写，但需要对可退金额和已退金额做反写
        			BigDecimal canRefundmentAmount = sinPay.getCanRefundmentAmount();
    				if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
        			BigDecimal refundmentAmount = sinPay.getRefundmentAmount();
        			if(refundmentAmount == null) refundmentAmount = FDCHelper.ZERO;
        			
//        			sinPay.setLimitAmount(canRefundmentAmount.add(amount.abs()));
//        			sinPay.setHasRefundmentAmount(refundmentAmount.subtract(amount.abs()));
        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
        			tmpUpdateSels.add("canRefundmentAmount");
        			tmpUpdateSels.add("refundmentAmount");
        			
        			if(isInv)SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinPay, tmpUpdateSels);
        			
        			//对应的收款单，也要进行可退金额的修改 TODO
    			}else if(GatheringEnum.CustomerRev.equals(fdcRev.getGathering())){//退款就有针对暂定款的退款咯
    				//找到对应的暂定款，然后反写对应的可退金额
    				//TODO 新增的功能，先不考虑
    			}else{//退款对象为销售房间或者为空，为空时当作销售房间处理
    				if(isValidPayId){
    					//付款明细的退款或其他明细的退款.
    					//需要反写可退金额，已退金额和实收金额
    					PurchasePayListEntryInfo purPay = null;
						try {
							purPay = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryInfo(new ObjectUuidPK(payId));
						} catch (EASBizException e) {
							e.printStackTrace();
						}
    					if(purPay != null){
    						BigDecimal canRefundmentAmount = purPay.getCanRefundmentAmount();
    	    				if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
    	        			BigDecimal refundmentAmount = purPay.getRefundmentAmount();
    	        			if(refundmentAmount == null) refundmentAmount = FDCHelper.ZERO;
    						BigDecimal actAmount = purPay.getActPayAmount();
    						if(actAmount == null) actAmount = FDCHelper.ZERO;
    						
    						purPay.setLimitAmount(canRefundmentAmount.add(amount.abs()));
    						purPay.setHasRefundmentAmount(refundmentAmount.subtract(amount.abs()));
    						purPay.setActRevAmount(actAmount.add(amount.abs()));
    						
    						SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
    	        			tmpUpdateSels.add("canRefundmentAmount");
    	        			tmpUpdateSels.add("refundmentAmount");
    	        			tmpUpdateSels.add("actPayAmount");
    						
//    	        			if(isInv)PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purPay, tmpUpdateSels);
    					}else{
    						PurchaseElsePayListEntryInfo elsePay = null;
							try {
								elsePay = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryInfo(new ObjectUuidPK(payId));
							} catch (EASBizException e) {
								e.printStackTrace();
							}
    						if(elsePay != null){
    							BigDecimal canRefundmentAmount = elsePay.getCanRefundmentAmount();
        	    				if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
        	        			BigDecimal refundmentAmount = elsePay.getRefundmentAmount();
        	        			if(refundmentAmount == null) refundmentAmount = FDCHelper.ZERO;
        						BigDecimal actAmount = elsePay.getActPayAmount();
        						if(actAmount == null) actAmount = FDCHelper.ZERO;
        						
        						elsePay.setLimitAmount(canRefundmentAmount.add(amount.abs()));
        						elsePay.setHasRefundmentAmount(refundmentAmount.subtract(amount.abs()));
        						elsePay.setActRevAmount(actAmount.add(amount.abs()));
        						
        						SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
        	        			tmpUpdateSels.add("canRefundmentAmount");
        	        			tmpUpdateSels.add("refundmentAmount");
        	        			tmpUpdateSels.add("actPayAmount");
        						
//        	        			if(isInv)PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePay, tmpUpdateSels);
    						}else{
    							//预定金的退款,补差款的退款,对应限制5
    							//TODO 要找到对应的预订金收款，反写其可退金额
    							if(isInv){
    								PurchaseInfo purchase = fdcRev.getPurchase();
    								if(purchase == null){
    			    					logger.error("不完整的数据.revId" + id);
    			    					throw new EASBizException(new NumericExceptionSubItem("106","不完整的数据.revId" + id));
    			    				}
    								String purId = purchase.getId().toString();
    								//TODO 
    							}
    						}
    					}
    				}else{
    					//预定金的退款,补差款的退款
    					//TODO
    				}
    			}
    		}
    	}else if(ReceiveBillTypeEnum.transfer.equals(billType)){
    		//转款需要综合上面2种反写。将转款单进行作废，同时将其对应的红冲单进行作废
    		//转款有销售房间，客户这2种
    		PurchaseInfo purchase = fdcRev.getPurchase();			
			if(purchase != null  &&  isVerify  &&  (PurchaseStateEnum.QuitRoomBlankOut.equals(purchase.getPurchaseState())
					|| PurchaseStateEnum.ChangeRoomBlankOut.equals(purchase.getPurchaseState()))){
				throw new EASBizException(new NumericExceptionSubItem("107","认购单已经作废，其转款单不能再调整！"));
			}
    		
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			
    			MoneyDefineInfo moneyDefine = fdcRevEntry.getMoneyDefine();
    			//对于转款的手续费，说明是退房结转或换房结转生成的.
    			if(MoneyTypeEnum.CommissionCharge.equals(moneyDefine.getMoneyType())){
        			if(isVerify){
        				throw new EASBizException(new NumericExceptionSubItem("112","结算生成的手续费不允许调整！"));
        			}
    			}
    			
    			String payId = fdcRevEntry.getPayListId();
    			
    			BigDecimal amount = fdcRevEntry.getAmount();
    			if(amount == null) amount = FDCHelper.ZERO;
    			boolean isValidPayId = BOSUuid.isValid(payId, true);
    			
    			if(isValidPayId){
    				PurchasePayListEntryInfo purPay = null;
					try {
						purPay = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryInfo(new ObjectUuidPK(payId));
					} catch (EASBizException e) {
						e.printStackTrace();
					}
    				if(purPay != null){
    					BigDecimal actAmount = purPay.getActPayAmount();	
	        			if(actAmount == null) actAmount = FDCHelper.ZERO;
	        			
	        			purPay.setActRevAmount(actAmount.subtract(amount));
	        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
	        			tmpUpdateSels.add("actPayAmount");
//	        			if(isInv)PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purPay, tmpUpdateSels);
    				}else{//为空则可能为预定金, 不用处理
    					
    				}
    			}else{//payId为空，说明转款为退房的手续费，或者换房的手续费，或者换房的中间款
    				//不用处理
    			}
    		}
    		
    		//如果是转款，且收款对象是客户，则说明是 换房结算
    		if(GatheringEnum.CustomerRev.equals(fdcRev.getGathering())){
    			if(isVerify){
    				throw new EASBizException(new NumericExceptionSubItem("112","换房结算生成的转款不允许调整！"));
    			}
    		}
    		
    		ReceivingBillInfo settleRev = fdcRev.getSettlementBill();
    		if(settleRev == null){
    			logger.error("转款单没有找到对应的红冲单呀！");
    			throw new EASBizException(new NumericExceptionSubItem("111","转款单没有找到对应的红冲单呀！"));
    		}
    		
        	settleRev = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(settleRev.getId()), sels);
        	
    		//对这个红冲单，也要进行调整
    		ReceivingBillInfo adjustSettleRev = buildAdjustRev(ctx, settleRev);
        	FDCReceiveBillInfo adjustSettleFdcRev = adjustSettleRev.getFdcReceiveBill();
        	//提交了一张与原收款单收款金额相反的收款单
        	FDCReceiveBillFactory.getLocalInstance(ctx).submit(adjustSettleFdcRev);
        	ReceivingBillFactory.getLocalInstance(ctx).submit(adjustSettleRev);
        	
        	//作废该红冲单
        	adjustSettleFdcRev.setIsBlankOut(true);
        	FDCReceiveBillFactory.getLocalInstance(ctx).updatePartial(fdcRev, blankOutSels);
        	
        	//反写原被红冲的收款单的可冲金额和已冲金额。如果有付款明细的话，还要反写付款明细的可充金额和已冲金额及实收金额
        	//TODO 此次转款调整只允许诚意金转的可以调整
        	//转的手续费和暂定款不允许调整
        	for(int i=0; i<settleRev.getFdcReceiveBillEntry().size(); i++){
        		FDCReceiveBillEntryInfo fdcRevEntry = settleRev.getFdcReceiveBillEntry().get(i);
        		
        		BigDecimal amount = fdcRevEntry.getAmount();
        		if(amount == null) amount = FDCHelper.ZERO;
        		
        		String counteractId = fdcRevEntry.getFCounteractId();
        		if(!BOSUuid.isValid(counteractId, true)){
        			logger.error("红冲单找不到对应的被红冲单据！");
        			throw new EASBizException(new NumericExceptionSubItem("112","红冲单找不到对应的被红冲单据！"));
        		}
        		
        		FDCReceiveBillEntryInfo counteractRevEntry = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryInfo(new ObjectUuidPK(counteractId));
        		
        		BigDecimal cCan = counteractRevEntry.getCanCounteractAmount();
        		if(cCan == null) cCan = FDCHelper.ZERO;
        		
        		BigDecimal cCou = counteractRevEntry.getCounteractAmount();
        		if(cCou == null) cCou = FDCHelper.ZERO;
        		
        		counteractRevEntry.setCanCounteractAmount(cCan.add(amount.abs()));
        		counteractRevEntry.setCounteractAmount(cCou.subtract(amount.abs()));
        		
        		SelectorItemCollection cUpdateSels = new SelectorItemCollection();
        		cUpdateSels.add("canCounteractAmount");
        		cUpdateSels.add("counteractAmount");
        		if(isInv)FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(counteractRevEntry, cUpdateSels);
        		
        		//找到被红冲单的付款明细，对于诚意金转款，也需要反写付款明细的可退金额等字段
        		String counteractPayId = counteractRevEntry.getPayListId();
        		
        		if(!BOSUuid.isValid(counteractPayId, true)){
        			//如果是暂定款转的房款，红冲单是没有应收明细的.
        			continue;
//        			logger.error("被红冲单找不到对应的诚意认购收款明细！");
//        			throw new EASBizException(new NumericExceptionSubItem("112","被红冲单找不到对应的诚意认购收款明细！"));
        		}
        		
        		SincerReceiveEntryInfo sinPay = null;
				try {
					sinPay = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(counteractPayId));
				} catch (EASBizException e) {
					e.printStackTrace();
				}
				if(sinPay == null){
					continue;
				}
        		BigDecimal sinCanRefundmentAmount = sinPay.getCanRefundmentAmount();
        		if(sinCanRefundmentAmount == null) sinCanRefundmentAmount = FDCHelper.ZERO;
        		BigDecimal sinRefundmentAmount = sinPay.getRefundmentAmount();
        		if(sinRefundmentAmount == null) sinRefundmentAmount = FDCHelper.ZERO;
        		
//        		sinPay.setLimitAmount(sinCanRefundmentAmount.add(amount.abs()));
//        		sinPay.setRefundmentAmount(sinRefundmentAmount.subtract(amount.abs()));
        		SelectorItemCollection sinUpdateSels = new SelectorItemCollection();
        		sinUpdateSels.add("canRefundmentAmount");
//        		sinUpdateSels.add("refundmentAmount");
        		if(isInv)SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinPay, sinUpdateSels);
        	}
    	}
    }
    
    /**
     * 根据原收养单生成一张调整类型的收款单，金额与原收款单相反.
     * @throws BOSException 
     * @throws EASBizException 
     * */
    private ReceivingBillInfo buildAdjustRev(Context ctx, ReceivingBillInfo rev) throws BOSException, EASBizException{
    	ReceivingBillInfo adjustRev = (ReceivingBillInfo) rev.clone();
    	FDCReceiveBillInfo adjustFdcRev = adjustRev.getFdcReceiveBill();
    	adjustRev.setId(null);
    	adjustRev.setBillStatus(BillStatusEnum.SUBMIT);
    	Timestamp t = FDCSQLFacadeFactory.getLocalInstance(ctx).getServerTime();
    	adjustRev.setBizDate(t);
    	adjustRev.setCreateTime(t);
//    	adjustRev.setIsAppointVoucher(false);
    	adjustRev.setFiVouchered(false);
    	
		OrgUnitInfo crrOu = ContextUtil.getCurrentSaleUnit(ctx);   //当前销售组织;
		if(crrOu==null)
			crrOu = ContextUtil.getCurrentOrgUnit(ctx);            //当前组织
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		if(iCodingRuleManager.isExist(new ReceivingBillInfo(), crrOu.getId().toString())){
			String number = iCodingRuleManager.getNumber(new ReceivingBillInfo(), crrOu.getId().toString());
			adjustRev.setNumber(number);
		}else{
			adjustRev.setNumber(ADJUST_PREFIX + rev.getNumber());
		}
		adjustFdcRev.setNumber(null);
		
    	BigDecimal srcRevAmount = adjustRev.getAmount();
		if(srcRevAmount != null){
			srcRevAmount = FDCHelper.ZERO.subtract(srcRevAmount);
		}
    	adjustRev.setAmount(srcRevAmount);
		adjustRev.setActRecAmt(srcRevAmount);
		
    	adjustFdcRev.setId(BOSUuid.create(new FDCReceiveBillInfo().getBOSType()));
    	adjustFdcRev.setBillTypeEnum(ReceiveBillTypeEnum.adjust);
    	adjustFdcRev.setAdjustSrcBill(rev);
    	//清空全收款单的发票，票据
    	adjustFdcRev.setInvoice(null);
    	adjustFdcRev.setCheque(null);
    	adjustFdcRev.setReceiptNumber(null);
    	
    	FDCReceiveBillEntryCollection fdcRevEntrys = adjustRev.getFdcReceiveBillEntry();
    	for(int i=0; i<fdcRevEntrys.size(); i++){
    		FDCReceiveBillEntryInfo fdcRevEntry = fdcRevEntrys.get(i);
    		fdcRevEntry.setId(null);
    		BigDecimal srcRevEntryAmount = fdcRevEntry.getAmount();
    		if(srcRevEntryAmount != null){
    			srcRevEntryAmount = FDCHelper.ZERO.subtract(srcRevEntryAmount);
    		}
    		fdcRevEntry.setAmount(srcRevEntryAmount);
    	}
    	
    	CustomerEntryCollection customerEntrys = adjustFdcRev.getCustomerEntrys();
    	for(int i=0; i<customerEntrys.size(); i++){
    		CustomerEntryInfo customerEntry = customerEntrys.get(i);
    		customerEntry.setId(null);
    	}
    	
    	ReceiveDistillCommisionEntryCollection dises =  adjustFdcRev.getDistillCommisionEntry();
    	for(int i=0; i<dises.size(); i++){
    		ReceiveDistillCommisionEntryInfo dis = dises.get(i);
    		dis.setId(null);
    	}
    	
    	AssItemsForCashRecCollection assItems = adjustRev.getAssItems();
    	for(int i=0; i<assItems.size(); i++){
    		AssItemsForCashRecInfo assItem = assItems.get(i);
    		assItem.setId(null);
    	}
    	
    	ReceivingBillEntryCollection revEntrys = adjustRev.getEntries();
    	for(int i=0; i<revEntrys.size(); i++){
    		ReceivingBillEntryInfo revEntry = revEntrys.get(i);
    		revEntry.setId(null);
    	}
    	
    	return adjustRev;
    }
    
    //作废收款单，不进行相关实收的反写
    private void blankOutRev(){
    	
    }
    
    /**
     * 作废认购单，实际是做退房类似的操作，将房间变为待售状态，并同时作废该认购单所有的收款单
     * */
    protected void _blankOutPurchase(Context ctx, String id)throws BOSException,EASBizException
    {
    }
}