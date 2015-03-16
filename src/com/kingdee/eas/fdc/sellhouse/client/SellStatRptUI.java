/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.st.weigh.client.IronTransBuilder;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * 区间销售统计报表 <br>
 */
public class SellStatRptUI extends AbstractSellStatRptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SellStatRptUI.class);
	
	/**
	 * 以楼栋ID为Key,该楼栋的Row为Value的Map,便于在fillData中快速定位某一楼栋的Row
	 * */
	private Map rowMap = new HashMap();

	private SellStatRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private Date beginDate = null;
	private Date endDate = null;
	private boolean isShowAll = false;
	private boolean isSpecialBizIntoSale = false;
	private boolean isPreIntoSale = false;
	private boolean isIncludeAttach = false;
	
	private RoomDisplaySetting setting = new RoomDisplaySetting();

	/**
	 * output class constructor
	 */
	public SellStatRptUI() throws Exception {
		super();
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
		// super.tblMain_doRequestRowSet(e);
	}

	private SellStatRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SellStatRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	
	protected void execQuery()
	{
		
		//通过 portal 传入参数之用
		Map timeMap = new HashMap();
		Map map = this.getUIContext();
		Object obj = map.get(UIContext.UICLASSPARAM);
		if(obj != null && obj.toString().trim().length() > 0)
		{
			String [] para = obj.toString().split("&");
			for(int i = 0; i < para.length; i ++)
			{
				String t = para[i];
		
				if(t != null)
				{
					String[] p1 = t.split("=");
					if(p1 == null || p1.length != 2)
						continue;
				
					if(p1[0] != null && p1[0].trim().length() > 0)
					{
						if(p1[1]  != null && p1[1].trim().length() > 0)
						{
							if("beginDate".equalsIgnoreCase(p1[0]))
							{
								String time = p1[1];
								Date d = null;
								try
								{
									d = DateTimeUtils.parseDate(time);
			
								} catch (ParseException e)
								{
									logger.error(e);
									MsgBox.showWarning("beginDate参数格式错误，应该为 ‘beginDate=YYYY-MM-DD’!");
								}
								timeMap.put("beginDate", d);
							}
							else if("endDate".equalsIgnoreCase(p1[0]))
							{

								String time = p1[1];
								Date d = null;
								try
								{
									d = DateTimeUtils.parseDate(time);

								} catch (ParseException e)
								{
									logger.error(e);
									MsgBox.showWarning("endDate参数格式错误，应该为 ‘endDate=YYYY-MM-DD’!");
								}
								timeMap.put("endDate", d);
							}
							else if("route".equalsIgnoreCase(p1[0]))
							{
								timeMap.put("route", "web");
							}
							else if("org".equalsIgnoreCase(p1[0]))
							{
								String id = p1[1];
								timeMap.put("org", id);
							}
						}
						else
						{

							if("beginDate".equalsIgnoreCase(p1[0]))
							{
								
								timeMap.put("beginDate", null);
							}
							else if("endDate".equalsIgnoreCase(p1[0]))
							{

								timeMap.put("endDate", null);
							}
							else if("route".equalsIgnoreCase(p1[0]))
							{
								timeMap.put("route", "web");
							}
							else if("org".equalsIgnoreCase(p1[0]))
							{
								timeMap.put("org", null);
							}
						
						}
					}
				}
			}
		}
		
		try
		{
			initTable();
			fillProjectAndBuilding(timeMap);
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		} catch (SQLException e) {
			handleException(e);
		}

		
		if(tblMain.getRowCount() == 0){
			return;
		}
	
		
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		if(timeMap.get("route") != null)
		{
			if(timeMap.get("beginDate") != null) beginDate = (Date)timeMap.get("beginDate");
			if(timeMap.get("endDate") != null) endDate = (Date)timeMap.get("endDate");
			
			map.put(UIContext.UICLASSPARAM, null);
		}
		else
		{
		if(filterMap.get("beginDate")!=null)	beginDate = new Date(((Timestamp)filterMap.get("beginDate")).getTime());
		if(filterMap.get("endDate")!=null)	endDate = new Date(((Timestamp)filterMap.get("endDate")).getTime());
		}
		isShowAll = ((Integer)filterMap.get("isShowAll")).intValue()>0?true:false;
		isSpecialBizIntoSale = ((Integer)filterMap.get("isSpecialBizIntoSale")).intValue()>0?true:false;	
		isPreIntoSale = ((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false;
		isIncludeAttach = ((Integer)filterMap.get("isIncludeAttach")).intValue()>0?true:false;
	
		
		try
		{
			fillData();
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		
		//以下进行查询区间的显示
		Date beginDate = DateTimeUtils.truncateDate(this.beginDate);
		Date endDate = DateTimeUtils.truncateDate(this.endDate);
		int termCol = 1;
		if (!isShowAll)
		{
			if (beginDate == null && endDate == null)
			{
				this.tblMain.getHeadRow(0).getCell(termCol).setValue("查询区间数据(全部显示)");
			}
			else if(beginDate == null && endDate != null)
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" 	+ FORMAT_DAY.format(cal.getTime()) + "之前)";
				this.tblMain.getHeadRow(0).getCell(termCol).setValue(des);
			}
			else if(beginDate != null && endDate == null)
			{
				Calendar cal = new GregorianCalendar();
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "之后)";
				this.tblMain.getHeadRow(0).getCell(termCol).setValue(des);
			}
			else
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "到"
						+ FORMAT_DAY.format(cal.getTime()) + ")";
				this.tblMain.getHeadRow(0).getCell(termCol).setValue(des);
			}
		} else
		{
			this.tblMain.getHeadRow(0).getCell(termCol).setValue("查询区间数据(全部显示)");
		}
	}

	protected void checkTableParsed() {
		tblMain.checkParsed();
	}

	public void onLoad() throws Exception {
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		this.tblMain.checkParsed();
		FDCClientHelper.addSqlMenu(this, this.menuFile);
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);

