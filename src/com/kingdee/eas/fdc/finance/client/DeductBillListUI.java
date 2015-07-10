/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * 描述:扣款单列表界面
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.2
 */
public class DeductBillListUI extends AbstractDeductBillListUI
{
	private static final String CONTRACT_ID = "entrys.contractId";
	private static final Logger logger = CoreUIObject.getLogger(DeductBillListUI.class);
	private CommonQueryDialog commonQueryDialog;
	private DeductBillListFilterUI filterUI;
    
    /**
     * output class constructor
     */
    public DeductBillListUI() throws Exception
    {
        super();
    }

    /**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return DeductBillEditUI.class
				.getName();
	}
	
	protected void updateButtonStatus() {

//		super.updateButtonStatus();

		// 如果是虚体成本中心，则不能增、删、改
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionAddNew.setEnabled(true);
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
			menuEdit.setVisible(true);
//		}
	}
	
    /**
	 * 
	 * 描述：冻结扣款单表列
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void freezeTableColumn() {
		FDCHelper.formatTableNumber(getMainTable(), "entrys.deductAmt");
		// 状态
//		int state_col_index = getMainTable().getColumn("state")
//				.getColumnIndex();
		// 合同编码
		int number_col_index = getMainTable().getColumn("number")
				.getColumnIndex();
		
//		// 合同编号(要多冻结一列才可以)
//		int name_col_index = getMainTable().getColumn("contractBill.number")
//				.getColumnIndex();
//		
		getMainTable().getViewManager().setFreezeView(-1, number_col_index+1);
	}
	

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		    checkSelected();
		    int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		 
		    AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		    if(tblMain.getCell(rowIndex, "id").getValue() == null){
		    	return;
		    }
	        String boID = tblMain.getCell(rowIndex, "id").getValue().toString().trim() ;
	        if(boID == null)
	        {
	            return;
	        }
	        boolean isEdit = false;
	        if(getBillStatePropertyName()!=null){
	    		int rowIdx=tblMain.getSelectManager().getActiveRowIndex();
	    		ICell cell =tblMain.getCell(rowIdx, getBillStatePropertyName());
	    		Object obj=cell.getValue();
	    		isEdit=ContractClientUtils.canUploadAttaForAudited(obj, false);
	    	}
	        acm.showAttachmentListUIByBoID(boID, null, this, isEdit, null);
	}
	
	
	
	 /**
	 * 
	 * 描述：返回远程接口（子类必须实现）
	 * @return
	 * @throws BOSException
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		return DeductBillFactory.getRemoteInstance();
	}
	
	/**
	 * 
	 * 描述：审核通过（子类必须实现，调用服务器端打审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void audit(List ids) throws Exception {
		DeductBillFactory.getRemoteInstance().audit(ids);
	}

	/**
	 * 
	 * 描述：反审核（子类必须实现，调用服务器端打反审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void unAudit(List ids) throws Exception {
		DeductBillFactory.getRemoteInstance().unAudit(ids);
	}
	
//	public void onGetRowSet(IRowSet rowSet) {
//		super.onGetRowSet(rowSet);
//		//Set idSet = new HashSet();
//		Map numberMap  = new HashMap();
//		try {
//			rowSet.beforeFirst();
//			while (rowSet.next()) {
//				String id = rowSet.getString(CONTRACT_ID);
//
//				if(id!=null){
//					numberMap.put(id,id);
//				}
//			}
//			
//			//Map contractMap = ContractClientUtils.getContractMap(idSet);
//			//编码名称
//			Map contractMap = ContractFacadeFactory.getRemoteInstance().getContractNumberAndNameMap(numberMap);
//			
//			rowSet.beforeFirst();
//			while(rowSet.next()) {
//				String id = rowSet.getString(CONTRACT_ID);
//				if(id==null) continue; 
//				FDCBillInfo billInfo = (FDCBillInfo) contractMap.get(id);
//				if(billInfo==null) continue; 
//				rowSet.updateString(ENTRYS_CONTRACT_NUMBER, billInfo.getNumber());
//				rowSet.updateString(ENTRYS_CONTRACT_NAME, billInfo.getName());
//			}
//			rowSet.beforeFirst();
//			
//		} catch (Exception e) {
//			handUIException(e);
//		}
//		
//	}
	
	protected void checkRef(String id) {
		super.checkRef(id);
		
	}
	
	/**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return DeductBillFactory.getRemoteInstance();
    }
    
    class KDTDataFillListner implements KDTDataFillListener {

    	/**
    	 * 实现单据头(状态,编码两列)的融合
    	 */
		public void afterDataFill(KDTDataRequestEvent e) {
			int firstRow = e.getFirstRow();
			int lastRow = e.getLastRow();
			String temp = "";
			int top = -1;
			int buttom = 0;
			boolean same = false;
			for (int i = firstRow; i < lastRow + 1; i++) {
				String number = (String)tblMain.getCell(i, "number").getValue();
				if(!same) {
					top = i-1;
				}
				if(temp.equals(number)) {
					same = true;
				}
				else {
					same = false;
					if(top >= 0) {
						
						buttom = i - 1;
						if(buttom > 0) {
							tblMain.getMergeManager().mergeBlock(top, 1, buttom, 1);
							tblMain.getMergeManager().mergeBlock(top, 2, buttom, 2);
						}
						
					}
					
				}
				temp = number;
			}
			
			if(same) {
				buttom = lastRow;
				tblMain.getMergeManager().mergeBlock(top, 1, buttom, 1);
				tblMain.getMergeManager().mergeBlock(top, 2, buttom, 2);
			}
			
		}
    	
    }
    public void onLoad() throws Exception {
    
    	tblMain.addKDTDataFillListener(new KDTDataFillListner());
    	super.onLoad();
    	actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
    	actionTraceDown.setVisible(true);
    	actionTraceDown.setEnabled(true);
    }
    
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		super.actionAddNew_actionPerformed(e);
	}
	
