/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDataStyle;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomIntentListUI extends AbstractRoomIntentListUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(RoomIntentListUI.class);
	CoreUIObject detailUI = null;
	
	Map actionMap = new HashMap();

//	private CustomerQueryPanel filterUI = null;

//	private CommonQueryDialog commonQueryDialog = null;

	/**
	 * output class constructor
	 */
	public RoomIntentListUI() throws Exception
	{
		super();
	}

	public void loadFields()
	{
		super.loadFields();
	}

	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}

//	private CustomerQueryPanel getFilterUI()
//	{
//		if (this.filterUI == null)
//		{
//			try
//			{
//				this.filterUI = new RoomIntentFilterUI(this, this.actionOnLoad);
//			} catch (Exception e)
//			{
//				this.handUIException(e);
//			}
//		}
//		return this.filterUI;
//	}
	
	 public void actionBuyingRoomPlan_actionPerformed(ActionEvent e) throws Exception
	 {
		 if(detailUI.getUIContext().get("roomId") == null){
			 MsgBox.showInfo("请选择房间！");
				this.abort();
		 }
		 String roomId = detailUI.getUIContext().get("roomId").toString();
		 BigDecimal totalAmount = (BigDecimal)detailUI.getUIContext().get("totalAmount");
//		 if(roomId ==null || "".equals(roomId)){
//			 MsgBox.showInfo("请选择房间！");
//				this.abort();
//		 }
		 UIContext uiContext = new UIContext(ui);
		 uiContext.put("ID", roomId);
		 uiContext.put("totalAmount", totalAmount);
		 uiContext.put("pricingType", detailUI.getUIContext().get("pricingType"));
		 // 创建UI对象并显示
		 IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BuyingRoomPlanUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		 uiWindow.show();
	 }
	 
	public void actionPayPlan_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!(node.getUserObject() instanceof SellProjectInfo))
		{
			return;
		}
		SellProjectInfo project = (SellProjectInfo) node.getUserObject();
		UIContext uiContext = new UIContext(ui);
		String projectID = project.getId().toString();
		uiContext.put("sellProjectID", projectID);
		String roomId = this.getSelectedKeyValue();
		if(roomId!=null)
			SimulantSellUI.showUI(this, roomId, projectID);
	}

	protected boolean initDefaultFilter()
	{
		return false;
	}

