/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.btp.client.BTPRelationNavUI;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.BankPaymentCollection;
import com.kingdee.eas.fdc.sellhouse.BankPaymentFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.client.CasReceivingBillUI;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreBillListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ReceiveGatherListUI extends AbstractReceiveGatherListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ReceiveGatherListUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public ReceiveGatherListUI() throws Exception
    {
        super();
    }
    
    protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad, this.getSystemType()));
//    	this.treeMain.setModel(FDCTreeHelper.getSellProjectTree(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initControl()
    {
    	this.actionAttachment.setVisible(false);
    	this.actionRevGather.setVisible(true);
    	this.actionRevGather.setEnabled(true);
    	this.actionAddNew.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.actionVoucher.setVisible(true);
		this.actionVoucher.setEnabled(true);
//		this.actionCreateTo.setVisible(true);
		this.actionTraceUp.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.acitonRelateReceivingBill.setVisible(true);
		this.acitonRelateReceivingBill.setEnabled(true);
//		this.actionCreateReceivingBill.setVisible(true);
//		this.actionCreateReceivingBill.setEnabled(true);
		this.menuAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
		this.menuUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
		
		this.btnRevGather.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
		this.menuRevGather.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_new"));
		
		this.btnRelateReceivingBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
		this.menuRelateReceivingBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_view"));
		this.actionCreateReceivingBill.setVisible(false);
		
		this.actionRevGather.setVisible(false);
    }
    
    public void onLoad() throws Exception {
    	this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFill(e);
			}
		});
    	
		super.onLoad();
		initTree();
		initControl();
		
		//不用实模式的情况会导致数据显示不全
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.treeMain.setSelectionRow(0);	
    }
    
    public void actionCreateReceivingBill_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String)row.getCell("id").getValue();
		String auditorName = (String) row.getCell("auditor.name").getValue();
    	boolean boo = ((Boolean)row.getCell("fiRevOrPay").getValue()).booleanValue();
    	if(boo)
    	{
    		MsgBox.showInfo("已生成出纳收款单，不允许再次生成!");
			return;
    	}else if(auditorName==null)
    	{
    		MsgBox.showInfo("出纳汇总单未审批!");
			return;
    	}else
    	{
    		ReceiveGatherFactory.getRemoteInstance().createReceivingBill(BOSUuid.read(id));
    		MsgBox.showWarning(this, "出纳收款单生成成功！");
			SysUtil.abort();
    	}
    }
    
    public void acitonRelateReceivingBill_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String)row.getCell("id").getValue();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sourceBillId", id, CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		ReceivingBillCollection recColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(view);
		if(recColl.size()>0)
		{
			ReceivingBillInfo recInfo = recColl.get(0);
			UIContext ctx = new UIContext(this);
			ctx.put(ctx.ID, recInfo.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(CasReceivingBillUI.class.getName(), ctx, null,
							OprtState.VIEW);
			uiWindow.show();
		}else
		{
			MsgBox.showInfo("没有对应的出纳收款单!");
			return;
		}
	}

	private void tblMain_afterDataFill(KDTDataRequestEvent e) {
		int sr = e.getFirstRow();
		if(e.getFirstRow() > 0){
			sr = sr - 1;
		}
		
		this.tblMain.getGroupManager().group(sr, e.getLastRow());
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	FilterInfo filter = new FilterInfo();
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
//    	this.actionRevGather.setEnabled(false);
    	if(thisNode!=null) {  
	    	if(thisNode.getUserObject() instanceof SellProjectInfo){
//	    		if(isSaleHouseOrg && thisNode.isLeaf()){
//		    		this.actionRevGather.setEnabled(true);
//	    		}
	    		SellProjectInfo spInfo = (SellProjectInfo)thisNode.getUserObject();
	    		filter.getFilterItems().add(new FilterItemInfo("project.longNumber",spInfo.getLongNumber() + "%",CompareType.LIKE));
	    	}else{
	    		Map spIdMap = FDCTreeHelper.getAllObjectIdMap(thisNode, "SellProject");
	    		if(spIdMap.size()>0)
	    			filter.getFilterItems().add(new FilterItemInfo("project.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
	    		else
	    			filter.getFilterItems().add(new FilterItemInfo("project.id",null));
	    	}
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("project.id",null));
    	}
		viewInfo = (EntityViewInfo)this.mainQuery.clone();
		if(viewInfo.getFilter()==null)
			viewInfo.setFilter(filter);
		else{
			try {
				viewInfo.getFilter().mergeFilter(filter,"AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	this.execQuery();
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		
		if(node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();
			if(sellProjectInfo!=null){
				uiContext.put("sellProject", sellProjectInfo);
			}
		}
	}
    
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("出纳汇总单未审批!");
			return;
		}
    	super.actionVoucher_actionPerformed(e);
    }
    
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String)row.getCell("id").getValue();
    	ReceiveGatherFactory.getRemoteInstance().deleteVoucher(new ObjectUuidPK(id));
    	MsgBox.showInfo("删除成功");
    	refresh(e);
//    	super.actionDelVoucher_actionPerformed(e);
    }
    
    public void actionRevGather_actionPerformed(ActionEvent e) throws Exception {
    	UIContext uiContext = new UIContext(this);
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		
		if(node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();
			if(sellProjectInfo!=null){
				uiContext.put("sellProject", sellProjectInfo);
			}
		}	
    	IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow curDialog = uiFactory.create(ReceiveGatherFilterListUI.class.getName(),uiContext, null);
		curDialog.show();
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo bankState = (BizEnumValueInfo) row.getCell("state").getValue();
		if (bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)|| bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE))
		{
			MsgBox.showInfo("已审批的单据不能删除!");
			return;
		}
		String id = (String)row.getCell("id").getValue();
		EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("receiveGather.id", id,CompareType.EQUALS));
    	view.setFilter(filter);
    	BankPaymentCollection bankColl = BankPaymentFactory.getRemoteInstance().getBankPaymentCollection(view);
    	if(bankColl.size()>0)
    	{
    		MsgBox.showInfo("银行放款生成的出纳汇总单不允许直接删除，放款单反审批会自动删除!");
    		this.abort();
    	}
    	super.actionRemove_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo bankState = (BizEnumValueInfo) row.getCell("state").getValue();
		if (bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)|| bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE))
		{
			MsgBox.showInfo("已审批的单据不能修改!");
			return;
		}
    	super.actionEdit_actionPerformed(e);
    }
    
    /*
     * 出纳汇总单审批
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo bankState = (BizEnumValueInfo) row.getCell("state").getValue();
		if (bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)|| bankState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE))
		{
			MsgBox.showInfo("出纳汇总单已审批!");
			return;
		}
		ReceiveGatherFactory.getRemoteInstance().audit(BOSUuid.read(getSelectedKeyValue()));
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
    }
    
    //反审批
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("出纳汇总单未审批!");
			return;
		}
		
		if (MsgBox.showConfirm2New(this, "确认反审批吗?") == MsgBox.YES)
		{
			ReceiveGatherFactory.getRemoteInstance().unAudit(BOSUuid.read(getSelectedKeyValue()));
			FDCClientUtils.showOprtOK(this);
			this.refresh(null);
		}
    }    
    
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String)row.getCell("id").getValue();
		Set idSet = FMHelper.getSrcBillIdCollBySrcId(null,id);
		//通过ID找到原单据ID的集合，说明凭证是汇总单生成
		if(idSet.size()>0)
		{
			super.actionTraceDown_actionPerformed(e);
		}else
		{
			//否则说明是出纳收款单生成的
			Map uiContext = getUIContext();			
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("receivingBill.id");
	    	ReceiveGatherInfo revGather = (ReceiveGatherInfo) ReceiveGatherFactory.getRemoteInstance().getValue(new ObjectUuidPK(id), sels);
			uiContext.put("billInfoID", revGather.getReceivingBill().getId().toString()); // 注意，这里的billID是44位长的BOSUuid，类型是String
			uiContext.put("findType", new Integer(CoreBillListUI.TRACE_DOWN)); // 表示下查
//			uiContext.put("selectedEntries", entryIDs);
			uiContext.put("findBillType", "down");
			
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow window = uiFactory.create(BTPRelationNavUI.class.getName(), uiContext,null);

			window.show();
		}
    	
    }
    
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		boolean boo = ((Boolean)row.getCell("fiRevOrPay").getValue()).booleanValue();
		if(boo)
		{
			MsgBox.showInfo("已生成出纳收款单不能再次生成!");
			this.abort();
		}
		super.actionCreateTo_actionPerformed(e);
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return ReceiveGatherFactory.getRemoteInstance();
    }
    
    protected MoneySysTypeEnum getSystemType(){
    	return MoneySysTypeEnum.SalehouseSys;
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected String[] getLocateNames() {
    	return super.getLocateNames();
    }
    
    protected String getEditUIName()
	{
		return ReceiveGatherEditUI.class.getName();
	}
    
    protected String getEditUIModal() {
    	return UIFactoryName.MODEL;
    }
}