//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// 获取用户选择的块
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try {

			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

			super.actionAudit_actionPerformed(e);

		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    	this.getFilterUI();
        super.actionQuery_actionPerformed(e);
    }
    //2009-1-22 增加指定字段以便基类获得idList.size()时，不计算分录。
    protected String[] getCountQueryFields()  {     
    	return new String[]{"id"};    
    }
    
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	String companyID = SysContext
			.getSysContext().getCurrentFIUnit().getId().toString();
    	
    	List idList =ContractClientUtils.getSelectedIdValues(
				tblMain, "id");
    	FDCSQLBuilder builder=new FDCSQLBuilder();
    	builder.appendSql("update t_fnc_deductbillEntry set faccountviewid = ");
		builder.appendSql("	( ");
		builder
				.appendSql("	select top 1 faccountid from T_FDC_DeductAccountEntrys where fparentid=(select top 1 ba.fid from T_FDC_BeforeAccountView ba where ba.fcompanyid 		        ='"
						+ companyID + "')");
		builder
				.appendSql("       and FDeductTypeID=t_fnc_deductbillEntry.FDeductTypeID) ");
		builder.appendSql("where ");
		builder.appendParam("fparentid",idList.toArray());
    	builder.execute();
    	super.actionVoucher_actionPerformed(e);
    	
    }
    protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	changeButtonStatus();
    	super.tblMain_tableSelectChanged(e);
    }

	protected void changeButtonStatus() throws EASBizException, BOSException {
		checkSelected();
		int rowIndex = FDCClientHelper.getSelectedRow(tblMain);
		IRow row = tblMain.getRow(rowIndex);
		String billStatus =row.getCell("state").getValue().toString();
		boolean fiVouchered = ((Boolean)row.getCell("fiVouchered").getValue()).booleanValue();
		if(FDCBillStateEnum.AUDITTED.toString().equals(billStatus)){
			if(fiVouchered){
				actionVoucher.setEnabled(false);
				actionDelVoucher.setEnabled(true);
			}else{
				actionVoucher.setEnabled(true);
				actionDelVoucher.setEnabled(false);
			}
		} else {
			actionVoucher.setEnabled(false);
			actionDelVoucher.setEnabled(false);
		}
	}
	
	/**
	 * 返回定位字段的集合<p>
	 * 增加定位字段：扣款单编号，状态，扣款单位名称，扣款类型，扣款金额，扣款日期，备注。 Modified By Owen_wen 2010-09-06
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "state", "contractBill.number", "contractBill.contractName", "entrys.deductUnit.name", 
				"entrys.deductType.name", "entrys.deductAmt", "entrys.deductDate", "entrys.remark",};
	}
	
	
	protected void execQuery() {
		super.execQuery();
		this.getMainQuery().setFilter(new FilterInfo());
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog == null) {
			commonQueryDialog = super.initCommonQueryDialog();
			commonQueryDialog.setWidth(400);
		}
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new DeductBillListFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		filterUI.setAuthorizedOrgs(this.authorizedOrgs);
		filterUI.setCompany(null);
		filterUI.setProject(null);
		DefaultKingdeeTreeNode projectNode = this.getProjSelectedTreeNode();
		if (projectNode != null && projectNode.getUserObject() != null && projectNode.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode.getUserObject();
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				FullOrgUnitInfo company = null;
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					company = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
				} else {
					company = (FullOrgUnitInfo) projTreeNodeInfo;
				}
				filterUI.setCompany(company);
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {
				filterUI.setProject((CurProjectInfo) projTreeNodeInfo);
			}
		}
		return this.filterUI;
	}
}