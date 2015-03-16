package com.kingdee.eas.fdc.finance.app.voucher;

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
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.WorkLoadCostVoucherEntryInfo;

public class WorkLoadCostVoucherHandler extends AbstractFDCVoucherHandler  implements IFDCCostVoucherHandler {
	public WorkLoadCostVoucherHandler(Context ctx,IFDCVoucherEntryCreator creator) {
		setCtx(ctx);
		setFDCVoucherEntryCreator(creator);
	}

	public IObjectCollection createCostEntrys(Map dataMap) throws EASBizException, BOSException {
		init(dataMap);
		IObjectCollection coll = null;
		/***
		 * 奖励单独拉出来同步
		 */
		IObjectCollection paySplitInfos=(IObjectCollection)dataMap.get("paySplitInfos");
		for(Iterator iter=paySplitInfos.iterator();iter.hasNext();){			
			IObjectCollection onecoll=createOneCostEntry((IObjectValue)iter.next());
			if(coll==null)
				coll = onecoll;
			else
				coll.addObjectCollection(onecoll);
		}
		return coll;
	}

	/**
	 * 根据一个付款拆分生成一个付款的单借方科目，凭证的科目通过调用getAccountView()方法取得
	 * 拆分上的付款是全的
	 * 注意：此处对应的付款拆分应该是成本类的合同拆分
	 * @param splitInfo
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws Exception 
	 */
	protected IObjectCollection createOneCostEntry(IObjectValue splitInfo) throws EASBizException, BOSException{
		/***
		 * 传入的splitInfo需要instanceof
		 * 周勇
		 */
		IObjectCollection coll = new WorkLoadCostVoucherEntryCollection();

		PaymentSplitInfo paymentSplitInfo = (PaymentSplitInfo)splitInfo;
		for(Iterator it=paymentSplitInfo.getEntrys().iterator();it.hasNext();){
			PaymentSplitEntryInfo entryInfo = (PaymentSplitEntryInfo)it.next();
			
			if(entryInfo.isIsLeaf()&&entryInfo.getCostAccount()!=null){
				coll.addObject(createCostEntry(entryInfo,paymentSplitInfo.getWorkLoadConfirmBill(),paymentSplitInfo));
			}
		}
		return coll;
	}
	
	/***
	 * 同步PaymentSplitEntry
	 * AccountViewInfo voucherAcct = getAccountView();
	 * @param entryInfo
	 * @param workLoadConfirmBillInfo
	 * @param splitInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected IObjectValue createCostEntry(PaymentSplitEntryInfo entryInfo,WorkLoadConfirmBillInfo workLoadConfirmBillInfo,PaymentSplitInfo splitInfo) throws EASBizException, BOSException{ 
		WorkLoadCostVoucherEntryInfo voucherEntryInfo = new WorkLoadCostVoucherEntryInfo();		
		voucherEntryInfo.setId(BOSUuid.create(voucherEntryInfo.getBOSType()));
		
		voucherEntryInfo.setCostAmt(entryInfo.getCostAmt());
		voucherEntryInfo.setPayAmt(entryInfo.getPayedAmt());
		voucherEntryInfo.setAmount(entryInfo.getAmount());
		voucherEntryInfo.setLocateAmount(entryInfo.getAmount());
		
		voucherEntryInfo.setExchangeRate(FDCHelper.ONE);
		voucherEntryInfo.setSupplier(workLoadConfirmBillInfo.getContractBill().getPartB());
		voucherEntryInfo.setCurrency(FDCHelper.getBaseCurrency(getCtx()));
		
		String key = entryInfo.getCostAccount().getCurProject().getId().toString() ;
		key += "_" + entryInfo.getCostAccount().getId().toString();
		AccountViewInfo voucherAcct = getAccountView(entryInfo.getCostAccount());
		voucherEntryInfo.setAccountView(voucherAcct);
		voucherEntryInfo.setCostAccount(entryInfo.getCostAccount());
		voucherEntryInfo.setParent(workLoadConfirmBillInfo);
		voucherEntryInfo.setType(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT);
		voucherEntryInfo.setCurProject(entryInfo.getCostAccount().getCurProject());
		voucherEntryInfo.setProductType(entryInfo.getProduct());
		return voucherEntryInfo;
	}
	

	/**
	 * 通过成本科目查找对应的会计科目
	 * @param key
	 * @return
	 * @throws EASBizException 
	 * @throws  
	 */
	public AccountViewInfo getAccountView(CostAccountInfo costAccount) throws BOSException,EASBizException{
		
		return getFDCVoucherEntryCreator().getProgressAccountView(costAccount);
	}