/*		// 第一次打开默认查询
		QuerySolutionInfo querySolution = QuerySolutionFacadeFactory
				.getRemoteInstance().getDefaultSolution(
						SellStatRptUI.class.getName(),
						"com.kingdee.eas.base.message.MsgQuery");

		if (querySolution != null) {
			if (querySolution.get("querypanelinfo") instanceof QueryPanelCollection) {
				QueryPanelInfo queryPanelInfo = ((QueryPanelCollection) querySolution
						.get("querypanelinfo")).get(0);
				if (queryPanelInfo.get("customerparams") != null) {
					String customerparams = queryPanelInfo
							.get("customerparams").toString();
					getFilterUI().setCustomerParams(
							XMLBean.TransStrToCustParams(customerparams));
				}

			}
		}
		this.refresh(null);*/
	}

	protected String[] getLocateNames() {
		String[] locateNames = new String[1];
		locateNames[0] = "name";
		return locateNames;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	private void fillData() throws BOSException, SQLException {
		//应该是先增加了楼栋行后，该函数用来往相应的楼栋行中填充数据。如果楼栋行数为0,那么以下查询操作都不用进行了。
		if(this.tblMain.getRowCount() == 0){
			return;
		}
		
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
				this.tblMain.getCell(i, j).setValue(null);
			}
		}
		
		fillTermSellAmount(isPreIntoSale, isIncludeAttach);
		
		fillTermSignAmount(isPreIntoSale, isIncludeAttach);
		
		fillTermContractAmount(isPreIntoSale, isIncludeAttach);
		
		fillTermReceiveAmount(isPreIntoSale, isIncludeAttach);
		
		fillTermUnsignAmount(isPreIntoSale, isIncludeAttach);
		
		fillTermKeepAmount(isPreIntoSale, isIncludeAttach);
		
		fillTermQuitAmount(isPreIntoSale, isIncludeAttach);
		
		fillTermPurchaseAmount(isPreIntoSale, isIncludeAttach);
		//以上统计主要是统计的该房间最新的销售数据.如果要统计特殊业务的纪录
		if(isSpecialBizIntoSale){
			handleSpecialBiz(isPreIntoSale, isIncludeAttach);
		}
		
		setUnionData();
		resetUnionData();
	}

	/**
	 * 在此之前已经统计了各房间最新的销售数据.这里处理特殊业务,主要是处理退房和变更及换房 <br>
	 * 例如:房间Room 1月1号认购100,2号变更为80,3号退房.那么这3天的销售额应该分别为100,-20,-80.如果要查询1号的销售额,前面的统计是取得房间的最新销售数据,即0(房间已退房).
	 * 再做如下处理 <br>
	 * 1.对于退房,查询出Room的已审批的退房单,其退房单关联的认购单的认购日期为查询区间内的,将认购单上的销售金额加到之前的统计结果上. (0+80)<br>
	 * 2.对于退房,查询出Room的已审批的退房单,其退房日期在查询区间内的,将之前的统计结果减掉认购单上的销售金额 (退房日期在3号,不在查询区间内)<br>
	 * 3.对于变更,查询出Room的已审批的变更单,其变更单关联的认购单的认购日期在查询区间内的,将变更单变更的差额加到之前的统计结果上 (80 + 20)<br>
	 * 4.对于变更,查询处Room的已审批的变更单,其变更日期在查询区间内的,将之前的统计结果减掉变更单上的差额 (变更日期在2号,不再查询区间内)
	 * TODO 增加了换房,这里换房可以当作退房来处理
	 * */
	private void handleSpecialBiz(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select d.FID FBuildingId,count(*) termSumCount,sum(c.FSaleArea) termSumArea,sum(c.FBuildingArea*c.FBaseBuildingPrice) termSumBaseAmount," 
				+"sum(b.FStandardTotalAmount) termSumAmount,"
				+"sum(b.FDealAmount) termDealTotalAmount,sum(b.FContractTotalAmount) termContractTotalAmount,sum(c.FAreaCompensateAmount) termAreaCompensateAmount " 
				+"from T_SHE_QuitRoom a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID " 
				+"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		if (isPreIntoSale) {
//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//预定不计入销售统计的话,预定退房的纪录也不应统计在内
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termQuitSellSet = builder.executeQuery();
		while (termQuitSellSet.next()) {
			String buildingId = termQuitSellSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			addToCell(row, "termSumCount", termQuitSellSet.getInt("termSumCount"));
			addToCell(row, "termSumArea", termQuitSellSet.getBigDecimal("termSumArea"));
			addToCell(row, "termSumBaseAmount", termQuitSellSet.getBigDecimal("termSumBaseAmount"));
			addToCell(row, "termSumAmount", termQuitSellSet.getBigDecimal("termSumAmount"));
			addToCell(row, "termDealTotalAmount", termQuitSellSet.getBigDecimal("termDealTotalAmount"));
			addToCell(row, "termContractTotalAmount", termQuitSellSet.getBigDecimal("termContractTotalAmount"));
			addToCell(row, "termAreaCompensateAmount", termQuitSellSet.getBigDecimal("termAreaCompensateAmount"));
			
			reSetSaleAmount(row);//如果变化了合同总价,销售总价也要相应变化
		}
		
		//签约合同退房前特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termSignCount,  sum(b.FContractTotalAmount) termSignTotalAmount, " +
				"sum(b.FDealAmount) termSignDealTotalAmount, sum(c.FSaleArea) termSignArea " +
				"from T_SHE_QuitRoom a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql("and b.FToSignDate is not null ");
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		this.appendFilterSql(builder, "b.FToSignDate");
		builder.appendSql(" group by d.FID ");
		IRowSet termSignQuitSellSet = builder.executeQuery();
		while(termSignQuitSellSet.next()){
			String buildingId = termSignQuitSellSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			addToCell(row, "termSignCount", termSignQuitSellSet.getInt("termSignCount"));
			addToCell(row, "termSignArea", termSignQuitSellSet.getBigDecimal("termSignArea"));
			addToCell(row, "termSignDealTotalAmount", termSignQuitSellSet.getBigDecimal("termSignDealTotalAmount"));
			addToCell(row, "termSignTotalAmount", termSignQuitSellSet.getBigDecimal("termSignTotalAmount"));
			reSetSaleAmount(row);
		}
		
		//销售未签约退房前特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termUsignCount,  sum(b.FStandardTotalAmount) termUnsignStandardTotalAmount, " +
				"sum(b.FDealAmount) termUnsignDealTotalAmount, sum(c.FSaleArea) termUnsignArea " +
				"from T_SHE_QuitRoom a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql(" and b.FToSignDate is null ");
		this.appendFilterSqlEndDate(builder, "b.FToSignDate");
		//builder.appendSql(" and b.FToSignDate is null or b.FToSignDate>"+FDCDateHelper.getSqlDate(this.endDate)");
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termUnsignQuitSellSet = builder.executeQuery();
		while(termUnsignQuitSellSet.next()){
			String buildingId = termUnsignQuitSellSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			addToCell(row, "termUsignCount", termUnsignQuitSellSet.getInt("termUsignCount"));
			addToCell(row, "termUnsignArea", termUnsignQuitSellSet.getBigDecimal("termUnsignArea"));
			addToCell(row, "termUnsignDealTotalAmount", termUnsignQuitSellSet.getBigDecimal("termUnsignDealTotalAmount"));
			addToCell(row, "termUnsignStandardTotalAmount", termUnsignQuitSellSet.getBigDecimal("termUnsignStandardTotalAmount"));
			reSetSaleAmount(row);
		}
		
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId,count(*) termSumCount,sum(c.FSaleArea) termSumArea,sum(c.FBuildingArea*c.FBaseBuildingPrice) termSumBaseAmount," 
				+"sum(b.FStandardTotalAmount) termSumAmount,"
				+"sum(b.FDealAmount) termDealTotalAmount,sum(b.FContractTotalAmount) termContractTotalAmount,sum(c.FAreaCompensateAmount) termAreaCompensateAmount " 
				+"from T_SHE_QuitRoom a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID " 
				+"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		if (isPreIntoSale) {
//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//预定不计入销售统计的话,预定退房的纪录也不应统计在内
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FQuitDate");	
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termQuitSet = builder.executeQuery();
		while (termQuitSet.next()) {
			String buildingId = termQuitSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			reduceFromCell(row, "termSumCount", termQuitSet.getInt("termSumCount"));
			reduceFromCell(row, "termSumArea", termQuitSet.getBigDecimal("termSumArea"));
			reduceFromCell(row, "termSumBaseAmount", termQuitSet.getBigDecimal("termSumBaseAmount"));
			reduceFromCell(row, "termSumAmount", termQuitSet.getBigDecimal("termSumAmount"));
			reduceFromCell(row, "termDealTotalAmount", termQuitSet.getBigDecimal("termDealTotalAmount"));
			reduceFromCell(row, "termContractTotalAmount", termQuitSet.getBigDecimal("termContractTotalAmount"));
			reduceFromCell(row, "termAreaCompensateAmount", termQuitSet.getBigDecimal("termAreaCompensateAmount"));
			
			reSetSaleAmount(row);
		}
		
		//签约合同退房后特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termSignCount,  sum(b.FContractTotalAmount) termSignTotalAmount, " +
				"sum(b.FDealAmount) termSignDealTotalAmount, sum(c.FSaleArea) termSignArea " +
				"from T_SHE_QuitRoom a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql("and b.FToSignDate is not null ");
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FQuitDate");	
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termSignQuitSet = builder.executeQuery();
		while(termSignQuitSet.next()){
			String buildingId = termSignQuitSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			reduceFromCell(row, "termSignCount", termSignQuitSet.getInt("termSignCount"));
			reduceFromCell(row, "termSignArea", termSignQuitSet.getBigDecimal("termSignArea"));
			reduceFromCell(row, "termSignDealTotalAmount", termSignQuitSet.getBigDecimal("termSignDealTotalAmount"));
			reduceFromCell(row, "termSignTotalAmount", termSignQuitSet.getBigDecimal("termSignTotalAmount"));
			reSetSaleAmount(row);
		}
		
		//销售未签约退房后特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termUsignCount,  sum(b.FStandardTotalAmount) termUnsignStandardTotalAmount, " +
				"sum(b.FDealAmount) termUnsignDealTotalAmount, sum(c.FSaleArea) termUnsignArea " +
				"from T_SHE_QuitRoom a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
//		builder.appendSql(" and b.FToSignDate is null or b.FToSignDate>"+this.endDate);
		builder.appendSql(" and b.FToSignDate is null ");
		this.appendFilterSqlEndDate(builder, "b.FToSignDate");
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FQuitDate");	
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termUnsignQuitSet = builder.executeQuery();
		while(termUnsignQuitSet.next()){
			String buildingId = termUnsignQuitSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			reduceFromCell(row, "termUsignCount", termUnsignQuitSet.getInt("termUsignCount"));
			reduceFromCell(row, "termUnsignArea", termUnsignQuitSet.getBigDecimal("termUnsignArea"));
			reduceFromCell(row, "termUnsignDealTotalAmount", termUnsignQuitSet.getBigDecimal("termUnsignDealTotalAmount"));
			reduceFromCell(row, "termUnsignStandardTotalAmount", termUnsignQuitSet.getBigDecimal("termUnsignStandardTotalAmount"));
			reSetSaleAmount(row);
		}

		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId,sum(a.FOldDealAmount-a.FNewDealAmount) termDealTotalAmount," 
				+"sum(a.FOldContractAmount-a.FNewContractAmount) termContractTotalAmount from T_SHE_PurchaseChange a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID");
		builder.appendSql(" where a.FState='4AUDITTED' AND c.FIsForSHE=1  ");
		if (isPreIntoSale) {
//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//预定不计入销售统计的话,预定变更的纪录也不应统计在内
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termChangeSellSet = builder.executeQuery();
		while (termChangeSellSet.next()) {
			String buildingId = termChangeSellSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			addToCell(row, "termDealTotalAmount", termChangeSellSet.getBigDecimal("termDealTotalAmount"));
			addToCell(row, "termContractTotalAmount", termChangeSellSet.getBigDecimal("termContractTotalAmount"));
			
			reSetSaleAmount(row);
		}
		
		//签约合同变更前特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, sum(FOldDealAmount-FNewDealAmount) termSignDealTotalAmount, " +
				"sum(FOldContractAmount-FNewContractAmount) termSignTotalAmount from T_SHE_PurchaseChange a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID ");
		builder.appendSql("where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql("and b.FToSignDate is not null ");
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		this.appendFilterSql(builder, "b.FToSignDate");
		builder.appendSql(" group by d.FID ");
		IRowSet termSignChangeSellSet = builder.executeQuery();
		while(termSignChangeSellSet.next()){
			String buildingId = termSignChangeSellSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			addToCell(row, "termSignDealTotalAmount", termSignChangeSellSet.getBigDecimal("termSignDealTotalAmount"));
			addToCell(row, "termSignTotalAmount", termSignChangeSellSet.getBigDecimal("termSignTotalAmount"));
			
			reSetSaleAmount(row);
		}
		
		//销售未签约变更前特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, sum(FOldDealAmount-FNewDealAmount) termSignDealTotalAmount " +
				"from T_SHE_PurchaseChange a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID ");
		builder.appendSql("where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
//		builder.appendSql(" and b.FToSignDate is null or b.FToSignDate>"+this.endDate);
		builder.appendSql(" and b.FToSignDate is null ");
		this.appendFilterSqlEndDate(builder, "b.FToSignDate");
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termUnsignChangeSellSet = builder.executeQuery();
		while(termUnsignChangeSellSet.next()){
			String buildingId = termUnsignChangeSellSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			addToCell(row, "termSignDealTotalAmount", termUnsignChangeSellSet.getBigDecimal("termSignDealTotalAmount"));
			
			reSetSaleAmount(row);
		}
		
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId,sum(a.FOldDealAmount-a.FNewDealAmount) termDealTotalAmount," 
				+"sum(a.FOldContractAmount-a.FNewContractAmount) termContractTotalAmount from T_SHE_PurchaseChange a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID");
		builder.appendSql(" where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		if (isPreIntoSale) {
//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//预定不计入销售统计的话,预定变更的纪录也不应统计在内
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FChangeDate");
		}
		
		builder.appendSql(" group by d.FID ");
		IRowSet termChangeSet = builder.executeQuery();
		while (termChangeSet.next()) {
			String buildingId = termChangeSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			reduceFromCell(row, "termDealTotalAmount", termChangeSet.getBigDecimal("termDealTotalAmount"));
			reduceFromCell(row, "termContractTotalAmount", termChangeSet.getBigDecimal("termContractTotalAmount"));
			
			reSetSaleAmount(row);
		}
		
		//签约合同变更后特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, sum(FOldDealAmount-FNewDealAmount) termSignDealTotalAmount, " +
				"sum(FOldContractAmount-FNewContractAmount) termSignTotalAmount from T_SHE_PurchaseChange a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID ");
		builder.appendSql("where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql("and b.FToSignDate is not null ");
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FChangeDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termSignChangeSet = builder.executeQuery();
		while(termSignChangeSet.next()){
			String buildingId = termSignChangeSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			reduceFromCell(row, "termSignDealTotalAmount", termSignChangeSet.getBigDecimal("termSignDealTotalAmount"));
			reduceFromCell(row, "termSignTotalAmount", termSignChangeSet.getBigDecimal("termSignTotalAmount"));
			
			reSetSaleAmount(row);
		}
		
		//销售未签约变更后特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, sum(FOldDealAmount-FNewDealAmount) termSignDealTotalAmount " +
				"from T_SHE_PurchaseChange a " +
				"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID ");
		builder.appendSql("where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql(" and b.FToSignDate is null ");
		this.appendFilterSqlEndDate(builder, "b.FToSignDate");
//		builder.appendSql(" and b.FToSignDate is null or b.FToSignDate>"+this.endDate);
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FChangeDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termUnsignChangeSet = builder.executeQuery();
		while(termUnsignChangeSet.next()){
			String buildingId = termUnsignChangeSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			reduceFromCell(row, "termSignDealTotalAmount", termUnsignChangeSet.getBigDecimal("termSignDealTotalAmount"));
			
			reSetSaleAmount(row);
		}
		
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId,count(*) termSumCount,sum(c.FSaleArea) termSumArea,sum(c.FBuildingArea*c.FBaseBuildingPrice) termSumBaseAmount," 
				+"sum(b.FStandardTotalAmount) termSumAmount,"
				+"sum(b.FDealAmount) termDealTotalAmount,sum(b.FContractTotalAmount) termContractTotalAmount,sum(c.FAreaCompensateAmount) termAreaCompensateAmount " 
				+"from T_SHE_ChangeRoom a " 
				+"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID " 
				+"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		if (isPreIntoSale) {
//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//预定不计入销售统计的话,预定退房的纪录也不应统计在内
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet changeRoomSellSet = builder.executeQuery();
		while (changeRoomSellSet.next()) {
			String buildingId = changeRoomSellSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			addToCell(row, "termSumCount", changeRoomSellSet.getInt("termSumCount"));
			addToCell(row, "termSumArea", changeRoomSellSet.getBigDecimal("termSumArea"));
			addToCell(row, "termSumBaseAmount", changeRoomSellSet.getBigDecimal("termSumBaseAmount"));
			addToCell(row, "termSumAmount", changeRoomSellSet.getBigDecimal("termSumAmount"));
			addToCell(row, "termDealTotalAmount", changeRoomSellSet.getBigDecimal("termDealTotalAmount"));
			addToCell(row, "termContractTotalAmount", changeRoomSellSet.getBigDecimal("termContractTotalAmount"));
			addToCell(row, "termAreaCompensateAmount", changeRoomSellSet.getBigDecimal("termAreaCompensateAmount"));
			
			reSetSaleAmount(row);//如果变化了合同总价,销售总价也要相应变化
		}
		
		//签约合同换房前特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termSignCount,  sum(b.FContractTotalAmount) termSignTotalAmount, " +
				"sum(b.FDealAmount) termSignDealTotalAmount, sum(c.FSaleArea) termSignArea " +
				"from T_SHE_ChangeRoom a " +
				"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql("and b.FToSignDate is not null ");
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		this.appendFilterSql(builder, "b.FToSignDate");
		builder.appendSql(" group by d.FID ");
		IRowSet signChangeRoomSellSet = builder.executeQuery();
		while(signChangeRoomSellSet.next()){
			String buildingId = signChangeRoomSellSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			addToCell(row, "termSignCount", signChangeRoomSellSet.getInt("termSignCount"));
			addToCell(row, "termSignArea", signChangeRoomSellSet.getBigDecimal("termSignArea"));
			addToCell(row, "termSignDealTotalAmount", signChangeRoomSellSet.getBigDecimal("termSignDealTotalAmount"));
			addToCell(row, "termSignTotalAmount", signChangeRoomSellSet.getBigDecimal("termSignTotalAmount"));
			reSetSaleAmount(row);
		}
		
		//销售未签约换房前特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termUsignCount,  sum(b.FStandardTotalAmount) termUnsignStandardTotalAmount, " +
				"sum(b.FDealAmount) termUnsignDealTotalAmount, sum(c.FSaleArea) termUnsignArea " +
				"from T_SHE_ChangeRoom a " +
				"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql(" and b.FToSignDate is null ");
		this.appendFilterSqlEndDate(builder, "b.FToSignDate");
//		builder.appendSql(" and b.FToSignDate is null or b.FToSignDate>"+this.endDate);
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termUnsignChangeRoomSellSet = builder.executeQuery();
		while(termUnsignChangeRoomSellSet.next()){
			String buildingId = termUnsignChangeRoomSellSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			addToCell(row, "termUsignCount", termUnsignChangeRoomSellSet.getInt("termUsignCount"));
			addToCell(row, "termUnsignArea", termUnsignChangeRoomSellSet.getBigDecimal("termUnsignArea"));
			addToCell(row, "termUnsignDealTotalAmount", termUnsignChangeRoomSellSet.getBigDecimal("termUnsignDealTotalAmount"));
			addToCell(row, "termUnsignStandardTotalAmount", termUnsignChangeRoomSellSet.getBigDecimal("termUnsignStandardTotalAmount"));
			reSetSaleAmount(row);
		}
		
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId,count(*) termSumCount,sum(c.FSaleArea) termSumArea,sum(c.FBuildingArea*c.FBaseBuildingPrice) termSumBaseAmount," 
				+"sum(b.FStandardTotalAmount) termSumAmount,"
				+"sum(b.FDealAmount) termDealTotalAmount,sum(b.FContractTotalAmount) termContractTotalAmount,sum(c.FAreaCompensateAmount) termAreaCompensateAmount " 
				+"from T_SHE_ChangeRoom a " 
				+"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID " 
				+"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		if (isPreIntoSale) {
//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//预定不计入销售统计的话,预定退房的纪录也不应统计在内
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FChangeDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termChangeRoomSet = builder.executeQuery();
		while (termChangeRoomSet.next()) {
			String buildingId = termChangeRoomSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			reduceFromCell(row, "termSumCount", termChangeRoomSet.getInt("termSumCount"));
			reduceFromCell(row, "termSumArea", termChangeRoomSet.getBigDecimal("termSumArea"));
			reduceFromCell(row, "termSumBaseAmount", termChangeRoomSet.getBigDecimal("termSumBaseAmount"));
			reduceFromCell(row, "termSumAmount", termChangeRoomSet.getBigDecimal("termSumAmount"));
			reduceFromCell(row, "termDealTotalAmount", termChangeRoomSet.getBigDecimal("termDealTotalAmount"));
			reduceFromCell(row, "termContractTotalAmount", termChangeRoomSet.getBigDecimal("termContractTotalAmount"));
			reduceFromCell(row, "termAreaCompensateAmount", termChangeRoomSet.getBigDecimal("termAreaCompensateAmount"));
			
			reSetSaleAmount(row);
		}
		
		//签约合同换房后特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termSignCount,  sum(b.FContractTotalAmount) termSignTotalAmount, " +
				"sum(b.FDealAmount) termSignDealTotalAmount, sum(c.FSaleArea) termSignArea " +
				"from T_SHE_ChangeRoom a " +
				"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql("and b.FToSignDate is not null ");
		if(!isIncludeAttach){
			builder.appendSql("and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FChangeDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet signChangeRoomSet = builder.executeQuery();
		while(signChangeRoomSet.next()){
			String buildingId = signChangeRoomSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			reduceFromCell(row, "termSignCount", signChangeRoomSet.getInt("termSignCount"));
			reduceFromCell(row, "termSignArea", signChangeRoomSet.getBigDecimal("termSignArea"));
			reduceFromCell(row, "termSignDealTotalAmount", signChangeRoomSet.getBigDecimal("termSignDealTotalAmount"));
			reduceFromCell(row, "termSignTotalAmount", signChangeRoomSet.getBigDecimal("termSignTotalAmount"));
			reSetSaleAmount(row);
		}
		
		//销售未签约换房后特殊业务
		builder.clear();
		builder
		.appendSql("select d.FID FBuildingId, count(*) termUsignCount,  sum(b.FStandardTotalAmount) termUnsignStandardTotalAmount, " +
				"sum(b.FDealAmount) termUnsignDealTotalAmount, sum(c.FSaleArea) termUnsignArea " +
				"from T_SHE_ChangeRoom a " +
				"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " +
				"left outer join T_SHE_Room c on b.FRoomID=c.FID " +
				"left outer join T_SHE_Building d on c.FBuildingID=d.FID " +
				"where a.FState='4AUDITTED' AND c.FIsForSHE=1 ");
		builder.appendSql(" and b.FToSignDate is null ");
		this.appendFilterSqlEndDate(builder, "b.FToSignDate");
//		builder.appendSql(" and b.FToSignDate is null or b.FToSignDate>"+this.endDate);
		if(!isIncludeAttach){
			builder.appendSql(" and c.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(setting.getBaseRoomSetting().isAuditDate()){
			this.appendFilterSql(builder, "a.FAuditTime");
		}else{
			this.appendFilterSql(builder, "a.FChangeDate");
		}
		builder.appendSql(" group by d.FID ");
		IRowSet termUnsignChangeRoomSet = builder.executeQuery();
		while(termUnsignChangeRoomSet.next()){
			String buildingId = termUnsignChangeRoomSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			reduceFromCell(row, "termUsignCount", termUnsignChangeRoomSet.getInt("termUsignCount"));
			reduceFromCell(row, "termUnsignArea", termUnsignChangeRoomSet.getBigDecimal("termUnsignArea"));
			reduceFromCell(row, "termUnsignDealTotalAmount", termUnsignChangeRoomSet.getBigDecimal("termUnsignDealTotalAmount"));
			reduceFromCell(row, "termUnsignStandardTotalAmount", termUnsignChangeRoomSet.getBigDecimal("termUnsignStandardTotalAmount"));
			reSetSaleAmount(row);
		}
	}

	/**
	 * 重新计算销售金额：合同金额+补差金额
	 * */
	private void reSetSaleAmount(IRow row) {		
		BigDecimal termContractTotalAmount = (BigDecimal) row.getCell("termContractTotalAmount").getValue();
		if(termContractTotalAmount == null){
			termContractTotalAmount = FDCHelper.ZERO;
		}
		
		BigDecimal termAreaCompensateAmount = (BigDecimal) row.getCell("termAreaCompensateAmount").getValue();
		if (termAreaCompensateAmount == null) {
			termAreaCompensateAmount = FDCHelper.ZERO;
		}
		BigDecimal termSumSaleAmount = termContractTotalAmount.add(termAreaCompensateAmount);
		row.getCell("termSumSaleAmount").setValue(termSumSaleAmount);
	}

	private void reduceFromCell(IRow row, String colName, BigDecimal reducedValue) {
		if(reducedValue == null  ||   reducedValue.compareTo(FDCHelper.ZERO) == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		BigDecimal srcValue = (BigDecimal) cell.getValue();
		if(srcValue == null) srcValue = FDCHelper.ZERO;
		cell.setValue(srcValue.subtract(reducedValue));
	}

	private void reduceFromCell(IRow row, String colName, int reducedValue) {
		if(reducedValue == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		int srcValue = 0;
		if(cell.getValue() != null) srcValue = ((Integer)cell.getValue()).intValue();
		cell.setValue(new Integer(srcValue - reducedValue));
	}

	private void addToCell(IRow row, String colName, BigDecimal addedValue) {
		if(addedValue == null  ||   addedValue.compareTo(FDCHelper.ZERO) == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		BigDecimal srcValue = (BigDecimal) cell.getValue();
		if(srcValue == null) srcValue = FDCHelper.ZERO;
		cell.setValue(srcValue.add(addedValue));
	}

	/**
	 * 将addedValue加到原先单元格的值上,并将新值设置在单元格上
	 * */
	private void addToCell(IRow row, String colName, int addedValue) {
		if(addedValue == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		int srcValue = 0;
		if(cell.getValue() != null) srcValue = ((Integer)cell.getValue()).intValue();
		cell.setValue(new Integer(srcValue + addedValue));
	}

	/**
	 * 填充 销售回款
	 * */
	private void fillTermReceiveAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();		
		//收款单款项类型改到分录后对报表的修改   zhicheng_jin
		builder
		.appendSql("select fbuildingid,sum(fre.famount) sumRevAmount from T_SHE_FDCReceiveBillEntry fre "
				+ "left join t_cas_receivingbill cas on fre.FReceivingBillID=cas.fid "
				+ "left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
				+ "inner join t_she_room room on fdc.froomid=room.fid "
				+ "left join T_SHE_Purchase as purchase on room.fid= purchase.froomid "
				+ "left join t_she_building building on  room.fbuildingid=building.fid "
				+ "left join t_she_moneyDefine money on  fre.FMoneyDefineId=money.fid "
				+ "where money.FMoneyType in ('PreconcertMoney','PurchaseAmount','HouseAmount','FisrtAmount','CompensateAmount','LoanAmount','AccFundAmount','Refundment')"
				//+ " and purchase.FPurchaseState not in('ChangeRoomBlankOut','QuitRoomBlankOut','NoPayBlankOut','ManualBlankOut') "
				+ " and fdc.fpurchaseid=purchase.fid AND room.FIsForSHE=1 ");

//		if (isPreIntoSale) {  跟房间状态无关
//			builder.appendSql(" and (room.fSellState = 'PrePurchase' or room.fSellState = 'Purchase' or room.fSellState = 'Sign')" );
//		} else {
//			builder.appendSql(" and (room.fSellState = 'Purchase' or room.fSellState = 'Sign')" );
//		}
		
//		if(!isIncludeAttach){  //要改得和与收款趋势表同步
//			builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
//		}
		
		this.appendFilterSql(builder, "cas.FBizDate");
		builder.appendSql(" group by fbuildingid ");
		
		
		IRowSet termReceiveSet = builder.executeQuery();
		while (termReceiveSet.next()) {
			String buildingId = termReceiveSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			BigDecimal termReceiveSumAmount = termReceiveSet
					.getBigDecimal("sumRevAmount");
			row.getCell("termReceiveSumAmount").setValue(termReceiveSumAmount);
			
			BigDecimal termSumSaleAmount = (BigDecimal) row.getCell("termSumSaleAmount").getValue();
			if(termSumSaleAmount == null){
				termSumSaleAmount = FDCHelper.ZERO;
			}
			if(termReceiveSumAmount == null){
				termReceiveSumAmount = FDCHelper.ZERO;
			}
			
//			row.getCell("termArrearageSumAmount").setValue(termSumSaleAmount.subtract(termReceiveSumAmount));
		}
	}

	/**
	 * 填充 合同总价  销售总价
	 * */
	private void fillTermContractAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException {
		//合同总价
		FDCSQLBuilder termContractBuilder = new FDCSQLBuilder();
		termContractBuilder.appendSql("select room.fbuildingid fbuildingId, sum(pur.FContractTotalAmount) termContractTotalAmount from t_she_room room " +
				"inner join t_she_purchase pur on room.flastpurchaseid=pur.fid ");
		if (isPreIntoSale) {
			termContractBuilder.appendSql(" where (room.fSellState = 'PrePurchase' or room.fSellState = 'Purchase' or room.fSellState = 'Sign') ");
		} else {
			termContractBuilder.appendSql(" where (room.fSellState = 'Purchase' or room.fSellState = 'Sign') ");
		}
		if(!isIncludeAttach){
			termContractBuilder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		
		if(isPreIntoSale){
			this.appendFilterSql(termContractBuilder, "room.FToSaleDate");
		}else{
			this.appendFilterSql(termContractBuilder, "room.FToPurchaseDate");
		}		
		termContractBuilder.appendSql(" AND room.FIsForSHE=1 ");		
		termContractBuilder.appendSql(" group by fbuildingid");
		IRowSet termContractSet = termContractBuilder.executeQuery();
		while (termContractSet.next()) {
			String buildingId = termContractSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			BigDecimal termContractTotalAmount = termContractSet.getBigDecimal("termContractTotalAmount");
			if(termContractTotalAmount == null){
				termContractTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termContractTotalAmount").setValue(termContractTotalAmount);
			
			BigDecimal termAreaCompensateAmount = (BigDecimal) row.getCell("termAreaCompensateAmount").getValue();
			if (termAreaCompensateAmount == null) {
				termAreaCompensateAmount = FDCHelper.ZERO;
			}
			BigDecimal termSumSaleAmount = termContractTotalAmount.add(termAreaCompensateAmount);
			row.getCell("termSumSaleAmount").setValue(termSumSaleAmount);
		}
	}

	/**
	 * 填充 签约合同总价、签约合同套数、签约合同面积、签约合同均价、签约合同成交总价
	 * */
	private void fillTermSignAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException {
		//合同总价
		FDCSQLBuilder termSignBuilder = new FDCSQLBuilder();
		termSignBuilder.
		appendSql("select room.FBuildingId FBuildingId, sum(pur.FContractTotalAmount) termSignTotalAmount, count(room.FID) termSignCount, sum(FSaleArea) termSignArea, sum(pur.FDealAmount) termSignDealTotalAmount from t_she_room room " +
				"inner join t_she_purchase pur on room.flastpurchaseid=pur.fid ");
		//termSignBuilder.appendSql(" where room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "' ");
		termSignBuilder.appendSql("where pur.FToSignDate is not null ");
		if(!isIncludeAttach){
			termSignBuilder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		//不对认购和预定时间进行过滤，只对签约时间进行过滤
//		if(isPreIntoSale){
//			this.appendFilterSql(termSignBuilder, "room.FToSaleDate");
//		}else{
//			this.appendFilterSql(termSignBuilder, "room.FToPurchaseDate");
//		}
		this.appendFilterSql(termSignBuilder, "room.FToSignDate");
		termSignBuilder.appendSql("  AND  room.FIsForSHE=1 ");
		termSignBuilder.appendSql(" group by FBuildingId ");
		IRowSet termSignSet = termSignBuilder.executeQuery();
		while (termSignSet.next()) {
			String buildingId = termSignSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}			
			BigDecimal termSignTotalAmount = termSignSet.getBigDecimal("termSignTotalAmount");
			if(termSignTotalAmount == null){
				termSignTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termSignTotalAmount").setValue(termSignTotalAmount);
			
			row.getCell("termSignCount").setValue(new Integer(termSignSet.getInt("termSignCount")));
			
			BigDecimal termSignArea = termSignSet.getBigDecimal("termSignArea");
			if(termSignArea == null){
				termSignArea = FDCHelper.ZERO;
			}
			row.getCell("termSignArea").setValue(termSignArea);
			if(FDCHelper.ZERO.compareTo(termSignArea) != 0){
				row.getCell("termSignAvgPrice").setValue(termSignTotalAmount.divide(termSignArea,2,BigDecimal.ROUND_HALF_UP));
			}else{
				row.getCell("termSignAvgPrice").setValue(FDCHelper.ZERO);
			}
			BigDecimal termSignDealTotalAmount = termSignSet.getBigDecimal("termSignDealTotalAmount");
			if(termSignDealTotalAmount == null){
				termSignDealTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termSignDealTotalAmount").setValue(termSignDealTotalAmount);
		}
	}
	
	/**
	 * 填充销售未签约套数、销售未签约面积、销售未签约标准总价、	销售未签约成交总价
	 */
	public void fillTermUnsignAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select room.FBuildingId FBuildingId, sum(pur.FStandardTotalAmount) termUnsignStandardTotalAmount, count(room.FID) termUnsignCount, sum(FSaleArea) termUnsignArea, sum(pur.FDealAmount) termUnsignDealTotalAmount from t_she_room room " +
		"inner join t_she_purchase pur on room.flastpurchaseid=pur.fid ");
//		builder.appendSql(" where 1=1 ");
		builder.appendSql(" where pur.FToSignDate is null ");
		this.appendFilterSqlEndDate(builder, "pur.FToSignDate");
//		builder.appendSql(" where pur.FToSignDate is null or pur.FToSignDate>"+this.endDate);
		if(!isIncludeAttach){
			builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "pur.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "pur.FToPurchaseDate");
		}
		builder.appendSql("  AND  room.FIsForSHE=1 ");
		builder.appendSql(" group by FBuildingId ");
		IRowSet termUnsignSet = builder.executeQuery();
		while(termUnsignSet.next()){
			String buildingId = termUnsignSet.getString("FBuildingId");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			
			row.getCell("termUnsignCount").setValue(new Integer(termUnsignSet.getInt("termUnsignCount")));
			
			BigDecimal termUnsignArea = termUnsignSet.getBigDecimal("termUnsignArea");
			if(termUnsignArea == null){
				termUnsignArea = FDCHelper.ZERO;
			}
			row.getCell("termUnsignArea").setValue(termUnsignArea);
			
			BigDecimal termUnsignStandardTotalAmount = termUnsignSet.getBigDecimal("termUnsignStandardTotalAmount");
			if(termUnsignStandardTotalAmount == null){
				termUnsignStandardTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termUnsignStandardTotalAmount").setValue(termUnsignStandardTotalAmount);
			
			BigDecimal termUnsignDealTotalAmount = termUnsignSet.getBigDecimal("termUnsignDealTotalAmount");
			if(termUnsignDealTotalAmount == null){
				termUnsignDealTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termUnsignDealTotalAmount").setValue(termUnsignDealTotalAmount);
		}
	}
	
	/**
	 * 填充 "销售套数"，"销售面积" ，底价总价，底价均价，标准总价，标准均价，成交总价，成交均价，"补差金额"
	 * 注：其实这里的均价均不用计算,在最后resetUnionData函数中还是会进行均价计算的
	 * */
	private void fillTermSellAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select FBuildingId,count(*) totalCount,sum(FBuildingArea*FBaseBuildingPrice) termSumBaseAmount,sum(FSaleArea) buildingArea,"
						+ "sum(FStandardTotalAmount) standardTotalAmount ,sum(FDealTotalAmount) dealTotalamount,"
						+ "sum(FDealTotalAmount) dealTotalAmount ,sum(FAreaCompensateAmount) areaCompensateAmount "
						+ "from t_she_room ");
		if (isPreIntoSale) {
			builder.appendSql(" where (fSellState = '" + RoomSellStateEnum.PREPURCHASE_VALUE + "' or fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') ");
		} else {
			builder.appendSql(" where (fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') ");
		}
		if(!isIncludeAttach){
			builder.appendSql(" and FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "FToSaleDate");
		}else{
			this.appendFilterSql(builder, "FToPurchaseDate");
		}
		builder.appendSql(" AND FIsForSHE=1 ");
		builder.appendSql(" group by fbuildingid ");
		IRowSet termSellSet = builder.executeQuery();
		while (termSellSet.next()) {
			String buildingId = termSellSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			row.getCell("termSumCount").setValue(
					new Integer(termSellSet.getInt("totalCount")));
			BigDecimal termSellArea = termSellSet.getBigDecimal("buildingArea");
			if (termSellArea == null) {
				termSellArea = FDCHelper.ZERO;
			}
			row.getCell("termSumArea").setValue(termSellArea);
			BigDecimal termSellSumAmount = termSellSet
					.getBigDecimal("standardTotalAmount");
			if (termSellSumAmount == null) {
				termSellSumAmount = FDCHelper.ZERO;
			}
			row.getCell("termSumAmount").setValue(termSellSumAmount);
			if (termSellArea.compareTo(FDCHelper.ZERO) != 0) {
				row.getCell("termAvgPrice").setValue(
						termSellSumAmount.divide(termSellArea, 2,
								BigDecimal.ROUND_HALF_UP));
			}
			
			BigDecimal termDealTotalAmount = termSellSet
					.getBigDecimal("dealTotalAmount");
			if(termDealTotalAmount == null){
				termDealTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termDealTotalAmount").setValue(termDealTotalAmount);
			BigDecimal termAreaCompensateAmount = termSellSet
					.getBigDecimal("areaCompensateAmount");
			row.getCell("termAreaCompensateAmount").setValue(
					termAreaCompensateAmount);

			if (termAreaCompensateAmount == null) {
				termAreaCompensateAmount = FDCHelper.ZERO;
			}
//			 BigDecimal termDealAmount = termSellSet
//			 .getBigDecimal("dealTotalamount");
//			BigDecimal dealAmount = termDealTotalAmount
//					.add(termAreaCompensateAmount);
//			row.getCell("termSumSaleAmount").setValue(dealAmount);

			row.getCell("termSubAmount").setValue(
					termSellSumAmount.subtract(termDealTotalAmount));

			BigDecimal termSumBaseAmount = termSellSet.getBigDecimal("termSumBaseAmount");
			row.getCell("termSumBaseAmount").setValue(termSumBaseAmount);
			
			if(termSumBaseAmount == null){
				termSumBaseAmount = FDCHelper.ZERO;
			}
			
			if(termSellArea.compareTo(FDCHelper.ZERO) != 0){
				BigDecimal termAvgBasePrice = termSumBaseAmount.divide(termSellArea, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("termAvgBasePrice").setValue(termAvgBasePrice);
			}
			
		}
	}
	
	/**
	 * 填充保留套数、保留面积、保留标准总价
	 */
	public void fillTermKeepAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select FBuildingID, count(*) termKeepCount, sum(FSaleArea) termKeepArea, sum(FStandardTotalAmount) termKeepStandardTotalAmount from T_SHE_Room room " +
				"inner join T_SHE_RoomKeepDownBill keep on room.FLastKeepDownBillID=keep.FID ");
		builder.appendSql("where keep.FBizDate is not null ");
		if(!isIncludeAttach){
			builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		this.appendFilterSql(builder, "keep.FBizDate");
		builder.appendSql(" AND FIsForSHE=1 ");
		builder.appendSql(" group by FBuildingID ");
		IRowSet termKeepSet = builder.executeQuery();
		while(termKeepSet.next()){
			String buildingId = termKeepSet.getString("FBuildingID");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			row.getCell("termKeepCount").setValue(new Integer(termKeepSet.getInt("termKeepCount")));
			
			BigDecimal termKeepArea = termKeepSet.getBigDecimal("termKeepArea");
			if(termKeepArea == null){
				termKeepArea = FDCHelper.ZERO;
			}
			row.getCell("termKeepArea").setValue(termKeepArea);
			
			BigDecimal termKeepStandardTotalAmount = termKeepSet.getBigDecimal("termKeepStandardTotalAmount");
			if(termKeepStandardTotalAmount == null){
				termKeepStandardTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termKeepStandardTotalAmount").setValue(termKeepStandardTotalAmount);
		}
	}
	
	/**
	 * 填充撤盘套数、撤盘面积、撤盘标准总价  
	 */
	public void fillTermQuitAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select room.FBuildingID FBuildingID, count(room.FID) termQuitCount, sum(room.FSaleArea) termQuitArea, sum(room.FStandardTotalAmount) termQuitStandardTotalAmount " +
				"from T_SHE_SellOrderRoomEntry quit " +
				"inner join T_SHE_Room room on quit.FRoomID=room.FID ");
		builder.appendSql(" where FQuitOrderDate is not null");
//		builder.appendSql("where 1=1");
		if(!isIncludeAttach){
			builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		this.appendFilterSql(builder, "FQuitOrderDate");
		builder.appendSql("  AND  room.FIsForSHE=1 ");
		builder.appendSql(" group by FBuildingID ");
		IRowSet termQuitSet = builder.executeQuery();
		while(termQuitSet.next()){
			String buildingId = termQuitSet.getString("FBuildingID");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			row.getCell("termQuitCount").setValue(new Integer(termQuitSet.getInt("termQuitCount")));
			
			BigDecimal termQuitArea = termQuitSet.getBigDecimal("termQuitArea");
			if(termQuitArea == null){
				termQuitArea = FDCHelper.ZERO;
			}
			row.getCell("termQuitArea").setValue(termQuitArea);
			
			BigDecimal termQuitStandardTotalAmount = termQuitSet.getBigDecimal("termQuitStandardTotalAmount");
			if(termQuitStandardTotalAmount == null){
				termQuitStandardTotalAmount = FDCHelper.ZERO;
			}
			row.getCell("termQuitStandardTotalAmount").setValue(termQuitStandardTotalAmount);
		}
	}
	
	/**
	 * 填充定金类回款
	 * 计算方式：当前定金-当前已结转金额
	 */
	public void fillTermPurchaseAmount(boolean isPreIntoSale, boolean isIncludeAttach) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		Map carryoverMap = new HashMap();     //存放已结转的金额
//		Map depositMap = new HashMap();       //存放定金
		
		//查出已结转的
		builder
		.appendSql("select g.FBuildingID FBuildingID, sum(a.FAmount) termCarryoverAmount from T_SHE_FdcBillSources a inner join T_SHE_FDCReceiveBillEntry b on a.FEntrySourceID=b.FID " +
				"left join T_CAS_ReceivingBill c on b.FReceivingBillID=c.FID " +
				"left join T_SHE_MoneyDefine h on b.FMoneyDefineID=h.FID " +
				"left join T_SHE_FDCReceiveBillEntry d on a.FHeadID=d.FID " +
				"left join T_CAS_ReceivingBill e on d.FReceivingBillID=e.FID " +
				"inner join T_SHE_FDCReceiveBill f on e.FFdcReceiveBillID=f.FID " +
				"inner join t_she_room g on f.froomid=g.fid " +
				"where h.FMoneyType='PurchaseAmount' " +
				" AND g.FIsForSHE=1 ");
		if(!isIncludeAttach){
			builder.appendSql(" and g.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		this.appendFilterSql(builder, "c.FBizDate");
		this.appendFilterSql(builder, "e.FBizDate");
		builder.appendSql(" group by FBuildingID ");
		IRowSet termCarryoverSet = builder.executeQuery();
		while(termCarryoverSet.next()){
			String buildingId = termCarryoverSet.getString("FBuildingID");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			
			BigDecimal termCarryoverAmount = termCarryoverSet.getBigDecimal("termCarryoverAmount");
			if(termCarryoverAmount == null){
				termCarryoverAmount = FDCHelper.ZERO;
			}
	
			carryoverMap.put(buildingId, termCarryoverAmount);
		}
		
		//查出定金
		builder.clear();
		builder
		.appendSql("select room.FBuildingID FBuildingID, sum(fre.FAmount) termDepositAmount from T_SHE_FDCReceiveBillEntry fre " +
				"left join T_CAS_ReceivingBill rb on fre.FReceivingBillID=rb.FID " +
				"inner join T_SHE_FDCReceiveBill frb on rb.FFdcReceiveBillID=frb.FID " +
				"inner join t_she_room room on frb.FRoomID=room.FID " +
				"left join T_SHE_MoneyDefine money on fre.FMoneyDefineID=money.FID " +
				"where money.FMoneyType='PurchaseAmount' " +
				"and frb.FBillTypeEnum='gathering' " +
				"and  room.FIsForSHE=1 ");
		if(!isIncludeAttach){
			builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum.ATTACHMENT_VALUE+"' ");
		}
		this.appendFilterSql(builder, "rb.FBizDate");
		builder.appendSql(" group by FBuildingID ");
		IRowSet termDepositSet = builder.executeQuery();
		while(termDepositSet.next()){
			String buildingId = termDepositSet.getString("FBuildingID");
			IRow row = (IRow)this.rowMap.get(buildingId);
			if(row == null){
				continue;
			}
			BigDecimal termDepositAmount = termDepositSet.getBigDecimal("termDepositAmount");
			if(termDepositAmount == null){
				termDepositAmount = FDCHelper.ZERO;
			}
//			depositMap.put(buildingId, termDepositAmount);
			
			if(carryoverMap.get(buildingId)!=null){
				BigDecimal termCarryoverAmount = (BigDecimal)carryoverMap.get(buildingId);
				if(termCarryoverAmount == null) termCarryoverAmount = FDCHelper.ZERO;
				termDepositAmount = termDepositAmount.subtract(termCarryoverAmount);
			}
			row.getCell("termPurchaseAmount").setValue(termDepositAmount);
			
		}
		
	}
	
	public void appendFilterSql(FDCSQLBuilder builder, String proName) {
		if (!isShowAll) {
			Date beginDate = this.beginDate;
			if (beginDate != null) {
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.endDate;
			if (endDate != null) {
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}
		}
	}
	
	private void appendFilterSqlEndDate(FDCSQLBuilder builder, String proName){
		if (!isShowAll) {
			Date endDate = this.endDate;
			if (endDate != null) {
				builder.appendSql(" or " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}	
		}
	}

	protected boolean initDefaultFilter() {
		return true;
	}


	
	private void initTable1(){
//		this.tblMain.removeColumns();
		
//		String[] keys = new String[]{     "name", "sumCount", "sumArea", "avgPrice", "sumAmount", "sellSumCount", "sellSumArea", "sellAvgBasePrice", "sellSumBaseAmount", "sellAvgPrice", "sellSumAmount", "sellAvgSubAmount", "sellSubAmount", "sellAvgDealAmount", "sellSumDealAmount",  "sellSumSignAmount", "sellSumContractAmount", "sellAreaCompensateAmount", "sellSumSaleAmount", "noSellSumCount", "noSellSumArea", "noSellAvgPrice", "noSellSumAmount", "receiveSumAmount", "arrearageSumAmount", "termSumCount", "termSumArea", "termAvgBasePrice", "termSumBaseAmount", "termAvgPrice", "termSumAmount", "termAvgSubAmount", "termSubAmount", "termAvgDealTotalAmount", "termDealTotalAmount", "termSignTotalAmount", "termContractTotalAmount", "termAreaCompensateAmount","termSumSaleAmount","termReceiveSumAmount", "termArrearageSumAmount"};
//		String[] headText1 = new String[]{"总称",  "总套数",    "总面积",   "均价",      "总标准价",   "已售套数",       "已售面积",     "已售底价均价",        "已售底价总价",       "已售标准均价",    "已售标准总价",    "已售优惠均价",       "已售优惠额",      "已售成交均价",        "已售成交总价",          "签约合同总价",        "已售合同总价",            "已售补差金额",                "已售销售总额",        "未售套数",         "未售面积",       "未售均价",        "未售标准总价",      "累计回款",           "累计欠款",             "查询期间数据",   "查询期间数据",  "查询期间数据",        "查询期间数据",        "查询期间数据",   "查询期间数据",     "查询期间数据",       "查询期间数据",    "查询期间数据",              "查询期间数据",          "查询期间数据",          "查询期间数据",              "查询期间数据",               "查询期间数据",       "查询期间数据",           "查询期间数据"};
//		String[] headText2 = new String[]{"总称",  "总套数",    "总面积",   "均价",      "总标准价",   "已售套数",       "已售面积",     "已售底价均价",        "已售底价总价",       "已售标准均价",    "已售标准总价",    "已售优惠均价",       "已售优惠额",      "已售成交均价",        "已售成交总价",          "签约合同总价",        "已售合同总价",            "已售补差金额",                "已售销售总额",        "未售套数",         "未售面积",       "未售均价",        "未售标准总价",      "累计回款",           "累计欠款",             "销售套数",      "销售面积",      "底价均价",           "底价总价",           "标准均价",      "标准总价",        "优惠均价",          "优惠额",         "成交均价",                 "成交总价",              "签约合同总价",          "合同总价",                 "补差金额",                  "销售总价",          "销售回款",               "销售欠款"};
		
		String[] keys = new String[]{      "name","termSumCount", "termSumArea", "termAvgBasePrice", "termSumBaseAmount", "termAvgPrice", "termSumAmount", "termAvgSubAmount", "termSubAmount", "termAvgDealTotalAmount", "termDealTotalAmount", "termSignTotalAmount", "termContractTotalAmount", "termAreaCompensateAmount","termSumSaleAmount","termReceiveSumAmount", "termArrearageSumAmount"};
		String[] headText1 = new String[]{ "总称", "查询期间数据",   "查询期间数据",  "查询期间数据",        "查询期间数据",        "查询期间数据",   "查询期间数据",     "查询期间数据",       "查询期间数据",    "查询期间数据",              "查询期间数据",          "查询期间数据",          "查询期间数据",              "查询期间数据",               "查询期间数据",       "查询期间数据",           "查询期间数据"};
		String[] headText2 = new String[]{ "总称", "销售套数",      "销售面积",      "底价均价",           "底价总价",           "标准均价",      "标准总价",        "优惠均价",          "优惠额",         "成交均价",                 "成交总价",              "签约合同总价",          "合同总价",                 "补差金额",                  "销售总价",          "销售回款",               "销售欠款"};
		
//		for(int i=0; i<keys.length; i++){
//			IColumn col = this.tblMain.addColumn();
//			col.setKey(keys[i]);
//		}
//		IRow headRow1 = this.tblMain.addHeadRow();
//		IRow headRow2 = this.tblMain.addHeadRow();
//		for(int i=0; i<keys.length; i++){
//			headRow1.getCell(i).setValue(headText1[i]);
//			headRow2.getCell(i).setValue(headText2[i]);
//		}
		
		this.tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		this.tblMain.getHeadMergeManager().mergeBlock(0, 1, 0, tblMain.getColumnCount() - 1);
	}
	
	private void initTable() {
		//表头合并
		initTable1();
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		for (int i = 1; i < this.tblMain.getColumnCount(); i++) {
			tblMain.getColumn(i).getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
		}
		tblMain.getColumn("termSumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termSignCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termUnsignCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termKeepCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termQuitCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
	}

	public void loadFields() {
		super.loadFields();
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + building.getName());
			row.setUserObject(node.getUserObject());
			this.rowMap.put(building.getId().toString(), row);
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	private void fillProjectAndBuilding(Map timeMap) throws BOSException, EASBizException,
			SQLException {
		tblMain.removeRows();
		TreeModel buildingTree = null;
		
		if (timeMap.get("org") != null)
		{
			try
			{
				String id = (String) timeMap.get("org");

				SaleOrgUnitInfo org = new SaleOrgUnitInfo();
				org.setId(BOSUuid.read(id));
				
				buildingTree = FDCTreeHelper.getBuildingTree(org,MoneySysTypeEnum.SalehouseSys);
			} catch (Exception e)
			{
				MsgBox.showWarning("传入组织构建楼栋树出错，将按默认组织获取楼栋树！");
				try
				{
					buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,	MoneySysTypeEnum.SalehouseSys);
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		} 
		else
		{
			try
			{
				buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,	MoneySysTypeEnum.SalehouseSys);
			} catch (Exception e)
			{
				this.handleException(e);
			}
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);
	}

	/**
	 * 汇总楼栋的数据到分区，项目，组织上
	 * */
	public void setUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							}
						}
					}

					row.getCell(j).setValue(aimCost);

					// 均价不应汇总
					row.getCell("termAvgPrice").setValue(null);
					row.getCell("termAvgBasePrice").setValue(null);
					row.getCell("termAvgDealTotalAmount").setValue(null);
					row.getCell("termAvgSubAmount").setValue(null);
				}
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			if (row != null) {
				BuildingInfo building = (BuildingInfo) row.getUserObject();
				if (building != null) {					
					
					if(this.isSpecialBizIntoSale){
						MsgBox.showInfo(this, "明细表中不会显示特殊业务的记录！");
					}
					
					SellStatRoomRptUI.showUI(this, building.getId().toString(),
							this.beginDate, this.endDate,
							this.isPreIntoSale,this.isIncludeAttach, isShowAll);
				}
			}
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected void setActionState() {

	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * 重新置入均价=总金额/总面积
	 * 增加计算：销售欠款 = 销售总价 - 销售回款
	 * 增加计算：非定金类回款 = 销售回款-定金类回款
	 */
	private void resetUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row == null)
				return;

			// 项目上的均价不能用汇总的值,重新用总价/面积计算
			//标准均价
			if (row.getCell("termSumAmount").getValue() instanceof BigDecimal
					&& row.getCell("termSumArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termSumArea")
							.getValue()) != 0) {
				row.getCell("termAvgPrice").setValue(
						((BigDecimal) row.getCell("termSumAmount").getValue())
								.divide(((BigDecimal) row
										.getCell("termSumArea").getValue()), 2,
										BigDecimal.ROUND_HALF_UP));
			}
			//成交均价
			if (row.getCell("termDealTotalAmount").getValue() instanceof BigDecimal
					&& row.getCell("termSumArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termSumArea")
							.getValue()) != 0) {
				row.getCell("termAvgDealTotalAmount").setValue(
						((BigDecimal) row.getCell("termDealTotalAmount")
								.getValue()).divide(((BigDecimal) row.getCell(
								"termSumArea").getValue()), 2,
								BigDecimal.ROUND_HALF_UP));
			}
			//优惠均价 TODO 确认一下：该均价是用 标准均价-成交均价 计算出来的
			if (row.getCell("termAvgPrice").getValue() instanceof BigDecimal
					&& row.getCell("termAvgDealTotalAmount").getValue() instanceof BigDecimal) {
				row.getCell("termAvgSubAmount").setValue(
						((BigDecimal) row.getCell("termAvgPrice").getValue())
								.subtract((BigDecimal) row.getCell(
										"termAvgDealTotalAmount").getValue()));
			}
			//底价均价
			if (row.getCell("termSumBaseAmount").getValue() instanceof BigDecimal
					&& row.getCell("termSumArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal) row.getCell("termSumArea")
							.getValue()) != 0) {
				row.getCell("termAvgBasePrice").setValue(
						((BigDecimal) row.getCell("termSumBaseAmount")
								.getValue()).divide(((BigDecimal) row.getCell(
								"termSumArea").getValue()), 2,
								BigDecimal.ROUND_HALF_UP));
			}
			// 签约合同均价=签约合同总价/签约合同面积
			if(row.getCell("termSignTotalAmount").getValue() instanceof BigDecimal 
					&& row.getCell("termSignArea").getValue() instanceof BigDecimal
					&& FDCHelper.ZERO.compareTo((BigDecimal)row.getCell("termSignArea").getValue()) != 0){
				row.getCell("termSignAvgPrice").setValue(((BigDecimal)row.getCell("termSignTotalAmount").getValue()
						).divide((BigDecimal)row.getCell("termSignArea").getValue(), 2, BigDecimal.ROUND_HALF_UP));
			}
			
			//termSumSaleAmount termReceiveSumAmount 
			BigDecimal termSumSaleAmount = new BigDecimal(0.00);
			if(row.getCell("termSumSaleAmount").getValue()!=null && row.getCell("termSumSaleAmount").getValue() instanceof BigDecimal)
				termSumSaleAmount = (BigDecimal)row.getCell("termSumSaleAmount").getValue();
			BigDecimal termReceiveSumAmount = new BigDecimal(0.00);
			if(row.getCell("termReceiveSumAmount").getValue()!=null && row.getCell("termReceiveSumAmount").getValue() instanceof BigDecimal)
				termReceiveSumAmount = (BigDecimal)row.getCell("termReceiveSumAmount").getValue();
			row.getCell("termAreageAmount").setValue(termSumSaleAmount.subtract(termReceiveSumAmount));
			
			//非定金类回款 = 销售回款-定金类回款
			BigDecimal termPurchaseAmount = new BigDecimal(0.00);
			if(row.getCell("termPurchaseAmount").getValue()!=null && row.getCell("termPurchaseAmount").getValue() instanceof BigDecimal){
				termPurchaseAmount = (BigDecimal)row.getCell("termPurchaseAmount").getValue();
			}
			row.getCell("termUnpurchaseAmount").setValue(termReceiveSumAmount.subtract(termPurchaseAmount));
		}

	}
}