package com.kingdee.eas.fdc.finance.client;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCProgressDialog;
import com.kingdee.eas.fdc.basedata.client.IFDCProgressMonitor;
import com.kingdee.eas.fdc.basedata.client.IFDCRunnableWithProgress;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.contract.client.SettlementNoCostEditUI;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

public class PaymentSplitProxy {
	private static String editName="com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI" ;
	private boolean debug=false;
	private static final Logger logger = CoreUIObject.getLogger(PaymentSplitProxy.class);
	public PaymentSplitProxy(){
		
	}
	
	/**
	 * 拆分当前财务组织下的所有满足条件的付款单
	 * split entity company org's payment 
	 * @throws BOSException 
	 */
	public void splitFIPayment(CoreUI ui,CompanyOrgUnitInfo companyInfo,boolean isCostSplit) throws Exception{
		if(!companyInfo.isIsBizUnit()){
			return;
		}
		//成本类付款拆分,等到
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("orgUnit.id", companyInfo.getId().toString());
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));
		view.getSelector().add("paymentBill.id");
		view.getFilter().setMaskString("#0 and (#1 or (#2 and #3))");
		HashSet set =new HashSet();
		if(isCostSplit){
			PaymentSplitCollection c1 = PaymentSplitFactory.getRemoteInstance().getPaymentSplitCollection(view);
			for(int i=0;i<c1.size();i++){
				//NP 工程量时paymentbill空
				if(c1.get(i)!=null&&c1.get(i).getPaymentBill()!=null&&c1.get(i).getPaymentBill().getId()!=null)
				set.add(c1.get(i).getPaymentBill().getId().toString());
			}

		}else{
			//财务类付款拆分
			PaymentNoCostSplitCollection c2 = PaymentNoCostSplitFactory.getRemoteInstance().getPaymentNoCostSplitCollection(view);
			for(int i=0;i<c2.size();i++){
				if(c2.get(i)!=null&&c2.get(i).getPaymentBill()!=null&&c2.get(i).getPaymentBill().getId()!=null)
				set.add(c2.get(i).getPaymentBill().getId().toString());
			}

		}
		
		view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("company.id", companyInfo.getId().toString());
		view.getFilter().getFilterItems().add(new FilterItemInfo("billStatus",new Integer(BillStatusEnum.AUDITED_VALUE),CompareType.GREATER_EQUALS));
		if(set.size()>0){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",set,CompareType.NOTINCLUDE));
		}
		
		view.getSelector().add("id");
		view.getSelector().add("amount");
		view.getSelector().add("contractBillId");
		view.getSelector().add("number");
		view.getSelector().add("fdcPayType.payType.id");
		view.getSelector().add("curProject.costCenter");
		
		//选择界面用
		view.getSelector().add("fdcPayType.payType.name");
		view.getSelector().add("billStatus");
		view.getSelector().add("fdcPayReqNumber");
    	view.getSelector().add("contractNo");
    	view.getSelector().add("fiVouchered");
    	
		view.getSorter().add(new SorterItemInfo("createTime"));
		view.getSorter().add(new SorterItemInfo("contractBillId"));
		//TODO 有性能问题
		if(isCostSplit){
			view.getFilter().getFilterItems().add(new FilterItemInfo("contractBillId","select fid from T_Con_ContractBill where fiscostsplit=1",CompareType.INNER));
		}else{
			view.getFilter().getFilterItems().add(new FilterItemInfo("contractBillId","select fid from T_Con_ContractBill where fiscostsplit=0 or fiscostsplit is null",CompareType.INNER));
		}
		final PaymentBillCollection paymentBillCollection = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
/*		//过滤出不需要的付款单
		标准环境不处理一体化对拆分的要求　 by sxhong 2008-11-05 15:27:01
		for(int i=paymentBillCollection.size()-1;i>=0;i--){
			PaymentBillInfo paymentBillInfo = paymentBillCollection.get(i);
			//删除掉没有付款类型的单据
			if(paymentBillInfo.getFdcPayType()==null
					||paymentBillInfo.getFdcPayType().getPayType()==null
					||paymentBillInfo.getFdcPayType().getPayType().getId()==null){
				paymentBillCollection.removeObject(i);
				continue;
			}
			
			FilterInfo filter=new FilterInfo();
			filter.appendFilterItem("id", paymentBillInfo.getContractBillId());
			filter.appendFilterItem("isCoseSplit", Boolean.valueOf(isCostSplit));
			filter.appendFilterItem("splitState", CostSplitStateEnum.ALLSPLIT_VALUE);
			filter.getFilterItems().add(new FilterItemInfo("conSplitExecState",ConSplitExecStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));

			if(FDCHelper.toBigDecimal(paymentBillInfo.getAmount()).compareTo(FDCHelper.ZERO)==0){
				paymentBillCollection.removeObject(i);
				continue;
			}
			if(!ContractBillFactory.getRemoteInstance().exists(filter)){
				paymentBillCollection.removeObject(i);
				continue;
			}
			
			//判断结算款是否有结算拆分
			//进度款是否生成凭证
			if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
				if(!hasSettleSplit(paymentBillInfo.getContractBillId())
						||!hasFiVouchered(paymentBillInfo.getContractBillId())){
					paymentBillCollection.removeObject(i);
					continue;
				}
				
			}
			//判断保修款是否有结算拆分
			if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
				if(!hasSettleSplit(paymentBillInfo.getContractBillId())){
					paymentBillCollection.removeObject(i);
					continue;
				}
				
			}

		}*/
