package com.kingdee.eas.fdc.basecrm.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASAppException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IPaymentBillType;
import com.kingdee.eas.fi.cas.IReceivingBillType;
import com.kingdee.eas.fi.cas.PaymentBillEntryCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeCollection;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.ReceivingBillTypeCollection;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.app.ArApRecPayServerHelper;
import com.kingdee.eas.fm.nt.NTNumberFormat;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class FDCReceivingBillControllerBean extends AbstractFDCReceivingBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillControllerBean");
    
    
    ArApRecPayServerHelper arapHelper = new ArApRecPayServerHelper();
	
	/**
	 * 收款单提交的通用操作,进行收款单入库操作，并对应收明细进行反写
	 * 需要区分 应收收款;直收收款;预收收款;退款;应收转款;直收转款;预收转款;调整;
	 * 收款模式：
	 * > 应收收款：一定存在收款明细,更新收款明细的实收金额
	 * > 直收收款：可能没有收款明细,如果没有收款明细,则新增一条付款明细,并更新其收款明细的实收金额
	 * > 预收收款：可能没有收款明细,如果没有收款明细,则新增一条付款明细,并更新其收款明细的实收金额
	 * 退款：一定存在收款明细,更新收款明细的已退金额
	 * > 应收退款：对应收明细的退款
	 * > 应退退款：没有应收明细的退款,如负的补差款
	 * 转款模式：转出的一定有收款明细,更新转出收款明细的已转金额
	 * > 应收转款：转入的收款明细与收款模式的反写相同
	 * > 直收转款：转入的收款明细与收款模式的反写相同
	 * > 预收转款：转入的收款明细与收款模式的反写相同
	 * 调整：一定存在收款明细,更新收款明细的已调金额
	 * 
	 * 注意：
	 * 1.(收款或转款)对于提交前没有收款明细,需要提交时同步生成收款明细的,必须将封装的收款明细对象set到收款单分录中,且该收款明细的id要使用createBosID生成一个
	 * 2.收款明细均需要在提交收款单前封装完整,需要特别注意各种标识字段是否有值
	 * 
	 * TODO 对收款单修改,删除的支持
	 * 收款单修改：
	 * 判断传入的收款单是否ID为空来确定是否为修改;删除单独写一个方法
	 * @param oldFdcRev 
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	private String doRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldRev, IRevHandle handle) throws EASBizException, BOSException {
		RevBillTypeEnum revBillType = rev.getRevBillType();
		
		if(oldRev == null){
			doRevOfAddNew(ctx, handle, revBillType, rev);
		}else{
			doRevOfEdit(ctx, handle, revBillType, rev, oldRev);
		}
		
		return _submit(ctx, rev).toString();
	}
	
	/**
	 * 设置审批中状态
	 */
    public void setAudittingStatus(Context ctx, BOSUuid billId)
		throws BOSException, EASBizException {
	    super.setAudittingStatus(ctx, billId);
	    FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		rev.setId(billId);
		rev.setBillStatus(RevBillStatusEnum.AUDITING);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
    }
    
    /**
	 * 设置提交状态
	 */
    public void setSubmitStatus(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	super.setSubmitStatus(ctx, billId);
    	FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		rev.setId(billId);
		rev.setBillStatus(RevBillStatusEnum.SUBMIT);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
    }
    
	//处理新增收款单的提交
	//新方式,对分录进行预处理,待修改 TODO
	private void doRevOfAddNew1(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillInfo rev) throws BOSException, EASBizException {
		Map date = parseRevEntrys(rev);
		
		FDCReceivingBillEntryCollection revEntrys = rev.getEntries();
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			Set revListDeses = date.keySet();
			for(Iterator itor = revListDeses.iterator(); itor.hasNext(); ){
				RevListDes revListDes = (RevListDes) itor.next();
				
				RevListTypeEnum revListType = revListDes.revListType;
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revListDes.revListId, revListDes.revListInfo, revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revListDes.amount);
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));//这个字段名也要用接口暴露出去？看来即使收款明细不合表,抽出收款明细的实体基类也是很有必要的啊,纯粹用接口去处理有点麻烦
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.add(curRefundmentAmount.abs()));
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//转款,还要考虑被红冲部分的纪录
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//这里需要区分普通转款和手续费转款,普通转款反写已转金额,手续费转款反写已划入费用
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			//调整和退款类似
			//TODO
		}
	}
	
	//处理新增收款单的提交
	private void doRevOfAddNew(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillInfo rev) throws BOSException, EASBizException {
		FDCReceivingBillEntryCollection revEntrys = rev.getEntries();
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				//代收的收款明细，不进行反写
				if(revEntry.getOrgUnit() != null){
					continue;
				}
				
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				
				revListInfo.setActRevDate(rev.getBizDate());
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));//这个字段名也要用接口暴露出去？看来即使收款明细不合表,抽出收款明细的实体基类也是很有必要的啊,纯粹用接口去处理有点麻烦
				updateSels.add(handle.getActRevDateFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			//TODO 应收收款和应退退款,反写这里好像不用区分
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.add(curRefundmentAmount.abs()));
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
				revListInfo.setActRevDate(rev.getBizDate());
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				updateSels.add(handle.getActRevDateFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//转款,还要考虑被红冲部分的纪录
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//这里需要区分普通转款和手续费转款,普通转款反写已转金额,手续费转款反写已划入费用
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				if(curRevAmount.compareTo(new BigDecimal(0))<0) {	//收款 或 转款 的调整
					revListInfo.setActRevAmount(srcActRevAmount.add(curRevAmount));
					revListInfo.setActRevDate(rev.getBizDate());				
					
					updateSels.add(handle.getActRevFieldName(revListType));
					updateSels.add(handle.getActRevDateFieldName(revListType));
				}else if(curRevAmount.compareTo(new BigDecimal(0))>0) {	//退款 的调整
					BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
					BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount().negate());
					
					revListInfo.setHasRefundmentAmount(hasRefundmentAmount.add(curRefundmentAmount));
					updateSels.add(handle.getHasRefundmentFieldName(revListType));
				}
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//转款,还要考虑被红冲部分的纪录
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//这里需要区分普通转款和手续费转款,普通转款反写已转金额,手续费转款反写已划入费用
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.add(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		
		}
	}
	
	/**
	 * 收款单修改时的收款明细反写
	 * */
	private void doRevOfEdit(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldRev) throws BOSException, EASBizException {
		Map revDate = parseRevEntrys(rev);
		Map oldRevDate = parseRevEntrys(oldRev);
		
		Set revKeys = revDate.keySet();
		Set oldRevKeys = oldRevDate.keySet();
		for(Iterator itor = revKeys.iterator(); itor.hasNext(); ){
			RevListDes revListDes = (RevListDes) itor.next();
			
			RevListDes oldRevListDes = null;
			for(Iterator oldItor = oldRevKeys.iterator(); oldItor.hasNext(); ){
				RevListDes tmpDes = (RevListDes) oldItor.next();
				if(revListDes.equals(tmpDes)){
					oldRevListDes = tmpDes;
					break;
				}
			}
			if(oldRevListDes == null){
				throw new EASBizException(new NumericExceptionSubItem("100","修改还不支持增删收款明细!"));
			}
			BigDecimal newAmount = getBigDecimal(revListDes.amount);
			BigDecimal oldAmount = getBigDecimal(oldRevListDes.amount);
			
			BigDecimal subAmount = newAmount.subtract(oldAmount);
			if(subAmount.compareTo(FDCHelper.ZERO) == 0){
				continue;
			}
			
			RevListTypeEnum revListType = revListDes.revListType;
			
			ICoreBase bizI = handle.getRevListBizInterface(ctx, revListType);
			IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revListDes.revListId, revListDes.revListInfo, revListType, false);
			//修改时需要把收款单上的业务日期反写到明细上的实收日期上去
			revListInfo.setActRevDate(rev.getBizDate());
			if(RevBillTypeEnum.gathering.equals(revBillType)){
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.add(subAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				updateSels.add(handle.getActRevDateFieldName(revListType));
				
				
				//如果是直收的修改,还要修改对应的应收金额
				if(RevMoneyTypeEnum.DirectRev.equals(revListInfo.getRevMoneyType())){
					revListInfo.setAppAmount(revListInfo.getActRevAmount());
					updateSels.add(handle.getAppRevFieldName(revListType));
					updateSels.add(handle.getActRevDateFieldName(revListType));
				}
				
				bizI.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}else if(RevBillTypeEnum.refundment.equals(revBillType)){
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.subtract(subAmount)); //这里应该减，因为退款是负的
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				updateSels.add(handle.getActRevDateFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}else if(RevBillTypeEnum.transfer.equals(revBillType)){
				throw new EASBizException(new NumericExceptionSubItem("101","转款暂不支持修改!"));
			}else if(RevBillTypeEnum.adjust.equals(revBillType)){
				throw new EASBizException(new NumericExceptionSubItem("101","调整单不支持修改!"));
			}
		}
	}
	
	/**
	 * 删除时对明细的反写
	 * */
	private void doRevOfDelete(Context ctx, IRevHandle handle, RevBillTypeEnum revBillType, FDCReceivingBillEntryCollection revEntrys) throws BOSException, EASBizException {
		if(RevBillTypeEnum.gathering.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				//如果是代收的收款明细，不进行明细的反写
				if(revEntry.getOrgUnit() != null){
					continue;
				}
				
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), null, revListType, false);
				
				if(revListInfo == null){
//					throw new EASBizException(new NumericExceptionSubItem("102","脏数据!"));
					logger.error("脏数据！:" + revEntry.getRevListId());
					continue;
				}
				
				//明细上的剩余金额已不足以回退了
				if(revListInfo.getAllRemainAmount().compareTo(revEntry.getRevAmount())<0){
					String exceptionDes = "对应的"+revListType.getAlias()+ "发生过转款或退款，不再支持原收款的删除！";
					throw new EASBizException(new NumericExceptionSubItem("100", exceptionDes ));
				}
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.subtract(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));//这个字段名也要用接口暴露出去？看来即使收款明细不合表,抽出收款明细的实体基类也是很有必要的啊,纯粹用接口去处理有点麻烦
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), null, revListType, false);
				
				if(revListInfo == null){
//					throw new EASBizException(new NumericExceptionSubItem("102","脏数据!"));
					logger.error("脏数据！:" + revEntry.getRevListId());
					continue;
				}
				
				BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
				BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setHasRefundmentAmount(hasRefundmentAmount.subtract(curRefundmentAmount.abs()));
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getHasRefundmentFieldName(revListType));
				
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
			}
		}else if(RevBillTypeEnum.transfer.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				//明细上的剩余金额已不足以回退了
				if(revListInfo.getAllRemainAmount().compareTo(revEntry.getRevAmount())<0){
					String exceptionDes = "对应的"+revListType.getAlias()+ "发生过转款或退款，不再支持原收款的删除！";
					throw new EASBizException(new NumericExceptionSubItem("100", exceptionDes ));
				}
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				revListInfo.setActRevAmount(srcActRevAmount.subtract(curRevAmount));
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add(handle.getActRevFieldName(revListType));
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//转款,还要考虑被红冲部分的纪录
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//这里需要区分普通转款和手续费转款,普通转款反写已转金额,手续费转款反写已划入费用
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		}else if(RevBillTypeEnum.adjust.equals(revBillType)){
			for(int i=0; i<revEntrys.size(); i++){
				FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
				RevListTypeEnum revListType = revEntry.getRevListType();
				ICoreBase bizInterface = handle.getRevListBizInterface(ctx, revListType);
				IRevListInfo revListInfo = regetRevListInfo(ctx, handle, revEntry.getRevListId(), revEntry.getRevListInfo(), revListType, true);
				
				BigDecimal srcActRevAmount = getBigDecimal(revListInfo.getActRevAmount());
				BigDecimal curRevAmount = getBigDecimal(revEntry.getRevAmount());
				
				SelectorItemCollection updateSels = new SelectorItemCollection();
				if(curRevAmount.compareTo(new BigDecimal(0))<0) {	//收款 或 转款 的调整
					revListInfo.setActRevAmount(srcActRevAmount.subtract(curRevAmount));					
					updateSels.add(handle.getActRevFieldName(revListType));
				}else if(curRevAmount.compareTo(new BigDecimal(0))>0) {	//退款 的调整
					BigDecimal hasRefundmentAmount = getBigDecimal(revListInfo.getHasRefundmentAmount());
					BigDecimal curRefundmentAmount = getBigDecimal(revEntry.getRevAmount().negate());
					
					revListInfo.setHasRefundmentAmount(hasRefundmentAmount.subtract(curRefundmentAmount));
					updateSels.add(handle.getHasRefundmentFieldName(revListType));
				}
				bizInterface.updatePartial((CoreBaseInfo) revListInfo, updateSels);
				
				FeeMoneyTypeEnum feeMoneyType = revListInfo.getFeeMoneyType();
				
				//转款,还要考虑被红冲部分的纪录
				TransferSourceEntryCollection srcEntrys = revEntry.getSourceEntries();
				for(int j=0; j<srcEntrys.size(); j++){
					TransferSourceEntryInfo srcEntry = srcEntrys.get(j);
					String tmpRevListId = srcEntry.getFromRevListId();
					RevListTypeEnum tmpRevListType = srcEntry.getFromRevListType();
					
					SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
					
					IRevListInfo tmpRevListInfo = handle.getRevListInfoObj(ctx, tmpRevListId, tmpRevListType);
					BigDecimal curTransferredAmount = getBigDecimal(srcEntry.getAmount());
					
					//这里需要区分普通转款和手续费转款,普通转款反写已转金额,手续费转款反写已划入费用
					if(FeeMoneyTypeEnum.Fee.equals(feeMoneyType)){
						BigDecimal hasToFeeAmount = getBigDecimal(tmpRevListInfo.getHasToFeeAmount());
						tmpRevListInfo.setHasToFeeAmount(hasToFeeAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasToFeeFieldName(revListType));
					}else{
						BigDecimal hasTransferredAmount = getBigDecimal(tmpRevListInfo.getHasTransferredAmount());
						tmpRevListInfo.setHasTransferredAmount(hasTransferredAmount.subtract(curTransferredAmount));
						
						tmpUpdateSels.add(handle.getHasTransferredFieldName(revListType));
					}
					
					ICoreBase tmpBizInterface = handle.getRevListBizInterface(ctx, tmpRevListType);
					tmpBizInterface.updatePartial((CoreBaseInfo) tmpRevListInfo, tmpUpdateSels);
				}				
			}
		
		
			
			
		}
	}
	
	private Map parseRevEntrys(FDCReceivingBillInfo rev) {
		if(rev == null){
			return null;
		}
		Map map = new LinkedHashMap();
		FDCReceivingBillEntryCollection revEntrys = rev.getEntries();
		for(int i=0; i<revEntrys.size(); i++){
			FDCReceivingBillEntryInfo revEntry = revEntrys.get(i);
			//代收的收款明细，不进行反写
			if(revEntry.getOrgUnit() != null){
				continue;
			}
			
			String revListId = revEntry.getRevListId();
			RevListTypeEnum revListType = revEntry.getRevListType();
			BigDecimal amount = revEntry.getRevAmount();
			
			List list = (List) map.get(new RevListDes(revListId));
			
			if(list == null){
				list = new ArrayList();
				list.add(revEntry);
				
				RevListDes revListDes = new RevListDes();
				revListDes.revListId = revListId;
				revListDes.revListType = revListType;
				revListDes.revListInfo = revEntry.getRevListInfo();
				revListDes.amount = revEntry.getRevAmount();
				
				map.put(revListDes, list);
			}else{
				Set keys = map.keySet();
				
				for(Iterator itor = keys.iterator(); itor.hasNext(); ){
					RevListDes des = (RevListDes) itor.next();
					if(revListId.equals(des.revListId)){
						des.amount = des.amount.add(amount);
						break;
					}
				}
				list.add(revEntry);
			}
		}
		
		return map;
	}

	/**
	 * 重新获取一下收款明细的值,因为这个收款明细是客户端传过来的,需要以库中的数据为准
	 * 对于库中没有这条收款明细记录的,将此条明细新增至库中
	 * */
	private IRevListInfo regetRevListInfo(Context ctx, IRevHandle handle, String revListId, IRevListInfo revListInfo, RevListTypeEnum revListType, boolean isNeedAddNew) throws EASBizException, BOSException {
		IRevListInfo tmp = handle.getRevListInfoObj(ctx, revListId, revListType);
		//TODO 新增这里需要增加更多的判断，防止存在的脏数据会导致这里更严重的错误
		if(isNeedAddNew  &&  tmp == null  &&  revListInfo != null){
			addnewRevListInfo(revListInfo, revListInfo.getRevMoneyType(), handle.getRevListBizInterface(ctx, revListType));
			return revListInfo;
		}
		return tmp;
	}
	
	/**
	 * 重新获取一下收款明细的值,因为这个收款明细是客户端传过来的,需要以库中的数据为准
	 * 对于库中没有这条收款明细记录的,将此条明细新增至库中
	 * @deprecated
	 * */
