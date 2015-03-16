package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.tenancy.DateEnum;
import com.kingdee.eas.fdc.tenancy.ILiquidated;
import com.kingdee.eas.fdc.tenancy.ILiquidatedManage;
import com.kingdee.eas.fdc.tenancy.ITenancyBill;
import com.kingdee.eas.fdc.tenancy.LiquidatedFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.LiquidatedManageFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedManageInfo;
import com.kingdee.eas.fdc.tenancy.LiquidatedManageCollection;
import com.kingdee.eas.fdc.tenancy.OccurreStateEnum;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection;
import com.kingdee.eas.fdc.tenancy.TenLiquidatedFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.tenancy.MoneyEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class LiquidatedManageControllerBean extends AbstractLiquidatedManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.LiquidatedManageControllerBean");
    private static final String LIQUIDATED="违约金";
    /**
     * 计算
     * @throws EASBizException 
     */
	protected boolean _account(Context ctx, BOSUuid tenBillOtherPayID)
			throws BOSException, EASBizException {
		if(tenBillOtherPayID!=null){
			ILiquidatedManage liquidatedManage=LiquidatedManageFactory.getLocalInstance(ctx);
			LiquidatedManageCollection lmCollection=liquidatedManage.getLiquidatedManageCollection("select * where tenBillOtherPayID='"+tenBillOtherPayID.toString()+"'");
			
			if(lmCollection.size()==0){
				return addNewLiquidatedManage(ctx,tenBillOtherPayID,null);
			}else{
				if(!lmCollection.get(0).isIsGenerate()){
					Date endDate=lmCollection.get(0).getEndDate();
					liquidatedManage.delete(new ObjectUuidPK(lmCollection.get(0).getId()));
					return addNewLiquidatedManage(ctx,tenBillOtherPayID,endDate);
				}
				return false;
			}
		}else{
			return false;
		}
	}

    /**
     * 批量计算
     */
	protected int _batchAccount(Context ctx, BOSUuid sellProject)
			throws BOSException, EASBizException {
//		TenancyBillCollection tbCollection=TenancyBillFactory.getLocalInstance(ctx).getTenancyBillCollection("select * where sellProject='"+sellProject+"' and tenancyState !='Saved' and tenancyState !='Submitted' and tenancyState !='Auditing' and tenancyState !='BlankOut')");
//		int sus=0;
//		for(int i=0;i<tbCollection.size();i++){
//			for(int j=0;j<tbCollection.get(i).getOtherPayList().size();j++){
//				if(account(ctx,tbCollection.get(i).getOtherPayList().get(j).getId())){
//					sus=sus+1;
//				}
//			}
//			for(int j=0;j<tbCollection.get(i).getTenancyRoomList().size();j++){
//				TenancyRoomEntryInfo trlInfo=TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryInfo(new ObjectUuidPK(tbCollection.get(i).getTenancyRoomList().get(j).getId()));
//				for(int k=0;k<trlInfo.getPayList().size();k++){
//					if(account(ctx,((TenancyRoomPayListEntryCollection)trlInfo.getPayList()).get(k).getId())){
//						sus=sus+1;
//					}
//				}
//			}
//		}
		int sus=0;
		Set id=getAccountBatchID(ctx,sellProject);
		Iterator ite=id.iterator();
		while(ite.hasNext()){
			if(account(ctx,BOSUuid.read(ite.next().toString()))){
				sus=sus+1;
			}
		}
		return sus;
	}

    /**
     * 生成应收
     */
	protected boolean _genTenBillOtherPay(Context ctx, BOSUuid id)
			throws BOSException, EASBizException {
		LiquidatedManageInfo lmInfo=LiquidatedManageFactory.getLocalInstance(ctx).getLiquidatedManageInfo(new ObjectUuidPK(id));
		IObjectValue objectValue = DynamicObjectFactory.getLocalInstance(ctx).getValue(lmInfo.getTenBillOtherPayID().getType(), new ObjectUuidPK(lmInfo.getTenBillOtherPayID()));
		MoneyDefineInfo mdfine=null;
		String des=LIQUIDATED;
		if(objectValue instanceof TenancyRoomPayListEntryInfo){
			mdfine=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(((TenancyRoomPayListEntryInfo)objectValue).getMoneyDefine().getId()));
			des=mdfine.getName()+des;
		}else if(objectValue instanceof TenBillOtherPayInfo){
			mdfine=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(((TenBillOtherPayInfo)objectValue).getMoneyDefine().getId()));
			des=mdfine.getName()+des;
		}
		if(!lmInfo.isIsGenerate()){
			TenBillOtherPayInfo tbopInfo=new TenBillOtherPayInfo();
			tbopInfo.setAppAmount(lmInfo.getActAmount());
			tbopInfo.setAppDate(FDCDateHelper.getDayBegin(Calendar.getInstance().getTime()));
			MoneyDefineCollection mdCollection=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection("select * where name ='"+LIQUIDATED+"'");
			if(mdCollection.size()>0){
				tbopInfo.setMoneyDefine(mdCollection.get(0));
				tbopInfo.setDescription(des);
				tbopInfo.setHead(lmInfo.getTenancyBill());
				BOSUuid bosid=BOSUuid.read(TenBillOtherPayFactory.getLocalInstance(ctx).addnew(tbopInfo).toString());;
				
				lmInfo.setIsGenerate(true);
				lmInfo.setGenOtherPayID(bosid);
				LiquidatedManageFactory.getLocalInstance(ctx).update(new ObjectUuidPK(lmInfo.getId()), lmInfo);
			}else{
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	protected void _checkNameDup(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
		
	}
	protected void _checkNumberDup(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
		
	}
	protected void addnewCheck(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
			 
	}
	
	public void checkNameBlank(Context arg0, DataBaseInfo arg1)
			throws BOSException, EASBizException {
	}

	public void checkNumberBlank(Context arg0, DataBaseInfo arg1)
			throws BOSException, EASBizException {
	}
	

	protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
	}

	protected boolean addNewLiquidatedManage(Context ctx, BOSUuid tenBillOtherPayID,Date endDate) throws BOSException, EASBizException{
		IObjectValue objectValue = DynamicObjectFactory.getLocalInstance(ctx).getValue(tenBillOtherPayID.getType(), new ObjectUuidPK(tenBillOtherPayID));
		ILiquidatedManage liquidatedManage=LiquidatedManageFactory.getLocalInstance(ctx);
		ITenancyBill tenancyBill=TenancyBillFactory.getLocalInstance(ctx);
		ILiquidated liquidated=LiquidatedFactory.getLocalInstance(ctx);
		LiquidatedManageInfo lmInfo=new LiquidatedManageInfo();
		LiquidatedInfo liquidatedInfo=null;
		BigDecimal lastAmount = FDCHelper.ZERO;
		
		if(objectValue instanceof TenancyRoomPayListEntryInfo){
			TenancyRoomPayListEntryInfo trpInfo=(TenancyRoomPayListEntryInfo)objectValue;
			TenancyRoomEntryInfo trEntryInfo=TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryInfo(new ObjectUuidPK(trpInfo.getTenRoom().getId()));
			TenancyBillInfo tbInfo=tenancyBill.getTenancyBillInfo(new ObjectUuidPK(trEntryInfo.getTenancy().getId()));
			MoneyDefineInfo mdfine=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(trpInfo.getMoneyDefine().getId()));
			//应收费用所对应的合同状态不是已保存、已提交、审批中、已作废
			if(!tbInfo.isIsAccountLiquidated()||TenancyBillStateEnum.Saved.equals(tbInfo.getTenancyState())
					||TenancyBillStateEnum.Submitted.equals(tbInfo.getTenancyState())||TenancyBillStateEnum.Auditing.equals(tbInfo.getTenancyState())
						||TenancyBillStateEnum.BlankOut.equals(tbInfo.getTenancyState())){
				return false;
			}
			if(trpInfo.getActRevAmount()!=null) lastAmount=trpInfo.getAppAmount().subtract(trpInfo.getActRevAmount());
			if(trpInfo.getHasTransferredAmount()!=null) lastAmount=lastAmount.add(trpInfo.getHasTransferredAmount());
			if(trpInfo.getHasRefundmentAmount()!=null) lastAmount=lastAmount.add(trpInfo.getHasRefundmentAmount());
			if(trpInfo.getHasAdjustedAmount()!=null) lastAmount=lastAmount.add(trpInfo.getHasAdjustedAmount());
			
			//应收费用存在未收款，且应收日期小于当前日期，应收费用款项名称不为“违约金”
			if(!((trpInfo.getActRevAmount()==null||lastAmount.compareTo(FDCHelper.ZERO)==1)&&trpInfo.getAppDate().before(FDCDateHelper.getDayBegin(Calendar.getInstance().getTime()))&&!LIQUIDATED.equals(mdfine.getName()))){
				return false;
			}
			if(tbInfo.isIsMDLiquidated()){
				//根据款项计算
				TenLiquidatedCollection tlCollection=TenLiquidatedFactory.getLocalInstance(ctx).getTenLiquidatedCollection("select * where tenancyBill='"+trEntryInfo.getTenancy().getId()+"' and moneyDefine='"+trpInfo.getMoneyDefine().getId()+"' and liquidated is not null");
				if(tlCollection.size()>0){
					liquidatedInfo=liquidated.getLiquidatedInfo(new ObjectUuidPK(tlCollection.get(0).getLiquidated().getId()));
					BigDecimal amount=getAmount(ctx,trpInfo.getAppAmount(),liquidatedInfo.getOccurred(),trpInfo.getAppDate(),liquidatedInfo.getRate(),liquidatedInfo.getStandard(),liquidatedInfo.getRelief());
					if(MoneyEnum.YUAN.equals(liquidatedInfo.getBit())) amount=amount.setScale(0,BigDecimal.ROUND_HALF_UP);
					if(MoneyEnum.JIAO.equals(liquidatedInfo.getBit())) amount=amount.setScale(1,BigDecimal.ROUND_HALF_UP);
					if(amount.compareTo(new BigDecimal(0))==0||amount.compareTo(new BigDecimal(0))==-1){
						return false;
					}
					lmInfo.setRate(liquidatedInfo.getRate());
					lmInfo.setRateDate(liquidatedInfo.getRateDate());
					lmInfo.setAmount(amount);
					lmInfo.setActAmount(amount);
					lmInfo.setReliefAmount(FDCHelper.ZERO);
				}else{
					return false;
				}
			}else{
				//根据合同计算
				BigDecimal amount=getAmount(ctx,trpInfo.getAppAmount(),tbInfo.getOccurred(),trpInfo.getAppDate(),tbInfo.getRate(),tbInfo.getStandard(),tbInfo.getRelief());
				if(MoneyEnum.YUAN.equals(tbInfo.getBit())) amount=amount.setScale(0,BigDecimal.ROUND_HALF_UP);
				if(MoneyEnum.JIAO.equals(tbInfo.getBit())) amount=amount.setScale(1,BigDecimal.ROUND_HALF_UP);
				if(amount.compareTo(new BigDecimal(0))==0||amount.compareTo(new BigDecimal(0))==-1){
					return false;
				}
				lmInfo.setRate(tbInfo.getRate());
				lmInfo.setRateDate(tbInfo.getRateDate());
				lmInfo.setAmount(amount);
				lmInfo.setActAmount(amount);
				lmInfo.setReliefAmount(FDCHelper.ZERO);
			}
			lmInfo.setSellProject(tbInfo.getSellProject());
			lmInfo.setMoneyDefine(trpInfo.getMoneyDefine());
			lmInfo.setTenancyBill(tbInfo);
			lmInfo.setReceiveDate(trpInfo.getAppDate());
			lmInfo.setStartDate(tbInfo.getStartDate());
			lmInfo.setEndDate(tbInfo.getEndDate());
			if(endDate==null){
				lmInfo.setLiqStartDate(trpInfo.getAppDate());
			}else{
				lmInfo.setLiqStartDate(endDate);
			}
			lmInfo.setLiqEndDate(FDCDateHelper.getDayBegin(Calendar.getInstance().getTime()));
			lmInfo.setTenBillOtherPayID(tenBillOtherPayID);
			liquidatedManage.addnew(lmInfo);
			return true;
		}else if(objectValue instanceof TenBillOtherPayInfo){
			TenBillOtherPayInfo tbopInfo=(TenBillOtherPayInfo)objectValue;
			TenancyBillInfo tbInfo=tenancyBill.getTenancyBillInfo(new ObjectUuidPK(tbopInfo.getHead().getId()));
			MoneyDefineInfo mdfine=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(tbopInfo.getMoneyDefine().getId()));
			
			//应收费用所对应的合同状态不是已保存、已提交、审批中、已作废
			if(!tbInfo.isIsAccountLiquidated()||TenancyBillStateEnum.Saved.equals(tbInfo.getTenancyState())
					||TenancyBillStateEnum.Submitted.equals(tbInfo.getTenancyState())||TenancyBillStateEnum.Auditing.equals(tbInfo.getTenancyState())
						||TenancyBillStateEnum.BlankOut.equals(tbInfo.getTenancyState())){
				return false;
			}
			
			if(tbopInfo.getActRevAmount()!=null) lastAmount=tbopInfo.getAppAmount().subtract(tbopInfo.getActRevAmount());
			if(tbopInfo.getHasTransferredAmount()!=null) lastAmount=lastAmount.add(tbopInfo.getHasTransferredAmount());
			if(tbopInfo.getHasRefundmentAmount()!=null) lastAmount=lastAmount.add(tbopInfo.getHasRefundmentAmount());
			if(tbopInfo.getHasAdjustedAmount()!=null) lastAmount=lastAmount.add(tbopInfo.getHasAdjustedAmount());
			
			
			//应收费用存在未收款，且应收日期小于当前日期，应收费用款项名称不为“违约金”
			if(!((tbopInfo.getActRevAmount()==null||lastAmount.compareTo(FDCHelper.ZERO)==1)&&tbopInfo.getAppDate().before(FDCDateHelper.getDayBegin(Calendar.getInstance().getTime()))&&!LIQUIDATED.equals(mdfine.getName()))){
				return false;
			}
			if(tbInfo.isIsMDLiquidated()){
				//根据款项计算
				TenLiquidatedCollection tlCollection=TenLiquidatedFactory.getLocalInstance(ctx).getTenLiquidatedCollection("select * where tenancyBill='"+tbopInfo.getHead().getId()+"' and moneyDefine='"+tbopInfo.getMoneyDefine().getId()+"' and liquidated is not null");
				if(tlCollection.size()>0){
					liquidatedInfo=liquidated.getLiquidatedInfo(new ObjectUuidPK(tlCollection.get(0).getLiquidated().getId()));
					BigDecimal amount=getAmount(ctx,tbopInfo.getAppAmount(),liquidatedInfo.getOccurred(),tbopInfo.getAppDate(),liquidatedInfo.getRate(),liquidatedInfo.getStandard(),liquidatedInfo.getRelief());
					if(MoneyEnum.YUAN.equals(liquidatedInfo.getBit())) amount=amount.setScale(0,BigDecimal.ROUND_HALF_UP);
					if(MoneyEnum.JIAO.equals(liquidatedInfo.getBit())) amount=amount.setScale(1,BigDecimal.ROUND_HALF_UP);
					if(amount.compareTo(new BigDecimal(0))==0||amount.compareTo(new BigDecimal(0))==-1){
						return false;
					}
					lmInfo.setRate(liquidatedInfo.getRate());
					lmInfo.setRateDate(liquidatedInfo.getRateDate());
					lmInfo.setAmount(amount);
					lmInfo.setActAmount(amount);
					lmInfo.setReliefAmount(FDCHelper.ZERO);
				}else{
					return false;
				}
			}else{
				//根据合同计算
				BigDecimal amount=getAmount(ctx,tbopInfo.getAppAmount(),tbInfo.getOccurred(),tbopInfo.getAppDate(),tbInfo.getRate(),tbInfo.getStandard(),tbInfo.getRelief());
				if(MoneyEnum.YUAN.equals(tbInfo.getBit())) amount=amount.setScale(0,BigDecimal.ROUND_HALF_UP);
				if(MoneyEnum.JIAO.equals(tbInfo.getBit())) amount=amount.setScale(1,BigDecimal.ROUND_HALF_UP);
				if(amount.compareTo(new BigDecimal(0))==0||amount.compareTo(new BigDecimal(0))==-1){
					return false;
				}
				lmInfo.setRate(tbInfo.getRate());
				lmInfo.setRateDate(tbInfo.getRateDate());
				lmInfo.setAmount(amount);
				lmInfo.setActAmount(amount);
				lmInfo.setReliefAmount(FDCHelper.ZERO);
			}
			lmInfo.setSellProject(tbInfo.getSellProject());
			lmInfo.setMoneyDefine(tbopInfo.getMoneyDefine());
			lmInfo.setTenancyBill(tbInfo);
			lmInfo.setReceiveDate(tbopInfo.getAppDate());
			lmInfo.setStartDate(tbInfo.getStartDate());
			lmInfo.setEndDate(tbInfo.getEndDate());
			if(endDate==null){
				lmInfo.setLiqStartDate(tbopInfo.getAppDate());
			}else{
				lmInfo.setLiqStartDate(endDate);
			}
			lmInfo.setLiqEndDate(FDCDateHelper.getDayBegin(Calendar.getInstance().getTime()));
			lmInfo.setTenBillOtherPayID(tenBillOtherPayID);
			liquidatedManage.addnew(lmInfo);
			return true;
		}else{
			return false;
		}
	}

	protected BigDecimal getAmount(Context ctx, BigDecimal amount,OccurreStateEnum occurre,Date receive,BigDecimal rate,int standard,int relief)
	throws BOSException, EASBizException {
		if(OccurreStateEnum.PRIOR.equals(occurre)){
			// 	发生前：应收费用的“应收金额”*（（系统当前日期 – 应收日期）+“违约金计算标准”的“发生前”天数-“违约金减免”的天数）*“违约金比例”
			return UIRuleUtil.getBigDecimal(UIRuleUtil.getBigDecimalValue(amount)*(UIRuleUtil.getBigDecimalValue(FDCDateHelper.getDiffDays(receive,FDCDateHelper.getDayBegin(Calendar.getInstance().getTime()))-1)
					+UIRuleUtil.getBigDecimalValue(standard)-relief)*UIRuleUtil.getBigDecimalValue(rate)/100).setScale(2,BigDecimal.ROUND_HALF_UP);
		}else if(OccurreStateEnum.AFTER.equals(occurre)){
		 	//发生后：应收费用的“应收金额”*（（系统当前日期 – 应收日期）-“违约金计算标准”的“发生后”天数-“违约金减免”的天数）*“违约金比例”
			return UIRuleUtil.getBigDecimal(UIRuleUtil.getBigDecimalValue(amount)*(UIRuleUtil.getBigDecimalValue(FDCDateHelper.getDiffDays(receive,FDCDateHelper.getDayBegin(Calendar.getInstance().getTime()))-1)
					-UIRuleUtil.getBigDecimalValue(standard)-relief)*UIRuleUtil.getBigDecimalValue(rate)/100).setScale(2,BigDecimal.ROUND_HALF_UP);
		}else{
			return null;
		}
			 
	}
	protected Set getAccountBatchID(Context ctx, BOSUuid sellProject) throws BOSException{
		Set id=new HashSet();
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		sqlBuilder.appendSql("select a.fid id from t_ten_tenbillotherpay a left outer join t_ten_tenancybill b on a.fheadid= b.fid ");
		sqlBuilder.appendSql("left outer join t_she_moneydefine c on a.fmoneydefineid=c.fid ");
		sqlBuilder.appendSql("where b.ftenancystate in('Audited','Executing','ContinueTenancying') and b.fisaccountliquidated=1 and b.fsellprojectid='"+sellProject+"' ");
		sqlBuilder.appendSql("and a.fappamount >0 and isnull(a.fappamount,0)-(isnull(a.FActRevAmount,0)-isnull(a.FHasTransferredAmount,0)-isnull(a.FHasRefundmentAmount,0)-isnull(a.FHasAdjustedAmount,0))>0 and a.fappdate<'"+FDCDateHelper.DateToString(Calendar.getInstance().getTime())+"' and c.fname_l2 !='"+LIQUIDATED+"' ");
		sqlBuilder.appendSql("union ");
		sqlBuilder.appendSql("select d.fid id from t_ten_tenancyroompaylistentry d left outer join t_ten_tenancyroomentry e on d.ftenroomid= e.fid ");
		sqlBuilder.appendSql("left outer join t_ten_tenancybill f on e.ftenancyid= f.fid ");
		sqlBuilder.appendSql("left outer join t_she_moneydefine g on d.fmoneydefineid=g.fid ");
		sqlBuilder.appendSql("where f.ftenancystate in('Audited','Executing','ContinueTenancying') and f.fisaccountliquidated=1 and f.fsellprojectid='"+sellProject+"'");
		sqlBuilder.appendSql("and d.fappamount >0 and isnull(d.fappamount,0)-(isnull(d.FActRevAmount,0)-isnull(d.FHasTransferredAmount,0)-isnull(d.FHasRefundmentAmount,0)-isnull(d.FHasAdjustedAmount,0))>0 and d.fappdate<'"+FDCDateHelper.DateToString(Calendar.getInstance().getTime())+"' and g.fname_l2 !='"+LIQUIDATED+"'");
		
		IRowSet rs =sqlBuilder.executeQuery();
		try {
			while(rs.next()){
				id.add(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

}