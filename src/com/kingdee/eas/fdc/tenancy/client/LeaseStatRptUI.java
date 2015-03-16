/**
 * 房间租赁统计表
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.client.ContractFullFilterUI;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class LeaseStatRptUI extends AbstractLeaseStatRptUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(LeaseStatRptUI.class);

	Map rowMap = new HashMap();

	private LeaseStatRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	/**
	 * output class constructor
	 */
	public LeaseStatRptUI() throws Exception
	{
		super();
	}

	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e)
	{
		// super.tblMain_doRequestRowSet(e);
	}

	private LeaseStatRptFilterUI getFilterUI()
	{
		if (this.filterUI == null)
		{
			try
			{
				this.filterUI = new LeaseStatRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e)
			{
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	protected void execQuery()
	{
		try
		{
			fillData();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Date beginDate = DateTimeUtils.truncateDate(this.getBeginQueryDate());
		Date endDate = DateTimeUtils.truncateDate(this.getEndQueryDate());
		if (beginDate == null || endDate == null)
		{
			this.tblMain.getHeadRow(0).getCell(18).setValue("查询区间数据(全部显示)");
		} else
		{
			Calendar cal = new GregorianCalendar();
			cal.setTime(endDate);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
			DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
			String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "到"
					+ FORMAT_DAY.format(cal.getTime()) + ")";
			this.tblMain.getHeadRow(0).getCell(18).setValue(des);
		}
	}

	protected void checkTableParsed()
	{
		tblMain.checkParsed();
	}

	public void onLoad() throws Exception
	{
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
		initTable();
		fillProjectAndBuilding();
		this.refresh(null);
	}

	protected String[] getLocateNames()
	{
		String[] locateNames = new String[1];
		locateNames[0] = "name";
		return locateNames;
	}

	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	private void fillData() throws BOSException, SQLException
	{
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
			for (int j = 1; j < this.tblMain.getColumnCount(); j++)
			{
				this.tblMain.getCell(i, j).setValue(null);
			}
		}
		String sellSql = "select FBuildingId,count(*) totalCount,sum(FBuildingArea) buildingArea,sum(FStandardTotalAmount) standardTotalAmount ,sum(FDealTotalAmount) dealTotalamount, "
				+ "sum(FAreaCompensateAmount) sellAreaCompensateAmount,sum(FDealTotalAmount) sellSumPrice "
				+ "from t_SHE_room where fSellState='PrePurchase' or fSellState='Purchase' or fSellState='Sign' "
				+ "group by FBuildingId";
		IRowSet sellSet = SQLExecutorFactory.getRemoteInstance(sellSql)
				.executeSQL();
		while (sellSet.next())
		{
			String buildingId = sellSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null)
			{
				continue;
			}
			row.getCell("sellSumCount").setValue(
					new Integer(sellSet.getInt("totalCount")));
			BigDecimal sellArea = sellSet.getBigDecimal("buildingArea");
			row.getCell("sellSumArea").setValue(sellArea);
			BigDecimal sellSumAmount = sellSet
					.getBigDecimal("standardTotalAmount");
			row.getCell("sellSumAmount").setValue(sellSumAmount);
			BigDecimal dealAmount = sellSet.getBigDecimal("dealTotalamount");
			row.getCell("sellSumDealAmount").setValue(dealAmount);
			BigDecimal areaCompensateAmount = sellSet
					.getBigDecimal("sellAreaCompensateAmount");
			row.getCell("sellAreaCompensateAmount").setValue(
					areaCompensateAmount);
			if (dealAmount == null)
			{
				dealAmount = FDCHelper.ZERO;
			}
			if (areaCompensateAmount == null)
			{
				areaCompensateAmount = FDCHelper.ZERO;
			}
			BigDecimal sellSumPrice = dealAmount.add(areaCompensateAmount);
			row.getCell("sellSumPrice").setValue(sellSumPrice);
			if (sellArea == null)
			{
				sellArea = FDCHelper.ZERO;
			}
			if (sellSumAmount == null)
			{
				sellSumAmount = FDCHelper.ZERO;
			}
			row.getCell("sellAvgPrice")
					.setValue(
							sellSumAmount.divide(sellArea, 2,
									BigDecimal.ROUND_HALF_UP));
//			if(dealAmount==null){
//				dealAmount = FDCHelper.ZERO;
//			}

		
			row.getCell("sellSubAmount").setValue(
					sellSumAmount.subtract(dealAmount));
		}

		String allSql = "select FBuildingId,count(*) totalCount,sum(FBuildingArea) buildingArea,sum(FStandardTotalAmount) standardTotalAmount "
				+ "from t_SHE_room FBuildingId group by FBuildingId";
		IRowSet allSet = SQLExecutorFactory.getRemoteInstance(allSql)
				.executeSQL();
		while (allSet.next())
		{
			String buildingId = allSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null)
			{
				continue;
			}
			Integer totalCount = new Integer(allSet.getInt("totalCount"));
			row.getCell("sumCount").setValue(totalCount);
			BigDecimal area = allSet.getBigDecimal("buildingArea");
			row.getCell("sumArea").setValue(area);
			BigDecimal sumAmount = allSet.getBigDecimal("standardTotalAmount");
			row.getCell("sumAmount").setValue(sumAmount);
			if (area == null)
			{
				area = FDCHelper.ZERO;
			}
			if (sumAmount == null)
			{
				sumAmount = FDCHelper.ZERO;
			}
			if (area.compareTo(FDCHelper.ZERO) != 0)
			{
				row.getCell("avgPrice").setValue(
						sumAmount.divide(area, 2, BigDecimal.ROUND_HALF_UP));
			}
			Integer sellCount = (Integer) row.getCell("sellSumCount")
					.getValue();
			if (sellCount == null)
			{
				sellCount = new Integer(0);
			}
			row.getCell("noSellSumCount").setValue(
					new Integer(totalCount.intValue() - sellCount.intValue()));
			BigDecimal sellArea = (BigDecimal) row.getCell("sellSumArea")
					.getValue();
			if (sellArea == null)
			{
				sellArea = FDCHelper.ZERO;
			}
			BigDecimal noSellArea = area.subtract(sellArea);
			row.getCell("noSellSumArea").setValue(noSellArea);
			BigDecimal sellSumAmount = (BigDecimal) row
					.getCell("sellSumAmount").getValue();
			if (sellSumAmount == null)
			{
				sellSumAmount = FDCHelper.ZERO;
			}
			BigDecimal noSellSumAmount = sumAmount.subtract(sellSumAmount);
			row.getCell("noSellSumAmount").setValue(noSellSumAmount);
			if (noSellArea.compareTo(FDCHelper.ZERO) != 0)
			{
				row.getCell("noSellAvgPrice").setValue(
						noSellSumAmount.divide(noSellArea, 2,
								BigDecimal.ROUND_HALF_UP));
			}
		}
		/*
		 * String allReceiveSql = "select FBuildingId,sum(famount) sumRevAmount
		 * from t_cas_receivingbill cas " + "left join t_she_fdcreceivebill fdc
		 * on cas.FFDCReceivebillid=fdc.fid " + "left join t_she_room room on
		 * fdc.froomid=room.fid " + "left join t_she_building building on
		 * room.fbuildingid=building.fid " + "left join t_she_moneyDefine money
		 * on fdc.FMoneyDefineId=money.fid " + "where money.FMoneyType in
		 * ('PreMoney','PurchaseAmount','HouseAmount','FisrtAmount','Refundment','LoanAmount','AccFundAmount')" +
		 * "group by fbuildingid ";
		 */
		String allReceiveSql = "select FBuildingId,sum(cas.famount) sumRevAmount from t_cas_receivingbill cas "
				+ "left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
				+ "left join t_she_room room on fdc.froomid=room.fid "
				+ "left join T_SHE_Purchase as purchase on room.fid= purchase.froomid "
				+ "left join t_she_building building on  room.fbuildingid=building.fid "
				+ "left join t_she_moneyDefine money on  fdc.FMoneyDefineId=money.fid "
				// + "where money.FMoneyType in
				// ('PreMoney','PurchaseAmount','HouseAmount','FisrtAmount','Refundment','LoanAmount','AccFundAmount')"
				+ "where money.FMoneyType in ('PurchaseAmount','HouseAmount','FisrtAmount','CompensateAmount','LoanAmount','AccFundAmount')"
				+ " and purchase.FPurchaseState not in('ChangeRoomBlankOut','QuitRoomBlankOut','NoPayBlankOut','ManualBlankOut','AdjustBlankOut') "
				+ " and fdc.fpurchaseid=purchase.fid "
				+ "group by fbuildingid ";

		IRowSet allReceiveSet = SQLExecutorFactory.getRemoteInstance(
				allReceiveSql).executeSQL();
		while (allReceiveSet.next())
		{
			String buildingId = allReceiveSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null)
			{
				continue;
			}
			BigDecimal sumRevAmount = allReceiveSet
					.getBigDecimal("sumRevAmount");
			row.getCell("receiveSumAmount").setValue(sumRevAmount);
		}

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select FBuildingId,count(*) totalCount,sum(FBuildingArea) buildingArea,"
						+ "sum(FStandardTotalAmount) standardTotalAmount ,sum(FDealTotalAmount) dealTotalamount,"
						+ "sum(FDealTotalAmount) dealTotalAmount ,sum(FAreaCompensateAmount) areaCompensateAmount "
						+ "from t_she_room where (fSellState='PrePurchase' or fSellState='Purchase' or fSellState='Sign')");

		this.appendFilterSql(builder, "FToPurchaseDate");
		builder.appendSql("group by fbuildingid ");
		IRowSet termSellSet = builder.executeQuery();
		while (termSellSet.next())
		{
			String buildingId = termSellSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null)
			{
				continue;
			}
			row.getCell("termSumCount").setValue(
					new Integer(termSellSet.getInt("totalCount")));
			BigDecimal termSellArea = termSellSet.getBigDecimal("buildingArea");
			row.getCell("termSumArea").setValue(termSellArea);
			BigDecimal termSellSumAmount = termSellSet
					.getBigDecimal("standardTotalAmount");
			row.getCell("termSumAmount").setValue(termSellSumAmount);

			BigDecimal termDealTotalAmount = termSellSet
					.getBigDecimal("dealTotalAmount");
			row.getCell("termDealTotalAmount").setValue(termDealTotalAmount);
			BigDecimal termAreaCompensateAmount = termSellSet
					.getBigDecimal("areaCompensateAmount");
			row.getCell("termAreaCompensateAmount").setValue(
					termAreaCompensateAmount);
			if (termDealTotalAmount == null)
			{
				termDealTotalAmount = FDCHelper.ZERO;
			}
			if (termAreaCompensateAmount == null)
			{
				termAreaCompensateAmount = FDCHelper.ZERO;
			}
			// BigDecimal termDealAmount = termSellSet
			// .getBigDecimal("dealTotalamount");
			BigDecimal dealAmount = termDealTotalAmount
					.add(termAreaCompensateAmount);
			BigDecimal termDealAmount = termSellSet
					.getBigDecimal("dealTotalamount");
			row.getCell("termSumDealAmount").setValue(dealAmount);
			if (termSellArea == null)
			{
				termSellArea = FDCHelper.ZERO;
			}
			if (termSellSumAmount == null)
			{
				termSellSumAmount = FDCHelper.ZERO;
			}
			if (termSellArea.compareTo(FDCHelper.ZERO) != 0)
			{
				row.getCell("termAvgPrice").setValue(
						termSellSumAmount.divide(termSellArea, 2,
								BigDecimal.ROUND_HALF_UP));
			}
			if (termDealAmount == null)
			{
				termDealAmount = FDCHelper.ZERO;
			}

			row.getCell("termSubAmount").setValue(
					termSellSumAmount.subtract(termDealAmount));
		}
		builder = new FDCSQLBuilder();
		/*
		 * builder.appendSql("select fbuildingid,sum(cas.famount) sumRevAmount
		 * from t_cas_receivingbill cas " + "left join t_she_fdcreceivebill fdc
		 * on cas.FFDCReceivebillid=fdc.fid " + "left join t_she_room room on
		 * fdc.froomid=room.fid " + "left join t_she_building building on
		 * room.fbuildingid=building.fid " + "left join t_she_moneyDefine money
		 * on fdc.FMoneyDefineId=money.fid " + "where money.FMoneyType in
		 * ('PreMoney','PurchaseAmount','HouseAmount','FisrtAmount','Refundment','LoanAmount','AccFundAmount')");
		 */
		builder
				.appendSql("select fbuildingid,sum(cas.famount) sumRevAmount from t_cas_receivingbill cas "
						+ "left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
						+ "left join t_she_room room on fdc.froomid=room.fid "
						+ "left join T_SHE_Purchase as purchase on room.fid= purchase.froomid "
						+ "left join t_she_building building on  room.fbuildingid=building.fid "
						+ "left join t_she_moneyDefine money on  fdc.FMoneyDefineId=money.fid "
						// + "where money.FMoneyType in
						// ('PreMoney','PurchaseAmount','HouseAmount','FisrtAmount','Refundment','LoanAmount','AccFundAmount')"
						+ "where money.FMoneyType in ('PurchaseAmount','HouseAmount','FisrtAmount','CompensateAmount','LoanAmount','AccFundAmount')"
						+ " and purchase.FPurchaseState not in('ChangeRoomBlankOut','QuitRoomBlankOut','NoPayBlankOut','ManualBlankOut','AdjustBlankOut') "
						+ " and fdc.fpurchaseid=purchase.fid ");

		this.appendFilterSql(builder, "cas.FBizDate");
		builder.appendSql("group by fbuildingid ");
		IRowSet termReceiveSet = builder.executeQuery();
		while (termReceiveSet.next())
		{
			String buildingId = termReceiveSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null)
			{
				continue;
			}
			BigDecimal sumRevAmount = termReceiveSet
					.getBigDecimal("sumRevAmount");
			row.getCell("termReceiveSumAmount").setValue(sumRevAmount);
		}
		setUnionData();
	}

	public void appendFilterSql(FDCSQLBuilder builder, String proName)
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll"))
		{

			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null)
			{
				builder.appendSql(" and " + proName + ">= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null)
			{
				builder.appendSql(" and " + proName + "< ? ");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
			}
		}
	}

	private Date getEndQueryDate()
	{
		FilterInfo filter = this.mainQuery.getFilter();
		if (filter != null)
		{
			FilterItemCollection items = filter.getFilterItems();
			for (int i = 0; i < items.size(); i++)
			{
				FilterItemInfo item = items.get(i);
				String expression = item.getCompareExpression();
				if (expression.equals("<"))
				{
					Date date = (Date) item.getCompareValue();
					return date;
				}
			}
		} else
		{
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
					.getCustomerParams());
			return this.getFilterUI().getBeginQueryDate(para);
		}
		return null;
	}

	protected boolean initDefaultFilter()
	{
		return true;
	}

	private Date getBeginQueryDate()
	{
		FilterInfo filter = this.mainQuery.getFilter();
		if (filter != null)
		{
			FilterItemCollection items = filter.getFilterItems();
			for (int i = 0; i < items.size(); i++)
			{
				FilterItemInfo item = items.get(i);
				String expression = item.getCompareExpression();
				if (expression.equals(">="))
				{
					Date date = (Date) item.getCompareValue();
					return date;
				}
			}
		} else
		{
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
					.getCustomerParams());
			return this.getFilterUI().getEndQueryDate(para);
		}

		return null;
	}

	private void initTable()
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		for (int i = 1; i < this.tblMain.getColumnCount(); i++)
		{
			tblMain.getColumn(i).getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
		}
		tblMain.getColumn("sumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("sellSumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("noSellSumCount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termSumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		// for (int i = 0; i < 16; i++) {
		// this.tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		// }
		// this.tblMain.getHeadMergeManager().mergeBlock(0, 16, 0, 22);
	}

	public void loadFields()
	{
		super.loadFields();
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException
	{
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++)
		{
			space += " ";
		}
		if (node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + building.getName());
			row.setUserObject(node.getUserObject());
			this.rowMap.put(building.getId().toString(), row);
		} else
		{
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf())
		{
			for (int i = 0; i < node.getChildCount(); i++)
			{
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	private void fillProjectAndBuilding() throws BOSException, EASBizException,
			SQLException
	{
		tblMain.removeRows();
		TreeModel buildingTree = null;
		try
		{
			buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree
				.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);
	}

	public void setUnionData()
	{
		for (int i = 0; i < tblMain.getRowCount(); i++)
		{
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() == null)
			{
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++)
				{
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level)
					{
						break;
					}
					if (rowAfter.getUserObject() != null)
					{
						rowList.add(rowAfter);
					}
				}
				for (int j = 1; j < this.tblMain.getColumnCount(); j++)
				{
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++)
					{
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null)
						{
							if (value instanceof BigDecimal)
							{
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer)
							{
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							}
						}
					}
					row.getCell(j).setValue(aimCost);
				}
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		if (e.getButton() == 1 && e.getClickCount() == 2)
		{
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			if (row != null)
			{
				BuildingInfo building = (BuildingInfo) row.getUserObject();
				if (building != null)
				{
					LeaseDetailStatRptUI.showUI(this, building.getId().toString(),
							this.getBeginQueryDate(), this.getEndQueryDate());
				}
			}
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionRefresh_actionPerformed(e);
	}

	protected String getEditUIName()
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return null;
	}

	protected void setActionState()
	{

	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}
}