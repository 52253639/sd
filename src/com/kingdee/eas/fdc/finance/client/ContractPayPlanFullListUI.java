/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractPayPlanFullListUI extends AbstractContractPayPlanFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanFullListUI.class);
    
	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	private Map proLongNameMap = new HashMap();
	private Map contractMap = new HashMap();
	
    /**
     * output class constructor
     */
    public ContractPayPlanFullListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
       // super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
       // super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
       // super.actionRemove_actionPerformed(e);
    }


    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {	//暂用代码屏蔽权限
    	if (!PermissionFactory
				.getRemoteInstance()
				.hasFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString()),
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentOrgUnit().getId().toString()),
						new MetaDataPK(
								"com.kingdee.eas.fdc.finance.client.ContractPayPlanFullListUI"),
						new MetaDataPK("ActionPrint"))) {
			MsgBox.showError(this, "您没有组织: "+SysContext.getSysContext()
					.getCurrentOrgUnit().getName()+" 的  打印  业务权限！");
			SysUtil.abort();
		}
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    	if (!PermissionFactory
				.getRemoteInstance()
				.hasFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString()),
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentOrgUnit().getId().toString()),
						new MetaDataPK(
								"com.kingdee.eas.fdc.finance.client.ContractPayPlanFullListUI"),
						new MetaDataPK("ActionPrintPreview"))) {

			MsgBox.showError(this, "您没有该组织的  打印预览  业务权限！");
			SysUtil.abort();
		}
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    protected boolean isShowAttachmentAction() {
    	return false;
    }
    
	/**
     * 设置是否显示合计行
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return false;
    }
    
    protected String getEditUIName() {
    	return ContractPayPlanEditUI.class.getName();
    }
    
	protected void execQuery() {
		super.execQuery();
		
		//FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"localAmt","amount"});
		FDCClientHelper.initTable(tblMain);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractPayPlanFactory.getRemoteInstance();
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}
	

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractPayPlanFullFilterUI(this,this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}
	
	public void onLoad() throws Exception {
		this.proLongNameMap = FDCHelper.createTreeDataMap(CurProjectFactory
				.getRemoteInstance(), "name", "\\");
		super.onLoad();
		
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionVoucher.setEnabled(false);
		this.actionDelVoucher.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuTool.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.menuEdit.setVisible(false);
		this.btnWFViewdoProccess.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.btnWFViewdoProccess.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		//由于元数据中没有设置原币金额列宽导致其不显示 故手动设置
		this.tblMain.getColumn("payOriAmount").setWidth(100);
		this.tblMain.getColumn("lastPrice").setWidth(100);
		//this.tblMain.getColumn("curProject.id").getStyleAttributes().setHided(true);
		//this.tblMain.getColumn("contractId").getStyleAttributes().setHided(true);
		//this.tblMain.getColumn("company.name").getStyleAttributes().setHided(true);
		
		FDCClientHelper.initTable(tblMain);
	}
	
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	

	public void onGetRowSet(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();			
			Map idMap  = new HashMap();
			while (rowSet.next()) {
				String contractId = rowSet.getString("contractId");
			
				//修正For长春力旺反馈：当合同最终结算后合同最新造价列不能正确显示(显示为0) by Cassiel_peng 2009-10-2
//				boolean hasSettle = rowSet.getBoolean("contractId.hasSettled");
//				if(!hasSettle){
					idMap.put(contractId,contractId);
//				}
			}
			
//			Map amountMap  = ((IContractBill)ContractBillFactory.getRemoteInstance()).getAmtByAmtWithoutCost(idMap);
//			
//			rowSet.beforeFirst();
//			while (rowSet.next()) {
//				ContractClientUtils.updateAmtByAmtWithoutCost( rowSet, amountMap);
//			}			
//			
//			//编码名称
			Map map = ContractFacadeFactory.getRemoteInstance().getLastPrice(idMap);
//			
			rowSet.beforeFirst();
			while (rowSet.next()) {
					String contractId = rowSet.getString("contractId");				
					BigDecimal amount = rowSet.getBigDecimal("contractId.amount");
					boolean hasSettle = rowSet.getBoolean("contractId.hasSettled");
		//			if(!hasSettle){
						BigDecimal supamount = FDCHelper.toBigDecimal(map.get(contractId),2);
						if(supamount != null){					
							rowSet.updateBigDecimal("contractId.settleAmt",supamount);
							rowSet.updateBigDecimal("notPayAmount",FDCHelper.subtract(supamount, FDCHelper.toBigDecimal(rowSet.getBigDecimal("payAmount"),2)));
		//					rowSet.updateBigDecimal("lastPrice",supamount.add(amount));
						}else{
							rowSet.updateBigDecimal("contractId.settleAmt",(amount));
							rowSet.updateBigDecimal("notPayAmount",FDCHelper.subtract(amount, FDCHelper.toBigDecimal(rowSet.getBigDecimal("payAmount"),2)));
		//					rowSet.updateBigDecimal("lastPrice",(amount));
						}
		//			}
			
		}
//				String displayName="";
//				String id=rowSet.getString("curProject.id");
//				String orgName=rowSet.getString("orgUnit.name");
//				if(orgName!=null){
//					displayName+=orgName;
//				}
//				String proName = (String) this.proLongNameMap.get(id);
//				if(proName!=null){
//					displayName+="\\"+proName;
//				}
//				rowSet.updateString("curProject.name",displayName);
//				
//				String contractId=rowSet.getString("contractId");
//
//				FDCBillInfo info = (FDCBillInfo)map.get(contractId);
//				if(info != null){
//					rowSet.updateString("contractNumber",info.getNumber());
//					rowSet.updateString("contractName",info.getName());
//				}
//				
//			}
			rowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		super.onGetRowSet(rowSet);
	}
    
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	protected void beforeExcutQuery(EntityViewInfo ev)
	{
		super.beforeExcutQuery(ev);
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		
		String ContractID = FDCClientHelper.getSelectedKeyValue(tblMain,"contractId");
		uiContext.put("ID", ContractID);
	}

}