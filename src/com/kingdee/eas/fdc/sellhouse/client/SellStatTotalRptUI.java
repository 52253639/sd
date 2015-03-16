/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTBasicUI;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.QueryPanelCollection;
import com.kingdee.eas.base.commonquery.QueryPanelInfo;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.XMLBean;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.ProductTypeSellStateFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SellStatTotalRptFacadeFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SellStatTotalRptUI extends AbstractSellStatTotalRptUI {
	private static final Logger logger = CoreUIObject
	.getLogger(SellStatTotalRptUI.class);
	private Map rowMap = new HashMap();

	private SellStatTotalRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	private boolean isPreIntoSale = false;
	private boolean isIncludeAttach = false;

	private final String key_allRoom = "allRoom";
	private final String key_waitRoom = "waitRoom";
	private final String key_noOrder = "noOrder";
	private final String key_orderRoom = "orderRoom";
	private final String key_orderNoSell = "orderNoSell";
	private final String key_keepRoom = "keepRoom";
	private final String key_preRoom = "preRoom";
	private final String key_purchase = "purchase";
	private final String key_signRoom = "signRoom";
	private final String key_sellRoom = "sellRoom";
	private final String key_preMoney = "preMoney";
	private final String key_noPreMoney = "noPreMoney";
	private final String key_LoanAmount = "LoanAmount";
	private final String key_AccFundAmount = "AccFundAmount";
	private final String key_noRoomMoney = "noRoomMoney";
	private final String key_huikuanMoney = "huikuanMoney";

	/**
	 * output class constructor
	 */
	public SellStatTotalRptUI() throws Exception {
		super();
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

	private SellStatTotalRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SellStatTotalRptFilterUI(this,
						this.actionOnLoad);
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
	
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}

	protected void execQuery() {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		int indexNum = this.tblMain.getColumn("name").getColumnIndex();
		this.tblMain.getViewManager().setFreezeView(-1, indexNum + 1);
		try {
			fillProjectAndBuilding();

			// 获取报表的过滤参数表
			Map paramMap = getRptParamMap();

			// 获取报表的相关数据
			Map rptDataMap = SellStatTotalRptFacadeFactory.getRemoteInstance()
					.getSellStatData(paramMap);

			// 根据获取的数据填充表格
			fillRptDataToTable(rptDataMap);
		} catch (Exception e) {
			super.handUIException(e);
		}
	}

	//
	protected void fillRptDataToTable(Map resultMap) {
		Map rptDataMapParam = (Map) resultMap.get("roomData");
		// 认购签约和已售中合同总价成交总价和优惠总价
		Map amountMap = (Map) resultMap.get("contracData");
		// 已签约补差金额
		Map compentMap = (Map) resultMap.get("compentAmount");
		// 应收已收款项信息
		Map moneyMap = (Map) resultMap.get("moneyData");
		// 认购房源中合同总价成交总价优惠总价
		Map purchaseAmountMap = (Map) amountMap.get("purchaseAmount");
		// 签约房源中合同总价成交总价优惠总价
		Map signAmountMap = (Map) amountMap.get("signAmount");
		// 已售房源中合同总价成交总价优惠总价
		Map sellRoomAmountMap = (Map) amountMap.get("sellRoomAmount");

		//结转定金
		Map jiezhuanpreMoneyMap = (Map) moneyMap.get("jiezhuanMoney");
		// 定金类款项
		Map preMoneyMap = (Map) moneyMap.get("preMoney");
		// 非定金类款项
		Map noPreMoneyMap = (Map) moneyMap.get("noPreMoney");
		// 按揭类款项
		Map LoanAmountMap = (Map) moneyMap.get("LoanAmount");
		// 公积金类款项
		Map AccFundAmountMap = (Map) moneyMap.get("AccFundAmount");
		// 非房款类
		Map noRoomMoneyMap = (Map) moneyMap.get("noRoomMoney");
		// 回款
		Map huikuanMap = (Map) moneyMap.get("huikuanMoney");
		// 总房源
		Map allRoomMap = (Map) rptDataMapParam.get("allRoom");
		// 未售房源
		Map waitRoomMap = (Map) rptDataMapParam.get("waitRoom");
		// 未推盘房源
		Map noOrderMap = (Map) rptDataMapParam.get("noOrder");
		// 已推盘房源
		Map orderRoomMap = (Map) rptDataMapParam.get("orderRoom");
		// 已推盘未售房源
		Map orderNoSellMap = (Map) rptDataMapParam.get("orderNoSell");
		// 已保留房源
		Map keepRoomMap = (Map) rptDataMapParam.get("keepRoom");
		// 已预定房源
		Map preRoomMap = (Map) rptDataMapParam.get("preRoom");
		// 已认购房源
		Map purchaseMap = (Map) rptDataMapParam.get("purchase");
		// 已签约房源
		Map signRoomMap = (Map) rptDataMapParam.get("signRoom");
		// 已售房源
		Map sellRoomMap = (Map) rptDataMapParam.get("sellRoom");
		fillTable(allRoomMap, "allRoom");
		fillTable(waitRoomMap, "waitRoom");
		fillTable(noOrderMap, "noOrder");
		fillTable(orderRoomMap, "orderRoom");
		fillTable(orderNoSellMap, "orderNoSell");
		fillTable(keepRoomMap, "keepRoom");
		//如果未勾选中预定计入统计的话隐藏已预定房源
		if(isPreIntoSale())
		{
			fillTable(preRoomMap, "preRoom");
//			this.tblMain.getColumn("sellAvgSubAmount").getStyleAttributes().setHided(true);
//			this.tblMain.getColumn("sellSubAmount").getStyleAttributes().setHided(true);
		}else
		{
			this.tblMain.getColumn("preCount").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("preArea").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("preAvgAmount").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("preAmount").getStyleAttributes().setHided(true);
			
		}
		
		fillTable(purchaseMap, "purchase");
		fillTable(signRoomMap, "signRoom");
		fillTable(sellRoomMap, "sellRoom");
		fillContactTable(purchaseAmountMap, "purchase");
		fillContactTable(signAmountMap, "signRoom");
		fillContactTable(sellRoomAmountMap, "sellRoom");
		fillCompentTable(compentMap);
		fillMoneyTable(jiezhuanpreMoneyMap, "jiezhuanMoney");
		fillMoneyTable(preMoneyMap, "preMoney");
		fillMoneyTable(noPreMoneyMap, "noPreMoney");
		fillMoneyTable(LoanAmountMap, "LoanAmount");
		fillMoneyTable(AccFundAmountMap, "AccFundAmount");
		fillMoneyTable(noRoomMoneyMap, "noRoomMoney");
		fillMoneyTable(huikuanMap, "huikuanMoney");
		setUnionData();
		
		/**
		 * 重新设置销售率，来解决销售率叠加的问题
		 * update by renliang at 2011-1-12
		 */
		setSellRate();
	}

	private void setSellRate() {
		IRow row  = this.tblMain.getRow(0);
		if(row!=null){
			if(row.getCell(0).getValue()!=null
				&&SysContext.getSysContext().getCurrentOrgUnit()!=null
				&&SysContext.getSysContext().getCurrentOrgUnit().getName()!=null
				&&row.getCell(0).getValue().equals(SysContext.getSysContext().getCurrentOrgUnit().getName())){
				BigDecimal sellSumCount = (BigDecimal)row.getCell("sellSumCount").getValue();
				sellSumCount = sellSumCount == null
						|| sellSumCount.compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO	: sellSumCount;
				BigDecimal allCount =  (BigDecimal)row.getCell("sumCount").getValue();
				allCount = allCount == null
						|| allCount.compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ONE : allCount;
				row.getCell("saleRate").setValue(
						sellSumCount.multiply(new BigDecimal(100)).divide(
								allCount, 2, BigDecimal.ROUND_HALF_UP));
			}
		}
	}

	// 应收已收款项明细
	protected void fillMoneyTable(Map moneyMap, String str) {
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = this.rowMap.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			if (moneyMap.get(buildIdSr) != null) {
				Object[] tmpObject = (Object[]) moneyMap.get(buildIdSr);
				IRow row = (IRow) this.rowMap.get(buildIdSr);
				if (row == null)
					continue;
				if (key_preMoney.equals(str)) {
					row.getCell("appPreAmount").setValue(tmpObject[0]);
					row.getCell("actPreAmount").setValue(tmpObject[1]);
					BigDecimal appPreAmount = (BigDecimal) tmpObject[0];
					appPreAmount = appPreAmount == null ? new BigDecimal(0)
							: appPreAmount;
					BigDecimal actPreAmount = (BigDecimal) tmpObject[1];
					actPreAmount = actPreAmount == null ? new BigDecimal(0)
							: actPreAmount;
					row.getCell("qianKuanPreAmount").setValue(
							appPreAmount.subtract(actPreAmount));
				} else if (key_noPreMoney.equals(str)) {
					row.getCell("appNoPreAmount").setValue(tmpObject[0]);
					row.getCell("actNoPreAmount").setValue(tmpObject[1]);
					BigDecimal appPreAmount = (BigDecimal) tmpObject[0];
					appPreAmount = appPreAmount == null ? new BigDecimal(0)
							: appPreAmount;
					BigDecimal actPreAmount = (BigDecimal) tmpObject[1];
					actPreAmount = actPreAmount == null ? new BigDecimal(0)
							: actPreAmount;
					row.getCell("qiankuanNoPreAmount").setValue(
							appPreAmount.subtract(actPreAmount));
				} else if (key_LoanAmount.equals(str)) {
					row.getCell("appAnJieAmount").setValue(tmpObject[0]);
					row.getCell("actAnJieAmount").setValue(tmpObject[1]);
					BigDecimal appPreAmount = (BigDecimal) tmpObject[0];
					appPreAmount = appPreAmount == null ? new BigDecimal(0)
							: appPreAmount;
					BigDecimal actPreAmount = (BigDecimal) tmpObject[1];
					actPreAmount = actPreAmount == null ? new BigDecimal(0)
							: actPreAmount;
					row.getCell("qiankuanAnJieAmount").setValue(
							appPreAmount.subtract(actPreAmount));
				} else if (key_AccFundAmount.equals(str)) {
					row.getCell("appGongjijinAmount").setValue(tmpObject[0]);
					row.getCell("actGongjijinAmount").setValue(tmpObject[1]);
					BigDecimal appPreAmount = (BigDecimal) tmpObject[0];
					appPreAmount = appPreAmount == null ? new BigDecimal(0)
							: appPreAmount;
					BigDecimal actPreAmount = (BigDecimal) tmpObject[1];
					actPreAmount = actPreAmount == null ? new BigDecimal(0)
							: actPreAmount;
					row.getCell("qiankuanGongjijinAmount").setValue(
							appPreAmount.subtract(actPreAmount));
				} else if (key_noRoomMoney.equals(str)) {
					row.getCell("appNoRoomAmount").setValue(tmpObject[0]);
					row.getCell("actNoRoomAmount").setValue(tmpObject[1]);
					BigDecimal appPreAmount = (BigDecimal) tmpObject[0];
					appPreAmount = appPreAmount == null ? new BigDecimal(0)
							: appPreAmount;
					BigDecimal actPreAmount = (BigDecimal) tmpObject[1];
					actPreAmount = actPreAmount == null ? new BigDecimal(0)
							: actPreAmount;
					row.getCell("qiankuanNoRoomAmount").setValue(
							appPreAmount.subtract(actPreAmount));
				} else if (key_huikuanMoney.equals(str)) {
//					row.getCell("actSumAmount").setValue(tmpObject[1]);
					BigDecimal appPreAmount = (BigDecimal) tmpObject[0];
					appPreAmount = appPreAmount == null ? new BigDecimal(0)
							: appPreAmount;
					BigDecimal actPreAmount = (BigDecimal) tmpObject[1];
					actPreAmount = actPreAmount == null ? new BigDecimal(0)
							: actPreAmount;
					BigDecimal compentAmount = (BigDecimal)row.getCell("actCompentAmount").getValue();
					compentAmount = CRMHelper.getBigDecimal(compentAmount);
					actPreAmount = actPreAmount.add(compentAmount);
					row.getCell("actSumAmount").setValue(actPreAmount);
					BigDecimal appSumAmount = new BigDecimal(0);
					if(row.getCell("appSumAmount").getValue()!=null)
						 appSumAmount = (BigDecimal)row.getCell("appSumAmount").getValue();
					row.getCell("qiankuanSumAmount").setValue(
							appSumAmount.subtract(actPreAmount));
				} else if("jiezhuanMoney".equals(str))
				{
					row.getCell("jiezhuanPreAmount").setValue(tmpObject[1]);
				}
			}
		}
	}

	// 已签约补差金额及应收补差和已经补差
	protected void fillCompentTable(Map compentMap) {
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = this.rowMap.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			if (compentMap.get(buildIdSr) != null) {
				Object[] tmpObject = (Object[]) compentMap.get(buildIdSr);
				IRow row = (IRow) this.rowMap.get(buildIdSr);
				BigDecimal compentAmount = (BigDecimal) tmpObject[0];
				compentAmount = compentAmount == null ? new BigDecimal(0)
						: compentAmount;
				row.getCell("sellAreaCompensateAmount").setValue(compentAmount);
				row.getCell("appCompentAmount").setValue(compentAmount);
				BigDecimal compentRevAmount = (BigDecimal) tmpObject[1];
				compentRevAmount = compentRevAmount == null ? new BigDecimal(0)
						: compentRevAmount;
				row.getCell("actCompentAmount").setValue(compentRevAmount);
				row.getCell("qiankuanCompentAmount").setValue(
						compentAmount.subtract(compentRevAmount));
				BigDecimal contractAmount = (BigDecimal) row.getCell(
						"sellSumContractAmount").getValue();
				contractAmount = contractAmount == null ? new BigDecimal(0)
						: contractAmount;
				row.getCell("sellAmount").setValue(
						compentAmount.add(contractAmount));
			}
		}
	}

	// 合同总价成交总价优惠总价
	protected void fillContactTable(Map map, String str) {
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = this.rowMap.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			if (map.get(buildIdSr) != null) {
				Object[] tmpObject = (Object[]) map.get(buildIdSr);
				IRow row = (IRow) this.rowMap.get(buildIdSr);
				if (row == null)
					continue;
				if (key_purchase.equals(str)) {
					BigDecimal area = tmpObject[3] == null
							|| ((BigDecimal) tmpObject[3])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(
							1)
							: (BigDecimal) tmpObject[3];
					BigDecimal contractAmount = tmpObject[0] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[0];
					BigDecimal dealAmount = tmpObject[1] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[1];
					BigDecimal youhuiAmount = tmpObject[2] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[2];
					row.getCell("purContractAmount").setValue(tmpObject[0]);
					row.getCell("purDealAmount").setValue(tmpObject[1]);
					row.getCell("purYouHuiAmount").setValue(tmpObject[2]);
					row.getCell("purContractAvgAmount").setValue(
							contractAmount.divide(area, 2,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("purDealAvgAmount").setValue(
							dealAmount
									.divide(area, 2, BigDecimal.ROUND_HALF_UP));
					row.getCell("purYouHuiAvgAmount").setValue(
							youhuiAmount.divide(area, 2,
									BigDecimal.ROUND_HALF_UP));
				} else if (key_signRoom.equals(str)) {
					BigDecimal area = tmpObject[3] == null
							|| ((BigDecimal) tmpObject[3])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(
							1)
							: (BigDecimal) tmpObject[3];
					BigDecimal contractAmount = tmpObject[0] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[0];
					BigDecimal dealAmount = tmpObject[1] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[1];
					BigDecimal youhuiAmount = tmpObject[2] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[2];
					row.getCell("signContractAmount").setValue(tmpObject[0]);
					row.getCell("signDealAmount").setValue(tmpObject[1]);
					row.getCell("signYouHuiAmount").setValue(tmpObject[2]);
					row.getCell("signContractAvgAmount").setValue(
							contractAmount.divide(area, 2,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("signDealAvgAmount").setValue(
							dealAmount
									.divide(area, 2, BigDecimal.ROUND_HALF_UP));
					row.getCell("signYouHuiAvgAmount").setValue(
							youhuiAmount.divide(area, 2,
									BigDecimal.ROUND_HALF_UP));
				} else if (key_sellRoom.equals(str)) {
					BigDecimal area = tmpObject[3] == null
							|| ((BigDecimal) tmpObject[3])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(
							1)
							: (BigDecimal) tmpObject[3];
					BigDecimal contractAmount = tmpObject[0] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[0];
					BigDecimal dealAmount = tmpObject[1] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[1];
					BigDecimal youhuiAmount = tmpObject[2] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[2];
					BigDecimal baseAmount = tmpObject[4] == null ? new BigDecimal(
							0)
							: (BigDecimal) tmpObject[4];
					row.getCell("sellSumContractAmount").setValue(tmpObject[0]);
					row.getCell("sellAmount").setValue(tmpObject[0]);
					row.getCell("appSumAmount").setValue(tmpObject[0]);
					row.getCell("sellSumDealAmount").setValue(tmpObject[1]);
					row.getCell("sellSubAmount").setValue(tmpObject[2]);
					row.getCell("sellSumBaseAmount").setValue(tmpObject[4]);
					row.getCell("sellContractAvgAmount").setValue(
							contractAmount.divide(area, 2,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("sellAvgDealAmount").setValue(
							dealAmount
									.divide(area, 2, BigDecimal.ROUND_HALF_UP));
					row.getCell("sellAvgSubAmount").setValue(
							youhuiAmount.divide(area, 2,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("sellAvgBasePrice").setValue(
							baseAmount
									.divide(area, 2, BigDecimal.ROUND_HALF_UP));
					BigDecimal sellSumCount = new BigDecimal(((Integer) row
							.getCell("sellSumCount").getValue()).intValue());
					sellSumCount = sellSumCount == null
							|| sellSumCount.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(
							0)
							: sellSumCount;
					BigDecimal allCount = new BigDecimal(((Integer) row
							.getCell("sumCount").getValue()).intValue());
					allCount = allCount == null
							|| allCount.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(
							1)
							: allCount;
					row.getCell("saleRate").setValue(
							sellSumCount.multiply(new BigDecimal(100)).divide(
									allCount, 2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
	}
	
	//如果面积为空的话实际均价应该为0
	 protected BigDecimal getAvgAmount(BigDecimal allAmount,BigDecimal allRoomArea)
	 {
		 if(allRoomArea.compareTo(new BigDecimal(0))==0)
		 {
			 return new BigDecimal(0);
		 }else
		 {
			 return allAmount.divide(allRoomArea, 2,
						BigDecimal.ROUND_HALF_UP);
		 }
	 }

	// 套数面积标准总价和标准均价
	protected void fillTable(Map map, String str) {
		if (this.rowMap == null || this.rowMap.size() == 0)
			return;
		Iterator iter = this.rowMap.keySet().iterator();
		while (iter.hasNext()) {
			String buildIdSr = (String) iter.next();
			if (map.get(buildIdSr) != null) {
				Object[] tmpObject = (Object[]) map.get(buildIdSr);
				IRow row = (IRow) this.rowMap.get(buildIdSr);
				if (row == null)
					continue;
				if (key_allRoom.equals(str)) {
					row.getCell("sumCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("sumArea").setValue(tmpObject[1]);
					row.getCell("avgPrice").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("sumAmount").setValue(tmpObject[2]);
					row.getCell("sumAmount").setUserObject(tmpObject[2]);
				} else if (key_waitRoom.equals(str)) {
					row.getCell("noSellSumCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("noSellSumArea").setValue(tmpObject[1]);
					row.getCell("noSellAvgPrice").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("noSellSumAmount").setValue(tmpObject[2]);
				} else if (key_noOrder.equals(str)) {
					row.getCell("unShowCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("unShowArea").setValue(tmpObject[1]);
					row.getCell("unShowAvgAmount").setValue(getAvgAmount(allAmount, allRoomArea));					
					row.getCell("unshowStandAmount").setValue(tmpObject[2]);
				} else if (key_orderRoom.equals(str)) {
					row.getCell("showCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("showArea").setValue(tmpObject[1]);
					row.getCell("showAvgAmount").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("showStandAmount").setValue(tmpObject[2]);
				} else if (key_orderNoSell.equals(str)) {
					row.getCell("showNoSellCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("showNoSellArea").setValue(tmpObject[1]);
					row.getCell("showNoSellAvgAmount").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("showNoSellStandAmount").setValue(tmpObject[2]);
				} else if (key_keepRoom.equals(str)) {
					row.getCell("keepCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("keepArea").setValue(tmpObject[1]);
					row.getCell("keepAvgAmount").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("keepAmount").setValue(tmpObject[2]);
				} else if (key_preRoom.equals(str)) {
					row.getCell("preCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("preArea").setValue(tmpObject[1]);
					row.getCell("preAvgAmount").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("preAmount").setValue(tmpObject[2]);
				} else if (key_purchase.equals(str)) {
					row.getCell("purCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("purArea").setValue(tmpObject[1]);
					row.getCell("purAvaAmount").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("purAmount").setValue(tmpObject[2]);
				} else if (key_signRoom.equals(str)) {
					row.getCell("signCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("signArea").setValue(tmpObject[1]);
					row.getCell("signAvgAmount").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("signAmount").setValue(tmpObject[2]);
				} else if (key_sellRoom.equals(str)) {
					row.getCell("sellSumCount").setValue(tmpObject[0]);
					BigDecimal allRoomArea = tmpObject[1] == null
							|| ((BigDecimal) tmpObject[1])
									.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
							: (BigDecimal) tmpObject[1];
					BigDecimal allAmount = tmpObject[2] == null ? new BigDecimal(0)
							: (BigDecimal) tmpObject[2];
					row.getCell("sellSumArea").setValue(tmpObject[1]);
					row.getCell("sellAvgPrice").setValue(getAvgAmount(allAmount, allRoomArea));
					row.getCell("sellStandAmount").setValue(tmpObject[2]);
				}

			}
		}
	}

	protected Map getRptParamMap() throws Exception {
		/**
		 * paramMap 描述： IncludeAttachment = 包括附属房产，为真是包括，为假时不包括 BuildArea =
		 * 表示面积类型，为真是建筑面积，为假时为套内面积 PreArea = 表示面积类型，为真是预测面积，为假时为实测面积
		 * IncludeOrder = 包括预订业务， 为真是包括，为假时不包括
		 */
		Map paramMap = new HashMap();
		paramMap.put("IncludeAttachment", Boolean.valueOf(isIncludeAttach()));
		paramMap.put("BuildArea", Boolean.valueOf(isBuildArea()));
		paramMap.put("PreArea", Boolean.valueOf(isPreArea()));
		paramMap.put("IncludeOrder", Boolean.valueOf(isPreIntoSale()));

		return paramMap;
	}

	protected void checkTableParsed() {
		tblMain.checkParsed();
	}

	public void onShow() throws Exception {
		super.onShow();

		int indexNum = this.tblMain.getColumn("name").getColumnIndex();
		this.tblMain.getViewManager().setFreezeView(-1, indexNum + 1);
	}

	public void onLoad() throws Exception {
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
	}

	protected String[] getLocateNames() {
		String[] locateNames = new String[1];
		locateNames[0] = "name";
		return locateNames;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	private boolean isIncludeAttach() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		return this.getFilterUI().isIncludeAttach(para);
	}

	private boolean isPreIntoSale() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		return this.getFilterUI().isPreIntoSale(para);
	}

	/**
	 * 如果为真则为建筑面积，如果为假则为套内面积
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean isBuildArea() throws Exception {
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		return this.getFilterUI().isBuildArea(param);
	}

	/**
	 * 如果为真则为预测面积，如果为假则为实测面积
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean isPreArea() throws Exception {
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		return this.getFilterUI().isPreArea(param);
	}

	protected boolean initDefaultFilter() {
		return true;
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

	private void fillProjectAndBuilding() throws BOSException, EASBizException,
			SQLException {
		tblMain.removeRows();
		TreeModel buildingTree = null;
		try {
			buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,
					MoneySysTypeEnum.SalehouseSys,SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit());
		} catch (Exception e) {
			this.handleException(e);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree
				.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);
	}

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
					
					// 均价汇总

					if ("sumAmount".equals(this.tblMain.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "sumAmount", "sumArea",
								"avgPrice");
					} else if ("noSellSumAmount".equals(this.tblMain.getColumn(
							j).getKey())) {
						setAvgAmount(row, aimCost, "noSellSumAmount",
								"noSellSumArea", "noSellAvgPrice");
					} else if ("unshowStandAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "unshowStandAmount",
								"unShowArea", "unShowAvgAmount");
					} else if ("showStandAmount".equals(this.tblMain.getColumn(
							j).getKey())) {
						setAvgAmount(row, aimCost, "showStandAmount",
								"showArea", "showAvgAmount");
					} else if ("showNoSellStandAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "showNoSellStandAmount",
								"showNoSellArea", "showNoSellAvgAmount");
					} else if ("keepAmount".equals(this.tblMain.getColumn(j)
							.getKey())) {
						setAvgAmount(row, aimCost, "keepAmount", "keepArea",
								"keepAvgAmount");
					} else if ("preAmount".equals(this.tblMain.getColumn(j)
							.getKey())) {
						setAvgAmount(row, aimCost, "preAmount", "preArea",
								"preAvgAmount");
					} else if ("purAmount".equals(this.tblMain.getColumn(j)
							.getKey())) {
						setAvgAmount(row, aimCost, "purAmount", "purArea",
								"purAvaAmount");
					} else if ("purYouHuiAmount".equals(this.tblMain.getColumn(
							j).getKey())) {
						setAvgAmount(row, aimCost, "purYouHuiAmount",
								"purArea", "purYouHuiAvgAmount");
					} else if ("purDealAmount".equals(this.tblMain.getColumn(j)
							.getKey())) {
						setAvgAmount(row, aimCost, "purDealAmount", "purArea",
								"purDealAvgAmount");
					} else if ("purContractAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "purContractAmount",
								"purArea", "purContractAvgAmount");
					} else if ("signAmount".equals(this.tblMain.getColumn(j)
							.getKey())) {
						setAvgAmount(row, aimCost, "signAmount", "signArea",
								"signAvgAmount");
					} else if ("signYouHuiAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "signYouHuiAmount",
								"signArea", "signYouHuiAvgAmount");
					} else if ("signDealAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "signDealAmount",
								"signArea", "signDealAvgAmount");
					} else if ("signContractAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "signContractAmount",
								"signArea", "signContractAvgAmount");
					} else if ("sellStandAmount".equals(this.tblMain.getColumn(
							j).getKey())) {
						setAvgAmount(row, aimCost, "sellStandAmount",
								"sellSumArea", "sellAvgPrice");
					} else if ("sellSumBaseAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "sellSumBaseAmount",
								"sellSumArea", "sellAvgBasePrice");
					} else if ("sellSubAmount".equals(this.tblMain.getColumn(j)
							.getKey())) {
						setAvgAmount(row, aimCost, "sellSubAmount",
								"sellSumArea", "sellAvgSubAmount");
					} else if ("sellSumDealAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "sellSumDealAmount",
								"sellSumArea", "sellAvgDealAmount");
					} else if ("sellSumContractAmount".equals(this.tblMain
							.getColumn(j).getKey())) {
						setAvgAmount(row, aimCost, "sellSumContractAmount",
								"sellSumArea", "sellContractAvgAmount");
					}

				}
			}
		}
	}

	private void setAvgAmount(IRow row, BigDecimal aimCost, String amount,
			String area, String avgAmount) {
		// if(amount.equals(this.tblMain.getColumn(j).getKey()))
		// {
		aimCost = aimCost==null?new BigDecimal(0):aimCost;
		BigDecimal sumArea = (BigDecimal) row.getCell(area).getValue();
		sumArea = sumArea == null || sumArea.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(1):sumArea;
		BigDecimal contractAvgAmount = aimCost.divide(sumArea, 2,
				BigDecimal.ROUND_HALF_UP);
		row.getCell(avgAmount).setValue(contractAvgAmount);
		// }
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			if (row != null) {
				BuildingInfo building = (BuildingInfo) row.getUserObject();
				if (building != null) {

					FDCCustomerParams para = new FDCCustomerParams(this
							.getFilterUI().getCustomerParams());

//					SellStatRoomRptUI.showUI(this, building.getId().toString(),
//							null, null, this.isPreIntoSale,
//							this.isIncludeAttach, true);
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
}