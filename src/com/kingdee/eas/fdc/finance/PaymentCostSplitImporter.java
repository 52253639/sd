package com.kingdee.eas.fdc.finance;

import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.AbstractSplitImporter;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBill;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.util.NumericExceptionSubItem;

public class PaymentCostSplitImporter extends AbstractSplitImporter {
	private ContractBillInfo contract=null;
	private PeriodInfo period=null;
	private Context ctx=null;
	
	//TODO 还要考虑期间
	public PaymentCostSplitImporter(Context ctx,String contractBillId,PeriodInfo period) throws BOSException,EASBizException{
		this.ctx=ctx;
		this.period=period;
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("hasSettled");
		selector.add("name");
		selector.add("number");
		selector.add("curProject.id");
//		selector.add("curProject.name");
//		selector.add("curProject.number");
		try{
			contract=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractBillId), selector);
		}catch(EASBizException e){
			throw new BOSException(e);
		}
		
	}
	public IObjectValue importSplit() throws BOSException ,EASBizException{
		PaymentSplitInfo info=new PaymentSplitInfo();
		info.setContractBill(contract);
		info.setCurProject(contract.getCurProject());
		PaymentSplitEntryCollection entrys = info.getEntrys();
		boolean noSettleSplit=false;
		if(isHasSettled()){
			IObjectCollection importEntrys=importSettleSplitEntrys();
			if(importEntrys==null||importEntrys.size()==0){
				//没有结算拆分的处理方式与未结算同
				noSettleSplit=true;
			}
			//update by renliang
			if(importEntrys!=null){
				for(Iterator iter=importEntrys.iterator();iter.hasNext();){
					FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
					if(entry.getLevel()<0){
						continue;
					}
					PaymentSplitEntryInfo paymentEntry=new PaymentSplitEntryInfo();
					paymentEntry.putAll(entry);
					paymentEntry.setId(null);
					entrys.add(paymentEntry);
				}
			}
			//标识结算后的工程量拆分 by hpw 2010.07.23
			info.setHasEffected(true);
		}
		
		if(!isHasSettled()||noSettleSplit){
			//为后续处理方便将合同做为未结算
			contract.setHasSettled(false);
			IObjectCollection contractEntrys=importContractSplitEntrys();
			IObjectCollection changeEntrys=importChangeSplitEntrys();
			if(contractEntrys==null||contractEntrys.size()==0){
				throw new EASBizException(new NumericExceptionSubItem("100","合同未拆分,请先拆分合同！"));
			}
			int index=1;
			for(Iterator iter=contractEntrys.iterator();iter.hasNext();){
				FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
				PaymentSplitEntryInfo paymentEntry=new PaymentSplitEntryInfo();
				paymentEntry.putAll(entry);
				paymentEntry.setContractAmt(paymentEntry.getAmount());
				paymentEntry.setAmount(FDCHelper.ZERO);
				paymentEntry.setId(null);
				paymentEntry.setCostBillId(contract.getId());
				entrys.add(paymentEntry);
				
				//处理分录顺序
				paymentEntry.setSeq(index);
				paymentEntry.setIndex(index);
				index++;
			}
			if(changeEntrys!=null){
				for(Iterator iter=changeEntrys.iterator();iter.hasNext();){
					ConChangeSplitEntryInfo entry=(ConChangeSplitEntryInfo)iter.next();
					PaymentSplitEntryInfo paymentEntry=new PaymentSplitEntryInfo();
					paymentEntry.putAll(entry);
					paymentEntry.setChangeAmt(paymentEntry.getAmount());
					paymentEntry.setAmount(FDCHelper.ZERO);
					paymentEntry.setCostBillId(entry.getParent().getContractChange().getId());
					paymentEntry.setId(null);
					entrys.add(paymentEntry);
					
					//处理分录顺序
					paymentEntry.setSeq(index);
					paymentEntry.setIndex(index);
					index++;
				}
			}
			//标识结算前的工程量拆分 by hpw 2010.07.23
			info.setHasEffected(false);
		}
		return info;
	}
	/**
	 * 引入合同拆分
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private IObjectCollection importContractSplitEntrys() throws BOSException,EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(period==null){
			view.getFilter().appendFilterItem("parent.contractBill.id", getContractBillId());
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		}else{
			StringBuffer sql=new StringBuffer();
			sql.append("select top 1 split.fid from T_Con_ContractCostSplit split ");
			sql.append(" inner join T_BD_Period period on period.fid=split.fperiodid ");
			sql.append(" where period.fnumber<=");
			sql.append(period.getNumber());
			sql.append(" and split.fcontractbillid='"+getContractBillId()+"'");
			sql.append(" and split.fislastVerThisPeriod=1 order by period.fnumber desc ");

			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.id",sql.toString(),CompareType.INNER));
			
		}
		view.getSorter().add(new SorterItemInfo("parent.id"));
		view.getSorter().add(new SorterItemInfo("index"));
		setEntrySelector(view.getSelector(),true);
		ContractCostSplitEntryCollection c = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);
		return c;
		
	}
	/**
	 * 引入变更拆分
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private IObjectCollection importChangeSplitEntrys() throws BOSException,EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(period==null){
			view.getFilter().appendFilterItem("parent.contractBill.id", getContractBillId());
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		}else{
			StringBuffer sql=new StringBuffer();
			sql.append("select conSplit.fid from T_Con_ConChangeSplit conSplit ");
			sql.append("inner join T_BD_Period period on period.fid=consplit.fperiodid ");
			sql.append("inner join ( ");
			sql.append("select split.fcontractchangeid,max(period.fnumber) as periodNumber from T_Con_ConChangeSplit split "); 
			sql.append("inner join T_BD_Period period on period.fid=split.fperiodid ");
			sql.append("where split.fcontractbillid=my_fcontractbillid and period.fnumber <= my_periodNumber and split.Fislastverthisperiod=1 "); 
			sql.append("group by split.fcontractchangeid ");
			sql.append(") t on  t.fcontractchangeid=conSplit.fcontractchangeid and period.fnumber=t.periodNumber ");
			
			String filterSql=sql.toString();
			filterSql=filterSql.replaceAll("my_fcontractbillid", "'"+getContractBillId()+"'");;
			filterSql=filterSql.replaceAll("my_periodNumber", ""+period.getNumber());
			
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.id",filterSql,CompareType.INNER));
	
		}
		view.getSelector().add("parent.contractChange.id");
		view.getSorter().add(new SorterItemInfo("parent.id"));
		view.getSorter().add(new SorterItemInfo("index"));
		setEntrySelector(view.getSelector(),true);
		ConChangeSplitEntryCollection c = ConChangeSplitEntryFactory.getLocalInstance(ctx).getConChangeSplitEntryCollection(view);
		return c;
	}
	/**
	 * 引入结算拆分
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public IObjectCollection importSettleSplitEntrys() throws BOSException,EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(period==null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.settlementBill.isFinalSettle", Boolean.TRUE, CompareType.EQUALS));
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.contractBill.id", getContractBillId(), CompareType.EQUALS));
			
		}else{
			StringBuffer sql=new StringBuffer();
			sql.append("select top 1 split.fid from T_Con_SettlementCostSplit split ");
			sql.append(" inner join T_BD_Period period on period.fid=split.fperiodid ");
			sql.append(" where period.fnumber<=");
			sql.append(period.getNumber());
			sql.append(" and split.fcontractbillid='"+getContractBillId()+"'");
			sql.append(" and split.fislastVerThisPeriod=1 order by period.fnumber desc ");
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.id",sql.toString(),CompareType.INNER));
		}

		setEntrySelector(view.getSelector(),true);
		view.getSorter().add(new SorterItemInfo("parent.id"));
		view.getSorter().add(new SorterItemInfo("index"));
		view.getSorter().add(new SorterItemInfo("seq"));//TODO 末级拆分时仅以index排序工程量拆分分录不正确,引入合同变更拆分是否也 要增加 by hpw 2010.7.2
		SettlementCostSplitEntryCollection c = SettlementCostSplitEntryFactory.getLocalInstance(ctx).getSettlementCostSplitEntryCollection(view);
		return c;
	}

	private boolean isHasSettled() throws BOSException,EASBizException{
		return contract.isHasSettled();
	}
	protected String getContractBillId() {
		return this.contract.getId().toString();
	}
}
