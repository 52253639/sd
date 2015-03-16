/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.client.AimCostClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomReceiveFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomReceiveUI extends AbstractRoomReceiveUI {
	private static final Logger logger = CoreUIObject
	.getLogger(RoomReceiveUI.class);
	private Set allBuildingIds = null; // 所包含楼栋id
	private Map moneyTypeColumnNameMap = null;// 款项类别列
	private Map moneyNameColumnMap = null; // 款项名称
	private Map moneyMap = null;
	
	private Map dataMap = null;  //过滤条件集合
	Map roomIdMap = new HashMap();
	Set moneySet = null;
	Map moneyNameMap = null;
	private RoomDisplaySetting setting = new RoomDisplaySetting();

	public RoomReceiveUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys,SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()));
//		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
//				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());

		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
	}

	private void initTable() {
		this.tblMain.checkParsed();
//		this.tblMain.enable(false);
//		this.tblMain.getColumn("projectName").getStyleAttributes().setLocked(true);
		for(int i = 1;i < 34;i++){
			this.tblMain.getColumn(i).getStyleAttributes().setLocked(true);
		}

		this.tblMain.getColumn("buildingArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("contractAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("contractAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("sellAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("sellAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("noLoanAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("noLoanAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("LoanAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("LoanAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("contractBackAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("contractBackAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("arrearageAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("arrearageAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("overflowAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("overflowAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("compensateAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("compensateAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblMain.getColumn("actCompensateAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actCompensateAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		initTree();
		hidderButton();
		this.treeMain.setSelectionRow(0);
//		this.execQuery();
		SimpleKDTSortManager.setTableSortable(tblMain);
//		addTotalRow();
	}

	private void hidderButton() {
		this.menuEdit.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnView.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnLocate.setVisible(false);
		this.actionView.setVisible(false);
		this.actionQuery.setVisible(false);  //过滤按钮
		this.actionLocate.setVisible(false);
	}

//	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
//			EntityViewInfo viewInfo) {
//		return super.getQueryExecutor(queryPK, viewInfo);
//	}
	
	//填充query可以用查出的数据
	protected void queryDate(IRowSet rowSet){
		BigDecimal buildingArea = FDCHelper.ZERO;
		BigDecimal actualBuildingArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		BigDecimal actualRoomArea = FDCHelper.ZERO;
		BigDecimal dealRoomPrice = FDCHelper.ZERO;
		BigDecimal contractAmount = FDCHelper.ZERO;
//		BigDecimal sellAmount = FDCHelper.ZERO;
		long count=0;
		try {
//			this.tblMain.removeRows();
			while(rowSet.next()){
				IRow row = this.tblMain.addRow();
				row.getCell("id").setValue(rowSet.getString("ID"));

				row.getCell("orgName").setValue(rowSet.getString("orgName"));
				row.getCell("projectName").setValue(rowSet.getString("projectName"));
				row.getCell("subareaName").setValue(rowSet.getString("subareaName"));
				row.getCell("buildingName").setValue(rowSet.getString("buildingName"));
				row.getCell("unit").setValue(rowSet.getString("unit"));
				row.getCell("roomNumber").setValue(rowSet.getString("roomNumber"));
				row.getCell("contractNumber").setValue(rowSet.getString("contractNumber"));
				row.getCell("signDate").setValue(rowSet.getDate("signDate"));
//				row.getCell("isOnRecord").setValue(rowSet.getString("isOnRecord"));
				row.getCell("isOnRecord").setValue(Boolean.valueOf(rowSet.getBoolean("isOnRecord")));
//				row.getCell("isOnRecord").setValue(Boolean.valueOf(true));
				row.getCell("customerNames").setValue(rowSet.getString("customerNames"));
				row.getCell("salesman").setValue(rowSet.getString("salesman"));
				row.getCell("productType").setValue(rowSet.getString("productType"));
				row.getCell("roomModel").setValue(rowSet.getString("roomModel"));
				
				buildingArea = buildingArea.add(rowSet.getBigDecimal("buildingArea")!=null?rowSet.getBigDecimal("buildingArea"):new BigDecimal(0));	
				actualBuildingArea = actualBuildingArea.add(rowSet.getBigDecimal("actualBuildingArea")!=null?rowSet.getBigDecimal("actualBuildingArea"):new BigDecimal(0));	
				roomArea = roomArea.add(rowSet.getBigDecimal("roomArea")!=null?rowSet.getBigDecimal("roomArea"):new BigDecimal(0));	
				actualRoomArea = actualRoomArea.add(rowSet.getBigDecimal("actualRoomArea")!=null?rowSet.getBigDecimal("actualRoomArea"):new BigDecimal(0));	
				dealRoomPrice = dealRoomPrice.add(rowSet.getBigDecimal("dealRoomPrice")!=null?rowSet.getBigDecimal("dealRoomPrice"):new BigDecimal(0));	
				contractAmount = contractAmount.add(rowSet.getBigDecimal("contractAmount")!=null?rowSet.getBigDecimal("contractAmount"):new BigDecimal(0));	
				
//				row.getCell("buildingArea").setValue(buildingArea);
				row.getCell("buildingArea").setValue(rowSet.getBigDecimal("buildingArea"));
				row.getCell("actualBuildingArea").setValue(rowSet.getBigDecimal("actualBuildingArea"));
				row.getCell("roomArea").setValue(rowSet.getBigDecimal("roomArea"));
				row.getCell("actualRoomArea").setValue(rowSet.getBigDecimal("actualRoomArea"));
				row.getCell("purchaseDate").setValue(rowSet.getDate("purchaseDate"));
				row.getCell("dealRoomPrice").setValue(rowSet.getBigDecimal("dealRoomPrice"));
				row.getCell("contractAmount").setValue(rowSet.getBigDecimal("contractAmount"));
//				row.getCell("sellAmount").setValue(rowSet.getBigDecimal("contractAmount"));
				row.getCell("sellAmount").setValue(row.getCell("contractAmount").getValue());
				row.getCell("payType").setValue(rowSet.getString("payType"));
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
			for(int i = 0;i<tblMain.getRowCount();i++){
				IRow row = this.tblMain.getRow(i);
				row.getCell("orgName").setValue(SysContext.getSysContext().getCurrentSaleUnit());
			}
		}

		if (this.tblMain.getFootManager() != null && this.tblMain.getFootRow(0) != null) {
			this.tblMain.getFootRow(0).getCell("buildingArea").setValue(
					buildingArea);
			this.tblMain.getFootRow(0).getCell("actualBuildingArea").setValue(
					actualBuildingArea);
			this.tblMain.getFootRow(0).getCell("roomArea").setValue(
					roomArea);
			this.tblMain.getFootRow(0).getCell("actualRoomArea").setValue(
					actualRoomArea);
			if(count==0){
				this.tblMain.getFootRow(0).getCell("dealRoomPrice").setValue(new BigDecimal(0));
			}else{
				this.tblMain.getFootRow(0).getCell("dealRoomPrice").setValue(
						dealRoomPrice.divide(new BigDecimal(count),8,BigDecimal.ROUND_HALF_UP));
			}
			this.tblMain.getFootRow(0).getCell("contractAmount").setValue(
					contractAmount);
		}
	}

	protected void execQuery() {
		
		super.execQuery();

		//合计行
		IRow footRow=null;
		KDTFootManager footRowManager= tblMain.getFootManager();
        if (footRowManager==null)
        {
            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
            footRowManager = new KDTFootManager(this.tblMain);
            footRowManager.addFootView();
            this.tblMain.setFootManager(footRowManager);
            footRow= footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
            this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
            this.tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        }else{
            footRow=footRowManager.getFootRow(0);
        }
		footRow.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);

		IRowSet sellAmountSet = null;
		IRowSet phoneSet = null;
		IRowSet loanAmountSet = null;
		IRowSet noLoanAmountSet = null;
		IRowSet accFundSet = null;
		IRowSet lateFeeSet = null;
		IRowSet comCharSet = null;
		IRowSet elseAmountSet = null;
		IRowSet fitmentAmountSet = null;
		IRowSet replaceFeeSet = null;
		IRowSet conArgSet = null;
		IRowSet conBackSet = null;
		IRowSet overFlowSet = null;
		IRowSet comsateSet = null;
		try {
			Map MapData = RoomReceiveFacadeFactory.getRemoteInstance().getRoomReceiveData(dataMap);
			
			IRowSet rowSet = (IRowSet)MapData.get("rowSet");   //获取可以用query取出的数据
			phoneSet = (IRowSet)MapData.get("phoneSet");   //电话、证件号码

			sellAmountSet = (IRowSet)MapData.get("sellAmountSet");   //获取‘补差款’ 类数据
			loanAmountSet = (IRowSet)MapData.get("loanAmountSet");   //获取‘按揭类房款’ 类数据
			noLoanAmountSet = (IRowSet)MapData.get("noLoanAmountSet");   //获取‘非按揭类房款’ 类数据
			accFundSet = (IRowSet)MapData.get("accFundSet");   //获取‘公积金’ 类数据
			lateFeeSet = (IRowSet)MapData.get("lateFeeSet");   //获取‘滞纳金’ 类数据
			comCharSet = (IRowSet)MapData.get("comCharSet");   //获取‘手续费’ 类数据
			elseAmountSet = (IRowSet)MapData.get("elseAmountSet");   //获取‘其他’ 类数据
			fitmentAmountSet = (IRowSet)MapData.get("fitmentAmountSet");   //获取‘装修款’ 类数据
			replaceFeeSet = (IRowSet)MapData.get("replaceFeeSet");   //获取‘代收费用’ 类数据
			conArgSet = (IRowSet)MapData.get("conArgSet");   //获取‘合同欠款’ 类数据
			conBackSet = (IRowSet)MapData.get("conBackSet");   //获取‘合同回款’ 类数据
			overFlowSet = (IRowSet)MapData.get("overFlowSet");  //获取 ‘溢交金额’ 类数据
			comsateSet = (IRowSet)MapData.get("comsateSet");  //获取 ‘应收补差金额’， ‘已收面积补差款’ 类数据
			
			queryDate(rowSet);   //query中数据
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		String[] roomIdArray = new String[this.tblMain.getRowCount()];

		if (this.tblMain.getFootManager() != null
				&& this.tblMain.getFootRow(0) != null) {
			this.tblMain.getFootRow(0).getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
		}

		try {
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				IRow row = this.tblMain.getRow(i);
				roomIdMap.put(row.getCell("id").getValue(), row);
				roomIdArray[i] = row.getCell("id").getValue().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
 			while (phoneSet.next()) {
				String roomId = phoneSet.getString("roomid");
				String cfNumber = phoneSet.getString("CertificateNumber");
				String phone = phoneSet.getString("fphone");
				String tel = phoneSet.getString("ftel");
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
  					continue;
				Object obj = row.getCell("certificateNumber").getValue();
				Object obj1 = row.getCell("phone").getValue();
				if (obj == null) {
					row.getCell("certificateNumber").setValue(cfNumber);
				} else {
					if(cfNumber!=null && !("".equals(tel))){
						cfNumber = (String) obj + "," + cfNumber;
						row.getCell("certificateNumber").setValue(cfNumber);
					}
				}
				
				if (obj1 == null) {
//					if(phone != null && !("".equals(tel))){
//						phone = phone+","+tel;
//					}
					if(tel != null && !("".equals(tel))){
						phone = phone+","+tel;
					}
					row.getCell("phone").setValue(phone);
				} else {
					if(tel != null && !("".equals(tel))){
						phone = phone+","+tel;
					}
					phone = (String) obj1 + ";" + phone;
					row.getCell("phone").setValue(phone);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//初始化数据溢交金额、滞纳金、手续费、其他、装修款、代收费用、应收补差金额、已收面积补差款
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
//			for (int j = 1; j < this.tblMain.getColumnCount(); j++)
//			{
				this.tblMain.getCell(i, "overflowAmount").setValue(new BigDecimal("0.00"));
				this.tblMain.getCell(i, "actCompensateAmount").setValue(new BigDecimal("0.00"));
				this.tblMain.getCell(i, "compensateAmount").setValue(new BigDecimal("0.00"));
				this.tblMain.getCell(i, "lateFee").setValue(new BigDecimal("0.00"));
				this.tblMain.getCell(i, "commissionCharge").setValue(new BigDecimal("0.00"));
				this.tblMain.getCell(i, "elseAmount").setValue(new BigDecimal("0.00"));
				this.tblMain.getCell(i, "fitmentAmount").setValue(new BigDecimal("0.00"));
				this.tblMain.getCell(i, "replaceFee").setValue(new BigDecimal("0.00"));
				
//			}
		}
		
		fillActCompensateAmount(comsateSet);  //应收补差金额、已收面积补差款
		fillLoanTable(noLoanAmountSet,loanAmountSet,accFundSet);   //非按揭类回款、按揭类回款、公积金
		fillTermReceiveAmount(conBackSet,conArgSet,overFlowSet);   //合同回款、合同欠款、溢交金额
		fillEndTable(lateFeeSet,comCharSet,elseAmountSet,fitmentAmountSet,replaceFeeSet);    //滞纳金、手续费、其他、装修款、代收费用
		fillSellAmount(sellAmountSet);   //销售总价
	}

	//非按揭类回款、按揭类回款、公积金
	private void fillLoanTable(IRowSet noLoanAmountSet,IRowSet loanAmountSet,IRowSet accFundSet) {
		if (roomIdMap.size() == 0)
			return;
		
		BigDecimal noAllLoanAmount = FDCHelper.ZERO;
		BigDecimal allLoanAmount = FDCHelper.ZERO;
		BigDecimal allAccumulationFund = FDCHelper.ZERO;
//		int rowNumber = -1;
		
		//非按揭类回款
		try {
			while (noLoanAmountSet.next()) {
				String roomId = noLoanAmountSet.getString("roomid");
				BigDecimal YSamount = noLoanAmountSet.getBigDecimal("YSamount");
				BigDecimal YZamount = noLoanAmountSet.getBigDecimal("YZamount");
				BigDecimal YQamount = noLoanAmountSet.getBigDecimal("YQamount");
//				BigDecimal bYSamount = noLoanAmountSet.getBigDecimal("bYSamount");
//				BigDecimal bYZamount = noLoanAmountSet.getBigDecimal("bYZamount");
//				BigDecimal bYQamount = noLoanAmountSet.getBigDecimal("vYQamount");
				BigDecimal sum = FDCHelper.ZERO;
				
				boolean isIn = noLoanAmountSet.getBoolean("isIn");
				String moneyType = noLoanAmountSet.getString("moneyType");
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(!isIn && moneyType.equals("EarnestMoney")){
					continue;
				}
				if(YSamount == null){
					YSamount = FDCHelper.ZERO;
				}
				if(YZamount == null){
					YZamount = FDCHelper.ZERO;
				}
				if(YQamount == null){
					YQamount = FDCHelper.ZERO;
				}
				
				sum = YSamount.subtract(YZamount).subtract(YQamount);
				
				Object obj = row.getCell("noLoanAmount").getValue();
//				if(isIn && !moneyType.equals("EarnestMoney")){
					noAllLoanAmount = noAllLoanAmount.add(sum);
					if (obj == null) {
						row.getCell("noLoanAmount").setValue(sum);
					} else {
						sum = sum.add((BigDecimal) obj);				
						row.getCell("noLoanAmount").setValue(sum);
					}
//				}		
//				row.getCell("noLoanAmount").setValue(amount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//按揭类回款
		try {
			while (loanAmountSet.next()) {
				String roomId = loanAmountSet.getString("roomid");
				BigDecimal YSamount = loanAmountSet.getBigDecimal("YSamount");
				BigDecimal YZamount = loanAmountSet.getBigDecimal("YZamount");
				BigDecimal YQamount = loanAmountSet.getBigDecimal("YQamount");
				BigDecimal sum = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(YSamount == null){
					YSamount = FDCHelper.ZERO;
				}
				if(YZamount == null){
					YZamount = FDCHelper.ZERO;
				}
				if(YQamount == null){
					YQamount = FDCHelper.ZERO;
				}
				sum = YSamount.subtract(YZamount).subtract(YQamount);
				
				Object obj = row.getCell("LoanAmount").getValue();
				allLoanAmount = allLoanAmount.add(sum);
				if (obj == null) {
					row.getCell("LoanAmount").setValue(sum);
//					allLoanAmount = allLoanAmount.add(amount);
				} else {
//					allLoanAmount = allLoanAmount.add(amount);
					sum = sum.add((BigDecimal) obj);					
					row.getCell("LoanAmount").setValue(sum);
				}			
//				row.getCell("LoanAmount").setValue(amount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//公积金
		try {
			while (accFundSet.next()) {
				String roomId = accFundSet.getString("roomid");
				BigDecimal YSamount = accFundSet.getBigDecimal("YSamount");
				BigDecimal YZamount = accFundSet.getBigDecimal("YZamount");
				BigDecimal YQamount = accFundSet.getBigDecimal("YQamount");
				BigDecimal sum = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(YSamount == null){
					YSamount = FDCHelper.ZERO;
				}
				if(YZamount == null){
					YZamount = FDCHelper.ZERO;
				}
				if(YQamount == null){
					YQamount = FDCHelper.ZERO;
				}
				sum = YSamount.subtract(YZamount).subtract(YQamount);
				
				Object obj = row.getCell("accumulationFund").getValue();
				allAccumulationFund = allAccumulationFund.add(sum);
				if (obj == null) {
					row.getCell("accumulationFund").setValue(sum);
//					allLoanAmount = allLoanAmount.add(AFamount);
				} else {
//					allLoanAmount = allLoanAmount.add(AFamount);
					sum = sum.add((BigDecimal) obj);					
					row.getCell("accumulationFund").setValue(sum);
				}		
//				row.getCell("accumulationFund").setValue(AFamount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.tblMain.getFootManager() != null
				&& this.tblMain.getFootRow(0) != null) {
//			this.tblMain.getFootRow(0).getCell("noLoanAmount").setValue(noAllLoanAmount);
			this.tblMain.getFootRow(0).getCell("LoanAmount").setValue(allLoanAmount);
			this.tblMain.getFootRow(0).getCell("accumulationFund").setValue(allAccumulationFund);
		}
	}
	
	//滞纳金、手续费、其他、装修款、代收费用
	private void fillEndTable(IRowSet lateFeeSet,IRowSet comCharSet,IRowSet elseAmountSet,IRowSet fitmentAmountSet,IRowSet replaceFeeSet) {
		if (roomIdMap.size() == 0)
			return;
		
		BigDecimal allLateFee = FDCHelper.ZERO;
		BigDecimal allCommissionCharge = FDCHelper.ZERO;
		BigDecimal allElseAmount = FDCHelper.ZERO;
		BigDecimal allFitmentAmount = FDCHelper.ZERO;
		BigDecimal allReplaceFee = FDCHelper.ZERO;
//		int rowNumber = -1;
		
		//滞纳金
		try {
			while (lateFeeSet.next()) {
				String roomId = lateFeeSet.getString("roomid");
				
				BigDecimal YSamount = lateFeeSet.getBigDecimal("YSamount");
				BigDecimal YZamount = lateFeeSet.getBigDecimal("YZamount");
				BigDecimal YQamount = lateFeeSet.getBigDecimal("YQamount");
				BigDecimal YTamount = lateFeeSet.getBigDecimal("YTamount");
				BigDecimal sumAmount = FDCHelper.ZERO;
//				BigDecimal sum = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(YSamount==null || ("").equals(YSamount)){
					YSamount = FDCHelper.ZERO;
				}
				if(YZamount==null || ("").equals(YZamount)){
					YZamount = FDCHelper.ZERO;
				}
				if(YQamount==null || ("").equals(YQamount)){
					YQamount = FDCHelper.ZERO;
				}
				if(YTamount==null || ("").equals(YTamount)){
					YTamount = FDCHelper.ZERO;
				}
				sumAmount = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);
				
				Object obj = row.getCell("lateFee").getValue();
				allLateFee = allLateFee.add(sumAmount);
				if (obj == null) {
					row.getCell("lateFee").setValue(sumAmount);
//					allLateFee = allLateFee.add(sumAmount);
				} else {
//					allLateFee = allLateFee.add(sumAmount);
					sumAmount = sumAmount.add((BigDecimal) obj);					
					row.getCell("lateFee").setValue(sumAmount);
				}
				
//				allLateFee = allLateFee.add(sumAmount);
//				row.getCell("lateFee").setValue(sumAmount);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//手续费
		try {
			while (comCharSet.next()) {
				String roomId = comCharSet.getString("roomid");
				
				BigDecimal YSamount = comCharSet.getBigDecimal("YSamount");
				BigDecimal YZamount = comCharSet.getBigDecimal("YZamount");
				BigDecimal YQamount = comCharSet.getBigDecimal("YQamount");
				BigDecimal YTamount = comCharSet.getBigDecimal("YTamount");
				BigDecimal sumAmount = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(YSamount==null || ("").equals(YSamount)){
					YSamount = new BigDecimal(0);
				}
				if(YZamount==null || ("").equals(YZamount)){
					YZamount = new BigDecimal(0);
				}
				if(YQamount==null || ("").equals(YQamount)){
					YQamount = new BigDecimal(0);
				}
				if(YTamount==null || ("").equals(YTamount)){
					YTamount = new BigDecimal(0);
				}
				sumAmount = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);
				
				Object obj = row.getCell("commissionCharge").getValue();
				if (obj == null) {
					row.getCell("commissionCharge").setValue(sumAmount);
					allCommissionCharge = allCommissionCharge.add(sumAmount);
				} else {
					allCommissionCharge = allCommissionCharge.add(sumAmount);
					sumAmount = sumAmount.add((BigDecimal) obj);					
					row.getCell("commissionCharge").setValue(sumAmount);
				}
				
//				allCommissionCharge = allCommissionCharge.add(sumAmount);
//				row.getCell("commissionCharge").setValue(sumAmount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//其他
		try {
			while (elseAmountSet.next()) {
				String roomId = elseAmountSet.getString("roomid");
				
				BigDecimal YSamount = elseAmountSet.getBigDecimal("YSamount");
				BigDecimal YZamount = elseAmountSet.getBigDecimal("YZamount");
				BigDecimal YQamount = elseAmountSet.getBigDecimal("YQamount");
				BigDecimal YTamount = elseAmountSet.getBigDecimal("YTamount");
				BigDecimal sumAmount = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(YSamount==null || ("").equals(YSamount)){
					YSamount = new BigDecimal(0);
				}
				if(YZamount==null || ("").equals(YZamount)){
					YZamount = new BigDecimal(0);
				}
				if(YQamount==null || ("").equals(YQamount)){
					YQamount = new BigDecimal(0);
				}
				if(YTamount==null || ("").equals(YTamount)){
					YTamount = new BigDecimal(0);
				}
				sumAmount = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);
				
				Object obj = row.getCell("elseAmount").getValue();
				if (obj == null) {
					row.getCell("elseAmount").setValue(sumAmount);
					allElseAmount = allElseAmount.add(sumAmount);
				} else {
					allElseAmount = allElseAmount.add(sumAmount);
					sumAmount = sumAmount.add((BigDecimal) obj);					
					row.getCell("elseAmount").setValue(sumAmount);
				}
				
//				allElseAmount = allElseAmount.add(sumAmount);
//				row.getCell("elseAmount").setValue(sumAmount);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//装修款
		try {
			while (fitmentAmountSet.next()) {
				String roomId = fitmentAmountSet.getString("roomid");
				
				BigDecimal YSamount = fitmentAmountSet.getBigDecimal("YSamount");
				BigDecimal YZamount = fitmentAmountSet.getBigDecimal("YZamount");
				BigDecimal YQamount = fitmentAmountSet.getBigDecimal("YQamount");
				BigDecimal YTamount = fitmentAmountSet.getBigDecimal("YTamount");
				BigDecimal sumAmount = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(YSamount==null || ("").equals(YSamount)){
					YSamount = new BigDecimal(0);
				}
				if(YZamount==null || ("").equals(YZamount)){
					YZamount = new BigDecimal(0);
				}
				if(YQamount==null || ("").equals(YQamount)){
					YQamount = new BigDecimal(0);
				}
				if(YTamount==null || ("").equals(YTamount)){
					YTamount = new BigDecimal(0);
				}
				sumAmount = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);
				
				Object obj = row.getCell("fitmentAmount").getValue();
				if (obj == null) {
					row.getCell("fitmentAmount").setValue(sumAmount);
					allFitmentAmount = allFitmentAmount.add(sumAmount);
				} else {
					allFitmentAmount = allFitmentAmount.add(sumAmount);
					sumAmount = sumAmount.add((BigDecimal) obj);					
					row.getCell("fitmentAmount").setValue(sumAmount);
				}
				
//				allElseAmount = allElseAmount.add(sumAmount);
//				row.getCell("elseAmount").setValue(sumAmount);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//代收费用
		try {
			while (replaceFeeSet.next()) {
				String roomId = replaceFeeSet.getString("roomid");
				
				BigDecimal YSamount = replaceFeeSet.getBigDecimal("YSamount");
				BigDecimal YZamount = replaceFeeSet.getBigDecimal("YZamount");
				BigDecimal YQamount = replaceFeeSet.getBigDecimal("YQamount");
				BigDecimal YTamount = replaceFeeSet.getBigDecimal("YTamount");
				BigDecimal sumAmount = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;
				if(YSamount==null || ("").equals(YSamount)){
					YSamount = new BigDecimal(0);
				}
				if(YZamount==null || ("").equals(YZamount)){
					YZamount = new BigDecimal(0);
				}
				if(YQamount==null || ("").equals(YQamount)){
					YQamount = new BigDecimal(0);
				}
				if(YTamount==null || ("").equals(YTamount)){
					YTamount = new BigDecimal(0);
				}
				sumAmount = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);
				
				Object obj = row.getCell("replaceFee").getValue();
				if (obj == null) {
					row.getCell("replaceFee").setValue(sumAmount);
					allReplaceFee = allReplaceFee.add(sumAmount);
				} else {
					allReplaceFee = allReplaceFee.add(sumAmount);
					sumAmount = sumAmount.add((BigDecimal) obj);					
					row.getCell("replaceFee").setValue(sumAmount);
				}
				
//				allElseAmount = allElseAmount.add(sumAmount);
//				row.getCell("elseAmount").setValue(sumAmount);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.tblMain.getFootManager() != null && this.tblMain.getFootRow(0) != null) {
			this.tblMain.getFootRow(0).getCell("lateFee").setValue(
					allLateFee);
			this.tblMain.getFootRow(0).getCell("commissionCharge").setValue(
					allCommissionCharge);
			this.tblMain.getFootRow(0).getCell("elseAmount").setValue(
					allElseAmount);
			this.tblMain.getFootRow(0).getCell("fitmentAmount").setValue(
					allFitmentAmount);
			this.tblMain.getFootRow(0).getCell("replaceFee").setValue(
					allReplaceFee);
		}
	}

	protected boolean isFootVisible() {
		return true;
	}
	
	//销售总价
	private void fillSellAmount(IRowSet sellAmountSet){
//		if (roomIdMap.size() == 0)
//			return;
		BigDecimal compensateAmount = FDCHelper.ZERO;
		BigDecimal allNoLoanAmount = FDCHelper.ZERO;
//		try{
//			while (sellAmountSet.next()) {
//				String roomID = sellAmountSet.getString("roomid");
//				BigDecimal compensate = sellAmountSet.getBigDecimal("sellAmount");
//				IRow row = (IRow) roomIdMap.get(roomID);
//				if (row == null)
//					continue;
//				if (compensate == null)
//					compensate = FDCHelper.ZERO;
//				BigDecimal sellAmount = (BigDecimal)row.getCell("sellAmount").getValue();
//				compensateAmount = compensateAmount.add(sellAmount);
//				compensate = compensate.add(sellAmount);
//				row.getCell("sellAmount").setValue(compensate);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		for(int i=0;i<tblMain.getRowCount();i++){
			IRow row = tblMain.getRow(i);
			BigDecimal sellAmount = (BigDecimal)(row.getCell("sellAmount").getValue()==null?FDCHelper.ZERO:row.getCell("sellAmount").getValue());
			BigDecimal bucha = (BigDecimal)(row.getCell("actCompensateAmount").getValue()==null?FDCHelper.ZERO:row.getCell("actCompensateAmount").getValue());
			sellAmount = sellAmount.add(bucha);
			compensateAmount = compensateAmount.add(sellAmount);
			row.getCell("sellAmount").setValue(sellAmount);

			BigDecimal obj1 = (BigDecimal)(row.getCell("actCompensateAmount").getValue()==null?FDCHelper.ZERO:row.getCell("actCompensateAmount").getValue());
			BigDecimal obj2 = (BigDecimal)(row.getCell("noLoanAmount").getValue()==null?FDCHelper.ZERO:row.getCell("noLoanAmount").getValue());
			obj2 = obj1.add(obj2);
			allNoLoanAmount = allNoLoanAmount.add(obj2);
			row.getCell("noLoanAmount").setValue(obj2);
		}

		if (this.tblMain.getFootManager() != null
				&& this.tblMain.getFootRow(0) != null) {
			this.tblMain.getFootRow(0).getCell("sellAmount").setValue(compensateAmount);
			this.tblMain.getFootRow(0).getCell("noLoanAmount").setValue(allNoLoanAmount);
		}
	}
	
	//应收补差金额、已收面积补差款
	private void fillActCompensateAmount(IRowSet comsateSet)
	{
		if (roomIdMap.size() == 0)
			return;

		BigDecimal actCompensateAmount = FDCHelper.ZERO;
		BigDecimal compensateAmount = FDCHelper.ZERO;
//		int rowNumber = -1;
		
		try {
			while(comsateSet.next())
			{
				String roomId = comsateSet.getString("roomId");
				BigDecimal compensateSumAmount = comsateSet.getBigDecimal("FAppAmount");
				BigDecimal ActcompensateSumAmount = comsateSet.getBigDecimal("FActRevAmount");
				BigDecimal YZAmount = comsateSet.getBigDecimal("YZAmount");
				BigDecimal YQAmount = comsateSet.getBigDecimal("YQAmount"); 
				BigDecimal sum = FDCHelper.ZERO;
				IRow row = (IRow) roomIdMap.get(roomId);
				if (row == null)
					continue;

				if(ActcompensateSumAmount == null){
					ActcompensateSumAmount = FDCHelper.ZERO;
				}
				if(compensateSumAmount == null){
					compensateSumAmount = FDCHelper.ZERO;
				}
				if(YZAmount == null){
					YZAmount = FDCHelper.ZERO;
				}
				if(YQAmount == null){
					YQAmount = FDCHelper.ZERO;
				}
				sum = ActcompensateSumAmount.subtract(YZAmount).subtract(YQAmount);
				
				Object obj = row.getCell("actCompensateAmount").getValue();
				actCompensateAmount = actCompensateAmount.add(sum);
				if (obj == null) {
					row.getCell("actCompensateAmount").setValue(sum);
				} else {
					sum = sum.add((BigDecimal) obj);					
					row.getCell("actCompensateAmount").setValue(sum);
				}
				
				Object obj1 = row.getCell("compensateAmount").getValue();
				compensateAmount = compensateAmount.add(compensateSumAmount);
				if (obj1 == null) {
					row.getCell("compensateAmount").setValue(compensateSumAmount);
				} else {
					compensateSumAmount = compensateSumAmount.add((BigDecimal) obj1);					
					row.getCell("compensateAmount").setValue(compensateSumAmount);
				}
			
//				if(compensateSumAmount == null){
//					compensateSumAmount = FDCHelper.ZERO;
//				}
//				if(ActcompensateSumAmount == null){
//					ActcompensateSumAmount = FDCHelper.ZERO;
//				}
//				row.getCell("actCompensateAmount").setValue(ActcompensateSumAmount);
//				row.getCell("compensateAmount").setValue(compensateSumAmount);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.tblMain.getFootManager() != null
				&& this.tblMain.getFootRow(0) != null) {
			this.tblMain.getFootRow(0).getCell("actCompensateAmount").setValue(actCompensateAmount);
			this.tblMain.getFootRow(0).getCell("compensateAmount").setValue(compensateAmount);
		}
	}

	//合同回款 、合同欠款、溢交金额
	private void fillTermReceiveAmount(IRowSet conBackSet,IRowSet conArgSet,IRowSet overFlowSet) {
		if (roomIdMap.size() == 0)
			return;

		BigDecimal contactComeBackCount = FDCHelper.ZERO;
		BigDecimal arrearageCount = FDCHelper.ZERO;
		BigDecimal outSellAmount = FDCHelper.ZERO;
//		int rowNumber = -1;

		//合同回款
		try {
//			BigDecimal SumAmount = FDCHelper.ZERO;
//			rowNumber = -1;
			if(conBackSet==null) return;
			
			while (conBackSet.next()) {
				String roomId = conBackSet.getString("roomid");
				BigDecimal YSamount = conBackSet.getBigDecimal("YSamount");
				BigDecimal YZamount = conBackSet.getBigDecimal("YZamount");
				BigDecimal YQamount = conBackSet.getBigDecimal("YQamount");
				BigDecimal YTamount = conBackSet.getBigDecimal("YTamount");
				
				boolean isIn = conBackSet.getBoolean("isIn");
				String moneyType = conBackSet.getString("moneyType");
				
				IRow row = (IRow) roomIdMap.get(roomId);

				if (row == null)
					continue;
				if(!isIn && moneyType.equals("EarnestMoney")){
					continue;
				}
				
				if(YSamount == null || "".equals(YSamount)){
					YSamount = FDCHelper.ZERO;
				}
				if(YZamount == null || "".equals(YZamount)){
					YZamount = FDCHelper.ZERO;
				}
				if(YQamount == null || "".equals(YQamount)){
					YQamount = FDCHelper.ZERO;
				}
				if(YTamount == null || "".equals(YTamount)){
					YTamount = FDCHelper.ZERO;
				}

				BigDecimal sum = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);

				contactComeBackCount = contactComeBackCount.add(sum);
				Object contractObj = row.getCell("contractBackAmount").getValue();
				if(contractObj == null){
					row.getCell("contractBackAmount").setValue(sum);
				}else{
					sum = sum.add((BigDecimal)contractObj);
					row.getCell("contractBackAmount").setValue(sum);
				}
//				if(row.getRowIndex() == rowNumber){
//					SumAmount = SumAmount.add(sum);
////				}else{
//					SumAmount = sum;
////				}
//				
//				rowNumber = row.getRowIndex();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//合同欠款
		try {
//			rowNumber = -1;
//			BigDecimal allAmount = FDCHelper.ZERO;
			while (conArgSet.next()) { 
				String roomId = conArgSet.getString("roomid");
				BigDecimal YSamount = conArgSet.getBigDecimal("YSamount");
				BigDecimal YZamount = conArgSet.getBigDecimal("YZamount");
				BigDecimal YQamount = conArgSet.getBigDecimal("YQamount");
				BigDecimal YTamount = conArgSet.getBigDecimal("YTamount");
				BigDecimal SumAmount = conArgSet.getBigDecimal("sumRevAmount");
				
				IRow row = (IRow) roomIdMap.get(roomId);
				
				if(row == null){
					continue;
				}
				
				if(YSamount == null || "".equals(YSamount)){
					YSamount = FDCHelper.ZERO;
				}
				if(YZamount == null || "".equals(YZamount)){
					YZamount = FDCHelper.ZERO;
				}
				if(YQamount == null || "".equals(YQamount)){
					YQamount = FDCHelper.ZERO;
				}
				if(YTamount == null || "".equals(YTamount)){
					YTamount = FDCHelper.ZERO;
				}
				if(SumAmount == null || "".equals(SumAmount)){
					SumAmount = FDCHelper.ZERO;
				}
				BigDecimal sum = SumAmount.subtract((YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount)));
				
				arrearageCount = arrearageCount.add(sum);
				
				Object arrearageObj = row.getCell("arrearageAmount").getValue();
				if(arrearageObj == null){
					row.getCell("arrearageAmount").setValue(sum);
				}else{
					sum = sum.add((BigDecimal)arrearageObj);
					row.getCell("arrearageAmount").setValue(sum);
				}
				
//				if(row.getRowIndex() == rowNumber){
//					allAmount = allAmount.add(sum);
//				}else{
//					allAmount = sum;
//				}
//				row.getCell("arrearageAmount").setValue(allAmount);
//				rowNumber = row.getRowIndex();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//溢交金额
		try {
//			rowNumber = -1;
//			BigDecimal allAmount = FDCHelper.ZERO;
			while (overFlowSet.next()) { 
				String roomId = overFlowSet.getString("roomid");
				BigDecimal YSamount = overFlowSet.getBigDecimal("YSamount");
				BigDecimal YZamount = overFlowSet.getBigDecimal("YZamount");
				BigDecimal YQamount = overFlowSet.getBigDecimal("YQamount");
				BigDecimal YTamount = overFlowSet.getBigDecimal("YTamount");
				BigDecimal SumAmount = overFlowSet.getBigDecimal("sumRevAmount");
				BigDecimal Sum = FDCHelper.ZERO;
				
				IRow row = (IRow) roomIdMap.get(roomId);
				
				if(row == null){
					continue;
				}
				
				if(YSamount == null || "".equals(YSamount)){
					YSamount = FDCHelper.ZERO;
				}
				if(YZamount == null || "".equals(YZamount)){
					YZamount = FDCHelper.ZERO;
				}
				if(YQamount == null || "".equals(YQamount)){
					YQamount = FDCHelper.ZERO;
				}
				if(YTamount == null || "".equals(YTamount)){
					YTamount = FDCHelper.ZERO;
				}
				if(SumAmount == null || "".equals(SumAmount)){
					SumAmount = FDCHelper.ZERO;
				}
				Sum = (YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount)).subtract(SumAmount);
				
//				if(row.getRowIndex() == rowNumber){
//					allAmount = allAmount.add(Sum);
//				}else{
//					allAmount = Sum;
//				}
//				BigDecimal obj = (BigDecimal)row.getCell("arrearageAmount").getValue();
//				if(obj.compareTo(new BigDecimal("0")) < 0){
//					outSellAmount = outSellAmount.add(obj.abs());
//					row.getCell("overflowAmount").setValue(obj.abs());
//				}
				if(Sum.compareTo(new BigDecimal("0")) > 0){
					outSellAmount = outSellAmount.add(Sum);
//					outSellAmount = outSellAmount.add(allAmount);
					row.getCell("overflowAmount").setValue(Sum);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (this.tblMain.getFootManager() != null
				&& this.tblMain.getFootRow(0) != null) {
			this.tblMain.getFootRow(0).getCell("contractBackAmount").setValue(
					contactComeBackCount);
			this.tblMain.getFootRow(0).getCell("arrearageAmount").setValue(
					arrearageCount);
			this.tblMain.getFootRow(0).getCell("overflowAmount").setValue(
					outSellAmount);
		}
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		/*for (int i = tblMain.getColumnCount() - 1; i > this.tblMain
		.getColumnIndex("actCompensateAmount"); i--) {
			this.tblMain.removeColumn(i);
		}*/
		
		this.tblMain.removeHeadRow(1);
		allBuildingIds = new HashSet();
		int unitNumber = 0; // 单元过滤，代表无单元过滤
		TreeNode buildingNode = (TreeNode) this.treeMain.getLastSelectedPathComponent();
		// allBuildingIds = "null";
		if (buildingNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) buildingNode;
			if (thisNode.getUserObject() != null) {
				if (thisNode.getUserObject() instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) thisNode
					.getUserObject();
					// allBuildingIds += "," + building.getId().toString();
					allBuildingIds.add(building.getId().toString());
				} else if (thisNode.getUserObject() instanceof Integer) { // 已作废
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode
					.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode
					.getUserObject();
					// allBuildingIds += "," +
					// parentBuilding.getId().toString();
					allBuildingIds.add(parentBuilding.getId().toString());
					unitNumber = ((Integer) thisNode.getUserObject())
					.intValue();
				} else if (thisNode.getUserObject() instanceof BuildingUnitInfo) { //
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode
					.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode
					.getUserObject();
					// allBuildingIds += "," +
					// parentBuilding.getId().toString();
					allBuildingIds.add(parentBuilding.getId().toString());
					unitNumber = ((BuildingUnitInfo) thisNode.getUserObject()).getSeq();
				} else {
					this.getAllBuildingIds(buildingNode);
				}
			}
		} else {
			this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain
					.getModel().getRoot());
			this.getAllBuildingIds((TreeNode) this.treeMain.getModel()
					.getRoot());
		}

		// UIContext uiContext = new UIContext(this);
		// uiContext.put("allBuildingIds", allBuildingIds);
		if (allBuildingIds.size() == 0) {
			return;
		}
		
		dataMap = new HashMap();
		
//		FilterInfo buildInfo = new FilterInfo();
//		buildInfo.getFilterItems().add(
//				new FilterItemInfo("sellState", "Purchase"));
//		buildInfo.getFilterItems().add(new FilterItemInfo("sellState", "Sign"));
//		buildInfo.getFilterItems().add(
//				new FilterItemInfo("houseProperty", "NoAttachment"));
//		buildInfo.getFilterItems().add(
//				new FilterItemInfo("building.id", allBuildingIds,
//						CompareType.INCLUDE));
//		if (unitNumber != 0) {
//			buildInfo.getFilterItems().add(
//					new FilterItemInfo("unit", new Integer(unitNumber)));
//			buildInfo.setMaskString("(#0 or #1) and #2 and #3 and #4");
//		} else {
//			buildInfo.setMaskString("(#0 or #1) and #2 and #3");
//		}
		dataMap.put("buildingIds", allBuildingIds);
		dataMap.put("unitNumber", new Integer(unitNumber));
		
//		this.mainQuery.setFilter(buildInfo);
//		this.tblMain.removeRows();
//		this.tblMain.removeAll();
//		fillOtherTable();   //代码添加“其他”列
		this.execQuery();
	}

	/**
	 * 查询所有的楼栋id
	 * 
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) thisNode.getUserObject();
			allBuildingIds.add(building.getId().toString());
			// allBuildingIds += "," + building.getId().toString();
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllBuildingIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}

}