//	protected CommonQueryDialog initCommonQueryDialog()
//	{
//		if (commonQueryDialog != null)
//		{
//			return commonQueryDialog;
//		}
//		commonQueryDialog = super.initCommonQueryDialog();
//		commonQueryDialog.setWidth(400);
//		commonQueryDialog.addUserPanel(this.getFilterUI());
//		commonQueryDialog.setShowFilter(false);	
//		return commonQueryDialog;	    
//	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	public void onLoad() throws Exception
	{
		super.onLoad();
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemPayplan.setEnabled(true);
		this.btnPayPlan.setEnabled(true);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPayPlan.setVisible(false);
		this.actionBuyingRoomPlan.setEnabled(true);
		this.actionKeepDown.setEnabled(true);
		this.actionSinPurchase.setEnabled(true);
		this.actionPrePurchase.setEnabled(true);
		this.actionPurchase.setEnabled(true);
		this.acionSignContract.setEnabled(true);
		this.menuItemSignContract.setEnabled(true);
		this.actionBuyingRoomPlan.setVisible(true);
		initCtrl();
		initAction();
		initRoomQuery();
		this.actionQuery.setEnabled(false);
		this.tabNew.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				int comIndex = tabNew.getSelectedIndex();
				changButton(comIndex);
			}
		});
		
		this.actionSinPurchase.setVisible(false);
		this.actionPrePurchase.setVisible(false);
		this.actionPurchase.setVisible(false);
		this.acionSignContract.setVisible(false);
		this.actionKeepDown.setVisible(false);
	}
	
    private void initAction(){
    	actionMap.put("actionBuyingRoomPlan",actionBuyingRoomPlan);
    	actionMap.put("actionKeepDown",actionKeepDown);
    	actionMap.put("actionSinPurchase",actionSinPurchase);
    	actionMap.put("actionPrePurchase",actionPrePurchase);
    	actionMap.put("actionPurchase",actionPurchase);
    	actionMap.put("acionSignContract",acionSignContract);
    }
	
	private void changButton(int comIndex){
		if(comIndex == 0){
			this.actionQuery.setEnabled(false);
		} else{
			this.actionQuery.setEnabled(true);
		}
	}

	private void initRoomQuery() throws Exception{
		if (detailUI == null) {
			UIContext uiContext = new UIContext(ui);
//			uiContext.put(UIContext.ID, room.getId().toString());
//			uiContext.put("isRecover", "Y");
			uiContext.put("actionMap", actionMap);
			detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(VirtualSellControlUI.class.getName(), uiContext, null, "VIEW");
			// detailUI.setDataObject(room);
			sclPanel.setViewportView(detailUI);
			sclPanel.setKeyBoardControl(true);
			// return;
		} else {
//			detailUI.getUIContext().put("isRecover", "Y");
//			detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
			detailUI.onLoad();
		}
	}
	/**
	 * 对数据进行分组显示
	 * 
	 * @author laiquan_luo
	 * 
	 */
	private void setDataGroupBySellState()
	{
		
		this.tblMain.getGroupManager().setGroup(true);
		this.tblMain.getColumn("sellState").setGroup(true);
		this.tblMain.getColumn("sellState").setMergeable(true);
		// this.tblMain.getGroupManager().setTotalize(true);
		this.tblMain.getColumn(1).setStat(true);

		this.tblMain.getGroupManager().setOrientation(KDTStyleConstants.DOWN);

		// 定义两个变量
		IRow row0;
		KDTDataStyle ds;

		// 获取第0级统计的模板
		row0 = (IRow) this.tblMain.getGroupManager().getStatRowTemplate(0);
		// 设置第0级统计行的背景色
		row0.getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		// 设置第0级统计行的第0个单元的值
		row0.getCell(1).setValue("小计");
		// 设置第0级统计行第3个单元的统计公式
		row0.getCell("buildingArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("roomArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("actualBuildingArea").setExpressions(
				KDTGroupManager.STAT_SUM);
		row0.getCell("actualRoomArea").setExpressions(KDTGroupManager.STAT_SUM);
		// row0.getCell("buildPrice").setExpressions(KDTGroupManager.STAT_SUM);
		// 建筑单价不合计modified by wenyaowei20090611
		// row0.getCell("roomPrice").setExpressions(KDTGroupManager.STAT_SUM);
		// 套内单价不合计modified by wenyaowei200906012
		row0.getCell("standardTotalAmount").setExpressions(
				KDTGroupManager.STAT_SUM);

	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception
	{
		if (this.getSelectedKeyValue() != null)
			super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception
	{

		//不用销售组织判断,改为售楼组织
		Map orgMap = FDCSysContext.getInstance().getOrgMap();
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionBuyingRoomPlan.setEnabled(false);
			this.actionKeepDown.setEnabled(false);
			this.actionSinPurchase.setEnabled(false);
			this.actionPrePurchase.setEnabled(false);
			this.actionPurchase.setEnabled(false);
			this.acionSignContract.setEnabled(false);
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
	{
		this.execQuery();
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		this.execQuery();
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo)
	{
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(allSpIdStr.trim().length()==0)
			allSpIdStr = "'nullnull'"; 
		
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId().toString()));
				}else if (node.getUserObject() instanceof SubareaInfo)		{
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("subarea.id", subInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
					BuildingUnitInfo buInfo = (BuildingUnitInfo)node.getUserObject();
//					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
//					filter.getFilterItems().add(new FilterItemInfo("room.unit", new Integer(buInfo.getSeq())));
//					现在已近改为buildUnit这个字段 ，这里的过滤条件也改掉 xiaoao_liu
					filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buInfo.getId().toString()));
				}
			}
			if(filter.getFilterItems().size()==0)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr ,CompareType.INNER));
			filter.getFilterItems().add(
					new FilterItemInfo("sellState",RoomSellStateEnum.ONSHOW_VALUE));
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		initCtrl();
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomFactory.getRemoteInstance();
	}

	protected String getEditUIName()
	{
		return RoomEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void initCtrl()
	{
		this.tblMain.getColumn("buildingArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.setDataGroupBySellState();
	}

	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	protected String getLongNumberFieldName()
	{
		return "number";
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		if (confirmRemove())
		{
			// prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	// 暴露treeMain，供RoomintentFilterUI中根据选中的节点设置一些过滤
	public KDTree getTreeMain()
	{
		return this.treeMain;
	}
	

	  /**
   * output actionSinPurchase_actionPerformed method
   */
  public void actionSinPurchase_actionPerformed(ActionEvent e) throws Exception
  {
		String buildingId = null;
	  	String  roomId = null;
	  	String sellProjectId = null;
	  	SellProjectInfo sellProject = null;
	  	int index = tabNew.getSelectedIndex();
	  	if(index == 0){
	  		roomId = (String)detailUI.getUIContext().get("roomId");
	  		buildingId = (String)detailUI.getUIContext().get("buildingId");
	  		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
	  	}else if(index == 1){
	  		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	  		if (rowIndex == -1)
	  		{
	  			FDCMsgBox.showInfo("请选择行!");
	  			this.abort();
	  		}
	  		IRow row = this.tblMain.getRow(rowIndex);
	  		if(row.getCell("id").getValue() == null){
	  			FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  		}
	  		roomId = (String)row.getCell("id").getValue();
	  		buildingId = (String)row.getCell("buildingId").getValue();
	  		sellProjectId = (String)row.getCell("sellProjectId").getValue();
	  		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
	  	}
	  	if(roomId == null || buildingId == null){
	  		FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  	}
	  	boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  	if(!isEnable){
	  		UIContext uiContext = new UIContext(this);
	  		uiContext.put("roomId", roomId);
	  		uiContext.put("sellProject", sellProject);
	  		IUIWindow uiWindow = UIFactory.createUIFactory(
	  				UIFactoryName.MODEL).create(
	  						SincerityPurchaseChangeNameUI.class.getName(),
	  				uiContext, null, OprtState.ADDNEW);
	  		uiWindow.show();
	  	}
  }
  
  
  public void actionKeepDown_actionPerformed(ActionEvent e) throws Exception
  {
	  	String buildingId = null;
	  	String  roomId = null;
	  	String sellProjectId = null;
	  	SellProjectInfo sellProject = null;
	  	int index = tabNew.getSelectedIndex();
	  	if(index == 0){
	  		roomId = (String)detailUI.getUIContext().get("roomId");
	  		buildingId = (String)detailUI.getUIContext().get("buildingId");
	  		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
	  	}else if(index == 1){
	  		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	  		if (rowIndex == -1)
	  		{
	  			FDCMsgBox.showInfo("请选择行!");
	  			this.abort();
	  		}
	  		IRow row = this.tblMain.getRow(rowIndex);
	  		if(row.getCell("id").getValue() == null){
	  			FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  		}
	  		roomId = (String)row.getCell("id").getValue();
	  		buildingId = (String)row.getCell("buildingId").getValue();
	  		sellProjectId = (String)row.getCell("sellProjectId").getValue();
	  		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
	  	}
	  	if(roomId == null || buildingId == null){
	  		FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  	}
	  	boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  	if(!isEnable){
	  		UIContext uiContext = new UIContext(this);
	  		uiContext.put("roomId", roomId);
	  		uiContext.put("sellProject", sellProject);
	  		IUIWindow uiWindow = UIFactory.createUIFactory(
	  				UIFactoryName.MODEL).create(
	  						RoomKeepDownBillEditUI.class.getName(),
	  				uiContext, null, OprtState.ADDNEW);
	  		uiWindow.show();
	  	}
  }
  	
  

  /**
   * output actionPrePurchase_actionPerformed method
   */
  public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception
  {
	  	String buildingId = null;
	  	String  roomId = null;
	  	String sellProjectId = null;
	  	SellProjectInfo sellProject = null;
	  	RoomSellStateEnum state = null;
	  	BizEnumValueInfo sellState=null;
	  	int index = tabNew.getSelectedIndex();
	  	if(index == 0){
	  		roomId = (String)detailUI.getUIContext().get("roomId");
	  		buildingId = (String)detailUI.getUIContext().get("buildingId");
	  		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
	  		state = (RoomSellStateEnum)detailUI.getUIContext().get("state");
	  	}else if(index == 1){
	  		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	  		if (rowIndex == -1)
	  		{
	  			FDCMsgBox.showInfo("请选择行!");
	  			this.abort();
	  		}
	  		IRow row = this.tblMain.getRow(rowIndex);
	  		if(row.getCell("id").getValue() == null){
	  			FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  		}
	  		roomId = (String)row.getCell("id").getValue();
	  		buildingId = (String)row.getCell("buildingId").getValue();
	  		sellProjectId = (String)row.getCell("sellProjectId").getValue();
	  		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
	  		sellState = (BizEnumValueInfo) row.getCell("sellState").getValue();
	  	}
	  	if(roomId == null || buildingId == null){
	  		FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  	}
	  	if((index == 0 && (RoomSellStateEnum.SincerPurchase.equals(state) || RoomSellStateEnum.OnShow.equals(state))) 
    			|| (index == 1 && (RoomSellStateEnum.SINCERPURCHASE_VALUE.equals(sellState.getValue()) 
    					|| RoomSellStateEnum.OnShow.equals(sellState.getValue())))){
	  		boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  		if(isEnable){
	  			return;
	  		}
    	}
	  	SHEManageHelper.toTransactionBill(BOSUuid.read(roomId), sellProject, this, PrePurchaseManageEditUI.class.getName());
//	  	UIContext uiContext = new UIContext(this);
//		uiContext.put("roomId", roomId);
//		uiContext.put("sellProject", sellProject);
//		IUIWindow uiWindow = UIFactory.createUIFactory(
//				UIFactoryName.MODEL).create(
//						PrePurchaseManageEditUI.class.getName(),
//				uiContext, null, OprtState.ADDNEW);
//		uiWindow.show();	
  }
  	

  /**
   * output actionPurchase_actionPerformed method
   */
  public void actionPurchase_actionPerformed(ActionEvent e) throws Exception
  {
	  	String buildingId = null;
	  	String  roomId = null;
	  	String sellProjectId = null;
	  	SellProjectInfo sellProject = null;
	  	RoomSellStateEnum state = null;
	  	BizEnumValueInfo sellState=null;
	  	int index = tabNew.getSelectedIndex();
	  	if(index == 0){
	  		roomId = (String)detailUI.getUIContext().get("roomId");
	  		buildingId = (String)detailUI.getUIContext().get("buildingId");
	  		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
	  		state = (RoomSellStateEnum)detailUI.getUIContext().get("state");
	  	}else if(index == 1){
	  		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	  		if (rowIndex == -1)
	  		{
	  			FDCMsgBox.showInfo("请选择行!");
	  			this.abort();
	  		}
	  		IRow row = this.tblMain.getRow(rowIndex);
	  		if(row.getCell("id").getValue() == null){
	  			FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  		}
	  		roomId = (String)row.getCell("id").getValue();
	  		buildingId = (String)row.getCell("buildingId").getValue();
	  		sellProjectId = (String)row.getCell("sellProjectId").getValue();
	  		sellState = (BizEnumValueInfo) row.getCell("sellState").getValue();
	  		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
	  	}
	  	if(roomId == null || buildingId == null){
	  		FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  	}
	  	
	  	if((index == 0 && (RoomSellStateEnum.SincerPurchase.equals(state) || RoomSellStateEnum.OnShow.equals(state))) 
    			|| (index == 1 && (RoomSellStateEnum.SINCERPURCHASE_VALUE.equals(sellState.getValue()) 
    					|| RoomSellStateEnum.OnShow.equals(sellState.getValue())))){
	  		boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  		if(isEnable){
	  			return;
	  		}
    	}
	  	SHEManageHelper.toTransactionBill(BOSUuid.read(roomId), sellProject, this, PurchaseManageEditUI.class.getName());
//	  	UIContext uiContext = new UIContext(this);
//		uiContext.put("roomId", roomId);
//		uiContext.put("sellProject", sellProject);
//		IUIWindow uiWindow = UIFactory.createUIFactory(
//				UIFactoryName.MODEL).create(
//						PurchaseManageEditUI.class.getName(),
//				uiContext, null, OprtState.ADDNEW);
//		uiWindow.show();
  }
  	

  /**
   * output acionSignContract_actionPerformed method
   */
  public void acionSignContract_actionPerformed(ActionEvent e) throws Exception
  {
	  	String buildingId = null;
	  	String  roomId = null;
	  	String sellProjectId = null;
	  	SellProjectInfo sellProject = null;
	  	RoomSellStateEnum state = null;
	  	BizEnumValueInfo sellState=null;
	  	int index = tabNew.getSelectedIndex();
	  	if(index == 0){
	  		roomId = (String)detailUI.getUIContext().get("roomId");
	  		buildingId = (String)detailUI.getUIContext().get("buildingId");
	  		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
	  		state = (RoomSellStateEnum)detailUI.getUIContext().get("state");
	  	}else if(index == 1){
	  		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	  		if (rowIndex == -1)
	  		{
	  			FDCMsgBox.showInfo("请选择行!");
	  			this.abort();
	  		}
	  		IRow row = this.tblMain.getRow(rowIndex);
	  		if(row.getCell("id").getValue() == null){
	  			FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  		}
	  		roomId = (String)row.getCell("id").getValue();
	  		buildingId = (String)row.getCell("buildingId").getValue();
	  		sellProjectId = (String)row.getCell("sellProjectId").getValue();
	  		sellState = (BizEnumValueInfo) row.getCell("sellState").getValue();
	  		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
	  	}
	  	if(roomId == null || buildingId == null){
	  		FDCMsgBox.showInfo("请选择房间!");
				this.abort();
	  	}
	  	if((index == 0 && (RoomSellStateEnum.SincerPurchase.equals(state) || RoomSellStateEnum.OnShow.equals(state))) 
    			|| (index == 1 && (RoomSellStateEnum.SINCERPURCHASE_VALUE.equals(sellState.getValue()) 
    					|| RoomSellStateEnum.OnShow.equals(sellState.getValue())))){
	  		boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  		if(isEnable){
	  			return;
	  		}
    	}
	  	SHEManageHelper.toTransactionBill(BOSUuid.read(roomId), sellProject, this, SignManageEditUI.class.getName());
//	  	UIContext uiContext = new UIContext(this);
//		uiContext.put("roomId", roomId);
//		uiContext.put("sellProject", sellProject);
//		IUIWindow uiWindow = UIFactory.createUIFactory(
//				UIFactoryName.MODEL).create(
//						SignManageEditUI.class.getName(),
//				uiContext, null, OprtState.ADDNEW);
//		uiWindow.show();
  }
	
}