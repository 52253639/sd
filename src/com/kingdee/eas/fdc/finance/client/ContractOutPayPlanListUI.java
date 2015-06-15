/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.ContractOutPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractOutPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractOutPayPlanInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ContractOutPayPlanListUI extends AbstractContractOutPayPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractOutPayPlanListUI.class);
    
    public ContractOutPayPlanListUI() throws Exception
    {
        super();
    }
    protected String[] getLocateNames() {
		return new String[] {"number", "contractName",  "partB.name", "contractType.name", "signDate"};
	}
	protected void audit(List ids) throws Exception {
		ContractOutPayPlanFactory.getRemoteInstance().audit(ids);
	}
	protected void unAudit(List ids) throws Exception {
		ContractOutPayPlanFactory.getRemoteInstance().unAudit(ids);
	}
	
//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		获取用户选择的块
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			super.actionAudit_actionPerformed(e);
	
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
		this.btnAttachment.setEnabled(true);
	    this.btnAttachment.setVisible(true);
	}
	protected KDTable getBillListTable() {
		return this.tblPayRequestBill;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ContractOutPayPlanFactory.getRemoteInstance();
	}
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return ContractOutPayPlanFactory.getRemoteInstance();
	}
	protected boolean isFootVisible() {
		return false;
	}
	protected void initTable() {
		FDCHelper.formatTableDate(getBillListTable(), "bizDate");
//		getBillListTable().getColumn("version").getStyleAttributes().setNumberFormat("#,##0.0;-#,##0.0");
		super.initTable();
	}
	protected void freezeBillTableColumn() {
		super.freezeBillTableColumn();
	}
	protected boolean  displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
		if(view==null){
			return false ;
		}
		ContractOutPayPlanCollection contractChangeBillCollection = ContractOutPayPlanFactory.getRemoteInstance().getContractOutPayPlanCollection(view);
		for (Iterator iter = contractChangeBillCollection.iterator(); iter.hasNext();) {
			ContractOutPayPlanInfo element = (ContractOutPayPlanInfo) iter
					.next();
			IRow row = getBillListTable().addRow();
			row.getCell("id").setValue(element.getId().toString());
			row.getCell("number").setValue(element.getNumber());
			row.getCell("state").setValue(element.getState());
			row.getCell("bizDate").setValue(element.getBizDate());
//			row.getCell("version").setValue(element.getVersion());
			
			row.getCell("description").setValue(element.getDescription());
			if(element.getCreator()!=null)
				row.getCell("creator.name").setValue(element.getCreator().getName());
			row.getCell("createTime").setValue(element.getCreateTime());
			if(element.getAuditor()!=null)
				row.getCell("auditor").setValue(element.getAuditor().getName());
			row.getCell("auditTime").setValue(element.getAuditTime());
			
//			if(element.getRespDept()!=null)
//				row.getCell("respDept").setValue(element.getRespDept().getName());
//			
//			if(element.getRespPerson()!=null)
//				row.getCell("respPerson").setValue(element.getRespPerson().getName());
			
//			row.getCell("isLatest").setValue(element.isIsLatest());
		}
		
		return true;
	}

    protected String getBillStatePropertyName() {
    	return "state";
    }
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contract.id", contractId));
    	
    	view.setFilter(filter);