/*		//合同对应到付款
		Map map=new HashMap();
		for(Iterator iter=paymentBillCollection.iterator();iter.hasNext();){
			PaymentBillInfo info=(PaymentBillInfo)iter.next();
			PaymentBillCollection coll=(PaymentBillCollection)map.get(info.getContractBillId());
			if(coll==null){
				coll=new PaymentBillCollection();
				coll.add(info);
			}else{
				
			}
		}*/
		//对未拆分的单据进行选择
		final PaymentBillCollection selectPays=BatchPaymentSplitSelectUI.showSelectUI(paymentBillCollection, ui);
		if(selectPays==null){
			return;
		}
		asynSplit(ui,selectPays, isCostSplit);
		
	}
	
	/**
	 * 进度框另起线程进行拆分
	 * @param paymentBillCollection
	 * @param isCostSplit
	 */
	private void asynSplit(CoreUI ui,final PaymentBillCollection paymentBillCollection,boolean isCostSplit){
		final Component activeWindow =ui;// KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
		if(paymentBillCollection.size()>0){
			FDCProgressDialog diag = FDCProgressDialog.createProgressDialog(activeWindow, true);
			final boolean isCostSplitTemp=isCostSplit;
			diag.run(true,true, new IFDCRunnableWithProgress(){
				public void run(IFDCProgressMonitor monitor) {
					try{
						monitor.beginTask("批量付款拆分,共:"+paymentBillCollection.size()+"条", paymentBillCollection.size());
						monitor.setTaskName("正在进行付款拆分，请稍候 ...");
						logger.info("数量:"+paymentBillCollection.size());
						for(int i=0;i<paymentBillCollection.size();i++){
							try{
								PaymentBillInfo paymentBillInfo = paymentBillCollection.get(i);
								if(debug) {
									logger.info("拆分付款单:"+paymentBillInfo.getNumber());
								}
								monitor.subTaskBegin("拆分第 "+(i+1)+" 条付款单:"+paymentBillInfo.getNumber());
								if(isCostSplitTemp){
									splitPayment(paymentBillInfo);
								}else{
									splitNoCostPayment(paymentBillInfo);
								}
								monitor.worked(1);
							
							}catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						MsgBox.showInfo(activeWindow, "批量付款拆分完成,请刷新！");
					}/*catch(Exception e){
						monitor.setCanceled(true);
						com.kingdee.eas.util.client.MsgBox.showError(activeWindow, "拆分错误中断");
						e.printStackTrace();
					}*/finally{
						monitor.done();
					}
				}
			});
		}else{
			MsgBox.showInfo(activeWindow, "没有符合拆分条件的付款单");
		}
	}
	private void splitPayment(PaymentBillInfo paymentBillInfo) throws Exception{
		String orgUnitId = null;
		if(paymentBillInfo.getCurProject()!=null&&paymentBillInfo.getCurProject().getCostCenter()!=null){
			orgUnitId = paymentBillInfo.getCurProject().getCostCenter().getId().toString();
		}
		boolean isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_SPLITBASEONPROP);
		
		PaymentSplitEditUI paymentSplitEditUI = getPaymentSplitEditUI(paymentBillInfo.getId().toString());
		if(isSplitBaseOnProp){
			paymentSplitEditUI.actionSplitBaseOnProp_actionPerformed(null);
		}else{
			paymentSplitEditUI.actionAutoMatchSplit_actionPerformed(null);
		}
		paymentSplitEditUI.actionSave_actionPerformed(null);

	}
	
	private void splitNoCostPayment(PaymentBillInfo paymentBillInfo) throws Exception{
		String orgUnitId = null;
		if(paymentBillInfo.getCurProject()!=null&&paymentBillInfo.getCurProject().getCostCenter()!=null){
			orgUnitId = paymentBillInfo.getCurProject().getCostCenter().getId().toString();
		}
		boolean isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_SPLITBASEONPROP); 
		PaymentNoCostSplitEditUI paymentNoCostSplitEditUI = getPaymentNoCostSplitEditUI(paymentBillInfo.getId().toString());
		if(isSplitBaseOnProp){
			paymentNoCostSplitEditUI.actionSplitBaseOnProp_actionPerformed(null);
		}else{
			paymentNoCostSplitEditUI.actionAutoMatchSplit_actionPerformed(null);
		}
		paymentNoCostSplitEditUI.actionSave_actionPerformed(null);

	}
	PaymentSplitEditUI paymentSplitEditUI=null;
	private PaymentSplitEditUI getPaymentSplitEditUI(String paymentId) throws Exception{
		if(paymentSplitEditUI==null){
			Map uiContext=new HashMap();
			uiContext.put("costBillID",paymentId);
			paymentSplitEditUI =(PaymentSplitEditUI) UIFactoryHelper.initUIObject(editName, uiContext, null, OprtState.ADDNEW);
		}else{
			paymentSplitEditUI.getUIContext().clear();
			paymentSplitEditUI.getUIContext().put("costBillID", paymentId);
			paymentSplitEditUI.setOprtState(OprtState.ADDNEW);
			paymentSplitEditUI.onLoad();
		}
		paymentSplitEditUI.onShow();
		return paymentSplitEditUI;
	}
	
	PaymentNoCostSplitEditUI paymentNoCostSplitEditUI=null;
	private PaymentNoCostSplitEditUI getPaymentNoCostSplitEditUI(String paymentId) throws Exception{
		if(paymentNoCostSplitEditUI==null){
			Map uiContext=new HashMap();
			uiContext.put("costBillID",paymentId);
			paymentNoCostSplitEditUI =(PaymentNoCostSplitEditUI) UIFactoryHelper.initUIObject(PaymentNoCostSplitEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		}else{
			paymentNoCostSplitEditUI.getUIContext().clear();
			paymentNoCostSplitEditUI.getUIContext().put("costBillID", paymentId);
			paymentNoCostSplitEditUI.setOprtState(OprtState.ADDNEW);
			paymentNoCostSplitEditUI.onLoad();
		}
		paymentNoCostSplitEditUI.onShow();
		return paymentNoCostSplitEditUI;
	}
	public boolean hasSettleSplit(String contractId) throws BOSException,EASBizException{
		if(contractId==null){
			return false;
		}
		boolean hasSplit=false;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("contractBill.hasSettled");
		view.getSelector().add("contractBill.isCoseSplit");
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		ContractSettlementBillCollection c = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
		if(c==null||c.size()<=0){
			return false;
		}
		ContractSettlementBillInfo settleInfo = c.get(0);
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("settlementBill.id", settleInfo.getId().toString());
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		filter.appendFilterItem("splitState", CostSplitStateEnum.ALLSPLIT_VALUE);
		if(settleInfo.getContractBill().isHasSettled()){
			if(settleInfo.getContractBill().isIsCoseSplit()){
				hasSplit = SettlementCostSplitFactory.getRemoteInstance().exists(filter);
			}else{
				hasSplit = SettNoCostSplitFactory.getRemoteInstance().exists(filter);
			}
		}else{
			hasSplit=false;
		}
		return hasSplit;
	}
	
	public boolean hasFiVouchered(String contractId) throws BOSException,EASBizException{
		if(contractId==null){
			return false;
		}
		//是否全部进行了拆分
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBillId", contractId);
		view.getFilter().appendFilterItem("fdcPayType.payType.id", PaymentTypeInfo.progressID);
		PaymentBillCollection c=PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
		if(c==null||c.size()==0){
			return true;
		}
		Set set=new HashSet();
		for(Iterator iter=c.iterator();iter.hasNext();){
			set.add(((PaymentBillInfo)iter.next()).getId().toString());
		}
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("id", contractId);
		filter.appendFilterItem("isCoseSplit", Boolean.TRUE);
		if(ContractBillFactory.getRemoteInstance().exists(filter)){
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",set,CompareType.INCLUDE));
			filter.appendFilterItem("fivouchered", Boolean.TRUE);
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			view.setFilter(filter);
			PaymentSplitCollection c2 = PaymentSplitFactory.getRemoteInstance().getPaymentSplitCollection(view);
			if(c2!=null&&c2.size()==c.size()){
				return true;
			}
			
		}else{
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",set,CompareType.INCLUDE));
			filter.appendFilterItem("fivouchered", Boolean.TRUE);
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			view.setFilter(filter);
			PaymentNoCostSplitCollection c2 = PaymentNoCostSplitFactory.getRemoteInstance().getPaymentNoCostSplitCollection(view);
			if(c2!=null&&c2.size()==c.size()){
				return true;
			}
		}
//		c.get(0).getFdcPayType().
		//是否全部生成了凭证
		return false;
	}
	
	public void setDebug(boolean debug){
		this.debug=debug;
	}
}
