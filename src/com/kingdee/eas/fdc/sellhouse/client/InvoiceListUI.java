/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.InvoiceCollection;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class InvoiceListUI extends AbstractInvoiceListUI {
	private static final Logger logger = CoreUIObject
	.getLogger(InvoiceListUI.class);
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	/**
	 * output class constructor
	 */
	public InvoiceListUI() throws Exception {
		super();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
	throws Exception {
		super.tblMain_tableClicked(e);
	}
	
	public void actionInvoicePrint_actionPerformed(ActionEvent e) throws Exception
	{
		
		/**
		 * 不知道为什么权限不起作用，老是返回的是true
		 * 结果手动调用后正确，奇怪的问题
		 * by renliang at 2010-11-20
		 * 
		 */
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(user.getId()), new ObjectUuidPK(org.getId()), "she_invoice_print");
		
		
		this.checkSelected();
		String invoiceID = this.getSelectedKeyValue();
		Map map = new HashMap();
		//将系统类型,房间ID,客户ID放到Map传到InvoicePrintDataProvider中,以便找到票据对应的收款单，在套打收款单分录信息
		map = getParamForReceivingBill(invoiceID);
		InvoicePrintDataProvider data = new InvoicePrintDataProvider(map,invoiceID,new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ReceiveInvoicePrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/invoice", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionInvoicePrint_actionPerformed(e);
	}
	
	/**
	 * output actionInvoicePrintView_actionPerformed method
	 */
	public void actionInvoicePrintView_actionPerformed(ActionEvent e) throws Exception
	{
		
		/**
		 * 不知道为什么权限不起作用，老是返回的是true
		 * 结果手动调用后正确，奇怪的问题
		 * by renliang at 2010-11-20
		 * 
		 */
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(user.getId()), new ObjectUuidPK(org.getId()), "she_invoice_printview");
		
		this.checkSelected();
		String invoiceID = this.getSelectedKeyValue();
		Map map = new HashMap();
		//将系统类型,房间ID,客户ID放到Map传到InvoicePrintDataProvider中,以便找到票据对应的收款单，在套打收款单分录信息
		map = getParamForReceivingBill(invoiceID);
		InvoicePrintDataProvider data = new InvoicePrintDataProvider(map,invoiceID,new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ReceiveInvoicePrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/invoice", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionInvoicePrintView_actionPerformed(e);
	}
	
	private Map getParamForReceivingBill(String invoiceID){
		Map map = new HashMap();
		try{
			String roomId = null;
			String sysCustId = null;
			MoneySysTypeEnum sysType = (MoneySysTypeEnum)this.getUIContext().get("SystemType");
			sysType =sysType==null?MoneySysTypeEnum.SalehouseSys:sysType;
			map.put("sysType",sysType);
			
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("room.id");
			coll.add("customer.*");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",invoiceID));
			view.setSelector(coll);
			view.setFilter(filter);
			InvoiceCollection invoiceColl = InvoiceFactory.getRemoteInstance().getInvoiceCollection(view);
			if(invoiceColl!= null && invoiceColl.size()>0 ){
				InvoiceInfo invoiceInfo = invoiceColl.get(0);
				RoomInfo roomInfo = (RoomInfo)invoiceInfo.getRoom();
				if(roomInfo!=null){
					roomId = roomInfo.getId().toString();
				}
				FDCCustomerInfo fdcCustInfo = (FDCCustomerInfo)invoiceInfo.getCustomer();
				if(fdcCustInfo!=null){
					sysCustId = fdcCustInfo.getSysCustomer().getId().toString();
				}
			}
			map.put("roomId",roomId);
			map.put("sysCustId",sysCustId);
		} catch (BOSException e) {
			handleException(e);
		}
		return map;
	}
	
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
	throws Exception {
//		super.tblMain_tableSelectChanged(e);
	}
	
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.execQuery();
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(allSpIdStr.trim().length()==0)
			allSpIdStr = "'nullnull'"; 
		
		try	{
			FilterInfo filter = new FilterInfo();
			FilterInfo filterNullRoom = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId().toString()));
					filterNullRoom.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId().toString()));
				}else if (node.getUserObject() instanceof SubareaInfo)		{
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("subarea.id", subInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo)		{
					//BuildingInfo bdInfo = (BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
					BuildingUnitInfo buInfo = (BuildingUnitInfo)node.getUserObject();
					/*filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("room.unit", new Integer(buInfo.getSeq())));
					现在已近改为buildUnit这个字段 ，这里的过滤条件也改掉 xiaoao_liu*/
					filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buInfo.getId().toString()));
				}
			}
			if(filter.getFilterItems().size()==0)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr ,CompareType.INNER));
			
			if(filterNullRoom.getFilterItems().size()==0){
				filterNullRoom.getFilterItems().add(new FilterItemInfo("project.id", allSpIdStr ,CompareType.INNER));
			}
			
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
				viewInfo.getFilter().mergeFilter(filterNullRoom, "or");
			} else
			{
				filter.mergeFilter(filterNullRoom, "or");
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return InvoiceFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return InvoiceEditUI.class.getName();
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	/**
	 * 产生编辑UI的方式，缺省是Dialog方式(即UIFactoryName)
	 */
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getColumn("amount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.actionInvoicePrint.setEnabled(true);
		this.actionInvoicePrintView.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		//add by jiyu_guan
		actionChangeInvoice.setEnabled(true);
		this.setUITitle("票据管理");
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		this.execQuery();
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}
	
	/**
	 * 把销售项目的id传递给edit界面
	 * new update by renliang 2011-1-20
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		SellProjectInfo spInfo = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node.getUserObject() instanceof OrgStructureInfo){
			FDCMsgBox.showWarning(this,"请选择下级节点!");
			SysUtil.abort();
		}
		spInfo = findSellProjectNode(node);
		if(spInfo!=null){
			uiContext.put("sellProjectId", spInfo.getId().toString());
		}else{
			FDCMsgBox.showWarning(this,"请先选择节点!");
			SysUtil.abort();
		}
		
		super.prepareUIContext(uiContext, e);
	}
	
	/**
	 * 递归拿到销售项目
	 * add by renliang at 2011-1-20
	 * @param node
	 * @return
	 */
	private SellProjectInfo findSellProjectNode(DefaultKingdeeTreeNode node) {
		if(node!=null){
			SellProjectInfo spInfo = null;
			if (node.getUserObject() instanceof SellProjectInfo)		{
				spInfo = (SellProjectInfo)node.getUserObject();
				return spInfo;
			}else{
				return findSellProjectNode((DefaultKingdeeTreeNode)node.getParent());
			}
		}
		return null;
	}
	
	
	/**
	 * add by jiyu_guan
	 */
	public void actionChangeInvoice_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);

		Map ctx = new UIContext(this);
		String invID = (String) row.getCell("id").getValue();
		ChequeTypeEnum invType= ChequeTypeEnum.getEnum(((com.kingdee.bos.dao.query.BizEnumValueDTO)row.getCell("chequeType").getValue()).getValue().toString());
		ctx.put("invID", invID);
		//传一个类型过去 区分是发票还是收据
		ctx.put("invType", invType);
		ctx.put(UIContext.OWNER, this);

		IUIWindow uiWindow = null;
		// 弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				MackOutInvoiceUI.class.getName(), ctx, null, OprtState.ADDNEW);

		// 开始展现UI
		uiWindow.show();
		// 清缓存，重新从数据库查一次
		CacheServiceFactory.getInstance().discardType(
				new InvoiceInfo().getBOSType());
		refresh(null);
	}
	
}