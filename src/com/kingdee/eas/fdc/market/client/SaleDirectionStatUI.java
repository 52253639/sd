/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BillAdjustCollection;
import com.kingdee.eas.fdc.sellhouse.BillAdjustFactory;
import com.kingdee.eas.fdc.sellhouse.BillAdjustInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;

public class SaleDirectionStatUI extends AbstractSaleDirectionStatUI {
	private static final Logger logger = CoreUIObject.getLogger(SaleDirectionStatUI.class);

	private SaleDirectionFilterUI filterUI = null;

	private Map buildingMap = null;

	private boolean chkIsShowSpecial = false;
	private boolean chkIsShowPre = false;

	private RoomDisplaySetting setting = new RoomDisplaySetting();

	public SaleDirectionStatUI() throws Exception {
		super();
	}

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
	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	private SaleDirectionFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SaleDirectionFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.menuEdit.setVisible(false);

		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	public void onShow() throws Exception {
		super.onShow();

		int indexNum = this.tblMain.getColumn("name").getColumnIndex();
		this.tblMain.getViewManager().setFreezeView(-1, indexNum + 1);
	}

	protected void execQuery() {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

		int indexNum = this.tblMain.getColumn("name").getColumnIndex();
		this.tblMain.getViewManager().setFreezeView(-1, indexNum + 1);

		// 构建楼栋数
		if (buildingMap == null || this.tblMain.getRowCount() == 0) {
			this.tblMain.removeRows();
			initTableBuildingTree();
		}
		this.tblMain.getColumn("contractCount").setWidth(100);
		this.tblMain.getColumn("contractCount").getStyleAttributes().setNumberFormat("%r-[ ]0.0n");
		this.tblMain.getColumn("allCratCount").setWidth(100);
		this.tblMain.getColumn("allCratCount").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
		this.tblMain.getColumn("yearCratCount").setWidth(150);
		this.tblMain.getColumn("yearCratCount").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
		this.tblMain.getColumn("preCratCount").setWidth(150);
		this.tblMain.getColumn("preCratCount").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
		this.tblMain.getColumn("durCratCount").setWidth(250);
		this.tblMain.getColumn("durCratCount").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");

		// 清除 日 统计列
		int durColIndex = this.tblMain.getColumnIndex("durCratCount");
		int allColCount = this.tblMain.getColumnCount();
		for (int i = allColCount; i >= durColIndex + 1; i--) {
			this.tblMain.removeColumn(i);
		}

		// 擦除所有数据
		clearAllDate();

		if (buildingMap == null)
			return;

		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());

		// 作用于房间上的 转认购时间 和 转销售时间 //预定业务进入销售统计 否toPurchaseDate 是toSaleDate
		// chkIsShowPre
		Date beginDate = new Date(((Timestamp) filterMap.get("beginDate")).getTime());
		Date selectEndDate = new Date(((Timestamp) filterMap.get("endDate")).getTime()); // 所选择的结束时间的开始时间
		Object specialObject = filterMap.get("chkIsShowSpecial");
		Object preObject = filterMap.get("chkIsShowPre");
		chkIsShowSpecial = ((Integer) specialObject).intValue() > 0 ? true : false;
		chkIsShowPre = ((Integer) preObject).intValue() > 0 ? true : false;

		/**
		 * 调试 if(beginDate!=null && endDate!=null && sellState==null) {
		 * System.out.println("************"+beginDate+","+endDate);
		 * System.out.println("******"+roomCollection.size()+"******"); for(int
		 * i=0;i<roomCollection.size();i++){
		 * System.out.println(roomCollection.get
		 * (i).getNumber()+","+roomCollection.get(i).getToSaleDate() + "," +
		 * roomCollection.get(i).getToPurchaseDate()+","+roomCollection.get(i).
		 * getLastPurchase().getContractTotalAmount()); }
		 * System.out.println("************"); }
		 */

