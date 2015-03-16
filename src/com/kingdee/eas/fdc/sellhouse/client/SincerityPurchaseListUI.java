/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.ISincerityPurchase;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
/**
 * output class name
 */
public class SincerityPurchaseListUI extends AbstractSincerityPurchaseListUI {
	private static final Logger logger = CoreUIObject.getLogger(SincerityPurchaseListUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	protected SellProjectInfo sellProject = null;
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	protected boolean isControl=SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo());
	/**
	 * output class constructor
	 */
	public SincerityPurchaseListUI() throws Exception {
		super();
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	public void onLoad() throws Exception {
		initTree();
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		initControl();
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
		
			this.btnPrePur.setEnabled(false);
			this.actionPrePur.setEnabled(false);
			this.btnSignContract.setEnabled(false);
			this.actionSignContract.setEnabled(false);
			this.btnToPurchase.setEnabled(false);
		}
		this.btnChangeRecord.setIcon(EASResource.getIcon("imgTbtn_seeevaluateobject"));
		
		this.actionRevAmount.setVisible(false);
	}
	protected void initControl(){
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	this.treeMain.setSelectionRow(0);
    	   
    	if(!isSaleHouseOrg){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionQuitNumber.setEnabled(false);
			this.actionToPurchase.setEnabled(false);
			this.actionRevAmount.setEnabled(false);
			this.actionQuitAmount.setEnabled(false);
			this.actionBatchPurchase.setEnabled(false);
			
			this.actionPrePur.setEnabled(false);
			this.actionSignContract.setEnabled(false);
			this.actionToPurchase.setEnabled(false);
			this.actionChangeRecord.setEnabled(false);
			
		}
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuSincerPrint.setEnabled(true);
		this.menuSincerPrintPreview.setEnabled(true);
		this.btnSincerPrint.setEnabled(true);
		this.btnSincerPrintPreview.setEnabled(true);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.menuBar.remove(this.menuHelp);
		this.menuBar.add(this.menuHelp);
		
		
		//禁用部分之前按钮
		this.btnBatchPurchase.setEnabled(false);
		this.btnSincerPrint.setEnabled(false);
		this.btnSincerPrintPreview.setEnabled(false);
		this.btnBatchPurchase.setVisible(false);
		this.btnSincerPrint.setVisible(false);
		this.btnSincerPrintPreview.setVisible(false);
		
		FDCHelper.formatTableDate(this.tblMain, "bizDate");
		FDCHelper.formatTableDate(this.tblMain, "validDate");
		FDCHelper.formatTableDate(this.tblMain, "pur.bizDate");
		FDCHelper.formatTableDate(this.tblMain, "pur.busAdscriptionDate");
		FDCHelper.formatTableDate(this.tblMain, "sign.bizDate");
		FDCHelper.formatTableDate(this.tblMain, "sign.busAdscriptionDate");
		
    }
	
//	public void checkIsValid(){
//		for(int i=0 ;i <this.tblMain.getRowCount() ; i++){
//			IRow row = this.tblMain.getRow(i);
//			Date validDate = FDCDateHelper.stringToDate(row.getCell("validDate").getValue().toString());
//			
//			if(SHEHelper.getCurrentTime().after(validDate)){
//				row.getStyleAttributes().setBackground(Color.red);
//			}
//		}
//	}
	
	protected void initListener() {
		super.initListener();

		/**
		 * 做数据的处理来优化性能 by renliang at 2011-3-23
		 */
		this.tblMain.getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						tblMain_afterDataFill(e);
					}
				});
	}

	private void tblMain_afterDataFill(KDTDataRequestEvent e) {

		// 取得当前页的第一行
		int sr = e.getFirstRow();
		if (e.getFirstRow() > 0) {
			sr = sr - 1;
		}
		// 取得当前页的最后一行
		int lr = e.getLastRow();

		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = sr; i <= lr; i++) {
			IRow row = tblMain.getRow(i);
			if (row == null) {
				continue;
			}
			BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
			if(state==null) continue;
    		if(state.getValue().equals(TransactionStateEnum.BAYNUMBER_VALUE)||state.getValue().equals(TransactionStateEnum.WAITTINGFORDEAL_VALUE)){
			Date validDate = FDCDateHelper.stringToDate(row.getCell("validDate").getValue().toString());
			if(SHEHelper.getCurrentTime().after(validDate)){
				row.getStyleAttributes().setBackground(Color.PINK);
			}
    		}
		}
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		//this.execQuery();
	}
	
	/*
	 * 更名记录
	 */
	public void actionChangeRecord_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();// 检查是否选中
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if(row == null){
			MsgBox.showWarning(this, "请选择要更名的认购单！");
			SysUtil.abort();
		}
