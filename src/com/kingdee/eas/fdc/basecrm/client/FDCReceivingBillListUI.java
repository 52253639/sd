/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCStringHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveCollection;
import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveInfo;
import com.kingdee.eas.fdc.propertymgmt.client.PPMNewReceiveBillEditUI;
import com.kingdee.eas.fdc.propertymgmt.client.PPMNewReceiveBillListUI;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.InvoiceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.MakeOutReceiptUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEReceivingBillEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEReceivingBillListUI;
import com.kingdee.eas.fdc.tenancy.client.DepositDealBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.InvoiceBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.OtherBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.TENReceivingBillListUI;
import com.kingdee.eas.fi.ar.IVerificationBillentry;
import com.kingdee.eas.fi.ar.OtherBillBizException;
import com.kingdee.eas.fi.ar.VerificationBillentryCollection;
import com.kingdee.eas.fi.ar.VerificationBillentryFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCReceivingBillListUI extends AbstractFDCReceivingBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCReceivingBillListUI.class);
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    MarketDisplaySetting setting=new MarketDisplaySetting();
    protected SellProjectInfo curSellProNode = null;
    /**
     * output class constructor
     */
    public FDCReceivingBillListUI() throws Exception
    {
        super();
    }
    
	private void tblMain_afterDataFill(KDTDataRequestEvent e) {
		int sr = e.getFirstRow();
		if(e.getFirstRow() > 0){
			sr = sr - 1;
		}
		
		this.tblMain.getGroupManager().group(sr, e.getLastRow());
	}
    
    public void onLoad() throws Exception {
    	//add by warship at 2010/09/07 修改性能问题
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFill(e);
			}
		});
    	
		super.onLoad();
		initTree();
		initControl();
		//不用实模式的情况会导致数据显示不全
		//this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.treeMain.setSelectionRow(0);	
	}
    
    private void initControl()
	{
    	this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionDelVoucher.setVisible(true);
		this.actionDelVoucher.setEnabled(true);
		this.actionAuditResult.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionReceive.setVisible(true);
		this.actionReceive.setEnabled(true);
		this.actionVoucher.setVisible(true);
		this.actionVoucher.setEnabled(true);
		this.actionAdjust.setVisible(true);
		this.actionAdjust.setEnabled(true);
		this.actionBatchReceiving.setVisible(true);
		this.actionBatchReceiving.setEnabled(true);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionCanceReceive.setVisible(true);
		this.actionCanceReceive.setEnabled(true);
		
		this.actionWorkFlowG.setVisible(true);
		this.actionAuditResult.setVisible(true);
		
		
		this.actionReceipt.setEnabled(true);
		this.actionRetakeReceipt.setEnabled(true);		
		this.actionCreateInvoice.setEnabled(true);
		this.actionClearInvoice.setEnabled(true);

		
		if(!SHEHelper.getCurrentSaleOrg().isIsBizUnit()){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false); 
			this.actionAddNew.setEnabled(false);
		}
	}
    
    protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTree(this.actionOnLoad, this.getSystemType()));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	if(this.getEditUIName().equals(InvoiceBillEditUI.class.getName())||
    			this.getEditUIName().equals(OtherBillEditUI.class.getName())||
    				this.getEditUIName().equals(DepositDealBillEditUI.class.getName())){
    		return super.getQueryExecutor(queryPK, viewInfo);	
    	}
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			
			FilterInfo roomFilter = new FilterInfo();
			if(node==null) {		
				Map spIdMap = FDCTreeHelper.getAllObjectIdMap((DefaultKingdeeTreeNode)treeMain.getModel().getRoot(), "SellProject");
				if(spIdMap.keySet().size()>0)
					roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", FDCTreeHelper.getStringFromSet(spIdMap.keySet()) ,CompareType.INNER));
				else
					roomFilter.getFilterItems().add(new FilterItemInfo("id", "nullnull"));
			}else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
			}else if(node.getUserObject() instanceof SubareaInfo){
				SubareaInfo subAreaInfo = (SubareaInfo)node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("building.subarea.id", subAreaInfo.getId()));
			}else if(node.getUserObject() instanceof BuildingInfo){
				BuildingInfo buildInfo = (BuildingInfo)node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("building.id", buildInfo.getId()));
			}else if(node.getUserObject() instanceof BuildingUnitInfo){
				BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnitInfo.getId()));
			}else{
				Map spIdMap = FDCTreeHelper.getAllObjectIdMap((DefaultKingdeeTreeNode)treeMain.getModel().getRoot(), "SellProject");
				if(spIdMap.keySet().size()>0)
					roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
				else
					roomFilter.getFilterItems().add(new FilterItemInfo("id", "nullnull"));
			}
			if(addAdjustRevBillTypeFilter()){
				roomFilter.getFilterItems().add(new FilterItemInfo("revBillType", RevBillTypeEnum.ADJUST_VALUE));
			}
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)		{
				viewInfo.getFilter().mergeFilter(roomFilter, "and");
			} else		{
				viewInfo.setFilter(roomFilter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		//TODO 增加所属系统的过滤条件
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    protected boolean addAdjustRevBillTypeFilter() {
		return false;
	}
    
    public void refreshListForOrder() throws Exception
	{
		super.refreshListForOrder();
		
		this.refresh(null);
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (saleOrg.isIsBizUnit()) {
			if(node==null || node.getUserObject() instanceof OrgStructureInfo)
				this.actionAddNew.setEnabled(false);
			else
				this.actionAddNew.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
		}		
		
		
		if(node.getUserObject() instanceof SellProjectInfo) {
			curSellProNode = (SellProjectInfo)node.getUserObject();
		}else if(node.getUserObject() instanceof SubareaInfo)  {
			SubareaInfo subAreaInfo = (SubareaInfo)node.getUserObject();
			curSellProNode = subAreaInfo.getSellProject();
		}else if(node.getUserObject() instanceof BuildingInfo){
			BuildingInfo buildInfo = (BuildingInfo)node.getUserObject();
			curSellProNode = buildInfo.getSellProject();
		}else if(node.getUserObject() instanceof BuildingUnitInfo) {
			DefaultKingdeeTreeNode parNode = (DefaultKingdeeTreeNode)node.getParent();
			BuildingInfo buildInfo = (BuildingInfo)parNode.getUserObject();
			curSellProNode = buildInfo.getSellProject();
		}else{
			curSellProNode = null;
		}
		
//		this.execQuery();
		this.refresh(null);
	}
    
    protected void refresh(ActionEvent e) throws Exception
	{
		int actRowNum = this.tblMain.getSelectManager().getActiveRowIndex();
		int actColNum = this.tblMain.getSelectManager().getActiveColumnIndex();
		setColGroups();
		this.tblMain.removeRows();
//		this.tblMain.getGroupManager().group();
		this.tblMain.scrollToVisible(actRowNum, actColNum);
		//Delete by zhiyuan_tang 2010/10/14     
		//不能再重新选中刚才的行。原因： 当选择一行再点击表头进行排序后，再点击查看等按钮就会提示传入编辑界面的ID不正确
		//框架默认是点表头排序之后，不再选择刚才的行，这样就只选择了表头，再点其它按钮就会提示请先选中行。
		//如果重新选中行的话，排序之后就是选择了两行，不会提示请先选择行的提示，会对这两行进行取数，然后取表头行时就挂了。
//		this.tblMain.getSelectManager().select(actRowNum, actColNum);
	}
    
    /**
     * 需要合并的列，子类可以重写
     * */
    protected void setColGroups(){
		setColGroup("id");
		setColGroup("sellProject.name");
		setColGroup("currency.name");
		setColGroup("number");
		setColGroup("amount");
		setColGroup("originalAmount");
		setColGroup("receiptNumber");		
		setColGroup("invoiceNumber");
		setColGroup("bizDate");
		setColGroup("billStatus");
		
		setColGroup("revBillType");
		setColGroup("revBizType");
		setColGroup("customer.name");
		
		//租赁收款单可以有多个房间,不能合并
//		setColGroup("roomLongNumber");
//		setColGroup("room.name");
//		setColGroup("room.number");
		
		setColGroup("fiVouchered");
		setColGroup("state");
		
		setColGroup("createTime");
		setColGroup("description");
    }
    
    protected final void setColGroup(String colKey) {
    	IColumn col = this.tblMain.getColumn(colKey);
    	if(col != null){
    		col.setGroup(true);
    	}
    }

	/**
	 * 在操作选中的列表项之前，检查对象是否存在。不存在则异常通知控制器。 Copy from ListUI
	 */
	protected void checkObjectExists() throws Exception
	{
		if (getSelectedKeyValue() == null)
		{
			return;
		}
		if (!getBizInterface().exists(
				new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue()))))
		{
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				
				/**
				 * bos 平台光标定位问题
				 * by renliang 暂时处理办法
				 */
				if(row == null && rowIndex == -1){
					row = tblMain.getRow(1);
				}
				
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey()).getValue()).getInt();
				if (BillStatusEnum.SUBMIT_VALUE != billStatus)
				{
					MsgBox.showInfo("只有提交状态的收款单才能审批!");
					this.abort();
				}
			}
		}
		List selectedIds = super.getSelectedIdValues();
		
		((IFDCReceivingBill) getBizInterface()).audit(selectedIds);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    /**
     * 物业那边的列不一样,扩展出去修改
     * */
    protected String getBillStatusColKey(){
    	return "billStatus";
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				
				/**
				 * bos 平台光标定位问题
				 * by renliang 暂时处理办法
				 */
				if(row ==null && rowIndex == -1){
					row = tblMain.getRow(1);
				}
				
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey())
						.getValue()).getInt();
				if (BillStatusEnum.AUDITED_VALUE != billStatus)
				{
					MsgBox.showInfo("只有审批状态的收款单才能反审批!");
					this.abort();
				}
			}
		}
		List selectedIds = super.getSelectedIdValues();
		
		((IFDCReceivingBill) getBizInterface()).unAudit(selectedIds);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    public void actionReceive_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		this.checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		ArrayList idList = new ArrayList();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey())
						.getValue()).getInt();
				if (BillStatusEnum.AUDITED_VALUE == billStatus)
				{
					String id = row.getCell("id").getValue().toString();
					if (isCanCanceRec(id))
					{
						idList.add(id);
					}
				}
			}
		}
		if (idList.size() == 0)
		{
			MsgBox.showInfo("只有已审批状态的收款单才能收款!");
			//return;
			SysUtil.abort();
		}
		((IFDCReceivingBill) getBizInterface()).receive(idList);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    public void actionCanceReceive_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
		this.checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		ArrayList idList = new ArrayList();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey())
						.getValue()).getInt();
				if (BillStatusEnum.RECED_VALUE == billStatus)
				{
					String id = row.getCell("id").getValue().toString();
					if (isCanCanceRec(id))
					{
						idList.add(id);
					}
				}
			}
		}
		if (idList.size() == 0)
		{
			MsgBox.showInfo("只有已收款状态的收款单才能取消收款!");
			return;
		}
		((IFDCReceivingBill) getBizInterface()).canceReceive(idList);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
	private boolean isCanCanceRec(String billId) throws BOSException,
	OtherBillBizException
	{
		IVerificationBillentry iVerifyEntryBill = VerificationBillentryFactory
				.getRemoteInstance();
		VerificationBillentryCollection verifyEntryInfoCol = null;
		verifyEntryInfoCol = iVerifyEntryBill
				.getVerificationBillentryCollection("where billID = '" + billId
						+ "'");
		if (verifyEntryInfoCol.size() > 0)
		{
			throw new OtherBillBizException(OtherBillBizException.BILLVERIFIED);
		}
		return true;
	 }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return FDCReceivingBillFactory.getRemoteInstance();
    }
    
    protected MoneySysTypeEnum getSystemType(){
    	return null;
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected String[] getLocateNames()
    {
        return super.getLocateNames();
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
    	if(this.getEditUIName().equals(InvoiceBillEditUI.class.getName())||
    			this.getEditUIName().equals(OtherBillEditUI.class.getName())){
    		super.actionVoucher_actionPerformed(e);	
    		return;
    	}
		checkSelected();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				String id = (String) row.getCell("id").getValue();
				
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("billStatus");
				sels.add("id");
				sels.add("fiVouchered");

				FDCReceivingBillInfo rev = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(id), sels);
								
				if (!RevBillStatusEnum.RECED.equals(rev.getBillStatus()))
				{
					MsgBox.showInfo(this, "只有已收款状态的收款单才能能生成凭证!");
					this.abort();
				}
				
				if(rev.isFiVouchered()){
					MsgBox.showInfo(this, "已经生成凭证的不能再生成!");
					this.abort();
				}
			}
		}
		super.actionVoucher_actionPerformed(e);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    
    protected CoreBillBaseCollection getNewBillList() throws Exception {
		checkSelected();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		Set selIds = new HashSet();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
				IRow row = tblMain.getRow(rowIndex);
				String id = (String) row.getCell("id").getValue();
				if (id != null)
					selIds.add(id);
			}
		}

		if (selIds == null) {
			logger.error("没有选择行？");
			this.abort();
		}

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", selIds, CompareType.INCLUDE));

		EntityViewInfo entityViewInfo = new EntityViewInfo();
		entityViewInfo.getSelector().add(new SelectorItemInfo("id"));
		entityViewInfo.setFilter(filterInfo);

		CoreBillBaseCollection sourceBillCollection = ((ICoreBillBase) getCoreBaseInterface()).getCoreBillBaseCollection(entityViewInfo);
		return sourceBillCollection;
	}
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		this.checkObjectExists();
		String id = this.getSelectedKeyValue();
		if (id == null)
		{
			logger.warn("逻辑错误,仔细检查");
			return;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		FDCReceivingBillInfo receivingBill = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (!RevBillStatusEnum.SAVE.equals(receivingBill.getBillStatus())&& !RevBillStatusEnum.SUBMIT.equals(receivingBill.getBillStatus()))
		{
			MsgBox.showInfo(this, "只有已提交或保存状态的收款单才可以修改！");
			return;
		}
		if (RevBillTypeEnum.transfer.equals(receivingBill.getRevBillType()))		{
			MsgBox.showInfo(this, "转款单不能修改！");
			return;
		}	
		if(isVerifyAdjustBillEditable()){
			if (RevBillTypeEnum.adjust.equals(receivingBill.getRevBillType()))		{
				MsgBox.showInfo(this, "调整单不能修改！");
				return;
			}
		}
		
		
    	super.actionEdit_actionPerformed(e);
    }
    /**
     *描述：是否校验调整单可以编辑
     *@author pu_zhang  2010-11-10
     */
    protected boolean isVerifyAdjustBillEditable() {
    	return true;
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		this.checkObjectExists();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows != null && selectRows.length > 1) {
			MsgBox.showInfo("请选择单行数据进行删除操作！");
			this.abort();
		}
		String id = this.getSelectedKeyValue();
		if (id == null) {
			logger.warn("逻辑错误,仔细检查");
			this.abort();
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("billStatus");
		FDCReceivingBillInfo rev = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)),sels);
		if(!RevBillStatusEnum.SUBMIT.equals(rev.getBillStatus())){
			MsgBox.showInfo("提交状态的单据才能删除！");
			this.abort();
		}
		
		if(confirmRemove()){
			FDCReceivingBillFactory.getRemoteInstance().delete(BOSUuid.read(id), getHandleClazzName());
			MsgBox.showInfo(this, "删除成功！");
			
			this.refresh(null);
		}
		

	}

    protected String getHandleClazzName(){
		return null;
	}
	
	public boolean isNeedShowBOTPRule() {
		return true;
	}
	
	public SelectorItemCollection getBOTPSelectors()
	{
		
		SelectorItemCollection sels = super.getBOTPSelectors();
		
		sels.add("entries.settlementType.*");
		sels.add("entries.revAccount.*");
		sels.add("entries.oppAccount.*");
		sels.add("entries.moneyDefine.*");
		sels.add("entries.revAccountBank.*");
		sels.add("entries.room.*");
		sels.add("entries.room.building.*");
		sels.add("entries.sourceEntries.*");

		sels.add("purchaseObj.*");
		sels.add("sincerityObj.*");
		sels.add("obligateObj.*");
		sels.add("tenancyObj.*");
		sels.add("fdcCustomers.*");
		sels.add("customer.*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("currency.*");
		sels.add("company.*");
		sels.add("*");
		
		return sels;
		
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		
		sels.add("entries.*");
		sels.add("entries.revAccount.*");
		sels.add("entries.oppAccount.*");
		return sels;
	}
	
	
	public void actionAdjust_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		checkObjectExists();
		
		String idStr = this.getSelectedKeyValue();
		if(idStr==null) return;
		
		if(confirmDialog("确认要生成调整单吗？")){
			Map map = FDCReceivingBillFactory.getRemoteInstance().adjust(BOSUuid.read(idStr));
			MsgBox.showInfo("调整成功！");
			FDCReceivingBillInfo adjustInfo = (FDCReceivingBillInfo)map.get("adjustInfo"); 
			showAdjustReceivingUI(adjustInfo);

			this.refresh(null);
		}
		
	}
	/**
	 * @param gCol
	 * @param mCol
	 * @throws UIException
	 * @throws Exception
	 */
	private void showAdjustReceivingUI(FDCReceivingBillInfo adjustInfo) throws UIException, Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("adjustInfo", adjustInfo);
		uiContext.put("ID", adjustInfo.getId());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SHEReceivingBillEditUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		this.refresh(null);
	}
	
	public void actionReceipt_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		String idStr = this.getSelectedKeyValue();
		FDCReceivingBillInfo revInfo = FDCReceivingBillFactory.getRemoteInstance()
				.getFDCReceivingBillInfo("select billStatus,receiptState,billType,revBillType," +
						"amount,room.name,room.number,customer.name,customer.number,room.lastPurchase,sellProject.id,sellProject.number,sellProject.name where id='"+idStr+"'");

		if (!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED)	&& !revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
			MsgBox.showWarning(this, "请先审批该收款单!");
			return;
		}
		if (revInfo.getReceiptState()!=null
				&& revInfo.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)) {
			MsgBox.showWarning(this, "已换发票不可再开换收据!");
			return;
		}
		if (!revInfo.getRevBillType().equals(RevBillTypeEnum.transfer)
				&& !revInfo.getRevBillType().equals(RevBillTypeEnum.gathering)) {
			MsgBox.showWarning(this, "只有收款或转款类型的收款单才可以开换收据!");
			return;
		}

		Map ctx = new UIContext(this);
		ctx.put("recID", idStr);
		ctx.put(UIContext.OWNER, this);
		ctx.put("room", revInfo.getRoom());
		ctx.put("customer", revInfo.getCustomer());
		ctx.put("SystemType", this.getSystemType());
		ctx.put("thisactionReceipt", Boolean.valueOf(true));
		ctx.put("chequeType", ChequeTypeEnum.receipt);
		ctx.put("sellProjectId", revInfo.getSellProject().getId().toString());

		IUIWindow uiWindow = null;
		// 弹出模式