//    	SorterItemInfo version=new SorterItemInfo("version");
//    	version.setSortType(SortType.DESCEND);
//    	view.getSorter().add(version);
    	
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);				
			}
    	}
    	
		return view;
	}
    
	/**
	 * 
	 * 描述：生成获取单据的Selector
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-3
	 *               <p>
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("state");
		selectors.add("number");
		selectors.add("name");
		
		selectors.add("originalAmount");
		//revLocalAmount
		selectors.add("amount");
		//revAmount
		selectors.add("revAmount");
		//revLocalAmount
		selectors.add("revLocalAmount");
		
		selectors.add("reviseReason");
		selectors.add("createTime");
		
		selectors.add("currency.name");
		selectors.add("period.number");
		selectors.add("contractType.name");
		selectors.add("creator.name");
		selectors.add("respPerson.name");
		selectors.add("respDept.name");
		return selectors;
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
//		String contractId = getSelectedKeyValue(getMainTable());
//		boolean isExist = getBizInterface().exists("where contractBill.id = '".concat(contractId).concat("'"));
//		if (isExist) {
//			throw new EASBizException(new NumericExceptionSubItem("1", "此合同下已存在合同付款计划，请进行修订操作！"));
//		}
		super.actionAddNew_actionPerformed(e);
	}
	protected String getEditUIName() {
		return ContractOutPayPlanEditUI.class.getName();
	}
	public void onShow() throws Exception{
		super.onShow();
		getBillListTable().setColumnMoveable(true);
		FDCClientHelper.setActionEnable(actionAudit, true);
//		FDCClientHelper.setActionEnable(actionWorkFlowG, false);
		//合同修订增加附件功能         by Cassiel_peng
		FDCClientHelper.setActionEnable(actionAttachment, true);
		FDCClientHelper.setActionEnable(actionRemove, true);
		
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
//		this.actionAuditResult.setVisible(false);
//		this.actionAudit.setVisible(false);
//		this.menuWorkFlow.setVisible(false);
		this.menuBiz.setVisible(false);
		this.tblPayRequestBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.actionModify.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_duizsetting"));
		this.actionModify.setVisible(false);
	}
	protected boolean isShowAttachmentAction() {
		return false;
	}
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		FDCClientHelper.setActionEnable(actionAddNew, true);
		FDCClientHelper.setActionEnable(actionEdit, true);
		FDCClientHelper.setActionEnable(actionLocate, true);
		FDCClientHelper.setActionEnable(actionRefresh, true);
		FDCClientHelper.setActionEnable(actionRemove, true);
		menuEdit.setVisible(true);
	}
	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
	}
	
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		ContractOutPayPlanInfo info=getSelectedInfo();
//		if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
//			if(!info.getRespPerson().getId().toString().equals(SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString())){
//				FDCMsgBox.showWarning("责任人非本人，禁止操作！");
//				SysUtil.abort();
//			}
//		}
		checkAudited(info);
		checkLastVersion(info);
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	private void checkAudited(ContractOutPayPlanInfo info) throws BOSException, EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkLastVersion(ContractOutPayPlanInfo info) throws BOSException, EASBizException {
//		if(!info.isIsLatest()){
//			MsgBox.showWarning(this, "非最新版本不能修订！");
//	        SysUtil.abort();
//		}
	}
	private ContractOutPayPlanInfo getSelectedInfo() throws BOSException, EASBizException {
		checkSelected();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("creator.*");
    	sel.add("auditor.*");
    	sel.add("entry.*");
    	sel.add("entry.bgItem.*");
    	sel.add("entry.paymentType.*");
    	sel.add("contractBill.number");
    	sel.add("contractBill.name");
		sel.add("contractBill.amount");
		sel.add("contractBill.curProject.displayName");
		sel.add("contractBill.orgUnit.displayName");
		sel.add("respPerson.*");
		sel.add("respDept.*");
		return ContractOutPayPlanFactory.getRemoteInstance().getContractOutPayPlanInfo(new ObjectUuidPK(getSelectedKeyValue()),sel);
	}
	protected void cbIsAll_itemStateChanged(ItemEvent e) throws Exception {
		treeSelectChange();
	}
	protected void treeSelectChange() throws Exception {

		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		DefaultKingdeeTreeNode  typeNode  =	getTypeSelectedTreeNode() ;
		
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		Object type  = null;
		if(typeNode!=null){
			type = typeNode.getUserObject();
		}
		FilterInfo filter=getTreeSelectFilter(project,type,containConWithoutTxt());
//		if(!this.cbIsAll.isSelected()){
//			FilterInfo pfilter=new FilterInfo();
//    		if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
//    			pfilter.getFilterItems().add(new FilterItemInfo("contractBill.respPerson.id", SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString()));
//    			filter.mergeFilter(pfilter, "and");
//    		}
//    	}
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
	@Override
	protected String getEditUIModal() {
		// TODO Auto-generated method stub
		return UIFactoryName.NEWTAB;
	}
}