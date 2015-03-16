/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.client.AbstractContractWithoutTextListUI;
import com.kingdee.eas.fdc.finance.FDCAdjustBillAllInfoInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustBillCollection;
import com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustBillFactory;
import com.kingdee.eas.fdc.finance.FDCAdjustBillInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustProductEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCAdjustBillListUI extends AbstractFDCAdjustBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCAdjustBillListUI.class);
    
    /**
     * output class constructor
     */
    public FDCAdjustBillListUI() throws Exception
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

	public void onLoad() throws Exception {
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit()
						.isIsCostOrgUnit())) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
			SysUtil.abort();
		}
		super.onLoad();
		this.btnAttachment.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
//		FDCAdjustBillAllInfoInfo fdcInfo = new FDCAdjustBillAllInfoInfo();
//			BOSUuid uid = BOSUuid.create(fdcInfo.getBOSType());
		
	}
	
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    protected String getKeyFieldName() {
    	return "id";
    }

    protected ICoreBillBase getRemoteInterface() throws BOSException {
		return FDCAdjustBillFactory.getRemoteInstance();
//		return null;
	}
	protected String getEditUIName() {
		return FDCAdjustBillEditUI.class.getName();
	}

	protected void audit(List ids) throws Exception {
		// TODO 自动生成方法存根
		
	}

	protected void unAudit(List ids) throws Exception {
		// TODO 自动生成方法存根
		
	}

	protected KDTable getBillListTable() {
		return tblBill;
	}

	
	protected boolean isFootVisible() {
		// TODO 自动生成方法存根
		return false;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCAdjustBillFactory.getRemoteInstance();
	}
	
	public void initUIContentLayout() {
		super.initUIContentLayout();
	}
	protected void initTable() {
		// TODO 自动生成方法存根
		super.initTable();
	}
	
	protected void selectFirstRow() {
		if (!isSelectForTable()) {
			// 选中第一行
			if (tblBill.getRow(0) != null && tblMain.getRow(0) != null) {
				tblBill.getSelectManager().select(0, 0);
				tblMain.getSelectManager().select(0, 0);
			}
		}
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("period.*");
		sic.add("number");
		sic.add("contractBill.number");
		sic.add("contractBill.name");
		sic.add("fiVouchered");
		sic.add("voucher.number");
		sic.add("creator.name");
		sic.add("createTime");
//		sic.add("entries.*");
//		sic.add("contractBill.*");
		sic.add("curProject.*");
		return sic;
	}
	
	protected EntityViewInfo genBillQueryView(KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
		int top = selectBlock.getTop();
		String contractId = (String) getMainTable().getCell(top,
				getKeyFieldName()).getValue();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", contractId));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("number"));
		
		SelectorItemCollection selectors = genBillQuerySelector();
		if (selectors != null && selectors.size() > 0) {
			for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);

			}
		}
		return view;
	}
	
	 protected void displayBillByContract(EntityViewInfo view) throws BOSException {
		 FDCAdjustBillCollection fdcAdjustBillCollection = FDCAdjustBillFactory
		 .getRemoteInstance().getFDCAdjustBillCollection(view);
		 for (Iterator iter = fdcAdjustBillCollection.iterator(); iter.hasNext();) {
			 FDCAdjustBillInfo element = (FDCAdjustBillInfo) iter.next();
			 if (element.getContractBill() != null) {
				 IRow row = getBillListTable().addRow();
				 row.getCell("id").setValue(element.getId().toString());
				 row.getCell("period").setValue(element.getPeriod());
				 row.getCell("number").setValue(element.getNumber());
				 row.getCell("contractBill.number").setValue(element.getContractBill().getNumber());
				 row.getCell("contractBill.name").setValue(element.getContractBill().getName());
				 row.getCell("fiVouchered").setValue(new Boolean(element.isFiVouchered()));
				 if(element.getVoucher()!=null)
					 row.getCell("voucher.number").setValue(element.getVoucher().getNumber());
				 if(element.getCreator()!=null)
					 row.getCell("creator").setValue(element.getCreator().getName());
				 row.getCell("createTime").setValue(element.getCreateTime());
			 }
		 }
	 }

	protected ArrayList getSelectedIdValues() {
		return super.getSelectedIdValues();
	}

	protected void tblBill_tableSelectChanged(KDTSelectEvent e) throws Exception {
		// TODO 自动生成方法存根
		KDTable table = getBillListTable();
		if(table.getBody()==null||table.getBody().size()==0){
			this.actionDelVoucher.setEnabled(false);
			this.actionVoucher.setEnabled(false);
			this.actionTraceDown.setEnabled(false);
			this.actionTraceDown.setVisible(false);
		}else{
			KDTSelectBlock block = e.getSelectBlock();
			IRow iRow = table.getRow(block.getEndRow());
			if(iRow.getCell("fiVouchered")!=null){
				Boolean fi = (Boolean)iRow.getCell("fiVouchered").getValue();
				this.actionDelVoucher.setEnabled(fi.booleanValue());
				this.actionVoucher.setEnabled(!fi.booleanValue());
				this.actionTraceDown.setEnabled(fi.booleanValue());
				this.actionTraceDown.setVisible(fi.booleanValue());
			}
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		// TODO 自动生成方法存根
		this.actionDelVoucher.setEnabled(false);
		this.actionVoucher.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceDown.setVisible(false);
		super.tblMain_tableSelectChanged(e);
	}
	/**
	 * 合同类型树是否有无文本合同
	 * @return
	 */
	protected boolean containConWithoutTxt(){
		return true;
	}

	protected void buildContractTypeTree() throws Exception {
		super.buildContractTypeTree();
		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeContractType
				.getModel();
		DefaultKingdeeTreeNode oldRootNode = (DefaultKingdeeTreeNode) treeModel
				.getRoot();
		// oldRootNode.setText(PayReqUtils.getRes("contract"));
		// KDTreeNode root=new KDTreeNode("root");
		// root.add(oldRootNode);
		// treeModel.setRoot(root);
		KDTreeNode node = new KDTreeNode("ContractWithoutText");
		node.setText(PayReqUtils.getRes("contractWithoutText"));
		treeContractType.addNodeInto(node, oldRootNode);// 用无合同类型内的数据来模拟
		// treeContractType.setRootVisible(false);
		// treeContractType.expandAllNodes(true, oldRootNode);
		// treeContractType.expandPath(new TreePath(oldRootNode.getPath()));

	}
	private boolean flag = false;
	private static ResourceBundleHelper resHelperWithout=new ResourceBundleHelper(AbstractContractWithoutTextListUI.class.getName());
	protected void treeContractType_valueChanged(TreeSelectionEvent e) throws Exception
	{	
		if (getTypeSelectedTreeNode()==null) return;
		KDTreeNode oldNode=null;
		if(e.getOldLeadSelectionPath()!=null){
			 oldNode = (KDTreeNode)e.getOldLeadSelectionPath().getLastPathComponent();
		}
		
		//选择无文本合同
		if (getTypeSelectedTreeNode().getUserObject().equals("ContractWithoutText")) {
			flag = true;
			if(oldNode==null||!oldNode.getUserObject().equals("ContractWithoutText")){
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
		        this.tblMain.setFormatXml(resHelperWithout.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","state","number","billName",
                		"currency.name","amount","originalAmount","receiveUnit.name","signDate"});
		        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractWithoutTextQuery");
		        tblMain.checkParsed(true);

			}
	        contractWithoutTextSelect();
	        FDCHelper.formatTableNumber(getMainTable(), "originalAmount");

		}else {
			flag = false;
			//测试
			if(oldNode==null||oldNode.getUserObject().equals("ContractWithoutText")){
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
				this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"bookedDate","period.number","id","state","hasSettled","contractType.name","number","name","","amount","partB.name","contractSource","signDate","landDeveloper.name","partC.name","costProperty","contractPropert","entrys.id"});

				mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillQuery");
				tblMain.checkParsed(true);
			}
			super.treeContractType_valueChanged(e);
		}
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//		getMainTable().getColumn("amount").getStyleAttributes().setNumberFormat("###,##0.00");
		
		FDCHelper.formatTableNumber(getMainTable(), "amount");
	}
	private void contractWithoutTextSelect() throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
//		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		
		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();
			
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();
				
				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
						CompareType.INCLUDE));
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		mainQuery.setFilter(filter);

		execQuery();

		if(isHasBillTable()) {
			getBillListTable().removeRows(false);
		}	
		
		getMainTable().repaint();
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
			
			//getMainTable().getRow(0).s
			//无文本合同不能新增生成付款申请单
			btnAddNew.setEnabled(false);
			actionAddNew.setEnabled(false);
			menuItemAddNew.setEnabled(false);
		}		

		tHelper.init() ;
		tHelper.setQuerySolutionInfo(null);
	}
	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception
	{
		if(getTypeSelectedTreeNode()!=null&&getTypeSelectedTreeNode().getUserObject().equals("ContractWithoutText")){
			contractWithoutTextSelect();
		}else{
			super.treeProject_valueChanged(e);
		}
	}
	
	/**
	 * 返回定位字段的集合
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "partB.name", "contractType.name", "signDate"};
	}
}