/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
//import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SellStatRoomRptUI extends AbstractSellStatRoomRptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SellStatRoomRptUI.class);


    private String allBuildingIds = "null";			//所包含楼栋id
	
	/**
	 * output class constructor
	 */
	public SellStatRoomRptUI() throws Exception {
		super();
	}


	
	public void onLoad() throws Exception {
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);

/*		已在UI中加了
 * 		this.tblMain.getColumn("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));	
		this.tblMain.getColumn("basePrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("lastPurchase.contractTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));		
		this.tblMain.getColumn("sellAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));		
		this.tblMain.getColumn("areaCompensateAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("contactComeBackCount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("arrearageCount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("otherShouldBackCount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));		
		this.tblMain.getColumn("finalAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));*/
//		SimpleKDTSortManager.setTableSortable(tblMain);
	}
	
	
	
	protected boolean isFootVisible() {
		return false;
		//return false;
	}

	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		int unitNumber = 0;  //单元过滤，代表无单元过滤
		FilterInfo defaultQuery = (FilterInfo)this.getUIContext().get("DefaultQueryInfo");
		if (defaultQuery != null) {  //通过销售汇总表直接进入的
			viewInfo.setFilter(defaultQuery);
			this.kDSplitPane.setDividerLocation(0);
			this.getUIContext().remove("DefaultQueryInfo");
		}else{
			TreeNode buildingNode = (TreeNode)this.treeMain.getLastSelectedPathComponent();
			allBuildingIds = "null";
			if(buildingNode!=null){
				DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)buildingNode;
				if(thisNode.getUserObject()!=null) {
					if(thisNode.getUserObject() instanceof BuildingInfo) {
						BuildingInfo building = (BuildingInfo)thisNode.getUserObject();
						allBuildingIds += "," + building.getId().toString();
					}else if(thisNode.getUserObject() instanceof Integer) { //已作废
						DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)thisNode.getParent();
						BuildingInfo parentBuilding = (BuildingInfo)parentNode.getUserObject();
						allBuildingIds += "," + parentBuilding.getId().toString();
						unitNumber = ((Integer)thisNode.getUserObject()).intValue();
					}else if(thisNode.getUserObject() instanceof BuildingUnitInfo) { //
						DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)thisNode.getParent();
						BuildingInfo parentBuilding = (BuildingInfo)parentNode.getUserObject();
						allBuildingIds += "," + parentBuilding.getId().toString();
						unitNumber = ((BuildingUnitInfo)thisNode.getUserObject()).getSeq();
					}else{
						this.getAllBuildingIds(buildingNode);
					}
				}
			}else {
				try {
					this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
				this.getAllBuildingIds((TreeNode)this.treeMain.getModel().getRoot());
			}				
				
			viewInfo = (EntityViewInfo)this.mainQuery.clone();
			FilterInfo buildInfo = new FilterInfo();
			buildInfo.getFilterItems().add(new FilterItemInfo("building.id", allBuildingIds, CompareType.INCLUDE));
			if(unitNumber!=0) 
				buildInfo.getFilterItems().add(new FilterItemInfo("unit", new Integer(unitNumber)));
			if(viewInfo.getFilter()==null) {
				viewInfo.setFilter(buildInfo);
			}else{
				try {
					viewInfo.getFilter().mergeFilter(buildInfo, "And");
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sor = new SorterItemInfo("building.id");
		sor.setSortType(SortType.ASCEND);
		sorter.add(sor);
		viewInfo.setSorter(sorter);
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	public void storeFields() {
		super.storeFields();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}


	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		actionViewPurchase_actionPerformed(e);
	}

	
	protected boolean initDefaultFilter() {
		if (this.getUIContext().get("DefaultQueryInfo") == null) 		
			return true;
		else
			return false;
	}
	

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getClickCount() == 2) {
			actionViewPurchase_actionPerformed(null);
		}

	}	
	
	public void actionViewPurchase_actionPerformed(ActionEvent e)
			throws Exception {
		String roomId = this.getSelectedKeyValue();
		if (roomId != null) {
			RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)));
			if(room.getLastPurchase()!=null) {
				UIContext uiContext = new UIContext(ui);
				uiContext.put("ID", room.getLastPurchase().getId().toString());
				// 创建UI对象并显示
				IUIWindow uiWindow = UIFactory
						.createUIFactory(UIFactoryName.NEWTAB).create(
								PurchaseEditUI.class.getName(), uiContext, null,
								"FINDVIEW");
				uiWindow.show();
			}else {
				if(room.getHouseProperty()!=null && room.getHouseProperty().equals(HousePropertyEnum.Attachment)){
					MsgBox.showInfo(this, "该房间为‘"+HousePropertyEnum.Attachment.getAlias()+"’无对应的认购单！");
				}
			}
		}
	}

	private CommonQueryDialog commonQueryDialog = null;

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		//commonQueryDialog.setShowSorter(false);
		//commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}


	private SellStatRoomRptFilterUI filterUI = null;

	private SellStatRoomRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SellStatRoomRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	

	
	
	/**
	 * 根据id显示窗体
	 * @param isShowAll 
	 * 
	 * @param filter
	 * @throws UIException 
	 */
	public static void showUI(Object rptUI, String buildingId, Date beginDate, Date endDate, boolean isPreIntoSale, boolean isIncludeAttach, boolean isShowAll) throws UIException {
		UIContext uiContext = new UIContext(rptUI);
		FilterInfo filter = new FilterInfo();
		
		if(!isShowAll) {
			String dateParam = "toPurchaseDate";
			if(isPreIntoSale) 
				dateParam = "toSaleDate";
			if(beginDate!=null)				
				filter.getFilterItems().add(new FilterItemInfo(dateParam,beginDate,CompareType.GREATER_EQUALS));
			if(endDate!=null)
				filter.getFilterItems().add(new FilterItemInfo(dateParam, FDCDateHelper.getNextDay(endDate),CompareType.LESS));
		}
		
		if(isIncludeAttach)
			filter.getFilterItems().add(new FilterItemInfo("houseProperty", HousePropertyEnum.ATTACHMENT_VALUE,CompareType.NOTEQUALS));
		
		Set sellTypes = new HashSet();
			sellTypes.add(RoomSellStateEnum.PURCHASE_VALUE);
			sellTypes.add(RoomSellStateEnum.SIGN_VALUE);
			if(isPreIntoSale){
				sellTypes.add(RoomSellStateEnum.PREPURCHASE_VALUE);
			}
		filter.getFilterItems().add(new FilterItemInfo("sellState", sellTypes,	CompareType.INCLUDE));	
		if(buildingId!=null)
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
		
		uiContext.put("DefaultQueryInfo", filter);
		
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SellStatRoomRptUI.class.getName(), uiContext, null,	"VIEW");
		uiWindow.show();
	}
	
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.execQuery();
	}
	
	
	/**
	 * 查询所有的楼栋id
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {	
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		if(thisNode.getUserObject() instanceof BuildingInfo){
			BuildingInfo building = (BuildingInfo)thisNode.getUserObject();
			allBuildingIds += "," + building.getId().toString();
		}
			
		if(!treeNode.isLeaf()){
			int childCount = treeNode.getChildCount();
			while(childCount>0) {				
				getAllBuildingIds(treeNode.getChildAt(childCount-1));		
				childCount --;
			}	
		}	
	}
	
	
	protected void execQuery() {
		//this.tblMain.removeRows();
		super.execQuery();
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		Date beginDate=null,endDate=null;
		boolean  isShowAll = true;
		boolean  isPreIntoSale = false;
		boolean  isIncludeAttach = true;
		
		if(this.mainQuery.getFilter()!=null) {
			FilterItemCollection filterColl = this.mainQuery.getFilter().getFilterItems();
			for(int i=0;i<filterColl.size();i++) {
				FilterItemInfo fInfo = filterColl.get(i);
				if(fInfo.getPropertyName().equals("toSaleDate") || fInfo.getPropertyName().equals("toPurchaseDate")) {
					isShowAll = false;
						if(fInfo.getCompareValue()!=null && fInfo.getCompareExpression()!=null) {
						if(fInfo.getCompareExpression().equals(">=")) beginDate = (Date)fInfo.getCompareValue();
						else if(fInfo.getCompareExpression().equals("<")) endDate = (Date)fInfo.getCompareValue();
					}
				}else if(fInfo.getPropertyName().equals("houseProperty"))		
					isIncludeAttach = false;
				else if(fInfo.getPropertyName().equals("sellState")) {
					if(fInfo.getCompareValue() instanceof Set) 
						if(((Set)fInfo.getCompareValue()).contains(RoomSellStateEnum.PREPURCHASE_VALUE))
							isPreIntoSale = true;
				}
			}
		}
		
		Map roomIdMap = new HashMap();
		try{
			for(int i=0;i<this.tblMain.getRowCount();i++) {
				IRow row = this.tblMain.getRow(i);
				roomIdMap.put(row.getCell("id").getValue(),row);
			}
		}catch(Exception e) {
			e.printStackTrace();
			this.handleException(e);
		}
		
		
		try {
			if(this.tblMain.getFootManager()!=null && this.tblMain.getFootRow(0)!=null) {
				this.tblMain.getFootRow(0).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			}
			
			fillTermReceiveAmount(roomIdMap,isShowAll,isPreIntoSale,isIncludeAttach,beginDate,endDate);
			
//			fillCustomerPhonesString(roomIdMap,isShowAll,isPreIntoSale,isIncludeAttach,beginDate,endDate);
		} catch (BOSException e) {
			e.printStackTrace();
			this.handleException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			this.handleException(e);
		}
		
		this.tblMain.getColumn(2).setGroup(true);
		this.tblMain.getGroupManager().setTotalize(true);
		this.tblMain.getColumn(2).setStat(true);
		
		IRow row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(-1);
		row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		row0.getCell(0).setValue("合计:");
		row0.getCell(13).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(14).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(16).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(17).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(19).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(20).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(21).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(22).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(23).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(25).setExpressions(KDTGroupManager.STAT_SUM);
		
		for(int i=27;i<33;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
		for(int i=46;i<51;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
		
		row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(0);
		row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		row0.getCell(0).setValue("楼栋小计：");
//		for(int i=13;i<this.tblMain.getColumnCount();i++)
			row0.getCell(13).setExpressions(KDTGroupManager.STAT_AVERAGE);
			row0.getCell(14).setExpressions(KDTGroupManager.STAT_AVERAGE);
			row0.getCell(16).setExpressions(KDTGroupManager.STAT_SUM);
			row0.getCell(17).setExpressions(KDTGroupManager.STAT_AVERAGE);
			row0.getCell(19).setExpressions(KDTGroupManager.STAT_AVERAGE);
			row0.getCell(20).setExpressions(KDTGroupManager.STAT_SUM);
			row0.getCell(21).setExpressions(KDTGroupManager.STAT_AVERAGE);
			row0.getCell(22).setExpressions(KDTGroupManager.STAT_AVERAGE);
			row0.getCell(23).setExpressions(KDTGroupManager.STAT_SUM);
			row0.getCell(25).setExpressions(KDTGroupManager.STAT_SUM);
			
			for(int i=27;i<33;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			for(int i=46;i<51;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
		
		//重新调整布局并刷新
		tblMain.reLayoutAndPaint();
		
		this.tblMain.getGroupManager().group();
	}
	
	
	/**
	 * 填充客户手机号码 phones 字段 ，多个客户分录，号码用，分隔
	 */
	private void fillCustomerPhonesString(Map roomIdMap,boolean  isShowAll,boolean  isPreIntoSale,boolean  isIncludeAttach,Date beginDate,Date endDate) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();		
		builder.appendSql("select room.fid,Customer.FPhone from t_she_room room ");
		builder.appendSql("left join T_SHE_Purchase purchase on room.fid= purchase.froomid ");
		builder.appendSql("left join T_SHE_PurchaseCustomerInfo purchaseCust on purchase.fid= purchaseCust.FHeadID ");
		builder.appendSql("left join T_SHE_FDCCustomer Customer on purchaseCust.FCustomerID= Customer.fid ");
		builder.appendSql(" where purchase.FPurchaseState not in('ChangeRoomBlankOut','QuitRoomBlankOut','NoPayBlankOut','ManualBlankOut','AdjustBlankOut') ");
		if (isPreIntoSale) {
			builder.appendSql(" and (room.fSellState = '"+RoomSellStateEnum.PREPURCHASE_VALUE+"' or room.fSellState = '"+ RoomSellStateEnum.PURCHASE_VALUE +"' or room.fSellState = '"+RoomSellStateEnum.SIGN_VALUE+"')" );
		} else {
			builder.appendSql(" and (room.fSellState = '"+RoomSellStateEnum.PURCHASE_VALUE+"' or room.fSellState = '"+RoomSellStateEnum.SIGN_VALUE+"')" );
		}		
		if(!isIncludeAttach){
			builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		String dateParam = "room.FToPurchaseDate";
		if(isPreIntoSale) 	dateParam = "room.FToSaleDate";
		if (!isShowAll) {
			if (beginDate != null) {
				builder.appendSql(" and " + dateParam + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			if (endDate != null) {
				builder.appendSql(" and " + dateParam + " < ? ");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
			}
		}	
		

		
		IRowSet termReceiveSet = builder.executeQuery();
		while (termReceiveSet.next()) {
			String roomId = termReceiveSet.getString("fid");
			String phone = termReceiveSet.getString("FPhone");

			IRow row = (IRow)roomIdMap.get(roomId);
			if(row==null) continue;
			if(phone==null || phone.trim().equals("")) continue;
			
			Object phonesObject = row.getCell("phones").getValue();
			if(phonesObject==null){
				row.getCell("phones").setValue(phone);
			}else{
				row.getCell("phones").setValue((String)phonesObject +" ; "+ phone);
			}
		}
		
	}
	
	
	
	
	// 开放给业务系统是否表头可排序。
	protected boolean isCanOrderTable()
	{
		return true;
	}



	/**
	 * 更新   合同回款	欠款		其他应收回款  的三个字段的值
	 * contactComeBackCount	arrearageCount		otherShouldBackCount	  
	 */
	private void fillTermReceiveAmount(Map roomIdMap,boolean  isShowAll,boolean  isPreIntoSale,boolean  isIncludeAttach,Date beginDate,Date endDate) throws BOSException, SQLException {
		if(roomIdMap.size()==0) return;	
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder
//		.appendSql("select room.fid rfid,room.FBuildingID buildingId,money.FMoneyType moneyType,sum(purEntry.factRevAmount) revAmount from t_she_room room "
//				+ "left join t_she_purchase purchase on room.flastpurchaseid = purchase.fid "
//				+ "left join t_she_purchasePayListEntry purEntry on purchase.fid = purEntry.fheadid "
//				+ "left join t_she_moneydefine money on purEntry.fmoneydefineid = money.fid "
//				+ "where purchase.FPurchaseState not in('ChangeRoomBlankOut','QuitRoomBlankOut','NoPayBlankOut','ManualBlankOut','AdjustBlankOut') ");
		builder
		.appendSql("select room.fid rfid,money.FMoneyType moneyType,fre.famount sumRevAmount from T_SHE_FDCReceiveBillEntry fre "
				+ "left join t_cas_receivingbill cas on fre.FReceivingBillID=cas.fid "
				+ "left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
				+ "left join t_she_room room on fdc.froomid=room.fid "
				+ "left join T_SHE_Purchase purchase on room.fid= purchase.froomid "
				+ "left join t_she_moneyDefine money on  fre.FMoneyDefineId=money.fid "
				+ " where purchase.FPurchaseState not in('ChangeRoomBlankOut','QuitRoomBlankOut','NoPayBlankOut','ManualBlankOut','AdjustBlankOut') "
				+ " and fdc.fpurchaseid=purchase.fid ");

		if (isPreIntoSale) {
			builder.appendSql(" and (room.fSellState = '"+RoomSellStateEnum.PREPURCHASE_VALUE+"' or room.fSellState = '"+ RoomSellStateEnum.PURCHASE_VALUE +"' or room.fSellState = '"+RoomSellStateEnum.SIGN_VALUE+"')" );
		} else {
			builder.appendSql(" and (room.fSellState = '"+RoomSellStateEnum.PURCHASE_VALUE+"' or room.fSellState = '"+RoomSellStateEnum.SIGN_VALUE+"')" );
		}		
		if(!isIncludeAttach){
			builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		String dateParam = "purEntry.factRevDate";
		if (!isShowAll) {
			if (beginDate != null) {
				builder.appendSql(" and " + dateParam + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			if (endDate != null) {
				builder.appendSql(" and " + dateParam + " < ? ");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
			}
		}	
		builder.appendSql(" AND room.FIsForSHE=1 ");
//		builder.appendSql(" group by room.FBuildingID ");
		//builder.appendSql(" group by room.fid , money.FMoneyType ");
		//+ " money.FMoneyType in ()"
		//更新   合同回款	欠款		其他应收回款  的三个字段的值
		//		contactComeBackCount	arrearageCount		otherShouldBackCount
		BigDecimal contactComeBackCount = FDCHelper.ZERO;
		BigDecimal arrearageCount = FDCHelper.ZERO;
		BigDecimal otherShouldBackCount = FDCHelper.ZERO;
		String shouldRevTypeStr = "'PreconcertMoney','PurchaseAmount','HouseAmount','FisrtAmount','CompensateAmount','LoanAmount','AccFundAmount','Refundment'";
		IRowSet termReceiveSet = builder.executeQuery();
		while (termReceiveSet.next()) {
			String roomId = termReceiveSet.getString("rfid");
			BigDecimal termReceiveSumAmount = termReceiveSet.getBigDecimal("sumRevAmount");
			if(FDCHelper.ZERO==null) termReceiveSumAmount = FDCHelper.ZERO;
			String moneyType = termReceiveSet.getString("moneyType");
			
			IRow row = (IRow)roomIdMap.get(roomId);
			if(row==null) continue;
			if(moneyType==null) continue;
			if(shouldRevTypeStr.indexOf(moneyType)>0) {  //属于付款明细中的
				addToCellCount(row,"contactComeBackCount",termReceiveSumAmount);
				contactComeBackCount = contactComeBackCount.add(termReceiveSumAmount);
			}else{		//属于其他应收部分的
				addToCellCount(row,"otherShouldBackCount",termReceiveSumAmount);
				otherShouldBackCount = otherShouldBackCount.add(termReceiveSumAmount);
			}		
		}
		
		
		Iterator iter = roomIdMap.keySet().iterator();
		while(iter.hasNext()) {      //欠款	列
			IRow row = (IRow)roomIdMap.get(iter.next());
			BigDecimal totalCountDec = FDCHelper.ZERO;;
			BigDecimal recCountDec = FDCHelper.ZERO;;
			Object totalCount = row.getCell("lastPurchase.contractTotalAmount").getValue();
				if(totalCount!=null && totalCount instanceof BigDecimal)
					totalCountDec = (BigDecimal)totalCount;
			Object recCount = row.getCell("contactComeBackCount").getValue();
				if(recCount!=null && recCount instanceof BigDecimal)
					recCountDec = (BigDecimal)recCount;
			addToCellCount(row,"arrearageCount",totalCountDec.subtract(recCountDec));
			arrearageCount = arrearageCount.add(totalCountDec.subtract(recCountDec));
		}
		
		
		if(this.tblMain.getFootManager()!=null &&  this.tblMain.getFootRow(0)!=null) {
			this.tblMain.getFootRow(0).getCell("contactComeBackCount").setValue(contactComeBackCount);
			this.tblMain.getFootRow(0).getCell("arrearageCount").setValue(arrearageCount);
			this.tblMain.getFootRow(0).getCell("otherShouldBackCount").setValue(otherShouldBackCount);
		}
		
	}
	

	private void addToCellCount(IRow row,String cellName,BigDecimal ctrlAmount) {		
		Object cellObject = row.getCell(cellName).getValue();
		if(cellObject!=null && cellObject instanceof BigDecimal){
			BigDecimal oldCount = (BigDecimal)cellObject;
			row.getCell(cellName).setValue(oldCount.add(ctrlAmount));
		}else{
			row.getCell(cellName).setValue(ctrlAmount);
		}		
	}
	
	
	/**
	 * 根据户型id显示窗体
	 * @param isShowAll 
	 * 
	 * @param filter
	 * @throws UIException 
	 */
	public static void showRoomUI(Object rptUI, String roomModelID, Date beginDate, Date endDate, boolean isPreIntoSale, boolean isIncludeAttach, boolean isShowAll) throws UIException {
		UIContext uiContext = new UIContext(rptUI);
		FilterInfo filter = new FilterInfo();
		
		if(!isShowAll) {
			String dateParam = "toPurchaseDate";
			if(isPreIntoSale) 
				dateParam = "toSaleDate";
			if(beginDate!=null)				
				filter.getFilterItems().add(new FilterItemInfo(dateParam,beginDate,CompareType.GREATER_EQUALS));
			if(endDate!=null)
				filter.getFilterItems().add(new FilterItemInfo(dateParam, FDCDateHelper.getNextDay(endDate),CompareType.LESS));
		}
		
		if(isIncludeAttach)
			filter.getFilterItems().add(new FilterItemInfo("houseProperty", HousePropertyEnum.ATTACHMENT_VALUE,CompareType.NOTEQUALS));
		
		Set sellTypes = new HashSet();
			sellTypes.add(RoomSellStateEnum.PURCHASE_VALUE);
			sellTypes.add(RoomSellStateEnum.SIGN_VALUE);
			if(isPreIntoSale){
				sellTypes.add(RoomSellStateEnum.PREPURCHASE_VALUE);
			}
		filter.getFilterItems().add(new FilterItemInfo("sellState", sellTypes,	CompareType.INCLUDE));	
		if(roomModelID!=null)
			filter.getFilterItems().add(new FilterItemInfo("roomModel.id", roomModelID));
		
		uiContext.put("DefaultQueryInfo", filter);
		
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SellStatRoomRptUI.class.getName(), uiContext, null,	"VIEW");
		uiWindow.show();
	}
	
	
	
	
	
	

}