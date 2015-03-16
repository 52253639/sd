/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
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
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlBuildingEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlBuildingEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlFactory;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo;
import com.kingdee.eas.fdc.sellhouse.BasePriceRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BasePriceRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IBasePriceControl;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class BasePriceControlEditUI extends AbstractBasePriceControlEditUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3996977873205042028L;
	private static final Logger logger = CoreUIObject
			.getLogger(BasePriceControlEditUI.class);
	private List roomList = new ArrayList();
	private boolean isOld = false;
	private boolean isAll = true;

	/**
	 * output class constructor
	 */
	public BasePriceControlEditUI() throws Exception {
		super();
	}

	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		BasePriceControlInfo info = this.editData;
		info.setProject((SellProjectInfo) this.prmtProject.getValue());

		this.editData.getBuildingEntry().clear();
		if (this.prmtBuilding.getValue() != null) {
			Object[] obj = (Object[]) this.prmtBuilding.getValue();
			for (int i = 0; i < obj.length; i++) {
				BuildingInfo bulingInfo = (BuildingInfo) obj[i];
				BasePriceControlBuildingEntryInfo entry = new BasePriceControlBuildingEntryInfo();
				entry.setBuilding(bulingInfo);
				this.editData.getBuildingEntry().add(entry);
			}
		}

		this.editData.getRoomEntry().clear();
		for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
			IRow row = this.kDTable1.getRow(i);
			if (row == null) {
				continue;
			}
			if (i == this.kDTable1.getRowCount() - 1) {
				continue;
			}
			RoomInfo room = new RoomInfo();
			room.setId((BOSUuid) row.getCell("id").getValue());
			if (row.getCell("buildBasePrice").getValue() != null) {
				room.setBaseBuildingPrice((BigDecimal) row.getCell(
						"buildBasePrice").getValue());
			}
			if (row.getCell("stanardBasePriece").getValue() != null) {
				room.setBaseStandardPrice((BigDecimal) row.getCell(
						"stanardBasePriece").getValue());
			}
			if (row.getCell("roomBasePrice").getValue() != null) {
				room.setBaseRoomPrice((BigDecimal) row.getCell("roomBasePrice")
						.getValue());
			}
			// room.setIsBasePriceAudited(true);
			BasePriceRoomEntryInfo entryInfo = new BasePriceRoomEntryInfo();
			entryInfo.setRoom(room);
			this.editData.getRoomEntry().add(entryInfo);
			roomList.add(room);
		}

	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BasePriceControlFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		BasePriceControlInfo info = new BasePriceControlInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");

		BuildingInfo building = null;
		String buildId = "";
		if (this.getUIContext().get("selectBuilding") != null) {
			if (this.getUIContext().get("selectBuilding") instanceof BuildingInfo) {
				building = (BuildingInfo) this.getUIContext().get(
						"selectBuilding");
			} else if (this.getUIContext().get("selectBuilding") instanceof String) {
				buildId = this.getUIContext().get("selectBuilding").toString();
			}
		}

		if (building != null) {
			info.setBuilding(building);
		} else {
			try {
				BuildingInfo buildInfo = BuildingFactory.getRemoteInstance()
						.getBuildingInfo(
								"select id,name,number where id='" + buildId
										+ "'");
				if (buildInfo != null) {
					info.setBuilding(buildInfo);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if (this.getUIContext().get("selectUnit") != null) {
			BuildingUnitInfo unit = (BuildingUnitInfo) this.getUIContext().get(
					"selectUnit");
			info.setUnit(unit);
		}

		if (sellProject != null) {
			info.setProject(sellProject);
		}

		return info;
	}

	public void loadFields() {
		super.loadFields();

		BasePriceControlBuildingEntryCollection BuildingColl = this.editData
				.getBuildingEntry();
		if (BuildingColl != null) {

			Object[] obj = new Object[BuildingColl.size()];
			for (int i = 0; i < BuildingColl.size(); i++) {
				BasePriceControlBuildingEntryInfo entry = BuildingColl.get(i);
				if (entry != null && entry.getBuilding() != null) {
					obj[i] = entry.getBuilding();
				}
			}

			if (obj.length > 0) {
				this.prmtBuilding.setDataNoNotify(obj);
			}

		}
		this.kDTable1.removeRows();
		BasePriceRoomEntryCollection coll = this.editData.getRoomEntry();
		for (int i = 0; i < coll.size(); i++) {
			BasePriceRoomEntryInfo entryInfo = coll.get(i);
			if (entryInfo != null) {
				RoomInfo room = entryInfo.getRoom();
				if (room != null) {
					addNewRoom(room);
				}
			}

		}
		this.addTotal(this.kDTable1);

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			if (this.getUIContext().get("selectRoom") != null) {
				List roomMap = (List) this.getUIContext().get("selectRoom");
				for (int i = 0; i < roomMap.size(); i++) {
					RoomInfo room = (RoomInfo) roomMap.get(i);
					if (room != null) {
						addNewRoom(room);
					}
				}
			}

			this.addTotal(this.kDTable1);
		}

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			this.prmtBuilding.setValue(null);
			Map buildId = null;
			String idStr = "";
			if (this.getUIContext().get("selectBuild") != null) {
				buildId = (Map) this.getUIContext().get("selectBuild");
				if(!buildId.isEmpty()){
					idStr = FDCTreeHelper.getStringFromSet(buildId.keySet());
					FilterInfo filter = null;

					filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("id", idStr, CompareType.INNER));
					//filter.setMaskString("#0 or #1");

					EntityViewInfo view = new EntityViewInfo();

					SelectorItemCollection collBuilding = new SelectorItemCollection();
					collBuilding.add(new SelectorItemInfo("id"));
					collBuilding.add(new SelectorItemInfo("name"));
					view.setFilter(filter);
					view.setSelector(collBuilding);
					BuildingCollection buildColl = null;
					try {
						buildColl = BuildingFactory.getRemoteInstance()
								.getBuildingCollection(view);
					} catch (BOSException e) {
						e.printStackTrace();
					}

					if (buildColl.size() > 0) {
						Object[] objbuild = new Object[buildColl.size()];
						for (int i = 0; i < buildColl.size(); i++) {
							BuildingInfo entry = (BuildingInfo) buildColl.get(i);
							if (entry != null) {
								objbuild[i] = entry;
							}
						}

						if (objbuild.length > 0) {
							this.prmtBuilding.setDataNoNotify(objbuild);
						}

					}
				}
			}
		}

		if (this.editData.getId() != null) {
			if (this.editData.getAuditor() == null
					|| this.editData.getAuditTime() == null) {
				try {
					BasePriceControlInfo baseInfo = BasePriceControlFactory
							.getRemoteInstance().getBasePriceControlInfo(
									"select id,auditor.id,auditor.name,auditor.number,auditTime where id='"
											+ this.editData.getId().toString()
											+ "'");
					if (baseInfo != null) {
						this.prmtAuditor.setValue(baseInfo.getAuditor());
						this.pkAuditTime.setValue(baseInfo.getAuditTime());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}

		// /initOldData(this.editData);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning(this, "请先提交!");
			this.abort();
		}
		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
						+ this.editData.getId().toString() + "'");

		if (info != null) {
			if (info.isIsExecuted()) {
				FDCMsgBox.showWarning(this, "单据已经执行，不能修改!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning(this, "单据已审批，不能修改!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能修改!");
				this.abort();
			}
		}
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());
		super.actionEdit_actionPerformed(e);
		if (OprtState.EDIT.equals(this.getOprtState())) {
			if (this.editData.getBaseType() != null) {
				this.initTableLock(this.editData.getBaseType());
			}
			this.actionChooseRoom.setEnabled(true);
			this.actionDelRoom.setEnabled(true);
			this.actionImportPrice.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionExceuted.setEnabled(true);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sicection = super.getSelectors();
		sicection.add(new SelectorItemInfo("roomEntry.room.id"));
		sicection.add(new SelectorItemInfo("roomEntry.room.number"));
		sicection.add(new SelectorItemInfo("roomEntry.room.name"));
		sicection.add(new SelectorItemInfo("roomEntry.room.roomNo"));
		sicection.add(new SelectorItemInfo("roomEntry.room.displayName"));
		sicection.add(new SelectorItemInfo("roomEntry.room.sellType"));
		sicection
				.add(new SelectorItemInfo("roomEntry.room.standardTotalAmount"));
		sicection.add(new SelectorItemInfo("roomEntry.room.buildingArea"));
		sicection.add(new SelectorItemInfo("roomEntry.room.buildPrice"));
		sicection.add(new SelectorItemInfo("roomEntry.room.roomArea"));
		sicection.add(new SelectorItemInfo("roomEntry.room.roomPrice"));
		sicection.add(new SelectorItemInfo("roomEntry.room.baseStandardPrice"));
		sicection.add(new SelectorItemInfo("roomEntry.room.baseBuildingPrice"));
		sicection.add(new SelectorItemInfo("roomEntry.room.baseRoomPrice"));
		sicection.add(new SelectorItemInfo("auditor.id"));
		sicection.add(new SelectorItemInfo("auditor.name"));
		sicection.add(new SelectorItemInfo("auditor.number"));
		sicection.add(new SelectorItemInfo("auditTime"));
		sicection.add(new SelectorItemInfo("createTime"));
		sicection.add(new SelectorItemInfo("project.id"));
		sicection.add(new SelectorItemInfo("project.name"));
		sicection.add(new SelectorItemInfo("project.number"));
		sicection.add(new SelectorItemInfo("project.longnumber"));
		sicection.add(new SelectorItemInfo("project.longnumber"));
		sicection.add(new SelectorItemInfo("buildingEntry.id"));
		sicection.add(new SelectorItemInfo("buildingEntry.building.id"));
		sicection.add(new SelectorItemInfo("buildingEntry.building.name"));
		sicection.add(new SelectorItemInfo("buildingEntry.building.number"));

		return sicection;
	}

	private SellProjectInfo getLongNumberForProject(String projectId) {
		SellProjectInfo info = null;

		try {
			info = SellProjectFactory.getRemoteInstance().getSellProjectInfo(
					"select id,name,number,longnumber where id='" + projectId
							+ "'");
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}

		return info;
	}

	/**
	 * 初始化楼栋F7控件
	 */
	private void initPrmtBuilding() {
		SellProjectInfo sellPro = this.editData.getProject();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		String projectStr = "";
		projectStr = getSellProjectString(getLongNumberForProject(sellPro
				.getId().toString()));

		if (!"".equals(projectStr)) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", projectStr,
							CompareType.INNER));
		} else {
			if (sellPro != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellPro.getId()
								.toString()));
			}
		}

		// 售楼属性
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.isForSHE", Boolean.TRUE));
		view.setFilter(filter);

		this.prmtBuilding.setEnabledMultiSelection(true);
		this.prmtBuilding.setEntityViewInfo(view);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("sellProject.id");
		sels.add("sellProject.name");
		sels.add("sellProject.number");
		this.prmtBuilding.setSelectorCollection(sels);

	}

	private String getSellProjectString(SellProjectInfo sellPro) {
		StringBuffer str = new StringBuffer();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("id",sellPro.getId().toString(),CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", sellPro.getLongNumber(),
						CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber",
						sellPro.getLongNumber() + "!%", CompareType.LIKE));
		filter.setMaskString("#0 or #1");
		view.setFilter(filter);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		coll.add(new SelectorItemInfo("number"));
		view.setSelector(coll);
		try {
			SellProjectCollection sellColl = SellProjectFactory
					.getRemoteInstance().getSellProjectCollection(view);
			// str.append("(");
			for (int i = 0; i < sellColl.size(); i++) {
				SellProjectInfo info = sellColl.get(i);
				if (info != null) {
					str.append("'");
					str.append(info.getId().toString());
					str.append("'");

					if (i != sellColl.size() - 1) {
						str.append(",");
					}
				}
			}
			// str.append(")");
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}

		if ("()".equals(str.toString())) {
			return "";
		}
		return str.toString();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionSave.setVisible(false);
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnExceuted.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.btnWorkFlow.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
		this.btnCalc.setIcon(EASResource.getIcon("imgTbtn_counter"));

		this.actionWorkFlow.setVisible(true);
		initRoomTable();
		initPrmtBuilding();
		this.prmtBuilding.setEditable(false);
		this.actionWorkFlow.setEnabled(true);

		this.txtAvgBuildingBasePrice.setPrecision(2);
		this.txtAvgRoomBasePrice.setPrecision(2);
		this.txtMaxBuildingBasePrice.setPrecision(2);
		this.txtMinBuildingBasePrice.setPrecision(2);
		this.txtMaxRoomBasePrice.setPrecision(2);
		this.txtMinRoomBasePrice.setPrecision(2);
		this.actionCalc.setEnabled(true);

		this.actionChooseRoom.setEnabled(false);
		this.actionImportPrice.setEnabled(false);
		this.actionDelRoom.setEnabled(false);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);

		if (OprtState.EDIT.equals(this.getOprtState())) {
			this.actionChooseRoom.setEnabled(true);
			this.actionDelRoom.setEnabled(false);
			this.actionImportPrice.setEnabled(true);
			if (this.editData.getBaseType() != null) {
				this.initTableLock(this.editData.getBaseType());
			}
		}

		initData();

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			// 默认按照建筑单价
			comboBaseType.setSelectedItem(BaseTypeEnum.BUILDINGAREA);
			this.actionChooseRoom.setEnabled(true);
			this.actionDelRoom.setEnabled(true);
			this.actionImportPrice.setEnabled(true);
		}

		this.kDTable1.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent e) {
			}

			public void editStarted(KDTEditEvent e) {
			}

			public void editStarting(KDTEditEvent e) {
			}

			public void editStopped(KDTEditEvent e) {
				if (e.getColIndex() == kDTable1.getColumn("stanardBasePriece")
						.getColumnIndex()) {
					try {
						int rowIndex = e.getRowIndex();
						calcPirceForStandard(rowIndex, true);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
				} else if (e.getColIndex() == kDTable1.getColumn(
						"buildBasePrice").getColumnIndex()) {
					int rowIndex = e.getRowIndex();
					calcPirceForBuild(rowIndex, true);
				} else if (e.getColIndex() == kDTable1.getColumn(
						"roomBasePrice").getColumnIndex()) {
					int rowIndex = e.getRowIndex();
					calcPirceForRoom(rowIndex, true);
				}
			}

			public void editStopping(KDTEditEvent e) {
			}

			public void editValueChanged(KDTEditEvent e) {
			}

		});

		this.actionAudit.setEnabled(true);
		this.actionExceuted.setEnabled(true);

		if (OprtState.VIEW.equals(this.getOprtState())
				|| OprtState.ADDNEW.equals(this.getOprtState())) {
			this.actionAudit.setEnabled(false);
			this.actionExceuted.setEnabled(false);
		}

		this.prmtBuilding.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				clearTableData();
			}
		});
	}

	private void clearTableData() {
		this.kDTable1.removeRows();
	}

	public void onShow() throws Exception {
		super.onShow();

		if (this.editData.getId() != null) {
			if (CRMHelper.isRunningWorkflow(this.editData.getId().toString())) {
				this.lockUIForViewStatus();
			}
		}

		if (OprtState.VIEW.equals(this.getOprtState())) {
			this.actionDelRoom.setEnabled(false);
		}

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			handleCodingRule();
		}
		if (!this.isSellOrg()) {
			this.actionAddNew.setEnabled(false);
			this.actionSubmit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionExceuted.setEnabled(false);
		} else {
			if (OprtState.VIEW.equals(this.getOprtState())
					|| OprtState.EDIT.equals(this.getOprtState())) {

				IBasePriceControl iBase = BasePriceControlFactory
						.getRemoteInstance();
				BasePriceControlInfo info = iBase
						.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
								+ this.editData.getId().toString() + "'");

				if (info != null) {
					if (info.getState().equals(FDCBillStateEnum.AUDITTED)
							&& !info.isIsExecuted()) {
						this.actionExceuted.setEnabled(true);
					}
				}
			}
		}
	}

	/**
	 * 当选择按照总价计算时，反算建筑和套内单价
	 */
	private void calcPirceForStandard(int rowIndex, boolean isCalc) {
		IRow row = this.kDTable1.getRow(rowIndex);
		if (row == null) {
			return;
		}
		BigDecimal standardPrice = FDCHelper.ZERO;
		BigDecimal buildArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		BigDecimal buildBasePrice = FDCHelper.ZERO;
		BigDecimal roomBasePrice = FDCHelper.ZERO;
		if (row.getCell("stanardBasePriece").getValue() != null) {
			standardPrice = (BigDecimal) row.getCell("stanardBasePriece")
					.getValue();
		}
		if (row.getCell("buildArea").getValue() != null) {
			buildArea = (BigDecimal) row.getCell("buildArea").getValue();
		}
		if (row.getCell("roomArea").getValue() != null) {
			roomArea = (BigDecimal) row.getCell("roomArea").getValue();
		}

		if (buildArea.compareTo(FDCHelper.ZERO) > 0) {
			buildBasePrice = FDCHelper.divide(standardPrice, buildArea);
		}
		if (roomArea.compareTo(FDCHelper.ZERO) > 0) {
			roomBasePrice = FDCHelper.divide(standardPrice, roomArea);
		}
		if (buildBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			row.getCell("buildBasePrice").setValue(buildBasePrice);
		}
		if (roomBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			row.getCell("roomBasePrice").setValue(roomBasePrice);
		}

		if (isCalc) {
			this.addTotal(kDTable1);
		}

	}

	/**
	 * 当选择建筑单价时，反算总价和套内单价
	 * 
	 * @param rowIndex
	 */
	private void calcPirceForBuild(int rowIndex, boolean isCalc) {
		IRow row = this.kDTable1.getRow(rowIndex);
		if (row == null) {
			return;
		}
		BigDecimal standardPrice = FDCHelper.ZERO;
		BigDecimal buildArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		BigDecimal buildBasePrice = FDCHelper.ZERO;
		BigDecimal roomBasePrice = FDCHelper.ZERO;
		if (row.getCell("buildBasePrice").getValue() != null) {
			buildBasePrice = (BigDecimal) row.getCell("buildBasePrice")
					.getValue();
		}
		if (row.getCell("buildArea").getValue() != null) {
			buildArea = (BigDecimal) row.getCell("buildArea").getValue();
		}
		if (row.getCell("roomArea").getValue() != null) {
			roomArea = (BigDecimal) row.getCell("roomArea").getValue();
		}

		if (buildBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			standardPrice = FDCHelper.multiply(buildBasePrice, buildArea);
		}

		if (roomArea.compareTo(FDCHelper.ZERO) > 0) {
			roomBasePrice = FDCHelper.divide(standardPrice, roomArea);
		}
		if (standardPrice.compareTo(FDCHelper.ZERO) > 0) {
			row.getCell("stanardBasePriece").setValue(standardPrice);
		}
		if (roomBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			row.getCell("roomBasePrice").setValue(roomBasePrice);
		}
		if (isCalc) {
			this.addTotal(kDTable1);
		}

	}

	/**
	 * 当选择套内单价时，反算总价和建筑单价
	 * 
	 * @param rowIndex
	 */
	private void calcPirceForRoom(int rowIndex, boolean isCalc) {
		IRow row = this.kDTable1.getRow(rowIndex);
		if (row == null) {
			return;
		}
		BigDecimal standardPrice = FDCHelper.ZERO;
		BigDecimal buildArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		BigDecimal buildBasePrice = FDCHelper.ZERO;
		BigDecimal roomBasePrice = FDCHelper.ZERO;
		if (row.getCell("roomBasePrice").getValue() != null) {
			roomBasePrice = (BigDecimal) row.getCell("roomBasePrice")
					.getValue();
		}
		if (row.getCell("buildArea").getValue() != null) {
			buildArea = (BigDecimal) row.getCell("buildArea").getValue();
		}
		if (row.getCell("roomArea").getValue() != null) {
			roomArea = (BigDecimal) row.getCell("roomArea").getValue();
		}

		if (roomBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			standardPrice = FDCHelper.multiply(roomBasePrice, roomArea);
		}

		if (buildArea.compareTo(FDCHelper.ZERO) > 0) {
			buildBasePrice = FDCHelper.divide(standardPrice, buildArea);
		}
		if (standardPrice.compareTo(FDCHelper.ZERO) > 0) {
			row.getCell("stanardBasePriece").setValue(standardPrice);
		}
		if (buildBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			row.getCell("buildBasePrice").setValue(buildBasePrice);
		}
		if (isCalc) {
			this.addTotal(kDTable1);
		}

	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			this.prmtCreator.setValue(SysContext.getSysContext()
					.getCurrentUserInfo());
			try {
				this.pkBizDate.setValue(FDCSQLFacadeFactory.getRemoteInstance()
						.getServerTime());
			} catch (BOSException e) {
				logger.error(e.getMessage() + "获得时间失败!");
			}
		}
	}

	/**
	 * 初始化房间table
	 * 
	 */
	private void initRoomTable() {
		this.kDTable1.getStyleAttributes().setLocked(true);
		this.kDTable1.checkParsed();
		this.kDTable1.getColumn("stanardBasePriece").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("buildBasePrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("roomBasePrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("buildArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("buildPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("roomPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kDTable1.getColumn("stanardTotal").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kDTable1.getColumn("stanardBasePriece").setEditor(numberEditor);
		this.kDTable1.getColumn("buildBasePrice").setEditor(numberEditor);
		this.kDTable1.getColumn("roomBasePrice").setEditor(numberEditor);

	}

	/**
	 * 房间的选择
	 */
	public void actionChooseRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionChooseRoom_actionPerformed(e);

		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery")));

		EntityViewInfo view = new EntityViewInfo();
		// 过滤条件
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();

		filterItems.add(new FilterItemInfo("isForSHE", Boolean.TRUE)); // 房间的售楼属性
		filterItems
				.add(new FilterItemInfo("sellProject.isForSHE", Boolean.TRUE)); // 项目的售楼属性

		String projectStr = "";

		if (this.editData.getProject() != null) {
			projectStr = getAllProjectByRoot(this.editData.getProject().getId()
					.toString());
		} else {
			SellProjectInfo info = (SellProjectInfo) this.prmtProject
					.getValue();
			projectStr = getAllProjectByRoot(info.getId().toString());
		}

		if (!"".equals(projectStr)) {
			filterItems.add(new FilterItemInfo("sellProject.id", projectStr,
					CompareType.INNER));
		}

		StringBuffer sb = new StringBuffer();
		if (this.prmtBuilding.getValue() != null) {
			Object[] obj = (Object[]) this.prmtBuilding.getValue();
			for (int i = 0; i < obj.length; i++) {
				BuildingInfo info = (BuildingInfo) obj[i];
				if (info != null) {
					sb.append("'");
					sb.append(info.getId().toString());
					sb.append("'");
					if (i != obj.length - 1) {
						sb.append(",");
					}
				}
			}
			if (sb.length() > 0) {
				filterItems.add(new FilterItemInfo("building.id",
						sb.toString(), CompareType.INNER));
			}
		}

		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		// 设置多选
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		
		if(object!=null && object.length>0){
			int comfim = MsgBox.NO;

			comfim = MsgBox.showConfirm2New(this, "已售房间是否参与底价控制？");

			if (comfim == MsgBox.NO) {
				this.isAll = false;
			}
			else {
				this.isAll = true;
			}
			
			if (object != null) {
				for (int i = 0; i < object.length; i++) {
					RoomInfo room = (RoomInfo) object[i];
					if (!this.isAll) {
						if (!room.getSellState().equals(RoomSellStateEnum.Init)
								&& !room.getSellState().equals(
										RoomSellStateEnum.OnShow)
								&& !room.getSellState().equals(
										RoomSellStateEnum.KeepDown)
								&& !room.getSellState().equals(
										RoomSellStateEnum.SincerPurchase)) {
							continue;
						}
					}
					addNewRoom(room);
				}
			}

			this.addTotal(this.kDTable1);
		}
		
	}

	private String getAllProjectByRoot(String idString) throws Exception {
		StringBuffer sb = new StringBuffer();

		String longNumber = getProjectLongNumber(idString);

		FilterInfo filter = null;

		filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("longNumber", longNumber,
								CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.setMaskString("#0 or #1");

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		view.setFilter(filter);
		view.setSelector(coll);
		SellProjectCollection projectColl = SellProjectFactory
				.getRemoteInstance().getSellProjectCollection(view);

		for (int i = 0; i < projectColl.size(); i++) {
			SellProjectInfo info = projectColl.get(i);
			if (info != null) {
				sb.append("'");
				sb.append(info.getId().toString());
				sb.append("'");
				if (i != projectColl.size() - 1) {
					sb.append(",");
				}
			}
		}
		if (sb.length() > 0) {
			return sb.toString();
		} else {
			return "";
		}
	}

	private String getProjectLongNumber(String idString)
			throws EASBizException, BOSException {
		String longNumber = "";
		SellProjectInfo info = SellProjectFactory.getRemoteInstance()
				.getSellProjectInfo(
						"select id,longnumber where id='" + idString + "'");
		if (info != null && info.getLongNumber() != null) {
			longNumber = info.getLongNumber().toString();
		}
		return longNumber;
	}

	public void actionDelRoom_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelRoom_actionPerformed(e);
		int activeRowIndex = this.kDTable1.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			MsgBox.showInfo("请选择行");
			return;
		}

		if (this.editData.getId() != null) {
			this.kDTable1.removeRow(activeRowIndex);
		} else {
			this.kDTable1.removeRow(activeRowIndex);
		}
		this.addTotal(this.kDTable1);
	}

	/**
	 * 添加合计行和重新计算最大，最小数值
	 * 
	 * @param table
	 */
	private void addTotal(KDTable table) {
		// 清除旧合计
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getRow(i).getUserObject() == null) {
				table.removeRow(i);
				i = -1;
			}
		}

		if (table.getRowCount() <= 0) {
			return;
		}

		BigDecimal stanardTotal = FDCHelper.ZERO;
		BigDecimal buildArea = FDCHelper.ZERO;
		BigDecimal buildPrice = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		BigDecimal roomPrice = FDCHelper.ZERO;
		BigDecimal stanardBasePriece = FDCHelper.ZERO;
		BigDecimal buildBasePrice = FDCHelper.ZERO;
		BigDecimal roomBasePrice = FDCHelper.ZERO;
		BigDecimal avgBuildPrice = FDCHelper.ZERO;
		BigDecimal avgroomPrice = FDCHelper.ZERO;
		BigDecimal maxBuildPrice = FDCHelper.ZERO;
		BigDecimal minBuildPrice = FDCHelper.ZERO;
		BigDecimal maxroomPrice = FDCHelper.ZERO;
		BigDecimal minroomPrice = FDCHelper.ZERO;

		for (int r = 0; r < table.getRowCount(); r++) {
			ICell stanardTotalCell = table.getCell(r, "stanardTotal");
			if (stanardTotalCell.getValue() != null) {
				stanardTotal = stanardTotal.add((BigDecimal) stanardTotalCell
						.getValue());
			}
			ICell buildAreaCell = table.getCell(r, "buildArea");
			if (buildAreaCell.getValue() != null) {
				buildArea = buildArea
						.add((BigDecimal) buildAreaCell.getValue());
			}
			ICell buildPriceCell = table.getCell(r, "buildPrice");
			if (buildPriceCell.getValue() != null) {
				buildPrice = buildPrice.add((BigDecimal) buildPriceCell
						.getValue());
			}
			ICell roomAreaCell = table.getCell(r, "roomArea");
			if (roomAreaCell.getValue() != null) {
				roomArea = roomArea.add((BigDecimal) roomAreaCell.getValue());
			}
			ICell roomPriceCell = table.getCell(r, "roomPrice");
			if (roomPriceCell.getValue() != null) {
				roomPrice = roomPrice
						.add((BigDecimal) roomPriceCell.getValue());
			}
			ICell stanardBasePrieceCell = table.getCell(r, "stanardBasePriece");
			if (stanardBasePrieceCell.getValue() != null) {
				stanardBasePriece = stanardBasePriece
						.add((BigDecimal) stanardBasePrieceCell.getValue());
			}
			ICell buildBasePriceCell = table.getCell(r, "buildBasePrice");
			if (buildBasePriceCell.getValue() != null) {
				buildBasePrice = buildBasePrice
						.add((BigDecimal) buildBasePriceCell.getValue());
			}
			ICell roomBasePriceCell = table.getCell(r, "roomBasePrice");
			if (roomBasePriceCell.getValue() != null) {
				roomBasePrice = roomBasePrice
						.add((BigDecimal) roomBasePriceCell.getValue());
			}
		}

		if (buildBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			avgBuildPrice = FDCHelper.divide(buildBasePrice, new BigDecimal(
					table.getRowCount()));
		}

		if (roomBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			avgroomPrice = FDCHelper.divide(roomBasePrice, new BigDecimal(table
					.getRowCount()));
		}
		BigDecimal tempBuild = FDCHelper.ZERO;
		BigDecimal tempRoom = FDCHelper.ZERO;
		for (int r = 0; r < table.getRowCount(); r++) {
			ICell buildBasePriceCell = table.getCell(r, "buildBasePrice");
			if (buildBasePriceCell.getValue() != null) {
				tempBuild = (BigDecimal) buildBasePriceCell.getValue();
			}
			ICell roomBasePriceCell = table.getCell(r, "roomBasePrice");
			if (roomBasePriceCell.getValue() != null) {
				tempRoom = (BigDecimal) roomBasePriceCell.getValue();
			}
			if (tempBuild.compareTo(maxBuildPrice) > 0) {
				maxBuildPrice = tempBuild;
			}
			if (r == 0) {
				minBuildPrice = tempBuild;
			} else {
				if (minBuildPrice.compareTo(tempBuild) > 0) {
					minBuildPrice = tempBuild;
				}

			}

			if (tempRoom.compareTo(maxroomPrice) > 0) {
				maxroomPrice = tempRoom;
			}
			if (r == 0) {
				minroomPrice = tempRoom;
			} else {
				if (minroomPrice.compareTo(tempRoom) > 0) {
					minroomPrice = tempRoom;
				}
			}
		}

		IRow totalRow = table.addRow();
		totalRow.setUserObject(null);
		totalRow.getCell("roomNumber").setValue("合计");
		if (stanardTotal.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("stanardTotal").setValue(stanardTotal);
		}

		if (buildArea.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("buildArea").setValue(buildArea);
		}

		if (buildPrice.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("buildPrice").setValue(
					FDCHelper.divide(buildPrice, new BigDecimal(String
							.valueOf(table.getRowCount() - 1))));
		}

		if (roomArea.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("roomArea").setValue(roomArea);
		}

		if (roomPrice.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("roomPrice").setValue(
					FDCHelper.divide(roomPrice, new BigDecimal(String
							.valueOf(table.getRowCount() - 1))));
		}

		if (stanardBasePriece.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("stanardBasePriece").setValue(stanardBasePriece);
		}

		if (buildBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("buildBasePrice").setValue(
					FDCHelper.divide(buildBasePrice, new BigDecimal(String
							.valueOf(table.getRowCount() - 1))));
		}

		if (roomBasePrice.compareTo(FDCHelper.ZERO) > 0) {
			totalRow.getCell("roomBasePrice").setValue(
					FDCHelper.divide(roomBasePrice, new BigDecimal(String
							.valueOf(table.getRowCount() - 1))));
		}
		totalRow.getStyleAttributes().setLocked(true);
		totalRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);

		this.txtAvgBuildingBasePrice.setValue(avgBuildPrice);
		this.txtAvgRoomBasePrice.setValue(avgroomPrice);
		this.txtMaxBuildingBasePrice.setValue(maxBuildPrice);
		this.txtMinBuildingBasePrice.setValue(minBuildPrice);
		this.txtMaxRoomBasePrice.setValue(maxroomPrice);
		this.txtMinRoomBasePrice.setValue(minroomPrice);
	}

	private void addNewRoom(RoomInfo room) {
		this.kDTable1.checkParsed();
		
		if (room == null) {
			return;
		}
		if(this.isRoomExist(room)){  //房间已存在，跳过
			return;
		}
		int activeRowIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.kDTable1.addRow();
		} else {
			row = this.kDTable1.addRow(activeRowIndex + 1);
		}

		showLoanRoom(row, room);
	}

	private void showLoanRoom(IRow row, RoomInfo room) {

		if (room != null) {
			row.setUserObject(room);
			row.getCell("id").setValue(room.getId());
			row.getCell("roomNumber").setValue(room.getDisplayName());
			row.getCell("number").setValue(room.getRoomNo());
			row.getCell("saleType").setValue(room.getSellType());
			row.getCell("stanardTotal").setValue(room.getStandardTotalAmount());
			row.getCell("buildArea").setValue(room.getBuildingArea());
			row.getCell("buildPrice").setValue(room.getBuildPrice());
			row.getCell("roomArea").setValue(room.getRoomArea());
			row.getCell("roomPrice").setValue(room.getRoomPrice());
			row.getCell("stanardBasePriece").setValue(
					room.getBaseStandardPrice());
			if (room.getBaseBuildingPrice() != null) {
				row.getCell("buildBasePrice").setValue(
						room.getBaseBuildingPrice());
			} else {
				row.getCell("buildBasePrice").setValue(FDCHelper.ZERO);
			}
			if (room.getBaseRoomPrice() != null) {
				row.getCell("roomBasePrice").setValue(room.getBaseRoomPrice());
			} else {
				row.getCell("roomBasePrice").setValue(FDCHelper.ZERO);
			}

			if (BaseTypeEnum.BUILDINGAREA
					.equals((BaseTypeEnum) this.comboBaseType.getSelectedItem())) {
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setLocked(false);
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
				row.getCell("buildBasePrice").getStyleAttributes().setLocked(
						false);
				row.getCell("buildBasePrice").getStyleAttributes()
						.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
			} else if (BaseTypeEnum.ROOMAREA
					.equals((BaseTypeEnum) this.comboBaseType.getSelectedItem())) {
				row.getCell("roomBasePrice").getStyleAttributes().setLocked(
						false);
				row.getCell("roomBasePrice").getStyleAttributes()
						.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setLocked(false);
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
			} else if (BaseTypeEnum.STANDARDAMOUNT
					.equals((BaseTypeEnum) this.comboBaseType.getSelectedItem())) {
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setLocked(false);
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
			}
		}
	}
	
	/**
	 * 判断选择的房间是否已存在
	 * @param room
	 * @return
	 */
	private boolean isRoomExist(RoomInfo room){
		boolean isExist = false;
		for(int i=0; i<this.kDTable1.getRowCount(); i++){
			IRow row = this.kDTable1.getRow(i);
			if(row.getUserObject() != null){
				RoomInfo rowRoom = (RoomInfo)row.getUserObject();
				if(rowRoom.getId().toString().equals(room.getId().toString())){
					isExist = true;
				}
			}
		}
		return isExist;
	}

	public void actionImportPrice_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportPrice_actionPerformed(e);
		this.importPrice(this.kDTable1);
	}

	private void importPrice(KDTable table) throws Exception {
		String fileName = SHEHelper.showExcelSelectDlg(this);

		table.getColumn("id").getStyleAttributes().setLocked(true);
		table.getColumn("roomNumber").getStyleAttributes().setLocked(true);
		table.getColumn("number").getStyleAttributes().setLocked(true);
		table.getColumn("saleType").getStyleAttributes().setLocked(true);
		table.getColumn("stanardTotal").getStyleAttributes().setLocked(true);
		table.getColumn("stanardBasePriece").getStyleAttributes().setLocked(
				true);
		table.getColumn("buildArea").getStyleAttributes().setLocked(true);
		table.getColumn("buildPrice").getStyleAttributes().setLocked(true);
		table.getColumn("buildBasePrice").getStyleAttributes().setLocked(true);
		table.getColumn("roomArea").getStyleAttributes().setLocked(true);
		table.getColumn("roomPrice").getStyleAttributes().setLocked(true);
		table.getColumn("roomBasePrice").getStyleAttributes().setLocked(true);

		// excel导入工具会过滤锁定状态的列，所以需暂时解锁
		if (((BaseTypeEnum) this.comboBaseType.getSelectedItem())
				.equals(BaseTypeEnum.STANDARDAMOUNT)) {
			table.getColumn("stanardBasePriece").getStyleAttributes()
					.setLocked(false);
		} else if (((BaseTypeEnum) this.comboBaseType.getSelectedItem())
				.equals(BaseTypeEnum.BUILDINGAREA)) {
			table.getColumn("stanardBasePriece").getStyleAttributes()
					.setLocked(false);
			table.getColumn("buildBasePrice").getStyleAttributes().setLocked(
					false);
		} else if (((BaseTypeEnum) this.comboBaseType.getSelectedItem())
				.equals(BaseTypeEnum.ROOMAREA)) {
			table.getColumn("stanardBasePriece").getStyleAttributes()
					.setLocked(false);
			table.getColumn("roomBasePrice").getStyleAttributes().setLocked(
					false);
		}

		int count = SHEHelper.importExcelToTable2(fileName, table, 1, 3);

		for (int j = 0; j < table.getRowCount(); j++) {
			IRow row = table.getRow(j);
			if (row == null) {
				continue;
			}
			if (j == this.kDTable1.getRowCount() - 1) {
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setLocked(true);
				row.getCell("buildBasePrice").getStyleAttributes().setLocked(
						true);
				row.getCell("roomBasePrice").getStyleAttributes().setLocked(
						true);
			}
		}

		MsgBox.showInfo("已经成功导入" + count + "条数据!");
		for (int i = 0; i < table.getRowCount(); i++) {
			if (i == table.getRowCount() - 1) {
				continue;
			}
			if (((BaseTypeEnum) this.comboBaseType.getSelectedItem())
					.equals(BaseTypeEnum.STANDARDAMOUNT)) {
				this.calcPirceForStandard(i, false);
			} else if (((BaseTypeEnum) this.comboBaseType.getSelectedItem())
					.equals(BaseTypeEnum.BUILDINGAREA)) {
				this.calcPirceForBuild(i, false);
			} else if (((BaseTypeEnum) this.comboBaseType.getSelectedItem())
					.equals(BaseTypeEnum.ROOMAREA)) {
				this.calcPirceForRoom(i, false);
			}
		}
		this.addTotal(table);
	}

	public void comboBaseType_itemStateChanged(java.awt.event.ItemEvent e) {
		initTableLock((BaseTypeEnum) e.getItem());
	}

	private void initTableLock(BaseTypeEnum type) {
		this.kDTable1.checkParsed();
		for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
			IRow row = this.kDTable1.getRow(i);
			if (row == null) {
				continue;
			}
			if (i == this.kDTable1.getRowCount() - 1) {
				row.getCell("stanardBasePriece").getStyleAttributes()
						.setLocked(true);
				row.getCell("buildBasePrice").getStyleAttributes().setLocked(
						true);
				row.getCell("roomBasePrice").getStyleAttributes().setLocked(
						true);
			} else {
				if (type.equals(BaseTypeEnum.STANDARDAMOUNT)) {
					row.getCell("stanardBasePriece").getStyleAttributes()
							.setLocked(false);
					row.getCell("stanardBasePriece").getStyleAttributes()
							.setBackground(
									FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getCell("buildBasePrice").getStyleAttributes()
							.setLocked(true);
					row.getCell("buildBasePrice").getStyleAttributes()
							.setBackground(null);
					row.getCell("roomBasePrice").getStyleAttributes()
							.setLocked(true);
					row.getCell("roomBasePrice").getStyleAttributes()
							.setBackground(null);
				} else if (type.equals(BaseTypeEnum.BUILDINGAREA)) {
					row.getCell("stanardBasePriece").getStyleAttributes()
							.setLocked(false);
					row.getCell("stanardBasePriece").getStyleAttributes()
							.setBackground(
									FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getCell("buildBasePrice").getStyleAttributes()
							.setLocked(false);
					row.getCell("buildBasePrice").getStyleAttributes()
							.setBackground(
									FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getCell("roomBasePrice").getStyleAttributes()
							.setLocked(true);
					row.getCell("roomBasePrice").getStyleAttributes()
							.setBackground(null);
				} else if (type.equals(BaseTypeEnum.ROOMAREA)) {
					row.getCell("stanardBasePriece").getStyleAttributes()
							.setLocked(false);
					row.getCell("stanardBasePriece").getStyleAttributes()
							.setBackground(
									FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getCell("buildBasePrice").getStyleAttributes()
							.setLocked(true);
					row.getCell("buildBasePrice").getStyleAttributes()
							.setBackground(null);
					row.getCell("roomBasePrice").getStyleAttributes()
							.setLocked(false);
					row.getCell("roomBasePrice").getStyleAttributes()
							.setBackground(
									FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
				}
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		if (this.kDTable1.getRowCount() <= 0) {
			FDCMsgBox.showWarning(this, "请选择房间!");
			this.abort();
		} else {
			boolean isFull = true;
			for (int i = 0; i < kDTable1.getRowCount(); i++) {
				if (i == kDTable1.getRowCount() - 1) {
					continue;
				}
				IRow row = kDTable1.getRow(i);
				if (row == null) {
					continue;
				}

				BaseTypeEnum type = (BaseTypeEnum) this.comboBaseType
						.getSelectedItem();
				if (type.equals(BaseTypeEnum.BUILDINGAREA)) {
					if (row.getCell("buildBasePrice").getValue() == null) {
						isFull = false;
					}
					if (row.getCell("stanardBasePriece").getValue() == null) {
						isFull = false;
					}
				} else if (type.equals(BaseTypeEnum.ROOMAREA)) {
					if (row.getCell("roomBasePrice").getValue() == null) {
						isFull = false;
					}
					if (row.getCell("stanardBasePriece").getValue() == null) {
						isFull = false;
					}
				} else if (type.equals(BaseTypeEnum.STANDARDAMOUNT)) {
					if (row.getCell("stanardBasePriece").getValue() == null) {
						isFull = false;
					}
				}
			}

			if (!isFull) {
				FDCMsgBox.showWarning(this, "底价信息不完整，请补全信息!");
				this.abort();
			}
		}
		if (this.editData.getId() != null) {

			IBasePriceControl iBase = BasePriceControlFactory
					.getRemoteInstance();
			BasePriceControlInfo info = iBase
					.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
							+ this.editData.getId().toString() + "'");
			if (info != null) {
				if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
					FDCMsgBox.showWarning(this, "单据已审批，不能提交!");
					this.abort();
				} else if (info.getState().equals(FDCBillStateEnum.AUDITTING)) {
					if (info.isIsExecuted()) {
						FDCMsgBox.showWarning(this, "单据审批中，不能提交!");
						this.abort();
					}
				} else if (!info.getState().equals(FDCBillStateEnum.SUBMITTED)) {
					if (info.isIsExecuted()) {
						FDCMsgBox.showWarning(this, "单据状态不适合提交，请检查!");
						this.abort();
					}
				}
			}
		}
		this.isOld = true;
		super.actionSubmit_actionPerformed(e);
		comboBaseType.setSelectedItem(BaseTypeEnum.BUILDINGAREA);

		if (this.getUIContext().get("selectRoom") != null) {
			this.kDTable1.removeRows();
		}

		// this.actionAddNew_actionPerformed(e);
		handleCodingRule();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		SellProjectInfo info = (SellProjectInfo) this.prmtProject.getValue();
		super.actionAddNew_actionPerformed(e);
		handleCodingRule();
		this.prmtProject.setValue(info);
		clearData();

	}

	private void clearData() throws BOSException {
		this.txtName.setText(null);
		this.txtNumber.setText(null);
		this.comboBaseType.setSelectedItem(BaseTypeEnum.BUILDINGAREA);
		this.kDTable1.removeRows();
		this.prmtBuilding.setValue(null);
		this.txtAvgBuildingBasePrice.setValue(null);
		this.txtAvgRoomBasePrice.setValue(null);
		this.txtMaxBuildingBasePrice.setValue(null);
		this.txtMinBuildingBasePrice.setValue(null);
		this.txtMaxRoomBasePrice.setValue(null);
		this.txtMinRoomBasePrice.setValue(null);
		this.prmtCreator.setValue(SysContext.getSysContext()
				.getCurrentUserInfo());
		this.pkBizDate.setValue(FDCSQLFacadeFactory.getRemoteInstance()
				.getServerTime());
		this.txtDescription.setText(null);
	}

	public void actionCalc_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalc_actionPerformed(e);
		CalculatorDialog calc = new CalculatorDialog(this, true);
		calc.showDialog(2, true);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning(this, "请先提交!");
			this.abort();
		}

		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state where id='"
						+ this.editData.getId().toString() + "'");
		if (info != null) {
			if (!info.getState().equals(FDCBillStateEnum.SUBMITTED)) {
				FDCMsgBox.showWarning(this, "单据状态不适合做审批操作，请检查!");
				this.abort();
			}
		}
		/*
		 * SelectorItemCollection selector = new SelectorItemCollection();
		 * selector.add(new SelectorItemInfo("state")); selector.add(new
		 * SelectorItemInfo("auditor")); selector.add(new
		 * SelectorItemInfo("auditTime")); BasePriceControlInfo model = new
		 * BasePriceControlInfo(); model.setId(this.editData.getId());
		 * model.setState(FDCBillStateEnum.AUDITTED);
		 * model.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
		 * model
		 * .setAuditTime(FDCSQLFacadeFactory.getRemoteInstance().getServerTime
		 * ());
		 */
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());

		try {
			iBase.auditBasePrice(this.editData.getId());
			FDCMsgBox.showWarning(this, "审批成功!");
			loadFields();
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "审批失败!");
		}

	}

	public void actionExceuted_actionPerformed(ActionEvent e) throws Exception {
		super.actionExceuted_actionPerformed(e);
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning(this, "请先提交!");
			this.abort();
		}

		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
						+ this.editData.getId().toString() + "'");
		if (info != null) {
			if (!info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning(this, "不能执行，请检查单据状态!");
				this.abort();
			} else {
				if (info.isIsExecuted()) {
					FDCMsgBox.showWarning(this, "已经执行，请不要重复操作!");
					this.abort();
				}
			}
		}
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("isExecuted"));
		BasePriceControlInfo model = new BasePriceControlInfo();
		model.setId(this.editData.getId());
		model.setIsExecuted(true);
		try {
			iBase.updatePartial(model, selector);
			FDCMsgBox.showWarning(this, "执行成功!");
			this.abort();
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "执行失败!");
		}

	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning(this, "请先提交!");
			this.abort();
		}
		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
						+ this.editData.getId().toString() + "'");

		if (info != null) {
			if (info.isIsExecuted()) {
				FDCMsgBox.showWarning(this, "单据已经执行，不能删除!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning(this, "单据已审批，不能删除!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能删除!");
				this.abort();
			}
		}
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId()
				.toString());
		super.actionRemove_actionPerformed(e);
		this.actionAddNew_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			BasePriceControlDataProvider data = new BasePriceControlDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.BasePriceControlForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/sellhouse/priceManage", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrintPreview_actionPerformed(e);
		}
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			BasePriceControlDataProvider data = new BasePriceControlDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.BasePriceControlForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/priceManage", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrint_actionPerformed(e);
		}
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

	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		BasePriceControlInfo pur = new BasePriceControlInfo();
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
						new BasePriceControlInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
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