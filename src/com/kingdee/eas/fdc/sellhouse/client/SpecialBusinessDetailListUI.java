/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SpecialBusinessDetailListUI extends AbstractSpecialBusinessDetailListUI {
	private static final Logger logger = CoreUIObject.getLogger(SpecialBusinessDetailListUI.class);

	Map rowMap = new HashMap();

	private SpecialBusinessDetailFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	private ItemAction[] hideAction = { this.actionEdit, this.actionAddNew, this.actionRemove, this.actionLocate, this.actionView };

	private String allBuildingIds = null;			//所包含楼栋id

	private boolean isAuditDate = false;

	/**
	 * output class constructor
	 */
	public SpecialBusinessDetailListUI() throws Exception {
		super();

		RoomDisplaySetting setting= new RoomDisplaySetting();
		this.isAuditDate = setting.getBaseRoomSetting().isAuditDate();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		FDCClientHelper.setActionVisible(hideAction,false);
		initTree();
		this.tblMain.checkParsed();
		this.initTable();
		this.refresh(null);
		if(this.treeMain.getRowCount()>0){
			this.treeMain.setSelectionRow(0);
		}
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() != 1)
			return;
		if(e.getClickCount() != 2)
			return;
		int activeRowIndex = e.getRowIndex();
		int activeColumnIndex = e.getColIndex();
		if(true)
			/*if (activeColumnIndex == this.tblMain.getColumnIndex("businessType"))*/ {
			ICell iCell = this.tblMain.getRow(activeRowIndex).getCell("businessType");
			if(iCell.getValue().toString().startsWith("变更")){
				//进入该单据的变更界面
				String id = this.tblMain.getRow(activeRowIndex).getCell("id").getValue().toString();
				UIContext context = new UIContext(ui);
				context.put(UIContext.ID, id);
				context.put(UIContext.OWNER, SpecialBusinessDetailListUI.this);
				try {
					IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseChangeBillEditUI.class.getName(), context,
							null, OprtState.VIEW);
					window.show();
				} catch (UIException e1) {
					this.handleException(e1);
				}
			}
			else if(iCell.getValue().toString().startsWith("更名")){
				//进入该单据的更名界面
				String id = this.tblMain.getRow(activeRowIndex).getCell("id").getValue().toString();
				UIContext context = new UIContext(ui);
				context.put(UIContext.ID, id);
				context.put(UIContext.OWNER, SpecialBusinessDetailListUI.this);
				try {
					IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseChangeCustomerEditUI.class.getName(), context,
							null, OprtState.VIEW);
					window.show();
				} catch (UIException e1) {
					this.handleException(e1);
				}
			}
			else if(iCell.getValue().toString().startsWith("退房")){
				//进入该单据的退房界面
				String id = this.tblMain.getRow(activeRowIndex).getCell("id").getValue().toString();
				UIContext context = new UIContext(ui);
				context.put(UIContext.ID, id);
				context.put(UIContext.OWNER, SpecialBusinessDetailListUI.this);
				try {
					IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuitRoomEditUI.class.getName(), context,
							null, OprtState.VIEW);
					window.show();
				} catch (UIException e1) {
					this.handleException(e1);
				}
			}
			else if(iCell.getValue().toString().startsWith("换房")){
				//进入该单据的退房界面
				String id = this.tblMain.getRow(activeRowIndex).getCell("id").getValue().toString();
				UIContext context = new UIContext(ui);
				context.put(UIContext.ID, id);
				context.put(UIContext.OWNER, SpecialBusinessDetailListUI.this);
				try {
					IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChangeRoomEditUI.class.getName(), context,
							null, OprtState.VIEW);
					window.show();
				} catch (UIException e1) {
					this.handleException(e1);
				}
			}

			else{
				return;
			}
		}
		else{
			return;
		}
	}
	
	private void appendQuitRoomStateFilter(FDCSQLBuilder detailBuilder) throws Exception
	{
		if(getIsOrderQuit()==true && getIsPurchaseQuit() == false && getIsSignQuit() == false)
		{
			detailBuilder.appendSql(" and QUITROOM.FQuitRoomState in('PreconcertQuitRoom')");
		}
		else if(getIsOrderQuit()==false && getIsPurchaseQuit() == true && getIsSignQuit() == false)
		{
			detailBuilder.appendSql(" and QUITROOM.FQuitRoomState in('TakeUpQuitRoom')");
		}
		else if(getIsOrderQuit()==false && getIsPurchaseQuit() == false && getIsSignQuit() == true)
		{
			detailBuilder.appendSql(" and QUITROOM.FQuitRoomState in('SignUpQuitRoom')");
		}
		else if(getIsOrderQuit()==true && getIsPurchaseQuit() == true && getIsSignQuit() == false)
		{
			detailBuilder.appendSql(" and QUITROOM.FQuitRoomState in('PreconcertQuitRoom', 'TakeUpQuitRoom')");
		}
		else if(getIsOrderQuit()==true && getIsPurchaseQuit() == false && getIsSignQuit() == true)
		{
			detailBuilder.appendSql(" and QUITROOM.FQuitRoomState in('PreconcertQuitRoom', 'SignUpQuitRoom')");
		}
		else if(getIsOrderQuit()==false && getIsPurchaseQuit() == true && getIsSignQuit() == true)
		{
			detailBuilder.appendSql(" and QUITROOM.FQuitRoomState in('TakeUpQuitRoom', 'SignUpQuitRoom')");
		}
		else if(getIsOrderQuit()==true && getIsPurchaseQuit() == true && getIsSignQuit() == true)
		{
			detailBuilder.appendSql(" and QUITROOM.FQuitRoomState in('PreconcertQuitRoom', 'TakeUpQuitRoom', 'SignUpQuitRoom')");
		}
	}

	private void GetSearchResult(String nodeName,String AllBuildings) throws Exception {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();

		this.tblMain.getHeadRow(0).getCell("comName").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("comName").getStyleAttributes().setHided(false);
		this.tblMain.getHeadRow(0).getCell("proName").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("proName").getStyleAttributes().setHided(false);
		this.tblMain.getHeadRow(0).getCell("building").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("building").getStyleAttributes().setHided(false);


		if ("sellProject".equals(nodeName.toString())) {
			this.tblMain.getHeadRow(0).getCell("comName").getStyleAttributes().setHided(true);	
			this.tblMain.getColumn("comName").getStyleAttributes().setHided(true);	
			this.tblMain.getHeadRow(0).getCell("proName").getStyleAttributes().setHided(true);	
			this.tblMain.getColumn("proName").getStyleAttributes().setHided(true);									
		}
		else if ("buiding".equals(nodeName.toString())) {
			this.tblMain.getHeadRow(0).getCell("comName").getStyleAttributes().setHided(true);	
			this.tblMain.getColumn("comName").getStyleAttributes().setHided(true);	
			this.tblMain.getHeadRow(0).getCell("proName").getStyleAttributes().setHided(true);	
			this.tblMain.getColumn("proName").getStyleAttributes().setHided(true);
			this.tblMain.getHeadRow(0).getCell("building").getStyleAttributes().setHided(true);	
			this.tblMain.getColumn("building").getStyleAttributes().setHided(true);	
		}
		else{
			this.tblMain.getHeadRow(0).getCell("comName").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("comName").getStyleAttributes().setHided(false);
			this.tblMain.getHeadRow(0).getCell("proName").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("proName").getStyleAttributes().setHided(false);
			this.tblMain.getHeadRow(0).getCell("building").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("building").getStyleAttributes().setHided(false);
		}

		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();

		boolean isHasBefore = false ;
		if(getIsChange())
		{
			//变更
			detailBuilder.appendSql("SELECT  ").
			appendSql(" distinct  ").
			appendSql(" ORGUNIT.FName_l2 AS comName,  ").
			appendSql(" SELLPROJECT.FName_l2 AS proName,  ").
			appendSql(" BUILDING.FName_l2 AS building,  ").
			appendSql(" ROOM.FUnit AS unit,  ").
			appendSql(" ROOM.FDisplayName AS roomNumber,  ").
			appendSql(" PRODUCTTYPE.FName_l2 AS productType,  ").
			appendSql(" ROOMMODELTYPE.FName_l2 AS modelName,  ").
			appendSql(" PURCHASE.FCustomerNames AS customerName,  ").
			appendSql(" SALESMAN.FName_l2 AS saleManName,  ").
			appendSql(" '变更' AS businessType, ").
			appendSql(" PURCHASECHANGE.FChangeDate AS businessDate,  ").
			appendSql(" ISNULL(PURCHASECHANGE.FOldContractAmount,0) AS totalPrice,  ").
			appendSql(" ISNULL(PURCHASECHANGE.FNewContractAmount,0) - ISNULL(PURCHASECHANGE.FOldContractAmount,0) AS changeAmount, ").
			appendSql(" PURCHASECHANGE.FChangeReason as changeRemark,  ").
			appendSql(" PURCHASECHANGE.FID as id  ").
			appendSql(" FROM T_SHE_PurchaseChange AS PURCHASECHANGE ").
			appendSql(" LEFT JOIN T_SHE_Purchase AS PURCHASE ").
			appendSql(" ON PURCHASECHANGE.FPurchaseID = PURCHASE.FID ").		
			appendSql(" LEFT JOIN T_SHE_Room AS ROOM ").
			appendSql(" ON PURCHASE.FRoomID = ROOM.FID ").
			appendSql(" INNER JOIN T_PM_User AS SALESMAN ").
			appendSql(" ON PURCHASE.FSalesmanID = SALESMAN.FID ").
			appendSql(" LEFT JOIN T_SHE_SellProject AS SELLPROJECT ").
			appendSql(" ON PURCHASE.FSellProjectID = SELLPROJECT.FID ").
			appendSql(" LEFT JOIN T_SHE_RoomModel AS ROOMMODEL ").
			appendSql(" ON ROOM.FRoomModelID = ROOMMODEL.FID ").		
			appendSql(" LEFT JOIN T_SHE_RoomModelType AS ROOMMODELTYPE").
			appendSql(" ON ROOMMODEL.FRoomModelTypeID = ROOMMODELTYPE.FID").
			appendSql(" LEFT JOIN T_FDC_ProductType AS PRODUCTTYPE ").
			appendSql(" ON ROOM.FProductTypeID = PRODUCTTYPE.FID ").
			appendSql(" LEFT JOIN T_SHE_Building AS BUILDING ").
			appendSql(" ON ROOM.FBuildingID = BUILDING.FID ").
			appendSql(" LEFT JOIN T_ORG_BaseUnit AS ORGUNIT ").
			appendSql(" ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ").
			appendSql(" LEFT JOIN T_PM_User AS LASTUPDATEUSER ").
			appendSql(" ON SELLPROJECT.FLastUpdateUserID = LASTUPDATEUSER.FID ").
			appendSql(" LEFT JOIN T_SHE_Building AS BUILDINGS ").
			appendSql(" ON SELLPROJECT.FID = BUILDINGS.FSellProjectID ").
			appendSql(" LEFT JOIN T_SHE_ShareOrgUnit AS ORGUNITSHARELIST ").
			appendSql(" ON SELLPROJECT.FID = ORGUNITSHARELIST.FHeadID ").
			appendSql(" LEFT JOIN T_SHE_Subarea AS SUBAREA ").
			appendSql(" ON SELLPROJECT.FID = SUBAREA.FSellProjectID ").
			appendSql(" LEFT JOIN T_PM_User AS CREATOR ").
			appendSql(" ON SELLPROJECT.FCreatorID = CREATOR.FID ").
			appendSql(" LEFT JOIN T_FDC_CurProject AS PROJECT ").
			appendSql(" ON SELLPROJECT.FProjectID = PROJECT.FID ").
			appendSql(" LEFT JOIN T_SHE_BuildingProperty AS BUILDINGPROPERTY ").
			appendSql(" ON SELLPROJECT.FBuildingPropertyID = BUILDINGPROPERTY.FID ").
			appendSql(" LEFT JOIN T_SHE_InvestorHouse AS INVESTORHOUSE ").
			appendSql(" ON SELLPROJECT.FInvestorHouseID = INVESTORHOUSE.FID ").
			appendSql(" LEFT JOIN T_ORG_CtrlUnit AS CU ").
			appendSql(" ON SELLPROJECT.FControlUnitID = CU.FID ").
			appendSql(" where PURCHASECHANGE.FState = '4AUDITTED' ");	

			if(this.isAuditDate)
			{
				this.appendDateFilterSql(detailBuilder,"PURCHASECHANGE.FAuditTime");
			}
			else
			{
				this.appendDateFilterSql(detailBuilder,"PURCHASECHANGE.FChangeDate");
			}
			this.appendStringFilterSql(detailBuilder, "BUILDING.FID", AllBuildings);	
			
			isHasBefore = true;
		}
		if(isHasBefore && getIsRename())
		{
			detailBuilder.appendSql("\n UNION \n");
		}

		if(getIsRename())
		{
			detailBuilder.appendSql(" ").		
			// --销售更名
			appendSql(" SELECT"). 
			appendSql(" distinct"). 
			appendSql(" ORGUNIT.FName_l2 AS comName,"). 
			appendSql(" SELLPROJECT.FName_l2 AS proName,"). 
			appendSql(" BUILDING.FName_l2 AS building, ").
			appendSql(" ROOM.FUnit AS unit, ").
			appendSql(" ROOM.FDisplayName AS roomNumber, ").
			appendSql(" PRODUCTTYPE.FName_l2 AS productType, ").
			appendSql(" ROOMMODELTYPE.FName_l2 AS modelName, ").
			appendSql(" PURCHASE.FCustomerNames AS customerName,"). 
			appendSql(" SALESMAN.FName_l2 AS saleManName,  ").
			appendSql(" '更名' AS businessType,").
			appendSql(" CHANGECUSTOMER.FBookedDate AS businessDate,"). 
			appendSql(" PURCHASE.FContractTotalAmount AS totalPrice, ").
//			此处null在db2不支持，所以用0
			appendSql(" 0 AS changeAmount,").
//			appendSql(" null AS changeAmount,").
			appendSql(" '由'||CHANGECUSTOMER.FOldCustomer||'更名到'||").
			appendSql(" CHANGECUSTOMER.FNewCustomer AS changeRemark,").
//			appendSql(" CONCAT(CONCAT('由',CHANGECUSTOMER.FOldCustomer),CONCAT('更名到',").
//			appendSql(" CHANGECUSTOMER.FNewCustomer)) AS changeRemark,").
			
			appendSql(" CHANGECUSTOMER.FID AS id ").
			appendSql(" FROM T_SHE_PChCustomer AS CHANGECUSTOMER").
			appendSql(" LEFT JOIN T_SHE_Purchase AS PURCHASE").
			appendSql(" ON CHANGECUSTOMER.FPurchaseID = PURCHASE.FID").
			appendSql(" LEFT JOIN T_SHE_Room AS ROOM").
			appendSql(" ON PURCHASE.FRoomID = ROOM.FID").
			appendSql(" INNER JOIN T_PM_User AS SALESMAN ").
			appendSql(" ON PURCHASE.FSalesmanID = SALESMAN.FID ").
			appendSql(" LEFT JOIN T_SHE_SellProject AS SELLPROJECT").
			appendSql(" ON PURCHASE.FSellProjectID = SELLPROJECT.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModel AS ROOMMODEL").
			appendSql(" ON ROOM.FRoomModelID = ROOMMODEL.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModelType AS ROOMMODELTYPE").
			appendSql(" ON ROOMMODEL.FRoomModelTypeID = ROOMMODELTYPE.FID").
			appendSql(" LEFT JOIN T_FDC_ProductType AS PRODUCTTYPE").
			appendSql(" ON ROOM.FProductTypeID = PRODUCTTYPE.FID").
			appendSql(" LEFT JOIN T_SHE_Building AS BUILDING").
			appendSql(" ON ROOM.FBuildingID = BUILDING.FID").
			appendSql(" LEFT JOIN T_ORG_BaseUnit AS ORGUNIT").
			appendSql(" ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID").
			appendSql(" where CHANGECUSTOMER.FState = '4AUDITTED' ");

			if(this.isAuditDate)
			{
				this.appendDateFilterSql(detailBuilder,"CHANGECUSTOMER.FAuditTime");
			}
			else
			{
				this.appendDateFilterSql(detailBuilder,"CHANGECUSTOMER.FBookedDate");
			}
			this.appendStringFilterSql(detailBuilder, "BUILDING.FID", AllBuildings);	
			
			if(!isHasBefore)
			{
				isHasBefore = true;
			}
		}

		if(isHasBefore && getIsCheckOut())
		{
			detailBuilder.appendSql("\n UNION \n");
		}

		if(getIsCheckOut())
		{
			detailBuilder.appendSql(" ( ").

			//退房
			appendSql(" SELECT ").
			appendSql(" distinct ").
			appendSql(" ORGUNIT.FName_l2 AS comName,"). 
			appendSql(" SELLPROJECT.FName_l2 AS proName, ").
			appendSql(" BUILDING.FName_l2 AS building, ").
			appendSql(" ROOM.FUnit AS unit, ").
			appendSql(" ROOM.FDisplayName AS roomNumber, ").
			appendSql(" PRODUCTTYPE.FName_l2 AS productType, ").
			appendSql(" ROOMMODELTYPE.FName_l2 AS modelName, ").
			appendSql(" PURCHASE.FCustomerNames AS customerName, ").
			appendSql(" SALESMAN.FName_l2 AS saleManName,  ").
			appendSql(" QUITROOM.FQuitRoomState AS businessType,").
			appendSql(" QUITROOM.FQuitDate AS businessDate, ").
			appendSql(" PURCHASE.FContractTotalAmount AS totalPrice,").
			appendSql(" (sum(purEntry.FRefundmentAmount)*(-1)) AS changeAmount,").
			appendSql(" QUITROOM.FQuitReason  AS changeRemark, ").
			appendSql(" QUITROOM.FID  AS id ").
			appendSql(" FROM T_SHE_QuitRoom AS QUITROOM").
			appendSql(" LEFT OUTER JOIN T_SHE_Room AS ROOM").
			appendSql(" ON QUITROOM.FRoomID = ROOM.FID").		
			appendSql(" LEFT JOIN T_SHE_Purchase AS PURCHASE").
			appendSql(" ON QUITROOM.FPurchaseID = PURCHASE.FID").
			appendSql(" INNER JOIN T_PM_User AS SALESMAN ").
			appendSql(" ON PURCHASE.FSalesmanID = SALESMAN.FID ").
			appendSql(" LEFT OUTER JOIN T_SHE_Building AS BUILDING").
			appendSql(" ON ROOM.FBuildingID = BUILDING.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModel AS ROOMMODEL").
			appendSql(" ON ROOM.FRoomModelID = ROOMMODEL.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModelType AS ROOMMODELTYPE").
			appendSql(" ON ROOMMODEL.FRoomModelTypeID = ROOMMODELTYPE.FID").
			appendSql(" LEFT JOIN T_FDC_ProductType AS PRODUCTTYPE").
			appendSql(" ON ROOM.FProductTypeID = PRODUCTTYPE.FID").
			appendSql(" LEFT JOIN T_SHE_Building AS BUILDING1").
			appendSql(" ON ROOM.FBuildingID = BUILDING1.FID").
			appendSql(" LEFT JOIN T_SHE_SellProject AS SELLPROJECT").
			appendSql(" ON PURCHASE.FSellProjectID = SELLPROJECT.FID").
			appendSql(" LEFT JOIN T_ORG_BaseUnit AS ORGUNIT").
			appendSql(" ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID").
			appendSql(" left join T_SHE_PurchasePayListEntry as purEntry on purEntry.FHeadID = PURCHASE.fid").
			appendSql(" where QUITROOM.FState = '4AUDITTED' ");	
			if(this.isAuditDate)
			{
				this.appendDateFilterSql(detailBuilder,"QUITROOM.FAuditTime");
			}
			else
			{
				this.appendDateFilterSql(detailBuilder,"QUITROOM.FQuitDate");
			}
			this.appendStringFilterSql(detailBuilder, "BUILDING.FID", AllBuildings);	
			
			appendQuitRoomStateFilter(detailBuilder);
			
			detailBuilder.appendSql("group by ORGUNIT.FName_l2 , SELLPROJECT.FName_l2 ,  BUILDING.FName_l2 ,  ROOM.FUnit , ");
			detailBuilder.appendSql("ROOM.FDisplayName , PRODUCTTYPE.FName_l2 ,  ROOMMODELTYPE.FName_l2 ,");
			detailBuilder.appendSql("PURCHASE.FCustomerNames ,  SALESMAN.FName_l2 , ");
			detailBuilder.appendSql("QUITROOM.FQuitRoomState , QUITROOM.FQuitDate , PURCHASE.FContractTotalAmount  ,  QUITROOM.FQuitReason  ,QUITROOM.FID )");
			
			if(!isHasBefore)
			{
				isHasBefore = true;
			}
		}

		if(isHasBefore && getIsChange())
		{
			detailBuilder.appendSql("\n UNION \n");
		}

		if(getIsChange())
		{
			detailBuilder.appendSql(" ").
			//调整
			appendSql(" SELECT ").
			appendSql(" distinct ").
			appendSql(" ORGUNIT.FName_l2 AS comName,"). 
			appendSql(" SELLPROJECT.FName_l2 AS proName, ").
			appendSql(" BUILDING.FName_l2 AS building, ").
			appendSql(" ROOM.FUnit AS unit, ").
			appendSql(" ROOM.FDisplayName AS roomNumber, ").
			appendSql(" PRODUCTTYPE.FName_l2 AS productType, ").
			appendSql(" ROOMMODELTYPE.FName_l2 AS modelName, ").
			appendSql(" PURCHASE.FCustomerNames AS customerName, ").
			appendSql(" SALESMAN.FName_l2 AS saleManName,  ").
			appendSql(" '调整' AS businessType,").
			appendSql(" BILLADJUST.FBizDate AS businessDate, ").
			appendSql(" PURCHASE.FContractTotalAmount AS totalPrice,").
			appendSql(" 0-PURCHASE.FContractTotalAmount AS changeAmount,").
			appendSql(" concat(concat(concat('调整认购单',BILLADJUST.Fnumber),';'),BILLADJUST.FDescription) AS changeRemark, ").
			appendSql(" BILLADJUST.FID  AS id ").
			appendSql(" FROM T_SHE_BillAdjust AS BILLADJUST").
			appendSql(" LEFT JOIN T_SHE_Purchase AS PURCHASE").
			appendSql(" ON BILLADJUST.FPurchaseID = PURCHASE.FID").	
			appendSql(" LEFT OUTER JOIN T_SHE_Room AS ROOM").
			appendSql(" ON PURCHASE.FRoomID = ROOM.FID").		
			appendSql(" INNER JOIN T_PM_User AS SALESMAN ").
			appendSql(" ON PURCHASE.FSalesmanID = SALESMAN.FID ").
			appendSql(" LEFT OUTER JOIN T_SHE_Building AS BUILDING").
			appendSql(" ON ROOM.FBuildingID = BUILDING.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModel AS ROOMMODEL").
			appendSql(" ON ROOM.FRoomModelID = ROOMMODEL.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModelType AS ROOMMODELTYPE").
			appendSql(" ON ROOMMODEL.FRoomModelTypeID = ROOMMODELTYPE.FID").
			appendSql(" LEFT JOIN T_FDC_ProductType AS PRODUCTTYPE").
			appendSql(" ON ROOM.FProductTypeID = PRODUCTTYPE.FID").
			appendSql(" LEFT JOIN T_SHE_Building AS BUILDING1").
			appendSql(" ON ROOM.FBuildingID = BUILDING1.FID").
			appendSql(" LEFT JOIN T_SHE_SellProject AS SELLPROJECT").
			appendSql(" ON PURCHASE.FSellProjectID = SELLPROJECT.FID").
			appendSql(" LEFT JOIN T_ORG_BaseUnit AS ORGUNIT").
			appendSql(" ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID").
			appendSql(" where BILLADJUST.FState = '4AUDITTED' ");	
			if(this.isAuditDate)
			{
				this.appendDateFilterSql(detailBuilder,"BILLADJUST.FAuditTime");
			}
			else
			{
				this.appendDateFilterSql(detailBuilder,"BILLADJUST.FBizDate");
			}
			this.appendStringFilterSql(detailBuilder, "BUILDING.FID", AllBuildings);
			
			if(!isHasBefore)
			{
				isHasBefore = true;
			}
		}

		//换房
		if(isHasBefore && getIsSwap())
		{
			detailBuilder.appendSql("\n UNION \n");
		}
		if(getIsSwap())
		{
			detailBuilder.appendSql(" ").

			appendSql(" SELECT ").
			appendSql(" distinct ").
			appendSql(" ORGUNIT.FName_l2 AS comName,"). 
			appendSql(" SELLPROJECT.FName_l2 AS proName, ").
			appendSql(" BUILDING.FName_l2 AS building, ").
			appendSql(" ROOM.FUnit AS unit, ").
			appendSql(" ROOM.FDisplayName AS roomNumber, ").
			appendSql(" PRODUCTTYPE.FName_l2 AS productType, ").
			appendSql(" ROOMMODELTYPE.FName_l2 AS modelName, ").
			appendSql(" PURCHASE.FCustomerNames AS customerName, ").
			appendSql(" SALESMAN.FName_l2 AS saleManName,  ").
			appendSql(" '换房' AS businessType,").
			appendSql(" change.FChangeDate AS businessDate, ").
			appendSql(" PURCHASE.FContractTotalAmount AS totalPrice,").
			appendSql(" 0-PURCHASE.FContractTotalAmount AS changeAmount,").
			appendSql(" change.FDescription  AS changeRemark, ").
			appendSql(" change.FID  AS id ").
			appendSql(" FROM T_SHE_ChangeRoom AS change").
			appendSql(" LEFT OUTER JOIN T_SHE_Room AS ROOM").
			appendSql(" on room.fid = change.FOldRoomID").		
			appendSql(" LEFT JOIN T_SHE_Purchase AS PURCHASE").
			appendSql(" on change.FOldPurchaseID = purchase.fid").
			appendSql(" INNER JOIN T_PM_User AS SALESMAN ").
			appendSql(" ON PURCHASE.FSalesmanID = SALESMAN.FID ").
			appendSql(" LEFT OUTER JOIN T_SHE_Building AS BUILDING").
			appendSql(" ON ROOM.FBuildingID = BUILDING.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModel AS ROOMMODEL").
			appendSql(" ON ROOM.FRoomModelID = ROOMMODEL.FID").
			appendSql(" LEFT JOIN T_SHE_RoomModelType AS ROOMMODELTYPE").
			appendSql(" ON ROOMMODEL.FRoomModelTypeID = ROOMMODELTYPE.FID").
			appendSql(" LEFT JOIN T_FDC_ProductType AS PRODUCTTYPE").
			appendSql(" ON ROOM.FProductTypeID = PRODUCTTYPE.FID").
			appendSql(" LEFT JOIN T_SHE_Building AS BUILDING1").
			appendSql(" ON ROOM.FBuildingID = BUILDING1.FID").
			appendSql(" LEFT JOIN T_SHE_SellProject AS SELLPROJECT").
			appendSql(" ON PURCHASE.FSellProjectID = SELLPROJECT.FID").
			appendSql(" LEFT JOIN T_ORG_BaseUnit AS ORGUNIT").
			appendSql(" ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID").
			appendSql(" where change.FState = '4AUDITTED' ");	

			if(this.isAuditDate)
			{
				this.appendDateFilterSql(detailBuilder,"change.FAuditTime");
			}
			else
			{
				this.appendDateFilterSql(detailBuilder,"change.FChangeDate");
			}
			this.appendStringFilterSql(detailBuilder, "BUILDING.FID", AllBuildings);	
			
		}

		if(getIsChange() || getIsCheckOut() || getIsRename() || getIsSwap())
		{
			detailBuilder.appendSql(" ORDER BY comName,proName,building,unit,roomNumber,productType,modelName,customerName");


			IRowSet detailSet = detailBuilder.executeQuery();
			
			logger.info(detailBuilder.getTestSql());
			while(detailSet.next())
			{
				IRow row = this.tblMain.addRow();
				row.getCell("comName").setValue(detailSet.getString("comName"));
				row.getCell("proName").setValue(detailSet.getString("proName"));
				row.getCell("building").setValue(detailSet.getString("building"));
				row.getCell("unit").setValue(detailSet.getString("unit"));
				row.getCell("roomNumber").setValue(detailSet.getString("roomNumber"));
				row.getCell("productType").setValue(detailSet.getString("productType"));
				row.getCell("modelName").setValue(detailSet.getString("modelName"));
				row.getCell("customerName").setValue(detailSet.getString("customerName"));
				row.getCell("saleManName").setValue(detailSet.getString("saleManName"));
				
				if(detailSet.getString("businessType") != null)
				{
					if(QuitRoomStateEnum.getEnum(detailSet.getString("businessType")) != null)
					{
						row.getCell("businessType").setValue(QuitRoomStateEnum.getEnum(detailSet.getString("businessType")).getAlias());
					}
					else
					{
						row.getCell("businessType").setValue(detailSet.getString("businessType"));
					}
				}
				row.getCell("businessDate").setValue(detailSet.getDate("businessDate"));
				row.getCell("totalPrice").setValue(detailSet.getString("totalPrice"));
				row.getCell("changeAmount").setValue(detailSet.getString("changeAmount"));
				row.getCell("changeRemark").setValue(detailSet.getString("changeRemark"));	
				row.getCell("id").setValue(detailSet.getString("id"));	
			}

		}
		this.tblMain.getMergeManager().mergeBlock(0, 0, this.tblMain.getRowCount() - 1, 3, KDTMergeManager.FREE_MERGE);
	}

	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		try
		{
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	/**
	 * 添加时间过滤
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendDateFilterSql(FDCSQLBuilder builder, String proName) throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll"))
		{
			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null)
			{
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null)
			{
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}
		}
	}
	/**
	 * 添加字符串过滤
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendStringFilterSql(FDCSQLBuilder builder, String proName,String proValue) throws Exception
	{		
		if(proName != null && proValue!=null){
			builder.appendSql(" and " + proName + " in (" + proValue + ")");
		}
	}


	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}


	protected void checkTableParsed() {
		super.checkTableParsed();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}


	private SpecialBusinessDetailFilterUI getFilterUI() throws Exception {
		if (this.filterUI == null) {
			this.filterUI = new SpecialBusinessDetailFilterUI(this, this.actionOnLoad);
		}
		return this.filterUI;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		allBuildingIds = "'0'";
		String nodeName = null;
		if (node == null) {
			return;
		}
		this.getAllBuildingIds(node);
		if (node.getUserObject() instanceof OrgStructureInfo) {
			nodeName = "company";		    
		}
		else if (node.getUserObject() instanceof SellProjectInfo) {
			nodeName = "sellProject";

		}
		else if (node.getUserObject() instanceof BuildingInfo) {
			nodeName = "buiding";
		}
		else{
			return;
		}

		GetSearchResult(nodeName,allBuildingIds);	
	}

	/**
	 * 查询所有的楼栋id
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {	
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;		
		if(thisNode.getUserObject() instanceof BuildingInfo){
			BuildingInfo building = (BuildingInfo)thisNode.getUserObject();
			allBuildingIds = allBuildingIds+",'" + building.getId().toString()+"'";
		}

		if(!treeNode.isLeaf()){
			int childCount = treeNode.getChildCount();
			while(childCount>0) {				
				getAllBuildingIds(treeNode.getChildAt(childCount-1));		
				childCount --;
			}	
		}	
	}

	protected OrgUnitInfo getOrgUnitInfo()
	{
		return SysContext.getSysContext().getCurrentOrgUnit(this.getMainBizOrgType());
	}


	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}





	private void initTable() {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		this.tblMain.getColumn("totalPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));	
		this.tblMain.getColumn("changeAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));	
	}

	protected void initTableParams() {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}


	protected String getKeyFieldName() {
		return "id";
	}

	private Date getBeginQueryDate() throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getBeginQueryDate(para);
	}

	private Date getEndQueryDate() throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getEndQueryDate(para);
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	protected boolean isAllowDefaultSolutionNull() {
		return false;
	}
	/**
	 * 获取所选分类统计的值
	 * @throws Exception 
	 */
	protected boolean getIsCheckOut() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsCheckOut(para);
	}
	protected boolean getIsOrderQuit() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsOrderQuit(para);
	}
	protected boolean getIsPurchaseQuit() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsPurchaseQuit(para);
	}
	protected boolean getIsSignQuit() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsSignQuit(para);
	}

	protected boolean getIsSwap() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsSwap(para);
	}

	protected boolean getIsChange() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsChange(para);
	}

	protected boolean getIsRename() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsRename(para);
	}
	protected boolean getIsAdjust() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return getFilterUI().getIsAdjust(para);
	}
}