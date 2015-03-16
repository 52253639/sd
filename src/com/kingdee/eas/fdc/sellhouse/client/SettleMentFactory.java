package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.IAsstActType;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.util.UuidException;

public class SettleMentFactory {

	private ReceivingBillInfo receivingBillInfo = null;
	
	/**
	 * @param sysType	所属系统
	 * @param revBillType 收款单类型   收款 退款 结转
	 * @param fdcEntryColl 房地产收款单分录 中的对象必须有款项和金额  款项中的收款类型也必须有
	 * @param roomInfo 房间  
	 * @param purInfo 认购单 -- 把认购单里的客户分录等对象查出来
	 */
	public SettleMentFactory(MoneySysTypeEnum sysType,ReceiveBillTypeEnum revBillType,FDCReceiveBillEntryCollection fdcEntryColl,
				RoomInfo roomInfo,PurchaseInfo purInfo) {
		try {
			receivingBillInfo = createBaseRecBillInfo();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		FDCReceiveBillInfo fdcRevBillInfo = receivingBillInfo.getFdcReceiveBill(); 
		fdcRevBillInfo.setMoneySysType(sysType);
		fdcRevBillInfo.setBillTypeEnum(revBillType);  //收款单类型 :收款 退款 结转
		receivingBillInfo.getFdcReceiveBillEntry().addCollection(fdcEntryColl);

		BigDecimal totalAmount = new BigDecimal(0);
		if(fdcEntryColl.size()>0){ //如果不设置receivingBillInfo.setRecBillType的属性，则该收款单生成凭证时会报无法反写的错
			MoneyDefineInfo monDeInfo = fdcEntryColl.get(0).getMoneyDefine();
			if(monDeInfo.getRevBillType()!=null) 
				receivingBillInfo.setRecBillType(monDeInfo.getRevBillType());
			for(int i=0;i<fdcEntryColl.size();i++) {
				FDCReceiveBillEntryInfo  fdcBillEntryInfo = fdcEntryColl.get(i);
				totalAmount = totalAmount.add(fdcBillEntryInfo.getAmount());
			}
		}
		receivingBillInfo.setAmount(totalAmount);
		
		if (receivingBillInfo.getEntries().isEmpty()) 	{   //收款单财务分录
			ReceivingBillEntryInfo entry = new ReceivingBillEntryInfo();
			entry.setAmount(totalAmount);
			entry.setActualAmt(totalAmount);
			receivingBillInfo.getEntries().add(entry);
		} else	{
			ReceivingBillEntryInfo entry = receivingBillInfo.getEntries().get(0);
			entry.setAmount(totalAmount);
			entry.setActualAmt(totalAmount);
		}
		
		
		fdcRevBillInfo.setRoom(roomInfo);
		if(purInfo!=null)  {				//房地产收款单中的系统客户分录
			fdcRevBillInfo.setPurchase(purInfo);
			fdcRevBillInfo.setSellProject(purInfo.getSellProject());
			PurchaseCustomerInfoCollection  purCustCol = purInfo.getCustomerInfo();
			for(int i=0;i<purCustCol.size();i++) {
				PurchaseCustomerInfoInfo custInfoInfo = purCustCol.get(i);
				CustomerInfo custInfo = null;
				if(custInfoInfo.getCustomer()!=null) {
					custInfo = custInfoInfo.getCustomer().getSysCustomer();
					if(custInfo!=null) {
						if(i==0){	//把主客户作为收款单的客户
							receivingBillInfo.setPayerID(custInfo.getId().toString());
							receivingBillInfo.setPayerName(custInfo.getName());
							receivingBillInfo.setPayerNumber(custInfo.getNumber());
						}
						
						CustomerEntryInfo custEntr = new CustomerEntryInfo();
						custEntr.setCustomer(custInfo);		
						fdcRevBillInfo.getCustomerEntrys().add(custEntr);
					}
				}				
			}
		}			
		
		
	}
	
	
	
	private AsstActTypeInfo getCustomerType() throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("realtionDataObject", "T_BD_Customer",CompareType.EQUALS));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		AsstActTypeCollection asstActTypeCollection = null;

		IAsstActType asstActType = AsstActTypeFactory.getRemoteInstance();
		asstActTypeCollection = asstActType.getAsstActTypeCollection(evi);

		if (asstActTypeCollection != null && asstActTypeCollection.size() == 1)
		{
			return asstActTypeCollection.get(0);
		}
		return null;
	}    
	
	//创建最基础的财务收款对象
	private ReceivingBillInfo createBaseRecBillInfo() throws EASBizException, BOSException, UuidException {
		receivingBillInfo = new ReceivingBillInfo();

		CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
		CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
				.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
		receivingBillInfo.setCurrency(baseCurrency);
		
		receivingBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		receivingBillInfo.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		
		receivingBillInfo.setIsInitializeBill(false);
		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		receivingBillInfo.setBizDate(new Date());
		
		receivingBillInfo.setPayerType(getCustomerType());		//往来户类型
		receivingBillInfo.setSourceType(SourceTypeEnum.FDC);	//来源类型（房地产成本管理）
		receivingBillInfo.setSourceSysType(SourceTypeEnum.FDC);
		
		receivingBillInfo.setBillStatus(BillStatusEnum.RECED);	//已收款状态   -- 后台处理的，因而直接认为已收款

		
		FDCReceiveBillInfo fdcReceiveBillInfo = new FDCReceiveBillInfo();	 //关联的房地产收款单
		fdcReceiveBillInfo.setId(BOSUuid.create(fdcReceiveBillInfo.getBOSType()));
	
		fdcReceiveBillInfo.setPayQuomodo(PayQuomodoEnum.CASH);	//付款方式
		
		fdcReceiveBillInfo.setGatheringObject(GatheringObjectEnum.room);  //暂定只是针对房间收款 -- 待改
		receivingBillInfo.setFdcReceiveBill(fdcReceiveBillInfo);		

		return receivingBillInfo;
	}
	

	
	
	public void submit() throws EASBizException, BOSException{	 
			FDCReceiveBillInfo fdcRevBillInfo = receivingBillInfo.getFdcReceiveBill();
			
			RoomInfo roomInfo = fdcRevBillInfo.getRoom();
			if(roomInfo==null)	return;
			
/*			
 * 			FDCReceiveBillEntryCollection  fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
 * 			if(fdcRecBillEntryColl.size()==0) return;
			for(int i=0;i<fdcRecBillEntryColl.size();i++) {
				FDCReceiveBillEntryInfo billEntryInfo =	fdcRecBillEntryColl.get(i);
				if(billEntryInfo.getAmount().compareTo(new BigDecimal(0))==0) return;
			}	*/
		
			ReceivingBillFactory.getRemoteInstance().addnew(receivingBillInfo);
			FDCReceiveBillFactory.getRemoteInstance().addnew(fdcRevBillInfo);
		
	}
	
	
	
	
	
	
	
	
}
