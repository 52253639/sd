/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractFinanceExecUI extends AbstractContractFinanceExecUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractFinanceExecUI.class);
    
    /**
     * output class constructor
     */
    public ContractFinanceExecUI() throws Exception
    {
        super();
    }

    /**
     * 屏蔽查看
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        
    }
    
    /**
     * 屏蔽查看合同
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
       
    }

	protected ICoreBase getBizInterface() throws Exception {
		return ContractExecInfosFactory.getRemoteInstance();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return (ICoreBillBase) ContractExecInfosFactory.getRemoteInstance();
	}
	//重载掉审批
	protected void audit(List ids) throws Exception {
		
	}
	//重载掉反审批
	protected void unAudit(List ids) throws Exception {
		
	}
	//tblMain
	protected KDTable getBillListTable() {
		return getMainTable();
	}
	//是否显示合计行
	protected boolean isFootVisible() {
		return true;
	}
	//初始化控件 只显示打印和打印预览
	private void initCtrl(){
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAddContent.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionConMove.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionExport.setVisible(false);
		this.actionExportData.setVisible(false);
		this.actionExportSelected.setVisible(false);
		this.actionImportData.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionProjectAttachment.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionStartWorkFlow.setVisible(false);
		this.actionToExcel.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionView.setVisible(false);
		this.actionViewContent.setVisible(false);
		this.actionViewContract.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionPublishReport.setVisible(false);
		this.actionExitCurrent.setVisible(false);
		this.actionStore.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
//		this.menuView.setVisible(true);
		this.menuEdit.setVisible(false);
	}

	protected void initTable() {
//		super.initTable();
		if(getMainTable().getColumn("contractBill.amount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "contractBill.amount");
		}
		if(getMainTable().getColumn("contractBill.originalAmount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "contractBill.originalAmount");
		}
		if(getMainTable().getColumn("changeAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "changeAmt");
		}
		if(getMainTable().getColumn("changeAmtOri")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "changeAmtOri");
		}
		if(getMainTable().getColumn("settleAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "settleAmt");
		}
		if(getMainTable().getColumn("settleAmtOri")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "settleAmtOri");
		}
		if(getMainTable().getColumn("costAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "costAmt");
		}
		if(getMainTable().getColumn("costAmtOri")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "costAmtOri");
		}
		if(getMainTable().getColumn("completedAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "completedAmt");
		}
		if(getMainTable().getColumn("paidAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "paidAmt");
		}
		if(getMainTable().getColumn("paidAmtOri")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "paidAmtOri");
		}
		if(getMainTable().getColumn("invoicedAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "invoicedAmt");
		}
		if(getMainTable().getColumn("notCompletedAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "notCompletedAmt");
		}
		if(getMainTable().getColumn("deductedAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "deductedAmt");
		}
		if(getMainTable().getColumn("deductedAmtOri")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "deductedAmtOri");
		}
		if(getMainTable().getColumn("compensatedAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "compensatedAmt");
		}
		if(getMainTable().getColumn("compensatedAmtOri")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "compensatedAmtOri");
		}
		if(getMainTable().getColumn("guerdonAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "guerdonAmt");
		}
		if(getMainTable().getColumn("guerdonAmtOri")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "guerdonAmtOri");
		}
		if(getMainTable().getColumn("partAMatlAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "partAMatlAmt");
		}
		if(getMainTable().getColumn("partAMatLoaAmt")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "partAMatLoaAmt");
		}
	}
	protected void execQuery() {

		super.execQuery();
		
		FDCClientUtils.fmtFootNumber(tblMain, new String[]{"contractBill.amount","contractBill.originalAmount"
				,"changeAmt","changeAmtOri","settleAmt","settleAmtOri","costAmt","costAmtOri"
				,"completedAmt","paidAmt","paidAmtOri","invoicedAmt","notCompletedAmt"
				,"deductedAmt","deductedAmtOri","compensatedAmt","compensatedAmtOri","guerdonAmt","guerdonAmtOri","partAMatlAmt","partAMatLoaAmt"});
	}
	public void onLoad() throws Exception {
		
		super.onLoad();		
		initCtrl();
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()),
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentCostUnit().getId().toString()),
					new MetaDataPK(
							"com.kingdee.eas.fdc.finance.client.ContractFinanceExecUI"),
					new MetaDataPK("ActionSynOldContract"))) {
			this.actionSynOldContract.setVisible(false);
			this.actionSynOldContract.setEnabled(false);
		}else{
			this.actionSynOldContract.setVisible(true);
			this.actionSynOldContract.setEnabled(true);
		}

		kDSplitPane2.add(contContrList, "bottom");
		kDSplitPane2.setDividerLocation(1.0);
//		FDCClientUtils.fmtFootNumber(tblMain, new String[]{"contractBill.amount","changeAmt"
//				,"settleAmt","costAmt","completedAmt","paidAmt",
//				"invoicedAmt","notCompletedAmt","deductedAmt","compensatedAmt"});
//		tHelper = new TableCoreuiPreferenceHelper(this);
	}
	/**
	 * 描述：重载采用KDLayout布局的容器的"OriginalBounds"客户属性。KDLayout设计思想采用了绝对布局方式，没有考虑Java中存在相对布局的情况。
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-08-28 <p>
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();

		pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(10, 10, 250, 609));
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(270, 10, 733, 609));
	}
	
	protected boolean isHasBillTable() {
		return false;
	}
	
	//重载掉
	protected void fetchInitData() throws Exception {
		
	}
	/**
	 * 重载父类方法
	 * 描述：左边树选择改变，重新构造条件执行查询
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void treeSelectChange() throws Exception {
		boolean conTree_isContract = false;
		if(containConWithoutTxt()&&getTypeSelectedTreeNode() != null
				&&getTypeSelectedTreeNode().getUserObject()!=null
				&& (getTypeSelectedTreeNode().getUserObject().equals("ContractWithoutText")
				||	getTypeSelectedTreeNode().getUserObject().equals("allContract"))){
			conTree_isContract = false;
		}else 
			conTree_isContract = true;
		FilterInfo filter = getTreeSelectChangeFilter(conTree_isContract);
		FilterItemCollection filterItems = filter.getFilterItems();
		
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
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
					
//					Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
//					filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
//							CompareType.INCLUDE));
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
				//f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				//只能是当前用户有组织范围的组织
				//OrgUnitFactory.getRemoteInstance().getu
				//if(authorizedOrgs!=null){
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();
	
		/*
		 * 合同类型树
		 */
		if (getTypeSelectedTreeNode() != null
				&& getTypeSelectedTreeNode().getUserObject() instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo) getTypeSelectedTreeNode()
					.getUserObject();
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractBill.contractType.id", idSet,
					CompareType.INCLUDE));
		}else if(containConWithoutTxt()&&getTypeSelectedTreeNode() != null
				&&getTypeSelectedTreeNode().getUserObject()!=null
				&&getTypeSelectedTreeNode().getUserObject().equals("ContractWithoutText")){
			if(getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo){
				FilterInfo f2 = new FilterInfo();
				f2.getFilterItems().add(new FilterItemInfo("isNoText",Boolean.TRUE));
				filter.mergeFilter(f2, "and");
			}else
				filter.getFilterItems().add(new FilterItemInfo("isNoText",Boolean.TRUE));
		}
		//三方合同
		typefilter.appendFilterItem("contractBill.isAmtWithoutCost", String.valueOf(0));
		
		if(filter!=null && typefilter!=null&&conTree_isContract){
			filter.mergeFilter(typefilter,"and");
		}
		mainQuery.setFilter(filter);

		execQuery();

		if(isHasBillTable()) {
			getBillListTable().removeRows(false);
		}	
		
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
			btnAddNew.setEnabled(true);
		}else if(isHasBillTable()){
			/*
			 * 没有合同时不能新增下游单据 sxhong
			 */
			btnAddNew.setEnabled(false);
		}
	}
	/**
	 * 
	 * 描述：当左边的树选择变化时的缺省条件（提供默认实现，合同状态为审核，子类可以覆盖，如果没有条件，也要返回一个new FilterInfo()，不能直接返回null）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-9-5 <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter(boolean onlyContract) {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if(onlyContract){
			filterItems.add(new FilterItemInfo("contractBill.contractType.isEnabled", Boolean.TRUE));
		}
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		return filter;
	}
    public void onGetRowSet(IRowSet rowSet)
    {
        if (rowSet == null || rowSet.size() == 0)
        {
            onNoResult();
        }
    }
    /**
     * 同步原有合同相关数据
     */
	public void actionSynOldContract_actionPerformed(ActionEvent e) throws Exception {
		ContractExecInfosFactory.getRemoteInstance().synOldContract();
		this.refreshList();
		MsgBox.showInfo(this,"原有数据同步成功！");
	}

	protected String getEditUIName() {
		return null;
	}

	protected boolean containConWithoutTxt(){
		return true;
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("partB.name"));
        sic.add(new SelectorItemInfo("contractBill.id"));
        sic.add(new SelectorItemInfo("contractBill.number"));
        sic.add(new SelectorItemInfo("contractBill.name"));
        sic.add(new SelectorItemInfo("contractBill.originalAmount"));
        sic.add(new SelectorItemInfo("contractBill.amount"));
        sic.add(new SelectorItemInfo("changeAmtOri"));
        sic.add(new SelectorItemInfo("changeAmt"));
        sic.add(new SelectorItemInfo("settleAmtOri"));
        sic.add(new SelectorItemInfo("settleAmt"));
        sic.add(new SelectorItemInfo("costAmtOri"));
        sic.add(new SelectorItemInfo("costAmt"));
        sic.add(new SelectorItemInfo("completedAmt"));
        sic.add(new SelectorItemInfo("paidAmtOri"));
        sic.add(new SelectorItemInfo("paidAmt"));
        sic.add(new SelectorItemInfo("invoicedAmt"));
        sic.add(new SelectorItemInfo("notCompletedAmt"));
        sic.add(new SelectorItemInfo("deductedAmtOri"));
        sic.add(new SelectorItemInfo("deductedAmt"));
        sic.add(new SelectorItemInfo("compensatedAmtOri"));
        sic.add(new SelectorItemInfo("compensatedAmt"));
        sic.add(new SelectorItemInfo("guerdonAmtOri"));
        sic.add(new SelectorItemInfo("guerdonAmt"));
        sic.add(new SelectorItemInfo("partAMatlAmt"));
        sic.add(new SelectorItemInfo("partAMatLoaAmt"));
        sic.add(new SelectorItemInfo("hasSettled"));
        sic.add(new SelectorItemInfo("isNoText"));
        return sic;
	}
	protected void buildContractTypeTree() throws Exception {
		super.buildContractTypeTree();
		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeContractType.getModel();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeModel.getRoot();
		KDTreeNode node=new KDTreeNode("ContractWithoutText");
		node.setText(PayReqUtils.getRes("contractWithoutText"));
		treeContractType.addNodeInto(node,root);
	}
	
	/**
	 * 增加定位字段：合同编号、合同名称、供应商、合同金额（原币）、合同金额（本币）
	 * @author owen_wen 2010-09-07
	 */
	protected String[] getLocateNames() {
		return new String[] {"contractBill.number", "contractBill.name", "partB.name", "contractBill.originalAmount", "contractBill.amount", };
	}
}