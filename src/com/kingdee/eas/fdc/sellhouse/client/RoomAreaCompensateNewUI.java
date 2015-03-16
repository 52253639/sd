/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessDefInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.define.ProcessDef;
import com.kingdee.bos.workflow.monitor.client.BasicShowWfDefinePanel;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListCollection;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisCollection;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisFactory;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class RoomAreaCompensateNewUI extends AbstractRoomAreaCompensateNewUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6101364547204544018L;
	private static final Logger logger = CoreUIObject
			.getLogger(RoomAreaCompensateNewUI.class);
	private boolean isFirst = false;
	private SellProjectInfo sellProject = null;
	private BuildingInfo building = null;
	private BuildingUnitInfo unit = null;
	private boolean isDeleAfter = false;
	private boolean isOld = false;

	/**
	 * output class constructor
	 */
	public RoomAreaCompensateNewUI() throws Exception {
		super();
	}

	private void initScheme() throws EASBizException, BOSException {

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));

		if (this.getUIContext().get("sellProject") != null) {
			SellProjectInfo sell = (SellProjectInfo) this.getUIContext().get(
					"sellProject");
			Set id=new HashSet();
			id = SHEManageHelper.getAllParentSellProjectCollection(sell,id);
		
			if (id != null && id.size() > 0) {
				filterInfo.getFilterItems().add(
					new FilterItemInfo("sellProject.id",FDCTreeHelper.getStringFromSet(id),
							CompareType.INNER));
			}else{
				filterInfo.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null,
								CompareType.EQUALS));
			}
		
		}else{
			Set id=new HashSet();
			id = SHEManageHelper.getAllParentSellProjectCollection(this.editData.getSellProject(),id);
		
			if (id != null && id.size() > 0) {
				filterInfo.getFilterItems().add(
					new FilterItemInfo("sellProject.id",FDCTreeHelper.getStringFromSet(id),
							CompareType.INNER));
			}else{
				filterInfo.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null,
								CompareType.EQUALS));
			}
		
		}

		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.CompensateSchemeForSHEQuery";
		SHEHelper.initF7(this.prmtScheme, queryInfo, filterInfo);
	}

	private void initTransactor() {

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("type", new Integer("20"),
						CompareType.EQUALS));

		filterInfo.getFilterItems().add(
				new FilterItemInfo("isDelete", Boolean.FALSE,
						CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isLocked", Boolean.FALSE,
						CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isForbidden", Boolean.FALSE,
						CompareType.EQUALS));
		String queryInfo = "com.kingdee.eas.base.permission.app.F7UserQuery";
		SHEHelper.initF7(this.prmtTransactor, queryInfo, filterInfo);

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		if (this.txtDescription.getText() != null
				&& !"".equals(this.txtDescription.getText())) {
			this.editData.setDescription(this.txtDescription.getText());
		}

		if (((RoomAreaCompensateTypeEnum) this.cbcType.getSelectedItem())
				.equals(RoomAreaCompensateTypeEnum.BYSCHEME)) {
			this.editData.setIsCalcByScheme(true);
		} else {
			this.editData.setIsCalcByScheme(false);
			this.editData.setScheme(null);
		}
		if (this.kdpAppDate.getValue() != null) {
			this.editData.setAppDate((Date) this.kdpAppDate.getValue());
		}
		this.editData.getRoomList().clear();

		for (int i = 0; i < this.kdtRoomList.getRowCount(); i++) {
			IRow row = this.kdtRoomList.getRow(i);
			if (row == null) {
				continue;
			}
			CompensateRoomListInfo roomList =(CompensateRoomListInfo) row.getUserObject();
			if(roomList==null){
				roomList=new CompensateRoomListInfo();
			}
			roomList.setIsNullify(false);
			RoomInfo room = new RoomInfo();
			room.setId(BOSUuid
					.read(row.getCell("roomId").getValue().toString()));
			SignManageInfo sign = new SignManageInfo();
			sign.setId(BOSUuid
					.read(row.getCell("signId").getValue().toString()));

			if (row.getCell("actAmount").getValue() != null) {
				roomList.setActAmount((BigDecimal) row.getCell("actAmount")
						.getValue());
			}
			if (row.getCell("attAmount").getValue() != null) {
				roomList.setAttAmount((BigDecimal) row.getCell("attAmount")
						.getValue());
			}
			if (row.getCell("mainAmount").getValue() != null) {
				roomList.setMainAmount((BigDecimal) row.getCell("mainAmount")
						.getValue());
			}
			if (row.getCell("referAmount").getValue() != null) {
				roomList.setRefAmount((BigDecimal) row.getCell("referAmount")
						.getValue());
			}
			if (row.getCell("lastAmount").getValue() != null) {
				roomList.setLastAmount((BigDecimal) row.getCell("lastAmount")
						.getValue());
			}
			if (row.getCell("rate").getValue() != null) {
				roomList.setCompensateRate((BigDecimal) row.getCell("rate")
						.getValue());
			}
			roomList.setRoom(room);
			roomList.setSign(sign);

			this.editData.getRoomList().add(roomList);
		}
	}

	public void loadFields() {
		super.loadFields();

		this.isFirst = true;

		if (this.editData.isIsCalcByScheme()) {
			this.contScheme.setVisible(true);
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYSCHEME);
			this.actionCalc.setEnabled(true);
		} else {
			this.contScheme.setVisible(false);
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYHAND);
			this.actionCalc.setEnabled(false);
		}

		this.txtDescription.setText(this.editData.getDescription());

		this.kdpAppDate.setValue(this.editData.getAppDate());

		this.kdtRoomList.removeRows();

		this.pkCreateTime.setValue(this.editData.getCreateTime());

		if (this.editData.getId() != null
				&& this.editData.getCompensateState().equals(
						RoomCompensateStateEnum.COMSTOP)) {
			loadCompensateRoomHis();
		} else {
			CompensateRoomListCollection roomColl = this.editData.getRoomList();
			for (int i = 0; i < roomColl.size(); i++) {
				CompensateRoomListInfo RoomListInfo = roomColl.get(i);
				kdtRoomList.checkParsed();
				IRow row = kdtRoomList.addRow(i);
				row.setUserObject(RoomListInfo);
				showLoadRoomInfo(row, RoomListInfo, this.editData
						.getCompensateState());
			}
		}

		if (this.editData.getId() != null) {
			if (this.editData.getAuditor() == null
					|| this.editData.getAuditTime() == null) {
				try {
					RoomAreaCompensateInfo roomInfo = RoomAreaCompensateFactory
							.getRemoteInstance().getRoomAreaCompensateInfo(
									"select id,auditor.id,auditor.name,auditor.number,auditTime where id='"
											+ this.editData.getId().toString()
											+ "'");
					if (roomInfo != null) {
						this.prmtAuditor.setValue(roomInfo.getAuditor());
						this.pkAuditTime.setValue(roomInfo.getAuditTime());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void loadCompensateRoomHis() {

		try {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("head.id"));
			selector.add(new SelectorItemInfo("room.id"));
			selector.add(new SelectorItemInfo("room.name"));
			selector.add(new SelectorItemInfo("room.buildingArea"));
			selector.add(new SelectorItemInfo("room.roomArea"));
			selector.add(new SelectorItemInfo("room.actualBuildingArea"));
			selector.add(new SelectorItemInfo("room.actualRoomArea"));
			selector.add(new SelectorItemInfo("sign.id"));
			selector.add(new SelectorItemInfo("sign.customerNames"));
			selector.add(new SelectorItemInfo("sign.id"));
			selector.add(new SelectorItemInfo("sign.contractBuildPrice"));
			selector.add(new SelectorItemInfo("sign.contractRoomPrice"));
			selector.add(new SelectorItemInfo("sign.contractTotalAmount"));
			selector.add(new SelectorItemInfo("sign.sellAmount"));
			selector.add(new SelectorItemInfo("mainAmount"));
			selector.add(new SelectorItemInfo("refAmount"));
			selector.add(new SelectorItemInfo("attAmount"));
			selector.add(new SelectorItemInfo("lastAmount"));
			selector.add(new SelectorItemInfo("actAmount"));
			selector.add(new SelectorItemInfo("compensateRate"));
			view.setSelector(selector);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", this.editData.getId()
							.toString(), CompareType.EQUALS));
			view.setFilter(filter);
			CompensateRoomListHisCollection coll = CompensateRoomListHisFactory
					.getRemoteInstance().getCompensateRoomListHisCollection(
							view);

			for (int i = 0; i < coll.size(); i++) {
				CompensateRoomListHisInfo RoomListInfo = coll.get(i);
				kdtRoomList.checkParsed();
				IRow row = kdtRoomList.addRow(i);
				showLoadRoomInfoHis(row, RoomListInfo, this.editData
						.getCompensateState());
			}
		} catch (BOSException e) {
			logger.error(e.getMessage() + "获得补差房间历史失败!");
		}
	}

	private void showLoadRoomInfo(IRow row,
			CompensateRoomListInfo RoomListInfo, RoomCompensateStateEnum state) {
		if (RoomListInfo == null) {
			return;
		}

		if (state != null) {
			row.getCell("state").setValue(state);
		}
		RoomInfo room = RoomListInfo.getRoom();
		if (room != null) {
			if (room.getId() != null) {
				row.getCell("roomId").setValue(room.getId());
			}

			if (room.getName() != null) {
				row.getCell("roomName").setValue(room.getName());
			}

			if (room.getBuildingArea() != null) {
				row.getCell("buildingArea").setValue(room.getBuildingArea());
			}

			if (room.getRoomArea() != null) {
				row.getCell("roomArea").setValue(room.getRoomArea());
			}

			if (room.getActualBuildingArea() != null) {
				row.getCell("actBuildArea").setValue(
						room.getActualBuildingArea());
			}

			if (room.getActualRoomArea() != null) {
				row.getCell("actRoomArea").setValue(room.getActualRoomArea());
			}

		}

		SignManageInfo signInfo = RoomListInfo.getSign();
		if (signInfo != null) {
			if (signInfo.getContractBuildPrice() != null) {
				row.getCell("conBuildPrice").setValue(
						signInfo.getContractBuildPrice());
			}
			if (signInfo.getContractRoomPrice() != null) {
				row.getCell("conRoomPrice").setValue(
						signInfo.getContractRoomPrice());
			}
			if (signInfo.getContractTotalAmount() != null) {
				row.getCell("conAmount").setValue(
						signInfo.getContractTotalAmount());
			}
			if (signInfo.getId() != null) {
				row.getCell("signId").setValue(signInfo.getId());
			}
			if(signInfo.getCustomerNames()!=null){
				row.getCell("customer").setValue(signInfo.getCustomerNames());
			}

		}

		if (RoomListInfo.getActAmount() != null) {
			row.getCell("actAmount").setValue(RoomListInfo.getActAmount());
		}
		if (RoomListInfo.getActAmount() != null) {
			row.getCell("attAmount").setValue(RoomListInfo.getAttAmount());
		}
		if (RoomListInfo.getMainAmount() != null) {
			row.getCell("mainAmount").setValue(RoomListInfo.getMainAmount());
		}
		if (RoomListInfo.getRefAmount() != null) {
			row.getCell("referAmount").setValue(RoomListInfo.getRefAmount());
		}
		if (RoomListInfo.getLastAmount() != null) {
			row.getCell("lastAmount").setValue(RoomListInfo.getLastAmount());
		}
		if (RoomListInfo.getCompensateRate() != null) {
			row.getCell("rate").setValue(RoomListInfo.getCompensateRate());
		}
	}

	private void showLoadRoomInfoHis(IRow row,
			CompensateRoomListHisInfo RoomListInfo,
			RoomCompensateStateEnum state) {
		if (RoomListInfo == null) {
			return;
		}

		if (state != null) {
			row.getCell("state").setValue(state);
		}
		RoomInfo room = RoomListInfo.getRoom();
		if (room != null) {
			if (room.getId() != null) {
				row.getCell("roomId").setValue(room.getId());
			}

			if (room.getName() != null) {
				row.getCell("roomName").setValue(room.getName());
			}

			if (room.getBuildingArea() != null) {
				row.getCell("buildingArea").setValue(room.getBuildingArea());
			}

			if (room.getRoomArea() != null) {
				row.getCell("roomArea").setValue(room.getRoomArea());
			}

			if (room.getActualBuildingArea() != null) {
				row.getCell("actBuildArea").setValue(
						room.getActualBuildingArea());
			}

			if (room.getActualRoomArea() != null) {
				row.getCell("actRoomArea").setValue(room.getActualRoomArea());
			}

		}

		SignManageInfo signInfo = RoomListInfo.getSign();
		if (signInfo != null) {
			if (signInfo.getContractBuildPrice() != null) {
				row.getCell("conBuildPrice").setValue(
						signInfo.getContractBuildPrice());
			}
			if (signInfo.getContractRoomPrice() != null) {
				row.getCell("conRoomPrice").setValue(
						signInfo.getContractRoomPrice());
			}
			if (signInfo.getContractTotalAmount() != null) {
				row.getCell("conAmount").setValue(
						signInfo.getContractTotalAmount());
			}
			if (signInfo.getId() != null) {
				row.getCell("signId").setValue(signInfo.getId());
			}

		}

		if (RoomListInfo.getActAmount() != null) {
			row.getCell("actAmount").setValue(RoomListInfo.getActAmount());
		}
		if (RoomListInfo.getActAmount() != null) {
			row.getCell("attAmount").setValue(RoomListInfo.getAttAmount());
		}
		if (RoomListInfo.getMainAmount() != null) {
			row.getCell("mainAmount").setValue(RoomListInfo.getMainAmount());
		}
		if (RoomListInfo.getRefAmount() != null) {
			row.getCell("referAmount").setValue(RoomListInfo.getRefAmount());
		}
		if (RoomListInfo.getLastAmount() != null) {
			row.getCell("lastAmount").setValue(RoomListInfo.getLastAmount());
		}
		if (RoomListInfo.getCompensateRate() != null) {
			row.getCell("rate").setValue(RoomListInfo.getCompensateRate());
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.txtNumber.setRequired(true);
		this.prmtScheme.setRequired(true);
		this.prmtTransactor.setRequired(true);
		this.pkCompensateDate.setRequired(true);
		this.prmtScheme.setEditable(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.actionCalc.setEnabled(false);
		this.btnWorkFlow.setEnabled(true);

		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.btnCalc.setIcon(EASResource.getIcon("imgTbtn_manualcount"));
		this.btnWorkFlow.setIcon(EASResource.getIcon("imgTbtn_flowchart"));

		this.prmtScheme.setEnabled(true);
		if (this.getUIContext().get("ByHand") != null) {
			this.contScheme.setVisible(false);
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYHAND);
		} else {
			this.contScheme.setVisible(true);
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYSCHEME);
		}

		initScheme();
		initTransactor();

		if (OprtState.ADDNEW.equals(this.getOprtState())
				|| OprtState.EDIT.equals(this.getOprtState())) {
			this.btnChooseRoom.setEnabled(true);
			this.btnDeleteRoom.setEnabled(true);
		}

		kdtRoomList.getStyleAttributes().setLocked(true);

		initTable();

		this.kdtRoomList.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent e) {
			}

			public void editStarted(KDTEditEvent e) {
			}

			public void editStarting(KDTEditEvent e) {
			}

			public void editStopped(KDTEditEvent e) {
				if (e.getColIndex() == kdtRoomList.getColumn("mainAmount")
						.getColumnIndex()) {
					try {
						int rowIndex = e.getRowIndex();
						calcPirceForMain(rowIndex, true);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
				} else if (e.getColIndex() == kdtRoomList
						.getColumn("attAmount").getColumnIndex()) {
					int rowIndex = e.getRowIndex();
					calcPirceForMain(rowIndex, true);
				} else if (e.getColIndex() == kdtRoomList
						.getColumn("actAmount").getColumnIndex()) {
					int rowIndex = e.getRowIndex();
					calcPirceForAct(rowIndex, true);
				}
			}

			public void editStopping(KDTEditEvent e) {
			}

			public void editValueChanged(KDTEditEvent e) {
			}

		});
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
		String[] fields=new String[this.kdtRoomList.getColumnCount()];
		for(int i=0;i<this.kdtRoomList.getColumnCount();i++){
			fields[i]=this.kdtRoomList.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.kdtRoomList,fields);
	}

	protected void initOldData(IObjectValue dataObject) {
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			// super.initOldData(dataObject);
		} else {
			if (!this.isOld) {
				super.initOldData(dataObject);
			}
		}

	}

	private void calcPirceForMain(int rowIndex, boolean b) {
		IRow row = this.kdtRoomList.getRow(rowIndex);
		if (row == null) {
			return;
		}
		BigDecimal mainAmount = FDCHelper.ZERO;
		BigDecimal attAmount = FDCHelper.ZERO;
		BigDecimal actAmount = FDCHelper.ZERO;
		BigDecimal conAmount = FDCHelper.ZERO;
		BigDecimal lastAmount = FDCHelper.ZERO;
		if (row.getCell("mainAmount").getValue() != null) {
			mainAmount = (BigDecimal) row.getCell("mainAmount").getValue();
		}
		if (row.getCell("attAmount").getValue() != null) {
			attAmount = (BigDecimal) row.getCell("attAmount").getValue();
		}
		if (row.getCell("conAmount").getValue() != null) {
			conAmount = (BigDecimal) row.getCell("conAmount").getValue();
		}
		actAmount = FDCHelper.add(mainAmount, attAmount);
		// row.getCell("referAmount").setValue(actAmount);
		row.getCell("actAmount").setValue(actAmount);
		lastAmount = FDCHelper.add(actAmount, conAmount);
		row.getCell("lastAmount").setValue(lastAmount);
	}

	private void calcPirceForAct(int rowIndex, boolean b) {
		IRow row = this.kdtRoomList.getRow(rowIndex);
		if (row == null) {
			return;
		}
		BigDecimal conAmount = FDCHelper.ZERO;
		BigDecimal actAmount = FDCHelper.ZERO;
		BigDecimal lastAmount = FDCHelper.ZERO;
		if (row.getCell("actAmount").getValue() != null) {
			actAmount = (BigDecimal) row.getCell("actAmount").getValue();
		}
		if (row.getCell("conAmount").getValue() != null) {
			conAmount = (BigDecimal) row.getCell("conAmount").getValue();
		}

		lastAmount = FDCHelper.add(actAmount, conAmount);
		row.getCell("lastAmount").setValue(lastAmount);
	}

	private void initTable() {

		if (OprtState.ADDNEW.equals(this.getOprtState())
				|| OprtState.EDIT.equals(this.getOprtState())) {
			KDFormattedTextField formattedTextField = new KDFormattedTextField(
					KDFormattedTextField.DECIMAL);
			formattedTextField.setPrecision(2);
			formattedTextField.setSupportedEmpty(true);
			formattedTextField.setNegatived(true);
			ICellEditor numberEditor = new KDTDefaultCellEditor(
					formattedTextField);
			if (this.kdtRoomList.getColumn("actAmount") != null) {
				this.kdtRoomList.getColumn("actAmount").getStyleAttributes()
						.setLocked(false);
				this.kdtRoomList.getColumn("actAmount").getStyleAttributes()
						.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
				this.kdtRoomList.getColumn("actAmount").getStyleAttributes()
						.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
				this.kdtRoomList.getColumn("actAmount").setEditor(numberEditor);
			}
			if (((RoomAreaCompensateTypeEnum) this.cbcType.getSelectedItem())
					.equals(RoomAreaCompensateTypeEnum.BYSCHEME)) {
				if (this.kdtRoomList.getColumn("attAmount") != null) {
					this.kdtRoomList.getColumn("attAmount")
							.getStyleAttributes().setLocked(true);
					this.kdtRoomList.getColumn("attAmount")
							.getStyleAttributes().setNumberFormat(
									FDCClientHelper.KDTABLE_NUMBER_FTM);
					this.kdtRoomList.getColumn("attAmount").setEditor(
							numberEditor);
					this.kdtRoomList.getColumn("attAmount")
							.getStyleAttributes().setBackground(null);
				}

				if (this.kdtRoomList.getColumn("mainAmount") != null) {
					this.kdtRoomList.getColumn("mainAmount")
							.getStyleAttributes().setLocked(true);
					this.kdtRoomList.getColumn("mainAmount")
							.getStyleAttributes().setNumberFormat(
									FDCClientHelper.KDTABLE_NUMBER_FTM);
					this.kdtRoomList.getColumn("mainAmount").setEditor(
							numberEditor);
					this.kdtRoomList.getColumn("mainAmount")
							.getStyleAttributes().setBackground(null);
				}
			} else {
				if (this.kdtRoomList.getColumn("attAmount") != null) {
					this.kdtRoomList.getColumn("attAmount")
							.getStyleAttributes().setLocked(false);
					this.kdtRoomList.getColumn("attAmount")
							.getStyleAttributes().setNumberFormat(
									FDCClientHelper.KDTABLE_NUMBER_FTM);
					this.kdtRoomList.getColumn("attAmount")
							.getStyleAttributes().setBackground(
									FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
					this.kdtRoomList.getColumn("attAmount").setEditor(
							numberEditor);
				}

				if (this.kdtRoomList.getColumn("mainAmount") != null) {
					this.kdtRoomList.getColumn("mainAmount")
							.getStyleAttributes().setLocked(false);
					this.kdtRoomList.getColumn("mainAmount")
							.getStyleAttributes().setNumberFormat(
									FDCClientHelper.KDTABLE_NUMBER_FTM);
					this.kdtRoomList.getColumn("mainAmount")
							.getStyleAttributes().setBackground(
									FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
					this.kdtRoomList.getColumn("mainAmount").setEditor(
							numberEditor);

				}
			}
		}
	}

	protected void btnChooseRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnChooseRoom_actionPerformed(e);
		UIContext uiContext = new UIContext(this);

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			uiContext.put("isMultiSelect", Boolean.TRUE);
			uiContext.put("selectSellProject", this.sellProject);
			uiContext.put("selectBuilding", this.building);
			uiContext.put("selectUnit", this.unit);
		} else if (OprtState.EDIT.equals(this.getOprtState())) {
			uiContext.put("isMultiSelect", Boolean.TRUE);
			uiContext.put("selectSellProject", this.editData.getSellProject());
			uiContext.put("selectBuilding", this.editData.getBuilding());
			uiContext.put("selectUnit", this.editData.getUnit());
		}

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CompensateSeletRoomUI.class.getName(), uiContext, null,
						null);
		uiWindow.show();
		Object[] object = null;

		CompensateSeletRoomUI cusEditUI = (CompensateSeletRoomUI) uiWindow
				.getUIObject();
		if (cusEditUI.getUIContext().get(CompensateSeletRoomUI.SELECTROOM) != null) {
			object = (Object[]) cusEditUI.getUIContext().get(
					CompensateSeletRoomUI.SELECTROOM);

			loadRoomInfo(object);
		}

		initTable();

	}

	private void loadRoomInfo(Object[] object) {

		if (object == null) {
			return;
		}

		this.kdtRoomList.checkParsed();
		this.kdtRoomList.removeRows();
		for (int i = 0; i < object.length; i++) {
			Map map = (Map) object[i];
			IRow row = this.kdtRoomList.addRow();
			if (map.get("name") != null) {
				row.getCell("roomName").setValue(map.get("name"));
			}

			if (map.get("customer") != null) {
				row.getCell("customer").setValue(map.get("customer"));
			}

			if (map.get("state") != null) {
				row.getCell("state").setValue(map.get("state"));
			} else {
				row.getCell("state").setValue("未补差");
			}

			if (map.get("buildArea") != null) {
				row.getCell("buildingArea").setValue(map.get("buildArea"));
			}

			if (map.get("roomArea") != null) {
				row.getCell("roomArea").setValue(map.get("roomArea"));
			}

			if (map.get("signManage.contractBuildPrice") != null) {
				row.getCell("conBuildPrice").setValue(
						map.get("signManage.contractBuildPrice"));
			}

			if (map.get("signManage.contractRoomPrice") != null) {
				row.getCell("conRoomPrice").setValue(
						map.get("signManage.contractRoomPrice"));
			}

			if (map.get("actualBuildingArea") != null) {
				row.getCell("actBuildArea").setValue(
						map.get("actualBuildingArea"));
			}

			if (map.get("actualRoomArea") != null) {
				row.getCell("actRoomArea").setValue(map.get("actualRoomArea"));
			}

			if (map.get("signManage.contractTotalAmount") != null) {
				row.getCell("conAmount").setValue(
						map.get("signManage.contractTotalAmount"));
			}

			if (map.get("signManage.sellAmount") != null) {
				row.getCell("lastAmount").setValue(
						map.get("signManage.sellAmount"));
			}

			if (map.get("id") != null) {
				row.getCell("roomId").setValue(map.get("id"));
			}

			if (map.get("signManage.id") != null) {
				row.getCell("signId").setValue(map.get("signManage.id"));
			}

		}

	}

	public void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e) {

		int activeRowIndex = this.kdtRoomList.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			MsgBox.showInfo("请选择行");
			return;
		}
		if (this.editData.getId() != null) {
			if(this.kdtRoomList.getRow(activeRowIndex).getUserObject()!=null
					&&((CompensateRoomListInfo)this.kdtRoomList.getRow(activeRowIndex).getUserObject()).getId()!=null){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sourceBillId", ((CompensateRoomListInfo)this.kdtRoomList.getRow(activeRowIndex).getUserObject()).getId()));
				try {
					if(RoomTransferFactory.getRemoteInstance().exists(filter)){
						FDCMsgBox.showWarning(this,"房间已结转，不能进行删除操作！");
						SysUtil.abort();
					}
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}
			this.kdtRoomList.removeRow(activeRowIndex);
		} else {
			this.kdtRoomList.removeRow(activeRowIndex);
		}

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// /setOprtState(OprtState.ADDNEW);
		// if(!this.isDeleAfter){
		// handleCodingRule();
		// }
		super.actionAddNew_actionPerformed(e);
		this.prmtAuditor.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		this.prmtTransactor.setEditable(false);
		this.pkAuditTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.btnChooseRoom.setEnabled(true);
		this.btnDeleteRoom.setEnabled(true);
//		handleCodingRule();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
	}

	public void onShow() throws Exception {
		super.onShow();
		this.prmtAuditor.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		this.prmtTransactor.setEditable(false);
		this.pkAuditTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.prmtScheme.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		if (OprtState.VIEW.equals(this.getOprtState())
				|| OprtState.EDIT.equals(this.getOprtState())) {
			if (this.editData.isIsCalcByScheme()) {
				this.contScheme.setVisible(true);
				this.cbcType
						.setSelectedItem(RoomAreaCompensateTypeEnum.BYSCHEME);
			} else {
				this.contScheme.setVisible(false);
				this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYHAND);
			}
		}
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
//			handleCodingRule();
			this.actionCalc.setEnabled(true);
		}

		if (OprtState.EDIT.equals(this.getOprtState())) {
//			handleCodingRule();
			this.actionCalc.setEnabled(true);
		}

		this.isFirst = false;

		if (this.editData.getId() != null) {
			if (CRMHelper.isRunningWorkflow(this.editData.getId().toString())) {
				this.lockUIForViewStatus();
			}
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				editAuditPayColumn(); // 在改类中实现以下方法
			}
		});

	}

	/**
	 * 描述：面积补差工作流中可以修改补差完成日期字段
	 * <p>
	 * 
	 * @author liang_ren969 @date:2010-5-21
	 *         <p>
	 *         <br>
	 *         举例：
	 * 
	 *         <pre>
	 *    SwingUtilities.invokeLater(new Runnable(){&lt;p&gt;
	 *   public void run() {&lt;p&gt;
	 *    editPayAuditColumn();&lt;p&gt;
	 *   }
	 * <p>
	  *    });
	 */
	private void editAuditPayColumn() {

		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null
				&& isFromWorkflow.toString().equals("true")
				&& getOprtState().equals(OprtState.EDIT)
				&& actionSubmit.isVisible()
				&& (editData.getCompensateState() == RoomCompensateStateEnum.COMSUBMIT || editData
						.getCompensateState() == RoomCompensateStateEnum.COMAUDITTING)) {

			// 首先锁定界面上所有的空间
			this.lockUIForViewStatus();

			// 首先给控件个访问的权限，然后设置是否可编辑就可以了！不能使用unloack方法，这样会使所用的控件处于edit的状态

			this.kdpAppDate
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.kdpAppDate.setEditable(true);
			this.actionSubmit.setEnabled(true);

		}

		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true")
				&& getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}

	public void actionChooseRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionChooseRoom_actionPerformed(e);
	}

	public void actionDeleteRoom_actionPerformed(ActionEvent e)
			throws Exception {

		if (this.editData.getId() != null) {
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory
					.getRemoteInstance().getRoomAreaCompensateInfo(
							"select id,name,compensateState where id='"
									+ this.editData.getId().toString() + "'");
			if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMSTOP)) {
				FDCMsgBox.showWarning(this, "单据已作废，不能删除!");
				this.abort();
			}
		}

		super.actionDeleteRoom_actionPerformed(e);
	}

	protected IObjectValue createNewData() {
		RoomAreaCompensateInfo info = new RoomAreaCompensateInfo();
		if (this.getUIContext().get("ByHand") != null) {
			info.setIsCalcByScheme(false);
			info.setCompensateType(RoomAreaCompensateTypeEnum.BYHAND);
		} else {
			info.setIsCalcByScheme(true);
			info.setCompensateType(RoomAreaCompensateTypeEnum.BYSCHEME);
		}
		info.setCompensateState(RoomCompensateStateEnum.COMSUBMIT);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		try {
			info.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
			info.setCompensateDate(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
			info.setAppDate(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}

		if (this.getUIContext().get("sellProject") != null) {
			info.setSellProject((SellProjectInfo) this.getUIContext().get(
					"sellProject"));
			this.sellProject = (SellProjectInfo) this.getUIContext().get(
					"sellProject");
		}
		if (this.getUIContext().get("selectBuilding") != null) {
			info.setBuilding((BuildingInfo) this.getUIContext().get(
					"selectBuilding"));
			this.building = (BuildingInfo) this.getUIContext().get(
					"selectBuilding");
		}
		if (this.getUIContext().get("selectUnit") != null) {
			info.setUnit((BuildingUnitInfo) this.getUIContext().get(
					"selectUnit"));
			this.unit = (BuildingUnitInfo) this.getUIContext()
					.get("selectUnit");
		}

		info.setTransactor(SysContext.getSysContext().getCurrentUserInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return info;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("isCalcByScheme"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("compensateType"));
		sic.add(new SelectorItemInfo("compensateState"));
		sic.add(new SelectorItemInfo("roomList.head.id"));
		sic.add(new SelectorItemInfo("roomList.room.id"));
		sic.add(new SelectorItemInfo("roomList.room.name"));
		sic.add(new SelectorItemInfo("roomList.room.buildingArea"));
		sic.add(new SelectorItemInfo("roomList.room.roomArea"));
		sic.add(new SelectorItemInfo("roomList.room.actualBuildingArea"));
		sic.add(new SelectorItemInfo("roomList.room.actualRoomArea"));
		sic.add(new SelectorItemInfo("roomList.sign.id"));
		sic.add(new SelectorItemInfo("roomList.sign.customerNames"));
		sic.add(new SelectorItemInfo("roomList.sign.id"));
		sic.add(new SelectorItemInfo("roomList.sign.contractBuildPrice"));
		sic.add(new SelectorItemInfo("roomList.sign.contractRoomPrice"));
		sic.add(new SelectorItemInfo("roomList.sign.contractTotalAmount"));
		sic.add(new SelectorItemInfo("roomList.sign.sellAmount"));
		sic.add(new SelectorItemInfo("roomList.mainAmount"));
		sic.add(new SelectorItemInfo("roomList.refAmount"));
		sic.add(new SelectorItemInfo("roomList.attAmount"));
		sic.add(new SelectorItemInfo("roomList.lastAmount"));
		sic.add(new SelectorItemInfo("roomList.actAmount"));
		sic.add(new SelectorItemInfo("roomList.compensateRate"));
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditor.number"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("sellProject.id"));
		sic.add(new SelectorItemInfo("sellProject.name"));
		sic.add(new SelectorItemInfo("sellProject.number"));
		sic.add(new SelectorItemInfo("unit.id"));
		sic.add(new SelectorItemInfo("unit.name"));
		sic.add(new SelectorItemInfo("unit.number"));
		sic.add(new SelectorItemInfo("building.id"));
		sic.add(new SelectorItemInfo("building.name"));
		sic.add(new SelectorItemInfo("building.number"));
		sic.add(new SelectorItemInfo("appDate"));
		sic.add(new SelectorItemInfo("CU.*"));
		return sic;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAreaCompensateFactory.getRemoteInstance();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		RoomAreaCompensateInfo info = RoomAreaCompensateFactory
				.getRemoteInstance().getRoomAreaCompensateInfo(
						"select id,name,compensateState where id='"
								+ this.editData.getId().toString() + "'");
		if (info.getCompensateState().equals(RoomCompensateStateEnum.COMSTOP)) {
			FDCMsgBox.showWarning(this, "单据已作废，不能修改!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMAUDITTED)) {
			FDCMsgBox.showWarning(this, "单据已审批，不能修改!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMAUDITTING)) {
			FDCMsgBox.showWarning(this, "单据审批中，不能修改!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMRECEIVED)) {
			FDCMsgBox.showWarning(this, "单据已收款，不能修改!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMRECEIVING)) {
			FDCMsgBox.showWarning(this, "单据收款中，不能修改!");
			this.abort();
		}
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());

		super.actionEdit_actionPerformed(e);
		if (OprtState.EDIT.equals(this.getOprtState())) {
			this.btnChooseRoom.setEnabled(true);
			this.btnDeleteRoom.setEnabled(true);
		}
		if (this.editData.isIsCalcByScheme()) {
			this.contScheme.setVisible(true);
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYSCHEME);
			this.actionCalc.setEnabled(true);
		} else {
			this.contScheme.setVisible(false);
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYHAND);
			this.actionCalc.setEnabled(false);
		}

		this.prmtAuditor.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		this.prmtTransactor.setEditable(false);
		this.pkAuditTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.initTable();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkCompensateDate);
		if (((RoomAreaCompensateTypeEnum) this.cbcType.getSelectedItem())
				.equals(RoomAreaCompensateTypeEnum.BYSCHEME)) {
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtScheme);
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtTransactor);
		FDCClientVerifyHelper.verifyEmpty(this, this.kdpAppDate);

//		Date appDate = (Date) this.kdpAppDate.getValue();
//		Date bookingDate = (Date) this.pkCompensateDate.getValue();
//		if (appDate != null && bookingDate != null) {
//			if (appDate.compareTo(bookingDate) <= 0) {
//				FDCMsgBox.showWarning(this, "应完成日期不能小于办理日期!");
//				this.abort();
//			}
//		}
		if (this.kdtRoomList.getRowCount() <= 0) {
			FDCMsgBox.showWarning(this, "请先选择房间!");
			this.abort();
		}
		checkAreaCompensateInfo();
		this.isOld = true;
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionChooseRoom.setEnabled(false);
			this.actionDeleteRoom.setEnabled(false);
		} else {
			this.actionChooseRoom.setEnabled(true);
			this.actionDeleteRoom.setEnabled(true);
			this.unLockUI();
		}
		if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		RoomAreaCompensateInfo bill = (RoomAreaCompensateInfo)editData;
    		if(editData!=null){
    			if(RoomCompensateStateEnum.COMAUDITTED.equals(bill.getCompensateState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
	}
	private void checkAreaCompensateInfo() {
		String error = "";
		if (((RoomAreaCompensateTypeEnum) this.cbcType.getSelectedItem())
				.equals(RoomAreaCompensateTypeEnum.BYHAND)) {
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYHAND);
			for (int i = 0; i < kdtRoomList.getRowCount(); i++) {
				IRow row = this.kdtRoomList.getRow(i);
				if (row == null) {
					continue;
				}

				if (row.getCell("mainAmount").getValue() == null) {
					error = error + "第" + i + "行主房间补差款为空!";
				}

				if (row.getCell("attAmount").getValue() == null) {
					error = error + "第" + i + "行附属房产补差款为空!";
				}
			}

			if (!"".equals(error) && error.length() > 0) {
				FDCMsgBox.showWarning(this, "补差信息不完整，请补充!");
				this.abort();
			}
		} else {
			for (int i = 0; i < kdtRoomList.getRowCount(); i++) {
				IRow row = this.kdtRoomList.getRow(i);
				if (row == null) {
					continue;
				}

				if (row.getCell("actAmount").getValue() == null) {
					error = error + "第" + i + "实际补差款为空!";
				}
			}

			if (!"".equals(error) && error.length() > 0) {
				FDCMsgBox.showWarning(this, "补差信息不完整，请补充!");
				this.abort();
			}
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

		RoomAreaCompensateInfo info = RoomAreaCompensateFactory
				.getRemoteInstance().getRoomAreaCompensateInfo(
						"select id,name,compensateState where id='"
								+ this.editData.getId().toString() + "'");
		if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMAUDITTED)) {
			FDCMsgBox.showWarning(this, "单据已审批，不能删除!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMAUDITTING)) {
			FDCMsgBox.showWarning(this, "单据审批中，不能删除!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMRECEIVED)) {
			FDCMsgBox.showWarning(this, "单据已收款，不能删除!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMRECEIVING)) {
			FDCMsgBox.showWarning(this, "单据收款中，不能删除!");
			this.abort();
		}

		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());

		if (confirmRemove()) {
			try {
				RoomAreaCompensateFactory.getRemoteInstance()
						.deleteCompensateInfo(this.editData.getId());
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "删除补差单失败!");
			}

		}
		this.isDeleAfter = true;
		this.actionAddNew_actionPerformed(e);
		// handleCodingRule();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
	}

	protected void cbcType_itemStateChanged(ItemEvent e) throws Exception {
		super.cbcType_itemStateChanged(e);
		if (((RoomAreaCompensateTypeEnum) e.getItem())
				.equals(RoomAreaCompensateTypeEnum.BYHAND)) {
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYHAND);
			this.contScheme.setVisible(false);
			this.actionCalc.setEnabled(false);
		} else {
			this.contScheme.setVisible(true);
			this.cbcType.setSelectedItem(RoomAreaCompensateTypeEnum.BYSCHEME);
			this.actionCalc.setEnabled(true);
		}

		if (!this.isFirst) {
			for (int i = 0; i < this.kdtRoomList.getRowCount(); i++) {
				IRow row = this.kdtRoomList.getRow(i);
				if (row == null) {
					continue;
				}

				row.getCell("mainAmount").setValue(null);
				row.getCell("attAmount").setValue(null);
				row.getCell("referAmount").setValue(null);
				row.getCell("actAmount").setValue(null);
			}
		}

		initTable();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning(this, "请先保存单据!");
			this.abort();
		}

		RoomAreaCompensateInfo info = RoomAreaCompensateFactory
				.getRemoteInstance().getRoomAreaCompensateInfo(
						"select id,name,compensateState where id='"
								+ this.editData.getId().toString() + "'");
		if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMAUDITTED)) {
			FDCMsgBox.showWarning(this, "单据已审批，不能再审批!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMAUDITTING)) {
			FDCMsgBox.showWarning(this, "单据审批中，不能审批!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMRECEIVED)) {
			FDCMsgBox.showWarning(this, "单据已收款，不能审批!");
			this.abort();
		} else if (info.getCompensateState().equals(
				RoomCompensateStateEnum.COMSTOP)) {
			FDCMsgBox.showWarning(this, "单据已作废，不能审批!");
			this.abort();
		}

		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());

		try {
			RoomAreaCompensateFactory.getRemoteInstance().auditAndCalcSellAmount(this.editData.getId().toString());
			MsgBox.showInfo("审批成功!");
			
			syncDataFromDB();
			handleOldData();
			
			this.actionUnAudit.setVisible(true);
			this.actionUnAudit.setEnabled(true);
			this.actionAudit.setVisible(false);
			this.actionAudit.setEnabled(false);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

	}
	protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
	protected void handleOldData() {
		if(!(getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		if (this.editData.getId() == null) {
			if (this.prmtScheme.getValue() == null) {
				FDCMsgBox.showWarning(this, "请先选择提交!");
				this.abort();
			}
		}

		RoomAreaCompensateInfo info = RoomAreaCompensateFactory
				.getRemoteInstance().getRoomAreaCompensateInfo(
						"select id,name,compensateState where id='"
								+ this.editData.getId().toString() + "'");

		if (!info.getCompensateState().equals(
				RoomCompensateStateEnum.COMAUDITTED)) {
			FDCMsgBox.showWarning(this, "单据状态不对，不能进行反审批!");
			this.abort();
		}

		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());

		try {

			RoomAreaCompensateFactory.getRemoteInstance().unAuditAndCalcSellAmount(this.editData.getId().toString());
			MsgBox.showInfo("反审批成功!");
			syncDataFromDB();
			handleOldData();
			this.actionUnAudit.setVisible(false);
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setVisible(true);
			this.actionAudit.setEnabled(true);
			
			actionSave.setEnabled(false);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public void actionCalc_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalc_actionPerformed(e);
		if (this.prmtScheme.getValue() == null) {
			FDCMsgBox.showWarning(this, "请先选择补差方案!");
			this.abort();
		}
		if (this.kdtRoomList.getRowCount() >= 0) {
			if (this.prmtScheme.getValue() == null) {
				FDCMsgBox.showWarning(this, "请选择房间!");
				this.abort();
			}
		}
		List roomList = new ArrayList();
		for (int i = 0; i < this.kdtRoomList.getRowCount(); i++) {
			IRow row = this.kdtRoomList.getRow(i);

			if (row == null) {
				continue;
			}
			roomList.add(row.getCell("roomId").getValue().toString());
		}
		CompensateSchemeInfo scheme = (CompensateSchemeInfo) this.prmtScheme
				.getValue();

		Map values = null;

		try {
			values = RoomAreaCompensateFactory.getRemoteInstance()
					.calcAmountForSHE(roomList, scheme.getId().toString());
		} catch (Exception ex) {
			if (ex instanceof BOSException
					&& ex.getMessage().startsWith(
							"COMPENSATEAREA_ERROR_NULL_BUILDINGAREA")) {
				MsgBox.showWarning(this, "房间实测建筑面积为空,不能计算!");
				SysUtil.abort();
			} else if (ex instanceof BOSException
					&& ex.getMessage().startsWith(
							"COMPENSATEAREA_ERROR_NULL_ROOMAREA")) {
				MsgBox.showWarning(this, "房间实测套内面积为空,不能计算！");
				SysUtil.abort();
			}
		}
		if (values != null && !values.isEmpty()) {
			for (int i = 0; i < this.kdtRoomList.getRowCount(); i++) {
				IRow row = this.kdtRoomList.getRow(i);

				if (row == null) {
					continue;
				}
				String roomId = row.getCell("roomId").getValue().toString();
				Map value = (Map) values.get(roomId);
				if (value != null) {
					BigDecimal main = FDCHelper.ZERO;
					BigDecimal att = FDCHelper.ZERO;
					BigDecimal total = FDCHelper.ZERO;
					if (value.get("compensateAmtMain") != null) {
						main = (BigDecimal) value.get("compensateAmtMain");
					}
					if (value.get("compensateAmtAtt") != null) {
						att = (BigDecimal) value.get("compensateAmtAtt");
					}
					if (value.get("compensateRate") != null) {
						row.getCell("rate").setValue(
								value.get("compensateRate"));
					}
					total = FDCHelper.add(main, att);

					row.getCell("referAmount").setValue(total);
					row.getCell("actAmount").setValue(total);
					row.getCell("mainAmount").setValue(main);
					row.getCell("attAmount").setValue(att);

					BigDecimal conAmount = FDCHelper.ZERO;
					BigDecimal lastAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;

					if (row.getCell("conAmount").getValue() != null) {
						conAmount = (BigDecimal) row.getCell("conAmount")
								.getValue();
					}
					if (row.getCell("actAmount").getValue() != null) {
						actAmount = (BigDecimal) row.getCell("actAmount")
								.getValue();
					}
					row.getCell("actAmount").setValue(actAmount);
					lastAmount = FDCHelper.add(actAmount, conAmount);
					row.getCell("lastAmount").setValue(lastAmount);
				}

			}
		}
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			RoomAreaCompensateaDataProvider data = new RoomAreaCompensateaDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.RoomAreaCompensateForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/postSaleService", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrint_actionPerformed(e);
		}

	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			RoomAreaCompensateaDataProvider data = new RoomAreaCompensateaDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.RoomAreaCompensateForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/sellhouse/postSaleService", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrintPreview_actionPerformed(e);
		}
	}

	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		if (!this.isDeleAfter) {
			if (!STATUS_ADDNEW.equals(this.oprtState)) {
				return;
			}
		}
		boolean isExist = true;
		RoomAreaCompensateInfo pur = new RoomAreaCompensateInfo();
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(pur, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(pur, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(pur,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(pur, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(pur, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(pur, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								pur, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(pur, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}

	protected void setNumberTextEnabled() {
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						new RoomAreaCompensateInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}

	public void actionWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning(this, "请先提交!");
			this.abort();
		}
		String selectId = null;
		if (this.editData.getId() != null) {
			selectId = this.editData.getId().toString();
		}

		isExistsWorkFlow(this.editData.getId().toString());
		IEnactmentService service2 = EnactmentServiceFactory
				.createRemoteEnactService();
		List openProcInsts = new ArrayList();
		ProcessInstInfo[] procInsts = service2
				.getProcessInstanceByHoldedObjectId(selectId);
		if (procInsts == null || procInsts.length == 0) {
			// 首先查看是否有ID，有ID则认为是以前单据，没有则认为是新增
			if (!StringUtils.isEmpty(selectId)) {
				// 如果没有运行时流程，判断是否有可以展现的流程图并展现
				procInsts = service2.getAllProcessInstancesByBizobjId(selectId);
				if (procInsts == null || procInsts.length <= 0)
					this.actionVIewSubmitProcess(e);
				else if (procInsts.length == 1) {
					showWorkflowDiagram(procInsts[0]);
				} else {
					showWorkflowListDiagram(procInsts);
				}
			} else {
				actionVIewSubmitProcess(e);
			}
		} else {
			for (int i = 0; i < procInsts.length; i++) {
				ProcessInstInfo processInstInfo = (ProcessInstInfo) procInsts[i];
				if ("open".equalsIgnoreCase(processInstInfo.getState()
						.substring(0, 4))) {// 显示所有状态为open.*的流程
					openProcInsts.add(processInstInfo);
				}
			}
			if (openProcInsts.size() == 0 || openProcInsts == null) {
				actionVIewSubmitProcess(e);
			} else if (openProcInsts.size() == 1) { // 如果只有一条运行流程,直接显示出来
				showWorkflowDiagram((ProcessInstInfo) openProcInsts.get(0));
			} else {
				showWorkflowListDiagram((ProcessInstInfo[]) openProcInsts
						.toArray(new ProcessInstInfo[] {}));
			}
		}
	}

	private void isExistsWorkFlow(String idStr) throws Exception {
		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo[] procInsts = service
				.getProcessInstanceByHoldedObjectId(idStr);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if (procInsts[i].getState().startsWith("open")) {
				processInstInfo = procInsts[i];
			}
		}
		if (processInstInfo == null) {
			// 如果没有运行时流程，判断是否有可以展现的流程图并展现
			procInsts = service.getAllProcessInstancesByBizobjId(idStr);
			if (procInsts == null || procInsts.length <= 0) {

				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_WFHasNotInstance"));
				this.abort();
			}
		}
	}

	private void actionVIewSubmitProcess(ActionEvent e) throws Exception {
		if (editData.getBOSType() == null) {
			return;
		}

		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		IObjectValue bizObj = AgentUtility.getNoAgentValue(this.editData);
		storeEditData4WfPreview();
		String procDefID = service.findSubmitProcDef(SysContext.getSysContext()
				.getCurrentUserInfo().getId().toString(), bizObj, this
				.getWFUIFuctionName(), this.getWFActionName());

		if (procDefID != null) {
			// 显示UI
			ProcessDefInfo processDefInfo = service
					.getProcessDefInfo(procDefID);
			Locale currentLocale = SysContext.getSysContext().getLocale();
			ProcessDef processDef = service
					.getProcessDefByDefineHashValue(processDefInfo
							.getMd5HashValue());
			String procDefDiagramTitle = "";
			if (processDef != null) {
				procDefDiagramTitle = processDef.getName(currentLocale);
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("define", processDef);
			uiContext.put("title", procDefDiagramTitle);
			BasicShowWfDefinePanel.Show(uiContext);
		} else {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotDef"));
		}
	}

	/**
	 * 返回工作流的UIFuctionName
	 * 如："com.kingdee.eas.ma.crbg.app.BgUsefundEditUIFunction"
	 * 
	 * @return
	 */
	protected String getWFUIFuctionName() {
		return null;
	}

	/**
	 * 返回工作流的ActionName。 如："submitWF"
	 * 
	 * @return
	 */
	protected String getWFActionName() {
		return null;
	}

	protected void storeEditData4WfPreview() {
		try {
			storeFields();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		BasicWorkFlowMonitorPanel.Show(uiContext);
	}

	private void showWorkflowListDiagram(ProcessInstInfo[] processInstInfos)
			throws UIException {
		UIContext uiContext = new UIContext(this);
		uiContext.put("procInsts", processInstInfos);
		String className = ProcessRunningListUI.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(className, uiContext);
		uiWindow.show();
	}

}