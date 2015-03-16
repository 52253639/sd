/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.PurchaseBillFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 采购申请单 序时薄
 */
public class PurchaseBillListUI extends AbstractPurchaseBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseBillListUI.class);
    public OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
    
    /**
     * output class constructor
     */
    public PurchaseBillListUI() throws Exception
    {
        super();
    }

   
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!checkBeforeOprate(new String[]{FDCBillStateEnum.SAVED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE})){
    		MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!checkBeforeOprate(new String[]{FDCBillStateEnum.SAVED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE})){
    		MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
			SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }
    
	protected String getEditUIName() {
		return PurchaseBillEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseBillFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	protected boolean checkBeforeOprate(String[] states) throws Exception 
	{
		boolean flag = false;
		
		List idList = ContractClientUtils.getSelectedIdValues(this.getMainTable(), getKeyFieldName());

		if(idList==null || idList.size()==0){
			return flag ;
		}

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = ((IFDCBill)getBizInterface()).getCoreBillBaseCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) 
			{
				if(billState.equals(states[i])) 
				{
					pass = true;
				}
			}
			if(!pass)
			{
				flag = false;
				break ;
			}
			else
			{
				flag = pass;
			}
		}

		return flag;
	}
	
	protected String getBillStatePropertyName() {
    	return "state";
    }
	
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
		List idList =ContractClientUtils.getSelectedIdValues(this.getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			doAudit(idList);
			showOprtOKMsgAndRefresh();
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");		
				
		List idList =ContractClientUtils.getSelectedIdValues(this.getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "UnAudit");
			doUnAudit(idList);
			//((IExpertAppraise)getBizInterface()).unAudit(idList);
			showOprtOKMsgAndRefresh();
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		
	}
	public void doUnAudit(List idList) throws Exception {
		((IFDCBill)getBizInterface()).unAudit(idList);
	}

	public void doAudit(List idList) throws Exception {
		((IFDCBill)getBizInterface()).audit(idList);
	}
	public void onLoad() throws Exception {
		// R101125-135 要求 非财务组织也能进入，所以暂时注释下面语句 Owen_wen 2011-01-11
		// if (currentOrg == null || !this.currentOrg.isIsCompanyOrgUnit()) {
		// MsgBox.showInfo("非财务组织不能进入!");
		// this.abort();
		// }
		super.onLoad();
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAuditResult.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		
//		R100524-228需求。
//		add 流程图按钮和审批结果查看按钮  by jiadong at 2010-6-11
		this.actionWorkFlowG.setVisible(true);
		this.actionWorkFlowG.setEnabled(true);
		
		this.actionAuditResult.setVisible(true);
		this.actionAuditResult.setEnabled(true);
		
		
	}
	protected  boolean isNeedfetchInitData() throws Exception{	
		return false;
	}
	protected boolean isIgnoreCUFilter() {
		// TODO 自动生成方法存根
		return false;
	}

	public FilterInfo getFilterInfo() {
		// TODO 自动生成方法存根
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString()));
		return filter;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo){
		FilterInfo filter =  getFilterInfo();
		
		if(viewInfo.getFilter()==null){
			viewInfo.setFilter(filter);
		}else{
			FilterInfo filter1 = viewInfo.getFilter();
			try {
				filter1.mergeFilter(filter,"and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
			viewInfo.setFilter(filter1);
		}
		return super.getQueryExecutor(queryPK,viewInfo);
	}

}