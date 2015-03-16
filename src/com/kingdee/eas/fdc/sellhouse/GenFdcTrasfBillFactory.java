package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;


/**
 * 转款单生成类
 * 
 *  分三步骤：
 *  1	genTrasfBill()
 *  2	setTrasfEntry()
 *  3	submitNewTrasfBill()
 * @author jeegan_wang
 */

public class GenFdcTrasfBillFactory {
	

	//根据某一条认购单的应收明细的id,查询出一条已存在的收款单,复制成一张新的收款单
	public static FDCReceivingBillInfo genTrasfBill(Context ctx,String revListId) throws BOSException, EASBizException	{
		FDCReceivingBillInfo fdcRevBillInfo = null;	//生成转款单
		//利用已存在的收款单来复制一张新的
		IFDCReceivingBillEntry fdcBillEntryFactry = null;
		if(ctx!=null)	fdcBillEntryFactry = FDCReceivingBillEntryFactory.getLocalInstance(ctx);
		else fdcBillEntryFactry = FDCReceivingBillEntryFactory.getRemoteInstance();
		
		if(revListId==null || revListId.trim().equals("") ) {
			throw new EASBizException(new NumericExceptionSubItem("100","收款明细id不能为空!"));
		}
		
		FDCReceivingBillEntryCollection fdcBillEntryColl = fdcBillEntryFactry
				.getFDCReceivingBillEntryCollection("select head.id where revListId = '"+ revListId +"' and " +
							" (head.revBillType = '"+RevBillTypeEnum.GATHERING_VALUE+"' or head.revBillType = '"+RevBillTypeEnum.TRANSFER_VALUE+"') ");	
		if(fdcBillEntryColl.size()==0) 
			throw new EASBizException(new NumericExceptionSubItem("100","查找不到收款明细对应的收款单据，创建转款单失败!"));
		
		fdcRevBillInfo = (FDCReceivingBillInfo)fdcBillEntryColl.get(0).getHead().clone();
		IFDCReceivingBill fdcBillFactory = null;
		if(ctx!=null) fdcBillFactory = FDCReceivingBillFactory.getLocalInstance(ctx);
		else fdcBillFactory = FDCReceivingBillFactory.getRemoteInstance();
		
		fdcRevBillInfo = fdcBillFactory.getFDCReceivingBillInfo("select *,entries.*,fdcCustomers.*,entries.sourceEntries.* " +
							" where id ='"+fdcRevBillInfo.getId().toString()+"'");			
		
		fdcRevBillInfo.setId(null);
		fdcRevBillInfo.setFiVouchered(false);
		fdcRevBillInfo.setBizDate(new Date());
		fdcRevBillInfo.setAmount(FDCHelper.ZERO);
		fdcRevBillInfo.setOriginalAmount(FDCHelper.ZERO);
		fdcRevBillInfo.setRevBillType(RevBillTypeEnum.transfer);
		fdcRevBillInfo.setBillStatus(RevBillStatusEnum.RECED);
		fdcRevBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		if(ctx!=null)
			fdcRevBillInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		else
			fdcRevBillInfo.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
		fdcRevBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		if(ctx!=null)
			fdcRevBillInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		else
			fdcRevBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		fdcRevBillInfo.setBillStatus(RevBillStatusEnum.SUBMIT);
		fdcRevBillInfo.setState(FDCBillStateEnum.SUBMITTED);
		fdcRevBillInfo.getEntries().clear();
		
		FDCReceivingBillEntryCollection addEntryColl = fdcRevBillInfo.getEntries();;
		for(int i=0;i<addEntryColl.size();i++) {
			FDCReceivingBillEntryInfo addEntryInfo = addEntryColl.get(i);
			addEntryInfo.setId(BOSUuid.create(addEntryInfo.getBOSType()));
			addEntryInfo.setHead(fdcRevBillInfo);
		}
		
		RevFDCCustomerEntryCollection fdcCustColl = fdcRevBillInfo.getFdcCustomers();
		for(int i=0;i<fdcCustColl.size();i++) {
			RevFDCCustomerEntryInfo fdcCustInfo = fdcCustColl.get(i);
			fdcCustInfo.setId(null);
			fdcCustInfo.setHead(fdcRevBillInfo);
		}			
		

		
		if(fdcRevBillInfo==null) {
			throw new EASBizException(new NumericExceptionSubItem("100","创建转款单失败!"));
		}
		
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
		if(orgUnit!=null)
			fdcRevBillInfo.setOrgUnit(orgUnit.castToFullOrgUnitInfo());
		
		if(!iCodingRuleManager.isExist(fdcRevBillInfo, orgUnit.getId().toString())) {
			//throw new EASBizException(new NumericExceptionSubItem("100","未启用编码规则 ，不能自动生成转款单!(组织："+orgUnit==null?"":orgUnit.getId().toString()+")"));	
			throw new EASBizException(new NumericExceptionSubItem("100","未启用编码规则 ，不能自动生成转款单!"));
		}
		
		String retNumber = iCodingRuleManager.getNumber(fdcRevBillInfo, orgUnit.getId().toString());
		fdcRevBillInfo.setNumber(retNumber); 
		
		return fdcRevBillInfo;
	}
	