//		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
//				MakeOutReceiptUI.class.getName(), ctx, null, OprtState.ADDNEW);
		
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(InvoiceEditUI.class.getName(), ctx, null,	"ADDNEW");

		// 开始展现UI
		uiWindow.show();
		refresh(null);
	}
	
	public void actionRetakeReceipt_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		String idStr = this.getSelectedKeyValue();
		
		
		IFDCReceivingBill fdcReceivingBill = FDCReceivingBillFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", idStr, CompareType.EQUALS));
		evi.setFilter(filterInfo);

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("receiptState"));
		coll.add(new SelectorItemInfo("receipt.id"));
		coll.add(new SelectorItemInfo("receipt.number"));
		coll.add(new SelectorItemInfo("receiptNumber"));
		coll.add(new SelectorItemInfo("room.id"));
		coll.add(new SelectorItemInfo("customer.id"));
		evi.setSelector(coll);

		FDCReceivingBillCollection collection = fdcReceivingBill.getFDCReceivingBillCollection(evi);
		
		if(collection!=null && collection.size()>0){
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory.getRemoteInstance();
			for (int j = 0; j < collection.size(); j++) {
				FDCReceivingBillInfo revInfo = collection.get(j);
				
				int ret = MsgBox.showConfirm3(this, "是否回收同收据的其它收款单?");
				if (ret == MsgBox.NO)	{	
					//需要考虑手工填写收据号的情况 2010.10.08
					if(revInfo.getReceiptState() != null && revInfo.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)){
						MsgBox.showWarning(this, "已开发票，不能直接回收收据!");
						SysUtil.abort();
					}
					if (revInfo.getReceiptState() != null
							&& revInfo.getReceiptState().equals(ReceiptStatusEnum.HasMake)) {
						if (( revInfo.getReceipt()==null || FDCStringHelper.isEmpty(revInfo.getReceipt().getNumber()) ) && FDCStringHelper.isEmpty(revInfo.getReceiptNumber())) {
							MsgBox.showWarning(this, "该收款单暂无收据!");
						} else {
							Map paramMap = new HashMap();
							paramMap = getRetakeReceivingBillIDs(revInfo);
							paramMap.put("receivingBillInfo", revInfo);
							if(revInfo.getReceipt()!=null && revInfo.getReceipt().getNumber()!=null){
								facade.retackReceipt(idStr, null, revInfo.getReceipt().getNumber(), null,paramMap);
							}else if(!FDCStringHelper.isEmpty(revInfo.getReceiptNumber())){
								facade.retackReceipt(idStr, null, revInfo.getReceiptNumber(), null,paramMap);
							}
							MsgBox.showWarning(this, "回收收据成功!");
							refresh(null);
						}
					} else {
						MsgBox.showWarning(this, "该收款单暂无收据!");
					}
				}else if(ret == MsgBox.YES){	
					Map paramMap = new HashMap();
					//需要考虑手工填写收据号的情况   2010.10.08
					paramMap = getRetakeReceivingBillIDs(revInfo);
					paramMap.put("receivingBillInfo", revInfo);
					if (((Set)paramMap.get("retakeReceivingBillidSet"))!=null && ((Set)paramMap.get("retakeReceivingBillidSet")).size()>0) {
						facade.retackReceiptBatch(paramMap, null, null, null);
						MsgBox.showWarning(this, "回收收据成功!");
						refresh(null);
					} else {
						MsgBox.showWarning(this, "该收款单暂无收据!");
					}
				}else	{
					return;
				}
			}
		}
	}
	
	
    /**
     * 描述： 获取同一个收款单下面，有收据的收款单。状态非空,已开发票,有收据(启用统一发票或者不启用统一发票)
     * @return map
     * 			收据的收款单IDSMap
     * @throws Exception
     * @author pu_zhang  2010-11-10
     */
	private Map getRetakeReceivingBillIDs(FDCReceivingBillInfo revInfo) throws Exception {
		Map reMap = new HashMap();
		Set idSet = new HashSet();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		if(revInfo.getRoom()!=null){
			filterInfo.getFilterItems().add(new FilterItemInfo("room", revInfo.getRoom().getId(), CompareType.EQUALS));	
		}
		filterInfo.getFilterItems().add(new FilterItemInfo("customer", revInfo.getCustomer().getId(), CompareType.EQUALS));
		evi.setFilter(filterInfo);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("receiptState"));
		coll.add(new SelectorItemInfo("receipt.id"));
		coll.add(new SelectorItemInfo("receipt.number"));
		coll.add(new SelectorItemInfo("receiptNumber"));
		evi.setSelector(coll);
		FDCReceivingBillCollection collection =  FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection(evi);
		if(collection!=null && collection.size()>0){
			for (int j = 0; j < collection.size(); j++) {
				FDCReceivingBillInfo revBillInfo = collection.get(j);
				//过滤出的单据   状态非空,已开发票,有收据(启用统一发票或者不启用统一发票)
				if(revBillInfo.getReceiptState() != null && !revBillInfo.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)
					&& ( !( revBillInfo.getReceipt()==null || FDCStringHelper.isEmpty(revBillInfo.getReceipt().getNumber()) )
					|| !FDCStringHelper.isEmpty(revBillInfo.getReceiptNumber()))) {
					//必须是同一个收据号才回收
					if(!( revBillInfo.getReceipt()==null || FDCStringHelper.isEmpty(revBillInfo.getReceipt().getNumber())) &&  revInfo.getReceipt()!=null   && revBillInfo.getReceipt().getId().equals(revInfo.getReceipt().getId())){
						idSet.add(revBillInfo.getId());
					}else if(!FDCStringHelper.isEmpty(revBillInfo.getReceiptNumber()) &&  revBillInfo.getReceiptNumber().equals(revInfo.getReceiptNumber())){
						idSet.add(revBillInfo.getId());
					}
					
				}
			}
		}
		reMap.put("retakeReceivingBillidSet", idSet);
		return reMap;	
	}

	public void actionCreateInvoice_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		UIContext uiContext = new UIContext(this);
		if (id != null)
		{
			FDCReceivingBillInfo revInfo = FDCReceivingBillFactory.getRemoteInstance()
					.getFDCReceivingBillInfo("select amount,room.name,room.number,customer.name,customer.number,billStatus ,sellProject.id,sellProject.number,sellProject.name where id='"+id+"'");
			if (revInfo.getAmount().compareTo(FDCHelper.ZERO) < 0)			{
				MsgBox.showInfo("红字不能开发票!");
				return;
			}
			if (!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED)	&& !revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
				MsgBox.showWarning(this, "请先审批该收款单!");
				return;
			}
			uiContext.put("room", revInfo.getRoom());
			uiContext.put("customer", revInfo.getCustomer());
			uiContext.put("SystemType", this.getSystemType());
			uiContext.put("thisactionCreateInvoice", Boolean.valueOf(true));
			uiContext.put("chequeType", ChequeTypeEnum.invoice);
			uiContext.put("sellProjectId", revInfo.getSellProject().getId().toString());
		}
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(InvoiceEditUI.class.getName(), uiContext, null,	"ADDNEW");
		uiWindow.show();
		this.refresh(null);	
	}
	
	public void actionClearInvoice_actionPerformed(ActionEvent e) throws Exception {

		super.actionClearInvoice_actionPerformed(e);
		String id = this.getSelectedKeyValue();
		if (id != null)
		{
			FDCReceivingBillInfo revInfo = FDCReceivingBillFactory.getRemoteInstance()
			.getFDCReceivingBillInfo("select invoice,amount,room.name,room.number where id='"+id+"'");
			
			InvoiceInfo invoice = revInfo.getInvoice();
			if (invoice == null)			{
				MsgBox.showInfo("选择收款单没有开发票!");
				SysUtil.abort();
			}
			// 增加将状态改为换发票之前状态，并更新操作记录 update by jiyu_guan
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory.getRemoteInstance();
			int ret = MsgBox.showConfirm3(this, "是否清除同发票的其他收款单?");
			if (ret == MsgBox.YES)	{
				facade.clearInvoice(invoice.getId().toString(), true);
				
				/**
				 *CASE WHEN FReceiptNumber is Null
				 *sql语法错误 
				 *update by renliang 2010-9-11
				 */
				String sql =null;
				if(setting.getMarkInvoice()==32){
					sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptID is Null THEN '0' ELSE '1' END) " +
					"where FInvoiceID = '"	+ invoice.getId().toString() + "'";
				}else{
				    sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptNumber is Null THEN '0' ELSE '1' END) " +
						"where FInvoiceID = '"	+ invoice.getId().toString() + "'";
				}
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				builder.executeUpdate();
				
				InvoiceFactory.getRemoteInstance().delete(new ObjectUuidPK(invoice.getId()));
				FDCMsgBox.showWarning(this, "发票清除成功！");
			} else if (ret == MsgBox.NO)	{
				facade.clearInvoice(revInfo.getId().toString(), false);
				
				/**
				 *CASE WHEN FReceiptNumber is Null
				 *sql语法错误 
				 *update by renliang 2010-9-11
				 */
				String sql=null;
				if(setting.getMarkInvoice()==32){//启用票据统一管理
					sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptID is Null THEN '0' ELSE '1' END) " +
					 "where FID = '"	+ revInfo.getId() + "'";
				}else{
					sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptNumber is Null THEN '0' ELSE '1' END) " +
					"where FID = '"	+ revInfo.getId() + "'";
				}
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				builder.executeUpdate();
				FDCReceivingBillCollection coll = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection("select id  where invoice.id = '"+invoice.getId().toString()+"'" );
				if(coll.size()==0){
					InvoiceFactory.getRemoteInstance().delete(new ObjectUuidPK(invoice.getId()));
				}
				FDCMsgBox.showWarning(this, "发票清除成功！");
			} else	{
				return;
			}
			this.refresh(null);
		}
			
	}
	
	
	/**
	 * by jinzhicheng
	 * at renliang 2010-7-26
	 */
	public IUIObject getInstance(Map uiContext) {
		if(uiContext.get("BOTPFilter") != null  ||  uiContext.get("isFromWorkflow") != null){
			try {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("revBizType");
				FDCReceivingBillCollection revs = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection((EntityViewInfo) uiContext.get("BOTPFilter"));
				if(revs != null  &&  !revs.isEmpty()){
					RevBizTypeEnum revBizType = revs.get(0).getRevBizType();
					if(RevBizTypeEnum.manage.equals(revBizType)){
						PPMNewReceiveBillListUI rev = new PPMNewReceiveBillListUI();
						return rev;
					}else if(RevBizTypeEnum.obligate.equals(revBizType) || RevBizTypeEnum.tenancy.equals(revBizType)){
						TENReceivingBillListUI rev = new TENReceivingBillListUI();
						return rev;
					}else{
						//售楼的
						return new SHEReceivingBillListUI();
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getInstance(uiContext);
	}

	
	


	
}