//		if(row.getCell("sincerityState").getValue().toString().equals(SincerityPurchaseStateEnum.ArrangeNum.toString())){
		if(row.getCell("sincerityState").getValue().toString().equals(TransactionStateEnum.BAYNUMBER.toString())){	
//		Map map = new UIContext(this);
			UIContext uiContext = new UIContext(this);
		uiContext.put("selectedID", row.getCell("id").getValue().toString());
//		uiContext.put("sinPurID", row.getCell("id").getValue().toString());
//			uiContext.put(UIContext.ID, getSelectedKeyValue());

			// 供子类定义要传递到EditUI中扩展的属性
			prepareUIContext(uiContext, e);
			IUIWindow uiWindow = null ; 
			// UIFactoryName.MODEL 为弹出模式
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
			create("com.kingdee.eas.fdc.sellhouse.client.SincerityChangeNameEditUI",uiContext, null,
							OprtState.ADDNEW);
			uiWindow.show();
		}
		
	}
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SincerityPurchaseFactory.getRemoteInstance();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		
		checkSelected();// 检查是否选中
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if(row == null){
			MsgBox.showWarning(this, "请选择要更名的认购单！");
			SysUtil.abort();
		}
		if(null!=row.getCell("sincerityState").getValue()){
			if(row.getCell("sincerityState").getValue().toString().equals(TransactionStateEnum.BAYNUMBER.toString())){
				btnChangeRecord.setEnabled(true);
			}else{
				btnChangeRecord.setEnabled(false);
			}
		}
		
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return SincerityPurchaseChangeNameUI.class.getName();
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//项目
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
				this.actionAddNew.setEnabled(false);
			}else if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		this.refresh(null);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo){
					//项目
					if(node.getUserObject() != null){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllSellProjectCollection(null,sellProject),CompareType.INCLUDE));	
					}
					if(!isControl){
						filter.getFilterItems().add(new FilterItemInfo("saleMansEntry.user.id", NewCommerceHelper.getPermitSaleManIdSql(sellProject, SysContext.getSysContext().getCurrentUserInfo()),CompareType.INNER));
					}
				}else if (node.getUserObject() instanceof OrgStructureInfo){
					//组织
					if(node.getUserObject() != null){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'",CompareType.INNER));
					}
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
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
	}

	
	
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
		//checkIsValid();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		SincerityPurchaseInfo sin = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if(sin.getBizState()!=null) {
			if (sin.getBizState().equals(TransactionStateEnum.QUITNUMBER)) {
				MsgBox.showInfo("已经退号,不能修改!");
				return;
			}
			if (sin.getBizState().equals(TransactionStateEnum.TOPUR)) {
				MsgBox.showInfo("已经转认购,不能修改!");
				return;
			}
			if (sin.isIsRev()) {
				MsgBox.showInfo("已经收款,不能修改!");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionQuitNumber_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQuitNumber_actionPerformed(e);
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow row = this.tblMain.getRow(index);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
		if (state.getValue().equals(TransactionStateEnum.QUITNUMBER_VALUE)) {
			MsgBox.showInfo("已经退号!");
			return;
		}
		if (!state.getValue().equals(TransactionStateEnum.BAYNUMBER_VALUE)) {
			MsgBox.showInfo("此单据状态不适合做本次操作!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "是否确认退号?") == MsgBox.YES) {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("bizState");
			SelectorItemCollection selsr = new SelectorItemCollection();
			selsr.add("room.*");
			selsr.add("transactionID");
			SincerityPurchaseInfo sin1 = ((ISincerityPurchase)this.getBizInterface()).getSincerityPurchaseInfo(
					new ObjectUuidPK(BOSUuid.read((String) row.getCell(
							"id").getValue())),selsr);
			RoomInfo room = sin1.getRoom();
			SelectorItemCollection selsrom = new SelectorItemCollection();
			// 不存在为排号得单据  本房屋未退盘过
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.BAYNUMBER_VALUE));
			if(sin1.getRoom()!=null){
				if(!sin1.getRoom().isIsPush()&&!this.getBizInterface().exists(filter)){
					room.setSellState(RoomSellStateEnum.Init);
					selsr.add("room.sellState");
					RoomFactory.getRemoteInstance().updatePartial(room, selsrom);
				}else{
					room.setSellState(RoomSellStateEnum.OnShow);
					
					selsr.add("room.sellState");
					RoomFactory.getRemoteInstance().updatePartial(room, selsrom);
				}
			}
			
			sin1.setBizState(TransactionStateEnum.QUITNUMBER);
		
			((ISincerityPurchase)this.getBizInterface()).quitNum(sin1, sels);
			MsgBox.showInfo("退号成功!");
			this.refresh(null);
			
			//退号后需要把房间状态改为待售但是一个房间可能对应多个诚意认购单或者是已经被认购了
			//那么只有当退号后这个房间没有有效状态的诚意认购单或者认购单才能变成待售状态
			//认购申请状态的认购单在没有收款的时候房间是待售状态，但是当他收取的预定金超过最低标准后房间就会变成预定状态
//			if(sin.getRoom()!=null)
//			{
//				RoomInfo room = sin.getRoom();
//				//如果没有有效状态的诚意认购单
//				if(!SincerityPurchaseFactory.getRemoteInstance().exists("where (sincerityState='"+SincerityPurchaseStateEnum.ARRANGENUM_VALUE+"' or sincerityState='"
//						+SincerityPurchaseStateEnum.TOPURCHASE_VALUE+"') and room.id='"+room.getId().toString()+"'"))
//				{
//					//也没有有效状态的认购单,把房间状态改为待售
//					//by tim_gao 修改认购单判断条件，不加对认购单的"认购申请"状态判断 2010-10-12
//					
////					修改原来的判断为当当前房间状态为诚意认购的时候且没有其他的认购单去改房间状态为待售，去除原来是有认购单就不对房间状态进行改变 xiaoao_liu
//					if(RoomFactory.getRemoteInstance().exists("where sellState='"+RoomSellStateEnum.SINCERPURCHASE_VALUE+"' and id='"+room.getId().toString() +"'"))
//					{
//						room.setSellState(RoomSellStateEnum.OnShow);
//						//by tim_gao 退号时，如果为未推盘房间，应退回未推盘状态 2010-9-24
//						if(sin.getRoom().getSellOrder()==null)
//						{	
//							sin.getRoom().setSellState(RoomSellStateEnum.Init);
//						}
//						SelectorItemCollection selector = new SelectorItemCollection();
//						selector.add("sellState");
//						RoomFactory.getRemoteInstance().updatePartial(room, selector);
//					}
//				}
//				
//			}
//			
//			
		}
	}
	
	public void actionSincerPrint_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		String sincerPurId = this.getSelectedKeyValue();
		SinerityPrintDataProvider data = new SinerityPrintDataProvider(sincerPurId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SincerityPrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/sincerityPrint", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionSincerPrint_actionPerformed(e);
	}
	
	public void actionSincerPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		String sincerPurId = this.getSelectedKeyValue();
		SinerityPrintDataProvider data = new SinerityPrintDataProvider(sincerPurId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SincerityPrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/sincerityPrint", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionSincerPrintPreview_actionPerformed(e);
	}

	public boolean isNullorZero(Object obj ){
		if(obj==null){
			return false;
		}
		if(FDCHelper.ZERO.equals((BigDecimal)obj)){
			return false;
		}
		return true;
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		//已收款判断
		boolean isRev =false;
		SelectorItemCollection selCol = new SelectorItemCollection();
		
		selCol.add("sincerPriceEntrys.*");
		selCol.add("sincerPriceEntrys.moneyDefine.*");
		selCol.add("*");
		SincerityPurchaseInfo sin = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(id)),selCol);

		
		BigDecimal actAmount = FDCHelper.ZERO;
	
		for(Iterator it = sin.getSincerPriceEntrys().iterator(); it.hasNext();){
			SincerReceiveEntryInfo sinInfo = (SincerReceiveEntryInfo) it.next();
			isRev = isNullorZero(SHEManageHelper.getActRevAmount(null,sinInfo.getTanPayListEntryId()));
			if(!isRev){
				isRev = isNullorZero(sinInfo.getActRevAmount());
			}
			if(isRev){
				break;
			}
		}
		
		if (sin.getBizState().equals(TransactionStateEnum.BAYNUMBER)&& !isRev) {
			checkSelected();
			if (confirmRemove()) {
				Remove();
				refresh(e);
			}
		} else {
			MsgBox.showInfo( "只能删除排号并未收款的预约单!");
		}
	}

	public void actionRevAmount_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("customer.*");
		sels.add("customer.customer.*");
		sels.add("sincerPriceEntrys.*");
		sels.add("sincerPriceEntrys.moneyDefine.*");
		SincerityPurchaseInfo info =((ISincerityPurchase)this.getBizInterface()).getSincerityPurchaseInfo(new ObjectUuidPK(id),sels);
		if(info.isIsValid()||TransactionStateEnum.QUITNUMBER.equals(info.getBizState())||
				TransactionStateEnum.SINNULLIFY.equals(info.getBizState())||TransactionStateEnum.WAITTINGFORDEAL.equals(info.getBizState())){
			
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[info.getCustomer().size()];
		for(int i=0;i<info.getCustomer().size();i++){
			custObjs[i]=info.getCustomer().get(i).getCustomer();
		}
		
		Set transEntryIdSet = new HashSet();
		SincerReceiveEntryCollection signPayListColl = info.getSincerPriceEntrys();
		for (int i = 0; i < signPayListColl.size(); i++) {
			SincerReceiveEntryInfo signPayEntryInfo = signPayListColl.get(i);
			transEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}				
		CRMClientHelper.openTheGatherRevBillWindow(this, null, info.getSellProject(), info ,custObjs,transEntryIdSet);
	}
	
	private SincerityPurchaseInfo getSincerityPurchaseById(String id) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("room.*");
		sels.add("room.building.sellProject");
		sels.add("customer.*");
		sels.add("customer.sysCustomer.*");
		sels.add("sellOrder.*");
		sels.add("salesman.*");
		
		sels.add("sincerPriceEntrys.*");
		sels.add("sincerPriceEntrys.moneyDefine.*");
		
		SincerityPurchaseInfo sin = SincerityPurchaseFactory
				.getRemoteInstance().getSincerityPurchaseInfo(
						new ObjectUuidPK(BOSUuid.read(id)),sels);
		return sin;
	}
	
	public void actionBatchPurchase_actionPerformed(ActionEvent e) throws Exception
    {
		BatchPurchaseUI.showUI(this);
    }

	public void actionQuitAmount_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQuitAmount_actionPerformed(e);
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}  
		
		//TODO 退款要根据选中的纪录  收款是否入帐 来区别处理.如果入帐
		SincerityPurchaseInfo sin = getSincerityPurchaseById(id);
		if(sin.isIsReceiveEnterAccount()){
	
		}else{
			if (!sin.isIsRev()) {
				if (sin.getRevDate() == null) {
					MsgBox.showError("没有收款!");
					return;
				} else {
					MsgBox.showError("已经退款!");
					return;
				}
			}
			sin.setIsRev(false);
			sin.setRevDate(null);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isRev");
			selector.add("revDate");
			SincerityPurchaseFactory.getRemoteInstance().updatePartial(sin, selector);
			MsgBox.showInfo("退款成功!");
			this.refresh(null);
		}
		

	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}

	/**
	 * 转签约
	 */
	public void actionSignContract_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
    	if(!TransactionStateEnum.BAYNUMBER_VALUE.equals(state.getValue())){
    		FDCMsgBox.showWarning("单据状态不适合做此操作！");
    		return;
    	}
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id));
		SHEManageHelper.toTransactionBill(BOSUuid.read(id), info.getBizState(),this,SignManageEditUI.class.getName());
	}
	
	public void actionPrePur_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
    	if(!TransactionStateEnum.BAYNUMBER_VALUE.equals(state.getValue())){
    		FDCMsgBox.showWarning("单据状态不适合做此操作！");
    		return;
    	}
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id));
		SHEManageHelper.toTransactionBill(BOSUuid.read(id), info.getBizState(),this,PrePurchaseManageEditUI.class.getName());
	}
	public void actionToPurchase_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
    	if(!TransactionStateEnum.BAYNUMBER_VALUE.equals(state.getValue())){
    		FDCMsgBox.showWarning("单据状态不适合做此操作！");
    		return;
    	}
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id));
		SHEManageHelper.toTransactionBill(BOSUuid.read(id), info.getBizState(),this,PurchaseManageEditUI.class.getName());
	}
	
	/**
	 * 判断是否允许直接签约 
	 * @param room
	 * @return
	 */
	public boolean getIsImmediacySign(String id){
		SaleOrgUnitInfo sale = SysContext.getSysContext().getCurrentSaleUnit();
		if (sale == null || !sale.isIsBizUnit()) {
			return false;
		}
		boolean debug = false;
		RoomDisplaySetting setting= new RoomDisplaySetting();
		HashMap functionSetMap = (HashMap) setting.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
		if(funcSet == null)	{
			debug = true ;
		}else{
			if(funcSet.getIsActGathering().booleanValue()
				|| funcSet.getIsSignGathering().booleanValue()){
				debug = false ;
			}else{
				debug = true ;
			}
		}
		return debug;
	}
	
	 /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
}