		Date endDate = this.getNextDayBeginTime(selectEndDate); // 所选择的结束时间下一天的开始时间
		BillAdjustCollection adjustColl = null;
		QuitRoomCollection quitRoomColl = null;
		PurchaseChangeCollection changeColl = null;
		ChangeRoomCollection changeRoomColl = null;
		try {

			Map conttractResult = getBuildContractCountResult(null, null, null);
			if (conttractResult == null)
				return;
			fillColumnDateByResultMap(conttractResult, "contractCount");

			// 填充'累计合同总价' allCratCount getTheQueryResult
			Map allResult = getTheQueryResult(null, null, null);
			if (allResult == null)
				return;
			fillColumnDateByResultMap(allResult, "allCratCount");

			// 填充'本年累计合同总价' yearCratCount --对于特殊业务,退房和变更 特殊处理
			Date yearBeginDate = FDCDateHelper.getFirstYearDate(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(yearBeginDate);
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			Date yearEndDate = calendar.getTime();
			Map yearResult = getTheQueryResult(yearBeginDate, yearEndDate, null);
			if (yearResult != null) {
				if (chkIsShowSpecial) {
					quitRoomColl = this.getQuitRoomCollection(yearBeginDate, yearEndDate);
					adjustColl = this.getAdjustRoomCollection(yearBeginDate, yearEndDate);
					changeColl = this.getPurchaseChangeCollection(yearBeginDate, yearEndDate);
					changeRoomColl = this.getChangeRoomCollection(yearBeginDate, yearEndDate);

					/*
					 * 调试 System.out.println("退房*****"); for(int
					 * i=0;i<quitRoomColl.size();i++) {
					 * 
					 * QuitRoomInfo testInfo = quitRoomColl.get(i);
					 * System.out.println
					 * (testInfo.getRoom().getBuilding().getName() + ","+
					 * testInfo.getRoom().getNumber() +","+
					 * testInfo.getPurchase().getToPurchaseDate() + "," +
					 * testInfo.getPurchase().getToSaleDate() +","+
					 * testInfo.getQuitDate() + "," +
					 * testInfo.getPurchase().getContractTotalAmount() ); }
					 * System.out.println("变更*****"); for(int
					 * i=0;i<changeColl.size();i++) { PurchaseChangeInfo
					 * testInfo = changeColl.get(i);
					 * System.out.println(testInfo.
					 * getPurchase().getRoom().getBuilding().getName() + ","+
					 * testInfo.getPurchase().getRoom().getNumber() +","+
					 * testInfo.getPurchase().getToPurchaseDate() + "," +
					 * testInfo.getPurchase().getToSaleDate() +","+
					 * testInfo.getChangeDate() + "," +
					 * testInfo.getNewContractAmount() + "," +
					 * testInfo.getOldContractAmount() ); }
					 */

					this.dealForTheQuitOrChangeRoom(yearResult, quitRoomColl, adjustColl, changeColl, yearBeginDate, yearEndDate);
					this.dealForTheChangeRoom(yearResult, changeRoomColl, yearBeginDate, yearEndDate);
					quitRoomColl = null;
					changeColl = null;
				}
				fillColumnDateByResultMap(yearResult, "yearCratCount");
			}

			// 填充'累计预定合同总价' preCratCount
			Map preResult = getTheQueryResult(null, null, RoomSellStateEnum.PrePurchase);
			if (preResult != null)
				fillColumnDateByResultMap(preResult, "preCratCount");

			// '区间累计合同总价 (***~*)' durCratCount 如果区间的为空，则可以直接返回 --对于特殊业务,退房和变更
			// 特殊处理
			String durCratHeadName = "区间累计合同总价\r\n" + "(" + FDCDateHelper.formatDate(beginDate) + "-" + FDCDateHelper.formatDate(selectEndDate) + "合计)";
			this.tblMain.getHeadRow(0).getCell("durCratCount").setValue(durCratHeadName);
			RoomCollection roomColl = this.getRoomCollection(beginDate, endDate, null);

			Map durResult = getTheQueryResult(roomColl, null);
			if (durResult != null)
				fillColumnDateByResultMap(durResult, "durCratCount");

			// 填充 日 统计列
			Date tempBeginDate = beginDate;
			int dayColumnIndex = 1;
			while (tempBeginDate.before(endDate)) {
				IColumn addColumn = this.tblMain.addColumn();
				addColumn.setKey("dayCratCount" + dayColumnIndex);
				// 设置显示样式
				addColumn.getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
				addColumn.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

				this.tblMain.getHeadRow(0).getCell("dayCratCount" + dayColumnIndex).setValue(FDCDateHelper.formatDate2(tempBeginDate));

				Map dayResulst = this.getDayQueryResult(roomColl, tempBeginDate, getNextDayBeginTime(tempBeginDate)); // getTheQueryResult
				// (
				// tempBeginDate
				// ,
				// getNaxtDayBeginTime
				// (
				// tempBeginDate
				// )
				// ,
				// null
				// )
				// ;

				if (dayResulst != null)
					fillColumnDateByResultMap(dayResulst, "dayCratCount" + dayColumnIndex);

				dayColumnIndex++;
				tempBeginDate = getNextDayBeginTime(tempBeginDate); // 指定日期的下一天
			}
			// 每日的特殊处理有点不同，需要直接操作每日的列中的数据 ,因此和区间的情况一起处理
			if (chkIsShowSpecial) {
				quitRoomColl = this.getQuitRoomCollection(beginDate, endDate);
				changeColl = this.getPurchaseChangeCollection(beginDate, endDate);
				adjustColl = this.getAdjustRoomCollection(yearBeginDate, yearEndDate);
				this.dealForTheQuitOrChangeRoomForDur(quitRoomColl, adjustColl, changeColl, beginDate, endDate);
			}

		} catch (BOSException e) {
			e.printStackTrace();
			this.handleException(e);
			this.abort();
		}

		setUnionData();
	}

