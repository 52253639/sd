package com.kingdee.eas.fi.cas.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.CashPamentBillUtil;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class PaymentBillControllerBeanEx extends PaymentBillControllerBean {
	
	protected Map _payForBook(Context ctx, Set idSet, boolean ifBookPayable, boolean isInvokeWF, boolean validateThrowExp)throws BOSException, EASBizException{	
		Map msg=null;
		Map map = helper.getPayValidMapByAction(ctx, idSet, 60);
		PaymentBillCollection coll = (PaymentBillCollection)map.get("validColl");
		PaymentBillInfo info = null;
        int i = 0;
        Date now=SysUtil.getAppServerTime(ctx);
        for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getSourceType().equals(SourceTypeEnum.FDC)){
            	if(info.getFdcPayReqID() != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),getSelectors());
            		if(payRequest.isHasClosed()){
            			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止付款！"));
            		}
            		if(payRequest.isIsBgControl()){
            			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
            			info.setPayDate(now);
            			Map bgmap=iBgControlFacade.checkBudget(info);
            			if(!((Boolean)bgmap.get("isPass")).booleanValue()){
            				throw new EASBizException(new NumericExceptionSubItem("100",bgmap.get("message").toString()));
            			}
            		}
                }
            	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
            	builder = new FDCSQLBuilder(ctx);
                builder.appendSql("update T_CAS_PaymentBill set fbizDate=? where fid=? ");
                builder.addParam(now);
                builder.addParam(info.getId().toString());
                builder.executeUpdate();
    		}
        }
        msg=super._payForBook(ctx, idSet, ifBookPayable,isInvokeWF,validateThrowExp);
		info = null;
        i = 0;
        
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("bgEntry.actPayAmount");
		sic.add("payDate");
		for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getSourceType().equals(SourceTypeEnum.FDC)){
            	if(info.getFdcPayReqID() != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),getSelectors());
            		if(payRequest.isIsBgControl()){
            			payRequest.setPayDate(now);
            			if(payRequest.getCurrency().getId().toString().equals(info.getCurrency().getId().toString())
            					&&payRequest.getOriginalAmount().compareTo(info.getAmount())!=0){
            				BigDecimal rate=info.getAmount().divide(FDCHelper.toBigDecimal(payRequest.getOriginalAmount()),6,BigDecimal.ROUND_HALF_UP);	
            				BigDecimal total=FDCHelper.ZERO;
            				for(int k=0;k<payRequest.getBgEntry().size();k++){
            					BigDecimal amount=FDCHelper.ZERO;
            					if(k==payRequest.getBgEntry().size()-1){
            						amount=info.getAmount().subtract(total);
            					}else{
            						amount=payRequest.getBgEntry().get(k).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
            						total=total.add(amount);
            					}
            					if(payRequest.getBgEntry().get(k).getActPayAmount()!=null){
            						payRequest.getBgEntry().get(k).setActPayAmount(payRequest.getBgEntry().get(k).getActPayAmount().add(amount));
            					}else{
            						payRequest.getBgEntry().get(k).setActPayAmount(amount);
            					}
            				}
            			}else{
            				for(int k=0;k<payRequest.getBgEntry().size();k++){
            					payRequest.getBgEntry().get(k).setActPayAmount(payRequest.getBgEntry().get(k).getRequestAmount());
            				}
            			}
                        PayRequestBillFactory.getLocalInstance(ctx).updatePartial(payRequest, sic);
                        
                        BgControlFacadeFactory.getLocalInstance(ctx).bgAudit(info.getId().toString(), "com.kingdee.eas.fi.cas.app.PaymentBill", null);
                        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                        builder.appendSql("update T_CON_PayRequestBill set fpaydate=? where fid=? ");
                        builder.addParam(now);
                        builder.addParam(payRequest.getId().toString());
                        builder.executeUpdate();
                        
                        if(BOSUuid.read(payRequest.getContractId()).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
                        	builder = new FDCSQLBuilder(ctx);
                            builder.appendSql("update T_CON_ContractWithoutText set FSignDate=? where fid=? ");
                            builder.addParam(now);
                            builder.addParam(payRequest.getContractId());
                            builder.executeUpdate();
                        }
            		}
            	}
            }
        }
		return msg;
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
	    sic.add(new SelectorItemInfo("sourceType"));
	    sic.add(new SelectorItemInfo("fdcPayReqID"));
		PaymentBillInfo info = getPaymentBillInfo(ctx, pk, sic);
		if(info.getSourceType().equals(SourceTypeEnum.FDC)){
        	if(info.getFdcPayReqID() != null){
        		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),getSelectors());
        		if(payRequest.isHasClosed()){
        			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止删除！"));
        		}
        	}
		}
		super._delete(ctx, pk);
	}
	protected Map _cancelPaySilent(Context ctx, Set idSet) throws BOSException, EASBizException {
		Map map = helper.getPayValidMapByAction(ctx, idSet, 70);
		PaymentBillCollection coll = (PaymentBillCollection)map.get("validColl");
		PaymentBillInfo info = null;
        int i = 0;
		Map reMap=super._cancelPaySilent(ctx, idSet);
		for(int size = coll.size(); i < size; i++){
			info = coll.get(i);
            if(info.getSourceType().equals(SourceTypeEnum.FDC)){
            	if(info.getFdcPayReqID() != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),getSelectors());
            		if(payRequest.isHasClosed()){
            			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止取消付款！"));
            		}
            		if(payRequest.isIsBgControl()){
            			BgControlFacadeFactory.getLocalInstance(ctx).returnBudget(BOSUuid.read(info.getId().toString()), "com.kingdee.eas.fi.cas.app.PaymentBill", null);
            			for(int k=0;k<payRequest.getBgEntry().size();k++){
            				for(int j=0;j<info.getEntries().size();j++){
	    						if(info.getEntries().get(j).getSourceBillEntryId().equals(payRequest.getBgEntry().get(k).getId().toString())){
	    							payRequest.getBgEntry().get(k).setActPayAmount(payRequest.getBgEntry().get(k).getActPayAmount().subtract(info.getEntries().get(j).getAmount()));
	    						}
	    					}
        				}
            			SelectorItemCollection sic = new SelectorItemCollection();
            			sic.add("bgEntry.actPayAmount");
        	            PayRequestBillFactory.getLocalInstance(ctx).updatePartial(payRequest, sic);
        			}
            		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                    builder.appendSql("update T_CON_PayRequestBill set fpaydate=? where fid=? ");
                    builder.addParam(null);
                    builder.addParam(info.getFdcPayReqID());
                    builder.executeUpdate();
                    
                    if(BOSUuid.read(info.getContractBillId()).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
                    	builder = new FDCSQLBuilder(ctx);
                        builder.appendSql("update T_CON_ContractWithoutText set FSignDate=? where fid=? ");
                        builder.addParam(null);
                        builder.addParam(info.getContractBillId());
                        builder.executeUpdate();
                    }
                }
    		}
        }
		return reMap;
	}
	protected void _cancelPay(Context ctx, Set idSet) throws BOSException, EASBizException {
		Map map = helper.getPayValidMapByAction(ctx, idSet, 70);
		PaymentBillCollection coll = (PaymentBillCollection)map.get("validColl");
		PaymentBillInfo info = null;
        int i = 0;
		super._cancelPay(ctx, idSet);
        for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getSourceType().equals(SourceTypeEnum.FDC)){
            	if(info.getFdcPayReqID() != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),getSelectors());
            		if(payRequest.isHasClosed()){
            			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止取消付款！"));
            		}
            		if(payRequest.isIsBgControl()){
            			BgControlFacadeFactory.getLocalInstance(ctx).returnBudget(BOSUuid.read(info.getId().toString()), "com.kingdee.eas.fi.cas.app.PaymentBill", null);
            			for(int k=0;k<payRequest.getBgEntry().size();k++){
            				for(int j=0;j<info.getEntries().size();j++){
	    						if(info.getEntries().get(j).getSourceBillEntryId().equals(payRequest.getBgEntry().get(k).getId().toString())){
	    							payRequest.getBgEntry().get(k).setActPayAmount(payRequest.getBgEntry().get(k).getActPayAmount().subtract(info.getEntries().get(j).getAmount()));
	    						}
	    					}
        				}
            			SelectorItemCollection sic = new SelectorItemCollection();
            			sic.add("bgEntry.actPayAmount");
        	            PayRequestBillFactory.getLocalInstance(ctx).updatePartial(payRequest, sic);
        			}
            		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                    builder.appendSql("update T_CON_PayRequestBill set fpaydate=? where fid=? ");
                    builder.addParam(null);
                    builder.addParam(info.getFdcPayReqID());
                    builder.executeUpdate();
                    
                    if(BOSUuid.read(info.getContractBillId()).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
                    	builder = new FDCSQLBuilder(ctx);
                        builder.appendSql("update T_CON_ContractWithoutText set FSignDate=? where fid=? ");
                        builder.addParam(null);
                        builder.addParam(info.getContractBillId());
                        builder.executeUpdate();
                    }
                }
    		}
        }
	}
	private SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("number");
		sic.add("state");
		sic.add("name");
		sic.add("payDate");
		sic.add("recBank");
		sic.add("recAccount");
		sic.add("moneyDesc");
		sic.add("contractNo");
		sic.add("description");
		sic.add("urgentDegree");
		sic.add("attachment");
		sic.add("bookedDate");
		sic.add("originalAmount");
		sic.add("amount");
		sic.add("exchangeRate");
		sic.add("process");
		sic.add("lastUpdateTime");

		sic.add("paymentProportion");
		sic.add("costAmount");
		sic.add("grtAmount");
		sic.add("capitalAmount");

		// 1
		sic.add("contractName");
		sic.add("changeAmt");
		sic.add("payTimes");
		// sic.add("curPlannedPayment");
		sic.add("curReqPercent");

		// 2
		sic.add("settleAmt");
		sic.add("curBackPay");
		sic.add("allReqPercent");

		//
		sic.add("guerdonOriginalAmt");
		sic.add("compensationOriginalAmt");

		// 合同内工程款
		sic.add("lstPrjAllPaidAmt");
		sic.add("lstPrjAllReqAmt");
		sic.add("projectPriceInContract");
		sic.add("projectPriceInContractOri");
		sic.add("prjAllReqAmt");

		// 预付款
		sic.add("prjPayEntry.lstAdvanceAllPaid");
		sic.add("prjPayEntry.lstAdvanceAllReq");
		sic.add("prjPayEntry.advance");
		sic.add("prjPayEntry.locAdvance");
		sic.add("prjPayEntry.advanceAllReq");
		sic.add("prjPayEntry.advanceAllPaid");

		// 甲供
		sic.add("lstAMatlAllPaidAmt");
		sic.add("lstAMatlAllReqAmt");
		sic.add("payPartAMatlAmt");
		sic.add("payPartAMatlOriAmt");
		sic.add("payPartAMatlAllReqAmt");

		// 实付
		sic.add("curPaid");

		// 5
		sic.add("contractPrice");
		sic.add("latestPrice");
		sic.add("payedAmt");
		sic.add("imageSchedule");
		sic.add("completePrjAmt");
		//
		sic.add("contractId");
		sic.add("hasPayoff");
		sic.add("hasClosed");
		sic.add("isPay");
		sic.add("auditTime");
		sic.add("createTime");
		sic.add("fivouchered");
		sic.add("sourceType");

		sic.add("isDifferPlace");
		sic.add("usage");

		// totalSettlePrice
		sic.add("totalSettlePrice");

		// 发票
		sic.add("invoiceNumber");
		sic.add("invoiceAmt");
		sic.add("allInvoiceAmt");
		sic.add("invoiceDate");
		
		sic.add(new SelectorItemInfo("entrys.amount"));
		sic.add(new SelectorItemInfo("entrys.payPartAMatlAmt"));
		sic.add(new SelectorItemInfo("entrys.projectPriceInContract"));
		sic.add(new SelectorItemInfo("entrys.parent.id"));
		sic.add(new SelectorItemInfo("entrys.paymentBill.id"));
		sic.add(new SelectorItemInfo("entrys.advance"));
		sic.add(new SelectorItemInfo("entrys.locAdvance"));

		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.displayName"));

		sic.add(new SelectorItemInfo("CU.name"));

		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("creator.name"));

		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));

		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		// 付款申请单增加存储本位币币别，以方便预算系统能取到该字段值 by Cassiel_peng 2009-10-5
		sic.add(new SelectorItemInfo("localCurrency.id"));
		sic.add(new SelectorItemInfo("localCurrency.number"));
		sic.add(new SelectorItemInfo("localCurrency.name"));

		sic.add(new SelectorItemInfo("supplier.number"));
		sic.add(new SelectorItemInfo("supplier.name"));

		sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("realSupplier.name"));

		sic.add(new SelectorItemInfo("settlementType.number"));
		sic.add(new SelectorItemInfo("settlementType.name"));

		sic.add(new SelectorItemInfo("paymentType.number"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		sic.add(new SelectorItemInfo("paymentType.payType.id"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("contractBase.number"));
		sic.add(new SelectorItemInfo("contractBase.name"));

		// 计划项目
		sic.add(new SelectorItemInfo("planHasCon.contract.id"));
		sic.add(new SelectorItemInfo("planHasCon.contractName"));
		sic.add(new SelectorItemInfo("planHasCon.head.deptment.name"));
		sic.add(new SelectorItemInfo("planHasCon.head.year"));
		sic.add(new SelectorItemInfo("planHasCon.head.month"));

		sic.add(new SelectorItemInfo("planUnCon.unConName"));
		sic.add(new SelectorItemInfo("planUnCon.parent.deptment.name"));
		sic.add(new SelectorItemInfo("planUnCon.parent.year"));
		sic.add(new SelectorItemInfo("planUnCon.parent.month"));
		
		sic.add("isBgControl");
		sic.add("applier.*");
		sic.add("applierOrgUnit.*");
		sic.add("applierCompany.*");
		sic.add("costedDept.*");
		sic.add("costedCompany.id");
		sic.add("costedCompany.name");
		sic.add("costedCompany.number");
		sic.add("bgEntry.*");
		sic.add("bgEntry.accountView.*");
		sic.add("bgEntry.expenseType.*");
		sic.add("bgEntry.bgItem.*");
		sic.add("isInvoice");
		sic.add("payBillType.*");
		sic.add("payContentType.*");
		sic.add("person.*");
		return sic;
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk=super._save(ctx, model);
		PaymentBillInfo info=(PaymentBillInfo) model;
		if(info.getFdcPayReqID()!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	        builder.appendSql("update T_CON_PayRequestBill set FHasClosed=? where fid=? ");
	        builder.addParam(0);
	        builder.addParam(info.getFdcPayReqID());
	        builder.executeUpdate();

	        if(info.getCurrency()!=null&&info.getAmount()!=null){
	        	SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("bgEntry.*");
				sic.add("bgEntry.expenseType.*");
				sic.add("bgEntry.accountView.*");
				sic.add("isBgControl");
				sic.add("currency.*");
				sic.add("costedDept.*");
				sic.add("originalAmount");
				sic.add("amount");
				sic.add("exchangeRate");
				sic.add("person.*");
				sic.add("completePrjAmt");
				PayRequestBillInfo payReqBill = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),sic);
				boolean isUpdateAmount=false;
				BigDecimal updateAmount=FDCHelper.ZERO;
				if(payReqBill.isIsBgControl()){
					info.getEntries().clear();
					BigDecimal subAmount=FDCHelper.ZERO;
//					PaymentBillCollection isPay=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection("select * from where fdcPayReqID='"+payReqBill.getId()+"' and id!='"+info.getId()+"' and billstatus=15");
//    				if(isPay.size()==0
//    						&&payReqBill.getCompletePrjAmt()!=null&&payReqBill.getCompletePrjAmt().compareTo(FDCHelper.ZERO)!=0&&payReqBill.getCompletePrjAmt().compareTo(payReqBill.getOriginalAmount())!=0){
//    					subAmount=payReqBill.getCompletePrjAmt().subtract(payReqBill.getOriginalAmount());
//    					isUpdateAmount=true;
//    				}
	    			if(payReqBill.getCurrency().getId().toString().equals(info.getCurrency().getId().toString())
	    					&&payReqBill.getOriginalAmount().compareTo(info.getAmount())!=0){
	    				BigDecimal total=FDCHelper.ZERO;
	    				BigDecimal rate=info.getAmount().divide(FDCHelper.toBigDecimal(payReqBill.getOriginalAmount()),6,BigDecimal.ROUND_HALF_UP);	
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getAmount().add(subAmount).subtract(total);
	    						actAmount=info.getAmount().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}else{
	    				BigDecimal total=FDCHelper.ZERO;
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getAmount().add(subAmount).subtract(total);
	    						actAmount=info.getLocalAmt().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}
	    			PaymentBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
	    			if(isUpdateAmount){
	    				builder = new FDCSQLBuilder(ctx);
	                    builder.appendSql("update T_CAS_PaymentBillentry set famount=?,fLocalAmount=? where fpaymentBillid=? ");
	                    builder.addParam(updateAmount);
	                    builder.addParam(updateAmount);
	                    builder.addParam(info.getId().toString());
	                    builder.executeUpdate();
	    			}
	    		}
			}
		}
		return pk; 
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException{
		IObjectPK pk=super._submit(ctx, model);
		PaymentBillInfo info=(PaymentBillInfo) model;
		if(info.getFdcPayReqID()!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	        builder.appendSql("update T_CON_PayRequestBill set FHasClosed=? where fid=? ");
	        builder.addParam(0);
	        builder.addParam(info.getFdcPayReqID());
	        builder.executeUpdate();
	        
	        if(info.getCurrency()!=null&&info.getAmount()!=null){
	        	SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("bgEntry.*");
				sic.add("bgEntry.expenseType.*");
				sic.add("bgEntry.accountView.*");
				sic.add("isBgControl");
				sic.add("currency.*");
				sic.add("costedDept.*");
				sic.add("originalAmount");
				sic.add("amount");
				sic.add("exchangeRate");
				sic.add("person.*");
				sic.add("completePrjAmt");
				PayRequestBillInfo payReqBill = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),sic);
				boolean isUpdateAmount=false;
				BigDecimal updateAmount=FDCHelper.ZERO;
				if(payReqBill.isIsBgControl()){
					info.getEntries().clear();
					BigDecimal subAmount=FDCHelper.ZERO;
//					PaymentBillCollection isPay=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection("select * from where fdcPayReqID='"+payReqBill.getId()+"' and id!='"+info.getId()+"' and billstatus=15");
//    				if(isPay.size()==0
//    						&&payReqBill.getCompletePrjAmt()!=null&&payReqBill.getCompletePrjAmt().compareTo(FDCHelper.ZERO)!=0&&payReqBill.getCompletePrjAmt().compareTo(payReqBill.getOriginalAmount())!=0){
//    					subAmount=payReqBill.getCompletePrjAmt().subtract(payReqBill.getOriginalAmount());
//    					isUpdateAmount=true;
//    				}
	    			if(payReqBill.getCurrency().getId().toString().equals(info.getCurrency().getId().toString())
	    					&&payReqBill.getOriginalAmount().compareTo(info.getAmount())!=0){
	    				BigDecimal total=FDCHelper.ZERO;
	    				BigDecimal rate=info.getAmount().divide(FDCHelper.toBigDecimal(payReqBill.getOriginalAmount()),6,BigDecimal.ROUND_HALF_UP);	
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getAmount().add(subAmount).subtract(total);
	    						actAmount=info.getAmount().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}else{
	    				BigDecimal total=FDCHelper.ZERO;
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getAmount().add(subAmount).subtract(total);
	    						actAmount=info.getLocalAmt().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}
	    			PaymentBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()),info);
	    			if(isUpdateAmount){
	    				builder = new FDCSQLBuilder(ctx);
	                    builder.appendSql("update T_CAS_PaymentBillentry set famount=?,fLocalAmount=? where fpaymentBillid=? ");
	                    builder.addParam(updateAmount);
	                    builder.addParam(updateAmount);
	                    builder.addParam(info.getId().toString());
	                    builder.executeUpdate();
	    			}
	    			SelectorItemCollection sel=new SelectorItemCollection();
	    			sel.add("company.*");
	    			sel.add("costCenter.*");
	    			sel.add("currency.*");
	    			sel.add("entries.*");
	    			sel.add("entries.currency.*");
	    			sel.add("entries.expenseType.*");
	    			sel.add("entries.costCenter.*");
	    			info=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(info.getId()),sel);
	    			Date now=SysUtil.getAppServerTime(ctx);
            		info.setPayDate(now);
	    			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
	    			Map bgmap=iBgControlFacade.checkBudget(info);
        			if(!((Boolean)bgmap.get("isPass")).booleanValue()){
        				throw new EASBizException(new NumericExceptionSubItem("100",bgmap.get("message").toString()));
        			}
	    		}
			}
		}
		return pk;
	}
}