//	private IRevListInfo regetRevListInfo(Context ctx, IRevHandle handle, IRevListInfo revListInfo, RevListTypeEnum revListType) throws EASBizException, BOSException {
//		String revListId = null;
//		if(revListInfo.getId() != null){
//			revListId = revListInfo.getId().toString();
//		}else{
//			logger.error("逻辑错误,对于直收收款时才生成收款明细的,也需要create一个ID");
//		}
//		
//		return this.regetRevListInfo(ctx, handle, revListId, revListInfo, revListType);
//	}
	
	/**
	 * 如果是要同步新增收款明细的,则将收款明细新增入库
	 * @param revListInfo 收款明细对象
	 * @param revMoneyType 款项表示,区分预收,直收,应收
	 * @param bizInterface 对应收款明细的业务接口
	 * */
	private void addnewRevListInfo(IRevListInfo revListInfo, RevMoneyTypeEnum revMoneyType, ICoreBase bizInterface) throws BOSException, EASBizException {
		//需要收款的时候才生成收款明细的,只有可能是预收收款和直收收款,这里可以顺便校验一下
/*		if(!RevMoneyTypeEnum.PreRev.equals(revMoneyType)  &&  
				!RevMoneyTypeEnum.DirectRev.equals(revMoneyType)){
			throw new BOSException("系统逻辑错误哈.");
		}*/		
		bizInterface.addnew((CoreBaseInfo) revListInfo);
		
		//此ID在客户端就应该已经设置了
//			revEntry.setRevListId(resId.toString());
//			revListInfo.setId(BOSUuid.read(resId.toString()));
	}

	private BigDecimal getBigDecimal(BigDecimal big){
		return CRMHelper.getBigDecimal(big);		
	}
	
	protected String _submitRev(Context ctx, IObjectValue rev, String handleClazzName) throws BOSException, EASBizException {
		FDCReceivingBillInfo fdcRev = (FDCReceivingBillInfo) rev;
		IRevHandle handle = null;
		
		if(fdcRev.getReceipt()!=null ||!StringUtils.isEmpty(fdcRev.getReceiptNumber())){
			fdcRev.setReceiptState(ReceiptStatusEnum.HasMake);
		}
		if(!StringUtils.isEmpty(handleClazzName)){
			try {
				handle = (IRevHandle) Class.forName(handleClazzName).newInstance();
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
			}
		}
		
		if(handle == null){
			throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
		}
		
		FDCReceivingBillInfo oldFdcRev = null;
		if(fdcRev != null  &&  fdcRev.getId() != null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("entries.*");
			oldFdcRev = getFDCReceivingBillInfo(ctx, new ObjectUuidPK(fdcRev.getId().toString()), sels);
		}
//		//如果收据号是F7 就要反写此所收据所在的票据管理所定义的收据状态  xin_wang 2010.09.22
//		if(fdcRev.getReceipt()!=null){
//			SelectorItemCollection sels = new SelectorItemCollection();
//			sels.add("status");
//			ChequeInfo ci =fdcRev.getReceipt();
//			ci.setStatus(ChequeStatusEnum.WrittenOff);
//			ChequeFactory.getLocalInstance(ctx).updatePartial(ci, sels);
//		}
		/* 
		 * fdcRev不为空，oldFdcRev为空，表示新增；
		   fdcRev不为空，oldFdcRev不为空，表示修改；
		 */
		handle.doBeforeRev(ctx, fdcRev, oldFdcRev);
		String id = doRev(ctx, fdcRev, oldFdcRev, handle);
		handle.doAfterRev(ctx, fdcRev, oldFdcRev);
	    
		return id;
	}
	
	public DAPTransformResult generateVoucher(Context ctx, IObjectPK[] sourceBillPkList, IObjectPK botMappingPK, SelectorItemCollection botpSelectors) throws BOSException, EASBizException {
		botpSelectors = getBOTPSelectors();
		
		return super.generateVoucher(ctx, sourceBillPkList, botMappingPK, botpSelectors);
	}
	
	
	protected SelectorItemCollection getBOTPSelectors() {
        SelectorItemCollection sels = new SelectorItemCollection();
		
		sels.add("entries.settlementType.*");
		sels.add("entries.revAccount.*");
		sels.add("entries.oppAccount.*");
		sels.add("entries.moneyDefine.*");
		sels.add("entries.revAccountBank.*");
		sels.add("entries.room.*");
		sels.add("entries.room.building.*");
		sels.add("entries.sourceEntries.*");

		sels.add("purchaseObj.*");
		sels.add("sincerityObj.*");
		sels.add("obligateObj.*");
		sels.add("tenancyObj.*");
		sels.add("fdcCustomers.*");
		sels.add("customer.*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("currency.*");
		sels.add("company.*");
		sels.add("*");		
        
		return sels;
	}
	
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
	}	
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
//		FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("room.*");
		selector.add("entries.*");
		selector.add("customer.*");
		selector.add("fdcCustomers.*");
		selector.add("receipt.*");
		selector.add("*");
		FDCReceivingBillInfo rev =FDCReceivingBillFactory.getLocalInstance(ctx).getFDCReceivingBillInfo(new ObjectUuidPK(billId), selector);