	private Date getNextDayBeginTime(Date date) { // 指定时间一天的下一天开始时间
		if (date == null)
			return new Date();

		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 根据楼栋树构建表体中的销售项目
	 */
	private void initTableBuildingTree() {
		try {
			TreeModel thisTree = SHEHelper.getBuildingTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys);
			DefaultMutableTreeNode thisRoot = (DefaultMutableTreeNode) thisTree.getRoot();
			buildingMap = new HashMap();
			fillNode(this.tblMain, thisRoot);
			this.tblMain.getTreeColumn().setDepth(thisRoot.getDepth() + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo thisInfo = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + thisInfo.getName());
			row.setUserObject(node.getUserObject());
			this.buildingMap.put(thisInfo.getId().toString(), row);
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

	/**
	 * 查询统计结果
	 * 
	 * @param beginDate
	 *            查询开始时间 可为空
	 * @param endDate
	 *            查询结束时间 可为空
	 * @param sellState
	 *            房间的销售状态 可为空 PrePurchase 预定 ； Purchase 认购 ； Sign 签约
	 * @return Map 楼栋的id --> 合同总价合计 (BigDecimal)
	 */
	private Map getTheQueryResult(Date beginDate, Date endDate, RoomSellStateEnum sellState) throws BOSException {
		if (buildingMap == null)
			return null;

		RoomCollection roomCollection = getRoomCollection(beginDate, endDate, sellState);

		return getTheQueryResult(roomCollection, sellState);
	}

	/**
	 * 查询统计结果
	 * 
	 * @param beginDate
	 *            查询开始时间 可为空
	 * @param endDate
	 *            查询结束时间 可为空
	 * @param sellState
	 *            房间的销售状态 可为空 PrePurchase 预定 ； Purchase 认购 ； Sign 签约
	 * @return Map 楼栋的id --> 合同总价合计 (BigDecimal)
	 */
	private Map getBuildContractCountResult(Date beginDate, Date endDate, RoomSellStateEnum sellState) throws BOSException {
		if (buildingMap == null)
			return null;

		RoomCollection roomCollection = getRoomCollection(beginDate, endDate, sellState);

		return getContractCountResult(roomCollection, sellState);
	}

	private Map getTheQueryResult(RoomCollection roomCollection, RoomSellStateEnum sellState) throws BOSException {
		if (buildingMap == null)
			return null;

		Map resultMap = new HashMap();
		if (roomCollection == null)
			return resultMap;
		for (int i = 0; i < roomCollection.size(); i++) {
			RoomInfo roomInfo = roomCollection.get(i);
			if (roomInfo.getBuilding() != null && roomInfo.getLastPurchase() != null) {
				String buildIdStr = roomInfo.getBuilding().getId().toString();
				BigDecimal ctrtAmount = roomInfo.getLastPurchase().getContractTotalAmount();
				if (ctrtAmount == null)
					ctrtAmount = new BigDecimal("0");
				if (resultMap.get(buildIdStr) == null) {
					resultMap.put(buildIdStr, ctrtAmount);
				} else {
					BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
					resultMap.put(buildIdStr, oldCount.add(ctrtAmount));
				}
			}
		}
		return resultMap;
	}

	private Map getContractCountResult(RoomCollection roomCollection, RoomSellStateEnum sellState) throws BOSException {
		if (buildingMap == null)
			return null;

		Map resultMap = new HashMap();
		if (roomCollection == null)
			return resultMap;
		for (int i = 0; i < roomCollection.size(); i++) {
			RoomInfo roomInfo = roomCollection.get(i);
			if (roomInfo.getBuilding() != null && roomInfo.getLastPurchase() != null) {
				String buildIdStr = roomInfo.getBuilding().getId().toString();
				BigDecimal contractCount = (BigDecimal) resultMap.get(buildIdStr);
				if (contractCount == null) {
					contractCount = FDCHelper.ZERO;
				}
				resultMap.put(buildIdStr, contractCount.add(FDCHelper.toBigDecimal("1")));
			}
		}
		return resultMap;
	}

	private void dealForTheQuitOrChangeRoomForDur(QuitRoomCollection quitRoomColl, BillAdjustCollection adjustColl, PurchaseChangeCollection changeColl, Date beginDate, Date endDate) {
		dealForTheQuitOrChangeRoom(null, quitRoomColl, adjustColl, changeColl, beginDate, endDate);
	}

	private void dealForTheChangeRoom(Map resultMap, ChangeRoomCollection changeRoomColl, Date beginDate, Date endDate) {
		// 换房的特殊处理
		if (changeRoomColl != null) {
			for (int i = 0; i < changeRoomColl.size(); i++) {
				ChangeRoomInfo changeRoomInfo = changeRoomColl.get(i);
				String buildIdStr = changeRoomInfo.getOldRoom().getBuilding().getId().toString();
				BigDecimal ctrlAmount = changeRoomInfo.getOldPurchase().getContractTotalAmount();
				if ((chkIsShowPre && changeRoomInfo.getOldPurchase().getToSaleDate() != null && changeRoomInfo.getOldPurchase().getToSaleDate().compareTo(beginDate) >= 0 && changeRoomInfo
						.getOldPurchase().getToSaleDate().compareTo(endDate) < 0)
						|| (!chkIsShowPre && changeRoomInfo.getOldPurchase().getToPurchaseDate() != null && changeRoomInfo.getOldPurchase().getToPurchaseDate().compareTo(beginDate) >= 0 && changeRoomInfo
								.getOldPurchase().getToPurchaseDate().compareTo(endDate) < 0)) {
					if (resultMap != null) {
						if (resultMap.get(buildIdStr) == null) {
							resultMap.put(buildIdStr, ctrlAmount);
						} else {
							BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
							resultMap.put(buildIdStr, oldCount.add(ctrlAmount));
						}
					} else {
						modifyDurAndDayDataByChangeRoom(changeRoomInfo, buildIdStr, ctrlAmount, beginDate, endDate, "Purchase");
					}
				}

				// 如果预定进入销售统计,必须算上退房业务;否则不能转认购时间不能为空
				if (chkIsShowPre || (!chkIsShowPre && changeRoomInfo.getOldPurchase().getToPurchaseDate() != null)) {
					Date changeDate = changeRoomInfo.getChangeDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						changeDate = changeRoomInfo.getAuditTime();
					}
					if (changeDate != null && changeDate.compareTo(beginDate) >= 0 && changeDate.compareTo(endDate) < 0) {
						if (resultMap != null) {
							if (resultMap.get(buildIdStr) == null) {
								resultMap.put(buildIdStr, ctrlAmount.negate());
							} else {
								BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
								resultMap.put(buildIdStr, oldCount.add(ctrlAmount.negate()));
							}
						} else {
							modifyDurAndDayDataByChangeRoom(changeRoomInfo, buildIdStr, ctrlAmount.negate(), beginDate, endDate, "ChangeDate");
						}
					}
				}
			}
		}
	}

	// 在之前数据映射结果上处理退房或变更房间的情况
	// resultMap 如果为空代表区间和按天的特殊处理，直接修改表体中的数据 durCratCount dayColumnIndex
	private void dealForTheQuitOrChangeRoom(Map resultMap, QuitRoomCollection quitRoomColl, BillAdjustCollection adjustColl, PurchaseChangeCollection changeColl, Date beginDate, Date endDate) {
		// 退房的特殊处理
		if (quitRoomColl != null) {
			for (int i = 0; i < quitRoomColl.size(); i++) {
				QuitRoomInfo quitInfo = quitRoomColl.get(i);
				String buildIdStr = quitInfo.getRoom().getBuilding().getId().toString();
				BigDecimal ctrlAmount = quitInfo.getPurchase().getContractTotalAmount();
				if ((chkIsShowPre && quitInfo.getPurchase().getToSaleDate() != null && quitInfo.getPurchase().getToSaleDate().compareTo(beginDate) >= 0 && quitInfo.getPurchase().getToSaleDate()
						.compareTo(endDate) < 0)
						|| (!chkIsShowPre && quitInfo.getPurchase().getToPurchaseDate() != null && quitInfo.getPurchase().getToPurchaseDate().compareTo(beginDate) >= 0 && quitInfo.getPurchase()
								.getToPurchaseDate().compareTo(endDate) < 0)) {
					if (resultMap != null) {
						if (resultMap.get(buildIdStr) == null) {
							resultMap.put(buildIdStr, ctrlAmount);
						} else {
							BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
							resultMap.put(buildIdStr, oldCount.add(ctrlAmount));
						}
					} else {
						modifyDurAndDayDataByQuitRoom(quitInfo, buildIdStr, ctrlAmount, beginDate, endDate, "Purchase");
					}
				}

				// 如果预定进入销售统计,必须算上退房业务;否则不能转认购时间不能为空
				if (chkIsShowPre || (!chkIsShowPre && quitInfo.getPurchase().getToPurchaseDate() != null)) {
					Date quitDate = quitInfo.getQuitDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						quitDate = quitInfo.getAuditTime();
					}

					if (quitDate != null && quitDate.compareTo(beginDate) >= 0 && quitDate.compareTo(endDate) < 0) {
						if (resultMap != null) {
							if (resultMap.get(buildIdStr) == null) {
								resultMap.put(buildIdStr, ctrlAmount.negate());
							} else {
								BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
								resultMap.put(buildIdStr, oldCount.add(ctrlAmount.negate()));
							}
						} else {
							modifyDurAndDayDataByQuitRoom(quitInfo, buildIdStr, ctrlAmount.negate(), beginDate, endDate, "QuitDate");
						}
					}
				}
			}
		}

		// 调整的特殊处理
		// by Pope
		if (adjustColl != null) {
			for (int i = 0; i < adjustColl.size(); i++) {
				BillAdjustInfo adjustInfo = adjustColl.get(i);
				String buildIdStr = adjustInfo.getPurchase().getRoom().getBuilding().getId().toString();
				BigDecimal ctrlAmount = adjustInfo.getPurchase().getContractTotalAmount();
				if ((chkIsShowPre && adjustInfo.getPurchase().getToSaleDate() != null && adjustInfo.getPurchase().getToSaleDate().compareTo(beginDate) >= 0 && adjustInfo.getPurchase().getToSaleDate()
						.compareTo(endDate) < 0)
						|| (!chkIsShowPre && adjustInfo.getPurchase().getToPurchaseDate() != null && adjustInfo.getPurchase().getToPurchaseDate().compareTo(beginDate) >= 0 && adjustInfo.getPurchase()
								.getToPurchaseDate().compareTo(endDate) < 0)) {
					if (resultMap != null) {
						if (resultMap.get(buildIdStr) == null) {
							resultMap.put(buildIdStr, ctrlAmount);
						} else {
							BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
							resultMap.put(buildIdStr, oldCount.add(ctrlAmount));
						}
					} else {
						modifyDurAndDayDataByAdjustRoom(adjustInfo, buildIdStr, ctrlAmount, beginDate, endDate, "Purchase");
					}
				}

				// 如果预定进入销售统计,必须算上退房业务;否则不能转认购时间不能为空
				if (chkIsShowPre || (!chkIsShowPre && adjustInfo.getPurchase().getToPurchaseDate() != null)) {
					Date quitDate = adjustInfo.getBizDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						quitDate = adjustInfo.getAuditTime();
					}

					if (quitDate != null && quitDate.compareTo(beginDate) >= 0 && quitDate.compareTo(endDate) < 0) {
						if (resultMap != null) {
							if (resultMap.get(buildIdStr) == null) {
								resultMap.put(buildIdStr, ctrlAmount.negate());
							} else {
								BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
								resultMap.put(buildIdStr, oldCount.add(ctrlAmount.negate()));
							}
						} else {
							modifyDurAndDayDataByAdjustRoom(adjustInfo, buildIdStr, ctrlAmount.negate(), beginDate, endDate, "BizDate");
						}
					}
				}
			}
		}