	/**
	 * 设置新收款单的分录明细		
	 * @param fdcRevBillInfo	指定的新的转款单对象
	 * @param trasfAmount		转款金额
	 * @param settleType		结算方式	  
	 * @param revMoneyType		转入款项类型
	 * @param revAccount		转入科目  ，可以为空，为空是从款项科目对照表中查
	 * @param revListId			转入明细id
	 * @param revType			转入明细类型
	 * @param fromRevMoneyType	转出款项类型
	 * @param fromRevListId		转出明细id
	 * @param fromRevType		转出明细类型
	 * @throws EASBizException
	 * @throws BOSException 
	 */
	public static void setTrasfEntry(Context ctx,FDCReceivingBillInfo fdcRevBillInfo,BigDecimal trasfAmount,SettlementTypeInfo settleType
			,MoneyDefineInfo revMoneyType,AccountViewInfo revAccount,String revListId,RevListTypeEnum revType,MoneyDefineInfo fromRevMoneyType,String fromRevListId,RevListTypeEnum fromRevType) throws EASBizException, BOSException {
		if(fdcRevBillInfo==null) {
			throw new EASBizException(new NumericExceptionSubItem("100","新收款单不能为空!"));
		}
		if(trasfAmount.compareTo(FDCHelper.ZERO)<=0 || revListId==null || fromRevListId==null ) {
			throw new EASBizException(new NumericExceptionSubItem("100","传递的参数不符合要求!(转入款小于0或转入明细id为空或转出明细id为空)"));
		}
		if(revMoneyType==null || fromRevMoneyType==null)	{
			throw new EASBizException(new NumericExceptionSubItem("100","传递的转入款项类型和转出款项类型不能为空!"));
		}
		
		FDCReceivingBillEntryInfo newEntry = new FDCReceivingBillEntryInfo();
		newEntry.setRevAmount(trasfAmount);
		newEntry.setRevListId(revListId);
		if(revAccount==null)
			revAccount = getContractTableByMoneyDefine(ctx,revMoneyType); 
		AccountViewInfo oppAccount = getContractTableByMoneyDefine(ctx,fromRevMoneyType); 
		if(revAccount==null) 
			throw new EASBizException(new NumericExceptionSubItem("100","找不到转入款项对应的科目！请检查款项科目对照表！"));
		if(oppAccount==null) 
			throw new EASBizException(new NumericExceptionSubItem("100","找不到转出款项对应的科目！请检查款项科目对照表！"));
		newEntry.setRevAccount(oppAccount);
		newEntry.setOppAccount(revAccount);
		
		newEntry.setMoneyDefine(revMoneyType);
		newEntry.setSettlementType(settleType);
		newEntry.setRevListType(revType);				//明细类型
		TransferSourceEntryInfo newTrsfEntry = new TransferSourceEntryInfo();
		newTrsfEntry.setAmount(trasfAmount);
		newTrsfEntry.setFromRevListId(fromRevListId);
		newTrsfEntry.setFromRevListType(fromRevType);	//明细类型
		newEntry.getSourceEntries().add(newTrsfEntry);
		fdcRevBillInfo.getEntries().add(newEntry);
		fdcRevBillInfo.setAmount(fdcRevBillInfo.getAmount().add(trasfAmount));
		fdcRevBillInfo.setOriginalAmount(fdcRevBillInfo.getOriginalAmount().add(trasfAmount));
	}
	