//		rev.setId(billId);
		
		if(rev.getReceipt()!=null){//收款单关联了收据的需要反写 xin_wang 2010.9.25
			
			ChequeInfo cheque =  new ChequeInfo();
			cheque.setId(rev.getReceipt().getId());
			StringBuffer sb = new StringBuffer();
			sb.append(rev.getRoom().getNumber());
			sb.append("，房款");
//			
			cheque.setResume(sb.toString());
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			BigDecimal invoiceAmount = new BigDecimal("0");
	
			//收款金额 是收款单上的金额
			invoiceAmount = rev.getAmount();
			
			Format u = NTNumberFormat.getInstance("rmb");
			cheque.setCapitalization(u.format(invoiceAmount));    		
			cheque.setAmount(invoiceAmount);
			
			String payerStrs = "";
			Date lastPayDate = null;

				if(rev.getCustomer()!=null) {
						payerStrs = rev.getCustomer().getName();
				}
				if(rev.getBizDate()!=null) {
					if(lastPayDate==null || rev.getBizDate().after(lastPayDate))
						lastPayDate = rev.getBizDate();	
				}
//			}    		
			//如果内容大于表字段最大长度300,进行截取
//			String des = writtenOfCheque.toString();
//			if(des.length() > 300) 	des = des.substring(0, 300);
//			cheque.setDescription(des);
			
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			cheque.setPayer(payerStrs.replaceFirst(",", ""));
			if(lastPayDate==null) {
				cheque.setPayTime(FDCCommonServerHelper.getServerTimeStamp());
			}else{
				cheque.setPayTime(new Timestamp(lastPayDate.getTime()));
			}
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("resume");
			sels.add("status");
			sels.add("amount");
			sels.add("description");
			sels.add("capitalization");    		
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			sels.add("payer");
			sels.add("payTime");
			ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);	//更新本对应的收据信息  
			//在票据管理没有此票据的就增加一条数据invoice，有了就UPdate  
			InvoiceInfo ii = new InvoiceInfo();
