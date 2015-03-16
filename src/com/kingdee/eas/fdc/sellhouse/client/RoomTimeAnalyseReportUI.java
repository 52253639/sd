/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTTreeColumnEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTTreeColumnListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.QueryPanelCollection;
import com.kingdee.eas.base.commonquery.QueryPanelInfo;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.XMLBean;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.client.UserAccreditUI;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class RoomTimeAnalyseReportUI extends AbstractRoomTimeAnalyseReportUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomTimeAnalyseReportUI.class);

	/**
	 * output class constructor
	 */
	public RoomTimeAnalyseReportUI() throws Exception {
		super();
	}

	//增加刷新功能
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {		
		doQuery();
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		
		kDTable1.checkParsed();
		this.kDTable1.getViewManager().freeze(-1, 0);		
		initCommonQuery();
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					QuerySolutionInfo solutionInfo = QuerySolutionFacadeFactory.getRemoteInstance()
							.getDefaultSolution(RoomTimeAnalyseReportUI.class.getName(), "com.kingdee.eas.base.message.MsgQuery");
					if (solutionInfo != null) {
						QueryPanelCollection queryPanelCollection = solutionInfo.getQueryPanelInfo();
						if (queryPanelCollection.size() > 0) {
							QueryPanelInfo queryPanelInfo = queryPanelCollection.get(0);
							if (queryPanelInfo.get("customerparams") != null) {
								final String customerparams = queryPanelInfo.get("customerparams").toString();
								LongTimeDialog longTimeDialog = UITools.getDialog(RoomTimeAnalyseReportUI.this);
								longTimeDialog.setLongTimeTask(new ILongTimeTask() {
									public Object exec() throws Exception {
										CustomerParams cp = XMLBean.TransStrToCustParams(customerparams);
										getFilterUI().setCustomerParams(cp);
										execQuery(cp);
										return "";
									}

									public void afterExec(Object result) throws Exception {
										if(RoomTimeAnalyseReportUI.this.kDTable1.getRowCount() != 0){
											UITools.showMsg(RoomTimeAnalyseReportUI.this, "数据装载成功", false);
										}
									}
								});
								longTimeDialog.show();

							}

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//导出权限问题的处理
	protected KDTable getTableForCommon() {
		return this.kDTable1;
	}
	
	CommonQueryDialog dialog;

	CommonQueryDialog initCommonQuery() {
		dialog = new CommonQueryDialog();
		dialog.setShowFilter(false);
		dialog.setShowSorter(false);
		dialog.setOwner(this);
		dialog.setUiObject(this);
		dialog.setParentUIClassName(this.getClass().getName());
		dialog.setQueryObjectPK(new MetaDataPK("com.kingdee.eas.base.message",
				"MsgQuery"));
		dialog.addUserPanel(getFilterUI());

		return dialog;
	}

	private RoomTimeFilterUI filterUI = null;

	private RoomTimeFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new RoomTimeFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.filterUI;
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if (dialog.show()) {
			doQuery();
		}
	}

	private void doQuery() {
		LongTimeDialog longTimeDialog = UITools.getDialog(this);
		longTimeDialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				CustomerParams params = getFilterUI().getCustomerParams();
				execQuery(params);
				return "";
			}

			public void afterExec(Object result) throws Exception {
				if(RoomTimeAnalyseReportUI.this.kDTable1.getRowCount() != 0){
					UITools.showMsg(RoomTimeAnalyseReportUI.this, "数据装载成功", false);
				}
			}
		});
		longTimeDialog.show();
	}

	public void execQuery(CustomerParams cp) throws EASBizException, BOSException,
			SQLException, ParseException {
		if(StringUtils.isEmpty(this.getFilterUI().getRoomSellType(cp))  ||  StringUtils.isEmpty(cp.getCustomerParam("params"))){
			this.kDTable1.removeRows(false);
			return;
		}
		
		String param = cp.getCustomerParam("params");
		List list = BizFlowEnum.getEnumList();
		HashMap enumMap = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			BizFlowEnum flowEnum = (BizFlowEnum) list.get(i);
			enumMap.put(flowEnum.getAlias(), flowEnum);
		}

		String[] rows = param.split("#");
		for (int i = 4; i < this.kDTable1.getColumnCount(); i++) {
			this.kDTable1.removeColumn(i);
			i--;
		}
		ArrayList arrayList = new ArrayList();

		for (int i = 0; i < rows.length; i++) {
			String[] cells = rows[i].split("~");
			if (cells.length != 3) {
				continue;
			}
			BizFlowEnum[] flowEnums = new BizFlowEnum[2];
			flowEnums[0] = (BizFlowEnum) enumMap.get(cells[0]);
			flowEnums[1] = (BizFlowEnum) enumMap.get(cells[1]);
			arrayList.add(flowEnums);

			IColumn column = this.kDTable1.addColumn();
			column.setWidth(150);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			String key = flowEnums[0].getValue() + "_"
					+ flowEnums[1].getValue() + "_" + i + "_avg";
			column.setKey(key);
			this.kDTable1.getHeadRow(0).getCell(key).setValue(cells[2]);

			column = this.kDTable1.addColumn();
			column.setWidth(150);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			key = flowEnums[0].getValue() + "_" + flowEnums[1].getValue() + "_"
					+ i + "_count";
			column.setKey(key);
			this.kDTable1.getHeadRow(0).getCell(key).setValue(cells[2] + " 数量");
			column.getStyleAttributes().setHided(true);

			column = this.kDTable1.addColumn();
			column.setWidth(150);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			key = flowEnums[0].getValue() + "_" + flowEnums[1].getValue() + "_"
					+ i + "_sum";
			column.setKey(key);
			this.kDTable1.getHeadRow(0).getCell(key).setValue(cells[2] + " 总和");
			column.getStyleAttributes().setHided(true);

		}
		fillProjectAndBuilding();
		fillRoom();
		fillRoomData(arrayList);
		fillRoomSumData(root);
		this.kDTable1.getStyleAttributes().setLocked(true);
		this.kDTable1.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);

	}

	// 填充房間1
	private void fillRoom() throws BOSException, EASBizException, SQLException {
		for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
			IRow row = this.kDTable1.getRow(i);
			if (row.getUserObject() instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) row
						.getUserObject();
				if (node.isLeaf()) {
					BuildingInfo buildingInfo = null;
					HashMap hashMap = (HashMap) node.getUserObject();
					Object obj = hashMap.get("userObject");
					if (obj instanceof BuildingInfo) {
						buildingInfo = (BuildingInfo) obj;
						i += fillRoomEntry(node, buildingInfo, 0,
								this.kDTable1, i);
					} else if (obj instanceof Integer) {  //已作废
						DefaultMutableTreeNode parentNode = ((DefaultMutableTreeNode) node
								.getParent());
						HashMap hashMap1 = (HashMap) parentNode.getUserObject();
						Object obj1 = hashMap1.get("userObject");
						if (obj1 instanceof BuildingInfo) {
							buildingInfo = (BuildingInfo) obj1;
							i += fillRoomEntry(node, buildingInfo,
									((Integer) obj).intValue(), this.kDTable1,
									i);
						}
					}else if (obj instanceof BuildingUnitInfo) {
						DefaultMutableTreeNode parentNode = ((DefaultMutableTreeNode) node
								.getParent());
						HashMap hashMap1 = (HashMap) parentNode.getUserObject();
						Object obj1 = hashMap1.get("userObject");
						if (obj1 instanceof BuildingInfo) {
							buildingInfo = (BuildingInfo) obj1;
							i += fillRoomEntry(node, buildingInfo,
									((BuildingUnitInfo) obj).getSeq(), this.kDTable1,
									i);
						}
					}
				}
			}
		}
	}

	// 填充房間2
	private int fillRoomEntry(DefaultMutableTreeNode node,
			BuildingInfo buildingInfo, int unit, KDTable table, int rowIndex)
			throws BOSException, EASBizException, SQLException {
		if (buildingInfo == null) {
			return 0;
		}

		String sql = "select *,lastPurchase.payType.id,lastSignContract.signDate where building = '"
				+ buildingInfo.getId().toString() + "' and unit = " + unit;

		CustomerParams params = getFilterUI().getCustomerParams();
		String sellType = getFilterUI().getRoomSellType(params);
		if (sellType != null) {
			sql += " and (" + sellType + ") ";
		} else {
			sql += " and (sellState = 'PrePurchase' or sellState = 'Purchase' or sellState = 'Sign' ) ";
		}
		sql += " order by number";
		RoomCollection roomCollection = RoomFactory.getRemoteInstance()
				.getRoomCollection(sql);

		int size = roomCollection.size();
		if (size > 0) {
			table.getRow(rowIndex).setCollapse(true);
		}
		for (int i = 0; i < size; i++) {
			RoomInfo roomInfo = roomCollection.get(i);
			IRow row = table.addRow(rowIndex + i + 1);
			row.setTreeLevel(node.getLevel() + 1);
			row.setUserObject(roomInfo);
			roomInfo.put("parentRow", table.getRow(rowIndex));
			roomInfo.put("row", row);
			roomInfo.put("node", node);
			roomInfo.put("building", buildingInfo);
			roomInfo.put("unit", unit + "");
			String space = "";
			for (int m = 0; m < node.getLevel() + 1; m++) {
				space += "  ";
			}
			row.getCell("name").setValue(space + roomInfo.getNumber());
			row.getStyleAttributes().setHided(true);
		}
		return size;
	}

	// 填充房間數據
	private void fillRoomData(ArrayList arrayList) throws BOSException,
			EASBizException, SQLException, ParseException {
		int rowCount = this.kDTable1.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			if (this.kDTable1.getRow(i).getUserObject() instanceof RoomInfo) {
				fillRoomDataEntry((RoomInfo) this.kDTable1.getRow(i)
						.getUserObject(), arrayList);
			}
		}
	}

	private void fillRoomDataEntry(RoomInfo room, ArrayList arrayList)
			throws BOSException, EASBizException, SQLException, ParseException {
		PurchaseInfo purchase;
		RoomSignContractInfo sign;
		RoomJoinInfo join;
		RoomAreaCompensateInfo areaCompensate;
		RoomLoanInfo loan;
		RoomPropertyBookInfo propertyBook;
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
				|| room.getSellState().equals(RoomSellStateEnum.Purchase)
				|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
			purchase = room.getLastPurchase();
			sign = room.getLastSignContract();
			join = SHEHelper.getRoomJoinInInReport(room);
			areaCompensate = SHEHelper.getRoomAreaCompensationInReport(room);
			loan = SHEHelper.getRoomLoanInReport(room);
			propertyBook = SHEHelper.getRoomPropertyBookInReport(room);
		} else {
			purchase = null;
			sign = null;
			join = null;
			areaCompensate = null;
			loan = null;
			propertyBook = null;
		}
		HashMap hashMap = addTotalFlowTable(room, purchase, sign, join,
				areaCompensate, loan, propertyBook);
		Date startDate, endDate;
		IRow row = (IRow) room.get("row");
		row.getCell("sellCount").setValue("1");
		row.getCell("sellArea").setValue(room.getBuildingArea());
		row.getCell("sellAmount").setValue(room.getDealTotalAmount());
		IRow parentRow = (IRow) room.get("parentRow");
		for (int i = 0; i < arrayList.size(); i++) {
			BizFlowEnum[] flowEnums = (BizFlowEnum[]) arrayList.get(i);
			startDate = (Date) hashMap.get(flowEnums[0]);
			endDate = (Date) hashMap.get(flowEnums[1]);
			if (startDate != null && endDate != null) {
				String countKey = flowEnums[0].getValue() + "_"
						+ flowEnums[1].getValue() + "_" + i + "_count";
				row.getCell(countKey).setValue(new BigDecimal("1"));
				if (parentRow.getCell(countKey).getValue() == null) {
					parentRow.getCell(countKey).setValue(new BigDecimal("1"));
				} else if (parentRow.getCell(countKey).getValue() instanceof BigDecimal) {
					BigDecimal bigDecimal = (BigDecimal) parentRow.getCell(
							countKey).getValue();
					bigDecimal = bigDecimal.add(new BigDecimal("1"));
					parentRow.getCell(countKey).setValue(bigDecimal);
				}

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				BigDecimal day = new BigDecimal(dateFormat.parse(
						dateFormat.format(endDate)).getTime()
						/ (1000 * 60 * 60 * 24)
						- dateFormat.parse(dateFormat.format(startDate))
								.getTime() / (1000 * 60 * 60 * 24));

				String sumKey = flowEnums[0].getValue() + "_"
						+ flowEnums[1].getValue() + "_" + i + "_sum";
				row.getCell(sumKey).setValue(day + "");

				if (parentRow.getCell(sumKey).getValue() == null) {
					parentRow.getCell(sumKey).setValue(day);
				} else if (parentRow.getCell(sumKey).getValue() instanceof BigDecimal) {
					BigDecimal bigDecimal = (BigDecimal) parentRow.getCell(
							sumKey).getValue();
					bigDecimal = bigDecimal.add(day);
					parentRow.getCell(sumKey).setValue(bigDecimal);
				}

				String avgKey = flowEnums[0].getValue() + "_"
						+ flowEnums[1].getValue() + "_" + i + "_avg";
				row.getCell(avgKey).setValue(day + "");

			}
		}

		if (parentRow.getCell("sellCount").getValue() == null) {
			parentRow.getCell("sellCount").setValue(new BigDecimal("1"));
		} else if (parentRow.getCell("sellCount").getValue() instanceof BigDecimal) {
			BigDecimal bigDecimal = (BigDecimal) parentRow.getCell("sellCount")
					.getValue();
			bigDecimal = bigDecimal.add(new BigDecimal("1"));
			parentRow.getCell("sellCount").setValue(bigDecimal);
		}

		if (parentRow.getCell("sellArea").getValue() == null) {
			parentRow.getCell("sellArea").setValue(room.getBuildingArea());
		} else if (parentRow.getCell("sellArea").getValue() instanceof BigDecimal) {
			if (room.getBuildingArea() != null) {
				BigDecimal bigDecimal = (BigDecimal) parentRow.getCell(
						"sellArea").getValue();
				bigDecimal = bigDecimal.add(room.getBuildingArea());
				parentRow.getCell("sellArea").setValue(bigDecimal);
			}

		}

		if (parentRow.getCell("sellAmount").getValue() == null) {
			parentRow.getCell("sellAmount").setValue(room.getDealTotalAmount());
		} else if (parentRow.getCell("sellAmount").getValue() instanceof BigDecimal) {
			if (room.getDealTotalAmount() != null) {
				BigDecimal bigDecimal = (BigDecimal) parentRow.getCell(
						"sellAmount").getValue();
				bigDecimal = bigDecimal.add(room.getDealTotalAmount());
				parentRow.getCell("sellAmount").setValue(bigDecimal);
			}

		}
	}

	private HashMap addTotalFlowTable(RoomInfo room, PurchaseInfo purchase,
			RoomSignContractInfo sign, RoomJoinInfo join,
			RoomAreaCompensateInfo areaCompensate, RoomLoanInfo loan,
			RoomPropertyBookInfo propertyBook) {
		HashMap hashMap = new HashMap();
		if (purchase == null) {
			return hashMap;
		}
		if (purchase != null && purchase.getPayType() != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", purchase.getPayType().getId()
							.toString()));
			BizListEntryCollection bizLists = new BizListEntryCollection();
			try {
				bizLists = BizListEntryFactory.getRemoteInstance()
						.getBizListEntryCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if (bizLists == null) {
				logger.warn("取数据出错了.");
				return hashMap;
			}
			view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("seq"));
			view.getSelector().add("*");
			view.getSelector().add("moneyDefine.*");
			view.getSelector().add("currency.*");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", purchase.getId().toString()));
			PurchasePayListEntryCollection listEntrys = null;
			try {
				listEntrys = PurchasePayListEntryFactory.getRemoteInstance()
						.getPurchasePayListEntryCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if (listEntrys == null) {
				logger.warn("取数据出错了.");
				return hashMap;
			}
			PurchasePayListEntryInfo firstMoneyEntry = null;
			PurchasePayListEntryInfo loanEntry = null;
			PurchasePayListEntryInfo accEntry = null;
			PurchasePayListEntryInfo lastEntry = null;
			for (int i = 0; i < listEntrys.size(); i++) {
				PurchasePayListEntryInfo entry = listEntrys.get(i);
				if (entry.getMoneyDefine().getMoneyType() == null) {
					continue;
				}
				if (entry.getMoneyDefine().getMoneyType().equals(
						MoneyTypeEnum.FisrtAmount)) {
					firstMoneyEntry = entry;
				}
				if (entry.getMoneyDefine().getMoneyType().equals(
						MoneyTypeEnum.LoanAmount)) {
					loanEntry = entry;
				}
				if (entry.getMoneyDefine().getMoneyType().equals(
						MoneyTypeEnum.AccFundAmount)) {
					accEntry = entry;
				}
				if (i == listEntrys.size() - 1) {
					lastEntry = entry;
				}
			}

			for (int i = 0; i < bizLists.size(); i++) {
				BizListEntryInfo entry = bizLists.get(i);
				BizFlowEnum bizFlow = entry.getBizFlow();
				BizTimeEnum bizTime = entry.getBizTime();
				Date curDate = new Date();
				if (bizTime.equals(BizTimeEnum.AppTime)) {
					curDate = entry.getAppDate();
				} else {
					curDate = new Date();
					int monthLimit = entry.getMonthLimit();
					int dayLimit = entry.getDayLimit();
					Calendar cal = Calendar.getInstance();
					cal.setTime(curDate);
					cal.add(Calendar.MONTH, monthLimit);
					cal.add(Calendar.DATE, dayLimit);
					curDate = cal.getTime();
				}
				if (bizFlow.equals(BizFlowEnum.Purchase)) {
					if (purchase != null) {
						hashMap.put(BizFlowEnum.Purchase, room
								.getToPurchaseDate());
					}

				} else if (bizFlow.equals(BizFlowEnum.Sign)) {
					if (sign != null) {
						hashMap.put(BizFlowEnum.Sign, sign.getSignDate());
					}
				} else if (bizFlow.equals(BizFlowEnum.AraaCompensation)) {
					if (areaCompensate != null) {
						hashMap.put(BizFlowEnum.AraaCompensation,
								areaCompensate.getCompensateDate());
					}
				} else if (bizFlow.equals(BizFlowEnum.PayFirstTerm)) {
					if (firstMoneyEntry == null) {
					} else {
						BigDecimal apAmount = firstMoneyEntry.getApAmount();
						BigDecimal actAmount = firstMoneyEntry
								.getActPayAmount();
						if (apAmount == null) {
							apAmount = FDCHelper.ZERO;
						}
						if (actAmount == null) {
							actAmount = FDCHelper.ZERO;
						}
						if (apAmount.compareTo(actAmount) == 0) {
							hashMap.put(BizFlowEnum.PayFirstTerm,
									firstMoneyEntry.getActRevDate());
						}
					}
				} else if (bizFlow.equals(BizFlowEnum.JoinIn)) {
					if (join != null) {
						hashMap.put(BizFlowEnum.JoinIn, join.getJoinEndDate());
					}
				} else if (bizFlow.equals(BizFlowEnum.Loan)) {
					if (loan != null) {
						hashMap
								.put(BizFlowEnum.Loan, loan
										.getProcessLoanDate());
					}
				} else if (bizFlow.equals(BizFlowEnum.AccFund)) {
					if (loan != null) {
						hashMap.put(BizFlowEnum.AccFund, loan
								.getFundProcessDate());
					}
				} else if (bizFlow.equals(BizFlowEnum.LoanInAcct)) {
					if (loanEntry == null) {
					} else {
						BigDecimal apAmount = loanEntry.getApAmount();
						BigDecimal actAmount = loanEntry.getActPayAmount();
						if (apAmount == null) {
							apAmount = FDCHelper.ZERO;
						}
						if (actAmount == null) {
							actAmount = FDCHelper.ZERO;
						}
						if (apAmount.compareTo(actAmount) == 0) {
							hashMap.put(BizFlowEnum.LoanInAcct, loanEntry
									.getActRevDate());
						}
					}
				} else if (bizFlow.equals(BizFlowEnum.AccFundInAcct)) {
					if (accEntry == null) {
					} else {
						BigDecimal apAmount = accEntry.getApAmount();
						BigDecimal actAmount = accEntry.getActPayAmount();
						if (apAmount == null) {
							apAmount = FDCHelper.ZERO;
						}
						if (actAmount == null) {
							actAmount = FDCHelper.ZERO;
						}
						if (apAmount.compareTo(actAmount) == 0) {
							hashMap.put(BizFlowEnum.AccFundInAcct, accEntry
									.getActRevDate());
						}
					}
				} else if (bizFlow.equals(BizFlowEnum.PropertyBook)) {
					if (propertyBook != null) {
						hashMap.put(BizFlowEnum.PropertyBook, propertyBook
								.getTransactDate());
					}
				} else if (bizFlow.equals(BizFlowEnum.PayAll)) {
					if (lastEntry == null) {
					} else {
						BigDecimal apAmount = lastEntry.getApAmount();
						BigDecimal actAmount = lastEntry.getActPayAmount();
						if (apAmount == null) {
							apAmount = FDCHelper.ZERO;
						}
						if (actAmount == null) {
							actAmount = FDCHelper.ZERO;
						}
						if (apAmount.compareTo(actAmount) == 0) {
							hashMap.put(BizFlowEnum.PayAll, lastEntry
									.getActRevDate());
						}
					}
				}

			}
		}
		return hashMap;
	}

	// 填充匯總數據
	private void fillRoomSumData(DefaultMutableTreeNode node)
			throws BOSException, EASBizException, SQLException {
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				fillRoomSumData((DefaultMutableTreeNode) node.getChildAt(i));
			}
		}

		HashMap map = (HashMap) node.getUserObject();
		IRow row = (IRow) map.get("row");
		IRow parentRow = (IRow) map.get("parentRow");
		if (parentRow != null) {
			for (int i = 1; i < this.kDTable1.getColumnCount(); i++) {
				String key = this.kDTable1.getColumnKey(i);
				if (key.indexOf("avg") > 0) {
					key = key.substring(0, key.indexOf("avg") - 1);
					if (row.getCell(key + "_sum").getValue() instanceof BigDecimal
							&& row.getCell(key + "_count").getValue() instanceof BigDecimal) {
						BigDecimal avg = ((BigDecimal) row
								.getCell(key + "_sum").getValue()).divide(
								(BigDecimal) row.getCell(key + "_count")
										.getValue(), 1,
								BigDecimal.ROUND_HALF_UP);
						row.getCell(key + "_avg").setValue(avg);
					}
				} else {
					if (parentRow.getCell(i).getValue() == null) {
						parentRow.getCell(i)
								.setValue(row.getCell(i).getValue());
					} else if (parentRow.getCell(i).getValue() instanceof BigDecimal
							&& row.getCell(i).getValue() instanceof BigDecimal) {
						BigDecimal bigDecimal = (BigDecimal) parentRow.getCell(
								i).getValue();
						bigDecimal = bigDecimal.add((BigDecimal) row.getCell(i)
								.getValue());
						parentRow.getCell(i).setValue(bigDecimal);
					}
				}
			}
		} else {
			for (int i = 1; i < this.kDTable1.getColumnCount(); i++) {
				String key = this.kDTable1.getColumnKey(i);
				if (key.indexOf("avg") > 0) {
					key = key.substring(0, key.indexOf("avg") - 1);
					if (row.getCell(key + "_sum").getValue() instanceof BigDecimal
							&& row.getCell(key + "_count").getValue() instanceof BigDecimal) {
						BigDecimal avg = ((BigDecimal) row
								.getCell(key + "_sum").getValue()).divide(
								(BigDecimal) row.getCell(key + "_count")
										.getValue(), 1,
								BigDecimal.ROUND_HALF_UP);
						row.getCell(key + "_avg").setValue(avg);
					}
				}
			}
		}

	}

	DefaultKingdeeTreeNode root;

	// 填充工程和棟
	private void fillProjectAndBuilding() throws BOSException, EASBizException,
			SQLException {
		this.kDTable1.removeRows(false);
		TreeModel buildingTree = null;
		try {
			buildingTree = SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys);
		} catch (Exception e) {
			this.handleException(e);
		}
		root = (DefaultKingdeeTreeNode) buildingTree.getRoot();
		this.kDTable1.getTreeColumn().setDepth(root.getDepth() + 2);
		fillNode(this.kDTable1, (DefaultMutableTreeNode) root, null);
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node,
			IRow parentRow) throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += "  ";
		}
		row.setUserObject(node);

		Object obj = node.getUserObject();
		HashMap hashMap = new HashMap();
		hashMap.put("userObject", obj);
		hashMap.put("row", row);
		hashMap.put("parentRow", parentRow);
		node.setUserObject(hashMap);

		row.getCell("name").setValue(space + node);
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i), row);
			}
		}
		row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
	}

	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			IRow row = KDTableUtil.getSelectedRow(this.kDTable1);
			if(row == null)
				return;
			if (row.getUserObject() instanceof RoomInfo) {
				RoomInfo roomInfo = (RoomInfo) row.getUserObject();
				PurchaseInfo purchaseInfo = roomInfo.getLastPurchase();
				UIContext context = new UIContext();
				context.put(UIContext.ID, purchaseInfo.getId().toString());
				context.put(UIContext.OWNER, this);
				IUIWindow window = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(
						PurchaseEditUI.class.getName(), context, null,
						"FINDVIEW");
				window.show();
			}
		}
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		this.kDTable1.getPrintManager().print();
	}

	/**
	 * output actionPrePrint_actionPerformed
	 */
	public void actionPrePrint_actionPerformed(ActionEvent e) throws Exception {
		this.kDTable1.getPrintManager().printPreview();
	}

	public static void main(String[] args) {
		Date endDate = new Date();
		endDate.setDate(12);
		endDate.setHours(1);
		Date startDate = new Date();
		startDate.setDate(5);

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			BigDecimal day = new BigDecimal(dateFormat.parse(
					dateFormat.format(endDate)).getTime()
					/ (1000 * 60 * 60 * 24)
					- dateFormat.parse(dateFormat.format(startDate)).getTime()
					/ (1000 * 60 * 60 * 24));
			System.out.println(day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}

}