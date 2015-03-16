package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
//import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 销售顾问业绩统计表
 * @author yinshujuan
 * @see SalemanStatRptUI
 */
public class SalemanAchievementStatRptUI extends
		AbstractSalemanAchievementStatRptUI
{
	private static final long serialVersionUID = 7197252999728258330L;

	private static final Logger logger = CoreUIObject.getLogger(SalemanAchievementStatRptUI.class);

	Map rowMap = new HashMap(); // 营销单元id+用户id --> row对象
	Set muIds = new HashSet();
	Map muMap = new HashMap(); // 营销单元和销售项目的映射 营销单元id --> 项目id

	private SalemanAchievementStatRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	public SalemanAchievementStatRptUI() throws Exception
	{
		super();
		logger.info("销售顾问业绩统计表");
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

	protected String[] getLocateNames()
	{
		String[] locateNames = new String[1];
		locateNames[0] = "salesman";
		return locateNames;
	}

	private SalemanAchievementStatRptFilterUI getFilterUI()
	{
		if (this.filterUI == null)
		{
			try
			{
				this.filterUI = new SalemanAchievementStatRptFilterUI(this,
						this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e)
			{
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	public void storeFields()
	{
		super.storeFields();
	}

	public void onLoad() throws Exception
	{
//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

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
		// this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		// this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		initTable();

		fillMarketingUnit();

		// fillSalesman();
		this.refresh(null);

		// 设置可以保存样式
		tHelper = new TablePreferencesHelper(this);
	}

	private void fillMarketingUnit()
	{
		tblMain.removeRows(false);

		TreeModel marketingTree = null;
		try
		{
			marketingTree = FDCTreeHelper.getMarketTree(this.actionOnLoad,true);
		} catch (Exception e)
		{
			this.handleException(e);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) marketingTree
				.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);

		// 查询各营销单元对应的 销售项目集合
		// SELECT *, head.name, eventType.name, receptionType.name,
		// commerceChance.name WHERE commerceChance.id =
		// 'hFUMqafgTAOAfCrwM31CdquX5Yo=' ORDER BY eventDate DESC
		if (muIds.size() > 0)
		{
			String muIdStr = "";
			Iterator iter = muIds.iterator();
			while (iter.hasNext())
			{
				muIdStr += ",'" + (String) iter.next() + "'";
			}
			muIdStr = muIdStr.substring(1);

			try
			{
				String oql = "select head.id,sellProject.id where sellProject.id in (" + muIdStr + ")";
				
				MarketingUnitSellProjectCollection muColl = MarketingUnitSellProjectFactory
						.getRemoteInstance().getMarketingUnitSellProjectCollection(oql);
				for (int i = 0; i < muColl.size(); i++)
				{
					String muIdString = muColl.get(i).getHead().getId()
							.toString();
					String spIdString = muColl.get(i).getSellProject().getId()
							.toString();
					if (muMap.get(muIdString) != null)
					{
						Set proSet = (Set) muMap.get(muIdString);
						proSet.add(spIdString);
						muMap.put(muIdString, proSet);
					} else
					{
						Set proSet = new HashSet();
						proSet.add(spIdString);
						muMap.put(muIdString, proSet);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				this.abort();
			}

		}
	}

	private void fillNode(KDTable tblMain, DefaultMutableTreeNode node)
	{
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++)
		{
			space += " ";
		}
		if (node.getUserObject() instanceof UserInfo)
		{
			UserInfo user = (UserInfo) node.getUserObject();
			row.getCell("user").setValue(space + user.getNumber());
			row.getCell("salesman").setValue(space + user.getName());
			row.setUserObject(user);
			// 查找父节点的营销单元
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node
					.getParent();
			if(parentNode.getUserObject() instanceof MarketingUnitInfo){
				MarketingUnitInfo muInfo = (MarketingUnitInfo) parentNode
				.getUserObject();
				this.rowMap.put(
						muInfo.getId().toString() + user.getId().toString(), row);
			}else if(parentNode.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo muInfo = (OrgStructureInfo) parentNode
				.getUserObject();
				this.rowMap.put(
						muInfo.getId().toString() + user.getId().toString(), row);
			}
		} else
		{
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("user").setValue(space + node);
			row.getCell("salesman").setValue(space + node);
			row.setMergeable(true);
			tblMain.getMergeManager().mergeBlock(row.getRowIndex(), 0,
					row.getRowIndex(), 1);
		}

		// 如果节点是营销单元 muIds
		if (node.getUserObject() instanceof MarketingUnitInfo)
		{
			MarketingUnitInfo muInfo = (MarketingUnitInfo) node.getUserObject();
			muIds.add(muInfo.getId().toString());
		}

		if (!node.isLeaf())
		{
			for (int i = 0; i < node.getChildCount(); i++)
			{
				this.fillNode(tblMain, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	protected void execQuery()
	{
		if (tblMain.getRowCount() == 0)
		{
			fillMarketingUnit();
		}

		if (tblMain.getRowCount() == 0)
		{
			return;
		}

		try
		{
			fillData();
		} catch (Exception e)
		{
			this.handleException(e);
		}
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

	private void fillData() throws BOSException, SQLException
	{
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
			for (int j = 2; j < this.tblMain.getColumnCount(); j++)
			{
				this.tblMain.getCell(i, j).setValue(null);
			}
		}
		// 统计每个用户建立的客户数量
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fsalesmanid,FHeadID,count(*) cusCount from T_SHE_FDCCustomer fdcCus  "
						+ " left join T_TEN_MarketingUnitSellProject muSellPro on fdcCus.FProjectID = muSellPro.FSellProjectID  where 1=1 ");

		// 加入用户职位隔离------------------------
		/*
		 * UserInfo currentUserInfo = SysContext.getSysContext()
		 * .getCurrentUserInfo(); Set personIds =
		 * FDCCustomerHelper.getChildPersonIdsOfCurrentUser(); if (personIds ==
		 * null) { builder.appendSql(" and fdcCus.fsalesmanid='" +
		 * currentUserInfo.getId().toString() + "'"); } else { builder = new
		 * FDCSQLBuilder(); builder.appendSql(
		 * "select fdcCus.fsalesmanid,count(*) cusCount from T_SHE_FDCCustomer fdcCus "
		 * ); builder
		 * .appendSql("inner join T_PM_User ur on fdcCus.FSalesmanID=ur.FID ");
		 * builder.appendSql("where ur.FPersonId in ("); int tmp = 0; for
		 * (Iterator itor = personIds.iterator(); itor.hasNext();) { String
		 * personId = (String) itor.next(); if (tmp != 0) {
		 * builder.appendSql(","); } builder.appendSql("'");
		 * builder.appendSql(personId); builder.appendSql("'"); tmp++; }
		 * builder.appendSql(") "); }
		 */
		// --------------------------------------
		this.appendFilterSql(builder, "fdcCus.FBookedDate");
		builder.appendSql(" group by fdcCus.fsalesmanid , muSellPro.FHeadID");
		/*
		 * select fsalesmanid,count() cusCount from T_SHE_FDCCustomer fdcCus
		 * where 1=1 group by fdcCus.fsalesmanid
		 */
		IRowSet cusSet = builder.executeQuery();
		while (cusSet.next())
		{
			String fsalesmanid = cusSet.getString("fsalesmanid");
			String muSellProId = cusSet.getString("FHeadID");
			IRow row = (IRow) this.rowMap.get(muSellProId + fsalesmanid);
			if (row == null)
			{
				continue;
			}
			row.getCell("newCustomer").setValue(
					new Integer(cusSet.getInt("cusCount")));
		}
		builder = new FDCSQLBuilder();
		//TODO:purchaseBuildingArea 认购建筑面积  yinshujuan
		//如果认购单中的第二销售顾问为空，则业绩应属于主销售顾问，否则属于第二销售顾问
		builder.appendSql("select purchase.FSalesmanId fsalesmanid,muSellPro.FHeadID FHeadID,count(*) totalCount,sum(room.FStandardTotalAmount) standardTotalAmount ,sum(room.FDealTotalAmount) dealTotalamount,sum(room.FBuildingArea) purchaseBuildingArea "
						+ "from t_SHE_room room inner join t_she_purchase purchase on room.FLastPurchaseID=purchase.fid "
						+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID " 
						+ " left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
						+ " where room.FIsForSHE=1 AND (room.fSellState='Purchase') ");
		builder.appendSql(" AND secondSaleMan.fheadid is null");//取认购单中第二销售顾问为空的数据
		this.appendFilterSql(builder, "room.FToPurchaseDate");
		builder.appendSql(" group by purchase.FSalesmanId , muSellPro.FHeadID ");
		builder.appendSql(" union all ");
		builder.appendSql("select secondSaleMan.FSecondManID fsalesmanid,muSellPro.FHeadID FHeadID,count(*) totalCount,sum(room.FStandardTotalAmount) standardTotalAmount ,sum(room.FDealTotalAmount) dealTotalamount,sum(room.FBuildingArea) purchaseBuildingArea "
				+ "from t_SHE_room room inner join t_she_purchase purchase on room.FLastPurchaseID=purchase.fid "
				+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID " 
				+ " left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
				+ " where room.FIsForSHE=1 AND (room.fSellState='Purchase') ");
		builder.appendSql(" AND secondSaleMan.fheadid is not null");//取认购单中第二销售顾问不为空的数据
		this.appendFilterSql(builder, "room.FToPurchaseDate");
		builder.appendSql(" group by secondSaleMan.FSecondManID , muSellPro.FHeadID ");


		/*
		 * select purchase.FSalesmanId,count()
		 * totalCount,sum(FStandardTotalAmount) standardTotalAmount
		 * ,sum(FDealTotalAmount) dealTotalamount from t_SHE_room room inner
		 * join t_she_purchase purchase on room.FLastPurchaseID=purchase.fid
		 * where (fSellState='PrePurchase' or fSellState='Purchase' or
		 * fSellState='Sign') group by purchase.FSalesmanId
		 */
		cusSet = builder.executeQuery();
		while (cusSet.next())
		{
			String fsalesmanid = cusSet.getString("fsalesmanid");
			String muSellProId = cusSet.getString("FHeadID");
			IRow row = (IRow) this.rowMap.get(muSellProId + fsalesmanid);
			if (row == null)
			{
				continue;
			}

//			Integer purchaseCount = new Integer(0);
			// if(row.getCell("purchaseCount").getValue())

			row.getCell("purchaseCount").setValue((row.getCell("purchaseCount").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("purchaseCount").getValue()).add
					(cusSet.getBigDecimal("totalCount")));
			row.getCell("purchaseAmount").setValue((row.getCell("purchaseAmount").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("purchaseAmount").getValue()).add
					(cusSet.getBigDecimal("dealTotalamount")));
			row.getCell("purchaseConArea").setValue((row.getCell("purchaseConArea").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("purchaseConArea").getValue()).add
					(cusSet.getBigDecimal("purchaseBuildingArea")));//认购建筑面积
			BigDecimal purchaseBuildingArea = (BigDecimal) row.getCell("purchaseConArea").getValue(); //认购建筑面积
			BigDecimal purchaseAmount = (BigDecimal)row.getCell("purchaseAmount").getValue();//认购总价
			if(purchaseBuildingArea==null||purchaseBuildingArea.compareTo(new BigDecimal(0))==0||purchaseAmount==null||purchaseAmount.compareTo(new BigDecimal(0))==0) {
				row.getCell("purchaseAveragePrice").setValue(new BigDecimal(0.00));
			}else {
				row.getCell("purchaseAveragePrice").setValue(purchaseAmount.divide(purchaseBuildingArea,2));//认购均价
			}
		}
		//TODO:signBuildingArea签约建筑面积 yinshujuan
		builder = new FDCSQLBuilder();
		builder.appendSql("select purchase.FSalesmanId fsalesmanid,muSellPro.FHeadID FHeadID,count(*) totalCount,sum(room.FStandardTotalAmount) standardTotalAmount ,sum(room.FDealTotalAmount) dealTotalamount ,sum(room.FBuildingArea) signBuildingArea "
						+ "from t_SHE_room room inner join t_she_purchase purchase on room.FLastPurchaseID=purchase.fid "
						+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID "
						+" left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
						+ " where room.FIsForSHE=1 AND (room.fSellState='Sign') ");
		builder.appendSql(" AND secondSaleMan.fheadid is null");//取认购单中第二销售顾问为空的数据
		this.appendFilterSql(builder, "room.FToSignDate");
		builder.appendSql(" group by purchase.FSalesmanId , muSellPro.FHeadID");
		builder.appendSql(" union all ");
		builder.appendSql("select secondSaleMan.FSecondManID fsalesmanid,muSellPro.FHeadID FHeadID,count(*) totalCount,sum(room.FStandardTotalAmount) standardTotalAmount ,sum(room.FDealTotalAmount) dealTotalamount ,sum(room.FBuildingArea) signBuildingArea "
				+ "from t_SHE_room room inner join t_she_purchase purchase on room.FLastPurchaseID=purchase.fid "
				+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID "
				+" left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
				+ " where room.FIsForSHE=1 AND (room.fSellState='Sign') ");
		builder.appendSql(" AND secondSaleMan.fheadid is not null");//取认购单中第二销售顾问为空的数据
		this.appendFilterSql(builder, "room.FToSignDate");
		builder.appendSql(" group by secondSaleMan.FSecondManID  , muSellPro.FHeadID");
		/*
		 * select purchase.FSalesmanId,count()
		 * totalCount,sum(FStandardTotalAmount) standardTotalAmount
		 * ,sum(FDealTotalAmount) dealTotalamount from t_SHE_room room inner
		 * join t_she_purchase purchase on room.FLastPurchaseID=purchase.fid
		 * where (fSellState='PrePurchase' or fSellState='Purchase' or
		 * fSellState='Sign') group by purchase.FSalesmanId
		 */
		cusSet = builder.executeQuery();
		while (cusSet.next())
		{
			String fsalesmanid = cusSet.getString("fsalesmanid");
			String muSellProId = cusSet.getString("FHeadID");
			IRow row = (IRow) this.rowMap.get(muSellProId + fsalesmanid);
			if (row == null)
			{
				continue;
			}
			row.getCell("contractCount").setValue((row.getCell("contractCount").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("contractCount").getValue()).add
					(cusSet.getBigDecimal("totalCount")));
			row.getCell("contractAmount").setValue((row.getCell("contractAmount").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("contractAmount").getValue()).add
					(cusSet.getBigDecimal("dealTotalamount")));
			row.getCell("signConArea").setValue((row.getCell("signConArea").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("signConArea").getValue()).add
					(cusSet.getBigDecimal("signBuildingArea")));//签约建筑面积
			BigDecimal signArea = (BigDecimal)row.getCell("signConArea").getValue();//签约面积
			BigDecimal sigAmount = (BigDecimal)row.getCell("contractAmount").getValue();//签约总额
			if(signArea==null||signArea.compareTo(new BigDecimal(0.00))==0||sigAmount==null||sigAmount.compareTo(new BigDecimal(0.00))==0) {
				row.getCell("signAveragePrice").setValue(new BigDecimal(0.00));
			}else {
				row.getCell("signAveragePrice").setValue(sigAmount.divide(signArea,2));//签约均价
				
			}
			
		}

		builder = new FDCSQLBuilder();
		builder.appendSql("select purchase.FSalesmanid FSalesmanid,muSellPro.FHeadID FHeadID,sum(billEntry.famount) sumRevAmount from T_SHE_FDCReceiveBillEntry as billEntry  "
						+ " left join t_cas_receivingbill cas on billEntry.FReceivingBillID=cas.fid "
						+ " left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
						+ " left join t_she_Purchase purchase on  fdc.fpurchaseid=purchase.fid "
						+ " left join t_she_room as room on purchase.froomid = room.fid "
						+ " left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid "
						+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID "
						+" left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
						+ " where money.FMoneyType in ('HouseAmount','FisrtAmount','LoanAmount','AccFundAmount') and room.FSellState in ('Purchase','Sign') ");
		builder.appendSql(" AND secondSaleMan.fheadid is null");//取认购单中第二销售顾问为空的数据
		this.appendFilterSql(builder, "cas.FBizDate");
		builder.appendSql(" group by purchase.FSalesmanid , muSellPro.FHeadID");
		builder.appendSql(" union all ");
		builder.appendSql("select secondSaleMan.FSecondManID FSalesmanid,muSellPro.FHeadID FHeadID,sum(billEntry.famount) sumRevAmount from T_SHE_FDCReceiveBillEntry as billEntry  "
				+ " left join t_cas_receivingbill cas on billEntry.FReceivingBillID=cas.fid "
				+ " left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
				+ " left join t_she_Purchase purchase on  fdc.fpurchaseid=purchase.fid "
				+ " left join t_she_room as room on purchase.froomid = room.fid "
				+ " left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid "
				+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID "
				+" left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
				+ " where money.FMoneyType in ('HouseAmount','FisrtAmount','LoanAmount','AccFundAmount') and room.FSellState in ('Purchase','Sign') ");
		builder.appendSql(" AND secondSaleMan.fheadid is not null");//取认购单中第二销售顾问不为空的数据
		this.appendFilterSql(builder, "cas.FBizDate");
		builder.appendSql(" group by secondSaleMan.FSecondManID , muSellPro.FHeadID");

		/*
		 * select FSalesmanid,sum(cas.famount) sumRevAmount from
		 * t_cas_receivingbill cas left join t_she_fdcreceivebill fdc on
		 * cas.FFDCReceivebillid=fdc.fid left join t_she_Purchase purchase on
		 * fdc.fpurchaseid=purchase.fid left join t_she_moneyDefine money on
		 * fdc.FMoneyDefineId=money.fid where money.FMoneyType in(
		 * 'PreMoney','PurchaseAmount','HouseAmount','FisrtAmount','Refundment','LoanAmount','AccFundAmount')group
		 * by FSalesmanid
		 */
		IRowSet termReceiveSet = builder.executeQuery();
		while (termReceiveSet.next())
		{
			String buildingId = termReceiveSet.getString("FSalesmanid");
			String muSellProId = termReceiveSet.getString("FHeadID");
			IRow row = (IRow) this.rowMap.get(muSellProId + buildingId);
			if (row == null)
			{
				continue;
			}
			BigDecimal sumRevAmount = termReceiveSet
					.getBigDecimal("sumRevAmount");
			row.getCell("receiveAmount").setValue((row.getCell("receiveAmount").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("receiveAmount").getValue()).add
					(sumRevAmount));
		}

		builder = new FDCSQLBuilder();

		builder
		// .appendSql("select FSalesmanid,muSellPro.FHeadID,sum(cas.famount) sumRevAmount from t_cas_receivingbill cas "
				.appendSql("select purchase.FSalesmanid FSalesmanid,muSellPro.FHeadID FHeadID,sum(billEntry.famount) sumRevAmount from T_SHE_FDCReceiveBillEntry as billEntry  "
						+ " left join t_cas_receivingbill cas on billEntry.FReceivingBillID=cas.fid "
						+ " left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
						+ " left join t_she_Purchase purchase on  fdc.fpurchaseid=purchase.fid "
						+ " left join t_she_room as room on purchase.froomid = room.fid "
						+ " left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid "
						+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID "
						+" left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
						+ " where money.FMoneyType in ('PreconcertMoney','PurchaseAmount') and room.FSellState in ('Purchase','Sign') ");
		builder.appendSql(" AND secondSaleMan.fheadid is null");//取认购单中第二销售顾问为空的数据
		this.appendFilterSql(builder, "cas.FBizDate");
		builder.appendSql(" group by purchase.FSalesmanid , muSellPro.FHeadID");
		builder.appendSql(" union all ");
		builder
		// .appendSql("select FSalesmanid,muSellPro.FHeadID,sum(cas.famount) sumRevAmount from t_cas_receivingbill cas "
				.appendSql("select secondSaleMan.FSecondManID FSalesmanid,muSellPro.FHeadID FHeadID,sum(billEntry.famount) sumRevAmount from T_SHE_FDCReceiveBillEntry as billEntry  "
						+ " left join t_cas_receivingbill cas on billEntry.FReceivingBillID=cas.fid "
						+ " left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "
						+ " left join t_she_Purchase purchase on  fdc.fpurchaseid=purchase.fid "
						+ " left join t_she_room as room on purchase.froomid = room.fid "
						+ " left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid "
						+ " left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID "
						+" left join T_SHE_PurchaseSaleMan secondSaleMan on purchase.fid = secondSaleMan.fheadid"
						+ " where money.FMoneyType in ('PreconcertMoney','PurchaseAmount') and room.FSellState in ('Purchase','Sign') ");
		builder.appendSql(" AND secondSaleMan.fheadid is not null");//取认购单中第二销售顾问不为空的数据
		this.appendFilterSql(builder, "cas.FBizDate");
		builder.appendSql(" group by secondSaleMan.FSecondManID , muSellPro.FHeadID");
		
		/*
		 * select FSalesmanid,sum(cas.famount) sumRevAmount from
		 * t_cas_receivingbill cas left join t_she_fdcreceivebill fdc on
		 * cas.FFDCReceivebillid=fdc.fid left join t_she_Purchase purchase on
		 * fdc.fpurchaseid=purchase.fid left join t_she_moneyDefine money on
		 * fdc.FMoneyDefineId=money.fid where money.FMoneyType in(
		 * 'PreMoney','PurchaseAmount','HouseAmount','FisrtAmount','Refundment','LoanAmount','AccFundAmount')group
		 * by FSalesmanid
		 */
		IRowSet termEarnestSet = builder.executeQuery();
		while (termEarnestSet.next())
		{
			String buildingId = termEarnestSet.getString("FSalesmanid");
			String muSellProId = termEarnestSet.getString("FHeadID");
			IRow row = (IRow) this.rowMap.get(muSellProId + buildingId);
			if (row == null)
			{
				continue;
			}
			BigDecimal sumRevAmount = termEarnestSet
					.getBigDecimal("sumRevAmount");
			row.getCell("earnestAmount").setValue((row.getCell("earnestAmount").getValue()==null?new BigDecimal(0):(BigDecimal)row.getCell("earnestAmount").getValue()).add
					(sumRevAmount));
		}

		setUnionData();
	}

	protected boolean initDefaultFilter()
	{
		return true;
	}

	protected void checkTableParsed()
	{
		tblMain.checkParsed();
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e)
	{
	}

	private void initTable()
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getColumn("newCustomer").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("newCustomer").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("purchaseAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("purchaseAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("purchaseCount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("purchaseCount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		tblMain.getColumn("contractAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("contractAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("contractCount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("contractCount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		tblMain.getColumn("receiveAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("receiveAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("earnestAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("earnestAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		for (int i = 1; i < 7; i++)
		{
			this.tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
	}

	public void loadFields()
	{
		super.loadFields();
	}

	/**
	 * @deprecated
	 * */
	protected void fillSalesman() throws BOSException, EASBizException,
			SQLException
	{
		this.tblMain.removeRows();
		PersonCollection persons = PersonFactory.getRemoteInstance()
				.getPersonCollection();
		Set idSet = new HashSet();
		for (int i = 0; i < persons.size(); i++)
		{
			idSet.add(persons.get(i).getId().toString());
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("person.*");
		FilterInfo filter = new FilterInfo();

		// ----加入职位过滤------
		UserInfo currentUserInfo = SysContext.getSysContext()
				.getCurrentUserInfo();
		Set personIds = FDCCustomerHelper.getChildPersonIdsOfCurrentUser();
		if (personIds == null)
		{
			filter.getFilterItems()
					.add(
							new FilterItemInfo("id", currentUserInfo.getId()
									.toString()));
		} else
		{
			filter.getFilterItems().add(
					new FilterItemInfo("person.id", personIds,
							CompareType.INCLUDE));
		}

		// ------------------

		view.setFilter(filter);
		// filter.getFilterItems().add(new FilterItemInfo("type",
		// UserType.OTHER));
		// filter.getFilterItems().add(new
		// FilterItemInfo("agentUser",Boolean.FALSE));
		// filter.setMaskString("(#0 or #1) and #2");
		// filter.setMaskString("#0 and #1");
		UserCollection users = UserFactory.getRemoteInstance()
				.getUserCollection(view);

		// this.tblMain.checkParsed();
		for (int i = 0; i < users.size(); i++)
		{
			UserInfo user = users.get(i);
			IRow row = this.tblMain.addRow();
			// if (user.getPerson() != null) {
			// row.getCell("salesmans").setValue(user.getPerson().getName());
			// }
			row.getCell("user").setValue(user.getNumber());
			row.getCell("salesman").setValue(user.getName());
			row.setUserObject(user);
			this.rowMap.put(user.getId().toString(), row);
		}

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
					if(j==tblMain.getColumnIndex("purchaseAveragePrice")||j==tblMain.getColumnIndex("signAveragePrice"))continue;//TODO:认购均价和签约均价不能汇总
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
				//TODO:设置认购均价和签约均价的汇总行 yinshujuan
				BigDecimal purchaseTotalAmount = (BigDecimal) row.getCell("purchaseAmount").getValue();  //认购总额
				BigDecimal purchaseBuildingArea = (BigDecimal) row.getCell("purchaseConArea").getValue(); //认购建筑总面积
				BigDecimal signTotalAmount = (BigDecimal) row.getCell("contractAmount").getValue();  //签约总额
				BigDecimal signBuildingArea = (BigDecimal) row.getCell("signConArea").getValue(); //签约建筑总面积
				BigDecimal purchaseAveragePrice = null; //认购均价
				if(purchaseBuildingArea==null||purchaseBuildingArea.compareTo(new BigDecimal(0))==0||purchaseTotalAmount==null) {
					purchaseAveragePrice = new BigDecimal(0.00);
				}else {
					purchaseAveragePrice = purchaseTotalAmount.divide(purchaseBuildingArea, 2);
				}
				BigDecimal signAveragePrice = null;//签约均价
				if(signBuildingArea==null||signBuildingArea.compareTo(new BigDecimal(0))==0||signTotalAmount==null) {
					signAveragePrice = new BigDecimal(0.00);
				}else {
					signAveragePrice = signTotalAmount.divide(signBuildingArea, 2);
				}
				row.getCell("purchaseAveragePrice").setValue(purchaseAveragePrice);
				row.getCell("signAveragePrice").setValue(signAveragePrice);
				
				
			}
		}
	}

	protected void setActionState()
	{

	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		// super.tblMain_tableClicked(e);
		// if (e.getButton() == 1 && e.getClickCount() == 2) {
		// int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		// IRow row = this.tblMain.getRow(rowIndex);
		// if (row != null) {
		// BuildingInfo building = (BuildingInfo) row.getUserObject();
		// if (building != null) {
		// SellStatRoomRptUI.showUI(this, building.getId().toString());
		// }
		// }
		// }
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