//			ii.setId()
			ii.setCheque(cheque);
			ii.setAmount(invoiceAmount);
			//fdcCustomers 交费客户，customer 收款客户  到底写那个客户到收据上了 交费客户写在收据上！
			FDCCustomerCollection custColl = FDCCustomerFactory.getLocalInstance(ctx)
			.getFDCCustomerCollection("select name,number where sysCustomer.id='"+rev.getCustomer().getId()+"'");
			if(custColl.size()>0)
				ii.setCustomer(custColl.get(0));
			ii.setDate(new Date());
			ii.setRoom(rev.getRoom());
			ii.setUser(ContextUtil.getCurrentUserInfo(ctx));
			ii.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
			ii.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			ii.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
			ii.setNumber(rev.getReceipt().getNumber());
			FilterInfo filter = new FilterInfo();
			ii.setChequeType(ChequeTypeEnum.receipt);
			filter.getFilterItems().add(new FilterItemInfo("cheque.id",rev.getReceipt().getId()));
			SelectorItemCollection coll  = new SelectorItemCollection();
//			coll.add("amount");
//			coll.add("date");
			coll.add("user.*");
			coll.add("room.*");
//			coll.add("CU");
			coll.add("cheque.*");
			coll.add("customer.*");
			coll.add("*");
			IReceiptInvoiceFacade facade = null;
			try {
				facade = ReceiptInvoiceFacadeFactory.getLocalInstance(ctx);
			} catch (BOSException e) {
//				ExceptionHandler.handle(e);
			}
			if(InvoiceFactory.getLocalInstance(ctx).exists(filter)){
				InvoiceInfo invoice =InvoiceFactory.getLocalInstance(ctx).getInvoiceInfo("select id where cheque.id ='"+rev.getReceipt().getId()+"'" );
				ii.setId(invoice.getId());
				InvoiceFactory.getLocalInstance(ctx).updatePartial(ii, coll);
				//update 票据的操作记录
				
			}else{
				ii.setId(BOSUuid.create(ii.getBOSType()));
				InvoiceFactory.getLocalInstance(ctx).addnew(ii);
				//票据上的操作记录
				StringBuffer content = new StringBuffer();
				content.append("对收款单:");
				content.append(rev.getNumber());
				content.append(" 开收据");
				RecordTypeEnum recordType = RecordTypeEnum.MakeOutInvoice;
				if (recordType != null) {
					try {
					facade.updateRecord(1, ii.getId().toString(),	recordType, content.toString(), null);
					} catch (BOSException e) {
//						ExceptionHandler.handle(e);
					}
				}	
			}
		}
		rev.setBillStatus(RevBillStatusEnum.AUDITED);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
		
		ArrayList list=new ArrayList();
		list.add(billId.toString());
		this.createCashBill(ctx, list);
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		
		if(ReceivingBillFactory.getLocalInstance(ctx).exists("select id from where sourceBillId='"+billId+"'")
				||PaymentBillFactory.getLocalInstance(ctx).exists("select id from where sourceBillId='"+billId+"'")){
			throw new EASBizException(new NumericExceptionSubItem("101","已生成出纳收付款单，请先删除出纳收付款单后再进行反审批操作！"));
		}
		FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
		rev.setId(billId);
		rev.setBillStatus(RevBillStatusEnum.SUBMIT);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("billStatus");
		this.updatePartial(ctx, rev, sels);
	}

	protected void _delete(Context ctx, BOSUuid fdcReceivingID,
			String handleClazzName) throws BOSException ,EASBizException{
		//
		IRevHandle handle = null;
		if(!StringUtils.isEmpty(handleClazzName)){
			try {
				handle = (IRevHandle) Class.forName(handleClazzName).newInstance();
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
			}
		}
		
		if(handle == null){
			throw new EASBizException(new NumericExceptionSubItem("101","系统错误！"));
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("entries.*");
		sels.add("entries.sourceEntries.*");
		FDCReceivingBillInfo rev = getFDCReceivingBillInfo(ctx, new ObjectUuidPK(fdcReceivingID), sels);
		
		doRevOfDelete(ctx, handle, rev.getRevBillType(), rev.getEntries());
		
		//删除需要考虑对业务系统的反写,需要实现
		handle.doOfDelRev(ctx, rev);
		
		this._delete(ctx, new ObjectUuidPK(fdcReceivingID));
	}

	protected void _receive(Context ctx, ArrayList recidList)
			throws BOSException, EASBizException {
		for(int i=0;i<recidList.size();i++)
		{
			FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
			rev.setId(BOSUuid.read((String)recidList.get(i)));
			rev.setBillStatus(RevBillStatusEnum.RECED);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("billStatus");
			this.updatePartial(ctx, rev, sels);	
			FDCReceivingBillFactory.getLocalInstance(ctx).receive(rev.getId());
		}			
	}
	

	protected void _canceReceive(Context ctx, ArrayList recidList)
			throws BOSException, EASBizException {
		for(int i=0;i<recidList.size();i++)
		{
			FDCReceivingBillInfo rev = new FDCReceivingBillInfo();
			rev.setId(BOSUuid.read((String)recidList.get(i)));
			rev.setBillStatus(RevBillStatusEnum.AUDITED);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("billStatus");
			this.updatePartial(ctx, rev, sels);	
		}
	}

	//生成调整单
	protected Map _adjust(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FDCReceivingBillInfo revInfo = this.getFDCReceivingBillInfo(ctx, "select *,entries.*,fdcCustomers.*,entries.sourceEntries.* " +
				" where id ='"+billId.toString()+"'");
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.adjust))
			throw new EASBizException(new NumericExceptionSubItem("100","不允许对调整单做调整！"));
		
		if(!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED) && 
				!revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
			throw new EASBizException(new NumericExceptionSubItem("100","只有已审批或已付款状态的单据能做调整！"));
		}
		
		//当选择的收款单关联的认购单已作废，不允许进行调整
		if(revInfo.getRevBizType()!=null && revInfo.getRevBizType().equals(RevBizTypeEnum.purchase) && revInfo.getPurchaseObj()!=null) {
			if(PurchaseFactory.getLocalInstance(ctx).exists("where id='"+ revInfo.getPurchaseObj().getId() +"' " +
					"and (purchaseState = '"+ PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE +"' " +
							"or purchaseState = '"+ PurchaseStateEnum.QUITROOMBLANKOUT_VALUE +"' " +
									"or purchaseState = '"+ PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"' ) ")) {
				throw new EASBizException(new NumericExceptionSubItem("100","关联的认购单已作废，不允许进行调整！"));
			}
		}
		
		FDCReceivingBillInfo adjustInfo = (FDCReceivingBillInfo)revInfo.clone();
		//设置编码		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(adjustInfo, orgUnit.getId().toString());
		if(retNumber==null || retNumber.trim().length()==0 )
			throw new EASBizException(new NumericExceptionSubItem("100","未启用编码规则 ，不能自动生成转款单！"));
		
		adjustInfo.setId(null);
		adjustInfo.setNumber(retNumber);
		adjustInfo.setRevBillType(RevBillTypeEnum.adjust);
		adjustInfo.setBizDate(new Date());
		adjustInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setAmount(adjustInfo.getAmount().negate());
		adjustInfo.setOriginalAmount(adjustInfo.getOriginalAmount().negate());
		adjustInfo.setBillStatus(RevBillStatusEnum.SUBMIT);
		adjustInfo.setState(FDCBillStateEnum.SUBMITTED);
		adjustInfo.setFiVouchered(false);	//add by zhiyuan_tang R101103-407   新生成的调整单都是未生成凭证的
		adjustInfo.setDescription("针对"+revInfo.getRevBillType().getAlias()+"-"+revInfo.getRevBizType().getAlias()+"，编号"+revInfo.getNumber()+"的调整");
		
		
		FDCReceivingBillEntryCollection adjustEntryColl = adjustInfo.getEntries();;
		for(int i=0;i<adjustEntryColl.size();i++) {
			FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
			adjustEntryInfo.setId(BOSUuid.create(adjustEntryInfo.getBOSType()));
			adjustEntryInfo.setHead(adjustInfo);
			adjustEntryInfo.setRevAmount(adjustEntryInfo.getRevAmount().negate());
			TransferSourceEntryCollection trasfEntryColl = adjustEntryInfo.getSourceEntries();
			for(int j=0;j<trasfEntryColl.size();j++){
				TransferSourceEntryInfo trasfEntryInfo = trasfEntryColl.get(j);
				trasfEntryInfo.setId(null);
				trasfEntryInfo.setHeadEntry(adjustEntryInfo);
				trasfEntryInfo.setAmount(trasfEntryInfo.getAmount().negate());
			}
		}
		
		RevFDCCustomerEntryCollection fdcCustColl =  adjustInfo.getFdcCustomers();
		for(int i=0;i<fdcCustColl.size();i++) {
			RevFDCCustomerEntryInfo fdcCustInfo = fdcCustColl.get(i);
			fdcCustInfo.setId(null);
			fdcCustInfo.setHead(adjustInfo);
		}
		
		String handleClazzName = null;
		handleClazzName  = CRMHelper.getHandleClassNameByRevBizType(adjustInfo.getRevBizType());
		
		//当收款或转款的单据做调整时，要验证调整后的明细的剩余金额不能小于0
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.gathering) || revInfo.getRevBillType().equals(RevBillTypeEnum.transfer))	{
			try {
				IRevHandle revHandle = (IRevHandle) Class.forName(handleClazzName).newInstance();
				
				for(int i=0;i<adjustEntryColl.size();i++) {
					FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
					IRevListInfo revListInfo = revHandle.getRevListInfoObj(ctx, adjustEntryInfo.getRevListId(), adjustEntryInfo.getRevListType());
					BigDecimal retLeftAmount = revListInfo.getAllRemainAmount().add(adjustEntryInfo.getRevAmount());
					if(retLeftAmount.compareTo(new BigDecimal(0))<0) {
						throw new EASBizException(new NumericExceptionSubItem("100","禁止调整！因为调整后会导致'"+adjustEntryInfo.getRevListType().getAlias()+"'的剩余金额为负值！" ));
					}
				}
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","异常错误！InstantiationException！" ));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","异常错误！IllegalAccessException！" ));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","异常错误！ClassNotFoundException！" ));
			}

		}		
		
		
		String id = this._submitRev(ctx, adjustInfo ,handleClazzName);
		Map reMap = new HashMap();
		reMap.put("adjustInfo", adjustInfo);
		return reMap;
	}

	/**
	 * 增加售楼收款单生成出纳收款单
	 * by renliang
	 */
	protected void _createCashBill(Context ctx, ArrayList idList, boolean isCreate) throws BOSException, EASAppException {
		
		try {
			createCashBill(ctx,idList);
		} catch (EASBizException e) {
			logger.error(e.getMessage()+"生成出纳收付款单出错！");
		}
		
	}		
	
	/**
	 * 生成出纳收款单
	 * @param ctx
	 * @param idList
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void createCashBill(Context ctx, ArrayList idList) throws BOSException, EASBizException{
		
		IFDCReceivingBill receivingBill= FDCReceivingBillFactory.getLocalInstance(ctx);
		Set  idSet = new HashSet(); 
		for (int i = 0; i < idList.size(); i++){
			String id = idList.get(i).toString();
			idSet.add(id);
		}
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		evi.setFilter(filterInfo);

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("*"));
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("company.*"));
		coll.add(new SelectorItemInfo("bizDate"));
		coll.add(new SelectorItemInfo("exchangeRate"));
		coll.add(new SelectorItemInfo("currency.*"));
		coll.add(new SelectorItemInfo("amount"));
		coll.add(new SelectorItemInfo("originalAmount"));
		
		coll.add(new SelectorItemInfo("entries.*"));
//		coll.add(new SelectorItemInfo("entries.settlementType.*"));
//		coll.add(new SelectorItemInfo("entries.revAccount.*"));
		coll.add(new SelectorItemInfo("entries.revAmount"));
		coll.add(new SelectorItemInfo("entries.oppAccount.*"));
		coll.add(new SelectorItemInfo("entries.moneyDefine.*"));
//		coll.add(new SelectorItemInfo("entries.moneyDefine.revBillType.*"));
		coll.add(new SelectorItemInfo("customer.id"));
		coll.add(new SelectorItemInfo("customer.number"));
		coll.add(new SelectorItemInfo("customer.name"));
		
		coll.add(new SelectorItemInfo("revAccount.*"));
		coll.add(new SelectorItemInfo("accountBank.*"));
		coll.add(new SelectorItemInfo("settlementType.*"));
		coll.add(new SelectorItemInfo("settlementNumber"));
		coll.add(new SelectorItemInfo("bank.*"));
		
		evi.setSelector(coll);

		
		FDCReceivingBillCollection collection = receivingBill.getFDCReceivingBillCollection(evi);
		
		
		Set updateIdSet = new HashSet();
		
		
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		CtrlUnitInfo cuInfo = ContextUtil.getCurrentCtrlUnit(ctx);

		IORMappingDAO iReceiving = ORMappingDAO.getInstance(new ReceivingBillInfo().getBOSType(), ctx, getConnection(ctx));
		IORMappingDAO iPay = ORMappingDAO.getInstance(new PaymentBillInfo().getBOSType(), ctx, getConnection(ctx));
		
		for (int i = 0; i < collection.size(); i++) {
			FDCReceivingBillCollection billColl = null;
			String id = collection.get(i).getId().toString();
			billColl=collection;
			if(billColl!=null && billColl.size()>0){
				for (int j = 0; j < billColl.size(); j++) {
					FDCReceivingBillInfo fdcReceivingBillInfo = billColl.get(j);
					FDCReceivingBillEntryCollection billEntry = fdcReceivingBillInfo.getEntries();
					if(fdcReceivingBillInfo.getRevBillType().equals(RevBillTypeEnum.refundment)){
						PaymentBillTypeInfo payemtnBillTypeInfo = getPaymentBillType(ctx);
						
						PaymentBillInfo  paymentBillInfo = new PaymentBillInfo();
						//公司
						paymentBillInfo.setCompany(fdcReceivingBillInfo.getCompany());
						//业务日期
						paymentBillInfo.setBizDate(FDCDateHelper.getDayBegin(fdcReceivingBillInfo.getBizDate()));
						//汇率
						paymentBillInfo.setExchangeRate(fdcReceivingBillInfo.getExchangeRate());
						//币别
						paymentBillInfo.setCurrency(fdcReceivingBillInfo.getCurrency());
						
						//收款金额
						paymentBillInfo.setActPayAmt(fdcReceivingBillInfo.getAmount().abs());
						
						paymentBillInfo.setCreator(userInfo);
						paymentBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						paymentBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
						paymentBillInfo.setLastUpdateUser(userInfo);
						paymentBillInfo.setCU(cuInfo);
						
					
						/**
						 * 设置编码		
						 */
						ICodingRuleManager iCodingRuleManager = null;
						if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
						else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
						
						OrgUnitInfo orgUnit = null;
						if(ctx!=null) {
							orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
							if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
						}else {
							orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
							if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
						}
						
						String retNumber = iCodingRuleManager.getNumber(paymentBillInfo, orgUnit.getId().toString());
						if(retNumber!=null && !"".equals(retNumber)){
							paymentBillInfo.setNumber(retNumber);
						}
						
						/**
						 * 添加收款人
						 */
						CustomerInfo custInfo  = fdcReceivingBillInfo.getCustomer();
						if(custInfo!=null){
							paymentBillInfo.setPayeeID(custInfo.getId().toString());
							paymentBillInfo.setPayeeNumber(custInfo.getNumber());
							paymentBillInfo.setPayeeName(custInfo.getName());
						}
						
						paymentBillInfo.setActPayLocAmt(fdcReceivingBillInfo.getOriginalAmount());
						
						//收款科目
						paymentBillInfo.setPayerAccount(fdcReceivingBillInfo.getRevAccount());	
						
						// 检查科目合法性
						try {
							arapHelper.verifyAccountView(ctx, fdcReceivingBillInfo.getRevAccount(), paymentBillInfo
									.getCurrency(), paymentBillInfo.getCompany());
						} catch (EASBizException e) {
							logger.error(e.getMessage()+"科目不按指定币别核算，请选择其它币别！");
							throw new BOSException("科目不按指定币别核算，请选择其它币别！");
						}
						paymentBillInfo.setPayerAccountBank(fdcReceivingBillInfo.getAccountBank());	
						
						//结算方式
						paymentBillInfo.setSettlementType(fdcReceivingBillInfo.getSettlementType());
						//结算号
						paymentBillInfo.setSettlementNumber(fdcReceivingBillInfo.getSettlementNumber());
						paymentBillInfo.setPayerBank(fdcReceivingBillInfo.getBank());
						
						paymentBillInfo.setPayBillType(payemtnBillTypeInfo);
						/**
						 * 设置非空字段
						 */
						paymentBillInfo.setPaymentBillType(CasRecPayBillTypeEnum.RealType);
						paymentBillInfo.setIsExchanged(false);
						paymentBillInfo.setIsInitializeBill(false);
						paymentBillInfo.setIsImport(false);
						paymentBillInfo.setFiVouchered(false);
						paymentBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
						paymentBillInfo.setIsAppointVoucher(false);
						paymentBillInfo.setIsCoopBuild(false);
						paymentBillInfo.setSourceType(SourceTypeEnum.CASH);
						//单据状态
						paymentBillInfo.setBillStatus(BillStatusEnum.SAVE);
						//原始单据id
						paymentBillInfo.setSourceBillId(id);
						
						for (int k = 0; k < billEntry.size(); k++) {
							/**
							 * 以下是分录内容
							 */
							PaymentBillEntryCollection payBillEntry = paymentBillInfo.getEntries();
							PaymentBillEntryInfo payBillEntryInfo = new PaymentBillEntryInfo();
							//收款金额
							payBillEntryInfo.setActualAmt(billEntry.get(k).getRevAmount().abs());
							//对方科目
							if(billEntry.get(i).getOppAccount()!=null){
								payBillEntryInfo.setOppAccount(billEntry.get(k).getOppAccount());	
							}
							
							payBillEntry.add(payBillEntryInfo);
						}
						iPay.addNewBatch(paymentBillInfo);
					}else{
						ReceivingBillTypeInfo receivingBillTypeInfo = getReceivingBillType(ctx);
						
						ReceivingBillInfo  receivingBillInfo = new ReceivingBillInfo();
						//公司
						receivingBillInfo.setCompany(fdcReceivingBillInfo.getCompany());
						//业务日期
						receivingBillInfo.setBizDate(FDCDateHelper.getDayBegin(fdcReceivingBillInfo.getBizDate()));
						//汇率
						receivingBillInfo.setExchangeRate(fdcReceivingBillInfo.getExchangeRate());
						//币别
						receivingBillInfo.setCurrency(fdcReceivingBillInfo.getCurrency());
						
						//收款金额
						receivingBillInfo.setActRecAmt(fdcReceivingBillInfo.getAmount());
						
						receivingBillInfo.setCreator(userInfo);
						receivingBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						receivingBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
						receivingBillInfo.setLastUpdateUser(userInfo);
						receivingBillInfo.setCU(cuInfo);
						
					
						/**
						 * 设置编码		
						 */
						ICodingRuleManager iCodingRuleManager = null;
						if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
						else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
						
						OrgUnitInfo orgUnit = null;
						if(ctx!=null) {
							orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
							if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
						}else {
							orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
							if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
						}
						
						String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
						
						
						///if(retNumber==null || retNumber.trim().length()==0 )
							//throw new EASBizException(new NumericExceptionSubItem("100","未启用编码规则 ，不能自动生成转款单！"));
						
						if(retNumber!=null && !"".equals(retNumber)){
							receivingBillInfo.setNumber(retNumber);
						}
						
						/**
						 * 添加收款人
						 */
						CustomerInfo custInfo  = fdcReceivingBillInfo.getCustomer();
						if(custInfo!=null){
							receivingBillInfo.setPayerID(custInfo.getId().toString());
							receivingBillInfo.setPayerNumber(custInfo.getNumber());
							receivingBillInfo.setPayerName(custInfo.getName());
						}
//						//折本位币
//						BigDecimal actrecLocAmt = FDCHelper.ZERO;
//						BigDecimal ecchangeRate = FDCHelper.ONE;
						
//						if(fdcReceivingBillInfo.getExchangeRate()==null){
//							ecchangeRate = fdcReceivingBillInfo.getExchangeRate();
//						}
//						
//						actrecLocAmt  = FDCHelper.multiply(billEntry.get(k).getRevAmount(), ecchangeRate);
						
						receivingBillInfo.setActRecLocAmt(fdcReceivingBillInfo.getOriginalAmount());
						
						//收款科目
							receivingBillInfo.setPayeeAccount(fdcReceivingBillInfo.getRevAccount());	
						
						// 检查科目合法性
						try {
							arapHelper.verifyAccountView(ctx, fdcReceivingBillInfo.getRevAccount(), receivingBillInfo
									.getCurrency(), receivingBillInfo.getCompany());
						} catch (EASBizException e) {
							logger.error(e.getMessage()+"科目不按指定币别核算，请选择其它币别！");
							throw new BOSException("科目不按指定币别核算，请选择其它币别！");
						}
						
						//收款账户
//						if(billEntry.get(i).getRevAccountBank()!=null){
							receivingBillInfo.setPayeeAccountBank(fdcReceivingBillInfo.getAccountBank());	
//						}
						
						//结算方式
						receivingBillInfo.setSettlementType(fdcReceivingBillInfo.getSettlementType());
						//结算号
						receivingBillInfo.setSettlementNumber(fdcReceivingBillInfo.getSettlementNumber());
						receivingBillInfo.setPayeeBank(fdcReceivingBillInfo.getBank());
						
//						MoneyDefineInfo moneyDefineInfo  = billEntry.get(k).getMoneyDefine();
						
//						/**
//						 * 新增加收款类型的转换
//						 */
//						if(moneyDefineInfo!=null){
//							//收款类型
//							receivingBillInfo.setRecBillType(moneyDefineInfo.getRevBillType());
//						}else{
							//收款类型
							receivingBillInfo.setRecBillType(receivingBillTypeInfo);
//						}
						/**
						 * 设置非空字段
						 */
						receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.RealType);
						receivingBillInfo.setIsExchanged(false);
						receivingBillInfo.setIsInitializeBill(false);
						receivingBillInfo.setIsImport(false);
						receivingBillInfo.setFiVouchered(false);
						receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
						receivingBillInfo.setIsAppointVoucher(false);
						receivingBillInfo.setIsCoopBuild(false);
						receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
						//单据状态
						receivingBillInfo.setBillStatus(BillStatusEnum.SAVE);
						//原始单据id
						receivingBillInfo.setSourceBillId(id);
						
						for (int k = 0; k < billEntry.size(); k++) {
							/**
							 * 以下是分录内容
							 */
							ReceivingBillEntryCollection receBillEntry = receivingBillInfo.getEntries();
							ReceivingBillEntryInfo receBillEntryInfo = new ReceivingBillEntryInfo();
							//收款金额
							receBillEntryInfo.setActualAmt(billEntry.get(k).getRevAmount());
							//对方科目
							if(billEntry.get(i).getOppAccount()!=null){
								receBillEntryInfo.setOppAccount(billEntry.get(k).getOppAccount());	
							}
							
							receBillEntry.add(receBillEntryInfo);
						}
						iReceiving.addNewBatch(receivingBillInfo);
					}
				}
			}
			updateIdSet.add(id);
		}
		
		/**
		 * 跟新是否已生成出纳收款单字段
		 */
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_BDC_FDCReceivingBill set FIsCreateBill=1 where ");
		builder.appendParam("fid", updateIdSet.toArray());
		
		iReceiving.executeBatch();
		builder.execute();
	}
	
	/**
	 * 判断是否有重复的值，如果有的话
	 * 把所有重复的删除，然后删除
	 * @param collection
	 * @return
	 */
	private FDCReceivingBillCollection deleteReduplicateBillCollection(FDCReceivingBillCollection collection){
		
		for (int i = 0; i < collection.size(); i++) {
			FDCReceivingBillEntryCollection billEntryColl = collection.get(i).getEntries();
			List list  = new ArrayList();
			for (int j = 0; j < billEntryColl.size(); j++) {
				FDCReceivingBillEntryInfo firstBillEntryInfo  = billEntryColl.get(j);
				for (int k = billEntryColl.size()-1; k >= j+1; k--) {
					FDCReceivingBillEntryInfo secondBillEntryInfo  = billEntryColl.get(k);
					if(firstBillEntryInfo.getMoneyDefine().getId().equals(secondBillEntryInfo.getMoneyDefine().getId())){
						if(firstBillEntryInfo.getSettlementType().getId().equals(secondBillEntryInfo.getSettlementType().getId())){
							if(firstBillEntryInfo.getRevAccountBank()!=null && secondBillEntryInfo.getRevAccountBank()!=null){
								if(firstBillEntryInfo.getRevAccountBank().getId().equals(secondBillEntryInfo.getRevAccountBank().getId())
										   ){
												if(!list.contains(firstBillEntryInfo)){
													list.add(firstBillEntryInfo);
												}
												if(!list.contains(secondBillEntryInfo)){
												list.add(secondBillEntryInfo);
											}
											billEntryColl.remove(firstBillEntryInfo);
											billEntryColl.remove(secondBillEntryInfo);
										}
							}else if(firstBillEntryInfo.getRevAccountBank()==null && secondBillEntryInfo.getRevAccountBank()==null){
									if(!list.contains(firstBillEntryInfo)){
										list.add(firstBillEntryInfo);
									}
									if(!list.contains(secondBillEntryInfo)){
										list.add(secondBillEntryInfo);
									}
									
									billEntryColl.remove(secondBillEntryInfo);
							}
						}

					}
				}
			}
			if(list.size()>0){
				FDCReceivingBillEntryInfo billInfo = (FDCReceivingBillEntryInfo)list.get(0);
				BigDecimal firstAmount = FDCHelper.ZERO;
				for (int j = 1; j < list.size(); j++) {
					FDCReceivingBillEntryInfo info = (FDCReceivingBillEntryInfo)list.get(j);
					firstAmount = FDCHelper.add(firstAmount, info.getRevAmount());
				}
				billInfo.setRevAmount(billInfo.getRevAmount().add(firstAmount));
				billEntryColl.add(billInfo);
			}
		}
		return collection;
	}
	
	
	/**
	 * 得到出纳系统的默认的收款类型
	 * @return
	 */
	private ReceivingBillTypeInfo getReceivingBillType(Context ctx){
		ReceivingBillTypeInfo typeInfo = null;
		ReceivingBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IReceivingBillType iReceivingBillType;
		try {
			iReceivingBillType = ReceivingBillTypeFactory
					.getLocalInstance(ctx);
			
			coll = iReceivingBillType.getReceivingBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"得到出纳系统的默认的收款类型!");
		}
		

		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}
	private PaymentBillTypeInfo getPaymentBillType(Context ctx){
		PaymentBillTypeInfo typeInfo = null;
		PaymentBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IPaymentBillType iPaymentBillType;
		try {
			iPaymentBillType = PaymentBillTypeFactory.getLocalInstance(ctx);
			
			coll = iPaymentBillType.getPaymentBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"得到出纳系统的默认的付款类型!");
		}
		

		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}

	protected void _adjustReceiveBill(Context ctx, BOSUuid billId, Map map) throws BOSException, EASBizException {
		//TODO 
		
		Map entryMap = (Map)map.get("entryMap");
		Map mainMap = (Map)map.get("mainMap");
		Date bizDate =  (Date)mainMap.get("bizDate");
		String description = (String)mainMap.get("description");
		FDCReceivingBillInfo revInfo = this.getFDCReceivingBillInfo(ctx, "select *,entries.*,fdcCustomers.*,entries.sourceEntries.* " +
				" where id ='"+billId.toString()+"'");
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.adjust))
			throw new EASBizException(new NumericExceptionSubItem("100","不允许对调整单做调整！"));
		
		if(!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED) && 
				!revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
			throw new EASBizException(new NumericExceptionSubItem("100","只有已审批或已付款状态的单据能做调整！"));
		}
		
		//当选择的收款单关联的认购单已作废，不允许进行调整
		if(revInfo.getRevBizType()!=null && revInfo.getRevBizType().equals(RevBizTypeEnum.purchase) && revInfo.getPurchaseObj()!=null) {
			if(PurchaseFactory.getLocalInstance(ctx).exists("where id='"+ revInfo.getPurchaseObj().getId() +"' " +
					"and (purchaseState = '"+ PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE +"' " +
							"or purchaseState = '"+ PurchaseStateEnum.QUITROOMBLANKOUT_VALUE +"' " +
									"or purchaseState = '"+ PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"' ) ")) {
				throw new EASBizException(new NumericExceptionSubItem("100","关联的认购单已作废，不允许进行调整！"));
			}
		}
		
		FDCReceivingBillInfo adjustInfo = (FDCReceivingBillInfo)revInfo.clone();
		//设置编码		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(adjustInfo, orgUnit.getId().toString());
		if(retNumber==null || retNumber.trim().length()==0 )
			throw new EASBizException(new NumericExceptionSubItem("100","未启用编码规则 ，不能自动生成转款单！"));
		
		adjustInfo.setId(null);
		adjustInfo.setNumber(retNumber);
		adjustInfo.setRevBillType(RevBillTypeEnum.adjust);
		adjustInfo.setBizDate(bizDate);
		adjustInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		adjustInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		adjustInfo.setAuditTime(null);
		adjustInfo.setIsCollection(false);
		adjustInfo.setIsMonthSettled(false);
		
		Set setId  = entryMap.keySet();
		Iterator iter = setId.iterator();