	public void beforeSave(Map param) throws BOSException {
		
	}

	public void save(Map param, IObjectCollection costEntrys) throws BOSException {
		String workLoadBillId ;
		WorkLoadConfirmBillInfo workLoadConfirmBillInfo = null;
		Map workLoadBills = (HashMap)param.get("paymentBills");
		FDCSQLBuilder builder = new FDCSQLBuilder(getCtx());
		beforeSave(workLoadBills);
		if(costEntrys==null){
			return;
		}
		
		builder.setPrepareStatementSql(COSTENTRY_INSERT_SQL);
		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

		for(Iterator it = costEntrys.iterator();it.hasNext();){
			WorkLoadCostVoucherEntryInfo entryInfo = (WorkLoadCostVoucherEntryInfo)it.next();
			workLoadBillId = entryInfo.getParent().getId().toString();
			/***
			 * 如果是复杂模式，且已经同步过了
			 * 就不再同步了，直接跳过
			 */
			if(hasSynchBills.containsKey(workLoadBillId)){
				continue;
			}
			if(workLoadBills.containsKey(workLoadBillId))
			{
				workLoadConfirmBillInfo = (WorkLoadConfirmBillInfo)workLoadBills.get(workLoadBillId);
				entryInfo.setSeq(workLoadConfirmBillInfo.getCostVoucherEntrys().size()+1);
				workLoadConfirmBillInfo.getCostVoucherEntrys().add(entryInfo);
				builder.addParam(entryInfo.getId().toString());
				builder.addParam(new Integer(entryInfo.getSeq()));
				builder.addParam(entryInfo.getCurrency().getId().toString());
				builder.addParam(entryInfo.getSupplier().getId().toString());
				builder.addParam(entryInfo.getAccountView().getId().toString());
				builder.addParam(entryInfo.getDesc());
				builder.addParam(entryInfo.getExchangeRate());
				builder.addParam(FDCHelper.toBigDecimal(entryInfo.getLocateAmount()));
				builder.addParam(FDCHelper.toBigDecimal(entryInfo.getAmount()));
				builder.addParam(entryInfo.getType());
				builder.addParam(entryInfo.getParent().getId().toString());
				if(entryInfo.getProductType()==null)
					builder.addParam(null);
				else
					builder.addParam(entryInfo.getProductType().getId().toString());
				builder.addParam(entryInfo.getCurProject().getId().toString());
				builder.addParam(FDCHelper.toBigDecimal(entryInfo.getCostAmt()));
				builder.addParam(FDCHelper.toBigDecimal(entryInfo.getPayAmt()));
				builder.addParam(entryInfo.getCostAccount()!=null?entryInfo.getCostAccount().getId().toString():null);
				builder.addBatch();
			}
		}
		builder.executeBatch();
		afterSave(param);
		builder.clear();
		builder = null;
	
		
	}
	public void afterSave(Map param) throws BOSException {
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		
		/***
		 * 更新付款拆分上的字段,凭证引用源单据字段
		 */
		if(needUpdatePaymentSplitSet.size()>0){
			builder.setPrepareStatementSql(PAYMENTSPLIT_UPDATE_SQL);
			builder.addParam(PaySplitVoucherRefersEnum.WORKLOADBILL_VALUE);
			builder.appendSql(" where ");
			builder.appendParam("fid",needUpdatePaymentSplitSet.toArray());
			builder.appendSql(" and ( FVoucherRefer is null or ");			
			builder.appendParam("FVoucherRefer",PaySplitVoucherRefersEnum.NOTREFER_VALUE);
			builder.appendSql(" ) ");
			builder.executeUpdate();
		}
		builder.clear();
	}
	
	/***
	 * 成本分录的插入SQL语句
	 */
	private final static String COSTENTRY_INSERT_SQL = "INSERT INTO T_FNC_WorkLoadCostVoucherEntry" +
			"(FID,FSeq,FCurrencyid,FSupplierid,FAccountViewID,FDesc,FExchangeRate,FLocateAmount,FAmount,FType,FParentID,FProductTypeID,FCurProjectID,FCostAmt,FPayAmt,fcostaccountid) values" +
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/***
	 * 更新付款拆分生成凭证的引用属性的SQL 
	 */
	private final static String PAYMENTSPLIT_UPDATE_SQL = "update t_fnc_paymentsplit set FVoucherRefer=?,FVoucherReferId=fworkloadbillid ";

}
