/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ArrearageQueryListUI extends AbstractArrearageQueryListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ArrearageQueryListUI.class);
        
    boolean executeFlag = false;
    
    public ArrearageQueryListUI() throws Exception
    {
        super();
    }


	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode)this.treeMain.getModel().getRoot());
	}
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		initTree();
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionLocate.setVisible(false);
	}
	
	
	protected boolean initDefaultFilter() {
		return true;
	}
	private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(500);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	private ArrearageQueryFilterUI filterUI = null;
	private ArrearageQueryFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ArrearageQueryFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	

	
	protected void refresh(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}
	
	protected void execQuery() {
		if(!executeFlag) {
			executeFlag = true;
			return;
		}
		
		this.tblMain.removeRows(false);
		try {
			FDCSQLBuilder unionBuilder = new FDCSQLBuilder();	
			
			String sqlStr = getExecuteSqlString();
			if(sqlStr.equals("")) return;
			unionBuilder.appendSql(sqlStr);
			

			IRowSet tableSet = unionBuilder.executeQuery();
			while (tableSet.next()) {
				String ID = tableSet.getString("ID");
				String sellProName = tableSet.getString("SELLPROJECTNAME");
				String subAreaName = tableSet.getString("SUBAREANAME");
				String buildName = tableSet.getString("BUILDINGNAME");
				String roomUnit = tableSet.getString("ROOMUNIT");
				String roomNumber = tableSet.getString("ROOMNUMBER");
				String custNames = tableSet.getString("CUSTOMERNAMES");
				String custPhones = tableSet.getString("CUSTOMERPHONES");
				String roomModelName = tableSet.getString("ROOMMODELNAME");
				String productName = tableSet.getString("PRODUCTTYPENAME");
				String moneyDefineName = tableSet.getString("MONEYDEFINENAME");
				BigDecimal payApAmount = tableSet.getBigDecimal("PAYLISTENTRYAPAMOUNT");
				java.sql.Date payApDateTemp = tableSet.getDate("PAYLISTENTRYAPDATE");
				Date payApDate = null;
				if(payApDateTemp!=null) payApDate = new Date(payApDateTemp.getTime());					
				BigDecimal actPayAmount = tableSet.getBigDecimal("PAYLISTENTRYACTPAYAMOUNT");
				java.sql.Date actPayDateTemp = tableSet.getDate("PAYLISTENTRYACTPAYDATE");
				Date actPayDate = null;
				if(actPayDateTemp!=null) actPayDate = new Date(actPayDateTemp.getTime());
				BigDecimal arearageAmount = tableSet.getBigDecimal("ARREARAGEAMOUNT");
				BigDecimal loanArageAmount = tableSet.getBigDecimal("LOANARAGEAMOUNT");
				java.sql.Date arearageTimeTemp = tableSet.getDate("ARREARAGETIME");
				Date arearageTime = null;
				if(arearageTimeTemp!=null) arearageTime = new Date(arearageTimeTemp.getTime());
				Date toSignDate = null;
				java.sql.Date toSignDateTemp = tableSet.getDate("ToSignDate");
				if(toSignDateTemp!=null)  toSignDate = new Date(toSignDateTemp.getTime());
				String saleManName = tableSet.getString("SALESMANNAME");
				String roomSellState = tableSet.getString("ROOMSELLSTATE");
				int seq = tableSet.getInt("PAYLISTENTRYSEQ");
				int arrearageDay = tableSet.getInt("arrearageDay");
				String roomID = tableSet.getString("ROOMID");   //得到ROOMID
				
				IRow row = this.tblMain.addRow();
				row.getCell("id").setValue(ID);
				row.getCell("sellProject.name").setValue(sellProName);
				row.getCell("subarea.name").setValue(subAreaName);
				row.getCell("building.name").setValue(buildName);
				row.getCell("room.unit").setValue(roomUnit);
				row.getCell("room.number").setValue(roomNumber);
				row.getCell("customerNames").setValue(custNames); 
				row.getCell("customerPhones").setValue(custPhones);
				row.getCell("roomModel.name").setValue(roomModelName);
				row.getCell("productType.name").setValue(productName);
				row.getCell("moneyDefine.name").setValue(moneyDefineName);
				row.getCell("payListEntry.apAmount").setValue(payApAmount);
				row.getCell("payListEntry.apDate").setValue(payApDate);
				row.getCell("payListEntry.actPayAmount").setValue(actPayAmount);
				row.getCell("payListEntry.actPayDate").setValue(actPayDate);
				row.getCell("arrearageAmount").setValue(arearageAmount);
				row.getCell("loanArageAmount").setValue(loanArageAmount);
				
				//通过ROOMID和款项类型把得到的按揭银行设置到相应的记录里
				if(map.get(roomID)!=null){
					Map bankMap = (Map)map.get(roomID);
					if(bankMap.get(moneyDefineName)!=null){
						row.getCell("loanBank").setValue(bankMap.get(moneyDefineName).toString());
					}
				}
				
				row.getCell("arrearageTime").setValue(arearageTime);
				row.getCell("signDate").setValue(toSignDate); 
				row.getCell("salesman.name").setValue(saleManName);
				row.getCell("room.sellState").setValue(roomSellState);
				row.getCell("payListEntry.seq").setValue(new Integer(seq));
				row.getCell("arrearageDay").setValue(new Integer(arrearageDay));				
			}	
			
			//刷新arrearageDay列
			for(int i=0;i<this.tblMain.getRowCount();i++) {
				IRow row = this.tblMain.getRow(i);
				Integer arrearageDay = (Integer)row.getCell("arrearageDay").getValue();
				if(arrearageDay.intValue()>0){
					BigDecimal arearageAmount = (BigDecimal)row.getCell("arrearageAmount").getValue();
					if(arearageAmount.compareTo(new BigDecimal("0"))>0) 
						row.getCell("arrearageDay").getStyleAttributes().setFontColor(Color.red);
					else
						row.getCell("arrearageDay").getStyleAttributes().setFontColor(Color.blue);
				}
				
				BigDecimal arrearageAmount = (BigDecimal)row.getCell("arrearageAmount").getValue();
				if(arrearageAmount==null) arrearageAmount = FDCHelper.ZERO;
				BigDecimal loanArageAmount = (BigDecimal)row.getCell("loanArageAmount").getValue();
				if(loanArageAmount==null)  loanArageAmount = FDCHelper.ZERO;
				row.getCell("unLoanArageAmount").setValue(arrearageAmount.subtract(loanArageAmount));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
		
		
		
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());	
		if(filterMap.get("radioShowGroup")!=null) {
			boolean radioShowGroup = ((Integer)filterMap.get("radioShowGroup")).intValue()>0?true:false;
			if(radioShowGroup) {	
				//this.tblMain.getGroupManager().removeGroup();	
				this.tblMain.getGroupManager().reInitialize();
				
				this.tblMain.getGroupManager().setGroup(true);
				this.tblMain.getGroupManager().setTotalize(true);
				IRow row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(-1);
				row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);	
				row0.getCell("sellProject.name").setValue("总计:");
				row0.getCell("payListEntry.apAmount").setExpressions(KDTGroupManager.STAT_SUM);
				row0.getCell("payListEntry.actPayAmount").setExpressions(KDTGroupManager.STAT_SUM);
				row0.getCell("arrearageAmount").setExpressions(KDTGroupManager.STAT_SUM);
				row0.getCell("loanArageAmount").setExpressions(KDTGroupManager.STAT_SUM);
				row0.getCell("unLoanArageAmount").setExpressions(KDTGroupManager.STAT_SUM);
				
				for(int i=1;i<=8;i++)
					this.tblMain.getColumn(i).setGroup(true);	
				
				this.tblMain.getColumn("productType.name").setGroup(true);
				
				this.tblMain.getColumn("productType.name").setStat(true);
				row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(9);		
				row0.getCell("productType.name").setValue("小计:");		
				row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);	
				row0.getCell("payListEntry.apAmount").setExpressions(KDTGroupManager.STAT_SUM);
				row0.getCell("payListEntry.actPayAmount").setExpressions(KDTGroupManager.STAT_SUM);
				row0.getCell("arrearageAmount").setExpressions(KDTGroupManager.STAT_SUM);
				//row0.getCell(i).setExpressions("=Cell(ROW_INDEX-1,COLUMN_INDEX).getValue()");	
				
				this.tblMain.getGroupManager().group();		
			}					
		}
		
	}
	
	Map map = new HashMap();    //存放相应款项的按揭银行
	String filterNode ="";      //专门对按揭银行的过滤条件
	private String getExecuteSqlString() throws BOSException, SQLException{
		StringBuffer sonFilterSql = new StringBuffer();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {   //根据选中的树的节点过滤
			if (node.getUserObject() instanceof Integer) {  //单元  -- 已作废
				Integer unit = (Integer) node.getUserObject();
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
				sonFilterSql.append("and BUILDING.FID = '"+building.getId().toString()+"' and ROOM.unit ="+unit.intValue() + " ");
			}else if (node.getUserObject() instanceof BuildingUnitInfo) {  
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
				sonFilterSql.append("and BUILDING.FID = '"+building.getId().toString()+"' and ROOM.FBuildUnitID ='"+buildUnit.getId().toString() + "' ");
			} else if (node.getUserObject() instanceof BuildingInfo) {  //楼栋
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				sonFilterSql.append("and BUILDING.FID = '"+building.getId().toString()+"' ");				
			} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
				SubareaInfo subarea = (SubareaInfo) node.getUserObject();
				sonFilterSql.append("and BUILDING.subarea.FID = '"+subarea.getId().toString()+"' ");
			} else if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
				sonFilterSql.append("and SELLPROJECT.FID = '"+sellProject.getId().toString()+"' ");
			}else{  //取所选节点下的所有项目过滤
				StringBuffer sellProFilter = new StringBuffer();
				getAllProjectIds(node,sellProFilter);
				if(!sellProFilter.toString().equals("")) sonFilterSql.append("and ("+sellProFilter.toString().replaceFirst("or", "")+")");
			}
			filterNode = sonFilterSql.toString();
		}else{
			StringBuffer sellProFilter = new StringBuffer();
			getAllProjectIds((TreeNode)treeMain.getModel().getRoot(),sellProFilter);
			if(!sellProFilter.toString().equals("")) sonFilterSql.append("and ("+sellProFilter.toString().replaceFirst("or", "")+")");
			filterNode = sonFilterSql.toString();
		}

		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());	
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		if(filterMap.get("purBeginDate")!=null)  {
			Date purBeginDate = new Date(((Timestamp)filterMap.get("purBeginDate")).getTime());
			sonFilterSql.append("and PURCHASE.FPurchaseDate >= {ts'"+FORMAT_DAY.format(purBeginDate)+"'} ");
		}
		if(filterMap.get("purEndDate")!=null){
			Date purEndDate = new Date(((Timestamp)filterMap.get("purEndDate")).getTime());
			sonFilterSql.append("and PURCHASE.FPurchaseDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'} ");
		}
		if(filterMap.get("dpAppBeginDate")!=null) {
			Date dpAppBeginDate = new Date(((Timestamp)filterMap.get("dpAppBeginDate")).getTime());
			sonFilterSql.append("and PAYLISTENTRY.FApDate >= {ts'"+FORMAT_DAY.format(dpAppBeginDate)+"'} ");
		}
		if(filterMap.get("dpAppEndDate")!=null)	 {
			Date dpAppEndDate = new Date(((Timestamp)filterMap.get("dpAppEndDate")).getTime());
			sonFilterSql.append("and PAYLISTENTRY.FApDate < {ts'"+FORMAT_DAY.format(dpAppEndDate)+"'} ");
		}
		if(filterMap.get("PurIdStr")!=null)   {
			String PurIdStr = (String)filterMap.get("PurIdStr");	//认购单的id集合
			sonFilterSql.append("and PURCHASE.FID in ("+PurIdStr+") ");
		}
		if(filterMap.get("MoneyTypeIdStr")!=null)  {
			String MoneyTypeIdStr = (String)filterMap.get("MoneyTypeIdStr");	//款项类型的id集合
			sonFilterSql.append("and MONEYDEFINE.FID in ("+MoneyTypeIdStr+") ");
		}else{
			return "";		//确保一定选择了款项类型
		}
		if(filterMap.get("RoomSellStatuStr")!=null)  {
			String RoomSellStatuStr = (String)filterMap.get("RoomSellStatuStr");	//房间销售状态值
			if(RoomSellStatuStr!=null && !RoomSellStatuStr.equals(""))
			sonFilterSql.append("and ROOM.FSellState in ("+RoomSellStatuStr+") ");
		}

		StringBuffer fatherFilterSql = new StringBuffer();
		if(filterMap.get("checkYuQiArage")!=null) {   //逾期欠款 = 欠款>0 and 逾期时间 > 0天 (逾期欠款,已经到期尚未付清)
			boolean checkYuQiArage = ((Integer)filterMap.get("checkYuQiArage")).intValue()>0?true:false;
			if(checkYuQiArage) {
				int areageDay = 0;
				if(filterMap.get("textYuQiDayNum")!=null)	{
					areageDay = ((Integer)filterMap.get("textYuQiDayNum")).intValue();
				}
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT > 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME) >"+areageDay+") ");
			}
		}
		if(filterMap.get("checkUnYuQiArage")!=null)  {   //未逾期欠款,没有到应付日期的欠款
			boolean checkUnYuQiArage = ((Integer)filterMap.get("checkUnYuQiArage")).intValue()>0?true:false;
			if(checkUnYuQiArage) {
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT > 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME)<=0 ) ");
			}
		}
		if(filterMap.get("checkYuQiPay")!=null) {   //逾期付清,曾经逾期现在已经付清的.
			boolean checkYuQiPay = ((Integer)filterMap.get("checkYuQiPay")).intValue()>0?true:false;
			if(checkYuQiPay) {
				int areageDay = 0;
				if(filterMap.get("textYuQiDayNum")!=null)	{
					areageDay = ((Integer)filterMap.get("textYuQiDayNum")).intValue();
				}
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT <= 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME)>"+areageDay+") ");
			}
		}
		if(filterMap.get("checkPayOnTime")!=null)  {  //按时付清,正常付款
			boolean checkPayOnTime = ((Integer)filterMap.get("checkPayOnTime")).intValue()>0?true:false;
			if(checkPayOnTime) {
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT <= 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME)<=0 ) ");
			}			
		}
		if(fatherFilterSql.toString().equals("")) return "";   //确保一定选择了欠款类型			
		
		StringBuffer shouldSql = new StringBuffer();
		StringBuffer elseSql = new StringBuffer();
		shouldSql.append("SELECT PURCHASE.FID ID, ROOM.FID ROOMID, SELLPROJECT.FName_l2 SELLPROJECTNAME, SUBAREA.FName_l2 SUBAREANAME,  ");
		shouldSql.append("BUILDING.FName_l2 BUILDINGNAME, BuildingUnit.FName_l2 ROOMUNIT, ROOM.FNumber ROOMNUMBER,"); 
		shouldSql.append("PURCHASE.FCustomerNames CUSTOMERNAMES,PURCHASE.FCustomerPhones CUSTOMERPHONES, ROOMMODEL.FName_l2 ROOMMODELNAME, PRODUCTTYPE.FName_l2 PRODUCTTYPENAME,"); 
		shouldSql.append("MONEYDEFINE.FName_l2 MONEYDEFINENAME, PAYLISTENTRY.FApAmount PAYLISTENTRYAPAMOUNT, PAYLISTENTRY.FApDate PAYLISTENTRYAPDATE,"); 
		shouldSql.append("PAYLISTENTRY.FActPayAmount PAYLISTENTRYACTPAYAMOUNT, PAYLISTENTRY.FActPayDate PAYLISTENTRYACTPAYDATE,"); 
		shouldSql.append("(PAYLISTENTRY.FApAmount -  CASE WHEN (PAYLISTENTRY.FActPayAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FActPayAmount END) ARREARAGEAMOUNT,");
		shouldSql.append("(CASE WHEN MONEYDEFINE.FMoneyType in ('LoanAmount','AccFundAmount') THEN (PAYLISTENTRY.FApAmount -  CASE WHEN (PAYLISTENTRY.FActPayAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FActPayAmount END) ELSE 0 END) LOANARAGEAMOUNT,"); //增加按揭类的欠款
		shouldSql.append("(CASE  WHEN (PAYLISTENTRY.FActPayDate IS NULL) THEN NOW() ELSE PAYLISTENTRY.FActPayDate END ) ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间 
		shouldSql.append("ROOM.FToPurchaseDate ToSignDate, SALESMAN.FName_l2 SALESMANNAME, ROOM.FSellState ROOMSELLSTATE, PAYLISTENTRY.FSeq PAYLISTENTRYSEQ ");
		shouldSql.append("FROM T_SHE_Purchase PURCHASE ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_PurchasePayListEntry PAYLISTENTRY ON PURCHASE.FID = PAYLISTENTRY.FHeadID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_Room ROOM ON PURCHASE.FRoomID = ROOM.FID ");
		shouldSql.append("LEFT OUTER JOIN T_PM_User SALESMAN ON PURCHASE.FSalesmanID = SALESMAN.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_MoneyDefine MONEYDEFINE ON PAYLISTENTRY.FMoneyDefineID = MONEYDEFINE.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_RoomModel ROOMMODEL ON ROOM.FRoomModelID = ROOMMODEL.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_Building BUILDING ON ROOM.FBuildingID = BUILDING.FID ");
		shouldSql.append("LEFT OUTER JOIN T_FDC_ProductType PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_SellProject SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_Subarea SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_BuildingUnit BuildingUnit ON ROOM.FBuildUnitID = BuildingUnit.FID ");
		shouldSql.append("WHERE (PAYLISTENTRY.FApDate IS NOT NULL) ");
		//排除认购状态为 ChangeRoomBlankOut 换房作废；QuitRoomBlankOut 退房作废；NoPayBlankOut 未付款作废； ManualBlankOut 手工作废;调整作废
		shouldSql.append(" AND PURCHASE.FPurchaseState NOT IN ('"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"', '"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE +"','"+PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"','"
							+PurchaseStateEnum.NOPAYBLANKOUT_VALUE+"','"+PurchaseStateEnum.MANUALBLANKOUT_VALUE+"') ");
		shouldSql.append(sonFilterSql.toString());
		
		elseSql.append("SELECT PURCHASE.FID ID, ROOM.FID ROOMID, SELLPROJECT.FName_l2 SELLPROJECTNAME, SUBAREA.FName_l2 SUBAREANAME, ");
		elseSql.append("BUILDING.FName_l2 BUILDINGNAME, BuildingUnit.Fname_l2 ROOMUNIT, ROOM.FNumber ROOMNUMBER, ");
		elseSql.append("PURCHASE.FCustomerNames CUSTOMERNAMES,PURCHASE.FCustomerPhones CUSTOMERPHONES, ROOMMODEL.FName_l2 ROOMMODELNAME, PRODUCTTYPE.FName_l2 PRODUCTTYPENAME,"); 
		elseSql.append("MONEYDEFINE.FName_l2 MONEYDEFINENAME, PAYLISTENTRY.FApAmount PAYLISTENTRYAPAMOUNT, PAYLISTENTRY.FApDate PAYLISTENTRYAPDATE,"); 
		elseSql.append("PAYLISTENTRY.FActPayAmount PAYLISTENTRYACTPAYAMOUNT, PAYLISTENTRY.FActPayDate PAYLISTENTRYACTPAYDATE, ");
		elseSql.append("(PAYLISTENTRY.FApAmount - CASE WHEN (PAYLISTENTRY.FActPayAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FActPayAmount END) ARREARAGEAMOUNT, ");
		elseSql.append("0 LOANARAGEAMOUNT, "); //增加按揭类的欠款
		elseSql.append("(CASE  WHEN (PAYLISTENTRY.FActPayDate IS NULL) THEN NOW() ELSE PAYLISTENTRY.FActPayDate END) ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间
		elseSql.append("ROOM.FToPurchaseDate ToSignDate,SALESMAN.FName_l2 SALESMANNAME, ROOM.FSellState ROOMSELLSTATE, (100 + PAYLISTENTRY.FSeq) PAYLISTENTRYSEQ ");
		elseSql.append("FROM T_SHE_Purchase PURCHASE ");
		elseSql.append("LEFT OUTER JOIN T_SHE_PurchaseElsePayListEntry PAYLISTENTRY ON PURCHASE.FID = PAYLISTENTRY.FHeadID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_Room ROOM ON PURCHASE.FRoomID = ROOM.FID ");
		elseSql.append("LEFT OUTER JOIN T_PM_User SALESMAN ON PURCHASE.FSalesmanID = SALESMAN.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_MoneyDefine MONEYDEFINE ON PAYLISTENTRY.FMoneyDefineID = MONEYDEFINE.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_RoomModel ROOMMODEL ON ROOM.FRoomModelID = ROOMMODEL.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_Building BUILDING ON ROOM.FBuildingID = BUILDING.FID ");
		elseSql.append("LEFT OUTER JOIN T_FDC_ProductType PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_SellProject SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_Subarea SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_BuildingUnit BuildingUnit ON ROOM.FBuildUnitID = BuildingUnit.FID ");		
		elseSql.append("WHERE (PAYLISTENTRY.FApDate IS NOT NULL) ");
		//排除认购状态为 ChangeRoomBlankOut 换房作废；QuitRoomBlankOut 退房作废；NoPayBlankOut 未付款作废； ManualBlankOut 手工作废；调整作废
		elseSql.append(" AND PURCHASE.FPurchaseState NOT IN ('"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"', '"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE	+"','"+PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"','"
							+PurchaseStateEnum.NOPAYBLANKOUT_VALUE+"','"+PurchaseStateEnum.MANUALBLANKOUT_VALUE+"') ");
		elseSql.append(sonFilterSql.toString());
		
		//MoenyType_Num = "moneyTypeNum";   //款项类型种类 ，存数字  0全部，1应收，2其它部分
		int textYuQiDayNum = 0;
		if(filterMap.get("moneyTypeNum")!=null)  textYuQiDayNum = ((Integer)filterMap.get("moneyTypeNum")).intValue();
		StringBuffer unionBuilder = new StringBuffer();   //
		unionBuilder.append("select ARREARAGEQUERY.*,DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME) arrearageDay from (");
		if(textYuQiDayNum==0) 
			unionBuilder.append(shouldSql.toString()+" union "+elseSql.toString());
		else if(textYuQiDayNum==1)
			unionBuilder.append(shouldSql.toString());
		else if(textYuQiDayNum==2)
			unionBuilder.append(elseSql.toString());
		unionBuilder.append(") ARREARAGEQUERY ");
		unionBuilder.append("WHERE " + fatherFilterSql.toString().replaceFirst("or", ""));
		unionBuilder.append("ORDER BY SELLPROJECTNAME ASC,SUBAREANAME ASC,BUILDINGNAME ASC,ROOMUNIT ASC,ROOMNUMBER ASC,PAYLISTENTRYSEQ ASC");
		
		//查出按揭银行放到MAP里
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select room.FID ROOMID, moneyDefine.FName_l2 MONEYNAME, bank.FName_l2 LOANBANK from t_she_Roomloan roomLoan " +
				"inner join t_she_MoneyDefine moneyDefine on roomLoan.FMmType=moneyDefine.FID " +
				"inner join t_she_LoanData loanData on roomLoan.FLoanDataID=loanData.FID " +
				"inner join t_bd_bank bank on loanData.FBankID=bank.FID " +
				"left outer join t_she_Room room on roomLoan.FRoomID = room.FID " +
				"LEFT OUTER JOIN T_SHE_Building BUILDING ON ROOM.FBuildingID = BUILDING.FID " +
				"LEFT OUTER JOIN T_SHE_SellProject SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID " +
				"LEFT OUTER JOIN T_SHE_Subarea SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID " +
				"LEFT OUTER JOIN T_SHE_BuildingUnit BuildingUnit ON ROOM.FBuildUnitID = BuildingUnit.FID " +
				"where 1=1" +
				filterNode.toString());
		IRowSet rowSet = builder.executeQuery();
		map.clear();
		Map bankMap = new HashMap();
		while(rowSet.next()){
			String roomID = rowSet.getString("ROOMID");
			String moneyName = rowSet.getString("MONEYNAME");
			String loanBank = rowSet.getString("LOANBANK");
			bankMap.put(moneyName, loanBank);
			map.put(roomID, bankMap);
		}
		
		return unionBuilder.toString();
	}
	
	
	
	
	protected String getEditUIModal() {
		return  UIFactoryName.NEWTAB;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseFactory.getRemoteInstance();
	}
	
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//		if (node == null) {      不选树的节点就查询所有，这里就不用RETURN了
//			return;
//		}
		
		this.execQuery();	
	}


	protected String getEditUIName() {
		return PurchaseEditUI.class.getName();
	}


	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	
	
/*	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		return;
	}*/
    
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		return;
	}
	
	
	/**
	 * 查询所有的销售项目id,组合成sql
	 */
	private void getAllProjectIds(TreeNode treeNode,StringBuffer filterSql) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		if(thisNode.getUserObject() instanceof SellProjectInfo){
			 SellProjectInfo project = (SellProjectInfo)thisNode.getUserObject();
			 filterSql.append("or SELLPROJECT.FID='" + project.getId().toString()+"' ");
		}

		int childCount = treeNode.getChildCount();
		while(childCount>0) {				
			getAllProjectIds(treeNode.getChildAt(childCount-1),filterSql);		
			 childCount --;
		}		
	}
	
	
    
    
}