//		for(int k=0;k<map.size();k++)){
//			
//		}
		BigDecimal amt = FDCHelper.ZERO;
		while(iter.hasNext()){
			String id = (String)iter.next();
			amt =  amt.add((BigDecimal)entryMap.get(id ));
		}
//		adjustInfo.setAmount(adjustInfo.getAmount().negate());
//		adjustInfo.setOriginalAmount(adjustInfo.getOriginalAmount().negate());
		adjustInfo.setAmount(amt);
		adjustInfo.setOriginalAmount(amt);
		adjustInfo.setBillStatus(RevBillStatusEnum.SUBMIT);
		adjustInfo.setState(FDCBillStateEnum.SUBMITTED);
//		adjustInfo.setDescription("针对"+revInfo.getRevBillType().getAlias()+"-"+revInfo.getRevBizType().getAlias()+"，编号"+revInfo.getNumber()+"的调整");
		adjustInfo.setDescription(description);
		
		FDCReceivingBillEntryCollection adjustEntryColl = adjustInfo.getEntries();;
		BigDecimal revAmount = FDCHelper.ZERO;
		for(int i=0;i<adjustEntryColl.size();i++) {
			FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
			if(adjustEntryInfo.getId()!=null){
				revAmount = (BigDecimal)entryMap.get(adjustEntryInfo.getId().toString());
			}
			adjustEntryInfo.setId(BOSUuid.create(adjustEntryInfo.getBOSType()));
			adjustEntryInfo.setHead(adjustInfo);
			adjustEntryInfo.setRevAmount(revAmount);
			TransferSourceEntryCollection trasfEntryColl = adjustEntryInfo.getSourceEntries();
			for(int j=0;j<trasfEntryColl.size();j++){
				TransferSourceEntryInfo trasfEntryInfo = trasfEntryColl.get(j);
				trasfEntryInfo.setId(null);
				trasfEntryInfo.setHeadEntry(adjustEntryInfo);
				trasfEntryInfo.setAmount(revAmount);
			}
		}
		
		RevFDCCustomerEntryCollection fdcCustColl =  adjustInfo.getFdcCustomers();
		for(int i=0;i<fdcCustColl.size();i++) {
			RevFDCCustomerEntryInfo fdcCustInfo = fdcCustColl.get(i);
			fdcCustInfo.setId(null);
			fdcCustInfo.setHead(adjustInfo);
		}
		
		String handleClazzName = null;
		handleClazzName  = "com.kingdee.eas.fdc.propertymgmt.app.PPMNewReceiveHandle";
		
		//当收款或转款的单据做调整时，要验证调整后的明细的剩余金额不能小于0
		if(revInfo.getRevBillType().equals(RevBillTypeEnum.gathering) || revInfo.getRevBillType().equals(RevBillTypeEnum.transfer))	{
			try {
				IRevHandle revHandle = (IRevHandle) Class.forName(handleClazzName).newInstance();
				
				for(int i=0;i<adjustEntryColl.size();i++) {
					FDCReceivingBillEntryInfo adjustEntryInfo = adjustEntryColl.get(i);
					IRevListInfo revListInfo = revHandle.getRevListInfoObj(ctx, adjustEntryInfo.getRevListId(), adjustEntryInfo.getRevListType());
					BigDecimal retLeftAmount = revListInfo.getAllRemainAmount().add(adjustEntryInfo.getRevAmount());
					if(retLeftAmount.compareTo(new BigDecimal(0))<0) {
						throw new EASBizException(new NumericExceptionSubItem("100","禁止调整！因为调整后会导致'"+adjustEntryInfo.getRevListType().getAlias()+"'的剩余金额为负值！" ));
					}
				}
			} catch (InstantiationException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","异常错误！InstantiationException！" ));
			} catch (IllegalAccessException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","异常错误！IllegalAccessException！" ));
			} catch (ClassNotFoundException e) {
				throw new EASBizException(new NumericExceptionSubItem("100","异常错误！ClassNotFoundException！" ));
			}

		}		
		
		
		this._submitRev(ctx, adjustInfo ,handleClazzName);
		
	}

	protected void _receive(Context ctx, BOSUuid BOSUuid) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return;
	}
	
	
	
}




class RevListDes{
	String revListId;
	RevListTypeEnum revListType;
	IRevListInfo revListInfo;
	BigDecimal amount;
	public RevListDes(){
	}
	public RevListDes(String revListId){
		this.revListId = revListId;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RevListDes){
			RevListDes other = (RevListDes) obj;
			return this.revListId.equals(other.revListId);
		}
		return false;
	}
	public int hashCode() {
		return revListId.hashCode();
	}
}
