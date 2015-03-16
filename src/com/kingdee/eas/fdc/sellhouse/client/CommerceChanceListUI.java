/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.User;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.market.client.QuestionPaperAnswerEditUI;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class CommerceChanceListUI extends AbstractCommerceChanceListUI {
	private static final Logger logger = CoreUIObject.getLogger(CommerceChanceListUI.class);

	private String allProjectIds = "null"; // 所包含所有的销售项目id

	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();

	/**
	 * output class constructor
	 */
	public CommerceChanceListUI() throws Exception {
		super();
	}
	
	protected void execQuery() {
		super.execQuery();
		this.tblMain.removeRows();
		int tempTop = 0;
		String tempReference = "";
//		重新绑定formatXML --jiadong
		this.tblMain.checkParsed();
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
			IRow row = this.tblMain.getRow(i);
//			Null判断，这里报过NULL异常。
			if(row == null || row.getCell("id") == null || row.getCell("id").getValue() == null){
				continue;
			}
			String id = (String)row.getCell("id").getValue();
						
			if (!tempReference.equalsIgnoreCase(id))
			{
				tempTop = i;
				tempReference = id;
			} else
			{
				// 融合单元格
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("number"), i, this.tblMain.getColumnIndex("number"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("name"), i, this.tblMain.getColumnIndex("name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("fdcCustomer.number"), i, this.tblMain.getColumnIndex("fdcCustomer.number"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("fdcCustomer.name"), i, this.tblMain.getColumnIndex("fdcCustomer.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("commerceStatus"), i, this.tblMain.getColumnIndex("commerceStatus"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("sellProject.name"), i, this.tblMain.getColumnIndex("sellProject.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("commerceLevel.name"), i, this.tblMain.getColumnIndex("commerceLevel.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("commerceDate"), i, this.tblMain.getColumnIndex("commerceDate"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("intentBuildingPro"), i, this.tblMain.getColumnIndex("intentBuildingPro"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("intentProductType"), i, this.tblMain.getColumnIndex("intentProductType"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("intentArea"), i, this.tblMain.getColumnIndex("intentArea"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("intentDirection"), i, this.tblMain.getColumnIndex("intentDirection"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("intentSight"), i, this.tblMain.getColumnIndex("intentSight"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("intentRoomType"), i, this.tblMain.getColumnIndex("intentRoomType"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("roomForm.name"), i, this.tblMain.getColumnIndex("roomForm.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("intendingMoney"), i, this.tblMain.getColumnIndex("intendingMoney"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("firstPayAmount"), i, this.tblMain.getColumnIndex("firstPayAmount"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("firstPayProportion.name"), i, this.tblMain.getColumnIndex("firstPayProportion.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("hopedPitch.name"), i, this.tblMain.getColumnIndex("hopedPitch.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("hopedTotalPrices.name"), i, this.tblMain.getColumnIndex("hopedTotalPrices.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("buyHouseReason.name"), i, this.tblMain.getColumnIndex("buyHouseReason.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("hopedUnitPrice.name"), i, this.tblMain.getColumnIndex("hopedUnitPrice.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("productDetail.name"), i, this.tblMain.getColumnIndex("productDetail.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("hopedFloor.name"), i, this.tblMain.getColumnIndex("hopedFloor.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("saleMan.name"), i, this.tblMain.getColumnIndex("saleMan.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("creator.name"), i, this.tblMain.getColumnIndex("creator.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("createTime"), i, this.tblMain.getColumnIndex("createTime"));
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.menuBiz.setVisible(true);
		this.actionAddTrackRecord.setEnabled(true);
		this.actionModifyTrackRecord.setEnabled(true);
		this.actionDelTrackRecord.setEnabled(true);
		this.actionTerminate.setEnabled(true);
		this.menuExcelImport.setEnabled(true);
		// this.actionImportantTrack.setEnabled(true);
		// this.actionCancelImportantTrack.setEnabled(true);
		// this.actionCustomerAdapter.setEnabled(true);
		// this.actionCustomerShare.setEnabled(true);
		// this.actionCustomerCancelShare.setEnabled(true);

		this.actionImportantTrack.setVisible(false);
		this.actionCancelImportantTrack.setVisible(false);
		this.actionCustomerAdapter.setVisible(false);
		this.actionCustomerShare.setVisible(false);
		this.actionCustomerCancelShare.setVisible(false);

//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			// this.actionRemove.setEnabled(false);
//			this.btnRemove.setEnabled(false);
//			this.menuItemRemove.setEnabled(false);
//
//			this.menuExcelImport.setEnabled(false);
//
//			this.actionAddTrackRecord.setEnabled(false);
//			this.actionModifyTrackRecord.setEnabled(false);
//			this.actionDelTrackRecord.setEnabled(false);
//			this.actionTerminate.setEnabled(false);
//			// this.actionImportantTrack.setEnabled(false);
//			// this.actionCancelImportantTrack.setEnabled(false);
//			// this.actionCustomerAdapter.setEnabled(false);
//			// this.actionCustomerShare.setEnabled(false);
//			// this.actionCustomerCancelShare.setEnabled(false);
//		}

		// this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);

		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getColumn("intendingMoney").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
		this.tblMain.getColumn("intendingMoney").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("firstPayAmount").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
		this.tblMain.getColumn("firstPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblTrackRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblTrackRecord.setEditable(false);
		this.tblTrackRecord.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");

		// 初始化营销单元树
		this.treeMarketUnit.setModel(FDCTreeHelper.getMarketTree(this.actionOnLoad));
		this.treeMarketUnit.expandAllNodes(true, (TreeNode) this.treeMarketUnit.getModel().getRoot());
		// this.treeMarketUnit.setSelectionRow(0);

		// 初始化项目树
		this.treeSellProject.setModel(FDCTreeHelper.getSellProjectTree(actionOnLoad, null));
		this.treeSellProject.expandAllNodes(true, (TreeNode) this.treeSellProject.getModel().getRoot());
		// this.treeSellProject.setSelectionRow(0);
		this.tblMain.getColumn("room.number").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("room.name").getStyleAttributes().setHided(false);
		
		this.tblQuestion.setEnabled(false);
		this.tblQuestion.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	/**
	 * 查询所有的销售项目id
	 * 
	 * @param treeNode
	 */
	private void getAllProjectIds(TreeNode treeNode) {
		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) thisNode.getUserObject();
				allProjectIds += "," + project.getId().toString();
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected void treeMarketUnit_valueChanged(TreeSelectionEvent e) throws Exception {
		this.execQuery();
		this.tblTrackRecord.removeRows();
	}

	protected void treeSellProject_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeSellProject.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo projectInfo = (SellProjectInfo) node.getUserObject();
			this.getUIContext().put("projectSelected", projectInfo);
		}
		this.execQuery();
		this.tblTrackRecord.removeRows();

	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		this.tblTrackRecord.removeRows();

		TreeNode proNode = (TreeNode) this.treeSellProject.getLastSelectedPathComponent();
		TreeNode muNode = (TreeNode) this.treeMarketUnit.getLastSelectedPathComponent();

		allProjectIds = "null";
		if (proNode != null)
			getAllProjectIds(proNode);
		else
			getAllProjectIds((TreeNode) this.treeSellProject.getModel().getRoot());

		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		Map saleManIdSet = FDCTreeHelper.getAllObjectIdMap(muNode, "User");
		if (saleManIdSet.size() == 0)
			saleManIdSet.put("null", new User());
		filter.getFilterItems().add(new FilterItemInfo("saleMan.id", FDCTreeHelper.getStringFromSet(saleManIdSet.keySet()), CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allProjectIds, CompareType.INCLUDE));
		
		//需要增加项目隔离
		try {
			FilterInfo addSpFilter = addSeparateBySellProject((DefaultMutableTreeNode)muNode);
			filter.mergeFilter(addSpFilter, "and");
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
		
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	//若选择的节点非当前用户，则必须增加项目隔离
	private FilterInfo addSeparateBySellProject(DefaultMutableTreeNode thisNode) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();	
		String spFilterString = "";
			if(thisNode==null || thisNode.getUserObject() instanceof OrgStructureInfo){
				spFilterString = CommerceHelper.getPermitProjectIdSql(userInfo);
			}else if(thisNode.getUserObject() instanceof MarketingUnitInfo){//选择单元或人节点的情况
				MarketingUnitInfo marketingInfo = (MarketingUnitInfo)thisNode.getUserObject();
				spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"+marketingInfo.getId().toString()+"'";				
			}else if(thisNode.getUserObject() instanceof UserInfo){
				UserInfo nodeUser = (UserInfo)thisNode.getUserObject();
				if(!nodeUser.getId().toString().equals(userInfo.getId().toString())) {
					MarketingUnitInfo marketingUnit = (MarketingUnitInfo)((DefaultKingdeeTreeNode)thisNode.getParent()).getUserObject();
					spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"+marketingUnit.getId().toString()+"'";
				}
			}
			
			if(!spFilterString.equals(""))
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",spFilterString,CompareType.INNER));
			return filter;
	}
	
	
	protected String getEditUIName() {
		return CommerceChanceEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		this.checkSelected();
		String selectId = this.getSelectedKeyValue();
		CommerceChanceInfo comChanceInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(BOSUuid.read(selectId)));
		if (comChanceInfo != null) {
			this.actionCustomerInfo.setEnabled(true);
		} else {
			this.actionCustomerInfo.setEnabled(false);
		}
		if (this.tblTrackRecord.getRowCount() > 0)
			this.tblTrackRecord.removeRows();
		reflashTrackRecordTable(selectId);
		refreshTblQuestion(selectId);
	}
	
	private void refreshTblQuestion(String id) throws Exception{
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("inputDate");
		sic.add("questionPaper.id");
		sic.add("questionPaper.topric");
		sic.add("number");
		sic.add("bizDate");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("commerceChance.id",id));
		view.setSelector(sic);
		view.setFilter(filter);
		QuestionPaperAnswerCollection coll=QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(view);
		tblQuestion.removeRows();
		for(Iterator it=coll.iterator();it.hasNext();){
			QuestionPaperAnswerInfo info=(QuestionPaperAnswerInfo)it.next();
			IRow row=tblQuestion.addRow();
			if(info!=null){
				row.getCell("number").setValue(info.getNumber()!=null?info.getNumber():null);
				row.getCell("printDate").setValue(info.getInputDate()!=null?info.getInputDate():null);
				row.getCell("operationDate").setValue(info.getBizDate()!=null?info.getBizDate():null);
				row.getCell("title").setValue(info.getQuestionPaper().getTopric()!=null?info.getQuestionPaper().getTopric():null);
				row.getCell("id").setValue(info.getId()!=null?info.getId():null);
				row.setUserObject(info);
			}
		}
	
	}
	
	public void actionQuestionPrint_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("sellProject.id");
		sic.add("sellProject.name");
		sic.add("sellProject.number");
		sic.add("fdcCustomer.id");
		sic.add("fdcCustomer.name");
		sic.add("fdcCustomer.number");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",getSelectedKeyValue()));
		view.setSelector(sic);
		view.setFilter(filter);
		CommerceChanceInfo info=CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view).get(0);
		uiContext.put("commerceChance", info);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
				.getName(), uiContext, null, OprtState.ADDNEW);
		curDialog.show();
	}
	
	protected void tblQuestion_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getButton()==1 && e.getClickCount()==2){//双击
			QuestionPaperAnswerInfo info=(QuestionPaperAnswerInfo)tblQuestion.getRow(e.getRowIndex()).getUserObject();
			if(info == null){
				return;
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, info.getId().toString());
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
					.getName(), uiContext, null, OprtState.VIEW);
			curDialog.show();
		}
	}

	public void actionCustomerInfo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCustomerInfo_actionPerformed(e);
		this.checkSelected();
		String selectId = this.getSelectedKeyValue();
		CommerceChanceInfo comChanceInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(BOSUuid.read(selectId)));
		if (comChanceInfo != null) {
			FDCCustomerInfo fdcCustomerInfo = comChanceInfo.getFdcCustomer();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, fdcCustomerInfo.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	// 根据所选商机的id刷新跟踪记录表
	private void reflashTrackRecordTable(String selectId) throws Exception {
		this.tblTrackRecord.removeRows();
		if (selectId != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("commerceChance.id", selectId));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("head.name"));
			selector.add(new SelectorItemInfo("eventType.name"));
			selector.add(new SelectorItemInfo("receptionType.name"));
			selector.add(new SelectorItemInfo("commerceChance.name"));
			view.setSelector(selector);
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo sortInfo = new SorterItemInfo("eventDate");
			sortInfo.setSortType(SortType.DESCEND);
			sorter.add(sortInfo);
			view.setSorter(sorter);
			TrackRecordCollection trackColl = TrackRecordFactory.getRemoteInstance().getTrackRecordCollection(view);
			for (int i = 0; i < trackColl.size(); i++) {
				TrackRecordInfo trackInfo = trackColl.get(i);

				IRow row = this.tblTrackRecord.addRow();
				row.setUserObject(trackInfo);
				row.getCell("id").setValue(trackInfo.getId().toString());
				row.getCell("number").setValue(trackInfo.getNumber());
				row.getCell("name").setValue(trackInfo.getName());
				row.getCell("eventDate").setValue(trackInfo.getEventDate());
				row.getCell("head.name").setValue(trackInfo.getHead());
				row.getCell("trackResult").setValue(trackInfo.getTrackResult() == null ? "" : trackInfo.getTrackResult().getAlias());
				row.getCell("eventType.name").setValue(trackInfo.getEventType() == null ? "" : trackInfo.getEventType().getName());
				row.getCell("receptionType.name").setValue(trackInfo.getReceptionType() == null ? "" : trackInfo.getReceptionType().getName());
				row.getCell("commerceChance.name").setValue(trackInfo.getCommerceChance() == null ? "" : trackInfo.getCommerceChance().getName());
				row.getCell("description").setValue(trackInfo.getDescription());
				row.getCell("createTime").setValue(trackInfo.getCreateTime());
			}
		}
	}

	protected void tblTrackRecord_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if (e.getType() != 1)
				return;

			TrackRecordInfo trackInfo = (TrackRecordInfo) this.tblTrackRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null)
				return;

			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, trackInfo.getId().toString());
			uiContext.put("ActionView", "OnlyView");
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode marketNode = (DefaultKingdeeTreeNode) this.treeMarketUnit.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) this.treeSellProject.getLastSelectedPathComponent();

		// if(marketNode!=null && marketNode.getUserObject() instanceof
		// UserInfo){
		// this.getUIContext().put("userSelected",(UserInfo)marketNode.
		// getUserObject()); //选择的营销人员
		// }

		if (projectNode != null && projectNode.getUserObject() instanceof SellProjectInfo) {
			this.getUIContext().put("projectSelected", (SellProjectInfo) projectNode.getUserObject()); // 选择的销售项目
		}

		super.actionAddNew_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要删除的商机!");
			return;
		}
		/*
		 * FilterInfo filter = new FilterInfo(); filter.getFilterItems().add(new
		 * FilterItemInfo("head.id",selectId));
		 * 
		 * if(TrackRecordFactory.getRemoteInstance().exists(filter)){
		 * MsgBox.showInfo("已经有跟踪记录关联了该条商机,不能删除!"); return; }
		 */
		CommerceChanceInfo comInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(BOSUuid.read(selectId)));
		if (comInfo != null && comInfo.getCommerceStatus() != null && !comInfo.getCommerceStatus().equals(CommerceStatusEnum.Intent)) {
			MsgBox.showInfo("非意向状态的商机不能删除!");
			return;
		}

		if (this.tblTrackRecord.getRowCount() > 0) {
			MsgBox.showInfo("已有跟进记录的商机不能删除!");
			return;
		}

		super.actionRemove_actionPerformed(e);
	}

	// 终止商机 --只有意向状态的商机可以终止
	public void actionTerminate_actionPerformed(ActionEvent e) throws Exception {

		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要终止的商机!");
			return;
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String idStr = (String) this.tblMain.getRow(selectRows[0]).getCell("id").getValue();
		if (idStr == null)
			return;

		CommerceChanceInfo comInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		if (comInfo != null) {
			if (!comInfo.getCommerceStatus().equals(CommerceStatusEnum.Intent)) {
				MsgBox.showInfo("只有意向状态的商机可以终止!");
				return;
			}

			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, comInfo.getId().toString());
			uiContext.put("ListUI", this);
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CommerceChanceTerminateReasonUI.class.getName(), uiContext, null, OprtState.EDIT);
				uiWindow.show();
			} catch (UIException ee) {
				ee.printStackTrace();
			}

		}

		super.actionTerminate_actionPerformed(e);

	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要修改的商机!");
			return;
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeSellProject.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo projectInfo = (SellProjectInfo) node.getUserObject();
				this.getUIContext().put("projectSelected", projectInfo);
			}
		}
		CommerceChanceInfo comInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(BOSUuid.read(selectId)));
		if (comInfo != null) {
			if (comInfo.getCommerceStatus().equals(CommerceStatusEnum.Finish)) {
				MsgBox.showInfo("已终止状态的商机不可以修改!");
				return;
			}
			super.actionEdit_actionPerformed(e);
		}
	}

	public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddTrackRecord_actionPerformed(e);

		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要跟进的商机!");
			return;
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String idStr = (String) this.tblMain.getRow(selectRows[0]).getCell("id").getValue();
		if (idStr == null)
			return;

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("fdcCustomer.*"));
		selector.add(new SelectorItemInfo("sellProject.*"));
		CommerceChanceInfo comInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(BOSUuid.read(idStr)), selector);
		if (comInfo == null)
			return;
		// 终止状态的商机无法再跟进
		if (comInfo.getCommerceStatus().equals(CommerceStatusEnum.Finish)) {
			MsgBox.showInfo("终止状态的商机无法再跟进!");
			return;
		}

		// 打开新增跟踪记录界面
		UIContext uiContext = new UIContext(this);
		uiContext.put("CommerceChance", comInfo);
		uiContext.put("FdcCustomer", comInfo.getFdcCustomer());
		uiContext.put("SellProject", comInfo.getSellProject());
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
		}

		reflashTrackRecordTable(selectId);
		this.tblMain.refresh();
	}

	public void actionModifyTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionModifyTrackRecord_actionPerformed(e);
		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要对应的商机!");
			return;
		}

		int[] trackRows = KDTableUtil.getSelectedRows(this.tblTrackRecord);
		if (trackRows.length == 0) {
			MsgBox.showInfo("请先选择要修改的跟进记录!");
			return;
		}
		String idTrack = (String) this.tblTrackRecord.getRow(trackRows[0]).getCell("id").getValue();
		if (idTrack == null)
			return;

		// 打开新增跟踪记录界面
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, idTrack);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
		}

		reflashTrackRecordTable(selectId);
		this.tblMain.refresh();
	}

	public void actionDelTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelTrackRecord_actionPerformed(e);

		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要对应的商机!");
			return;
		}

		int[] trackRows = KDTableUtil.getSelectedRows(this.tblTrackRecord);
		if (trackRows.length == 0) {
			MsgBox.showInfo("请先选择要删除的跟进记录!");
			return;
		}
		String idTrack = (String) this.tblTrackRecord.getRow(trackRows[0]).getCell("id").getValue();
		if (idTrack == null)
			return;

		if (MsgBox.OK == MsgBox.showConfirm2("确认要删除吗?")) {
			TrackRecordFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(idTrack)));
			reflashTrackRecordTable(selectId);
		}

	}

	/**
	 * 从Excel中批量导入
	 */
	public void actionExcelBatchImport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelBatchImport_actionPerformed(e);

		UIContext uiContext = new UIContext(this);

		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) this.treeSellProject.getLastSelectedPathComponent();
		if (projectNode != null && projectNode.getUserObject() instanceof SellProjectInfo) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) projectNode;
			SellProjectInfo proInfo = (SellProjectInfo) thisNode.getUserObject();
			uiContext.put("sellProject", proInfo);
		}

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(CommerceChanceImportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	private String getCustomerID() throws EASBizException, BOSException, UuidException {
		String commerID = this.getSelectedKeyValue();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("fdcCustomer.*");
		CommerceChanceInfo commerInfo = CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(BOSUuid.read(commerID)), sel);
		FDCCustomerInfo customer = commerInfo.getFdcCustomer();
		return customer.getId().toString();
	}

}