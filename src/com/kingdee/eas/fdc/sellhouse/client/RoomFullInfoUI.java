/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BillAdjustCollection;
import com.kingdee.eas.fdc.sellhouse.BillAdjustInfo;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillCollection;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomFullInfoUI extends AbstractRoomFullInfoUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomFullInfoUI.class);

	private PurchaseInfo purchase = null;

	private RoomSignContractInfo sign = null;


	private Map allInfoMap = new HashMap(); 

	public void loadFields() {
		super.loadFields();
		RoomInfo room = (RoomInfo) this.editData;
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtNo.setText(room.getNumber());
		this.txtRoomNo.setText(room.getRoomNo());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtFloorHeight.setValue(room.getFloorHeight());
		this.f7Direction.setValue(room.getDirection());
		this.f7Sight.setValue(room.getSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		if (room.isIsCalByRoomArea()) {
			this.txtStandardPrice.setValue(room.getRoomPrice());
		} else {
			this.txtStandardPrice.setValue(room.getBuildPrice());
		}
		this.txtStandardTotalAmount.setValue(room.getStandardTotalAmount());
		this.txtDealPrice.setValue(room.getDealPrice());
		this.txtDealTotalAmount.setValue(room.getDealTotalAmount());
		this.chkIsCalByRoom.setSelected(room.isIsCalByRoomArea());
		this.comboRoomState.setSelectedItem(room.getSellState());
		this.tabBizInfo.removeAll();
		EventListener[] listeners = this.tabBizInfo.getListeners(ChangeListener.class);	
		for(int i=0;i<listeners.length;i++)
			this.tabBizInfo.removeChangeListener((ChangeListener)listeners[i]);
		
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase) || room.getSellState().equals(RoomSellStateEnum.Purchase) || room.getSellState().equals(RoomSellStateEnum.Sign)) {
			purchase = room.getLastPurchase();
			sign = room.getLastSignContract();					
		} else {
			purchase = null;
			sign = null;
		}
		
		allInfoMap = new HashMap(); 
		// 总览		--  根据付款方案对应的业务流程分录来显示的。显示该付款方案对应的流程的完成情况
		addTotalFlowTable();
		// 业务
		addBizTable();
		// 应付
		addPlanPayTable();
		// 实付
		addActPayTable();
		// 历史付款
		addHistoryPayTable();

		this.addHistoryPriceTable();
		//房间回收历史
		try {
			addRecoverHistoryTable(room);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		this.tabBizInfo.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				if(tabBizInfo.getSelectedComponent()==null) return;
				String comName = tabBizInfo.getSelectedComponent().getName();
				int comIndex = tabBizInfo.getSelectedIndex();
				if(comIndex==0) return;
				if(comName==null) return;
				if(comName.equals("TotalExplore")) {
					showTotalFlowTable(comIndex);
				}else if(comName.equals("Bizness")){
					showBizTable(comIndex);
				}else if(comName.equals("AppRevList")){
					showPlanPayTable(comIndex);
				}else if(comName.equals("ActRevList")){
					showActPayTable(comIndex);
				}else if(comName.equals("HistoryPay")){
					showHistoryPayTable(comIndex);
				}else if(comName.equals("HistoryPrice")){
					showHistoryPriceTable(comIndex);
				}
			}
		});
		
		String comName = tabBizInfo.getComponentAt(0).getName();
		if(comName!=null) {
			if(comName.equals("TotalExplore"))
				showTotalFlowTable(0);
			else if(comName.equals("Bizness"))
				showBizTable(0);
		}
		
		
	}

	/**
	 * 历史收付Table
	 */
	private void addHistoryPayTable() {
		KDTable historyPayTable = new KDTable();
		
		historyPayTable.setName("HistoryPay");
		this.tabBizInfo.add(historyPayTable, "历史收付");
	}

	protected void showHistoryPayTable(int comIndex) {
		KDTable historyPayTable = (KDTable)this.tabBizInfo.getComponentAt(comIndex);
	
		if(allInfoMap.get("ReceivingBillCollectionR")!=null) return;
		
		RoomInfo room = (RoomInfo) this.editData;
		try {
			Map tempMap = RoomFactory.getRemoteInstance().getRoomInfoCollectionMap(room, "ReceivingBillCollectionR");
			allInfoMap.putAll(tempMap);
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}
		
		FDCReceivingBillCollection revs = (FDCReceivingBillCollection)allInfoMap.get("ReceivingBillCollectionR");

		SHEHelper.fillActPayTable(historyPayTable, revs,SHEReceivingBillEditUI.class);
	}
	
	private void addActPayTable() {
		if (this.purchase == null) {
			return;
		}

		final KDTable actualPayTable = new KDTable();

		actualPayTable.setName("ActRevList");
		this.tabBizInfo.add(actualPayTable, "实收");
	}
	protected void showActPayTable(int comIndex) {
		if (this.purchase == null) {
			return;
		}
		
		if(allInfoMap.get("ReceivingBillCollectionP")!=null) return;
		
		RoomInfo room = (RoomInfo) this.editData;
		try {
			Map tempMap = RoomFactory.getRemoteInstance().getRoomInfoCollectionMap(room, "ReceivingBillCollectionP");
			allInfoMap.putAll(tempMap);
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}
		
		KDTable actualPayTable = (KDTable)this.tabBizInfo.getComponentAt(comIndex);
		FDCReceivingBillCollection revs = (FDCReceivingBillCollection)allInfoMap.get("ReceivingBillCollectionP");
		if(revs!=null)
			SHEHelper.fillActPayTable(actualPayTable, revs,SHEReceivingBillEditUI.class);
	}
	
	/**
	 * 增加历史定价页签
	 * 
	 * @author laiquan_luo
	 */
	private void addHistoryPriceTable() {
		final KDTable priceTable = new KDTable();
		priceTable.getStyleAttributes().setLocked(true);

		priceTable.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				try {
					if (e.getButton() == 1 && e.getClickCount() == 2) {
						if (e.getType() != 1)
							return;

						IRow row = priceTable.getRow(e.getRowIndex());

						String roomPriceBillId = null;
						if (row.getCell("roomPriceBill.id").getValue() != null) {
							roomPriceBillId = row.getCell("roomPriceBill.id").getValue().toString();
						}
						String adjustPriceBillId = null;

						if (row.getCell("priceAdjustBill.id").getValue() != null) {
							adjustPriceBillId = row.getCell("priceAdjustBill.id").getValue().toString();
						}

						if (roomPriceBillId != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", roomPriceBillId);
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomPriceBillEditUI.class.getName(), uiContext, null, "VIEW");
							uiWindow.show();
						} else if (adjustPriceBillId != null) {
							UIContext uiContext = new UIContext(this);
							uiContext.put("ID", adjustPriceBillId);
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PriceAdjustEditUI.class.getName(), uiContext, null, "VIEW");
							uiWindow.show();
						}
					}
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
				}
			}
		});

		IColumn col = priceTable.addColumn();
		col.setKey("id");
		col.getStyleAttributes().setHided(true);

		col = priceTable.addColumn();
		col.setKey("priceType");

		col = priceTable.addColumn();
		col.setKey("bizDate");
		col.getStyleAttributes().setNumberFormat("yyyy-MM-dd");

		col = priceTable.addColumn();
		col.setKey("standardTotalAmount");
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		col = priceTable.addColumn();
		col.setKey("roomPrice");
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		col = priceTable.addColumn();
		col.setKey("buildPrice");
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		col = priceTable.addColumn();
		col.setKey("isCalByRoomArea");

		col = priceTable.addColumn();
		col.setKey("head.id");
		col.getStyleAttributes().setHided(true);

		col = priceTable.addColumn();
		col.setKey("roomPriceBill.id");
		col.getStyleAttributes().setHided(true);

		col = priceTable.addColumn();
		col.setKey("priceAdjustBill.id");
		col.getStyleAttributes().setHided(true);

		IRow headRow = priceTable.addHeadRow();
		headRow.getCell("priceType").setValue("单据类型");
		headRow.getCell("bizDate").setValue("业务日期");
		headRow.getCell("standardTotalAmount").setValue("标准总价");
		headRow.getCell("roomPrice").setValue("套内单价");
		headRow.getCell("buildPrice").setValue("建筑单价");
		headRow.getCell("isCalByRoomArea").setValue("是否按套内面积计算");

		priceTable.setName("HistoryPrice");
		this.tabBizInfo.add(priceTable, "历史定价");

	}
	private void showHistoryPriceTable(int comIndex) {
		final KDTable priceTable = (KDTable)this.tabBizInfo.getComponentAt(comIndex);
		
		if(priceTable!=null && priceTable.getRowCount()>0) return;
		
		RoomInfo room = (RoomInfo) this.editData;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("SELECT SHEPRICEHISTORYENTRY.FID AS ID, " + "SHEPRICEHISTORYENTRY.FRoomPrice AS ROOMPRICE, " + "SHEPRICEHISTORYENTRY.FBuildPrice AS BUILDPRICE, "
				+ "SHEPRICEHISTORYENTRY.FIsCalByRoomArea AS ISCALBYROOMAREA, " + "SHEPRICEHISTORYENTRY.FBuildingArea AS BUILDINGAREA, " + "SHEPRICEHISTORYENTRY.FRoomArea AS ROOMAREA, "
				+ "SHEPRICEHISTORYENTRY.FBizDate AS BIZDATE, " + "HEAD.FID AS HEADID, " + "SHEPRICEHISTORYENTRY.FPriceType AS PRICETYPE, "
				+ "SHEPRICEHISTORYENTRY.FRoomPriceBillID AS ROOMPRICEBILLID, " + "SHEPRICEHISTORYENTRY.FPriceAdjustBillID AS PRICEADJUSTBILLID, "
				+ "SHEPRICEHISTORYENTRY.FStandardTotalPrice AS HEADSTANDARDTOTALAMOUNT " + " FROM T_SHE_SHEPriceHistoryEntry AS SHEPRICEHISTORYENTRY " + " INNER JOIN T_SHE_Room AS HEAD "
				+ " ON SHEPRICEHISTORYENTRY.FHeadID = HEAD.FID " + " LEFT OUTER JOIN T_SHE_PriceAdjust AS PRICEADJUSTBILL " + " ON SHEPRICEHISTORYENTRY.FPriceAdjustBillID = PRICEADJUSTBILL.FID "
				+ " LEFT OUTER JOIN T_SHE_RoomPriceBill AS ROOMPRICEBILL " + "ON SHEPRICEHISTORYENTRY.FRoomPriceBillID = ROOMPRICEBILL.FID " + " where HEAD.FID = '" + room.getId().toString() + "'");

		IRowSet rowSet = null;
		try {
			rowSet = sqlBuilder.executeQuery();

			while (rowSet.next()) {
				IRow row = priceTable.addRow();

				String temp = rowSet.getString("PRICETYPE");
				if ("setPrice".equalsIgnoreCase(temp)) {
					row.getCell("priceType").setValue("定价");
				} else if ("adjustPrice".equalsIgnoreCase(temp)) {
					row.getCell("priceType").setValue("调价");
				}

				row.getCell("bizDate").setValue(rowSet.getTimestamp("bizdate"));
				row.getCell("standardTotalAmount").setValue(rowSet.getBigDecimal("HEADSTANDARDTOTALAMOUNT"));
				row.getCell("roomPrice").setValue(rowSet.getBigDecimal("roomPrice"));
				row.getCell("buildPrice").setValue(rowSet.getBigDecimal("buildprice"));
				row.getCell("head.id").setValue(rowSet.getString("HEADID"));
				row.getCell("roomPriceBill.id").setValue(rowSet.getString("ROOMPRICEBILLID"));
				row.getCell("priceAdjustBill.id").setValue(rowSet.getString("PRICEADJUSTBILLID"));

				boolean debug = rowSet.getBoolean("ISCALBYROOMAREA");
				if (debug)
					row.getCell("isCalByRoomArea").setValue(Boolean.TRUE);
				else
					row.getCell("isCalByRoomArea").setValue(Boolean.FALSE);

			}
		} catch (BOSException e) {
			super.handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			super.handUIExceptionAndAbort(e);
		}
	}
	
	private void addPlanPayTable() {
		if (this.purchase == null) {
			return;
		}
		
		KDTable payTable = new KDTable();
		payTable.getStyleAttributes().setLocked(true);
		// payTable.getTreeColumn().setDepth(2);
		IColumn column = payTable.addColumn();
		column.setKey("term");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		column = payTable.addColumn();
		column.setKey("moneyName");
		column = payTable.addColumn();
		column.setKey("apAmount");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column = payTable.addColumn();
		column.setKey("actPayAmount");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		column = payTable.addColumn();
		column.setKey("refundmentAmount");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		column = payTable.addColumn();
		column.setKey("currency");
		IRow headRow = payTable.addHeadRow();
		headRow.getCell("term").setValue("期限");
		headRow.getCell("moneyName").setValue("款项名称");
		headRow.getCell("apAmount").setValue("应收金额");
		headRow.getCell("actPayAmount").setValue("已收金额");
		headRow.getCell("refundmentAmount").setValue("退款金额");
		headRow.getCell("currency").setValue("币别");

		payTable.setName("AppRevList");
		this.tabBizInfo.add(payTable, "应收");
	}

	protected void showPlanPayTable(int comIndex) {
		if (this.purchase == null) {
			return;
		}		

		KDTable payTable = (KDTable)this.tabBizInfo.getComponentAt(comIndex);
		if((allInfoMap.get("PurchasePayListEntryCollection")!=null && payTable.getRowCount()>0)||(allInfoMap.get("elsePayListEntry")!=null&&payTable.getRowCount()>0)) return;
		
		try {
			if(allInfoMap.get("PurchasePayListEntryCollection")==null) {
				RoomInfo room = (RoomInfo) this.editData;
				Map tempMap = RoomFactory.getRemoteInstance().getRoomInfoCollectionMap(room, "PurchasePayListEntryCollection elsePayListEntry");
				allInfoMap.putAll(tempMap);
			}
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}
		PurchasePayListEntryCollection listEntrys = (PurchasePayListEntryCollection)allInfoMap.get("PurchasePayListEntryCollection");
		CRMHelper.sortCollection(listEntrys, "seq", true);
		
		BigDecimal addApAmount = FDCHelper.ZERO;
		BigDecimal addActPayAmount = FDCHelper.ZERO;
		BigDecimal addRefundmentAmount = FDCHelper.ZERO;
		for(int i=0;i<listEntrys.size();i++) {
			PurchasePayListEntryInfo entry = (PurchasePayListEntryInfo)listEntrys.get(i);
			IRow row = payTable.addRow();			
			row.setTreeLevel(0);
			BigDecimal amount = entry.getApAmount();
			row.getCell("moneyName").setValue(entry.getMoneyDefine());
			row.getCell("apAmount").setValue(entry.getAppAmount());
			addApAmount = addApAmount.add(amount==null?FDCHelper.ZERO:amount);
			row.getCell("actPayAmount").setValue(entry.getActRevAmount());
			addActPayAmount = addActPayAmount.add(entry.getActPayAmount()==null?FDCHelper.ZERO:entry.getActPayAmount());
			row.getCell("refundmentAmount").setValue(entry.getHasRefundmentAmount());
			addRefundmentAmount = addRefundmentAmount.add(entry.getRefundmentAmount()==null?FDCHelper.ZERO:entry.getRefundmentAmount());
			row.getCell("currency").setValue(entry.getCurrency());
			row.getCell("term").setValue(entry.getAppDate());
		}
		//其他付款明细 xin_wang 2010.0919
		PurchaseElsePayListEntryCollection elsePayListEntrys = (PurchaseElsePayListEntryCollection)allInfoMap.get("elsePayListEntry");
		if(elsePayListEntrys!=null&&elsePayListEntrys.size()>0){
			CRMHelper.sortCollection(elsePayListEntrys, "seq", true);
			for(int i=0;i<elsePayListEntrys.size();i++) {
				PurchaseElsePayListEntryInfo entry = (PurchaseElsePayListEntryInfo)elsePayListEntrys.get(i);
				IRow row = payTable.addRow();
				if(payTable.getRowCount()<1){//只有其他明细，这种情况不能，对业务不确定 保险起见 xin_wang 2010.09.19
					row.setTreeLevel(0);
				}
				BigDecimal amount = entry.getApAmount();
				row.getCell("moneyName").setValue(entry.getMoneyDefine());
				row.getCell("apAmount").setValue(entry.getAppAmount());
				addApAmount = addApAmount.add(amount==null?FDCHelper.ZERO:amount);
				row.getCell("actPayAmount").setValue(entry.getActRevAmount());
				addActPayAmount = addActPayAmount.add(entry.getActPayAmount()==null?FDCHelper.ZERO:entry.getActPayAmount());
				row.getCell("refundmentAmount").setValue(entry.getHasRefundmentAmount());
				addRefundmentAmount = addRefundmentAmount.add(entry.getRefundmentAmount()==null?FDCHelper.ZERO:entry.getRefundmentAmount());
				row.getCell("currency").setValue(entry.getCurrency());
				row.getCell("term").setValue(entry.getAppDate());
			}
		}
		//增加合计行
		KDTFootManager footRowManager= payTable.getFootManager();
		IRow footRow = null;
		footRowManager = new KDTFootManager(payTable);
		footRowManager.addFootView();
		payTable.setFootManager(footRowManager);
		footRow= footRowManager.addFootRow(0);
		footRow.setUserObject("FDC_PARAM_TOTALCOST");
		footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		payTable.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
		payTable.getIndexColumn().setWidth(60);
		footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
		footRow.getCell("apAmount").setValue(addApAmount);
		footRow.getCell("actPayAmount").setValue(addActPayAmount);
		footRow.getCell("refundmentAmount").setValue(addRefundmentAmount);
		footRowManager.addIndexText(0, "合计");  
	}
	
	private void addBizTable() {
		final KDTable bizTable = initBizTable();
		bizTable.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				try {
					if (e.getButton() == 1 && e.getClickCount() == 2) {
						if (e.getType() != 1)
							return;

						IRow row = bizTable.getRow(e.getRowIndex());
						Object bizObj = row.getUserObject();
						showView(bizObj);
					}
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
				}
			}
		});
		bizTable.getStyleAttributes().setLocked(true);
		
		bizTable.setName("Bizness");
		this.tabBizInfo.add(bizTable, "业务");
	}

	/**
	 * 针对“房间回收”功能，在销售控制下增加“房间回收记录“的页签，显示操作历史记录，记录操作时间、操作人。该页签只在“房间回收”功能操作后显示，如操作多次，显示多条记录
	 * @param roomInfoMap
	 * @author xingli
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void addRecoverHistoryTable(RoomInfo roomInfo) throws BOSException, EASBizException {
		IRoomRecoverHistory historyService=RoomRecoverHistoryFactory.getRemoteInstance();
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",roomInfo.getId().toString()));
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("caoZuoRen.*");
		ev.setFilter(filter);
		ev.setSelector(selector);
		RoomRecoverHistoryCollection coll = historyService.getRoomRecoverHistoryCollection(ev);
		if (coll.size()<=0) {
			return;
		}
//		this.getUIContext().clear();
		final KDTable historyTable = new KDTable();
//		historyTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//		historyTable.getTreeColumn().setDepth(2);
		IColumn column = historyTable.addColumn();
		column.setKey("operationRen");
		column = historyTable.addColumn();
		column.setKey("operationDate");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		IRow headRow = historyTable.addHeadRow();
		headRow.getCell("operationRen").setValue("操作人");
		headRow.getCell("operationDate").setValue("操作时间");
 
		
		Map payMap = new TreeMap();
		for (int i = 0; i < coll.size(); i++) {
			  RoomRecoverHistoryInfo historyEntry = coll.get(i);
			payMap.put(historyEntry.getId().toString(), historyEntry);
		}
		Set set = payMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			RoomRecoverHistoryInfo entry = (RoomRecoverHistoryInfo) payMap.get(key);
			Date operationDate = entry.getOperatingDate();
			UserInfo userInfo=entry.getCaoZuoRen();
			IRow row = historyTable.addRow();
			row.setTreeLevel(0);
			row.getCell("operationRen").setValue(userInfo);
			row.getCell("operationDate").setValue(operationDate);
		}
		historyTable.getStyleAttributes().setLocked(true);
		this.tabBizInfo.add(historyTable, "房间回收记录");
	}

	private void showBizTable(int comInde) {
		if(allInfoMap.get("PurchaseCollection")!=null) return;
		
		RoomInfo room = (RoomInfo) this.editData;
		try {
			Map tempMap = RoomFactory.getRemoteInstance().getRoomInfoCollectionMap(room, 
					"PurchaseCollection PurchaseChangeCollection PurchaseChangeCustomerCollection RoomSignContractCollection RoomKeepDownBillCollection ChangeRoomCollection BillAdjustCollection QuitRoomCollection SincerityPurchaseCollection");
			allInfoMap.putAll(tempMap);
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}
		
		PurchaseCollection purchases = (PurchaseCollection)allInfoMap.get("PurchaseCollection");
		PurchaseChangeCollection purchaseChanges = (PurchaseChangeCollection)allInfoMap.get("PurchaseChangeCollection");
		PurchaseChangeCustomerCollection purchaseChangeCustomers = (PurchaseChangeCustomerCollection)allInfoMap.get("PurchaseChangeCustomerCollection");
		RoomSignContractCollection roomSignContracts = (RoomSignContractCollection)allInfoMap.get("RoomSignContractCollection");  //根据房间ID获得该房间的签约记录
		RoomKeepDownBillCollection keepDownBills = (RoomKeepDownBillCollection)allInfoMap.get("RoomKeepDownBillCollection"); //根据房间ID获得该房间的保留记录
		ChangeRoomCollection changeRooms = (ChangeRoomCollection)allInfoMap.get("ChangeRoomCollection");  //根据原房间查询相应的换房单
		BillAdjustCollection adjusts = (BillAdjustCollection)allInfoMap.get("BillAdjustCollection");
		QuitRoomCollection quits = (QuitRoomCollection)allInfoMap.get("QuitRoomCollection");
		SincerityPurchaseCollection sinColl = (SincerityPurchaseCollection)allInfoMap.get("SincerityPurchaseCollection");
		
		KDTable bizTable = (KDTable)this.tabBizInfo.getComponentAt(comInde);		
		List list = parseBizData(purchases, purchaseChanges, purchaseChangeCustomers, roomSignContracts, keepDownBills, quits, changeRooms, adjusts ,sinColl);
		fillBizTable(bizTable, list);
	}
	


	private void showView(Object bizObj) throws UIException {
		if (bizObj == null) {
			logger.warn("怎么会为null呢,仔细检查.");
			return;
		}
		String id = null;
		Class viewClazz = null;
		UIContext uiContext = new UIContext(this);
		
		if (bizObj instanceof RoomKeepDownBillInfo) {
			RoomKeepDownBillInfo keepDownBillInfo = (RoomKeepDownBillInfo) bizObj;
			id = keepDownBillInfo.getId().toString();
			viewClazz = RoomKeepDownBillEditUI.class;
		} else if (bizObj instanceof PurchaseInfo) {
			PurchaseInfo purchaseInfo = (PurchaseInfo) bizObj;
			id = purchaseInfo.getId().toString();
			viewClazz = PurchaseEditUI.class;
		} else if (bizObj instanceof PurchaseChangeInfo) {
			PurchaseChangeInfo changeInfo = (PurchaseChangeInfo) bizObj;
			id = changeInfo.getId().toString();
			viewClazz = PurchaseChangeBillEditUI.class;
		} else if (bizObj instanceof PurchaseChangeCustomerInfo) {
			PurchaseChangeCustomerInfo cusChange = (PurchaseChangeCustomerInfo) bizObj;
			id = cusChange.getId().toString();
			viewClazz = PurchaseChangeCustomerEditUI.class;
		} else if (bizObj instanceof RoomSignContractInfo) {
			RoomSignContractInfo signContractInfo = (RoomSignContractInfo) bizObj;
			id = signContractInfo.getId().toString();
			viewClazz = RoomSignContractEditUI.class;
		} else if (bizObj instanceof QuitRoomInfo) {
			QuitRoomInfo quitRoomInfo = (QuitRoomInfo) bizObj;
			id = quitRoomInfo.getId().toString();
			viewClazz = QuitRoomEditUI.class;
		} else if (bizObj instanceof ChangeRoomInfo) {
			ChangeRoomInfo changeRoom = (ChangeRoomInfo) bizObj;
			id = changeRoom.getId().toString();
			viewClazz = ChangeRoomEditUI.class;
		}else if(bizObj instanceof BillAdjustInfo){
			BillAdjustInfo adjustInfo = (BillAdjustInfo)bizObj;
			id = adjustInfo.getId().toString();
			viewClazz = BillAdjustEditUI.class;
		}else if(bizObj instanceof SincerityPurchaseInfo){
			SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo)bizObj;
			id = sinInfo.getId().toString();
			viewClazz = SincerityPurchaseEditUI.class;
			uiContext.put("sellProject", sinInfo.getSellProject());
		}else {
			logger.warn("不支持的bizType:" + bizObj.getClass().getName());
			return;
		}
		
		uiContext.put("ID", id);
		uiContext.put("src","SELLCONTROL");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(viewClazz.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}

	private void fillBizTable(KDTable bizTable, List list) {
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof RoomKeepDownBillInfo) {
				RoomKeepDownBillInfo keepDownBillInfo = (RoomKeepDownBillInfo) obj;
				addRowOnBizTable(bizTable, keepDownBillInfo, 0);
			}else if(obj instanceof SincerityPurchaseInfo){
				SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo)obj;
				addRowOnBizTable(bizTable, sinInfo, 0);
			}else if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				PurchaseInfo purchase = (PurchaseInfo) objs[0];
				addRowOnBizTable(bizTable, purchase, 0);
				List bizList = (List) objs[1];
				for (int j = 0; j < bizList.size(); j++) {
					addRowOnBizTable(bizTable, bizList.get(j), 1);
				}
			}
		}
	}

	/**
	 * 将各业务记录整合 TODO 待注释重构
	 * 
	 * @param changeRooms
	 * @param adjusts
	 * 增加诚意认购单的处理
	 * */
	private List parseBizData(PurchaseCollection purchases, PurchaseChangeCollection purchaseChanges, PurchaseChangeCustomerCollection purchaseChangeCustomers,
			RoomSignContractCollection roomSignContracts, RoomKeepDownBillCollection keepDownBills, QuitRoomCollection quits, 
			ChangeRoomCollection changeRooms, BillAdjustCollection adjusts,SincerityPurchaseCollection sinColl) {
		// 做容错处理
		if (purchases == null) {
			logger.warn("数据错误数据错误!!!!!!!!!!!");
			purchases = new PurchaseCollection();
		}
		if (purchaseChanges == null) {
			logger.warn("数据错误数据错误!!!!!!!!!!!");
			purchaseChanges = new PurchaseChangeCollection();
		}
		if (purchaseChangeCustomers == null) {
			logger.warn("数据错误数据错误!!!!!!!!!!!");
			purchaseChangeCustomers = new PurchaseChangeCustomerCollection();
		}
		if (roomSignContracts == null) {
			logger.warn("数据错误数据错误!!!!!!!!!!!");
			roomSignContracts = new RoomSignContractCollection();
		}
		if (keepDownBills == null) {
			logger.warn("数据错误数据错误!!!!!!!!!!!");
			keepDownBills = new RoomKeepDownBillCollection();
		}
		if (adjusts == null) {
			logger.warn("数据错误数据错误!!!!!!!!!!!");
			adjusts = new BillAdjustCollection();
		}

		List list = new ArrayList();
		
		if(sinColl!=null) {
			for(int i=0;i<sinColl.size();i++) {
				SincerityPurchaseInfo sinInfo = sinColl.get(i);
				list.add(sinInfo);
			}
		}
		
		for (int i = 0; i < purchases.size(); i++) {
			PurchaseInfo purchaseInfo = purchases.get(i);
			String purchaseId = purchaseInfo.getId().toString();

			List bizList = new ArrayList();
			for (int j = 0; j < purchaseChanges.size(); j++) {
				PurchaseChangeInfo changeInfo = purchaseChanges.get(j);
				if (purchaseId.equals(changeInfo.getPurchase().getId().toString())) {
					changeInfo.setPurchase(purchaseInfo);
					bizList.add(changeInfo);
				}
			}

			for (int j = 0; j < purchaseChangeCustomers.size(); j++) {
				PurchaseChangeCustomerInfo cusChange = purchaseChangeCustomers.get(j);
				if (purchaseId.equals(cusChange.getPurchase().getId().toString())) {
					cusChange.setPurchase(purchaseInfo);
					bizList.add(cusChange);
				}
			}

			for (int j = 0; j < roomSignContracts.size(); j++) {
				RoomSignContractInfo signContractInfo = roomSignContracts.get(j);
				if (purchaseId.equals(signContractInfo.getPurchase().getId().toString())) {
					signContractInfo.setPurchase(purchaseInfo);
					bizList.add(signContractInfo);
				}
			}

			if (quits != null) {
				for (int j = 0; j < quits.size(); j++) {
					QuitRoomInfo quitRoomInfo = quits.get(j);
					if (purchaseId.equals(quitRoomInfo.getPurchase().getId().toString())) {
						quitRoomInfo.setPurchase(purchaseInfo);
						bizList.add(quitRoomInfo);
					}
				}
			}

			if (changeRooms != null) {
				for (int j = 0; j < changeRooms.size(); j++) {
					ChangeRoomInfo changeRoom = changeRooms.get(j);
					if (changeRoom.getOldPurchase() != null && purchaseId.equals(changeRoom.getOldPurchase().getId().toString())) {
						changeRoom.setOldPurchase(purchaseInfo);
						bizList.add(changeRoom);
					}
				}
			}

			for (int j = 0; j < adjusts.size(); j++) {
				BillAdjustInfo adjust = adjusts.get(j);
				if (adjust.getPurchase() != null && purchaseId.equals(adjust.getPurchase().getId().toString())) {
					adjust.setPurchase(purchaseInfo);
					bizList.add(adjust);
				}
			}

			Collections.sort(bizList, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					Date date0 = getCompareDateOfBizObj(arg0);
					Date date1 = getCompareDateOfBizObj(arg1);
					if (date0 == null || date1 == null) {
						return 0;
					}
					return date0.compareTo(date1);
				}
			});

			list.add(new Object[] { purchaseInfo, bizList });
		}
		for (int i = 0; i < keepDownBills.size(); i++) {
			RoomKeepDownBillInfo keepDownBillInfo = keepDownBills.get(i);
			RoomKeepDownBillInfo keepDownCopy = (RoomKeepDownBillInfo) keepDownBillInfo.clone();
			keepDownCopy.setCancelDate(null);
			list.add(keepDownCopy);

			Date cancelDate = keepDownBillInfo.getCancelDate();
			if (cancelDate != null) {
				keepDownBillInfo.setDescription(null);
				list.add(keepDownBillInfo);
			}
		}

		Collections.sort(list, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Date date0 = getCompareDate(arg0);
				Date date1 = getCompareDate(arg1);
				if (date0 == null || date1 == null) {
					return 0;
				}
				return date1.compareTo(date0);
			}
		});

		return list;
	}

	private Date getCompareDate(Object obj) {
		if (obj instanceof RoomKeepDownBillInfo) {
			RoomKeepDownBillInfo roomKeepDown = (RoomKeepDownBillInfo) obj;
			if (roomKeepDown.getCancelDate() != null) {
				return roomKeepDown.getCancelDate();
			} else {
				return roomKeepDown.getBizDate();
			}
		}else if(obj instanceof SincerityPurchaseInfo){
			SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo)obj;
			return sinInfo.getBookDate();
		}else if (obj instanceof Object[]) {
			Object[] objs = (Object[]) obj;
			PurchaseInfo purchase = (PurchaseInfo) objs[0];
			// 如果是预定,按预定日期去排序
			if (purchase.getPrePurchaseDate() != null) {
				return purchase.getPrePurchaseDate();
			} else {
				return purchase.getPurchaseDate();
			}
		} else {
			return null;
		}
	}

	private Date getCompareDateOfBizObj(Object obj) {
		if (obj instanceof PurchaseChangeInfo) {
			return ((PurchaseChangeInfo) obj).getChangeDate();
		} else if (obj instanceof PurchaseChangeCustomerInfo) {
			return ((PurchaseChangeCustomerInfo) obj).getBizDate();
		} else if (obj instanceof RoomSignContractInfo) {
			return ((RoomSignContractInfo) obj).getSignDate();
		} else if (obj instanceof QuitRoomInfo) {
			return ((QuitRoomInfo) obj).getQuitDate();
		} else if (obj instanceof ChangeRoomInfo) {
			return ((ChangeRoomInfo) obj).getChangeDate();
		} else if (obj instanceof BillAdjustInfo) {
			return ((BillAdjustInfo) obj).getBizDate();
		} else if(obj instanceof SincerityPurchaseInfo){
			return ((SincerityPurchaseInfo) obj).getBookDate();
		}else {
			return null;
		}
	}

	/**
	 * 在bizTable中加入一条业务记录
	 * */
	private void addRowOnBizTable(final KDTable bizTable, Object bizObj, int level) {
		if (bizObj == null) {
			logger.warn("逻辑错误,请仔细检查.");
			return;
		}
		IRow row = bizTable.addRow();
		row.setTreeLevel(level);
		row.setUserObject(bizObj);

		String space = "";
		for (int i = 0; i < level; i++) {
			space += "  ";
		}
		if (bizObj instanceof RoomKeepDownBillInfo) {
			RoomKeepDownBillInfo keepDownBillInfo = (RoomKeepDownBillInfo) bizObj;
			row.getCell("bizNumber").setValue(keepDownBillInfo.getNumber());
			// row.getCell("customer").setValue(keepDownBillInfo.get(""));
			// row.getCell("amount").setValue(keepDownBillInfo.get(""));
			// row.getCell("state").setValue(keepDownBillInfo.get);
			row.getCell("description").setValue(keepDownBillInfo.getDescription());
			if (keepDownBillInfo.getCancelDate() != null) {
				row.setUserObject(keepDownBillInfo);
				row.getCell("bizType").setValue("取消保留");
				row.getCell("occurDate").setValue(keepDownBillInfo.getCancelDate());
			} else {
				row.getCell("bizType").setValue("保留");
				row.getCell("occurDate").setValue(keepDownBillInfo.getBizDate());
				row.getCell("salesman").setValue(keepDownBillInfo.getHandler().getName());
			}
			row.getCell("createUser").setValue(keepDownBillInfo.getCreator()==null ? "" : keepDownBillInfo.getCreator().getName());
		} else if (bizObj instanceof PurchaseInfo) {
			PurchaseInfo purchaseInfo = (PurchaseInfo) bizObj;
			row.getCell("bizType").setValue(space + "认购");
			row.getCell("bizNumber").setValue(purchaseInfo.getNumber());
			row.getCell("state").setValue(purchaseInfo.getPurchaseState());
			row.getCell("occurDate").setValue(purchaseInfo.getPrePurchaseDate() != null ? purchaseInfo.getPrePurchaseDate() : purchaseInfo.getPurchaseDate());
			row.getCell("customer").setValue(purchaseInfo.getCustomerNames());
			row.getCell("salesman").setValue(purchaseInfo.getSalesman().getName());
			row.getCell("amount").setValue(purchaseInfo.getDealAmount());
			row.getCell("description").setValue(purchaseInfo.getDescription());
			row.getCell("createUser").setValue(purchaseInfo.getCreator() == null ? "" : purchaseInfo.getCreator().getName());
		} else if (bizObj instanceof PurchaseChangeInfo) {
			PurchaseChangeInfo changeInfo = (PurchaseChangeInfo) bizObj;
			row.getCell("bizType").setValue(space + "认购变更");
			row.getCell("bizNumber").setValue(changeInfo.getNumber());
			row.getCell("state").setValue(changeInfo.getState());
			row.getCell("occurDate").setValue(changeInfo.getChangeDate());
			row.getCell("customer").setValue(changeInfo.getPurchase().getCustomerNames());
			row.getCell("salesman").setValue(changeInfo.getPurchase().getSalesman().getName());
			row.getCell("description").setValue(changeInfo.getDescription());
			row.getCell("createUser").setValue(changeInfo.getCreator() == null ? "" : changeInfo.getCreator().getName());
		} else if (bizObj instanceof PurchaseChangeCustomerInfo) {
			PurchaseChangeCustomerInfo cusChange = (PurchaseChangeCustomerInfo) bizObj;
			row.getCell("bizType").setValue(space + "认购更名");
			row.getCell("bizNumber").setValue(cusChange.getNumber());
			row.getCell("state").setValue(cusChange.getState());
			row.getCell("occurDate").setValue(cusChange.getBizDate());
			row.getCell("customer").setValue(cusChange.getPurchase().getCustomerNames());
			row.getCell("salesman").setValue(cusChange.getPurchase().getSalesman().getName());
			row.getCell("description").setValue(cusChange.getDescription());
			row.getCell("createUser").setValue(cusChange.getCreator() == null ? "" : cusChange.getCreator().getName());
		} else if (bizObj instanceof RoomSignContractInfo) {
			RoomSignContractInfo signContractInfo = (RoomSignContractInfo) bizObj;
			row.getCell("bizType").setValue(space + "签约");
			row.getCell("bizNumber").setValue(signContractInfo.getNumber());
			row.getCell("state").setValue(signContractInfo.isIsBlankOut() ? "已作废" : "签约");
			row.getCell("occurDate").setValue(signContractInfo.getSignDate());
			row.getCell("customer").setValue(signContractInfo.getPurchase().getCustomerNames());
			row.getCell("salesman").setValue(signContractInfo.getPurchase().getSalesman().getName());
			if(signContractInfo.getPurchase()!=null && signContractInfo.getPurchase().getDealAmount()!=null){
				row.getCell("amount").setValue(signContractInfo.getPurchase().getDealAmount());
			}
			row.getCell("description").setValue(signContractInfo.getDescription());
			row.getCell("createUser").setValue(signContractInfo.getCreator() == null ? "" : signContractInfo.getCreator().getName());
		} else if (bizObj instanceof QuitRoomInfo) {
			QuitRoomInfo quitRoomInfo = (QuitRoomInfo) bizObj;
			row.getCell("bizType").setValue(space + "退房");
			row.getCell("bizNumber").setValue(quitRoomInfo.getNumber());
			row.getCell("state").setValue(quitRoomInfo.getState());
			row.getCell("occurDate").setValue(quitRoomInfo.getQuitDate());
			row.getCell("customer").setValue(quitRoomInfo.getPurchase().getCustomerNames());
			row.getCell("salesman").setValue(quitRoomInfo.getPurchase().getSalesman().getName());
			// row.getCell("amount").setValue();
			row.getCell("description").setValue(quitRoomInfo.getDescription());
			row.getCell("createUser").setValue(quitRoomInfo.getCreator() == null ? "" : quitRoomInfo.getCreator().getName());
		} else if (bizObj instanceof ChangeRoomInfo) {
			ChangeRoomInfo changeRoom = (ChangeRoomInfo) bizObj;
			row.getCell("bizType").setValue(space + "换房");
			row.getCell("bizNumber").setValue(changeRoom.getNumber());
			row.getCell("state").setValue(changeRoom.getState());
			row.getCell("occurDate").setValue(changeRoom.getChangeDate());
			row.getCell("customer").setValue(changeRoom.getOldPurchase().getCustomerNames());
			row.getCell("salesman").setValue(changeRoom.getOldPurchase().getSalesman().getName());// TODO
																									// 这里有问题的
																									// .
			// row.getCell("amount").setValue();
			row.getCell("description").setValue(changeRoom.getDescription());
			row.getCell("createUser").setValue(changeRoom.getCreator() == null ? "" : changeRoom.getCreator().getName());
		} else if (bizObj instanceof BillAdjustInfo) {
			BillAdjustInfo adjust = (BillAdjustInfo) bizObj;
			row.getCell("bizType").setValue(space + "调整");
			row.getCell("bizNumber").setValue(adjust.getNumber());
			row.getCell("state").setValue(adjust.getState());
			row.getCell("occurDate").setValue(adjust.getBizDate());
			row.getCell("customer").setValue(adjust.getPurchase().getCustomerNames());
			row.getCell("salesman").setValue(adjust.getPurchase().getSalesman().getName());
			// row.getCell("amount").setValue();
			row.getCell("description").setValue(adjust.getDescription());
			row.getCell("createUser").setValue(adjust.getCreator() == null ? "" : adjust.getCreator().getName());
		} else if(bizObj instanceof SincerityPurchaseInfo) {
			SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo)bizObj;
			row.getCell("bizType").setValue(space + "诚意认购");
			row.getCell("bizNumber").setValue(sinInfo.getNumber());
			row.getCell("state").setValue(sinInfo.getSincerityState());
			row.getCell("occurDate").setValue(sinInfo.getBookDate());
			String customer =null;
			// by tim_gao 预约排号结构改变 2010-06-15
			if(null!=sinInfo.getCustomer())
			{
				for(int i = 0 ; i < sinInfo.getCustomer().size();i++){
					customer = sinInfo.getCustomer().get(i).getCustomer().getName()+";";
					
				}
			}
			row.getCell("customer").setValue(sinInfo.getCustomer()==null?"":customer);
			row.getCell("salesman").setValue(sinInfo.getSalesman()==null?"":sinInfo.getSalesman().getName());
			row.getCell("amount").setValue(sinInfo.getSincerityAmount());
			row.getCell("description").setValue(sinInfo.getDescription());
			row.getCell("createUser").setValue(sinInfo.getCreator() == null ? "" : sinInfo.getCreator().getName());
		}
	}

	private KDTable initBizTable() {
		KDTable bizTable = new KDTable();
		bizTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		bizTable.getTreeColumn().setDepth(2);
		IColumn column = bizTable.addColumn();
		column.setKey("bizType");
		column = bizTable.addColumn();
		column.setKey("bizNumber");
		column = bizTable.addColumn();
		column.setKey("state");
		column = bizTable.addColumn();
		column.setKey("customer");
		column = bizTable.addColumn();
		column.setKey("occurDate");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		column = bizTable.addColumn();
		column.setKey("salesman");
		column = bizTable.addColumn();
		column.setKey("createUser");
		column = bizTable.addColumn();
		column.setKey("amount");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column = bizTable.addColumn();
		column.setKey("description");

		IRow headRow = bizTable.addHeadRow();
		headRow.getCell("bizType").setValue("业务类型");
		headRow.getCell("bizNumber").setValue("业务编号");
		headRow.getCell("state").setValue("状态");
		// 2010-4-8  lixin   调整客户与发起日期顺序
		headRow.getCell("customer").setValue("客户");
		headRow.getCell("occurDate").setValue("发起日期");
		headRow.getCell("salesman").setValue("销售顾问");
		headRow.getCell("createUser").setValue("制单人");
		headRow.getCell("amount").setValue("金额");
		headRow.getCell("description").setValue("说明");

		return bizTable;
	}


	private void totalFlowTableClick(final KDTable totalFlowTable, com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		try {
			if (e.getButton() == 1 && e.getClickCount() == 2) {
				if (e.getType() != 1)
					return;

				IRow row = totalFlowTable.getRow(e.getRowIndex());
				BizFlowEnum bizFlow = (BizFlowEnum) row.getCell("biz").getValue();
				RoomInfo room = (RoomInfo) editData;
				if (bizFlow.equals(BizFlowEnum.Purchase)) {
					if (room.getLastPurchase() != null) {
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", room.getLastPurchase().getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseEditUI.class.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				} else if (bizFlow.equals(BizFlowEnum.Sign)) {
					if (room.getLastSignContract() != null) {
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", room.getLastSignContract().getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				} else if (bizFlow.equals(BizFlowEnum.AraaCompensation)) {
					if (room.getLastAreaCompensate() != null) {
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", room.getLastAreaCompensate().getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomAreaCompensateEditUI.class.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				} else if (bizFlow.equals(BizFlowEnum.JoinIn)) {
					RoomJoinInfo joinIn = SHEHelper.getRoomJoinIn(room);
					if (joinIn != null) {
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", joinIn.getId());
						uiContext.put("roomId", room.getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomJoinEditUI.class.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				} else if (bizFlow.equals(BizFlowEnum.PropertyBook)) {
					RoomPropertyBookInfo propertyBook = SHEHelper.getRoomPropertyBook(room);
					if (propertyBook != null) {
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", propertyBook.getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomPropertyBookEditUI.class.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				}
			}
		} catch (Exception exc) {
			handUIException(exc);
		} finally {
		}
	}

	
	private void addTotalFlowTable() {
		if (this.purchase == null) {
			return;
		}
		final KDTable totalFlowTable = new KDTable();
		totalFlowTable.checkParsed();
		totalFlowTable.getStyleAttributes().setLocked(true);
		totalFlowTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		totalFlowTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
				totalFlowTableClick(totalFlowTable, e);
			}
		});

		IColumn column = totalFlowTable.addColumn();
		column.setKey("biz");
		column = totalFlowTable.addColumn();
		column.setKey("apDate");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		column = totalFlowTable.addColumn();
		column.setKey("value");
		column = totalFlowTable.addColumn();
		column.setKey("actDate");
		formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		IRow headRow = totalFlowTable.addHeadRow();
		headRow.getCell("biz").setValue("业务名称");
		headRow.getCell("apDate").setValue("期限");
		headRow.getCell("value").setValue("是否完成");
		headRow.getCell("actDate").setValue("完成日期");

		totalFlowTable.setName("TotalExplore");
		this.tabBizInfo.add(totalFlowTable, "总览");
	}
	
	
	private void showTotalFlowTable(int comIndex) {
		if (purchase == null) return;
		
		KDTable totalFlowTable = (KDTable)this.tabBizInfo.getComponentAt(comIndex);
		if(totalFlowTable==null) return;

		if(allInfoMap.get("BizListEntryCollection")!=null) return;

		RoomInfo room = (RoomInfo) this.editData;
		
		Map tempMap = fillRowsToTotalFlowTable(totalFlowTable,room,purchase,sign);
		allInfoMap.putAll(tempMap);
	}

	
	/**
	 * 这个函数还被认购单编辑界面的总览调用到了 
	 */
	public static Map fillRowsToTotalFlowTable(KDTable totalFlowTable,RoomInfo room,PurchaseInfo purchase,RoomSignContractInfo sign){
		Map tempMap = new HashMap();
		try {
			tempMap = RoomFactory.getRemoteInstance().getRoomInfoCollectionMap(room, "BizListEntryCollection PurchasePayListEntryCollection elsePayListEntry " +
					"RoomJoinInfo RoomAreaCompensateInfo RoomLoanInfo RoomAccFundInfo RoomPropertyBookInfo");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		BizListEntryCollection bizLists = (BizListEntryCollection)tempMap.get("BizListEntryCollection");
		if(bizLists==null) return tempMap;
		PurchasePayListEntryCollection listEntrys = (PurchasePayListEntryCollection)tempMap.get("PurchasePayListEntryCollection");;
		RoomJoinInfo join = (RoomJoinInfo)tempMap.get("RoomJoinInfo");
		RoomAreaCompensateInfo areaCompensate = (RoomAreaCompensateInfo)tempMap.get("RoomAreaCompensateInfo");
		RoomLoanInfo loan = (RoomLoanInfo)tempMap.get("RoomLoanInfo");
		RoomLoanInfo accFund = (RoomLoanInfo)tempMap.get("RoomAccFundInfo");
		RoomPropertyBookInfo propertyBook = (RoomPropertyBookInfo)tempMap.get("RoomPropertyBookInfo");
		
		PurchasePayListEntryInfo firstMoneyEntry = null;
		PurchasePayListEntryInfo loanEntry = null;
		PurchasePayListEntryInfo accEntry = null;
		PurchasePayListEntryInfo lastEntry = null;
		for (int i = 0; i < listEntrys.size(); i++) {
			PurchasePayListEntryInfo entry = listEntrys.get(i);
			MoneyTypeEnum motyEnum = entry.getMoneyDefine().getMoneyType();
			if (motyEnum == null) 	continue;			
			if (motyEnum.equals(MoneyTypeEnum.FisrtAmount)) {
				firstMoneyEntry = entry;
			}else if (motyEnum.equals(MoneyTypeEnum.LoanAmount)) {
				loanEntry = entry;
			}else if (motyEnum.equals(MoneyTypeEnum.AccFundAmount)) {
				accEntry = entry;
			}
			if (i == listEntrys.size() - 1) {
				lastEntry = entry;
			}
		}

		
		for (int i = 0; i < bizLists.size(); i++) {
			IRow row = totalFlowTable.addRow();
			BizListEntryInfo bizInfo = bizLists.get(i);
			BizFlowEnum bizFlow = bizInfo.getBizFlow();
			row.getCell("biz").setValue(bizFlow);
			
			if(bizFlow.equals(BizFlowEnum.Sign)){
				if(purchase!=null){
					Date curDate = null;
					try {
						if(purchase!=null) {
							PurchaseInfo temPurInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
									"select planSignDate where id='"+purchase.getId()+"'");
							curDate = temPurInfo.getPlanSignDate();
						}
						if(curDate!=null){
							row.getCell("apDate").setValue(curDate);
						}else{
							BizTimeEnum bizTime = bizInfo.getBizTime();			
							if (bizTime.equals(BizTimeEnum.AppTime)) {
								row.getCell("apDate").setValue(bizInfo.getAppDate());
							} else {
								Date date = null;
								try {
									if(purchase!=null) {
										PurchaseInfo temPurInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
												"select purchaseDate,prePurchaseDate where id='"+purchase.getId()+"'");
										date = temPurInfo.getPurchaseDate();
										if(date==null) date = temPurInfo.getPrePurchaseDate();
									}
								} catch (Exception e) {
									ExceptionHandler.handle(e);
								} 
								if(curDate!=null) {
									int monthLimit = bizInfo.getMonthLimit();
									int dayLimit = bizInfo.getDayLimit();
									Calendar cal = Calendar.getInstance();
									cal.setTime(date);
									cal.add(Calendar.MONTH, monthLimit);
									cal.add(Calendar.DATE, dayLimit);
									date = cal.getTime();
									row.getCell("apDate").setValue(date);
								}
							}
						}
					} catch (Exception e) {
						ExceptionHandler.handle(e);
					} 
					
				}
			}else{
				BizTimeEnum bizTime = bizInfo.getBizTime();			
				if (bizTime.equals(BizTimeEnum.AppTime)) {
					row.getCell("apDate").setValue(bizInfo.getAppDate());
				} else {
					Date curDate = null;
					try {
						if(purchase!=null) {
							PurchaseInfo temPurInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
									"select purchaseDate,prePurchaseDate where id='"+purchase.getId()+"'");
							curDate = temPurInfo.getPurchaseDate();
							if(curDate==null) curDate = temPurInfo.getPrePurchaseDate();
						}
					} catch (Exception e) {
						ExceptionHandler.handle(e);
					} 
					if(curDate!=null) {
						int monthLimit = bizInfo.getMonthLimit();
						int dayLimit = bizInfo.getDayLimit();
						Calendar cal = Calendar.getInstance();
						cal.setTime(curDate);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						curDate = cal.getTime();
						row.getCell("apDate").setValue(curDate);
					}
				}
			}
			
			row.getCell("value").setValue(Boolean.FALSE);
			row.getCell("actDate").setValue(null);
			if (bizFlow.equals(BizFlowEnum.Purchase)) {
				// 预定的不能算完成认购 ---zhicheng_jin 090320
				if (purchase != null && !RoomSellStateEnum.PrePurchase.equals(room.getSellState())) {
					row.getCell("value").setValue(Boolean.TRUE);
					row.getCell("actDate").setValue(room.getToPurchaseDate());
				}

			} else if (bizFlow.equals(BizFlowEnum.Sign)) {
				//签约单状态时审批才能算签约完成  xin_wang 2010.09.01
				if (sign != null&&FDCBillStateEnum.AUDITTED.equals(sign.getState())) {
					row.getCell("value").setValue(Boolean.TRUE);
					row.getCell("actDate").setValue(sign.getSignDate());
				}
			} else if (bizFlow.equals(BizFlowEnum.AraaCompensation)) {
				//面积补差单据状态只有是补差审批、补差已收款 才算是完成面积补差 xin_wang 2010.09.02
				if (areaCompensate != null){
					if(RoomCompensateStateEnum.COMAUDITTED.equals(areaCompensate.getCompensateState())||RoomCompensateStateEnum.COMRECEIVED.equals(areaCompensate.getCompensateState())){
						row.getCell("value").setValue(Boolean.TRUE);
						row.getCell("actDate").setValue(areaCompensate.getCompensateDate());
					}
				}
			} else if (bizFlow.equals(BizFlowEnum.PayFirstTerm)) {
				if (firstMoneyEntry == null) {
					row.getCell("value").setValue("无首期");
				} else {
					BigDecimal apAmount = firstMoneyEntry.getAppAmount();
					BigDecimal actAmount = firstMoneyEntry.getActRevAmount();
					if (apAmount == null) {
						apAmount = FDCHelper.ZERO;
					}
					if (actAmount == null) {
						actAmount = FDCHelper.ZERO;
					}
					if (apAmount.compareTo(actAmount) <= 0) {
						row.getCell("value").setValue(Boolean.TRUE);
						row.getCell("actDate").setValue(firstMoneyEntry.getActRevDate());
					}
				}
			} else if (bizFlow.equals(BizFlowEnum.JoinIn)) {
				if (join != null && join.getJoinEndDate() != null) {
					row.getCell("value").setValue(Boolean.TRUE);
					row.getCell("actDate").setValue(join.getJoinEndDate());
				}
			} else if (bizFlow.equals(BizFlowEnum.Loan)) {
				if(room.getRoomLoanState()!=null && room.getRoomLoanState().equals(RoomLoanStateEnum.LOANED))
					row.getCell("value").setValue(Boolean.TRUE);
				else
					row.getCell("value").setValue(Boolean.FALSE);
				
				if (loan != null) {
					row.getCell("actDate").setValue(loan.getProcessLoanDate());
				}
			} else if (bizFlow.equals(BizFlowEnum.AccFund)) {
				if(room.getRoomACCFundState()!=null && room.getRoomACCFundState().equals(RoomACCFundStateEnum.FUND))
					row.getCell("value").setValue(Boolean.TRUE);
				else
					row.getCell("value").setValue(Boolean.FALSE);
                
				if (accFund != null) {
					row.getCell("actDate").setValue(accFund.getProcessLoanDate());                //公积金日期数据还是存在按揭日期里
				}
			} else if (bizFlow.equals(BizFlowEnum.LoanInAcct)) {
				if (loanEntry == null) {
					row.getCell("value").setValue(Boolean.FALSE);
				} else {
					BigDecimal apAmount = loanEntry.getAppAmount();
					BigDecimal actAmount = loanEntry.getActRevAmount();
					if (apAmount == null) {
						apAmount = FDCHelper.ZERO;
					}
					if (actAmount == null) {
						actAmount = FDCHelper.ZERO;
					}
					if (apAmount.compareTo(actAmount) <= 0) {
						row.getCell("value").setValue(Boolean.TRUE);
						row.getCell("actDate").setValue(loanEntry.getActRevDate());
					}
				}
			} else if (bizFlow.equals(BizFlowEnum.AccFundInAcct)) {
				if (accEntry == null) {
					row.getCell("value").setValue(Boolean.FALSE);
				} else {
					BigDecimal apAmount = accEntry.getAppAmount();
					BigDecimal actAmount = accEntry.getActRevAmount();
					if (apAmount == null) {
						apAmount = FDCHelper.ZERO;
					}
					if (actAmount == null) {
						actAmount = FDCHelper.ZERO;
					}
					if (apAmount.compareTo(actAmount) <= 0) {
						row.getCell("value").setValue(Boolean.TRUE);
						row.getCell("actDate").setValue(accEntry.getActRevDate());
					}
				}
			} else if (bizFlow.equals(BizFlowEnum.PropertyBook)) {
				if(room.getRoomBookState()!=null && room.getRoomBookState().equals(RoomBookStateEnum.BOOKED))
					row.getCell("value").setValue(Boolean.TRUE);
				else
					row.getCell("value").setValue(Boolean.FALSE);
				
				if (propertyBook != null) {
					row.getCell("actDate").setValue(propertyBook.getTransactDate());
				}
			} else if (bizFlow.equals(BizFlowEnum.PayAll)) {
				if (lastEntry == null) {
					row.getCell("value").setValue(Boolean.FALSE);
				} else {
					BigDecimal apAmount = lastEntry.getAppAmount();
					BigDecimal actAmount = lastEntry.getActRevAmount();
					if (apAmount == null) {
						apAmount = FDCHelper.ZERO;
					}
					if (actAmount == null) {
						actAmount = FDCHelper.ZERO;
					}
					if (apAmount.compareTo(actAmount) == 0) {
						row.getCell("value").setValue(Boolean.TRUE);
						row.getCell("actDate").setValue(lastEntry.getActRevDate());
					}
				}
			}
		}
		return tempMap;
		
	}
	
	
	
	/**
	 * output class constructor
	 */
	public RoomFullInfoUI() throws Exception {
		super();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("id");
		sels.add("buildingArea");
		sels.add("roomArea");
		sels.add("actualBuildingArea");
		sels.add("actualRoomArea");
		sels.add("number");
		sels.add("roomNo");

		sels.add("apportionArea");
		sels.add("balconyArea");
		sels.add("floorHeight");
		sels.add("direction.name");

		sels.add("sight.name");
		sels.add("roomModel.name");
		sels.add("buildingProperty.name");
		sels.add("houseProperty");

		sels.add("roomPrice");
		sels.add("buildPrice");
		sels.add("standardTotalAmount");
		sels.add("dealPrice");
		sels.add("dealTotalAmount");
		sels.add("isCalByRoomArea");
		sels.add("sellState");

		sels.add("lastPurchase.id");
		sels.add("lastPurchase.payType.id");

		sels.add("lastSignContract.id");
		sels.add("lastSignContract.signDate");
		//麻烦帮我取下签约单的状态  xin_wang 2010.09.02
		sels.add("lastSignContract.state");
		
		sels.add("toPurchaseDate");
		sels.add("roomCompensateState");
		sels.add("roomLoanState");
		sels.add("roomACCFundState");
		sels.add("roomBookState");

		return sels;
	}

	private void setTextFormat(KDFormattedTextField text) {
		text.setRemoveingZeroInDispaly(false);
		text.setRemoveingZeroInEdit(false);
		text.setPrecision(2);
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setSupportedEmpty(true);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);

		this.chkIsCalByRoom.setEnabled(false);

		setTextFormat(txtBuildingArea);
		setTextFormat(txtRoomArea);
		setTextFormat(txtApportionArea);
		setTextFormat(txtBalconyArea);
		setTextFormat(txtActualBuildingArea);

		setTextFormat(txtActualBuildingArea);
		setTextFormat(txtActualRoomArea);
		setTextFormat(txtStandardPrice);
		setTextFormat(txtStandardTotalAmount);
		setTextFormat(txtDealPrice);

		setTextFormat(txtDealTotalAmount);
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	/*
	 * class BizAboutRoom{ private String bizType; private String number;
	 * private String state; private String occurDate; private String customer;
	 * private String salesman; private String amount; private String
	 * description; public String getAmount() { return amount; } public void
	 * setAmount(String amount) { this.amount = amount; } public String
	 * getBizType() { return bizType; } public void setBizType(String bizType) {
	 * this.bizType = bizType; } public String getCustomer() { return customer;
	 * } public void setCustomer(String customer) { this.customer = customer; }
	 * public String getDescription() { return description; } public void
	 * setDescription(String description) { this.description = description; }
	 * public String getNumber() { return number; } public void setNumber(String
	 * number) { this.number = number; } public String getOccurDate() { return
	 * occurDate; } public void setOccurDate(String occurDate) { this.occurDate
	 * = occurDate; } public String getSalesman() { return salesman; } public
	 * void setSalesman(String salesman) { this.salesman = salesman; } public
	 * String getState() { return state; } public void setState(String state) {
	 * this.state = state; } }
	 * 
	 * class RoomBizView implements KDTMouseListener{ private Class viewClazz;
	 * private String keyName = "id";
	 * 
	 * public void tableClicked(KDTMouseEvent e) { try { if (e.getButton() == 1
	 * && e.getClickCount() == 2) { int rowIndex = e.getRowIndex(); if (rowIndex
	 * == -1) { return; }
	 * 
	 * IRow row = bizTable.getRow(rowIndex); PurchaseInfo pur = (PurchaseInfo)
	 * row.getUserObject(); UIContext uiContext = new UIContext(this);
	 * uiContext.put("ID", pur.getId()); IUIWindow uiWindow =
	 * UIFactory.createUIFactory( UIFactoryName.MODEL).create(
	 * PurchaseEditUI.class.getName(), uiContext, null, "VIEW");
	 * uiWindow.show(); } } catch (Exception exc) { handUIException(exc); }
	 * finally { } } }
	 */
}