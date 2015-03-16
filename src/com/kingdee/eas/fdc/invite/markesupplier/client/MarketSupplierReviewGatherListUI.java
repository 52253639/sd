/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketSupplierReviewGatherListUI extends AbstractMarketSupplierReviewGatherListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierReviewGatherListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierReviewGatherListUI() throws Exception
    {
        super();
    }
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber","6%",CompareType.LIKE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory.getRemoteInstance(), filter, "采购类别");
		this.supplierTypeTree.setModel(model);
		this.supplierTypeTree.setSelectionRow(0);
	}
	protected void buildOrgTree() throws Exception{
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "非采购组织不能操作");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,"", cuInfo.getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.orgTree.setModel(orgTreeModel);
		this.orgTree.setSelectionRow(0);
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	protected void supplierTypeTreeTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		this.refresh(null);
	}
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh(null);
	}
	protected String getEditUIModal(){
		return UIFactoryName.NEWTAB;
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
			}
		return null;
	}
	private FilterInfo getTreeFilter() throws Exception {
    	FilterInfo filter = new FilterInfo();
    	FilterItemCollection filterItems = filter.getFilterItems();
    	
    	DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(orgTree);
    	Object TypeInfo = null;
    	//是否选中
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		TypeInfo = TypeNode.getUserObject();
    		//设置容器的title
    		kDContainer1.setTitle(TypeNode.getText());
    	}
    	if (TypeInfo instanceof InviteTypeInfo) {
		    String longNumber = ((InviteTypeInfo)TypeInfo).getLongNumber();
			filterItems.add(new FilterItemInfo("supplier.inviteType.longNumber", longNumber+"%",CompareType.LIKE));
		}
    	if(OrgNode.getUserObject() instanceof OrgStructureInfo){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("longNumber");
    		FullOrgUnitInfo org=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo)OrgNode.getUserObject()).getUnit().getId()),sel);
    		String longNumber=org.getLongNumber();
    		filterItems.add(new FilterItemInfo("orgUnit.longNumber", longNumber+"%",CompareType.LIKE));
    	}
    	return filter;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try	{
			FilterInfo filter = new FilterInfo();
			if(this.getUIContext().get("IDSET")!=null){
				filter.getFilterItems().add(new FilterItemInfo("id", (Set)this.getUIContext().get("IDSET"),CompareType.INCLUDE));
			}else{
				filter = getTreeFilter();
				if(filter==null){
					filter = new FilterInfo();
				}
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
//			if(viewInfo.getSorter().size() < 2  ){ //默认有一个 ID ASC
//				SorterItemCollection sort=new SorterItemCollection();
//				SorterItemInfo supplier=new SorterItemInfo("supplier.number");
//				sort.add(supplier);
//				
//				SorterItemInfo evaluationType=new SorterItemInfo("evaluationType.number");
//				sort.add(evaluationType);
//				
//				SorterItemInfo template=new SorterItemInfo("template.number");
//				sort.add(template);
//				
//				viewInfo.setSorter(sort);
//			}
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	public void onLoad() throws Exception {
		super.onLoad();
//		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		if(this.getUIContext().get("IDSET")==null){
			buildOrgTree();
			buildInviteTypeTree();
		}else{
			this.contOrg.setVisible(false);
			this.contSupplierType.setVisible(false);
			kDSplitPane1.setDividerLocation(0);
			this.kDContainer1.setTitle("供应商评审");
		}
		
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
//		CRMClientHelper.changeTableNumberFormat(tblMain, "amount");
		this.setToolTipText("供应商评审");
	}
	protected void btnManagerUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (FDCMsgBox.showConfirm2(this,"是否数据升级？") != MsgBox.OK) {
			return;
		}
		int m=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select max(FSupplierEvaluationId) supplierEvaluationId,fheadid headId from T_GYS_SupplierRGContractEntry where fheadid not in(select fheadid from T_GYS_SRGContractEntry)group by fheadid");
		IRowSet rs = sqlBuilder.executeQuery();
		SelectorItemCollection sel=new SelectorItemCollection();
    	sel.add("id");
    	sel.add("supplier.manager");
    	sel.add("supplier.managerPhone");
    	sel.add("contractEntry.*");
    	sel.add("contractEntry.contractBill.id");
		while(rs.next()){
        	FDCSplQualificationAuditBillInfo supplierEv=FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK(rs.getString("supplierEvaluationId")),sel) ;
        	SupplierReviewGatherInfo revGather=SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherInfo(new ObjectUuidPK(rs.getString("headId")));
        	for(int i=0;i<supplierEv.getContractEntry().size();i++){
        		SupplierRGContractEntryInfo contractEntry=new SupplierRGContractEntryInfo();
        		contractEntry.setHead(revGather);
        		contractEntry.setIsHasContract(supplierEv.getContractEntry().get(i).isIsHasContract());
        		contractEntry.setContract(supplierEv.getContractEntry().get(i).getContract());
        		contractEntry.setContractBill(supplierEv.getContractEntry().get(i).getContractBill());
        		contractEntry.setContractAmount(supplierEv.getContractEntry().get(i).getContractAmount());
        		contractEntry.setManager(supplierEv.getSupplier().getManager());
        		contractEntry.setManagerPhone(supplierEv.getSupplier().getManagerPhone());
        		
        		SupplierRGContractEntryFactory.getRemoteInstance().addnew(contractEntry);
        	}
			m++;
		}
		FDCMsgBox.showInfo(this,m+"条供应商评审汇总成功升级！");
	}
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (FDCMsgBox.showConfirm2(this,"是否数据升级？") != MsgBox.OK) {
			return;
		}
		int m=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fid from T_GYS_SupplierReviewGather");
		IRowSet rs = sqlBuilder.executeQuery();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("entry.*");
		sel.add("entry.auditTemplate.templateEntry.weight");
		sel.add("entry.auditTemplate.templateEntry.id");
		SelectorItemCollection updatesel=new SelectorItemCollection();
		updatesel.add("amount");
		while(rs.next()){
			Set entryId=new HashSet();
			String id=rs.getString("fid");
			BigDecimal sum = new BigDecimal(0);
			SupplierReviewGatherInfo info=SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherInfo(new ObjectUuidPK(id),sel);
			for(int i=0;i<info.getEntry().size();i++){
				BigDecimal score=info.getEntry().get(i).getScore();
				if(info.getEntry().get(i).getAuditTemplate()==null||info.getEntry().get(i).getAuditTemplate().getTemplateEntry()==null) continue;
				BigDecimal weight=info.getEntry().get(i).getAuditTemplate().getTemplateEntry().getWeight();
				String tempEntryId=info.getEntry().get(i).getAuditTemplate().getTemplateEntry().getId().toString();
				if(score!=null&&weight!=null&&tempEntryId!=null){
					if(!entryId.contains(tempEntryId)){
						entryId.add(tempEntryId);
						sum=sum.add(score.multiply(weight).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
					}
				}
			}
			sum=sum.multiply(new BigDecimal(100).divide(new BigDecimal(5), 2, RoundingMode.HALF_UP));
			info.setAmount(sum);
			SupplierReviewGatherFactory.getRemoteInstance().updatePartial(info, updatesel);
			m++;
		}
		FDCMsgBox.showInfo(this,m+"条供应商评审汇总成功升级！");
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
//		CRMClientHelper.getFootRow(tblMain, new String[]{"contractAmount"});
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getBizState(id);
		if (!FDCBillStateEnum.SAVED.equals(state)&&!FDCBillStateEnum.SUBMITTED.equals(state)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getBizState(id);
		if (!FDCBillStateEnum.SAVED.equals(state)&&!FDCBillStateEnum.SUBMITTED.equals(state)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((FDCBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(orgTree);
		if(OrgNode!=null&&OrgNode.getUserObject()!=null){
			if(OrgNode.getUserObject() instanceof OrgStructureInfo){
				uiContext.put("org", OrgNode.getUserObject());
			}else{
				uiContext.put("org", null);
			}
		}
	}
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo();
		
        return objectValue;
    }

}