		// 对变更的做特殊处理
		if (changeColl != null) {
			for (int i = 0; i < changeColl.size(); i++) {
				PurchaseChangeInfo changeInfo = changeColl.get(i);
				String buildIdStr = changeInfo.getPurchase().getRoom().getBuilding().getId().toString();
				BigDecimal newAmount = changeInfo.getNewContractAmount();
				BigDecimal oldAmount = changeInfo.getOldContractAmount();
				BigDecimal ctrlAmount = newAmount.subtract(oldAmount);
				if ((chkIsShowPre && changeInfo.getPurchase().getToSaleDate() != null && changeInfo.getPurchase().getToSaleDate().compareTo(beginDate) >= 0 && changeInfo.getPurchase().getToSaleDate()
						.compareTo(endDate) < 0)
						|| (!chkIsShowPre && changeInfo.getPurchase().getToPurchaseDate() != null && changeInfo.getPurchase().getToPurchaseDate().compareTo(beginDate) >= 0 && changeInfo.getPurchase()
								.getToPurchaseDate().compareTo(endDate) < 0)) {
					if (resultMap != null) {
						if (resultMap.get(buildIdStr) == null) {
							resultMap.put(buildIdStr, ctrlAmount.negate());
						} else {
							BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
							resultMap.put(buildIdStr, oldCount.add(ctrlAmount.negate()));
						}
					} else {
						modifyDurAndDayDataByPurChange(changeInfo, buildIdStr, ctrlAmount.negate(), beginDate, endDate, "Purchase");
					}
				}

				// 如果预定进入销售统计,必须算上变更业务,则;否则认购日期不能为空
				if (chkIsShowPre || (!chkIsShowPre && changeInfo.getPurchase().getToPurchaseDate() != null)) {
					Date changeDate = changeInfo.getChangeDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						changeDate = changeInfo.getAuditTime();
					}
					if (changeDate != null && changeDate.compareTo(beginDate) >= 0 && changeDate.compareTo(endDate) < 0) {
						if (resultMap != null) {
							if (resultMap.get(buildIdStr) == null) {
								resultMap.put(buildIdStr, ctrlAmount);
							} else {
								BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
								resultMap.put(buildIdStr, oldCount.add(ctrlAmount));
							}
						} else {
							modifyDurAndDayDataByPurChange(changeInfo, buildIdStr, ctrlAmount, beginDate, endDate, "QuitDate");
						}
					}
				}
			}
		}
	}

	// 如果换房单符合区间或某天的条件，则修改对应的单元格数据
	// compareDateType 按天比较的日期类型 1. Purchase 按换房单对应旧认购单日期比较， 2. ChangeDate
	// 按换房日比较
	private void modifyDurAndDayDataByChangeRoom(ChangeRoomInfo changeRoomInfo, String buildIdStr, BigDecimal ctrlAmount, Date beginDate, Date endDate, String compareDateType) {
		IRow row = (IRow) buildingMap.get(buildIdStr);
		if (row != null) {
			// 修改区间列
			Object cellObject = row.getCell("durCratCount").getValue();
			if (cellObject != null && cellObject instanceof BigDecimal) {
				BigDecimal oldCount = (BigDecimal) cellObject;
				row.getCell("durCratCount").setValue(oldCount.add(ctrlAmount));
			} else {
				row.getCell("durCratCount").setValue(ctrlAmount);
			}

			// 修改某天的日期列 -- 比较区件范围里的每一日
			Date tempBeginDate = beginDate;
			int dayColumnIndex = 1;
			while (tempBeginDate.before(endDate)) {
				if (compareDateType.equals("Purchase")) {
					if ((chkIsShowPre && changeRoomInfo.getOldPurchase().getToSaleDate().compareTo(tempBeginDate) >= 0 && changeRoomInfo.getOldPurchase().getToSaleDate().compareTo(
							getNextDayBeginTime(tempBeginDate)) < 0)
							|| (!chkIsShowPre && changeRoomInfo.getOldPurchase().getToPurchaseDate().compareTo(tempBeginDate) >= 0 && changeRoomInfo.getOldPurchase().getToPurchaseDate().compareTo(
									getNextDayBeginTime(tempBeginDate)) < 0)) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				} else if (compareDateType.equals("ChangeDate")) {
					Date changeDate = changeRoomInfo.getChangeDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						changeDate = changeRoomInfo.getAuditTime();
					}

					if (changeDate != null && changeDate.compareTo(tempBeginDate) >= 0 && changeDate.compareTo(getNextDayBeginTime(tempBeginDate)) < 0) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				}
				dayColumnIndex++;
				tempBeginDate = getNextDayBeginTime(tempBeginDate); // 指定日期的下一天
			}
		}
	}

	// 如果退房单符合区间或某天的条件，则修改对应的单元格数据
	// compareDateType 按天比较的日期类型 1. Purchase 按退房单对应认购单日期比较， 2. QuitDate 按退房日比较
	private void modifyDurAndDayDataByQuitRoom(QuitRoomInfo quitInfo, String buildIdStr, BigDecimal ctrlAmount, Date beginDate, Date endDate, String compareDateType) {
		IRow row = (IRow) buildingMap.get(buildIdStr);
		if (row != null) {
			// 修改区间列
			Object cellObject = row.getCell("durCratCount").getValue();
			if (cellObject != null && cellObject instanceof BigDecimal) {
				BigDecimal oldCount = (BigDecimal) cellObject;
				row.getCell("durCratCount").setValue(oldCount.add(ctrlAmount));
			} else {
				row.getCell("durCratCount").setValue(ctrlAmount);
			}

			// 修改某天的日期列 -- 比较区件范围里的每一日
			Date tempBeginDate = beginDate;
			int dayColumnIndex = 1;
			while (tempBeginDate.before(endDate)) {
				if (compareDateType.equals("Purchase")) {
					if ((chkIsShowPre && quitInfo.getPurchase().getToSaleDate().compareTo(tempBeginDate) >= 0 && quitInfo.getPurchase().getToSaleDate().compareTo(getNextDayBeginTime(tempBeginDate)) < 0)
							|| (!chkIsShowPre && quitInfo.getPurchase().getToPurchaseDate().compareTo(tempBeginDate) >= 0 && quitInfo.getPurchase().getToPurchaseDate().compareTo(
									getNextDayBeginTime(tempBeginDate)) < 0)) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				} else if (compareDateType.equals("QuitDate")) {
					Date quitDate = quitInfo.getQuitDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						quitDate = quitInfo.getAuditTime();
					}

					if (quitDate != null && quitDate.compareTo(tempBeginDate) >= 0 && quitDate.compareTo(getNextDayBeginTime(tempBeginDate)) < 0) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				}
				dayColumnIndex++;
				tempBeginDate = getNextDayBeginTime(tempBeginDate); // 指定日期的下一天
			}
		}
	}

	// 如果调整单符合区间或某天的条件，则修改对应的单元格数据
	// compareDateType 按天比较的日期类型 1. Purchase 按退房单对应认购单日期比较， 2. QuitDate 按退房日比较
	// by Pope
	private void modifyDurAndDayDataByAdjustRoom(BillAdjustInfo adjustInfo, String buildIdStr, BigDecimal ctrlAmount, Date beginDate, Date endDate, String compareDateType) {
		IRow row = (IRow) buildingMap.get(buildIdStr);
		if (row != null) {
			// 修改区间列
			Object cellObject = row.getCell("durCratCount").getValue();
			if (cellObject != null && cellObject instanceof BigDecimal) {
				BigDecimal oldCount = (BigDecimal) cellObject;
				row.getCell("durCratCount").setValue(oldCount.add(ctrlAmount));
			} else {
				row.getCell("durCratCount").setValue(ctrlAmount);
			}

			// 修改某天的日期列 -- 比较区件范围里的每一日
			Date tempBeginDate = beginDate;
			int dayColumnIndex = 1;
			while (tempBeginDate.before(endDate)) {
				if (compareDateType.equals("Purchase")) {
					if ((chkIsShowPre && adjustInfo.getPurchase().getToSaleDate().compareTo(tempBeginDate) >= 0 && adjustInfo.getPurchase().getToSaleDate().compareTo(
							getNextDayBeginTime(tempBeginDate)) < 0)
							|| (!chkIsShowPre && adjustInfo.getPurchase().getToPurchaseDate().compareTo(tempBeginDate) >= 0 && adjustInfo.getPurchase().getToPurchaseDate().compareTo(
									getNextDayBeginTime(tempBeginDate)) < 0)) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				} else if (compareDateType.equals("BizDate")) {
					Date quitDate = adjustInfo.getBizDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						quitDate = adjustInfo.getAuditTime();
					}

					if (quitDate != null && quitDate.compareTo(tempBeginDate) >= 0 && quitDate.compareTo(getNextDayBeginTime(tempBeginDate)) < 0) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				}
				dayColumnIndex++;
				tempBeginDate = getNextDayBeginTime(tempBeginDate); // 指定日期的下一天
			}
		}
	}

	// 如果变更单符合区间或某天的条件，则修改对应的单元格数据
	// compareDateType 按天比较的日期类型 1. Purchase 按退房单对应认购单日期比较， 2. QuitDate 按退房日比较
	private void modifyDurAndDayDataByPurChange(PurchaseChangeInfo changeInfo, String buildIdStr, BigDecimal ctrlAmount, Date beginDate, Date endDate, String compareDateType) {
		IRow row = (IRow) buildingMap.get(buildIdStr);
		if (row != null) {
			// 修改区间列
			Object cellObject = row.getCell("durCratCount").getValue();
			if (cellObject != null && cellObject instanceof BigDecimal) {
				BigDecimal oldCount = (BigDecimal) cellObject;
				row.getCell("durCratCount").setValue(oldCount.add(ctrlAmount));
			} else {
				row.getCell("durCratCount").setValue(ctrlAmount);
			}

			// 修改某天的日期列 -- 比较区件范围里的每一日
			Date tempBeginDate = beginDate;
			int dayColumnIndex = 1;
			while (tempBeginDate.before(endDate)) {
				if (compareDateType.equals("Purchase")) {
					if ((chkIsShowPre && changeInfo.getPurchase().getToSaleDate().compareTo(tempBeginDate) >= 0 && changeInfo.getPurchase().getToSaleDate().compareTo(
							getNextDayBeginTime(tempBeginDate)) < 0)
							|| (!chkIsShowPre && changeInfo.getPurchase().getToPurchaseDate().compareTo(tempBeginDate) >= 0 && changeInfo.getPurchase().getToPurchaseDate().compareTo(
									getNextDayBeginTime(tempBeginDate)) < 0)) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				} else if (compareDateType.equals("QuitDate")) {
					Date changeDate = changeInfo.getChangeDate();
					if (setting.getBaseRoomSetting().isAuditDate()) {
						changeDate = changeInfo.getAuditTime();
					}
					if (changeDate != null && changeDate.compareTo(tempBeginDate) >= 0 && changeDate.compareTo(getNextDayBeginTime(tempBeginDate)) < 0) {
						Object cellDayObject = row.getCell("dayCratCount" + dayColumnIndex).getValue();
						if (cellDayObject != null && cellDayObject instanceof BigDecimal) {
							BigDecimal oldCount = (BigDecimal) cellDayObject;
							row.getCell("dayCratCount" + dayColumnIndex).setValue(oldCount.add(ctrlAmount));
						} else {
							row.getCell("dayCratCount" + dayColumnIndex).setValue(ctrlAmount);
						}
					}
				}
				dayColumnIndex++;
				tempBeginDate = getNextDayBeginTime(tempBeginDate); // 指定日期的下一天
			}
		}
	}

	// 从集合中选取在某一天的时间范围内的，做成映射
	private Map getDayQueryResult(RoomCollection roomCollection, Date dayBeginTime, Date dayEndTime) throws BOSException {
		// 预定业务进入销售统计 否toPurchaseDate 是toSaleDate chkIsShowPre
		Map resultMap = new HashMap();
		if (roomCollection == null || roomCollection.size() == 0)
			return resultMap;
		for (int i = 0; i < roomCollection.size(); i++) {
			RoomInfo roomInfo = roomCollection.get(i);
			if (!chkIsShowPre) {
				if (roomInfo.getToPurchaseDate() == null || roomInfo.getToPurchaseDate().compareTo(dayBeginTime) < 0 || roomInfo.getToPurchaseDate().compareTo(dayEndTime) >= 0) {
					continue;
				}
			} else if (roomInfo.getToSaleDate() == null || roomInfo.getToSaleDate().compareTo(dayBeginTime) < 0 || roomInfo.getToSaleDate().compareTo(dayEndTime) >= 0) {
				continue;
			}

			if (roomInfo.getBuilding() != null && roomInfo.getLastPurchase() != null) {
				String buildIdStr = roomInfo.getBuilding().getId().toString();
				BigDecimal ctrtAmount = roomInfo.getLastPurchase().getContractTotalAmount();
				if (ctrtAmount == null)
					ctrtAmount = new BigDecimal("0");
				if (resultMap.get(buildIdStr) == null) {
					resultMap.put(buildIdStr, ctrtAmount);
				} else {
					BigDecimal oldCount = (BigDecimal) resultMap.get(buildIdStr);
					resultMap.put(buildIdStr, oldCount.add(ctrtAmount));
				}
			}
		}
		return resultMap;
	}

	private RoomCollection getRoomCollection(Date beginDate, Date endDate, RoomSellStateEnum sellState) throws BOSException {
		Set builSet = buildingMap.keySet();
		if (builSet.size() == 0)
			return null;
		Set buildingSet = new HashSet();
		buildingSet.addAll(builSet);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selecColl = new SelectorItemCollection();
		selecColl.add(new SelectorItemInfo("building.id"));
		selecColl.add(new SelectorItemInfo("lastPurchase.contractTotalAmount"));
		selecColl.add(new SelectorItemInfo("toSaleDate"));
		selecColl.add(new SelectorItemInfo("toPurchaseDate"));
		selecColl.add(new SelectorItemInfo("number")); // 调试
		view.setSelector(selecColl);
		FilterInfo filter = new FilterInfo();
		String maskString = "#0 and #1";
		filter.getFilterItems().add(new FilterItemInfo("building.id", buildingSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("lastPurchase.id", null, CompareType.NOTEQUALS));
		int indexNum = 2;
		if (sellState == null) {
			filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.Purchase));
			filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.Sign));
			if (this.chkIsShowPre) {
				filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.PrePurchase));
				maskString += " and (#2 or #3 or #4) ";
				indexNum += 3;
			} else {
				maskString += " and (#2 or #3) ";
				indexNum += 2;
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("sellState", sellState));
			maskString += " and #2 ";
			indexNum++;
		}

		// 预定业务进入销售统计 否toPurchaseDate 是toSaleDate chkIsShowPre
		String queryDateParm = "toPurchaseDate";
		if (chkIsShowPre)
			queryDateParm = "toSaleDate";

		if (beginDate != null) {
			filter.getFilterItems().add(new FilterItemInfo(queryDateParm, beginDate, CompareType.GREATER_EQUALS));
			maskString += " and #" + indexNum;
			indexNum++;
		}
		if (endDate != null) {
			filter.getFilterItems().add(new FilterItemInfo(queryDateParm, endDate, CompareType.LESS));
			maskString += " and #" + indexNum;
			indexNum++;
		}
		filter.setMaskString(maskString);
		view.setFilter(filter);

		FilterInfo elseFilter = new FilterInfo();
		elseFilter.getFilterItems().add(new FilterItemInfo("isForSHE", new Integer(1)));
		view.getFilter().mergeFilter(elseFilter, "AND");

		return RoomFactory.getRemoteInstance().getRoomCollection(view);
	}

	// 退房 --特殊处理
	private QuitRoomCollection getQuitRoomCollection(Date beginDate, Date endDate) throws BOSException {
		if (buildingMap == null)
			return null;
		if (beginDate == null || endDate == null)
			return null;

		Set builSet = buildingMap.keySet();
		if (builSet.size() == 0)
			return null;
		Set buildingSet = new HashSet();
		buildingSet.addAll(builSet);

		// 预定业务进入销售统计 否toPurchaseDate 是toSaleDate
		String queryDateParm = "toPurchaseDate";
		if (chkIsShowPre)
			queryDateParm = "toSaleDate";

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selecColl = new SelectorItemCollection();
		selecColl.add(new SelectorItemInfo("room.building.id"));
		selecColl.add(new SelectorItemInfo("room.number")); // 调试
		selecColl.add(new SelectorItemInfo("room.building.name")); // 调试
		selecColl.add(new SelectorItemInfo("purchase.toPurchaseDate"));
		selecColl.add(new SelectorItemInfo("purchase.toSaleDate"));
		selecColl.add(new SelectorItemInfo("quitDate"));
		selecColl.add(new SelectorItemInfo("auditTime"));
		selecColl.add(new SelectorItemInfo("purchase.contractTotalAmount"));
		view.setSelector(selecColl);
		FilterInfo filter = new FilterInfo();
		String maskStr = "#0 and #1 and ((#2 and #3) or (#4 and #5)) ";
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("purchase." + queryDateParm, beginDate, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchase." + queryDateParm, endDate, CompareType.LESS));
		if (setting.getBaseRoomSetting().isAuditDate()) {
			filter.getFilterItems().add(new FilterItemInfo("auditTime", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("auditTime", endDate, CompareType.LESS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("quitDate", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("quitDate", endDate, CompareType.LESS));
		}

		filter.setMaskString(maskStr);
		view.setFilter(filter);

		return QuitRoomFactory.getRemoteInstance().getQuitRoomCollection(view);
	}

	// 调整 --特殊处理
	// by Pope
	private BillAdjustCollection getAdjustRoomCollection(Date beginDate, Date endDate) throws BOSException {
		if (buildingMap == null)
			return null;
		if (beginDate == null || endDate == null)
			return null;

		Set builSet = buildingMap.keySet();
		if (builSet.size() == 0)
			return null;
		Set buildingSet = new HashSet();
		buildingSet.addAll(builSet);

		// 预定业务进入销售统计 否toPurchaseDate 是toSaleDate
		String queryDateParm = "toPurchaseDate";
		if (chkIsShowPre)
			queryDateParm = "toSaleDate";

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selecColl = new SelectorItemCollection();
		selecColl.add(new SelectorItemInfo("purchase.room.building.id"));
		selecColl.add(new SelectorItemInfo("purchase.room.number")); // 调试
		selecColl.add(new SelectorItemInfo("purchase.room.building.name")); // 调试
		selecColl.add(new SelectorItemInfo("purchase.toPurchaseDate"));
		selecColl.add(new SelectorItemInfo("purchase.toSaleDate"));
		selecColl.add(new SelectorItemInfo("bizDate"));
		selecColl.add(new SelectorItemInfo("auditTime"));
		selecColl.add(new SelectorItemInfo("purchase.contractTotalAmount"));
		view.setSelector(selecColl);
		FilterInfo filter = new FilterInfo();
		String maskStr = "#0 and #1 and ((#2 and #3) or (#4 and #5)) ";
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("purchase.room.building.id", buildingSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("purchase." + queryDateParm, beginDate, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchase." + queryDateParm, endDate, CompareType.LESS));
		if (setting.getBaseRoomSetting().isAuditDate()) {
			filter.getFilterItems().add(new FilterItemInfo("auditTime", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("auditTime", endDate, CompareType.LESS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("bizDate", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("bizDate", endDate, CompareType.LESS));
		}

		filter.setMaskString(maskStr);
		view.setFilter(filter);
		return BillAdjustFactory.getRemoteInstance().getBillAdjustCollection(view);
	}

	// 换房 --特殊处理
	private ChangeRoomCollection getChangeRoomCollection(Date beginDate, Date endDate) throws BOSException {
		if (buildingMap == null)
			return null;
		if (beginDate == null || endDate == null)
			return null;

		Set builSet = buildingMap.keySet();
		if (builSet.size() == 0)
			return null;
		Set buildingSet = new HashSet();
		buildingSet.addAll(builSet);

		// 预定业务进入销售统计 否toPurchaseDate 是toSaleDate
		String queryDateParm = "toPurchaseDate";
		if (chkIsShowPre)
			queryDateParm = "toSaleDate";

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selecColl = new SelectorItemCollection();
		selecColl.add(new SelectorItemInfo("oldRoom.building.id"));
		selecColl.add(new SelectorItemInfo("oldRoom.number")); // 调试
		selecColl.add(new SelectorItemInfo("oldRoom.building.name")); // 调试
		selecColl.add(new SelectorItemInfo("oldPurchase.toPurchaseDate"));
		selecColl.add(new SelectorItemInfo("oldPurchase.toSaleDate"));
		selecColl.add(new SelectorItemInfo("changeDate"));
		selecColl.add(new SelectorItemInfo("auditTime"));
		selecColl.add(new SelectorItemInfo("oldPurchase.contractTotalAmount"));
		view.setSelector(selecColl);
		FilterInfo filter = new FilterInfo();
		String maskStr = "#0 and #1 and ((#2 and #3) or (#4 and #5)) ";
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("oldRoom.building.id", buildingSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("oldPurchase." + queryDateParm, beginDate, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("oldPurchase." + queryDateParm, endDate, CompareType.LESS));

		if (setting.getBaseRoomSetting().isAuditDate()) {
			filter.getFilterItems().add(new FilterItemInfo("auditTime", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("auditTime", endDate, CompareType.LESS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("changeDate", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("changeDate", endDate, CompareType.LESS));
		}

		filter.setMaskString(maskStr);
		view.setFilter(filter);

		return ChangeRoomFactory.getRemoteInstance().getChangeRoomCollection(view);
	}

	// 变更 --特殊处理
	private PurchaseChangeCollection getPurchaseChangeCollection(Date beginDate, Date endDate) throws BOSException {
		if (buildingMap == null)
			return null;
		if (beginDate == null || endDate == null)
			return null;

		Set builSet = buildingMap.keySet();
		if (builSet.size() == 0)
			return null;
		Set buildingSet = new HashSet();
		buildingSet.addAll(builSet);

		// 预定业务进入销售统计 否toPurchaseDate 是toSaleDate
		String queryDateParm = "toPurchaseDate";
		if (chkIsShowPre)
			queryDateParm = "toSaleDate";

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selecColl = new SelectorItemCollection();
		selecColl.add(new SelectorItemInfo("purchase.room.building.id"));
		selecColl.add(new SelectorItemInfo("purchase.room.number")); // 调试
		selecColl.add(new SelectorItemInfo("purchase.room.building.name")); // 调试
		selecColl.add(new SelectorItemInfo("purchase.toPurchaseDate"));
		selecColl.add(new SelectorItemInfo("purchase.toSaleDate"));
		selecColl.add(new SelectorItemInfo("changeDate"));
		selecColl.add(new SelectorItemInfo("auditTime"));
		selecColl.add(new SelectorItemInfo("newContractAmount"));
		selecColl.add(new SelectorItemInfo("oldContractAmount"));
		view.setSelector(selecColl);
		FilterInfo filter = new FilterInfo();
		String maskStr = "#0 and #1 and ((#2 and #3) or (#4 and #5))";
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("purchase.room.building.id", buildingSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("purchase." + queryDateParm, beginDate, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchase." + queryDateParm, endDate, CompareType.LESS));

		if (setting.getBaseRoomSetting().isAuditDate()) {
			filter.getFilterItems().add(new FilterItemInfo("auditTime", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("auditTime", endDate, CompareType.LESS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("changeDate", beginDate, CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("changeDate", endDate, CompareType.LESS));
		}

		filter.setMaskString(maskStr);
		view.setFilter(filter);

		return PurchaseChangeFactory.getRemoteInstance().getPurchaseChangeCollection(view);
	}

	private void fillColumnDateByResultMap(Map resultMap, String colName) {
		if (resultMap == null)
			return;

		Set keySet = resultMap.keySet();
		Iterator iter = keySet.iterator();
		while (iter.hasNext()) {
			String buildIdStr = (String) iter.next();
			IRow row = (IRow) buildingMap.get(buildIdStr);
			if (row != null) {
				row.getCell(colName).setValue(resultMap.get(buildIdStr));
			}
		}
	}

	private void clearAllDate() {
		int colCount = this.tblMain.getColumnCount();
		int firstColNum = this.tblMain.getColumnIndex("contractCount");
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			for (int j = firstColNum; j < colCount; j++) {
				row.getCell(j).setValue(null);
			}
		}
	}

	/**
	 * 统计所有父节点
	 */
	public void setUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i); // 当前行
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList(); // 所有叶节点项目集合
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}

				for (int j = 2; j < this.tblMain.getColumnCount(); j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}

					if (!aimCost.equals(FMConstants.ZERO))
						row.getCell(j).setValue(aimCost);
				}

			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		return;
	}

	public static void main(String[] args) {

		System.out.println("aaaaa");
		System.out.println(true == true);
		System.out.println(false == false);
		System.out.println(true == false);

	}

}