	//handleClassName 默认是 "com.kingdee.eas.fdc.sellhouse.app.SheRevHandle"
	//如果提交时不想考虑因收款提交或删除的操作引起的反写问题 则可以使用 "com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle"
	public static void submitNewTrasfBill(Context ctx,FDCReceivingBillInfo fdcRevBillInfo,String handleClassName) throws BOSException, EASBizException	{
		IFDCReceivingBill fdcBillFactory = null;
		if(ctx!=null)  fdcBillFactory = FDCReceivingBillFactory.getLocalInstance(ctx);
		else fdcBillFactory = FDCReceivingBillFactory.getRemoteInstance();
		
		if(fdcRevBillInfo==null) {
			throw new EASBizException(new NumericExceptionSubItem("100","新收款单不能为空!"));
		}
		
		if(fdcRevBillInfo.getEntries()==null || fdcRevBillInfo.getEntries().size()==0) {
			throw new EASBizException(new NumericExceptionSubItem("100","新收款单的转入款明细不能为空!"));
		}
		
		FDCReceivingBillEntryCollection entryColl = fdcRevBillInfo.getEntries();
		for(int i=0;i<entryColl.size();i++)	{
			FDCReceivingBillEntryInfo entryInfo = entryColl.get(i);
			if(entryInfo.getRevAmount()==null || entryInfo.getRevAmount().compareTo(FDCHelper.ZERO)<=0 || entryInfo.getRevListId()==null) {
				throw new EASBizException(new NumericExceptionSubItem("100","新收款单的第("+(i+1)+")条转入款明细的信息不正确!"));
			}
			if(entryInfo.getSourceEntries()==null || entryInfo.getSourceEntries().size()==0) {
				throw new EASBizException(new NumericExceptionSubItem("100","新收款单的第("+(i+1)+")条对应的转出款为空!"));
			}
			TransferSourceEntryCollection trasfColl = entryInfo.getSourceEntries();
			BigDecimal allTrasfAmount = FDCHelper.ZERO;
			for(int j=0;j<trasfColl.size();j++)	{
				if(trasfColl.get(j).getAmount()==null || trasfColl.get(j).getAmount().compareTo(FDCHelper.ZERO)<=0)
					throw new EASBizException(new NumericExceptionSubItem("100","新收款单的第("+(i+1)+")条对应的转出款金额不正确!"));
				allTrasfAmount = allTrasfAmount.add(trasfColl.get(j).getAmount());
			}
			if(entryInfo.getRevAmount().compareTo(allTrasfAmount)!=0){
				throw new EASBizException(new NumericExceptionSubItem("100","新收款单的第("+(i+1)+")条对应的转入和转出款金额不一致!"));
			}
		}
		
		fdcBillFactory.submitRev(fdcRevBillInfo, handleClassName);
	}
	
	
	
	
	
	/**
	 * 根据款项类型查询出款项科目对照表
	 * */
	private static AccountViewInfo getContractTableByMoneyDefine(Context ctx,MoneyDefineInfo moneyDefine) throws EASBizException, BOSException {
		if(moneyDefine==null) return null;
		CompanyOrgUnitInfo company = null;
		if(ctx!=null) company = ContextUtil.getCurrentFIUnit(ctx);
		else company = SysContext.getSysContext().getCurrentFIUnit();
			
		String kSql = "select accountView.*,isChanged where moneyDefine.id='"+moneyDefine.getId().toString()+"'";
		if(company != null){
			kSql += " and fullOrgUnit.id='"+ company.getId().toString() +"'";
		}else{
			kSql += " and fullOrgUnit.id='idnull'";
		}
		MoneySubjectContrastCollection monContractInfoColl = null;
		if(ctx!=null)
			monContractInfoColl = MoneySubjectContrastFactory.getLocalInstance(ctx).getMoneySubjectContrastCollection(kSql);
		else
			monContractInfoColl = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(kSql);
		
		if(monContractInfoColl.size()>0) {
			return monContractInfoColl.get(0).getAccountView();
		}else{
			return null;
		}
		
	}
	
	
	
	
}
