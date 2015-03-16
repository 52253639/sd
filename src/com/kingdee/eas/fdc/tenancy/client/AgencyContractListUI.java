/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IUserCellDisplayParser;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AgencyContractFactory;
import com.kingdee.eas.fdc.tenancy.AgencyContractInfo;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.fdc.tenancy.ContractStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class AgencyContractListUI extends AbstractAgencyContractListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AgencyContractListUI.class);
    
    /**
     * output class constructor
     */
    public AgencyContractListUI() throws Exception
    {
        super();
    }

    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeSellProject.getLastSelectedPathComponent();
    	if(node != null  &&  node.getUserObject() != null  &&  node.getUserObject() instanceof SellProjectInfo){
    		uiContext.put("sellProject", node.getUserObject());
    	}
    	
    	DefaultKingdeeTreeNode agencyNode = (DefaultKingdeeTreeNode) treeAgency.getLastSelectedPathComponent();
		if(agencyNode != null  &&  agencyNode.getUserObject() != null  &&  agencyNode.getUserObject() instanceof AgencyInfo){
			uiContext.put("agency", agencyNode.getUserObject());
		}
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	verifyOper(ContractStateEnum.SUBMITTED);
    	if(confirmDialog("是否确认审批？")){
    		AgencyContractFactory.getRemoteInstance().audit(BOSUuid.read(this.getSelectedId()));
        	this.refresh(e);	
    	}
    }
    
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
    	AgencyContractInfo contract = verifyOper(ContractStateEnum.InUsing);
    	if(confirmDialog("是否确认终止？")){
    		AgencyContractFactory.getRemoteInstance().cancel(new ObjectUuidPK(this.getSelectedId()), contract);
        	this.refresh(e);	
    	}
    }
    
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
    	AgencyContractInfo contract = verifyOper(ContractStateEnum.Audited);
    	if(confirmDialog("是否确认启用？")){
    		AgencyContractFactory.getRemoteInstance().cancelCancel(new ObjectUuidPK(this.getSelectedId()), contract);
        	this.refresh(e);	
    	}
    }
    
    private AgencyContractInfo verifyOper(ContractStateEnum state) throws EASBizException, BOSException{
    	String id = this.getSelectedId();
		AgencyContractInfo contract = AgencyContractFactory.getRemoteInstance().getAgencyContractInfo(new ObjectUuidPK(id));
    	if(!state.equals(contract.getContractState())){
    		MsgBox.showWarning(this, state.getAlias() + "的合同才能执行该操作！");
    		this.abort();
    	}
    	return contract;
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.MODEL;
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.btnCancel.setIcon(EASResource.getIcon("imgTbtn_logoutuser"));
    	this.btnCancelCancel.setIcon(EASResource.getIcon("imgTbtn_execute"));
    	initTree();
    	this.treeSellProject.setSelectionRow(0);
    	this.treeAgency.setSelectionRow(0);
    	this.actionAuditResult.setVisible(false);
    	
    	
    	
    	KDFormattedTextField editor = new KDFormattedTextField();
		SHEHelper.setTextFormat(editor);
		this.tblMain.getColumn("value").setEditor(new KDTDefaultCellEditor(editor));
    	FDCHelper.formatTableNumber(getMainTable(), "value");
    }
    
    protected void treeSellProject_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.treeSellProject_valueChanged(e);
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeSellProject.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (node.getUserObject() instanceof SellProjectInfo) {
			SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
    }
    
    protected void treeAgency_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.treeAgency_valueChanged(e);
    	this.execQuery();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeSellProject.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				//支持非明细节点的查询
//				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			DefaultKingdeeTreeNode agencyNode = (DefaultKingdeeTreeNode) treeAgency.getLastSelectedPathComponent();
			if(agencyNode != null  &&  agencyNode.getUserObject() != null  &&  agencyNode.getUserObject() instanceof AgencyInfo){
				AgencyInfo agency = (AgencyInfo) agencyNode.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("agency.id", agency.getId().toString()));
			}
			
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
    /**
     * @throws BOSException 
     * 执行实模式查询，返回RowSet
     */
    protected IRowSet doRealModeExcuteQuery(IQueryExecutor exec, int start,int length) throws BOSException
    {  
    	exec.option().isAutoIgnoreZero = false;
        return exec.executeQuery();
    }
    protected void initTree() throws Exception {
    	this.treeSellProject.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeSellProject.expandAllNodes(true, (TreeNode) this.treeSellProject.getModel().getRoot());
		
		this.treeAgency.setModel(TenancyClientHelper.getAgencyTree());
		this.treeSellProject.expandAllNodes(true, (TreeNode) this.treeAgency.getModel().getRoot());
//		this.tblMain.setUserCellDisplayParser(new IUserCellDisplayParser(){
//
//			public Object parse(int rowIndex, int colIndex, ICell cell,
//					Object value) {
//				if(cell.getValue() instanceof Number){
//					if(BigDecimal.ZERO.compareTo(cell.getValue())==0){
//						return BigDecimal.ZERO;
//					}
//				}
//				return cell.getValue();
//			}
//			
//		});
//		this.tblMain.getColumn("value").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("value").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
    }
    
    protected String getEditUIName() {
    	return AgencyContractEditUI.class.getName();
    }
    
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return AgencyContractFactory.getRemoteInstance();
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		List ids = getSelectedIdValues();
		if(ids == null  ||  ids.isEmpty())
			return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(ids), CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("contractState", ContractStateEnum.Audited));
		filter.getFilterItems().add(new FilterItemInfo("contractState", ContractStateEnum.InUsing));
		filter.setMaskString("#0 and (#1 or #2)");
		if(getBizInterface().exists(filter)){
			MsgBox.showWarning(this, "已审批或已启用的中介合同不能删除！");
			abort();
		}
		
		super.actionRemove_actionPerformed(e);
	}
	public void loadFields() {

		super.loadFields();
	}
	 protected void tblMain_tableSelectChanged( com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		 ContractStateEnum  cse = ContractStateEnum.getEnum((String)(((BizEnumValueDTO)(this.getSelectedRow().getCell("state").getValue())).getValue()));
		  
		 	this.actionEdit.setEnabled(true);
	 		this.actionAudit.setEnabled(true);
	 		this.actionCancel.setEnabled(true); 
	 		this.actionCancelCancel.setEnabled(true); 
	 		//单据状态时审批的时候
		 	if(ContractStateEnum.Audited.equals(cse)){
		 		this.actionEdit.setEnabled(false);
		 		this.actionAudit.setEnabled(false);
		 		this.actionCancel.setEnabled(false);
		 	}
		 	//单据状态时启用的时候
		 	if(ContractStateEnum.InUsing.equals(cse)){
		 		this.actionEdit.setEnabled(false);
		 		this.actionAudit.setEnabled(false);
		 		this.actionCancelCancel.setEnabled(false);
		 	}
		 	
	}
}